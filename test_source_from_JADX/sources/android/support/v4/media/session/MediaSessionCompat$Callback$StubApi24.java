package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaSessionCompatApi24.Callback;

@RequiresApi(24)
class MediaSessionCompat$Callback$StubApi24 extends MediaSessionCompat$Callback$StubApi23 implements Callback {
    final /* synthetic */ MediaSessionCompat.Callback this$0;

    MediaSessionCompat$Callback$StubApi24(MediaSessionCompat.Callback callback) {
        this.this$0 = callback;
        super(callback);
    }

    public void onPrepare() {
        this.this$0.onPrepare();
    }

    public void onPrepareFromMediaId(String mediaId, Bundle extras) {
        this.this$0.onPrepareFromMediaId(mediaId, extras);
    }

    public void onPrepareFromSearch(String query, Bundle extras) {
        this.this$0.onPrepareFromSearch(query, extras);
    }

    public void onPrepareFromUri(Uri uri, Bundle extras) {
        this.this$0.onPrepareFromUri(uri, extras);
    }
}
