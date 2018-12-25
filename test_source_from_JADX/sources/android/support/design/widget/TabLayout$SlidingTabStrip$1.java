package android.support.design.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.support.design.widget.TabLayout.SlidingTabStrip;

class TabLayout$SlidingTabStrip$1 implements AnimatorUpdateListener {
    final /* synthetic */ SlidingTabStrip this$1;
    final /* synthetic */ int val$startLeft;
    final /* synthetic */ int val$startRight;
    final /* synthetic */ int val$targetLeft;
    final /* synthetic */ int val$targetRight;

    TabLayout$SlidingTabStrip$1(SlidingTabStrip this$1, int i, int i2, int i3, int i4) {
        this.this$1 = this$1;
        this.val$startLeft = i;
        this.val$targetLeft = i2;
        this.val$startRight = i3;
        this.val$targetRight = i4;
    }

    public void onAnimationUpdate(ValueAnimator animator) {
        float fraction = animator.getAnimatedFraction();
        this.this$1.setIndicatorPosition(AnimationUtils.lerp(this.val$startLeft, this.val$targetLeft, fraction), AnimationUtils.lerp(this.val$startRight, this.val$targetRight, fraction));
    }
}
