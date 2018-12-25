package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_PILOTING_PLANEGEARBOX_STATE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_USBACCESSORY_CLAWCONTROL_ACTION_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_USBACCESSORY_GUNCONTROL_ACTION_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_USBACCESSORY_LIGHTCONTROL_MODE_ENUM;

public class ARFeatureMiniDrone {
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_FLOODCONTROLSTATE_FLOODCONTROLCHANGED_DELAY */
    public static String f1562x8aefe8b7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR */
    public static String f1563x99c8833c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT */
    public static String f1564x99ca23ce;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR */
    public static String f1565x8affcab8;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE */
    public static String f1566x8bc5bd41;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGED_MASS_STORAGE_ID */
    public static String f1567xac64ce9e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGED_STATE */
    public static String f1568xd95b8865;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_NAVIGATIONDATASTATE_DRONEPOSITION_POSX */
    public static String f1569x9d522ba3;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_NAVIGATIONDATASTATE_DRONEPOSITION_POSY */
    public static String f1570x9d522ba4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_NAVIGATIONDATASTATE_DRONEPOSITION_POSZ */
    public static String f1571x9d522ba5;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_NAVIGATIONDATASTATE_DRONEPOSITION_PSI */
    public static String f1572x4f65bfc7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_NAVIGATIONDATASTATE_DRONEPOSITION_TS */
    public static String f1573xd945593e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSETTINGSSTATE_BANKEDTURNCHANGED_STATE */
    public static String f1574xc797053e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSETTINGSSTATE_MAXALTITUDECHANGED_CURRENT */
    public static String f1575xa2d765f8;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSETTINGSSTATE_MAXALTITUDECHANGED_MAX */
    public static String f1576xf36e6d63;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSETTINGSSTATE_MAXALTITUDECHANGED_MIN */
    public static String f1577xf36e6e51;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSETTINGSSTATE_MAXTILTCHANGED_CURRENT */
    public static String f1578x811fb27d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSETTINGSSTATE_MAXTILTCHANGED_MAX */
    public static String f1579xd1f3c468;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSETTINGSSTATE_MAXTILTCHANGED_MIN */
    public static String f1580xd1f3c556;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSTATE_ALERTSTATECHANGED_STATE */
    public static String f1581xb22374c4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSTATE_AUTOTAKEOFFMODECHANGED_STATE */
    public static String f1582x7c0a1417;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSTATE_FLYINGMODECHANGED_MODE */
    public static String f1583x7772176d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE */
    public static String f1584xbbb1d511;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_PILOTINGSTATE_PLANEGEARBOXCHANGED_STATE */
    public static String f1585x8dedca99;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SETTINGSSTATE_CUTOUTMODECHANGED_ENABLE */
    public static String f1586xff594703;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SETTINGSSTATE_PRODUCTINERTIALVERSIONCHANGED_HARDWARE */
    public static String f1587x1aa323d0;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SETTINGSSTATE_PRODUCTINERTIALVERSIONCHANGED_SOFTWARE */
    public static String f1588x624e9f8f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SETTINGSSTATE_PRODUCTMOTORSVERSIONCHANGED_HARDWARE */
    public static String f1589x631f1e64;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SETTINGSSTATE_PRODUCTMOTORSVERSIONCHANGED_MOTOR */
    public static String f1590xf0ee2959;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SETTINGSSTATE_PRODUCTMOTORSVERSIONCHANGED_SOFTWARE */
    public static String f1591xaaca9a23;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SETTINGSSTATE_PRODUCTMOTORSVERSIONCHANGED_TYPE */
    public static String f1592xc5b86db6;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXHORIZONTALSPEEDCHANGED_CURRENT */
    public static String f1593x3a51d3cc;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXHORIZONTALSPEEDCHANGED_MAX */
    public static String f1594xb350e537;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXHORIZONTALSPEEDCHANGED_MIN */
    public static String f1595xb350e625;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXPLANEMODEROTATIONSPEEDCHANGED_CURRENT */
    public static String f1596x6d38ac63;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXPLANEMODEROTATIONSPEEDCHANGED_MAX */
    public static String f1597xbbb1a14e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXPLANEMODEROTATIONSPEEDCHANGED_MIN */
    public static String f1598xbbb1a23c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXROTATIONSPEEDCHANGED_CURRENT */
    public static String f1599x9b666;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXROTATIONSPEEDCHANGED_MAX */
    public static String f1600xfb4624d1;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXROTATIONSPEEDCHANGED_MIN */
    public static String f1601xfb4625bf;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXVERTICALSPEEDCHANGED_CURRENT */
    public static String f1602x4c49625e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXVERTICALSPEEDCHANGED_MAX */
    public static String f1603xc06a8cc9;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_MAXVERTICALSPEEDCHANGED_MIN */
    public static String f1604xc06a8db7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_SPEEDSETTINGSSTATE_WHEELSCHANGED_PRESENT */
    public static String f1605x89301b5;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_USBACCESSORYSTATE_CLAWSTATE_ID */
    public static String f1606xbfb014f9;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_USBACCESSORYSTATE_CLAWSTATE_STATE */
    public static String f1607xec6dcbd3;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_ID */
    public static String f1608x71ec28b8;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE */
    public static String f1609x49ababb4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_USBACCESSORYSTATE_LIGHTSTATE_ID */
    public static String f1610xa8bcdace;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_USBACCESSORYSTATE_LIGHTSTATE_INTENSITY */
    public static String f1611x87b39700;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MINIDRONE_USBACCESSORYSTATE_LIGHTSTATE_STATE */
    public static String f1612x31cfb9de;
    private static String TAG = "ARFeatureMiniDrone";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendAnimationsCap(long j, short s);

