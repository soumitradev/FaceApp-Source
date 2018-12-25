package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.TabLayout.SlidingTabStrip;

class TabLayout$SlidingTabStrip$2 extends AnimatorListenerAdapter {
    final /* synthetic */ SlidingTabStrip this$1;
    final /* synthetic */ int val$position;

    TabLayout$SlidingTabStrip$2(SlidingTabStrip this$1, int i) {
        this.this$1 = this$1;
        this.val$position = i;
    }

    public void onAnimationEnd(Animator animator) {
        this.this$1.mSelectedPosition = this.val$position;
        this.this$1.mSelectionOffset = 0.0f;
    }
}
