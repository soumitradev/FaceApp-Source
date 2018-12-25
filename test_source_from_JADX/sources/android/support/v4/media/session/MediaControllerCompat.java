package android.support.v4.media.session;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.SupportActivity;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import java.util.HashSet;
import java.util.List;

public final class MediaControllerCompat {
    static final String COMMAND_ADD_QUEUE_ITEM = "android.support.v4.media.session.command.ADD_QUEUE_ITEM";
    static final String COMMAND_ADD_QUEUE_ITEM_AT = "android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT";
    static final String COMMAND_ARGUMENT_INDEX = "android.support.v4.media.session.command.ARGUMENT_INDEX";
    static final String COMMAND_ARGUMENT_MEDIA_DESCRIPTION = "android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION";
    static final String COMMAND_GET_EXTRA_BINDER = "android.support.v4.media.session.command.GET_EXTRA_BINDER";
    static final String COMMAND_REMOVE_QUEUE_ITEM = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM";
    static final String COMMAND_REMOVE_QUEUE_ITEM_AT = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT";
    static final String TAG = "MediaControllerCompat";
    private final MediaControllerCompat$MediaControllerImpl mImpl;
    private final HashSet<MediaControllerCompat$Callback> mRegisteredCallbacks = new HashSet();
    private final Token mToken;

    public static void setMediaController(@NonNull Activity activity, MediaControllerCompat mediaController) {
        if (activity instanceof SupportActivity) {
            ((SupportActivity) activity).putExtraData(new MediaControllerCompat$MediaControllerExtraData(mediaController));
        }
        if (VERSION.SDK_INT >= 21) {
            Object controllerObj = null;
            if (mediaController != null) {
                controllerObj = MediaControllerCompatApi21.fromToken(activity, mediaController.getSessionToken().getToken());
            }
            MediaControllerCompatApi21.setMediaController(activity, controllerObj);
        }
    }

    public static MediaControllerCompat getMediaController(@NonNull Activity activity) {
        MediaControllerCompat mediaControllerCompat = null;
        if (activity instanceof SupportActivity) {
            MediaControllerCompat$MediaControllerExtraData extraData = (MediaControllerCompat$MediaControllerExtraData) ((SupportActivity) activity).getExtraData(MediaControllerCompat$MediaControllerExtraData.class);
            if (extraData != null) {
                mediaControllerCompat = extraData.getMediaController();
            }
            return mediaControllerCompat;
        }
        if (VERSION.SDK_INT >= 21) {
            Object controllerObj = MediaControllerCompatApi21.getMediaController(activity);
            if (controllerObj == null) {
                return null;
            }
            try {
                return new MediaControllerCompat((Context) activity, Token.fromToken(MediaControllerCompatApi21.getSessionToken(controllerObj)));
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in getMediaController.", e);
            }
        }
        return null;
    }

