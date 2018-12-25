package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_ANIMATIONS_STOPANIMATION_ANIM_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_CHARGER_SETMAXCHARGERATE_RATE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_MAVLINK_START_TYPE_ENUM;

public class ARFeatureCommon {
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_ERROR */
    public static String f1397x8e41b60c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY */
    public static String f1398xa6ed54ce;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGMODIFICATIONENABLED_ENABLED */
    public static String f1399xa762e236;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_ACCESSORYSTATE_SUPPORTEDACCESSORIESLISTCHANGED_ACCESSORY */
    public static String f1400xe7c25cc4;
    public static String ARCONTROLLER_DICTIONARY_KEY_COMMON_ANIMATIONSSTATE_LIST_ANIM;
    public static String ARCONTROLLER_DICTIONARY_KEY_COMMON_ANIMATIONSSTATE_LIST_ERROR;
    public static String ARCONTROLLER_DICTIONARY_KEY_COMMON_ANIMATIONSSTATE_LIST_STATE;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_ARLIBSVERSIONSSTATE_CONTROLLERLIBARCOMMANDSVERSION_VERSION */
    public static String f1401xfff8d5f4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_ARLIBSVERSIONSSTATE_DEVICELIBARCOMMANDSVERSION_VERSION */
    public static String f1402xee63482e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_ARLIBSVERSIONSSTATE_SKYCONTROLLERLIBARCOMMANDSVERSION_VERSION */
    public static String f1403x3621608b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_AUDIOSTATE_AUDIOSTREAMINGRUNNING_RUNNING */
    public static String f1404x8cc79e0c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CALIBRATIONSTATE_MAGNETOCALIBRATIONAXISTOCALIBRATECHANGED_AXIS */
    public static String f1405x3c2e3c17;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CALIBRATIONSTATE_MAGNETOCALIBRATIONREQUIREDSTATE_REQUIRED */
    public static String f1406x4e63e32c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTARTEDCHANGED_STARTED */
    public static String f1407xc4f96d55;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATECHANGED_CALIBRATIONFAILED */
    public static String f1408x75a7f06b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATECHANGED_XAXISCALIBRATION */
    public static String f1409xc2992dcd;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATECHANGED_YAXISCALIBRATION */
    public static String f1410xa42b0bac;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATECHANGED_ZAXISCALIBRATION */
    public static String f1411x85bce98b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CALIBRATIONSTATE_PITOTCALIBRATIONSTATECHANGED_LASTERROR */
    public static String f1412x1672fe9b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CALIBRATIONSTATE_PITOTCALIBRATIONSTATECHANGED_STATE */
    public static String f1413x5f477a7a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CAMERASETTINGSSTATE_CAMERASETTINGSCHANGED_FOV */
    public static String f1414x6c5e6127;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CAMERASETTINGSSTATE_CAMERASETTINGSCHANGED_PANMAX */
    public static String f1415xb546ced;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CAMERASETTINGSSTATE_CAMERASETTINGSCHANGED_PANMIN */
    public static String f1416xb546ddb;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CAMERASETTINGSSTATE_CAMERASETTINGSCHANGED_TILTMAX */
    public static String f1417x40600ce1;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CAMERASETTINGSSTATE_CAMERASETTINGSCHANGED_TILTMIN */
    public static String f1418x40600dcf;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CHARGERSTATE_CHARGINGINFO_FULLCHARGINGTIME */
    public static String f1419x71332b9e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CHARGERSTATE_CHARGINGINFO_INTENSITY */
    public static String f1420x7dfece62;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE */
    public static String f1421x3e62372a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CHARGERSTATE_CHARGINGINFO_RATE */
    public static String f1422xd8b9a931;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_PHASE */
    public static String f1423x58e39c1d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_STATUS */
    public static String f1424xc9579170;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CHARGERSTATE_LASTCHARGERATECHANGED_RATE */
    public static String f1425x45348dc4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_CHARGERSTATE_MAXCHARGERATECHANGED_RATE */
    public static String f1426xa3da7b34;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_BATTERYSTATECHANGED_PERCENT */
    public static String f1427xd58cdac0;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_COUNTRYLISTKNOWN_COUNTRYCODES */
    public static String f1428x2a3593ea;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_COUNTRYLISTKNOWN_LISTFLAGS */
    public static String f1429xd768d12f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_CURRENTDATECHANGED_DATE */
    public static String f1430x185141ea;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_CURRENTTIMECHANGED_TIME */
    public static String f1431xd34151a8;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_DEPRECATEDMASSSTORAGECONTENTCHANGED_MASS_STORAGE_ID */
    public static String f1432xa162dc44;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_DEPRECATEDMASSSTORAGECONTENTCHANGED_NBCRASHLOGS */
    public static String f1433x8276741c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_DEPRECATEDMASSSTORAGECONTENTCHANGED_NBPHOTOS */
    public static String f1434x7ae61fdb;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_DEPRECATEDMASSSTORAGECONTENTCHANGED_NBPUDS */
    public static String f1435xda91572e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_DEPRECATEDMASSSTORAGECONTENTCHANGED_NBVIDEOS */
    public static String f1436x852c12b2;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGECONTENTFORCURRENTRUN_MASS_STORAGE_ID */
    public static String f1437x695b525e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGECONTENTFORCURRENTRUN_NBPHOTOS */
    public static String f1438xb856f301;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGECONTENTFORCURRENTRUN_NBRAWPHOTOS */
    public static String f1439xbfbc8c09;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGECONTENTFORCURRENTRUN_NBVIDEOS */
    public static String f1440xc29ce5d8;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGECONTENT_MASS_STORAGE_ID */
    public static String f1441x8efedf53;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGECONTENT_NBCRASHLOGS */
    public static String f1442xfdf456ab;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGECONTENT_NBPHOTOS */
    public static String f1443x9a1adfac;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGECONTENT_NBPUDS */
    public static String f1444x3ce5d73f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGECONTENT_NBRAWPHOTOS */
    public static String f1445x4eaedb7e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGECONTENT_NBVIDEOS */
    public static String f1446xa460d283;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGEINFOREMAININGLISTCHANGED_FREE_SPACE */
    public static String f1447xecad18f3;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGEINFOREMAININGLISTCHANGED_PHOTO_REMAINING */
    public static String f1448xfa62a449;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGEINFOREMAININGLISTCHANGED_REC_TIME */
    public static String f1449x3c06a4dc;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGEINFOSTATELISTCHANGED_FULL */
    public static String f1450xa897d34a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGEINFOSTATELISTCHANGED_INTERNAL */
    public static String f1451xf94b99f8;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGEINFOSTATELISTCHANGED_MASS_STORAGE_ID */
    public static String f1452xa11f880f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGEINFOSTATELISTCHANGED_PLUGGED */
    public static String f1453x5e35443d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGEINFOSTATELISTCHANGED_SIZE */
    public static String f1454xa89d90bc;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGEINFOSTATELISTCHANGED_USED_SIZE */
    public static String f1455x1049ff68;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGESTATELISTCHANGED_MASS_STORAGE_ID */
    public static String f1456xd5f273e1;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_MASSSTORAGESTATELISTCHANGED_NAME */
    public static String f1457x46256eb4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL */
    public static String f1458xeb109a7a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_SENSORSSTATESLISTCHANGED_SENSORNAME */
    public static String f1459x16e55c33;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_SENSORSSTATESLISTCHANGED_SENSORSTATE */
    public static String f1460xc61517c9;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_VIDEORECORDINGTIMESTAMP_STARTTIMESTAMP */
    public static String f1461x20137c89;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_VIDEORECORDINGTIMESTAMP_STOPTIMESTAMP */
    public static String f1462xe7d92eff;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_WIFISIGNALCHANGED_RSSI */
    public static String f1463x6024c2f5;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_FLIGHTPLANSETTINGSSTATE_RETURNHOMEONDISCONNECTCHANGED_ISREADONLY */
    public static String f1464x1510f428;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_FLIGHTPLANSETTINGSSTATE_RETURNHOMEONDISCONNECTCHANGED_STATE */
    public static String f1465xdbf38035;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_FLIGHTPLANSTATE_AVAILABILITYSTATECHANGED_AVAILABILITYSTATE */
    public static String f1466x4f9e1159;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_COMPONENT */
    public static String f1467xc00e81dc;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_STATE */
    public static String f1468x1ad878f0;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_FLIGHTPLANSTATE_LOCKSTATECHANGED_LOCKSTATE */
    public static String f1469x98518e59;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_HEADLIGHTSSTATE_INTENSITYCHANGED_LEFT */
    public static String f1470x91aa077d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_HEADLIGHTSSTATE_INTENSITYCHANGED_RIGHT */
    public static String f1471xa3ed4946;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_FILEPATH */
    public static String f1472xd6a8205a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_STATE */
    public static String f1473xfe11ba58;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_TYPE */
    public static String f1474x9495c073;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_MAVLINKSTATE_MAVLINKPLAYERRORSTATECHANGED_ERROR */
    public static String f1475xceb49fb1;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_MAVLINKSTATE_MISSIONITEMEXECUTED_IDX */
    public static String f1476x58ae587f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_NETWORKEVENT_DISCONNECTION_CAUSE */
    public static String f1477xa1ec5620;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_OVERHEATSTATE_OVERHEATREGULATIONCHANGED_REGULATIONTYPE */
    public static String f1478xf9ae1f8c;
    public static String ARCONTROLLER_DICTIONARY_KEY_COMMON_RUNSTATE_RUNIDCHANGED_RUNID;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_AUTOCOUNTRYCHANGED_AUTOMATIC */
    public static String f1479xb14b87e7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_COUNTRYCHANGED_CODE */
    public static String f1480xe84719a0;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTNAMECHANGED_NAME */
    public static String f1481x7683cfa2;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTSERIALHIGHCHANGED_HIGH */
    public static String f1482x2c124084;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTSERIALLOWCHANGED_LOW */
    public static String f1483xfae5f60a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTVERSIONCHANGED_HARDWARE */
    public static String f1484x2d1ec08a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTVERSIONCHANGED_SOFTWARE */
    public static String f1485x74ca3c49;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_COMMON_WIFISETTINGSSTATE_OUTDOORSETTINGSCHANGED_OUTDOOR */
    public static String f1486xf361b2b5;
    private static String TAG = "ARFeatureCommon";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendAccessoryConfig(long j, int i);

