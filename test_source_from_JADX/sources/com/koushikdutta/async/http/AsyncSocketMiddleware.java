package com.koushikdutta.async.http;

import android.net.Uri;
import com.koushikdutta.async.ArrayDeque;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback.NullDataCallback;
import com.koushikdutta.async.future.SimpleCancellable;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.GetSocketData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnResponseCompleteDataOnRequestSentData;
import java.net.InetSocketAddress;
import java.util.Hashtable;

public class AsyncSocketMiddleware extends SimpleMiddleware {
    boolean connectAllAddresses;
    Hashtable<String, ConnectionInfo> connectionInfo;
    int idleTimeoutMs;
    protected AsyncHttpClient mClient;
    int maxConnectionCount;
    int port;
    InetSocketAddress proxyAddress;
    String proxyHost;
    int proxyPort;
    String scheme;

    static class ConnectionInfo {
        int openCount;
        ArrayDeque<GetSocketData> queue = new ArrayDeque();
        ArrayDeque<IdleSocketHolder> sockets = new ArrayDeque();

        ConnectionInfo() {
        }
    }

    class IdleSocketHolder {
        long idleTime = System.currentTimeMillis();
        AsyncSocket socket;

        public IdleSocketHolder(AsyncSocket socket) {
            this.socket = socket;
        }
    }

    public AsyncSocketMiddleware(AsyncHttpClient client, String scheme, int port) {
        this.idleTimeoutMs = 300000;
        this.connectionInfo = new Hashtable();
        this.maxConnectionCount = Integer.MAX_VALUE;
        this.mClient = client;
        this.scheme = scheme;
        this.port = port;
    }

    public void setIdleTimeoutMs(int idleTimeoutMs) {
        this.idleTimeoutMs = idleTimeoutMs;
    }

    public int getSchemePort(Uri uri) {
        if (uri.getScheme() != null) {
            if (uri.getScheme().equals(this.scheme)) {
                if (uri.getPort() == -1) {
                    return this.port;
                }
                return uri.getPort();
            }
        }
        return -1;
    }

    public AsyncSocketMiddleware(AsyncHttpClient client) {
        this(client, "http", 80);
    }

    protected ConnectCallback wrapCallback(GetSocketData data, Uri uri, int port, boolean proxied, ConnectCallback callback) {
        return callback;
    }

    public boolean getConnectAllAddresses() {
        return this.connectAllAddresses;
    }

    public void setConnectAllAddresses(boolean connectAllAddresses) {
        this.connectAllAddresses = connectAllAddresses;
    }

    public void disableProxy() {
        this.proxyPort = -1;
        this.proxyHost = null;
        this.proxyAddress = null;
    }

    public void enableProxy(String host, int port) {
        this.proxyHost = host;
        this.proxyPort = port;
        this.proxyAddress = null;
    }

