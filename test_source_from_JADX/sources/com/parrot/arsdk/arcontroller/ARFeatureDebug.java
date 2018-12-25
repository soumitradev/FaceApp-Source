package com.parrot.arsdk.arcontroller;

public class ARFeatureDebug {
    public static String ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_ID;
    public static String ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_LABEL;
    public static String ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_MODE;
    public static String ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_RANGE_MAX;
    public static String ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_RANGE_MIN;
    public static String ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_RANGE_STEP;
    public static String ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_TYPE;
    public static String ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_VALUE;
    public static String ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSLIST_ID;
    public static String ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSLIST_VALUE;
    private static String TAG = "ARFeatureDebug";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendGetAllSettings(long j);

    private native int nativeSendSetSetting(long j, short s, String str);

    private static native String nativeStaticGetKeyDebugSettingsInfoId();

    private static native String nativeStaticGetKeyDebugSettingsInfoLabel();

    private static native String nativeStaticGetKeyDebugSettingsInfoListflags();

    private static native String nativeStaticGetKeyDebugSettingsInfoMode();

    private static native String nativeStaticGetKeyDebugSettingsInfoRangemax();

    private static native String nativeStaticGetKeyDebugSettingsInfoRangemin();

    private static native String nativeStaticGetKeyDebugSettingsInfoRangestep();

    private static native String nativeStaticGetKeyDebugSettingsInfoType();

    private static native String nativeStaticGetKeyDebugSettingsInfoValue();

    private static native String nativeStaticGetKeyDebugSettingsListId();

    private static native String nativeStaticGetKeyDebugSettingsListValue();

    static {
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_ID = "";
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_LABEL = "";
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_TYPE = "";
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_MODE = "";
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_RANGE_MIN = "";
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_RANGE_MAX = "";
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_RANGE_STEP = "";
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_VALUE = "";
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSLIST_ID = "";
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSLIST_VALUE = "";
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_ID = nativeStaticGetKeyDebugSettingsInfoId();
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_LABEL = nativeStaticGetKeyDebugSettingsInfoLabel();
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_TYPE = nativeStaticGetKeyDebugSettingsInfoType();
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_MODE = nativeStaticGetKeyDebugSettingsInfoMode();
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_RANGE_MIN = nativeStaticGetKeyDebugSettingsInfoRangemin();
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_RANGE_MAX = nativeStaticGetKeyDebugSettingsInfoRangemax();
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_RANGE_STEP = nativeStaticGetKeyDebugSettingsInfoRangestep();
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSINFO_VALUE = nativeStaticGetKeyDebugSettingsInfoValue();
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSLIST_ID = nativeStaticGetKeyDebugSettingsListId();
        ARCONTROLLER_DICTIONARY_KEY_DEBUG_SETTINGSLIST_VALUE = nativeStaticGetKeyDebugSettingsListValue();
    }

    public ARFeatureDebug(long nativeFeature) {
        if (nativeFeature != 0) {
            this.jniFeature = nativeFeature;
            this.initOk = true;
        }
    }

    public void dispose() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                this.jniFeature = 0;
                this.initOk = false;
            }
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public ARCONTROLLER_ERROR_ENUM sendGetAllSettings() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendGetAllSettings(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSetSetting(short _id, String _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSetSetting(this.jniFeature, _id, _value));
            }
        }
        return error;
    }
}