    private native int nativeSendAnimationsStartAnimation(long j, int i);

    private native int nativeSendAnimationsStopAllAnimations(long j);

    private native int nativeSendAnimationsStopAnimation(long j, int i);

    private native int nativeSendAudioControllerReadyForStreaming(long j, byte b);

    private native int nativeSendCalibrationMagnetoCalibration(long j, byte b);

    private native int nativeSendCalibrationPitotCalibration(long j, byte b);

    private native int nativeSendChargerSetMaxChargeRate(long j, int i);

    private native int nativeSendCommonAllStates(long j);

    private native int nativeSendCommonCurrentDate(long j, String str);

    private native int nativeSendCommonCurrentTime(long j, String str);

    private native int nativeSendCommonReboot(long j);

    private native int nativeSendControllerIsPiloting(long j, byte b);

    private native int nativeSendFactoryReset(long j);

    private native int nativeSendFlightPlanSettingsReturnHomeOnDisconnect(long j, byte b);

    private native int nativeSendGPSControllerPositionForRun(long j, double d, double d2);

    private native int nativeSendHeadlightsIntensity(long j, byte b, byte b2);

    private native int nativeSendMavlinkPause(long j);

    private native int nativeSendMavlinkStart(long j, String str, int i);

    private native int nativeSendMavlinkStop(long j);

