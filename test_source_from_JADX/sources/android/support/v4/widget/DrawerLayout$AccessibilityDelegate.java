package android.support.v4.widget;

import android.graphics.Rect;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

class DrawerLayout$AccessibilityDelegate extends AccessibilityDelegateCompat {
    private final Rect mTmpRect = new Rect();
    final /* synthetic */ DrawerLayout this$0;

    DrawerLayout$AccessibilityDelegate(DrawerLayout this$0) {
        this.this$0 = this$0;
    }

    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        if (DrawerLayout.CAN_HIDE_DESCENDANTS) {
            super.onInitializeAccessibilityNodeInfo(host, info);
        } else {
            AccessibilityNodeInfoCompat superNode = AccessibilityNodeInfoCompat.obtain(info);
            super.onInitializeAccessibilityNodeInfo(host, superNode);
            info.setSource(host);
            ViewParent parent = ViewCompat.getParentForAccessibility(host);
            if (parent instanceof View) {
                info.setParent((View) parent);
            }
            copyNodeInfoNoChildren(info, superNode);
            superNode.recycle();
            addChildrenForAccessibility(info, (ViewGroup) host);
        }
        info.setClassName(DrawerLayout.class.getName());
        info.setFocusable(false);
        info.setFocused(false);
        info.removeAction(AccessibilityNodeInfoCompat$AccessibilityActionCompat.ACTION_FOCUS);
        info.removeAction(AccessibilityNodeInfoCompat$AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
    }

    public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(host, event);
        event.setClassName(DrawerLayout.class.getName());
    }

    public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
        if (event.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(host, event);
        }
        List<CharSequence> eventText = event.getText();
        View visibleDrawer = this.this$0.findVisibleDrawer();
        if (visibleDrawer != null) {
            CharSequence title = this.this$0.getDrawerTitle(this.this$0.getDrawerViewAbsoluteGravity(visibleDrawer));
            if (title != null) {
                eventText.add(title);
            }
        }
        return true;
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
        if (!DrawerLayout.CAN_HIDE_DESCENDANTS) {
            if (!DrawerLayout.includeChildForAccessibility(child)) {
                return false;
            }
        }
        return super.onRequestSendAccessibilityEvent(host, child, event);
    }

    private void addChildrenForAccessibility(AccessibilityNodeInfoCompat info, ViewGroup v) {
        int childCount = v.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = v.getChildAt(i);
            if (DrawerLayout.includeChildForAccessibility(child)) {
                info.addChild(child);
            }
        }
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
    }
}
