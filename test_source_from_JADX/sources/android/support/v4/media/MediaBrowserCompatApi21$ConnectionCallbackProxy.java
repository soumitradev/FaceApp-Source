package android.support.v4.media;

import android.media.browse.MediaBrowser.ConnectionCallback;

class MediaBrowserCompatApi21$ConnectionCallbackProxy<T extends MediaBrowserCompatApi21$ConnectionCallback> extends ConnectionCallback {
    protected final T mConnectionCallback;

    public MediaBrowserCompatApi21$ConnectionCallbackProxy(T connectionCallback) {
        this.mConnectionCallback = connectionCallback;
    }

    public void onConnected() {
        this.mConnectionCallback.onConnected();
    }

    public void onConnectionSuspended() {
        this.mConnectionCallback.onConnectionSuspended();
    }

    public void onConnectionFailed() {
        this.mConnectionCallback.onConnectionFailed();
    }
}
