package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityNodeInfo.RangeInfo;

public class AccessibilityNodeInfoCompat$RangeInfoCompat {
    public static final int RANGE_TYPE_FLOAT = 1;
    public static final int RANGE_TYPE_INT = 0;
    public static final int RANGE_TYPE_PERCENT = 2;
    final Object mInfo;

    public static AccessibilityNodeInfoCompat$RangeInfoCompat obtain(int type, float min, float max, float current) {
        if (VERSION.SDK_INT >= 19) {
            return new AccessibilityNodeInfoCompat$RangeInfoCompat(RangeInfo.obtain(type, min, max, current));
        }
        return new AccessibilityNodeInfoCompat$RangeInfoCompat(null);
    }

    AccessibilityNodeInfoCompat$RangeInfoCompat(Object info) {
        this.mInfo = info;
    }

    public float getCurrent() {
        if (VERSION.SDK_INT >= 19) {
            return ((RangeInfo) this.mInfo).getCurrent();
        }
        return 0.0f;
    }

    public float getMax() {
        if (VERSION.SDK_INT >= 19) {
            return ((RangeInfo) this.mInfo).getMax();
        }
        return 0.0f;
    }

    public float getMin() {
        if (VERSION.SDK_INT >= 19) {
            return ((RangeInfo) this.mInfo).getMin();
        }
        return 0.0f;
    }

    public int getType() {
        if (VERSION.SDK_INT >= 19) {
            return ((RangeInfo) this.mInfo).getType();
        }
        return 0;
    }
}
