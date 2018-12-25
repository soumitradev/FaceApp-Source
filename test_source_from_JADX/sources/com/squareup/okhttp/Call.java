package com.squareup.okhttp;

import android.support.v4.app.NotificationCompat;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Call {
    volatile boolean canceled;
    private final OkHttpClient client;
    HttpEngine engine;
    private boolean executed;
    Request originalRequest;

    class ApplicationInterceptorChain implements Chain {
        private final boolean forWebSocket;
        private final int index;
        private final Request request;

        ApplicationInterceptorChain(int index, Request request, boolean forWebSocket) {
            this.index = index;
            this.request = request;
            this.forWebSocket = forWebSocket;
        }

        public Connection connection() {
            return null;
        }

        public Request request() {
            return this.request;
        }

        public Response proceed(Request request) throws IOException {
            if (this.index >= Call.this.client.interceptors().size()) {
                return Call.this.getResponse(request, this.forWebSocket);
            }
            return ((Interceptor) Call.this.client.interceptors().get(this.index)).intercept(new ApplicationInterceptorChain(this.index + 1, request, this.forWebSocket));
        }
    }

    final class AsyncCall extends NamedRunnable {
        private final boolean forWebSocket;
        private final Callback responseCallback;

        private AsyncCall(Callback responseCallback, boolean forWebSocket) {
            super("OkHttp %s", this$0.originalRequest.urlString());
            this.responseCallback = responseCallback;
            this.forWebSocket = forWebSocket;
        }

        String host() {
            return Call.this.originalRequest.url().getHost();
        }

        Request request() {
            return Call.this.originalRequest;
        }

        Object tag() {
            return Call.this.originalRequest.tag();
        }

        void cancel() {
            Call.this.cancel();
        }

        Call get() {
            return Call.this;
        }

        protected void execute() {
            try {
                Response response = Call.this.getResponseWithInterceptorChain(this.forWebSocket);
                if (Call.this.canceled) {
                    this.responseCallback.onFailure(Call.this.originalRequest, new IOException("Canceled"));
                } else {
                    this.responseCallback.onResponse(response);
                }
            } catch (IOException e) {
                if (false) {
                    Logger logger = Internal.logger;
                    Level level = Level.INFO;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Callback failure for ");
                    stringBuilder.append(Call.this.toLoggableString());
                    logger.log(level, stringBuilder.toString(), e);
                } else {
                    this.responseCallback.onFailure(Call.this.engine.getRequest(), e);
                }
            } catch (Throwable th) {
                Call.this.client.getDispatcher().finished(this);
            }
            Call.this.client.getDispatcher().finished(this);
        }
    }

    protected Call(OkHttpClient client, Request originalRequest) {
        this.client = client.copyWithDefaults();
        this.originalRequest = originalRequest;
    }

    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        try {
            this.client.getDispatcher().executed(this);
            Response result = getResponseWithInterceptorChain(null);
            if (result != null) {
                return result;
            }
            throw new IOException("Canceled");
        } finally {
            this.client.getDispatcher().finished(this);
        }
    }

    Object tag() {
        return this.originalRequest.tag();
    }

    public void enqueue(Callback responseCallback) {
        enqueue(responseCallback, false);
    }

    void enqueue(Callback responseCallback, boolean forWebSocket) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        this.client.getDispatcher().enqueue(new AsyncCall(responseCallback, forWebSocket));
    }

    public void cancel() {
        this.canceled = true;
        if (this.engine != null) {
            this.engine.disconnect();
        }
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    private String toLoggableString() {
        String string = this.canceled ? "canceled call" : NotificationCompat.CATEGORY_CALL;
        try {
            String redactedUrl = new URL(this.originalRequest.url(), "/...").toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" to ");
            stringBuilder.append(redactedUrl);
            return stringBuilder.toString();
        } catch (MalformedURLException e) {
            return string;
        }
    }

    private Response getResponseWithInterceptorChain(boolean forWebSocket) throws IOException {
        return new ApplicationInterceptorChain(0, this.originalRequest, forWebSocket).proceed(this.originalRequest);
    }

    Response getResponse(Request request, boolean forWebSocket) throws IOException {
        Request request2;
        Call call = this;
        RequestBody body = request.body();
        if (body != null) {
            request2 = request.newBuilder();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                request2.header("Content-Type", contentType.toString());
            }
            long contentLength = body.contentLength();
            if (contentLength != -1) {
                request2.header("Content-Length", Long.toString(contentLength));
                request2.removeHeader("Transfer-Encoding");
            } else {
                request2.header("Transfer-Encoding", "chunked");
                request2.removeHeader("Content-Length");
            }
            request2 = request2.build();
        } else {
            request2 = request;
        }
        call.engine = new HttpEngine(call.client, request2, false, false, forWebSocket, null, null, null, null);
        int followUpCount = 0;
        while (!call.canceled) {
            try {
                call.engine.sendRequest();
                call.engine.readResponse();
                Response response = call.engine.getResponse();
                Request followUp = call.engine.followUpRequest();
                if (followUp == null) {
                    if (!forWebSocket) {
                        call.engine.releaseConnection();
                    }
                    return response;
                }
                followUpCount++;
                if (followUpCount > 20) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Too many follow-up requests: ");
                    stringBuilder.append(followUpCount);
                    throw new ProtocolException(stringBuilder.toString());
                }
                if (!call.engine.sameConnection(followUp.url())) {
                    call.engine.releaseConnection();
                }
                Request request3 = followUp;
                call.engine = new HttpEngine(call.client, request3, false, false, forWebSocket, call.engine.close(), null, null, response);
                request2 = request3;
            } catch (IOException e) {
                IOException e2 = e;
                HttpEngine retryEngine = call.engine.recover(e2, null);
                if (retryEngine != null) {
                    call.engine = retryEngine;
                } else {
                    throw e2;
                }
            }
        }
        call.engine.releaseConnection();
        return null;
    }
}
