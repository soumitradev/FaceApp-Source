package android.support.v4.view.accessibility;

import android.support.annotation.NonNull;
import android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener;

/* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat$AccessibilityStateChangeListenerWrapper */
class C0213x1a0f6089 implements AccessibilityStateChangeListener {
    AccessibilityManagerCompat.AccessibilityStateChangeListener mListener;

    C0213x1a0f6089(@NonNull AccessibilityManagerCompat.AccessibilityStateChangeListener listener) {
        this.mListener = listener;
    }

    public int hashCode() {
        return this.mListener.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null) {
            if (getClass() == o.getClass()) {
                return this.mListener.equals(((C0213x1a0f6089) o).mListener);
            }
        }
        return false;
    }

    public void onAccessibilityStateChanged(boolean enabled) {
        this.mListener.onAccessibilityStateChanged(enabled);
    }
}
