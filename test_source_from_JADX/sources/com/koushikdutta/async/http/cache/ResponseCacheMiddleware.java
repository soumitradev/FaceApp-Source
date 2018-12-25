package com.koushikdutta.async.http.cache;

import android.net.Uri;
import android.util.Base64;
import com.koushikdutta.async.AsyncSSLSocket;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.SimpleCancellable;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.GetSocketData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnBodyDataOnRequestSentData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnResponseCompleteDataOnRequestSentData;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.SimpleMiddleware;
import com.koushikdutta.async.util.Allocator;
import com.koushikdutta.async.util.Charsets;
import com.koushikdutta.async.util.FileCache;
import com.koushikdutta.async.util.StreamUtility;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.CacheResponse;
import java.nio.ByteBuffer;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.SSLEngine;

public class ResponseCacheMiddleware extends SimpleMiddleware {
    public static final String CACHE = "cache";
    public static final String CONDITIONAL_CACHE = "conditional-cache";
    public static final int ENTRY_BODY = 1;
    public static final int ENTRY_COUNT = 2;
    public static final int ENTRY_METADATA = 0;
    private static final String LOGTAG = "AsyncHttpCache";
    public static final String SERVED_FROM = "X-Served-From";
    private FileCache cache;
    private int cacheHitCount;
    private int cacheStoreCount;
    private boolean caching = true;
    private int conditionalCacheHitCount;
    private int networkCount;
    private AsyncServer server;
    private int writeAbortCount;
    private int writeSuccessCount;

    public static class CacheData {
        ResponseHeaders cachedResponseHeaders;
        EntryCacheResponse candidate;
        long contentLength;
        FileInputStream[] snapshot;
    }

    private static final class Entry {
        private final String cipherSuite;
        private final Certificate[] localCertificates;
        private final Certificate[] peerCertificates;
        private final String requestMethod;
        private final RawHeaders responseHeaders;
        private final String uri;
        private final RawHeaders varyHeaders;

        public Entry(InputStream in) throws IOException {
            try {
                int i;
                StrictLineReader reader = new StrictLineReader(in, Charsets.US_ASCII);
                this.uri = reader.readLine();
                this.requestMethod = reader.readLine();
                this.varyHeaders = new RawHeaders();
                int varyRequestHeaderLineCount = reader.readInt();
                for (i = 0; i < varyRequestHeaderLineCount; i++) {
                    this.varyHeaders.addLine(reader.readLine());
                }
                this.responseHeaders = new RawHeaders();
                this.responseHeaders.setStatusLine(reader.readLine());
                i = reader.readInt();
                for (int i2 = 0; i2 < i; i2++) {
                    this.responseHeaders.addLine(reader.readLine());
                }
                this.cipherSuite = null;
                this.peerCertificates = null;
                this.localCertificates = null;
                StreamUtility.closeQuietly(reader, in);
            } catch (Throwable th) {
                StreamUtility.closeQuietly(null, in);
            }
        }

        public Entry(Uri uri, RawHeaders varyHeaders, AsyncHttpRequest request, RawHeaders responseHeaders) {
            this.uri = uri.toString();
            this.varyHeaders = varyHeaders;
            this.requestMethod = request.getMethod();
            this.responseHeaders = responseHeaders;
            this.cipherSuite = null;
            this.peerCertificates = null;
            this.localCertificates = null;
        }

        public void writeTo(EntryEditor editor) throws IOException {
            int i = 0;
            Writer writer = new BufferedWriter(new OutputStreamWriter(editor.newOutputStream(0), Charsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.uri);
            stringBuilder.append('\n');
            writer.write(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.requestMethod);
            stringBuilder.append('\n');
            writer.write(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toString(this.varyHeaders.length()));
            stringBuilder.append('\n');
            writer.write(stringBuilder.toString());
            for (int i2 = 0; i2 < this.varyHeaders.length(); i2++) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(this.varyHeaders.getFieldName(i2));
                stringBuilder2.append(": ");
                stringBuilder2.append(this.varyHeaders.getValue(i2));
                stringBuilder2.append('\n');
                writer.write(stringBuilder2.toString());
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.responseHeaders.getStatusLine());
            stringBuilder.append('\n');
            writer.write(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toString(this.responseHeaders.length()));
            stringBuilder.append('\n');
            writer.write(stringBuilder.toString());
            while (i < this.responseHeaders.length()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.responseHeaders.getFieldName(i));
                stringBuilder.append(": ");
                stringBuilder.append(this.responseHeaders.getValue(i));
                stringBuilder.append('\n');
                writer.write(stringBuilder.toString());
                i++;
            }
            if (isHttps()) {
                writer.write(10);
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(this.cipherSuite);
                stringBuilder3.append('\n');
                writer.write(stringBuilder3.toString());
                writeCertArray(writer, this.peerCertificates);
                writeCertArray(writer, this.localCertificates);
            }
            writer.close();
        }

