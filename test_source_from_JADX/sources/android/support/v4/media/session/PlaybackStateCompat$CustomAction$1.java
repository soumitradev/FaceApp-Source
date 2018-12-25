package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.session.PlaybackStateCompat.CustomAction;

class PlaybackStateCompat$CustomAction$1 implements Creator<CustomAction> {
    PlaybackStateCompat$CustomAction$1() {
    }

    public CustomAction createFromParcel(Parcel p) {
        return new CustomAction(p);
    }

    public CustomAction[] newArray(int size) {
        return new CustomAction[size];
    }
}
