package org.catrobat.catroid.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;

public enum ScratchVisibilityState implements Parcelable {
    UNKNOWN(0),
    PRIVATE(1),
    PUBLIC(2);
    
    public static final Creator<ScratchVisibilityState> CREATOR = null;
    private static SparseArray<ScratchVisibilityState> visibilityStates;
    private int visibilityState;

    /* renamed from: org.catrobat.catroid.common.ScratchVisibilityState$1 */
    static class C17601 implements Creator<ScratchVisibilityState> {
        C17601() {
        }

        public ScratchVisibilityState createFromParcel(Parcel source) {
            return ScratchVisibilityState.values()[source.readInt()];
        }

        public ScratchVisibilityState[] newArray(int size) {
            return new ScratchVisibilityState[size];
        }
    }

    static {
        visibilityStates = new SparseArray();
        ScratchVisibilityState[] values = values();
        int length = values.length;
        int i;
        while (i < length) {
            ScratchVisibilityState legEnum = values[i];
            visibilityStates.put(legEnum.visibilityState, legEnum);
            i++;
        }
        CREATOR = new C17601();
    }

    private ScratchVisibilityState(int visibilityState) {
        this.visibilityState = visibilityState;
    }

    public static ScratchVisibilityState valueOf(int visibilityState) {
        return (ScratchVisibilityState) visibilityStates.get(visibilityState);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }
}
