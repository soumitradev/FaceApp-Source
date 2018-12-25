package android.support.v7.widget;

import android.support.v7.widget.ActionMenuView.OnMenuItemClickListener;
import android.view.MenuItem;

class Toolbar$1 implements OnMenuItemClickListener {
    final /* synthetic */ Toolbar this$0;

    Toolbar$1(Toolbar this$0) {
        this.this$0 = this$0;
    }

    public boolean onMenuItemClick(MenuItem item) {
        if (this.this$0.mOnMenuItemClickListener != null) {
            return this.this$0.mOnMenuItemClickListener.onMenuItemClick(item);
        }
        return false;
    }
}
