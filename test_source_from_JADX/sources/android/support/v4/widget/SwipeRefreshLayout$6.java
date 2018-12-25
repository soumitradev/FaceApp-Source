package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

class SwipeRefreshLayout$6 extends Animation {
    final /* synthetic */ SwipeRefreshLayout this$0;

    SwipeRefreshLayout$6(SwipeRefreshLayout this$0) {
        this.this$0 = this$0;
    }

    public void applyTransformation(float interpolatedTime, Transformation t) {
        int endTarget;
        if (this.this$0.mUsingCustomStart) {
            endTarget = this.this$0.mSpinnerOffsetEnd;
        } else {
            endTarget = this.this$0.mSpinnerOffsetEnd - Math.abs(this.this$0.mOriginalOffsetTop);
        }
        this.this$0.setTargetOffsetTopAndBottom((this.this$0.mFrom + ((int) (((float) (endTarget - this.this$0.mFrom)) * interpolatedTime))) - this.this$0.mCircleView.getTop());
        this.this$0.mProgress.setArrowScale(1.0f - interpolatedTime);
    }
}
