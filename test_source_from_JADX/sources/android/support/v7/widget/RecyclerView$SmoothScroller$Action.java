package android.support.v7.widget;

import android.util.Log;
import android.view.animation.Interpolator;

public class RecyclerView$SmoothScroller$Action {
    public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
    private boolean mChanged;
    private int mConsecutiveUpdates;
    private int mDuration;
    private int mDx;
    private int mDy;
    private Interpolator mInterpolator;
    private int mJumpToPosition;

    public RecyclerView$SmoothScroller$Action(int dx, int dy) {
        this(dx, dy, Integer.MIN_VALUE, null);
    }

    public RecyclerView$SmoothScroller$Action(int dx, int dy, int duration) {
        this(dx, dy, duration, null);
    }

    public RecyclerView$SmoothScroller$Action(int dx, int dy, int duration, Interpolator interpolator) {
        this.mJumpToPosition = -1;
        this.mChanged = false;
        this.mConsecutiveUpdates = 0;
        this.mDx = dx;
        this.mDy = dy;
        this.mDuration = duration;
        this.mInterpolator = interpolator;
    }

    public void jumpTo(int targetPosition) {
        this.mJumpToPosition = targetPosition;
    }

    boolean hasJumpTarget() {
        return this.mJumpToPosition >= 0;
    }

    void runIfNecessary(RecyclerView recyclerView) {
        if (this.mJumpToPosition >= 0) {
            int position = this.mJumpToPosition;
            this.mJumpToPosition = -1;
            recyclerView.jumpToPositionForSmoothScroller(position);
            this.mChanged = false;
            return;
        }
        if (this.mChanged) {
            validate();
            if (this.mInterpolator != null) {
                recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
            } else if (this.mDuration == Integer.MIN_VALUE) {
                recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
            } else {
                recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
            }
            this.mConsecutiveUpdates++;
            if (this.mConsecutiveUpdates > 10) {
                Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
            }
            this.mChanged = false;
        } else {
            this.mConsecutiveUpdates = 0;
        }
    }

    private void validate() {
        if (this.mInterpolator != null && this.mDuration < 1) {
            throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
        } else if (this.mDuration < 1) {
            throw new IllegalStateException("Scroll duration must be a positive number");
        }
    }

    public int getDx() {
        return this.mDx;
    }

    public void setDx(int dx) {
        this.mChanged = true;
        this.mDx = dx;
    }

    public int getDy() {
        return this.mDy;
    }

    public void setDy(int dy) {
        this.mChanged = true;
        this.mDy = dy;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setDuration(int duration) {
        this.mChanged = true;
        this.mDuration = duration;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mChanged = true;
        this.mInterpolator = interpolator;
    }

    public void update(int dx, int dy, int duration, Interpolator interpolator) {
        this.mDx = dx;
        this.mDy = dy;
        this.mDuration = duration;
        this.mInterpolator = interpolator;
        this.mChanged = true;
    }
}
