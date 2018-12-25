package android.support.v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaSession.Stub;
import android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.support.v4.media.session.MediaSessionCompat.ResultReceiverWrapper;
import android.view.KeyEvent;
import java.util.List;

class MediaSessionCompat$MediaSessionImplBase$MediaSessionStub extends Stub {
    final /* synthetic */ MediaSessionImplBase this$0;

    MediaSessionCompat$MediaSessionImplBase$MediaSessionStub(MediaSessionImplBase this$0) {
        this.this$0 = this$0;
    }

    public void sendCommand(String command, Bundle args, ResultReceiverWrapper cb) {
        this.this$0.postToHandler(1, new MediaSessionCompat$MediaSessionImplBase$Command(command, args, ResultReceiverWrapper.access$400(cb)));
    }

    public boolean sendMediaButton(KeyEvent mediaButton) {
        boolean z = true;
        if ((this.this$0.mFlags & 1) == 0) {
            z = false;
        }
        boolean handlesMediaButtons = z;
        if (handlesMediaButtons) {
            this.this$0.postToHandler(21, mediaButton);
        }
        return handlesMediaButtons;
    }

    public void registerCallbackListener(IMediaControllerCallback cb) {
        if (this.this$0.mDestroyed) {
            try {
                cb.onSessionDestroyed();
            } catch (Exception e) {
            }
        } else {
            this.this$0.mControllerCallbacks.register(cb);
        }
    }

    public void unregisterCallbackListener(IMediaControllerCallback cb) {
        this.this$0.mControllerCallbacks.unregister(cb);
    }

    public String getPackageName() {
        return this.this$0.mPackageName;
    }

    public String getTag() {
        return this.this$0.mTag;
    }

    public PendingIntent getLaunchPendingIntent() {
        PendingIntent pendingIntent;
        synchronized (this.this$0.mLock) {
            pendingIntent = this.this$0.mSessionActivity;
        }
        return pendingIntent;
    }

    public long getFlags() {
        long j;
        synchronized (this.this$0.mLock) {
            j = (long) this.this$0.mFlags;
        }
        return j;
    }

