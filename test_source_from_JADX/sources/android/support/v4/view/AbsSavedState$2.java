package android.support.v4.view;

import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;

class AbsSavedState$2 implements ClassLoaderCreator<AbsSavedState> {
    AbsSavedState$2() {
    }

    public AbsSavedState createFromParcel(Parcel in, ClassLoader loader) {
        if (in.readParcelable(loader) == null) {
            return AbsSavedState.EMPTY_STATE;
        }
        throw new IllegalStateException("superState must be null");
    }

    public AbsSavedState createFromParcel(Parcel in) {
        return createFromParcel(in, null);
    }

    public AbsSavedState[] newArray(int size) {
        return new AbsSavedState[size];
    }
}
