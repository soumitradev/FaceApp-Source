package com.koushikdutta.async.http;

import android.net.Uri;
import android.text.TextUtils;
import com.koushikdutta.async.AsyncSSLSocket;
import com.koushikdutta.async.AsyncSSLSocketWrapper;
import com.koushikdutta.async.AsyncSSLSocketWrapper.HandshakeCallback;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.LineEmitter;
import com.koushikdutta.async.LineEmitter.StringCallback;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.GetSocketData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;

public class AsyncSSLSocketMiddleware extends AsyncSocketMiddleware {
    protected List<AsyncSSLEngineConfigurator> engineConfigurators = new ArrayList();
    protected HostnameVerifier hostnameVerifier;
    protected SSLContext sslContext;
    protected TrustManager[] trustManagers;

    public AsyncSSLSocketMiddleware(AsyncHttpClient client) {
        super(client, "https", 443);
    }

    public void setSSLContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public SSLContext getSSLContext() {
        return this.sslContext != null ? this.sslContext : AsyncSSLSocketWrapper.getDefaultSSLContext();
    }

    public void setTrustManagers(TrustManager[] trustManagers) {
        this.trustManagers = trustManagers;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public void addEngineConfigurator(AsyncSSLEngineConfigurator engineConfigurator) {
        this.engineConfigurators.add(engineConfigurator);
    }

    public void clearEngineConfigurators() {
        this.engineConfigurators.clear();
    }

    protected SSLEngine createConfiguredSSLEngine(GetSocketData data, String host, int port) {
        SSLContext sslContext = getSSLContext();
        SSLEngine sslEngine = null;
        for (AsyncSSLEngineConfigurator configurator : this.engineConfigurators) {
            sslEngine = configurator.createEngine(sslContext, host, port);
            if (sslEngine != null) {
                break;
            }
        }
        for (AsyncSSLEngineConfigurator configurator2 : this.engineConfigurators) {
            configurator2.configureEngine(sslEngine, data, host, port);
        }
        return sslEngine;
    }

    protected HandshakeCallback createHandshakeCallback(GetSocketData data, final ConnectCallback callback) {
        return new HandshakeCallback() {
            public void onHandshakeCompleted(Exception e, AsyncSSLSocket socket) {
                callback.onConnectCompleted(e, socket);
            }
        };
    }

    protected void tryHandshake(AsyncSocket socket, GetSocketData data, Uri uri, int port, ConnectCallback callback) {
        AsyncSSLSocketWrapper.handshake(socket, uri.getHost(), port, createConfiguredSSLEngine(data, uri.getHost(), port), this.trustManagers, this.hostnameVerifier, true, createHandshakeCallback(data, callback));
    }

    protected ConnectCallback wrapCallback(GetSocketData data, Uri uri, int port, boolean proxied, ConnectCallback callback) {
        final ConnectCallback connectCallback = callback;
        final boolean z = proxied;
        final GetSocketData getSocketData = data;
        final Uri uri2 = uri;
        final int i = port;
        return new ConnectCallback() {
            public void onConnectCompleted(Exception ex, final AsyncSocket socket) {
                if (ex != null) {
                    connectCallback.onConnectCompleted(ex, socket);
                } else if (z) {
                    String connect = String.format(Locale.ENGLISH, "CONNECT %s:%s HTTP/1.1\r\nHost: %s\r\n\r\n", new Object[]{uri2.getHost(), Integer.valueOf(i), uri2.getHost()});
                    AsyncHttpRequest asyncHttpRequest = getSocketData.request;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Proxying: ");
                    stringBuilder.append(connect);
                    asyncHttpRequest.logv(stringBuilder.toString());
                    Util.writeAll((DataSink) socket, connect.getBytes(), new CompletedCallback() {

                        /* renamed from: com.koushikdutta.async.http.AsyncSSLSocketMiddleware$2$1$1 */
                        class C11231 implements StringCallback {
                            String statusLine;

                            C11231() {
                            }

                            public void onStringAvailable(String s) {
                                getSocketData.request.logv(s);
                                if (this.statusLine == null) {
                                    this.statusLine = s.trim();
                                    if (!this.statusLine.matches("HTTP/1.\\d 2\\d\\d .*")) {
                                        socket.setDataCallback(null);
                                        socket.setEndCallback(null);
                                        ConnectCallback connectCallback = connectCallback;
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("non 2xx status line: ");
                                        stringBuilder.append(this.statusLine);
                                        connectCallback.onConnectCompleted(new IOException(stringBuilder.toString()), socket);
                                    }
                                } else if (TextUtils.isEmpty(s.trim())) {
                                    socket.setDataCallback(null);
                                    socket.setEndCallback(null);
                                    AsyncSSLSocketMiddleware.this.tryHandshake(socket, getSocketData, uri2, i, connectCallback);
                                }
                            }
                        }

                        /* renamed from: com.koushikdutta.async.http.AsyncSSLSocketMiddleware$2$1$2 */
                        class C11242 implements CompletedCallback {
                            C11242() {
                            }

                            public void onCompleted(Exception ex) {
                                if (!socket.isOpen() && ex == null) {
                                    ex = new IOException("socket closed before proxy connect response");
                                }
                                connectCallback.onConnectCompleted(ex, socket);
                            }
                        }

                        public void onCompleted(Exception ex) {
                            if (ex != null) {
                                connectCallback.onConnectCompleted(ex, socket);
                                return;
                            }
                            LineEmitter liner = new LineEmitter();
                            liner.setLineCallback(new C11231());
                            socket.setDataCallback(liner);
                            socket.setEndCallback(new C11242());
                        }
                    });
                } else {
                    AsyncSSLSocketMiddleware.this.tryHandshake(socket, getSocketData, uri2, i, connectCallback);
                }
            }
        };
    }
}
