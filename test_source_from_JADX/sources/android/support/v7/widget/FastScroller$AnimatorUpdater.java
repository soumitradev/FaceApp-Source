package android.support.v7.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

class FastScroller$AnimatorUpdater implements AnimatorUpdateListener {
    final /* synthetic */ FastScroller this$0;

    private FastScroller$AnimatorUpdater(FastScroller fastScroller) {
        this.this$0 = fastScroller;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int alpha = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 1132396544);
        FastScroller.access$600(this.this$0).setAlpha(alpha);
        FastScroller.access$700(this.this$0).setAlpha(alpha);
        FastScroller.access$500(this.this$0);
    }
}