    private native int nativeSendNetworkDisconnect(long j);

    private native int nativeSendOverHeatSwitchOff(long j);

    private native int nativeSendOverHeatVentilate(long j);

    private native int nativeSendSettingsAllSettings(long j);

    private native int nativeSendSettingsAutoCountry(long j, byte b);

    private native int nativeSendSettingsCountry(long j, String str);

    private native int nativeSendSettingsProductName(long j, String str);

    private native int nativeSendSettingsReset(long j);

    private native int nativeSendWifiSettingsOutdoorSetting(long j, byte b);

    /* renamed from: nativeStaticGetKeyCommonARLibsVersionsStateControllerLibARCommandsVersionVersion */
    private static native String m330x98fc0e84();

    /* renamed from: nativeStaticGetKeyCommonARLibsVersionsStateDeviceLibARCommandsVersionVersion */
    private static native String m331x616c508a();

    /* renamed from: nativeStaticGetKeyCommonARLibsVersionsStateSkyControllerLibARCommandsVersionVersion */
    private static native String m332xc607045f();

    /* renamed from: nativeStaticGetKeyCommonAccessoryStateAccessoryConfigChangedError */
    private static native String m333x1d97230();

    /* renamed from: nativeStaticGetKeyCommonAccessoryStateAccessoryConfigChangedNewAccessory */
    private static native String m334x90bafc2a();

    /* renamed from: nativeStaticGetKeyCommonAccessoryStateAccessoryConfigModificationEnabledEnabled */
    private static native String m335xc2f92f8();

    /* renamed from: nativeStaticGetKeyCommonAccessoryStateSupportedAccessoriesListChangedAccessory */
    private static native String m336x75ccd1d6();

    private static native String nativeStaticGetKeyCommonAnimationsStateListAnim();

    private static native String nativeStaticGetKeyCommonAnimationsStateListError();

    private static native String nativeStaticGetKeyCommonAnimationsStateListState();

    private static native String nativeStaticGetKeyCommonAudioStateAudioStreamingRunningRunning();

    /* renamed from: nativeStaticGetKeyCommonCalibrationStateMagnetoCalibrationAxisToCalibrateChangedAxis */
    private static native String m337x5fb1490f();

    /* renamed from: nativeStaticGetKeyCommonCalibrationStateMagnetoCalibrationRequiredStateRequired */
    private static native String m338x6ee0d50c();

    /* renamed from: nativeStaticGetKeyCommonCalibrationStateMagnetoCalibrationStartedChangedStarted */
    private static native String m339x312bdd29();

