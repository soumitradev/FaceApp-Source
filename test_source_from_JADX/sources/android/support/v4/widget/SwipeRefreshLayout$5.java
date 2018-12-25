package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

class SwipeRefreshLayout$5 implements AnimationListener {
    final /* synthetic */ SwipeRefreshLayout this$0;

    SwipeRefreshLayout$5(SwipeRefreshLayout this$0) {
        this.this$0 = this$0;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        if (!this.this$0.mScale) {
            this.this$0.startScaleDownAnimation(null);
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }
}
