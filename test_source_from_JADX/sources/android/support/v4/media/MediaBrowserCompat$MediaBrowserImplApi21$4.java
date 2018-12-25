package android.support.v4.media;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21;
import android.support.v4.media.MediaBrowserCompat.SearchCallback;

class MediaBrowserCompat$MediaBrowserImplApi21$4 implements Runnable {
    final /* synthetic */ MediaBrowserImplApi21 this$0;
    final /* synthetic */ SearchCallback val$callback;
    final /* synthetic */ Bundle val$extras;
    final /* synthetic */ String val$query;

    MediaBrowserCompat$MediaBrowserImplApi21$4(MediaBrowserImplApi21 this$0, SearchCallback searchCallback, String str, Bundle bundle) {
        this.this$0 = this$0;
        this.val$callback = searchCallback;
        this.val$query = str;
        this.val$extras = bundle;
    }

    public void run() {
        this.val$callback.onError(this.val$query, this.val$extras);
    }
}
