package com.koushikdutta.async.http.server;

import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.LineEmitter;
import com.koushikdutta.async.LineEmitter.StringCallback;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.CompletedCallback.NullCompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.HttpUtil;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import java.util.regex.Matcher;

public abstract class AsyncHttpServerRequestImpl extends FilteredDataEmitter implements AsyncHttpServerRequest, CompletedCallback {
    AsyncHttpRequestBody mBody;
    StringCallback mHeaderCallback = new C11742();
    Matcher mMatcher;
    private Headers mRawHeaders = new Headers();
    private CompletedCallback mReporter = new C11731();
    AsyncSocket mSocket;
    String method;
    private String statusLine;

    /* renamed from: com.koushikdutta.async.http.server.AsyncHttpServerRequestImpl$1 */
    class C11731 implements CompletedCallback {
        C11731() {
        }

        public void onCompleted(Exception error) {
            AsyncHttpServerRequestImpl.this.onCompleted(error);
        }
    }

    /* renamed from: com.koushikdutta.async.http.server.AsyncHttpServerRequestImpl$2 */
    class C11742 implements StringCallback {
        C11742() {
        }

        public void onStringAvailable(String s) {
            try {
                if (AsyncHttpServerRequestImpl.this.statusLine == null) {
                    AsyncHttpServerRequestImpl.this.statusLine = s;
                    if (!AsyncHttpServerRequestImpl.this.statusLine.contains("HTTP/")) {
                        AsyncHttpServerRequestImpl.this.onNotHttp();
                        AsyncHttpServerRequestImpl.this.mSocket.setDataCallback(null);
                    }
                } else if ("\r".equals(s)) {
                    DataEmitter emitter = HttpUtil.getBodyDecoder(AsyncHttpServerRequestImpl.this.mSocket, Protocol.HTTP_1_1, AsyncHttpServerRequestImpl.this.mRawHeaders, true);
                    AsyncHttpServerRequestImpl.this.mBody = HttpUtil.getBody(emitter, AsyncHttpServerRequestImpl.this.mReporter, AsyncHttpServerRequestImpl.this.mRawHeaders);
                    if (AsyncHttpServerRequestImpl.this.mBody == null) {
                        AsyncHttpServerRequestImpl.this.mBody = AsyncHttpServerRequestImpl.this.onUnknownBody(AsyncHttpServerRequestImpl.this.mRawHeaders);
                        if (AsyncHttpServerRequestImpl.this.mBody == null) {
                            AsyncHttpServerRequestImpl.this.mBody = new UnknownRequestBody(AsyncHttpServerRequestImpl.this.mRawHeaders.get("Content-Type"));
                        }
                    }
                    AsyncHttpServerRequestImpl.this.mBody.parse(emitter, AsyncHttpServerRequestImpl.this.mReporter);
                    AsyncHttpServerRequestImpl.this.onHeadersReceived();
                } else {
                    AsyncHttpServerRequestImpl.this.mRawHeaders.addLine(s);
                }
            } catch (Exception ex) {
                AsyncHttpServerRequestImpl.this.onCompleted(ex);
            }
        }
    }

    protected abstract void onHeadersReceived();

    public String getStatusLine() {
        return this.statusLine;
    }

    public void onCompleted(Exception e) {
        report(e);
    }

    protected void onNotHttp() {
        System.out.println("not http!");
    }

    protected AsyncHttpRequestBody onUnknownBody(Headers headers) {
        return null;
    }

    public String getMethod() {
        return this.method;
    }

    void setSocket(AsyncSocket socket) {
        this.mSocket = socket;
        LineEmitter liner = new LineEmitter();
        this.mSocket.setDataCallback(liner);
        liner.setLineCallback(this.mHeaderCallback);
        this.mSocket.setEndCallback(new NullCompletedCallback());
    }

    public AsyncSocket getSocket() {
        return this.mSocket;
    }

    public Headers getHeaders() {
        return this.mRawHeaders;
    }

    public void setDataCallback(DataCallback callback) {
        this.mSocket.setDataCallback(callback);
    }

    public DataCallback getDataCallback() {
        return this.mSocket.getDataCallback();
    }

    public boolean isChunked() {
        return this.mSocket.isChunked();
    }

    public Matcher getMatcher() {
        return this.mMatcher;
    }

    public AsyncHttpRequestBody getBody() {
        return this.mBody;
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

    public String toString() {
        if (this.mRawHeaders == null) {
            return super.toString();
        }
        return this.mRawHeaders.toPrefixString(this.statusLine);
    }
}
