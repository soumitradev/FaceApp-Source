package android.support.v4.view;

import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;
import android.support.v4.view.ViewPager.SavedState;

class ViewPager$SavedState$1 implements ClassLoaderCreator<SavedState> {
    ViewPager$SavedState$1() {
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