    /* renamed from: nativeStaticGetKeyCommonCalibrationStateMagnetoCalibrationStateChangedCalibrationFailed */
    private static native String m340xa3175c3f();

    /* renamed from: nativeStaticGetKeyCommonCalibrationStateMagnetoCalibrationStateChangedXAxisCalibration */
    private static native String m341x7a32cb79();

    /* renamed from: nativeStaticGetKeyCommonCalibrationStateMagnetoCalibrationStateChangedYAxisCalibration */
    private static native String m342x5bc4a958();

    /* renamed from: nativeStaticGetKeyCommonCalibrationStateMagnetoCalibrationStateChangedZAxisCalibration */
    private static native String m343x3d568737();

    /* renamed from: nativeStaticGetKeyCommonCalibrationStatePitotCalibrationStateChangedLastError */
    private static native String m344xab723905();

    /* renamed from: nativeStaticGetKeyCommonCalibrationStatePitotCalibrationStateChangedState */
    private static native String m345x94357204();

    /* renamed from: nativeStaticGetKeyCommonCameraSettingsStateCameraSettingsChangedFov */
    private static native String m346x8180bff1();

    /* renamed from: nativeStaticGetKeyCommonCameraSettingsStateCameraSettingsChangedPanMax */
    private static native String m347x760925e3();

    /* renamed from: nativeStaticGetKeyCommonCameraSettingsStateCameraSettingsChangedPanMin */
    private static native String m348x760926d1();

    /* renamed from: nativeStaticGetKeyCommonCameraSettingsStateCameraSettingsChangedTiltMax */
    private static native String m349x2c50868b();

    /* renamed from: nativeStaticGetKeyCommonCameraSettingsStateCameraSettingsChangedTiltMin */
    private static native String m350x2c508779();

    private static native String nativeStaticGetKeyCommonChargerStateChargingInfoFullChargingTime();

    private static native String nativeStaticGetKeyCommonChargerStateChargingInfoIntensity();

    private static native String nativeStaticGetKeyCommonChargerStateChargingInfoPhase();

    private static native String nativeStaticGetKeyCommonChargerStateChargingInfoRate();

    /* renamed from: nativeStaticGetKeyCommonChargerStateCurrentChargeStateChangedPhase */
    private static native String m351xde683d77();

    /* renamed from: nativeStaticGetKeyCommonChargerStateCurrentChargeStateChangedStatus */
    private static native String m352xf4671b76();

    private static native String nativeStaticGetKeyCommonChargerStateLastChargeRateChangedRate();

    private static native String nativeStaticGetKeyCommonChargerStateMaxChargeRateChangedRate();

    private static native String nativeStaticGetKeyCommonCommonStateBatteryStateChangedPercent();

    private static native String nativeStaticGetKeyCommonCommonStateCountryListKnownCountryCodes();

    private static native String nativeStaticGetKeyCommonCommonStateCountryListKnownListFlags();

    private static native String nativeStaticGetKeyCommonCommonStateCurrentDateChangedDate();

    private static native String nativeStaticGetKeyCommonCommonStateCurrentTimeChangedTime();

    /* renamed from: nativeStaticGetKeyCommonCommonStateDeprecatedMassStorageContentChangedMassstorageid */
    private static native String m353xcaafffa0();

    /* renamed from: nativeStaticGetKeyCommonCommonStateDeprecatedMassStorageContentChangedNbCrashLogs */
    private static native String m354xfac43d60();

    /* renamed from: nativeStaticGetKeyCommonCommonStateDeprecatedMassStorageContentChangedNbPhotos */
    private static native String m355xeaec8557();

    /* renamed from: nativeStaticGetKeyCommonCommonStateDeprecatedMassStorageContentChangedNbPuds */
    private static native String m356xa279e7aa();

    /* renamed from: nativeStaticGetKeyCommonCommonStateDeprecatedMassStorageContentChangedNbVideos */
    private static native String m357xf532782e();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageContentForCurrentRunMassstorageid */
    private static native String m358x2c23326();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageContentForCurrentRunNbPhotos */
    private static native String m359x8b7db611();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageContentForCurrentRunNbRawPhotos */
    private static native String m360x74e79739();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageContentForCurrentRunNbVideos */
    private static native String m361x95c3a8e8();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageContentMassstorageid */
    private static native String m362x6e7d4303();

    private static native String nativeStaticGetKeyCommonCommonStateMassStorageContentNbCrashLogs();

    private static native String nativeStaticGetKeyCommonCommonStateMassStorageContentNbPhotos();

    private static native String nativeStaticGetKeyCommonCommonStateMassStorageContentNbPuds();

    private static native String nativeStaticGetKeyCommonCommonStateMassStorageContentNbRawPhotos();

