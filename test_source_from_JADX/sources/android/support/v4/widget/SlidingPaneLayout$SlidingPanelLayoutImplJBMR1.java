package android.support.v4.widget;

import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;

@RequiresApi(17)
class SlidingPaneLayout$SlidingPanelLayoutImplJBMR1 extends SlidingPaneLayout$SlidingPanelLayoutImplBase {
    SlidingPaneLayout$SlidingPanelLayoutImplJBMR1() {
    }

    public void invalidateChildRegion(SlidingPaneLayout parent, View child) {
        ViewCompat.setLayerPaint(child, ((SlidingPaneLayout$LayoutParams) child.getLayoutParams()).dimPaint);
    }
}
