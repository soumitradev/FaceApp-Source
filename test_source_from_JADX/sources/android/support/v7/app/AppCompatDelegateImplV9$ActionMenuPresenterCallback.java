package android.support.v7.app;

import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.view.Window;

final class AppCompatDelegateImplV9$ActionMenuPresenterCallback implements Callback {
    final /* synthetic */ AppCompatDelegateImplV9 this$0;

    AppCompatDelegateImplV9$ActionMenuPresenterCallback(AppCompatDelegateImplV9 appCompatDelegateImplV9) {
        this.this$0 = appCompatDelegateImplV9;
    }

    public boolean onOpenSubMenu(MenuBuilder subMenu) {
        Window.Callback cb = this.this$0.getWindowCallback();
        if (cb != null) {
            cb.onMenuOpened(108, subMenu);
        }
        return true;
    }

    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        this.this$0.checkCloseActionMenu(menu);
    }
}
