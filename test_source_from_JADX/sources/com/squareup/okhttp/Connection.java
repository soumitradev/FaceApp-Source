package com.squareup.okhttp;

import com.badlogic.gdx.net.HttpStatus;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpTransport;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.SpdyTransport;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.io.IOException;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import okio.Source;

public final class Connection {
    private boolean connected = false;
    private Handshake handshake;
    private HttpConnection httpConnection;
    private long idleStartTimeNs;
    private Object owner;
    private final ConnectionPool pool;
    private Protocol protocol = Protocol.HTTP_1_1;
    private int recycleCount;
    private final Route route;
    private Socket socket;
    private SpdyConnection spdyConnection;

    public Connection(ConnectionPool pool, Route route) {
        this.pool = pool;
        this.route = route;
    }

    Object getOwner() {
        Object obj;
        synchronized (this.pool) {
            obj = this.owner;
        }
        return obj;
    }

    void setOwner(Object owner) {
        if (!isSpdy()) {
            synchronized (this.pool) {
                if (this.owner != null) {
                    throw new IllegalStateException("Connection already has an owner!");
                }
                this.owner = owner;
            }
        }
    }

    boolean clearOwner() {
        synchronized (this.pool) {
            if (this.owner == null) {
                return false;
            }
            this.owner = null;
            return true;
        }
    }

    void closeIfOwnedBy(Object owner) throws IOException {
        if (isSpdy()) {
            throw new IllegalStateException();
        }
        synchronized (this.pool) {
            if (this.owner != owner) {
                return;
            }
            this.owner = null;
            this.socket.close();
        }
    }

    void connect(int connectTimeout, int readTimeout, int writeTimeout, Request tunnelRequest) throws IOException {
        if (this.connected) {
            throw new IllegalStateException("already connected");
        }
        if (this.route.proxy.type() != Type.DIRECT) {
            if (this.route.proxy.type() != Type.HTTP) {
                this.socket = new Socket(this.route.proxy);
                this.socket.setSoTimeout(readTimeout);
                Platform.get().connectSocket(this.socket, this.route.inetSocketAddress, connectTimeout);
                if (this.route.address.sslSocketFactory == null) {
                    upgradeToTls(tunnelRequest, readTimeout, writeTimeout);
                } else {
                    this.httpConnection = new HttpConnection(this.pool, this, this.socket);
                }
                this.connected = true;
            }
        }
        this.socket = this.route.address.socketFactory.createSocket();
        this.socket.setSoTimeout(readTimeout);
        Platform.get().connectSocket(this.socket, this.route.inetSocketAddress, connectTimeout);
        if (this.route.address.sslSocketFactory == null) {
            this.httpConnection = new HttpConnection(this.pool, this, this.socket);
        } else {
            upgradeToTls(tunnelRequest, readTimeout, writeTimeout);
        }
        this.connected = true;
    }

    void connectAndSetOwner(OkHttpClient client, Object owner, Request request) throws IOException {
        setOwner(owner);
        if (!isConnected()) {
            connect(client.getConnectTimeout(), client.getReadTimeout(), client.getWriteTimeout(), tunnelRequest(request));
            if (isSpdy()) {
                client.getConnectionPool().share(this);
            }
            client.routeDatabase().connected(getRoute());
        }
        setTimeouts(client.getReadTimeout(), client.getWriteTimeout());
    }

    private Request tunnelRequest(Request request) throws IOException {
        if (!this.route.requiresTunnel()) {
            return null;
        }
        String authority;
        String host = request.url().getHost();
        int port = Util.getEffectivePort(request.url());
        if (port == Util.getDefaultPort("https")) {
            authority = host;
        } else {
            authority = new StringBuilder();
            authority.append(host);
            authority.append(":");
            authority.append(port);
            authority = authority.toString();
        }
        Builder result = new Builder().url(new URL("https", host, port, "/")).header("Host", authority).header("Proxy-Connection", "Keep-Alive");
        String userAgent = request.header("User-Agent");
        if (userAgent != null) {
            result.header("User-Agent", userAgent);
        }
        String proxyAuthorization = request.header("Proxy-Authorization");
        if (proxyAuthorization != null) {
            result.header("Proxy-Authorization", proxyAuthorization);
        }
        return result.build();
    }

