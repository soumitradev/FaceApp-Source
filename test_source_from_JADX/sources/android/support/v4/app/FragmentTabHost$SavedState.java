package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View.BaseSavedState;

class FragmentTabHost$SavedState extends BaseSavedState {
    public static final Creator<FragmentTabHost$SavedState> CREATOR = new C01631();
    String curTab;

    /* renamed from: android.support.v4.app.FragmentTabHost$SavedState$1 */
    static class C01631 implements Creator<FragmentTabHost$SavedState> {
        C01631() {
        }

        public FragmentTabHost$SavedState createFromParcel(Parcel in) {
            return new FragmentTabHost$SavedState(in);
        }

        public FragmentTabHost$SavedState[] newArray(int size) {
            return new FragmentTabHost$SavedState[size];
        }
    }

    FragmentTabHost$SavedState(Parcelable superState) {
        super(superState);
    }

    FragmentTabHost$SavedState(Parcel in) {
        super(in);
        this.curTab = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(this.curTab);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FragmentTabHost.SavedState{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" curTab=");
        stringBuilder.append(this.curTab);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
