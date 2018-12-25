package android.support.v4.os;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class ResultReceiver$1 implements Creator<ResultReceiver> {
    ResultReceiver$1() {
    }

    public ResultReceiver createFromParcel(Parcel in) {
        return new ResultReceiver(in);
    }

    public ResultReceiver[] newArray(int size) {
        return new ResultReceiver[size];
    }
}
