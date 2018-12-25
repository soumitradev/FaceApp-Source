package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.View;

class ViewPropertyAnimatorCompat$ViewPropertyAnimatorListenerApi14 implements ViewPropertyAnimatorListener {
    boolean mAnimEndCalled;
    ViewPropertyAnimatorCompat mVpa;

    ViewPropertyAnimatorCompat$ViewPropertyAnimatorListenerApi14(ViewPropertyAnimatorCompat vpa) {
        this.mVpa = vpa;
    }

    public void onAnimationStart(View view) {
        this.mAnimEndCalled = false;
        if (this.mVpa.mOldLayerType > -1) {
            view.setLayerType(2, null);
        }
        if (this.mVpa.mStartAction != null) {
            Runnable startAction = this.mVpa.mStartAction;
            this.mVpa.mStartAction = null;
            startAction.run();
        }
        ViewPropertyAnimatorListener listenerTag = view.getTag(2113929216);
        ViewPropertyAnimatorListener listener = null;
        if (listenerTag instanceof ViewPropertyAnimatorListener) {
            listener = listenerTag;
        }
        if (listener != null) {
            listener.onAnimationStart(view);
        }
    }

    public void onAnimationEnd(View view) {
        if (this.mVpa.mOldLayerType > -1) {
            view.setLayerType(this.mVpa.mOldLayerType, null);
            this.mVpa.mOldLayerType = -1;
        }
        if (VERSION.SDK_INT >= 16 || !this.mAnimEndCalled) {
            if (this.mVpa.mEndAction != null) {
                Runnable endAction = this.mVpa.mEndAction;
                this.mVpa.mEndAction = null;
                endAction.run();
            }
            ViewPropertyAnimatorListener listenerTag = view.getTag(2113929216);
            ViewPropertyAnimatorListener listener = null;
            if (listenerTag instanceof ViewPropertyAnimatorListener) {
                listener = listenerTag;
            }
            if (listener != null) {
                listener.onAnimationEnd(view);
            }
            this.mAnimEndCalled = true;
        }
    }

    public void onAnimationCancel(View view) {
        ViewPropertyAnimatorListener listenerTag = view.getTag(2113929216);
        ViewPropertyAnimatorListener listener = null;
        if (listenerTag instanceof ViewPropertyAnimatorListener) {
            listener = listenerTag;
        }
        if (listener != null) {
            listener.onAnimationCancel(view);
        }
    }
}