    private native int nativeSendAnimationsFlip(long j, int i);

    private native int nativeSendConfigurationControllerName(long j, String str);

    private native int nativeSendConfigurationControllerType(long j, String str);

    private native int nativeSendGPSControllerLatitudeForRun(long j, double d);

    private native int nativeSendGPSControllerLongitudeForRun(long j, double d);

    private native int nativeSendMediaRecordPicture(long j, byte b);

    private native int nativeSendMediaRecordPictureV2(long j);

    private native int nativeSendPilotingAutoTakeOffMode(long j, byte b);

    private native int nativeSendPilotingEmergency(long j);

    private native int nativeSendPilotingFlatTrim(long j);

    private native int nativeSendPilotingFlyingMode(long j, int i);

    private native int nativeSendPilotingLanding(long j);

    private native int nativeSendPilotingPCMD(long j, byte b, byte b2, byte b3, byte b4, byte b5, int i);

    private native int nativeSendPilotingPlaneGearBox(long j, int i);

    private native int nativeSendPilotingSettingsBankedTurn(long j, byte b);

    private native int nativeSendPilotingSettingsMaxAltitude(long j, float f);

    private native int nativeSendPilotingSettingsMaxTilt(long j, float f);

    private native int nativeSendPilotingTakeOff(long j);

    private native int nativeSendRemoteControllerSetPairedRemote(long j, short s, short s2, short s3);

    private native int nativeSendSettingsCutOutMode(long j, byte b);

    private native int nativeSendSpeedSettingsMaxHorizontalSpeed(long j, float f);

    private native int nativeSendSpeedSettingsMaxPlaneModeRotationSpeed(long j, float f);

    private native int nativeSendSpeedSettingsMaxRotationSpeed(long j, float f);

    private native int nativeSendSpeedSettingsMaxVerticalSpeed(long j, float f);

    private native int nativeSendSpeedSettingsWheels(long j, byte b);

    private native int nativeSendUsbAccessoryClawControl(long j, byte b, int i);

    private native int nativeSendUsbAccessoryGunControl(long j, byte b, int i);

    private native int nativeSendUsbAccessoryLightControl(long j, byte b, int i, byte b2);

    private native int nativeSetPilotingPCMD(long j, byte b, byte b2, byte b3, byte b4, byte b5, int i);

    private native int nativeSetPilotingPCMDFlag(long j, byte b);

    private native int nativeSetPilotingPCMDGaz(long j, byte b);

    private native int nativeSetPilotingPCMDPitch(long j, byte b);

    private native int nativeSetPilotingPCMDRoll(long j, byte b);

    private native int nativeSetPilotingPCMDTimestamp(long j, int i);

    private native int nativeSetPilotingPCMDYaw(long j, byte b);

