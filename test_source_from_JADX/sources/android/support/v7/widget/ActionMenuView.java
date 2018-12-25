package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuBuilder$ItemInvoker;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import com.google.common.primitives.Ints;

public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder$ItemInvoker, MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    @RestrictTo({Scope.LIBRARY_GROUP})
    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    private static class ActionMenuPresenterCallback implements Callback {
        ActionMenuPresenterCallback() {
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            return false;
        }
    }

    private class MenuBuilderCallback implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(item);
        }

        public void onMenuModeChange(MenuBuilder menu) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menu);
            }
        }
    }

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBaselineAligned(false);
        float density = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * density);
        this.mGeneratedItemPadding = (int) (4.0f * density);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    public void setPopupTheme(@StyleRes int resId) {
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId == 0) {
                this.mPopupContext = getContext();
            } else {
                this.mPopupContext = new ContextThemeWrapper(getContext(), resId);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setPresenter(ActionMenuPresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.setMenuView(this);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean wasFormatted = this.mFormatItems;
        this.mFormatItems = MeasureSpec.getMode(widthMeasureSpec) == Ints.MAX_POWER_OF_TWO;
        if (wasFormatted != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (!(!this.mFormatItems || this.mMenu == null || widthSize == this.mFormatItemsWidth)) {
            this.mFormatItemsWidth = widthSize;
            this.mMenu.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (!this.mFormatItems || childCount <= 0) {
            for (int i = 0; i < childCount; i++) {
                ActionMenuView$LayoutParams lp = (ActionMenuView$LayoutParams) getChildAt(i).getLayoutParams();
                lp.rightMargin = 0;
                lp.leftMargin = 0;
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        onMeasureExactFormat(widthMeasureSpec, heightMeasureSpec);
    }

    private void onMeasureExactFormat(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthPadding = getPaddingLeft() + getPaddingRight();
        int heightPadding = getPaddingTop() + getPaddingBottom();
        int itemHeightSpec = getChildMeasureSpec(heightMeasureSpec, heightPadding, -2);
        widthSize -= widthPadding;
        int cellCount = widthSize / this.mMinCellSize;
        int cellSizeRemaining = widthSize % this.mMinCellSize;
        if (cellCount == 0) {
            setMeasuredDimension(widthSize, 0);
            return;
        }
        int cellCount2;
        int cellSizeRemaining2;
        boolean isGeneratedItem;
        boolean needsExpansion;
        int widthSize2;
        boolean z;
        long smallestItemsAt;
        int cellSize = r0.mMinCellSize + (cellSizeRemaining / cellCount);
        int cellsRemaining = cellCount;
        boolean hasOverflow = false;
        long smallestItemsAt2 = 0;
        int childCount = getChildCount();
        int heightSize2 = heightSize;
        heightSize = 0;
        int visibleItemCount = 0;
        int expandableItemCount = 0;
        int maxCellsUsed = 0;
        int cellsRemaining2 = cellsRemaining;
        cellsRemaining = 0;
        while (true) {
            int widthPadding2 = widthPadding;
            if (cellsRemaining >= childCount) {
                break;
            }
            int heightPadding2;
            View child = getChildAt(cellsRemaining);
            cellCount2 = cellCount;
            if (child.getVisibility() == 8) {
                heightPadding2 = heightPadding;
                cellSizeRemaining2 = cellSizeRemaining;
            } else {
                int visibleItemCount2;
                boolean z2;
                isGeneratedItem = child instanceof ActionMenuItemView;
                visibleItemCount++;
                if (isGeneratedItem) {
                    cellSizeRemaining2 = cellSizeRemaining;
                    visibleItemCount2 = visibleItemCount;
                    z2 = false;
                    child.setPadding(r0.mGeneratedItemPadding, 0, r0.mGeneratedItemPadding, 0);
                } else {
                    cellSizeRemaining2 = cellSizeRemaining;
                    visibleItemCount2 = visibleItemCount;
                    z2 = false;
                }
                ActionMenuView$LayoutParams lp = (ActionMenuView$LayoutParams) child.getLayoutParams();
                lp.expanded = z2;
                lp.extraPixels = z2;
                lp.cellsUsed = z2;
                lp.expandable = z2;
                lp.leftMargin = z2;
                lp.rightMargin = z2;
                boolean z3 = isGeneratedItem && ((ActionMenuItemView) child).hasText();
                lp.preventEdgeOffset = z3;
                visibleItemCount = measureChildForCells(child, cellSize, lp.isOverflowButton ? 1 : cellsRemaining2, itemHeightSpec, heightPadding);
                maxCellsUsed = Math.max(maxCellsUsed, visibleItemCount);
                heightPadding2 = heightPadding;
                if (lp.expandable != 0) {
                    expandableItemCount++;
                }
                if (lp.isOverflowButton) {
                    hasOverflow = true;
                }
                cellsRemaining2 -= visibleItemCount;
                heightSize = Math.max(heightSize, child.getMeasuredHeight());
                if (visibleItemCount == 1) {
                    smallestItemsAt2 |= (long) (1 << cellsRemaining);
                    visibleItemCount = visibleItemCount2;
                    heightSize = heightSize;
                } else {
                    visibleItemCount = visibleItemCount2;
                }
            }
            cellsRemaining++;
            widthPadding = widthPadding2;
            cellCount = cellCount2;
            cellSizeRemaining = cellSizeRemaining2;
            heightPadding = heightPadding2;
            int i = heightMeasureSpec;
        }
        cellCount2 = cellCount;
        cellSizeRemaining2 = cellSizeRemaining;
        boolean centerSingleExpandedItem = hasOverflow && visibleItemCount == 2;
        isGeneratedItem = false;
        while (expandableItemCount > 0 && cellsRemaining2 > 0) {
            long minCellsAt = 0;
            cellCount = Integer.MAX_VALUE;
            widthPadding = 0;
            cellsRemaining = 0;
            while (true) {
                cellSizeRemaining = cellsRemaining;
                if (cellSizeRemaining >= childCount) {
                    break;
                }
                View child2 = getChildAt(cellSizeRemaining);
                needsExpansion = isGeneratedItem;
                ActionMenuView$LayoutParams needsExpansion2 = (ActionMenuView$LayoutParams) child2.getLayoutParams();
                if (needsExpansion2.expandable != null) {
                    if (needsExpansion2.cellsUsed < cellCount) {
                        cellCount = needsExpansion2.cellsUsed;
                        minCellsAt = 1 << cellSizeRemaining;
                        widthPadding = 1;
                    } else if (needsExpansion2.cellsUsed == cellCount) {
                        widthPadding++;
                        minCellsAt |= 1 << cellSizeRemaining;
                    }
                }
                cellsRemaining = cellSizeRemaining + 1;
                isGeneratedItem = needsExpansion;
            }
            needsExpansion = isGeneratedItem;
            long smallestItemsAt3 = smallestItemsAt2 | minCellsAt;
            if (widthPadding > cellsRemaining2) {
                widthSize2 = widthSize;
                z = centerSingleExpandedItem;
                smallestItemsAt = smallestItemsAt3;
                break;
            }
            int minCellsItemCount;
            cellCount++;
            smallestItemsAt2 = smallestItemsAt3;
            i = 0;
            while (i < childCount) {
                View child3 = getChildAt(i);
                ActionMenuView$LayoutParams lp2 = (ActionMenuView$LayoutParams) child3.getLayoutParams();
                widthSize2 = widthSize;
                minCellsItemCount = widthPadding;
                z = centerSingleExpandedItem;
                if ((minCellsAt & ((long) (1 << i))) != 0) {
                    if (z && lp2.preventEdgeOffset && cellsRemaining2 == 1) {
                        child3.setPadding(r0.mGeneratedItemPadding + cellSize, 0, r0.mGeneratedItemPadding, 0);
                    }
                    lp2.cellsUsed++;
                    lp2.expanded = true;
                    cellsRemaining2--;
                } else if (lp2.cellsUsed == cellCount) {
                    smallestItemsAt2 |= (long) (1 << i);
                }
                i++;
                widthPadding = minCellsItemCount;
                widthSize = widthSize2;
                centerSingleExpandedItem = z;
            }
            minCellsItemCount = widthPadding;
            z = centerSingleExpandedItem;
            isGeneratedItem = true;
        }
        widthSize2 = widthSize;
        z = centerSingleExpandedItem;
        needsExpansion = isGeneratedItem;
        smallestItemsAt = smallestItemsAt2;
        boolean singleItem = !hasOverflow && visibleItemCount == 1;
        int i2;
        if (cellsRemaining2 <= 0 || smallestItemsAt == 0) {
            i2 = visibleItemCount;
        } else {
            boolean z4;
            int i3;
            if (cellsRemaining2 >= visibleItemCount - 1 && !singleItem) {
                if (maxCellsUsed <= 1) {
                    z4 = singleItem;
                    i2 = visibleItemCount;
                }
            }
            float expandCount = (float) Long.bitCount(smallestItemsAt);
            if (singleItem) {
                i3 = childCount;
            } else {
                if (!((smallestItemsAt & 1) == 0 || ((ActionMenuView$LayoutParams) getChildAt(0).getLayoutParams()).preventEdgeOffset)) {
                    expandCount -= 0.5f;
                }
                i3 = childCount;
                if (!((smallestItemsAt & ((long) (1 << (childCount - 1)))) == 0 || ((ActionMenuView$LayoutParams) getChildAt(i3 - 1).getLayoutParams()).preventEdgeOffset)) {
                    expandCount -= 0.5f;
                }
            }
            cellCount = expandCount > 0.0f ? (int) (((float) (cellsRemaining2 * cellSize)) / expandCount) : 0;
            cellSizeRemaining = 0;
            while (true) {
                childCount = i3;
                if (cellSizeRemaining >= childCount) {
                    break;
                }
                z4 = singleItem;
                i2 = visibleItemCount;
                if ((smallestItemsAt & ((long) (true << cellSizeRemaining))) != 0) {
                    singleItem = getChildAt(cellSizeRemaining);
                    lp2 = (ActionMenuView$LayoutParams) singleItem.getLayoutParams();
                    if (singleItem instanceof ActionMenuItemView) {
                        lp2.extraPixels = cellCount;
                        lp2.expanded = true;
                        if (cellSizeRemaining == 0 && !lp2.preventEdgeOffset) {
                            lp2.leftMargin = (-cellCount) / 2;
                        }
                        needsExpansion = true;
                    } else {
                        if (lp2.isOverflowButton) {
                            lp2.extraPixels = cellCount;
                            lp2.expanded = true;
                            lp2.rightMargin = (-cellCount) / 2;
                            needsExpansion = true;
                        } else {
                            if (cellSizeRemaining != 0) {
                                lp2.leftMargin = cellCount / 2;
                            }
                            if (cellSizeRemaining != childCount - 1) {
                                lp2.rightMargin = cellCount / 2;
                            }
                        }
                        cellSizeRemaining++;
                        i3 = childCount;
                        singleItem = z4;
                        visibleItemCount = i2;
                    }
                }
                cellSizeRemaining++;
                i3 = childCount;
                singleItem = z4;
                visibleItemCount = i2;
            }
            i2 = visibleItemCount;
        }
        if (needsExpansion) {
            int i4 = 0;
            while (true) {
                i = i4;
                if (i >= childCount) {
                    break;
                }
                View child4 = getChildAt(i);
                ActionMenuView$LayoutParams lp3 = (ActionMenuView$LayoutParams) child4.getLayoutParams();
                if (lp3.expanded) {
                    child4.measure(MeasureSpec.makeMeasureSpec((lp3.cellsUsed * cellSize) + lp3.extraPixels, Ints.MAX_POWER_OF_TWO), itemHeightSpec);
                }
                i4 = i + 1;
            }
        }
        if (heightMode != Ints.MAX_POWER_OF_TWO) {
            widthSize = heightSize;
        } else {
            widthSize = heightSize2;
        }
        setMeasuredDimension(widthSize2, widthSize);
    }

    static int measureChildForCells(View child, int cellSize, int cellsRemaining, int parentHeightMeasureSpec, int parentHeightPadding) {
        View view = child;
        int i = cellsRemaining;
        ActionMenuView$LayoutParams lp = (ActionMenuView$LayoutParams) view.getLayoutParams();
        int childHeightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(parentHeightMeasureSpec) - parentHeightPadding, MeasureSpec.getMode(parentHeightMeasureSpec));
        ActionMenuItemView itemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean expandable = false;
        boolean hasText = itemView != null && itemView.hasText();
        int cellsUsed = 0;
        if (i > 0 && (!hasText || i >= 2)) {
            view.measure(MeasureSpec.makeMeasureSpec(cellSize * i, Integer.MIN_VALUE), childHeightSpec);
            int measuredWidth = view.getMeasuredWidth();
            cellsUsed = measuredWidth / cellSize;
            if (measuredWidth % cellSize != 0) {
                cellsUsed++;
            }
            if (hasText && cellsUsed < 2) {
                cellsUsed = 2;
            }
        }
        if (!lp.isOverflowButton && hasText) {
            expandable = true;
        }
        lp.expandable = expandable;
        lp.cellsUsed = cellsUsed;
        view.measure(MeasureSpec.makeMeasureSpec(cellsUsed * cellSize, Ints.MAX_POWER_OF_TWO), childHeightSpec);
        return cellsUsed;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (this.mFormatItems) {
            int midVertical;
            boolean isLayoutRtl;
            int overflowWidth;
            int t;
            int size;
            int childCount = getChildCount();
            int midVertical2 = (bottom - top) / 2;
            int dividerWidth = getDividerWidth();
            int nonOverflowCount = 0;
            int widthRemaining = ((right - left) - getPaddingRight()) - getPaddingLeft();
            boolean hasOverflow = false;
            boolean isLayoutRtl2 = ViewUtils.isLayoutRtl(this);
            int widthRemaining2 = widthRemaining;
            widthRemaining = 0;
            int overflowWidth2 = 0;
            int i = 0;
            while (i < childCount) {
                View v = getChildAt(i);
                if (v.getVisibility() == 8) {
                    midVertical = midVertical2;
                    isLayoutRtl = isLayoutRtl2;
                } else {
                    ActionMenuView$LayoutParams p = (ActionMenuView$LayoutParams) v.getLayoutParams();
                    if (p.isOverflowButton) {
                        int l;
                        overflowWidth = v.getMeasuredWidth();
                        if (hasSupportDividerBeforeChildAt(i)) {
                            overflowWidth += dividerWidth;
                        }
                        overflowWidth2 = v.getMeasuredHeight();
                        if (isLayoutRtl2) {
                            isLayoutRtl = isLayoutRtl2;
                            l = getPaddingLeft() + p.leftMargin;
                            isLayoutRtl2 = l + overflowWidth;
                        } else {
                            isLayoutRtl = isLayoutRtl2;
                            isLayoutRtl2 = (getWidth() - getPaddingRight()) - p.rightMargin;
                            l = isLayoutRtl2 - overflowWidth;
                        }
                        t = midVertical2 - (overflowWidth2 / 2);
                        midVertical = midVertical2;
                        v.layout(l, t, isLayoutRtl2, t + overflowWidth2);
                        widthRemaining2 -= overflowWidth;
                        hasOverflow = true;
                        overflowWidth2 = overflowWidth;
                    } else {
                        midVertical = midVertical2;
                        isLayoutRtl = isLayoutRtl2;
                        size = (v.getMeasuredWidth() + p.leftMargin) + p.rightMargin;
                        widthRemaining += size;
                        widthRemaining2 -= size;
                        if (hasSupportDividerBeforeChildAt(i)) {
                            widthRemaining += dividerWidth;
                        }
                        nonOverflowCount++;
                    }
                }
                i++;
                isLayoutRtl2 = isLayoutRtl;
                midVertical2 = midVertical;
            }
            midVertical = midVertical2;
            isLayoutRtl = isLayoutRtl2;
            int i2 = 1;
            int spacerSize;
            int t2;
            if (childCount != 1 || hasOverflow) {
                if (hasOverflow) {
                    i2 = 0;
                }
                size = nonOverflowCount - i2;
                t = 0;
                spacerSize = Math.max(0, size > 0 ? widthRemaining2 / size : 0);
                int dividerWidth2;
                int overflowWidth3;
                if (isLayoutRtl) {
                    overflowWidth = getWidth() - getPaddingRight();
                    while (t < childCount) {
                        View v2 = getChildAt(t);
                        ActionMenuView$LayoutParams lp = (ActionMenuView$LayoutParams) v2.getLayoutParams();
                        int spacerCount = size;
                        if (v2.getVisibility() == 8) {
                            dividerWidth2 = dividerWidth;
                            overflowWidth3 = overflowWidth2;
                        } else if (lp.isOverflowButton != 0) {
                            dividerWidth2 = dividerWidth;
                            overflowWidth3 = overflowWidth2;
                        } else {
                            overflowWidth -= lp.rightMargin;
                            size = v2.getMeasuredWidth();
                            i2 = v2.getMeasuredHeight();
                            midVertical2 = midVertical - (i2 / 2);
                            dividerWidth2 = dividerWidth;
                            overflowWidth3 = overflowWidth2;
                            v2.layout(overflowWidth - size, midVertical2, overflowWidth, midVertical2 + i2);
                            overflowWidth -= (lp.leftMargin + size) + spacerSize;
                        }
                        t++;
                        size = spacerCount;
                        dividerWidth = dividerWidth2;
                        overflowWidth2 = overflowWidth3;
                    }
                    dividerWidth2 = dividerWidth;
                    overflowWidth3 = overflowWidth2;
                } else {
                    dividerWidth2 = dividerWidth;
                    overflowWidth3 = overflowWidth2;
                    size = getPaddingLeft();
                    while (t < childCount) {
                        View v3 = getChildAt(t);
                        ActionMenuView$LayoutParams lp2 = (ActionMenuView$LayoutParams) v3.getLayoutParams();
                        if (v3.getVisibility() != 8) {
                            if (!lp2.isOverflowButton) {
                                size += lp2.leftMargin;
                                dividerWidth = v3.getMeasuredWidth();
                                overflowWidth2 = v3.getMeasuredHeight();
                                t2 = midVertical - (overflowWidth2 / 2);
                                v3.layout(size, t2, size + dividerWidth, t2 + overflowWidth2);
                                size += (lp2.rightMargin + dividerWidth) + spacerSize;
                            }
                        }
                        t++;
                    }
                }
                return;
            }
            View v4 = getChildAt(null);
            t = v4.getMeasuredWidth();
            spacerSize = v4.getMeasuredHeight();
            t2 = ((right - left) / 2) - (t / 2);
            i2 = midVertical - (spacerSize / 2);
            v4.layout(t2, i2, t2 + t, i2 + spacerSize);
            return;
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(@Nullable Drawable icon) {
        getMenu();
        this.mPresenter.setOverflowIcon(icon);
    }

    @Nullable
    public Drawable getOverflowIcon() {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setOverflowReserved(boolean reserveOverflow) {
        this.mReserveOverflow = reserveOverflow;
    }

    protected ActionMenuView$LayoutParams generateDefaultLayoutParams() {
        ActionMenuView$LayoutParams params = new ActionMenuView$LayoutParams(-2, -2);
        params.gravity = 16;
        return params;
    }

    public ActionMenuView$LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ActionMenuView$LayoutParams(getContext(), attrs);
    }

    protected ActionMenuView$LayoutParams generateLayoutParams(LayoutParams p) {
        if (p == null) {
            return generateDefaultLayoutParams();
        }
        ActionMenuView$LayoutParams result = p instanceof ActionMenuView$LayoutParams ? new ActionMenuView$LayoutParams((ActionMenuView$LayoutParams) p) : new ActionMenuView$LayoutParams(p);
        if (result.gravity <= 0) {
            result.gravity = 16;
        }
        return result;
    }

    protected boolean checkLayoutParams(LayoutParams p) {
        return p != null && (p instanceof ActionMenuView$LayoutParams);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public ActionMenuView$LayoutParams generateOverflowButtonLayoutParams() {
        ActionMenuView$LayoutParams result = generateDefaultLayoutParams();
        result.isOverflowButton = true;
        return result;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean invokeItem(MenuItemImpl item) {
        return this.mMenu.performItemAction(item, 0);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int getWindowAnimations() {
        return 0;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void initialize(MenuBuilder menu) {
        this.mMenu = menu;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            this.mMenu = new MenuBuilder(context);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter(context);
            this.mPresenter.setReserveOverflow(true);
            this.mPresenter.setCallback(this.mActionMenuPresenterCallback != null ? this.mActionMenuPresenterCallback : new ActionMenuPresenterCallback());
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setMenuCallbacks(Callback pcb, MenuBuilder.Callback mcb) {
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public boolean showOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    protected boolean hasSupportDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return false;
        }
        View childBefore = getChildAt(childIndex - 1);
        View child = getChildAt(childIndex);
        boolean result = false;
        if (childIndex < getChildCount() && (childBefore instanceof ActionMenuChildView)) {
            result = false | ((ActionMenuChildView) childBefore).needsDividerAfter();
        }
        if (childIndex > 0 && (child instanceof ActionMenuChildView)) {
            result |= ((ActionMenuChildView) child).needsDividerBefore();
        }
        return result;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setExpandedActionViewsExclusive(boolean exclusive) {
        this.mPresenter.setExpandedActionViewsExclusive(exclusive);
    }
}
