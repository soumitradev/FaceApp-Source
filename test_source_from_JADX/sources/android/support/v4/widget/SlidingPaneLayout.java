package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.badlogic.gdx.net.HttpStatus;
import com.google.common.primitives.Ints;
import java.util.ArrayList;

public class SlidingPaneLayout extends ViewGroup {
    private static final int DEFAULT_FADE_COLOR = -858993460;
    private static final int DEFAULT_OVERHANG_SIZE = 32;
    static final SlidingPaneLayout$SlidingPanelLayoutImpl IMPL;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final String TAG = "SlidingPaneLayout";
    private boolean mCanSlide;
    private int mCoveredFadeColor;
    final ViewDragHelper mDragHelper;
    private boolean mFirstLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    boolean mIsUnableToDrag;
    private final int mOverhangSize;
    private SlidingPaneLayout$PanelSlideListener mPanelSlideListener;
    private int mParallaxBy;
    private float mParallaxOffset;
    final ArrayList<SlidingPaneLayout$DisableLayerRunnable> mPostedRunnables;
    boolean mPreservedOpenState;
    private Drawable mShadowDrawableLeft;
    private Drawable mShadowDrawableRight;
    float mSlideOffset;
    int mSlideRange;
    View mSlideableView;
    private int mSliderFadeColor;
    private final Rect mTmpRect;

    static {
        if (VERSION.SDK_INT >= 17) {
            IMPL = new SlidingPaneLayout$SlidingPanelLayoutImplJBMR1();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new SlidingPaneLayout$SlidingPanelLayoutImplJB();
        } else {
            IMPL = new SlidingPaneLayout$SlidingPanelLayoutImplBase();
        }
    }

