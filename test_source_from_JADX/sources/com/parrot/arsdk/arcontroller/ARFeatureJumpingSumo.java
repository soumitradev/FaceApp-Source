package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_JUMP_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_MEDIARECORD_VIDEOV2_RECORD_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_MEDIARECORD_VIDEO_RECORD_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGS_WIFISELECTION_BAND_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGS_WIFISELECTION_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_NETWORK_WIFISCAN_BAND_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM;

public class ARFeatureJumpingSumo {
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE */
    public static String f1516x4ec24b37;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ANIMATIONSSTATE_JUMPMOTORPROBLEMCHANGED_ERROR */
    public static String f1517x75a3648a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ANIMATIONSSTATE_JUMPTYPECHANGED_STATE */
    public static String f1518xc97ce963;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_AUDIOSETTINGSSTATE_MASTERVOLUMECHANGED_VOLUME */
    public static String f1519xae30a46a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_AUDIOSETTINGSSTATE_THEMECHANGED_THEME */
    public static String f1520x5ec4b07e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR */
    public static String f1521x68007fc5;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT */
    public static String f1522x68022057;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR */
    public static String f1523xceb1e702;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT */
    public static String f1524xceb38794;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR */
    public static String f1525xab2ac901;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE */
    public static String f1526xabf0bb8a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGED_MASS_STORAGE_ID */
    public static String f1527x303573e7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGED_STATE */
    public static String f1528xa79384ee;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR */
    public static String f1529xdc4146be;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE */
    public static String f1530xdd073947;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGED_MASS_STORAGE_ID */
    public static String f1531xcc04e4a4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGED_STATE */
    public static String f1532xeb84deb;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED */
    public static String f1533xcfc07902;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_BAND */
    public static String f1534xe7626036;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_CHANNEL */
    public static String f1535xb140db42;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE */
    public static String f1536xe76ae93b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSTATE_LINKQUALITYCHANGED_QUALITY */
    public static String f1537xd6b4bed;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND */
    public static String f1538x1ff44f66;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_CHANNEL */
    public static String f1539xcfca5e12;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_IN_OR_OUT */
    public static String f1540x2480d89b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSTATE_WIFISCANLISTCHANGED_BAND */
    public static String f1541x80f27b2e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSTATE_WIFISCANLISTCHANGED_CHANNEL */
    public static String f1542xf9f33d4a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSTATE_WIFISCANLISTCHANGED_RSSI */
    public static String f1543x80fa0550;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_NETWORKSTATE_WIFISCANLISTCHANGED_SSID */
    public static String f1544x80fa7874;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_PILOTINGSTATE_ALERTSTATECHANGED_STATE */
    public static String f1545xeb00069b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_PILOTINGSTATE_POSTURECHANGED_STATE */
    public static String f1546xfc274174;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_PILOTINGSTATE_SPEEDCHANGED_REALSPEED */
    public static String f1547x18de0cad;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_PILOTINGSTATE_SPEEDCHANGED_SPEED */
    public static String f1548xf2cd5d8b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ROADPLANSTATE_PLAYSCRIPTCHANGED_RESULTCODE */
    public static String f1549x9a9129c9;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ROADPLANSTATE_SCRIPTDELETECHANGED_RESULTCODE */
    public static String f1550xabd6ca00;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ROADPLANSTATE_SCRIPTMETADATALISTCHANGED_LASTMODIFIED */
    public static String f1551x2ce205f7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ROADPLANSTATE_SCRIPTMETADATALISTCHANGED_NAME */
    public static String f1552x8d520923;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ROADPLANSTATE_SCRIPTMETADATALISTCHANGED_PRODUCT */
    public static String f1553x24811b37;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ROADPLANSTATE_SCRIPTMETADATALISTCHANGED_UUID */
    public static String f1554x8d558253;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ROADPLANSTATE_SCRIPTMETADATALISTCHANGED_VERSION */
    public static String f1555x4be86b20;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_ROADPLANSTATE_SCRIPTUPLOADCHANGED_RESULTCODE */
    public static String f1556x175f28d6;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_SETTINGSSTATE_PRODUCTGPSVERSIONCHANGED_HARDWARE */
    public static String f1557xd08d9dc1;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_SETTINGSSTATE_PRODUCTGPSVERSIONCHANGED_SOFTWARE */
    public static String f1558x18391980;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_SPEEDSETTINGSSTATE_OUTDOORCHANGED_OUTDOOR */
    public static String f1559x8581e48f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_JUMPINGSUMO_VIDEOSETTINGSSTATE_AUTORECORDCHANGED_ENABLED */
    public static String f1560xa5f75a08;
    private static String TAG = "ARFeatureJumpingSumo";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendAnimationsJump(long j, int i);

