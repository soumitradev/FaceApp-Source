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
import org.catrobat.catroid.common.DroneConfigPreference.Preferences;
import org.catrobat.catroid.generated70026.R;

public class ParrotARDroneSettingsFragment extends PreferenceFragment {
    public static final String TAG = ParrotARDroneSettingsFragment.class.getSimpleName();

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getPreferenceScreen().getTitle());
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragment.setToChosenLanguage(getActivity());
        addPreferencesFromResource(R.xml.drone_preferences);
        CheckBoxPreference droneCheckBoxPreference = (CheckBoxPreference) findPreference(SettingsFragment.SETTINGS_SHOW_PARROT_AR_DRONE_BRICKS);
        final PreferenceCategory droneConnectionSettings = (PreferenceCategory) findPreference(SettingsFragment.DRONE_SETTINGS_CATEGORY);
        droneConnectionSettings.setEnabled(droneCheckBoxPreference.isChecked());
        final String[] dronePreferences = new String[]{SettingsFragment.DRONE_CONFIGS, SettingsFragment.DRONE_ALTITUDE_LIMIT, SettingsFragment.DRONE_VERTICAL_SPEED, SettingsFragment.DRONE_ROTATION_SPEED, SettingsFragment.DRONE_TILT_ANGLE};
        droneCheckBoxPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object isChecked) {
                droneConnectionSettings.setEnabled(((Boolean) isChecked).booleanValue());
                return true;
            }
        });
        for (String dronePreference : dronePreferences) {
            ListPreference listPreference = (ListPreference) findPreference(dronePreference);
            Object obj = -1;
            switch (dronePreference.hashCode()) {
                case -1269160228:
                    if (dronePreference.equals(SettingsFragment.DRONE_ROTATION_SPEED)) {
                        obj = 3;
                        break;
                    }
                    break;
                case -680942380:
                    if (dronePreference.equals(SettingsFragment.DRONE_VERTICAL_SPEED)) {
                        obj = 2;
                        break;
                    }
                    break;
                case 809943668:
                    if (dronePreference.equals(SettingsFragment.DRONE_ALTITUDE_LIMIT)) {
                        obj = 1;
                        break;
                    }
                    break;
                case 1189797130:
                    if (dronePreference.equals(SettingsFragment.DRONE_CONFIGS)) {
                        obj = null;
                        break;
                    }
                    break;
                case 1424749159:
                    if (dronePreference.equals(SettingsFragment.DRONE_TILT_ANGLE)) {
                        obj = 4;
                        break;
                    }
                    break;
                default:
                    break;
            }
            switch (obj) {
                case null:
                    listPreference.setEntries(R.array.drone_setting_default_config);
                    final ListPreference list = listPreference;
                    listPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            int index = list.findIndexOfValue(newValue.toString());
                            for (String dronePreference : dronePreferences) {
                                ListPreference listPreference = (ListPreference) ParrotARDroneSettingsFragment.this.findPreference(dronePreference);
                                Object obj = -1;
                                int hashCode = dronePreference.hashCode();
                                if (hashCode != -1269160228) {
                                    if (hashCode != -680942380) {
                                        if (hashCode != 809943668) {
                                            if (hashCode == 1424749159) {
                                                if (dronePreference.equals(SettingsFragment.DRONE_TILT_ANGLE)) {
                                                    obj = 3;
                                                }
                                            }
                                        } else if (dronePreference.equals(SettingsFragment.DRONE_ALTITUDE_LIMIT)) {
                                            obj = null;
                                        }
                                    } else if (dronePreference.equals(SettingsFragment.DRONE_VERTICAL_SPEED)) {
                                        obj = 1;
                                    }
                                } else if (dronePreference.equals(SettingsFragment.DRONE_ROTATION_SPEED)) {
                                    obj = 2;
                                }
                                switch (obj) {
                                    case null:
                                        listPreference.setValue("FIRST");
                                        break;
                                    case 1:
                                        if (index == 0 || index == 1) {
                                            listPreference.setValue("SECOND");
                                        }
                                        if (index != 2 && index != 3) {
                                            break;
                                        }
                                        listPreference.setValue("THIRD");
                                        break;
                                        break;
                                    case 2:
                                        if (index == 0 || index == 1) {
                                            listPreference.setValue("SECOND");
                                        }
                                        if (index != 2 && index != 3) {
                                            break;
                                        }
                                        listPreference.setValue("THIRD");
                                        break;
                                    case 3:
                                        if (index == 0 || index == 1) {
                                            listPreference.setValue("SECOND");
                                        }
                                        if (index != 2 && index != 3) {
                                            break;
                                        }
                                        listPreference.setValue("THIRD");
                                        break;
                                    default:
                                        break;
                                }
                            }
                            return true;
                        }
                    });
                    break;
                case 1:
                    listPreference.setEntries(R.array.drone_altitude_spinner_items);
                    break;
                case 2:
                    listPreference.setEntries(R.array.drone_max_vertical_speed_items);
                    break;
                case 3:
                    listPreference.setEntries(R.array.drone_max_rotation_speed_items);
                    break;
                case 4:
                    listPreference.setEntries(R.array.drone_max_tilt_angle_items);
                    break;
                default:
                    break;
            }
            listPreference.setEntryValues(Preferences.getPreferenceCodes());
        }
    }
}
