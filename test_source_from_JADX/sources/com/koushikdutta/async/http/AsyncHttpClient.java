package com.koushikdutta.async.http;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.badlogic.gdx.net.HttpStatus;
import com.koushikdutta.async.AsyncSSLException;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.CompletedCallback.NullCompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback.NullDataCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnResponseCompleteDataOnRequestSentData;
import com.koushikdutta.async.http.callback.HttpConnectCallback;
import com.koushikdutta.async.http.callback.RequestCallback;
import com.koushikdutta.async.http.spdy.SpdyMiddleware;
import com.koushikdutta.async.parser.AsyncParser;
import com.koushikdutta.async.parser.ByteBufferListParser;
import com.koushikdutta.async.parser.JSONArrayParser;
import com.koushikdutta.async.parser.JSONObjectParser;
import com.koushikdutta.async.parser.StringParser;
import com.koushikdutta.async.stream.OutputStreamDataCallback;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;

public class AsyncHttpClient {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String LOGTAG = "AsyncHttp";
    private static AsyncHttpClient mDefaultInstance;
    HttpTransportMiddleware httpTransportMiddleware;
    final List<AsyncHttpClientMiddleware> mMiddleware = new CopyOnWriteArrayList();
    AsyncServer mServer;
    AsyncSocketMiddleware socketMiddleware;
    SpdyMiddleware sslSocketMiddleware;

    public interface WebSocketConnectCallback {
        void onCompleted(Exception exception, WebSocket webSocket);
    }

    public static abstract class RequestCallbackBase<T> implements RequestCallback<T> {
        public void onProgress(AsyncHttpResponse response, long downloaded, long total) {
        }

        public void onConnect(AsyncHttpResponse response) {
        }
    }

    public static abstract class DownloadCallback extends RequestCallbackBase<ByteBufferList> {
    }

    public static abstract class FileCallback extends RequestCallbackBase<File> {
    }

    public static abstract class JSONArrayCallback extends RequestCallbackBase<JSONArray> {
    }

    public static abstract class JSONObjectCallback extends RequestCallbackBase<JSONObject> {
    }

    public static abstract class StringCallback extends RequestCallbackBase<String> {
    }

    private class FutureAsyncHttpResponse extends SimpleFuture<AsyncHttpResponse> {
        public Object scheduled;
        public AsyncSocket socket;
        public Runnable timeoutRunnable;

        private FutureAsyncHttpResponse() {
        }

        public boolean cancel() {
            if (!super.cancel()) {
                return false;
            }
            if (this.socket != null) {
                this.socket.setDataCallback(new NullDataCallback());
                this.socket.close();
            }
            if (this.scheduled != null) {
                AsyncHttpClient.this.mServer.removeAllCallbacks(this.scheduled);
            }
            return true;
        }
    }

    public static AsyncHttpClient getDefaultInstance() {
        if (mDefaultInstance == null) {
            mDefaultInstance = new AsyncHttpClient(AsyncServer.getDefault());
        }
        return mDefaultInstance;
    }

    public Collection<AsyncHttpClientMiddleware> getMiddleware() {
        return this.mMiddleware;
    }

    public void insertMiddleware(AsyncHttpClientMiddleware middleware) {
        this.mMiddleware.add(0, middleware);
    }

    public AsyncHttpClient(AsyncServer server) {
        this.mServer = server;
        AsyncHttpClientMiddleware asyncSocketMiddleware = new AsyncSocketMiddleware(this);
        this.socketMiddleware = asyncSocketMiddleware;
        insertMiddleware(asyncSocketMiddleware);
        asyncSocketMiddleware = new SpdyMiddleware(this);
        this.sslSocketMiddleware = asyncSocketMiddleware;
        insertMiddleware(asyncSocketMiddleware);
        asyncSocketMiddleware = new HttpTransportMiddleware();
        this.httpTransportMiddleware = asyncSocketMiddleware;
        insertMiddleware(asyncSocketMiddleware);
        this.sslSocketMiddleware.addEngineConfigurator(new SSLEngineSNIConfigurator());
    }

