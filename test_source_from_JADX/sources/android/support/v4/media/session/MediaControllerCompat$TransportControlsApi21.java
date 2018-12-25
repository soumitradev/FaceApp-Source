package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaControllerCompatApi21.TransportControls;
import android.support.v4.media.session.PlaybackStateCompat.CustomAction;

class MediaControllerCompat$TransportControlsApi21 extends MediaControllerCompat$TransportControls {
    protected final Object mControlsObj;

    public MediaControllerCompat$TransportControlsApi21(Object controlsObj) {
        this.mControlsObj = controlsObj;
    }

    public void prepare() {
        sendCustomAction("android.support.v4.media.session.action.PREPARE", null);
    }

    public void prepareFromMediaId(String mediaId, Bundle extras) {
        Bundle bundle = new Bundle();
        bundle.putString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID", mediaId);
        bundle.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", extras);
        sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID", bundle);
    }

    public void prepareFromSearch(String query, Bundle extras) {
        Bundle bundle = new Bundle();
        bundle.putString("android.support.v4.media.session.action.ARGUMENT_QUERY", query);
        bundle.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", extras);
        sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_SEARCH", bundle);
    }

    public void prepareFromUri(Uri uri, Bundle extras) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", uri);
        bundle.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", extras);
        sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_URI", bundle);
    }

    public void play() {
        TransportControls.play(this.mControlsObj);
    }

    public void pause() {
        TransportControls.pause(this.mControlsObj);
    }

    public void stop() {
        TransportControls.stop(this.mControlsObj);
    }

    public void seekTo(long pos) {
        TransportControls.seekTo(this.mControlsObj, pos);
    }

    public void fastForward() {
        TransportControls.fastForward(this.mControlsObj);
    }

    public void rewind() {
        TransportControls.rewind(this.mControlsObj);
    }

    public void skipToNext() {
        TransportControls.skipToNext(this.mControlsObj);
    }

    public void skipToPrevious() {
        TransportControls.skipToPrevious(this.mControlsObj);
    }

    public void setRating(RatingCompat rating) {
        TransportControls.setRating(this.mControlsObj, rating != null ? rating.getRating() : null);
    }

    public void setRating(RatingCompat rating, Bundle extras) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_RATING", rating);
        bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", extras);
        sendCustomAction("android.support.v4.media.session.action.SET_RATING", bundle);
    }

    public void setCaptioningEnabled(boolean enabled) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED", enabled);
        sendCustomAction("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED", bundle);
    }

    public void setRepeatMode(int repeatMode) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE", repeatMode);
        sendCustomAction("android.support.v4.media.session.action.SET_REPEAT_MODE", bundle);
    }

    public void setShuffleMode(int shuffleMode) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE", shuffleMode);
        sendCustomAction("android.support.v4.media.session.action.SET_SHUFFLE_MODE", bundle);
    }

    public void playFromMediaId(String mediaId, Bundle extras) {
        TransportControls.playFromMediaId(this.mControlsObj, mediaId, extras);
    }

    public void playFromSearch(String query, Bundle extras) {
        TransportControls.playFromSearch(this.mControlsObj, query, extras);
    }

    public void playFromUri(Uri uri, Bundle extras) {
        if (uri != null) {
            if (!Uri.EMPTY.equals(uri)) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", uri);
                bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", extras);
                sendCustomAction("android.support.v4.media.session.action.PLAY_FROM_URI", bundle);
                return;
            }
        }
        throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
    }

    public void skipToQueueItem(long id) {
        TransportControls.skipToQueueItem(this.mControlsObj, id);
    }

    public void sendCustomAction(CustomAction customAction, Bundle args) {
        MediaControllerCompat.access$100(customAction.getAction(), args);
        TransportControls.sendCustomAction(this.mControlsObj, customAction.getAction(), args);
    }

    public void sendCustomAction(String action, Bundle args) {
        MediaControllerCompat.access$100(action, args);
        TransportControls.sendCustomAction(this.mControlsObj, action, args);
    }
}