    /* renamed from: nativeStaticGetKeyMiniDroneFloodControlStateFloodControlChangedDelay */
    private static native String m427xde71d84d();

    /* renamed from: nativeStaticGetKeyMiniDroneMediaRecordEventPictureEventChangedError */
    private static native String m428x97036648();

    /* renamed from: nativeStaticGetKeyMiniDroneMediaRecordEventPictureEventChangedEvent */
    private static native String m429x970506da();

    /* renamed from: nativeStaticGetKeyMiniDroneMediaRecordStatePictureStateChangedMassstorageid */
    private static native String m430xb370e310();

    /* renamed from: nativeStaticGetKeyMiniDroneMediaRecordStatePictureStateChangedState */
    private static native String m431xefc30d3f();

    /* renamed from: nativeStaticGetKeyMiniDroneMediaRecordStatePictureStateChangedV2Error */
    private static native String m432x37bc561a();

    /* renamed from: nativeStaticGetKeyMiniDroneMediaRecordStatePictureStateChangedV2State */
    private static native String m433x388248a3();

    private static native String nativeStaticGetKeyMiniDroneNavigationDataStateDronePositionPosx();

    private static native String nativeStaticGetKeyMiniDroneNavigationDataStateDronePositionPosy();

    private static native String nativeStaticGetKeyMiniDroneNavigationDataStateDronePositionPosz();

    private static native String nativeStaticGetKeyMiniDroneNavigationDataStateDronePositionPsi();

    private static native String nativeStaticGetKeyMiniDroneNavigationDataStateDronePositionTs();

    /* renamed from: nativeStaticGetKeyMiniDronePilotingSettingsStateBankedTurnChangedState */
    private static native String m434xbaef6ce6();

    /* renamed from: nativeStaticGetKeyMiniDronePilotingSettingsStateMaxAltitudeChangedCurrent */
    private static native String m435x4ff044ba();

    /* renamed from: nativeStaticGetKeyMiniDronePilotingSettingsStateMaxAltitudeChangedMax */
    private static native String m436xbae5f525();

    /* renamed from: nativeStaticGetKeyMiniDronePilotingSettingsStateMaxAltitudeChangedMin */
    private static native String m437xbae5f613();

    /* renamed from: nativeStaticGetKeyMiniDronePilotingSettingsStateMaxTiltChangedCurrent */
    private static native String m438x7952315();

    /* renamed from: nativeStaticGetKeyMiniDronePilotingSettingsStateMaxTiltChangedMax */
    private static native String m439xee563900();

    /* renamed from: nativeStaticGetKeyMiniDronePilotingSettingsStateMaxTiltChangedMin */
    private static native String m440xee5639ee();

    private static native String nativeStaticGetKeyMiniDronePilotingStateAlertStateChangedState();

    /* renamed from: nativeStaticGetKeyMiniDronePilotingStateAutoTakeOffModeChangedState */
    private static native String m441x2ccad025();

    private static native String nativeStaticGetKeyMiniDronePilotingStateFlyingModeChangedMode();

    private static native String nativeStaticGetKeyMiniDronePilotingStateFlyingStateChangedState();

    private static native String nativeStaticGetKeyMiniDronePilotingStatePlaneGearBoxChangedState();

    private static native String nativeStaticGetKeyMiniDroneSettingsStateCutOutModeChangedEnable();

    /* renamed from: nativeStaticGetKeyMiniDroneSettingsStateProductInertialVersionChangedHardware */
    private static native String m442xec6a50aa();

    /* renamed from: nativeStaticGetKeyMiniDroneSettingsStateProductInertialVersionChangedSoftware */
    private static native String m443x3415cc69();

    /* renamed from: nativeStaticGetKeyMiniDroneSettingsStateProductMotorsVersionChangedHardware */
    private static native String m444xbe444656();

    /* renamed from: nativeStaticGetKeyMiniDroneSettingsStateProductMotorsVersionChangedMotor */
    private static native String m445x13f35ec7();

    /* renamed from: nativeStaticGetKeyMiniDroneSettingsStateProductMotorsVersionChangedSoftware */
    private static native String m446x5efc215();

