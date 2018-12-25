package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewParent;

@RequiresApi(19)
class ViewParentCompat$ViewParentCompatApi19Impl extends ViewParentCompat$ViewParentCompatBaseImpl {
    ViewParentCompat$ViewParentCompatApi19Impl() {
    }

    public void notifySubtreeAccessibilityStateChanged(ViewParent parent, View child, View source, int changeType) {
        parent.notifySubtreeAccessibilityStateChanged(child, source, changeType);
    }
}