    private static native String nativeStaticGetKeyCommonCommonStateMassStorageContentNbVideos();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageInfoRemainingListChangedFreespace */
    private static native String m363x7b343332();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageInfoRemainingListChangedPhotoremaining */
    private static native String m364x50b681ec();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageInfoRemainingListChangedRectime */
    private static native String m365x4d35de75();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageInfoStateListChangedFull */
    private static native String m366x19eb927c();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageInfoStateListChangedInternal */
    private static native String m367xf77782a();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageInfoStateListChangedMassstorageid */
    private static native String m368x252c5315();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageInfoStateListChangedPlugged */
    private static native String m369x5eec5beb();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageInfoStateListChangedSize */
    private static native String m370x19f14fee();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageInfoStateListChangedUsedsize */
    private static native String m371xdcde56ab();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageStateListChangedMassstorageid */
    private static native String m372x7e3ce423();

    /* renamed from: nativeStaticGetKeyCommonCommonStateMassStorageStateListChangedName */
    private static native String m373x441a51aa();

    private static native String nativeStaticGetKeyCommonCommonStateProductModelModel();

    /* renamed from: nativeStaticGetKeyCommonCommonStateSensorsStatesListChangedSensorName */
    private static native String m374xab99adad();

    /* renamed from: nativeStaticGetKeyCommonCommonStateSensorsStatesListChangedSensorState */
    private static native String m375xc7eaf5af();

    /* renamed from: nativeStaticGetKeyCommonCommonStateVideoRecordingTimestampStartTimestamp */
    private static native String m376x1cf235c7();

    /* renamed from: nativeStaticGetKeyCommonCommonStateVideoRecordingTimestampStopTimestamp */
    private static native String m377x9d6795e1();

    private static native String nativeStaticGetKeyCommonCommonStateWifiSignalChangedRssi();

    /* renamed from: nativeStaticGetKeyCommonFlightPlanSettingsStateReturnHomeOnDisconnectChangedIsReadOnly */
    private static native String m378x3389e71a();

    /* renamed from: nativeStaticGetKeyCommonFlightPlanSettingsStateReturnHomeOnDisconnectChangedState */
    private static native String m379xa4d8a63();

    /* renamed from: nativeStaticGetKeyCommonFlightPlanStateAvailabilityStateChangedAvailabilityState */
    private static native String m380xc5335d9();

    /* renamed from: nativeStaticGetKeyCommonFlightPlanStateComponentStateListChangedComponent */
    private static native String m381x9f3ac81a();

    /* renamed from: nativeStaticGetKeyCommonFlightPlanStateComponentStateListChangedState */
    private static native String m382xd05a862e();

    private static native String nativeStaticGetKeyCommonFlightPlanStateLockStateChangedLockState();

    private static native String nativeStaticGetKeyCommonHeadlightsStateIntensityChangedLeft();

    private static native String nativeStaticGetKeyCommonHeadlightsStateIntensityChangedRight();

    /* renamed from: nativeStaticGetKeyCommonMavlinkStateMavlinkFilePlayingStateChangedFilepath */
    private static native String m383x63aaea28();

    /* renamed from: nativeStaticGetKeyCommonMavlinkStateMavlinkFilePlayingStateChangedState */
    private static native String m384x7563fb6a();

    /* renamed from: nativeStaticGetKeyCommonMavlinkStateMavlinkFilePlayingStateChangedType */
    private static native String m385x565e9941();

    /* renamed from: nativeStaticGetKeyCommonMavlinkStateMavlinkPlayErrorStateChangedError */
    private static native String m386xb0ea4c7f();

    private static native String nativeStaticGetKeyCommonMavlinkStateMissionItemExecutedIdx();

    private static native String nativeStaticGetKeyCommonNetworkEventDisconnectionCause();

    /* renamed from: nativeStaticGetKeyCommonOverHeatStateOverHeatRegulationChangedRegulationType */
    private static native String m387xaf8d4a16();

    private static native String nativeStaticGetKeyCommonRunStateRunIdChangedRunId();

    private static native String nativeStaticGetKeyCommonSettingsStateAutoCountryChangedAutomatic();

    private static native String nativeStaticGetKeyCommonSettingsStateCountryChangedCode();

    private static native String nativeStaticGetKeyCommonSettingsStateProductNameChangedName();

    /* renamed from: nativeStaticGetKeyCommonSettingsStateProductSerialHighChangedHigh */
    private static native String m388xbe7b8e06();

    private static native String nativeStaticGetKeyCommonSettingsStateProductSerialLowChangedLow();

    /* renamed from: nativeStaticGetKeyCommonSettingsStateProductVersionChangedHardware */
    private static native String m389xb31c7e5e();

    /* renamed from: nativeStaticGetKeyCommonSettingsStateProductVersionChangedSoftware */
    private static native String m390xfac7fa1d();

    /* renamed from: nativeStaticGetKeyCommonWifiSettingsStateOutdoorSettingsChangedOutdoor */
    private static native String m391x5754c707();