        private boolean isHttps() {
            return this.uri.startsWith("https://");
        }

        private Certificate[] readCertArray(StrictLineReader reader) throws IOException {
            int length = reader.readInt();
            if (length == -1) {
                return null;
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                Certificate[] result = new Certificate[length];
                for (int i = 0; i < result.length; i++) {
                    result[i] = certificateFactory.generateCertificate(new ByteArrayInputStream(Base64.decode(reader.readLine(), 0)));
                }
                return result;
            } catch (CertificateException e) {
                throw new IOException(e.getMessage());
            }
        }

        private void writeCertArray(Writer writer, Certificate[] certificates) throws IOException {
            if (certificates == null) {
                writer.write("-1\n");
                return;
            }
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Integer.toString(certificates.length));
                stringBuilder.append('\n');
                writer.write(stringBuilder.toString());
                for (Certificate certificate : certificates) {
                    String line = Base64.encodeToString(certificate.getEncoded(), 0);
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(line);
                    stringBuilder2.append('\n');
                    writer.write(stringBuilder2.toString());
                }
            } catch (CertificateEncodingException e) {
                throw new IOException(e.getMessage());
            }
        }

        public boolean matches(Uri uri, String requestMethod, Map<String, List<String>> requestHeaders) {
            return this.uri.equals(uri.toString()) && this.requestMethod.equals(requestMethod) && new ResponseHeaders(uri, this.responseHeaders).varyMatches(this.varyHeaders.toMultimap(), requestHeaders);
        }
    }

    static class EntryCacheResponse extends CacheResponse {
        private final Entry entry;
        private final FileInputStream snapshot;

        public EntryCacheResponse(Entry entry, FileInputStream snapshot) {
            this.entry = entry;
            this.snapshot = snapshot;
        }

        public Map<String, List<String>> getHeaders() {
            return this.entry.responseHeaders.toMultimap();
        }

        public FileInputStream getBody() {
            return this.snapshot;
        }
    }

    class EntryEditor {
        boolean done;
        String key;
        FileOutputStream[] outs = new FileOutputStream[2];
        File[] temps;

        public EntryEditor(String key) {
            this.key = key;
            this.temps = ResponseCacheMiddleware.this.cache.getTempFiles(2);
        }

        void commit() {
            StreamUtility.closeQuietly(this.outs);
            if (!this.done) {
                ResponseCacheMiddleware.this.cache.commitTempFiles(this.key, this.temps);
                ResponseCacheMiddleware.this.writeSuccessCount = ResponseCacheMiddleware.this.writeSuccessCount + 1;
                this.done = true;
            }
        }

        FileOutputStream newOutputStream(int index) throws IOException {
            if (this.outs[index] == null) {
                this.outs[index] = new FileOutputStream(this.temps[index]);
            }
            return this.outs[index];
        }

        void abort() {
            StreamUtility.closeQuietly(this.outs);
            FileCache.removeFiles(this.temps);
            if (!this.done) {
                ResponseCacheMiddleware.this.writeAbortCount = ResponseCacheMiddleware.this.writeAbortCount + 1;
                this.done = true;
            }
        }
    }

    private static class BodyCacher extends FilteredDataEmitter {
        ByteBufferList cached;
        EntryEditor editor;

        private BodyCacher() {
        }

        protected void report(Exception e) {
            super.report(e);
            if (e != null) {
                abort();
            }
        }

        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            if (this.cached != null) {
                super.onDataAvailable(emitter, this.cached);
                if (this.cached.remaining() <= 0) {
                    this.cached = null;
                } else {
                    return;
                }
            }
            ByteBufferList copy = new ByteBufferList();
            if (this.editor != null) {
                OutputStream outputStream = this.editor.newOutputStream(1);
                if (outputStream != null) {
                    while (!bb.isEmpty()) {
                        ByteBuffer b = bb.remove();
                        try {
                            ByteBufferList.writeOutputStream(outputStream, b);
                            copy.add(b);
                        } catch (Exception e) {
                            try {
                                abort();
                            } catch (Throwable th) {
                                bb.get(copy);
                                copy.get(bb);
                            }
                        } catch (Throwable th2) {
                            copy.add(b);
                        }
                    }
                } else {
                    abort();
                }
            }
            bb.get(copy);
            copy.get(bb);
            super.onDataAvailable(emitter, bb);
            if (this.editor != null && bb.remaining() > 0) {
                this.cached = new ByteBufferList();
                bb.get(this.cached);
            }
        }

        public void close() {
            abort();
            super.close();
        }

        public void abort() {
            if (this.editor != null) {
                this.editor.abort();
                this.editor = null;
            }
        }

        public void commit() {
            if (this.editor != null) {
                this.editor.commit();
                this.editor = null;
            }
        }
    }

    private static class CachedBodyEmitter extends FilteredDataEmitter {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Allocator allocator = new Allocator();
        boolean allowEnd;
        EntryCacheResponse cacheResponse;
        private boolean paused;
        ByteBufferList pending = new ByteBufferList();
        Runnable sendCachedDataRunnable = new C06551();

        /* renamed from: com.koushikdutta.async.http.cache.ResponseCacheMiddleware$CachedBodyEmitter$1 */
        class C06551 implements Runnable {
            C06551() {
            }

            public void run() {
                CachedBodyEmitter.this.sendCachedDataOnNetworkThread();
            }
        }

        /* renamed from: com.koushikdutta.async.http.cache.ResponseCacheMiddleware$CachedBodyEmitter$2 */
        class C06562 implements Runnable {
            C06562() {
            }

            public void run() {
                CachedBodyEmitter.this.close();
            }
        }

        static {
            Class cls = ResponseCacheMiddleware.class;
        }

        public CachedBodyEmitter(EntryCacheResponse cacheResponse, long contentLength) {
            this.cacheResponse = cacheResponse;
            this.allocator.setCurrentAlloc((int) contentLength);
        }

        void sendCachedDataOnNetworkThread() {
            if (this.pending.remaining() > 0) {
                super.onDataAvailable(this, this.pending);
                if (this.pending.remaining() > 0) {
                    return;
                }
            }
            try {
                ByteBuffer buffer = this.allocator.allocate();
                int read = this.cacheResponse.getBody().read(buffer.array(), buffer.arrayOffset(), buffer.capacity());
                if (read == -1) {
                    ByteBufferList.reclaim(buffer);
                    this.allowEnd = true;
                    report(null);
                    return;
                }
                this.allocator.track((long) read);
                buffer.limit(read);
                this.pending.add(buffer);
                super.onDataAvailable(this, this.pending);
                if (this.pending.remaining() <= 0) {
                    getServer().postDelayed(this.sendCachedDataRunnable, 10);
                }
            } catch (IOException e) {
                this.allowEnd = true;
                report(e);
            }
        }

        void sendCachedData() {
            getServer().post(this.sendCachedDataRunnable);
        }

        public void resume() {
            this.paused = false;
            sendCachedData();
        }

        public boolean isPaused() {
            return this.paused;
        }

        public void close() {
            if (getServer().getAffinity() != Thread.currentThread()) {
                getServer().post(new C06562());
                return;
            }
            this.pending.recycle();
            StreamUtility.closeQuietly(this.cacheResponse.getBody());
            super.close();
        }

        protected void report(Exception e) {
            if (this.allowEnd) {
                StreamUtility.closeQuietly(this.cacheResponse.getBody());
                super.report(e);
            }
        }
    }

    private class CachedSocket extends CachedBodyEmitter implements AsyncSocket {
        boolean closed;
        CompletedCallback closedCallback;
        boolean open;

        public CachedSocket(EntryCacheResponse cacheResponse, long contentLength) {
            super(cacheResponse, contentLength);
            this.allowEnd = true;
        }

        public void end() {
        }

        protected void report(Exception e) {
            super.report(e);
            if (!this.closed) {
                this.closed = true;
                if (this.closedCallback != null) {
                    this.closedCallback.onCompleted(e);
                }
            }
        }

        public void write(ByteBufferList bb) {
            bb.recycle();
        }

        public WritableCallback getWriteableCallback() {
            return null;
        }

        public void setWriteableCallback(WritableCallback handler) {
        }

        public boolean isOpen() {
            return this.open;
        }

        public void close() {
            this.open = false;
        }

        public CompletedCallback getClosedCallback() {
            return this.closedCallback;
        }

        public void setClosedCallback(CompletedCallback handler) {
            this.closedCallback = handler;
        }

        public AsyncServer getServer() {
            return ResponseCacheMiddleware.this.server;
        }
    }

    private class CachedSSLSocket extends CachedSocket implements AsyncSSLSocket {
        public CachedSSLSocket(EntryCacheResponse cacheResponse, long contentLength) {
            super(cacheResponse, contentLength);
        }

        public SSLEngine getSSLEngine() {
            return null;
        }

        public X509Certificate[] getPeerCertificates() {
            return null;
        }
    }

    private ResponseCacheMiddleware() {
    }

    public static ResponseCacheMiddleware addCache(AsyncHttpClient client, File cacheDir, long size) throws IOException {
        for (AsyncHttpClientMiddleware middleware : client.getMiddleware()) {
            if (middleware instanceof ResponseCacheMiddleware) {
                throw new IOException("Response cache already added to http client");
            }
        }
        ResponseCacheMiddleware ret = new ResponseCacheMiddleware();
        ret.server = client.getServer();
        ret.cache = new FileCache(cacheDir, size, false);
        client.insertMiddleware(ret);
        return ret;
    }

    public FileCache getFileCache() {
        return this.cache;
    }

    public boolean getCaching() {
        return this.caching;
    }

    public void setCaching(boolean caching) {
        this.caching = caching;
    }

    public void removeFromCache(Uri uri) {
        getFileCache().remove(FileCache.toKeyString(new Object[]{uri}));
    }

    public Cancellable getSocket(GetSocketData data) {
        Cancellable cancellable;
        int i;
        RequestHeaders requestHeaders;
        String str;
        FileInputStream[] snapshot;
        IOException requestHeaders2;
        final GetSocketData getSocketData = data;
        RequestHeaders requestHeaders3 = new RequestHeaders(getSocketData.request.getUri(), RawHeaders.fromMultimap(getSocketData.request.getHeaders().getMultiMap()));
        getSocketData.state.put("request-headers", requestHeaders3);
        if (this.cache == null || !r1.caching) {
            cancellable = null;
            i = 1;
        } else if (requestHeaders3.isNoCache()) {
            requestHeaders = requestHeaders3;
            cancellable = null;
            i = 1;
        } else {
            String key = FileCache.toKeyString(new Object[]{getSocketData.request.getUri()});
            FileInputStream[] snapshot2 = null;
            try {
                snapshot2 = r1.cache.get(key, 2);
                if (snapshot2 == null) {
                    try {
                        r1.networkCount++;
                        return null;
                    } catch (IOException e) {
                        requestHeaders = requestHeaders3;
                        str = key;
                        snapshot = snapshot2;
                        requestHeaders2 = e;
                        r1.networkCount++;
                        StreamUtility.closeQuietly(snapshot);
                        return null;
                    }
                }
                try {
                    long contentLength = (long) snapshot2[1].available();
                    Entry entry = new Entry(snapshot2[0]);
                    if (entry.matches(getSocketData.request.getUri(), getSocketData.request.getMethod(), getSocketData.request.getHeaders().getMultiMap())) {
                        EntryCacheResponse candidate = new EntryCacheResponse(entry, snapshot2[1]);
                        try {
                            int key2;
                            Map<String, List<String>> responseHeadersMap = candidate.getHeaders();
                            FileInputStream cachedResponseBody = candidate.getBody();
                            Map<String, List<String>> map;
                            FileInputStream fileInputStream;
                            if (responseHeadersMap == null) {
                                str = key;
                                requestHeaders3 = null;
                                map = responseHeadersMap;
                                fileInputStream = cachedResponseBody;
                                key2 = 1;
                                snapshot = snapshot2;
                            } else if (cachedResponseBody == null) {
                                requestHeaders = requestHeaders3;
                                str = key;
                                requestHeaders3 = null;
                                map = responseHeadersMap;
                                fileInputStream = cachedResponseBody;
                                key2 = 1;
                                snapshot = snapshot2;
                            } else {
                                RawHeaders rawResponseHeaders = RawHeaders.fromMultimap(responseHeadersMap);
                                ResponseHeaders cachedResponseHeaders = new ResponseHeaders(getSocketData.request.getUri(), rawResponseHeaders);
                                rawResponseHeaders.set("Content-Length", String.valueOf(contentLength));
                                rawResponseHeaders.removeAll("Content-Encoding");
                                rawResponseHeaders.removeAll("Transfer-Encoding");
                                FileInputStream[] snapshot3 = snapshot2;
                                cachedResponseHeaders.setLocalTimestamps(System.currentTimeMillis(), System.currentTimeMillis());
                                responseHeadersMap = cachedResponseHeaders.chooseResponseSource(System.currentTimeMillis(), requestHeaders3);
                                if (responseHeadersMap == ResponseSource.CACHE) {
                                    getSocketData.request.logi("Response retrieved from cache");
                                    final CachedSocket socket = entry.isHttps() ? new CachedSSLSocket(candidate, contentLength) : new CachedSocket(candidate, contentLength);
                                    socket.pending.add(ByteBuffer.wrap(rawResponseHeaders.toHeaderString().getBytes()));
                                    r1.server.post(new Runnable() {
                                        public void run() {
                                            getSocketData.connectCallback.onConnectCompleted(null, socket);
                                            socket.sendCachedDataOnNetworkThread();
                                        }
                                    });
                                    r1.cacheHitCount++;
                                    getSocketData.state.put("socket-owner", r1);
                                    requestHeaders3 = new SimpleCancellable();
                                    requestHeaders3.setComplete();
                                    return requestHeaders3;
                                }
                                if (responseHeadersMap == ResponseSource.CONDITIONAL_CACHE) {
                                    getSocketData.request.logi("Response may be served from conditional cache");
                                    requestHeaders3 = new CacheData();
                                    requestHeaders3.snapshot = snapshot3;
                                    requestHeaders3.contentLength = contentLength;
                                    requestHeaders3.cachedResponseHeaders = cachedResponseHeaders;
                                    requestHeaders3.candidate = candidate;
                                    getSocketData.state.put("cache-data", requestHeaders3);
                                    return null;
                                }
                                cachedResponseBody = snapshot3;
                                getSocketData.request.logd("Response can not be served from cache");
                                r1.networkCount++;
                                StreamUtility.closeQuietly(cachedResponseBody);
                                return null;
                            }
                            r1.networkCount += key2;
                            StreamUtility.closeQuietly(snapshot);
                            return requestHeaders3;
                        } catch (Exception e2) {
                            requestHeaders = requestHeaders3;
                            str = key;
                            snapshot = snapshot2;
                            Exception requestHeaders4 = e2;
                            r1.networkCount++;
                            StreamUtility.closeQuietly(snapshot);
                            return null;
                        }
                    }
                    r1.networkCount++;
                    StreamUtility.closeQuietly(snapshot2);
                    return null;
                } catch (IOException e3) {
                    requestHeaders = requestHeaders3;
                    str = key;
                    snapshot = snapshot2;
                    requestHeaders2 = e3;
                    r1.networkCount++;
                    StreamUtility.closeQuietly(snapshot);
                    return null;
                }
            } catch (IOException e32) {
                requestHeaders = requestHeaders3;
                str = key;
                requestHeaders2 = e32;
                snapshot = snapshot2;
                r1.networkCount++;
                StreamUtility.closeQuietly(snapshot);
                return null;
            }
        }
        r1.networkCount += i;
        return cancellable;
    }

    public int getConditionalCacheHitCount() {
        return this.conditionalCacheHitCount;
    }

    public int getCacheHitCount() {
        return this.cacheHitCount;
    }

    public int getNetworkCount() {
        return this.networkCount;
    }

    public int getCacheStoreCount() {
        return this.cacheStoreCount;
    }

    public void onBodyDecoder(OnBodyDataOnRequestSentData data) {
        if (((CachedSocket) Util.getWrappedSocket(data.socket, CachedSocket.class)) != null) {
            data.response.headers().set(SERVED_FROM, CACHE);
            return;
        }
        CacheData cacheData = (CacheData) data.state.get("cache-data");
        RawHeaders rh = RawHeaders.fromMultimap(data.response.headers().getMultiMap());
        rh.removeAll("Content-Length");
        rh.setStatusLine(String.format(Locale.ENGLISH, "%s %s %s", new Object[]{data.response.protocol(), Integer.valueOf(data.response.code()), data.response.message()}));
        ResponseHeaders networkResponse = new ResponseHeaders(data.request.getUri(), rh);
        data.state.put("response-headers", networkResponse);
        if (cacheData != null) {
            if (cacheData.cachedResponseHeaders.validate(networkResponse)) {
                data.request.logi("Serving response from conditional cache");
                ResponseHeaders combined = cacheData.cachedResponseHeaders.combine(networkResponse);
                data.response.headers(new Headers(combined.getHeaders().toMultimap()));
                data.response.code(combined.getHeaders().getResponseCode());
                data.response.message(combined.getHeaders().getResponseMessage());
                data.response.headers().set(SERVED_FROM, CONDITIONAL_CACHE);
                this.conditionalCacheHitCount++;
                CachedBodyEmitter bodySpewer = new CachedBodyEmitter(cacheData.candidate, cacheData.contentLength);
                bodySpewer.setDataEmitter(data.bodyEmitter);
                data.bodyEmitter = bodySpewer;
                bodySpewer.sendCachedData();
                return;
            }
            data.state.remove("cache-data");
            StreamUtility.closeQuietly(cacheData.snapshot);
        }
        if (this.caching) {
            RequestHeaders requestHeaders = (RequestHeaders) data.state.get("request-headers");
            if (requestHeaders != null && networkResponse.isCacheable(requestHeaders)) {
                if (data.request.getMethod().equals("GET")) {
                    String key = FileCache.toKeyString(new Object[]{data.request.getUri()});
                    Entry entry = new Entry(data.request.getUri(), requestHeaders.getHeaders().getAll(networkResponse.getVaryFields()), data.request, networkResponse.getHeaders());
                    BodyCacher cacher = new BodyCacher();
                    EntryEditor editor = new EntryEditor(key);
                    try {
                        entry.writeTo(editor);
                        editor.newOutputStream(1);
                        cacher.editor = editor;
                        cacher.setDataEmitter(data.bodyEmitter);
                        data.bodyEmitter = cacher;
                        data.state.put("body-cacher", cacher);
                        data.request.logd("Caching response");
                        this.cacheStoreCount++;
                        return;
                    } catch (Exception e) {
                        editor.abort();
                        this.networkCount++;
                        return;
                    }
                }
            }
            this.networkCount++;
            data.request.logd("Response is not cacheable");
        }
    }

    public void onResponseComplete(OnResponseCompleteDataOnRequestSentData data) {
        CacheData cacheData = (CacheData) data.state.get("cache-data");
        if (!(cacheData == null || cacheData.snapshot == null)) {
            StreamUtility.closeQuietly(cacheData.snapshot);
        }
        if (((CachedSocket) Util.getWrappedSocket(data.socket, CachedSocket.class)) != null) {
            StreamUtility.closeQuietly(((CachedSocket) Util.getWrappedSocket(data.socket, CachedSocket.class)).cacheResponse.getBody());
        }
        BodyCacher cacher = (BodyCacher) data.state.get("body-cacher");
        if (cacher == null) {
            return;
        }
        if (data.exception != null) {
            cacher.abort();
        } else {
            cacher.commit();
        }
    }

    public void clear() {
        if (this.cache != null) {
            this.cache.clear();
        }
    }
}
