package com.squareup.okhttp;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public final class Route {
    final Address address;
    final ConnectionSpec connectionSpec;
    final InetSocketAddress inetSocketAddress;
    final Proxy proxy;
    final boolean shouldSendTlsFallbackIndicator;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress, ConnectionSpec connectionSpec) {
        this(address, proxy, inetSocketAddress, connectionSpec, false);
    }

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress, ConnectionSpec connectionSpec, boolean shouldSendTlsFallbackIndicator) {
        if (address == null) {
            throw new NullPointerException("address == null");
        } else if (proxy == null) {
            throw new NullPointerException("proxy == null");
        } else if (inetSocketAddress == null) {
            throw new NullPointerException("inetSocketAddress == null");
        } else if (connectionSpec == null) {
            throw new NullPointerException("connectionConfiguration == null");
        } else {
            this.address = address;
            this.proxy = proxy;
            this.inetSocketAddress = inetSocketAddress;
            this.connectionSpec = connectionSpec;
            this.shouldSendTlsFallbackIndicator = shouldSendTlsFallbackIndicator;
        }
    }

    public Address getAddress() {
        return this.address;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public InetSocketAddress getSocketAddress() {
        return this.inetSocketAddress;
    }

    public ConnectionSpec getConnectionSpec() {
        return this.connectionSpec;
    }

    public boolean getShouldSendTlsFallbackIndicator() {
        return this.shouldSendTlsFallbackIndicator;
    }

    public boolean requiresTunnel() {
        return this.address.sslSocketFactory != null && this.proxy.type() == Type.HTTP;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Route)) {
            return false;
        }
        Route other = (Route) obj;
        if (this.address.equals(other.address) && this.proxy.equals(other.proxy) && this.inetSocketAddress.equals(other.inetSocketAddress) && this.connectionSpec.equals(other.connectionSpec) && this.shouldSendTlsFallbackIndicator == other.shouldSendTlsFallbackIndicator) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return (((((((((17 * 31) + this.address.hashCode()) * 31) + this.proxy.hashCode()) * 31) + this.inetSocketAddress.hashCode()) * 31) + this.connectionSpec.hashCode()) * 31) + this.shouldSendTlsFallbackIndicator;
    }
}
