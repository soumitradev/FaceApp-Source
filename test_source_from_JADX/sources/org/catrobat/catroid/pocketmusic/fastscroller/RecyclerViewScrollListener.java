package org.catrobat.catroid.pocketmusic.fastscroller;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

public class RecyclerViewScrollListener extends OnScrollListener {
    private int oldScrollState = 0;
    private final FastScroller scroller;

    public RecyclerViewScrollListener(FastScroller scroller) {
        this.scroller = scroller;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newScrollState) {
        if (newScrollState == 0 && this.oldScrollState != 0) {
            this.scroller.getViewProvider().onScrollFinished();
        } else if (newScrollState != 0 && this.oldScrollState == 0) {
            this.scroller.getViewProvider().onScrollStarted();
        }
        this.oldScrollState = newScrollState;
        super.onScrollStateChanged(recyclerView, newScrollState);
    }

    public void onScrolled(RecyclerView rv, int dx, int dy) {
        if (this.scroller.shouldUpdateHandlePosition()) {
            updateHandlePosition(rv);
        }
        super.onScrolled(rv, dx, dy);
    }

    void updateHandlePosition(RecyclerView rv) {
        float relativePos;
        if (this.scroller.isVertical()) {
            relativePos = ((float) rv.computeVerticalScrollOffset()) / ((float) (rv.computeVerticalScrollRange() - rv.computeVerticalScrollExtent()));
        } else {
            relativePos = ((float) rv.computeHorizontalScrollOffset()) / ((float) (rv.computeHorizontalScrollRange() - rv.computeHorizontalScrollExtent()));
        }
        this.scroller.setScrollerPosition(relativePos);
    }
}
