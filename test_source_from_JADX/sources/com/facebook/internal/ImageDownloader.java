package com.facebook.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.badlogic.gdx.net.HttpStatus;
import com.facebook.C0410R;
import com.facebook.FacebookException;
import com.facebook.internal.ImageRequest.Callback;
import com.facebook.internal.WorkQueue.WorkItem;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageDownloader {
    private static final int CACHE_READ_QUEUE_MAX_CONCURRENT = 2;
    private static final int DOWNLOAD_QUEUE_MAX_CONCURRENT = 8;
    private static WorkQueue cacheReadQueue = new WorkQueue(2);
    private static WorkQueue downloadQueue = new WorkQueue(8);
    private static Handler handler;
    private static final Map<RequestKey, DownloaderContext> pendingRequests = new HashMap();

    private static class CacheReadWorkItem implements Runnable {
        private boolean allowCachedRedirects;
        private Context context;
        private RequestKey key;

        CacheReadWorkItem(Context context, RequestKey key, boolean allowCachedRedirects) {
            this.context = context;
            this.key = key;
            this.allowCachedRedirects = allowCachedRedirects;
        }

        public void run() {
            ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
        }
    }

    private static class DownloadImageWorkItem implements Runnable {
        private Context context;
        private RequestKey key;

        DownloadImageWorkItem(Context context, RequestKey key) {
            this.context = context;
            this.key = key;
        }

        public void run() {
            ImageDownloader.download(this.key, this.context);
        }
    }

    private static class DownloaderContext {
        boolean isCancelled;
        ImageRequest request;
        WorkItem workItem;

        private DownloaderContext() {
        }
    }

    private static class RequestKey {
        private static final int HASH_MULTIPLIER = 37;
        private static final int HASH_SEED = 29;
        Object tag;
        Uri uri;

        RequestKey(Uri url, Object tag) {
            this.uri = url;
            this.tag = tag;
        }

        public int hashCode() {
            return (((29 * 37) + this.uri.hashCode()) * 37) + this.tag.hashCode();
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof RequestKey)) {
                return false;
            }
            RequestKey compareTo = (RequestKey) o;
            boolean z = compareTo.uri == this.uri && compareTo.tag == this.tag;
            return z;
        }
    }

    public static void downloadAsync(ImageRequest request) {
        if (request != null) {
            RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
            synchronized (pendingRequests) {
                DownloaderContext downloaderContext = (DownloaderContext) pendingRequests.get(key);
                if (downloaderContext != null) {
                    downloaderContext.request = request;
                    downloaderContext.isCancelled = false;
                    downloaderContext.workItem.moveToFront();
                } else {
                    enqueueCacheRead(request, key, request.isCachedRedirectAllowed());
                }
            }
        }
    }

    public static boolean cancelRequest(ImageRequest request) {
        boolean cancelled = false;
        RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = (DownloaderContext) pendingRequests.get(key);
            if (downloaderContext != null) {
                cancelled = true;
                if (downloaderContext.workItem.cancel()) {
                    pendingRequests.remove(key);
                } else {
                    downloaderContext.isCancelled = true;
                }
            }
        }
        return cancelled;
    }

    public static void prioritizeRequest(ImageRequest request) {
        RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = (DownloaderContext) pendingRequests.get(key);
            if (downloaderContext != null) {
                downloaderContext.workItem.moveToFront();
            }
        }
    }

    public static void clearCache(Context context) {
        ImageResponseCache.clearCache(context);
        UrlRedirectCache.clearCache();
    }

    private static void enqueueCacheRead(ImageRequest request, RequestKey key, boolean allowCachedRedirects) {
        enqueueRequest(request, key, cacheReadQueue, new CacheReadWorkItem(request.getContext(), key, allowCachedRedirects));
    }

    private static void enqueueDownload(ImageRequest request, RequestKey key) {
        enqueueRequest(request, key, downloadQueue, new DownloadImageWorkItem(request.getContext(), key));
    }

    private static void enqueueRequest(ImageRequest request, RequestKey key, WorkQueue workQueue, Runnable workItem) {
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = new DownloaderContext();
            downloaderContext.request = request;
            pendingRequests.put(key, downloaderContext);
            downloaderContext.workItem = workQueue.addActiveWorkItem(workItem);
        }
    }

    private static void issueResponse(RequestKey key, Exception error, Bitmap bitmap, boolean isCachedRedirect) {
        DownloaderContext completedRequestContext = removePendingRequest(key);
        if (completedRequestContext != null && !completedRequestContext.isCancelled) {
            ImageRequest request = completedRequestContext.request;
            Callback callback = request.getCallback();
            if (callback != null) {
                final ImageRequest imageRequest = request;
                final Exception exception = error;
                final boolean z = isCachedRedirect;
                final Bitmap bitmap2 = bitmap;
                final Callback callback2 = callback;
                getHandler().post(new Runnable() {
                    public void run() {
                        callback2.onCompleted(new ImageResponse(imageRequest, exception, z, bitmap2));
                    }
                });
            }
        }
    }

    private static void readFromCache(RequestKey key, Context context, boolean allowCachedRedirects) {
        InputStream cachedStream = null;
        boolean isCachedRedirect = false;
        if (allowCachedRedirects) {
            Uri redirectUri = UrlRedirectCache.getRedirectedUri(key.uri);
            if (redirectUri != null) {
                cachedStream = ImageResponseCache.getCachedImageStream(redirectUri, context);
                isCachedRedirect = cachedStream != null;
            }
        }
        if (!isCachedRedirect) {
            cachedStream = ImageResponseCache.getCachedImageStream(key.uri, context);
        }
        if (cachedStream != null) {
            Bitmap bitmap = BitmapFactory.decodeStream(cachedStream);
            Utility.closeQuietly(cachedStream);
            issueResponse(key, null, bitmap, isCachedRedirect);
            return;
        }
        DownloaderContext downloaderContext = removePendingRequest(key);
        if (downloaderContext != null && !downloaderContext.isCancelled) {
            enqueueDownload(downloaderContext.request, key);
        }
    }

    private static void download(RequestKey key, Context context) {
        HttpURLConnection connection = null;
        InputStream stream = null;
        Exception error = null;
        Bitmap bitmap = null;
        boolean issueResponse = true;
        try {
            connection = (HttpURLConnection) new URL(key.uri.toString()).openConnection();
            connection.setInstanceFollowRedirects(false);
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                switch (responseCode) {
                    case HttpStatus.SC_MOVED_PERMANENTLY /*301*/:
                    case HttpStatus.SC_MOVED_TEMPORARILY /*302*/:
                        issueResponse = false;
                        String redirectLocation = connection.getHeaderField(FirebaseAnalytics$Param.LOCATION);
                        if (!Utility.isNullOrEmpty(redirectLocation)) {
                            Uri redirectUri = Uri.parse(redirectLocation);
                            UrlRedirectCache.cacheUriRedirect(key.uri, redirectUri);
                            DownloaderContext downloaderContext = removePendingRequest(key);
                            if (!(downloaderContext == null || downloaderContext.isCancelled)) {
                                enqueueCacheRead(downloaderContext.request, new RequestKey(redirectUri, key.tag), false);
                            }
                            break;
                        }
                        break;
                    default:
                        stream = connection.getErrorStream();
                        StringBuilder errorMessageBuilder = new StringBuilder();
                        if (stream != null) {
                            InputStreamReader reader = new InputStreamReader(stream);
                            char[] buffer = new char[128];
                            while (true) {
                                int read = reader.read(buffer, 0, buffer.length);
                                int bufferLength = read;
                                if (read > 0) {
                                    errorMessageBuilder.append(buffer, 0, bufferLength);
                                } else {
                                    Utility.closeQuietly(reader);
                                }
                            }
                        } else {
                            errorMessageBuilder.append(context.getString(C0410R.string.com_facebook_image_download_unknown_error));
                        }
                        error = new FacebookException(errorMessageBuilder.toString());
                        break;
                }
            }
            stream = ImageResponseCache.interceptAndCacheImageStream(context, connection);
            bitmap = BitmapFactory.decodeStream(stream);
        } catch (Exception e) {
            error = e;
        } catch (Throwable th) {
            Utility.closeQuietly(null);
            Utility.disconnectQuietly(null);
        }
        Utility.closeQuietly(stream);
        Utility.disconnectQuietly(connection);
        if (issueResponse) {
            issueResponse(key, error, bitmap, false);
        }
    }

    private static synchronized Handler getHandler() {
        Handler handler;
        synchronized (ImageDownloader.class) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            handler = handler;
        }
        return handler;
    }

    private static DownloaderContext removePendingRequest(RequestKey key) {
        DownloaderContext downloaderContext;
        synchronized (pendingRequests) {
            downloaderContext = (DownloaderContext) pendingRequests.remove(key);
        }
        return downloaderContext;
    }
}
