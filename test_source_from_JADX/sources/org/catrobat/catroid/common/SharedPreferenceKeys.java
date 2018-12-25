package org.catrobat.catroid.common;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;

public final class SharedPreferenceKeys {
    public static final String ACCESSIBILITY_PROFILE_PREFERENCE_KEY = "AccessibilityProfile";
    public static final String AGREED_TO_PRIVACY_POLICY_PREFERENCE_KEY = "AgreedToPrivacyPolicy";
    public static final String DEVICE_LANGUAGE = "deviceLanguage";
    public static final String[] LANGUAGE_CODE = new String[]{DEVICE_LANGUAGE, "az", "in", "bs", "ca", "cs", "sr-rCS", "sr-rSP", "da", "de", "en-rAU", "en-rCA", "en-rGB", "en", "es", "el", "fr", "gl", "hr", "it", "sw", "hu", "mk", "ms", "nl", "no", "pl", "pt-rBR", "pt", "ru", "ro", "sq", "sl", "sk", "sv", "vi", "tr", "uk", "bg", "ml", "ta", "kn", "te", "th", "gu", "hi", "ja", "ko", "lt", "zh-rCN", "zh-rTW", ArchiveStreamFactory.AR, "ur", "fa", "ps", "sd", "iw"};
    public static final String LANGUAGE_TAG_KEY = "applicationLanguage";
    public static final String SCRATCH_CONVERTER_CLIENT_ID_PREFERENCE_KEY = "scratchconverter.clientID";
    public static final String SCRATCH_CONVERTER_DOWNLOAD_STATE_PREFERENCE_KEY = "scratchconverter.downloadStatePref";
    public static final String SHOW_DETAILS_LOOKS_PREFERENCE_KEY = "showDetailsLookList";
    public static final String SHOW_DETAILS_PROJECTS_PREFERENCE_KEY = "showDetailsProjectList";
    public static final String SHOW_DETAILS_SCENES_PREFERENCE_KEY = "showDetailsSceneList";
    public static final String SHOW_DETAILS_SCRATCH_PROJECTS_PREFERENCE_KEY = "showDetailsScratchProjects";
    public static final String SHOW_DETAILS_SOUNDS_PREFERENCE_KEY = "showDetailsSoundList";
    public static final String SHOW_DETAILS_SPRITES_PREFERENCE_KEY = "showDetailsSpriteList";

    private SharedPreferenceKeys() {
        throw new AssertionError("No.");
    }
}
