package com.koushikdutta.async.http.server;

import android.net.Uri;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.callback.HttpConnectCallback;

public class AsyncProxyServer extends AsyncHttpServer {
    AsyncHttpClient proxyClient;

    public AsyncProxyServer(AsyncServer server) {
        this.proxyClient = new AsyncHttpClient(server);
    }

    protected void onRequest(HttpServerRequestCallback callback, AsyncHttpServerRequest request, final AsyncHttpServerResponse response) {
        super.onRequest(callback, request, response);
        if (callback == null) {
            Uri uri;
            try {
                uri = Uri.parse(request.getPath());
                if (uri.getScheme() == null) {
                    throw new Exception("no host or full uri provided");
                }
                this.proxyClient.execute(new AsyncHttpRequest(uri, request.getMethod(), request.getHeaders()), new HttpConnectCallback() {
                    public void onConnectCompleted(Exception ex, AsyncHttpResponse remoteResponse) {
                        if (ex != null) {
                            response.code(500);
                            response.send(ex.getMessage());
                            return;
                        }
                        response.proxy(remoteResponse);
                    }
                });
            } catch (Exception e) {
                try {
                    String host = request.getHeaders().get("Host");
                    int port = 80;
                    if (host != null) {
                        String[] splits = host.split(":", 2);
                        if (splits.length == 2) {
                            host = splits[0];
                            port = Integer.parseInt(splits[1]);
                        }
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("http://");
                    stringBuilder.append(host);
                    stringBuilder.append(":");
                    stringBuilder.append(port);
                    stringBuilder.append(request.getPath());
                    uri = Uri.parse(stringBuilder.toString());
                } catch (Exception e2) {
                    response.code(500);
                    response.send(e2.getMessage());
                }
            }
        }
    }

    protected boolean onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        return true;
    }
}
