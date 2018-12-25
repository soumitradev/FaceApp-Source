package android.support.v4.media.session;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.MediaSessionCompat.Callback;

class MediaSessionCompat$Callback$CallbackHandler extends Handler {
    private static final int MSG_MEDIA_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 1;
    final /* synthetic */ Callback this$0;

    MediaSessionCompat$Callback$CallbackHandler(Callback callback, Looper looper) {
        this.this$0 = callback;
        super(looper);
    }

    public void handleMessage(Message msg) {
        if (msg.what == 1) {
            Callback.access$000(this.this$0);
        }
    }
}
