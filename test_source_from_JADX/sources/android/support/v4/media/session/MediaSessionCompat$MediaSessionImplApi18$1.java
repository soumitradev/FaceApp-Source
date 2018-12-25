package android.support.v4.media.session;

import android.media.RemoteControlClient.OnPlaybackPositionUpdateListener;
import android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi18;

class MediaSessionCompat$MediaSessionImplApi18$1 implements OnPlaybackPositionUpdateListener {
    final /* synthetic */ MediaSessionImplApi18 this$0;

    MediaSessionCompat$MediaSessionImplApi18$1(MediaSessionImplApi18 this$0) {
        this.this$0 = this$0;
    }

    public void onPlaybackPositionUpdate(long newPositionMs) {
        this.this$0.postToHandler(18, Long.valueOf(newPositionMs));
    }
}
