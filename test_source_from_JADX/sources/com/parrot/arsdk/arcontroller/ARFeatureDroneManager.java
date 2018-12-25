package com.parrot.arsdk.arcontroller;

public class ARFeatureDroneManager {
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_AUTHENTICATIONFAILED_MODEL */
    public static String f1487x4d37b0ef;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_AUTHENTICATIONFAILED_NAME */
    public static String f1488x448e6fe5;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_AUTHENTICATIONFAILED_SERIAL */
    public static String f1489x637500ce;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONREFUSED_MODEL */
    public static String f1490xb6e6f2ac;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONREFUSED_NAME;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONREFUSED_SERIAL */
    public static String f1491x2fadf6b1;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_MODEL;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_NAME;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_SERIAL;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_STATE;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_ACTIVE;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_CONNECTION_ORDER */
    public static String f1492x1dc4c653;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_HAS_SAVED_KEY */
    public static String f1493x23c2fadc;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_MODEL;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_NAME;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_RSSI;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_SECURITY;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_SERIAL;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_VISIBLE;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_HAS_SAVED_KEY */
    public static String f1494x2c71811b;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_MODEL;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_NAME;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_SECURITY */
    public static String f1495x90ea6367;
    public static String ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_SERIAL;
    private static String TAG = "ARFeatureDroneManager";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendConnect(long j, String str, String str2);

    private native int nativeSendDiscoverDrones(long j);

    private native int nativeSendForget(long j, String str);

    private static native String nativeStaticGetKeyDroneManagerAuthenticationFailedModel();

    private static native String nativeStaticGetKeyDroneManagerAuthenticationFailedName();

    private static native String nativeStaticGetKeyDroneManagerAuthenticationFailedSerial();

    private static native String nativeStaticGetKeyDroneManagerConnectionRefusedModel();

    private static native String nativeStaticGetKeyDroneManagerConnectionRefusedName();

    private static native String nativeStaticGetKeyDroneManagerConnectionRefusedSerial();

    private static native String nativeStaticGetKeyDroneManagerConnectionStateModel();

    private static native String nativeStaticGetKeyDroneManagerConnectionStateName();

    private static native String nativeStaticGetKeyDroneManagerConnectionStateSerial();

    private static native String nativeStaticGetKeyDroneManagerConnectionStateState();

    private static native String nativeStaticGetKeyDroneManagerDroneListItemActive();

    private static native String nativeStaticGetKeyDroneManagerDroneListItemConnectionorder();

    private static native String nativeStaticGetKeyDroneManagerDroneListItemHassavedkey();

    private static native String nativeStaticGetKeyDroneManagerDroneListItemListflags();

    private static native String nativeStaticGetKeyDroneManagerDroneListItemModel();

    private static native String nativeStaticGetKeyDroneManagerDroneListItemName();

    private static native String nativeStaticGetKeyDroneManagerDroneListItemRssi();

    private static native String nativeStaticGetKeyDroneManagerDroneListItemSecurity();

    private static native String nativeStaticGetKeyDroneManagerDroneListItemSerial();

    private static native String nativeStaticGetKeyDroneManagerDroneListItemVisible();

    private static native String nativeStaticGetKeyDroneManagerKnownDroneItemHassavedkey();

    private static native String nativeStaticGetKeyDroneManagerKnownDroneItemListflags();

    private static native String nativeStaticGetKeyDroneManagerKnownDroneItemModel();

    private static native String nativeStaticGetKeyDroneManagerKnownDroneItemName();

    private static native String nativeStaticGetKeyDroneManagerKnownDroneItemSecurity();

    private static native String nativeStaticGetKeyDroneManagerKnownDroneItemSerial();

    static {
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_SERIAL = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_MODEL = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_NAME = "";
        f1492x1dc4c653 = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_ACTIVE = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_VISIBLE = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_SECURITY = "";
        f1493x23c2fadc = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_RSSI = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_STATE = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_SERIAL = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_MODEL = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_NAME = "";
        f1489x637500ce = "";
        f1487x4d37b0ef = "";
        f1488x448e6fe5 = "";
        f1491x2fadf6b1 = "";
        f1490xb6e6f2ac = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONREFUSED_NAME = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_SERIAL = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_MODEL = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_NAME = "";
        f1495x90ea6367 = "";
        f1494x2c71811b = "";
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_SERIAL = nativeStaticGetKeyDroneManagerDroneListItemSerial();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_MODEL = nativeStaticGetKeyDroneManagerDroneListItemModel();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_NAME = nativeStaticGetKeyDroneManagerDroneListItemName();
        f1492x1dc4c653 = nativeStaticGetKeyDroneManagerDroneListItemConnectionorder();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_ACTIVE = nativeStaticGetKeyDroneManagerDroneListItemActive();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_VISIBLE = nativeStaticGetKeyDroneManagerDroneListItemVisible();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_SECURITY = nativeStaticGetKeyDroneManagerDroneListItemSecurity();
        f1493x23c2fadc = nativeStaticGetKeyDroneManagerDroneListItemHassavedkey();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_DRONELISTITEM_RSSI = nativeStaticGetKeyDroneManagerDroneListItemRssi();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_STATE = nativeStaticGetKeyDroneManagerConnectionStateState();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_SERIAL = nativeStaticGetKeyDroneManagerConnectionStateSerial();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_MODEL = nativeStaticGetKeyDroneManagerConnectionStateModel();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONSTATE_NAME = nativeStaticGetKeyDroneManagerConnectionStateName();
        f1489x637500ce = nativeStaticGetKeyDroneManagerAuthenticationFailedSerial();
        f1487x4d37b0ef = nativeStaticGetKeyDroneManagerAuthenticationFailedModel();
        f1488x448e6fe5 = nativeStaticGetKeyDroneManagerAuthenticationFailedName();
        f1491x2fadf6b1 = nativeStaticGetKeyDroneManagerConnectionRefusedSerial();
        f1490xb6e6f2ac = nativeStaticGetKeyDroneManagerConnectionRefusedModel();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_CONNECTIONREFUSED_NAME = nativeStaticGetKeyDroneManagerConnectionRefusedName();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_SERIAL = nativeStaticGetKeyDroneManagerKnownDroneItemSerial();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_MODEL = nativeStaticGetKeyDroneManagerKnownDroneItemModel();
        ARCONTROLLER_DICTIONARY_KEY_DRONE_MANAGER_KNOWNDRONEITEM_NAME = nativeStaticGetKeyDroneManagerKnownDroneItemName();
        f1495x90ea6367 = nativeStaticGetKeyDroneManagerKnownDroneItemSecurity();
        f1494x2c71811b = nativeStaticGetKeyDroneManagerKnownDroneItemHassavedkey();
    }

    public ARFeatureDroneManager(long nativeFeature) {
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

    public ARCONTROLLER_ERROR_ENUM sendDiscoverDrones() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendDiscoverDrones(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendConnect(String _serial, String _key) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendConnect(this.jniFeature, _serial, _key));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendForget(String _serial) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendForget(this.jniFeature, _serial));
            }
        }
        return error;
    }
}
