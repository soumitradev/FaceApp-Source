package org.catrobat.catroid.ui.settingsfragments;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor.Sensor;
import org.catrobat.catroid.generated70026.R;

public class NXTSensorsSettingsFragment extends PreferenceFragment {
    public static final String TAG = NXTSensorsSettingsFragment.class.getSimpleName();

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getPreferenceScreen().getTitle());
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragment.setToChosenLanguage(getActivity());
        addPreferencesFromResource(R.xml.nxt_preferences);
        CheckBoxPreference nxtCheckBoxPreference = (CheckBoxPreference) findPreference(SettingsFragment.SETTINGS_MINDSTORMS_NXT_BRICKS_ENABLED);
        final PreferenceCategory nxtConnectionSettings = (PreferenceCategory) findPreference(SettingsFragment.NXT_SETTINGS_CATEGORY);
        nxtConnectionSettings.setEnabled(nxtCheckBoxPreference.isChecked());
        nxtCheckBoxPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object isChecked) {
                nxtConnectionSettings.setEnabled(((Boolean) isChecked).booleanValue());
                return true;
            }
        });
        for (String sensorPreference : SettingsFragment.NXT_SENSORS) {
            ListPreference listPreference = (ListPreference) findPreference(sensorPreference);
            listPreference.setEntries(R.array.nxt_sensor_chooser);
            listPreference.setEntryValues(Sensor.getSensorCodes());
        }
    }
}
