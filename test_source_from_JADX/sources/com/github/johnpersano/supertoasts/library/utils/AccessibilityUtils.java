package com.github.johnpersano.supertoasts.library.utils;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

public class AccessibilityUtils {
    public static boolean sendAccessibilityEvent(View view) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        if (!accessibilityManager.isEnabled()) {
            return false;
        }
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(64);
        accessibilityEvent.setClassName(view.getClass().getName());
        accessibilityEvent.setPackageName(view.getContext().getPackageName());
        view.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        accessibilityManager.sendAccessibilityEvent(accessibilityEvent);
        return true;
    }
}
