package com.koushikdutta.async.http.socketio;

import android.text.TextUtils;
import com.koushikdutta.async.future.SimpleFuture;

class SocketIOClient$1 implements ConnectCallback {
    final /* synthetic */ ConnectCallback val$callback;
    final /* synthetic */ SocketIOConnection val$connection;
    final /* synthetic */ SocketIORequest val$request;
    final /* synthetic */ SimpleFuture val$ret;

    /* renamed from: com.koushikdutta.async.http.socketio.SocketIOClient$1$1 */
    class C11801 implements ConnectCallback {
        C11801() {
        }

        public void onConnectCompleted(Exception ex, SocketIOClient client) {
            if (SocketIOClient$1.this.val$callback != null) {
                SocketIOClient$1.this.val$callback.onConnectCompleted(ex, client);
            }
            SocketIOClient$1.this.val$ret.setComplete(ex, client);
        }
    }

    SocketIOClient$1(SocketIORequest socketIORequest, ConnectCallback connectCallback, SimpleFuture simpleFuture, SocketIOConnection socketIOConnection) {
        this.val$request = socketIORequest;
        this.val$callback = connectCallback;
        this.val$ret = simpleFuture;
        this.val$connection = socketIOConnection;
    }

    public void onConnectCompleted(Exception ex, SocketIOClient client) {
        if (ex == null) {
            if (!TextUtils.isEmpty(this.val$request.getEndpoint())) {
                this.val$connection.clients.remove(client);
                client.of(this.val$request.getEndpoint(), new C11801());
                return;
            }
        }
        if (this.val$callback != null) {
            this.val$callback.onConnectCompleted(ex, client);
        }
        this.val$ret.setComplete(ex, client);
    }
}
