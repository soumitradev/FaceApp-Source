package android.support.v4.media.session;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaControllerCallback.Stub;
import android.support.v4.media.session.MediaControllerCompatApi21.Callback;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import java.lang.ref.WeakReference;
import java.util.List;

public abstract class MediaControllerCompat$Callback implements DeathRecipient {
    private final Object mCallbackObj;
    MessageHandler mHandler;
    boolean mHasExtraCallback;

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback$MessageHandler */
    private class MessageHandler extends Handler {
        private static final int MSG_DESTROYED = 8;
        private static final int MSG_EVENT = 1;
        private static final int MSG_SESSION_READY = 13;
        private static final int MSG_UPDATE_CAPTIONING_ENABLED = 11;
        private static final int MSG_UPDATE_EXTRAS = 7;
        private static final int MSG_UPDATE_METADATA = 3;
        private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
        private static final int MSG_UPDATE_QUEUE = 5;
        private static final int MSG_UPDATE_QUEUE_TITLE = 6;
        private static final int MSG_UPDATE_REPEAT_MODE = 9;
        private static final int MSG_UPDATE_SHUFFLE_MODE = 12;
        private static final int MSG_UPDATE_VOLUME = 4;
        boolean mRegistered = null;

        MessageHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (this.mRegistered) {
                switch (msg.what) {
                    case 1:
                        MediaControllerCompat$Callback.this.onSessionEvent((String) msg.obj, msg.getData());
                        break;
                    case 2:
                        MediaControllerCompat$Callback.this.onPlaybackStateChanged((PlaybackStateCompat) msg.obj);
                        break;
                    case 3:
                        MediaControllerCompat$Callback.this.onMetadataChanged((MediaMetadataCompat) msg.obj);
                        break;
                    case 4:
                        MediaControllerCompat$Callback.this.onAudioInfoChanged((MediaControllerCompat$PlaybackInfo) msg.obj);
                        break;
                    case 5:
                        MediaControllerCompat$Callback.this.onQueueChanged((List) msg.obj);
                        break;
                    case 6:
                        MediaControllerCompat$Callback.this.onQueueTitleChanged((CharSequence) msg.obj);
                        break;
                    case 7:
                        MediaControllerCompat$Callback.this.onExtrasChanged((Bundle) msg.obj);
                        break;
                    case 8:
                        MediaControllerCompat$Callback.this.onSessionDestroyed();
                        break;
                    case 9:
                        MediaControllerCompat$Callback.this.onRepeatModeChanged(((Integer) msg.obj).intValue());
                        break;
                    case 11:
                        MediaControllerCompat$Callback.this.onCaptioningEnabledChanged(((Boolean) msg.obj).booleanValue());
                        break;
                    case 12:
                        MediaControllerCompat$Callback.this.onShuffleModeChanged(((Integer) msg.obj).intValue());
                        break;
                    case 13:
                        MediaControllerCompat$Callback.this.onSessionReady();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback$StubApi21 */
    private static class StubApi21 implements Callback {
        private final WeakReference<MediaControllerCompat$Callback> mCallback;

        StubApi21(MediaControllerCompat$Callback callback) {
            this.mCallback = new WeakReference(callback);
        }

        public void onSessionDestroyed() {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.onSessionDestroyed();
            }
        }

        public void onSessionEvent(String event, Bundle extras) {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback == null) {
                return;
            }
            if (!callback.mHasExtraCallback || VERSION.SDK_INT >= 23) {
                callback.onSessionEvent(event, extras);
            }
        }

        public void onPlaybackStateChanged(Object stateObj) {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback == null) {
                return;
            }
            if (!callback.mHasExtraCallback) {
                callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(stateObj));
            }
        }

