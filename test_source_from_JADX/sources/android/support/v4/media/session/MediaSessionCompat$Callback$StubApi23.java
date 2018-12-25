package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaSessionCompatApi23.Callback;

@RequiresApi(23)
class MediaSessionCompat$Callback$StubApi23 extends MediaSessionCompat$Callback$StubApi21 implements Callback {
    final /* synthetic */ MediaSessionCompat.Callback this$0;

    MediaSessionCompat$Callback$StubApi23(MediaSessionCompat.Callback callback) {
        this.this$0 = callback;
        super(callback);
    }

    public void onPlayFromUri(Uri uri, Bundle extras) {
        this.this$0.onPlayFromUri(uri, extras);
    }
}
