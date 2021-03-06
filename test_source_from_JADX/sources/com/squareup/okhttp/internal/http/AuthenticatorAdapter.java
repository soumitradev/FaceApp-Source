package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.Authenticator.RequestorType;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.List;

public final class AuthenticatorAdapter implements Authenticator {
    public static final Authenticator INSTANCE = new AuthenticatorAdapter();

    public Request authenticate(Proxy proxy, Response response) throws IOException {
        List<Challenge> challenges = response.challenges();
        Request request = response.request();
        URL url = request.url();
        int size = challenges.size();
        int i = 0;
        while (true) {
            int size2 = size;
            AuthenticatorAdapter authenticatorAdapter;
            Proxy proxy2;
            if (i < size2) {
                Challenge challenge = (Challenge) challenges.get(i);
                if ("Basic".equalsIgnoreCase(challenge.getScheme())) {
                    PasswordAuthentication auth = java.net.Authenticator.requestPasswordAuthentication(url.getHost(), getConnectToInetAddress(proxy, url), url.getPort(), url.getProtocol(), challenge.getRealm(), challenge.getScheme(), url, RequestorType.SERVER);
                    if (auth != null) {
                        return request.newBuilder().header("Authorization", Credentials.basic(auth.getUserName(), new String(auth.getPassword()))).build();
                    }
                } else {
                    authenticatorAdapter = this;
                    proxy2 = proxy;
                }
                i++;
                size = size2;
            } else {
                authenticatorAdapter = this;
                proxy2 = proxy;
                return null;
            }
        }
    }

    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
        List<Challenge> challenges = response.challenges();
        Request request = response.request();
        URL url = request.url();
        int size = challenges.size();
        int i = 0;
        while (true) {
            int size2 = size;
            AuthenticatorAdapter authenticatorAdapter;
            if (i < size2) {
                Challenge challenge = (Challenge) challenges.get(i);
                if ("Basic".equalsIgnoreCase(challenge.getScheme())) {
                    InetSocketAddress proxyAddress = (InetSocketAddress) proxy.address();
                    PasswordAuthentication auth = java.net.Authenticator.requestPasswordAuthentication(proxyAddress.getHostName(), getConnectToInetAddress(proxy, url), proxyAddress.getPort(), url.getProtocol(), challenge.getRealm(), challenge.getScheme(), url, RequestorType.PROXY);
                    if (auth != null) {
                        return request.newBuilder().header("Proxy-Authorization", Credentials.basic(auth.getUserName(), new String(auth.getPassword()))).build();
                    }
                } else {
                    authenticatorAdapter = this;
                }
                i++;
                size = size2;
            } else {
                authenticatorAdapter = this;
                return null;
            }
        }
    }

    private InetAddress getConnectToInetAddress(Proxy proxy, URL url) throws IOException {
        if (proxy == null || proxy.type() == Type.DIRECT) {
            return InetAddress.getByName(url.getHost());
        }
        return ((InetSocketAddress) proxy.address()).getAddress();
    }
}
