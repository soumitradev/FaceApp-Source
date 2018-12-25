package com.squareup.okhttp.internal.http;

import android.support.v4.internal.view.SupportMenu;
import com.squareup.okhttp.Address;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLProtocolException;

public final class RouteSelector {
    private final Address address;
    private final OkHttpClient client;
    private List<ConnectionSpec> connectionSpecs = Collections.emptyList();
    private List<InetSocketAddress> inetSocketAddresses = Collections.emptyList();
    private InetSocketAddress lastInetSocketAddress;
    private Proxy lastProxy;
    private ConnectionSpec lastSpec;
    private final Network network;
    private int nextInetSocketAddressIndex;
    private int nextProxyIndex;
    private int nextSpecIndex;
    private final List<Route> postponedRoutes = new ArrayList();
    private List<Proxy> proxies = Collections.emptyList();
    private final Request request;
    private final RouteDatabase routeDatabase;
    private final URI uri;

    private RouteSelector(Address address, URI uri, OkHttpClient client, Request request) {
        this.address = address;
        this.uri = uri;
        this.client = client;
        this.routeDatabase = Internal.instance.routeDatabase(client);
        this.network = Internal.instance.network(client);
        this.request = request;
        resetNextProxy(uri, address.getProxy());
    }

    public static RouteSelector get(Address address, Request request, OkHttpClient client) throws IOException {
        return new RouteSelector(address, request.uri(), client, request);
    }

    public boolean hasNext() {
        if (!(hasNextConnectionSpec() || hasNextInetSocketAddress() || hasNextProxy())) {
            if (!hasNextPostponed()) {
                return false;
            }
        }
        return true;
    }

