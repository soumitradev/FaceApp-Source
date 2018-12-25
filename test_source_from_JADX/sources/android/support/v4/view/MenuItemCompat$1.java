package android.support.v4.view;

import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;

class MenuItemCompat$1 implements OnActionExpandListener {
    final /* synthetic */ MenuItemCompat.OnActionExpandListener val$listener;

    MenuItemCompat$1(MenuItemCompat.OnActionExpandListener onActionExpandListener) {
        this.val$listener = onActionExpandListener;
    }

    public boolean onMenuItemActionExpand(MenuItem item) {
        return this.val$listener.onMenuItemActionExpand(item);
    }

    public boolean onMenuItemActionCollapse(MenuItem item) {
        return this.val$listener.onMenuItemActionCollapse(item);
    }
}
