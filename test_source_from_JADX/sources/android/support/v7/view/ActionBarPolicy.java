package android.support.v7.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.appcompat.C0034R;
import android.support.v7.appcompat.R$bool;
import android.view.ViewConfiguration;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScreenValues;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ActionBarPolicy {
    private Context mContext;

    public static ActionBarPolicy get(Context context) {
        return new ActionBarPolicy(context);
    }

    private ActionBarPolicy(Context context) {
        this.mContext = context;
    }

    public int getMaxActionButtons() {
        Configuration configuration = this.mContext.getResources().getConfiguration();
        int widthDp = configuration.screenWidthDp;
        int heightDp = configuration.screenHeightDp;
        if (configuration.smallestScreenWidthDp <= SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT && widthDp <= SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT && (widthDp <= 960 || heightDp <= ScreenValues.CAST_SCREEN_HEIGHT)) {
            if (widthDp <= ScreenValues.CAST_SCREEN_HEIGHT || heightDp <= 960) {
                if (widthDp < 500 && (widthDp <= 640 || heightDp <= 480)) {
                    if (widthDp <= 480 || heightDp <= 640) {
                        if (widthDp >= Constants.SCRATCH_IMAGE_DEFAULT_HEIGHT) {
                            return 3;
                        }
                        return 2;
                    }
                }
                return 4;
            }
        }
        return 5;
    }

    public boolean showsOverflowMenuButton() {
        if (VERSION.SDK_INT >= 19) {
            return true;
        }
        return ViewConfiguration.get(this.mContext).hasPermanentMenuKey() ^ true;
    }

    public int getEmbeddedMenuWidthLimit() {
        return this.mContext.getResources().getDisplayMetrics().widthPixels / 2;
    }

    public boolean hasEmbeddedTabs() {
        return this.mContext.getResources().getBoolean(R$bool.abc_action_bar_embed_tabs);
    }

    public int getTabContainerHeight() {
        TypedArray a = this.mContext.obtainStyledAttributes(null, C0034R.styleable.ActionBar, C0034R.attr.actionBarStyle, 0);
        int height = a.getLayoutDimension(C0034R.styleable.ActionBar_height, 0);
        Resources r = this.mContext.getResources();
        if (!hasEmbeddedTabs()) {
            height = Math.min(height, r.getDimensionPixelSize(C0034R.dimen.abc_action_bar_stacked_max_height));
        }
        a.recycle();
        return height;
    }

    public boolean enableHomeButtonByDefault() {
        return this.mContext.getApplicationInfo().targetSdkVersion < 14;
    }

    public int getStackedTabMaxWidth() {
        return this.mContext.getResources().getDimensionPixelSize(C0034R.dimen.abc_action_bar_stacked_tab_max_width);
    }
}
