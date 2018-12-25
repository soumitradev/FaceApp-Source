package android.support.v4.widget;

import android.support.v4.widget.ViewDragHelper.Callback;
import android.view.View;

class SlidingPaneLayout$DragHelperCallback extends Callback {
    final /* synthetic */ SlidingPaneLayout this$0;

    SlidingPaneLayout$DragHelperCallback(SlidingPaneLayout slidingPaneLayout) {
        this.this$0 = slidingPaneLayout;
    }

    public boolean tryCaptureView(View child, int pointerId) {
        if (this.this$0.mIsUnableToDrag) {
            return false;
        }
        return ((SlidingPaneLayout$LayoutParams) child.getLayoutParams()).slideable;
    }

    public void onViewDragStateChanged(int state) {
        if (this.this$0.mDragHelper.getViewDragState() != 0) {
            return;
        }
        if (this.this$0.mSlideOffset == 0.0f) {
            this.this$0.updateObscuredViewsVisibility(this.this$0.mSlideableView);
            this.this$0.dispatchOnPanelClosed(this.this$0.mSlideableView);
            this.this$0.mPreservedOpenState = false;
            return;
        }
        this.this$0.dispatchOnPanelOpened(this.this$0.mSlideableView);
        this.this$0.mPreservedOpenState = true;
    }

    public void onViewCaptured(View capturedChild, int activePointerId) {
        this.this$0.setAllChildrenVisible();
    }

    public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
        this.this$0.onPanelDragged(left);
        this.this$0.invalidate();
    }

    public void onViewReleased(View releasedChild, float xvel, float yvel) {
        int left;
        SlidingPaneLayout$LayoutParams lp = (SlidingPaneLayout$LayoutParams) releasedChild.getLayoutParams();
        int startToRight;
        if (this.this$0.isLayoutRtlSupport()) {
            startToRight = this.this$0.getPaddingRight() + lp.rightMargin;
            if (xvel < 0.0f || (xvel == 0.0f && this.this$0.mSlideOffset > 0.5f)) {
                startToRight += this.this$0.mSlideRange;
            }
            left = (this.this$0.getWidth() - startToRight) - this.this$0.mSlideableView.getWidth();
        } else {
            startToRight = this.this$0.getPaddingLeft() + lp.leftMargin;
            if (xvel <= 0.0f) {
                if (xvel != 0.0f || this.this$0.mSlideOffset <= 0.5f) {
                    left = startToRight;
                }
            }
            left = startToRight + this.this$0.mSlideRange;
        }
        this.this$0.mDragHelper.settleCapturedViewAt(left, releasedChild.getTop());
        this.this$0.invalidate();
    }

    public int getViewHorizontalDragRange(View child) {
        return this.this$0.mSlideRange;
    }

    public int clampViewPositionHorizontal(View child, int left, int dx) {
        SlidingPaneLayout$LayoutParams lp = (SlidingPaneLayout$LayoutParams) this.this$0.mSlideableView.getLayoutParams();
        if (this.this$0.isLayoutRtlSupport()) {
            int startBound = this.this$0.getWidth() - ((this.this$0.getPaddingRight() + lp.rightMargin) + this.this$0.mSlideableView.getWidth());
            return Math.max(Math.min(left, startBound), startBound - this.this$0.mSlideRange);
        }
        startBound = this.this$0.getPaddingLeft() + lp.leftMargin;
        return Math.min(Math.max(left, startBound), this.this$0.mSlideRange + startBound);
    }

    public int clampViewPositionVertical(View child, int top, int dy) {
        return child.getTop();
    }

    public void onEdgeDragStarted(int edgeFlags, int pointerId) {
        this.this$0.mDragHelper.captureChildView(this.this$0.mSlideableView, pointerId);
    }
}
