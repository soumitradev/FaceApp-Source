package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarDrawerToggle.Delegate;

@RequiresApi(18)
class ActionBarDrawerToggle$JellybeanMr2Delegate implements Delegate {
    final Activity mActivity;

    ActionBarDrawerToggle$JellybeanMr2Delegate(Activity activity) {
        this.mActivity = activity;
    }

    public Drawable getThemeUpIndicator() {
        TypedArray a = getActionBarThemedContext().obtainStyledAttributes(null, new int[]{16843531}, 16843470, 0);
        Drawable result = a.getDrawable(0);
        a.recycle();
        return result;
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

    public void setActionBarUpIndicator(Drawable drawable, int contentDescRes) {
        ActionBar actionBar = this.mActivity.getActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(drawable);
            actionBar.setHomeActionContentDescription(contentDescRes);
        }
    }

    public void setActionBarDescription(int contentDescRes) {
        ActionBar actionBar = this.mActivity.getActionBar();
        if (actionBar != null) {
            actionBar.setHomeActionContentDescription(contentDescRes);
        }
    }
}
