package com.koushikdutta.async.http.socketio;

import android.net.Uri;
import android.text.TextUtils;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.DependentCancellable;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.future.TransformFuture;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.socketio.transport.SocketIOTransport;
import com.koushikdutta.async.http.socketio.transport.SocketIOTransport.StringCallback;
import com.koushikdutta.async.http.socketio.transport.WebSocketTransport;
import com.koushikdutta.async.http.socketio.transport.XHRPollingTransport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

class SocketIOConnection {
    int ackCount;
    Hashtable<String, Acknowledge> acknowledges = new Hashtable();
    ArrayList<SocketIOClient> clients = new ArrayList();
    Cancellable connecting;
    int heartbeat;
    AsyncHttpClient httpClient;
    long reconnectDelay;
    SocketIORequest request;
    SocketIOTransport transport;

    /* renamed from: com.koushikdutta.async.http.socketio.SocketIOConnection$3 */
    class C06613 implements Runnable {
        C06613() {
        }

        public void run() {
            SocketIOTransport ts = SocketIOConnection.this.transport;
            if (SocketIOConnection.this.heartbeat > 0 && ts != null) {
                if (ts.isConnected()) {
                    ts.send("2:::");
                    ts.getServer().postDelayed(this, (long) SocketIOConnection.this.heartbeat);
                }
            }
        }
    }

    /* renamed from: com.koushikdutta.async.http.socketio.SocketIOConnection$4 */
    class C06624 implements Runnable {
        C06624() {
        }

        public void run() {
            SocketIOConnection.this.reconnect(null);
        }
    }

    private interface SelectCallback {
        void onSelect(SocketIOClient socketIOClient);
    }

    /* renamed from: com.koushikdutta.async.http.socketio.SocketIOConnection$1 */
    class C11821 implements FutureCallback<SocketIOTransport> {
        C11821() {
        }

        public void onCompleted(Exception e, SocketIOTransport result) {
            if (e != null) {
                SocketIOConnection.this.reportDisconnect(e);
                return;
            }
            SocketIOConnection.this.reconnectDelay = SocketIOConnection.this.request.config.reconnectDelay;
            SocketIOConnection.this.transport = result;
            SocketIOConnection.this.attach();
        }
    }

    /* renamed from: com.koushikdutta.async.http.socketio.SocketIOConnection$6 */
    class C11856 implements SelectCallback {
        C11856() {
        }

        public void onSelect(SocketIOClient client) {
            if (!client.isConnected()) {
                if (!client.connected) {
                    client.connected = true;
                    ConnectCallback callback = client.connectCallback;
                    if (callback != null) {
                        callback.onConnectCompleted(null, client);
                    }
                } else if (client.disconnected) {
                    client.disconnected = false;
                    ReconnectCallback callback2 = client.reconnectCallback;
                    if (callback2 != null) {
                        callback2.onReconnect();
                    }
                }
            }
        }
    }

    /* renamed from: com.koushikdutta.async.http.socketio.SocketIOConnection$2 */
    class C13612 extends TransformFuture<SocketIOTransport, String> {
        C13612() {
        }

        protected void transform(String result) throws Exception {
            String[] parts = result.split(":");
            final String sessionId = parts[0];
            if ("".equals(parts[1])) {
                SocketIOConnection.this.heartbeat = 0;
            } else {
                SocketIOConnection.this.heartbeat = (Integer.parseInt(parts[1]) / 2) * 1000;
            }
            HashSet<String> set = new HashSet(Arrays.asList(parts[3].split(",")));
            final SimpleFuture<SocketIOTransport> transport = new SimpleFuture();
            if (set.contains("websocket")) {
                SocketIOConnection.this.httpClient.websocket(Uri.parse(SocketIOConnection.this.request.getUri().toString()).buildUpon().appendPath("websocket").appendPath(sessionId).build().toString(), null, null).setCallback(new FutureCallback<WebSocket>() {
                    public void onCompleted(Exception e, WebSocket result) {
                        if (e != null) {
                            transport.setComplete(e);
                        } else {
                            transport.setComplete(new WebSocketTransport(result, sessionId));
                        }
                    }
                });
            } else if (set.contains("xhr-polling")) {
                transport.setComplete(new XHRPollingTransport(SocketIOConnection.this.httpClient, Uri.parse(SocketIOConnection.this.request.getUri().toString()).buildUpon().appendPath("xhr-polling").appendPath(sessionId).build().toString(), sessionId));
            } else {
                throw new SocketIOException("transport not supported");
            }
            setComplete((Future) transport);
        }
    }

    public SocketIOConnection(AsyncHttpClient httpClient, SocketIORequest request) {
        this.httpClient = httpClient;
        this.request = request;
        this.reconnectDelay = this.request.config.reconnectDelay;
    }

