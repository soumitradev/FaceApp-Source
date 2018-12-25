package com.facebook.internal;

import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache.Limits;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

class UrlRedirectCache {
    private static final String REDIRECT_CONTENT_TAG;
    static final String TAG = UrlRedirectCache.class.getSimpleName();
    private static volatile FileLruCache urlRedirectCache;

    UrlRedirectCache() {
    }

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TAG);
        stringBuilder.append("_Redirect");
        REDIRECT_CONTENT_TAG = stringBuilder.toString();
    }

    static synchronized FileLruCache getCache() throws IOException {
        FileLruCache fileLruCache;
        synchronized (UrlRedirectCache.class) {
            if (urlRedirectCache == null) {
                urlRedirectCache = new FileLruCache(TAG, new Limits());
            }
            fileLruCache = urlRedirectCache;
        }
        return fileLruCache;
    }

    static Uri getRedirectedUri(Uri uri) {
        InputStreamReader reader;
        Throwable th;
        if (uri == null) {
            return null;
        }
        String uriString = uri.toString();
        InputStreamReader reader2 = null;
        try {
            FileLruCache cache = getCache();
            reader = reader2;
            reader2 = uriString;
            boolean redirectExists = false;
            while (true) {
                try {
                    InputStream inputStream = cache.get(reader2, REDIRECT_CONTENT_TAG);
                    InputStream stream = inputStream;
                    if (inputStream == null) {
                        break;
                    }
                    redirectExists = true;
                    reader = new InputStreamReader(stream);
                    char[] buffer = new char[128];
                    StringBuilder urlBuilder = new StringBuilder();
                    while (true) {
                        int read = reader.read(buffer, 0, buffer.length);
                        int bufferLength = read;
                        if (read <= 0) {
                            break;
                        }
                        urlBuilder.append(buffer, 0, bufferLength);
                    }
                    Utility.closeQuietly(reader);
                    Object reader3 = urlBuilder.toString();
                } catch (IOException e) {
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            if (redirectExists) {
                Uri parse = Uri.parse(reader2);
                Utility.closeQuietly(reader);
                return parse;
            }
        } catch (IOException e2) {
            reader = reader2;
        } catch (Throwable th3) {
            th = th3;
            reader = reader2;
            String reader4 = uriString;
            Utility.closeQuietly(reader);
            throw th;
        }
        Utility.closeQuietly(reader);
        return null;
    }

    static void cacheUriRedirect(Uri fromUri, Uri toUri) {
        if (fromUri != null) {
            if (toUri != null) {
                OutputStream redirectStream = null;
                try {
                    redirectStream = getCache().openPutStream(fromUri.toString(), REDIRECT_CONTENT_TAG);
                    redirectStream.write(toUri.toString().getBytes());
                } catch (IOException e) {
                } catch (Throwable th) {
                    Utility.closeQuietly(redirectStream);
                }
                Utility.closeQuietly(redirectStream);
            }
        }
    }

    static void clearCache() {
        try {
            getCache().clearCache();
        } catch (IOException e) {
            LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("clearCache failed ");
            stringBuilder.append(e.getMessage());
            Logger.log(loggingBehavior, 5, str, stringBuilder.toString());
        }
    }
}
