package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import com.badlogic.gdx.net.HttpStatus;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.CacheControl.Builder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Downloader.ResponseException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import name.antonsmirnov.firmata.FormatHelper;

public class OkHttpDownloader implements Downloader {
    private final OkHttpClient client;

    private static OkHttpClient defaultOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(BLEScanner.ARDISCOVERY_BLE_TIMEOUT_DURATION, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(BLEScanner.ARDISCOVERY_BLE_TIMEOUT_DURATION, TimeUnit.MILLISECONDS);
        return client;
    }

    public OkHttpDownloader(Context context) {
        this(Utils.createDefaultCacheDir(context));
    }

    public OkHttpDownloader(File cacheDir) {
        this(cacheDir, Utils.calculateDiskCacheSize(cacheDir));
    }

    public OkHttpDownloader(Context context, long maxSize) {
        this(Utils.createDefaultCacheDir(context), maxSize);
    }

    public OkHttpDownloader(File cacheDir, long maxSize) {
        this(defaultOkHttpClient());
        try {
            this.client.setCache(new Cache(cacheDir, maxSize));
        } catch (IOException e) {
        }
    }

    public OkHttpDownloader(OkHttpClient client) {
        this.client = client;
    }

    protected final OkHttpClient getClient() {
        return this.client;
    }

    public Downloader$Response load(Uri uri, int networkPolicy) throws IOException {
        CacheControl cacheControl = null;
        if (networkPolicy != 0) {
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                cacheControl = CacheControl.FORCE_CACHE;
            } else {
                Builder builder = new Builder();
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    builder.noCache();
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    builder.noStore();
                }
                cacheControl = builder.build();
            }
        }
        Request.Builder builder2 = new Request.Builder().url(uri.toString());
        if (cacheControl != null) {
            builder2.cacheControl(cacheControl);
        }
        Response response = this.client.newCall(builder2.build()).execute();
        int responseCode = response.code();
        if (responseCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
            response.body().close();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(responseCode);
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(response.message());
            throw new ResponseException(stringBuilder.toString(), networkPolicy, responseCode);
        }
        boolean fromCache = response.cacheResponse() != null;
        ResponseBody responseBody = response.body();
        return new Downloader$Response(responseBody.byteStream(), fromCache, responseBody.contentLength());
    }

    public void shutdown() {
        Cache cache = this.client.getCache();
        if (cache != null) {
            try {
                cache.close();
            } catch (IOException e) {
            }
        }
    }
}
