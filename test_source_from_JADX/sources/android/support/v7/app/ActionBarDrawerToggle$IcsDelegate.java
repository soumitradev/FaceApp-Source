package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarDrawerToggle.Delegate;

class ActionBarDrawerToggle$IcsDelegate implements Delegate {
    final Activity mActivity;
    SetIndicatorInfo mSetIndicatorInfo;

    ActionBarDrawerToggle$IcsDelegate(Activity activity) {
        this.mActivity = activity;
    }

    public Drawable getThemeUpIndicator() {
        return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(this.mActivity);
    }

    public Context getActionBarThemedContext() {
        ActionBar actionBar = this.mActivity.getActionBar();
        if (actionBar != null) {
            return actionBar.getThemedContext();
        }
        return this.mActivity;
    }

    public boolean isNavigationVisible() {
        ActionBar actionBar = this.mActivity.getActionBar();
        return (actionBar == null || (actionBar.getDisplayOptions() & 4) == 0) ? false : true;
    }

    public void setActionBarUpIndicator(Drawable themeImage, int contentDescRes) {
        ActionBar actionBar = this.mActivity.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, themeImage, contentDescRes);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    public void setActionBarDescription(int contentDescRes) {
        this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, contentDescRes);
    }
}
