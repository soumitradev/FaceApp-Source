package com.koushikdutta.async.http.socketio.transport;

import android.net.Uri;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.StringBody;
import com.koushikdutta.async.http.socketio.transport.SocketIOTransport.StringCallback;

public class XHRPollingTransport implements SocketIOTransport {
    private static final String SEPARATOR = "�";
    private AsyncHttpClient client;
    private CompletedCallback closedCallback;
    private boolean connected = true;
    private String sessionId;
    private Uri sessionUrl;
    private StringCallback stringCallback;

    /* renamed from: com.koushikdutta.async.http.socketio.transport.XHRPollingTransport$1 */
    class C13571 extends AsyncHttpClient.StringCallback {
        C13571() {
        }

        public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
            if (e != null) {
                XHRPollingTransport.this.close(e);
            } else {
                XHRPollingTransport.this.sendResult(result);
            }
        }
    }

    /* renamed from: com.koushikdutta.async.http.socketio.transport.XHRPollingTransport$2 */
    class C13582 extends AsyncHttpClient.StringCallback {
        C13582() {
        }

        public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
            if (e != null) {
                XHRPollingTransport.this.close(e);
                return;
            }
            XHRPollingTransport.this.sendResult(result);
            XHRPollingTransport.this.doLongPolling();
        }
    }

    public XHRPollingTransport(AsyncHttpClient client, String sessionUrl, String sessionId) {
        this.client = client;
        this.sessionUrl = Uri.parse(sessionUrl);
        this.sessionId = sessionId;
        doLongPolling();
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void setClosedCallback(CompletedCallback handler) {
        this.closedCallback = handler;
    }

    public void disconnect() {
        this.connected = false;
        close(null);
    }

    private void close(Exception ex) {
        if (this.closedCallback != null) {
            this.closedCallback.onCompleted(ex);
        }
    }

    public AsyncServer getServer() {
        return this.client.getServer();
    }

    public void send(String message) {
        if (message.startsWith("5")) {
            postMessage(message);
            return;
        }
        AsyncHttpRequest request = new AsyncHttpPost(computedRequestUrl());
        request.setBody(new StringBody(message));
        this.client.executeString(request, new C13571());
    }

    private void postMessage(String message) {
        if (message.startsWith("5")) {
            AsyncHttpRequest request = new AsyncHttpPost(computedRequestUrl());
            request.setBody(new StringBody(message));
            this.client.executeString(request, null);
        }
    }

    private void doLongPolling() {
        this.client.executeString(new AsyncHttpGet(computedRequestUrl()), new C13582());
    }

    private void sendResult(String result) {
        if (this.stringCallback != null) {
            if (result.contains(SEPARATOR)) {
                String[] results = result.split(SEPARATOR);
                for (int i = 1; i < results.length; i += 2) {
                    this.stringCallback.onStringAvailable(results[i + 1]);
                }
                return;
            }
            this.stringCallback.onStringAvailable(result);
        }
    }

    private String computedRequestUrl() {
        return this.sessionUrl.buildUpon().appendQueryParameter("t", String.valueOf(System.currentTimeMillis())).build().toString();
    }

    public void setStringCallback(StringCallback callback) {
        this.stringCallback = callback;
    }

    public boolean heartbeats() {
        return false;
    }

    public String getSessionId() {
        return this.sessionId;
    }
}
