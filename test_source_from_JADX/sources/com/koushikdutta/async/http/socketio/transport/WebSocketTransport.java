package com.koushikdutta.async.http.socketio.transport;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback.NullDataCallback;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.socketio.transport.SocketIOTransport.StringCallback;

public class WebSocketTransport implements SocketIOTransport {
    private String sessionId;
    private StringCallback stringCallback;
    private WebSocket webSocket;

    public WebSocketTransport(WebSocket webSocket, String sessionId) {
        this.webSocket = webSocket;
        this.sessionId = sessionId;
        this.webSocket.setDataCallback(new NullDataCallback());
    }

    public boolean isConnected() {
        return this.webSocket.isOpen();
    }

    public void setClosedCallback(CompletedCallback handler) {
        this.webSocket.setClosedCallback(handler);
    }

    public void disconnect() {
        this.webSocket.close();
    }

    public AsyncServer getServer() {
        return this.webSocket.getServer();
    }

    public void send(String message) {
        this.webSocket.send(message);
    }

    public void setStringCallback(final StringCallback callback) {
        if (this.stringCallback != callback) {
            if (callback == null) {
                this.webSocket.setStringCallback(null);
            } else {
                this.webSocket.setStringCallback(new WebSocket.StringCallback() {
                    public void onStringAvailable(String s) {
                        callback.onStringAvailable(s);
                    }
                });
            }
            this.stringCallback = callback;
        }
    }

    public boolean heartbeats() {
        return true;
    }

    public String getSessionId() {
        return this.sessionId;
    }
}
