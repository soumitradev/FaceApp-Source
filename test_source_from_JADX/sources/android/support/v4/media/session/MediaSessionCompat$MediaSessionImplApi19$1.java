package android.support.v4.media.session;

import android.media.Rating;
import android.media.RemoteControlClient.OnMetadataUpdateListener;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi19;

class MediaSessionCompat$MediaSessionImplApi19$1 implements OnMetadataUpdateListener {
    final /* synthetic */ MediaSessionImplApi19 this$0;

    MediaSessionCompat$MediaSessionImplApi19$1(MediaSessionImplApi19 this$0) {
        this.this$0 = this$0;
    }

    public void onMetadataUpdate(int key, Object newValue) {
        if (key == 268435457 && (newValue instanceof Rating)) {
            this.this$0.postToHandler(19, RatingCompat.fromRating(newValue));
        }
    }
}