    private native int nativeSendAnimationsJumpCancel(long j);

    private native int nativeSendAnimationsJumpLoad(long j);

    private native int nativeSendAnimationsJumpStop(long j);

    private native int nativeSendAnimationsSimpleAnimation(long j, int i);

    private native int nativeSendAudioSettingsMasterVolume(long j, byte b);

    private native int nativeSendAudioSettingsTheme(long j, int i);

    private native int nativeSendMediaRecordPicture(long j, byte b);

    private native int nativeSendMediaRecordPictureV2(long j);

    private native int nativeSendMediaRecordVideo(long j, int i, byte b);

    private native int nativeSendMediaRecordVideoV2(long j, int i);

    private native int nativeSendMediaStreamingVideoEnable(long j, byte b);

    private native int nativeSendNetworkSettingsWifiSelection(long j, int i, int i2, byte b);

    private native int nativeSendNetworkWifiAuthChannel(long j);

    private native int nativeSendNetworkWifiScan(long j, int i);

    private native int nativeSendPilotingAddCapOffset(long j, float f);

    private native int nativeSendPilotingPCMD(long j, byte b, byte b2, byte b3);

    private native int nativeSendPilotingPosture(long j, int i);

    private native int nativeSendRoadPlanAllScriptsMetadata(long j);

    private native int nativeSendRoadPlanPlayScript(long j, String str);

    private native int nativeSendRoadPlanScriptDelete(long j, String str);

    private native int nativeSendRoadPlanScriptUploaded(long j, String str, String str2);

    private native int nativeSendSpeedSettingsOutdoor(long j, byte b);

    private native int nativeSendVideoSettingsAutorecord(long j, byte b);

    private native int nativeSetPilotingPCMD(long j, byte b, byte b2, byte b3);

    private native int nativeSetPilotingPCMDFlag(long j, byte b);

    private native int nativeSetPilotingPCMDSpeed(long j, byte b);

    private native int nativeSetPilotingPCMDTurn(long j, byte b);

    private static native String nativeStaticGetKeyJumpingSumoAnimationsStateJumpLoadChangedState();

    /* renamed from: nativeStaticGetKeyJumpingSumoAnimationsStateJumpMotorProblemChangedError */
    private static native String m392x7a2cede4();

    private static native String nativeStaticGetKeyJumpingSumoAnimationsStateJumpTypeChangedState();

    /* renamed from: nativeStaticGetKeyJumpingSumoAudioSettingsStateMasterVolumeChangedVolume */
    private static native String m393x9fcc6df0();

    private static native String nativeStaticGetKeyJumpingSumoAudioSettingsStateThemeChangedTheme();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordEventPictureEventChangedError */
    private static native String m394x5287985f();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordEventPictureEventChangedEvent */
    private static native String m395x528938f1();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordEventVideoEventChangedError */
    private static native String m396x34f87242();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordEventVideoEventChangedEvent */
    private static native String m397x34fa12d4();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordStatePictureStateChangedMassstorageid */
    private static native String m398x5ac90e27();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordStatePictureStateChangedState */
    private static native String m399xab473f56();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordStatePictureStateChangedV2Error */
    private static native String m400x22fc5e71();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordStatePictureStateChangedV2State */
    private static native String m401x23c250fa();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordStateVideoStateChangedMassstorageid */
    private static native String m402xcf5266ca();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordStateVideoStateChangedState */
    private static native String m403x6318eaf9();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordStateVideoStateChangedV2Error */
    private static native String m404x2d11ad54();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaRecordStateVideoStateChangedV2State */
    private static native String m405x2dd79fdd();

