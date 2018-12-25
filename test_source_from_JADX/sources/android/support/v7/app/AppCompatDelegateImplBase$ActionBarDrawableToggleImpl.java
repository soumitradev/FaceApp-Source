package android.support.v7.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarDrawerToggle.Delegate;
import android.support.v7.appcompat.C0034R;
import android.support.v7.widget.TintTypedArray;

class AppCompatDelegateImplBase$ActionBarDrawableToggleImpl implements Delegate {
    final /* synthetic */ AppCompatDelegateImplBase this$0;

    AppCompatDelegateImplBase$ActionBarDrawableToggleImpl(AppCompatDelegateImplBase appCompatDelegateImplBase) {
        this.this$0 = appCompatDelegateImplBase;
    }

    public Drawable getThemeUpIndicator() {
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), null, new int[]{C0034R.attr.homeAsUpIndicator});
        Drawable result = a.getDrawable(0);
        a.recycle();
        return result;
    }

    public Context getActionBarThemedContext() {
        return this.this$0.getActionBarThemedContext();
    }

    public boolean isNavigationVisible() {
        ActionBar ab = this.this$0.getSupportActionBar();
        return (ab == null || (ab.getDisplayOptions() & 4) == 0) ? false : true;
    }

    public void setActionBarUpIndicator(Drawable upDrawable, int contentDescRes) {
        ActionBar ab = this.this$0.getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(upDrawable);
            ab.setHomeActionContentDescription(contentDescRes);
        }
    }

    public void setActionBarDescription(int contentDescRes) {
        ActionBar ab = this.this$0.getSupportActionBar();
        if (ab != null) {
            ab.setHomeActionContentDescription(contentDescRes);
        }
    }
}
