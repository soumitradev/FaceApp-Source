package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;

class MediaSessionCompat$QueueItem$1 implements Creator<QueueItem> {
    MediaSessionCompat$QueueItem$1() {
    }

    public QueueItem createFromParcel(Parcel p) {
        return new QueueItem(p);
    }

    public QueueItem[] newArray(int size) {
        return new QueueItem[size];
    }
}
