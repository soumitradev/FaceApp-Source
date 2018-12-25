package com.koushikdutta.async.http;

import android.net.Uri;
import android.util.Log;
import com.koushikdutta.async.AsyncSSLException;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import java.util.Locale;

public class AsyncHttpRequest {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DEFAULT_TIMEOUT = 30000;
    String LOGTAG;
    long executionTime;
    int logLevel;
    private AsyncHttpRequestBody mBody;
    private boolean mFollowRedirect;
    private String mMethod;
    private Headers mRawHeaders;
    int mTimeout;
    String proxyHost;
    int proxyPort;
    Uri uri;

    /* renamed from: com.koushikdutta.async.http.AsyncHttpRequest$1 */
    class C11191 implements RequestLine {
        C11191() {
        }

        public String getUri() {
            return AsyncHttpRequest.this.getUri().toString();
        }

        public ProtocolVersion getProtocolVersion() {
            return new ProtocolVersion("HTTP", 1, 1);
        }

        public String getMethod() {
            return AsyncHttpRequest.this.mMethod;
        }

        public String toString() {
            if (AsyncHttpRequest.this.proxyHost != null) {
                return String.format(Locale.ENGLISH, "%s %s HTTP/1.1", new Object[]{AsyncHttpRequest.this.mMethod, AsyncHttpRequest.this.getUri()});
            }
            String path = AsyncHttpRequest.this.getUri().getEncodedPath();
            if (path == null || path.length() == 0) {
                path = "/";
            }
            String query = AsyncHttpRequest.this.getUri().getEncodedQuery();
            if (!(query == null || query.length() == 0)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(path);
                stringBuilder.append("?");
                stringBuilder.append(query);
                path = stringBuilder.toString();
            }
            return String.format(Locale.ENGLISH, "%s %s HTTP/1.1", new Object[]{AsyncHttpRequest.this.mMethod, path});
        }
    }

    public RequestLine getRequestLine() {
        return new C11191();
    }

    protected static String getDefaultUserAgent() {
        String agent = System.getProperty("http.agent");
        if (agent != null) {
            return agent;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Java");
        stringBuilder.append(System.getProperty("java.version"));
        return stringBuilder.toString();
    }

    public String getMethod() {
        return this.mMethod;
    }

    public AsyncHttpRequest setMethod(String method) {
        if (getClass() != AsyncHttpRequest.class) {
            throw new UnsupportedOperationException("can't change method on a subclass of AsyncHttpRequest");
        }
        this.mMethod = method;
        return this;
    }

    public AsyncHttpRequest(Uri uri, String method) {
        this(uri, method, null);
    }

    public static void setDefaultHeaders(Headers ret, Uri uri) {
        if (uri != null) {
            String host = uri.getHost();
            if (uri.getPort() != -1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(host);
                stringBuilder.append(":");
                stringBuilder.append(uri.getPort());
                host = stringBuilder.toString();
            }
            if (host != null) {
                ret.set("Host", host);
            }
        }
        ret.set("User-Agent", getDefaultUserAgent());
        ret.set("Accept-Encoding", "gzip, deflate");
        ret.set("Connection", "keep-alive");
        ret.set("Accept", "*/*");
    }

    public AsyncHttpRequest(Uri uri, String method, Headers headers) {
        this.mRawHeaders = new Headers();
        this.mFollowRedirect = true;
        this.mTimeout = 30000;
        this.proxyPort = -1;
        this.mMethod = method;
        this.uri = uri;
        if (headers == null) {
            this.mRawHeaders = new Headers();
        } else {
            this.mRawHeaders = headers;
        }
        if (headers == null) {
            setDefaultHeaders(this.mRawHeaders, uri);
        }
    }

    public Uri getUri() {
        return this.uri;
    }

    public Headers getHeaders() {
        return this.mRawHeaders;
    }

    public boolean getFollowRedirect() {
        return this.mFollowRedirect;
    }

    public AsyncHttpRequest setFollowRedirect(boolean follow) {
        this.mFollowRedirect = follow;
        return this;
    }

    public void setBody(AsyncHttpRequestBody body) {
        this.mBody = body;
    }

    public AsyncHttpRequestBody getBody() {
        return this.mBody;
    }

    public void onHandshakeException(AsyncSSLException e) {
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public AsyncHttpRequest setTimeout(int timeout) {
        this.mTimeout = timeout;
        return this;
    }

    public AsyncHttpRequest setHeader(String name, String value) {
        getHeaders().set(name, value);
        return this;
    }

    public AsyncHttpRequest addHeader(String name, String value) {
        getHeaders().add(name, value);
        return this;
    }

    public void enableProxy(String host, int port) {
        this.proxyHost = host;
        this.proxyPort = port;
    }

    public void disableProxy() {
        this.proxyHost = null;
        this.proxyPort = -1;
    }

    public String getProxyHost() {
        return this.proxyHost;
    }

    public int getProxyPort() {
        return this.proxyPort;
    }

    public String toString() {
        if (this.mRawHeaders == null) {
            return super.toString();
        }
        return this.mRawHeaders.toPrefixString(this.uri.toString());
    }

    public void setLogging(String tag, int level) {
        this.LOGTAG = tag;
        this.logLevel = level;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public String getLogTag() {
        return this.LOGTAG;
    }

    private String getLogMessage(String message) {
        long elapsed = 0;
        if (this.executionTime != 0) {
            elapsed = System.currentTimeMillis() - this.executionTime;
        }
        return String.format(Locale.ENGLISH, "(%d ms) %s: %s", new Object[]{Long.valueOf(elapsed), getUri(), message});
    }

    public void logi(String message) {
        if (this.LOGTAG != null && this.logLevel <= 4) {
            Log.i(this.LOGTAG, getLogMessage(message));
        }
    }

    public void logv(String message) {
        if (this.LOGTAG != null && this.logLevel <= 2) {
            Log.v(this.LOGTAG, getLogMessage(message));
        }
    }

    public void logw(String message) {
        if (this.LOGTAG != null && this.logLevel <= 5) {
            Log.w(this.LOGTAG, getLogMessage(message));
        }
    }

    public void logd(String message) {
        if (this.LOGTAG != null && this.logLevel <= 3) {
            Log.d(this.LOGTAG, getLogMessage(message));
        }
    }

    public void logd(String message, Exception e) {
        if (this.LOGTAG != null && this.logLevel <= 3) {
            Log.d(this.LOGTAG, getLogMessage(message));
            Log.d(this.LOGTAG, e.getMessage(), e);
        }
    }

    public void loge(String message) {
        if (this.LOGTAG != null && this.logLevel <= 6) {
            Log.e(this.LOGTAG, getLogMessage(message));
        }
    }

    public void loge(String message, Exception e) {
        if (this.LOGTAG != null && this.logLevel <= 6) {
            Log.e(this.LOGTAG, getLogMessage(message));
            Log.e(this.LOGTAG, e.getMessage(), e);
        }
    }
}