    private void upgradeToTls(Request tunnelRequest, int readTimeout, int writeTimeout) throws IOException {
        Platform platform = Platform.get();
        if (tunnelRequest != null) {
            makeTunnel(tunnelRequest, readTimeout, writeTimeout);
        }
        this.socket = this.route.address.sslSocketFactory.createSocket(this.socket, this.route.address.uriHost, this.route.address.uriPort, true);
        SSLSocket sslSocket = this.socket;
        this.route.connectionSpec.apply(sslSocket, this.route);
        try {
            sslSocket.startHandshake();
            if (this.route.connectionSpec.supportsTlsExtensions()) {
                String selectedProtocol = platform.getSelectedProtocol(sslSocket);
                String maybeProtocol = selectedProtocol;
                if (selectedProtocol != null) {
                    this.protocol = Protocol.get(maybeProtocol);
                }
            }
            platform.afterHandshake(sslSocket);
            this.handshake = Handshake.get(sslSocket.getSession());
            if (this.route.address.hostnameVerifier.verify(this.route.address.uriHost, sslSocket.getSession())) {
                this.route.address.certificatePinner.check(this.route.address.uriHost, this.handshake.peerCertificates());
                if (this.protocol != Protocol.SPDY_3) {
                    if (this.protocol != Protocol.HTTP_2) {
                        this.httpConnection = new HttpConnection(this.pool, this, this.socket);
                        return;
                    }
                }
                sslSocket.setSoTimeout(0);
                this.spdyConnection = new SpdyConnection.Builder(this.route.address.getUriHost(), true, this.socket).protocol(this.protocol).build();
                this.spdyConnection.sendConnectionPreface();
                return;
            }
            X509Certificate cert = sslSocket.getSession().getPeerCertificates()[0];
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Hostname ");
            stringBuilder.append(this.route.address.uriHost);
            stringBuilder.append(" not verified:");
            stringBuilder.append("\n    certificate: ");
            stringBuilder.append(CertificatePinner.pin(cert));
            stringBuilder.append("\n    DN: ");
            stringBuilder.append(cert.getSubjectDN().getName());
            stringBuilder.append("\n    subjectAltNames: ");
            stringBuilder.append(OkHostnameVerifier.allSubjectAltNames(cert));
            throw new SSLPeerUnverifiedException(stringBuilder.toString());
        } catch (Throwable th) {
            platform.afterHandshake(sslSocket);
        }
    }

    boolean isConnected() {
        return this.connected;
    }

    public Route getRoute() {
        return this.route;
    }

    public Socket getSocket() {
        return this.socket;
    }

    boolean isAlive() {
        return (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown()) ? false : true;
    }

    boolean isReadable() {
        if (this.httpConnection != null) {
            return this.httpConnection.isReadable();
        }
        return true;
    }

    void resetIdleStartTime() {
        if (this.spdyConnection != null) {
            throw new IllegalStateException("spdyConnection != null");
        }
        this.idleStartTimeNs = System.nanoTime();
    }

    boolean isIdle() {
        if (this.spdyConnection != null) {
            if (!this.spdyConnection.isIdle()) {
                return false;
            }
        }
        return true;
    }

    long getIdleStartTimeNs() {
        return this.spdyConnection == null ? this.idleStartTimeNs : this.spdyConnection.getIdleStartTimeNs();
    }

    public Handshake getHandshake() {
        return this.handshake;
    }

    Transport newTransport(HttpEngine httpEngine) throws IOException {
        return this.spdyConnection != null ? new SpdyTransport(httpEngine, this.spdyConnection) : new HttpTransport(httpEngine, this.httpConnection);
    }

    boolean isSpdy() {
        return this.spdyConnection != null;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    void setProtocol(Protocol protocol) {
        if (protocol == null) {
            throw new IllegalArgumentException("protocol == null");
        }
        this.protocol = protocol;
    }

    void setTimeouts(int readTimeoutMillis, int writeTimeoutMillis) throws IOException {
        if (!this.connected) {
            throw new IllegalStateException("setTimeouts - not connected");
        } else if (this.httpConnection != null) {
            this.socket.setSoTimeout(readTimeoutMillis);
            this.httpConnection.setTimeouts(readTimeoutMillis, writeTimeoutMillis);
        }
    }

    void incrementRecycleCount() {
        this.recycleCount++;
    }

    int recycleCount() {
        return this.recycleCount;
    }

    private void makeTunnel(Request request, int readTimeout, int writeTimeout) throws IOException {
        HttpConnection tunnelConnection = new HttpConnection(this.pool, this, this.socket);
        tunnelConnection.setTimeouts(readTimeout, writeTimeout);
        URL url = request.url();
        String requestLine = new StringBuilder();
        requestLine.append("CONNECT ");
        requestLine.append(url.getHost());
        requestLine.append(":");
        requestLine.append(url.getPort());
        requestLine.append(" HTTP/1.1");
        requestLine = requestLine.toString();
        while (true) {
            tunnelConnection.writeRequest(request.headers(), requestLine);
            tunnelConnection.flush();
            Response response = tunnelConnection.readResponse().request(request).build();
            long contentLength = OkHeaders.contentLength(response);
            if (contentLength == -1) {
                contentLength = 0;
            }
            Source body = tunnelConnection.newFixedLengthSource(contentLength);
            Util.skipAll(body, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            body.close();
            int code = response.code();
            if (code == 200) {
                break;
            } else if (code != HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected response code for CONNECT: ");
                stringBuilder.append(response.code());
                throw new IOException(stringBuilder.toString());
            } else {
                request = OkHeaders.processAuthHeader(this.route.address.authenticator, response, this.route.proxy);
                if (request == null) {
                    throw new IOException("Failed to authenticate with proxy");
                }
            }
        }
        if (tunnelConnection.bufferSize() > 0) {
            throw new IOException("TLS tunnel buffered too many bytes!");
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Connection{");
        stringBuilder.append(this.route.address.uriHost);
        stringBuilder.append(":");
        stringBuilder.append(this.route.address.uriPort);
        stringBuilder.append(", proxy=");
        stringBuilder.append(this.route.proxy);
        stringBuilder.append(" hostAddress=");
        stringBuilder.append(this.route.inetSocketAddress.getAddress().getHostAddress());
        stringBuilder.append(" cipherSuite=");
        stringBuilder.append(this.handshake != null ? this.handshake.cipherSuite() : "none");
        stringBuilder.append(" protocol=");
        stringBuilder.append(this.protocol);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
