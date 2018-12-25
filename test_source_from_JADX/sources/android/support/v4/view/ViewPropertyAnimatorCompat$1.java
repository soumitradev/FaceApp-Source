package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

class ViewPropertyAnimatorCompat$1 extends AnimatorListenerAdapter {
    final /* synthetic */ ViewPropertyAnimatorCompat this$0;
    final /* synthetic */ ViewPropertyAnimatorListener val$listener;
    final /* synthetic */ View val$view;

    ViewPropertyAnimatorCompat$1(ViewPropertyAnimatorCompat this$0, ViewPropertyAnimatorListener viewPropertyAnimatorListener, View view) {
        this.this$0 = this$0;
        this.val$listener = viewPropertyAnimatorListener;
        this.val$view = view;
    }

    public void onAnimationCancel(Animator animation) {
        this.val$listener.onAnimationCancel(this.val$view);
    }

    public void onAnimationEnd(Animator animation) {
        this.val$listener.onAnimationEnd(this.val$view);
    }

    public void onAnimationStart(Animator animation) {
        this.val$listener.onAnimationStart(this.val$view);
    }
}
