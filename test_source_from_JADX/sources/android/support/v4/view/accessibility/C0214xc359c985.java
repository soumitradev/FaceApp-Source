package android.support.v4.view.accessibility;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener;

@RequiresApi(19)
/* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper */
class C0214xc359c985 implements TouchExplorationStateChangeListener {
    final AccessibilityManagerCompat$TouchExplorationStateChangeListener mListener;

    C0214xc359c985(@NonNull AccessibilityManagerCompat$TouchExplorationStateChangeListener listener) {
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
                return this.mListener.equals(((C0214xc359c985) o).mListener);
            }
        }
        return false;
    }

    public void onTouchExplorationStateChanged(boolean enabled) {
        this.mListener.onTouchExplorationStateChanged(enabled);
    }
}
