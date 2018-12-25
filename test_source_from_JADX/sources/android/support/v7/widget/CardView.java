package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.cardview.C0244R;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.common.primitives.Ints;

public class CardView extends FrameLayout {
    private static final int[] COLOR_BACKGROUND_ATTR = new int[]{16842801};
    private static final CardViewImpl IMPL;
    private final CardViewDelegate mCardViewDelegate;
    private boolean mCompatPadding;
    final Rect mContentPadding;
    private boolean mPreventCornerOverlap;
    final Rect mShadowBounds;
    int mUserSetMinHeight;
    int mUserSetMinWidth;

    /* renamed from: android.support.v7.widget.CardView$1 */
    class C07191 implements CardViewDelegate {
        private Drawable mCardBackground;

        C07191() {
        }

        public void setCardBackground(Drawable drawable) {
            this.mCardBackground = drawable;
            CardView.this.setBackgroundDrawable(drawable);
        }

        public boolean getUseCompatPadding() {
            return CardView.this.getUseCompatPadding();
        }

        public boolean getPreventCornerOverlap() {
            return CardView.this.getPreventCornerOverlap();
        }

        public void setShadowPadding(int left, int top, int right, int bottom) {
            CardView.this.mShadowBounds.set(left, top, right, bottom);
            super.setPadding(CardView.this.mContentPadding.left + left, CardView.this.mContentPadding.top + top, CardView.this.mContentPadding.right + right, CardView.this.mContentPadding.bottom + bottom);
        }

        public void setMinWidthHeightInternal(int width, int height) {
            if (width > CardView.this.mUserSetMinWidth) {
                super.setMinimumWidth(width);
            }
            if (height > CardView.this.mUserSetMinHeight) {
                super.setMinimumHeight(height);
            }
        }

        public Drawable getCardBackground() {
            return this.mCardBackground;
        }

        public View getCardView() {
            return CardView.this;
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new CardViewApi21Impl();
        } else if (VERSION.SDK_INT >= 17) {
            IMPL = new CardViewApi17Impl();
        } else {
            IMPL = new CardViewBaseImpl();
        }
        IMPL.initStatic();
    }

