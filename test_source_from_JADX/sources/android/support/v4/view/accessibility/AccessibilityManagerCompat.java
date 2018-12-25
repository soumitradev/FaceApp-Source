package android.support.v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityManager;
import java.util.List;

public final class AccessibilityManagerCompat {

    @Deprecated
    public interface AccessibilityStateChangeListener {
        @Deprecated
        void onAccessibilityStateChanged(boolean z);
    }

    @Deprecated
    public static abstract class AccessibilityStateChangeListenerCompat implements AccessibilityStateChangeListener {
    }

    @Deprecated
    public static boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListener listener) {
        if (listener == null) {
            return false;
        }
        return manager.addAccessibilityStateChangeListener(new C0213x1a0f6089(listener));
    }

    @Deprecated
    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListener listener) {
        if (listener == null) {
            return false;
        }
        return manager.removeAccessibilityStateChangeListener(new C0213x1a0f6089(listener));
    }

    @Deprecated
    public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
        return manager.getInstalledAccessibilityServiceList();
    }

    @Deprecated
    public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
        return manager.getEnabledAccessibilityServiceList(feedbackTypeFlags);
    }

    @Deprecated
    public static boolean isTouchExplorationEnabled(AccessibilityManager manager) {
        return manager.isTouchExplorationEnabled();
    }

    public static boolean addTouchExplorationStateChangeListener(AccessibilityManager manager, AccessibilityManagerCompat$TouchExplorationStateChangeListener listener) {
        if (VERSION.SDK_INT < 19 || listener == null) {
            return false;
        }
        return manager.addTouchExplorationStateChangeListener(new C0214xc359c985(listener));
    }

    public static boolean removeTouchExplorationStateChangeListener(AccessibilityManager manager, AccessibilityManagerCompat$TouchExplorationStateChangeListener listener) {
        if (VERSION.SDK_INT < 19 || listener == null) {
            return false;
        }
        return manager.removeTouchExplorationStateChangeListener(new C0214xc359c985(listener));
    }

    private AccessibilityManagerCompat() {
    }
}