    /* renamed from: nativeStaticGetKeyJumpingSumoMediaStreamingStateVideoEnableChangedEnabled */
    private static native String m406xf1ba1642();

    /* renamed from: nativeStaticGetKeyJumpingSumoNetworkSettingsStateWifiSelectionChangedBand */
    private static native String m407xe9a9293c();

    /* renamed from: nativeStaticGetKeyJumpingSumoNetworkSettingsStateWifiSelectionChangedChannel */
    private static native String m408x9c9ca89c();

    /* renamed from: nativeStaticGetKeyJumpingSumoNetworkSettingsStateWifiSelectionChangedType */
    private static native String m409xe9b1b241();

    /* renamed from: nativeStaticGetKeyJumpingSumoNetworkStateLinkQualityChangedQuality */
    private static native String m410x8c1a7e43();

    /* renamed from: nativeStaticGetKeyJumpingSumoNetworkStateWifiAuthChannelListChangedBand */
    private static native String m411xcf5d1312();

    /* renamed from: nativeStaticGetKeyJumpingSumoNetworkStateWifiAuthChannelListChangedChannel */
    private static native String m412x60556706();

    /* renamed from: nativeStaticGetKeyJumpingSumoNetworkStateWifiAuthChannelListChangedInorout */
    private static native String m413xa8befe89();

    private static native String nativeStaticGetKeyJumpingSumoNetworkStateWifiScanListChangedBand();

    /* renamed from: nativeStaticGetKeyJumpingSumoNetworkStateWifiScanListChangedChannel */
    private static native String m414x22dd58c();

    private static native String nativeStaticGetKeyJumpingSumoNetworkStateWifiScanListChangedRssi();

    private static native String nativeStaticGetKeyJumpingSumoNetworkStateWifiScanListChangedSsid();

    private static native String nativeStaticGetKeyJumpingSumoPilotingStateAlertStateChangedState();

    private static native String nativeStaticGetKeyJumpingSumoPilotingStatePostureChangedState();

    private static native String nativeStaticGetKeyJumpingSumoPilotingStateSpeedChangedRealSpeed();

    private static native String nativeStaticGetKeyJumpingSumoPilotingStateSpeedChangedSpeed();

    /* renamed from: nativeStaticGetKeyJumpingSumoRoadPlanStatePlayScriptChangedResultCode */
    private static native String m415xb8508441();

    /* renamed from: nativeStaticGetKeyJumpingSumoRoadPlanStateScriptDeleteChangedResultCode */
    private static native String m416x9ee48b6a();

    /* renamed from: nativeStaticGetKeyJumpingSumoRoadPlanStateScriptMetadataListChangedLastModified */
    private static native String m417x42a8c7fd();

    /* renamed from: nativeStaticGetKeyJumpingSumoRoadPlanStateScriptMetadataListChangedName */
    private static native String m418xb5ab9d09();

    /* renamed from: nativeStaticGetKeyJumpingSumoRoadPlanStateScriptMetadataListChangedProduct */
    private static native String m419xb4c3b1b1();

    /* renamed from: nativeStaticGetKeyJumpingSumoRoadPlanStateScriptMetadataListChangedUuid */
    private static native String m420xb5af1639();

    /* renamed from: nativeStaticGetKeyJumpingSumoRoadPlanStateScriptMetadataListChangedVersion */
    private static native String m421xdc2b019a();

    /* renamed from: nativeStaticGetKeyJumpingSumoRoadPlanStateScriptUploadChangedResultCode */
    private static native String m422x5809f9d4();

    /* renamed from: nativeStaticGetKeyJumpingSumoSettingsStateProductGPSVersionChangedHardware */
    private static native String m423x5a7f0583();

    /* renamed from: nativeStaticGetKeyJumpingSumoSettingsStateProductGPSVersionChangedSoftware */
    private static native String m424xa22a8142();

    /* renamed from: nativeStaticGetKeyJumpingSumoSpeedSettingsStateOutdoorChangedOutdoor */
    private static native String m425xd307b2b3();

