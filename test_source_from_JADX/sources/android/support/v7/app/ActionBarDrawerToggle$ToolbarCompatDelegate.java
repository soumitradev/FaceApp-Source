package android.support.v7.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBarDrawerToggle.Delegate;
import android.support.v7.widget.Toolbar;

class ActionBarDrawerToggle$ToolbarCompatDelegate implements Delegate {
    final CharSequence mDefaultContentDescription;
    final Drawable mDefaultUpIndicator;
    final Toolbar mToolbar;

    ActionBarDrawerToggle$ToolbarCompatDelegate(Toolbar toolbar) {
        this.mToolbar = toolbar;
        this.mDefaultUpIndicator = toolbar.getNavigationIcon();
        this.mDefaultContentDescription = toolbar.getNavigationContentDescription();
    }

    public void setActionBarUpIndicator(Drawable upDrawable, @StringRes int contentDescRes) {
        this.mToolbar.setNavigationIcon(upDrawable);
        setActionBarDescription(contentDescRes);
    }

    public void setActionBarDescription(@StringRes int contentDescRes) {
        if (contentDescRes == 0) {
            this.mToolbar.setNavigationContentDescription(this.mDefaultContentDescription);
        } else {
            this.mToolbar.setNavigationContentDescription(contentDescRes);
        }
    }

    public Drawable getThemeUpIndicator() {
        return this.mDefaultUpIndicator;
    }

    public Context getActionBarThemedContext() {
        return this.mToolbar.getContext();
    }

    public boolean isNavigationVisible() {
        return true;
    }
}
