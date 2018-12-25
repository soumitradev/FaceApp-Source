package com.squareup.okhttp.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface Network {
    public static final Network DEFAULT = new C20341();

    /* renamed from: com.squareup.okhttp.internal.Network$1 */
    static class C20341 implements Network {
        C20341() {
        }

        public InetAddress[] resolveInetAddresses(String host) throws UnknownHostException {
            if (host != null) {
                return InetAddress.getAllByName(host);
            }
            throw new UnknownHostException("host == null");
        }
    }

    InetAddress[] resolveInetAddresses(String str) throws UnknownHostException;
}
