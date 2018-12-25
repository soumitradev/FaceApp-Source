package android.support.design.widget;

import android.support.v4.view.ViewCompat;
import android.view.View;

class SwipeDismissBehavior$SettleRunnable implements Runnable {
    private final boolean mDismiss;
    private final View mView;
    final /* synthetic */ SwipeDismissBehavior this$0;

    SwipeDismissBehavior$SettleRunnable(SwipeDismissBehavior swipeDismissBehavior, View view, boolean dismiss) {
        this.this$0 = swipeDismissBehavior;
        this.mView = view;
        this.mDismiss = dismiss;
    }

    public void run() {
        if (this.this$0.mViewDragHelper != null && this.this$0.mViewDragHelper.continueSettling(true)) {
            ViewCompat.postOnAnimation(this.mView, this);
        } else if (this.mDismiss && this.this$0.mListener != null) {
            this.this$0.mListener.onDismiss(this.mView);
        }
    }
}
