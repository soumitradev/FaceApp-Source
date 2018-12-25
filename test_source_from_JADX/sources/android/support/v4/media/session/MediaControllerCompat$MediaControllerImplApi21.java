package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaSession.Stub;
import android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiresApi(21)
class MediaControllerCompat$MediaControllerImplApi21 implements MediaControllerCompat$MediaControllerImpl {
    private HashMap<MediaControllerCompat$Callback, ExtraCallback> mCallbackMap = new HashMap();
    protected final Object mControllerObj;
    private IMediaSession mExtraBinder;
    private final List<MediaControllerCompat$Callback> mPendingCallbacks = new ArrayList();

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver */
    private static class ExtraBinderRequestResultReceiver extends ResultReceiver {
        private WeakReference<MediaControllerCompat$MediaControllerImplApi21> mMediaControllerImpl;

        public ExtraBinderRequestResultReceiver(MediaControllerCompat$MediaControllerImplApi21 mediaControllerImpl, Handler handler) {
            super(handler);
            this.mMediaControllerImpl = new WeakReference(mediaControllerImpl);
        }

        protected void onReceiveResult(int resultCode, Bundle resultData) {
            MediaControllerCompat$MediaControllerImplApi21 mediaControllerImpl = (MediaControllerCompat$MediaControllerImplApi21) this.mMediaControllerImpl.get();
            if (mediaControllerImpl != null) {
                if (resultData != null) {
                    mediaControllerImpl.mExtraBinder = Stub.asInterface(BundleCompat.getBinder(resultData, "android.support.v4.media.session.EXTRA_BINDER"));
                    mediaControllerImpl.processPendingCallbacks();
                }
            }
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraCallback */
    private static class ExtraCallback extends StubCompat {
        ExtraCallback(MediaControllerCompat$Callback callback) {
            super(callback);
        }

        public void onSessionDestroyed() throws RemoteException {
            throw new AssertionError();
        }

        public void onMetadataChanged(MediaMetadataCompat metadata) throws RemoteException {
            throw new AssertionError();
        }

        public void onQueueChanged(List<QueueItem> list) throws RemoteException {
            throw new AssertionError();
        }

        public void onQueueTitleChanged(CharSequence title) throws RemoteException {
            throw new AssertionError();
        }

        public void onExtrasChanged(Bundle extras) throws RemoteException {
            throw new AssertionError();
        }

        public void onVolumeInfoChanged(ParcelableVolumeInfo info) throws RemoteException {
            throw new AssertionError();
        }
    }

    public MediaControllerCompat$MediaControllerImplApi21(Context context, MediaSessionCompat session) {
        this.mControllerObj = MediaControllerCompatApi21.fromToken(context, session.getSessionToken().getToken());
        this.mExtraBinder = session.getSessionToken().getExtraBinder();
        if (this.mExtraBinder == null) {
            requestExtraBinder();
        }
    }

    public MediaControllerCompat$MediaControllerImplApi21(Context context, Token sessionToken) throws RemoteException {
        this.mControllerObj = MediaControllerCompatApi21.fromToken(context, sessionToken.getToken());
        if (this.mControllerObj == null) {
            throw new RemoteException();
        }
        this.mExtraBinder = sessionToken.getExtraBinder();
        if (this.mExtraBinder == null) {
            requestExtraBinder();
        }
    }

    public final void registerCallback(MediaControllerCompat$Callback callback, Handler handler) {
        MediaControllerCompatApi21.registerCallback(this.mControllerObj, callback.mCallbackObj, handler);
        if (this.mExtraBinder != null) {
            ExtraCallback extraCallback = new ExtraCallback(callback);
            this.mCallbackMap.put(callback, extraCallback);
            callback.mHasExtraCallback = true;
            try {
                this.mExtraBinder.registerCallbackListener(extraCallback);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
            }
            return;
        }
        synchronized (this.mPendingCallbacks) {
            callback.mHasExtraCallback = false;
            this.mPendingCallbacks.add(callback);
        }
    }

    public final void unregisterCallback(MediaControllerCompat$Callback callback) {
        MediaControllerCompatApi21.unregisterCallback(this.mControllerObj, callback.mCallbackObj);
        if (this.mExtraBinder != null) {
            try {
                ExtraCallback extraCallback = (ExtraCallback) this.mCallbackMap.remove(callback);
                if (extraCallback != null) {
                    callback.mHasExtraCallback = false;
                    this.mExtraBinder.unregisterCallbackListener(extraCallback);
                }
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", e);
            }
            return;
        }
        synchronized (this.mPendingCallbacks) {
            this.mPendingCallbacks.remove(callback);
        }
    }

    public boolean dispatchMediaButtonEvent(KeyEvent event) {
        return MediaControllerCompatApi21.dispatchMediaButtonEvent(this.mControllerObj, event);
    }

    public MediaControllerCompat$TransportControls getTransportControls() {
        Object controlsObj = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
        return controlsObj != null ? new MediaControllerCompat$TransportControlsApi21(controlsObj) : null;
    }

    public PlaybackStateCompat getPlaybackState() {
        if (this.mExtraBinder != null) {
            try {
                return this.mExtraBinder.getPlaybackState();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", e);
            }
        }
        Object stateObj = MediaControllerCompatApi21.getPlaybackState(this.mControllerObj);
        return stateObj != null ? PlaybackStateCompat.fromPlaybackState(stateObj) : null;
    }

    public MediaMetadataCompat getMetadata() {
        Object metadataObj = MediaControllerCompatApi21.getMetadata(this.mControllerObj);
        return metadataObj != null ? MediaMetadataCompat.fromMediaMetadata(metadataObj) : null;
    }

    public List<QueueItem> getQueue() {
        List<Object> queueObjs = MediaControllerCompatApi21.getQueue(this.mControllerObj);
        return queueObjs != null ? QueueItem.fromQueueItemList(queueObjs) : null;
    }

    public void addQueueItem(MediaDescriptionCompat description) {
        if ((getFlags() & 4) == 0) {
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }
        Bundle params = new Bundle();
        params.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", description);
        sendCommand("android.support.v4.media.session.command.ADD_QUEUE_ITEM", params, null);
    }

    public void addQueueItem(MediaDescriptionCompat description, int index) {
        if ((getFlags() & 4) == 0) {
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }
        Bundle params = new Bundle();
        params.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", description);
        params.putInt("android.support.v4.media.session.command.ARGUMENT_INDEX", index);
        sendCommand("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT", params, null);
    }

    public void removeQueueItem(MediaDescriptionCompat description) {
        if ((getFlags() & 4) == 0) {
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }
        Bundle params = new Bundle();
        params.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", description);
        sendCommand("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM", params, null);
    }

    public CharSequence getQueueTitle() {
        return MediaControllerCompatApi21.getQueueTitle(this.mControllerObj);
    }

    public Bundle getExtras() {
        return MediaControllerCompatApi21.getExtras(this.mControllerObj);
    }

    public int getRatingType() {
        if (VERSION.SDK_INT < 22 && this.mExtraBinder != null) {
            try {
                return this.mExtraBinder.getRatingType();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getRatingType.", e);
            }
        }
        return MediaControllerCompatApi21.getRatingType(this.mControllerObj);
    }

    public boolean isCaptioningEnabled() {
        if (this.mExtraBinder != null) {
            try {
                return this.mExtraBinder.isCaptioningEnabled();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in isCaptioningEnabled.", e);
            }
        }
        return false;
    }

    public int getRepeatMode() {
        if (this.mExtraBinder != null) {
            try {
                return this.mExtraBinder.getRepeatMode();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", e);
            }
        }
        return -1;
    }

    public int getShuffleMode() {
        if (this.mExtraBinder != null) {
            try {
                return this.mExtraBinder.getShuffleMode();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getShuffleMode.", e);
            }
        }
        return -1;
    }

    public long getFlags() {
        return MediaControllerCompatApi21.getFlags(this.mControllerObj);
    }

    public MediaControllerCompat$PlaybackInfo getPlaybackInfo() {
        Object volumeInfoObj = MediaControllerCompatApi21.getPlaybackInfo(this.mControllerObj);
        return volumeInfoObj != null ? new MediaControllerCompat$PlaybackInfo(PlaybackInfo.getPlaybackType(volumeInfoObj), PlaybackInfo.getLegacyAudioStream(volumeInfoObj), PlaybackInfo.getVolumeControl(volumeInfoObj), PlaybackInfo.getMaxVolume(volumeInfoObj), PlaybackInfo.getCurrentVolume(volumeInfoObj)) : null;
    }

    public PendingIntent getSessionActivity() {
        return MediaControllerCompatApi21.getSessionActivity(this.mControllerObj);
    }

    public void setVolumeTo(int value, int flags) {
        MediaControllerCompatApi21.setVolumeTo(this.mControllerObj, value, flags);
    }

    public void adjustVolume(int direction, int flags) {
        MediaControllerCompatApi21.adjustVolume(this.mControllerObj, direction, flags);
    }

    public void sendCommand(String command, Bundle params, ResultReceiver cb) {
        MediaControllerCompatApi21.sendCommand(this.mControllerObj, command, params, cb);
    }

    public boolean isSessionReady() {
        return this.mExtraBinder != null;
    }

    public String getPackageName() {
        return MediaControllerCompatApi21.getPackageName(this.mControllerObj);
    }

    public Object getMediaController() {
        return this.mControllerObj;
    }

    private void requestExtraBinder() {
        sendCommand("android.support.v4.media.session.command.GET_EXTRA_BINDER", null, new ExtraBinderRequestResultReceiver(this, new Handler()));
    }

    private void processPendingCallbacks() {
        if (this.mExtraBinder != null) {
            synchronized (this.mPendingCallbacks) {
                for (MediaControllerCompat$Callback callback : this.mPendingCallbacks) {
                    ExtraCallback extraCallback = new ExtraCallback(callback);
                    this.mCallbackMap.put(callback, extraCallback);
                    callback.mHasExtraCallback = true;
                    try {
                        this.mExtraBinder.registerCallbackListener(extraCallback);
                        callback.onSessionReady();
                    } catch (RemoteException e) {
                        Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                    }
                }
                this.mPendingCallbacks.clear();
            }
        }
    }
}
