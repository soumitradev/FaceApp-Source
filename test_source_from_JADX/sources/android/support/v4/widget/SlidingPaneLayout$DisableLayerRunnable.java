package android.support.v4.widget;

import android.view.View;

class SlidingPaneLayout$DisableLayerRunnable implements Runnable {
    final View mChildView;
    final /* synthetic */ SlidingPaneLayout this$0;

    SlidingPaneLayout$DisableLayerRunnable(SlidingPaneLayout slidingPaneLayout, View childView) {
        this.this$0 = slidingPaneLayout;
        this.mChildView = childView;
    }

    public void run() {
        if (this.mChildView.getParent() == this.this$0) {
            this.mChildView.setLayerType(0, null);
            this.this$0.invalidateChildRegion(this.mChildView);
        }
        this.this$0.mPostedRunnables.remove(this);
    }
}
