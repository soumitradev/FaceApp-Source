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
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor.Sensor;
import org.catrobat.catroid.generated70026.R;

public class Ev3SensorsSettingsFragment extends PreferenceFragment {
    public static final String TAG = Ev3SensorsSettingsFragment.class.getSimpleName();

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getPreferenceScreen().getTitle());
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragment.setToChosenLanguage(getActivity());
        addPreferencesFromResource(R.xml.ev3_preferences);
        CheckBoxPreference ev3CheckBoxPreference = (CheckBoxPreference) findPreference(SettingsFragment.SETTINGS_MINDSTORMS_EV3_BRICKS_ENABLED);
        final PreferenceCategory ev3ConnectionSettings = (PreferenceCategory) findPreference(SettingsFragment.EV3_SETTINGS_CATEGORY);
        ev3ConnectionSettings.setEnabled(ev3CheckBoxPreference.isChecked());
        ev3CheckBoxPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object isChecked) {
                ev3ConnectionSettings.setEnabled(((Boolean) isChecked).booleanValue());
                return true;
            }
        });
        for (String sensorPreference : SettingsFragment.EV3_SENSORS) {
            ListPreference listPreference = (ListPreference) findPreference(sensorPreference);
            listPreference.setEntries(R.array.ev3_sensor_chooser);
            listPreference.setEntryValues(Sensor.getSensorCodes());
        }
    }
}