    public SlidingPaneLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mSliderFadeColor = DEFAULT_FADE_COLOR;
        this.mFirstLayout = true;
        this.mTmpRect = new Rect();
        this.mPostedRunnables = new ArrayList();
        float density = context.getResources().getDisplayMetrics().density;
        this.mOverhangSize = (int) ((32.0f * density) + 0.5f);
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new SlidingPaneLayout$AccessibilityDelegate(this));
        ViewCompat.setImportantForAccessibility(this, 1);
        this.mDragHelper = ViewDragHelper.create(this, 0.5f, new SlidingPaneLayout$DragHelperCallback(this));
        this.mDragHelper.setMinVelocity(400.0f * density);
    }

    public void setParallaxDistance(int parallaxBy) {
        this.mParallaxBy = parallaxBy;
        requestLayout();
    }

    public int getParallaxDistance() {
        return this.mParallaxBy;
    }

    public void setSliderFadeColor(@ColorInt int color) {
        this.mSliderFadeColor = color;
    }

    @ColorInt
    public int getSliderFadeColor() {
        return this.mSliderFadeColor;
    }

    public void setCoveredFadeColor(@ColorInt int color) {
        this.mCoveredFadeColor = color;
    }

    @ColorInt
    public int getCoveredFadeColor() {
        return this.mCoveredFadeColor;
    }

    public void setPanelSlideListener(@Nullable SlidingPaneLayout$PanelSlideListener listener) {
        this.mPanelSlideListener = listener;
    }

    void dispatchOnPanelSlide(View panel) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelSlide(panel, this.mSlideOffset);
        }
    }

    void dispatchOnPanelOpened(View panel) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelOpened(panel);
        }
        sendAccessibilityEvent(32);
    }

    void dispatchOnPanelClosed(View panel) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelClosed(panel);
        }
        sendAccessibilityEvent(32);
    }

    void updateObscuredViewsVisibility(View panel) {
        int left;
        boolean z;
        View view = panel;
        boolean isLayoutRtl = isLayoutRtlSupport();
        int startBound = isLayoutRtl ? getWidth() - getPaddingRight() : getPaddingLeft();
        int endBound = isLayoutRtl ? getPaddingLeft() : getWidth() - getPaddingRight();
        int topBound = getPaddingTop();
        int bottomBound = getHeight() - getPaddingBottom();
        int bottom;
        int top;
        int right;
        if (view == null || !viewIsOpaque(panel)) {
            bottom = 0;
            top = 0;
            right = 0;
            left = 0;
        } else {
            left = panel.getLeft();
            right = panel.getRight();
            top = panel.getTop();
            bottom = panel.getBottom();
        }
        int i = 0;
        int childCount = getChildCount();
        while (i < childCount) {
            View child = getChildAt(i);
            if (child == view) {
                z = isLayoutRtl;
                return;
            }
            if (child.getVisibility() == 8) {
                z = isLayoutRtl;
            } else {
                int vis;
                int clampedChildLeft = Math.max(isLayoutRtl ? endBound : startBound, child.getLeft());
                int clampedChildTop = Math.max(topBound, child.getTop());
                z = isLayoutRtl;
                int clampedChildRight = Math.min(isLayoutRtl ? startBound : endBound, child.getRight());
                int clampedChildBottom = Math.min(bottomBound, child.getBottom());
                if (clampedChildLeft < left || clampedChildTop < top || clampedChildRight > right || clampedChildBottom > bottom) {
                    vis = 0;
                } else {
                    vis = 4;
                }
                int i2 = clampedChildRight;
                child.setVisibility(vis);
            }
            i++;
            isLayoutRtl = z;
            view = panel;
        }
        SlidingPaneLayout slidingPaneLayout = this;
        z = isLayoutRtl;
    }

    void setAllChildrenVisible() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == 4) {
                child.setVisibility(0);
            }
        }
    }

    private static boolean viewIsOpaque(View v) {
        boolean z = true;
        if (v.isOpaque()) {
            return true;
        }
        if (VERSION.SDK_INT >= 18) {
            return false;
        }
        Drawable bg = v.getBackground();
        if (bg == null) {
            return false;
        }
        if (bg.getOpacity() != -1) {
            z = false;
        }
        return z;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
        int count = this.mPostedRunnables.size();
        for (int i = 0; i < count; i++) {
            ((SlidingPaneLayout$DisableLayerRunnable) this.mPostedRunnables.get(i)).run();
        }
        this.mPostedRunnables.clear();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize;
        int childCount;
        int maxLayoutHeight;
        SlidingPaneLayout slidingPaneLayout = this;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize2 = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode != Ints.MAX_POWER_OF_TWO) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            } else if (widthMode == Integer.MIN_VALUE) {
                widthMode = Ints.MAX_POWER_OF_TWO;
            } else if (widthMode == 0) {
                widthMode = Ints.MAX_POWER_OF_TWO;
                widthSize = HttpStatus.SC_MULTIPLE_CHOICES;
            }
        } else if (heightMode == 0) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Height must not be UNSPECIFIED");
            } else if (heightMode == 0) {
                heightMode = Integer.MIN_VALUE;
                heightSize2 = HttpStatus.SC_MULTIPLE_CHOICES;
            }
        }
        int layoutHeight = 0;
        int maxLayoutHeight2 = 0;
        if (heightMode == Integer.MIN_VALUE) {
            maxLayoutHeight2 = (heightSize2 - getPaddingTop()) - getPaddingBottom();
        } else if (heightMode == Ints.MAX_POWER_OF_TWO) {
            int paddingTop = (heightSize2 - getPaddingTop()) - getPaddingBottom();
            maxLayoutHeight2 = paddingTop;
            layoutHeight = paddingTop;
        }
        float weightSum = 0.0f;
        boolean canSlide = false;
        int widthAvailable = (widthSize - getPaddingLeft()) - getPaddingRight();
        int widthRemaining = widthAvailable;
        int childCount2 = getChildCount();
        if (childCount2 > 2) {
            Log.e(TAG, "onMeasure: More than two child views are not supported.");
        }
        slidingPaneLayout.mSlideableView = null;
        int widthRemaining2 = widthRemaining;
        widthRemaining = layoutHeight;
        layoutHeight = 0;
        while (true) {
            int i = 8;
            if (layoutHeight >= childCount2) {
                break;
            }
            View child = getChildAt(layoutHeight);
            SlidingPaneLayout$LayoutParams lp = (SlidingPaneLayout$LayoutParams) child.getLayoutParams();
            int widthMode2 = widthMode;
            if (child.getVisibility() == 8) {
                lp.dimWhenOffset = false;
            } else {
                int horizontalMargin;
                int childHeightSpec;
                boolean canSlide2;
                if (lp.weight > 0) {
                    weightSum += lp.weight;
                    if (lp.width == 0) {
                    }
                }
                widthMode = lp.leftMargin + lp.rightMargin;
                heightSize = heightSize2;
                if (lp.width == -2) {
                    heightSize2 = MeasureSpec.makeMeasureSpec(widthAvailable - widthMode, Integer.MIN_VALUE);
                } else if (lp.width == -1) {
                    heightSize2 = MeasureSpec.makeMeasureSpec(widthAvailable - widthMode, Ints.MAX_POWER_OF_TWO);
                } else {
                    heightSize2 = MeasureSpec.makeMeasureSpec(lp.width, Ints.MAX_POWER_OF_TWO);
                    horizontalMargin = widthMode;
                    if (lp.height == -2) {
                        i = MeasureSpec.makeMeasureSpec(maxLayoutHeight2, Integer.MIN_VALUE);
                    } else if (lp.height != -1) {
                        i = MeasureSpec.makeMeasureSpec(maxLayoutHeight2, Ints.MAX_POWER_OF_TWO);
                    } else {
                        i = MeasureSpec.makeMeasureSpec(lp.height, Ints.MAX_POWER_OF_TWO);
                        widthMode = i;
                        child.measure(heightSize2, widthMode);
                        i = child.getMeasuredWidth();
                        childHeightSpec = widthMode;
                        widthMode = child.getMeasuredHeight();
                        if (heightMode == Integer.MIN_VALUE && widthMode > widthRemaining) {
                            widthRemaining = Math.min(widthMode, maxLayoutHeight2);
                        }
                        widthRemaining2 -= i;
                        canSlide2 = widthRemaining2 >= 0;
                        lp.slideable = canSlide2;
                        canSlide2 |= canSlide;
                        if (lp.slideable) {
                            slidingPaneLayout.mSlideableView = child;
                        }
                        canSlide = canSlide2;
                        layoutHeight++;
                        widthMode = widthMode2;
                        heightSize2 = heightSize;
                    }
                    widthMode = i;
                    child.measure(heightSize2, widthMode);
                    i = child.getMeasuredWidth();
                    childHeightSpec = widthMode;
                    widthMode = child.getMeasuredHeight();
                    widthRemaining = Math.min(widthMode, maxLayoutHeight2);
                    widthRemaining2 -= i;
                    if (widthRemaining2 >= 0) {
                    }
                    lp.slideable = canSlide2;
                    canSlide2 |= canSlide;
                    if (lp.slideable) {
                        slidingPaneLayout.mSlideableView = child;
                    }
                    canSlide = canSlide2;
                    layoutHeight++;
                    widthMode = widthMode2;
                    heightSize2 = heightSize;
                }
                horizontalMargin = widthMode;
                if (lp.height == -2) {
                    i = MeasureSpec.makeMeasureSpec(maxLayoutHeight2, Integer.MIN_VALUE);
                } else if (lp.height != -1) {
                    i = MeasureSpec.makeMeasureSpec(lp.height, Ints.MAX_POWER_OF_TWO);
                    widthMode = i;
                    child.measure(heightSize2, widthMode);
                    i = child.getMeasuredWidth();
                    childHeightSpec = widthMode;
                    widthMode = child.getMeasuredHeight();
                    widthRemaining = Math.min(widthMode, maxLayoutHeight2);
                    widthRemaining2 -= i;
                    if (widthRemaining2 >= 0) {
                    }
                    lp.slideable = canSlide2;
                    canSlide2 |= canSlide;
                    if (lp.slideable) {
                        slidingPaneLayout.mSlideableView = child;
                    }
                    canSlide = canSlide2;
                    layoutHeight++;
                    widthMode = widthMode2;
                    heightSize2 = heightSize;
                } else {
                    i = MeasureSpec.makeMeasureSpec(maxLayoutHeight2, Ints.MAX_POWER_OF_TWO);
                }
                widthMode = i;
                child.measure(heightSize2, widthMode);
                i = child.getMeasuredWidth();
                childHeightSpec = widthMode;
                widthMode = child.getMeasuredHeight();
                widthRemaining = Math.min(widthMode, maxLayoutHeight2);
                widthRemaining2 -= i;
                if (widthRemaining2 >= 0) {
                }
                lp.slideable = canSlide2;
                canSlide2 |= canSlide;
                if (lp.slideable) {
                    slidingPaneLayout.mSlideableView = child;
                }
                canSlide = canSlide2;
                layoutHeight++;
                widthMode = widthMode2;
                heightSize2 = heightSize;
            }
            heightSize = heightSize2;
            layoutHeight++;
            widthMode = widthMode2;
            heightSize2 = heightSize;
        }
        heightSize = heightSize2;
        if (!canSlide) {
            if (weightSum <= 0.0f) {
                setMeasuredDimension(widthSize, (getPaddingTop() + widthRemaining) + getPaddingBottom());
                slidingPaneLayout.mCanSlide = canSlide;
                if (slidingPaneLayout.mDragHelper.getViewDragState() != 0 && !canSlide) {
                    slidingPaneLayout.mDragHelper.abort();
                    return;
                }
            }
        }
        widthMode = widthAvailable - slidingPaneLayout.mOverhangSize;
        heightSize2 = 0;
        while (heightSize2 < childCount2) {
            int heightMode2;
            int fixedPanelWidthLimit;
            child = getChildAt(heightSize2);
            if (child.getVisibility() != i) {
                SlidingPaneLayout$LayoutParams lp2 = (SlidingPaneLayout$LayoutParams) child.getLayoutParams();
                if (child.getVisibility() != i) {
                    boolean skippedFirstPass = lp2.width == 0 && lp2.weight > 0.0f;
                    int measuredWidth = skippedFirstPass ? 0 : child.getMeasuredWidth();
                    if (!canSlide || child == slidingPaneLayout.mSlideableView) {
                        heightMode2 = heightMode;
                        childCount = childCount2;
                        boolean z = skippedFirstPass;
                        i = measuredWidth;
                        if (lp2.weight > 0) {
                            if (lp2.width != 0) {
                                heightMode = MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), Ints.MAX_POWER_OF_TWO);
                            } else if (lp2.height == -2) {
                                heightMode = MeasureSpec.makeMeasureSpec(maxLayoutHeight2, Integer.MIN_VALUE);
                            } else if (lp2.height == -1) {
                                heightMode = MeasureSpec.makeMeasureSpec(maxLayoutHeight2, Ints.MAX_POWER_OF_TWO);
                            } else {
                                heightMode = MeasureSpec.makeMeasureSpec(lp2.height, Ints.MAX_POWER_OF_TWO);
                            }
                            if (canSlide) {
                                childCount2 = widthAvailable - (lp2.leftMargin + lp2.rightMargin);
                                fixedPanelWidthLimit = widthMode;
                                maxLayoutHeight = maxLayoutHeight2;
                                maxLayoutHeight2 = MeasureSpec.makeMeasureSpec(childCount2, Ints.MAX_POWER_OF_TWO);
                                if (i != childCount2) {
                                    child.measure(maxLayoutHeight2, heightMode);
                                }
                            } else {
                                fixedPanelWidthLimit = widthMode;
                                maxLayoutHeight = maxLayoutHeight2;
                                child.measure(MeasureSpec.makeMeasureSpec(i + ((int) ((lp2.weight * ((float) Math.max(0, widthRemaining2))) / weightSum)), Ints.MAX_POWER_OF_TWO), heightMode);
                            }
                        } else {
                            fixedPanelWidthLimit = widthMode;
                            maxLayoutHeight = maxLayoutHeight2;
                        }
                        heightSize2++;
                        heightMode = heightMode2;
                        childCount2 = childCount;
                        widthMode = fixedPanelWidthLimit;
                        maxLayoutHeight2 = maxLayoutHeight;
                        i = 8;
                    } else if (lp2.width < 0) {
                        if (measuredWidth <= widthMode) {
                            heightMode2 = heightMode;
                            if (lp2.weight <= 0) {
                                fixedPanelWidthLimit = widthMode;
                                maxLayoutHeight = maxLayoutHeight2;
                                childCount = childCount2;
                                heightSize2++;
                                heightMode = heightMode2;
                                childCount2 = childCount;
                                widthMode = fixedPanelWidthLimit;
                                maxLayoutHeight2 = maxLayoutHeight;
                                i = 8;
                            }
                        } else {
                            heightMode2 = heightMode;
                        }
                        if (skippedFirstPass) {
                            childCount = childCount2;
                            if (lp2.height == -2) {
                                childCount2 = MeasureSpec.makeMeasureSpec(maxLayoutHeight2, Integer.MIN_VALUE);
                                heightMode = Ints.MAX_POWER_OF_TWO;
                            } else if (lp2.height == -1) {
                                heightMode = Ints.MAX_POWER_OF_TWO;
                                childCount2 = MeasureSpec.makeMeasureSpec(maxLayoutHeight2, Ints.MAX_POWER_OF_TWO);
                            } else {
                                heightMode = Ints.MAX_POWER_OF_TWO;
                                childCount2 = MeasureSpec.makeMeasureSpec(lp2.height, Ints.MAX_POWER_OF_TWO);
                            }
                        } else {
                            childCount = childCount2;
                            heightMode = Ints.MAX_POWER_OF_TWO;
                            childCount2 = MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), Ints.MAX_POWER_OF_TWO);
                        }
                        child.measure(MeasureSpec.makeMeasureSpec(widthMode, heightMode), childCount2);
                        fixedPanelWidthLimit = widthMode;
                        maxLayoutHeight = maxLayoutHeight2;
                        heightSize2++;
                        heightMode = heightMode2;
                        childCount2 = childCount;
                        widthMode = fixedPanelWidthLimit;
                        maxLayoutHeight2 = maxLayoutHeight;
                        i = 8;
                    } else {
                        heightMode2 = heightMode;
                        childCount = childCount2;
                        fixedPanelWidthLimit = widthMode;
                        maxLayoutHeight = maxLayoutHeight2;
                        heightSize2++;
                        heightMode = heightMode2;
                        childCount2 = childCount;
                        widthMode = fixedPanelWidthLimit;
                        maxLayoutHeight2 = maxLayoutHeight;
                        i = 8;
                    }
                }
            }
            fixedPanelWidthLimit = widthMode;
            heightMode2 = heightMode;
            maxLayoutHeight = maxLayoutHeight2;
            childCount = childCount2;
            heightSize2++;
            heightMode = heightMode2;
            childCount2 = childCount;
            widthMode = fixedPanelWidthLimit;
            maxLayoutHeight2 = maxLayoutHeight;
            i = 8;
        }
        maxLayoutHeight = maxLayoutHeight2;
        childCount = childCount2;
        setMeasuredDimension(widthSize, (getPaddingTop() + widthRemaining) + getPaddingBottom());
        slidingPaneLayout.mCanSlide = canSlide;
        if (slidingPaneLayout.mDragHelper.getViewDragState() != 0) {
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        SlidingPaneLayout slidingPaneLayout = this;
        boolean isLayoutRtl = isLayoutRtlSupport();
        if (isLayoutRtl) {
            slidingPaneLayout.mDragHelper.setEdgeTrackingEnabled(2);
        } else {
            slidingPaneLayout.mDragHelper.setEdgeTrackingEnabled(1);
        }
        int width = r - l;
        int paddingStart = isLayoutRtl ? getPaddingRight() : getPaddingLeft();
        int paddingEnd = isLayoutRtl ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        int xStart = paddingStart;
        int nextXStart = xStart;
        if (slidingPaneLayout.mFirstLayout) {
            float f = (slidingPaneLayout.mCanSlide && slidingPaneLayout.mPreservedOpenState) ? 1.0f : 0.0f;
            slidingPaneLayout.mSlideOffset = f;
        }
        int xStart2 = xStart;
        xStart = 0;
        while (xStart < childCount) {
            int paddingStart2;
            View child = getChildAt(xStart);
            if (child.getVisibility() == 8) {
                paddingStart2 = paddingStart;
            } else {
                int range;
                SlidingPaneLayout$LayoutParams lp = (SlidingPaneLayout$LayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth();
                int offset = 0;
                if (lp.slideable) {
                    int margin = lp.leftMargin + lp.rightMargin;
                    range = (Math.min(nextXStart, (width - paddingEnd) - slidingPaneLayout.mOverhangSize) - xStart2) - margin;
                    slidingPaneLayout.mSlideRange = range;
                    int lpMargin = isLayoutRtl ? lp.rightMargin : lp.leftMargin;
                    paddingStart2 = paddingStart;
                    lp.dimWhenOffset = ((xStart2 + lpMargin) + range) + (childWidth / 2) > width - paddingEnd ? 1 : 0;
                    paddingStart = (int) (((float) range) * slidingPaneLayout.mSlideOffset);
                    xStart2 += paddingStart + lpMargin;
                    slidingPaneLayout.mSlideOffset = ((float) paddingStart) / ((float) slidingPaneLayout.mSlideRange);
                } else {
                    paddingStart2 = paddingStart;
                    if (!slidingPaneLayout.mCanSlide || slidingPaneLayout.mParallaxBy == 0) {
                        xStart2 = nextXStart;
                    } else {
                        xStart2 = nextXStart;
                        offset = (int) ((1.0f - slidingPaneLayout.mSlideOffset) * ((float) slidingPaneLayout.mParallaxBy));
                    }
                }
                if (isLayoutRtl) {
                    range = (width - xStart2) + offset;
                    paddingStart = range - childWidth;
                } else {
                    paddingStart = xStart2 - offset;
                    range = paddingStart + childWidth;
                }
                child.layout(paddingStart, paddingTop, range, paddingTop + child.getMeasuredHeight());
                nextXStart += child.getWidth();
            }
            xStart++;
            paddingStart = paddingStart2;
        }
        if (slidingPaneLayout.mFirstLayout) {
            if (slidingPaneLayout.mCanSlide) {
                if (slidingPaneLayout.mParallaxBy != 0) {
                    parallaxOtherViews(slidingPaneLayout.mSlideOffset);
                }
                if (((SlidingPaneLayout$LayoutParams) slidingPaneLayout.mSlideableView.getLayoutParams()).dimWhenOffset) {
                    dimChildView(slidingPaneLayout.mSlideableView, slidingPaneLayout.mSlideOffset, slidingPaneLayout.mSliderFadeColor);
                }
            } else {
                for (int i = 0; i < childCount; i++) {
                    dimChildView(getChildAt(i), 0.0f, slidingPaneLayout.mSliderFadeColor);
                }
            }
            updateObscuredViewsVisibility(slidingPaneLayout.mSlideableView);
        }
        slidingPaneLayout.mFirstLayout = false;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw) {
            this.mFirstLayout = true;
        }
    }

    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        if (!isInTouchMode() && !this.mCanSlide) {
            this.mPreservedOpenState = child == this.mSlideableView;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        boolean z = true;
        if (!this.mCanSlide && action == 0 && getChildCount() > 1) {
            View secondChild = getChildAt(1);
            if (secondChild != null) {
                this.mPreservedOpenState = this.mDragHelper.isViewUnder(secondChild, (int) ev.getX(), (int) ev.getY()) ^ true;
            }
        }
        if (this.mCanSlide) {
            if (!this.mIsUnableToDrag || action == 0) {
                if (action != 3) {
                    if (action != 1) {
                        boolean interceptTap = false;
                        float x;
                        float y;
                        if (action == 0) {
                            this.mIsUnableToDrag = false;
                            x = ev.getX();
                            y = ev.getY();
                            this.mInitialMotionX = x;
                            this.mInitialMotionY = y;
                            if (this.mDragHelper.isViewUnder(this.mSlideableView, (int) x, (int) y) && isDimmed(this.mSlideableView)) {
                                interceptTap = true;
                            }
                        } else if (action == 2) {
                            x = ev.getX();
                            y = ev.getY();
                            float adx = Math.abs(x - this.mInitialMotionX);
                            float ady = Math.abs(y - this.mInitialMotionY);
                            if (adx > ((float) this.mDragHelper.getTouchSlop()) && ady > adx) {
                                this.mDragHelper.cancel();
                                this.mIsUnableToDrag = true;
                                return false;
                            }
                        }
                        if (!this.mDragHelper.shouldInterceptTouchEvent(ev)) {
                            if (!interceptTap) {
                                z = false;
                            }
                        }
                        return z;
                    }
                }
                this.mDragHelper.cancel();
                return false;
            }
        }
        this.mDragHelper.cancel();
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.mCanSlide) {
            return super.onTouchEvent(ev);
        }
        this.mDragHelper.processTouchEvent(ev);
        float x;
        float y;
        switch (ev.getActionMasked()) {
            case 0:
                x = ev.getX();
                y = ev.getY();
                this.mInitialMotionX = x;
                this.mInitialMotionY = y;
                break;
            case 1:
                if (isDimmed(this.mSlideableView)) {
                    x = ev.getX();
                    y = ev.getY();
                    float dx = x - this.mInitialMotionX;
                    float dy = y - this.mInitialMotionY;
                    int slop = this.mDragHelper.getTouchSlop();
                    if ((dx * dx) + (dy * dy) < ((float) (slop * slop)) && this.mDragHelper.isViewUnder(this.mSlideableView, (int) x, (int) y)) {
                        closePane(this.mSlideableView, 0);
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    private boolean closePane(View pane, int initialVelocity) {
        if (!this.mFirstLayout) {
            if (!smoothSlideTo(0.0f, initialVelocity)) {
                return false;
            }
        }
        this.mPreservedOpenState = false;
        return true;
    }

    private boolean openPane(View pane, int initialVelocity) {
        if (!this.mFirstLayout) {
            if (!smoothSlideTo(1.0f, initialVelocity)) {
                return false;
            }
        }
        this.mPreservedOpenState = true;
        return true;
    }

    @Deprecated
    public void smoothSlideOpen() {
        openPane();
    }

    public boolean openPane() {
        return openPane(this.mSlideableView, 0);
    }

    @Deprecated
    public void smoothSlideClosed() {
        closePane();
    }

    public boolean closePane() {
        return closePane(this.mSlideableView, 0);
    }

    public boolean isOpen() {
        if (this.mCanSlide) {
            if (this.mSlideOffset != 1.0f) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public boolean canSlide() {
        return this.mCanSlide;
    }

    public boolean isSlideable() {
        return this.mCanSlide;
    }

    void onPanelDragged(int newLeft) {
        if (this.mSlideableView == null) {
            this.mSlideOffset = 0.0f;
            return;
        }
        boolean isLayoutRtl = isLayoutRtlSupport();
        SlidingPaneLayout$LayoutParams lp = (SlidingPaneLayout$LayoutParams) this.mSlideableView.getLayoutParams();
        this.mSlideOffset = ((float) ((isLayoutRtl ? (getWidth() - newLeft) - this.mSlideableView.getWidth() : newLeft) - ((isLayoutRtl ? getPaddingRight() : getPaddingLeft()) + (isLayoutRtl ? lp.rightMargin : lp.leftMargin)))) / ((float) this.mSlideRange);
        if (this.mParallaxBy != 0) {
            parallaxOtherViews(this.mSlideOffset);
        }
        if (lp.dimWhenOffset) {
            dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
        }
        dispatchOnPanelSlide(this.mSlideableView);
    }

    private void dimChildView(View v, float mag, int fadeColor) {
        SlidingPaneLayout$LayoutParams lp = (SlidingPaneLayout$LayoutParams) v.getLayoutParams();
        if (mag > 0.0f && fadeColor != 0) {
            int color = (((int) (((float) ((ViewCompat.MEASURED_STATE_MASK & fadeColor) >>> 24)) * mag)) << 24) | (ViewCompat.MEASURED_SIZE_MASK & fadeColor);
            if (lp.dimPaint == null) {
                lp.dimPaint = new Paint();
            }
            lp.dimPaint.setColorFilter(new PorterDuffColorFilter(color, Mode.SRC_OVER));
            if (v.getLayerType() != 2) {
                v.setLayerType(2, lp.dimPaint);
            }
            invalidateChildRegion(v);
        } else if (v.getLayerType() != 0) {
            if (lp.dimPaint != null) {
                lp.dimPaint.setColorFilter(null);
            }
            SlidingPaneLayout$DisableLayerRunnable dlr = new SlidingPaneLayout$DisableLayerRunnable(this, v);
            this.mPostedRunnables.add(dlr);
            ViewCompat.postOnAnimation(this, dlr);
        }
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        SlidingPaneLayout$LayoutParams lp = (SlidingPaneLayout$LayoutParams) child.getLayoutParams();
        int save = canvas.save();
        if (!(!this.mCanSlide || lp.slideable || this.mSlideableView == null)) {
            canvas.getClipBounds(this.mTmpRect);
            if (isLayoutRtlSupport()) {
                this.mTmpRect.left = Math.max(this.mTmpRect.left, this.mSlideableView.getRight());
            } else {
                this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
            }
            canvas.clipRect(this.mTmpRect);
        }
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas.restoreToCount(save);
        return result;
    }

    void invalidateChildRegion(View v) {
        IMPL.invalidateChildRegion(this, v);
    }

    boolean smoothSlideTo(float slideOffset, int velocity) {
        if (!this.mCanSlide) {
            return false;
        }
        int x;
        SlidingPaneLayout$LayoutParams lp = (SlidingPaneLayout$LayoutParams) this.mSlideableView.getLayoutParams();
        if (isLayoutRtlSupport()) {
            x = (int) (((float) getWidth()) - ((((float) (getPaddingRight() + lp.rightMargin)) + (((float) this.mSlideRange) * slideOffset)) + ((float) this.mSlideableView.getWidth())));
        } else {
            x = (int) (((float) (getPaddingLeft() + lp.leftMargin)) + (((float) this.mSlideRange) * slideOffset));
        }
        if (!this.mDragHelper.smoothSlideViewTo(this.mSlideableView, x, this.mSlideableView.getTop())) {
            return false;
        }
        setAllChildrenVisible();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public void computeScroll() {
        if (this.mDragHelper.continueSettling(true)) {
            if (this.mCanSlide) {
                ViewCompat.postInvalidateOnAnimation(this);
            } else {
                this.mDragHelper.abort();
            }
        }
    }

    @Deprecated
    public void setShadowDrawable(Drawable d) {
        setShadowDrawableLeft(d);
    }

    public void setShadowDrawableLeft(@Nullable Drawable d) {
        this.mShadowDrawableLeft = d;
    }

    public void setShadowDrawableRight(@Nullable Drawable d) {
        this.mShadowDrawableRight = d;
    }

    @Deprecated
    public void setShadowResource(@DrawableRes int resId) {
        setShadowDrawable(getResources().getDrawable(resId));
    }

    public void setShadowResourceLeft(int resId) {
        setShadowDrawableLeft(ContextCompat.getDrawable(getContext(), resId));
    }

    public void setShadowResourceRight(int resId) {
        setShadowDrawableRight(ContextCompat.getDrawable(getContext(), resId));
    }

    public void draw(Canvas c) {
        Drawable shadowDrawable;
        super.draw(c);
        if (isLayoutRtlSupport()) {
            shadowDrawable = this.mShadowDrawableRight;
        } else {
            shadowDrawable = this.mShadowDrawableLeft;
        }
        View shadowView = getChildCount() > 1 ? getChildAt(1) : null;
        if (shadowView != null) {
            if (shadowDrawable != null) {
                int left;
                int right;
                int top = shadowView.getTop();
                int bottom = shadowView.getBottom();
                int shadowWidth = shadowDrawable.getIntrinsicWidth();
                if (isLayoutRtlSupport()) {
                    left = shadowView.getRight();
                    right = left + shadowWidth;
                } else {
                    right = shadowView.getLeft();
                    left = right - shadowWidth;
                }
                shadowDrawable.setBounds(left, top, right, bottom);
                shadowDrawable.draw(c);
            }
        }
    }

    private void parallaxOtherViews(float slideOffset) {
        boolean dimViews;
        int childCount;
        int i;
        View v;
        boolean isLayoutRtl = isLayoutRtlSupport();
        SlidingPaneLayout$LayoutParams slideLp = (SlidingPaneLayout$LayoutParams) this.mSlideableView.getLayoutParams();
        if (slideLp.dimWhenOffset) {
            if ((isLayoutRtl ? slideLp.rightMargin : slideLp.leftMargin) <= 0) {
                dimViews = true;
                childCount = getChildCount();
                for (i = 0; i < childCount; i++) {
                    v = getChildAt(i);
                    if (v == this.mSlideableView) {
                        int oldOffset = (int) ((1.0f - this.mParallaxOffset) * ((float) this.mParallaxBy));
                        this.mParallaxOffset = slideOffset;
                        int dx = oldOffset - ((int) ((1.0f - slideOffset) * ((float) this.mParallaxBy)));
                        v.offsetLeftAndRight(isLayoutRtl ? -dx : dx);
                        if (!dimViews) {
                            dimChildView(v, isLayoutRtl ? this.mParallaxOffset - 1.0f : 1.0f - this.mParallaxOffset, this.mCoveredFadeColor);
                        }
                    }
                }
            }
        }
        dimViews = false;
        childCount = getChildCount();
        for (i = 0; i < childCount; i++) {
            v = getChildAt(i);
            if (v == this.mSlideableView) {
                int oldOffset2 = (int) ((1.0f - this.mParallaxOffset) * ((float) this.mParallaxBy));
                this.mParallaxOffset = slideOffset;
                int dx2 = oldOffset2 - ((int) ((1.0f - slideOffset) * ((float) this.mParallaxBy)));
                if (isLayoutRtl) {
                }
                v.offsetLeftAndRight(isLayoutRtl ? -dx2 : dx2);
                if (!dimViews) {
                    if (isLayoutRtl) {
                    }
                    dimChildView(v, isLayoutRtl ? this.mParallaxOffset - 1.0f : 1.0f - this.mParallaxOffset, this.mCoveredFadeColor);
                }
            }
        }
    }

    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        View view = v;
        boolean z = true;
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int i = group.getChildCount() - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()) {
                    if (canScroll(child, true, dx, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
                        return true;
                    }
                }
            }
        }
        if (checkV) {
            if (view.canScrollHorizontally(isLayoutRtlSupport() ? dx : -dx)) {
                return z;
            }
        }
        scrollX = dx;
        z = false;
        return z;
    }

    boolean isDimmed(View child) {
        boolean z = false;
        if (child == null) {
            return false;
        }
        SlidingPaneLayout$LayoutParams lp = (SlidingPaneLayout$LayoutParams) child.getLayoutParams();
        if (this.mCanSlide && lp.dimWhenOffset && this.mSlideOffset > 0.0f) {
            z = true;
        }
        return z;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new SlidingPaneLayout$LayoutParams();
    }

    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams ? new SlidingPaneLayout$LayoutParams((MarginLayoutParams) p) : new SlidingPaneLayout$LayoutParams(p);
    }

    protected boolean checkLayoutParams(LayoutParams p) {
        return (p instanceof SlidingPaneLayout$LayoutParams) && super.checkLayoutParams(p);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new SlidingPaneLayout$LayoutParams(getContext(), attrs);
    }

    protected Parcelable onSaveInstanceState() {
        SlidingPaneLayout$SavedState ss = new SlidingPaneLayout$SavedState(super.onSaveInstanceState());
        ss.isOpen = isSlideable() ? isOpen() : this.mPreservedOpenState;
        return ss;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SlidingPaneLayout$SavedState) {
            SlidingPaneLayout$SavedState ss = (SlidingPaneLayout$SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            if (ss.isOpen) {
                openPane();
            } else {
                closePane();
            }
            this.mPreservedOpenState = ss.isOpen;
            return;
        }
        super.onRestoreInstanceState(state);
    }

    boolean isLayoutRtlSupport() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }
}
