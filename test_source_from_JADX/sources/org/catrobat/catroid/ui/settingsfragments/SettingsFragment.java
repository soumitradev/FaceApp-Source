package org.catrobat.catroid.ui.settingsfragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.DroneConfigPreference.Preferences;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor.Sensor;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.utils.CrashReporter;
import org.catrobat.catroid.utils.SnackbarUtil;

public class SettingsFragment extends PreferenceFragment {
    public static final String ACCESSIBILITY_SCREEN_KEY = "setting_accessibility_screen";
    public static final String DRONE_ALTITUDE_LIMIT = "setting_drone_altitude_limit";
    public static final String DRONE_CONFIGS = "setting_drone_basic_configs";
    public static final String DRONE_ROTATION_SPEED = "setting_drone_rotation_speed";
    public static final String DRONE_SCREEN_KEY = "settings_drone_screen";
    public static final String DRONE_SETTINGS_CATEGORY = "setting_drone_category";
    public static final String DRONE_TILT_ANGLE = "setting_drone_tilt_angle";
    public static final String DRONE_VERTICAL_SPEED = "setting_drone_vertical_speed";
    public static final String EV3_SCREEN_KEY = "setting_ev3_screen";
    public static final String[] EV3_SENSORS = new String[]{"setting_mindstorms_ev3_sensor_1", "setting_mindstorms_ev3_sensor_2", "setting_mindstorms_ev3_sensor_3", "setting_mindstorms_ev3_sensor_4"};
    public static final String EV3_SETTINGS_CATEGORY = "setting_ev3_category";
    public static final String NXT_SCREEN_KEY = "setting_nxt_screen";
    public static final String[] NXT_SENSORS = new String[]{"setting_mindstorms_nxt_sensor_1", "setting_mindstorms_nxt_sensor_2", "setting_mindstorms_nxt_sensor_3", "setting_mindstorms_nxt_sensor_4"};
    public static final String NXT_SETTINGS_CATEGORY = "setting_nxt_category";
    public static final String PARROT_JUMPING_SUMO_SCREEN_KEY = "setting_parrot_jumping_sumo_bricks";
    public static final String RASPBERRY_SCREEN_KEY = "settings_raspberry_screen";
    public static final String RASPI_CONNECTION_SETTINGS_CATEGORY = "setting_raspi_connection_settings_category";
    public static final String RASPI_HOST = "setting_raspi_host_preference";
    public static final String RASPI_PORT = "setting_raspi_port_preference";
    public static final String RASPI_VERSION_SPINNER = "setting_raspi_version_preference";
    public static final String SETTINGS_CAST_GLOBALLY_ENABLED = "setting_cast_globally_enabled";
    public static final String SETTINGS_CRASH_REPORTS = "setting_enable_crash_reports";
    public static final String SETTINGS_DRONE_CHOOSER = "settings_chooser_drone";
    public static final String SETTINGS_MINDSTORMS_EV3_BRICKS_ENABLED = "settings_mindstorms_ev3_bricks_enabled";
    public static final String SETTINGS_MINDSTORMS_EV3_SHOW_SENSOR_INFO_BOX_DISABLED = "settings_mindstorms_ev3_show_sensor_info_box_disabled";
    public static final String SETTINGS_MINDSTORMS_NXT_BRICKS_ENABLED = "settings_mindstorms_nxt_bricks_enabled";
    public static final String SETTINGS_MINDSTORMS_NXT_SHOW_SENSOR_INFO_BOX_DISABLED = "settings_mindstorms_nxt_show_sensor_info_box_disabled";
    public static final String SETTINGS_MULTILINGUAL = "setting_multilingual";
    /* renamed from: SETTINGS_PARROT_AR_DRONE_CATROBAT_TERMS_OF_SERVICE_ACCEPTED_PERMANENTLY */
    public static final String f1769x6038ab88 = "setting_parrot_ar_drone_catrobat_terms_of_service_accepted_permanently";
    /* renamed from: SETTINGS_PARROT_JUMPING_SUMO_CATROBAT_TERMS_OF_SERVICE_ACCEPTED_PERMANENTLY */
    public static final String f1770x86c800c3 = "setting_parrot_jumping_sumo_catrobat_terms_of_service_accepted_permanently";
    public static final String SETTINGS_SHOW_ARDUINO_BRICKS = "setting_arduino_bricks";
    public static final String SETTINGS_SHOW_EMBROIDERY_BRICKS = "setting_embroidery_bricks";
    public static final String SETTINGS_SHOW_HINTS = "setting_enable_hints";
    public static final String SETTINGS_SHOW_NFC_BRICKS = "setting_nfc_bricks";
    public static final String SETTINGS_SHOW_PARROT_AR_DRONE_BRICKS = "setting_parrot_ar_drone_bricks";
    public static final String SETTINGS_SHOW_PHIRO_BRICKS = "setting_enable_phiro_bricks";
    public static final String SETTINGS_SHOW_RASPI_BRICKS = "setting_raspi_bricks";
    public static final String TAG = SettingsFragment.class.getSimpleName();
    PreferenceScreen screen = null;