    private static void validateCustomAction(String action, Bundle args) {
        if (action != null) {
            Object obj = -1;
            int hashCode = action.hashCode();
            if (hashCode != -1348483723) {
                if (hashCode == 503011406) {
                    if (action.equals(MediaSessionCompat.ACTION_UNFOLLOW)) {
                        obj = 1;
                    }
                }
            } else if (action.equals(MediaSessionCompat.ACTION_FOLLOW)) {
                obj = null;
            }
            switch (obj) {
                case null:
                case 1:
                    if (args == null || !args.containsKey(MediaSessionCompat.ARGUMENT_MEDIA_ATTRIBUTE)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("An extra field android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE is required for this action ");
                        stringBuilder.append(action);
                        stringBuilder.append(".");
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                default:
                    break;
            }
        }
    }

    public MediaControllerCompat(Context context, @NonNull MediaSessionCompat session) {
        if (session == null) {
            throw new IllegalArgumentException("session must not be null");
        }
        this.mToken = session.getSessionToken();
        if (VERSION.SDK_INT >= 24) {
            this.mImpl = new MediaControllerCompat$MediaControllerImplApi24(context, session);
        } else if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaControllerCompat$MediaControllerImplApi23(context, session);
        } else if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaControllerCompat$MediaControllerImplApi21(context, session);
        } else {
            this.mImpl = new MediaControllerCompat$MediaControllerImplBase(this.mToken);
        }
    }

    public MediaControllerCompat(Context context, @NonNull Token sessionToken) throws RemoteException {
        if (sessionToken == null) {
            throw new IllegalArgumentException("sessionToken must not be null");
        }
        this.mToken = sessionToken;
        if (VERSION.SDK_INT >= 24) {
            this.mImpl = new MediaControllerCompat$MediaControllerImplApi24(context, sessionToken);
        } else if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaControllerCompat$MediaControllerImplApi23(context, sessionToken);
        } else if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaControllerCompat$MediaControllerImplApi21(context, sessionToken);
        } else {
            this.mImpl = new MediaControllerCompat$MediaControllerImplBase(this.mToken);
        }
    }

    public MediaControllerCompat$TransportControls getTransportControls() {
        return this.mImpl.getTransportControls();
    }

    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent != null) {
            return this.mImpl.dispatchMediaButtonEvent(keyEvent);
        }
        throw new IllegalArgumentException("KeyEvent may not be null");
    }

    public PlaybackStateCompat getPlaybackState() {
        return this.mImpl.getPlaybackState();
    }

    public MediaMetadataCompat getMetadata() {
        return this.mImpl.getMetadata();
    }

    public List<QueueItem> getQueue() {
        return this.mImpl.getQueue();
    }

    public void addQueueItem(MediaDescriptionCompat description) {
        this.mImpl.addQueueItem(description);
    }

    public void addQueueItem(MediaDescriptionCompat description, int index) {
        this.mImpl.addQueueItem(description, index);
    }

    public void removeQueueItem(MediaDescriptionCompat description) {
        this.mImpl.removeQueueItem(description);
    }

    @Deprecated
    public void removeQueueItemAt(int index) {
        List<QueueItem> queue = getQueue();
        if (queue != null && index >= 0 && index < queue.size()) {
            QueueItem item = (QueueItem) queue.get(index);
            if (item != null) {
                removeQueueItem(item.getDescription());
            }
        }
    }

    public CharSequence getQueueTitle() {
        return this.mImpl.getQueueTitle();
    }

    public Bundle getExtras() {
        return this.mImpl.getExtras();
    }

    public int getRatingType() {
        return this.mImpl.getRatingType();
    }

    public boolean isCaptioningEnabled() {
        return this.mImpl.isCaptioningEnabled();
    }

    public int getRepeatMode() {
        return this.mImpl.getRepeatMode();
    }

    public int getShuffleMode() {
        return this.mImpl.getShuffleMode();
    }

    public long getFlags() {
        return this.mImpl.getFlags();
    }

    public MediaControllerCompat$PlaybackInfo getPlaybackInfo() {
        return this.mImpl.getPlaybackInfo();
    }

    public PendingIntent getSessionActivity() {
        return this.mImpl.getSessionActivity();
    }

    public Token getSessionToken() {
        return this.mToken;
    }

    public void setVolumeTo(int value, int flags) {
        this.mImpl.setVolumeTo(value, flags);
    }

    public void adjustVolume(int direction, int flags) {
        this.mImpl.adjustVolume(direction, flags);
    }

    public void registerCallback(@NonNull MediaControllerCompat$Callback callback) {
        registerCallback(callback, null);
    }

    public void registerCallback(@NonNull MediaControllerCompat$Callback callback, Handler handler) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        if (handler == null) {
            handler = new Handler();
        }
        callback.setHandler(handler);
        this.mImpl.registerCallback(callback, handler);
        this.mRegisteredCallbacks.add(callback);
    }

    public void unregisterCallback(@NonNull MediaControllerCompat$Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        try {
            this.mRegisteredCallbacks.remove(callback);
            this.mImpl.unregisterCallback(callback);
        } finally {
            callback.setHandler(null);
        }
    }

    public void sendCommand(@NonNull String command, Bundle params, ResultReceiver cb) {
        if (TextUtils.isEmpty(command)) {
            throw new IllegalArgumentException("command must neither be null nor empty");
        }
        this.mImpl.sendCommand(command, params, cb);
    }

    public boolean isSessionReady() {
        return this.mImpl.isSessionReady();
    }

    public String getPackageName() {
        return this.mImpl.getPackageName();
    }

    public Object getMediaController() {
        return this.mImpl.getMediaController();
    }
}
