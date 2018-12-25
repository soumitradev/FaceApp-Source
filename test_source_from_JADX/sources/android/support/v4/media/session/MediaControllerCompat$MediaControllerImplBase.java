package android.support.v4.media.session;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaSession.Stub;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.support.v4.media.session.MediaSessionCompat.ResultReceiverWrapper;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

class MediaControllerCompat$MediaControllerImplBase implements MediaControllerCompat$MediaControllerImpl {
    private IMediaSession mBinder;
    private MediaControllerCompat$TransportControls mTransportControls;

    public MediaControllerCompat$MediaControllerImplBase(Token token) {
        this.mBinder = Stub.asInterface((IBinder) token.getToken());
    }

    public void registerCallback(MediaControllerCompat$Callback callback, Handler handler) {
        if (callback == null) {
            throw new IllegalArgumentException("callback may not be null.");
        }
        try {
            this.mBinder.asBinder().linkToDeath(callback, 0);
            this.mBinder.registerCallbackListener((IMediaControllerCallback) callback.mCallbackObj);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
            callback.onSessionDestroyed();
        }
    }

    public void unregisterCallback(MediaControllerCompat$Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback may not be null.");
        }
        try {
            this.mBinder.unregisterCallbackListener((IMediaControllerCallback) callback.mCallbackObj);
            this.mBinder.asBinder().unlinkToDeath(callback, 0);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", e);
        }
    }

    public boolean dispatchMediaButtonEvent(KeyEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("event may not be null.");
        }
        try {
            this.mBinder.sendMediaButton(event);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in dispatchMediaButtonEvent.", e);
        }
        return false;
    }

    public MediaControllerCompat$TransportControls getTransportControls() {
        if (this.mTransportControls == null) {
            this.mTransportControls = new MediaControllerCompat$TransportControlsBase(this.mBinder);
        }
        return this.mTransportControls;
    }

    public PlaybackStateCompat getPlaybackState() {
        try {
            return this.mBinder.getPlaybackState();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", e);
            return null;
        }
    }

    public MediaMetadataCompat getMetadata() {
        try {
            return this.mBinder.getMetadata();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getMetadata.", e);
            return null;
        }
    }

    public List<QueueItem> getQueue() {
        try {
            return this.mBinder.getQueue();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getQueue.", e);
            return null;
        }
    }

    public void addQueueItem(MediaDescriptionCompat description) {
        try {
            if ((this.mBinder.getFlags() & 4) == 0) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            this.mBinder.addQueueItem(description);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in addQueueItem.", e);
        }
    }

    public void addQueueItem(MediaDescriptionCompat description, int index) {
        try {
            if ((this.mBinder.getFlags() & 4) == 0) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            this.mBinder.addQueueItemAt(description, index);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in addQueueItemAt.", e);
        }
    }

    public void removeQueueItem(MediaDescriptionCompat description) {
        try {
            if ((this.mBinder.getFlags() & 4) == 0) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            this.mBinder.removeQueueItem(description);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in removeQueueItem.", e);
        }
    }

    public CharSequence getQueueTitle() {
        try {
            return this.mBinder.getQueueTitle();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getQueueTitle.", e);
            return null;
        }
    }

    public Bundle getExtras() {
        try {
            return this.mBinder.getExtras();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getExtras.", e);
            return null;
        }
    }

    public int getRatingType() {
        try {
            return this.mBinder.getRatingType();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getRatingType.", e);
            return 0;
        }
    }

    public boolean isCaptioningEnabled() {
        try {
            return this.mBinder.isCaptioningEnabled();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in isCaptioningEnabled.", e);
            return false;
        }
    }

    public int getRepeatMode() {
        try {
            return this.mBinder.getRepeatMode();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", e);
            return -1;
        }
    }

    public int getShuffleMode() {
        try {
            return this.mBinder.getShuffleMode();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getShuffleMode.", e);
            return -1;
        }
    }

    public long getFlags() {
        try {
            return this.mBinder.getFlags();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getFlags.", e);
            return 0;
        }
    }

    public MediaControllerCompat$PlaybackInfo getPlaybackInfo() {
        try {
            ParcelableVolumeInfo info = this.mBinder.getVolumeAttributes();
            return new MediaControllerCompat$PlaybackInfo(info.volumeType, info.audioStream, info.controlType, info.maxVolume, info.currentVolume);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getPlaybackInfo.", e);
            return null;
        }
    }

    public PendingIntent getSessionActivity() {
        try {
            return this.mBinder.getLaunchPendingIntent();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getSessionActivity.", e);
            return null;
        }
    }

    public void setVolumeTo(int value, int flags) {
        try {
            this.mBinder.setVolumeTo(value, flags, null);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in setVolumeTo.", e);
        }
    }

    public void adjustVolume(int direction, int flags) {
        try {
            this.mBinder.adjustVolume(direction, flags, null);
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in adjustVolume.", e);
        }
    }

    public void sendCommand(String command, Bundle params, ResultReceiver cb) {
        try {
            this.mBinder.sendCommand(command, params, new ResultReceiverWrapper(cb));
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in sendCommand.", e);
        }
    }

    public boolean isSessionReady() {
        return true;
    }

    public String getPackageName() {
        try {
            return this.mBinder.getPackageName();
        } catch (RemoteException e) {
            Log.e("MediaControllerCompat", "Dead object in getPackageName.", e);
            return null;
        }
    }

    public Object getMediaController() {
        return null;
    }
}