    public CardView(@NonNull Context context) {
        this(context, null);
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, C0244R.attr.cardViewStyle);
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        ColorStateList colorStateList;
        super(context, attrs, defStyleAttr);
        this.mContentPadding = new Rect();
        this.mShadowBounds = new Rect();
        this.mCardViewDelegate = new C07191();
        Context context2 = context;
        TypedArray a = context2.obtainStyledAttributes(attrs, C0244R.styleable.CardView, defStyleAttr, C0244R.style.CardView);
        if (a.hasValue(C0244R.styleable.CardView_cardBackgroundColor)) {
            colorStateList = a.getColorStateList(C0244R.styleable.CardView_cardBackgroundColor);
        } else {
            int color;
            TypedArray aa = getContext().obtainStyledAttributes(COLOR_BACKGROUND_ATTR);
            int themeColorBackground = aa.getColor(0, 0);
            aa.recycle();
            float[] hsv = new float[3];
            Color.colorToHSV(themeColorBackground, hsv);
            if (hsv[2] > 0.5f) {
                color = getResources().getColor(C0244R.color.cardview_light_background);
            } else {
                color = getResources().getColor(C0244R.color.cardview_dark_background);
            }
            colorStateList = ColorStateList.valueOf(color);
        }
        ColorStateList backgroundColor = colorStateList;
        float radius = a.getDimension(C0244R.styleable.CardView_cardCornerRadius, 0.0f);
        float elevation = a.getDimension(C0244R.styleable.CardView_cardElevation, 0.0f);
        float maxElevation = a.getDimension(C0244R.styleable.CardView_cardMaxElevation, 0.0f);
        r0.mCompatPadding = a.getBoolean(C0244R.styleable.CardView_cardUseCompatPadding, false);
        r0.mPreventCornerOverlap = a.getBoolean(C0244R.styleable.CardView_cardPreventCornerOverlap, true);
        int defaultPadding = a.getDimensionPixelSize(C0244R.styleable.CardView_contentPadding, 0);
        r0.mContentPadding.left = a.getDimensionPixelSize(C0244R.styleable.CardView_contentPaddingLeft, defaultPadding);
        r0.mContentPadding.top = a.getDimensionPixelSize(C0244R.styleable.CardView_contentPaddingTop, defaultPadding);
        r0.mContentPadding.right = a.getDimensionPixelSize(C0244R.styleable.CardView_contentPaddingRight, defaultPadding);
        r0.mContentPadding.bottom = a.getDimensionPixelSize(C0244R.styleable.CardView_contentPaddingBottom, defaultPadding);
        if (elevation > maxElevation) {
            maxElevation = elevation;
        }
        float maxElevation2 = maxElevation;
        r0.mUserSetMinWidth = a.getDimensionPixelSize(C0244R.styleable.CardView_android_minWidth, 0);
        r0.mUserSetMinHeight = a.getDimensionPixelSize(C0244R.styleable.CardView_android_minHeight, 0);
        a.recycle();
        IMPL.initialize(r0.mCardViewDelegate, context2, backgroundColor, radius, elevation, maxElevation2);
    }

    public void setPadding(int left, int top, int right, int bottom) {
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
    }

    public boolean getUseCompatPadding() {
        return this.mCompatPadding;
    }

    public void setUseCompatPadding(boolean useCompatPadding) {
        if (this.mCompatPadding != useCompatPadding) {
            this.mCompatPadding = useCompatPadding;
            IMPL.onCompatPaddingChanged(this.mCardViewDelegate);
        }
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        this.mContentPadding.set(left, top, right, bottom);
        IMPL.updatePadding(this.mCardViewDelegate);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (IMPL instanceof CardViewApi21Impl) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == Integer.MIN_VALUE || widthMode == Ints.MAX_POWER_OF_TWO) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil((double) IMPL.getMinWidth(this.mCardViewDelegate)), MeasureSpec.getSize(widthMeasureSpec)), widthMode);
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == Integer.MIN_VALUE || heightMode == Ints.MAX_POWER_OF_TWO) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil((double) IMPL.getMinHeight(this.mCardViewDelegate)), MeasureSpec.getSize(heightMeasureSpec)), heightMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setMinimumWidth(int minWidth) {
        this.mUserSetMinWidth = minWidth;
        super.setMinimumWidth(minWidth);
    }

    public void setMinimumHeight(int minHeight) {
        this.mUserSetMinHeight = minHeight;
        super.setMinimumHeight(minHeight);
    }

    public void setCardBackgroundColor(@ColorInt int color) {
        IMPL.setBackgroundColor(this.mCardViewDelegate, ColorStateList.valueOf(color));
    }

    public void setCardBackgroundColor(@Nullable ColorStateList color) {
        IMPL.setBackgroundColor(this.mCardViewDelegate, color);
    }

    @NonNull
    public ColorStateList getCardBackgroundColor() {
        return IMPL.getBackgroundColor(this.mCardViewDelegate);
    }

    public int getContentPaddingLeft() {
        return this.mContentPadding.left;
    }

    public int getContentPaddingRight() {
        return this.mContentPadding.right;
    }

    public int getContentPaddingTop() {
        return this.mContentPadding.top;
    }

    public int getContentPaddingBottom() {
        return this.mContentPadding.bottom;
    }

    public void setRadius(float radius) {
        IMPL.setRadius(this.mCardViewDelegate, radius);
    }

    public float getRadius() {
        return IMPL.getRadius(this.mCardViewDelegate);
    }

    public void setCardElevation(float elevation) {
        IMPL.setElevation(this.mCardViewDelegate, elevation);
    }

    public float getCardElevation() {
        return IMPL.getElevation(this.mCardViewDelegate);
    }

    public void setMaxCardElevation(float maxElevation) {
        IMPL.setMaxElevation(this.mCardViewDelegate, maxElevation);
    }

    public float getMaxCardElevation() {
        return IMPL.getMaxElevation(this.mCardViewDelegate);
    }

    public boolean getPreventCornerOverlap() {
        return this.mPreventCornerOverlap;
    }

    public void setPreventCornerOverlap(boolean preventCornerOverlap) {
        if (preventCornerOverlap != this.mPreventCornerOverlap) {
            this.mPreventCornerOverlap = preventCornerOverlap;
            IMPL.onPreventCornerOverlapChanged(this.mCardViewDelegate);
        }
    }
}
