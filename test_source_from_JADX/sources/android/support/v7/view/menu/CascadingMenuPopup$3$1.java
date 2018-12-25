package android.support.v7.view.menu;

import android.support.v7.view.menu.CascadingMenuPopup.C00633;
import android.support.v7.view.menu.CascadingMenuPopup.CascadingMenuInfo;
import android.view.MenuItem;

class CascadingMenuPopup$3$1 implements Runnable {
    final /* synthetic */ C00633 this$1;
    final /* synthetic */ MenuItem val$item;
    final /* synthetic */ MenuBuilder val$menu;
    final /* synthetic */ CascadingMenuInfo val$nextInfo;

    CascadingMenuPopup$3$1(C00633 this$1, CascadingMenuInfo cascadingMenuInfo, MenuItem menuItem, MenuBuilder menuBuilder) {
        this.this$1 = this$1;
        this.val$nextInfo = cascadingMenuInfo;
        this.val$item = menuItem;
        this.val$menu = menuBuilder;
    }

    public void run() {
        if (this.val$nextInfo != null) {
            this.this$1.this$0.mShouldCloseImmediately = true;
            this.val$nextInfo.menu.close(false);
            this.this$1.this$0.mShouldCloseImmediately = false;
        }
        if (this.val$item.isEnabled() && this.val$item.hasSubMenu()) {
            this.val$menu.performItemAction(this.val$item, 4);
        }
    }
}
