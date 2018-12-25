package com.koushikdutta.async.http;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback.NullDataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.nio.charset.Charset;
import name.antonsmirnov.firmata.FormatHelper;

abstract class AsyncHttpResponseImpl extends FilteredDataEmitter implements AsyncSocket, AsyncHttpResponse, ResponseHead {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    int code;
    boolean mCompleted = false;
    private boolean mFirstWrite = true;
    protected Headers mHeaders;
    private CompletedCallback mReporter = new C11212();
    private AsyncHttpRequest mRequest;
    DataSink mSink;
    private AsyncSocket mSocket;
    String message;
    String protocol;

    /* renamed from: com.koushikdutta.async.http.AsyncHttpResponseImpl$1 */
    class C11201 implements CompletedCallback {
        C11201() {
        }

        public void onCompleted(Exception ex) {
            AsyncHttpResponseImpl.this.onRequestCompleted(ex);
        }
    }

    /* renamed from: com.koushikdutta.async.http.AsyncHttpResponseImpl$2 */
    class C11212 implements CompletedCallback {
        C11212() {
        }

        public void onCompleted(Exception error) {
            if (error == null || AsyncHttpResponseImpl.this.mCompleted) {
                AsyncHttpResponseImpl.this.report(error);
            } else {
                AsyncHttpResponseImpl.this.report(new ConnectionClosedException("connection closed before response completed.", error));
            }
        }
    }

    /* renamed from: com.koushikdutta.async.http.AsyncHttpResponseImpl$3 */
    class C13273 extends NullDataCallback {
        C13273() {
        }

        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            super.onDataAvailable(emitter, bb);
            AsyncHttpResponseImpl.this.mSocket.close();
        }
    }

    public AsyncSocket socket() {
        return this.mSocket;
    }

    public AsyncHttpRequest getRequest() {
        return this.mRequest;
    }

    void setSocket(AsyncSocket exchange) {
        this.mSocket = exchange;
        if (this.mSocket != null) {
            this.mSocket.setEndCallback(this.mReporter);
        }
    }

    protected void onHeadersSent() {
        AsyncHttpRequestBody requestBody = this.mRequest.getBody();
        if (requestBody != null) {
            requestBody.write(this.mRequest, this, new C11201());
        } else {
            onRequestCompleted(null);
        }
    }

    protected void onRequestCompleted(Exception ex) {
    }

    protected void onHeadersReceived() {
    }

    public DataEmitter emitter() {
        return getDataEmitter();
    }

    public ResponseHead emitter(DataEmitter emitter) {
        setDataEmitter(emitter);
        return this;
    }

    private void terminate() {
        this.mSocket.setDataCallback(new C13273());
    }

    protected void report(Exception e) {
        super.report(e);
        terminate();
        this.mSocket.setWriteableCallback(null);
        this.mSocket.setClosedCallback(null);
        this.mSocket.setEndCallback(null);
        this.mCompleted = true;
    }

    public void close() {
        super.close();
        terminate();
    }

    public AsyncHttpResponseImpl(AsyncHttpRequest request) {
        this.mRequest = request;
    }

    public Headers headers() {
        return this.mHeaders;
    }

    public ResponseHead headers(Headers headers) {
        this.mHeaders = headers;
        return this;
    }

    public int code() {
        return this.code;
    }

    public ResponseHead code(int code) {
        this.code = code;
        return this;
    }

    public ResponseHead protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public ResponseHead message(String message) {
        this.message = message;
        return this;
    }

    public String protocol() {
        return this.protocol;
    }

    public String message() {
        return this.message;
    }

    public String toString() {
        if (this.mHeaders == null) {
            return super.toString();
        }
        Headers headers = this.mHeaders;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.protocol);
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(this.code);
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(this.message);
        return headers.toPrefixString(stringBuilder.toString());
    }

    private void assertContent() {
        if (this.mFirstWrite) {
            this.mFirstWrite = false;
        }
    }

    public DataSink sink() {
        return this.mSink;
    }

    public ResponseHead sink(DataSink sink) {
        this.mSink = sink;
        return this;
    }

    public void write(ByteBufferList bb) {
        assertContent();
        this.mSink.write(bb);
    }

    public void end() {
        throw new AssertionError("end called?");
    }

    public void setWriteableCallback(WritableCallback handler) {
        this.mSink.setWriteableCallback(handler);
    }

    public WritableCallback getWriteableCallback() {
        return this.mSink.getWriteableCallback();
    }

    public boolean isOpen() {
        return this.mSink.isOpen();
    }

    public void setClosedCallback(CompletedCallback handler) {
        this.mSink.setClosedCallback(handler);
    }

    public CompletedCallback getClosedCallback() {
        return this.mSink.getClosedCallback();
    }

    public AsyncServer getServer() {
        return this.mSocket.getServer();
    }

    public String charset() {
        Multimap mm = Multimap.parseSemicolonDelimited(headers().get("Content-Type"));
        if (mm != null) {
            String string = mm.getString(HttpRequest.PARAM_CHARSET);
            String cs = string;
            if (string != null && Charset.isSupported(cs)) {
                return cs;
            }
        }
        return null;
    }
}
