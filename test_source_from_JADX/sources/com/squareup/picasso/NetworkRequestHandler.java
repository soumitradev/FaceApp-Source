package com.squareup.picasso;

import android.graphics.Bitmap;
import android.net.NetworkInfo;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;
import java.io.InputStream;

class NetworkRequestHandler extends RequestHandler {
    static final int RETRY_COUNT = 2;
    private static final String SCHEME_HTTP = "http";
    private static final String SCHEME_HTTPS = "https";
    private final Downloader downloader;
    private final Stats stats;

    static class ContentLengthException extends IOException {
        public ContentLengthException(String message) {
            super(message);
        }
    }

    public NetworkRequestHandler(Downloader downloader, Stats stats) {
        this.downloader = downloader;
        this.stats = stats;
    }

    public boolean canHandleRequest(Request data) {
        String scheme = data.uri.getScheme();
        if (!SCHEME_HTTP.equals(scheme)) {
            if (!SCHEME_HTTPS.equals(scheme)) {
                return false;
            }
        }
        return true;
    }

    public Result load(Request request, int networkPolicy) throws IOException {
        Downloader$Response response = this.downloader.load(request.uri, request.networkPolicy);
        if (response == null) {
            return null;
        }
        Picasso$LoadedFrom loadedFrom = response.cached ? Picasso$LoadedFrom.DISK : Picasso$LoadedFrom.NETWORK;
        Bitmap bitmap = response.getBitmap();
        if (bitmap != null) {
            return new Result(bitmap, loadedFrom);
        }
        InputStream is = response.getInputStream();
        if (is == null) {
            return null;
        }
        if (loadedFrom == Picasso$LoadedFrom.DISK && response.getContentLength() == 0) {
            Utils.closeQuietly(is);
            throw new ContentLengthException("Received response with 0 content-length header.");
        }
        if (loadedFrom == Picasso$LoadedFrom.NETWORK && response.getContentLength() > 0) {
            this.stats.dispatchDownloadFinished(response.getContentLength());
        }
        return new Result(is, loadedFrom);
    }

    int getRetryCount() {
        return 2;
    }

    boolean shouldRetry(boolean airplaneMode, NetworkInfo info) {
        if (info != null) {
            if (!info.isConnected()) {
                return false;
            }
        }
        return true;
    }

    boolean supportsReplay() {
        return true;
    }
}
