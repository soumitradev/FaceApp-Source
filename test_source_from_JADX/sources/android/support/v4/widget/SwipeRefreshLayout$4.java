package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

class SwipeRefreshLayout$4 extends Animation {
    final /* synthetic */ SwipeRefreshLayout this$0;
    final /* synthetic */ int val$endingAlpha;
    final /* synthetic */ int val$startingAlpha;

    SwipeRefreshLayout$4(SwipeRefreshLayout this$0, int i, int i2) {
        this.this$0 = this$0;
        this.val$startingAlpha = i;
        this.val$endingAlpha = i2;
    }

    public void applyTransformation(float interpolatedTime, Transformation t) {
        this.this$0.mProgress.setAlpha((int) (((float) this.val$startingAlpha) + (((float) (this.val$endingAlpha - this.val$startingAlpha)) * interpolatedTime)));
    }
}
