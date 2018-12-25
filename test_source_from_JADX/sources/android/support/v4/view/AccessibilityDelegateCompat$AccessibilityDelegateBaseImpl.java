package android.support.v4.view;

import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

class AccessibilityDelegateCompat$AccessibilityDelegateBaseImpl {
    AccessibilityDelegateCompat$AccessibilityDelegateBaseImpl() {
    }

    public AccessibilityDelegate newAccessibilityDelegateBridge(final AccessibilityDelegateCompat compat) {
        return new AccessibilityDelegate() {
            public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                return compat.dispatchPopulateAccessibilityEvent(host, event);
            }

            public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
                compat.onInitializeAccessibilityEvent(host, event);
            }

            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                compat.onInitializeAccessibilityNodeInfo(host, AccessibilityNodeInfoCompat.wrap(info));
            }

            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                compat.onPopulateAccessibilityEvent(host, event);
            }

            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                return compat.onRequestSendAccessibilityEvent(host, child, event);
            }

            public void sendAccessibilityEvent(View host, int eventType) {
                compat.sendAccessibilityEvent(host, eventType);
            }

            public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
                compat.sendAccessibilityEventUnchecked(host, event);
            }
        };
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(AccessibilityDelegate delegate, View host) {
        return null;
    }

    public boolean performAccessibilityAction(AccessibilityDelegate delegate, View host, int action, Bundle args) {
        return false;
    }
}