    /* renamed from: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$1 */
    class C20141 implements OnPreferenceChangeListener {
        C20141() {
        }

        public boolean onPreferenceChange(Preference preference, Object isChecked) {
            SettingsFragment.setAutoCrashReportingEnabled(SettingsFragment.this.getActivity().getApplicationContext(), ((Boolean) isChecked).booleanValue());
            return true;
        }
    }

    /* renamed from: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$2 */
    class C20152 implements OnPreferenceChangeListener {
        C20152() {
        }

        public boolean onPreferenceChange(Preference preference, Object newValue) {
            preference.getEditor().remove(SnackbarUtil.SHOWN_HINT_LIST).commit();
            return true;
        }
    }

    /* renamed from: org.catrobat.catroid.ui.settingsfragments.SettingsFragment$3 */
    class C20163 implements OnPreferenceChangeListener {
        C20163() {
        }

        public boolean onPreferenceChange(Preference preference, Object newValue) {
            SettingsFragment.setLanguageSharedPreference(SettingsFragment.this.getActivity().getBaseContext(), newValue.toString());
            SettingsFragment.this.startActivity(new Intent(SettingsFragment.this.getActivity().getBaseContext(), MainMenuActivity.class));
            SettingsFragment.this.getActivity().finishAffinity();
            return true;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToChosenLanguage(getActivity());
        addPreferencesFromResource(R.xml.preferences);
        setAnonymousCrashReportPreference();
        setHintPreferences();
        setLanguage();
        this.screen = getPreferenceScreen();
    }

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.preference_title);
    }

    private void setAnonymousCrashReportPreference() {
        ((CheckBoxPreference) findPreference(SETTINGS_CRASH_REPORTS)).setOnPreferenceChangeListener(new C20141());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onPreferenceTreeClick(android.preference.PreferenceScreen r6, android.preference.Preference r7) {
        /*
        r5 = this;
        r0 = r7.getKey();
        r1 = r0.hashCode();
        switch(r1) {
            case -915856881: goto L_0x003e;
            case 954050316: goto L_0x0034;
            case 1566879480: goto L_0x002a;
            case 1661083933: goto L_0x0020;
            case 1712638408: goto L_0x0016;
            case 2091838384: goto L_0x000c;
            default: goto L_0x000b;
        };
    L_0x000b:
        goto L_0x0048;
    L_0x000c:
        r1 = "setting_nxt_screen";
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0048;
    L_0x0014:
        r1 = 1;
        goto L_0x0049;
    L_0x0016:
        r1 = "setting_parrot_jumping_sumo_bricks";
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0048;
    L_0x001e:
        r1 = 4;
        goto L_0x0049;
    L_0x0020:
        r1 = "settings_raspberry_screen";
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0048;
    L_0x0028:
        r1 = 5;
        goto L_0x0049;
    L_0x002a:
        r1 = "setting_ev3_screen";
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0048;
    L_0x0032:
        r1 = 2;
        goto L_0x0049;
    L_0x0034:
        r1 = "setting_accessibility_screen";
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0048;
    L_0x003c:
        r1 = 0;
        goto L_0x0049;
    L_0x003e:
        r1 = "settings_drone_screen";
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0048;
    L_0x0046:
        r1 = 3;
        goto L_0x0049;
    L_0x0048:
        r1 = -1;
    L_0x0049:
        r2 = 2131362373; // 0x7f0a0245 float:1.8344525E38 double:1.0530329273E-314;
        switch(r1) {
            case 0: goto L_0x00e3;
            case 1: goto L_0x00c6;
            case 2: goto L_0x00a9;
            case 3: goto L_0x008c;
            case 4: goto L_0x006f;
            case 5: goto L_0x0051;
            default: goto L_0x004f;
        };
    L_0x004f:
        goto L_0x0100;
    L_0x0051:
        r1 = r5.getFragmentManager();
        r1 = r1.beginTransaction();
        r3 = new org.catrobat.catroid.ui.settingsfragments.RaspberryPiSettingsFragment;
        r3.<init>();
        r4 = org.catrobat.catroid.ui.settingsfragments.RaspberryPiSettingsFragment.TAG;
        r1 = r1.replace(r2, r3, r4);
        r2 = org.catrobat.catroid.ui.settingsfragments.RaspberryPiSettingsFragment.TAG;
        r1 = r1.addToBackStack(r2);
        r1.commit();
        goto L_0x0100;
    L_0x006f:
        r1 = r5.getFragmentManager();
        r1 = r1.beginTransaction();
        r3 = new org.catrobat.catroid.ui.settingsfragments.ParrotJumpingSumoSettingsFragment;
        r3.<init>();
        r4 = org.catrobat.catroid.ui.settingsfragments.ParrotJumpingSumoSettingsFragment.TAG;
        r1 = r1.replace(r2, r3, r4);
        r2 = org.catrobat.catroid.ui.settingsfragments.ParrotJumpingSumoSettingsFragment.TAG;
        r1 = r1.addToBackStack(r2);
        r1.commit();
        goto L_0x0100;
    L_0x008c:
        r1 = r5.getFragmentManager();
        r1 = r1.beginTransaction();
        r3 = new org.catrobat.catroid.ui.settingsfragments.ParrotARDroneSettingsFragment;
        r3.<init>();
        r4 = org.catrobat.catroid.ui.settingsfragments.ParrotARDroneSettingsFragment.TAG;
        r1 = r1.replace(r2, r3, r4);
        r2 = org.catrobat.catroid.ui.settingsfragments.ParrotARDroneSettingsFragment.TAG;
        r1 = r1.addToBackStack(r2);
        r1.commit();
        goto L_0x0100;
    L_0x00a9:
        r1 = r5.getFragmentManager();
        r1 = r1.beginTransaction();
        r3 = new org.catrobat.catroid.ui.settingsfragments.Ev3SensorsSettingsFragment;
        r3.<init>();
        r4 = org.catrobat.catroid.ui.settingsfragments.Ev3SensorsSettingsFragment.TAG;
        r1 = r1.replace(r2, r3, r4);
        r2 = org.catrobat.catroid.ui.settingsfragments.Ev3SensorsSettingsFragment.TAG;
        r1 = r1.addToBackStack(r2);
        r1.commit();
        goto L_0x0100;
    L_0x00c6:
        r1 = r5.getFragmentManager();
        r1 = r1.beginTransaction();
        r3 = new org.catrobat.catroid.ui.settingsfragments.NXTSensorsSettingsFragment;
        r3.<init>();
        r4 = org.catrobat.catroid.ui.settingsfragments.NXTSensorsSettingsFragment.TAG;
        r1 = r1.replace(r2, r3, r4);
        r2 = org.catrobat.catroid.ui.settingsfragments.NXTSensorsSettingsFragment.TAG;
        r1 = r1.addToBackStack(r2);
        r1.commit();
        goto L_0x0100;
    L_0x00e3:
        r1 = r5.getFragmentManager();
        r1 = r1.beginTransaction();
        r3 = new org.catrobat.catroid.ui.settingsfragments.AccessibilitySettingsFragment;
        r3.<init>();
        r4 = org.catrobat.catroid.ui.settingsfragments.AccessibilitySettingsFragment.TAG;
        r1 = r1.replace(r2, r3, r4);
        r2 = org.catrobat.catroid.ui.settingsfragments.AccessibilitySettingsFragment.TAG;
        r1 = r1.addToBackStack(r2);
        r1.commit();
    L_0x0100:
        r1 = super.onPreferenceTreeClick(r6, r7);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.ui.settingsfragments.SettingsFragment.onPreferenceTreeClick(android.preference.PreferenceScreen, android.preference.Preference):boolean");
    }

    private void setHintPreferences() {
        ((CheckBoxPreference) findPreference(SETTINGS_SHOW_HINTS)).setOnPreferenceChangeListener(new C20152());
    }

    public static void setTermsOfServiceAgreedPermanently(Context context, boolean agreed) {
        setBooleanSharedPreference(agreed, f1769x6038ab88, context);
    }

    public static void setTermsOfServiceJSAgreedPermanently(Context context, boolean agreed) {
        setBooleanSharedPreference(agreed, f1770x86c800c3, context);
    }

    public static boolean isEmroiderySharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_EMBROIDERY_BRICKS, context);
    }

    public static boolean isDroneSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_PARROT_AR_DRONE_BRICKS, context);
    }

    public static boolean isJSSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, PARROT_JUMPING_SUMO_SCREEN_KEY, context);
    }

    public static boolean isMindstormsNXTSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_MINDSTORMS_NXT_BRICKS_ENABLED, context);
    }

    public static boolean isMindstormsEV3SharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_MINDSTORMS_EV3_BRICKS_ENABLED, context);
    }

    public static boolean areTermsOfServiceAgreedPermanently(Context context) {
        return getBooleanSharedPreference(false, f1769x6038ab88, context);
    }

    public static boolean areTermsOfServiceJSAgreedPermanently(Context context) {
        return getBooleanSharedPreference(false, f1770x86c800c3, context);
    }

    public static boolean isPhiroSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_PHIRO_BRICKS, context);
    }

    public static boolean isCastSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_CAST_GLOBALLY_ENABLED, context);
    }

    public static void setPhiroSharedPreferenceEnabled(Context context, boolean value) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SETTINGS_SHOW_PHIRO_BRICKS, value);
        editor.commit();
    }

    public static void setJumpingSumoSharedPreferenceEnabled(Context context, boolean value) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(PARROT_JUMPING_SUMO_SCREEN_KEY, value);
        editor.commit();
    }

    public static void setArduinoSharedPreferenceEnabled(Context context, boolean value) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SETTINGS_SHOW_ARDUINO_BRICKS, value);
        editor.commit();
    }

    public static void setEmbroiderySharedPreferenceEnabled(Context context, boolean value) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SETTINGS_SHOW_EMBROIDERY_BRICKS, value);
        editor.commit();
    }

    public static void setRaspiSharedPreferenceEnabled(Context context, boolean value) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SETTINGS_SHOW_RASPI_BRICKS, value);
        editor.commit();
    }

    public static boolean isArduinoSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_ARDUINO_BRICKS, context);
    }

    public static boolean isNfcSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_NFC_BRICKS, context);
    }

    public static void setNfcSharedPreferenceEnabled(Context context, boolean value) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SETTINGS_SHOW_NFC_BRICKS, value);
        editor.commit();
    }

    public static boolean isRaspiSharedPreferenceEnabled(Context context) {
        return getBooleanSharedPreference(false, SETTINGS_SHOW_RASPI_BRICKS, context);
    }

    public static void setAutoCrashReportingEnabled(Context context, boolean isEnabled) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SETTINGS_CRASH_REPORTS, isEnabled);
        editor.commit();
        if (isEnabled) {
            CrashReporter.initialize(context);
        }
    }

    private static void setBooleanSharedPreference(boolean value, String settingsString, Context context) {
        getSharedPreferences(context).edit().putBoolean(settingsString, value).commit();
    }

    private static boolean getBooleanSharedPreference(boolean defaultValue, String settingsString, Context context) {
        return getSharedPreferences(context).getBoolean(settingsString, defaultValue);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Sensor[] getLegoNXTSensorMapping(Context context) {
        String[] sensorPreferences = NXT_SENSORS;
        Sensor[] sensorMapping = new Sensor[4];
        for (int i = 0; i < 4; i++) {
            sensorMapping[i] = Sensor.getSensorFromSensorCode(getSharedPreferences(context).getString(sensorPreferences[i], null));
        }
        return sensorMapping;
    }

    public static EV3Sensor.Sensor[] getLegoEV3SensorMapping(Context context) {
        String[] sensorPreferences = EV3_SENSORS;
        EV3Sensor.Sensor[] sensorMapping = new EV3Sensor.Sensor[4];
        for (int i = 0; i < 4; i++) {
            sensorMapping[i] = EV3Sensor.Sensor.getSensorFromSensorCode(getSharedPreferences(context).getString(sensorPreferences[i], null));
        }
        return sensorMapping;
    }

    public static String getRaspiHost(Context context) {
        return getSharedPreferences(context).getString(RASPI_HOST, null);
    }

    public static int getRaspiPort(Context context) {
        return Integer.parseInt(getSharedPreferences(context).getString(RASPI_PORT, null));
    }

    public static String getRaspiRevision(Context context) {
        return getSharedPreferences(context).getString(RASPI_VERSION_SPINNER, null);
    }

    public static void setLegoMindstormsNXTSensorMapping(Context context, Sensor[] sensorMapping) {
        Editor editor = getSharedPreferences(context).edit();
        for (int i = 0; i < NXT_SENSORS.length; i++) {
            editor.putString(NXT_SENSORS[i], sensorMapping[i].getSensorCode());
        }
        editor.commit();
    }

    public static void setLegoMindstormsEV3SensorMapping(Context context, EV3Sensor.Sensor[] sensorMapping) {
        Editor editor = getSharedPreferences(context).edit();
        for (int i = 0; i < EV3_SENSORS.length; i++) {
            editor.putString(EV3_SENSORS[i], sensorMapping[i].getSensorCode());
        }
        editor.commit();
    }

    public static void setLegoMindstormsNXTSensorMapping(Context context, Sensor sensor, String sensorSetting) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putString(sensorSetting, sensor.getSensorCode());
        editor.commit();
    }

    public static void setLegoMindstormsEV3SensorMapping(Context context, EV3Sensor.Sensor sensor, String sensorSetting) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putString(sensorSetting, sensor.getSensorCode());
        editor.commit();
    }

    public static Preferences[] getDronePreferenceMapping(Context context) {
        String[] dronePreferences = new String[]{DRONE_CONFIGS, DRONE_ALTITUDE_LIMIT, DRONE_VERTICAL_SPEED, DRONE_ROTATION_SPEED, DRONE_TILT_ANGLE};
        Preferences[] preferenceMapping = new Preferences[5];
        for (int i = 0; i < 5; i++) {
            preferenceMapping[i] = Preferences.getPreferenceFromPreferenceCode(getSharedPreferences(context).getString(dronePreferences[i], null));
        }
        return preferenceMapping;
    }

    public static Preferences getDronePreferenceMapping(Context context, String preferenceSetting) {
        return Preferences.getPreferenceFromPreferenceCode(getSharedPreferences(context).getString(preferenceSetting, null));
    }

    public static void enableARDroneBricks(Context context, Boolean newValue) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_SHOW_PARROT_AR_DRONE_BRICKS, newValue.booleanValue()).commit();
    }

    public static void setCastFeatureAvailability(Context context, boolean newValue) {
        getSharedPreferences(context).edit().putBoolean(SETTINGS_CAST_GLOBALLY_ENABLED, newValue).commit();
    }

    public static void enableJumpingSumoBricks(Context context, Boolean newValue) {
        getSharedPreferences(context).edit().putBoolean(PARROT_JUMPING_SUMO_SCREEN_KEY, newValue.booleanValue()).commit();
    }

    public static void enableLegoMindstormsNXTBricks(Context context) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SETTINGS_MINDSTORMS_NXT_BRICKS_ENABLED, true);
        editor.commit();
    }

    public static void enableLegoMindstormsEV3Bricks(Context context) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SETTINGS_MINDSTORMS_EV3_BRICKS_ENABLED, true);
        editor.commit();
    }

    public static void setDroneChooserEnabled(Context context, boolean enable) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SETTINGS_DRONE_CHOOSER, enable);
        editor.commit();
    }

    public static boolean getDroneChooserEnabled(Context context) {
        return getSharedPreferences(context).getBoolean(SETTINGS_DRONE_CHOOSER, false);
    }

    public static void resetSharedPreferences(Context context) {
        getSharedPreferences(context).edit().clear().commit();
    }

    private void setLanguage() {
        List<String> languagesNames = new ArrayList();
        for (String aLanguageCode : SharedPreferenceKeys.LANGUAGE_CODE) {
            Object obj = -1;
            int hashCode = aLanguageCode.hashCode();
            if (hashCode != -1927847986) {
                if (hashCode == 3665) {
                    if (aLanguageCode.equals("sd")) {
                        obj = null;
                    }
                }
            } else if (aLanguageCode.equals(SharedPreferenceKeys.DEVICE_LANGUAGE)) {
                obj = 1;
            }
            switch (obj) {
                case null:
                    languagesNames.add("سنڌي");
                    break;
                case 1:
                    languagesNames.add(getResources().getString(R.string.device_language));
                    break;
                default:
                    if (aLanguageCode.length() != 2) {
                        String language = aLanguageCode.substring(0, 2);
                        String country = aLanguageCode.substring(4);
                        languagesNames.add(new Locale(language, country).getDisplayName(new Locale(language, country)));
                        break;
                    }
                    languagesNames.add(new Locale(aLanguageCode).getDisplayName(new Locale(aLanguageCode)));
                    break;
            }
        }
        String[] languages = new String[languagesNames.size()];
        languagesNames.toArray(languages);
        ListPreference listPreference = (ListPreference) findPreference(SETTINGS_MULTILINGUAL);
        listPreference.setEntries(languages);
        listPreference.setEntryValues(SharedPreferenceKeys.LANGUAGE_CODE);
        listPreference.setOnPreferenceChangeListener(new C20163());
    }

    public static void setToChosenLanguage(Activity activity) {
        String languageTag = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).getString(SharedPreferenceKeys.LANGUAGE_TAG_KEY, "");
        Locale mLocale = Arrays.asList(SharedPreferenceKeys.LANGUAGE_CODE).contains(languageTag) ? getLocaleFromLanguageTag(languageTag) : new Locale(CatroidApplication.defaultSystemLanguage);
        Locale.setDefault(mLocale);
        updateLocale(activity, mLocale);
        updateLocale(activity.getApplicationContext(), mLocale);
    }

    public static void updateLocale(Context context, Locale locale) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.setLocale(locale);
        resources.updateConfiguration(conf, displayMetrics);
    }

    private static Locale getLocaleFromLanguageTag(String languageTag) {
        if (languageTag.contains("-r")) {
            String[] tags = languageTag.split("-r");
            return new Locale(tags[0], tags[1]);
        } else if (languageTag.equals(SharedPreferenceKeys.DEVICE_LANGUAGE)) {
            return new Locale(CatroidApplication.defaultSystemLanguage);
        } else {
            return new Locale(languageTag);
        }
    }

    public static void setLanguageSharedPreference(Context context, String value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(SharedPreferenceKeys.LANGUAGE_TAG_KEY, value);
        editor.commit();
    }

    public static void removeLanguageSharedPreference(Context mContext) {
        PreferenceManager.getDefaultSharedPreferences(mContext).edit().remove(SharedPreferenceKeys.LANGUAGE_TAG_KEY).commit();
    }
}
