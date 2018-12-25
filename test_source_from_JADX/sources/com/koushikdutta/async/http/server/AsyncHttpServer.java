package com.koushikdutta.async.http.server;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;
import com.badlogic.gdx.net.HttpStatus;
import com.facebook.internal.AnalyticsEvents;
import com.koushikdutta.async.AsyncSSLSocket;
import com.koushikdutta.async.AsyncSSLSocketWrapper;
import com.koushikdutta.async.AsyncSSLSocketWrapper.HandshakeCallback;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncServerSocket;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.CompletedCallback.NullCompletedCallback;
import com.koushikdutta.async.callback.DataCallback.NullDataCallback;
import com.koushikdutta.async.callback.ListenCallback;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.HttpUtil;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.WebSocketImpl;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.http.body.StringBody;
import com.koushikdutta.async.util.StreamUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLContext;
import name.antonsmirnov.firmata.FormatHelper;

@TargetApi(5)
public class AsyncHttpServer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static Hashtable<Integer, String> mCodes = new Hashtable();
    static Hashtable<String, String> mContentTypes = new Hashtable();
    final Hashtable<String, ArrayList<Pair>> mActions = new Hashtable();
    CompletedCallback mCompletedCallback;
    ListenCallback mListenCallback = new C13301();
    ArrayList<AsyncServerSocket> mListeners = new ArrayList();

    private static class Pair {
        HttpServerRequestCallback callback;
        Pattern regex;

        private Pair() {
        }
    }

    public interface WebSocketRequestCallback {
        void onConnected(WebSocket webSocket, AsyncHttpServerRequest asyncHttpServerRequest);
    }

    /* renamed from: com.koushikdutta.async.http.server.AsyncHttpServer$1 */
    class C13301 implements ListenCallback {
        C13301() {
        }

        public void onAccepted(final AsyncSocket socket) {
            new AsyncHttpServerRequestImpl() {
                String fullPath;
                boolean hasContinued;
                HttpServerRequestCallback match;
                String path;
                boolean requestComplete;
                AsyncHttpServerResponseImpl res;
                boolean responseComplete;

                /* renamed from: com.koushikdutta.async.http.server.AsyncHttpServer$1$1$1 */
                class C11651 implements CompletedCallback {
                    C11651() {
                    }

                    public void onCompleted(Exception ex) {
                        C13561.this.resume();
                        if (ex != null) {
                            C13561.this.report(ex);
                            return;
                        }
                        C13561.this.hasContinued = true;
                        C13561.this.onHeadersReceived();
                    }
                }

                /* renamed from: com.koushikdutta.async.http.server.AsyncHttpServer$1$1$3 */
                class C13293 extends NullDataCallback {
                    C13293() {
                    }

                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                        super.onDataAvailable(emitter, bb);
                        C13561.this.mSocket.close();
                    }
                }

                protected AsyncHttpRequestBody onUnknownBody(Headers headers) {
                    return AsyncHttpServer.this.onUnknownBody(headers);
                }

                protected void onHeadersReceived() {
                    Headers headers = getHeaders();
                    if (this.hasContinued || !"100-continue".equals(headers.get("Expect"))) {
                        String[] parts = getStatusLine().split(FormatHelper.SPACE);
                        this.fullPath = parts[1];
                        this.path = this.fullPath.split("\\?")[0];
                        this.method = parts[0];
                        synchronized (AsyncHttpServer.this.mActions) {
                            ArrayList<Pair> pairs = (ArrayList) AsyncHttpServer.this.mActions.get(this.method);
                            if (pairs != null) {
                                Iterator it = pairs.iterator();
                                while (it.hasNext()) {
                                    Pair p = (Pair) it.next();
                                    Matcher m = p.regex.matcher(this.path);
                                    if (m.matches()) {
                                        this.mMatcher = m;
                                        this.match = p.callback;
                                        break;
                                    }
                                }
                            }
                        }
                        this.res = new AsyncHttpServerResponseImpl(socket, this) {
                            protected void report(Exception e) {
                                super.report(e);
                                if (e != null) {
                                    socket.setDataCallback(new NullDataCallback());
                                    socket.setEndCallback(new NullCompletedCallback());
                                    socket.close();
                                }
                            }

                            protected void onEnd() {
                                super.onEnd();
                                this.mSocket.setEndCallback(null);
                                C13561.this.responseComplete = true;
                                C13561.this.handleOnCompleted();
                            }
                        };
                        boolean handled = AsyncHttpServer.this.onRequest(this, this.res);
                        if (this.match != null || handled) {
                            if (!getBody().readFullyOnRequest()) {
                                AsyncHttpServer.this.onRequest(this.match, this, this.res);
                            } else if (this.requestComplete) {
                                AsyncHttpServer.this.onRequest(this.match, this, this.res);
                            }
                            return;
                        }
                        this.res.code(HttpStatus.SC_NOT_FOUND);
                        this.res.end();
                        return;
                    }
                    pause();
                    Util.writeAll(this.mSocket, "HTTP/1.1 100 Continue\r\n\r\n".getBytes(), new C11651());
                }

                public void onCompleted(Exception e) {
                    if (this.res.code() != 101) {
                        this.requestComplete = true;
                        super.onCompleted(e);
                        this.mSocket.setDataCallback(new C13293());
                        handleOnCompleted();
                        if (getBody().readFullyOnRequest()) {
                            AsyncHttpServer.this.onRequest(this.match, this, this.res);
                        }
                    }
                }

                private void handleOnCompleted() {
                    if (!this.requestComplete || !this.responseComplete) {
                        return;
                    }
                    if (HttpUtil.isKeepAlive(Protocol.HTTP_1_1, getHeaders())) {
                        C13301.this.onAccepted(socket);
                    } else {
                        socket.close();
                    }
                }

                public String getPath() {
                    return this.path;
                }

                public Multimap getQuery() {
                    String[] parts = this.fullPath.split("\\?", 2);
                    if (parts.length < 2) {
                        return new Multimap();
                    }
                    return Multimap.parseQuery(parts[1]);
                }
            }.setSocket(socket);
            socket.resume();
        }

        public void onCompleted(Exception error) {
            AsyncHttpServer.this.report(error);
        }

        public void onListening(AsyncServerSocket socket) {
            AsyncHttpServer.this.mListeners.add(socket);
        }
    }

    static {
        mCodes.put(Integer.valueOf(200), "OK");
        mCodes.put(Integer.valueOf(HttpStatus.SC_ACCEPTED), "Accepted");
        mCodes.put(Integer.valueOf(HttpStatus.SC_PARTIAL_CONTENT), "Partial Content");
        mCodes.put(Integer.valueOf(101), "Switching Protocols");
        mCodes.put(Integer.valueOf(HttpStatus.SC_MOVED_PERMANENTLY), "Moved Permanently");
        mCodes.put(Integer.valueOf(HttpStatus.SC_MOVED_TEMPORARILY), "Found");
        mCodes.put(Integer.valueOf(HttpStatus.SC_NOT_FOUND), "Not Found");
    }

    public AsyncHttpServer() {
        mContentTypes.put("js", "application/javascript");
        mContentTypes.put("json", "application/json");
        mContentTypes.put("png", "image/png");
        mContentTypes.put("jpg", "image/jpeg");
        mContentTypes.put("html", "text/html");
        mContentTypes.put("css", "text/css");
        mContentTypes.put("mp4", "video/mp4");
        mContentTypes.put("mov", "video/quicktime");
        mContentTypes.put("wmv", "video/x-ms-wmv");
    }

    public void stop() {
        if (this.mListeners != null) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((AsyncServerSocket) it.next()).stop();
            }
        }
    }

    protected boolean onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        return false;
    }

    protected void onRequest(HttpServerRequestCallback callback, AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        if (callback != null) {
            callback.onRequest(request, response);
        }
    }

    protected AsyncHttpRequestBody onUnknownBody(Headers headers) {
        return new UnknownRequestBody(headers.get("Content-Type"));
    }

    public AsyncServerSocket listen(AsyncServer server, int port) {
        return server.listen(null, port, this.mListenCallback);
    }

    private void report(Exception ex) {
        if (this.mCompletedCallback != null) {
            this.mCompletedCallback.onCompleted(ex);
        }
    }

    public AsyncServerSocket listen(int port) {
        return listen(AsyncServer.getDefault(), port);
    }

    public void listenSecure(final int port, final SSLContext sslContext) {
        AsyncServer.getDefault().listen(null, port, new ListenCallback() {

            /* renamed from: com.koushikdutta.async.http.server.AsyncHttpServer$2$1 */
            class C11661 implements HandshakeCallback {
                C11661() {
                }

                public void onHandshakeCompleted(Exception e, AsyncSSLSocket socket) {
                    if (socket != null) {
                        AsyncHttpServer.this.mListenCallback.onAccepted(socket);
                    }
                }
            }

            public void onAccepted(AsyncSocket socket) {
                AsyncSSLSocketWrapper.handshake(socket, null, port, sslContext.createSSLEngine(), null, null, false, new C11661());
            }

            public void onListening(AsyncServerSocket socket) {
                AsyncHttpServer.this.mListenCallback.onListening(socket);
            }

            public void onCompleted(Exception ex) {
                AsyncHttpServer.this.mListenCallback.onCompleted(ex);
            }
        });
    }

    public ListenCallback getListenCallback() {
        return this.mListenCallback;
    }

    public void setErrorCallback(CompletedCallback callback) {
        this.mCompletedCallback = callback;
    }

    public CompletedCallback getErrorCallback() {
        return this.mCompletedCallback;
    }

    public void removeAction(String action, String regex) {
        synchronized (this.mActions) {
            ArrayList<Pair> pairs = (ArrayList) this.mActions.get(action);
            if (pairs == null) {
                return;
            }
            for (int i = 0; i < pairs.size(); i++) {
                if (regex.equals(((Pair) pairs.get(i)).regex.toString())) {
                    pairs.remove(i);
                    return;
                }
            }
        }
    }

    public void addAction(String action, String regex, HttpServerRequestCallback callback) {
        Pair p = new Pair();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("^");
        stringBuilder.append(regex);
        p.regex = Pattern.compile(stringBuilder.toString());
        p.callback = callback;
        synchronized (this.mActions) {
            ArrayList<Pair> pairs = (ArrayList) this.mActions.get(action);
            if (pairs == null) {
                pairs = new ArrayList();
                this.mActions.put(action, pairs);
            }
            pairs.add(p);
        }
    }

    public void websocket(String regex, WebSocketRequestCallback callback) {
        websocket(regex, null, callback);
    }

    public void websocket(String regex, final String protocol, final WebSocketRequestCallback callback) {
        get(regex, new HttpServerRequestCallback() {
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                boolean hasUpgrade = false;
                String connection = request.getHeaders().get("Connection");
                if (connection != null) {
                    for (String c : connection.split(",")) {
                        if ("Upgrade".equalsIgnoreCase(c.trim())) {
                            hasUpgrade = true;
                            break;
                        }
                    }
                }
                if ("websocket".equalsIgnoreCase(request.getHeaders().get("Upgrade"))) {
                    if (hasUpgrade) {
                        if (TextUtils.equals(protocol, request.getHeaders().get("Sec-WebSocket-Protocol"))) {
                            callback.onConnected(new WebSocketImpl(request, response), request);
                            return;
                        }
                        response.code(HttpStatus.SC_NOT_FOUND);
                        response.end();
                        return;
                    }
                }
                response.code(HttpStatus.SC_NOT_FOUND);
                response.end();
            }
        });
    }

    public void get(String regex, HttpServerRequestCallback callback) {
        addAction("GET", regex, callback);
    }

    public void post(String regex, HttpServerRequestCallback callback) {
        addAction("POST", regex, callback);
    }

    public static android.util.Pair<Integer, InputStream> getAssetStream(Context context, String asset) {
        try {
            InputStream is = context.getAssets().open(asset);
            return new android.util.Pair(Integer.valueOf(is.available()), is);
        } catch (IOException e) {
            return null;
        }
    }

    public static String getContentType(String path) {
        String type = tryGetContentType(path);
        if (type != null) {
            return type;
        }
        return StringBody.CONTENT_TYPE;
    }

    public static String tryGetContentType(String path) {
        int index = path.lastIndexOf(".");
        if (index != -1) {
            String ct = (String) mContentTypes.get(path.substring(index + 1));
            if (ct != null) {
                return ct;
            }
        }
        return null;
    }

    public void directory(Context context, String regex, final String assetPath) {
        final Context _context = context.getApplicationContext();
        addAction("GET", regex, new HttpServerRequestCallback() {
            public void onRequest(AsyncHttpServerRequest request, final AsyncHttpServerResponse response) {
                String path = request.getMatcher().replaceAll("");
                android.util.Pair<Integer, InputStream> pair = _context;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(assetPath);
                stringBuilder.append(path);
                pair = AsyncHttpServer.getAssetStream(pair, stringBuilder.toString());
                if (pair != null) {
                    if (pair.second != null) {
                        final InputStream is = pair.second;
                        response.getHeaders().set("Content-Length", String.valueOf(pair.first));
                        response.code(200);
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(assetPath);
                        stringBuilder2.append(path);
                        response.getHeaders().add("Content-Type", AsyncHttpServer.getContentType(stringBuilder2.toString()));
                        Util.pump(is, (DataSink) response, new CompletedCallback() {
                            public void onCompleted(Exception ex) {
                                response.end();
                                StreamUtility.closeQuietly(is);
                            }
                        });
                        return;
                    }
                }
                response.code(HttpStatus.SC_NOT_FOUND);
                response.end();
            }
        });
        addAction("HEAD", regex, new HttpServerRequestCallback() {
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                String path = request.getMatcher().replaceAll("");
                android.util.Pair<Integer, InputStream> pair = _context;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(assetPath);
                stringBuilder.append(path);
                pair = AsyncHttpServer.getAssetStream(pair, stringBuilder.toString());
                if (pair != null) {
                    if (pair.second != null) {
                        StreamUtility.closeQuietly(pair.second);
                        response.getHeaders().set("Content-Length", String.valueOf(pair.first));
                        response.code(200);
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(assetPath);
                        stringBuilder2.append(path);
                        response.getHeaders().add("Content-Type", AsyncHttpServer.getContentType(stringBuilder2.toString()));
                        response.writeHead();
                        response.end();
                        return;
                    }
                }
                response.code(HttpStatus.SC_NOT_FOUND);
                response.end();
            }
        });
    }

    public void directory(String regex, File directory) {
        directory(regex, directory, false);
    }

    public void directory(String regex, final File directory, final boolean list) {
        addAction("GET", regex, new HttpServerRequestCallback() {

            /* renamed from: com.koushikdutta.async.http.server.AsyncHttpServer$6$1 */
            class C06591 implements Comparator<File> {
                C06591() {
                }

                public int compare(File lhs, File rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            }

            public void onRequest(AsyncHttpServerRequest request, final AsyncHttpServerResponse response) {
                File file = new File(directory, request.getMatcher().replaceAll(""));
                if (file.isDirectory() && list) {
                    ArrayList<File> dirs = new ArrayList();
                    ArrayList<File> files = new ArrayList();
                    for (File f : file.listFiles()) {
                        if (f.isDirectory()) {
                            dirs.add(f);
                        } else {
                            files.add(f);
                        }
                    }
                    Comparator<File> c = new C06591();
                    Collections.sort(dirs, c);
                    Collections.sort(files, c);
                    files.addAll(0, dirs);
                } else if (file.isFile()) {
                    try {
                        InputStream is = new FileInputStream(file);
                        response.code(200);
                        Util.pump(is, (DataSink) response, new CompletedCallback() {
                            public void onCompleted(Exception ex) {
                                response.end();
                            }
                        });
                    } catch (FileNotFoundException e) {
                        response.code(HttpStatus.SC_NOT_FOUND);
                        response.end();
                    }
                } else {
                    response.code(HttpStatus.SC_NOT_FOUND);
                    response.end();
                }
            }
        });
    }

    public static String getResponseCodeDescription(int code) {
        String d = (String) mCodes.get(Integer.valueOf(code));
        if (d == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return d;
    }
}
