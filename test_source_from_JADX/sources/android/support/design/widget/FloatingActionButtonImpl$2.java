package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.FloatingActionButtonImpl.InternalVisibilityChangedListener;

class FloatingActionButtonImpl$2 extends AnimatorListenerAdapter {
    final /* synthetic */ FloatingActionButtonImpl this$0;
    final /* synthetic */ boolean val$fromUser;
    final /* synthetic */ InternalVisibilityChangedListener val$listener;

    FloatingActionButtonImpl$2(FloatingActionButtonImpl this$0, boolean z, InternalVisibilityChangedListener internalVisibilityChangedListener) {
        this.this$0 = this$0;
        this.val$fromUser = z;
        this.val$listener = internalVisibilityChangedListener;
    }

    public void onAnimationStart(Animator animation) {
        this.this$0.mView.internalSetVisibility(0, this.val$fromUser);
    }

    public void onAnimationEnd(Animator animation) {
        this.this$0.mAnimState = 0;
        if (this.val$listener != null) {
            this.val$listener.onShown();
        }
    }
}
