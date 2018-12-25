package com.koushikdutta.async.http;

import android.text.TextUtils;
import android.util.Base64;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.http.WebSocket.PingCallback;
import com.koushikdutta.async.http.WebSocket.PongCallback;
import com.koushikdutta.async.http.WebSocket.StringCallback;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.UUID;

public class WebSocketImpl implements WebSocket {
    static final String MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private DataCallback mDataCallback;
    CompletedCallback mExceptionCallback;
    HybiParser mParser;
    private PingCallback mPingCallback;
    private PongCallback mPongCallback;
    BufferedDataSink mSink;
    private AsyncSocket mSocket;
    private StringCallback mStringCallback;
    private LinkedList<ByteBufferList> pending;

    public void end() {
        this.mSocket.end();
    }

    private static byte[] toByteArray(UUID uuid) {
        byte[] byteArray = new byte[16];
        ByteBuffer.wrap(byteArray).asLongBuffer().put(new long[]{uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()});
        return byteArray;
    }

    private static String SHA1(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            return Base64.encodeToString(md.digest(), 2);
        } catch (Exception e) {
            return null;
        }
    }

    private void addAndEmit(ByteBufferList bb) {
        if (this.pending == null) {
            Util.emitAllData(this, bb);
            if (bb.remaining() > 0) {
                this.pending = new LinkedList();
                this.pending.add(bb);
            }
            return;
        }
        while (!isPaused()) {
            bb = (ByteBufferList) this.pending.remove();
            Util.emitAllData(this, bb);
            if (bb.remaining() > 0) {
                this.pending.add(0, bb);
            }
        }
        if (this.pending.size() == 0) {
            this.pending = null;
        }
    }

    private void setupParser(boolean masking, boolean deflate) {
        this.mParser = new HybiParser(this.mSocket) {
            protected void report(Exception ex) {
                if (WebSocketImpl.this.mExceptionCallback != null) {
                    WebSocketImpl.this.mExceptionCallback.onCompleted(ex);
                }
            }

            protected void onMessage(byte[] payload) {
                WebSocketImpl.this.addAndEmit(new ByteBufferList(payload));
            }

            protected void onMessage(String payload) {
                if (WebSocketImpl.this.mStringCallback != null) {
                    WebSocketImpl.this.mStringCallback.onStringAvailable(payload);
                }
            }

            protected void onDisconnect(int code, String reason) {
                WebSocketImpl.this.mSocket.close();
            }

            protected void sendFrame(byte[] frame) {
                WebSocketImpl.this.mSink.write(new ByteBufferList(frame));
            }

            protected void onPing(String payload) {
                if (WebSocketImpl.this.mPingCallback != null) {
                    WebSocketImpl.this.mPingCallback.onPingReceived(payload);
                }
            }

            protected void onPong(String payload) {
                if (WebSocketImpl.this.mPongCallback != null) {
                    WebSocketImpl.this.mPongCallback.onPongReceived(payload);
                }
            }
        };
        this.mParser.setMasking(masking);
        this.mParser.setDeflate(deflate);
        if (this.mSocket.isPaused()) {
            this.mSocket.resume();
        }
    }

    public WebSocketImpl(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        this(request.getSocket());
        String key = request.getHeaders().get("Sec-WebSocket-Key");
        String concat = new StringBuilder();
        concat.append(key);
        concat.append(MAGIC);
        String sha1 = SHA1(concat.toString());
        String origin = request.getHeaders().get("Origin");
        response.code(101);
        response.getHeaders().set("Upgrade", "WebSocket");
        response.getHeaders().set("Connection", "Upgrade");
        response.getHeaders().set("Sec-WebSocket-Accept", sha1);
        String protocol = request.getHeaders().get("Sec-WebSocket-Protocol");
        if (!TextUtils.isEmpty(protocol)) {
            response.getHeaders().set("Sec-WebSocket-Protocol", protocol);
        }
        response.writeHead();
        setupParser(false, false);
    }

    public static void addWebSocketUpgradeHeaders(AsyncHttpRequest req, String protocol) {
        Headers headers = req.getHeaders();
        String key = Base64.encodeToString(toByteArray(UUID.randomUUID()), 2);
        headers.set("Sec-WebSocket-Version", "13");
        headers.set("Sec-WebSocket-Key", key);
        headers.set("Sec-WebSocket-Extensions", "x-webkit-deflate-frame");
        headers.set("Connection", "Upgrade");
        headers.set("Upgrade", "websocket");
        if (protocol != null) {
            headers.set("Sec-WebSocket-Protocol", protocol);
        }
        headers.set("Pragma", "no-cache");
        headers.set("Cache-Control", "no-cache");
        if (TextUtils.isEmpty(req.getHeaders().get("User-Agent"))) {
            req.getHeaders().set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.15 Safari/537.36");
        }
    }

    public WebSocketImpl(AsyncSocket socket) {
        this.mSocket = socket;
        this.mSink = new BufferedDataSink(this.mSocket);
    }

    public static WebSocket finishHandshake(Headers requestHeaders, AsyncHttpResponse response) {
        if (response == null || response.code() != 101 || !"websocket".equalsIgnoreCase(response.headers().get("Upgrade"))) {
            return null;
        }
        String sha1 = response.headers().get("Sec-WebSocket-Accept");
        if (sha1 == null) {
            return null;
        }
        String key = requestHeaders.get("Sec-WebSocket-Key");
        if (key == null) {
            return null;
        }
        String concat = new StringBuilder();
        concat.append(key);
        concat.append(MAGIC);
        if (!sha1.equalsIgnoreCase(SHA1(concat.toString()).trim())) {
            return null;
        }
        String extensions = requestHeaders.get("Sec-WebSocket-Extensions");
        boolean deflate = false;
        if (extensions != null && extensions.equals("x-webkit-deflate-frame")) {
            deflate = true;
        }
        WebSocketImpl ret = new WebSocketImpl(response.detachSocket());
        ret.setupParser(true, deflate);
        return ret;
    }

    public void close() {
        this.mSocket.close();
    }

    public void setClosedCallback(CompletedCallback handler) {
        this.mSocket.setClosedCallback(handler);
    }

    public CompletedCallback getClosedCallback() {
        return this.mSocket.getClosedCallback();
    }

    public void setEndCallback(CompletedCallback callback) {
        this.mExceptionCallback = callback;
    }

    public CompletedCallback getEndCallback() {
        return this.mExceptionCallback;
    }

    public void send(byte[] bytes) {
        this.mSink.write(new ByteBufferList(this.mParser.frame(bytes)));
    }

    public void send(byte[] bytes, int offset, int len) {
        this.mSink.write(new ByteBufferList(this.mParser.frame(bytes, offset, len)));
    }

    public void send(String string) {
        this.mSink.write(new ByteBufferList(this.mParser.frame(string)));
    }

    public void ping(String string) {
        this.mSink.write(new ByteBufferList(ByteBuffer.wrap(this.mParser.pingFrame(string))));
    }

    public void pong(String string) {
        this.mSink.write(new ByteBufferList(ByteBuffer.wrap(this.mParser.pongFrame(string))));
    }

    public void setStringCallback(StringCallback callback) {
        this.mStringCallback = callback;
    }

    public void setDataCallback(DataCallback callback) {
        this.mDataCallback = callback;
    }

    public StringCallback getStringCallback() {
        return this.mStringCallback;
    }

    public void setPingCallback(PingCallback callback) {
        this.mPingCallback = callback;
    }

    public void setPongCallback(PongCallback callback) {
        this.mPongCallback = callback;
    }

    public PongCallback getPongCallback() {
        return this.mPongCallback;
    }

    public DataCallback getDataCallback() {
        return this.mDataCallback;
    }

    public boolean isOpen() {
        return this.mSocket.isOpen();
    }

    public boolean isBuffering() {
        return this.mSink.remaining() > 0;
    }

    public void write(ByteBufferList bb) {
        send(bb.getAllByteArray());
    }

    public void setWriteableCallback(WritableCallback handler) {
        this.mSink.setWriteableCallback(handler);
    }

    public WritableCallback getWriteableCallback() {
        return this.mSink.getWriteableCallback();
    }

    public AsyncSocket getSocket() {
        return this.mSocket;
    }

    public AsyncServer getServer() {
        return this.mSocket.getServer();
    }

    public boolean isChunked() {
        return false;
    }

    public void pause() {
        this.mSocket.pause();
    }

    public void resume() {
        this.mSocket.resume();
    }

    public boolean isPaused() {
        return this.mSocket.isPaused();
    }

    public String charset() {
        return null;
    }
}
