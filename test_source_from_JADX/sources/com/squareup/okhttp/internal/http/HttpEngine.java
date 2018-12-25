package com.squareup.okhttp.internal.http;

import com.badlogic.gdx.net.HttpStatus;
import com.facebook.appevents.AppEventsConstants;
import com.squareup.okhttp.Address;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.Version;
import com.squareup.okhttp.internal.http.CacheStrategy.Factory;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.CookieHandler;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY = new C20351();
    public static final int MAX_FOLLOW_UPS = 20;
    private Address address;
    public final boolean bufferRequestBody;
    private BufferedSink bufferedRequestBody;
    private Response cacheResponse;
    private CacheStrategy cacheStrategy;
    private final boolean callerWritesRequestBody;
    final OkHttpClient client;
    private Connection connection;
    private final boolean forWebSocket;
    private Request networkRequest;
    private final Response priorResponse;
    private Sink requestBodyOut;
    private Route route;
    private RouteSelector routeSelector;
    long sentRequestMillis = -1;
    private CacheRequest storeRequest;
    private boolean transparentGzip;
    private Transport transport;
    private final Request userRequest;
    private Response userResponse;

    /* renamed from: com.squareup.okhttp.internal.http.HttpEngine$1 */
    static class C20351 extends ResponseBody {
        C20351() {
        }

        public MediaType contentType() {
            return null;
        }

        public long contentLength() {
            return 0;
        }

        public BufferedSource source() {
            return new Buffer();
        }
    }

    class NetworkInterceptorChain implements Chain {
        private int calls;
        private final int index;
        private final Request request;

        NetworkInterceptorChain(int index, Request request) {
            this.index = index;
            this.request = request;
        }

        public Connection connection() {
            return HttpEngine.this.connection;
        }

        public Request request() {
            return this.request;
        }

        public Response proceed(Request request) throws IOException {
            this.calls++;
            if (this.index > 0) {
                StringBuilder stringBuilder;
                Interceptor caller = (Interceptor) HttpEngine.this.client.networkInterceptors().get(this.index - 1);
                Address address = connection().getRoute().getAddress();
                if (request.url().getHost().equals(address.getUriHost())) {
                    if (Util.getEffectivePort(request.url()) == address.getUriPort()) {
                        if (this.calls > 1) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("network interceptor ");
                            stringBuilder.append(caller);
                            stringBuilder.append(" must call proceed() exactly once");
                            throw new IllegalStateException(stringBuilder.toString());
                        }
                    }
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("network interceptor ");
                stringBuilder.append(caller);
                stringBuilder.append(" must retain the same host and port");
                throw new IllegalStateException(stringBuilder.toString());
            }
            if (this.index < HttpEngine.this.client.networkInterceptors().size()) {
                NetworkInterceptorChain chain = new NetworkInterceptorChain(this.index + 1, request);
                Interceptor interceptor = (Interceptor) HttpEngine.this.client.networkInterceptors().get(this.index);
                Response interceptedResponse = interceptor.intercept(chain);
                if (chain.calls == 1) {
                    return interceptedResponse;
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("network interceptor ");
                stringBuilder2.append(interceptor);
                stringBuilder2.append(" must call proceed() exactly once");
                throw new IllegalStateException(stringBuilder2.toString());
            }
            HttpEngine.this.transport.writeRequestHeaders(request);
            if (HttpEngine.this.permitsRequestBody() && request.body() != null) {
                BufferedSink bufferedRequestBody = Okio.buffer(HttpEngine.this.transport.createRequestBody(request, request.body().contentLength()));
                request.body().writeTo(bufferedRequestBody);
                bufferedRequestBody.close();
            }
            return HttpEngine.this.readNetworkResponse();
        }
    }

    public HttpEngine(OkHttpClient client, Request request, boolean bufferRequestBody, boolean callerWritesRequestBody, boolean forWebSocket, Connection connection, RouteSelector routeSelector, RetryableSink requestBodyOut, Response priorResponse) {
        this.client = client;
        this.userRequest = request;
        this.bufferRequestBody = bufferRequestBody;
        this.callerWritesRequestBody = callerWritesRequestBody;
        this.forWebSocket = forWebSocket;
        this.connection = connection;
        this.routeSelector = routeSelector;
        this.requestBodyOut = requestBodyOut;
        this.priorResponse = priorResponse;
        if (connection != null) {
            Internal.instance.setOwner(connection, this);
            this.route = connection.getRoute();
            return;
        }
        this.route = null;
    }

    public void sendRequest() throws IOException {
        if (this.cacheStrategy == null) {
            if (this.transport != null) {
                throw new IllegalStateException();
            }
            Request request = networkRequest(this.userRequest);
            InternalCache responseCache = Internal.instance.internalCache(this.client);
            Response cacheCandidate = responseCache != null ? responseCache.get(request) : null;
            this.cacheStrategy = new Factory(System.currentTimeMillis(), request, cacheCandidate).get();
            this.networkRequest = this.cacheStrategy.networkRequest;
            this.cacheResponse = this.cacheStrategy.cacheResponse;
            if (responseCache != null) {
                responseCache.trackResponse(this.cacheStrategy);
            }
            if (cacheCandidate != null && this.cacheResponse == null) {
                Util.closeQuietly(cacheCandidate.body());
            }
            if (this.networkRequest != null) {
                if (this.connection == null) {
                    connect();
                }
                this.transport = Internal.instance.newTransport(this.connection, this);
                if (this.callerWritesRequestBody && permitsRequestBody() && this.requestBodyOut == null) {
                    long contentLength = OkHeaders.contentLength(request);
                    if (!this.bufferRequestBody) {
                        this.transport.writeRequestHeaders(this.networkRequest);
                        this.requestBodyOut = this.transport.createRequestBody(this.networkRequest, contentLength);
                    } else if (contentLength > 2147483647L) {
                        throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
                    } else if (contentLength != -1) {
                        this.transport.writeRequestHeaders(this.networkRequest);
                        this.requestBodyOut = new RetryableSink((int) contentLength);
                    } else {
                        this.requestBodyOut = new RetryableSink();
                    }
                }
            } else {
                if (this.connection != null) {
                    Internal.instance.recycle(this.client.getConnectionPool(), this.connection);
                    this.connection = null;
                }
                if (this.cacheResponse != null) {
                    this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).build();
                } else {
                    this.userResponse = new Builder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build();
                }
                this.userResponse = unzip(this.userResponse);
            }
        }
    }

    private static Response stripBody(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    private void connect() throws IOException {
        if (this.connection != null) {
            throw new IllegalStateException();
        }
        if (this.routeSelector == null) {
            this.address = createAddress(this.client, this.networkRequest);
            this.routeSelector = RouteSelector.get(this.address, this.networkRequest, this.client);
        }
        this.connection = nextConnection();
        this.route = this.connection.getRoute();
    }

    private Connection nextConnection() throws IOException {
        Connection connection = createNextConnection();
        Internal.instance.connectAndSetOwner(this.client, connection, this, this.networkRequest);
        return connection;
    }

    private Connection createNextConnection() throws IOException {
        Connection pooled;
        ConnectionPool pool = this.client.getConnectionPool();
        while (true) {
            Connection connection = pool.get(this.address);
            pooled = connection;
            if (connection == null) {
                return new Connection(pool, this.routeSelector.next());
            }
            if (this.networkRequest.method().equals("GET")) {
                break;
            } else if (Internal.instance.isReadable(pooled)) {
                break;
            } else {
                pooled.getSocket().close();
            }
        }
        return pooled;
    }

    public void writingRequestHeaders() {
        if (this.sentRequestMillis != -1) {
            throw new IllegalStateException();
        }
        this.sentRequestMillis = System.currentTimeMillis();
    }

    boolean permitsRequestBody() {
        return HttpMethod.permitsRequestBody(this.userRequest.method());
    }

    public Sink getRequestBody() {
        if (this.cacheStrategy != null) {
            return this.requestBodyOut;
        }
        throw new IllegalStateException();
    }

    public BufferedSink getBufferedRequestBody() {
        BufferedSink result = this.bufferedRequestBody;
        if (result != null) {
            return result;
        }
        BufferedSink buffer;
        Sink requestBody = getRequestBody();
        if (requestBody != null) {
            buffer = Okio.buffer(requestBody);
            this.bufferedRequestBody = buffer;
        } else {
            buffer = null;
        }
        return buffer;
    }

    public boolean hasResponse() {
        return this.userResponse != null;
    }

    public Request getRequest() {
        return this.userRequest;
    }

    public Response getResponse() {
        if (this.userResponse != null) {
            return this.userResponse;
        }
        throw new IllegalStateException();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public HttpEngine recover(IOException e, Sink requestBodyOut) {
        boolean canRetryRequestBody;
        if (!(this.routeSelector == null || this.connection == null)) {
            connectFailed(this.routeSelector, e);
        }
        if (requestBodyOut != null) {
            if (!(requestBodyOut instanceof RetryableSink)) {
                canRetryRequestBody = false;
                if (!(this.routeSelector == null && this.connection == null) && ((this.routeSelector == null || this.routeSelector.hasNext()) && isRecoverable(e))) {
                    if (canRetryRequestBody) {
                        return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, close(), this.routeSelector, (RetryableSink) requestBodyOut, this.priorResponse);
                    }
                }
                return null;
            }
        }
        canRetryRequestBody = true;
        if (canRetryRequestBody) {
            return null;
        }
        return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, close(), this.routeSelector, (RetryableSink) requestBodyOut, this.priorResponse);
    }

    private void connectFailed(RouteSelector routeSelector, IOException e) {
        if (Internal.instance.recycleCount(this.connection) <= 0) {
            routeSelector.connectFailed(this.connection.getRoute(), e);
        }
    }

    public HttpEngine recover(IOException e) {
        return recover(e, this.requestBodyOut);
    }

    private boolean isRecoverable(IOException e) {
        if (this.client.getRetryOnConnectionFailure() && !(e instanceof SSLPeerUnverifiedException)) {
            if (!(e instanceof SSLHandshakeException) || !(e.getCause() instanceof CertificateException)) {
                if ((e instanceof ProtocolException) || (e instanceof InterruptedIOException)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public Route getRoute() {
        return this.route;
    }

    private void maybeCache() throws IOException {
        InternalCache responseCache = Internal.instance.internalCache(this.client);
        if (responseCache != null) {
            if (CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
                this.storeRequest = responseCache.put(stripBody(this.userResponse));
                return;
            }
            if (HttpMethod.invalidatesCache(this.networkRequest.method())) {
                try {
                    responseCache.remove(this.networkRequest);
                } catch (IOException e) {
                }
            }
        }
    }

    public void releaseConnection() throws IOException {
        if (!(this.transport == null || this.connection == null)) {
            this.transport.releaseConnectionOnIdle();
        }
        this.connection = null;
    }

    public void disconnect() {
        if (this.transport != null) {
            try {
                this.transport.disconnect(this);
            } catch (IOException e) {
            }
        }
    }

    public Connection close() {
        if (this.bufferedRequestBody != null) {
            Util.closeQuietly(this.bufferedRequestBody);
        } else if (this.requestBodyOut != null) {
            Util.closeQuietly(this.requestBodyOut);
        }
        if (this.userResponse == null) {
            if (this.connection != null) {
                Util.closeQuietly(this.connection.getSocket());
            }
            this.connection = null;
            return null;
        }
        Util.closeQuietly(this.userResponse.body());
        if (this.transport == null || this.connection == null || this.transport.canReuseConnection()) {
            if (!(this.connection == null || Internal.instance.clearOwner(this.connection))) {
                this.connection = null;
            }
            Connection result = this.connection;
            this.connection = null;
            return result;
        }
        Util.closeQuietly(this.connection.getSocket());
        this.connection = null;
        return null;
    }

    private Response unzip(Response response) throws IOException {
        if (this.transparentGzip) {
            if (HttpRequest.ENCODING_GZIP.equalsIgnoreCase(this.userResponse.header("Content-Encoding"))) {
                if (response.body() == null) {
                    return response;
                }
                Source responseBody = new GzipSource(response.body().source());
                Headers strippedHeaders = response.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build();
                return response.newBuilder().headers(strippedHeaders).body(new RealResponseBody(strippedHeaders, Okio.buffer(responseBody))).build();
            }
        }
        return response;
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals("HEAD")) {
            return false;
        }
        int responseCode = response.code();
        if (((responseCode >= 100 && responseCode < 200) || responseCode == HttpStatus.SC_NO_CONTENT || responseCode == HttpStatus.SC_NOT_MODIFIED) && OkHeaders.contentLength(response) == -1) {
            if (!"chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
                return false;
            }
        }
        return true;
    }

    private Request networkRequest(Request request) throws IOException {
        Request.Builder result = request.newBuilder();
        if (request.header("Host") == null) {
            result.header("Host", hostHeader(request.url()));
        }
        if ((this.connection == null || this.connection.getProtocol() != Protocol.HTTP_1_0) && request.header("Connection") == null) {
            result.header("Connection", "Keep-Alive");
        }
        if (request.header("Accept-Encoding") == null) {
            this.transparentGzip = true;
            result.header("Accept-Encoding", HttpRequest.ENCODING_GZIP);
        }
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            OkHeaders.addCookies(result, cookieHandler.get(request.uri(), OkHeaders.toMultimap(result.build().headers(), null)));
        }
        if (request.header("User-Agent") == null) {
            result.header("User-Agent", Version.userAgent());
        }
        return result.build();
    }

    public static String hostHeader(URL url) {
        if (Util.getEffectivePort(url) == Util.getDefaultPort(url.getProtocol())) {
            return url.getHost();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url.getHost());
        stringBuilder.append(":");
        stringBuilder.append(url.getPort());
        return stringBuilder.toString();
    }

    public void readResponse() throws IOException {
        if (this.userResponse == null) {
            if (this.networkRequest == null && this.cacheResponse == null) {
                throw new IllegalStateException("call sendRequest() first!");
            } else if (this.networkRequest != null) {
                Response networkResponse;
                InternalCache responseCache;
                if (this.forWebSocket) {
                    this.transport.writeRequestHeaders(this.networkRequest);
                    networkResponse = readNetworkResponse();
                } else if (this.callerWritesRequestBody) {
                    if (this.bufferedRequestBody != null && this.bufferedRequestBody.buffer().size() > 0) {
                        this.bufferedRequestBody.emit();
                    }
                    if (this.sentRequestMillis == -1) {
                        if (OkHeaders.contentLength(this.networkRequest) == -1 && (this.requestBodyOut instanceof RetryableSink)) {
                            this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(((RetryableSink) this.requestBodyOut).contentLength())).build();
                        }
                        this.transport.writeRequestHeaders(this.networkRequest);
                    }
                    if (this.requestBodyOut != null) {
                        if (this.bufferedRequestBody != null) {
                            this.bufferedRequestBody.close();
                        } else {
                            this.requestBodyOut.close();
                        }
                        if (this.requestBodyOut instanceof RetryableSink) {
                            this.transport.writeRequestBody((RetryableSink) this.requestBodyOut);
                        }
                    }
                    networkResponse = readNetworkResponse();
                    receiveHeaders(networkResponse.headers());
                    if (this.cacheResponse != null) {
                        if (validate(this.cacheResponse, networkResponse)) {
                            Util.closeQuietly(this.cacheResponse.body());
                        } else {
                            this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), networkResponse.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(networkResponse)).build();
                            networkResponse.body().close();
                            releaseConnection();
                            responseCache = Internal.instance.internalCache(this.client);
                            responseCache.trackConditionalCacheHit();
                            responseCache.update(this.cacheResponse, stripBody(this.userResponse));
                            this.userResponse = unzip(this.userResponse);
                            return;
                        }
                    }
                    this.userResponse = networkResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(networkResponse)).build();
                    if (hasBody(this.userResponse)) {
                        maybeCache();
                        this.userResponse = unzip(cacheWritingResponse(this.storeRequest, this.userResponse));
                    }
                } else {
                    networkResponse = new NetworkInterceptorChain(0, this.networkRequest).proceed(this.networkRequest);
                }
                receiveHeaders(networkResponse.headers());
                if (this.cacheResponse != null) {
                    if (validate(this.cacheResponse, networkResponse)) {
                        Util.closeQuietly(this.cacheResponse.body());
                    } else {
                        this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), networkResponse.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(networkResponse)).build();
                        networkResponse.body().close();
                        releaseConnection();
                        responseCache = Internal.instance.internalCache(this.client);
                        responseCache.trackConditionalCacheHit();
                        responseCache.update(this.cacheResponse, stripBody(this.userResponse));
                        this.userResponse = unzip(this.userResponse);
                        return;
                    }
                }
                this.userResponse = networkResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(networkResponse)).build();
                if (hasBody(this.userResponse)) {
                    maybeCache();
                    this.userResponse = unzip(cacheWritingResponse(this.storeRequest, this.userResponse));
                }
            }
        }
    }

    private Response readNetworkResponse() throws IOException {
        this.transport.finishRequest();
        Response networkResponse = this.transport.readResponseHeaders().request(this.networkRequest).handshake(this.connection.getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
        if (!this.forWebSocket) {
            networkResponse = networkResponse.newBuilder().body(this.transport.openResponseBody(networkResponse)).build();
        }
        Internal.instance.setProtocol(this.connection, networkResponse.protocol());
        return networkResponse;
    }

    private Response cacheWritingResponse(final CacheRequest cacheRequest, Response response) throws IOException {
        if (cacheRequest == null) {
            return response;
        }
        Sink cacheBodyUnbuffered = cacheRequest.body();
        if (cacheBodyUnbuffered == null) {
            return response;
        }
        final BufferedSource source = response.body().source();
        final BufferedSink cacheBody = Okio.buffer(cacheBodyUnbuffered);
        return response.newBuilder().body(new RealResponseBody(response.headers(), Okio.buffer(new Source() {
            boolean cacheRequestClosed;

            public long read(Buffer sink, long byteCount) throws IOException {
                try {
                    long bytesRead = source.read(sink, byteCount);
                    if (bytesRead == -1) {
                        if (!this.cacheRequestClosed) {
                            this.cacheRequestClosed = true;
                            cacheBody.close();
                        }
                        return -1;
                    }
                    sink.copyTo(cacheBody.buffer(), sink.size() - bytesRead, bytesRead);
                    cacheBody.emitCompleteSegments();
                    return bytesRead;
                } catch (IOException e) {
                    if (!this.cacheRequestClosed) {
                        this.cacheRequestClosed = true;
                        cacheRequest.abort();
                    }
                    throw e;
                }
            }

            public Timeout timeout() {
                return source.timeout();
            }

            public void close() throws IOException {
                if (!(this.cacheRequestClosed || Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
                    this.cacheRequestClosed = true;
                    cacheRequest.abort();
                }
                source.close();
            }
        }))).build();
    }

    private static boolean validate(Response cached, Response network) {
        if (network.code() == HttpStatus.SC_NOT_MODIFIED) {
            return true;
        }
        Date lastModified = cached.headers().getDate("Last-Modified");
        if (lastModified != null) {
            Date networkLastModified = network.headers().getDate("Last-Modified");
            if (networkLastModified != null && networkLastModified.getTime() < lastModified.getTime()) {
                return true;
            }
        }
        return false;
    }

    private static Headers combine(Headers cachedHeaders, Headers networkHeaders) throws IOException {
        int i;
        Headers.Builder result = new Headers.Builder();
        int size = cachedHeaders.size();
        for (i = 0; i < size; i++) {
            String fieldName = cachedHeaders.name(i);
            String value = cachedHeaders.value(i);
            if (!"Warning".equalsIgnoreCase(fieldName) || !value.startsWith(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                if (!OkHeaders.isEndToEnd(fieldName) || networkHeaders.get(fieldName) == null) {
                    result.add(fieldName, value);
                }
            }
        }
        size = networkHeaders.size();
        for (i = 0; i < size; i++) {
            fieldName = networkHeaders.name(i);
            if (!"Content-Length".equalsIgnoreCase(fieldName)) {
                if (OkHeaders.isEndToEnd(fieldName)) {
                    result.add(fieldName, networkHeaders.value(i));
                }
            }
        }
        return result.build();
    }

    public void receiveHeaders(Headers headers) throws IOException {
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            cookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(headers, null));
        }
    }

    public Request followUpRequest() throws IOException {
        if (this.userResponse == null) {
            throw new IllegalStateException();
        }
        Proxy selectedProxy;
        if (getRoute() != null) {
            selectedProxy = getRoute().getProxy();
        } else {
            selectedProxy = this.client.getProxy();
        }
        int responseCode = this.userResponse.code();
        if (responseCode != HttpStatus.SC_UNAUTHORIZED) {
            if (responseCode != HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED) {
                switch (responseCode) {
                    case HttpStatus.SC_MULTIPLE_CHOICES /*300*/:
                    case HttpStatus.SC_MOVED_PERMANENTLY /*301*/:
                    case HttpStatus.SC_MOVED_TEMPORARILY /*302*/:
                    case HttpStatus.SC_SEE_OTHER /*303*/:
                        break;
                    default:
                        switch (responseCode) {
                            case 307:
                            case StatusLine.HTTP_PERM_REDIRECT /*308*/:
                                if (!(this.userRequest.method().equals("GET") || this.userRequest.method().equals("HEAD"))) {
                                    return null;
                                }
                            default:
                                return null;
                        }
                }
                if (!this.client.getFollowRedirects()) {
                    return null;
                }
                String location = this.userResponse.header("Location");
                if (location == null) {
                    return null;
                }
                URL url = new URL(this.userRequest.url(), location);
                if (!url.getProtocol().equals("https") && !url.getProtocol().equals("http")) {
                    return null;
                }
                if (!url.getProtocol().equals(this.userRequest.url().getProtocol()) && !this.client.getFollowSslRedirects()) {
                    return null;
                }
                Request.Builder requestBuilder = this.userRequest.newBuilder();
                if (HttpMethod.permitsRequestBody(this.userRequest.method())) {
                    requestBuilder.method("GET", null);
                    requestBuilder.removeHeader("Transfer-Encoding");
                    requestBuilder.removeHeader("Content-Length");
                    requestBuilder.removeHeader("Content-Type");
                }
                if (!sameConnection(url)) {
                    requestBuilder.removeHeader("Authorization");
                }
                return requestBuilder.url(url).build();
            } else if (selectedProxy.type() != Type.HTTP) {
                throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
            }
        }
        return OkHeaders.processAuthHeader(this.client.getAuthenticator(), this.userResponse, selectedProxy);
    }

    public boolean sameConnection(URL followUp) {
        URL url = this.userRequest.url();
        return url.getHost().equals(followUp.getHost()) && Util.getEffectivePort(url) == Util.getEffectivePort(followUp) && url.getProtocol().equals(followUp.getProtocol());
    }

    private static Address createAddress(OkHttpClient client, Request request) throws UnknownHostException {
        String uriHost = request.url().getHost();
        if (uriHost != null) {
            if (uriHost.length() != 0) {
                SSLSocketFactory sslSocketFactory = null;
                HostnameVerifier hostnameVerifier = null;
                CertificatePinner certificatePinner = null;
                if (request.isHttps()) {
                    sslSocketFactory = client.getSslSocketFactory();
                    hostnameVerifier = client.getHostnameVerifier();
                    certificatePinner = client.getCertificatePinner();
                }
                HostnameVerifier hostnameVerifier2 = hostnameVerifier;
                return new Address(uriHost, Util.getEffectivePort(request.url()), client.getSocketFactory(), sslSocketFactory, hostnameVerifier2, certificatePinner, client.getAuthenticator(), client.getProxy(), client.getProtocols(), client.getConnectionSpecs(), client.getProxySelector());
            }
        }
        throw new UnknownHostException(request.url().toString());
    }
}
