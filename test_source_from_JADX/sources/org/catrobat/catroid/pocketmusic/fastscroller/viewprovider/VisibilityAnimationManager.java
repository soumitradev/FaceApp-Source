package org.catrobat.catroid.pocketmusic.fastscroller.viewprovider;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.support.annotation.AnimatorRes;
import android.view.View;
import org.catrobat.catroid.generated70026.R;

public class VisibilityAnimationManager {
    protected AnimatorSet hideAnimator;
    private float pivotXRelative;
    private float pivotYRelative;
    protected AnimatorSet showAnimator;
    protected final View view;

    public static abstract class AbsBuilder<T extends VisibilityAnimationManager> {
        protected int hideAnimatorResource = R.animator.pocketmusic_fastscroller_hide;
        protected int hideDelay = 1000;
        protected float pivotX = 0.5f;
        protected float pivotY = 0.5f;
        protected int showAnimatorResource = R.animator.pocketmusic_fastscroller_show;
        protected final View view;

        public abstract T build();

        public AbsBuilder(View view) {
            this.view = view;
        }

        public AbsBuilder<T> withShowAnimator(@AnimatorRes int showAnimatorResource) {
            this.showAnimatorResource = showAnimatorResource;
            return this;
        }

        public AbsBuilder<T> withHideAnimator(@AnimatorRes int hideAnimatorResource) {
            this.hideAnimatorResource = hideAnimatorResource;
            return this;
        }

        public AbsBuilder<T> withHideDelay(int hideDelay) {
            this.hideDelay = hideDelay;
            return this;
        }

        public AbsBuilder<T> withPivotX(float pivotX) {
            this.pivotX = pivotX;
            return this;
        }

        public AbsBuilder<T> withPivotY(float pivotY) {
            this.pivotY = pivotY;
            return this;
        }
    }

    public static class Builder extends AbsBuilder<VisibilityAnimationManager> {
        public Builder(View view) {
            super(view);
        }

        public VisibilityAnimationManager build() {
            return new VisibilityAnimationManager(this.view, this.showAnimatorResource, this.hideAnimatorResource, this.pivotX, this.pivotY, this.hideDelay);
        }
    }

    protected VisibilityAnimationManager(final View view, @AnimatorRes int showAnimator, @AnimatorRes int hideAnimator, float pivotXRelative, float pivotYRelative, int hideDelay) {
        this.view = view;
        this.pivotXRelative = pivotXRelative;
        this.pivotYRelative = pivotYRelative;
        this.hideAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), hideAnimator);
        this.hideAnimator.setStartDelay((long) hideDelay);
        this.hideAnimator.setTarget(view);
        this.showAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), showAnimator);
        this.showAnimator.setTarget(view);
        this.hideAnimator.addListener(new AnimatorListenerAdapter() {
            boolean wasCanceled;

            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!this.wasCanceled) {
                    view.setVisibility(4);
                }
                this.wasCanceled = false;
            }

            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                this.wasCanceled = true;
            }
        });
        updatePivot();
    }

    public void show() {
        this.hideAnimator.cancel();
        if (this.view.getVisibility() == 4) {
            this.view.setVisibility(0);
            updatePivot();
            this.showAnimator.start();
        }
    }

    public void hide() {
        updatePivot();
        this.hideAnimator.start();
    }

    protected void updatePivot() {
        this.view.setPivotX(this.pivotXRelative * ((float) this.view.getMeasuredWidth()));
        this.view.setPivotY(this.pivotYRelative * ((float) this.view.getMeasuredHeight()));
    }
}
