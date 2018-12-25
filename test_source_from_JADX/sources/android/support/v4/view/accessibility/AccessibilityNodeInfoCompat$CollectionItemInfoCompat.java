package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo;

public class AccessibilityNodeInfoCompat$CollectionItemInfoCompat {
    final Object mInfo;

    public static AccessibilityNodeInfoCompat$CollectionItemInfoCompat obtain(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading, boolean selected) {
        if (VERSION.SDK_INT >= 21) {
            return new AccessibilityNodeInfoCompat$CollectionItemInfoCompat(CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading, selected));
        }
        if (VERSION.SDK_INT >= 19) {
            return new AccessibilityNodeInfoCompat$CollectionItemInfoCompat(CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading));
        }
        return new AccessibilityNodeInfoCompat$CollectionItemInfoCompat(null);
    }

    public static AccessibilityNodeInfoCompat$CollectionItemInfoCompat obtain(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading) {
        if (VERSION.SDK_INT >= 19) {
            return new AccessibilityNodeInfoCompat$CollectionItemInfoCompat(CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading));
        }
        return new AccessibilityNodeInfoCompat$CollectionItemInfoCompat(null);
    }

    AccessibilityNodeInfoCompat$CollectionItemInfoCompat(Object info) {
        this.mInfo = info;
    }

    public int getColumnIndex() {
        if (VERSION.SDK_INT >= 19) {
            return ((CollectionItemInfo) this.mInfo).getColumnIndex();
        }
        return 0;
    }

    public int getColumnSpan() {
        if (VERSION.SDK_INT >= 19) {
            return ((CollectionItemInfo) this.mInfo).getColumnSpan();
        }
        return 0;
    }

    public int getRowIndex() {
        if (VERSION.SDK_INT >= 19) {
            return ((CollectionItemInfo) this.mInfo).getRowIndex();
        }
        return 0;
    }

    public int getRowSpan() {
        if (VERSION.SDK_INT >= 19) {
            return ((CollectionItemInfo) this.mInfo).getRowSpan();
        }
        return 0;
    }

    public boolean isHeading() {
        if (VERSION.SDK_INT >= 19) {
            return ((CollectionItemInfo) this.mInfo).isHeading();
        }
        return false;
    }

    public boolean isSelected() {
        if (VERSION.SDK_INT >= 21) {
            return ((CollectionItemInfo) this.mInfo).isSelected();
        }
        return false;
    }
}