    /* renamed from: nativeStaticGetKeyJumpingSumoVideoSettingsStateAutorecordChangedEnabled */
    private static native String m426xf34961ca();

    static {
        f1546xfc274174 = "";
        f1545xeb00069b = "";
        f1548xf2cd5d8b = "";
        f1547x18de0cad = "";
        f1516x4ec24b37 = "";
        f1518xc97ce963 = "";
        f1517x75a3648a = "";
        f1558x18391980 = "";
        f1557xd08d9dc1 = "";
        f1528xa79384ee = "";
        f1527x303573e7 = "";
        f1532xeb84deb = "";
        f1531xcc04e4a4 = "";
        f1526xabf0bb8a = "";
        f1525xab2ac901 = "";
        f1530xdd073947 = "";
        f1529xdc4146be = "";
        f1522x68022057 = "";
        f1521x68007fc5 = "";
        f1524xceb38794 = "";
        f1523xceb1e702 = "";
        f1536xe76ae93b = "";
        f1534xe7626036 = "";
        f1535xb140db42 = "";
        f1544x80fa7874 = "";
        f1543x80fa0550 = "";
        f1541x80f27b2e = "";
        f1542xf9f33d4a = "";
        f1538x1ff44f66 = "";
        f1539xcfca5e12 = "";
        f1540x2480d89b = "";
        f1537xd6b4bed = "";
        f1519xae30a46a = "";
        f1520x5ec4b07e = "";
        f1554x8d558253 = "";
        f1555x4be86b20 = "";
        f1553x24811b37 = "";
        f1552x8d520923 = "";
        f1551x2ce205f7 = "";
        f1556x175f28d6 = "";
        f1550xabd6ca00 = "";
        f1549x9a9129c9 = "";
        f1559x8581e48f = "";
        f1533xcfc07902 = "";
        f1560xa5f75a08 = "";
        f1546xfc274174 = nativeStaticGetKeyJumpingSumoPilotingStatePostureChangedState();
        f1545xeb00069b = nativeStaticGetKeyJumpingSumoPilotingStateAlertStateChangedState();
        f1548xf2cd5d8b = nativeStaticGetKeyJumpingSumoPilotingStateSpeedChangedSpeed();
        f1547x18de0cad = nativeStaticGetKeyJumpingSumoPilotingStateSpeedChangedRealSpeed();
        f1516x4ec24b37 = nativeStaticGetKeyJumpingSumoAnimationsStateJumpLoadChangedState();
        f1518xc97ce963 = nativeStaticGetKeyJumpingSumoAnimationsStateJumpTypeChangedState();
        f1517x75a3648a = m392x7a2cede4();
        f1558x18391980 = m424xa22a8142();
        f1557xd08d9dc1 = m423x5a7f0583();
        f1528xa79384ee = m399xab473f56();
        f1527x303573e7 = m398x5ac90e27();
        f1532xeb84deb = m403x6318eaf9();
        f1531xcc04e4a4 = m402xcf5266ca();
        f1526xabf0bb8a = m401x23c250fa();
        f1525xab2ac901 = m400x22fc5e71();
        f1530xdd073947 = m405x2dd79fdd();
        f1529xdc4146be = m404x2d11ad54();
        f1522x68022057 = m395x528938f1();
        f1521x68007fc5 = m394x5287985f();
        f1524xceb38794 = m397x34fa12d4();
        f1523xceb1e702 = m396x34f87242();
        f1536xe76ae93b = m409xe9b1b241();
        f1534xe7626036 = m407xe9a9293c();
        f1535xb140db42 = m408x9c9ca89c();
        f1544x80fa7874 = nativeStaticGetKeyJumpingSumoNetworkStateWifiScanListChangedSsid();
        f1543x80fa0550 = nativeStaticGetKeyJumpingSumoNetworkStateWifiScanListChangedRssi();
        f1541x80f27b2e = nativeStaticGetKeyJumpingSumoNetworkStateWifiScanListChangedBand();
        f1542xf9f33d4a = m414x22dd58c();
        f1538x1ff44f66 = m411xcf5d1312();
        f1539xcfca5e12 = m412x60556706();
        f1540x2480d89b = m413xa8befe89();
        f1537xd6b4bed = m410x8c1a7e43();
        f1519xae30a46a = m393x9fcc6df0();
        f1520x5ec4b07e = nativeStaticGetKeyJumpingSumoAudioSettingsStateThemeChangedTheme();
        f1554x8d558253 = m420xb5af1639();
        f1555x4be86b20 = m421xdc2b019a();
        f1553x24811b37 = m419xb4c3b1b1();
        f1552x8d520923 = m418xb5ab9d09();
        f1551x2ce205f7 = m417x42a8c7fd();
        f1556x175f28d6 = m422x5809f9d4();
        f1550xabd6ca00 = m416x9ee48b6a();
        f1549x9a9129c9 = m415xb8508441();
        f1559x8581e48f = m425xd307b2b3();
        f1533xcfc07902 = m406xf1ba1642();
        f1560xa5f75a08 = m426xf34961ca();
    }

