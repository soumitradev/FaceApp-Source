package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

@RequiresApi(21)
class ViewParentCompat$ViewParentCompatApi21Impl extends ViewParentCompat$ViewParentCompatApi19Impl {
    ViewParentCompat$ViewParentCompatApi21Impl() {
    }

    public boolean onStartNestedScroll(ViewParent parent, View child, View target, int nestedScrollAxes) {
        try {
            return parent.onStartNestedScroll(child, target, nestedScrollAxes);
        } catch (AbstractMethodError e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewParent ");
            stringBuilder.append(parent);
            stringBuilder.append(" does not implement interface ");
            stringBuilder.append("method onStartNestedScroll");
            Log.e("ViewParentCompat", stringBuilder.toString(), e);
            return false;
        }
    }

    public void onNestedScrollAccepted(ViewParent parent, View child, View target, int nestedScrollAxes) {
        try {
            parent.onNestedScrollAccepted(child, target, nestedScrollAxes);
        } catch (AbstractMethodError e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewParent ");
            stringBuilder.append(parent);
            stringBuilder.append(" does not implement interface ");
            stringBuilder.append("method onNestedScrollAccepted");
            Log.e("ViewParentCompat", stringBuilder.toString(), e);
        }
    }

    public void onStopNestedScroll(ViewParent parent, View target) {
        try {
            parent.onStopNestedScroll(target);
        } catch (AbstractMethodError e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewParent ");
            stringBuilder.append(parent);
            stringBuilder.append(" does not implement interface ");
            stringBuilder.append("method onStopNestedScroll");
            Log.e("ViewParentCompat", stringBuilder.toString(), e);
        }
    }

    public void onNestedScroll(ViewParent parent, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        try {
            parent.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        } catch (AbstractMethodError e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewParent ");
            stringBuilder.append(parent);
            stringBuilder.append(" does not implement interface ");
            stringBuilder.append("method onNestedScroll");
            Log.e("ViewParentCompat", stringBuilder.toString(), e);
        }
    }

    public void onNestedPreScroll(ViewParent parent, View target, int dx, int dy, int[] consumed) {
        try {
            parent.onNestedPreScroll(target, dx, dy, consumed);
        } catch (AbstractMethodError e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewParent ");
            stringBuilder.append(parent);
            stringBuilder.append(" does not implement interface ");
            stringBuilder.append("method onNestedPreScroll");
            Log.e("ViewParentCompat", stringBuilder.toString(), e);
        }
    }

    public boolean onNestedFling(ViewParent parent, View target, float velocityX, float velocityY, boolean consumed) {
        try {
            return parent.onNestedFling(target, velocityX, velocityY, consumed);
        } catch (AbstractMethodError e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewParent ");
            stringBuilder.append(parent);
            stringBuilder.append(" does not implement interface ");
            stringBuilder.append("method onNestedFling");
            Log.e("ViewParentCompat", stringBuilder.toString(), e);
            return false;
        }
    }

    public boolean onNestedPreFling(ViewParent parent, View target, float velocityX, float velocityY) {
        try {
            return parent.onNestedPreFling(target, velocityX, velocityY);
        } catch (AbstractMethodError e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewParent ");
            stringBuilder.append(parent);
            stringBuilder.append(" does not implement interface ");
            stringBuilder.append("method onNestedPreFling");
            Log.e("ViewParentCompat", stringBuilder.toString(), e);
            return false;
        }
    }
}
