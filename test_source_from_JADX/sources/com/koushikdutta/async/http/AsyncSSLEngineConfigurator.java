package com.koushikdutta.async.http;

import com.koushikdutta.async.http.AsyncHttpClientMiddleware.GetSocketData;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public interface AsyncSSLEngineConfigurator {
    void configureEngine(SSLEngine sSLEngine, GetSocketData getSocketData, String str, int i);

    SSLEngine createEngine(SSLContext sSLContext, String str, int i);
}
