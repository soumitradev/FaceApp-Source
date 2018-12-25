package org.catrobat.catroid.ui.settingsfragments;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import org.catrobat.catroid.generated70026.R;

public class RaspberryPiSettingsFragment extends PreferenceFragment {
    public static final String TAG = RaspberryPiSettingsFragment.class.getSimpleName();

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getPreferenceScreen().getTitle());
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragment.setToChosenLanguage(getActivity());
        addPreferencesFromResource(R.xml.raspberry_preferences);
        CheckBoxPreference raspiCheckBoxPreference = (CheckBoxPreference) findPreference(SettingsFragment.SETTINGS_SHOW_RASPI_BRICKS);
        final PreferenceCategory rpiConnectionSettings = (PreferenceCategory) findPreference(SettingsFragment.RASPI_CONNECTION_SETTINGS_CATEGORY);
        rpiConnectionSettings.setEnabled(raspiCheckBoxPreference.isChecked());
        raspiCheckBoxPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object isChecked) {
                rpiConnectionSettings.setEnabled(((Boolean) isChecked).booleanValue());
                return true;
            }
        });
        final EditTextPreference host = (EditTextPreference) findPreference(SettingsFragment.RASPI_HOST);
        host.setSummary(host.getText());
        host.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                host.setSummary(newValue.toString());
                return true;
            }
        });
        final EditTextPreference port = (EditTextPreference) findPreference(SettingsFragment.RASPI_PORT);
        port.setSummary(port.getText());
        port.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                port.setSummary(newValue.toString());
                return true;
            }
        });
    }
}
