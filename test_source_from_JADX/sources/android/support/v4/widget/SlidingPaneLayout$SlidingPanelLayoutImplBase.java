package android.support.v4.widget;

import android.support.v4.view.ViewCompat;
import android.view.View;

class SlidingPaneLayout$SlidingPanelLayoutImplBase implements SlidingPaneLayout$SlidingPanelLayoutImpl {
    SlidingPaneLayout$SlidingPanelLayoutImplBase() {
    }

    public void invalidateChildRegion(SlidingPaneLayout parent, View child) {
        ViewCompat.postInvalidateOnAnimation(parent, child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
    }
}
