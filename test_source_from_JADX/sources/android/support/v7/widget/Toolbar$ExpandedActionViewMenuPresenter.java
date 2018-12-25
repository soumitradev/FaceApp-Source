package android.support.v7.widget;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.view.ViewGroup;
import android.view.ViewParent;

class Toolbar$ExpandedActionViewMenuPresenter implements MenuPresenter {
    MenuItemImpl mCurrentExpandedItem;
    MenuBuilder mMenu;
    final /* synthetic */ Toolbar this$0;

    Toolbar$ExpandedActionViewMenuPresenter(Toolbar toolbar) {
        this.this$0 = toolbar;
    }

    public void initForMenu(Context context, MenuBuilder menu) {
        if (!(this.mMenu == null || this.mCurrentExpandedItem == null)) {
            this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
        }
        this.mMenu = menu;
    }

    public MenuView getMenuView(ViewGroup root) {
        return null;
    }

    public void updateMenuView(boolean cleared) {
        if (this.mCurrentExpandedItem != null) {
            boolean found = false;
            if (this.mMenu != null) {
                int count = this.mMenu.size();
                for (int i = 0; i < count; i++) {
                    if (this.mMenu.getItem(i) == this.mCurrentExpandedItem) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
            }
        }
    }

    public void setCallback(Callback cb) {
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        return false;
    }

    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
    }

    public boolean flagActionItems() {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
        this.this$0.ensureCollapseButtonView();
        ViewParent collapseButtonParent = this.this$0.mCollapseButtonView.getParent();
        if (collapseButtonParent != this.this$0) {
            if (collapseButtonParent instanceof ViewGroup) {
                ((ViewGroup) collapseButtonParent).removeView(this.this$0.mCollapseButtonView);
            }
            this.this$0.addView(this.this$0.mCollapseButtonView);
        }
        this.this$0.mExpandedActionView = item.getActionView();
        this.mCurrentExpandedItem = item;
        ViewParent expandedActionParent = this.this$0.mExpandedActionView.getParent();
        if (expandedActionParent != this.this$0) {
            if (expandedActionParent instanceof ViewGroup) {
                ((ViewGroup) expandedActionParent).removeView(this.this$0.mExpandedActionView);
            }
            Toolbar$LayoutParams lp = this.this$0.generateDefaultLayoutParams();
            lp.gravity = 8388611 | (this.this$0.mButtonGravity & 112);
            lp.mViewType = 2;
            this.this$0.mExpandedActionView.setLayoutParams(lp);
            this.this$0.addView(this.this$0.mExpandedActionView);
        }
        this.this$0.removeChildrenForExpandedActionView();
        this.this$0.requestLayout();
        item.setActionViewExpanded(true);
        if (this.this$0.mExpandedActionView instanceof CollapsibleActionView) {
            ((CollapsibleActionView) this.this$0.mExpandedActionView).onActionViewExpanded();
        }
        return true;
    }

    public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
        if (this.this$0.mExpandedActionView instanceof CollapsibleActionView) {
            ((CollapsibleActionView) this.this$0.mExpandedActionView).onActionViewCollapsed();
        }
        this.this$0.removeView(this.this$0.mExpandedActionView);
        this.this$0.removeView(this.this$0.mCollapseButtonView);
        this.this$0.mExpandedActionView = null;
        this.this$0.addChildrenForExpandedActionView();
        this.mCurrentExpandedItem = null;
        this.this$0.requestLayout();
        item.setActionViewExpanded(false);
        return true;
    }

    public int getId() {
        return 0;
    }

    public Parcelable onSaveInstanceState() {
        return null;
    }

    public void onRestoreInstanceState(Parcelable state) {
    }
}
