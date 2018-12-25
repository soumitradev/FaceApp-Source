package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_RC_CALIBRATION_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_RC_CHANNEL_ACTION_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_RC_CHANNEL_TYPE_ENUM;

public class ARFeatureRc {
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_CALIBRATED;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_CALIBRATION_TYPE;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_CHANNEL_ACTION;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_NEUTRAL_CALIBRATED */
    public static String f1647x27c6bc76;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_REQUIRED;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_ACTION;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_CALIBRATED_TYPE;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_INVERTED;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_SUPPORTED_TYPE;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELSMONITORSTATE_STATE;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELVALUE_ACTION;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELVALUE_ID;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELVALUE_VALUE;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_RECEIVERSTATE_ENABLED;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_RECEIVERSTATE_PROTOCOL;
    public static String ARCONTROLLER_DICTIONARY_KEY_RC_RECEIVERSTATE_STATE;
    private static String TAG = "ARFeatureRc";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendAbortCalibration(long j);

    private native int nativeSendEnableReceiver(long j, byte b);

    private native int nativeSendInvertChannel(long j, int i, byte b);

    private native int nativeSendMonitorChannels(long j, byte b);

    private native int nativeSendResetCalibration(long j);

    private native int nativeSendStartCalibration(long j, int i, int i2, int i3);

    private static native String nativeStaticGetKeyRcCalibrationStateCalibrated();

    private static native String nativeStaticGetKeyRcCalibrationStateCalibrationtype();

    private static native String nativeStaticGetKeyRcCalibrationStateChannelaction();

    private static native String nativeStaticGetKeyRcCalibrationStateNeutralcalibrated();

    private static native String nativeStaticGetKeyRcCalibrationStateRequired();

    private static native String nativeStaticGetKeyRcChannelActionItemAction();

    private static native String nativeStaticGetKeyRcChannelActionItemCalibratedtype();

    private static native String nativeStaticGetKeyRcChannelActionItemInverted();

    private static native String nativeStaticGetKeyRcChannelActionItemSupportedtype();

    private static native String nativeStaticGetKeyRcChannelValueAction();

    private static native String nativeStaticGetKeyRcChannelValueId();

    private static native String nativeStaticGetKeyRcChannelValueListflags();

    private static native String nativeStaticGetKeyRcChannelValueValue();

    private static native String nativeStaticGetKeyRcChannelsMonitorStateState();

    private static native String nativeStaticGetKeyRcReceiverStateEnabled();

    private static native String nativeStaticGetKeyRcReceiverStateProtocol();

    private static native String nativeStaticGetKeyRcReceiverStateState();

    static {
        ARCONTROLLER_DICTIONARY_KEY_RC_RECEIVERSTATE_STATE = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_RECEIVERSTATE_PROTOCOL = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_RECEIVERSTATE_ENABLED = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELSMONITORSTATE_STATE = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELVALUE_ID = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELVALUE_ACTION = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELVALUE_VALUE = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_CALIBRATION_TYPE = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_CHANNEL_ACTION = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_REQUIRED = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_CALIBRATED = "";
        f1647x27c6bc76 = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_ACTION = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_SUPPORTED_TYPE = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_CALIBRATED_TYPE = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_INVERTED = "";
        ARCONTROLLER_DICTIONARY_KEY_RC_RECEIVERSTATE_STATE = nativeStaticGetKeyRcReceiverStateState();
        ARCONTROLLER_DICTIONARY_KEY_RC_RECEIVERSTATE_PROTOCOL = nativeStaticGetKeyRcReceiverStateProtocol();
        ARCONTROLLER_DICTIONARY_KEY_RC_RECEIVERSTATE_ENABLED = nativeStaticGetKeyRcReceiverStateEnabled();
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELSMONITORSTATE_STATE = nativeStaticGetKeyRcChannelsMonitorStateState();
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELVALUE_ID = nativeStaticGetKeyRcChannelValueId();
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELVALUE_ACTION = nativeStaticGetKeyRcChannelValueAction();
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELVALUE_VALUE = nativeStaticGetKeyRcChannelValueValue();
        ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_CALIBRATION_TYPE = nativeStaticGetKeyRcCalibrationStateCalibrationtype();
        ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_CHANNEL_ACTION = nativeStaticGetKeyRcCalibrationStateChannelaction();
        ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_REQUIRED = nativeStaticGetKeyRcCalibrationStateRequired();
        ARCONTROLLER_DICTIONARY_KEY_RC_CALIBRATIONSTATE_CALIBRATED = nativeStaticGetKeyRcCalibrationStateCalibrated();
        f1647x27c6bc76 = nativeStaticGetKeyRcCalibrationStateNeutralcalibrated();
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_ACTION = nativeStaticGetKeyRcChannelActionItemAction();
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_SUPPORTED_TYPE = nativeStaticGetKeyRcChannelActionItemSupportedtype();
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_CALIBRATED_TYPE = nativeStaticGetKeyRcChannelActionItemCalibratedtype();
        ARCONTROLLER_DICTIONARY_KEY_RC_CHANNELACTIONITEM_INVERTED = nativeStaticGetKeyRcChannelActionItemInverted();
    }

    public ARFeatureRc(long nativeFeature) {
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

    public ARCONTROLLER_ERROR_ENUM sendMonitorChannels(byte _enable) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMonitorChannels(this.jniFeature, _enable));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendStartCalibration(ARCOMMANDS_RC_CALIBRATION_TYPE_ENUM _calibration_type, ARCOMMANDS_RC_CHANNEL_ACTION_ENUM _channel_action, ARCOMMANDS_RC_CHANNEL_TYPE_ENUM _channel_type) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendStartCalibration(this.jniFeature, _calibration_type.getValue(), _channel_action.getValue(), _channel_type.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendInvertChannel(ARCOMMANDS_RC_CHANNEL_ACTION_ENUM _action, byte _flag) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendInvertChannel(this.jniFeature, _action.getValue(), _flag));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAbortCalibration() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAbortCalibration(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendResetCalibration() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendResetCalibration(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendEnableReceiver(byte _enable) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendEnableReceiver(this.jniFeature, _enable));
            }
        }
        return error;
    }
}
