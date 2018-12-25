package android.support.v4.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.AbsSavedState;

protected class DrawerLayout$SavedState extends AbsSavedState {
    public static final Creator<DrawerLayout$SavedState> CREATOR = new C02171();
    int lockModeEnd;
    int lockModeLeft;
    int lockModeRight;
    int lockModeStart;
    int openDrawerGravity = 0;

    /* renamed from: android.support.v4.widget.DrawerLayout$SavedState$1 */
    static class C02171 implements ClassLoaderCreator<DrawerLayout$SavedState> {
        C02171() {
        }

        public DrawerLayout$SavedState createFromParcel(Parcel in, ClassLoader loader) {
            return new DrawerLayout$SavedState(in, loader);
        }

        public DrawerLayout$SavedState createFromParcel(Parcel in) {
            return new DrawerLayout$SavedState(in, null);
        }

        public DrawerLayout$SavedState[] newArray(int size) {
            return new DrawerLayout$SavedState[size];
        }
    }

    public DrawerLayout$SavedState(@NonNull Parcel in, @Nullable ClassLoader loader) {
        super(in, loader);
        this.openDrawerGravity = in.readInt();
        this.lockModeLeft = in.readInt();
        this.lockModeRight = in.readInt();
        this.lockModeStart = in.readInt();
        this.lockModeEnd = in.readInt();
    }

    public DrawerLayout$SavedState(@NonNull Parcelable superState) {
        super(superState);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.openDrawerGravity);
        dest.writeInt(this.lockModeLeft);
        dest.writeInt(this.lockModeRight);
        dest.writeInt(this.lockModeStart);
        dest.writeInt(this.lockModeEnd);
    }
}
