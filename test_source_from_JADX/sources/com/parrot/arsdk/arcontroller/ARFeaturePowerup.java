package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_MEDIARECORD_VIDEOV2_RECORD_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_NETWORKSETTINGS_WIFISELECTION_BAND_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_NETWORKSETTINGS_WIFISELECTION_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_NETWORK_WIFISCAN_BAND_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_PILOTING_MOTORMODE_MODE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_VIDEOSETTINGS_VIDEOMODE_MODE_ENUM;

public class ARFeaturePowerup {
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR */
    public static String f1613xda15e5d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT */
    public static String f1614xda2feef;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR */
    public static String f1615xd3a98b9a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT */
    public static String f1616xd3ab2c2c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR */
    public static String f1617x6c0e6199;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE */
    public static String f1618x6cd45422;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR */
    public static String f1619x81e22556;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE */
    public static String f1620x82a817df;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED */
    public static String f1621xe6204b9a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_BAND */
    public static String f1622xfdc232ce;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_CHANNEL */
    public static String f1623x6a3ce1aa;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE */
    public static String f1624xfdcabbd3;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSTATE_LINKQUALITYCHANGED_QUALITY */
    public static String f1625x3f20b455;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND */
    public static String f1626xe0d7e7fe;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_CHANNEL */
    public static String f1627x8564de7a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_IN_OR_OUT */
    public static String f1628xdd7cdf03;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND */
    public static String f1629xf236dd96;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_CHANNEL */
    public static String f1630xfeeae1e2;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_RSSI */
    public static String f1631xf23e67b8;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_SSID */
    public static String f1632xf23edadc;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSETTINGSSTATE_SETTINGCHANGED_CURRENT */
    public static String f1633x4a189d4f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSETTINGSSTATE_SETTINGCHANGED_MAX */
    public static String f1634xb3a6e83a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSETTINGSSTATE_SETTINGCHANGED_MIN */
    public static String f1635xb3a6e928;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSETTINGSSTATE_SETTINGCHANGED_SETTING */
    public static String f1636x7d4bee26;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSTATE_ALERTSTATECHANGED_STATE */
    public static String f1637x5c446903;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSTATE_ALTITUDECHANGED_ALTITUDE */
    public static String f1638x1874503d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSTATE_ATTITUDECHANGED_PITCH */
    public static String f1639x83a7c65d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSTATE_ATTITUDECHANGED_ROLL */
    public static String f1640xb9eda1a0;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSTATE_ATTITUDECHANGED_YAW */
    public static String f1641xc3eefccc;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSTATE_FLYINGSTATECHANGED_STATE */
    public static String f1642x55af68b2;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_PILOTINGSTATE_MOTORMODECHANGED_MODE */
    public static String f1643xc3205cf8;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_SOUNDSSTATE_BUZZCHANGED_ENABLED */
    public static String f1644x31fc777b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_VIDEOSETTINGSSTATE_AUTORECORDCHANGED_ENABLED */
    public static String f1645x66daf2a0;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_POWERUP_VIDEOSETTINGSSTATE_VIDEOMODECHANGED_MODE */
    public static String f1646x7c5141d6;
    private static String TAG = "ARFeaturePowerup";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendMediaRecordPictureV2(long j);

    private native int nativeSendMediaRecordVideoV2(long j, int i);

    private native int nativeSendMediaStreamingVideoEnable(long j, byte b);

    private native int nativeSendNetworkSettingsWifiSelection(long j, int i, int i2, byte b);

    private native int nativeSendNetworkWifiAuthChannel(long j);

    private native int nativeSendNetworkWifiScan(long j, int i);

    private native int nativeSendPilotingMotorMode(long j, int i);

    private native int nativeSendPilotingPCMD(long j, byte b, byte b2, byte b3);

    private native int nativeSendPilotingSettingsSet(long j, int i, float f);

    private native int nativeSendPilotingUserTakeOff(long j, byte b);