    static {
        f1477xa1ec5620 = "";
        f1481x7683cfa2 = "";
        f1485x74ca3c49 = "";
        f1484x2d1ec08a = "";
        f1482x2c124084 = "";
        f1483xfae5f60a = "";
        f1480xe84719a0 = "";
        f1479xb14b87e7 = "";
        f1427xd58cdac0 = "";
        f1456xd5f273e1 = "";
        f1457x46256eb4 = "";
        f1452xa11f880f = "";
        f1454xa89d90bc = "";
        f1455x1049ff68 = "";
        f1453x5e35443d = "";
        f1450xa897d34a = "";
        f1451xf94b99f8 = "";
        f1430x185141ea = "";
        f1431xd34151a8 = "";
        f1447xecad18f3 = "";
        f1449x3c06a4dc = "";
        f1448xfa62a449 = "";
        f1463x6024c2f5 = "";
        f1459x16e55c33 = "";
        f1460xc61517c9 = "";
        f1458xeb109a7a = "";
        f1429xd768d12f = "";
        f1428x2a3593ea = "";
        f1432xa162dc44 = "";
        f1434x7ae61fdb = "";
        f1436x852c12b2 = "";
        f1435xda91572e = "";
        f1433x8276741c = "";
        f1441x8efedf53 = "";
        f1443x9a1adfac = "";
        f1446xa460d283 = "";
        f1444x3ce5d73f = "";
        f1442xfdf456ab = "";
        f1445x4eaedb7e = "";
        f1437x695b525e = "";
        f1438xb856f301 = "";
        f1440xc29ce5d8 = "";
        f1439xbfbc8c09 = "";
        f1461x20137c89 = "";
        f1462xe7d92eff = "";
        f1478xf9ae1f8c = "";
        f1486xf361b2b5 = "";
        f1473xfe11ba58 = "";
        f1472xd6a8205a = "";
        f1474x9495c073 = "";
        f1475xceb49fb1 = "";
        f1476x58ae587f = "";
        f1465xdbf38035 = "";
        f1464x1510f428 = "";
        f1409xc2992dcd = "";
        f1410xa42b0bac = "";
        f1411x85bce98b = "";
        f1408x75a7f06b = "";
        f1406x4e63e32c = "";
        f1405x3c2e3c17 = "";
        f1407xc4f96d55 = "";
        f1413x5f477a7a = "";
        f1412x1672fe9b = "";
        f1414x6c5e6127 = "";
        f1415xb546ced = "";
        f1416xb546ddb = "";
        f1417x40600ce1 = "";
        f1418x40600dcf = "";
        f1466x4f9e1159 = "";
        f1467xc00e81dc = "";
        f1468x1ad878f0 = "";
        f1469x98518e59 = "";
        f1401xfff8d5f4 = "";
        f1403x3621608b = "";
        f1402xee63482e = "";
        f1404x8cc79e0c = "";
        f1470x91aa077d = "";
        f1471xa3ed4946 = "";
        ARCONTROLLER_DICTIONARY_KEY_COMMON_ANIMATIONSSTATE_LIST_ANIM = "";
        ARCONTROLLER_DICTIONARY_KEY_COMMON_ANIMATIONSSTATE_LIST_STATE = "";
        ARCONTROLLER_DICTIONARY_KEY_COMMON_ANIMATIONSSTATE_LIST_ERROR = "";
        f1400xe7c25cc4 = "";
        f1398xa6ed54ce = "";
        f1397x8e41b60c = "";
        f1399xa762e236 = "";
        f1426xa3da7b34 = "";
        f1424xc9579170 = "";
        f1423x58e39c1d = "";
        f1425x45348dc4 = "";
        f1421x3e62372a = "";
        f1422xd8b9a931 = "";
        f1420x7dfece62 = "";
        f1419x71332b9e = "";
        ARCONTROLLER_DICTIONARY_KEY_COMMON_RUNSTATE_RUNIDCHANGED_RUNID = "";
        f1477xa1ec5620 = nativeStaticGetKeyCommonNetworkEventDisconnectionCause();
        f1481x7683cfa2 = nativeStaticGetKeyCommonSettingsStateProductNameChangedName();
        f1485x74ca3c49 = m390xfac7fa1d();
        f1484x2d1ec08a = m389xb31c7e5e();
        f1482x2c124084 = m388xbe7b8e06();
        f1483xfae5f60a = nativeStaticGetKeyCommonSettingsStateProductSerialLowChangedLow();
        f1480xe84719a0 = nativeStaticGetKeyCommonSettingsStateCountryChangedCode();
        f1479xb14b87e7 = nativeStaticGetKeyCommonSettingsStateAutoCountryChangedAutomatic();
        f1427xd58cdac0 = nativeStaticGetKeyCommonCommonStateBatteryStateChangedPercent();
        f1456xd5f273e1 = m372x7e3ce423();
        f1457x46256eb4 = m373x441a51aa();
        f1452xa11f880f = m368x252c5315();
        f1454xa89d90bc = m370x19f14fee();
        f1455x1049ff68 = m371xdcde56ab();
        f1453x5e35443d = m369x5eec5beb();
        f1450xa897d34a = m366x19eb927c();
        f1451xf94b99f8 = m367xf77782a();
        f1430x185141ea = nativeStaticGetKeyCommonCommonStateCurrentDateChangedDate();
        f1431xd34151a8 = nativeStaticGetKeyCommonCommonStateCurrentTimeChangedTime();
        f1447xecad18f3 = m363x7b343332();
        f1449x3c06a4dc = m365x4d35de75();
        f1448xfa62a449 = m364x50b681ec();
        f1463x6024c2f5 = nativeStaticGetKeyCommonCommonStateWifiSignalChangedRssi();
        f1459x16e55c33 = m374xab99adad();
        f1460xc61517c9 = m375xc7eaf5af();
        f1458xeb109a7a = nativeStaticGetKeyCommonCommonStateProductModelModel();
        f1429xd768d12f = nativeStaticGetKeyCommonCommonStateCountryListKnownListFlags();
        f1428x2a3593ea = nativeStaticGetKeyCommonCommonStateCountryListKnownCountryCodes();
        f1432xa162dc44 = m353xcaafffa0();
        f1434x7ae61fdb = m355xeaec8557();
        f1436x852c12b2 = m357xf532782e();
        f1435xda91572e = m356xa279e7aa();
        f1433x8276741c = m354xfac43d60();
        f1441x8efedf53 = m362x6e7d4303();
        f1443x9a1adfac = nativeStaticGetKeyCommonCommonStateMassStorageContentNbPhotos();
        f1446xa460d283 = nativeStaticGetKeyCommonCommonStateMassStorageContentNbVideos();
        f1444x3ce5d73f = nativeStaticGetKeyCommonCommonStateMassStorageContentNbPuds();
        f1442xfdf456ab = nativeStaticGetKeyCommonCommonStateMassStorageContentNbCrashLogs();
        f1445x4eaedb7e = nativeStaticGetKeyCommonCommonStateMassStorageContentNbRawPhotos();
        f1437x695b525e = m358x2c23326();
        f1438xb856f301 = m359x8b7db611();
        f1440xc29ce5d8 = m361x95c3a8e8();
        f1439xbfbc8c09 = m360x74e79739();
        f1461x20137c89 = m376x1cf235c7();
        f1462xe7d92eff = m377x9d6795e1();
        f1478xf9ae1f8c = m387xaf8d4a16();
        f1486xf361b2b5 = m391x5754c707();
        f1473xfe11ba58 = m384x7563fb6a();
        f1472xd6a8205a = m383x63aaea28();
        f1474x9495c073 = m385x565e9941();
        f1475xceb49fb1 = m386xb0ea4c7f();
        f1476x58ae587f = nativeStaticGetKeyCommonMavlinkStateMissionItemExecutedIdx();
        f1465xdbf38035 = m379xa4d8a63();
        f1464x1510f428 = m378x3389e71a();
        f1409xc2992dcd = m341x7a32cb79();
        f1410xa42b0bac = m342x5bc4a958();
        f1411x85bce98b = m343x3d568737();
        f1408x75a7f06b = m340xa3175c3f();
        f1406x4e63e32c = m338x6ee0d50c();
        f1405x3c2e3c17 = m337x5fb1490f();
        f1407xc4f96d55 = m339x312bdd29();
        f1413x5f477a7a = m345x94357204();
        f1412x1672fe9b = m344xab723905();
        f1414x6c5e6127 = m346x8180bff1();
        f1415xb546ced = m347x760925e3();
        f1416xb546ddb = m348x760926d1();
        f1417x40600ce1 = m349x2c50868b();
        f1418x40600dcf = m350x2c508779();
        f1466x4f9e1159 = m380xc5335d9();
        f1467xc00e81dc = m381x9f3ac81a();
        f1468x1ad878f0 = m382xd05a862e();
        f1469x98518e59 = nativeStaticGetKeyCommonFlightPlanStateLockStateChangedLockState();
        f1401xfff8d5f4 = m330x98fc0e84();
        f1403x3621608b = m332xc607045f();
        f1402xee63482e = m331x616c508a();
        f1404x8cc79e0c = nativeStaticGetKeyCommonAudioStateAudioStreamingRunningRunning();
        f1470x91aa077d = nativeStaticGetKeyCommonHeadlightsStateIntensityChangedLeft();
        f1471xa3ed4946 = nativeStaticGetKeyCommonHeadlightsStateIntensityChangedRight();
        ARCONTROLLER_DICTIONARY_KEY_COMMON_ANIMATIONSSTATE_LIST_ANIM = nativeStaticGetKeyCommonAnimationsStateListAnim();
        ARCONTROLLER_DICTIONARY_KEY_COMMON_ANIMATIONSSTATE_LIST_STATE = nativeStaticGetKeyCommonAnimationsStateListState();
        ARCONTROLLER_DICTIONARY_KEY_COMMON_ANIMATIONSSTATE_LIST_ERROR = nativeStaticGetKeyCommonAnimationsStateListError();
        f1400xe7c25cc4 = m336x75ccd1d6();
        f1398xa6ed54ce = m334x90bafc2a();
        f1397x8e41b60c = m333x1d97230();
        f1399xa762e236 = m335xc2f92f8();
        f1426xa3da7b34 = nativeStaticGetKeyCommonChargerStateMaxChargeRateChangedRate();
        f1424xc9579170 = m352xf4671b76();
        f1423x58e39c1d = m351xde683d77();
        f1425x45348dc4 = nativeStaticGetKeyCommonChargerStateLastChargeRateChangedRate();
        f1421x3e62372a = nativeStaticGetKeyCommonChargerStateChargingInfoPhase();
        f1422xd8b9a931 = nativeStaticGetKeyCommonChargerStateChargingInfoRate();
        f1420x7dfece62 = nativeStaticGetKeyCommonChargerStateChargingInfoIntensity();
        f1419x71332b9e = nativeStaticGetKeyCommonChargerStateChargingInfoFullChargingTime();
        ARCONTROLLER_DICTIONARY_KEY_COMMON_RUNSTATE_RUNIDCHANGED_RUNID = nativeStaticGetKeyCommonRunStateRunIdChangedRunId();
    }

