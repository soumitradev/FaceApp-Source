package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;

public class AccessibilityNodeInfoCompat$CollectionInfoCompat {
    public static final int SELECTION_MODE_MULTIPLE = 2;
    public static final int SELECTION_MODE_NONE = 0;
    public static final int SELECTION_MODE_SINGLE = 1;
    final Object mInfo;

    public static AccessibilityNodeInfoCompat$CollectionInfoCompat obtain(int rowCount, int columnCount, boolean hierarchical, int selectionMode) {
        if (VERSION.SDK_INT >= 21) {
            return new AccessibilityNodeInfoCompat$CollectionInfoCompat(CollectionInfo.obtain(rowCount, columnCount, hierarchical, selectionMode));
        }
        if (VERSION.SDK_INT >= 19) {
            return new AccessibilityNodeInfoCompat$CollectionInfoCompat(CollectionInfo.obtain(rowCount, columnCount, hierarchical));
        }
        return new AccessibilityNodeInfoCompat$CollectionInfoCompat(null);
    }

    public static AccessibilityNodeInfoCompat$CollectionInfoCompat obtain(int rowCount, int columnCount, boolean hierarchical) {
        if (VERSION.SDK_INT >= 19) {
            return new AccessibilityNodeInfoCompat$CollectionInfoCompat(CollectionInfo.obtain(rowCount, columnCount, hierarchical));
        }
        return new AccessibilityNodeInfoCompat$CollectionInfoCompat(null);
    }

    AccessibilityNodeInfoCompat$CollectionInfoCompat(Object info) {
        this.mInfo = info;
    }

    public int getColumnCount() {
        if (VERSION.SDK_INT >= 19) {
            return ((CollectionInfo) this.mInfo).getColumnCount();
        }
        return 0;
    }

    public int getRowCount() {
        if (VERSION.SDK_INT >= 19) {
            return ((CollectionInfo) this.mInfo).getRowCount();
        }
        return 0;
    }

    public boolean isHierarchical() {
        if (VERSION.SDK_INT >= 19) {
            return ((CollectionInfo) this.mInfo).isHierarchical();
        }
        return false;
    }

    public int getSelectionMode() {
        if (VERSION.SDK_INT >= 21) {
            return ((CollectionInfo) this.mInfo).getSelectionMode();
        }
        return 0;
    }
}