    String computeLookup(Uri uri, int port, String proxyHost, int proxyPort) {
        String proxy;
        StringBuilder stringBuilder;
        if (proxyHost != null) {
            proxy = new StringBuilder();
            proxy.append(proxyHost);
            proxy.append(":");
            proxy.append(proxyPort);
            proxy = proxy.toString();
        } else {
            proxy = "";
        }
        if (proxyHost != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(proxyHost);
            stringBuilder.append(":");
            stringBuilder.append(proxyPort);
            proxy = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(uri.getScheme());
        stringBuilder.append("//");
        stringBuilder.append(uri.getHost());
        stringBuilder.append(":");
        stringBuilder.append(port);
        stringBuilder.append("?proxy=");
        stringBuilder.append(proxy);
        return stringBuilder.toString();
    }

    public int getMaxConnectionCount() {
        return this.maxConnectionCount;
    }

    public void setMaxConnectionCount(int maxConnectionCount) {
        this.maxConnectionCount = maxConnectionCount;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.koushikdutta.async.future.Cancellable getSocket(final com.koushikdutta.async.http.AsyncHttpClientMiddleware.GetSocketData r15) {
        /*
        r14 = this;
        r0 = r15.request;
        r0 = r0.getUri();
        r1 = r15.request;
        r1 = r1.getUri();
        r7 = r14.getSchemePort(r1);
        r1 = 0;
        r2 = -1;
        if (r7 != r2) goto L_0x0015;
    L_0x0014:
        return r1;
    L_0x0015:
        r2 = r15.state;
        r3 = "socket-owner";
        r2.put(r3, r14);
        r2 = r15.request;
        r2 = r2.getProxyHost();
        r3 = r15.request;
        r3 = r3.getProxyPort();
        r8 = r14.computeLookup(r0, r7, r2, r3);
        r9 = r14.getOrCreateConnectionInfo(r8);
        monitor-enter(r14);
        r2 = r9.openCount;	 Catch:{ all -> 0x0134 }
        r3 = r14.maxConnectionCount;	 Catch:{ all -> 0x0134 }
        if (r2 < r3) goto L_0x0043;
    L_0x0037:
        r1 = new com.koushikdutta.async.future.SimpleCancellable;	 Catch:{ all -> 0x0134 }
        r1.<init>();	 Catch:{ all -> 0x0134 }
        r2 = r9.queue;	 Catch:{ all -> 0x0134 }
        r2.add(r15);	 Catch:{ all -> 0x0134 }
        monitor-exit(r14);	 Catch:{ all -> 0x0134 }
        return r1;
    L_0x0043:
        r2 = r9.openCount;	 Catch:{ all -> 0x0134 }
        r2 = r2 + 1;
        r9.openCount = r2;	 Catch:{ all -> 0x0134 }
    L_0x0049:
        r2 = r9.sockets;	 Catch:{ all -> 0x0134 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x0134 }
        if (r2 != 0) goto L_0x008e;
    L_0x0051:
        r2 = r9.sockets;	 Catch:{ all -> 0x0134 }
        r2 = r2.pop();	 Catch:{ all -> 0x0134 }
        r2 = (com.koushikdutta.async.http.AsyncSocketMiddleware.IdleSocketHolder) r2;	 Catch:{ all -> 0x0134 }
        r3 = r2.socket;	 Catch:{ all -> 0x0134 }
        r4 = r2.idleTime;	 Catch:{ all -> 0x0134 }
        r6 = r14.idleTimeoutMs;	 Catch:{ all -> 0x0134 }
        r10 = (long) r6;	 Catch:{ all -> 0x0134 }
        r12 = r4 + r10;
        r4 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x0134 }
        r6 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1));
        if (r6 >= 0) goto L_0x0071;
    L_0x006a:
        r3.setClosedCallback(r1);	 Catch:{ all -> 0x0134 }
        r3.close();	 Catch:{ all -> 0x0134 }
        goto L_0x0049;
    L_0x0071:
        r4 = r3.isOpen();	 Catch:{ all -> 0x0134 }
        if (r4 != 0) goto L_0x0078;
    L_0x0077:
        goto L_0x0049;
    L_0x0078:
        r4 = r15.request;	 Catch:{ all -> 0x0134 }
        r5 = "Reusing keep-alive socket";
        r4.logd(r5);	 Catch:{ all -> 0x0134 }
        r4 = r15.connectCallback;	 Catch:{ all -> 0x0134 }
        r4.onConnectCompleted(r1, r3);	 Catch:{ all -> 0x0134 }
        r1 = new com.koushikdutta.async.future.SimpleCancellable;	 Catch:{ all -> 0x0134 }
        r1.<init>();	 Catch:{ all -> 0x0134 }
        r1.setComplete();	 Catch:{ all -> 0x0134 }
        monitor-exit(r14);	 Catch:{ all -> 0x0134 }
        return r1;
    L_0x008e:
        monitor-exit(r14);	 Catch:{ all -> 0x0134 }
        r1 = r14.connectAllAddresses;
        if (r1 == 0) goto L_0x00c1;
    L_0x0093:
        r1 = r14.proxyHost;
        if (r1 != 0) goto L_0x00c1;
    L_0x0097:
        r1 = r15.request;
        r1 = r1.getProxyHost();
        if (r1 == 0) goto L_0x00a0;
    L_0x009f:
        goto L_0x00c1;
    L_0x00a0:
        r1 = r15.request;
        r2 = "Resolving domain and connecting to all available addresses";
        r1.logv(r2);
        r1 = r14.mClient;
        r1 = r1.getServer();
        r2 = r0.getHost();
        r1 = r1.getAllByName(r2);
        r2 = new com.koushikdutta.async.http.AsyncSocketMiddleware$1;
        r2.<init>(r15, r0, r7);
        r1 = r1.then(r2);
        r1 = (com.koushikdutta.async.future.Cancellable) r1;
        return r1;
    L_0x00c1:
        r1 = r15.request;
        r2 = "Connecting socket";
        r1.logd(r2);
        r1 = 0;
        r2 = r15.request;
        r2 = r2.getProxyHost();
        if (r2 != 0) goto L_0x00de;
    L_0x00d1:
        r2 = r14.proxyHost;
        if (r2 == 0) goto L_0x00de;
    L_0x00d5:
        r2 = r15.request;
        r3 = r14.proxyHost;
        r4 = r14.proxyPort;
        r2.enableProxy(r3, r4);
    L_0x00de:
        r2 = r15.request;
        r2 = r2.getProxyHost();
        if (r2 == 0) goto L_0x00f6;
    L_0x00e6:
        r2 = r15.request;
        r2 = r2.getProxyHost();
        r3 = r15.request;
        r3 = r3.getProxyPort();
        r1 = 1;
        r10 = r1;
        r11 = r2;
        goto L_0x00fd;
    L_0x00f6:
        r2 = r0.getHost();
        r10 = r1;
        r11 = r2;
        r3 = r7;
    L_0x00fd:
        r12 = r3;
        if (r10 == 0) goto L_0x011e;
    L_0x0100:
        r1 = r15.request;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Using proxy: ";
        r2.append(r3);
        r2.append(r11);
        r3 = ":";
        r2.append(r3);
        r2.append(r12);
        r2 = r2.toString();
        r1.logv(r2);
    L_0x011e:
        r1 = r14.mClient;
        r13 = r1.getServer();
        r6 = r15.connectCallback;
        r1 = r14;
        r2 = r15;
        r3 = r0;
        r4 = r7;
        r5 = r10;
        r1 = r1.wrapCallback(r2, r3, r4, r5, r6);
        r1 = r13.connectSocket(r11, r12, r1);
        return r1;
    L_0x0134:
        r1 = move-exception;
        monitor-exit(r14);	 Catch:{ all -> 0x0134 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.http.AsyncSocketMiddleware.getSocket(com.koushikdutta.async.http.AsyncHttpClientMiddleware$GetSocketData):com.koushikdutta.async.future.Cancellable");
    }

    private ConnectionInfo getOrCreateConnectionInfo(String lookup) {
        ConnectionInfo info = (ConnectionInfo) this.connectionInfo.get(lookup);
        if (info != null) {
            return info;
        }
        info = new ConnectionInfo();
        this.connectionInfo.put(lookup, info);
        return info;
    }

    private void maybeCleanupConnectionInfo(String lookup) {
        ConnectionInfo info = (ConnectionInfo) this.connectionInfo.get(lookup);
        if (info != null) {
            while (!info.sockets.isEmpty()) {
                IdleSocketHolder idleSocketHolder = (IdleSocketHolder) info.sockets.peekLast();
                AsyncSocket socket = idleSocketHolder.socket;
                if (idleSocketHolder.idleTime + ((long) this.idleTimeoutMs) > System.currentTimeMillis()) {
                    break;
                }
                info.sockets.pop();
                socket.setClosedCallback(null);
                socket.close();
            }
            if (info.openCount == 0 && info.queue.isEmpty() && info.sockets.isEmpty()) {
                this.connectionInfo.remove(lookup);
            }
        }
    }

    private void recycleSocket(AsyncSocket socket, AsyncHttpRequest request) {
        if (socket != null) {
            final ArrayDeque<IdleSocketHolder> sockets;
            Uri uri = request.getUri();
            final String lookup = computeLookup(uri, getSchemePort(uri), request.getProxyHost(), request.getProxyPort());
            final IdleSocketHolder idleSocketHolder = new IdleSocketHolder(socket);
            synchronized (this) {
                sockets = getOrCreateConnectionInfo(lookup).sockets;
                sockets.push(idleSocketHolder);
            }
            socket.setClosedCallback(new CompletedCallback() {
                public void onCompleted(Exception ex) {
                    synchronized (AsyncSocketMiddleware.this) {
                        sockets.remove(idleSocketHolder);
                        AsyncSocketMiddleware.this.maybeCleanupConnectionInfo(lookup);
                    }
                }
            });
        }
    }

    private void idleSocket(final AsyncSocket socket) {
        socket.setEndCallback(new CompletedCallback() {
            public void onCompleted(Exception ex) {
                socket.setClosedCallback(null);
                socket.close();
            }
        });
        socket.setWriteableCallback(null);
        socket.setDataCallback(new NullDataCallback() {
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                super.onDataAvailable(emitter, bb);
                bb.recycle();
                socket.setClosedCallback(null);
                socket.close();
            }
        });
    }

    private void nextConnection(AsyncHttpRequest request) {
        Uri uri = request.getUri();
        String key = computeLookup(uri, getSchemePort(uri), request.getProxyHost(), request.getProxyPort());
        synchronized (this) {
            ConnectionInfo info = (ConnectionInfo) this.connectionInfo.get(key);
            if (info == null) {
                return;
            }
            info.openCount--;
            while (info.openCount < this.maxConnectionCount && info.queue.size() > 0) {
                GetSocketData gsd = (GetSocketData) info.queue.remove();
                SimpleCancellable socketCancellable = gsd.socketCancellable;
                if (!socketCancellable.isCancelled()) {
                    socketCancellable.setParent(getSocket(gsd));
                }
            }
            maybeCleanupConnectionInfo(key);
        }
    }

    public void onResponseComplete(OnResponseCompleteDataOnRequestSentData data) {
        if (data.state.get("socket-owner") == this) {
            try {
                idleSocket(data.socket);
                if (data.exception == null) {
                    if (data.socket.isOpen()) {
                        if (HttpUtil.isKeepAlive(data.response.protocol(), data.response.headers())) {
                            if (HttpUtil.isKeepAlive(Protocol.HTTP_1_1, data.request.getHeaders())) {
                                data.request.logd("Recycling keep-alive socket");
                                recycleSocket(data.socket, data.request);
                                nextConnection(data.request);
                                return;
                            }
                        }
                        data.request.logv("closing out socket (not keep alive)");
                        data.socket.setClosedCallback(null);
                        data.socket.close();
                        nextConnection(data.request);
                        return;
                    }
                }
                data.request.logv("closing out socket (exception)");
                data.socket.setClosedCallback(null);
                data.socket.close();
            } finally {
                nextConnection(data.request);
            }
        }
    }
}