    @SuppressLint({"NewApi"})
    private static void setupAndroidProxy(AsyncHttpRequest request) {
        if (request.proxyHost == null) {
            try {
                List<Proxy> proxies = ProxySelector.getDefault().select(URI.create(request.getUri().toString()));
                if (!proxies.isEmpty()) {
                    Proxy proxy = (Proxy) proxies.get(0);
                    if (proxy.type() == Type.HTTP && (proxy.address() instanceof InetSocketAddress)) {
                        String proxyHost;
                        InetSocketAddress proxyAddress = (InetSocketAddress) proxy.address();
                        if (VERSION.SDK_INT >= 14) {
                            proxyHost = proxyAddress.getHostString();
                        } else {
                            InetAddress address = proxyAddress.getAddress();
                            if (address != null) {
                                proxyHost = address.getHostAddress();
                            } else {
                                proxyHost = proxyAddress.getHostName();
                            }
                        }
                        request.enableProxy(proxyHost, proxyAddress.getPort());
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public AsyncSocketMiddleware getSocketMiddleware() {
        return this.socketMiddleware;
    }

    public SpdyMiddleware getSSLSocketMiddleware() {
        return this.sslSocketMiddleware;
    }

    public Future<AsyncHttpResponse> execute(AsyncHttpRequest request, HttpConnectCallback callback) {
        FutureAsyncHttpResponse futureAsyncHttpResponse = new FutureAsyncHttpResponse();
        FutureAsyncHttpResponse ret = futureAsyncHttpResponse;
        execute(request, 0, futureAsyncHttpResponse, callback);
        return ret;
    }

    public Future<AsyncHttpResponse> execute(String uri, HttpConnectCallback callback) {
        return execute(new AsyncHttpGet(uri), callback);
    }

    private void reportConnectedCompleted(FutureAsyncHttpResponse cancel, Exception ex, AsyncHttpResponseImpl response, AsyncHttpRequest request, HttpConnectCallback callback) {
        boolean complete;
        this.mServer.removeAllCallbacks(cancel.scheduled);
        if (ex != null) {
            request.loge("Connection error", ex);
            complete = cancel.setComplete(ex);
        } else {
            request.logd("Connection successful");
            complete = cancel.setComplete((Object) response);
        }
        if (complete) {
            callback.onConnectCompleted(ex, response);
            return;
        }
        if (response != null) {
            response.setDataCallback(new NullDataCallback());
            response.close();
        }
    }

    private void execute(AsyncHttpRequest request, int redirectCount, FutureAsyncHttpResponse cancel, HttpConnectCallback callback) {
        if (this.mServer.isAffinityThread()) {
            executeAffinity(request, redirectCount, cancel, callback);
            return;
        }
        final AsyncHttpRequest asyncHttpRequest = request;
        final int i = redirectCount;
        final FutureAsyncHttpResponse futureAsyncHttpResponse = cancel;
        final HttpConnectCallback httpConnectCallback = callback;
        this.mServer.post(new Runnable() {
            public void run() {
                AsyncHttpClient.this.executeAffinity(asyncHttpRequest, i, futureAsyncHttpResponse, httpConnectCallback);
            }
        });
    }

    private static long getTimeoutRemaining(AsyncHttpRequest request) {
        return (long) request.getTimeout();
    }

    private static void copyHeader(AsyncHttpRequest from, AsyncHttpRequest to, String header) {
        String value = from.getHeaders().get(header);
        if (!TextUtils.isEmpty(value)) {
            to.getHeaders().set(header, value);
        }
    }

    private void executeAffinity(AsyncHttpRequest request, int redirectCount, FutureAsyncHttpResponse cancel, HttpConnectCallback callback) {
        if (redirectCount > 15) {
            reportConnectedCompleted(cancel, new RedirectLimitExceededException("too many redirects"), null, request, callback);
            return;
        }
        final FutureAsyncHttpResponse futureAsyncHttpResponse;
        Uri uri = request.getUri();
        OnResponseCompleteDataOnRequestSentData data = new OnResponseCompleteDataOnRequestSentData();
        request.executionTime = System.currentTimeMillis();
        data.request = request;
        request.logd("Executing request.");
        for (AsyncHttpClientMiddleware middleware : this.mMiddleware) {
            middleware.onRequest(data);
        }
        if (request.getTimeout() > 0) {
            final OnResponseCompleteDataOnRequestSentData onResponseCompleteDataOnRequestSentData = data;
            futureAsyncHttpResponse = cancel;
            final AsyncHttpRequest asyncHttpRequest = request;
            final HttpConnectCallback httpConnectCallback = callback;
            cancel.timeoutRunnable = new Runnable() {
                public void run() {
                    if (onResponseCompleteDataOnRequestSentData.socketCancellable != null) {
                        onResponseCompleteDataOnRequestSentData.socketCancellable.cancel();
                        if (onResponseCompleteDataOnRequestSentData.socket != null) {
                            onResponseCompleteDataOnRequestSentData.socket.close();
                        }
                    }
                    AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, new TimeoutException(), null, asyncHttpRequest, httpConnectCallback);
                }
            };
            cancel.scheduled = this.mServer.postDelayed(cancel.timeoutRunnable, getTimeoutRemaining(request));
        }
        final AsyncHttpRequest asyncHttpRequest2 = request;
        futureAsyncHttpResponse = cancel;
        final HttpConnectCallback httpConnectCallback2 = callback;
        final OnResponseCompleteDataOnRequestSentData onResponseCompleteDataOnRequestSentData2 = data;
        final int i = redirectCount;
        data.connectCallback = new ConnectCallback() {
            boolean reported;

            public void onConnectCompleted(Exception ex, AsyncSocket socket) {
                if (!this.reported || socket == null) {
                    this.reported = true;
                    asyncHttpRequest2.logv("socket connected");
                    if (futureAsyncHttpResponse.isCancelled()) {
                        if (socket != null) {
                            socket.close();
                        }
                        return;
                    }
                    if (futureAsyncHttpResponse.timeoutRunnable != null) {
                        AsyncHttpClient.this.mServer.removeAllCallbacks(futureAsyncHttpResponse.scheduled);
                    }
                    if (ex != null) {
                        AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, ex, null, asyncHttpRequest2, httpConnectCallback2);
                        return;
                    }
                    onResponseCompleteDataOnRequestSentData2.socket = socket;
                    futureAsyncHttpResponse.socket = socket;
                    AsyncHttpClient.this.executeSocket(asyncHttpRequest2, i, futureAsyncHttpResponse, httpConnectCallback2, onResponseCompleteDataOnRequestSentData2);
                    return;
                }
                socket.setDataCallback(new NullDataCallback());
                socket.setEndCallback(new NullCompletedCallback());
                socket.close();
                throw new AssertionError("double connect callback");
            }
        };
        setupAndroidProxy(request);
        if (request.getBody() != null && request.getHeaders().get("Content-Type") == null) {
            request.getHeaders().set("Content-Type", request.getBody().getContentType());
        }
        for (AsyncHttpClientMiddleware middleware2 : this.mMiddleware) {
            Cancellable socketCancellable = middleware2.getSocket(data);
            if (socketCancellable != null) {
                data.socketCancellable = socketCancellable;
                cancel.setParent(socketCancellable);
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid uri=");
        stringBuilder.append(request.getUri());
        stringBuilder.append(" middlewares=");
        stringBuilder.append(this.mMiddleware);
        reportConnectedCompleted(cancel, new IllegalArgumentException(stringBuilder.toString()), null, request, callback);
    }

    private void executeSocket(AsyncHttpRequest request, int redirectCount, FutureAsyncHttpResponse cancel, HttpConnectCallback callback, OnResponseCompleteDataOnRequestSentData data) {
        final FutureAsyncHttpResponse futureAsyncHttpResponse = cancel;
        final AsyncHttpRequest asyncHttpRequest = request;
        final HttpConnectCallback httpConnectCallback = callback;
        final OnResponseCompleteDataOnRequestSentData onResponseCompleteDataOnRequestSentData = data;
        final int i = redirectCount;
        final AsyncHttpResponseImpl ret = new AsyncHttpResponseImpl(request) {
            protected void onRequestCompleted(Exception ex) {
                if (ex != null) {
                    AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, ex, null, asyncHttpRequest, httpConnectCallback);
                    return;
                }
                asyncHttpRequest.logv("request completed");
                if (!futureAsyncHttpResponse.isCancelled()) {
                    if (futureAsyncHttpResponse.timeoutRunnable != null && this.mHeaders == null) {
                        AsyncHttpClient.this.mServer.removeAllCallbacks(futureAsyncHttpResponse.scheduled);
                        futureAsyncHttpResponse.scheduled = AsyncHttpClient.this.mServer.postDelayed(futureAsyncHttpResponse.timeoutRunnable, AsyncHttpClient.getTimeoutRemaining(asyncHttpRequest));
                    }
                    for (AsyncHttpClientMiddleware middleware : AsyncHttpClient.this.mMiddleware) {
                        middleware.onRequestSent(onResponseCompleteDataOnRequestSentData);
                    }
                }
            }

            public void setDataEmitter(DataEmitter emitter) {
                onResponseCompleteDataOnRequestSentData.bodyEmitter = emitter;
                for (AsyncHttpClientMiddleware middleware : AsyncHttpClient.this.mMiddleware) {
                    middleware.onBodyDecoder(onResponseCompleteDataOnRequestSentData);
                }
                super.setDataEmitter(onResponseCompleteDataOnRequestSentData.bodyEmitter);
                Headers headers = this.mHeaders;
                int responseCode = code();
                if ((responseCode == HttpStatus.SC_MOVED_PERMANENTLY || responseCode == HttpStatus.SC_MOVED_TEMPORARILY || responseCode == 307) && asyncHttpRequest.getFollowRedirect()) {
                    String location = headers.get("Location");
                    try {
                        Uri redirect = Uri.parse(location);
                        if (redirect.getScheme() == null) {
                            redirect = Uri.parse(new URL(new URL(asyncHttpRequest.getUri().toString()), location).toString());
                        }
                        AsyncHttpRequest newReq = new AsyncHttpRequest(redirect, asyncHttpRequest.getMethod().equals("HEAD") ? "HEAD" : "GET");
                        newReq.executionTime = asyncHttpRequest.executionTime;
                        newReq.logLevel = asyncHttpRequest.logLevel;
                        newReq.LOGTAG = asyncHttpRequest.LOGTAG;
                        newReq.proxyHost = asyncHttpRequest.proxyHost;
                        newReq.proxyPort = asyncHttpRequest.proxyPort;
                        AsyncHttpClient.setupAndroidProxy(newReq);
                        AsyncHttpClient.copyHeader(asyncHttpRequest, newReq, "User-Agent");
                        AsyncHttpClient.copyHeader(asyncHttpRequest, newReq, "Range");
                        asyncHttpRequest.logi("Redirecting");
                        newReq.logi("Redirected");
                        AsyncHttpClient.this.execute(newReq, i + 1, futureAsyncHttpResponse, httpConnectCallback);
                        setDataCallback(new NullDataCallback());
                        return;
                    } catch (Exception e) {
                        AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, e, this, asyncHttpRequest, httpConnectCallback);
                        return;
                    }
                }
                AsyncHttpRequest asyncHttpRequest = asyncHttpRequest;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Final (post cache response) headers:\n");
                stringBuilder.append(toString());
                asyncHttpRequest.logv(stringBuilder.toString());
                AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, null, this, asyncHttpRequest, httpConnectCallback);
            }

            protected void onHeadersReceived() {
                super.onHeadersReceived();
                if (!futureAsyncHttpResponse.isCancelled()) {
                    if (futureAsyncHttpResponse.timeoutRunnable != null) {
                        AsyncHttpClient.this.mServer.removeAllCallbacks(futureAsyncHttpResponse.scheduled);
                    }
                    AsyncHttpRequest asyncHttpRequest = asyncHttpRequest;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Received headers:\n");
                    stringBuilder.append(toString());
                    asyncHttpRequest.logv(stringBuilder.toString());
                    for (AsyncHttpClientMiddleware middleware : AsyncHttpClient.this.mMiddleware) {
                        middleware.onHeadersReceived(onResponseCompleteDataOnRequestSentData);
                    }
                }
            }

            protected void report(Exception ex) {
                if (ex != null) {
                    asyncHttpRequest.loge("exception during response", ex);
                }
                if (!futureAsyncHttpResponse.isCancelled()) {
                    if (ex instanceof AsyncSSLException) {
                        asyncHttpRequest.loge("SSL Exception", ex);
                        AsyncSSLException ase = (AsyncSSLException) ex;
                        asyncHttpRequest.onHandshakeException(ase);
                        if (ase.getIgnore()) {
                            return;
                        }
                    }
                    AsyncSocket socket = socket();
                    if (socket != null) {
                        super.report(ex);
                        if (!((socket.isOpen() && ex == null) || headers() != null || ex == null)) {
                            AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, ex, null, asyncHttpRequest, httpConnectCallback);
                        }
                        onResponseCompleteDataOnRequestSentData.exception = ex;
                        for (AsyncHttpClientMiddleware middleware : AsyncHttpClient.this.mMiddleware) {
                            middleware.onResponseComplete(onResponseCompleteDataOnRequestSentData);
                        }
                    }
                }
            }

            public AsyncSocket detachSocket() {
                asyncHttpRequest.logd("Detaching socket");
                AsyncSocket socket = socket();
                if (socket == null) {
                    return null;
                }
                socket.setWriteableCallback(null);
                socket.setClosedCallback(null);
                socket.setEndCallback(null);
                socket.setDataCallback(null);
                setSocket(null);
                return socket;
            }
        };
        data.sendHeadersCallback = new CompletedCallback() {
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    ret.report(ex);
                } else {
                    ret.onHeadersSent();
                }
            }
        };
        data.receiveHeadersCallback = new CompletedCallback() {
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    ret.report(ex);
                } else {
                    ret.onHeadersReceived();
                }
            }
        };
        data.response = ret;
        ret.setSocket(data.socket);
        for (AsyncHttpClientMiddleware middleware : this.mMiddleware) {
            if (middleware.exchangeHeaders(data)) {
                return;
            }
        }
    }

    public Future<ByteBufferList> executeByteBufferList(AsyncHttpRequest request, DownloadCallback callback) {
        return execute(request, new ByteBufferListParser(), callback);
    }

    public Future<String> executeString(AsyncHttpRequest req, StringCallback callback) {
        return execute(req, new StringParser(), callback);
    }

    public Future<JSONObject> executeJSONObject(AsyncHttpRequest req, JSONObjectCallback callback) {
        return execute(req, new JSONObjectParser(), callback);
    }

    public Future<JSONArray> executeJSONArray(AsyncHttpRequest req, JSONArrayCallback callback) {
        return execute(req, new JSONArrayParser(), callback);
    }

    private <T> void invokeWithAffinity(RequestCallback<T> callback, SimpleFuture<T> future, AsyncHttpResponse response, Exception e, T result) {
        boolean complete;
        if (e != null) {
            complete = future.setComplete(e);
        } else {
            complete = future.setComplete((Object) result);
        }
        if (complete && callback != null) {
            callback.onCompleted(e, response, result);
        }
    }

    private <T> void invoke(RequestCallback<T> callback, SimpleFuture<T> future, AsyncHttpResponse response, Exception e, T result) {
        final RequestCallback<T> requestCallback = callback;
        final SimpleFuture<T> simpleFuture = future;
        final AsyncHttpResponse asyncHttpResponse = response;
        final Exception exception = e;
        final T t = result;
        this.mServer.post(new Runnable() {
            public void run() {
                AsyncHttpClient.this.invokeWithAffinity(requestCallback, simpleFuture, asyncHttpResponse, exception, t);
            }
        });
    }

    private void invokeProgress(RequestCallback callback, AsyncHttpResponse response, long downloaded, long total) {
        if (callback != null) {
            callback.onProgress(response, downloaded, total);
        }
    }

    private void invokeConnect(RequestCallback callback, AsyncHttpResponse response) {
        if (callback != null) {
            callback.onConnect(response);
        }
    }

    public Future<File> executeFile(AsyncHttpRequest req, String filename, FileCallback callback) {
        final File file = new File(filename);
        file.getParentFile().mkdirs();
        try {
            final OutputStream fout = new BufferedOutputStream(new FileOutputStream(file), 8192);
            final Cancellable cancel = new FutureAsyncHttpResponse();
            SimpleFuture ret = new SimpleFuture<File>() {
                public void cancelCleanup() {
                    try {
                        ((AsyncHttpResponse) cancel.get()).setDataCallback(new NullDataCallback());
                        ((AsyncHttpResponse) cancel.get()).close();
                    } catch (Exception e) {
                    }
                    try {
                        fout.close();
                    } catch (Exception e2) {
                    }
                    file.delete();
                }
            };
            ret.setParent(cancel);
            final OutputStream outputStream = fout;
            final File file2 = file;
            final FileCallback fileCallback = callback;
            final SimpleFuture simpleFuture = ret;
            execute(req, 0, cancel, new HttpConnectCallback() {
                long mDownloaded = null;

                public void onConnectCompleted(Exception ex, final AsyncHttpResponse response) {
                    if (ex != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                        }
                        file2.delete();
                        AsyncHttpClient.this.invoke(fileCallback, simpleFuture, response, ex, null);
                        return;
                    }
                    AsyncHttpClient.this.invokeConnect(fileCallback, response);
                    final AsyncHttpResponse asyncHttpResponse = response;
                    final long contentLength = (long) HttpUtil.contentLength(response.headers());
                    response.setDataCallback(new OutputStreamDataCallback(outputStream) {
                        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                            C11189 c11189 = C11189.this;
                            c11189.mDownloaded += (long) bb.remaining();
                            super.onDataAvailable(emitter, bb);
                            AsyncHttpClient.this.invokeProgress(fileCallback, asyncHttpResponse, C11189.this.mDownloaded, contentLength);
                        }
                    });
                    response.setEndCallback(new CompletedCallback() {
                        public void onCompleted(Exception ex) {
                            try {
                                outputStream.close();
                            } catch (Exception e) {
                                ex = e;
                            }
                            if (ex != null) {
                                file2.delete();
                                AsyncHttpClient.this.invoke(fileCallback, simpleFuture, response, ex, null);
                                return;
                            }
                            AsyncHttpClient.this.invoke(fileCallback, simpleFuture, response, null, file2);
                        }
                    });
                }
            });
            return ret;
        } catch (Exception e) {
            SimpleFuture<File> ret2 = new SimpleFuture();
            ret2.setComplete(e);
            return ret2;
        }
    }

    public <T> SimpleFuture<T> execute(AsyncHttpRequest req, final AsyncParser<T> parser, final RequestCallback<T> callback) {
        Cancellable cancel = new FutureAsyncHttpResponse();
        final SimpleFuture<T> ret = new SimpleFuture();
        execute(req, 0, cancel, new HttpConnectCallback() {
            public void onConnectCompleted(Exception ex, final AsyncHttpResponse response) {
                if (ex != null) {
                    AsyncHttpClient.this.invoke(callback, ret, response, ex, null);
                    return;
                }
                AsyncHttpClient.this.invokeConnect(callback, response);
                ret.setParent(parser.parse(response).setCallback(new FutureCallback<T>() {
                    public void onCompleted(Exception e, T result) {
                        AsyncHttpClient.this.invoke(callback, ret, response, e, result);
                    }
                }));
            }
        });
        ret.setParent(cancel);
        return ret;
    }

    public Future<WebSocket> websocket(final AsyncHttpRequest req, String protocol, final WebSocketConnectCallback callback) {
        WebSocketImpl.addWebSocketUpgradeHeaders(req, protocol);
        final SimpleFuture<WebSocket> ret = new SimpleFuture();
        ret.setParent(execute(req, new HttpConnectCallback() {
            public void onConnectCompleted(Exception ex, AsyncHttpResponse response) {
                if (ex != null) {
                    if (ret.setComplete(ex) && callback != null) {
                        callback.onCompleted(ex, null);
                    }
                    return;
                }
                Object ws = WebSocketImpl.finishHandshake(req.getHeaders(), response);
                if (ws == null) {
                    ex = new WebSocketHandshakeException("Unable to complete websocket handshake");
                    if (!ret.setComplete(ex)) {
                        return;
                    }
                } else if (!ret.setComplete(ws)) {
                    return;
                }
                if (callback != null) {
                    callback.onCompleted(ex, ws);
                }
            }
        }));
        return ret;
    }

    public Future<WebSocket> websocket(String uri, String protocol, WebSocketConnectCallback callback) {
        return websocket(new AsyncHttpGet(uri.replace("ws://", "http://").replace("wss://", "https://")), protocol, callback);
    }

    public AsyncServer getServer() {
        return this.mServer;
    }
}
