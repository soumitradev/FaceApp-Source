package android.support.v4.widget;

import android.support.v4.widget.ViewDragHelper.Callback;
import android.view.View;

class DrawerLayout$ViewDragCallback extends Callback {
    private final int mAbsGravity;
    private ViewDragHelper mDragger;
    private final Runnable mPeekRunnable = new C02181();
    final /* synthetic */ DrawerLayout this$0;

    /* renamed from: android.support.v4.widget.DrawerLayout$ViewDragCallback$1 */
    class C02181 implements Runnable {
        C02181() {
        }

        public void run() {
            DrawerLayout$ViewDragCallback.this.peekDrawer();
        }
    }

    DrawerLayout$ViewDragCallback(DrawerLayout drawerLayout, int gravity) {
        this.this$0 = drawerLayout;
        this.mAbsGravity = gravity;
    }

    public void setDragger(ViewDragHelper dragger) {
        this.mDragger = dragger;
    }

    public void removeCallbacks() {
        this.this$0.removeCallbacks(this.mPeekRunnable);
    }

    public boolean tryCaptureView(View child, int pointerId) {
        return this.this$0.isDrawerView(child) && this.this$0.checkDrawerViewAbsoluteGravity(child, this.mAbsGravity) && this.this$0.getDrawerLockMode(child) == 0;
    }

    public void onViewDragStateChanged(int state) {
        this.this$0.updateDrawerState(this.mAbsGravity, state, this.mDragger.getCapturedView());
    }

    public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
        float offset;
        int childWidth = changedView.getWidth();
        if (this.this$0.checkDrawerViewAbsoluteGravity(changedView, 3)) {
            offset = ((float) (childWidth + left)) / ((float) childWidth);
        } else {
            offset = ((float) (this.this$0.getWidth() - left)) / ((float) childWidth);
        }
        this.this$0.setDrawerViewOffset(changedView, offset);
        changedView.setVisibility(offset == 0.0f ? 4 : 0);
        this.this$0.invalidate();
    }

    public void onViewCaptured(View capturedChild, int activePointerId) {
        ((DrawerLayout$LayoutParams) capturedChild.getLayoutParams()).isPeeking = false;
        closeOtherDrawer();
    }

    private void closeOtherDrawer() {
        int i = 3;
        if (this.mAbsGravity == 3) {
            i = 5;
        }
        View toClose = this.this$0.findDrawerWithGravity(i);
        if (toClose != null) {
            this.this$0.closeDrawer(toClose);
        }
    }

    public void onViewReleased(View releasedChild, float xvel, float yvel) {
        int i;
        float offset = this.this$0.getDrawerViewOffset(releasedChild);
        int childWidth = releasedChild.getWidth();
        if (this.this$0.checkDrawerViewAbsoluteGravity(releasedChild, 3)) {
            if (xvel <= 0.0f) {
                if (xvel != 0.0f || offset <= 0.5f) {
                    i = -childWidth;
                }
            }
            i = 0;
        } else {
            int i2;
            i = this.this$0.getWidth();
            if (xvel >= 0.0f) {
                if (xvel != 0.0f || offset <= 0.5f) {
                    i2 = i;
                    i = i2;
                }
            }
            i2 = i - childWidth;
            i = i2;
        }
        this.mDragger.settleCapturedViewAt(i, releasedChild.getTop());
        this.this$0.invalidate();
    }

    public void onEdgeTouched(int edgeFlags, int pointerId) {
        this.this$0.postDelayed(this.mPeekRunnable, 160);
    }

    void peekDrawer() {
        View toCapture;
        int peekDistance = this.mDragger.getEdgeSize();
        int childLeft = 0;
        boolean leftEdge = this.mAbsGravity == 3;
        if (leftEdge) {
            toCapture = this.this$0.findDrawerWithGravity(3);
            if (toCapture != null) {
                childLeft = -toCapture.getWidth();
            }
            childLeft += peekDistance;
        } else {
            toCapture = this.this$0.findDrawerWithGravity(5);
            childLeft = this.this$0.getWidth() - peekDistance;
        }
        if (toCapture == null) {
            return;
        }
        if (((leftEdge && toCapture.getLeft() < childLeft) || (!leftEdge && toCapture.getLeft() > childLeft)) && this.this$0.getDrawerLockMode(toCapture) == 0) {
            DrawerLayout$LayoutParams lp = (DrawerLayout$LayoutParams) toCapture.getLayoutParams();
            this.mDragger.smoothSlideViewTo(toCapture, childLeft, toCapture.getTop());
            lp.isPeeking = true;
            this.this$0.invalidate();
            closeOtherDrawer();
            this.this$0.cancelChildViewTouch();
        }
    }

    public boolean onEdgeLock(int edgeFlags) {
        return false;
    }

    public void onEdgeDragStarted(int edgeFlags, int pointerId) {
        View toCapture;
        if ((edgeFlags & 1) == 1) {
            toCapture = this.this$0.findDrawerWithGravity(3);
        } else {
            toCapture = this.this$0.findDrawerWithGravity(5);
        }
        if (toCapture != null && this.this$0.getDrawerLockMode(toCapture) == 0) {
            this.mDragger.captureChildView(toCapture, pointerId);
        }
    }

    public int getViewHorizontalDragRange(View child) {
        return this.this$0.isDrawerView(child) ? child.getWidth() : 0;
    }

    public int clampViewPositionHorizontal(View child, int left, int dx) {
        if (this.this$0.checkDrawerViewAbsoluteGravity(child, 3)) {
            return Math.max(-child.getWidth(), Math.min(left, 0));
        }
        int width = this.this$0.getWidth();
        return Math.max(width - child.getWidth(), Math.min(left, width));
    }

    public int clampViewPositionVertical(View child, int top, int dy) {
        return child.getTop();
    }
}
