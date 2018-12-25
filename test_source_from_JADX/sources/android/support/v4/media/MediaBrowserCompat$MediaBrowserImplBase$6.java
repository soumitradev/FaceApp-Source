package android.support.v4.media;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat.CustomActionCallback;
import android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase;

class MediaBrowserCompat$MediaBrowserImplBase$6 implements Runnable {
    final /* synthetic */ MediaBrowserImplBase this$0;
    final /* synthetic */ String val$action;
    final /* synthetic */ CustomActionCallback val$callback;
    final /* synthetic */ Bundle val$extras;

    MediaBrowserCompat$MediaBrowserImplBase$6(MediaBrowserImplBase this$0, CustomActionCallback customActionCallback, String str, Bundle bundle) {
        this.this$0 = this$0;
        this.val$callback = customActionCallback;
        this.val$action = str;
        this.val$extras = bundle;
    }

    public void run() {
        this.val$callback.onError(this.val$action, this.val$extras, null);
    }
}
