package android.support.v4.media.session;

import android.content.Intent;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaSessionCompat.Callback;
import android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi21;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.util.Log;

@RequiresApi(21)
class MediaSessionCompat$Callback$StubApi21 implements MediaSessionCompatApi21$Callback {
    final /* synthetic */ Callback this$0;

    MediaSessionCompat$Callback$StubApi21(Callback callback) {
        this.this$0 = callback;
    }

    public void onCommand(String command, Bundle extras, ResultReceiver cb) {
        try {
            QueueItem item = null;
            MediaSessionImplApi21 impl;
            if (command.equals("android.support.v4.media.session.command.GET_EXTRA_BINDER")) {
                impl = (MediaSessionImplApi21) Callback.access$100(this.this$0).get();
                if (impl != null) {
                    IBinder asBinder;
                    Bundle result = new Bundle();
                    IMediaSession extraBinder = impl.getSessionToken().getExtraBinder();
                    String str = "android.support.v4.media.session.EXTRA_BINDER";
                    if (extraBinder != null) {
                        asBinder = extraBinder.asBinder();
                    }
                    BundleCompat.putBinder(result, str, asBinder);
                    cb.send(0, result);
                }
            } else if (command.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM")) {
                extras.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                this.this$0.onAddQueueItem((MediaDescriptionCompat) extras.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
            } else if (command.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT")) {
                extras.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                this.this$0.onAddQueueItem((MediaDescriptionCompat) extras.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"), extras.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX"));
            } else if (command.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM")) {
                extras.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                this.this$0.onRemoveQueueItem((MediaDescriptionCompat) extras.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
            } else if (command.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT")) {
                impl = (MediaSessionImplApi21) Callback.access$100(this.this$0).get();
                if (!(impl == null || MediaSessionImplApi21.access$200(impl) == null)) {
                    int index = extras.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX", -1);
                    if (index >= 0 && index < MediaSessionImplApi21.access$200(impl).size()) {
                        item = (QueueItem) MediaSessionImplApi21.access$200(impl).get(index);
                    }
                    if (item != null) {
                        this.this$0.onRemoveQueueItem(item.getDescription());
                    }
                }
            } else {
                this.this$0.onCommand(command, extras, cb);
            }
        } catch (BadParcelableException e) {
            Log.e("MediaSessionCompat", "Could not unparcel the extra data.");
        }
    }

    public boolean onMediaButtonEvent(Intent mediaButtonIntent) {
        return this.this$0.onMediaButtonEvent(mediaButtonIntent);
    }

    public void onPlay() {
        this.this$0.onPlay();
    }

    public void onPlayFromMediaId(String mediaId, Bundle extras) {
        this.this$0.onPlayFromMediaId(mediaId, extras);
    }

    public void onPlayFromSearch(String search, Bundle extras) {
        this.this$0.onPlayFromSearch(search, extras);
    }

    public void onSkipToQueueItem(long id) {
        this.this$0.onSkipToQueueItem(id);
    }

    public void onPause() {
        this.this$0.onPause();
    }

    public void onSkipToNext() {
        this.this$0.onSkipToNext();
    }

    public void onSkipToPrevious() {
        this.this$0.onSkipToPrevious();
    }

    public void onFastForward() {
        this.this$0.onFastForward();
    }

    public void onRewind() {
        this.this$0.onRewind();
    }

    public void onStop() {
        this.this$0.onStop();
    }

    public void onSeekTo(long pos) {
        this.this$0.onSeekTo(pos);
    }

    public void onSetRating(Object ratingObj) {
        this.this$0.onSetRating(RatingCompat.fromRating(ratingObj));
    }

    public void onSetRating(Object ratingObj, Bundle extras) {
        this.this$0.onSetRating(RatingCompat.fromRating(ratingObj), extras);
    }

    public void onCustomAction(String action, Bundle extras) {
        if (action.equals("android.support.v4.media.session.action.PLAY_FROM_URI")) {
            this.this$0.onPlayFromUri((Uri) extras.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI"), (Bundle) extras.getParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
        } else if (action.equals("android.support.v4.media.session.action.PREPARE")) {
            this.this$0.onPrepare();
        } else if (action.equals("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID")) {
            this.this$0.onPrepareFromMediaId(extras.getString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID"), extras.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
        } else if (action.equals("android.support.v4.media.session.action.PREPARE_FROM_SEARCH")) {
            this.this$0.onPrepareFromSearch(extras.getString("android.support.v4.media.session.action.ARGUMENT_QUERY"), extras.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
        } else if (action.equals("android.support.v4.media.session.action.PREPARE_FROM_URI")) {
            this.this$0.onPrepareFromUri((Uri) extras.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI"), extras.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
        } else if (action.equals("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED")) {
            this.this$0.onSetCaptioningEnabled(extras.getBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED"));
        } else if (action.equals("android.support.v4.media.session.action.SET_REPEAT_MODE")) {
            this.this$0.onSetRepeatMode(extras.getInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE"));
        } else if (action.equals("android.support.v4.media.session.action.SET_SHUFFLE_MODE")) {
            this.this$0.onSetShuffleMode(extras.getInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE"));
        } else if (action.equals("android.support.v4.media.session.action.SET_RATING")) {
            extras.setClassLoader(RatingCompat.class.getClassLoader());
            this.this$0.onSetRating((RatingCompat) extras.getParcelable("android.support.v4.media.session.action.ARGUMENT_RATING"), extras.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
        } else {
            this.this$0.onCustomAction(action, extras);
        }
    }
}
