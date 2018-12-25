package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(16)
class AccessibilityNodeProviderCompat$AccessibilityNodeProviderApi16 extends AccessibilityNodeProvider {
    final AccessibilityNodeProviderCompat mCompat;

    AccessibilityNodeProviderCompat$AccessibilityNodeProviderApi16(AccessibilityNodeProviderCompat compat) {
        this.mCompat = compat;
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
        AccessibilityNodeInfoCompat compatInfo = this.mCompat.createAccessibilityNodeInfo(virtualViewId);
        if (compatInfo == null) {
            return null;
        }
        return compatInfo.unwrap();
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
        List<AccessibilityNodeInfoCompat> compatInfos = this.mCompat.findAccessibilityNodeInfosByText(text, virtualViewId);
        if (compatInfos == null) {
            return null;
        }
        List<AccessibilityNodeInfo> infoList = new ArrayList();
        int infoCount = compatInfos.size();
        for (int i = 0; i < infoCount; i++) {
            infoList.add(((AccessibilityNodeInfoCompat) compatInfos.get(i)).unwrap());
        }
        return infoList;
    }

    public boolean performAction(int virtualViewId, int action, Bundle arguments) {
        return this.mCompat.performAction(virtualViewId, action, arguments);
    }
}
