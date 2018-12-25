package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.Build.VERSION;
import com.badlogic.gdx.net.HttpStatus;
import com.squareup.picasso.Downloader.ResponseException;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.Constants;

public class UrlConnectionDownloader implements Downloader {
    private static final ThreadLocal<StringBuilder> CACHE_HEADER_BUILDER = new C16661();
    private static final String FORCE_CACHE = "only-if-cached,max-age=2147483647";
    static final String RESPONSE_SOURCE = "X-Android-Response-Source";
    static volatile Object cache;
    private static final Object lock = new Object();
    private final Context context;

    /* renamed from: com.squareup.picasso.UrlConnectionDownloader$1 */
    static class C16661 extends ThreadLocal<StringBuilder> {
        C16661() {
        }

        protected StringBuilder initialValue() {
            return new StringBuilder();
        }
    }

    private static class ResponseCacheIcs {
        private ResponseCacheIcs() {
        }

        static Object install(Context context) throws IOException {
            File cacheDir = Utils.createDefaultCacheDir(context);
            HttpResponseCache cache = HttpResponseCache.getInstalled();
            if (cache == null) {
                return HttpResponseCache.install(cacheDir, Utils.calculateDiskCacheSize(cacheDir));
            }
            return cache;
        }

        static void close(Object cache) {
            try {
                ((HttpResponseCache) cache).close();
            } catch (IOException e) {
            }
        }
    }

    public UrlConnectionDownloader(Context context) {
        this.context = context.getApplicationContext();
    }

    protected HttpURLConnection openConnection(Uri path) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(path.toString()).openConnection();
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(20000);
        return connection;
    }

    public Downloader$Response load(Uri uri, int networkPolicy) throws IOException {
        if (VERSION.SDK_INT >= 14) {
            installCacheIfNeeded(this.context);
        }
        HttpURLConnection connection = openConnection(uri);
        connection.setUseCaches(true);
        if (networkPolicy != 0) {
            String headerValue;
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                headerValue = FORCE_CACHE;
            } else {
                StringBuilder headerValue2 = (StringBuilder) CACHE_HEADER_BUILDER.get();
                headerValue2.setLength(0);
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    headerValue2.append("no-cache");
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    if (headerValue2.length() > 0) {
                        headerValue2.append(Constants.REMIX_URL_SEPARATOR);
                    }
                    headerValue2.append("no-store");
                }
                headerValue = headerValue2.toString();
            }
            connection.setRequestProperty("Cache-Control", headerValue);
        }
        int responseCode = connection.getResponseCode();
        if (responseCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
            connection.disconnect();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(responseCode);
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(connection.getResponseMessage());
            throw new ResponseException(stringBuilder.toString(), networkPolicy, responseCode);
        }
        long contentLength = (long) connection.getHeaderFieldInt("Content-Length", -1);
        return new Downloader$Response(connection.getInputStream(), Utils.parseResponseSourceHeader(connection.getHeaderField(RESPONSE_SOURCE)), contentLength);
    }

    public void shutdown() {
        if (VERSION.SDK_INT >= 14 && cache != null) {
            ResponseCacheIcs.close(cache);
        }
    }

    private static void installCacheIfNeeded(Context context) {
        if (cache == null) {
            try {
                synchronized (lock) {
                    if (cache == null) {
                        cache = ResponseCacheIcs.install(context);
                    }
                }
            } catch (IOException e) {
            }
        }
    }
}
