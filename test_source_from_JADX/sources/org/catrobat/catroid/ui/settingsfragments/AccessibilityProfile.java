package org.catrobat.catroid.ui.settingsfragments;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.Theme;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.catrobat.catroid.generated70026.R;

public class AccessibilityProfile {
    public static final String BEGINNER_BRICKS = "accessibility_beginner_bricks";
    private static final Set<String> BOOLEAN_PREFERENCES = new HashSet(Arrays.asList(new String[]{LARGE_TEXT, HIGH_CONTRAST, ICONS, LARGE_ICONS, ICON_HIGH_CONTRAST, ELEMENT_SPACING, BEGINNER_BRICKS, DRAGNDROP_DELAY}));
    private static final String CUSTOM_ACCESSIBILITY_PROFILE = "custom_accessibility_profile";
    public static final String DRAGNDROP_DELAY = "accessibility_dragndrop_delay";
    static final String DYSLEXIC = "dyslexic";
    static final String ELEMENT_SPACING = "accessibility_element_spacing";
    private static final String FONT_STYLE = "accessibility_font_style";
    static final String HIGH_CONTRAST = "accessibility_high_contrast";
    static final String ICONS = "accessibility_category_icons";
    static final String ICON_HIGH_CONTRAST = "accessibility_category_icons_high_contrast";
    static final String LARGE_ICONS = "accessibility_category_icons_big";
    static final String LARGE_TEXT = "accessibility_large_text";
    private static final String REGULAR = "regular";
    static final String SERIF = "serif";
    private Set<String> setPreferences = new HashSet();

    private AccessibilityProfile(Set<String> preferences) {
        this.setPreferences = preferences;
    }

    AccessibilityProfile(String... preferences) {
        this.setPreferences = new HashSet(Arrays.asList(preferences));
    }

    public static AccessibilityProfile fromCurrentPreferences(SharedPreferences sharedPreferences) {
        Set preferences = new HashSet();
        for (String preference : BOOLEAN_PREFERENCES) {
            if (sharedPreferences.getBoolean(preference, false)) {
                preferences.add(preference);
            }
        }
        String fontStyle = sharedPreferences.getString(FONT_STYLE, REGULAR);
        if (!fontStyle.equals(REGULAR)) {
            preferences.add(fontStyle);
        }
        return new AccessibilityProfile(preferences);
    }

    static AccessibilityProfile fromCustomProfile(SharedPreferences sharedPreferences) {
        return new AccessibilityProfile(sharedPreferences.getStringSet(CUSTOM_ACCESSIBILITY_PROFILE, null));
    }

    void saveAsCustomProfile(SharedPreferences sharedPreferences) {
        Editor editor = sharedPreferences.edit();
        editor.putStringSet(CUSTOM_ACCESSIBILITY_PROFILE, this.setPreferences);
        editor.commit();
    }

    private void clearCurrent(SharedPreferences sharedPreferences) {
        Editor editor = sharedPreferences.edit();
        for (String preference : BOOLEAN_PREFERENCES) {
            editor.putBoolean(preference, false);
        }
        editor.putString(FONT_STYLE, REGULAR);
        editor.commit();
    }

    void setAsCurrent(SharedPreferences sharedPreferences) {
        clearCurrent(sharedPreferences);
        Editor editor = sharedPreferences.edit();
        for (String preference : this.setPreferences) {
            if (!preference.equals(SERIF)) {
                if (!preference.equals(DYSLEXIC)) {
                    editor.putBoolean(preference, true);
                }
            }
            editor.putString(FONT_STYLE, preference);
        }
        editor.commit();
    }

    public void applyAccessibilityStyles(Theme theme) {
        if (this.setPreferences.contains(LARGE_TEXT)) {
            theme.applyStyle(R.style.FontSizeLarge, true);
        }
        if (this.setPreferences.contains(HIGH_CONTRAST)) {
            theme.applyStyle(R.style.ContrastHigh, true);
        }
        if (this.setPreferences.contains(ELEMENT_SPACING)) {
            theme.applyStyle(R.style.SpacingLarge, true);
        }
        if (this.setPreferences.contains(ICON_HIGH_CONTRAST)) {
            theme.applyStyle(R.style.CategoryIconContrastHigh, true);
        }
        if (this.setPreferences.contains(ICONS)) {
            theme.applyStyle(R.style.CategoryIconVisible, true);
        }
        if (this.setPreferences.contains(LARGE_ICONS)) {
            theme.applyStyle(R.style.CategoryIconSizeLarge, true);
        }
        if (this.setPreferences.contains(SERIF)) {
            theme.applyStyle(R.style.FontSerif, true);
        }
        if (this.setPreferences.contains(DYSLEXIC)) {
            theme.applyStyle(R.style.FontDyslexic, true);
        }
    }
}
