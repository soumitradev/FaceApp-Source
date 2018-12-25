package android.support.v4.media;

import android.support.v4.media.MediaBrowserCompat.ItemCallback;
import android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21;

class MediaBrowserCompat$MediaBrowserImplApi21$2 implements Runnable {
    final /* synthetic */ MediaBrowserImplApi21 this$0;
    final /* synthetic */ ItemCallback val$cb;
    final /* synthetic */ String val$mediaId;

    MediaBrowserCompat$MediaBrowserImplApi21$2(MediaBrowserImplApi21 this$0, ItemCallback itemCallback, String str) {
        this.this$0 = this$0;
        this.val$cb = itemCallback;
        this.val$mediaId = str;
    }

    public void run() {
        this.val$cb.onError(this.val$mediaId);
    }
}
