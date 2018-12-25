package android.support.v7.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;

public class StaggeredGridLayoutManager$LayoutParams extends LayoutParams {
    public static final int INVALID_SPAN_ID = -1;
    boolean mFullSpan;
    StaggeredGridLayoutManager$Span mSpan;

    public StaggeredGridLayoutManager$LayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public StaggeredGridLayoutManager$LayoutParams(int width, int height) {
        super(width, height);
    }

    public StaggeredGridLayoutManager$LayoutParams(MarginLayoutParams source) {
        super(source);
    }

    public StaggeredGridLayoutManager$LayoutParams(ViewGroup.LayoutParams source) {
        super(source);
    }

    public StaggeredGridLayoutManager$LayoutParams(LayoutParams source) {
        super(source);
    }

    public void setFullSpan(boolean fullSpan) {
        this.mFullSpan = fullSpan;
    }

    public boolean isFullSpan() {
        return this.mFullSpan;
    }

    public final int getSpanIndex() {
        if (this.mSpan == null) {
            return -1;
        }
        return this.mSpan.mIndex;
    }
}
