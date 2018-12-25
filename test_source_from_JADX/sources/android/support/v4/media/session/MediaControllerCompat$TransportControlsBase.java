package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.PlaybackStateCompat.CustomAction;
import android.util.Log;

class MediaControllerCompat$TransportControlsBase extends MediaControllerCompat$TransportControls {
    private IMediaSession mBinder;

    public MediaControllerCompat$TransportControlsBase(IMediaSession binder) {
        this.mBinder = binder;
    }

    public void prepare() {
        try {
            this.mBinder.prepare();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in prepare.", e);
        }
    }

    public void prepareFromMediaId(String mediaId, Bundle extras) {
        try {
            this.mBinder.prepareFromMediaId(mediaId, extras);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in prepareFromMediaId.", e);
        }
    }

    public void prepareFromSearch(String query, Bundle extras) {
        try {
            this.mBinder.prepareFromSearch(query, extras);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in prepareFromSearch.", e);
        }
    }

    public void prepareFromUri(Uri uri, Bundle extras) {
        try {
            this.mBinder.prepareFromUri(uri, extras);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in prepareFromUri.", e);
        }
    }

    public void play() {
        try {
            this.mBinder.play();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in play.", e);
        }
    }

    public void playFromMediaId(String mediaId, Bundle extras) {
        try {
            this.mBinder.playFromMediaId(mediaId, extras);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in playFromMediaId.", e);
        }
    }

    public void playFromSearch(String query, Bundle extras) {
        try {
            this.mBinder.playFromSearch(query, extras);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in playFromSearch.", e);
        }
    }

    public void playFromUri(Uri uri, Bundle extras) {
        try {
            this.mBinder.playFromUri(uri, extras);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in playFromUri.", e);
        }
    }

    public void skipToQueueItem(long id) {
        try {
            this.mBinder.skipToQueueItem(id);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in skipToQueueItem.", e);
        }
    }

    public void pause() {
        try {
            this.mBinder.pause();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in pause.", e);
        }
    }

    public void stop() {
        try {
            this.mBinder.stop();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in stop.", e);
        }
    }

    public void seekTo(long pos) {
        try {
            this.mBinder.seekTo(pos);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in seekTo.", e);
        }
    }

    public void fastForward() {
        try {
            this.mBinder.fastForward();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in fastForward.", e);
        }
    }

    public void skipToNext() {
        try {
            this.mBinder.next();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in skipToNext.", e);
        }
    }

    public void rewind() {
        try {
            this.mBinder.rewind();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in rewind.", e);
        }
    }

    public void skipToPrevious() {
        try {
            this.mBinder.previous();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in skipToPrevious.", e);
        }
    }

    public void setRating(RatingCompat rating) {
        try {
            this.mBinder.rate(rating);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in setRating.", e);
        }
    }

    public void setRating(RatingCompat rating, Bundle extras) {
        try {
            this.mBinder.rateWithExtras(rating, extras);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in setRating.", e);
        }
    }

    public void setCaptioningEnabled(boolean enabled) {
        try {
            this.mBinder.setCaptioningEnabled(enabled);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in setCaptioningEnabled.", e);
        }
    }

    public void setRepeatMode(int repeatMode) {
        try {
            this.mBinder.setRepeatMode(repeatMode);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in setRepeatMode.", e);
        }
    }

    public void setShuffleMode(int shuffleMode) {
        try {
            this.mBinder.setShuffleMode(shuffleMode);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in setShuffleMode.", e);
        }
    }

    public void sendCustomAction(CustomAction customAction, Bundle args) {
        sendCustomAction(customAction.getAction(), args);
    }

    public void sendCustomAction(String action, Bundle args) {
        MediaControllerCompat.access$100(action, args);
        try {
            this.mBinder.sendCustomAction(action, args);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in sendCustomAction.", e);
        }
    }
}
