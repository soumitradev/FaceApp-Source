package com.koushikdutta.async.http.spdy;

import android.net.Uri;
import android.text.TextUtils;
import com.koushikdutta.async.AsyncSSLSocket;
import com.koushikdutta.async.AsyncSSLSocketWrapper.HandshakeCallback;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.future.MultiFuture;
import com.koushikdutta.async.future.SimpleCancellable;
import com.koushikdutta.async.future.TransformFuture;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.GetSocketData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnExchangeHeaderData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnRequestSentData;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncSSLEngineConfigurator;
import com.koushikdutta.async.http.AsyncSSLSocketMiddleware;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.HttpUtil;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.http.spdy.AsyncSpdyConnection.SpdySocket;
import com.koushikdutta.async.util.Charsets;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import name.antonsmirnov.firmata.FormatHelper;

public class SpdyMiddleware extends AsyncSSLSocketMiddleware {
    private static final NoSpdyException NO_SPDY = new NoSpdyException();
    Field alpnProtocols;
    Hashtable<String, SpdyConnectionWaiter> connections = new Hashtable();
    boolean initialized;
    Method nativeGetAlpnNegotiatedProtocol;
    Method nativeGetNpnNegotiatedProtocol;
    Field npnProtocols;
    Field peerHost;
    Field peerPort;
    boolean spdyEnabled;
    Field sslNativePointer;
    Field sslParameters;
    Field useSni;

    private static class NoSpdyException extends Exception {
        private NoSpdyException() {
        }
    }

    /* renamed from: com.koushikdutta.async.http.spdy.SpdyMiddleware$1 */
    class C11961 implements AsyncSSLEngineConfigurator {
        C11961() {
        }

        public SSLEngine createEngine(SSLContext sslContext, String peerHost, int peerPort) {
            return null;
        }

        public void configureEngine(SSLEngine engine, GetSocketData data, String host, int port) {
            SpdyMiddleware.this.configure(engine, data, host, port);
        }
    }

    private static class SpdyConnectionWaiter extends MultiFuture<AsyncSpdyConnection> {
        SimpleCancellable originalCancellable;

        private SpdyConnectionWaiter() {
            this.originalCancellable = new SimpleCancellable();
        }
    }

    public SpdyMiddleware(AsyncHttpClient client) {
        super(client);
        addEngineConfigurator(new C11961());
    }

