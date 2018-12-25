package android.support.v4.media.session;

import android.content.Intent;
import android.media.Rating;
import android.media.session.MediaSession.Callback;
import android.os.Bundle;
import android.os.ResultReceiver;

class MediaSessionCompatApi21$CallbackProxy<T extends MediaSessionCompatApi21$Callback> extends Callback {
    protected final T mCallback;

    public MediaSessionCompatApi21$CallbackProxy(T callback) {
        this.mCallback = callback;
    }

    public void onCommand(String command, Bundle args, ResultReceiver cb) {
        this.mCallback.onCommand(command, args, cb);
    }

    public boolean onMediaButtonEvent(Intent mediaButtonIntent) {
        if (!this.mCallback.onMediaButtonEvent(mediaButtonIntent)) {
            if (!super.onMediaButtonEvent(mediaButtonIntent)) {
                return false;
            }
        }
        return true;
    }

    public void onPlay() {
        this.mCallback.onPlay();
    }

    public void onPlayFromMediaId(String mediaId, Bundle extras) {
        this.mCallback.onPlayFromMediaId(mediaId, extras);
    }

    public void onPlayFromSearch(String search, Bundle extras) {
        this.mCallback.onPlayFromSearch(search, extras);
    }

    public void onSkipToQueueItem(long id) {
        this.mCallback.onSkipToQueueItem(id);
    }

    public void onPause() {
        this.mCallback.onPause();
    }

    public void onSkipToNext() {
        this.mCallback.onSkipToNext();
    }

    public void onSkipToPrevious() {
        this.mCallback.onSkipToPrevious();
    }

    public void onFastForward() {
        this.mCallback.onFastForward();
    }

    public void onRewind() {
        this.mCallback.onRewind();
    }

    public void onStop() {
        this.mCallback.onStop();
    }

    public void onSeekTo(long pos) {
        this.mCallback.onSeekTo(pos);
    }

    public void onSetRating(Rating rating) {
        this.mCallback.onSetRating(rating);
    }

    public void onCustomAction(String action, Bundle extras) {
        this.mCallback.onCustomAction(action, extras);
    }
}
