package android.support.v4.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.v4.view.AbsSavedState;

class SlidingPaneLayout$SavedState extends AbsSavedState {
    public static final Creator<SlidingPaneLayout$SavedState> CREATOR = new C02201();
    boolean isOpen;

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$SavedState$1 */
    static class C02201 implements ClassLoaderCreator<SlidingPaneLayout$SavedState> {
        C02201() {
        }

        public SlidingPaneLayout$SavedState createFromParcel(Parcel in, ClassLoader loader) {
            return new SlidingPaneLayout$SavedState(in, null);
        }

        public SlidingPaneLayout$SavedState createFromParcel(Parcel in) {
            return new SlidingPaneLayout$SavedState(in, null);
        }

        public SlidingPaneLayout$SavedState[] newArray(int size) {
            return new SlidingPaneLayout$SavedState[size];
        }
    }

    SlidingPaneLayout$SavedState(Parcelable superState) {
        super(superState);
    }

    SlidingPaneLayout$SavedState(Parcel in, ClassLoader loader) {
        super(in, loader);
        this.isOpen = in.readInt() != 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(this.isOpen);
    }
}
