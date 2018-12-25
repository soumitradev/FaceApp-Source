package org.catrobat.catroid.ui.settingsfragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.SettingsActivity;
import org.catrobat.catroid.utils.ToastUtil;

public class AccessibilityProfilesFragment extends Fragment implements OnClickListener {
    public static final String SETTINGS_FRAGMENT_INTENT_KEY = "rollBackToAccessibilityFragment";
    public static final String TAG = AccessibilityProfilesFragment.class.getSimpleName();
    private View parent;

    private class AccessibilityProfileVH {
        ImageView imageView;
        RadioButton radioButton;
        TextView subtitle;
        TextView title;
        View view;

        AccessibilityProfileVH(View view) {
            this.view = view;
            this.radioButton = (RadioButton) view.findViewById(R.id.radio_button);
            this.imageView = (ImageView) view.findViewById(R.id.image_view);
            this.title = (TextView) view.findViewById(R.id.title_view);
            this.subtitle = (TextView) view.findViewById(R.id.subtitle_view);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.parent = inflater.inflate(R.layout.fragment_accesibility_profiles, container, false);
        return this.parent;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AccessibilityProfileVH viewHolder = new AccessibilityProfileVH(this.parent.findViewById(R.id.custom_profile));
        viewHolder.view.setOnClickListener(this);
        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_default_myprofile));
        viewHolder.title.setText(R.string.preference_access_title_profile_custom);
        viewHolder.subtitle.setText(R.string.preference_access_summary_profile_custom);
        viewHolder = new AccessibilityProfileVH(this.parent.findViewById(R.id.default_profile));
        viewHolder.view.setOnClickListener(this);
        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_default_myprofile));
        viewHolder.title.setText(R.string.preference_access_title_profile_default);
        viewHolder.subtitle.setText(R.string.preference_access_summary_profile_default);
        viewHolder = new AccessibilityProfileVH(this.parent.findViewById(R.id.argus));
        viewHolder.view.setOnClickListener(this);
        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_argus));
        viewHolder.title.setText(R.string.preference_access_title_profile_argus);
        viewHolder.subtitle.setText(R.string.preference_access_summary_profile_argus);
        viewHolder = new AccessibilityProfileVH(this.parent.findViewById(R.id.fenrir));
        viewHolder.view.setOnClickListener(this);
        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_fenrir));
        viewHolder.title.setText(R.string.preference_access_title_profile_fenrir);
        viewHolder.subtitle.setText(R.string.preference_access_summary_profile_fenrir);
        viewHolder = new AccessibilityProfileVH(this.parent.findViewById(R.id.odin));
        viewHolder.view.setOnClickListener(this);
        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_odin));
        viewHolder.title.setText(R.string.preference_access_title_profile_odin);
        viewHolder.subtitle.setText(R.string.preference_access_summary_profile_odin);
        viewHolder = new AccessibilityProfileVH(this.parent.findViewById(R.id.tiro));
        viewHolder.view.setOnClickListener(this);
        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_tiro));
        viewHolder.title.setText(R.string.preference_access_title_profile_tiro);
        viewHolder.subtitle.setText(R.string.preference_access_summary_profile_tiro);
        new AccessibilityProfileVH(this.parent.findViewById(PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(SharedPreferenceKeys.ACCESSIBILITY_PROFILE_PREFERENCE_KEY, R.id.default_profile))).radioButton.setChecked(true);
    }

    public void onClick(View v) {
        AccessibilityProfile newProfile;
        new AccessibilityProfileVH(v).radioButton.setChecked(true);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        AccessibilityProfile currentProfile = AccessibilityProfile.fromCurrentPreferences(sharedPreferences);
        if (sharedPreferences.getBoolean("accessibility_profile_is_custom", false)) {
            currentProfile.saveAsCustomProfile(sharedPreferences);
            sharedPreferences.edit().putBoolean("accessibility_profile_is_custom", false).commit();
        }
        switch (v.getId()) {
            case R.id.argus:
                newProfile = new AccessibilityProfile("accessibility_high_contrast", "accessibility_category_icons");
                break;
            case R.id.custom_profile:
                newProfile = AccessibilityProfile.fromCustomProfile(sharedPreferences);
                sharedPreferences.edit().putBoolean("accessibility_profile_is_custom", true).commit();
                break;
            case R.id.fenrir:
                newProfile = new AccessibilityProfile("accessibility_element_spacing", AccessibilityProfile.DRAGNDROP_DELAY);
                break;
            case R.id.odin:
                newProfile = new AccessibilityProfile("accessibility_large_text", "accessibility_high_contrast", "accessibility_category_icons", "accessibility_category_icons_big", "accessibility_element_spacing");
                break;
            case R.id.tiro:
                newProfile = new AccessibilityProfile(AccessibilityProfile.BEGINNER_BRICKS);
                break;
            default:
                newProfile = new AccessibilityProfile(new String[0]);
                break;
        }
        sharedPreferences.edit().putInt(SharedPreferenceKeys.ACCESSIBILITY_PROFILE_PREFERENCE_KEY, v.getId()).commit();
        newProfile.setAsCurrent(sharedPreferences);
        startActivity(new Intent(getActivity().getBaseContext(), MainMenuActivity.class));
        Intent settingsIntent = new Intent(getActivity().getBaseContext(), SettingsActivity.class);
        settingsIntent.putExtra(SETTINGS_FRAGMENT_INTENT_KEY, true);
        startActivity(settingsIntent);
        ToastUtil.showSuccess(getActivity(), getString(R.string.accessibility_settings_applied));
        getActivity().finishAffinity();
    }

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.preference_title_accessibility_profiles);
    }
}
