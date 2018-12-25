package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

@Deprecated
public class ActionBarDrawerToggle implements DrawerListener {
    private static final int ID_HOME = 16908332;
    private static final String TAG = "ActionBarDrawerToggle";
    private static final int[] THEME_ATTRS = new int[]{16843531};
    private static final float TOGGLE_DRAWABLE_OFFSET = 0.33333334f;
    final Activity mActivity;
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    private Drawable mDrawerImage;
    private final int mDrawerImageResource;
    private boolean mDrawerIndicatorEnabled;
    private final DrawerLayout mDrawerLayout;
    private boolean mHasCustomUpIndicator;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private ActionBarDrawerToggle$SetIndicatorInfo mSetIndicatorInfo;
    private ActionBarDrawerToggle$SlideDrawable mSlider;

    @Deprecated
    public interface Delegate {
        @Nullable
        Drawable getThemeUpIndicator();

        void setActionBarDescription(@StringRes int i);

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i);
    }

    @Deprecated
    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate();
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, @DrawableRes int drawerImageRes, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        this(activity, drawerLayout, assumeMaterial(activity) ^ 1, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    private static boolean assumeMaterial(Context context) {
        return context.getApplicationInfo().targetSdkVersion >= 21 && VERSION.SDK_INT >= 21;
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, boolean animate, @DrawableRes int drawerImageRes, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        this.mDrawerIndicatorEnabled = true;
        this.mActivity = activity;
        if (activity instanceof DelegateProvider) {
            this.mActivityImpl = ((DelegateProvider) activity).getDrawerToggleDelegate();
        } else {
            this.mActivityImpl = null;
        }
        this.mDrawerLayout = drawerLayout;
        this.mDrawerImageResource = drawerImageRes;
        this.mOpenDrawerContentDescRes = openDrawerContentDescRes;
        this.mCloseDrawerContentDescRes = closeDrawerContentDescRes;
        this.mHomeAsUpIndicator = getThemeUpIndicator();
        this.mDrawerImage = ContextCompat.getDrawable(activity, drawerImageRes);
        this.mSlider = new ActionBarDrawerToggle$SlideDrawable(this, this.mDrawerImage);
        this.mSlider.setOffset(animate ? TOGGLE_DRAWABLE_OFFSET : 0.0f);
    }

    public void syncState() {
        if (this.mDrawerLayout.isDrawerOpen(8388611)) {
            this.mSlider.setPosition(1.0f);
        } else {
            this.mSlider.setPosition(0.0f);
        }
        if (this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(8388611) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
        }
    }

    public void setHomeAsUpIndicator(Drawable indicator) {
        if (indicator == null) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
            this.mHasCustomUpIndicator = false;
        } else {
            this.mHomeAsUpIndicator = indicator;
            this.mHasCustomUpIndicator = true;
        }
        if (!this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
        }
    }

    public void setHomeAsUpIndicator(int resId) {
        Drawable indicator = null;
        if (resId != 0) {
            indicator = ContextCompat.getDrawable(this.mActivity, resId);
        }
        setHomeAsUpIndicator(indicator);
    }

    public void setDrawerIndicatorEnabled(boolean enable) {
        if (enable != this.mDrawerIndicatorEnabled) {
            if (enable) {
                setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(8388611) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
            } else {
                setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
            }
            this.mDrawerIndicatorEnabled = enable;
        }
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
        }
        this.mDrawerImage = ContextCompat.getDrawable(this.mActivity, this.mDrawerImageResource);
        syncState();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == null || item.getItemId() != ID_HOME || !this.mDrawerIndicatorEnabled) {
            return false;
        }
        if (this.mDrawerLayout.isDrawerVisible(8388611)) {
            this.mDrawerLayout.closeDrawer(8388611);
        } else {
            this.mDrawerLayout.openDrawer(8388611);
        }
        return true;
    }

    public void onDrawerSlide(View drawerView, float slideOffset) {
        float glyphOffset = this.mSlider.getPosition();
        if (slideOffset > 0.5f) {
            glyphOffset = Math.max(glyphOffset, Math.max(0.0f, slideOffset - 0.5f) * 2.0f);
        } else {
            glyphOffset = Math.min(glyphOffset, 2.0f * slideOffset);
        }
        this.mSlider.setPosition(glyphOffset);
    }

    public void onDrawerOpened(View drawerView) {
        this.mSlider.setPosition(1.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mCloseDrawerContentDescRes);
        }
    }

    public void onDrawerClosed(View drawerView) {
        this.mSlider.setPosition(0.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mOpenDrawerContentDescRes);
        }
    }

    public void onDrawerStateChanged(int newState) {
    }

    private Drawable getThemeUpIndicator() {
        if (this.mActivityImpl != null) {
            return this.mActivityImpl.getThemeUpIndicator();
        }
        if (VERSION.SDK_INT >= 18) {
            Context context;
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                context = actionBar.getThemedContext();
            } else {
                context = this.mActivity;
            }
            TypedArray a = context.obtainStyledAttributes(null, THEME_ATTRS, 16843470, 0);
            Drawable result = a.getDrawable(0);
            a.recycle();
            return result;
        }
        TypedArray a2 = this.mActivity.obtainStyledAttributes(THEME_ATTRS);
        Drawable result2 = a2.getDrawable(0);
        a2.recycle();
        return result2;
    }

    private void setActionBarUpIndicator(Drawable upDrawable, int contentDescRes) {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarUpIndicator(upDrawable, contentDescRes);
            return;
        }
        ActionBar actionBar;
        if (VERSION.SDK_INT >= 18) {
            actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(upDrawable);
                actionBar.setHomeActionContentDescription(contentDescRes);
            }
        } else {
            if (this.mSetIndicatorInfo == null) {
                this.mSetIndicatorInfo = new ActionBarDrawerToggle$SetIndicatorInfo(this.mActivity);
            }
            if (this.mSetIndicatorInfo.mSetHomeAsUpIndicator != null) {
                try {
                    actionBar = this.mActivity.getActionBar();
                    this.mSetIndicatorInfo.mSetHomeAsUpIndicator.invoke(actionBar, new Object[]{upDrawable});
                    this.mSetIndicatorInfo.mSetHomeActionContentDescription.invoke(actionBar, new Object[]{Integer.valueOf(contentDescRes)});
                } catch (Exception e) {
                    Log.w(TAG, "Couldn't set home-as-up indicator via JB-MR2 API", e);
                }
            } else if (this.mSetIndicatorInfo.mUpIndicatorView != null) {
                this.mSetIndicatorInfo.mUpIndicatorView.setImageDrawable(upDrawable);
            } else {
                Log.w(TAG, "Couldn't set home-as-up indicator");
            }
        }
    }

    private void setActionBarDescription(int contentDescRes) {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarDescription(contentDescRes);
            return;
        }
        ActionBar actionBar;
        if (VERSION.SDK_INT >= 18) {
            actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(contentDescRes);
            }
        } else {
            if (this.mSetIndicatorInfo == null) {
                this.mSetIndicatorInfo = new ActionBarDrawerToggle$SetIndicatorInfo(this.mActivity);
            }
            if (this.mSetIndicatorInfo.mSetHomeAsUpIndicator != null) {
                try {
                    actionBar = this.mActivity.getActionBar();
                    this.mSetIndicatorInfo.mSetHomeActionContentDescription.invoke(actionBar, new Object[]{Integer.valueOf(contentDescRes)});
                    actionBar.setSubtitle(actionBar.getSubtitle());
                } catch (Exception e) {
                    Log.w(TAG, "Couldn't set content description via JB-MR2 API", e);
                }
            }
        }
    }
}
