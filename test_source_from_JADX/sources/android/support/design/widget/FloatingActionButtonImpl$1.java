package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.FloatingActionButtonImpl.InternalVisibilityChangedListener;

class FloatingActionButtonImpl$1 extends AnimatorListenerAdapter {
    private boolean mCancelled;
    final /* synthetic */ FloatingActionButtonImpl this$0;
    final /* synthetic */ boolean val$fromUser;
    final /* synthetic */ InternalVisibilityChangedListener val$listener;

    FloatingActionButtonImpl$1(FloatingActionButtonImpl this$0, boolean z, InternalVisibilityChangedListener internalVisibilityChangedListener) {
        this.this$0 = this$0;
        this.val$fromUser = z;
        this.val$listener = internalVisibilityChangedListener;
    }

    public void onAnimationStart(Animator animation) {
        this.this$0.mView.internalSetVisibility(0, this.val$fromUser);
        this.mCancelled = false;
    }

    public void onAnimationCancel(Animator animation) {
        this.mCancelled = true;
    }

    public void onAnimationEnd(Animator animation) {
        this.this$0.mAnimState = 0;
        if (!this.mCancelled) {
            this.this$0.mView.internalSetVisibility(this.val$fromUser ? 8 : 4, this.val$fromUser);
            if (this.val$listener != null) {
                this.val$listener.onHidden();
            }
        }
    }
}
