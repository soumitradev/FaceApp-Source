package android.support.v4.media.session;

import android.media.MediaDescription;
import android.media.session.MediaSession.QueueItem;

class MediaSessionCompatApi21$QueueItem {
    MediaSessionCompatApi21$QueueItem() {
    }

    public static Object createItem(Object mediaDescription, long id) {
        return new QueueItem((MediaDescription) mediaDescription, id);
    }

    public static Object getDescription(Object queueItem) {
        return ((QueueItem) queueItem).getDescription();
    }

    public static long getQueueId(Object queueItem) {
        return ((QueueItem) queueItem).getQueueId();
    }
}
