package android.support.design.widget;

import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;
import android.support.design.widget.AppBarLayout.Behavior.SavedState;

class AppBarLayout$Behavior$SavedState$1 implements ClassLoaderCreator<SavedState> {
    AppBarLayout$Behavior$SavedState$1() {
    }

    public SavedState createFromParcel(Parcel source, ClassLoader loader) {
        return new SavedState(source, loader);
    }

    public SavedState createFromParcel(Parcel source) {
        return new SavedState(source, null);
    }

    public SavedState[] newArray(int size) {
        return new SavedState[size];
    }
}