    private native int nativeSendSoundsBuzz(long j, byte b);

    private native int nativeSendVideoSettingsAutorecord(long j, byte b);

    private native int nativeSendVideoSettingsVideoMode(long j, int i);

    private native int nativeSetPilotingPCMD(long j, byte b, byte b2, byte b3);

    private native int nativeSetPilotingPCMDFlag(long j, byte b);

    private native int nativeSetPilotingPCMDRoll(long j, byte b);

    private native int nativeSetPilotingPCMDThrottle(long j, byte b);

    /* renamed from: nativeStaticGetKeyPowerupMediaRecordEventPictureEventChangedError */
    private static native String m461x56ec9127();

    /* renamed from: nativeStaticGetKeyPowerupMediaRecordEventPictureEventChangedEvent */
    private static native String m462x56ee31b9();

    private static native String nativeStaticGetKeyPowerupMediaRecordEventVideoEventChangedError();

    private static native String nativeStaticGetKeyPowerupMediaRecordEventVideoEventChangedEvent();

    /* renamed from: nativeStaticGetKeyPowerupMediaRecordStatePictureStateChangedV2Error */
    private static native String m463xa2064539();

    /* renamed from: nativeStaticGetKeyPowerupMediaRecordStatePictureStateChangedV2State */
    private static native String m464xa2cc37c2();

    /* renamed from: nativeStaticGetKeyPowerupMediaRecordStateVideoStateChangedV2Error */
    private static native String m465x3176a61c();

    /* renamed from: nativeStaticGetKeyPowerupMediaRecordStateVideoStateChangedV2State */
    private static native String m466x323c98a5();

    /* renamed from: nativeStaticGetKeyPowerupMediaStreamingStateVideoEnableChangedEnabled */
    private static native String m467xd5e56b0a();

    /* renamed from: nativeStaticGetKeyPowerupNetworkSettingsStateWifiSelectionChangedBand */
    private static native String m468xcdd47e04();

    /* renamed from: nativeStaticGetKeyPowerupNetworkSettingsStateWifiSelectionChangedChannel */
    private static native String m469xeb1bbed4();

    /* renamed from: nativeStaticGetKeyPowerupNetworkSettingsStateWifiSelectionChangedType */
    private static native String m470xcddd0709();

    private static native String nativeStaticGetKeyPowerupNetworkStateLinkQualityChangedQuality();

    /* renamed from: nativeStaticGetKeyPowerupNetworkStateWifiAuthChannelListChangedBand */
    private static native String m471x4e66f9da();

    /* renamed from: nativeStaticGetKeyPowerupNetworkStateWifiAuthChannelListChangedChannel */
    private static native String m472x194ab3e();

    /* renamed from: nativeStaticGetKeyPowerupNetworkStateWifiAuthChannelListChangedInorout */
    private static native String m473x49fe42c1();

    private static native String nativeStaticGetKeyPowerupNetworkStateWifiScanListChangedBand();

    private static native String nativeStaticGetKeyPowerupNetworkStateWifiScanListChangedChannel();

    private static native String nativeStaticGetKeyPowerupNetworkStateWifiScanListChangedRssi();

    private static native String nativeStaticGetKeyPowerupNetworkStateWifiScanListChangedSsid();

    /* renamed from: nativeStaticGetKeyPowerupPilotingSettingsStateSettingChangedCurrent */
    private static native String m474xea8e4003();

    /* renamed from: nativeStaticGetKeyPowerupPilotingSettingsStateSettingChangedListflags */
    private static native String m475x1034f8b3();

    private static native String nativeStaticGetKeyPowerupPilotingSettingsStateSettingChangedMax();

    private static native String nativeStaticGetKeyPowerupPilotingSettingsStateSettingChangedMin();

    /* renamed from: nativeStaticGetKeyPowerupPilotingSettingsStateSettingChangedSetting */
    private static native String m476x1dc190da();

    private static native String nativeStaticGetKeyPowerupPilotingStateAlertStateChangedState();

