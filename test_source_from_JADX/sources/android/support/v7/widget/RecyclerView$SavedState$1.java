package android.support.v7.widget;

import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;
import android.support.v7.widget.RecyclerView.SavedState;

class RecyclerView$SavedState$1 implements ClassLoaderCreator<SavedState> {
    RecyclerView$SavedState$1() {
    }

    public SavedState createFromParcel(Parcel in, ClassLoader loader) {
        return new SavedState(in, loader);
    }

    public SavedState createFromParcel(Parcel in) {
        return new SavedState(in, null);
    }

    public SavedState[] newArray(int size) {
        return new SavedState[size];
    }
}
