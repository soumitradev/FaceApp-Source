package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowInsets;
import com.badlogic.gdx.net.HttpStatus;
import com.google.common.primitives.Ints;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class DrawerLayout extends ViewGroup {
    private static final boolean ALLOW_EDGE_LOCK = false;
    static final boolean CAN_HIDE_DESCENDANTS = (VERSION.SDK_INT >= 19);
    private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
    private static final int DEFAULT_SCRIM_COLOR = -1728053248;
    private static final int DRAWER_ELEVATION = 10;
    static final int[] LAYOUT_ATTRS = new int[]{16842931};
    public static final int LOCK_MODE_LOCKED_CLOSED = 1;
    public static final int LOCK_MODE_LOCKED_OPEN = 2;
    public static final int LOCK_MODE_UNDEFINED = 3;
    public static final int LOCK_MODE_UNLOCKED = 0;
    private static final int MIN_DRAWER_MARGIN = 64;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int PEEK_DELAY = 160;
    private static final boolean SET_DRAWER_SHADOW_FROM_ELEVATION;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "DrawerLayout";
    private static final int[] THEME_ATTRS = new int[]{16843828};
    private static final float TOUCH_SLOP_SENSITIVITY = 1.0f;
    private final DrawerLayout$ChildAccessibilityDelegate mChildAccessibilityDelegate;
    private boolean mChildrenCanceledTouch;
    private boolean mDisallowInterceptRequested;
    private boolean mDrawStatusBarBackground;
    private float mDrawerElevation;
    private int mDrawerState;
    private boolean mFirstLayout;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private Object mLastInsets;
    private final DrawerLayout$ViewDragCallback mLeftCallback;
    private final ViewDragHelper mLeftDragger;
    @Nullable
    private DrawerListener mListener;
    private List<DrawerListener> mListeners;
    private int mLockModeEnd;
    private int mLockModeLeft;
    private int mLockModeRight;
    private int mLockModeStart;
    private int mMinDrawerMargin;
    private final ArrayList<View> mNonDrawerViews;
    private final DrawerLayout$ViewDragCallback mRightCallback;
    private final ViewDragHelper mRightDragger;
    private int mScrimColor;
    private float mScrimOpacity;
    private Paint mScrimPaint;
    private Drawable mShadowEnd;
    private Drawable mShadowLeft;
    private Drawable mShadowLeftResolved;
    private Drawable mShadowRight;
    private Drawable mShadowRightResolved;
    private Drawable mShadowStart;
    private Drawable mStatusBarBackground;
    private CharSequence mTitleLeft;
    private CharSequence mTitleRight;

    public interface DrawerListener {
        void onDrawerClosed(@NonNull View view);

        void onDrawerOpened(@NonNull View view);

        void onDrawerSlide(@NonNull View view, float f);

        void onDrawerStateChanged(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface EdgeGravity {
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface LockMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface State {
    }

    static {
        boolean z = true;
        if (VERSION.SDK_INT < 21) {
            z = false;
        }
        SET_DRAWER_SHADOW_FROM_ELEVATION = z;
    }

    public DrawerLayout(@NonNull Context context) {
        this(context, null);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mChildAccessibilityDelegate = new DrawerLayout$ChildAccessibilityDelegate();
        this.mScrimColor = DEFAULT_SCRIM_COLOR;
        this.mScrimPaint = new Paint();
        this.mFirstLayout = true;
        this.mLockModeLeft = 3;
        this.mLockModeRight = 3;
        this.mLockModeStart = 3;
        this.mLockModeEnd = 3;
        this.mShadowStart = null;
        this.mShadowEnd = null;
        this.mShadowLeft = null;
        this.mShadowRight = null;
        setDescendantFocusability(262144);
        float density = getResources().getDisplayMetrics().density;
        this.mMinDrawerMargin = (int) ((64.0f * density) + 0.5f);
        float minVel = 400.0f * density;
        this.mLeftCallback = new DrawerLayout$ViewDragCallback(this, 3);
        this.mRightCallback = new DrawerLayout$ViewDragCallback(this, 5);
        this.mLeftDragger = ViewDragHelper.create(this, 1.0f, this.mLeftCallback);
        this.mLeftDragger.setEdgeTrackingEnabled(1);
        this.mLeftDragger.setMinVelocity(minVel);
        this.mLeftCallback.setDragger(this.mLeftDragger);
        this.mRightDragger = ViewDragHelper.create(this, 1.0f, this.mRightCallback);
        this.mRightDragger.setEdgeTrackingEnabled(2);
        this.mRightDragger.setMinVelocity(minVel);
        this.mRightCallback.setDragger(this.mRightDragger);
        setFocusableInTouchMode(true);
        ViewCompat.setImportantForAccessibility(this, 1);
        ViewCompat.setAccessibilityDelegate(this, new DrawerLayout$AccessibilityDelegate(this));
        setMotionEventSplittingEnabled(false);
        if (ViewCompat.getFitsSystemWindows(this)) {
            if (VERSION.SDK_INT >= 21) {
                setOnApplyWindowInsetsListener(new DrawerLayout$1(this));
                setSystemUiVisibility(1280);
                TypedArray a = context.obtainStyledAttributes(THEME_ATTRS);
                try {
                    this.mStatusBarBackground = a.getDrawable(0);
                } finally {
                    a.recycle();
                }
            } else {
                this.mStatusBarBackground = null;
            }
        }
        this.mDrawerElevation = 10.0f * density;
        this.mNonDrawerViews = new ArrayList();
    }

    public void setDrawerElevation(float elevation) {
        this.mDrawerElevation = elevation;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (isDrawerView(child)) {
                ViewCompat.setElevation(child, this.mDrawerElevation);
            }
        }
    }

    public float getDrawerElevation() {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return this.mDrawerElevation;
        }
        return 0.0f;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setChildInsets(Object insets, boolean draw) {
        this.mLastInsets = insets;
        this.mDrawStatusBarBackground = draw;
        boolean z = !draw && getBackground() == null;
        setWillNotDraw(z);
        requestLayout();
    }

    public void setDrawerShadow(Drawable shadowDrawable, int gravity) {
        if (!SET_DRAWER_SHADOW_FROM_ELEVATION) {
            if ((gravity & 8388611) == 8388611) {
                this.mShadowStart = shadowDrawable;
            } else if ((gravity & GravityCompat.END) == GravityCompat.END) {
                this.mShadowEnd = shadowDrawable;
            } else if ((gravity & 3) == 3) {
                this.mShadowLeft = shadowDrawable;
            } else if ((gravity & 5) == 5) {
                this.mShadowRight = shadowDrawable;
            } else {
                return;
            }
            resolveShadowDrawables();
            invalidate();
        }
    }

    public void setDrawerShadow(@DrawableRes int resId, int gravity) {
        setDrawerShadow(ContextCompat.getDrawable(getContext(), resId), gravity);
    }

    public void setScrimColor(@ColorInt int color) {
        this.mScrimColor = color;
        invalidate();
    }

    @Deprecated
    public void setDrawerListener(DrawerListener listener) {
        if (this.mListener != null) {
            removeDrawerListener(this.mListener);
        }
        if (listener != null) {
            addDrawerListener(listener);
        }
        this.mListener = listener;
    }

    public void addDrawerListener(@NonNull DrawerListener listener) {
        if (listener != null) {
            if (this.mListeners == null) {
                this.mListeners = new ArrayList();
            }
            this.mListeners.add(listener);
        }
    }

    public void removeDrawerListener(@NonNull DrawerListener listener) {
        if (listener != null && this.mListeners != null) {
            this.mListeners.remove(listener);
        }
    }

    public void setDrawerLockMode(int lockMode) {
        setDrawerLockMode(lockMode, 3);
        setDrawerLockMode(lockMode, 5);
    }

    public void setDrawerLockMode(int lockMode, int edgeGravity) {
        int absGravity = GravityCompat.getAbsoluteGravity(edgeGravity, ViewCompat.getLayoutDirection(this));
        if (edgeGravity == 3) {
            this.mLockModeLeft = lockMode;
        } else if (edgeGravity == 5) {
            this.mLockModeRight = lockMode;
        } else if (edgeGravity == 8388611) {
            this.mLockModeStart = lockMode;
        } else if (edgeGravity == GravityCompat.END) {
            this.mLockModeEnd = lockMode;
        }
        if (lockMode != 0) {
            (absGravity == 3 ? this.mLeftDragger : this.mRightDragger).cancel();
        }
        View toClose;
        switch (lockMode) {
            case 1:
                toClose = findDrawerWithGravity(absGravity);
                if (toClose != null) {
                    closeDrawer(toClose);
                    return;
                }
                return;
            case 2:
                toClose = findDrawerWithGravity(absGravity);
                if (toClose != null) {
                    openDrawer(toClose);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setDrawerLockMode(int lockMode, @NonNull View drawerView) {
        if (isDrawerView(drawerView)) {
            setDrawerLockMode(lockMode, ((DrawerLayout$LayoutParams) drawerView.getLayoutParams()).gravity);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(drawerView);
        stringBuilder.append(" is not a ");
        stringBuilder.append("drawer with appropriate layout_gravity");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int getDrawerLockMode(int edgeGravity) {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int endLockMode;
        if (edgeGravity != 3) {
            if (edgeGravity != 5) {
                if (edgeGravity != 8388611) {
                    if (edgeGravity == GravityCompat.END) {
                        if (this.mLockModeEnd != 3) {
                            return this.mLockModeEnd;
                        }
                        endLockMode = layoutDirection == 0 ? this.mLockModeRight : this.mLockModeLeft;
                        if (endLockMode != 3) {
                            return endLockMode;
                        }
                    }
                } else if (this.mLockModeStart != 3) {
                    return this.mLockModeStart;
                } else {
                    endLockMode = layoutDirection == 0 ? this.mLockModeLeft : this.mLockModeRight;
                    if (endLockMode != 3) {
                        return endLockMode;
                    }
                }
            } else if (this.mLockModeRight != 3) {
                return this.mLockModeRight;
            } else {
                endLockMode = layoutDirection == 0 ? this.mLockModeEnd : this.mLockModeStart;
                if (endLockMode != 3) {
                    return endLockMode;
                }
            }
        } else if (this.mLockModeLeft != 3) {
            return this.mLockModeLeft;
        } else {
            endLockMode = layoutDirection == 0 ? this.mLockModeStart : this.mLockModeEnd;
            if (endLockMode != 3) {
                return endLockMode;
            }
        }
        return 0;
    }

    public int getDrawerLockMode(@NonNull View drawerView) {
        if (isDrawerView(drawerView)) {
            return getDrawerLockMode(((DrawerLayout$LayoutParams) drawerView.getLayoutParams()).gravity);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(drawerView);
        stringBuilder.append(" is not a drawer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setDrawerTitle(int edgeGravity, @Nullable CharSequence title) {
        int absGravity = GravityCompat.getAbsoluteGravity(edgeGravity, ViewCompat.getLayoutDirection(this));
        if (absGravity == 3) {
            this.mTitleLeft = title;
        } else if (absGravity == 5) {
            this.mTitleRight = title;
        }
    }

    @Nullable
    public CharSequence getDrawerTitle(int edgeGravity) {
        int absGravity = GravityCompat.getAbsoluteGravity(edgeGravity, ViewCompat.getLayoutDirection(this));
        if (absGravity == 3) {
            return this.mTitleLeft;
        }
        if (absGravity == 5) {
            return this.mTitleRight;
        }
        return null;
    }

    void updateDrawerState(int forGravity, int activeState, View activeDrawer) {
        int state;
        DrawerLayout$LayoutParams lp;
        int i;
        int leftState = this.mLeftDragger.getViewDragState();
        int rightState = this.mRightDragger.getViewDragState();
        if (leftState != 1) {
            if (rightState != 1) {
                if (leftState != 2) {
                    if (rightState != 2) {
                        state = 0;
                        if (activeDrawer != null && activeState == 0) {
                            lp = (DrawerLayout$LayoutParams) activeDrawer.getLayoutParams();
                            if (lp.onScreen != 0.0f) {
                                dispatchOnDrawerClosed(activeDrawer);
                            } else if (lp.onScreen == 1.0f) {
                                dispatchOnDrawerOpened(activeDrawer);
                            }
                        }
                        if (state == this.mDrawerState) {
                            this.mDrawerState = state;
                            if (this.mListeners == null) {
                                for (i = this.mListeners.size() - 1; i >= 0; i--) {
                                    ((DrawerListener) this.mListeners.get(i)).onDrawerStateChanged(state);
                                }
                            }
                        }
                    }
                }
                state = 2;
                lp = (DrawerLayout$LayoutParams) activeDrawer.getLayoutParams();
                if (lp.onScreen != 0.0f) {
                    dispatchOnDrawerClosed(activeDrawer);
                } else if (lp.onScreen == 1.0f) {
                    dispatchOnDrawerOpened(activeDrawer);
                }
                if (state == this.mDrawerState) {
                    this.mDrawerState = state;
                    if (this.mListeners == null) {
                        for (i = this.mListeners.size() - 1; i >= 0; i--) {
                            ((DrawerListener) this.mListeners.get(i)).onDrawerStateChanged(state);
                        }
                    }
                }
            }
        }
        state = 1;
        lp = (DrawerLayout$LayoutParams) activeDrawer.getLayoutParams();
        if (lp.onScreen != 0.0f) {
            dispatchOnDrawerClosed(activeDrawer);
        } else if (lp.onScreen == 1.0f) {
            dispatchOnDrawerOpened(activeDrawer);
        }
        if (state == this.mDrawerState) {
            this.mDrawerState = state;
            if (this.mListeners == null) {
                for (i = this.mListeners.size() - 1; i >= 0; i--) {
                    ((DrawerListener) this.mListeners.get(i)).onDrawerStateChanged(state);
                }
            }
        }
    }

    void dispatchOnDrawerClosed(View drawerView) {
        DrawerLayout$LayoutParams lp = (DrawerLayout$LayoutParams) drawerView.getLayoutParams();
        if ((lp.openState & 1) == 1) {
            lp.openState = 0;
            if (this.mListeners != null) {
                for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                    ((DrawerListener) this.mListeners.get(i)).onDrawerClosed(drawerView);
                }
            }
            updateChildrenImportantForAccessibility(drawerView, false);
            if (hasWindowFocus()) {
                View rootView = getRootView();
                if (rootView != null) {
                    rootView.sendAccessibilityEvent(32);
                }
            }
        }
    }

    void dispatchOnDrawerOpened(View drawerView) {
        DrawerLayout$LayoutParams lp = (DrawerLayout$LayoutParams) drawerView.getLayoutParams();
        if ((lp.openState & 1) == 0) {
            lp.openState = 1;
            if (this.mListeners != null) {
                for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                    ((DrawerListener) this.mListeners.get(i)).onDrawerOpened(drawerView);
                }
            }
            updateChildrenImportantForAccessibility(drawerView, true);
            if (hasWindowFocus()) {
                sendAccessibilityEvent(32);
            }
        }
    }

    private void updateChildrenImportantForAccessibility(View drawerView, boolean isDrawerOpen) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if ((isDrawerOpen || isDrawerView(child)) && !(isDrawerOpen && child == drawerView)) {
                ViewCompat.setImportantForAccessibility(child, 4);
            } else {
                ViewCompat.setImportantForAccessibility(child, 1);
            }
        }
    }

    void dispatchOnDrawerSlide(View drawerView, float slideOffset) {
        if (this.mListeners != null) {
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                ((DrawerListener) this.mListeners.get(i)).onDrawerSlide(drawerView, slideOffset);
            }
        }
    }

    void setDrawerViewOffset(View drawerView, float slideOffset) {
        DrawerLayout$LayoutParams lp = (DrawerLayout$LayoutParams) drawerView.getLayoutParams();
        if (slideOffset != lp.onScreen) {
            lp.onScreen = slideOffset;
            dispatchOnDrawerSlide(drawerView, slideOffset);
        }
    }

    float getDrawerViewOffset(View drawerView) {
        return ((DrawerLayout$LayoutParams) drawerView.getLayoutParams()).onScreen;
    }

    int getDrawerViewAbsoluteGravity(View drawerView) {
        return GravityCompat.getAbsoluteGravity(((DrawerLayout$LayoutParams) drawerView.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(this));
    }

    boolean checkDrawerViewAbsoluteGravity(View drawerView, int checkFor) {
        return (getDrawerViewAbsoluteGravity(drawerView) & checkFor) == checkFor;
    }

    View findOpenDrawer() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if ((((DrawerLayout$LayoutParams) child.getLayoutParams()).openState & 1) == 1) {
                return child;
            }
        }
        return null;
    }

    void moveDrawerToOffset(View drawerView, float slideOffset) {
        int i;
        float oldOffset = getDrawerViewOffset(drawerView);
        int width = drawerView.getWidth();
        int dx = ((int) (((float) width) * slideOffset)) - ((int) (((float) width) * oldOffset));
        if (checkDrawerViewAbsoluteGravity(drawerView, 3)) {
            i = dx;
        } else {
            i = -dx;
        }
        drawerView.offsetLeftAndRight(i);
        setDrawerViewOffset(drawerView, slideOffset);
    }

    View findDrawerWithGravity(int gravity) {
        int absHorizGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this)) & 7;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if ((getDrawerViewAbsoluteGravity(child) & 7) == absHorizGravity) {
                return child;
            }
        }
        return null;
    }

    static String gravityToString(int gravity) {
        if ((gravity & 3) == 3) {
            return "LEFT";
        }
        if ((gravity & 5) == 5) {
            return "RIGHT";
        }
        return Integer.toHexString(gravity);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int i2;
        int i3;
        boolean z;
        DrawerLayout drawerLayout = this;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (!(widthMode == Ints.MAX_POWER_OF_TWO && heightMode == Ints.MAX_POWER_OF_TWO)) {
            if (isInEditMode()) {
                if (widthMode == Integer.MIN_VALUE) {
                    widthMode = Ints.MAX_POWER_OF_TWO;
                } else if (widthMode == 0) {
                    widthMode = Ints.MAX_POWER_OF_TWO;
                    widthSize = HttpStatus.SC_MULTIPLE_CHOICES;
                }
                if (heightMode == Integer.MIN_VALUE) {
                    heightMode = Ints.MAX_POWER_OF_TWO;
                } else if (heightMode == 0) {
                    heightMode = Ints.MAX_POWER_OF_TWO;
                    heightSize = HttpStatus.SC_MULTIPLE_CHOICES;
                }
            } else {
                i = heightMeasureSpec;
                heightMode = widthMeasureSpec;
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
        }
        setMeasuredDimension(widthSize, heightSize);
        boolean applyInsets = drawerLayout.mLastInsets != null && ViewCompat.getFitsSystemWindows(this);
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int childCount = getChildCount();
        boolean hasDrawerOnRightEdge = false;
        boolean hasDrawerOnLeftEdge = false;
        int i4 = 0;
        while (i4 < childCount) {
            View child = drawerLayout.getChildAt(i4);
            if (child.getVisibility() == 8) {
                i2 = widthMode;
                i3 = heightMode;
                z = applyInsets;
            } else {
                boolean isLeftEdgeDrawer;
                StringBuilder stringBuilder;
                DrawerLayout$LayoutParams lp = (DrawerLayout$LayoutParams) child.getLayoutParams();
                if (applyInsets) {
                    int cgrav = GravityCompat.getAbsoluteGravity(lp.gravity, layoutDirection);
                    if (!ViewCompat.getFitsSystemWindows(child)) {
                        i2 = widthMode;
                        i3 = heightMode;
                        z = applyInsets;
                        if (VERSION.SDK_INT >= 21) {
                            widthMode = (WindowInsets) drawerLayout.mLastInsets;
                            if (cgrav == 3) {
                                widthMode = widthMode.replaceSystemWindowInsets(widthMode.getSystemWindowInsetLeft(), widthMode.getSystemWindowInsetTop(), 0, widthMode.getSystemWindowInsetBottom());
                            } else if (cgrav == 5) {
                                widthMode = widthMode.replaceSystemWindowInsets(0, widthMode.getSystemWindowInsetTop(), widthMode.getSystemWindowInsetRight(), widthMode.getSystemWindowInsetBottom());
                            }
                            lp.leftMargin = widthMode.getSystemWindowInsetLeft();
                            lp.topMargin = widthMode.getSystemWindowInsetTop();
                            lp.rightMargin = widthMode.getSystemWindowInsetRight();
                            lp.bottomMargin = widthMode.getSystemWindowInsetBottom();
                            if (!drawerLayout.isContentView(child)) {
                                child.measure(MeasureSpec.makeMeasureSpec((widthSize - lp.leftMargin) - lp.rightMargin, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec((heightSize - lp.topMargin) - lp.bottomMargin, Ints.MAX_POWER_OF_TWO));
                            } else if (drawerLayout.isDrawerView(child)) {
                                if (SET_DRAWER_SHADOW_FROM_ELEVATION && ViewCompat.getElevation(child) != drawerLayout.mDrawerElevation) {
                                    ViewCompat.setElevation(child, drawerLayout.mDrawerElevation);
                                }
                                widthMode = drawerLayout.getDrawerViewAbsoluteGravity(child) & 7;
                                isLeftEdgeDrawer = widthMode == 3;
                                if ((isLeftEdgeDrawer || !hasDrawerOnLeftEdge) && (isLeftEdgeDrawer || !hasDrawerOnRightEdge)) {
                                    if (isLeftEdgeDrawer) {
                                        hasDrawerOnLeftEdge = true;
                                    } else {
                                        hasDrawerOnRightEdge = true;
                                    }
                                    child.measure(getChildMeasureSpec(widthMeasureSpec, (drawerLayout.mMinDrawerMargin + lp.leftMargin) + lp.rightMargin, lp.width), getChildMeasureSpec(heightMeasureSpec, lp.topMargin + lp.bottomMargin, lp.height));
                                    i4++;
                                    widthMode = i2;
                                    heightMode = i3;
                                    applyInsets = z;
                                    drawerLayout = this;
                                } else {
                                    StringBuilder stringBuilder2 = new StringBuilder();
                                    stringBuilder2.append("Child drawer has absolute gravity ");
                                    stringBuilder2.append(gravityToString(widthMode));
                                    stringBuilder2.append(" but this ");
                                    stringBuilder2.append(TAG);
                                    stringBuilder2.append(" already has a ");
                                    stringBuilder2.append("drawer view along that edge");
                                    throw new IllegalStateException(stringBuilder2.toString());
                                }
                            } else {
                                heightMode = widthMeasureSpec;
                                i = heightMeasureSpec;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("Child ");
                                stringBuilder.append(child);
                                stringBuilder.append(" at index ");
                                stringBuilder.append(i4);
                                stringBuilder.append(" does not have a valid layout_gravity - must be Gravity.LEFT, ");
                                stringBuilder.append("Gravity.RIGHT or Gravity.NO_GRAVITY");
                                throw new IllegalStateException(stringBuilder.toString());
                            }
                        }
                    } else if (VERSION.SDK_INT >= 21) {
                        WindowInsets wi = drawerLayout.mLastInsets;
                        if (cgrav == 3) {
                            i2 = widthMode;
                            i3 = heightMode;
                            z = applyInsets;
                            wi = wi.replaceSystemWindowInsets(wi.getSystemWindowInsetLeft(), wi.getSystemWindowInsetTop(), false, wi.getSystemWindowInsetBottom());
                        } else {
                            i2 = widthMode;
                            i3 = heightMode;
                            z = applyInsets;
                            if (cgrav == 5) {
                                wi = wi.replaceSystemWindowInsets(0, wi.getSystemWindowInsetTop(), wi.getSystemWindowInsetRight(), wi.getSystemWindowInsetBottom());
                            }
                        }
                        child.dispatchApplyWindowInsets(wi);
                    }
                    if (!drawerLayout.isContentView(child)) {
                        child.measure(MeasureSpec.makeMeasureSpec((widthSize - lp.leftMargin) - lp.rightMargin, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec((heightSize - lp.topMargin) - lp.bottomMargin, Ints.MAX_POWER_OF_TWO));
                    } else if (drawerLayout.isDrawerView(child)) {
                        heightMode = widthMeasureSpec;
                        i = heightMeasureSpec;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Child ");
                        stringBuilder.append(child);
                        stringBuilder.append(" at index ");
                        stringBuilder.append(i4);
                        stringBuilder.append(" does not have a valid layout_gravity - must be Gravity.LEFT, ");
                        stringBuilder.append("Gravity.RIGHT or Gravity.NO_GRAVITY");
                        throw new IllegalStateException(stringBuilder.toString());
                    } else {
                        ViewCompat.setElevation(child, drawerLayout.mDrawerElevation);
                        widthMode = drawerLayout.getDrawerViewAbsoluteGravity(child) & 7;
                        if (widthMode == 3) {
                        }
                        if (isLeftEdgeDrawer) {
                        }
                        if (isLeftEdgeDrawer) {
                            hasDrawerOnRightEdge = true;
                        } else {
                            hasDrawerOnLeftEdge = true;
                        }
                        child.measure(getChildMeasureSpec(widthMeasureSpec, (drawerLayout.mMinDrawerMargin + lp.leftMargin) + lp.rightMargin, lp.width), getChildMeasureSpec(heightMeasureSpec, lp.topMargin + lp.bottomMargin, lp.height));
                        i4++;
                        widthMode = i2;
                        heightMode = i3;
                        applyInsets = z;
                        drawerLayout = this;
                    }
                }
                i2 = widthMode;
                i3 = heightMode;
                z = applyInsets;
                if (!drawerLayout.isContentView(child)) {
                    child.measure(MeasureSpec.makeMeasureSpec((widthSize - lp.leftMargin) - lp.rightMargin, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec((heightSize - lp.topMargin) - lp.bottomMargin, Ints.MAX_POWER_OF_TWO));
                } else if (drawerLayout.isDrawerView(child)) {
                    ViewCompat.setElevation(child, drawerLayout.mDrawerElevation);
                    widthMode = drawerLayout.getDrawerViewAbsoluteGravity(child) & 7;
                    if (widthMode == 3) {
                    }
                    if (isLeftEdgeDrawer) {
                    }
                    if (isLeftEdgeDrawer) {
                        hasDrawerOnLeftEdge = true;
                    } else {
                        hasDrawerOnRightEdge = true;
                    }
                    child.measure(getChildMeasureSpec(widthMeasureSpec, (drawerLayout.mMinDrawerMargin + lp.leftMargin) + lp.rightMargin, lp.width), getChildMeasureSpec(heightMeasureSpec, lp.topMargin + lp.bottomMargin, lp.height));
                    i4++;
                    widthMode = i2;
                    heightMode = i3;
                    applyInsets = z;
                    drawerLayout = this;
                } else {
                    heightMode = widthMeasureSpec;
                    i = heightMeasureSpec;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Child ");
                    stringBuilder.append(child);
                    stringBuilder.append(" at index ");
                    stringBuilder.append(i4);
                    stringBuilder.append(" does not have a valid layout_gravity - must be Gravity.LEFT, ");
                    stringBuilder.append("Gravity.RIGHT or Gravity.NO_GRAVITY");
                    throw new IllegalStateException(stringBuilder.toString());
                }
            }
            heightMode = widthMeasureSpec;
            i = heightMeasureSpec;
            i4++;
            widthMode = i2;
            heightMode = i3;
            applyInsets = z;
            drawerLayout = this;
        }
        i = heightMeasureSpec;
        i2 = widthMode;
        i3 = heightMode;
        z = applyInsets;
        heightMode = widthMeasureSpec;
    }

    private void resolveShadowDrawables() {
        if (!SET_DRAWER_SHADOW_FROM_ELEVATION) {
            this.mShadowLeftResolved = resolveLeftShadow();
            this.mShadowRightResolved = resolveRightShadow();
        }
    }

    private Drawable resolveLeftShadow() {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (layoutDirection == 0) {
            if (this.mShadowStart != null) {
                mirror(this.mShadowStart, layoutDirection);
                return this.mShadowStart;
            }
        } else if (this.mShadowEnd != null) {
            mirror(this.mShadowEnd, layoutDirection);
            return this.mShadowEnd;
        }
        return this.mShadowLeft;
    }

    private Drawable resolveRightShadow() {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (layoutDirection == 0) {
            if (this.mShadowEnd != null) {
                mirror(this.mShadowEnd, layoutDirection);
                return this.mShadowEnd;
            }
        } else if (this.mShadowStart != null) {
            mirror(this.mShadowStart, layoutDirection);
            return this.mShadowStart;
        }
        return this.mShadowRight;
    }

    private boolean mirror(Drawable drawable, int layoutDirection) {
        if (drawable != null) {
            if (DrawableCompat.isAutoMirrored(drawable)) {
                DrawableCompat.setLayoutDirection(drawable, layoutDirection);
                return true;
            }
        }
        return false;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount;
        this.mInLayout = true;
        int width = r - l;
        int childCount2 = getChildCount();
        int i = 0;
        while (i < childCount2) {
            int width2;
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                DrawerLayout$LayoutParams lp = (DrawerLayout$LayoutParams) child.getLayoutParams();
                if (isContentView(child)) {
                    child.layout(lp.leftMargin, lp.topMargin, lp.leftMargin + child.getMeasuredWidth(), lp.topMargin + child.getMeasuredHeight());
                } else {
                    int childLeft;
                    float newOffset;
                    int height;
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    if (checkDrawerViewAbsoluteGravity(child, 3)) {
                        childLeft = (-childWidth) + ((int) (((float) childWidth) * lp.onScreen));
                        newOffset = ((float) (childWidth + childLeft)) / ((float) childWidth);
                    } else {
                        childLeft = width - ((int) (((float) childWidth) * lp.onScreen));
                        newOffset = ((float) (width - childLeft)) / ((float) childWidth);
                    }
                    boolean changeOffset = newOffset != lp.onScreen;
                    int vgrav = lp.gravity & 112;
                    if (vgrav == 16) {
                        width2 = width;
                        childCount = childCount2;
                        height = b - t;
                        int childTop = (height - childHeight) / 2;
                        if (childTop < lp.topMargin) {
                            childTop = lp.topMargin;
                        } else if (childTop + childHeight > height - lp.bottomMargin) {
                            childTop = (height - lp.bottomMargin) - childHeight;
                        }
                        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                    } else if (vgrav != 80) {
                        width2 = width;
                        child.layout(childLeft, lp.topMargin, childLeft + childWidth, lp.topMargin + childHeight);
                        childCount = childCount2;
                    } else {
                        width2 = width;
                        height = b - t;
                        childCount = childCount2;
                        child.layout(childLeft, (height - lp.bottomMargin) - child.getMeasuredHeight(), childLeft + childWidth, height - lp.bottomMargin);
                    }
                    if (changeOffset) {
                        setDrawerViewOffset(child, newOffset);
                    }
                    height = lp.onScreen > 0.0f ? 0 : 4;
                    if (child.getVisibility() != height) {
                        child.setVisibility(height);
                    }
                    i++;
                    width = width2;
                    childCount2 = childCount;
                }
            }
            width2 = width;
            childCount = childCount2;
            i++;
            width = width2;
            childCount2 = childCount;
        }
        childCount = childCount2;
        r0.mInLayout = false;
        r0.mFirstLayout = false;
    }

    public void requestLayout() {
        if (!this.mInLayout) {
            super.requestLayout();
        }
    }

    public void computeScroll() {
        int childCount = getChildCount();
        float scrimOpacity = 0.0f;
        for (int i = 0; i < childCount; i++) {
            scrimOpacity = Math.max(scrimOpacity, ((DrawerLayout$LayoutParams) getChildAt(i).getLayoutParams()).onScreen);
        }
        this.mScrimOpacity = scrimOpacity;
        boolean leftDraggerSettling = this.mLeftDragger.continueSettling(true);
        boolean rightDraggerSettling = this.mRightDragger.continueSettling(true);
        if (leftDraggerSettling || rightDraggerSettling) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private static boolean hasOpaqueBackground(View v) {
        Drawable bg = v.getBackground();
        boolean z = false;
        if (bg == null) {
            return false;
        }
        if (bg.getOpacity() == -1) {
            z = true;
        }
        return z;
    }

    public void setStatusBarBackground(@Nullable Drawable bg) {
        this.mStatusBarBackground = bg;
        invalidate();
    }

    @Nullable
    public Drawable getStatusBarBackgroundDrawable() {
        return this.mStatusBarBackground;
    }

    public void setStatusBarBackground(int resId) {
        this.mStatusBarBackground = resId != 0 ? ContextCompat.getDrawable(getContext(), resId) : null;
        invalidate();
    }

    public void setStatusBarBackgroundColor(@ColorInt int color) {
        this.mStatusBarBackground = new ColorDrawable(color);
        invalidate();
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        resolveShadowDrawables();
    }

    public void onDraw(Canvas c) {
        super.onDraw(c);
        if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
            int inset;
            if (VERSION.SDK_INT >= 21) {
                inset = this.mLastInsets != null ? ((WindowInsets) this.mLastInsets).getSystemWindowInsetTop() : 0;
            } else {
                inset = 0;
            }
            if (inset > 0) {
                this.mStatusBarBackground.setBounds(0, 0, getWidth(), inset);
                this.mStatusBarBackground.draw(c);
            }
        }
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        int childCount;
        int clipRight;
        int clipLeft;
        int vright;
        int clipLeft2;
        Canvas canvas2 = canvas;
        View view = child;
        int height = getHeight();
        boolean drawingContent = isContentView(view);
        int clipRight2 = getWidth();
        int restoreCount = canvas.save();
        if (drawingContent) {
            childCount = getChildCount();
            clipRight = clipRight2;
            clipRight2 = 0;
            for (clipLeft = 0; clipLeft < childCount; clipLeft++) {
                View v = getChildAt(clipLeft);
                if (v != view && v.getVisibility() == 0 && hasOpaqueBackground(v) && isDrawerView(v)) {
                    if (v.getHeight() >= height) {
                        if (checkDrawerViewAbsoluteGravity(v, 3)) {
                            vright = v.getRight();
                            if (vright > clipRight2) {
                                clipRight2 = vright;
                            }
                        } else {
                            vright = v.getLeft();
                            if (vright < clipRight) {
                                clipRight = vright;
                            }
                        }
                    }
                }
            }
            canvas2.clipRect(clipRight2, 0, clipRight, getHeight());
            clipLeft2 = clipRight2;
            vright = clipRight;
        } else {
            clipLeft2 = 0;
            vright = clipRight2;
        }
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas2.restoreToCount(restoreCount);
        if (r0.mScrimOpacity > 0.0f && drawingContent) {
            clipRight = (int) (((float) ((r0.mScrimColor & ViewCompat.MEASURED_STATE_MASK) >>> 24)) * r0.mScrimOpacity);
            int color = (clipRight << 24) | (r0.mScrimColor & ViewCompat.MEASURED_SIZE_MASK);
            r0.mScrimPaint.setColor(color);
            float height2 = (float) getHeight();
            canvas2.drawRect((float) clipLeft2, 0.0f, (float) vright, height2, r0.mScrimPaint);
        } else if (r0.mShadowLeftResolved != null && checkDrawerViewAbsoluteGravity(view, 3)) {
            clipLeft = r0.mShadowLeftResolved.getIntrinsicWidth();
            childRight = child.getRight();
            alpha = Math.max(0.0f, Math.min(((float) childRight) / ((float) r0.mLeftDragger.getEdgeSize()), 1.0f));
            r0.mShadowLeftResolved.setBounds(childRight, child.getTop(), childRight + clipLeft, child.getBottom());
            r0.mShadowLeftResolved.setAlpha((int) (255.0f * alpha));
            r0.mShadowLeftResolved.draw(canvas2);
        } else if (r0.mShadowRightResolved != null && checkDrawerViewAbsoluteGravity(view, 5)) {
            clipLeft = r0.mShadowRightResolved.getIntrinsicWidth();
            childRight = child.getLeft();
            childCount = getWidth() - childRight;
            alpha = Math.max(0.0f, Math.min(((float) childCount) / ((float) r0.mRightDragger.getEdgeSize()), 1.0f));
            r0.mShadowRightResolved.setBounds(childRight - clipLeft, child.getTop(), childRight, child.getBottom());
            r0.mShadowRightResolved.setAlpha((int) (255.0f * alpha));
            r0.mShadowRightResolved.draw(canvas2);
        }
        return result;
    }

    boolean isContentView(View child) {
        return ((DrawerLayout$LayoutParams) child.getLayoutParams()).gravity == 0;
    }

    boolean isDrawerView(View child) {
        int absGravity = GravityCompat.getAbsoluteGravity(((DrawerLayout$LayoutParams) child.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(child));
        if ((absGravity & 3) == 0 && (absGravity & 5) == 0) {
            return false;
        }
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean interceptForDrag = this.mLeftDragger.shouldInterceptTouchEvent(ev) | this.mRightDragger.shouldInterceptTouchEvent(ev);
        boolean interceptForTap = false;
        switch (ev.getActionMasked()) {
            case 0:
                float x = ev.getX();
                float y = ev.getY();
                this.mInitialMotionX = x;
                this.mInitialMotionY = y;
                if (this.mScrimOpacity > 0.0f) {
                    View child = this.mLeftDragger.findTopChildUnder((int) x, (int) y);
                    if (child != null && isContentView(child)) {
                        interceptForTap = true;
                    }
                }
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
            case 1:
            case 3:
                closeDrawers(true);
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
            case 2:
                if (this.mLeftDragger.checkTouchSlop(3)) {
                    this.mLeftCallback.removeCallbacks();
                    this.mRightCallback.removeCallbacks();
                    break;
                }
                break;
            default:
                break;
        }
        if (interceptForDrag || interceptForTap || hasPeekingDrawer()) {
            return true;
        }
        if (this.mChildrenCanceledTouch) {
            return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        this.mLeftDragger.processTouchEvent(ev);
        this.mRightDragger.processTouchEvent(ev);
        int action = ev.getAction() & 255;
        boolean z = true;
        if (action != 3) {
            float x;
            float y;
            switch (action) {
                case 0:
                    x = ev.getX();
                    y = ev.getY();
                    this.mInitialMotionX = x;
                    this.mInitialMotionY = y;
                    this.mDisallowInterceptRequested = false;
                    this.mChildrenCanceledTouch = false;
                    break;
                case 1:
                    x = ev.getX();
                    y = ev.getY();
                    boolean peekingOnly = true;
                    View touchedView = this.mLeftDragger.findTopChildUnder((int) x, (int) y);
                    if (touchedView != null && isContentView(touchedView)) {
                        float dx = x - this.mInitialMotionX;
                        float dy = y - this.mInitialMotionY;
                        int slop = this.mLeftDragger.getTouchSlop();
                        if ((dx * dx) + (dy * dy) < ((float) (slop * slop))) {
                            View openDrawer = findOpenDrawer();
                            if (openDrawer != null) {
                                if (getDrawerLockMode(openDrawer) != 2) {
                                    z = false;
                                }
                                peekingOnly = z;
                            }
                        }
                    }
                    closeDrawers(peekingOnly);
                    this.mDisallowInterceptRequested = false;
                    break;
                default:
                    break;
            }
        }
        closeDrawers(true);
        this.mDisallowInterceptRequested = false;
        this.mChildrenCanceledTouch = false;
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        this.mDisallowInterceptRequested = disallowIntercept;
        if (disallowIntercept) {
            closeDrawers(true);
        }
    }

    public void closeDrawers() {
        closeDrawers(false);
    }

    void closeDrawers(boolean peekingOnly) {
        int childCount = getChildCount();
        boolean needsInvalidate = false;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            DrawerLayout$LayoutParams lp = (DrawerLayout$LayoutParams) child.getLayoutParams();
            if (isDrawerView(child)) {
                if (!peekingOnly || lp.isPeeking) {
                    int childWidth = child.getWidth();
                    if (checkDrawerViewAbsoluteGravity(child, 3)) {
                        needsInvalidate |= this.mLeftDragger.smoothSlideViewTo(child, -childWidth, child.getTop());
                    } else {
                        needsInvalidate |= this.mRightDragger.smoothSlideViewTo(child, getWidth(), child.getTop());
                    }
                    lp.isPeeking = false;
                }
            }
        }
        this.mLeftCallback.removeCallbacks();
        this.mRightCallback.removeCallbacks();
        if (needsInvalidate) {
            invalidate();
        }
    }

    public void openDrawer(@NonNull View drawerView) {
        openDrawer(drawerView, true);
    }

    public void openDrawer(@NonNull View drawerView, boolean animate) {
        if (isDrawerView(drawerView)) {
            DrawerLayout$LayoutParams lp = (DrawerLayout$LayoutParams) drawerView.getLayoutParams();
            if (this.mFirstLayout) {
                lp.onScreen = 1.0f;
                lp.openState = 1;
                updateChildrenImportantForAccessibility(drawerView, true);
            } else if (animate) {
                lp.openState |= 2;
                if (checkDrawerViewAbsoluteGravity(drawerView, 3)) {
                    this.mLeftDragger.smoothSlideViewTo(drawerView, 0, drawerView.getTop());
                } else {
                    this.mRightDragger.smoothSlideViewTo(drawerView, getWidth() - drawerView.getWidth(), drawerView.getTop());
                }
            } else {
                moveDrawerToOffset(drawerView, 1.0f);
                updateDrawerState(lp.gravity, 0, drawerView);
                drawerView.setVisibility(0);
            }
            invalidate();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(drawerView);
        stringBuilder.append(" is not a sliding drawer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void openDrawer(int gravity) {
        openDrawer(gravity, true);
    }

    public void openDrawer(int gravity, boolean animate) {
        View drawerView = findDrawerWithGravity(gravity);
        if (drawerView == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No drawer view found with gravity ");
            stringBuilder.append(gravityToString(gravity));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        openDrawer(drawerView, animate);
    }

    public void closeDrawer(@NonNull View drawerView) {
        closeDrawer(drawerView, true);
    }

    public void closeDrawer(@NonNull View drawerView, boolean animate) {
        if (isDrawerView(drawerView)) {
            DrawerLayout$LayoutParams lp = (DrawerLayout$LayoutParams) drawerView.getLayoutParams();
            if (this.mFirstLayout) {
                lp.onScreen = 0.0f;
                lp.openState = 0;
            } else if (animate) {
                lp.openState = 4 | lp.openState;
                if (checkDrawerViewAbsoluteGravity(drawerView, 3)) {
                    this.mLeftDragger.smoothSlideViewTo(drawerView, -drawerView.getWidth(), drawerView.getTop());
                } else {
                    this.mRightDragger.smoothSlideViewTo(drawerView, getWidth(), drawerView.getTop());
                }
            } else {
                moveDrawerToOffset(drawerView, 0.0f);
                updateDrawerState(lp.gravity, 0, drawerView);
                drawerView.setVisibility(4);
            }
            invalidate();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(drawerView);
        stringBuilder.append(" is not a sliding drawer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void closeDrawer(int gravity) {
        closeDrawer(gravity, true);
    }

    public void closeDrawer(int gravity, boolean animate) {
        View drawerView = findDrawerWithGravity(gravity);
        if (drawerView == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No drawer view found with gravity ");
            stringBuilder.append(gravityToString(gravity));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        closeDrawer(drawerView, animate);
    }

    public boolean isDrawerOpen(@NonNull View drawer) {
        if (isDrawerView(drawer)) {
            return (((DrawerLayout$LayoutParams) drawer.getLayoutParams()).openState & 1) == 1;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("View ");
            stringBuilder.append(drawer);
            stringBuilder.append(" is not a drawer");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public boolean isDrawerOpen(int drawerGravity) {
        View drawerView = findDrawerWithGravity(drawerGravity);
        if (drawerView != null) {
            return isDrawerOpen(drawerView);
        }
        return false;
    }

    public boolean isDrawerVisible(@NonNull View drawer) {
        if (isDrawerView(drawer)) {
            return ((DrawerLayout$LayoutParams) drawer.getLayoutParams()).onScreen > 0.0f;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("View ");
            stringBuilder.append(drawer);
            stringBuilder.append(" is not a drawer");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public boolean isDrawerVisible(int drawerGravity) {
        View drawerView = findDrawerWithGravity(drawerGravity);
        if (drawerView != null) {
            return isDrawerVisible(drawerView);
        }
        return false;
    }

    private boolean hasPeekingDrawer() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (((DrawerLayout$LayoutParams) getChildAt(i).getLayoutParams()).isPeeking) {
                return true;
            }
        }
        return false;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new DrawerLayout$LayoutParams(-1, -1);
    }

    protected LayoutParams generateLayoutParams(LayoutParams p) {
        if (p instanceof DrawerLayout$LayoutParams) {
            return new DrawerLayout$LayoutParams((DrawerLayout$LayoutParams) p);
        }
        return p instanceof MarginLayoutParams ? new DrawerLayout$LayoutParams((MarginLayoutParams) p) : new DrawerLayout$LayoutParams(p);
    }

    protected boolean checkLayoutParams(LayoutParams p) {
        return (p instanceof DrawerLayout$LayoutParams) && super.checkLayoutParams(p);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new DrawerLayout$LayoutParams(getContext(), attrs);
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        if (getDescendantFocusability() != 393216) {
            int i;
            View child;
            int childCount = getChildCount();
            boolean isDrawerOpen = false;
            for (i = 0; i < childCount; i++) {
                child = getChildAt(i);
                if (!isDrawerView(child)) {
                    this.mNonDrawerViews.add(child);
                } else if (isDrawerOpen(child)) {
                    isDrawerOpen = true;
                    child.addFocusables(views, direction, focusableMode);
                }
            }
            if (!isDrawerOpen) {
                i = this.mNonDrawerViews.size();
                for (int i2 = 0; i2 < i; i2++) {
                    child = (View) this.mNonDrawerViews.get(i2);
                    if (child.getVisibility() == 0) {
                        child.addFocusables(views, direction, focusableMode);
                    }
                }
            }
            this.mNonDrawerViews.clear();
        }
    }

    private boolean hasVisibleDrawer() {
        return findVisibleDrawer() != null;
    }

    View findVisibleDrawer() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (isDrawerView(child) && isDrawerVisible(child)) {
                return child;
            }
        }
        return null;
    }

    void cancelChildViewTouch() {
        if (!this.mChildrenCanceledTouch) {
            long now = SystemClock.uptimeMillis();
            MotionEvent cancelEvent = MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0);
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).dispatchTouchEvent(cancelEvent);
            }
            cancelEvent.recycle();
            this.mChildrenCanceledTouch = true;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !hasVisibleDrawer()) {
            return super.onKeyDown(keyCode, event);
        }
        event.startTracking();
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyUp(keyCode, event);
        }
        View visibleDrawer = findVisibleDrawer();
        if (visibleDrawer != null && getDrawerLockMode(visibleDrawer) == 0) {
            closeDrawers();
        }
        return visibleDrawer != null;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof DrawerLayout$SavedState) {
            DrawerLayout$SavedState ss = (DrawerLayout$SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            if (ss.openDrawerGravity != 0) {
                View toOpen = findDrawerWithGravity(ss.openDrawerGravity);
                if (toOpen != null) {
                    openDrawer(toOpen);
                }
            }
            if (ss.lockModeLeft != 3) {
                setDrawerLockMode(ss.lockModeLeft, 3);
            }
            if (ss.lockModeRight != 3) {
                setDrawerLockMode(ss.lockModeRight, 5);
            }
            if (ss.lockModeStart != 3) {
                setDrawerLockMode(ss.lockModeStart, 8388611);
            }
            if (ss.lockModeEnd != 3) {
                setDrawerLockMode(ss.lockModeEnd, (int) GravityCompat.END);
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    protected Parcelable onSaveInstanceState() {
        DrawerLayout$SavedState ss = new DrawerLayout$SavedState(super.onSaveInstanceState());
        int childCount = getChildCount();
        int i = 0;
        while (i < childCount) {
            DrawerLayout$LayoutParams lp = (DrawerLayout$LayoutParams) getChildAt(i).getLayoutParams();
            boolean isClosedAndOpening = true;
            boolean isOpenedAndNotClosing = lp.openState == 1;
            if (lp.openState != 2) {
                isClosedAndOpening = false;
            }
            if (!isOpenedAndNotClosing) {
                if (!isClosedAndOpening) {
                    i++;
                }
            }
            ss.openDrawerGravity = lp.gravity;
            break;
        }
        ss.lockModeLeft = this.mLockModeLeft;
        ss.lockModeRight = this.mLockModeRight;
        ss.lockModeStart = this.mLockModeStart;
        ss.lockModeEnd = this.mLockModeEnd;
        return ss;
    }

    public void addView(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
        if (findOpenDrawer() == null) {
            if (!isDrawerView(child)) {
                ViewCompat.setImportantForAccessibility(child, 1);
                if (!CAN_HIDE_DESCENDANTS) {
                    ViewCompat.setAccessibilityDelegate(child, this.mChildAccessibilityDelegate);
                }
            }
        }
        ViewCompat.setImportantForAccessibility(child, 4);
        if (!CAN_HIDE_DESCENDANTS) {
            ViewCompat.setAccessibilityDelegate(child, this.mChildAccessibilityDelegate);
        }
    }

    static boolean includeChildForAccessibility(View child) {
        return (ViewCompat.getImportantForAccessibility(child) == 4 || ViewCompat.getImportantForAccessibility(child) == 2) ? false : true;
    }
}