    public ParcelableVolumeInfo getVolumeAttributes() {
        int controlType;
        int max;
        Throwable th;
        synchronized (this.this$0.mLock) {
            try {
                int volumeType = this.this$0.mVolumeType;
                try {
                    int current;
                    int controlType2;
                    int stream = this.this$0.mLocalStream;
                    try {
                        VolumeProviderCompat vp = this.this$0.mVolumeProvider;
                        if (volumeType == 2) {
                            controlType = vp.getVolumeControl();
                            try {
                                try {
                                    current = vp.getCurrentVolume();
                                    controlType2 = controlType;
                                    max = vp.getMaxVolume();
                                } catch (Throwable th2) {
                                    th = th2;
                                    controlType2 = controlType;
                                    while (true) {
                                        try {
                                            break;
                                        } catch (Throwable th3) {
                                            th = th3;
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                controlType2 = controlType;
                                while (true) {
                                    break;
                                }
                                throw th;
                            }
                        }
                        controlType = 2;
                        controlType2 = 2;
                        max = this.this$0.mAudioManager.getStreamMaxVolume(stream);
                        current = this.this$0.mAudioManager.getStreamVolume(stream);
                    } catch (Throwable th5) {
                        th = th5;
                        while (true) {
                            break;
                        }
                        throw th;
                    }
                    try {
                        return new ParcelableVolumeInfo(volumeType, stream, controlType2, max, current);
                    } catch (Throwable th6) {
                        th = th6;
                        int i = max;
                        while (true) {
                            break;
                        }
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    while (true) {
                        break;
                    }
                    throw th;
                }
            } catch (Throwable th8) {
                th = th8;
                while (true) {
                    break;
                }
                throw th;
            }
        }
    }

    public void adjustVolume(int direction, int flags, String packageName) {
        this.this$0.adjustVolume(direction, flags);
    }

    public void setVolumeTo(int value, int flags, String packageName) {
        this.this$0.setVolumeTo(value, flags);
    }

    public void prepare() throws RemoteException {
        this.this$0.postToHandler(3);
    }

    public void prepareFromMediaId(String mediaId, Bundle extras) throws RemoteException {
        this.this$0.postToHandler(4, mediaId, extras);
    }

    public void prepareFromSearch(String query, Bundle extras) throws RemoteException {
        this.this$0.postToHandler(5, query, extras);
    }

    public void prepareFromUri(Uri uri, Bundle extras) throws RemoteException {
        this.this$0.postToHandler(6, uri, extras);
    }

    public void play() throws RemoteException {
        this.this$0.postToHandler(7);
    }

    public void playFromMediaId(String mediaId, Bundle extras) throws RemoteException {
        this.this$0.postToHandler(8, mediaId, extras);
    }

    public void playFromSearch(String query, Bundle extras) throws RemoteException {
        this.this$0.postToHandler(9, query, extras);
    }

    public void playFromUri(Uri uri, Bundle extras) throws RemoteException {
        this.this$0.postToHandler(10, uri, extras);
    }

    public void skipToQueueItem(long id) {
        this.this$0.postToHandler(11, Long.valueOf(id));
    }

    public void pause() throws RemoteException {
        this.this$0.postToHandler(12);
    }

    public void stop() throws RemoteException {
        this.this$0.postToHandler(13);
    }

    public void next() throws RemoteException {
        this.this$0.postToHandler(14);
    }

    public void previous() throws RemoteException {
        this.this$0.postToHandler(15);
    }

    public void fastForward() throws RemoteException {
        this.this$0.postToHandler(16);
    }

    public void rewind() throws RemoteException {
        this.this$0.postToHandler(17);
    }

    public void seekTo(long pos) throws RemoteException {
        this.this$0.postToHandler(18, Long.valueOf(pos));
    }

    public void rate(RatingCompat rating) throws RemoteException {
        this.this$0.postToHandler(19, rating);
    }

    public void rateWithExtras(RatingCompat rating, Bundle extras) throws RemoteException {
        this.this$0.postToHandler(31, rating, extras);
    }

    public void setCaptioningEnabled(boolean enabled) throws RemoteException {
        this.this$0.postToHandler(29, Boolean.valueOf(enabled));
    }

    public void setRepeatMode(int repeatMode) throws RemoteException {
        this.this$0.postToHandler(23, repeatMode);
    }

    public void setShuffleModeEnabledRemoved(boolean enabled) throws RemoteException {
    }

    public void setShuffleMode(int shuffleMode) throws RemoteException {
        this.this$0.postToHandler(30, shuffleMode);
    }

    public void sendCustomAction(String action, Bundle args) throws RemoteException {
        this.this$0.postToHandler(20, action, args);
    }

    public MediaMetadataCompat getMetadata() {
        return this.this$0.mMetadata;
    }

    public PlaybackStateCompat getPlaybackState() {
        Throwable th;
        synchronized (this.this$0.mLock) {
            try {
                PlaybackStateCompat state = this.this$0.mState;
                try {
                    MediaMetadataCompat state2 = this.this$0.mMetadata;
                    return MediaSessionCompat.access$500(state, state2);
                } catch (Throwable th2) {
                    th = th2;
                    PlaybackStateCompat playbackStateCompat = state;
                    state = null;
                    PlaybackStateCompat state3 = playbackStateCompat;
                    while (true) {
                        try {
                            break;
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                MediaMetadataCompat metadata = null;
                while (true) {
                    break;
                }
                throw th;
            }
        }
    }

    public List<QueueItem> getQueue() {
        List<QueueItem> list;
        synchronized (this.this$0.mLock) {
            list = this.this$0.mQueue;
        }
        return list;
    }

    public void addQueueItem(MediaDescriptionCompat description) {
        this.this$0.postToHandler(25, description);
    }

    public void addQueueItemAt(MediaDescriptionCompat description, int index) {
        this.this$0.postToHandler(26, description, index);
    }

    public void removeQueueItem(MediaDescriptionCompat description) {
        this.this$0.postToHandler(27, description);
    }

    public void removeQueueItemAt(int index) {
        this.this$0.postToHandler(28, index);
    }

    public CharSequence getQueueTitle() {
        return this.this$0.mQueueTitle;
    }

    public Bundle getExtras() {
        Bundle bundle;
        synchronized (this.this$0.mLock) {
            bundle = this.this$0.mExtras;
        }
        return bundle;
    }

    public int getRatingType() {
        return this.this$0.mRatingType;
    }

    public boolean isCaptioningEnabled() {
        return this.this$0.mCaptioningEnabled;
    }

    public int getRepeatMode() {
        return this.this$0.mRepeatMode;
    }

    public boolean isShuffleModeEnabledRemoved() {
        return false;
    }

    public int getShuffleMode() {
        return this.this$0.mShuffleMode;
    }

    public boolean isTransportControlEnabled() {
        return (this.this$0.mFlags & 2) != 0;
    }
}
