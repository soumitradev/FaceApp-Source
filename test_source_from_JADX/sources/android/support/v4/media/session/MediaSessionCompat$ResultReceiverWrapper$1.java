package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.session.MediaSessionCompat.ResultReceiverWrapper;

class MediaSessionCompat$ResultReceiverWrapper$1 implements Creator<ResultReceiverWrapper> {
    MediaSessionCompat$ResultReceiverWrapper$1() {
    }

    public ResultReceiverWrapper createFromParcel(Parcel p) {
        return new ResultReceiverWrapper(p);
    }

    public ResultReceiverWrapper[] newArray(int size) {
        return new ResultReceiverWrapper[size];
    }
}
