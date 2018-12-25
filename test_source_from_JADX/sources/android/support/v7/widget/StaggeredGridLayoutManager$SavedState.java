package android.support.v7.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.util.List;

@RestrictTo({Scope.LIBRARY_GROUP})
public class StaggeredGridLayoutManager$SavedState implements Parcelable {
    public static final Creator<StaggeredGridLayoutManager$SavedState> CREATOR = new C02941();
    boolean mAnchorLayoutFromEnd;
    int mAnchorPosition;
    List<FullSpanItem> mFullSpanItems;
    boolean mLastLayoutRTL;
    boolean mReverseLayout;
    int[] mSpanLookup;
    int mSpanLookupSize;
    int[] mSpanOffsets;
    int mSpanOffsetsSize;
    int mVisibleAnchorPosition;

    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$SavedState$1 */
    static class C02941 implements Creator<StaggeredGridLayoutManager$SavedState> {
        C02941() {
        }

        public StaggeredGridLayoutManager$SavedState createFromParcel(Parcel in) {
            return new StaggeredGridLayoutManager$SavedState(in);
        }

        public StaggeredGridLayoutManager$SavedState[] newArray(int size) {
            return new StaggeredGridLayoutManager$SavedState[size];
        }
    }

    StaggeredGridLayoutManager$SavedState(Parcel in) {
        this.mAnchorPosition = in.readInt();
        this.mVisibleAnchorPosition = in.readInt();
        this.mSpanOffsetsSize = in.readInt();
        if (this.mSpanOffsetsSize > 0) {
            this.mSpanOffsets = new int[this.mSpanOffsetsSize];
            in.readIntArray(this.mSpanOffsets);
        }
        this.mSpanLookupSize = in.readInt();
        if (this.mSpanLookupSize > 0) {
            this.mSpanLookup = new int[this.mSpanLookupSize];
            in.readIntArray(this.mSpanLookup);
        }
        boolean z = false;
        this.mReverseLayout = in.readInt() == 1;
        this.mAnchorLayoutFromEnd = in.readInt() == 1;
        if (in.readInt() == 1) {
            z = true;
        }
        this.mLastLayoutRTL = z;
        this.mFullSpanItems = in.readArrayList(FullSpanItem.class.getClassLoader());
    }

    public StaggeredGridLayoutManager$SavedState(StaggeredGridLayoutManager$SavedState other) {
        this.mSpanOffsetsSize = other.mSpanOffsetsSize;
        this.mAnchorPosition = other.mAnchorPosition;
        this.mVisibleAnchorPosition = other.mVisibleAnchorPosition;
        this.mSpanOffsets = other.mSpanOffsets;
        this.mSpanLookupSize = other.mSpanLookupSize;
        this.mSpanLookup = other.mSpanLookup;
        this.mReverseLayout = other.mReverseLayout;
        this.mAnchorLayoutFromEnd = other.mAnchorLayoutFromEnd;
        this.mLastLayoutRTL = other.mLastLayoutRTL;
        this.mFullSpanItems = other.mFullSpanItems;
    }

    void invalidateSpanInfo() {
        this.mSpanOffsets = null;
        this.mSpanOffsetsSize = 0;
        this.mSpanLookupSize = 0;
        this.mSpanLookup = null;
        this.mFullSpanItems = null;
    }

    void invalidateAnchorPositionInfo() {
        this.mSpanOffsets = null;
        this.mSpanOffsetsSize = 0;
        this.mAnchorPosition = -1;
        this.mVisibleAnchorPosition = -1;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mAnchorPosition);
        dest.writeInt(this.mVisibleAnchorPosition);
        dest.writeInt(this.mSpanOffsetsSize);
        if (this.mSpanOffsetsSize > 0) {
            dest.writeIntArray(this.mSpanOffsets);
        }
        dest.writeInt(this.mSpanLookupSize);
        if (this.mSpanLookupSize > 0) {
            dest.writeIntArray(this.mSpanLookup);
        }
        dest.writeInt(this.mReverseLayout);
        dest.writeInt(this.mAnchorLayoutFromEnd);
        dest.writeInt(this.mLastLayoutRTL);
        dest.writeList(this.mFullSpanItems);
    }
}
