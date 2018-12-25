package android.support.design.widget;

import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.view.View;
import android.view.ViewParent;

class SwipeDismissBehavior$1 extends Callback {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = -1;
    private int mOriginalCapturedViewLeft;
    final /* synthetic */ SwipeDismissBehavior this$0;

    SwipeDismissBehavior$1(SwipeDismissBehavior this$0) {
        this.this$0 = this$0;
    }

    public boolean tryCaptureView(View child, int pointerId) {
        return this.mActivePointerId == -1 && this.this$0.canSwipeDismissView(child);
    }

    public void onViewCaptured(View capturedChild, int activePointerId) {
        this.mActivePointerId = activePointerId;
        this.mOriginalCapturedViewLeft = capturedChild.getLeft();
        ViewParent parent = capturedChild.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    public void onViewDragStateChanged(int state) {
        if (this.this$0.mListener != null) {
            this.this$0.mListener.onDragStateChanged(state);
        }
    }

    public void onViewReleased(View child, float xvel, float yvel) {
        int targetLeft;
        this.mActivePointerId = -1;
        int childWidth = child.getWidth();
        boolean dismiss = false;
        if (shouldDismiss(child, xvel)) {
            targetLeft = child.getLeft() < this.mOriginalCapturedViewLeft ? this.mOriginalCapturedViewLeft - childWidth : this.mOriginalCapturedViewLeft + childWidth;
            dismiss = true;
        } else {
            targetLeft = this.mOriginalCapturedViewLeft;
        }
        if (this.this$0.mViewDragHelper.settleCapturedViewAt(targetLeft, child.getTop())) {
            ViewCompat.postOnAnimation(child, new SwipeDismissBehavior$SettleRunnable(this.this$0, child, dismiss));
        } else if (dismiss && this.this$0.mListener != null) {
            this.this$0.mListener.onDismiss(child);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean shouldDismiss(android.view.View r7, float r8) {
        /*
        r6 = this;
        r0 = 0;
        r1 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
        r2 = 0;
        r3 = 1;
        if (r1 == 0) goto L_0x0044;
    L_0x0007:
        r1 = android.support.v4.view.ViewCompat.getLayoutDirection(r7);
        if (r1 != r3) goto L_0x000f;
    L_0x000d:
        r1 = 1;
        goto L_0x0010;
    L_0x000f:
        r1 = 0;
    L_0x0010:
        r4 = r6.this$0;
        r4 = r4.mSwipeDirection;
        r5 = 2;
        if (r4 != r5) goto L_0x0018;
    L_0x0017:
        return r3;
    L_0x0018:
        r4 = r6.this$0;
        r4 = r4.mSwipeDirection;
        if (r4 != 0) goto L_0x002d;
    L_0x001e:
        if (r1 == 0) goto L_0x0027;
    L_0x0020:
        r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
        if (r0 >= 0) goto L_0x0026;
    L_0x0024:
        r2 = 1;
        goto L_0x002c;
    L_0x0026:
        goto L_0x002c;
    L_0x0027:
        r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
        if (r0 <= 0) goto L_0x0026;
    L_0x002b:
        goto L_0x0024;
    L_0x002c:
        return r2;
    L_0x002d:
        r4 = r6.this$0;
        r4 = r4.mSwipeDirection;
        if (r4 != r3) goto L_0x0042;
    L_0x0033:
        if (r1 == 0) goto L_0x003c;
    L_0x0035:
        r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
        if (r0 <= 0) goto L_0x003b;
    L_0x0039:
        r2 = 1;
        goto L_0x0041;
    L_0x003b:
        goto L_0x0041;
    L_0x003c:
        r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
        if (r0 >= 0) goto L_0x003b;
    L_0x0040:
        goto L_0x0039;
    L_0x0041:
        return r2;
        return r2;
    L_0x0044:
        r0 = r7.getLeft();
        r1 = r6.mOriginalCapturedViewLeft;
        r0 = r0 - r1;
        r1 = r7.getWidth();
        r1 = (float) r1;
        r4 = r6.this$0;
        r4 = r4.mDragDismissThreshold;
        r1 = r1 * r4;
        r1 = java.lang.Math.round(r1);
        r4 = java.lang.Math.abs(r0);
        if (r4 < r1) goto L_0x0062;
    L_0x0060:
        r2 = 1;
    L_0x0062:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.SwipeDismissBehavior$1.shouldDismiss(android.view.View, float):boolean");
    }

    public int getViewHorizontalDragRange(View child) {
        return child.getWidth();
    }

    public int clampViewPositionHorizontal(View child, int left, int dx) {
        int min;
        int max;
        boolean isRtl = ViewCompat.getLayoutDirection(child) == 1;
        if (this.this$0.mSwipeDirection == 0) {
            if (isRtl) {
                min = this.mOriginalCapturedViewLeft - child.getWidth();
                max = this.mOriginalCapturedViewLeft;
            } else {
                min = this.mOriginalCapturedViewLeft;
                max = this.mOriginalCapturedViewLeft + child.getWidth();
            }
        } else if (this.this$0.mSwipeDirection != 1) {
            min = this.mOriginalCapturedViewLeft - child.getWidth();
            max = this.mOriginalCapturedViewLeft + child.getWidth();
            return SwipeDismissBehavior.clamp(min, left, max);
        } else if (isRtl) {
            min = this.mOriginalCapturedViewLeft;
            max = this.mOriginalCapturedViewLeft + child.getWidth();
        } else {
            min = this.mOriginalCapturedViewLeft - child.getWidth();
            max = this.mOriginalCapturedViewLeft;
        }
        return SwipeDismissBehavior.clamp(min, left, max);
    }

    public int clampViewPositionVertical(View child, int top, int dy) {
        return child.getTop();
    }

    public void onViewPositionChanged(View child, int left, int top, int dx, int dy) {
        float startAlphaDistance = ((float) this.mOriginalCapturedViewLeft) + (((float) child.getWidth()) * this.this$0.mAlphaStartSwipeDistance);
        float endAlphaDistance = ((float) this.mOriginalCapturedViewLeft) + (((float) child.getWidth()) * this.this$0.mAlphaEndSwipeDistance);
        if (((float) left) <= startAlphaDistance) {
            child.setAlpha(1.0f);
        } else if (((float) left) >= endAlphaDistance) {
            child.setAlpha(0.0f);
        } else {
            child.setAlpha(SwipeDismissBehavior.clamp(0.0f, 1.0f - SwipeDismissBehavior.fraction(startAlphaDistance, endAlphaDistance, (float) left), 1.0f));
        }
    }
}
