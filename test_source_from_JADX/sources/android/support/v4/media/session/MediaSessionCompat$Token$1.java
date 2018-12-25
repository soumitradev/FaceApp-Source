package android.support.v4.media.session;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.session.MediaSessionCompat.Token;

class MediaSessionCompat$Token$1 implements Creator<Token> {
    MediaSessionCompat$Token$1() {
    }

    public Token createFromParcel(Parcel in) {
        Object inner;
        if (VERSION.SDK_INT >= 21) {
            inner = in.readParcelable(null);
        } else {
            inner = in.readStrongBinder();
        }
        return new Token(inner);
    }

    public Token[] newArray(int size) {
        return new Token[size];
    }
}
