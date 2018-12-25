package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

abstract class FloatingActionButtonImpl$ShadowAnimatorImpl extends AnimatorListenerAdapter implements AnimatorUpdateListener {
    private float mShadowSizeEnd;
    private float mShadowSizeStart;
    private boolean mValidValues;
    final /* synthetic */ FloatingActionButtonImpl this$0;

    protected abstract float getTargetShadowSize();

    private FloatingActionButtonImpl$ShadowAnimatorImpl(FloatingActionButtonImpl floatingActionButtonImpl) {
        this.this$0 = floatingActionButtonImpl;
    }

    public void onAnimationUpdate(ValueAnimator animator) {
        if (!this.mValidValues) {
            this.mShadowSizeStart = this.this$0.mShadowDrawable.getShadowSize();
            this.mShadowSizeEnd = getTargetShadowSize();
            this.mValidValues = true;
        }
        this.this$0.mShadowDrawable.setShadowSize(this.mShadowSizeStart + ((this.mShadowSizeEnd - this.mShadowSizeStart) * animator.getAnimatedFraction()));
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.mShadowDrawable.setShadowSize(this.mShadowSizeEnd);
        this.mValidValues = false;
    }
}
