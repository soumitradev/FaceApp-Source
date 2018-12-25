package android.support.v4.media;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;

class MediaDescriptionCompat$1 implements Creator<MediaDescriptionCompat> {
    MediaDescriptionCompat$1() {
    }

    public MediaDescriptionCompat createFromParcel(Parcel in) {
        if (VERSION.SDK_INT < 21) {
            return new MediaDescriptionCompat(in);
        }
        return MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(in));
    }

    public MediaDescriptionCompat[] newArray(int size) {
        return new MediaDescriptionCompat[size];
    }
}