    /* renamed from: nativeStaticGetKeyMiniDroneSettingsStateProductMotorsVersionChangedType */
    private static native String m447x21b056a8();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxHorizontalSpeedChangedCurrent */
    private static native String m448xbc6f68de();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxHorizontalSpeedChangedMax */
    private static native String m449x7cc4cb49();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxHorizontalSpeedChangedMin */
    private static native String m450x7cc4cc37();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxPlaneModeRotationSpeedChangedCurrent */
    private static native String m451x7ba0a239();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxPlaneModeRotationSpeedChangedMax */
    private static native String m452xa26ea24();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxPlaneModeRotationSpeedChangedMin */
    private static native String m453xa26eb12();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxRotationSpeedChangedCurrent */
    private static native String m454x757940c4();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxRotationSpeedChangedMax */
    private static native String m455x9a92862f();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxRotationSpeedChangedMin */
    private static native String m456x9a92871d();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxVerticalSpeedChangedCurrent */
    private static native String m457x1d1835cc();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxVerticalSpeedChangedMax */
    private static native String m458xfbc53f37();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateMaxVerticalSpeedChangedMin */
    private static native String m459xfbc54025();

    /* renamed from: nativeStaticGetKeyMiniDroneSpeedSettingsStateWheelsChangedPresent */
    private static native String m460x4f668b59();

    private static native String nativeStaticGetKeyMiniDroneUsbAccessoryStateClawStateId();

    private static native String nativeStaticGetKeyMiniDroneUsbAccessoryStateClawStateListflags();

    private static native String nativeStaticGetKeyMiniDroneUsbAccessoryStateClawStateState();

    private static native String nativeStaticGetKeyMiniDroneUsbAccessoryStateGunStateId();

    private static native String nativeStaticGetKeyMiniDroneUsbAccessoryStateGunStateListflags();

    private static native String nativeStaticGetKeyMiniDroneUsbAccessoryStateGunStateState();

    private static native String nativeStaticGetKeyMiniDroneUsbAccessoryStateLightStateId();

    private static native String nativeStaticGetKeyMiniDroneUsbAccessoryStateLightStateIntensity();

    private static native String nativeStaticGetKeyMiniDroneUsbAccessoryStateLightStateListflags();

    private static native String nativeStaticGetKeyMiniDroneUsbAccessoryStateLightStateState();

