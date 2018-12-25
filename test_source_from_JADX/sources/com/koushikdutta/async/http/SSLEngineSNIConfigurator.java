package com.koushikdutta.async.http;

import android.os.Build.VERSION;
import com.google.android.gms.security.ProviderInstaller;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.GetSocketData;
import java.lang.reflect.Field;
import java.util.Hashtable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class SSLEngineSNIConfigurator implements AsyncSSLEngineConfigurator {
    Hashtable<String, EngineHolder> holders = new Hashtable();

    private static class EngineHolder implements AsyncSSLEngineConfigurator {
        Field peerHost;
        Field peerPort;
        boolean skipReflection;
        Field sslParameters;
        Field useSni;

        public SSLEngine createEngine(SSLContext sslContext, String peerHost, int peerPort) {
            return null;
        }

        public EngineHolder(Class engineClass) {
            try {
                this.peerHost = engineClass.getSuperclass().getDeclaredField("peerHost");
                this.peerHost.setAccessible(true);
                this.peerPort = engineClass.getSuperclass().getDeclaredField("peerPort");
                this.peerPort.setAccessible(true);
                this.sslParameters = engineClass.getDeclaredField("sslParameters");
                this.sslParameters.setAccessible(true);
                this.useSni = this.sslParameters.getType().getDeclaredField("useSni");
                this.useSni.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
        }

        public void configureEngine(SSLEngine engine, GetSocketData data, String host, int port) {
            if (this.useSni != null) {
                if (!this.skipReflection) {
                    try {
                        this.peerHost.set(engine, host);
                        this.peerPort.set(engine, Integer.valueOf(port));
                        this.useSni.set(this.sslParameters.get(engine), Boolean.valueOf(true));
                    } catch (IllegalAccessException e) {
                    }
                }
            }
        }
    }

    public SSLEngine createEngine(SSLContext sslContext, String peerHost, int peerPort) {
        boolean skipReflection;
        if (!ProviderInstaller.PROVIDER_NAME.equals(sslContext.getProvider().getName())) {
            if (VERSION.SDK_INT < 23) {
                skipReflection = false;
                if (skipReflection) {
                    return sslContext.createSSLEngine();
                }
                return sslContext.createSSLEngine(peerHost, peerPort);
            }
        }
        skipReflection = true;
        if (skipReflection) {
            return sslContext.createSSLEngine();
        }
        return sslContext.createSSLEngine(peerHost, peerPort);
    }

    EngineHolder ensureHolder(SSLEngine engine) {
        String name = engine.getClass().getCanonicalName();
        EngineHolder holder = (EngineHolder) this.holders.get(name);
        if (holder != null) {
            return holder;
        }
        holder = new EngineHolder(engine.getClass());
        this.holders.put(name, holder);
        return holder;
    }

    public void configureEngine(SSLEngine engine, GetSocketData data, String host, int port) {
        ensureHolder(engine).configureEngine(engine, data, host, port);
    }
}
