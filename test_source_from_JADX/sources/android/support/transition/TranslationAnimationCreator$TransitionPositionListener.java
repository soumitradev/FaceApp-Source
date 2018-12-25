package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

class TranslationAnimationCreator$TransitionPositionListener extends AnimatorListenerAdapter {
    private final View mMovingView;
    private float mPausedX;
    private float mPausedY;
    private final int mStartX;
    private final int mStartY;
    private final float mTerminalX;
    private final float mTerminalY;
    private int[] mTransitionPosition;
    private final View mViewInHierarchy;

    private TranslationAnimationCreator$TransitionPositionListener(View movingView, View viewInHierarchy, int startX, int startY, float terminalX, float terminalY) {
        this.mMovingView = movingView;
        this.mViewInHierarchy = viewInHierarchy;
        this.mStartX = startX - Math.round(this.mMovingView.getTranslationX());
        this.mStartY = startY - Math.round(this.mMovingView.getTranslationY());
        this.mTerminalX = terminalX;
        this.mTerminalY = terminalY;
        this.mTransitionPosition = (int[]) this.mViewInHierarchy.getTag(C0147R.id.transition_position);
        if (this.mTransitionPosition != null) {
            this.mViewInHierarchy.setTag(C0147R.id.transition_position, null);
        }
    }

    public void onAnimationCancel(Animator animation) {
        if (this.mTransitionPosition == null) {
            this.mTransitionPosition = new int[2];
        }
        this.mTransitionPosition[0] = Math.round(((float) this.mStartX) + this.mMovingView.getTranslationX());
        this.mTransitionPosition[1] = Math.round(((float) this.mStartY) + this.mMovingView.getTranslationY());
        this.mViewInHierarchy.setTag(C0147R.id.transition_position, this.mTransitionPosition);
    }

    public void onAnimationEnd(Animator animator) {
        this.mMovingView.setTranslationX(this.mTerminalX);
        this.mMovingView.setTranslationY(this.mTerminalY);
    }

    public void onAnimationPause(Animator animator) {
        this.mPausedX = this.mMovingView.getTranslationX();
        this.mPausedY = this.mMovingView.getTranslationY();
        this.mMovingView.setTranslationX(this.mTerminalX);
        this.mMovingView.setTranslationY(this.mTerminalY);
    }

    public void onAnimationResume(Animator animator) {
        this.mMovingView.setTranslationX(this.mPausedX);
        this.mMovingView.setTranslationY(this.mPausedY);
    }
}
