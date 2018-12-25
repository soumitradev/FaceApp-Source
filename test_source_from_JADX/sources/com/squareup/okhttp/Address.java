package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class Address {
    final Authenticator authenticator;
    final CertificatePinner certificatePinner;
    final List<ConnectionSpec> connectionSpecs;
    final HostnameVerifier hostnameVerifier;
    final List<Protocol> protocols;
    final Proxy proxy;
    final ProxySelector proxySelector;
    final SocketFactory socketFactory;
    final SSLSocketFactory sslSocketFactory;
    final String uriHost;
    final int uriPort;

    public Address(String uriHost, int uriPort, SocketFactory socketFactory, SSLSocketFactory sslSocketFactory, HostnameVerifier hostnameVerifier, CertificatePinner certificatePinner, Authenticator authenticator, Proxy proxy, List<Protocol> protocols, List<ConnectionSpec> connectionSpecs, ProxySelector proxySelector) {
        if (uriHost == null) {
            throw new NullPointerException("uriHost == null");
        } else if (uriPort <= 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("uriPort <= 0: ");
            stringBuilder.append(uriPort);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (authenticator == null) {
            throw new IllegalArgumentException("authenticator == null");
        } else if (protocols == null) {
            throw new IllegalArgumentException("protocols == null");
        } else if (proxySelector == null) {
            throw new IllegalArgumentException("proxySelector == null");
        } else {
            this.proxy = proxy;
            this.uriHost = uriHost;
            this.uriPort = uriPort;
            this.socketFactory = socketFactory;
            this.sslSocketFactory = sslSocketFactory;
            this.hostnameVerifier = hostnameVerifier;
            this.certificatePinner = certificatePinner;
            this.authenticator = authenticator;
            this.protocols = Util.immutableList((List) protocols);
            this.connectionSpecs = Util.immutableList((List) connectionSpecs);
            this.proxySelector = proxySelector;
        }
    }

    public String getUriHost() {
        return this.uriHost;
    }

    public int getUriPort() {
        return this.uriPort;
    }

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
    }

    public List<Protocol> getProtocols() {
        return this.protocols;
    }

    public List<ConnectionSpec> getConnectionSpecs() {
        return this.connectionSpecs;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public boolean equals(Object other) {
        boolean z = false;
        if (!(other instanceof Address)) {
            return false;
        }
        Address that = (Address) other;
        if (Util.equal(this.proxy, that.proxy) && this.uriHost.equals(that.uriHost) && this.uriPort == that.uriPort && Util.equal(this.sslSocketFactory, that.sslSocketFactory) && Util.equal(this.hostnameVerifier, that.hostnameVerifier) && Util.equal(this.certificatePinner, that.certificatePinner) && Util.equal(this.authenticator, that.authenticator) && Util.equal(this.protocols, that.protocols) && Util.equal(this.connectionSpecs, that.connectionSpecs) && Util.equal(this.proxySelector, that.proxySelector)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((17 * 31) + (this.proxy != null ? this.proxy.hashCode() : 0)) * 31) + this.uriHost.hashCode()) * 31) + this.uriPort) * 31) + (this.sslSocketFactory != null ? this.sslSocketFactory.hashCode() : 0)) * 31) + (this.hostnameVerifier != null ? this.hostnameVerifier.hashCode() : 0)) * 31;
        if (this.certificatePinner != null) {
            i = this.certificatePinner.hashCode();
        }
        return ((((((((hashCode + i) * 31) + this.authenticator.hashCode()) * 31) + this.protocols.hashCode()) * 31) + this.connectionSpecs.hashCode()) * 31) + this.proxySelector.hashCode();
    }
}