    public Route next() throws IOException {
        if (!hasNextConnectionSpec()) {
            if (!hasNextInetSocketAddress()) {
                if (hasNextProxy()) {
                    this.lastProxy = nextProxy();
                } else if (hasNextPostponed()) {
                    return nextPostponed();
                } else {
                    throw new NoSuchElementException();
                }
            }
            this.lastInetSocketAddress = nextInetSocketAddress();
        }
        this.lastSpec = nextConnectionSpec();
        Route route = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, this.lastSpec, shouldSendTlsFallbackIndicator(this.lastSpec));
        if (!this.routeDatabase.shouldPostpone(route)) {
            return route;
        }
        this.postponedRoutes.add(route);
        return next();
    }

    private boolean shouldSendTlsFallbackIndicator(ConnectionSpec connectionSpec) {
        if (connectionSpec == this.connectionSpecs.get(0) || !connectionSpec.isTls()) {
            return false;
        }
        return true;
    }

    public void connectFailed(Route failedRoute, IOException failure) {
        if (!(failedRoute.getProxy().type() == Type.DIRECT || this.address.getProxySelector() == null)) {
            this.address.getProxySelector().connectFailed(this.uri, failedRoute.getProxy().address(), failure);
        }
        this.routeDatabase.failed(failedRoute);
        if (!(failure instanceof SSLHandshakeException) && !(failure instanceof SSLProtocolException)) {
            while (this.nextSpecIndex < this.connectionSpecs.size()) {
                List list = this.connectionSpecs;
                int i = this.nextSpecIndex;
                this.nextSpecIndex = i + 1;
                ConnectionSpec connectionSpec = (ConnectionSpec) list.get(i);
                this.routeDatabase.failed(new Route(this.address, this.lastProxy, this.lastInetSocketAddress, connectionSpec, shouldSendTlsFallbackIndicator(connectionSpec)));
            }
        }
    }

    private void resetNextProxy(URI uri, Proxy proxy) {
        if (proxy != null) {
            this.proxies = Collections.singletonList(proxy);
        } else {
            this.proxies = new ArrayList();
            List<Proxy> selectedProxies = this.client.getProxySelector().select(uri);
            if (selectedProxies != null) {
                this.proxies.addAll(selectedProxies);
            }
            this.proxies.removeAll(Collections.singleton(Proxy.NO_PROXY));
            this.proxies.add(Proxy.NO_PROXY);
        }
        this.nextProxyIndex = 0;
    }

    private boolean hasNextProxy() {
        return this.nextProxyIndex < this.proxies.size();
    }

    private Proxy nextProxy() throws IOException {
        if (hasNextProxy()) {
            List list = this.proxies;
            int i = this.nextProxyIndex;
            this.nextProxyIndex = i + 1;
            Proxy result = (Proxy) list.get(i);
            resetNextInetSocketAddress(result);
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No route to ");
        stringBuilder.append(this.address.getUriHost());
        stringBuilder.append("; exhausted proxy configurations: ");
        stringBuilder.append(this.proxies);
        throw new SocketException(stringBuilder.toString());
    }

    private void resetNextInetSocketAddress(Proxy proxy) throws IOException {
        String socketHost;
        int socketPort;
        StringBuilder stringBuilder;
        this.inetSocketAddresses = new ArrayList();
        if (proxy.type() != Type.DIRECT) {
            if (proxy.type() != Type.SOCKS) {
                SocketAddress proxyAddress = proxy.address();
                if (proxyAddress instanceof InetSocketAddress) {
                    InetSocketAddress proxySocketAddress = (InetSocketAddress) proxyAddress;
                    socketHost = getHostString(proxySocketAddress);
                    socketPort = proxySocketAddress.getPort();
                    if (socketPort >= 1) {
                        if (socketPort > SupportMenu.USER_MASK) {
                            for (InetAddress inetAddress : this.network.resolveInetAddresses(socketHost)) {
                                this.inetSocketAddresses.add(new InetSocketAddress(inetAddress, socketPort));
                            }
                            this.nextInetSocketAddressIndex = 0;
                            return;
                        }
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("No route to ");
                    stringBuilder.append(socketHost);
                    stringBuilder.append(":");
                    stringBuilder.append(socketPort);
                    stringBuilder.append("; port is out of range");
                    throw new SocketException(stringBuilder.toString());
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Proxy.address() is not an InetSocketAddress: ");
                stringBuilder2.append(proxyAddress.getClass());
                throw new IllegalArgumentException(stringBuilder2.toString());
            }
        }
        socketHost = this.address.getUriHost();
        socketPort = Util.getEffectivePort(this.uri);
        if (socketPort >= 1) {
            if (socketPort > SupportMenu.USER_MASK) {
                while (r5 < r3) {
                    this.inetSocketAddresses.add(new InetSocketAddress(inetAddress, socketPort));
                }
                this.nextInetSocketAddressIndex = 0;
                return;
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("No route to ");
        stringBuilder.append(socketHost);
        stringBuilder.append(":");
        stringBuilder.append(socketPort);
        stringBuilder.append("; port is out of range");
        throw new SocketException(stringBuilder.toString());
    }

    static String getHostString(InetSocketAddress socketAddress) {
        InetAddress address = socketAddress.getAddress();
        if (address == null) {
            return socketAddress.getHostName();
        }
        return address.getHostAddress();
    }

    private boolean hasNextInetSocketAddress() {
        return this.nextInetSocketAddressIndex < this.inetSocketAddresses.size();
    }

    private InetSocketAddress nextInetSocketAddress() throws IOException {
        if (hasNextInetSocketAddress()) {
            List list = this.inetSocketAddresses;
            int i = this.nextInetSocketAddressIndex;
            this.nextInetSocketAddressIndex = i + 1;
            InetSocketAddress result = (InetSocketAddress) list.get(i);
            resetConnectionSpecs();
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No route to ");
        stringBuilder.append(this.address.getUriHost());
        stringBuilder.append("; exhausted inet socket addresses: ");
        stringBuilder.append(this.inetSocketAddresses);
        throw new SocketException(stringBuilder.toString());
    }

    private void resetConnectionSpecs() {
        this.connectionSpecs = new ArrayList();
        List<ConnectionSpec> specs = this.address.getConnectionSpecs();
        int size = specs.size();
        for (int i = 0; i < size; i++) {
            ConnectionSpec spec = (ConnectionSpec) specs.get(i);
            if (this.request.isHttps() == spec.isTls()) {
                this.connectionSpecs.add(spec);
            }
        }
        this.nextSpecIndex = 0;
    }

    private boolean hasNextConnectionSpec() {
        return this.nextSpecIndex < this.connectionSpecs.size();
    }

    private ConnectionSpec nextConnectionSpec() throws IOException {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2;
        String stringBuilder3;
        if (this.connectionSpecs.isEmpty()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("No route to ");
            if (this.uri.getScheme() != null) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(this.uri.getScheme());
                stringBuilder2.append("://");
                stringBuilder3 = stringBuilder2.toString();
            } else {
                stringBuilder3 = "//";
            }
            stringBuilder.append(stringBuilder3);
            stringBuilder.append(this.address.getUriHost());
            stringBuilder.append("; no connection specs");
            throw new UnknownServiceException(stringBuilder.toString());
        } else if (hasNextConnectionSpec()) {
            List list = this.connectionSpecs;
            int i = this.nextSpecIndex;
            this.nextSpecIndex = i + 1;
            return (ConnectionSpec) list.get(i);
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("No route to ");
            if (this.uri.getScheme() != null) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(this.uri.getScheme());
                stringBuilder2.append("://");
                stringBuilder3 = stringBuilder2.toString();
            } else {
                stringBuilder3 = "//";
            }
            stringBuilder.append(stringBuilder3);
            stringBuilder.append(this.address.getUriHost());
            stringBuilder.append("; exhausted connection specs: ");
            stringBuilder.append(this.connectionSpecs);
            throw new SocketException(stringBuilder.toString());
        }
    }

    private boolean hasNextPostponed() {
        return this.postponedRoutes.isEmpty() ^ 1;
    }

    private Route nextPostponed() {
        return (Route) this.postponedRoutes.remove(0);
    }
}