    private static native String nativeStaticGetKeyPowerupPilotingStateAltitudeChangedAltitude();

    private static native String nativeStaticGetKeyPowerupPilotingStateAttitudeChangedPitch();

    private static native String nativeStaticGetKeyPowerupPilotingStateAttitudeChangedRoll();

    private static native String nativeStaticGetKeyPowerupPilotingStateAttitudeChangedYaw();

    private static native String nativeStaticGetKeyPowerupPilotingStateFlyingStateChangedState();

    private static native String nativeStaticGetKeyPowerupPilotingStateMotorModeChangedMode();

    private static native String nativeStaticGetKeyPowerupSoundsStateBuzzChangedEnabled();

    /* renamed from: nativeStaticGetKeyPowerupVideoSettingsStateAutorecordChangedEnabled */
    private static native String m477x72534892();

    private static native String nativeStaticGetKeyPowerupVideoSettingsStateVideoModeChangedMode();

    static {
        f1637x5c446903 = "";
        f1642x55af68b2 = "";
        f1643xc3205cf8 = "";
        f1640xb9eda1a0 = "";
        f1639x83a7c65d = "";
        f1641xc3eefccc = "";
        f1638x1874503d = "";
        f1636x7d4bee26 = "";
        f1633x4a189d4f = "";
        f1635xb3a6e928 = "";
        f1634xb3a6e83a = "";
        f1618x6cd45422 = "";
        f1617x6c0e6199 = "";
        f1620x82a817df = "";
        f1619x81e22556 = "";
        f1614xda2feef = "";
        f1613xda15e5d = "";
        f1616xd3ab2c2c = "";
        f1615xd3a98b9a = "";
        f1624xfdcabbd3 = "";
        f1622xfdc232ce = "";
        f1623x6a3ce1aa = "";
        f1632xf23edadc = "";
        f1631xf23e67b8 = "";
        f1629xf236dd96 = "";
        f1630xfeeae1e2 = "";
        f1626xe0d7e7fe = "";
        f1627x8564de7a = "";
        f1628xdd7cdf03 = "";
        f1625x3f20b455 = "";
        f1621xe6204b9a = "";
        f1645x66daf2a0 = "";
        f1646x7c5141d6 = "";
        f1644x31fc777b = "";
        f1637x5c446903 = nativeStaticGetKeyPowerupPilotingStateAlertStateChangedState();
        f1642x55af68b2 = nativeStaticGetKeyPowerupPilotingStateFlyingStateChangedState();
        f1643xc3205cf8 = nativeStaticGetKeyPowerupPilotingStateMotorModeChangedMode();
        f1640xb9eda1a0 = nativeStaticGetKeyPowerupPilotingStateAttitudeChangedRoll();
        f1639x83a7c65d = nativeStaticGetKeyPowerupPilotingStateAttitudeChangedPitch();
        f1641xc3eefccc = nativeStaticGetKeyPowerupPilotingStateAttitudeChangedYaw();
        f1638x1874503d = nativeStaticGetKeyPowerupPilotingStateAltitudeChangedAltitude();
        f1636x7d4bee26 = m476x1dc190da();
        f1633x4a189d4f = m474xea8e4003();
        f1635xb3a6e928 = nativeStaticGetKeyPowerupPilotingSettingsStateSettingChangedMin();
        f1634xb3a6e83a = nativeStaticGetKeyPowerupPilotingSettingsStateSettingChangedMax();
        f1618x6cd45422 = m464xa2cc37c2();
        f1617x6c0e6199 = m463xa2064539();
        f1620x82a817df = m466x323c98a5();
        f1619x81e22556 = m465x3176a61c();
        f1614xda2feef = m462x56ee31b9();
        f1613xda15e5d = m461x56ec9127();
        f1616xd3ab2c2c = nativeStaticGetKeyPowerupMediaRecordEventVideoEventChangedEvent();
        f1615xd3a98b9a = nativeStaticGetKeyPowerupMediaRecordEventVideoEventChangedError();
        f1624xfdcabbd3 = m470xcddd0709();
        f1622xfdc232ce = m468xcdd47e04();
        f1623x6a3ce1aa = m469xeb1bbed4();
        f1632xf23edadc = nativeStaticGetKeyPowerupNetworkStateWifiScanListChangedSsid();
        f1631xf23e67b8 = nativeStaticGetKeyPowerupNetworkStateWifiScanListChangedRssi();
        f1629xf236dd96 = nativeStaticGetKeyPowerupNetworkStateWifiScanListChangedBand();
        f1630xfeeae1e2 = nativeStaticGetKeyPowerupNetworkStateWifiScanListChangedChannel();
        f1626xe0d7e7fe = m471x4e66f9da();
        f1627x8564de7a = m472x194ab3e();
        f1628xdd7cdf03 = m473x49fe42c1();
        f1625x3f20b455 = nativeStaticGetKeyPowerupNetworkStateLinkQualityChangedQuality();
        f1621xe6204b9a = m467xd5e56b0a();
        f1645x66daf2a0 = m477x72534892();
        f1646x7c5141d6 = nativeStaticGetKeyPowerupVideoSettingsStateVideoModeChangedMode();
        f1644x31fc777b = nativeStaticGetKeyPowerupSoundsStateBuzzChangedEnabled();
    }

