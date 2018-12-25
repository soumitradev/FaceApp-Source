package android.support.v4.widget;

import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;

class ExploreByTouchHelper$MyNodeProvider extends AccessibilityNodeProviderCompat {
    final /* synthetic */ ExploreByTouchHelper this$0;

    ExploreByTouchHelper$MyNodeProvider(ExploreByTouchHelper exploreByTouchHelper) {
        this.this$0 = exploreByTouchHelper;
    }

    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) {
        return AccessibilityNodeInfoCompat.obtain(this.this$0.obtainAccessibilityNodeInfo(virtualViewId));
    }

    public boolean performAction(int virtualViewId, int action, Bundle arguments) {
        return this.this$0.performAction(virtualViewId, action, arguments);
    }

    public AccessibilityNodeInfoCompat findFocus(int focusType) {
        int focusedId = focusType == 2 ? ExploreByTouchHelper.access$000(this.this$0) : ExploreByTouchHelper.access$100(this.this$0);
        if (focusedId == Integer.MIN_VALUE) {
            return null;
        }
        return createAccessibilityNodeInfo(focusedId);
    }
}
