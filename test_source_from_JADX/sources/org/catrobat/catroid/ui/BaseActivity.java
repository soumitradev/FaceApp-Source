package org.catrobat.catroid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.ui.settingsfragments.AccessibilityProfile;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.CrashReporter;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String RECOVERED_FROM_CRASH = "RECOVERED_FROM_CRASH";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyAccessibilityStyles();
        Thread.setDefaultUncaughtExceptionHandler(new BaseExceptionHandler(this));
        checkIfCrashRecoveryAndFinishActivity(this);
        if (SettingsFragment.isCastSharedPreferenceEnabled(this)) {
            CastManager.getInstance().initializeCast(this);
        }
    }

    private void applyAccessibilityStyles() {
        AccessibilityProfile.fromCurrentPreferences(PreferenceManager.getDefaultSharedPreferences(this)).applyAccessibilityStyles(getTheme());
    }

    protected void onResume() {
        super.onResume();
        if (SettingsFragment.isCastSharedPreferenceEnabled(this)) {
            CastManager.getInstance().initializeCast(this);
        }
        invalidateOptionsMenu();
        googleAnalyticsTrackScreenResume();
    }

    protected void googleAnalyticsTrackScreenResume() {
        Tracker googleTracker = ((CatroidApplication) getApplication()).getDefaultTracker();
        googleTracker.setScreenName(getClass().getName());
        googleTracker.send(new ScreenViewBuilder().build());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    private void checkIfCrashRecoveryAndFinishActivity(Activity activity) {
        if (isRecoveringFromCrash()) {
            CrashReporter.logUnhandledException();
            if (activity instanceof MainMenuActivity) {
                PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(RECOVERED_FROM_CRASH, false).commit();
            } else {
                activity.finish();
            }
        }
    }

    private boolean isRecoveringFromCrash() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(RECOVERED_FROM_CRASH, false);
    }
}