    public ARFeaturePowerup(long nativeFeature) {
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

    public ARCONTROLLER_ERROR_ENUM sendPilotingPCMD(byte _flag, byte _throttle, byte _roll) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingPCMD(this.jniFeature, _flag, _throttle, _roll));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMD(byte _flag, byte _throttle, byte _roll) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMD(this.jniFeature, _flag, _throttle, _roll));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDFlag(byte _flag) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDFlag(this.jniFeature, _flag));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDThrottle(byte _throttle) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDThrottle(this.jniFeature, _throttle));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDRoll(byte _roll) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDRoll(this.jniFeature, _roll));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingUserTakeOff(byte _state) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingUserTakeOff(this.jniFeature, _state));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingMotorMode(ARCOMMANDS_POWERUP_PILOTING_MOTORMODE_MODE_ENUM _mode) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingMotorMode(this.jniFeature, _mode.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsSet(ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM _setting, float _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsSet(this.jniFeature, _setting.getValue(), _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaRecordPictureV2() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaRecordPictureV2(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaRecordVideoV2(ARCOMMANDS_POWERUP_MEDIARECORD_VIDEOV2_RECORD_ENUM _record) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaRecordVideoV2(this.jniFeature, _record.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendNetworkSettingsWifiSelection(ARCOMMANDS_POWERUP_NETWORKSETTINGS_WIFISELECTION_TYPE_ENUM _type, ARCOMMANDS_POWERUP_NETWORKSETTINGS_WIFISELECTION_BAND_ENUM _band, byte _channel) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendNetworkSettingsWifiSelection(this.jniFeature, _type.getValue(), _band.getValue(), _channel));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendNetworkWifiScan(ARCOMMANDS_POWERUP_NETWORK_WIFISCAN_BAND_ENUM _band) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendNetworkWifiScan(this.jniFeature, _band.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendNetworkWifiAuthChannel() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendNetworkWifiAuthChannel(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaStreamingVideoEnable(byte _enable) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaStreamingVideoEnable(this.jniFeature, _enable));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendVideoSettingsAutorecord(byte _enable) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendVideoSettingsAutorecord(this.jniFeature, _enable));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendVideoSettingsVideoMode(ARCOMMANDS_POWERUP_VIDEOSETTINGS_VIDEOMODE_MODE_ENUM _mode) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendVideoSettingsVideoMode(this.jniFeature, _mode.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSoundsBuzz(byte _enable) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSoundsBuzz(this.jniFeature, _enable));
            }
        }
        return error;
    }
}