    public ARFeatureCommon(long nativeFeature) {
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

    public ARCONTROLLER_ERROR_ENUM sendNetworkDisconnect() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendNetworkDisconnect(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSettingsAllSettings() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSettingsAllSettings(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSettingsReset() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSettingsReset(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSettingsProductName(String _name) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSettingsProductName(this.jniFeature, _name));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSettingsCountry(String _code) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSettingsCountry(this.jniFeature, _code));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSettingsAutoCountry(byte _automatic) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSettingsAutoCountry(this.jniFeature, _automatic));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendCommonAllStates() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendCommonAllStates(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendCommonCurrentDate(String _date) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendCommonCurrentDate(this.jniFeature, _date));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendCommonCurrentTime(String _time) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendCommonCurrentTime(this.jniFeature, _time));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendCommonReboot() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendCommonReboot(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendOverHeatSwitchOff() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendOverHeatSwitchOff(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendOverHeatVentilate() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendOverHeatVentilate(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendControllerIsPiloting(byte _piloting) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendControllerIsPiloting(this.jniFeature, _piloting));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendWifiSettingsOutdoorSetting(byte _outdoor) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendWifiSettingsOutdoorSetting(this.jniFeature, _outdoor));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMavlinkStart(String _filepath, ARCOMMANDS_COMMON_MAVLINK_START_TYPE_ENUM _type) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMavlinkStart(this.jniFeature, _filepath, _type.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMavlinkPause() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMavlinkPause(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMavlinkStop() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMavlinkStop(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendFlightPlanSettingsReturnHomeOnDisconnect(byte _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendFlightPlanSettingsReturnHomeOnDisconnect(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendCalibrationMagnetoCalibration(byte _calibrate) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendCalibrationMagnetoCalibration(this.jniFeature, _calibrate));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendCalibrationPitotCalibration(byte _calibrate) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendCalibrationPitotCalibration(this.jniFeature, _calibrate));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendGPSControllerPositionForRun(double _latitude, double _longitude) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendGPSControllerPositionForRun(this.jniFeature, _latitude, _longitude));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAudioControllerReadyForStreaming(byte _ready) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAudioControllerReadyForStreaming(this.jniFeature, _ready));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendHeadlightsIntensity(byte _left, byte _right) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendHeadlightsIntensity(this.jniFeature, _left, _right));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsStartAnimation(ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM _anim) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsStartAnimation(this.jniFeature, _anim.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsStopAnimation(ARCOMMANDS_COMMON_ANIMATIONS_STOPANIMATION_ANIM_ENUM _anim) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsStopAnimation(this.jniFeature, _anim.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsStopAllAnimations() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsStopAllAnimations(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAccessoryConfig(ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM _accessory) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAccessoryConfig(this.jniFeature, _accessory.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendChargerSetMaxChargeRate(ARCOMMANDS_COMMON_CHARGER_SETMAXCHARGERATE_RATE_ENUM _rate) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendChargerSetMaxChargeRate(this.jniFeature, _rate.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendFactoryReset() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendFactoryReset(this.jniFeature));
            }
        }
        return error;
    }
}
