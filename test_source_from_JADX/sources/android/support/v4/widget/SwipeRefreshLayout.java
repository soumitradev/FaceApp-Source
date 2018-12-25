package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;
import com.google.common.primitives.Ints;
import org.catrobat.catroid.common.BrickValues;

public class SwipeRefreshLayout extends ViewGroup implements NestedScrollingParent, NestedScrollingChild {
    private static final int ALPHA_ANIMATION_DURATION = 300;
    private static final int ANIMATE_TO_START_DURATION = 200;
    private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
    private static final int CIRCLE_BG_LIGHT = -328966;
    @VisibleForTesting
    static final int CIRCLE_DIAMETER = 40;
    @VisibleForTesting
    static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0f;
    public static final int DEFAULT = 1;
    private static final int DEFAULT_CIRCLE_TARGET = 64;
    private static final float DRAG_RATE = 0.5f;
    private static final int INVALID_POINTER = -1;
    public static final int LARGE = 0;
    private static final int[] LAYOUT_ATTRS = new int[]{16842766};
    private static final String LOG_TAG = SwipeRefreshLayout.class.getSimpleName();
    private static final int MAX_ALPHA = 255;
    private static final float MAX_PROGRESS_ANGLE = 0.8f;
    private static final int SCALE_DOWN_DURATION = 150;
    private static final int STARTING_PROGRESS_ALPHA = 76;
    private int mActivePointerId;
    private Animation mAlphaMaxAnimation;
    private Animation mAlphaStartAnimation;
    private final Animation mAnimateToCorrectPosition;
    private final Animation mAnimateToStartPosition;
    private SwipeRefreshLayout$OnChildScrollUpCallback mChildScrollUpCallback;
    private int mCircleDiameter;
    CircleImageView mCircleView;
    private int mCircleViewIndex;
    int mCurrentTargetOffsetTop;
    private final DecelerateInterpolator mDecelerateInterpolator;
    protected int mFrom;
    private float mInitialDownY;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    SwipeRefreshLayout$OnRefreshListener mListener;
    private int mMediumAnimationDuration;
    private boolean mNestedScrollInProgress;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    boolean mNotify;
    protected int mOriginalOffsetTop;
    private final int[] mParentOffsetInWindow;
    private final int[] mParentScrollConsumed;
    CircularProgressDrawable mProgress;
    private AnimationListener mRefreshListener;
    boolean mRefreshing;
    private boolean mReturningToStart;
    boolean mScale;
    private Animation mScaleAnimation;
    private Animation mScaleDownAnimation;
    private Animation mScaleDownToStartAnimation;
    int mSpinnerOffsetEnd;
    float mStartingScale;
    private View mTarget;
    private float mTotalDragDistance;
    private float mTotalUnconsumed;
    private int mTouchSlop;
    boolean mUsingCustomStart;

    void reset() {
        this.mCircleView.clearAnimation();
        this.mProgress.stop();
        this.mCircleView.setVisibility(8);
        setColorViewAlpha(255);
        if (this.mScale) {
            setAnimationProgress(0.0f);
        } else {
            setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCurrentTargetOffsetTop);
        }
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            reset();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    private void setColorViewAlpha(int targetAlpha) {
        this.mCircleView.getBackground().setAlpha(targetAlpha);
        this.mProgress.setAlpha(targetAlpha);
    }

    public void setProgressViewOffset(boolean scale, int start, int end) {
        this.mScale = scale;
        this.mOriginalOffsetTop = start;
        this.mSpinnerOffsetEnd = end;
        this.mUsingCustomStart = true;
        reset();
        this.mRefreshing = false;
    }

    public int getProgressViewStartOffset() {
        return this.mOriginalOffsetTop;
    }

    public int getProgressViewEndOffset() {
        return this.mSpinnerOffsetEnd;
    }

