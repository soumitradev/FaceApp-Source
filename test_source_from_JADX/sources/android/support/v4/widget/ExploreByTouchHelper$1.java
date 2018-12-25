package android.support.v4.widget;

import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.FocusStrategy.BoundsAdapter;

class ExploreByTouchHelper$1 implements BoundsAdapter<AccessibilityNodeInfoCompat> {
    ExploreByTouchHelper$1() {
    }

    public void obtainBounds(AccessibilityNodeInfoCompat node, Rect outBounds) {
        node.getBoundsInParent(outBounds);
    }
}
