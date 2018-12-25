package android.support.v4.media;

import android.support.v4.media.MediaBrowserCompat.ConnectionCallback;

class MediaBrowserCompat$ConnectionCallback$StubApi21 implements MediaBrowserCompatApi21$ConnectionCallback {
    final /* synthetic */ ConnectionCallback this$0;

    MediaBrowserCompat$ConnectionCallback$StubApi21(ConnectionCallback connectionCallback) {
        this.this$0 = connectionCallback;
    }

    public void onConnected() {
        if (this.this$0.mConnectionCallbackInternal != null) {
            this.this$0.mConnectionCallbackInternal.onConnected();
        }
        this.this$0.onConnected();
    }

    public void onConnectionSuspended() {
        if (this.this$0.mConnectionCallbackInternal != null) {
            this.this$0.mConnectionCallbackInternal.onConnectionSuspended();
        }
        this.this$0.onConnectionSuspended();
    }

    public void onConnectionFailed() {
        if (this.this$0.mConnectionCallbackInternal != null) {
            this.this$0.mConnectionCallbackInternal.onConnectionFailed();
        }
        this.this$0.onConnectionFailed();
    }
}
