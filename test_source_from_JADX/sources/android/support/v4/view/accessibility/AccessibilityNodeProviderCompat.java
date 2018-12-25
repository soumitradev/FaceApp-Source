package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import java.util.List;

public class AccessibilityNodeProviderCompat {
    public static final int HOST_VIEW_ID = -1;
    private final Object mProvider;

    public AccessibilityNodeProviderCompat() {
        if (VERSION.SDK_INT >= 19) {
            this.mProvider = new AccessibilityNodeProviderCompat$AccessibilityNodeProviderApi19(this);
        } else if (VERSION.SDK_INT >= 16) {
            this.mProvider = new AccessibilityNodeProviderCompat$AccessibilityNodeProviderApi16(this);
        } else {
            this.mProvider = null;
        }
    }

    public AccessibilityNodeProviderCompat(Object provider) {
        this.mProvider = provider;
    }

    public Object getProvider() {
        return this.mProvider;
    }

    @Nullable
    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) {
        return null;
    }

    public boolean performAction(int virtualViewId, int action, Bundle arguments) {
        return false;
    }

    @Nullable
    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
        return null;
    }

    @Nullable
    public AccessibilityNodeInfoCompat findFocus(int focus) {
        return null;
    }
}