    private void configure(SSLEngine engine, GetSocketData data, String host, int port) {
        if (!this.initialized && this.spdyEnabled) {
            this.initialized = true;
            try {
                this.peerHost = engine.getClass().getSuperclass().getDeclaredField("peerHost");
                this.peerPort = engine.getClass().getSuperclass().getDeclaredField("peerPort");
                this.sslParameters = engine.getClass().getDeclaredField("sslParameters");
                this.npnProtocols = this.sslParameters.getType().getDeclaredField("npnProtocols");
                this.alpnProtocols = this.sslParameters.getType().getDeclaredField("alpnProtocols");
                this.useSni = this.sslParameters.getType().getDeclaredField("useSni");
                this.sslNativePointer = engine.getClass().getDeclaredField("sslNativePointer");
                String nativeCryptoName = new StringBuilder();
                nativeCryptoName.append(this.sslParameters.getType().getPackage().getName());
                nativeCryptoName.append(".NativeCrypto");
                nativeCryptoName = nativeCryptoName.toString();
                this.nativeGetNpnNegotiatedProtocol = Class.forName(nativeCryptoName, true, this.sslParameters.getType().getClassLoader()).getDeclaredMethod("SSL_get_npn_negotiated_protocol", new Class[]{Long.TYPE});
                this.nativeGetAlpnNegotiatedProtocol = Class.forName(nativeCryptoName, true, this.sslParameters.getType().getClassLoader()).getDeclaredMethod("SSL_get0_alpn_selected", new Class[]{Long.TYPE});
                this.peerHost.setAccessible(true);
                this.peerPort.setAccessible(true);
                this.sslParameters.setAccessible(true);
                this.npnProtocols.setAccessible(true);
                this.alpnProtocols.setAccessible(true);
                this.useSni.setAccessible(true);
                this.sslNativePointer.setAccessible(true);
                this.nativeGetNpnNegotiatedProtocol.setAccessible(true);
                this.nativeGetAlpnNegotiatedProtocol.setAccessible(true);
            } catch (Exception e) {
                this.sslParameters = null;
                this.npnProtocols = null;
                this.alpnProtocols = null;
                this.useSni = null;
                this.sslNativePointer = null;
                this.nativeGetNpnNegotiatedProtocol = null;
                this.nativeGetAlpnNegotiatedProtocol = null;
            }
        }
        if (canSpdyRequest(data) && this.sslParameters != null) {
            try {
                byte[] protocols = concatLengthPrefixed(Protocol.SPDY_3);
                this.peerHost.set(engine, host);
                this.peerPort.set(engine, Integer.valueOf(port));
                Object sslp = this.sslParameters.get(engine);
                this.alpnProtocols.set(sslp, protocols);
                this.useSni.set(sslp, Boolean.valueOf(true));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public boolean getSpdyEnabled() {
        return this.spdyEnabled;
    }

    public void setSpdyEnabled(boolean enabled) {
        this.spdyEnabled = enabled;
    }

    public void setSSLContext(SSLContext sslContext) {
        super.setSSLContext(sslContext);
        this.initialized = false;
    }

    static byte[] concatLengthPrefixed(Protocol... protocols) {
        ByteBuffer result = ByteBuffer.allocate(8192);
        for (Protocol protocol : protocols) {
            if (protocol != Protocol.HTTP_1_0) {
                result.put((byte) protocol.toString().length());
                result.put(protocol.toString().getBytes(Charsets.UTF_8));
            }
        }
        result.flip();
        return new ByteBufferList(result).getAllByteArray();
    }

    private static String requestPath(Uri uri) {
        String pathAndQuery = uri.getEncodedPath();
        if (pathAndQuery == null) {
            pathAndQuery = "/";
        } else if (!pathAndQuery.startsWith("/")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/");
            stringBuilder.append(pathAndQuery);
            pathAndQuery = stringBuilder.toString();
        }
        if (TextUtils.isEmpty(uri.getEncodedQuery())) {
            return pathAndQuery;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(pathAndQuery);
        stringBuilder.append("?");
        stringBuilder.append(uri.getEncodedQuery());
        return stringBuilder.toString();
    }

    private void noSpdy(String key) {
        SpdyConnectionWaiter conn = (SpdyConnectionWaiter) this.connections.remove(key);
        if (conn != null) {
            conn.setComplete(NO_SPDY);
        }
    }

    private void invokeConnect(String key, ConnectCallback callback, Exception e, AsyncSSLSocket socket) {
        SpdyConnectionWaiter waiter = (SpdyConnectionWaiter) this.connections.get(key);
        if (waiter == null || waiter.originalCancellable.setComplete()) {
            callback.onConnectCompleted(e, socket);
        }
    }

    protected HandshakeCallback createHandshakeCallback(final GetSocketData data, final ConnectCallback callback) {
        final String key = (String) data.state.get("spdykey");
        if (key == null) {
            return super.createHandshakeCallback(data, callback);
        }
        return new HandshakeCallback() {
            public void onHandshakeCompleted(Exception e, AsyncSSLSocket socket) {
                data.request.logv("checking spdy handshake");
                if (e == null) {
                    if (SpdyMiddleware.this.nativeGetAlpnNegotiatedProtocol != null) {
                        try {
                            long ptr = ((Long) SpdyMiddleware.this.sslNativePointer.get(socket.getSSLEngine())).longValue();
                            byte[] proto = (byte[]) SpdyMiddleware.this.nativeGetAlpnNegotiatedProtocol.invoke(null, new Object[]{Long.valueOf(ptr)});
                            if (proto == null) {
                                SpdyMiddleware.this.invokeConnect(key, callback, null, socket);
                                SpdyMiddleware.this.noSpdy(key);
                                return;
                            }
                            String protoString = new String(proto);
                            Protocol p = Protocol.get(protoString);
                            if (p != null) {
                                if (p.needsSpdyConnection()) {
                                    try {
                                        new AsyncSpdyConnection(socket, Protocol.get(protoString)) {
                                            boolean hasReceivedSettings;

                                            public void settings(boolean clearPrevious, Settings settings) {
                                                super.settings(clearPrevious, settings);
                                                if (!this.hasReceivedSettings) {
                                                    this.hasReceivedSettings = true;
                                                    SpdyConnectionWaiter waiter = (SpdyConnectionWaiter) SpdyMiddleware.this.connections.get(key);
                                                    if (waiter.originalCancellable.setComplete()) {
                                                        AsyncHttpRequest asyncHttpRequest = data.request;
                                                        StringBuilder stringBuilder = new StringBuilder();
                                                        stringBuilder.append("using new spdy connection for host: ");
                                                        stringBuilder.append(data.request.getUri().getHost());
                                                        asyncHttpRequest.logv(stringBuilder.toString());
                                                        SpdyMiddleware.this.newSocket(data, this, callback);
                                                    }
                                                    waiter.setComplete((Object) this);
                                                }
                                            }
                                        }.sendConnectionPreface();
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                    return;
                                }
                            }
                            SpdyMiddleware.this.invokeConnect(key, callback, null, socket);
                            SpdyMiddleware.this.noSpdy(key);
                            return;
                        } catch (Exception ex) {
                            throw new AssertionError(ex);
                        }
                    }
                }
                SpdyMiddleware.this.invokeConnect(key, callback, e, socket);
                SpdyMiddleware.this.noSpdy(key);
            }
        };
    }

    private void newSocket(GetSocketData data, AsyncSpdyConnection connection, ConnectCallback callback) {
        AsyncHttpRequest request = data.request;
        data.protocol = connection.protocol.toString();
        AsyncHttpRequestBody requestBody = data.request.getBody();
        ArrayList<Header> headers = new ArrayList();
        headers.add(new Header(Header.TARGET_METHOD, request.getMethod()));
        headers.add(new Header(Header.TARGET_PATH, requestPath(request.getUri())));
        String host = request.getHeaders().get("Host");
        if (Protocol.SPDY_3 == connection.protocol) {
            headers.add(new Header(Header.VERSION, "HTTP/1.1"));
            headers.add(new Header(Header.TARGET_HOST, host));
        } else if (Protocol.HTTP_2 == connection.protocol) {
            headers.add(new Header(Header.TARGET_AUTHORITY, host));
        } else {
            throw new AssertionError();
        }
        headers.add(new Header(Header.TARGET_SCHEME, request.getUri().getScheme()));
        Multimap mm = request.getHeaders().getMultiMap();
        for (String key : mm.keySet()) {
            if (!SpdyTransport.isProhibitedHeader(connection.protocol, key)) {
                for (String value : (List) mm.get(key)) {
                    headers.add(new Header(key.toLowerCase(Locale.US), value));
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append(request);
        request.logv(stringBuilder.toString());
        callback.onConnectCompleted(null, connection.newStream(headers, requestBody != null, true));
    }

    private boolean canSpdyRequest(GetSocketData data) {
        return data.request.getBody() == null;
    }

    protected ConnectCallback wrapCallback(GetSocketData data, Uri uri, int port, boolean proxied, ConnectCallback callback) {
        final ConnectCallback superCallback = super.wrapCallback(data, uri, port, proxied, callback);
        final String key = (String) data.state.get("spdykey");
        if (key == null) {
            return superCallback;
        }
        return new ConnectCallback() {
            public void onConnectCompleted(Exception ex, AsyncSocket socket) {
                if (ex != null) {
                    SpdyConnectionWaiter conn = (SpdyConnectionWaiter) SpdyMiddleware.this.connections.remove(key);
                    if (conn != null) {
                        conn.setComplete(ex);
                    }
                }
                superCallback.onConnectCompleted(ex, socket);
            }
        };
    }

    public Cancellable getSocket(final GetSocketData data) {
        Uri uri = data.request.getUri();
        int port = getSchemePort(data.request.getUri());
        if (port == -1) {
            return null;
        }
        if (!this.spdyEnabled) {
            return super.getSocket(data);
        }
        if (!canSpdyRequest(data)) {
            return super.getSocket(data);
        }
        String key = new StringBuilder();
        key.append(uri.getHost());
        key.append(port);
        key = key.toString();
        SpdyConnectionWaiter conn = (SpdyConnectionWaiter) this.connections.get(key);
        if (conn != null) {
            if (conn.tryGetException() instanceof NoSpdyException) {
                return super.getSocket(data);
            }
            if (!(conn.tryGet() == null || ((AsyncSpdyConnection) conn.tryGet()).socket.isOpen())) {
                this.connections.remove(key);
                conn = null;
            }
        }
        if (conn == null) {
            data.state.put("spdykey", key);
            Cancellable ret = super.getSocket(data);
            if (!ret.isDone()) {
                if (!ret.isCancelled()) {
                    SpdyConnectionWaiter conn2 = new SpdyConnectionWaiter();
                    this.connections.put(key, conn2);
                    return conn2.originalCancellable;
                }
            }
            return ret;
        }
        AsyncHttpRequest asyncHttpRequest = data.request;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("waiting for potential spdy connection for host: ");
        stringBuilder.append(data.request.getUri().getHost());
        asyncHttpRequest.logv(stringBuilder.toString());
        final SimpleCancellable ret2 = new SimpleCancellable();
        conn.setCallback(new FutureCallback<AsyncSpdyConnection>() {
            public void onCompleted(Exception e, AsyncSpdyConnection conn) {
                if (e instanceof NoSpdyException) {
                    data.request.logv("spdy not available");
                    ret2.setParent(super.getSocket(data));
                } else if (e != null) {
                    if (ret2.setComplete()) {
                        data.connectCallback.onConnectCompleted(e, null);
                    }
                } else {
                    AsyncHttpRequest asyncHttpRequest = data.request;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("using existing spdy connection for host: ");
                    stringBuilder.append(data.request.getUri().getHost());
                    asyncHttpRequest.logv(stringBuilder.toString());
                    if (ret2.setComplete()) {
                        SpdyMiddleware.this.newSocket(data, conn, data.connectCallback);
                    }
                }
            }
        });
        return ret2;
    }

    public boolean exchangeHeaders(final OnExchangeHeaderData data) {
        if (!(data.socket instanceof SpdySocket)) {
            return super.exchangeHeaders(data);
        }
        if (data.request.getBody() != null) {
            data.response.sink(data.socket);
        }
        data.sendHeadersCallback.onCompleted(null);
        final SpdySocket spdySocket = data.socket;
        ((C13626) spdySocket.headers().then(new TransformFuture<Headers, List<Header>>() {
            protected void transform(List<Header> result) throws Exception {
                Headers headers = new Headers();
                for (Header header : result) {
                    headers.add(header.name.utf8(), header.value.utf8());
                }
                String[] statusParts = headers.remove(Header.RESPONSE_STATUS.utf8()).split(FormatHelper.SPACE, 2);
                data.response.code(Integer.parseInt(statusParts[0]));
                if (statusParts.length == 2) {
                    data.response.message(statusParts[1]);
                }
                data.response.protocol(headers.remove(Header.VERSION.utf8()));
                data.response.headers(headers);
                setComplete((Object) headers);
            }
        })).setCallback(new FutureCallback<Headers>() {
            public void onCompleted(Exception e, Headers result) {
                data.receiveHeadersCallback.onCompleted(e);
                data.response.emitter(HttpUtil.getBodyDecoder(spdySocket, spdySocket.getConnection().protocol, result, false));
            }
        });
        return true;
    }

    public void onRequestSent(OnRequestSentData data) {
        if ((data.socket instanceof SpdySocket) && data.request.getBody() != null) {
            data.response.sink().end();
        }
    }
}
