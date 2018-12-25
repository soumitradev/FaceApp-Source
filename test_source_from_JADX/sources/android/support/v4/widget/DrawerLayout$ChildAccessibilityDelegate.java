package android.support.v4.widget;

import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;

final class DrawerLayout$ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
    DrawerLayout$ChildAccessibilityDelegate() {
    }

    public void onInitializeAccessibilityNodeInfo(View child, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(child, info);
        if (!DrawerLayout.includeChildForAccessibility(child)) {
            info.setParent(null);
        }
    }
}