    public boolean isConnected() {
        return this.transport != null && this.transport.isConnected();
    }

    public void emitRaw(int type, SocketIOClient client, String message, Acknowledge acknowledge) {
        String ack = "";
        if (acknowledge != null) {
            String id = new StringBuilder();
            id.append("");
            int i = this.ackCount;
            this.ackCount = i + 1;
            id.append(i);
            id = id.toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(id);
            stringBuilder.append("+");
            ack = stringBuilder.toString();
            this.acknowledges.put(id, acknowledge);
        }
        this.transport.send(String.format(Locale.ENGLISH, "%d:%s:%s:%s", new Object[]{Integer.valueOf(type), ack, client.endpoint, message}));
    }

    public void connect(SocketIOClient client) {
        if (!this.clients.contains(client)) {
            this.clients.add(client);
        }
        this.transport.send(String.format(Locale.ENGLISH, "1::%s", new Object[]{client.endpoint}));
    }

    public void disconnect(SocketIOClient client) {
        this.clients.remove(client);
        boolean needsEndpointDisconnect = true;
        Iterator it = this.clients.iterator();
        while (it.hasNext()) {
            if (!TextUtils.equals(((SocketIOClient) it.next()).endpoint, client.endpoint)) {
                if (TextUtils.isEmpty(client.endpoint)) {
                }
            }
            needsEndpointDisconnect = false;
        }
        SocketIOTransport ts = this.transport;
        if (needsEndpointDisconnect && ts != null) {
            ts.send(String.format(Locale.ENGLISH, "0::%s", new Object[]{client.endpoint}));
        }
        if (this.clients.size() <= 0) {
            if (ts != null) {
                ts.setStringCallback(null);
                ts.setClosedCallback(null);
                ts.disconnect();
                this.transport = null;
            }
        }
    }

    void reconnect(DependentCancellable child) {
        if (!isConnected()) {
            if (this.connecting == null || this.connecting.isDone() || this.connecting.isCancelled()) {
                this.request.logi("Reconnecting socket.io");
                this.connecting = ((C13612) this.httpClient.executeString(this.request, null).then(new C13612())).setCallback(new C11821());
                if (child != null) {
                    child.setParent(this.connecting);
                }
                return;
            }
            if (child != null) {
                child.setParent(this.connecting);
            }
        }
    }

    void setupHeartbeat() {
        new C06613().run();
    }

    private void select(String endpoint, SelectCallback callback) {
        Iterator it = this.clients.iterator();
        while (it.hasNext()) {
            SocketIOClient client = (SocketIOClient) it.next();
            if (endpoint == null || TextUtils.equals(client.endpoint, endpoint)) {
                callback.onSelect(client);
            }
        }
    }

    private void delayReconnect() {
        if (this.transport == null) {
            if (this.clients.size() != 0) {
                boolean disconnected = false;
                Iterator it = this.clients.iterator();
                while (it.hasNext()) {
                    if (((SocketIOClient) it.next()).disconnected) {
                        disconnected = true;
                        break;
                    }
                }
                if (disconnected) {
                    this.httpClient.getServer().postDelayed(new C06624(), nextReconnectDelay(this.reconnectDelay));
                    this.reconnectDelay *= 2;
                    if (this.request.config.reconnectDelayMax > 0) {
                        this.reconnectDelay = Math.min(this.reconnectDelay, this.request.config.reconnectDelayMax);
                    }
                }
            }
        }
    }

    private long nextReconnectDelay(long targetDelay) {
        if (targetDelay >= 2 && targetDelay <= 4611686018427387903L) {
            if (this.request.config.randomizeReconnectDelay) {
                return (targetDelay >> 1) + ((long) (((double) targetDelay) * Math.random()));
            }
        }
        return targetDelay;
    }

    private void reportDisconnect(final Exception ex) {
        if (ex != null) {
            this.request.loge("socket.io disconnected", ex);
        } else {
            this.request.logi("socket.io disconnected");
        }
        select(null, new SelectCallback() {
            public void onSelect(SocketIOClient client) {
                if (client.connected) {
                    client.disconnected = true;
                    DisconnectCallback closed = client.getDisconnectCallback();
                    if (closed != null) {
                        closed.onDisconnect(ex);
                    }
                    return;
                }
                ConnectCallback callback = client.connectCallback;
                if (callback != null) {
                    callback.onConnectCompleted(ex, client);
                }
            }
        });
        delayReconnect();
    }

    private void reportConnect(String endpoint) {
        select(endpoint, new C11856());
    }

    private void reportJson(String endpoint, final JSONObject jsonMessage, final Acknowledge acknowledge) {
        select(endpoint, new SelectCallback() {
            public void onSelect(SocketIOClient client) {
                JSONCallback callback = client.jsonCallback;
                if (callback != null) {
                    callback.onJSON(jsonMessage, acknowledge);
                }
            }
        });
    }

