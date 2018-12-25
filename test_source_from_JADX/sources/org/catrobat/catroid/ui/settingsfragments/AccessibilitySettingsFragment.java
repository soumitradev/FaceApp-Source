package org.catrobat.catroid.ui.settingsfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.SettingsActivity;
import org.catrobat.catroid.utils.ToastUtil;

public class AccessibilitySettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    private static final String ACCESSIBILITY_PROFILES_SCREEN_KEY = "setting_accessibility_profile_screen";
    static final String CUSTOM_PROFILE = "accessibility_profile_is_custom";
    public static final String TAG = AccessibilitySettingsFragment.class.getSimpleName();
    private boolean preferenceChanged = false;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.accessiblity_preferences);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragment.setToChosenLanguage(getActivity());
    }

    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getPreferenceScreen().getTitle());
    }

    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onDetach() {
        super.onDetach();
        if (this.preferenceChanged) {
            startActivity(new Intent(getActivity().getBaseContext(), MainMenuActivity.class));
            startActivity(new Intent(getActivity().getBaseContext(), SettingsActivity.class));
            ToastUtil.showSuccess(getActivity(), getString(R.string.accessibility_settings_applied));
            getActivity().finishAffinity();
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        this.preferenceChanged = true;
        sharedPreferences.edit().putBoolean(CUSTOM_PROFILE, true);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        Object obj;
        String key = preference.getKey();
        if (key.hashCode() == -2060912030) {
            if (key.equals(ACCESSIBILITY_PROFILES_SCREEN_KEY)) {
                obj = null;
                if (obj != null) {
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, new AccessibilityProfilesFragment(), AccessibilityProfilesFragment.TAG).addToBackStack(AccessibilityProfilesFragment.TAG).commit();
                }
                return super.onPreferenceTreeClick(preferenceScreen, preference);
            }
        }
        obj = -1;
        if (obj != null) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new AccessibilityProfilesFragment(), AccessibilityProfilesFragment.TAG).addToBackStack(AccessibilityProfilesFragment.TAG).commit();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
