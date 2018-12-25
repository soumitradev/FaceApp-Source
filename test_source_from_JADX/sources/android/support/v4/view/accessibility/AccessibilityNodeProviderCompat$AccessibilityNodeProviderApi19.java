package android.support.v4.view.accessibility;

import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;

@RequiresApi(19)
class AccessibilityNodeProviderCompat$AccessibilityNodeProviderApi19 extends AccessibilityNodeProviderCompat$AccessibilityNodeProviderApi16 {
    AccessibilityNodeProviderCompat$AccessibilityNodeProviderApi19(AccessibilityNodeProviderCompat compat) {
        super(compat);
    }

    public AccessibilityNodeInfo findFocus(int focus) {
        AccessibilityNodeInfoCompat compatInfo = this.mCompat.findFocus(focus);
        if (compatInfo == null) {
            return null;
        }
        return compatInfo.unwrap();
    }
}