    private void reportString(String endpoint, final String string, final Acknowledge acknowledge) {
        select(endpoint, new SelectCallback() {
            public void onSelect(SocketIOClient client) {
                StringCallback callback = client.stringCallback;
                if (callback != null) {
                    callback.onString(string, acknowledge);
                }
            }
        });
    }

    private void reportEvent(String endpoint, final String event, final JSONArray arguments, final Acknowledge acknowledge) {
        select(endpoint, new SelectCallback() {
            public void onSelect(SocketIOClient client) {
                client.onEvent(event, arguments, acknowledge);
            }
        });
    }

    private void reportError(String endpoint, final String error) {
        select(endpoint, new SelectCallback() {
            public void onSelect(SocketIOClient client) {
                ErrorCallback callback = client.errorCallback;
                if (callback != null) {
                    callback.onError(error);
                }
            }
        });
    }

    private Acknowledge acknowledge(String _messageId, final String endpoint) {
        if (TextUtils.isEmpty(_messageId)) {
            return null;
        }
        final String messageId = _messageId.replaceAll("\\+$", "");
        return new Acknowledge() {
            public void acknowledge(JSONArray arguments) {
                String data = "";
                if (arguments != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(data);
                    stringBuilder.append("+");
                    stringBuilder.append(arguments.toString());
                    data = stringBuilder.toString();
                }
                SocketIOTransport transport = SocketIOConnection.this.transport;
                if (transport == null) {
                    final Exception e = new SocketIOException("not connected to server");
                    SocketIOConnection.this.select(endpoint, new SelectCallback() {
                        public void onSelect(SocketIOClient client) {
                            ExceptionCallback callback = client.exceptionCallback;
                            if (callback != null) {
                                callback.onException(e);
                            }
                        }
                    });
                    return;
                }
                transport.send(String.format(Locale.ENGLISH, "6:::%s%s", new Object[]{messageId, data}));
            }
        };
    }

    private void attach() {
        if (this.transport.heartbeats()) {
            setupHeartbeat();
        }
        this.transport.setClosedCallback(new CompletedCallback() {
            public void onCompleted(Exception ex) {
                SocketIOConnection.this.transport = null;
                SocketIOConnection.this.reportDisconnect(ex);
            }
        });
        this.transport.setStringCallback(new StringCallback() {
            public void onStringAvailable(String message) {
                try {
                    String[] parts = message.split(":", 4);
                    switch (Integer.parseInt(parts[0])) {
                        case 0:
                            SocketIOConnection.this.transport.disconnect();
                            SocketIOConnection.this.reportDisconnect(null);
                            break;
                        case 1:
                            SocketIOConnection.this.reportConnect(parts[2]);
                            break;
                        case 2:
                            SocketIOConnection.this.transport.send("2::");
                            break;
                        case 3:
                            SocketIOConnection.this.reportString(parts[2], parts[3], SocketIOConnection.this.acknowledge(parts[1], parts[2]));
                            break;
                        case 4:
                            SocketIOConnection.this.reportJson(parts[2], new JSONObject(parts[3]), SocketIOConnection.this.acknowledge(parts[1], parts[2]));
                            break;
                        case 5:
                            JSONObject data = new JSONObject(parts[3]);
                            SocketIOConnection.this.reportEvent(parts[2], data.getString("name"), data.optJSONArray("args"), SocketIOConnection.this.acknowledge(parts[1], parts[2]));
                            break;
                        case 6:
                            String[] ackParts = parts[3].split("\\+", 2);
                            Acknowledge ack = (Acknowledge) SocketIOConnection.this.acknowledges.remove(ackParts[0]);
                            if (ack != null) {
                                JSONArray arguments = null;
                                if (ackParts.length == 2) {
                                    arguments = new JSONArray(ackParts[1]);
                                }
                                ack.acknowledge(arguments);
                                break;
                            }
                            return;
                        case 7:
                            SocketIOConnection.this.reportError(parts[2], parts[3]);
                            break;
                        case 8:
                            break;
                        default:
                            throw new SocketIOException("unknown code");
                    }
                } catch (Exception ex) {
                    SocketIOConnection.this.transport.setClosedCallback(null);
                    SocketIOConnection.this.transport.disconnect();
                    SocketIOConnection.this.transport = null;
                    SocketIOConnection.this.reportDisconnect(ex);
                }
            }
        });
        select(null, new SelectCallback() {
            public void onSelect(SocketIOClient client) {
                if (!TextUtils.isEmpty(client.endpoint)) {
                    SocketIOConnection.this.connect(client);
                }
            }
        });
    }
}
