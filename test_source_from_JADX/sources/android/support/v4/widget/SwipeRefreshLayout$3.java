package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

class SwipeRefreshLayout$3 extends Animation {
    final /* synthetic */ SwipeRefreshLayout this$0;

    SwipeRefreshLayout$3(SwipeRefreshLayout this$0) {
        this.this$0 = this$0;
    }

    public void applyTransformation(float interpolatedTime, Transformation t) {
        this.this$0.setAnimationProgress(1.0f - interpolatedTime);
    }
}