    static {
        f1584xbbb1d511 = "";
        f1581xb22374c4 = "";
        f1582x7c0a1417 = "";
        f1583x7772176d = "";
        f1585x8dedca99 = "";
        f1568xd95b8865 = "";
        f1567xac64ce9e = "";
        f1566x8bc5bd41 = "";
        f1565x8affcab8 = "";
        f1564x99ca23ce = "";
        f1563x99c8833c = "";
        f1575xa2d765f8 = "";
        f1577xf36e6e51 = "";
        f1576xf36e6d63 = "";
        f1578x811fb27d = "";
        f1580xd1f3c556 = "";
        f1579xd1f3c468 = "";
        f1574xc797053e = "";
        f1602x4c49625e = "";
        f1604xc06a8db7 = "";
        f1603xc06a8cc9 = "";
        f1599x9b666 = "";
        f1601xfb4625bf = "";
        f1600xfb4624d1 = "";
        f1605x89301b5 = "";
        f1593x3a51d3cc = "";
        f1595xb350e625 = "";
        f1594xb350e537 = "";
        f1596x6d38ac63 = "";
        f1598xbbb1a23c = "";
        f1597xbbb1a14e = "";
        f1590xf0ee2959 = "";
        f1592xc5b86db6 = "";
        f1591xaaca9a23 = "";
        f1589x631f1e64 = "";
        f1588x624e9f8f = "";
        f1587x1aa323d0 = "";
        f1586xff594703 = "";
        f1562x8aefe8b7 = "";
        f1610xa8bcdace = "";
        f1612x31cfb9de = "";
        f1611x87b39700 = "";
        f1606xbfb014f9 = "";
        f1607xec6dcbd3 = "";
        f1608x71ec28b8 = "";
        f1609x49ababb4 = "";
        f1569x9d522ba3 = "";
        f1570x9d522ba4 = "";
        f1571x9d522ba5 = "";
        f1572x4f65bfc7 = "";
        f1573xd945593e = "";
        f1584xbbb1d511 = nativeStaticGetKeyMiniDronePilotingStateFlyingStateChangedState();
        f1581xb22374c4 = nativeStaticGetKeyMiniDronePilotingStateAlertStateChangedState();
        f1582x7c0a1417 = m441x2ccad025();
        f1583x7772176d = nativeStaticGetKeyMiniDronePilotingStateFlyingModeChangedMode();
        f1585x8dedca99 = nativeStaticGetKeyMiniDronePilotingStatePlaneGearBoxChangedState();
        f1568xd95b8865 = m431xefc30d3f();
        f1567xac64ce9e = m430xb370e310();
        f1566x8bc5bd41 = m433x388248a3();
        f1565x8affcab8 = m432x37bc561a();
        f1564x99ca23ce = m429x970506da();
        f1563x99c8833c = m428x97036648();
        f1575xa2d765f8 = m435x4ff044ba();
        f1577xf36e6e51 = m437xbae5f613();
        f1576xf36e6d63 = m436xbae5f525();
        f1578x811fb27d = m438x7952315();
        f1580xd1f3c556 = m440xee5639ee();
        f1579xd1f3c468 = m439xee563900();
        f1574xc797053e = m434xbaef6ce6();
        f1602x4c49625e = m457x1d1835cc();
        f1604xc06a8db7 = m459xfbc54025();
        f1603xc06a8cc9 = m458xfbc53f37();
        f1599x9b666 = m454x757940c4();
        f1601xfb4625bf = m456x9a92871d();
        f1600xfb4624d1 = m455x9a92862f();
        f1605x89301b5 = m460x4f668b59();
        f1593x3a51d3cc = m448xbc6f68de();
        f1595xb350e625 = m450x7cc4cc37();
        f1594xb350e537 = m449x7cc4cb49();
        f1596x6d38ac63 = m451x7ba0a239();
        f1598xbbb1a23c = m453xa26eb12();
        f1597xbbb1a14e = m452xa26ea24();
        f1590xf0ee2959 = m445x13f35ec7();
        f1592xc5b86db6 = m447x21b056a8();
        f1591xaaca9a23 = m446x5efc215();
        f1589x631f1e64 = m444xbe444656();
        f1588x624e9f8f = m443x3415cc69();
        f1587x1aa323d0 = m442xec6a50aa();
        f1586xff594703 = nativeStaticGetKeyMiniDroneSettingsStateCutOutModeChangedEnable();
        f1562x8aefe8b7 = m427xde71d84d();
        f1610xa8bcdace = nativeStaticGetKeyMiniDroneUsbAccessoryStateLightStateId();
        f1612x31cfb9de = nativeStaticGetKeyMiniDroneUsbAccessoryStateLightStateState();
        f1611x87b39700 = nativeStaticGetKeyMiniDroneUsbAccessoryStateLightStateIntensity();
        f1606xbfb014f9 = nativeStaticGetKeyMiniDroneUsbAccessoryStateClawStateId();
        f1607xec6dcbd3 = nativeStaticGetKeyMiniDroneUsbAccessoryStateClawStateState();
        f1608x71ec28b8 = nativeStaticGetKeyMiniDroneUsbAccessoryStateGunStateId();
        f1609x49ababb4 = nativeStaticGetKeyMiniDroneUsbAccessoryStateGunStateState();
        f1569x9d522ba3 = nativeStaticGetKeyMiniDroneNavigationDataStateDronePositionPosx();
        f1570x9d522ba4 = nativeStaticGetKeyMiniDroneNavigationDataStateDronePositionPosy();
        f1571x9d522ba5 = nativeStaticGetKeyMiniDroneNavigationDataStateDronePositionPosz();
        f1572x4f65bfc7 = nativeStaticGetKeyMiniDroneNavigationDataStateDronePositionPsi();
        f1573xd945593e = nativeStaticGetKeyMiniDroneNavigationDataStateDronePositionTs();
    }

