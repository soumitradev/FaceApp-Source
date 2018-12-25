package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

class SwipeRefreshLayout$1 implements AnimationListener {
    final /* synthetic */ SwipeRefreshLayout this$0;

    SwipeRefreshLayout$1(SwipeRefreshLayout this$0) {
        this.this$0 = this$0;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        if (this.this$0.mRefreshing) {
            this.this$0.mProgress.setAlpha(255);
            this.this$0.mProgress.start();
            if (this.this$0.mNotify && this.this$0.mListener != null) {
                this.this$0.mListener.onRefresh();
            }
            this.this$0.mCurrentTargetOffsetTop = this.this$0.mCircleView.getTop();
            return;
        }
        this.this$0.reset();
    }
}
