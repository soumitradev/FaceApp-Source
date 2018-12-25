package com.koushikdutta.async.http.socketio;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.transport.SocketIOTransport;
import org.json.JSONArray;
import org.json.JSONObject;

@Deprecated
public class SocketIOClient extends EventEmitter {
    ConnectCallback connectCallback;
    boolean connected;
    SocketIOConnection connection;
    DisconnectCallback disconnectCallback;
    boolean disconnected;
    String endpoint;
    ErrorCallback errorCallback;
    ExceptionCallback exceptionCallback;
    JSONCallback jsonCallback;
    ReconnectCallback reconnectCallback;
    StringCallback stringCallback;

    private void emitRaw(int type, String message, Acknowledge acknowledge) {
        this.connection.emitRaw(type, this, message, acknowledge);
    }

    public void emit(String name, JSONArray args) {
        emit(name, args, null);
    }

    public void emit(String message) {
        emit(message, (Acknowledge) null);
    }

    public void emit(JSONObject jsonMessage) {
        emit(jsonMessage, null);
    }

    public void emit(String name, JSONArray args, Acknowledge acknowledge) {
        JSONObject event = new JSONObject();
        try {
            event.put("name", name);
            event.put("args", args);
            emitRaw(5, event.toString(), acknowledge);
        } catch (Exception e) {
        }
    }

    public void emit(String message, Acknowledge acknowledge) {
        emitRaw(3, message, acknowledge);
    }

    public void emit(JSONObject jsonMessage, Acknowledge acknowledge) {
        emitRaw(4, jsonMessage.toString(), acknowledge);
    }

    public void emitEvent(String name) {
        emitEvent(name, null);
    }

    public void emitEvent(String name, Acknowledge acknowledge) {
        JSONObject event = new JSONObject();
        try {
            event.put("name", name);
            emitRaw(5, event.toString(), acknowledge);
        } catch (Exception e) {
        }
    }

    public static Future<SocketIOClient> connect(AsyncHttpClient client, String uri, ConnectCallback callback) {
        return connect(client, new SocketIORequest(uri), callback);
    }

    public static Future<SocketIOClient> connect(AsyncHttpClient client, SocketIORequest request, ConnectCallback callback) {
        SimpleFuture<SocketIOClient> ret = new SimpleFuture();
        SocketIOConnection connection = new SocketIOConnection(client, request);
        connection.clients.add(new SocketIOClient(connection, "", new SocketIOClient$1(request, callback, ret, connection)));
        connection.reconnect(ret);
        return ret;
    }

    public void setExceptionCallback(ExceptionCallback exceptionCallback) {
        this.exceptionCallback = exceptionCallback;
    }

    public ExceptionCallback getExceptionCallback() {
        return this.exceptionCallback;
    }

    public ErrorCallback getErrorCallback() {
        return this.errorCallback;
    }

    public void setErrorCallback(ErrorCallback callback) {
        this.errorCallback = callback;
    }

    public DisconnectCallback getDisconnectCallback() {
        return this.disconnectCallback;
    }

    public void setDisconnectCallback(DisconnectCallback callback) {
        this.disconnectCallback = callback;
    }

    public ReconnectCallback getReconnectCallback() {
        return this.reconnectCallback;
    }

    public void setReconnectCallback(ReconnectCallback callback) {
        this.reconnectCallback = callback;
    }

    public JSONCallback getJSONCallback() {
        return this.jsonCallback;
    }

    public void setJSONCallback(JSONCallback callback) {
        this.jsonCallback = callback;
    }

    public StringCallback getStringCallback() {
        return this.stringCallback;
    }

    public void setStringCallback(StringCallback callback) {
        this.stringCallback = callback;
    }

    private SocketIOClient(SocketIOConnection connection, String endpoint, ConnectCallback callback) {
        this.endpoint = endpoint;
        this.connection = connection;
        this.connectCallback = callback;
    }

    public boolean isConnected() {
        return this.connected && !this.disconnected && this.connection.isConnected();
    }

    public void disconnect() {
        this.connection.disconnect(this);
        DisconnectCallback disconnectCallback = this.disconnectCallback;
        if (disconnectCallback != null) {
            disconnectCallback.onDisconnect(null);
        }
    }

    public void of(String endpoint, ConnectCallback connectCallback) {
        this.connection.connect(new SocketIOClient(this.connection, endpoint, connectCallback));
    }

    public void reconnect() {
        this.connection.reconnect(null);
    }

    public SocketIOTransport getTransport() {
        return this.connection.transport;
    }
}
