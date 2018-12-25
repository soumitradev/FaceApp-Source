package android.support.v4.view;

import android.view.View;
import android.view.ViewParent;

class ViewParentCompat$ViewParentCompatBaseImpl {
    ViewParentCompat$ViewParentCompatBaseImpl() {
    }

    public boolean onStartNestedScroll(ViewParent parent, View child, View target, int nestedScrollAxes) {
        if (parent instanceof NestedScrollingParent) {
            return ((NestedScrollingParent) parent).onStartNestedScroll(child, target, nestedScrollAxes);
        }
        return false;
    }

    public void onNestedScrollAccepted(ViewParent parent, View child, View target, int nestedScrollAxes) {
        if (parent instanceof NestedScrollingParent) {
            ((NestedScrollingParent) parent).onNestedScrollAccepted(child, target, nestedScrollAxes);
        }
    }

    public void onStopNestedScroll(ViewParent parent, View target) {
        if (parent instanceof NestedScrollingParent) {
            ((NestedScrollingParent) parent).onStopNestedScroll(target);
        }
    }

    public void onNestedScroll(ViewParent parent, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (parent instanceof NestedScrollingParent) {
            ((NestedScrollingParent) parent).onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        }
    }

    public void onNestedPreScroll(ViewParent parent, View target, int dx, int dy, int[] consumed) {
        if (parent instanceof NestedScrollingParent) {
            ((NestedScrollingParent) parent).onNestedPreScroll(target, dx, dy, consumed);
        }
    }

    public boolean onNestedFling(ViewParent parent, View target, float velocityX, float velocityY, boolean consumed) {
        if (parent instanceof NestedScrollingParent) {
            return ((NestedScrollingParent) parent).onNestedFling(target, velocityX, velocityY, consumed);
        }
        return false;
    }

    public boolean onNestedPreFling(ViewParent parent, View target, float velocityX, float velocityY) {
        if (parent instanceof NestedScrollingParent) {
            return ((NestedScrollingParent) parent).onNestedPreFling(target, velocityX, velocityY);
        }
        return false;
    }

    public void notifySubtreeAccessibilityStateChanged(ViewParent parent, View child, View source, int changeType) {
    }
}
