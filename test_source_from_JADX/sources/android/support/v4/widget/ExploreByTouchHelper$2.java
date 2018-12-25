package android.support.v4.widget;

import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.FocusStrategy.CollectionAdapter;

class ExploreByTouchHelper$2 implements CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> {
    ExploreByTouchHelper$2() {
    }

    public AccessibilityNodeInfoCompat get(SparseArrayCompat<AccessibilityNodeInfoCompat> collection, int index) {
        return (AccessibilityNodeInfoCompat) collection.valueAt(index);
    }

    public int size(SparseArrayCompat<AccessibilityNodeInfoCompat> collection) {
        return collection.size();
    }
}
