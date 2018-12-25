package android.support.v7.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.v4.view.AbsSavedState;

public class Toolbar$SavedState extends AbsSavedState {
    public static final Creator<Toolbar$SavedState> CREATOR = new C02961();
    int expandedMenuItemId;
    boolean isOverflowOpen;

    /* renamed from: android.support.v7.widget.Toolbar$SavedState$1 */
    static class C02961 implements ClassLoaderCreator<Toolbar$SavedState> {
        C02961() {
        }

        public Toolbar$SavedState createFromParcel(Parcel in, ClassLoader loader) {
            return new Toolbar$SavedState(in, loader);
        }

        public Toolbar$SavedState createFromParcel(Parcel in) {
            return new Toolbar$SavedState(in, null);
        }

        public Toolbar$SavedState[] newArray(int size) {
            return new Toolbar$SavedState[size];
        }
    }

    public Toolbar$SavedState(Parcel source) {
        this(source, null);
    }

    public Toolbar$SavedState(Parcel source, ClassLoader loader) {
        super(source, loader);
        this.expandedMenuItemId = source.readInt();
        this.isOverflowOpen = source.readInt() != 0;
    }

    public Toolbar$SavedState(Parcelable superState) {
        super(superState);
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(this.expandedMenuItemId);
        out.writeInt(this.isOverflowOpen);
    }
}
