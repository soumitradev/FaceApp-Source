package android.support.v4.widget;

import android.graphics.Rect;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

class SlidingPaneLayout$AccessibilityDelegate extends AccessibilityDelegateCompat {
    private final Rect mTmpRect = new Rect();
    final /* synthetic */ SlidingPaneLayout this$0;

    SlidingPaneLayout$AccessibilityDelegate(SlidingPaneLayout this$0) {
        this.this$0 = this$0;
    }

    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        AccessibilityNodeInfoCompat superNode = AccessibilityNodeInfoCompat.obtain(info);
        super.onInitializeAccessibilityNodeInfo(host, superNode);
        copyNodeInfoNoChildren(info, superNode);
        superNode.recycle();
        info.setClassName(SlidingPaneLayout.class.getName());
        info.setSource(host);
        ViewParent parent = ViewCompat.getParentForAccessibility(host);
        if (parent instanceof View) {
            info.setParent((View) parent);
        }
        int childCount = this.this$0.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.this$0.getChildAt(i);
            if (!filter(child) && child.getVisibility() == 0) {
                ViewCompat.setImportantForAccessibility(child, 1);
                info.addChild(child);
            }
        }
    }

    public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(host, event);
        event.setClassName(SlidingPaneLayout.class.getName());
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
        if (filter(child)) {
            return false;
        }
        return super.onRequestSendAccessibilityEvent(host, child, event);
    }

    public boolean filter(View child) {
        return this.this$0.isDimmed(child);
    }

    private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat dest, AccessibilityNodeInfoCompat src) {
        Rect rect = this.mTmpRect;
        src.getBoundsInParent(rect);
        dest.setBoundsInParent(rect);
        src.getBoundsInScreen(rect);
        dest.setBoundsInScreen(rect);
        dest.setVisibleToUser(src.isVisibleToUser());
        dest.setPackageName(src.getPackageName());
        dest.setClassName(src.getClassName());
        dest.setContentDescription(src.getContentDescription());
        dest.setEnabled(src.isEnabled());
        dest.setClickable(src.isClickable());
        dest.setFocusable(src.isFocusable());
        dest.setFocused(src.isFocused());
        dest.setAccessibilityFocused(src.isAccessibilityFocused());
        dest.setSelected(src.isSelected());
        dest.setLongClickable(src.isLongClickable());
        dest.addAction(src.getActions());
        dest.setMovementGranularities(src.getMovementGranularities());
    }
}
