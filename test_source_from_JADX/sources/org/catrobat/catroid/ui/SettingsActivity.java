package org.catrobat.catroid.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.settingsfragments.AccessibilityProfilesFragment;
import org.catrobat.catroid.ui.settingsfragments.AccessibilitySettingsFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class SettingsActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
        if (getIntent().getExtras() != null && getIntent().getBooleanExtra(AccessibilityProfilesFragment.SETTINGS_FRAGMENT_INTENT_KEY, false)) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new AccessibilitySettingsFragment()).addToBackStack(AccessibilitySettingsFragment.TAG).commit();
        }
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.preference_title);
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