        public void onMetadataChanged(Object metadataObj) {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(metadataObj));
            }
        }

        public void onQueueChanged(List<?> queue) {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.onQueueChanged(QueueItem.fromQueueItemList(queue));
            }
        }

        public void onQueueTitleChanged(CharSequence title) {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.onQueueTitleChanged(title);
            }
        }

        public void onExtrasChanged(Bundle extras) {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.onExtrasChanged(extras);
            }
        }

        public void onAudioInfoChanged(int type, int stream, int control, int max, int current) {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.onAudioInfoChanged(new MediaControllerCompat$PlaybackInfo(type, stream, control, max, current));
            }
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback$StubCompat */
    private static class StubCompat extends Stub {
        private final WeakReference<MediaControllerCompat$Callback> mCallback;

        StubCompat(MediaControllerCompat$Callback callback) {
            this.mCallback = new WeakReference(callback);
        }

        public void onEvent(String event, Bundle extras) throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(1, event, extras);
            }
        }

        public void onSessionDestroyed() throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(8, null, null);
            }
        }

        public void onPlaybackStateChanged(PlaybackStateCompat state) throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(2, state, null);
            }
        }

        public void onMetadataChanged(MediaMetadataCompat metadata) throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(3, metadata, null);
            }
        }

        public void onQueueChanged(List<QueueItem> queue) throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(5, queue, null);
            }
        }

        public void onQueueTitleChanged(CharSequence title) throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(6, title, null);
            }
        }

        public void onCaptioningEnabledChanged(boolean enabled) throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(11, Boolean.valueOf(enabled), null);
            }
        }

        public void onRepeatModeChanged(int repeatMode) throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(9, Integer.valueOf(repeatMode), null);
            }
        }

        public void onShuffleModeChangedRemoved(boolean enabled) throws RemoteException {
        }

        public void onShuffleModeChanged(int shuffleMode) throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(12, Integer.valueOf(shuffleMode), null);
            }
        }

        public void onExtrasChanged(Bundle extras) throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(7, extras, null);
            }
        }

        public void onVolumeInfoChanged(ParcelableVolumeInfo info) throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                MediaControllerCompat$PlaybackInfo pi = null;
                if (info != null) {
                    pi = new MediaControllerCompat$PlaybackInfo(info.volumeType, info.audioStream, info.controlType, info.maxVolume, info.currentVolume);
                }
                callback.postToHandler(4, pi, null);
            }
        }

        public void onSessionReady() throws RemoteException {
            MediaControllerCompat$Callback callback = (MediaControllerCompat$Callback) this.mCallback.get();
            if (callback != null) {
                callback.postToHandler(13, null, null);
            }
        }
    }

    public MediaControllerCompat$Callback() {
        if (VERSION.SDK_INT >= 21) {
            this.mCallbackObj = MediaControllerCompatApi21.createCallback(new StubApi21(this));
        } else {
            this.mCallbackObj = new StubCompat(this);
        }
    }

    public void onSessionReady() {
    }

    public void onSessionDestroyed() {
    }

    public void onSessionEvent(String event, Bundle extras) {
    }

    public void onPlaybackStateChanged(PlaybackStateCompat state) {
    }

    public void onMetadataChanged(MediaMetadataCompat metadata) {
    }

    public void onQueueChanged(List<QueueItem> list) {
    }

    public void onQueueTitleChanged(CharSequence title) {
    }

    public void onExtrasChanged(Bundle extras) {
    }

    public void onAudioInfoChanged(MediaControllerCompat$PlaybackInfo info) {
    }

    public void onCaptioningEnabledChanged(boolean enabled) {
    }

    public void onRepeatModeChanged(int repeatMode) {
    }

    public void onShuffleModeChanged(int shuffleMode) {
    }

    public void binderDied() {
        onSessionDestroyed();
    }

    void setHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MessageHandler(handler.getLooper());
            this.mHandler.mRegistered = true;
        } else if (this.mHandler != null) {
            this.mHandler.mRegistered = false;
            this.mHandler.removeCallbacksAndMessages(null);
            this.mHandler = null;
        }
    }

    void postToHandler(int what, Object obj, Bundle data) {
        if (this.mHandler != null) {
            Message msg = this.mHandler.obtainMessage(what, obj);
            msg.setData(data);
            msg.sendToTarget();
        }
    }
}
