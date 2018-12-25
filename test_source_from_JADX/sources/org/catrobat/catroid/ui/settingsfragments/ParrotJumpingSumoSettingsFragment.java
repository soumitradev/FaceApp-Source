package org.catrobat.catroid.ui.settingsfragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import org.catrobat.catroid.generated70026.R;

public class ParrotJumpingSumoSettingsFragment extends PreferenceFragment {
    public static final String TAG = ParrotJumpingSumoSettingsFragment.class.getSimpleName();

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getPreferenceScreen().getTitle());
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragment.setToChosenLanguage(getActivity());
        addPreferencesFromResource(R.xml.jumping_sumo_preferences);
    }
}
