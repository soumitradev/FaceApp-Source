package android.support.v7.app;

import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.view.Menu;
import android.view.Window;

final class AppCompatDelegateImplV9$PanelMenuPresenterCallback implements Callback {
    final /* synthetic */ AppCompatDelegateImplV9 this$0;

    AppCompatDelegateImplV9$PanelMenuPresenterCallback(AppCompatDelegateImplV9 appCompatDelegateImplV9) {
        this.this$0 = appCompatDelegateImplV9;
    }

    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        Menu parentMenu = menu.getRootMenu();
        boolean isSubMenu = parentMenu != menu;
        AppCompatDelegateImplV9$PanelFeatureState panel = this.this$0.findMenuPanel(isSubMenu ? parentMenu : menu);
        if (panel == null) {
            return;
        }
        if (isSubMenu) {
            this.this$0.callOnPanelClosed(panel.featureId, panel, parentMenu);
            this.this$0.closePanel(panel, true);
            return;
        }
        this.this$0.closePanel(panel, allMenusAreClosing);
    }

    public boolean onOpenSubMenu(MenuBuilder subMenu) {
        if (subMenu == null && this.this$0.mHasActionBar) {
            Window.Callback cb = this.this$0.getWindowCallback();
            if (!(cb == null || this.this$0.isDestroyed())) {
                cb.onMenuOpened(108, subMenu);
            }
        }
        return true;
    }
}
