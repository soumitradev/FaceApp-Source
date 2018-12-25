package android.support.v4.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;

class ViewPropertyAnimatorCompat$2 implements AnimatorUpdateListener {
    final /* synthetic */ ViewPropertyAnimatorCompat this$0;
    final /* synthetic */ ViewPropertyAnimatorUpdateListener val$listener;
    final /* synthetic */ View val$view;

    ViewPropertyAnimatorCompat$2(ViewPropertyAnimatorCompat this$0, ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener, View view) {
        this.this$0 = this$0;
        this.val$listener = viewPropertyAnimatorUpdateListener;
        this.val$view = view;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.val$listener.onAnimationUpdate(this.val$view);
    }
}