    public ARFeatureJumpingSumo(long nativeFeature) {
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

    public ARCONTROLLER_ERROR_ENUM sendPilotingPCMD(byte _flag, byte _speed, byte _turn) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingPCMD(this.jniFeature, _flag, _speed, _turn));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMD(byte _flag, byte _speed, byte _turn) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMD(this.jniFeature, _flag, _speed, _turn));
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

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDSpeed(byte _speed) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDSpeed(this.jniFeature, _speed));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDTurn(byte _turn) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDTurn(this.jniFeature, _turn));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingPosture(ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM _type) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingPosture(this.jniFeature, _type.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingAddCapOffset(float _offset) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingAddCapOffset(this.jniFeature, _offset));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsJumpStop() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsJumpStop(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsJumpCancel() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsJumpCancel(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsJumpLoad() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsJumpLoad(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsJump(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_JUMP_TYPE_ENUM _type) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsJump(this.jniFeature, _type.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsSimpleAnimation(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM _id) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsSimpleAnimation(this.jniFeature, _id.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaRecordPicture(byte _mass_storage_id) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaRecordPicture(this.jniFeature, _mass_storage_id));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaRecordVideo(ARCOMMANDS_JUMPINGSUMO_MEDIARECORD_VIDEO_RECORD_ENUM _record, byte _mass_storage_id) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaRecordVideo(this.jniFeature, _record.getValue(), _mass_storage_id));
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

    public ARCONTROLLER_ERROR_ENUM sendMediaRecordVideoV2(ARCOMMANDS_JUMPINGSUMO_MEDIARECORD_VIDEOV2_RECORD_ENUM _record) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaRecordVideoV2(this.jniFeature, _record.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendNetworkSettingsWifiSelection(ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGS_WIFISELECTION_TYPE_ENUM _type, ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGS_WIFISELECTION_BAND_ENUM _band, byte _channel) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendNetworkSettingsWifiSelection(this.jniFeature, _type.getValue(), _band.getValue(), _channel));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendNetworkWifiScan(ARCOMMANDS_JUMPINGSUMO_NETWORK_WIFISCAN_BAND_ENUM _band) {
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

    public ARCONTROLLER_ERROR_ENUM sendAudioSettingsMasterVolume(byte _volume) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAudioSettingsMasterVolume(this.jniFeature, _volume));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAudioSettingsTheme(ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM _theme) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAudioSettingsTheme(this.jniFeature, _theme.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendRoadPlanAllScriptsMetadata() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendRoadPlanAllScriptsMetadata(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendRoadPlanScriptUploaded(String _uuid, String _md5Hash) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendRoadPlanScriptUploaded(this.jniFeature, _uuid, _md5Hash));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendRoadPlanScriptDelete(String _uuid) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendRoadPlanScriptDelete(this.jniFeature, _uuid));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendRoadPlanPlayScript(String _uuid) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendRoadPlanPlayScript(this.jniFeature, _uuid));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsOutdoor(byte _outdoor) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsOutdoor(this.jniFeature, _outdoor));
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

    public ARCONTROLLER_ERROR_ENUM sendVideoSettingsAutorecord(byte _enabled) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendVideoSettingsAutorecord(this.jniFeature, _enabled));
            }
        }
        return error;
    }
}
