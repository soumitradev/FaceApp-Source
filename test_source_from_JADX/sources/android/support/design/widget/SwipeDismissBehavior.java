package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SwipeDismissBehavior<V extends View> extends Behavior<V> {
    private static final float DEFAULT_ALPHA_END_DISTANCE = 0.5f;
    private static final float DEFAULT_ALPHA_START_DISTANCE = 0.0f;
    private static final float DEFAULT_DRAG_DISMISS_THRESHOLD = 0.5f;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    public static final int SWIPE_DIRECTION_ANY = 2;
    public static final int SWIPE_DIRECTION_END_TO_START = 1;
    public static final int SWIPE_DIRECTION_START_TO_END = 0;
    float mAlphaEndSwipeDistance = 0.5f;
    float mAlphaStartSwipeDistance = 0.0f;
    private final Callback mDragCallback = new SwipeDismissBehavior$1(this);
    float mDragDismissThreshold = 0.5f;
    private boolean mInterceptingEvents;
    OnDismissListener mListener;
    private float mSensitivity = 0.0f;
    private boolean mSensitivitySet;
    int mSwipeDirection = 2;
    ViewDragHelper mViewDragHelper;

    public interface OnDismissListener {
        void onDismiss(View view);

        void onDragStateChanged(int i);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    private @interface SwipeDirection {
    }

    public void setListener(OnDismissListener listener) {
        this.mListener = listener;
    }

    public void setSwipeDirection(int direction) {
        this.mSwipeDirection = direction;
    }

    public void setDragDismissDistance(float distance) {
        this.mDragDismissThreshold = clamp(0.0f, distance, 1.0f);
    }

    public void setStartAlphaSwipeDistance(float fraction) {
        this.mAlphaStartSwipeDistance = clamp(0.0f, fraction, 1.0f);
    }

    public void setEndAlphaSwipeDistance(float fraction) {
        this.mAlphaEndSwipeDistance = clamp(0.0f, fraction, 1.0f);
    }

    public void setSensitivity(float sensitivity) {
        this.mSensitivity = sensitivity;
        this.mSensitivitySet = true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        boolean dispatchEventToHelper = this.mInterceptingEvents;
        int actionMasked = event.getActionMasked();
        if (actionMasked != 3) {
            switch (actionMasked) {
                case 0:
                    this.mInterceptingEvents = parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY());
                    dispatchEventToHelper = this.mInterceptingEvents;
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
        this.mInterceptingEvents = false;
        if (!dispatchEventToHelper) {
            return false;
        }
        ensureViewDragHelper(parent);
        return this.mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V v, MotionEvent event) {
        if (this.mViewDragHelper == null) {
            return false;
        }
        this.mViewDragHelper.processTouchEvent(event);
        return true;
    }

    public boolean canSwipeDismissView(@NonNull View view) {
        return true;
    }

    private void ensureViewDragHelper(ViewGroup parent) {
        if (this.mViewDragHelper == null) {
            ViewDragHelper create;
            if (this.mSensitivitySet) {
                create = ViewDragHelper.create(parent, this.mSensitivity, this.mDragCallback);
            } else {
                create = ViewDragHelper.create(parent, this.mDragCallback);
            }
            this.mViewDragHelper = create;
        }
    }

    static float clamp(float min, float value, float max) {
        return Math.min(Math.max(min, value), max);
    }

    static int clamp(int min, int value, int max) {
        return Math.min(Math.max(min, value), max);
    }

    public int getDragState() {
        return this.mViewDragHelper != null ? this.mViewDragHelper.getViewDragState() : 0;
    }

    static float fraction(float startValue, float endValue, float value) {
        return (value - startValue) / (endValue - startValue);
    }
}
