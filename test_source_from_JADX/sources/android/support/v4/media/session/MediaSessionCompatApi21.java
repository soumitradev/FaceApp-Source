package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.media.AudioAttributes.Builder;
import android.media.MediaMetadata;
import android.media.VolumeProvider;
import android.media.session.MediaSession;
import android.media.session.MediaSession.Callback;
import android.media.session.MediaSession.QueueItem;
import android.media.session.MediaSession.Token;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(21)
class MediaSessionCompatApi21 {
    static final String TAG = "MediaSessionCompatApi21";

    MediaSessionCompatApi21() {
    }

    public static Object createSession(Context context, String tag) {
        return new MediaSession(context, tag);
    }

    public static Object verifySession(Object mediaSession) {
        if (mediaSession instanceof MediaSession) {
            return mediaSession;
        }
        throw new IllegalArgumentException("mediaSession is not a valid MediaSession object");
    }

    public static Object verifyToken(Object token) {
        if (token instanceof Token) {
            return token;
        }
        throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
    }

    public static Object createCallback(MediaSessionCompatApi21$Callback callback) {
        return new MediaSessionCompatApi21$CallbackProxy(callback);
    }

    public static void setCallback(Object sessionObj, Object callbackObj, Handler handler) {
        ((MediaSession) sessionObj).setCallback((Callback) callbackObj, handler);
    }

    public static void setFlags(Object sessionObj, int flags) {
        ((MediaSession) sessionObj).setFlags(flags);
    }

    public static void setPlaybackToLocal(Object sessionObj, int stream) {
        Builder bob = new Builder();
        bob.setLegacyStreamType(stream);
        ((MediaSession) sessionObj).setPlaybackToLocal(bob.build());
    }

    public static void setPlaybackToRemote(Object sessionObj, Object volumeProviderObj) {
        ((MediaSession) sessionObj).setPlaybackToRemote((VolumeProvider) volumeProviderObj);
    }

    public static void setActive(Object sessionObj, boolean active) {
        ((MediaSession) sessionObj).setActive(active);
    }

    public static boolean isActive(Object sessionObj) {
        return ((MediaSession) sessionObj).isActive();
    }

    public static void sendSessionEvent(Object sessionObj, String event, Bundle extras) {
        ((MediaSession) sessionObj).sendSessionEvent(event, extras);
    }

    public static void release(Object sessionObj) {
        ((MediaSession) sessionObj).release();
    }

    public static Parcelable getSessionToken(Object sessionObj) {
        return ((MediaSession) sessionObj).getSessionToken();
    }

    public static void setPlaybackState(Object sessionObj, Object stateObj) {
        ((MediaSession) sessionObj).setPlaybackState((PlaybackState) stateObj);
    }

    public static void setMetadata(Object sessionObj, Object metadataObj) {
        ((MediaSession) sessionObj).setMetadata((MediaMetadata) metadataObj);
    }

    public static void setSessionActivity(Object sessionObj, PendingIntent pi) {
        ((MediaSession) sessionObj).setSessionActivity(pi);
    }

    public static void setMediaButtonReceiver(Object sessionObj, PendingIntent pi) {
        ((MediaSession) sessionObj).setMediaButtonReceiver(pi);
    }

    public static void setQueue(Object sessionObj, List<Object> queueObjs) {
        if (queueObjs == null) {
            ((MediaSession) sessionObj).setQueue(null);
            return;
        }
        ArrayList<QueueItem> queue = new ArrayList();
        Iterator it = queueObjs.iterator();
        while (it.hasNext()) {
            queue.add((QueueItem) it.next());
        }
        ((MediaSession) sessionObj).setQueue(queue);
    }

    public static void setQueueTitle(Object sessionObj, CharSequence title) {
        ((MediaSession) sessionObj).setQueueTitle(title);
    }

    public static void setExtras(Object sessionObj, Bundle extras) {
        ((MediaSession) sessionObj).setExtras(extras);
    }

    public static boolean hasCallback(Object sessionObj) {
        boolean z = false;
        try {
            Field callbackField = sessionObj.getClass().getDeclaredField("mCallback");
            if (callbackField == null) {
                return false;
            }
            callbackField.setAccessible(true);
            if (callbackField.get(sessionObj) != null) {
                z = true;
            }
            return z;
        } catch (NoSuchFieldException e) {
            Log.w(TAG, "Failed to get mCallback object.");
        }
    }
}