    public ARFeatureMiniDrone(long nativeFeature) {
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

    public ARCONTROLLER_ERROR_ENUM sendPilotingFlatTrim() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingFlatTrim(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingTakeOff() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingTakeOff(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingPCMD(byte _flag, byte _roll, byte _pitch, byte _yaw, byte _gaz, int _timestamp) {
        ARFeatureMiniDrone aRFeatureMiniDrone = this;
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            try {
                if (aRFeatureMiniDrone.initOk) {
                    int nativeError = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingPCMD(aRFeatureMiniDrone.jniFeature, _flag, _roll, _pitch, _yaw, _gaz, _timestamp));
                }
            } finally {
                Object obj = r0;
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMD(byte _flag, byte _roll, byte _pitch, byte _yaw, byte _gaz, int _timestamp) {
        ARFeatureMiniDrone aRFeatureMiniDrone = this;
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            try {
                if (aRFeatureMiniDrone.initOk) {
                    int nativeError = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMD(aRFeatureMiniDrone.jniFeature, _flag, _roll, _pitch, _yaw, _gaz, _timestamp));
                }
            } finally {
                Object obj = r0;
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

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDRoll(byte _roll) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDRoll(this.jniFeature, _roll));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDPitch(byte _pitch) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDPitch(this.jniFeature, _pitch));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDYaw(byte _yaw) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDYaw(this.jniFeature, _yaw));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDGaz(byte _gaz) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDGaz(this.jniFeature, _gaz));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDTimestamp(int _timestamp) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDTimestamp(this.jniFeature, _timestamp));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingLanding() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingLanding(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingEmergency() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingEmergency(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingAutoTakeOffMode(byte _state) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingAutoTakeOffMode(this.jniFeature, _state));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingFlyingMode(ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM _mode) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingFlyingMode(this.jniFeature, _mode.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingPlaneGearBox(ARCOMMANDS_MINIDRONE_PILOTING_PLANEGEARBOX_STATE_ENUM _state) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingPlaneGearBox(this.jniFeature, _state.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsFlip(ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM _direction) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsFlip(this.jniFeature, _direction.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsCap(short _offset) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsCap(this.jniFeature, _offset));
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

    public ARCONTROLLER_ERROR_ENUM sendMediaRecordPictureV2() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaRecordPictureV2(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsMaxAltitude(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsMaxAltitude(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsMaxTilt(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsMaxTilt(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsBankedTurn(byte _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsBankedTurn(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsMaxVerticalSpeed(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsMaxVerticalSpeed(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsMaxRotationSpeed(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsMaxRotationSpeed(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsWheels(byte _present) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsWheels(this.jniFeature, _present));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsMaxHorizontalSpeed(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsMaxHorizontalSpeed(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsMaxPlaneModeRotationSpeed(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsMaxPlaneModeRotationSpeed(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSettingsCutOutMode(byte _enable) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSettingsCutOutMode(this.jniFeature, _enable));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendGPSControllerLatitudeForRun(double _latitude) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendGPSControllerLatitudeForRun(this.jniFeature, _latitude));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendGPSControllerLongitudeForRun(double _longitude) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendGPSControllerLongitudeForRun(this.jniFeature, _longitude));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendConfigurationControllerType(String _type) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendConfigurationControllerType(this.jniFeature, _type));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendConfigurationControllerName(String _name) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendConfigurationControllerName(this.jniFeature, _name));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendUsbAccessoryLightControl(byte _id, ARCOMMANDS_MINIDRONE_USBACCESSORY_LIGHTCONTROL_MODE_ENUM _mode, byte _intensity) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendUsbAccessoryLightControl(this.jniFeature, _id, _mode.getValue(), _intensity));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendUsbAccessoryClawControl(byte _id, ARCOMMANDS_MINIDRONE_USBACCESSORY_CLAWCONTROL_ACTION_ENUM _action) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendUsbAccessoryClawControl(this.jniFeature, _id, _action.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendUsbAccessoryGunControl(byte _id, ARCOMMANDS_MINIDRONE_USBACCESSORY_GUNCONTROL_ACTION_ENUM _action) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendUsbAccessoryGunControl(this.jniFeature, _id, _action.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendRemoteControllerSetPairedRemote(short _msb_mac, short _mid_mac, short _lsb_mac) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendRemoteControllerSetPairedRemote(this.jniFeature, _msb_mac, _mid_mac, _lsb_mac));
            }
        }
        return error;
    }
}
