package android.support.v7.app;

import android.view.View;
import android.view.View.OnClickListener;

class ActionBarDrawerToggle$1 implements OnClickListener {
    final /* synthetic */ ActionBarDrawerToggle this$0;

    ActionBarDrawerToggle$1(ActionBarDrawerToggle this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        if (this.this$0.mDrawerIndicatorEnabled) {
            this.this$0.toggle();
        } else if (this.this$0.mToolbarNavigationClickListener != null) {
            this.this$0.mToolbarNavigationClickListener.onClick(v);
        }
    }
}
