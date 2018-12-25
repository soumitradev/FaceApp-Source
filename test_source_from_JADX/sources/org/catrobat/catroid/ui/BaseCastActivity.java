package org.catrobat.catroid.ui;

import android.view.Menu;
import android.view.MenuItem;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public abstract class BaseCastActivity extends BaseActivity {
    public boolean onCreateOptionsMenu(Menu menu) {
        if (SettingsFragment.isCastSharedPreferenceEnabled(this)) {
            CastManager.getInstance().setCastButton(menu.findItem(R.id.cast_button));
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.cast_button) {
            return super.onOptionsItemSelected(item);
        }
        CastManager.getInstance().openDeviceSelectorOrDisconnectDialog();
        return true;
    }
}
