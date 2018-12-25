package org.catrobat.catroid.pocketmusic.fastscroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.catrobat.catroid.R$styleable;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.DefaultScrollerViewProvider;
import org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ScrollerViewProvider;
import org.catrobat.catroid.pocketmusic.ui.TactScrollRecyclerView;

public class FastScroller extends LinearLayout {
    private static final int STYLE_NONE = -1;
    private View bubble;
    private int bubbleColor;
    private int bubbleOffset;
    private int bubbleTextAppearance;
    private TextView bubbleTextView;
    private View handle;
    private int handleColor;
    private boolean manuallyChangingPosition;
    private TactScrollRecyclerView recyclerView;
    private final RecyclerViewScrollListener scrollListener;
    private int scrollerOrientation;
    private SectionTitleProvider titleProvider;
    private ScrollerViewProvider viewProvider;

    /* renamed from: org.catrobat.catroid.pocketmusic.fastscroller.FastScroller$1 */
    class C18581 implements OnTouchListener {
        C18581() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() != 0) {
                if (event.getAction() != 2) {
                    if (event.getAction() != 1) {
                        return false;
                    }
                    FastScroller.this.manuallyChangingPosition = false;
                    if (FastScroller.this.titleProvider != null) {
                        FastScroller.this.viewProvider.onHandleReleased();
                    }
                    return true;
                }
            }
            if (FastScroller.this.titleProvider != null && event.getAction() == 0) {
                FastScroller.this.viewProvider.onHandleGrabbed();
            }
            FastScroller.this.manuallyChangingPosition = true;
            float relativePos = FastScroller.this.getRelativeTouchPosition(event);
            FastScroller.this.setScrollerPosition(relativePos);
            FastScroller.this.setRecyclerViewPosition(relativePos);
            return true;
        }
    }

    public FastScroller(Context context) {
        this(context, null);
    }

    public FastScroller(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastScroller(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.scrollListener = new RecyclerViewScrollListener(this);
        setClipChildren(false);
        TypedArray style = context.obtainStyledAttributes(attrs, R$styleable.FastScroller, R.attr.FastScroller_style, 0);
        try {
            this.bubbleColor = style.getColor(0, -1);
            this.handleColor = style.getColor(2, -1);
            this.bubbleTextAppearance = style.getResourceId(1, -1);
            setViewProvider(new DefaultScrollerViewProvider());
        } finally {
            style.recycle();
        }
    }

    public void setViewProvider(ScrollerViewProvider viewProvider) {
        removeAllViews();
        this.viewProvider = viewProvider;
        viewProvider.setFastScroller(this);
        this.bubble = viewProvider.provideBubbleView(this);
        this.handle = viewProvider.provideHandleView(this);
        this.bubbleTextView = viewProvider.provideBubbleTextView();
        addView(this.bubble);
        addView(this.handle);
    }

    public void setRecyclerView(TactScrollRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if (recyclerView.getAdapter() instanceof SectionTitleProvider) {
            this.titleProvider = (SectionTitleProvider) recyclerView.getAdapter();
        }
        recyclerView.addOnScrollListener(this.scrollListener);
    }

    public void setOrientation(int orientation) {
        this.scrollerOrientation = orientation;
        super.setOrientation(orientation == 0 ? 1 : 0);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initHandleMovement();
        this.bubbleOffset = this.viewProvider.getBubbleOffset();
        applyStyling();
        if (!isInEditMode()) {
            this.scrollListener.updateHandlePosition(this.recyclerView);
        }
    }

    private void applyStyling() {
        if (this.bubbleColor != -1) {
            setBackgroundTint(this.bubbleTextView, this.bubbleColor);
        }
        if (this.handleColor != -1) {
            setBackgroundTint(this.handle, this.handleColor);
        }
        if (this.bubbleTextAppearance != -1) {
            TextViewCompat.setTextAppearance(this.bubbleTextView, this.bubbleTextAppearance);
        }
    }

    private void setBackgroundTint(View view, int color) {
        Drawable background = DrawableCompat.wrap(view.getBackground());
        if (background != null) {
            DrawableCompat.setTint(background.mutate(), color);
            view.setBackground(background);
        }
    }

    private void initHandleMovement() {
        this.handle.setOnTouchListener(new C18581());
    }

    private float getRelativeTouchPosition(MotionEvent event) {
        if (isVertical()) {
            return (event.getRawY() - getViewRawCoordinate(this.handle, 1)) / ((float) (getHeight() - this.handle.getHeight()));
        }
        return (event.getRawX() - getViewRawCoordinate(this.handle, 0)) / ((float) (getWidth() - this.handle.getWidth()));
    }

    private float getViewRawCoordinate(View view, int vectorIndex) {
        int[] location = new int[2];
        location[vectorIndex] = (int) view.getY();
        ((View) view.getParent()).getLocationInWindow(location);
        return (float) location[vectorIndex];
    }

    private void setRecyclerViewPosition(float relativePos) {
        if (this.recyclerView != null) {
            int itemCount = this.recyclerView.getAdapter().getItemCount();
            int targetPos = (int) getValueInRange(0.0f, (float) (itemCount - 2), (float) ((int) (((float) itemCount) * relativePos)));
            this.recyclerView.scrollBy(this.recyclerView.getTactViewWidth() * (targetPos - ((LinearLayoutManager) this.recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition()), 0);
            if (!(this.titleProvider == null || this.bubbleTextView == null)) {
                this.bubbleTextView.setText(this.titleProvider.getSectionTitle(targetPos));
            }
        }
    }

    void setScrollerPosition(float relativePos) {
        if (isVertical()) {
            this.bubble.setY(getValueInRange(0.0f, (float) (getHeight() - this.bubble.getHeight()), (((float) (getHeight() - this.handle.getHeight())) * relativePos) + ((float) this.bubbleOffset)));
            this.handle.setY(getValueInRange(0.0f, (float) (getHeight() - this.handle.getHeight()), ((float) (getHeight() - this.handle.getHeight())) * relativePos));
            return;
        }
        this.bubble.setX(getValueInRange(0.0f, (float) (getWidth() - this.bubble.getWidth()), (((float) (getWidth() - this.handle.getWidth())) * relativePos) + ((float) this.bubbleOffset)));
        this.handle.setX(getValueInRange(0.0f, (float) (getWidth() - this.handle.getWidth()), ((float) (getWidth() - this.handle.getWidth())) * relativePos));
    }

    public boolean isVertical() {
        return this.scrollerOrientation == 1;
    }

    boolean shouldUpdateHandlePosition() {
        return (this.handle == null || this.manuallyChangingPosition || this.recyclerView.getChildCount() <= 0) ? false : true;
    }

    ScrollerViewProvider getViewProvider() {
        return this.viewProvider;
    }

    private float getValueInRange(float min, float max, float value) {
        return Math.min(Math.max(min, value), max);
    }
}