    public void setProgressViewEndTarget(boolean scale, int end) {
        this.mSpinnerOffsetEnd = end;
        this.mScale = scale;
        this.mCircleView.invalidate();
    }

    public void setSize(int size) {
        if (size == 0 || size == 1) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            if (size == 0) {
                this.mCircleDiameter = (int) (metrics.density * 56.0f);
            } else {
                this.mCircleDiameter = (int) (metrics.density * 40.0f);
            }
            this.mCircleView.setImageDrawable(null);
            this.mProgress.setStyle(size);
            this.mCircleView.setImageDrawable(this.mProgress);
        }
    }

    public SwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mRefreshing = false;
        this.mTotalDragDistance = -1.0f;
        this.mParentScrollConsumed = new int[2];
        this.mParentOffsetInWindow = new int[2];
        this.mActivePointerId = -1;
        this.mCircleViewIndex = -1;
        this.mRefreshListener = new SwipeRefreshLayout$1(this);
        this.mAnimateToCorrectPosition = new SwipeRefreshLayout$6(this);
        this.mAnimateToStartPosition = new SwipeRefreshLayout$7(this);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mMediumAnimationDuration = getResources().getInteger(17694721);
        setWillNotDraw(false);
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.0f);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        this.mCircleDiameter = (int) (metrics.density * 40.0f);
        createProgressView();
        setChildrenDrawingOrderEnabled(true);
        this.mSpinnerOffsetEnd = (int) (metrics.density * 64.0f);
        this.mTotalDragDistance = (float) this.mSpinnerOffsetEnd;
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        this.mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        int i = -this.mCircleDiameter;
        this.mCurrentTargetOffsetTop = i;
        this.mOriginalOffsetTop = i;
        moveToStart(1.0f);
        TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
        setEnabled(a.getBoolean(0, true));
        a.recycle();
    }

    protected int getChildDrawingOrder(int childCount, int i) {
        if (this.mCircleViewIndex < 0) {
            return i;
        }
        if (i == childCount - 1) {
            return this.mCircleViewIndex;
        }
        if (i >= this.mCircleViewIndex) {
            return i + 1;
        }
        return i;
    }

    private void createProgressView() {
        this.mCircleView = new CircleImageView(getContext(), CIRCLE_BG_LIGHT);
        this.mProgress = new CircularProgressDrawable(getContext());
        this.mProgress.setStyle(1);
        this.mCircleView.setImageDrawable(this.mProgress);
        this.mCircleView.setVisibility(8);
        addView(this.mCircleView);
    }

    public void setOnRefreshListener(@Nullable SwipeRefreshLayout$OnRefreshListener listener) {
        this.mListener = listener;
    }

    public void setRefreshing(boolean refreshing) {
        if (!refreshing || this.mRefreshing == refreshing) {
            setRefreshing(refreshing, false);
            return;
        }
        int endTarget;
        this.mRefreshing = refreshing;
        if (this.mUsingCustomStart) {
            endTarget = this.mSpinnerOffsetEnd;
        } else {
            endTarget = this.mSpinnerOffsetEnd + this.mOriginalOffsetTop;
        }
        setTargetOffsetTopAndBottom(endTarget - this.mCurrentTargetOffsetTop);
        this.mNotify = false;
        startScaleUpAnimation(this.mRefreshListener);
    }

    private void startScaleUpAnimation(AnimationListener listener) {
        this.mCircleView.setVisibility(0);
        this.mProgress.setAlpha(255);
        this.mScaleAnimation = new SwipeRefreshLayout$2(this);
        this.mScaleAnimation.setDuration((long) this.mMediumAnimationDuration);
        if (listener != null) {
            this.mCircleView.setAnimationListener(listener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleAnimation);
    }

    void setAnimationProgress(float progress) {
        this.mCircleView.setScaleX(progress);
        this.mCircleView.setScaleY(progress);
    }

    private void setRefreshing(boolean refreshing, boolean notify) {
        if (this.mRefreshing != refreshing) {
            this.mNotify = notify;
            ensureTarget();
            this.mRefreshing = refreshing;
            if (this.mRefreshing) {
                animateOffsetToCorrectPosition(this.mCurrentTargetOffsetTop, this.mRefreshListener);
            } else {
                startScaleDownAnimation(this.mRefreshListener);
            }
        }
    }

    void startScaleDownAnimation(AnimationListener listener) {
        this.mScaleDownAnimation = new SwipeRefreshLayout$3(this);
        this.mScaleDownAnimation.setDuration(150);
        this.mCircleView.setAnimationListener(listener);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownAnimation);
    }

    private void startProgressAlphaStartAnimation() {
        this.mAlphaStartAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 76);
    }

    private void startProgressAlphaMaxAnimation() {
        this.mAlphaMaxAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 255);
    }

    private Animation startAlphaAnimation(int startingAlpha, int endingAlpha) {
        Animation alpha = new SwipeRefreshLayout$4(this, startingAlpha, endingAlpha);
        alpha.setDuration(300);
        this.mCircleView.setAnimationListener(null);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(alpha);
        return alpha;
    }

    @Deprecated
    public void setProgressBackgroundColor(int colorRes) {
        setProgressBackgroundColorSchemeResource(colorRes);
    }

    public void setProgressBackgroundColorSchemeResource(@ColorRes int colorRes) {
        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), colorRes));
    }

    public void setProgressBackgroundColorSchemeColor(@ColorInt int color) {
        this.mCircleView.setBackgroundColor(color);
    }

    @Deprecated
    public void setColorScheme(@ColorRes int... colors) {
        setColorSchemeResources(colors);
    }

    public void setColorSchemeResources(@ColorRes int... colorResIds) {
        Context context = getContext();
        int[] colorRes = new int[colorResIds.length];
        for (int i = 0; i < colorResIds.length; i++) {
            colorRes[i] = ContextCompat.getColor(context, colorResIds[i]);
        }
        setColorSchemeColors(colorRes);
    }

    public void setColorSchemeColors(@ColorInt int... colors) {
        ensureTarget();
        this.mProgress.setColorSchemeColors(colors);
    }

    public boolean isRefreshing() {
        return this.mRefreshing;
    }

    private void ensureTarget() {
        if (this.mTarget == null) {
            int i = 0;
            while (i < getChildCount()) {
                View child = getChildAt(i);
                if (child.equals(this.mCircleView)) {
                    i++;
                } else {
                    this.mTarget = child;
                    return;
                }
            }
        }
    }

    public void setDistanceToTriggerSync(int distance) {
        this.mTotalDragDistance = (float) distance;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (r0.mTarget == null) {
                ensureTarget();
            }
            if (r0.mTarget != null) {
                View child = r0.mTarget;
                int childLeft = getPaddingLeft();
                int childTop = getPaddingTop();
                child.layout(childLeft, childTop, childLeft + ((width - getPaddingLeft()) - getPaddingRight()), childTop + ((height - getPaddingTop()) - getPaddingBottom()));
                int circleWidth = r0.mCircleView.getMeasuredWidth();
                r0.mCircleView.layout((width / 2) - (circleWidth / 2), r0.mCurrentTargetOffsetTop, (width / 2) + (circleWidth / 2), r0.mCurrentTargetOffsetTop + r0.mCircleView.getMeasuredHeight());
            }
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mTarget == null) {
            ensureTarget();
        }
        if (this.mTarget != null) {
            this.mTarget.measure(MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), Ints.MAX_POWER_OF_TWO));
            this.mCircleView.measure(MeasureSpec.makeMeasureSpec(this.mCircleDiameter, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(this.mCircleDiameter, Ints.MAX_POWER_OF_TWO));
            this.mCircleViewIndex = -1;
            for (int index = 0; index < getChildCount(); index++) {
                if (getChildAt(index) == this.mCircleView) {
                    this.mCircleViewIndex = index;
                    break;
                }
            }
        }
    }

    public int getProgressCircleDiameter() {
        return this.mCircleDiameter;
    }

    public boolean canChildScrollUp() {
        if (this.mChildScrollUpCallback != null) {
            return this.mChildScrollUpCallback.canChildScrollUp(this, this.mTarget);
        }
        if (this.mTarget instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) this.mTarget, -1);
        }
        return this.mTarget.canScrollVertically(-1);
    }

    public void setOnChildScrollUpCallback(@Nullable SwipeRefreshLayout$OnChildScrollUpCallback callback) {
        this.mChildScrollUpCallback = callback;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        int action = ev.getActionMasked();
        if (this.mReturningToStart && action == 0) {
            this.mReturningToStart = false;
        }
        if (!(!isEnabled() || this.mReturningToStart || canChildScrollUp() || this.mRefreshing)) {
            if (!this.mNestedScrollInProgress) {
                if (action != 6) {
                    int pointerIndex;
                    switch (action) {
                        case 0:
                            setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCircleView.getTop());
                            this.mActivePointerId = ev.getPointerId(0);
                            this.mIsBeingDragged = false;
                            pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                            if (pointerIndex >= 0) {
                                this.mInitialDownY = ev.getY(pointerIndex);
                                break;
                            }
                            return false;
                        case 1:
                        case 3:
                            this.mIsBeingDragged = false;
                            this.mActivePointerId = -1;
                            break;
                        case 2:
                            if (this.mActivePointerId != -1) {
                                pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                                if (pointerIndex >= 0) {
                                    startDragging(ev.getY(pointerIndex));
                                    break;
                                }
                                return false;
                            }
                            Log.e(LOG_TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
                            return false;
                        default:
                            break;
                    }
                }
                onSecondaryPointerUp(ev);
                return this.mIsBeingDragged;
            }
        }
        return false;
    }

    public void requestDisallowInterceptTouchEvent(boolean b) {
        if (VERSION.SDK_INT < 21 && (this.mTarget instanceof AbsListView)) {
            return;
        }
        if (this.mTarget == null || ViewCompat.isNestedScrollingEnabled(this.mTarget)) {
            super.requestDisallowInterceptTouchEvent(b);
        }
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (!isEnabled() || this.mReturningToStart || this.mRefreshing || (nestedScrollAxes & 2) == 0) ? false : true;
    }

    public void onNestedScrollAccepted(View child, View target, int axes) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        startNestedScroll(axes & 2);
        this.mTotalUnconsumed = 0.0f;
        this.mNestedScrollInProgress = true;
    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (dy > 0 && this.mTotalUnconsumed > 0.0f) {
            if (((float) dy) > this.mTotalUnconsumed) {
                consumed[1] = dy - ((int) this.mTotalUnconsumed);
                this.mTotalUnconsumed = 0.0f;
            } else {
                this.mTotalUnconsumed -= (float) dy;
                consumed[1] = dy;
            }
            moveSpinner(this.mTotalUnconsumed);
        }
        if (this.mUsingCustomStart && dy > 0 && this.mTotalUnconsumed == 0.0f && Math.abs(dy - consumed[1]) > 0) {
            this.mCircleView.setVisibility(8);
        }
        int[] parentConsumed = this.mParentScrollConsumed;
        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
            consumed[0] = consumed[0] + parentConsumed[0];
            consumed[1] = consumed[1] + parentConsumed[1];
        }
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    public void onStopNestedScroll(View target) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(target);
        this.mNestedScrollInProgress = false;
        if (this.mTotalUnconsumed > 0.0f) {
            finishSpinner(this.mTotalUnconsumed);
            this.mTotalUnconsumed = 0.0f;
        }
        stopNestedScroll();
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, this.mParentOffsetInWindow);
        int dy = this.mParentOffsetInWindow[1] + dyUnconsumed;
        if (dy < 0 && !canChildScrollUp()) {
            this.mTotalUnconsumed += (float) Math.abs(dy);
            moveSpinner(this.mTotalUnconsumed);
        }
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    public boolean isNestedScrollingEnabled() {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int axes) {
        return this.mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    public void stopNestedScroll() {
        this.mNestedScrollingChildHelper.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return this.mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return dispatchNestedPreFling(velocityX, velocityY);
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return this.mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    private boolean isAnimationRunning(Animation animation) {
        return (animation == null || !animation.hasStarted() || animation.hasEnded()) ? false : true;
    }

    private void moveSpinner(float overscrollTop) {
        this.mProgress.setArrowEnabled(true);
        float dragPercent = Math.min(1.0f, Math.abs(overscrollTop / this.mTotalDragDistance));
        float adjustedPercent = (((float) Math.max(((double) dragPercent) - 0.4d, BrickValues.SET_COLOR_TO)) * 5.0f) / 3.0f;
        float slingshotDist = (float) (this.mUsingCustomStart ? r0.mSpinnerOffsetEnd - r0.mOriginalOffsetTop : r0.mSpinnerOffsetEnd);
        float tensionSlingshotPercent = Math.max(0.0f, Math.min(Math.abs(overscrollTop) - this.mTotalDragDistance, slingshotDist * 2.0f) / slingshotDist);
        float tensionPercent = ((float) (((double) (tensionSlingshotPercent / 4.0f)) - Math.pow((double) (tensionSlingshotPercent / 4.0f), 2.0d))) * 2.0f;
        int targetY = r0.mOriginalOffsetTop + ((int) ((slingshotDist * dragPercent) + ((slingshotDist * tensionPercent) * 2.0f)));
        if (r0.mCircleView.getVisibility() != 0) {
            r0.mCircleView.setVisibility(0);
        }
        if (!r0.mScale) {
            r0.mCircleView.setScaleX(1.0f);
            r0.mCircleView.setScaleY(1.0f);
        }
        if (r0.mScale) {
            setAnimationProgress(Math.min(1.0f, overscrollTop / r0.mTotalDragDistance));
        }
        if (overscrollTop < r0.mTotalDragDistance) {
            if (r0.mProgress.getAlpha() > 76 && !isAnimationRunning(r0.mAlphaStartAnimation)) {
                startProgressAlphaStartAnimation();
            }
        } else if (r0.mProgress.getAlpha() < 255 && !isAnimationRunning(r0.mAlphaMaxAnimation)) {
            startProgressAlphaMaxAnimation();
        }
        r0.mProgress.setStartEndTrim(0.0f, Math.min(0.8f, adjustedPercent * 0.8f));
        r0.mProgress.setArrowScale(Math.min(1.0f, adjustedPercent));
        r0.mProgress.setProgressRotation((((0.4f * adjustedPercent) - 16.0f) + (2.0f * tensionPercent)) * DRAG_RATE);
        setTargetOffsetTopAndBottom(targetY - r0.mCurrentTargetOffsetTop);
    }

    private void finishSpinner(float overscrollTop) {
        if (overscrollTop > this.mTotalDragDistance) {
            setRefreshing(true, true);
            return;
        }
        this.mRefreshing = false;
        this.mProgress.setStartEndTrim(0.0f, 0.0f);
        AnimationListener listener = null;
        if (!this.mScale) {
            listener = new SwipeRefreshLayout$5(this);
        }
        animateOffsetToStartPosition(this.mCurrentTargetOffsetTop, listener);
        this.mProgress.setArrowEnabled(false);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (this.mReturningToStart && action == 0) {
            this.mReturningToStart = false;
        }
        if (!(!isEnabled() || this.mReturningToStart || canChildScrollUp() || this.mRefreshing)) {
            if (!this.mNestedScrollInProgress) {
                int pointerIndex;
                float overscrollTop;
                switch (action) {
                    case 0:
                        this.mActivePointerId = ev.getPointerId(0);
                        this.mIsBeingDragged = false;
                        break;
                    case 1:
                        pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                        if (pointerIndex < 0) {
                            Log.e(LOG_TAG, "Got ACTION_UP event but don't have an active pointer id.");
                            return false;
                        }
                        if (this.mIsBeingDragged) {
                            overscrollTop = (ev.getY(pointerIndex) - this.mInitialMotionY) * DRAG_RATE;
                            this.mIsBeingDragged = false;
                            finishSpinner(overscrollTop);
                        }
                        this.mActivePointerId = -1;
                        return false;
                    case 2:
                        pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                        if (pointerIndex < 0) {
                            Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                            return false;
                        }
                        float y = ev.getY(pointerIndex);
                        startDragging(y);
                        if (this.mIsBeingDragged) {
                            overscrollTop = (y - this.mInitialMotionY) * DRAG_RATE;
                            if (overscrollTop > 0.0f) {
                                moveSpinner(overscrollTop);
                                break;
                            }
                            return false;
                        }
                        break;
                    case 3:
                        return false;
                    case 5:
                        pointerIndex = ev.getActionIndex();
                        if (pointerIndex >= 0) {
                            this.mActivePointerId = ev.getPointerId(pointerIndex);
                            break;
                        }
                        Log.e(LOG_TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                        return false;
                    case 6:
                        onSecondaryPointerUp(ev);
                        break;
                    default:
                        break;
                }
                return true;
            }
        }
        return false;
    }

    private void startDragging(float y) {
        if (y - this.mInitialDownY > ((float) this.mTouchSlop) && !this.mIsBeingDragged) {
            this.mInitialMotionY = this.mInitialDownY + ((float) this.mTouchSlop);
            this.mIsBeingDragged = true;
            this.mProgress.setAlpha(76);
        }
    }

    private void animateOffsetToCorrectPosition(int from, AnimationListener listener) {
        this.mFrom = from;
        this.mAnimateToCorrectPosition.reset();
        this.mAnimateToCorrectPosition.setDuration(200);
        this.mAnimateToCorrectPosition.setInterpolator(this.mDecelerateInterpolator);
        if (listener != null) {
            this.mCircleView.setAnimationListener(listener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToCorrectPosition);
    }

    private void animateOffsetToStartPosition(int from, AnimationListener listener) {
        if (this.mScale) {
            startScaleDownReturnToStartAnimation(from, listener);
            return;
        }
        this.mFrom = from;
        this.mAnimateToStartPosition.reset();
        this.mAnimateToStartPosition.setDuration(200);
        this.mAnimateToStartPosition.setInterpolator(this.mDecelerateInterpolator);
        if (listener != null) {
            this.mCircleView.setAnimationListener(listener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToStartPosition);
    }

    void moveToStart(float interpolatedTime) {
        setTargetOffsetTopAndBottom((this.mFrom + ((int) (((float) (this.mOriginalOffsetTop - this.mFrom)) * interpolatedTime))) - this.mCircleView.getTop());
    }

    private void startScaleDownReturnToStartAnimation(int from, AnimationListener listener) {
        this.mFrom = from;
        this.mStartingScale = this.mCircleView.getScaleX();
        this.mScaleDownToStartAnimation = new SwipeRefreshLayout$8(this);
        this.mScaleDownToStartAnimation.setDuration(150);
        if (listener != null) {
            this.mCircleView.setAnimationListener(listener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownToStartAnimation);
    }

    void setTargetOffsetTopAndBottom(int offset) {
        this.mCircleView.bringToFront();
        ViewCompat.offsetTopAndBottom(this.mCircleView, offset);
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = ev.getActionIndex();
        if (ev.getPointerId(pointerIndex) == this.mActivePointerId) {
            this.mActivePointerId = ev.getPointerId(pointerIndex == 0 ? 1 : 0);
        }
    }
}
