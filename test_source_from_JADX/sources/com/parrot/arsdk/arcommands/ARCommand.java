package com.parrot.arsdk.arcommands;

import com.parrot.arsdk.arsal.ARNativeData;

public class ARCommand extends ARNativeData {
    public static final boolean ARCOMMANDS_ARCOMMAND_HAS_DEBUG_COMMANDS = true;
    public static final int ARCOMMANDS_ARCOMMAND_HEADER_SIZE = 4;
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_ANIMATION_BOOMERANG = (1 << ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM.BOOMERANG.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_ANIMATION_CANDLE = (1 << ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM.CANDLE.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_ANIMATION_DOLLY_SLIDE = (1 << ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM.DOLLY_SLIDE.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_ANIMATION_HELICOID = (1 << ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM.HELICOID.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_ANIMATION_NONE = (1 << ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM.NONE.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_ANIMATION_SWING = (1 << ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM.SWING.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_BOOMERANG_CONFIGURE_PARAM_DISTANCE = (1 << ARCOMMANDS_FOLLOW_ME_BOOMERANG_CONFIGURE_PARAM_ENUM.DISTANCE.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_BOOMERANG_CONFIGURE_PARAM_SPEED = (1 << ARCOMMANDS_FOLLOW_ME_BOOMERANG_CONFIGURE_PARAM_ENUM.SPEED.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_CANDLE_CONFIGURE_PARAM_SPEED = (1 << ARCOMMANDS_FOLLOW_ME_CANDLE_CONFIGURE_PARAM_ENUM.SPEED.getValue());
    /* renamed from: ARCOMMANDS_FLAG_FOLLOW_ME_CANDLE_CONFIGURE_PARAM_VERTICAL_DISTANCE */
    public static final int f1771x43fa2687 = (1 << ARCOMMANDS_FOLLOW_ME_CANDLE_CONFIGURE_PARAM_ENUM.VERTICAL_DISTANCE.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_DOLLY_SLIDE_CONFIGURE_PARAM_ANGLE = (1 << ARCOMMANDS_FOLLOW_ME_DOLLY_SLIDE_CONFIGURE_PARAM_ENUM.ANGLE.getValue());
    /* renamed from: ARCOMMANDS_FLAG_FOLLOW_ME_DOLLY_SLIDE_CONFIGURE_PARAM_HORIZONTAL_DISTANCE */
    public static final int f1772x431d1940 = (1 << ARCOMMANDS_FOLLOW_ME_DOLLY_SLIDE_CONFIGURE_PARAM_ENUM.HORIZONTAL_DISTANCE.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_DOLLY_SLIDE_CONFIGURE_PARAM_SPEED = (1 << ARCOMMANDS_FOLLOW_ME_DOLLY_SLIDE_CONFIGURE_PARAM_ENUM.SPEED.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_GEO_REL_CONFIGURE_PARAM_AZIMUTH = (1 << ARCOMMANDS_FOLLOW_ME_GEO_REL_CONFIGURE_PARAM_ENUM.AZIMUTH.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_GEO_REL_CONFIGURE_PARAM_DISTANCE = (1 << ARCOMMANDS_FOLLOW_ME_GEO_REL_CONFIGURE_PARAM_ENUM.DISTANCE.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_GEO_REL_CONFIGURE_PARAM_ELEVATION = (1 << ARCOMMANDS_FOLLOW_ME_GEO_REL_CONFIGURE_PARAM_ENUM.ELEVATION.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_HELICOID_CONFIGURE_PARAM_REVOLUTION_NB = (1 << ARCOMMANDS_FOLLOW_ME_HELICOID_CONFIGURE_PARAM_ENUM.REVOLUTION_NB.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_HELICOID_CONFIGURE_PARAM_SPEED = (1 << ARCOMMANDS_FOLLOW_ME_HELICOID_CONFIGURE_PARAM_ENUM.SPEED.getValue());
    /* renamed from: ARCOMMANDS_FLAG_FOLLOW_ME_HELICOID_CONFIGURE_PARAM_VERTICAL_DISTANCE */
    public static final int f1773x8441d1bb = (1 << ARCOMMANDS_FOLLOW_ME_HELICOID_CONFIGURE_PARAM_ENUM.VERTICAL_DISTANCE.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_INPUT_DRONE_CALIBRATED = (1 << ARCOMMANDS_FOLLOW_ME_INPUT_ENUM.DRONE_CALIBRATED.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_INPUT_DRONE_FAR_ENOUGH = (1 << ARCOMMANDS_FOLLOW_ME_INPUT_ENUM.DRONE_FAR_ENOUGH.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_INPUT_DRONE_GPS_GOOD_ACCURACY = (1 << ARCOMMANDS_FOLLOW_ME_INPUT_ENUM.DRONE_GPS_GOOD_ACCURACY.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_INPUT_DRONE_HIGH_ENOUGH = (1 << ARCOMMANDS_FOLLOW_ME_INPUT_ENUM.DRONE_HIGH_ENOUGH.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_INPUT_IMAGE_DETECTION = (1 << ARCOMMANDS_FOLLOW_ME_INPUT_ENUM.IMAGE_DETECTION.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_INPUT_TARGET_BAROMETER_OK = (1 << ARCOMMANDS_FOLLOW_ME_INPUT_ENUM.TARGET_BAROMETER_OK.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_INPUT_TARGET_GPS_GOOD_ACCURACY = (1 << ARCOMMANDS_FOLLOW_ME_INPUT_ENUM.TARGET_GPS_GOOD_ACCURACY.getValue());
    public static final int ARCOMMANDS_FLAG_FOLLOW_ME_SWING_CONFIGURE_PARAM_SPEED = (1 << ARCOMMANDS_FOLLOW_ME_SWING_CONFIGURE_PARAM_ENUM.SPEED.getValue());
    /* renamed from: ARCOMMANDS_FLAG_FOLLOW_ME_SWING_CONFIGURE_PARAM_VERTICAL_DISTANCE */
    public static final int f1774x7f1ec88c = (1 << ARCOMMANDS_FOLLOW_ME_SWING_CONFIGURE_PARAM_ENUM.VERTICAL_DISTANCE.getValue());
    public static final int ARCOMMANDS_FLAG_GENERIC_LIST_FLAGS_EMPTY = (1 << ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM.EMPTY.getValue());
    public static final int ARCOMMANDS_FLAG_GENERIC_LIST_FLAGS_FIRST = (1 << ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM.FIRST.getValue());
    public static final int ARCOMMANDS_FLAG_GENERIC_LIST_FLAGS_LAST = (1 << ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM.LAST.getValue());
    public static final int ARCOMMANDS_FLAG_GENERIC_LIST_FLAGS_REMOVE = (1 << ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM.REMOVE.getValue());
    public static final int ARCOMMANDS_FLAG_MAPPER_MINI_MODE_PLANE = (1 << ARCOMMANDS_MAPPER_MINI_MODE_ENUM.PLANE.getValue());
    public static final int ARCOMMANDS_FLAG_MAPPER_MINI_MODE_QUAD = (1 << ARCOMMANDS_MAPPER_MINI_MODE_ENUM.QUAD.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_ACTION_EMERGENCY = (1 << ARCOMMANDS_RC_CHANNEL_ACTION_ENUM.EMERGENCY.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_ACTION_GAZ = (1 << ARCOMMANDS_RC_CHANNEL_ACTION_ENUM.GAZ.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_ACTION_INVALID = (1 << ARCOMMANDS_RC_CHANNEL_ACTION_ENUM.INVALID.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_ACTION_PILOTING_MODE = (1 << ARCOMMANDS_RC_CHANNEL_ACTION_ENUM.PILOTING_MODE.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_ACTION_PITCH = (1 << ARCOMMANDS_RC_CHANNEL_ACTION_ENUM.PITCH.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_ACTION_RETURN_HOME = (1 << ARCOMMANDS_RC_CHANNEL_ACTION_ENUM.RETURN_HOME.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_ACTION_ROLL = (1 << ARCOMMANDS_RC_CHANNEL_ACTION_ENUM.ROLL.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_ACTION_TAKEOFF_LAND = (1 << ARCOMMANDS_RC_CHANNEL_ACTION_ENUM.TAKEOFF_LAND.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_ACTION_TAKE_CONTROL = (1 << ARCOMMANDS_RC_CHANNEL_ACTION_ENUM.TAKE_CONTROL.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_ACTION_YAW = (1 << ARCOMMANDS_RC_CHANNEL_ACTION_ENUM.YAW.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_TYPE_BISTABLE_BUTTON = (1 << ARCOMMANDS_RC_CHANNEL_TYPE_ENUM.BISTABLE_BUTTON.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_TYPE_INVALID = (1 << ARCOMMANDS_RC_CHANNEL_TYPE_ENUM.INVALID.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_TYPE_MONOSTABLE_BUTTON = (1 << ARCOMMANDS_RC_CHANNEL_TYPE_ENUM.MONOSTABLE_BUTTON.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_TYPE_ROTARY_BUTTON = (1 << ARCOMMANDS_RC_CHANNEL_TYPE_ENUM.ROTARY_BUTTON.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_TYPE_SIGNED_AXIS = (1 << ARCOMMANDS_RC_CHANNEL_TYPE_ENUM.SIGNED_AXIS.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_TYPE_TRISTATE_BUTTON = (1 << ARCOMMANDS_RC_CHANNEL_TYPE_ENUM.TRISTATE_BUTTON.getValue());
    public static final int ARCOMMANDS_FLAG_RC_CHANNEL_TYPE_UNSIGNED_AXIS = (1 << ARCOMMANDS_RC_CHANNEL_TYPE_ENUM.UNSIGNED_AXIS.getValue());
    public static final int ARCOMMANDS_FLAG_WIFI_BAND_2_4_GHZ = (1 << ARCOMMANDS_WIFI_BAND_ENUM.BAND_2_4_GHZ.getValue());
    public static final int ARCOMMANDS_FLAG_WIFI_BAND_5_GHZ = (1 << ARCOMMANDS_WIFI_BAND_ENUM.BAND_5_GHZ.getValue());
    public static final int ARCOMMANDS_FLAG_WIFI_ENVIRONMENT_INDOOR = (1 << ARCOMMANDS_WIFI_ENVIRONMENT_ENUM.INDOOR.getValue());
    public static final int ARCOMMANDS_FLAG_WIFI_ENVIRONMENT_OUTDOOR = (1 << ARCOMMANDS_WIFI_ENVIRONMENT_ENUM.OUTDOOR.getValue());
    private static final ARCommandsDecoder _decoder = new ARCommandsDecoder();

    private native int nativeSetARDrone3AccessoryStateConnectedAccessories(long j, int i, byte b, int i2, String str, String str2, byte b2);

    private native int nativeSetARDrone3AnimationsFlip(long j, int i, int i2);

    private native int nativeSetARDrone3AntiflickeringElectricFrequency(long j, int i, int i2);

    private native int nativeSetARDrone3AntiflickeringSetMode(long j, int i, int i2);

    private native int nativeSetARDrone3AntiflickeringStateElectricFrequencyChanged(long j, int i, int i2);

    private native int nativeSetARDrone3AntiflickeringStateModeChanged(long j, int i, int i2);

    private native int nativeSetARDrone3CameraOrientation(long j, int i, byte b, byte b2);

    private native int nativeSetARDrone3CameraOrientationV2(long j, int i, float f, float f2);

    private native int nativeSetARDrone3CameraStateDefaultCameraOrientation(long j, int i, byte b, byte b2);

    private native int nativeSetARDrone3CameraStateDefaultCameraOrientationV2(long j, int i, float f, float f2);

    private native int nativeSetARDrone3CameraStateOrientation(long j, int i, byte b, byte b2);

    private native int nativeSetARDrone3CameraStateOrientationV2(long j, int i, float f, float f2);

    private native int nativeSetARDrone3CameraStateVelocityRange(long j, int i, float f, float f2);

    private native int nativeSetARDrone3CameraVelocity(long j, int i, float f, float f2);

    private native int nativeSetARDrone3GPSSettingsHomeType(long j, int i, int i2);

    private native int nativeSetARDrone3GPSSettingsResetHome(long j, int i);

    private native int nativeSetARDrone3GPSSettingsReturnHomeDelay(long j, int i, short s);

    private native int nativeSetARDrone3GPSSettingsSendControllerGPS(long j, int i, double d, double d2, double d3, double d4, double d5);

    private native int nativeSetARDrone3GPSSettingsSetHome(long j, int i, double d, double d2, double d3);

    private native int nativeSetARDrone3GPSSettingsStateGPSFixStateChanged(long j, int i, byte b);

    private native int nativeSetARDrone3GPSSettingsStateGPSUpdateStateChanged(long j, int i, int i2);

    private native int nativeSetARDrone3GPSSettingsStateGeofenceCenterChanged(long j, int i, double d, double d2);

    private native int nativeSetARDrone3GPSSettingsStateHomeChanged(long j, int i, double d, double d2, double d3);

    private native int nativeSetARDrone3GPSSettingsStateHomeTypeChanged(long j, int i, int i2);

    private native int nativeSetARDrone3GPSSettingsStateResetHomeChanged(long j, int i, double d, double d2, double d3);

    private native int nativeSetARDrone3GPSSettingsStateReturnHomeDelayChanged(long j, int i, short s);

    private native int nativeSetARDrone3GPSStateHomeTypeAvailabilityChanged(long j, int i, int i2, byte b);

    private native int nativeSetARDrone3GPSStateHomeTypeChosenChanged(long j, int i, int i2);

    private native int nativeSetARDrone3GPSStateNumberOfSatelliteChanged(long j, int i, byte b);

    private native int nativeSetARDrone3MediaRecordEventPictureEventChanged(long j, int i, int i2, int i3);

    private native int nativeSetARDrone3MediaRecordEventVideoEventChanged(long j, int i, int i2, int i3);

    private native int nativeSetARDrone3MediaRecordPicture(long j, int i, byte b);

    private native int nativeSetARDrone3MediaRecordPictureV2(long j, int i);

    private native int nativeSetARDrone3MediaRecordStatePictureStateChanged(long j, int i, byte b, byte b2);

    private native int nativeSetARDrone3MediaRecordStatePictureStateChangedV2(long j, int i, int i2, int i3);

    private native int nativeSetARDrone3MediaRecordStateVideoResolutionState(long j, int i, int i2, int i3);

    private native int nativeSetARDrone3MediaRecordStateVideoStateChanged(long j, int i, int i2, byte b);

    private native int nativeSetARDrone3MediaRecordStateVideoStateChangedV2(long j, int i, int i2, int i3);

    private native int nativeSetARDrone3MediaRecordVideo(long j, int i, int i2, byte b);

    private native int nativeSetARDrone3MediaRecordVideoV2(long j, int i, int i2);

    private native int nativeSetARDrone3MediaStreamingStateVideoEnableChanged(long j, int i, int i2);

    private native int nativeSetARDrone3MediaStreamingStateVideoStreamModeChanged(long j, int i, int i2);

    private native int nativeSetARDrone3MediaStreamingVideoEnable(long j, int i, byte b);

    private native int nativeSetARDrone3MediaStreamingVideoStreamMode(long j, int i, int i2);

    private native int nativeSetARDrone3NetworkSettingsStateWifiSecurity(long j, int i, int i2, String str, int i3);

    private native int nativeSetARDrone3NetworkSettingsStateWifiSecurityChanged(long j, int i, int i2);

    private native int nativeSetARDrone3NetworkSettingsStateWifiSelectionChanged(long j, int i, int i2, int i3, byte b);

    private native int nativeSetARDrone3NetworkSettingsWifiSecurity(long j, int i, int i2, String str, int i3);

    private native int nativeSetARDrone3NetworkSettingsWifiSelection(long j, int i, int i2, int i3, byte b);

    private native int nativeSetARDrone3NetworkStateAllWifiAuthChannelChanged(long j, int i);

    private native int nativeSetARDrone3NetworkStateAllWifiScanChanged(long j, int i);

    private native int nativeSetARDrone3NetworkStateWifiAuthChannelListChanged(long j, int i, int i2, byte b, byte b2);

    private native int nativeSetARDrone3NetworkStateWifiScanListChanged(long j, int i, String str, short s, int i2, byte b);

    private native int nativeSetARDrone3NetworkWifiAuthChannel(long j, int i);

    private native int nativeSetARDrone3NetworkWifiScan(long j, int i, int i2);

    private native int nativeSetARDrone3PROStateFeatures(long j, int i, long j2);

    private native int nativeSetARDrone3PictureSettingsAutoWhiteBalanceSelection(long j, int i, int i2);

    private native int nativeSetARDrone3PictureSettingsExpositionSelection(long j, int i, float f);

    private native int nativeSetARDrone3PictureSettingsPictureFormatSelection(long j, int i, int i2);

    private native int nativeSetARDrone3PictureSettingsSaturationSelection(long j, int i, float f);

    private native int nativeSetARDrone3PictureSettingsStateAutoWhiteBalanceChanged(long j, int i, int i2);

    private native int nativeSetARDrone3PictureSettingsStateExpositionChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetARDrone3PictureSettingsStatePictureFormatChanged(long j, int i, int i2);

    private native int nativeSetARDrone3PictureSettingsStateSaturationChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetARDrone3PictureSettingsStateTimelapseChanged(long j, int i, byte b, float f, float f2, float f3);

    private native int nativeSetARDrone3PictureSettingsStateVideoAutorecordChanged(long j, int i, byte b, byte b2);

    private native int nativeSetARDrone3PictureSettingsStateVideoFramerateChanged(long j, int i, int i2);

    private native int nativeSetARDrone3PictureSettingsStateVideoRecordingModeChanged(long j, int i, int i2);

    private native int nativeSetARDrone3PictureSettingsStateVideoResolutionsChanged(long j, int i, int i2);

    /* renamed from: nativeSetARDrone3PictureSettingsStateVideoStabilizationModeChanged */
    private native int m550x767cfb26(long j, int i, int i2);

    private native int nativeSetARDrone3PictureSettingsTimelapseSelection(long j, int i, byte b, float f);

    private native int nativeSetARDrone3PictureSettingsVideoAutorecordSelection(long j, int i, byte b, byte b2);

    private native int nativeSetARDrone3PictureSettingsVideoFramerate(long j, int i, int i2);

    private native int nativeSetARDrone3PictureSettingsVideoRecordingMode(long j, int i, int i2);

    private native int nativeSetARDrone3PictureSettingsVideoResolutions(long j, int i, int i2);

    private native int nativeSetARDrone3PictureSettingsVideoStabilizationMode(long j, int i, int i2);

    private native int nativeSetARDrone3PilotingAutoTakeOffMode(long j, int i, byte b);

    private native int nativeSetARDrone3PilotingCancelMoveTo(long j, int i);

    private native int nativeSetARDrone3PilotingCircle(long j, int i, int i2);

    private native int nativeSetARDrone3PilotingEmergency(long j, int i);

    private native int nativeSetARDrone3PilotingEventMoveByEnd(long j, int i, float f, float f2, float f3, float f4, int i2);

    private native int nativeSetARDrone3PilotingFlatTrim(long j, int i);

    private native int nativeSetARDrone3PilotingLanding(long j, int i);

    private native int nativeSetARDrone3PilotingMoveBy(long j, int i, float f, float f2, float f3, float f4);

    private native int nativeSetARDrone3PilotingMoveTo(long j, int i, double d, double d2, double d3, int i2, float f);

    private native int nativeSetARDrone3PilotingNavigateHome(long j, int i, byte b);

    private native int nativeSetARDrone3PilotingPCMD(long j, int i, byte b, byte b2, byte b3, byte b4, byte b5, int i2);

    private native int nativeSetARDrone3PilotingSettingsAbsolutControl(long j, int i, byte b);

    private native int nativeSetARDrone3PilotingSettingsBankedTurn(long j, int i, byte b);

    private native int nativeSetARDrone3PilotingSettingsCirclingAltitude(long j, int i, short s);

    private native int nativeSetARDrone3PilotingSettingsCirclingDirection(long j, int i, int i2);

    private native int nativeSetARDrone3PilotingSettingsCirclingRadius(long j, int i, short s);

    private native int nativeSetARDrone3PilotingSettingsMaxAltitude(long j, int i, float f);

    private native int nativeSetARDrone3PilotingSettingsMaxDistance(long j, int i, float f);

    private native int nativeSetARDrone3PilotingSettingsMaxTilt(long j, int i, float f);

    private native int nativeSetARDrone3PilotingSettingsMinAltitude(long j, int i, float f);

    private native int nativeSetARDrone3PilotingSettingsNoFlyOverMaxDistance(long j, int i, byte b);

    private native int nativeSetARDrone3PilotingSettingsPitchMode(long j, int i, int i2);

    /* renamed from: nativeSetARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalAcceleration */
    private native int m551x4014590a(long j, int i, float f);

    /* renamed from: nativeSetARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalSpeed */
    private native int m552xf801a2fd(long j, int i, float f);

    /* renamed from: nativeSetARDrone3PilotingSettingsSetAutonomousFlightMaxRotationSpeed */
    private native int m553x372ffe63(long j, int i, float f);

    /* renamed from: nativeSetARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalAcceleration */
    private native int m554xa598a2dc(long j, int i, float f);

    /* renamed from: nativeSetARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalSpeed */
    private native int m555xe5bce16b(long j, int i, float f);

    private native int nativeSetARDrone3PilotingSettingsStateAbsolutControlChanged(long j, int i, byte b);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalAcceleration */
    private native int m556x660618bb(long j, int i, float f);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalSpeed */
    private native int m557x9880b86c(long j, int i, float f);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAutonomousFlightMaxRotationSpeed */
    private native int m558x3e03a392(long j, int i, float f);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalAcceleration */
    private native int m559xfa89acd(long j, int i, float f);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalSpeed */
    private native int m560xec90869a(long j, int i, float f);

    private native int nativeSetARDrone3PilotingSettingsStateBankedTurnChanged(long j, int i, byte b);

    private native int nativeSetARDrone3PilotingSettingsStateCirclingAltitudeChanged(long j, int i, short s, short s2, short s3);

    private native int nativeSetARDrone3PilotingSettingsStateCirclingDirectionChanged(long j, int i, int i2);

    private native int nativeSetARDrone3PilotingSettingsStateCirclingRadiusChanged(long j, int i, short s, short s2, short s3);

    private native int nativeSetARDrone3PilotingSettingsStateMaxAltitudeChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetARDrone3PilotingSettingsStateMaxDistanceChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetARDrone3PilotingSettingsStateMaxTiltChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetARDrone3PilotingSettingsStateMinAltitudeChanged(long j, int i, float f, float f2, float f3);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateNoFlyOverMaxDistanceChanged */
    private native int m561x11f80d54(long j, int i, byte b);

    private native int nativeSetARDrone3PilotingSettingsStatePitchModeChanged(long j, int i, int i2);

    private native int nativeSetARDrone3PilotingStateAirSpeedChanged(long j, int i, float f);

    private native int nativeSetARDrone3PilotingStateAlertStateChanged(long j, int i, int i2);

    private native int nativeSetARDrone3PilotingStateAltitudeChanged(long j, int i, double d);

    private native int nativeSetARDrone3PilotingStateAttitudeChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetARDrone3PilotingStateAutoTakeOffModeChanged(long j, int i, byte b);

    private native int nativeSetARDrone3PilotingStateFlatTrimChanged(long j, int i);

    private native int nativeSetARDrone3PilotingStateFlyingStateChanged(long j, int i, int i2);

    private native int nativeSetARDrone3PilotingStateGpsLocationChanged(long j, int i, double d, double d2, double d3, byte b, byte b2, byte b3);

    private native int nativeSetARDrone3PilotingStateLandingStateChanged(long j, int i, int i2);

    private native int nativeSetARDrone3PilotingStateMoveToChanged(long j, int i, double d, double d2, double d3, int i2, float f, int i3);

    private native int nativeSetARDrone3PilotingStateNavigateHomeStateChanged(long j, int i, int i2, int i3);

    private native int nativeSetARDrone3PilotingStatePositionChanged(long j, int i, double d, double d2, double d3);

    private native int nativeSetARDrone3PilotingStateSpeedChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetARDrone3PilotingTakeOff(long j, int i);

    private native int nativeSetARDrone3PilotingUserTakeOff(long j, int i, byte b);

    private native int nativeSetARDrone3SettingsStateCPUID(long j, int i, String str);

    private native int nativeSetARDrone3SettingsStateMotorErrorLastErrorChanged(long j, int i, int i2);

    private native int nativeSetARDrone3SettingsStateMotorErrorStateChanged(long j, int i, byte b, int i2);

    private native int nativeSetARDrone3SettingsStateMotorFlightsStatusChanged(long j, int i, short s, short s2, int i2);

    private native int nativeSetARDrone3SettingsStateMotorSoftwareVersionChanged(long j, int i, String str);

    private native int nativeSetARDrone3SettingsStateP7ID(long j, int i, String str);

    private native int nativeSetARDrone3SettingsStateProductGPSVersionChanged(long j, int i, String str, String str2);

    private native int nativeSetARDrone3SettingsStateProductMotorVersionListChanged(long j, int i, byte b, String str, String str2, String str3);

    private native int nativeSetARDrone3SpeedSettingsHullProtection(long j, int i, byte b);

    private native int nativeSetARDrone3SpeedSettingsMaxPitchRollRotationSpeed(long j, int i, float f);

    private native int nativeSetARDrone3SpeedSettingsMaxRotationSpeed(long j, int i, float f);

    private native int nativeSetARDrone3SpeedSettingsMaxVerticalSpeed(long j, int i, float f);

    private native int nativeSetARDrone3SpeedSettingsOutdoor(long j, int i, byte b);

    private native int nativeSetARDrone3SpeedSettingsStateHullProtectionChanged(long j, int i, byte b);

    /* renamed from: nativeSetARDrone3SpeedSettingsStateMaxPitchRollRotationSpeedChanged */
    private native int m562x6b65eaa2(long j, int i, float f, float f2, float f3);

    private native int nativeSetARDrone3SpeedSettingsStateMaxRotationSpeedChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetARDrone3SpeedSettingsStateMaxVerticalSpeedChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetARDrone3SpeedSettingsStateOutdoorChanged(long j, int i, byte b);

    private native int nativeSetCommonARLibsVersionsStateControllerLibARCommandsVersion(long j, int i, String str);

    private native int nativeSetCommonARLibsVersionsStateDeviceLibARCommandsVersion(long j, int i, String str);

    /* renamed from: nativeSetCommonARLibsVersionsStateSkyControllerLibARCommandsVersion */
    private native int m563x5b611fd6(long j, int i, String str);

    private native int nativeSetCommonAccessoryConfig(long j, int i, int i2);

    private native int nativeSetCommonAccessoryStateAccessoryConfigChanged(long j, int i, int i2, int i3);

    private native int nativeSetCommonAccessoryStateAccessoryConfigModificationEnabled(long j, int i, byte b);

    private native int nativeSetCommonAccessoryStateSupportedAccessoriesListChanged(long j, int i, int i2);

    private native int nativeSetCommonAnimationsStartAnimation(long j, int i, int i2);

    private native int nativeSetCommonAnimationsStateList(long j, int i, int i2, int i3, int i4);

    private native int nativeSetCommonAnimationsStopAllAnimations(long j, int i);

    private native int nativeSetCommonAnimationsStopAnimation(long j, int i, int i2);

    private native int nativeSetCommonAudioControllerReadyForStreaming(long j, int i, byte b);

    private native int nativeSetCommonAudioStateAudioStreamingRunning(long j, int i, byte b);

    private native int nativeSetCommonCalibrationMagnetoCalibration(long j, int i, byte b);

    private native int nativeSetCommonCalibrationPitotCalibration(long j, int i, byte b);

    /* renamed from: nativeSetCommonCalibrationStateMagnetoCalibrationAxisToCalibrateChanged */
    private native int m564x786bdf6b(long j, int i, int i2);

    private native int nativeSetCommonCalibrationStateMagnetoCalibrationRequiredState(long j, int i, byte b);

    private native int nativeSetCommonCalibrationStateMagnetoCalibrationStartedChanged(long j, int i, byte b);

    private native int nativeSetCommonCalibrationStateMagnetoCalibrationStateChanged(long j, int i, byte b, byte b2, byte b3, byte b4);

    private native int nativeSetCommonCalibrationStatePitotCalibrationStateChanged(long j, int i, int i2, byte b);

    private native int nativeSetCommonCameraSettingsStateCameraSettingsChanged(long j, int i, float f, float f2, float f3, float f4, float f5);

    private native int nativeSetCommonChargerSetMaxChargeRate(long j, int i, int i2);

    private native int nativeSetCommonChargerStateChargingInfo(long j, int i, int i2, int i3, byte b, byte b2);

    private native int nativeSetCommonChargerStateCurrentChargeStateChanged(long j, int i, int i2, int i3);

    private native int nativeSetCommonChargerStateLastChargeRateChanged(long j, int i, int i2);

    private native int nativeSetCommonChargerStateMaxChargeRateChanged(long j, int i, int i2);

    private native int nativeSetCommonCommonAllStates(long j, int i);

    private native int nativeSetCommonCommonCurrentDate(long j, int i, String str);

    private native int nativeSetCommonCommonCurrentTime(long j, int i, String str);

    private native int nativeSetCommonCommonReboot(long j, int i);

    private native int nativeSetCommonCommonStateAllStatesChanged(long j, int i);

    private native int nativeSetCommonCommonStateBatteryStateChanged(long j, int i, byte b);

    private native int nativeSetCommonCommonStateCountryListKnown(long j, int i, byte b, String str);

    private native int nativeSetCommonCommonStateCurrentDateChanged(long j, int i, String str);

    private native int nativeSetCommonCommonStateCurrentTimeChanged(long j, int i, String str);

    private native int nativeSetCommonCommonStateDeprecatedMassStorageContentChanged(long j, int i, byte b, short s, short s2, short s3, short s4);

    private native int nativeSetCommonCommonStateMassStorageContent(long j, int i, byte b, short s, short s2, short s3, short s4, short s5);

    private native int nativeSetCommonCommonStateMassStorageContentForCurrentRun(long j, int i, byte b, short s, short s2, short s3);

    private native int nativeSetCommonCommonStateMassStorageInfoRemainingListChanged(long j, int i, int i2, short s, int i3);

    private native int nativeSetCommonCommonStateMassStorageInfoStateListChanged(long j, int i, byte b, int i2, int i3, byte b2, byte b3, byte b4);

    private native int nativeSetCommonCommonStateMassStorageStateListChanged(long j, int i, byte b, String str);

    private native int nativeSetCommonCommonStateProductModel(long j, int i, int i2);

    private native int nativeSetCommonCommonStateSensorsStatesListChanged(long j, int i, int i2, byte b);

    private native int nativeSetCommonCommonStateVideoRecordingTimestamp(long j, int i, long j2, long j3);

    private native int nativeSetCommonCommonStateWifiSignalChanged(long j, int i, short s);

    private native int nativeSetCommonControllerIsPiloting(long j, int i, byte b);

    private native int nativeSetCommonFactoryReset(long j, int i);

    private native int nativeSetCommonFlightPlanEventSpeedBridleEvent(long j, int i);

    private native int nativeSetCommonFlightPlanEventStartingErrorEvent(long j, int i);

    private native int nativeSetCommonFlightPlanSettingsReturnHomeOnDisconnect(long j, int i, byte b);

    /* renamed from: nativeSetCommonFlightPlanSettingsStateReturnHomeOnDisconnectChanged */
    private native int m565x24f80c6b(long j, int i, byte b, byte b2);

    private native int nativeSetCommonFlightPlanStateAvailabilityStateChanged(long j, int i, byte b);

    private native int nativeSetCommonFlightPlanStateComponentStateListChanged(long j, int i, int i2, byte b);

    private native int nativeSetCommonFlightPlanStateLockStateChanged(long j, int i, byte b);

    private native int nativeSetCommonGPSControllerPositionForRun(long j, int i, double d, double d2);

    private native int nativeSetCommonHeadlightsIntensity(long j, int i, byte b, byte b2);

    private native int nativeSetCommonHeadlightsStateIntensityChanged(long j, int i, byte b, byte b2);

    private native int nativeSetCommonMavlinkPause(long j, int i);

    private native int nativeSetCommonMavlinkStart(long j, int i, String str, int i2);

    private native int nativeSetCommonMavlinkStateMavlinkFilePlayingStateChanged(long j, int i, int i2, String str, int i3);

    private native int nativeSetCommonMavlinkStateMavlinkPlayErrorStateChanged(long j, int i, int i2);

    private native int nativeSetCommonMavlinkStateMissionItemExecuted(long j, int i, int i2);

    private native int nativeSetCommonMavlinkStop(long j, int i);

    private native int nativeSetCommonNetworkDisconnect(long j, int i);

    private native int nativeSetCommonNetworkEventDisconnection(long j, int i, int i2);

    private native int nativeSetCommonOverHeatStateOverHeatChanged(long j, int i);

    private native int nativeSetCommonOverHeatStateOverHeatRegulationChanged(long j, int i, byte b);

    private native int nativeSetCommonOverHeatSwitchOff(long j, int i);

    private native int nativeSetCommonOverHeatVentilate(long j, int i);

    private native int nativeSetCommonRunStateRunIdChanged(long j, int i, String str);

    private native int nativeSetCommonSettingsAllSettings(long j, int i);

    private native int nativeSetCommonSettingsAutoCountry(long j, int i, byte b);

    private native int nativeSetCommonSettingsCountry(long j, int i, String str);

    private native int nativeSetCommonSettingsProductName(long j, int i, String str);

    private native int nativeSetCommonSettingsReset(long j, int i);

    private native int nativeSetCommonSettingsStateAllSettingsChanged(long j, int i);

    private native int nativeSetCommonSettingsStateAutoCountryChanged(long j, int i, byte b);

    private native int nativeSetCommonSettingsStateCountryChanged(long j, int i, String str);

    private native int nativeSetCommonSettingsStateProductNameChanged(long j, int i, String str);

    private native int nativeSetCommonSettingsStateProductSerialHighChanged(long j, int i, String str);

    private native int nativeSetCommonSettingsStateProductSerialLowChanged(long j, int i, String str);

    private native int nativeSetCommonSettingsStateProductVersionChanged(long j, int i, String str, String str2);

    private native int nativeSetCommonSettingsStateResetChanged(long j, int i);

    private native int nativeSetCommonWifiSettingsOutdoorSetting(long j, int i, byte b);

    private native int nativeSetCommonWifiSettingsStateOutdoorSettingsChanged(long j, int i, byte b);

    private native int nativeSetControllerInfoBarometer(long j, int i, float f, double d);

    private native int nativeSetControllerInfoGps(long j, int i, double d, double d2, float f, float f2, float f3, float f4, float f5, float f6, double d3);

    private native int nativeSetDebugGetAllSettings(long j, int i);

    private native int nativeSetDebugSetSetting(long j, int i, short s, String str);

    private native int nativeSetDebugSettingsInfo(long j, int i, byte b, short s, String str, int i2, int i3, String str2, String str3, String str4, String str5);

    private native int nativeSetDebugSettingsList(long j, int i, short s, String str);

    private native int nativeSetDroneManagerAuthenticationFailed(long j, int i, String str, short s, String str2);

    private native int nativeSetDroneManagerConnect(long j, int i, String str, String str2);

    private native int nativeSetDroneManagerConnectionRefused(long j, int i, String str, short s, String str2);

    private native int nativeSetDroneManagerConnectionState(long j, int i, int i2, String str, short s, String str2);

    private native int nativeSetDroneManagerDiscoverDrones(long j, int i);

    private native int nativeSetDroneManagerDroneListItem(long j, int i, String str, short s, String str2, byte b, byte b2, byte b3, int i2, byte b4, byte b5, byte b6);

    private native int nativeSetDroneManagerForget(long j, int i, String str);

    private native int nativeSetDroneManagerKnownDroneItem(long j, int i, String str, short s, String str2, int i2, byte b, byte b2);

    private native int nativeSetFollowMeBoomerangAnimConfig(long j, int i, byte b, float f, float f2);

    private native int nativeSetFollowMeCandleAnimConfig(long j, int i, byte b, float f, float f2);

    private native int nativeSetFollowMeConfigureGeographic(long j, int i, byte b, float f, float f2, float f3);

    private native int nativeSetFollowMeConfigureRelative(long j, int i, byte b, float f, float f2, float f3);

    private native int nativeSetFollowMeDollySlideAnimConfig(long j, int i, byte b, float f, float f2, float f3);

    private native int nativeSetFollowMeGeographicConfig(long j, int i, byte b, float f, float f2, float f3);

    private native int nativeSetFollowMeHelicoidAnimConfig(long j, int i, byte b, float f, float f2, float f3);

    private native int nativeSetFollowMeModeInfo(long j, int i, int i2, short s, short s2);

    private native int nativeSetFollowMeRelativeConfig(long j, int i, byte b, float f, float f2, float f3);

    private native int nativeSetFollowMeStart(long j, int i, int i2);

    private native int nativeSetFollowMeStartBoomerangAnim(long j, int i, byte b, float f, float f2);

    private native int nativeSetFollowMeStartCandleAnim(long j, int i, byte b, float f, float f2);

    private native int nativeSetFollowMeStartDollySlideAnim(long j, int i, byte b, float f, float f2, float f3);

    private native int nativeSetFollowMeStartHelicoidAnim(long j, int i, byte b, float f, float f2, float f3);

    private native int nativeSetFollowMeStartSwingAnim(long j, int i, byte b, float f, float f2);

    private native int nativeSetFollowMeState(long j, int i, int i2, int i3, int i4, short s);

    private native int nativeSetFollowMeStop(long j, int i);

    private native int nativeSetFollowMeStopAnimation(long j, int i);

    private native int nativeSetFollowMeSwingAnimConfig(long j, int i, byte b, float f, float f2);

    private native int nativeSetFollowMeTargetFramingPosition(long j, int i, byte b, byte b2);

    private native int nativeSetFollowMeTargetFramingPositionChanged(long j, int i, byte b, byte b2);

    private native int nativeSetFollowMeTargetImageDetection(long j, int i, float f, float f2, float f3, byte b, byte b2, long j2);

    private native int nativeSetFollowMeTargetImageDetectionState(long j, int i, int i2);

    private native int nativeSetFollowMeTargetTrajectory(long j, int i, double d, double d2, float f, float f2, float f3, float f4);

    private native int nativeSetGenericDefault(long j, int i);

    private native int nativeSetGenericDroneSettingsChanged(long j, int i, int i2, float f, float f2, float f3, int i3, float f4, float f5, float f6, int i4, float f7, float f8, float f9, int i5, byte b, int i6, float f10, float f11, float f12, int i7, float f13, float f14, float f15, int i8, float f16, float f17, float f18, int i9, short s, int i10, ARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_HOMETYPECHANGED_TYPE_ENUM arcommands_ardrone3_gpssettingsstate_hometypechanged_type_enum, int i11, C1395xb64e46a9 c1395xb64e46a9, int i12, byte b2);

    private native int nativeSetGenericSetDroneSettings(long j, int i, int i2, float f, int i3, float f2, int i4, float f3, int i5, byte b, int i6, float f4, int i7, float f5, int i8, float f6, int i9, short s, int i10, ARCOMMANDS_ARDRONE3_GPSSETTINGS_HOMETYPE_TYPE_ENUM arcommands_ardrone3_gpssettings_hometype_type_enum, int i11, C1399x62185770 c1399x62185770, int i12, byte b2);

    private native int nativeSetJumpingSumoAnimationsJump(long j, int i, int i2);

    private native int nativeSetJumpingSumoAnimationsJumpCancel(long j, int i);

    private native int nativeSetJumpingSumoAnimationsJumpLoad(long j, int i);

    private native int nativeSetJumpingSumoAnimationsJumpStop(long j, int i);

    private native int nativeSetJumpingSumoAnimationsSimpleAnimation(long j, int i, int i2);

    private native int nativeSetJumpingSumoAnimationsStateJumpLoadChanged(long j, int i, int i2);

    private native int nativeSetJumpingSumoAnimationsStateJumpMotorProblemChanged(long j, int i, int i2);

    private native int nativeSetJumpingSumoAnimationsStateJumpTypeChanged(long j, int i, int i2);

    private native int nativeSetJumpingSumoAudioSettingsMasterVolume(long j, int i, byte b);

    private native int nativeSetJumpingSumoAudioSettingsStateMasterVolumeChanged(long j, int i, byte b);

    private native int nativeSetJumpingSumoAudioSettingsStateThemeChanged(long j, int i, int i2);

    private native int nativeSetJumpingSumoAudioSettingsTheme(long j, int i, int i2);

    private native int nativeSetJumpingSumoMediaRecordEventPictureEventChanged(long j, int i, int i2, int i3);

    private native int nativeSetJumpingSumoMediaRecordEventVideoEventChanged(long j, int i, int i2, int i3);

    private native int nativeSetJumpingSumoMediaRecordPicture(long j, int i, byte b);

    private native int nativeSetJumpingSumoMediaRecordPictureV2(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordStatePictureStateChanged(long j, int i, byte b, byte b2);

    private native int nativeSetJumpingSumoMediaRecordStatePictureStateChangedV2(long j, int i, int i2, int i3);

    private native int nativeSetJumpingSumoMediaRecordStateVideoStateChanged(long j, int i, int i2, byte b);

    private native int nativeSetJumpingSumoMediaRecordStateVideoStateChangedV2(long j, int i, int i2, int i3);

    private native int nativeSetJumpingSumoMediaRecordVideo(long j, int i, int i2, byte b);

    private native int nativeSetJumpingSumoMediaRecordVideoV2(long j, int i, int i2);

    private native int nativeSetJumpingSumoMediaStreamingStateVideoEnableChanged(long j, int i, int i2);

    private native int nativeSetJumpingSumoMediaStreamingVideoEnable(long j, int i, byte b);

    private native int nativeSetJumpingSumoNetworkSettingsStateWifiSelectionChanged(long j, int i, int i2, int i3, byte b);

    private native int nativeSetJumpingSumoNetworkSettingsWifiSelection(long j, int i, int i2, int i3, byte b);

    private native int nativeSetJumpingSumoNetworkStateAllWifiAuthChannelChanged(long j, int i);

    private native int nativeSetJumpingSumoNetworkStateAllWifiScanChanged(long j, int i);

    private native int nativeSetJumpingSumoNetworkStateLinkQualityChanged(long j, int i, byte b);

    private native int nativeSetJumpingSumoNetworkStateWifiAuthChannelListChanged(long j, int i, int i2, byte b, byte b2);

    private native int nativeSetJumpingSumoNetworkStateWifiScanListChanged(long j, int i, String str, short s, int i2, byte b);

    private native int nativeSetJumpingSumoNetworkWifiAuthChannel(long j, int i);

    private native int nativeSetJumpingSumoNetworkWifiScan(long j, int i, int i2);

    private native int nativeSetJumpingSumoPilotingAddCapOffset(long j, int i, float f);

    private native int nativeSetJumpingSumoPilotingPCMD(long j, int i, byte b, byte b2, byte b3);

    private native int nativeSetJumpingSumoPilotingPosture(long j, int i, int i2);

    private native int nativeSetJumpingSumoPilotingStateAlertStateChanged(long j, int i, int i2);

    private native int nativeSetJumpingSumoPilotingStatePostureChanged(long j, int i, int i2);

    private native int nativeSetJumpingSumoPilotingStateSpeedChanged(long j, int i, byte b, short s);

    private native int nativeSetJumpingSumoRoadPlanAllScriptsMetadata(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanPlayScript(long j, int i, String str);

    private native int nativeSetJumpingSumoRoadPlanScriptDelete(long j, int i, String str);

    private native int nativeSetJumpingSumoRoadPlanScriptUploaded(long j, int i, String str, String str2);

    private native int nativeSetJumpingSumoRoadPlanStateAllScriptsMetadataChanged(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanStatePlayScriptChanged(long j, int i, int i2);

    private native int nativeSetJumpingSumoRoadPlanStateScriptDeleteChanged(long j, int i, int i2);

    private native int nativeSetJumpingSumoRoadPlanStateScriptMetadataListChanged(long j, int i, String str, byte b, String str2, String str3, long j2);

    private native int nativeSetJumpingSumoRoadPlanStateScriptUploadChanged(long j, int i, int i2);

    private native int nativeSetJumpingSumoSettingsStateProductGPSVersionChanged(long j, int i, String str, String str2);

    private native int nativeSetJumpingSumoSpeedSettingsOutdoor(long j, int i, byte b);

    private native int nativeSetJumpingSumoSpeedSettingsStateOutdoorChanged(long j, int i, byte b);

    private native int nativeSetJumpingSumoVideoSettingsAutorecord(long j, int i, byte b);

    private native int nativeSetJumpingSumoVideoSettingsStateAutorecordChanged(long j, int i, byte b);

    private native int nativeSetMapperActiveProduct(long j, int i, short s);

    private native int nativeSetMapperApplicationAxisEvent(long j, int i, int i2, byte b);

    private native int nativeSetMapperApplicationButtonEvent(long j, int i, int i2);

    private native int nativeSetMapperAxisMappingItem(long j, int i, int i2, short s, int i3, int i4, int i5, byte b);

    private native int nativeSetMapperButtonMappingItem(long j, int i, int i2, short s, int i3, int i4, byte b);

    private native int nativeSetMapperExpoMapItem(long j, int i, int i2, short s, int i3, int i4, byte b);

    private native int nativeSetMapperGrab(long j, int i, int i2, int i3);

    private native int nativeSetMapperGrabAxisEvent(long j, int i, int i2, byte b);

    private native int nativeSetMapperGrabButtonEvent(long j, int i, int i2, int i3);

    private native int nativeSetMapperGrabState(long j, int i, int i2, int i3, int i4);

    private native int nativeSetMapperInvertedMapItem(long j, int i, int i2, short s, int i3, byte b, byte b2);

    private native int nativeSetMapperMapAxisAction(long j, int i, short s, int i2, int i3, int i4);

    private native int nativeSetMapperMapButtonAction(long j, int i, short s, int i2, int i3);

    private native int nativeSetMapperMiniAxisMappingItem(long j, int i, short s, byte b, int i2, byte b2, int i3, byte b3);

    private native int nativeSetMapperMiniButtonMappingItem(long j, int i, short s, byte b, int i2, int i3, byte b2);

    private native int nativeSetMapperMiniMapAxisAction(long j, int i, byte b, int i2, byte b2, int i3);

    private native int nativeSetMapperMiniMapButtonAction(long j, int i, byte b, int i2, int i3);

    private native int nativeSetMapperMiniResetMapping(long j, int i, byte b);

    private native int nativeSetMapperResetMapping(long j, int i, short s);

    private native int nativeSetMapperSetExpo(long j, int i, short s, int i2, int i3);

    private native int nativeSetMapperSetInverted(long j, int i, short s, int i2, byte b);

    private native int nativeSetMiniDroneAnimationsCap(long j, int i, short s);

    private native int nativeSetMiniDroneAnimationsFlip(long j, int i, int i2);

    private native int nativeSetMiniDroneConfigurationControllerName(long j, int i, String str);

    private native int nativeSetMiniDroneConfigurationControllerType(long j, int i, String str);

    private native int nativeSetMiniDroneFloodControlStateFloodControlChanged(long j, int i, short s);

    private native int nativeSetMiniDroneGPSControllerLatitudeForRun(long j, int i, double d);

    private native int nativeSetMiniDroneGPSControllerLongitudeForRun(long j, int i, double d);

    private native int nativeSetMiniDroneMediaRecordEventPictureEventChanged(long j, int i, int i2, int i3);

    private native int nativeSetMiniDroneMediaRecordPicture(long j, int i, byte b);

    private native int nativeSetMiniDroneMediaRecordPictureV2(long j, int i);

    private native int nativeSetMiniDroneMediaRecordStatePictureStateChanged(long j, int i, byte b, byte b2);

    private native int nativeSetMiniDroneMediaRecordStatePictureStateChangedV2(long j, int i, int i2, int i3);

    private native int nativeSetMiniDroneNavigationDataStateDronePosition(long j, int i, float f, float f2, short s, short s2, short s3);

    private native int nativeSetMiniDronePilotingAutoTakeOffMode(long j, int i, byte b);

    private native int nativeSetMiniDronePilotingEmergency(long j, int i);

    private native int nativeSetMiniDronePilotingFlatTrim(long j, int i);

    private native int nativeSetMiniDronePilotingFlyingMode(long j, int i, int i2);

    private native int nativeSetMiniDronePilotingLanding(long j, int i);

    private native int nativeSetMiniDronePilotingPCMD(long j, int i, byte b, byte b2, byte b3, byte b4, byte b5, int i2);

    private native int nativeSetMiniDronePilotingPlaneGearBox(long j, int i, int i2);

    private native int nativeSetMiniDronePilotingSettingsBankedTurn(long j, int i, byte b);

    private native int nativeSetMiniDronePilotingSettingsMaxAltitude(long j, int i, float f);

    private native int nativeSetMiniDronePilotingSettingsMaxTilt(long j, int i, float f);

    private native int nativeSetMiniDronePilotingSettingsStateBankedTurnChanged(long j, int i, byte b);

    private native int nativeSetMiniDronePilotingSettingsStateMaxAltitudeChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetMiniDronePilotingSettingsStateMaxTiltChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetMiniDronePilotingStateAlertStateChanged(long j, int i, int i2);

    private native int nativeSetMiniDronePilotingStateAutoTakeOffModeChanged(long j, int i, byte b);

    private native int nativeSetMiniDronePilotingStateFlatTrimChanged(long j, int i);

    private native int nativeSetMiniDronePilotingStateFlyingModeChanged(long j, int i, int i2);

    private native int nativeSetMiniDronePilotingStateFlyingStateChanged(long j, int i, int i2);

    private native int nativeSetMiniDronePilotingStatePlaneGearBoxChanged(long j, int i, int i2);

    private native int nativeSetMiniDronePilotingTakeOff(long j, int i);

    private native int nativeSetMiniDroneRemoteControllerSetPairedRemote(long j, int i, short s, short s2, short s3);

    private native int nativeSetMiniDroneSettingsCutOutMode(long j, int i, byte b);

    private native int nativeSetMiniDroneSettingsStateCutOutModeChanged(long j, int i, byte b);

    private native int nativeSetMiniDroneSettingsStateProductInertialVersionChanged(long j, int i, String str, String str2);

    private native int nativeSetMiniDroneSettingsStateProductMotorsVersionChanged(long j, int i, byte b, String str, String str2, String str3);

    private native int nativeSetMiniDroneSpeedSettingsMaxHorizontalSpeed(long j, int i, float f);

    private native int nativeSetMiniDroneSpeedSettingsMaxPlaneModeRotationSpeed(long j, int i, float f);

    private native int nativeSetMiniDroneSpeedSettingsMaxRotationSpeed(long j, int i, float f);

    private native int nativeSetMiniDroneSpeedSettingsMaxVerticalSpeed(long j, int i, float f);

    private native int nativeSetMiniDroneSpeedSettingsStateMaxHorizontalSpeedChanged(long j, int i, float f, float f2, float f3);

    /* renamed from: nativeSetMiniDroneSpeedSettingsStateMaxPlaneModeRotationSpeedChanged */
    private native int m566xec057123(long j, int i, float f, float f2, float f3);

    private native int nativeSetMiniDroneSpeedSettingsStateMaxRotationSpeedChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetMiniDroneSpeedSettingsStateMaxVerticalSpeedChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetMiniDroneSpeedSettingsStateWheelsChanged(long j, int i, byte b);

    private native int nativeSetMiniDroneSpeedSettingsWheels(long j, int i, byte b);

    private native int nativeSetMiniDroneUsbAccessoryClawControl(long j, int i, byte b, int i2);

    private native int nativeSetMiniDroneUsbAccessoryGunControl(long j, int i, byte b, int i2);

    private native int nativeSetMiniDroneUsbAccessoryLightControl(long j, int i, byte b, int i2, byte b2);

    private native int nativeSetMiniDroneUsbAccessoryStateClawState(long j, int i, byte b, int i2, byte b2);

    private native int nativeSetMiniDroneUsbAccessoryStateGunState(long j, int i, byte b, int i2, byte b2);

    private native int nativeSetMiniDroneUsbAccessoryStateLightState(long j, int i, byte b, int i2, byte b2, byte b3);

    private native int nativeSetPowerupMediaRecordEventPictureEventChanged(long j, int i, int i2, int i3);

    private native int nativeSetPowerupMediaRecordEventVideoEventChanged(long j, int i, int i2, int i3);

    private native int nativeSetPowerupMediaRecordPictureV2(long j, int i);

    private native int nativeSetPowerupMediaRecordStatePictureStateChangedV2(long j, int i, int i2, int i3);

    private native int nativeSetPowerupMediaRecordStateVideoStateChangedV2(long j, int i, int i2, int i3);

    private native int nativeSetPowerupMediaRecordVideoV2(long j, int i, int i2);

    private native int nativeSetPowerupMediaStreamingStateVideoEnableChanged(long j, int i, int i2);

    private native int nativeSetPowerupMediaStreamingVideoEnable(long j, int i, byte b);

    private native int nativeSetPowerupNetworkSettingsStateWifiSelectionChanged(long j, int i, int i2, int i3, byte b);

    private native int nativeSetPowerupNetworkSettingsWifiSelection(long j, int i, int i2, int i3, byte b);

    private native int nativeSetPowerupNetworkStateAllWifiAuthChannelChanged(long j, int i);

    private native int nativeSetPowerupNetworkStateAllWifiScanChanged(long j, int i);

    private native int nativeSetPowerupNetworkStateLinkQualityChanged(long j, int i, byte b);

    private native int nativeSetPowerupNetworkStateWifiAuthChannelListChanged(long j, int i, int i2, byte b, byte b2);

    private native int nativeSetPowerupNetworkStateWifiScanListChanged(long j, int i, String str, short s, int i2, byte b);

    private native int nativeSetPowerupNetworkWifiAuthChannel(long j, int i);

    private native int nativeSetPowerupNetworkWifiScan(long j, int i, int i2);

    private native int nativeSetPowerupPilotingMotorMode(long j, int i, int i2);

    private native int nativeSetPowerupPilotingPCMD(long j, int i, byte b, byte b2, byte b3);

    private native int nativeSetPowerupPilotingSettingsSet(long j, int i, int i2, float f);

    private native int nativeSetPowerupPilotingSettingsStateSettingChanged(long j, int i, int i2, float f, float f2, float f3, byte b);

    private native int nativeSetPowerupPilotingStateAlertStateChanged(long j, int i, int i2);

    private native int nativeSetPowerupPilotingStateAltitudeChanged(long j, int i, float f);

    private native int nativeSetPowerupPilotingStateAttitudeChanged(long j, int i, float f, float f2, float f3);

    private native int nativeSetPowerupPilotingStateFlyingStateChanged(long j, int i, int i2);

    private native int nativeSetPowerupPilotingStateMotorModeChanged(long j, int i, int i2);

    private native int nativeSetPowerupPilotingUserTakeOff(long j, int i, byte b);

    private native int nativeSetPowerupSoundsBuzz(long j, int i, byte b);

    private native int nativeSetPowerupSoundsStateBuzzChanged(long j, int i, byte b);

    private native int nativeSetPowerupVideoSettingsAutorecord(long j, int i, byte b);

    private native int nativeSetPowerupVideoSettingsStateAutorecordChanged(long j, int i, byte b);

    private native int nativeSetPowerupVideoSettingsStateVideoModeChanged(long j, int i, int i2);

    private native int nativeSetPowerupVideoSettingsVideoMode(long j, int i, int i2);

    private native int nativeSetRcAbortCalibration(long j, int i);

    private native int nativeSetRcCalibrationState(long j, int i, int i2, int i3, int i4, int i5, byte b);

    private native int nativeSetRcChannelActionItem(long j, int i, int i2, int i3, int i4, byte b);

    private native int nativeSetRcChannelValue(long j, int i, byte b, int i2, short s, byte b2);

    private native int nativeSetRcChannelsMonitorState(long j, int i, byte b);

    private native int nativeSetRcEnableReceiver(long j, int i, byte b);

    private native int nativeSetRcInvertChannel(long j, int i, int i2, byte b);

    private native int nativeSetRcMonitorChannels(long j, int i, byte b);

    private native int nativeSetRcReceiverState(long j, int i, int i2, String str, byte b);

    private native int nativeSetRcResetCalibration(long j, int i);

    private native int nativeSetRcStartCalibration(long j, int i, int i2, int i3, int i4);

    private native int nativeSetSkyControllerAccessPointSettingsAccessPointChannel(long j, int i, byte b);

    private native int nativeSetSkyControllerAccessPointSettingsAccessPointSSID(long j, int i, String str);

    /* renamed from: nativeSetSkyControllerAccessPointSettingsStateAccessPointChannelChanged */
    private native int m567x6414b289(long j, int i, byte b);

    /* renamed from: nativeSetSkyControllerAccessPointSettingsStateAccessPointSSIDChanged */
    private native int m568x1269781(long j, int i, String str);

    /* renamed from: nativeSetSkyControllerAccessPointSettingsStateWifiSecurityChanged */
    private native int m569x609ba12b(long j, int i, int i2, String str);

    /* renamed from: nativeSetSkyControllerAccessPointSettingsStateWifiSelectionChanged */
    private native int m570x3cd4e891(long j, int i, int i2, int i3, byte b);

    private native int nativeSetSkyControllerAccessPointSettingsWifiSecurity(long j, int i, int i2, String str);

    private native int nativeSetSkyControllerAccessPointSettingsWifiSelection(long j, int i, int i2, int i3, byte b);

    private native int nativeSetSkyControllerAxisFiltersDefaultAxisFilters(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersGetCurrentAxisFilters(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersGetPresetAxisFilters(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersSetAxisFilter(long j, int i, int i2, String str);

    private native int nativeSetSkyControllerAxisFiltersStateAllCurrentFiltersSent(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersStateAllPresetFiltersSent(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersStateCurrentAxisFilters(long j, int i, int i2, String str);

    private native int nativeSetSkyControllerAxisFiltersStatePresetAxisFilters(long j, int i, String str, String str2);

    private native int nativeSetSkyControllerAxisMappingsDefaultAxisMapping(long j, int i);

    private native int nativeSetSkyControllerAxisMappingsGetAvailableAxisMappings(long j, int i);

    private native int nativeSetSkyControllerAxisMappingsGetCurrentAxisMappings(long j, int i);

    private native int nativeSetSkyControllerAxisMappingsSetAxisMapping(long j, int i, int i2, String str);

    /* renamed from: nativeSetSkyControllerAxisMappingsStateAllAvailableAxisMappingsSent */
    private native int m571x81a5915f(long j, int i);

    /* renamed from: nativeSetSkyControllerAxisMappingsStateAllCurrentAxisMappingsSent */
    private native int m572xbce56a0f(long j, int i);

    private native int nativeSetSkyControllerAxisMappingsStateAvailableAxisMappings(long j, int i, String str, String str2);

    private native int nativeSetSkyControllerAxisMappingsStateCurrentAxisMappings(long j, int i, int i2, String str);

    private native int nativeSetSkyControllerButtonEventsSettings(long j, int i);

    private native int nativeSetSkyControllerButtonMappingsDefaultButtonMapping(long j, int i);

    private native int nativeSetSkyControllerButtonMappingsGetAvailableButtonMappings(long j, int i);

    private native int nativeSetSkyControllerButtonMappingsGetCurrentButtonMappings(long j, int i);

    private native int nativeSetSkyControllerButtonMappingsSetButtonMapping(long j, int i, int i2, String str);

    /* renamed from: nativeSetSkyControllerButtonMappingsStateAllAvailableButtonsMappingsSent */
    private native int m573x3feda2e(long j, int i);

    /* renamed from: nativeSetSkyControllerButtonMappingsStateAllCurrentButtonMappingsSent */
    private native int m574xa83e1bef(long j, int i);

    private native int nativeSetSkyControllerButtonMappingsStateAvailableButtonMappings(long j, int i, String str, String str2);

    private native int nativeSetSkyControllerButtonMappingsStateCurrentButtonMappings(long j, int i, int i2, String str);

    /* renamed from: nativeSetSkyControllerCalibrationEnableMagnetoCalibrationQualityUpdates */
    private native int m575xebe21a4b(long j, int i, byte b);

    /* renamed from: nativeSetSkyControllerCalibrationStateMagnetoCalibrationQualityUpdatesState */
    private native int m576x6d1bcfe8(long j, int i, byte b);

    private native int nativeSetSkyControllerCalibrationStateMagnetoCalibrationState(long j, int i, int i2, byte b, byte b2, byte b3);

    private native int nativeSetSkyControllerCameraResetOrientation(long j, int i);

    private native int nativeSetSkyControllerCoPilotingSetPilotingSource(long j, int i, int i2);

    private native int nativeSetSkyControllerCoPilotingStatePilotingSource(long j, int i, int i2);

    private native int nativeSetSkyControllerCommonAllStates(long j, int i);

    private native int nativeSetSkyControllerCommonEventStateShutdown(long j, int i, int i2);

    private native int nativeSetSkyControllerCommonStateAllStatesChanged(long j, int i);

    private native int nativeSetSkyControllerDeviceConnectToDevice(long j, int i, String str);

    private native int nativeSetSkyControllerDeviceRequestCurrentDevice(long j, int i);

    private native int nativeSetSkyControllerDeviceRequestDeviceList(long j, int i);

    private native int nativeSetSkyControllerDeviceStateConnexionChanged(long j, int i, int i2, String str, short s);

    private native int nativeSetSkyControllerDeviceStateDeviceList(long j, int i, String str);

    private native int nativeSetSkyControllerFactoryReset(long j, int i);

    private native int nativeSetSkyControllerGamepadInfosGetGamepadControls(long j, int i);

    private native int nativeSetSkyControllerGamepadInfosStateAllGamepadControlsSent(long j, int i);

    private native int nativeSetSkyControllerGamepadInfosStateGamepadControl(long j, int i, int i2, int i3, String str);

    private native int nativeSetSkyControllerSettingsAllSettings(long j, int i);

    private native int nativeSetSkyControllerSettingsReset(long j, int i);

    private native int nativeSetSkyControllerSettingsStateAllSettingsChanged(long j, int i);

    private native int nativeSetSkyControllerSettingsStateProductSerialChanged(long j, int i, String str);

    private native int nativeSetSkyControllerSettingsStateProductVariantChanged(long j, int i, int i2);

    private native int nativeSetSkyControllerSettingsStateProductVersionChanged(long j, int i, String str, String str2);

    private native int nativeSetSkyControllerSettingsStateResetChanged(long j, int i);

    private native int nativeSetSkyControllerSkyControllerStateAttitudeChanged(long j, int i, float f, float f2, float f3, float f4);

    private native int nativeSetSkyControllerSkyControllerStateBatteryChanged(long j, int i, byte b);

    private native int nativeSetSkyControllerSkyControllerStateBatteryState(long j, int i, int i2);

    private native int nativeSetSkyControllerSkyControllerStateGpsFixChanged(long j, int i, byte b);

    private native int nativeSetSkyControllerSkyControllerStateGpsPositionChanged(long j, int i, double d, double d2, double d3, float f);

    private native int nativeSetSkyControllerWifiConnectToWifi(long j, int i, String str, String str2, String str3);

    private native int nativeSetSkyControllerWifiForgetWifi(long j, int i, String str);

    private native int nativeSetSkyControllerWifiRequestCurrentWifi(long j, int i);

    private native int nativeSetSkyControllerWifiRequestWifiList(long j, int i);

    private native int nativeSetSkyControllerWifiStateAllWifiAuthChannelChanged(long j, int i);

    private native int nativeSetSkyControllerWifiStateConnexionChanged(long j, int i, String str, int i2);

    private native int nativeSetSkyControllerWifiStateWifiAuthChannelListChanged(long j, int i, int i2, byte b, byte b2);

    private native int nativeSetSkyControllerWifiStateWifiAuthChannelListChangedV2(long j, int i, int i2, byte b, byte b2, byte b3);

    private native int nativeSetSkyControllerWifiStateWifiCountryChanged(long j, int i, String str);

    private native int nativeSetSkyControllerWifiStateWifiEnvironmentChanged(long j, int i, int i2);

    private native int nativeSetSkyControllerWifiStateWifiList(long j, int i, String str, String str2, byte b, byte b2, int i2, int i3);

    private native int nativeSetSkyControllerWifiStateWifiSignalChanged(long j, int i, byte b);

    private native int nativeSetSkyControllerWifiWifiAuthChannel(long j, int i);

    private native int nativeSetWifiApChannelChanged(long j, int i, int i2, int i3, byte b);

    private native int nativeSetWifiAuthorizedChannel(long j, int i, int i2, byte b, byte b2, byte b3);

    private native int nativeSetWifiCountryChanged(long j, int i, int i2, String str);

    private native int nativeSetWifiEnvironmentChanged(long j, int i, int i2);

    private native int nativeSetWifiRssiChanged(long j, int i, short s);

    private native int nativeSetWifiScan(long j, int i, byte b);

    private native int nativeSetWifiScannedItem(long j, int i, String str, short s, int i2, byte b, byte b2);

    private native int nativeSetWifiSecurityChanged(long j, int i, String str, int i2);

    private native int nativeSetWifiSetApChannel(long j, int i, int i2, int i3, byte b);

    private native int nativeSetWifiSetCountry(long j, int i, int i2, String str);

    private native int nativeSetWifiSetEnvironment(long j, int i, int i2);

    private native int nativeSetWifiSetSecurity(long j, int i, int i2, String str, int i3);

    private native int nativeSetWifiSupportedCountries(long j, int i, String str);

    private native int nativeSetWifiUpdateAuthorizedChannels(long j, int i);

    private static native String nativeStaticToString(long j, int i);

    private native String nativeToString(long j, int i);

    public ARCommand(int capacity) {
        super(capacity);
    }

    public ARCommand(ARNativeData oldData) {
        super(oldData);
    }

    public ARCommand(long data, int dataSize) {
        super(data, dataSize);
    }

    public ARCommand(ARNativeData oldData, int capacity) {
        super(oldData, capacity);
    }

    public String toString() {
        return nativeToString(this.pointer, this.used);
    }

    public static String arNativeDataToARCommandString(ARNativeData data) {
        if (data == null) {
            return "null";
        }
        String ret = nativeStaticToString(data.getData(), data.getDataSize());
        if (ret == null) {
            ret = data.toString();
        }
        return ret;
    }

    public ARCOMMANDS_DECODER_ERROR_ENUM decode() {
        ARCOMMANDS_DECODER_ERROR_ENUM err = ARCOMMANDS_DECODER_ERROR_ENUM.ARCOMMANDS_DECODER_ERROR;
        if (this.valid) {
            return _decoder.decode(this);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setGenericDefault() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetGenericDefault(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setGenericSetDroneSettings(ARCommandsGenericDroneSettings _settings) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetGenericSetDroneSettings(r15.pointer, r15.capacity, _settings.getArdrone3MaxAltitudeIsSet(), _settings.getArdrone3MaxAltitudeCurrent(), _settings.getArdrone3MaxTiltIsSet(), _settings.getArdrone3MaxTiltCurrent(), _settings.getArdrone3MaxDistanceIsSet(), _settings.getArdrone3MaxDistanceValue(), _settings.getArdrone3NoFlyOverMaxDistanceIsSet(), _settings.getArdrone3NoFlyOverMaxDistanceShouldNotFlyOver(), _settings.getArdrone3MaxVerticalSpeedIsSet(), _settings.getArdrone3MaxVerticalSpeedCurrent(), _settings.getArdrone3MaxRotationSpeedIsSet(), _settings.getArdrone3MaxRotationSpeedCurrent(), _settings.getArdrone3MaxPitchRollRotationSpeedIsSet(), _settings.getArdrone3MaxPitchRollRotationSpeedCurrent(), _settings.getArdrone3ReturnHomeDelayIsSet(), _settings.getArdrone3ReturnHomeDelayDelay(), _settings.getArdrone3HomeTypeIsSet(), _settings.getArdrone3HomeTypeType(), _settings.getArdrone3VideoStabilizationModeIsSet(), _settings.getArdrone3VideoStabilizationModeMode(), _settings.getArdrone3BankedTurnIsSet(), _settings.getArdrone3BankedTurnValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setGenericDroneSettingsChanged(ARCommandsGenericDroneSettingsChanged _settings) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetGenericDroneSettingsChanged(r15.pointer, r15.capacity, _settings.getArdrone3MaxAltitudeChangedIsSet(), _settings.getArdrone3MaxAltitudeChangedCurrent(), _settings.getArdrone3MaxAltitudeChangedMin(), _settings.getArdrone3MaxAltitudeChangedMax(), _settings.getArdrone3MaxTiltChangedIsSet(), _settings.getArdrone3MaxTiltChangedCurrent(), _settings.getArdrone3MaxTiltChangedMin(), _settings.getArdrone3MaxTiltChangedMax(), _settings.getArdrone3MaxDistanceChangedIsSet(), _settings.getArdrone3MaxDistanceChangedCurrent(), _settings.getArdrone3MaxDistanceChangedMin(), _settings.getArdrone3MaxDistanceChangedMax(), _settings.getArdrone3NoFlyOverMaxDistanceChangedIsSet(), _settings.getArdrone3NoFlyOverMaxDistanceChangedShouldNotFlyOver(), _settings.getArdrone3MaxVerticalSpeedChangedIsSet(), _settings.getArdrone3MaxVerticalSpeedChangedCurrent(), _settings.getArdrone3MaxVerticalSpeedChangedMin(), _settings.getArdrone3MaxVerticalSpeedChangedMax(), _settings.getArdrone3MaxRotationSpeedChangedIsSet(), _settings.getArdrone3MaxRotationSpeedChangedCurrent(), _settings.getArdrone3MaxRotationSpeedChangedMin(), _settings.getArdrone3MaxRotationSpeedChangedMax(), _settings.getArdrone3MaxPitchRollRotationSpeedChangedIsSet(), _settings.getArdrone3MaxPitchRollRotationSpeedChangedCurrent(), _settings.getArdrone3MaxPitchRollRotationSpeedChangedMin(), _settings.getArdrone3MaxPitchRollRotationSpeedChangedMax(), _settings.getArdrone3ReturnHomeDelayChangedIsSet(), _settings.getArdrone3ReturnHomeDelayChangedDelay(), _settings.getArdrone3HomeTypeChangedIsSet(), _settings.getArdrone3HomeTypeChangedType(), _settings.getArdrone3VideoStabilizationModeChangedIsSet(), _settings.getArdrone3VideoStabilizationModeChangedMode(), _settings.getArdrone3BankedTurnChangedIsSet(), _settings.getArdrone3BankedTurnChangedState());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingFlatTrim() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingFlatTrim(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingTakeOff() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingTakeOff(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingPCMD(byte _flag, byte _roll, byte _pitch, byte _yaw, byte _gaz, int _timestampAndSeqNum) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingPCMD(r10.pointer, r10.capacity, _flag, _roll, _pitch, _yaw, _gaz, _timestampAndSeqNum);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingLanding() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingLanding(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingEmergency() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingEmergency(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingNavigateHome(byte _start) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingNavigateHome(this.pointer, this.capacity, _start);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingAutoTakeOffMode(byte _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingAutoTakeOffMode(this.pointer, this.capacity, _state);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingMoveBy(float _dX, float _dY, float _dZ, float _dPsi) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingMoveBy(this.pointer, this.capacity, _dX, _dY, _dZ, _dPsi);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingUserTakeOff(byte _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingUserTakeOff(this.pointer, this.capacity, _state);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingCircle(ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM _direction) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingCircle(this.pointer, this.capacity, _direction.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingMoveTo(double _latitude, double _longitude, double _altitude, ARCOMMANDS_ARDRONE3_PILOTING_MOVETO_ORIENTATION_MODE_ENUM _orientation_mode, float _heading) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingMoveTo(r12.pointer, r12.capacity, _latitude, _longitude, _altitude, _orientation_mode.getValue(), _heading);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingCancelMoveTo() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingCancelMoveTo(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3AnimationsFlip(ARCOMMANDS_ARDRONE3_ANIMATIONS_FLIP_DIRECTION_ENUM _direction) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3AnimationsFlip(this.pointer, this.capacity, _direction.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3CameraOrientation(byte _tilt, byte _pan) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3CameraOrientation(this.pointer, this.capacity, _tilt, _pan);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3CameraOrientationV2(float _tilt, float _pan) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3CameraOrientationV2(this.pointer, this.capacity, _tilt, _pan);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3CameraVelocity(float _tilt, float _pan) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3CameraVelocity(this.pointer, this.capacity, _tilt, _pan);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordPicture(byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordPicture(this.pointer, this.capacity, _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordVideo(ARCOMMANDS_ARDRONE3_MEDIARECORD_VIDEO_RECORD_ENUM _record, byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordVideo(this.pointer, this.capacity, _record.getValue(), _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordPictureV2() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordPictureV2(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordVideoV2(ARCOMMANDS_ARDRONE3_MEDIARECORD_VIDEOV2_RECORD_ENUM _record) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordVideoV2(this.pointer, this.capacity, _record.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkWifiScan(ARCOMMANDS_ARDRONE3_NETWORK_WIFISCAN_BAND_ENUM _band) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkWifiScan(this.pointer, this.capacity, _band.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkWifiAuthChannel() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkWifiAuthChannel(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsMaxAltitude(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsMaxAltitude(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsMaxTilt(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsMaxTilt(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsAbsolutControl(byte _on) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsAbsolutControl(this.pointer, this.capacity, _on);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsMaxDistance(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsMaxDistance(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsNoFlyOverMaxDistance(byte _shouldNotFlyOver) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsNoFlyOverMaxDistance(this.pointer, this.capacity, _shouldNotFlyOver);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalSpeed(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m552xf801a2fd(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalSpeed(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m555xe5bce16b(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalAcceleration */
    public ARCOMMANDS_GENERATOR_ERROR_ENUM m608xd7d0d121(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m551x4014590a(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalAcceleration */
    public ARCOMMANDS_GENERATOR_ERROR_ENUM m609xcd7234b3(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m554xa598a2dc(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsSetAutonomousFlightMaxRotationSpeed(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m553x372ffe63(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsBankedTurn(byte _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsBankedTurn(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsMinAltitude(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsMinAltitude(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsCirclingDirection(C1402xbc6011c1 _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsCirclingDirection(this.pointer, this.capacity, _value.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsCirclingRadius(short _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsCirclingRadius(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsCirclingAltitude(short _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsCirclingAltitude(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsPitchMode(ARCOMMANDS_ARDRONE3_PILOTINGSETTINGS_PITCHMODE_VALUE_ENUM _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsPitchMode(this.pointer, this.capacity, _value.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SpeedSettingsMaxVerticalSpeed(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SpeedSettingsMaxVerticalSpeed(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SpeedSettingsMaxRotationSpeed(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SpeedSettingsMaxRotationSpeed(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SpeedSettingsHullProtection(byte _present) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SpeedSettingsHullProtection(this.pointer, this.capacity, _present);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SpeedSettingsOutdoor(byte _outdoor) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SpeedSettingsOutdoor(this.pointer, this.capacity, _outdoor);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SpeedSettingsMaxPitchRollRotationSpeed(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SpeedSettingsMaxPitchRollRotationSpeed(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkSettingsWifiSelection(ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISELECTION_TYPE_ENUM _type, ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISELECTION_BAND_ENUM _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkSettingsWifiSelection(this.pointer, this.capacity, _type.getValue(), _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkSettingsWifiSecurity(ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISECURITY_TYPE_ENUM _type, String _key, ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISECURITY_KEYTYPE_ENUM _keyType) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkSettingsWifiSecurity(this.pointer, this.capacity, _type.getValue(), _key, _keyType.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsPictureFormatSelection(C1397x91f54a09 _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsPictureFormatSelection(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsAutoWhiteBalanceSelection(C1396x10f9ec46 _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsAutoWhiteBalanceSelection(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsExpositionSelection(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsExpositionSelection(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsSaturationSelection(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsSaturationSelection(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsTimelapseSelection(byte _enabled, float _interval) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsTimelapseSelection(this.pointer, this.capacity, _enabled, _interval);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsVideoAutorecordSelection(byte _enabled, byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsVideoAutorecordSelection(this.pointer, this.capacity, _enabled, _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsVideoStabilizationMode(C1399x62185770 _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsVideoStabilizationMode(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsVideoRecordingMode(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORECORDINGMODE_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsVideoRecordingMode(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsVideoFramerate(C1398xcc3fb1b5 _framerate) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsVideoFramerate(this.pointer, this.capacity, _framerate.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsVideoResolutions(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_ENUM _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsVideoResolutions(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaStreamingVideoEnable(byte _enable) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaStreamingVideoEnable(this.pointer, this.capacity, _enable);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaStreamingVideoStreamMode(ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaStreamingVideoStreamMode(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsSetHome(double _latitude, double _longitude, double _altitude) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsSetHome(r10.pointer, r10.capacity, _latitude, _longitude, _altitude);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsResetHome() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsResetHome(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsSendControllerGPS(double _latitude, double _longitude, double _altitude, double _horizontalAccuracy, double _verticalAccuracy) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsSendControllerGPS(r14.pointer, r14.capacity, _latitude, _longitude, _altitude, _horizontalAccuracy, _verticalAccuracy);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsHomeType(ARCOMMANDS_ARDRONE3_GPSSETTINGS_HOMETYPE_TYPE_ENUM _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsHomeType(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsReturnHomeDelay(short _delay) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsReturnHomeDelay(this.pointer, this.capacity, _delay);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3AntiflickeringElectricFrequency(C1369x263150ea _frequency) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3AntiflickeringElectricFrequency(this.pointer, this.capacity, _frequency.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3AntiflickeringSetMode(ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3AntiflickeringSetMode(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordStatePictureStateChanged(byte _state, byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordStatePictureStateChanged(this.pointer, this.capacity, _state, _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordStateVideoStateChanged(C1382x735058e6 _state, byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordStateVideoStateChanged(this.pointer, this.capacity, _state.getValue(), _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordStatePictureStateChangedV2(C1377x75f32627 _state, C1376xe509eb10 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordStatePictureStateChangedV2(this.pointer, this.capacity, _state.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordStateVideoStateChangedV2(C1381x2e98e6ca _state, C1380x9dafabb3 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordStateVideoStateChangedV2(this.pointer, this.capacity, _state.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordStateVideoResolutionState(C1379x6f240e9d _streaming, C1378xf397ca6e _recording) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordStateVideoResolutionState(this.pointer, this.capacity, _streaming.getValue(), _recording.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordEventPictureEventChanged(C1373xbaa3e9ba _event, C1372xe17dff0c _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordEventPictureEventChanged(this.pointer, this.capacity, _event.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaRecordEventVideoEventChanged(C1375xdcd3c0dd _event, C1374x3add62f _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaRecordEventVideoEventChanged(this.pointer, this.capacity, _event.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateFlatTrimChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateFlatTrimChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateFlyingStateChanged(ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateFlyingStateChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateAlertStateChanged(ARCOMMANDS_ARDRONE3_PILOTINGSTATE_ALERTSTATECHANGED_STATE_ENUM _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateAlertStateChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateNavigateHomeStateChanged(C1405xa48cc1b6 _state, C1404x955e1555 _reason) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateNavigateHomeStateChanged(this.pointer, this.capacity, _state.getValue(), _reason.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStatePositionChanged(double _latitude, double _longitude, double _altitude) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStatePositionChanged(r10.pointer, r10.capacity, _latitude, _longitude, _altitude);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateSpeedChanged(float _speedX, float _speedY, float _speedZ) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateSpeedChanged(this.pointer, this.capacity, _speedX, _speedY, _speedZ);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateAttitudeChanged(float _roll, float _pitch, float _yaw) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateAttitudeChanged(this.pointer, this.capacity, _roll, _pitch, _yaw);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateAutoTakeOffModeChanged(byte _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateAutoTakeOffModeChanged(this.pointer, this.capacity, _state);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateAltitudeChanged(double _altitude) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateAltitudeChanged(this.pointer, this.capacity, _altitude);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateGpsLocationChanged(double _latitude, double _longitude, double _altitude, byte _latitude_accuracy, byte _longitude_accuracy, byte _altitude_accuracy) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateGpsLocationChanged(r13.pointer, r13.capacity, _latitude, _longitude, _altitude, _latitude_accuracy, _longitude_accuracy, _altitude_accuracy);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateLandingStateChanged(ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_ENUM _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateLandingStateChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateAirSpeedChanged(float _airSpeed) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateAirSpeedChanged(this.pointer, this.capacity, _airSpeed);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingStateMoveToChanged(double _latitude, double _longitude, double _altitude, C1403x808e9112 _orientation_mode, float _heading, ARCOMMANDS_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_STATUS_ENUM _status) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingStateMoveToChanged(r13.pointer, r13.capacity, _latitude, _longitude, _altitude, _orientation_mode.getValue(), _heading, _status.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingEventMoveByEnd(float _dX, float _dY, float _dZ, float _dPsi, ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_ENUM _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingEventMoveByEnd(r9.pointer, r9.capacity, _dX, _dY, _dZ, _dPsi, _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkStateWifiScanListChanged(String _ssid, short _rssi, ARCOMMANDS_ARDRONE3_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkStateWifiScanListChanged(this.pointer, this.capacity, _ssid, _rssi, _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkStateAllWifiScanChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkStateAllWifiScanChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkStateWifiAuthChannelListChanged(C1389x5b9d9bcb _band, byte _channel, byte _in_or_out) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkStateWifiAuthChannelListChanged(this.pointer, this.capacity, _band.getValue(), _channel, _in_or_out);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkStateAllWifiAuthChannelChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkStateAllWifiAuthChannelChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateMaxAltitudeChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsStateMaxAltitudeChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateMaxTiltChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsStateMaxTiltChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateAbsolutControlChanged(byte _on) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsStateAbsolutControlChanged(this.pointer, this.capacity, _on);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateMaxDistanceChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsStateMaxDistanceChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateNoFlyOverMaxDistanceChanged(byte _shouldNotFlyOver) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m561x11f80d54(this.pointer, this.capacity, _shouldNotFlyOver);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalSpeed */
    public ARCOMMANDS_GENERATOR_ERROR_ENUM m611x40a14df5(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m557x9880b86c(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalSpeed(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m560xec90869a(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalAcceleration */
    public ARCOMMANDS_GENERATOR_ERROR_ENUM m610x84e712(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m556x660618bb(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalAcceleration */
    public ARCOMMANDS_GENERATOR_ERROR_ENUM m612xa76512e4(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m559xfa89acd(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateAutonomousFlightMaxRotationSpeed(float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m558x3e03a392(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateBankedTurnChanged(byte _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsStateBankedTurnChanged(this.pointer, this.capacity, _state);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateMinAltitudeChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsStateMinAltitudeChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateCirclingDirectionChanged(C1400xe6290a4 _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsStateCirclingDirectionChanged(this.pointer, this.capacity, _value.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateCirclingRadiusChanged(short _current, short _min, short _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsStateCirclingRadiusChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStateCirclingAltitudeChanged(short _current, short _min, short _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsStateCirclingAltitudeChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PilotingSettingsStatePitchModeChanged(C1401xc59e32b5 _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PilotingSettingsStatePitchModeChanged(this.pointer, this.capacity, _value.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SpeedSettingsStateMaxVerticalSpeedChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SpeedSettingsStateMaxVerticalSpeedChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SpeedSettingsStateMaxRotationSpeedChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SpeedSettingsStateMaxRotationSpeedChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SpeedSettingsStateHullProtectionChanged(byte _present) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SpeedSettingsStateHullProtectionChanged(this.pointer, this.capacity, _present);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SpeedSettingsStateOutdoorChanged(byte _outdoor) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SpeedSettingsStateOutdoorChanged(this.pointer, this.capacity, _outdoor);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SpeedSettingsStateMaxPitchRollRotationSpeedChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m562x6b65eaa2(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkSettingsStateWifiSelectionChanged(C1388x56efbed6 _type, C1387xc1826cbb _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkSettingsStateWifiSelectionChanged(this.pointer, this.capacity, _type.getValue(), _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkSettingsStateWifiSecurityChanged(C1385x549a9714 _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkSettingsStateWifiSecurityChanged(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3NetworkSettingsStateWifiSecurity(ARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITY_TYPE_ENUM _type, String _key, C1386x559429ff _keyType) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3NetworkSettingsStateWifiSecurity(this.pointer, this.capacity, _type.getValue(), _key, _keyType.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SettingsStateProductMotorVersionListChanged(byte _motor_number, String _type, String _software, String _hardware) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SettingsStateProductMotorVersionListChanged(this.pointer, this.capacity, _motor_number, _type, _software, _hardware);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SettingsStateProductGPSVersionChanged(String _software, String _hardware) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SettingsStateProductGPSVersionChanged(this.pointer, this.capacity, _software, _hardware);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SettingsStateMotorErrorStateChanged(byte _motorIds, C1407x8eb7f9e2 _motorError) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SettingsStateMotorErrorStateChanged(this.pointer, this.capacity, _motorIds, _motorError.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SettingsStateMotorSoftwareVersionChanged(String _version) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SettingsStateMotorSoftwareVersionChanged(this.pointer, this.capacity, _version);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SettingsStateMotorFlightsStatusChanged(short _nbFlights, short _lastFlightDuration, int _totalFlightDuration) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SettingsStateMotorFlightsStatusChanged(this.pointer, this.capacity, _nbFlights, _lastFlightDuration, _totalFlightDuration);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SettingsStateMotorErrorLastErrorChanged(C1406xb27b3a1 _motorError) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SettingsStateMotorErrorLastErrorChanged(this.pointer, this.capacity, _motorError.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SettingsStateP7ID(String _serialID) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SettingsStateP7ID(this.pointer, this.capacity, _serialID);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3SettingsStateCPUID(String _id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3SettingsStateCPUID(this.pointer, this.capacity, _id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsStatePictureFormatChanged(C1391xf931d08 _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsStatePictureFormatChanged(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsStateAutoWhiteBalanceChanged(C1390xca383277 _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsStateAutoWhiteBalanceChanged(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsStateExpositionChanged(float _value, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsStateExpositionChanged(this.pointer, this.capacity, _value, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsStateSaturationChanged(float _value, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsStateSaturationChanged(this.pointer, this.capacity, _value, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsStateTimelapseChanged(byte _enabled, float _interval, float _minInterval, float _maxInterval) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsStateTimelapseChanged(this.pointer, this.capacity, _enabled, _interval, _minInterval, _maxInterval);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsStateVideoAutorecordChanged(byte _enabled, byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsStateVideoAutorecordChanged(this.pointer, this.capacity, _enabled, _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsStateVideoStabilizationModeChanged(C1395xb64e46a9 _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m550x767cfb26(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsStateVideoRecordingModeChanged(C1393xabbd0257 _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsStateVideoRecordingModeChanged(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsStateVideoFramerateChanged(C1392x2caea9d2 _framerate) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsStateVideoFramerateChanged(this.pointer, this.capacity, _framerate.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PictureSettingsStateVideoResolutionsChanged(C1394x80a06f8d _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PictureSettingsStateVideoResolutionsChanged(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaStreamingStateVideoEnableChanged(C1383xb44ff36f _enabled) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaStreamingStateVideoEnableChanged(this.pointer, this.capacity, _enabled.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3MediaStreamingStateVideoStreamModeChanged(C1384xe5650d6d _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3MediaStreamingStateVideoStreamModeChanged(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsStateHomeChanged(double _latitude, double _longitude, double _altitude) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsStateHomeChanged(r10.pointer, r10.capacity, _latitude, _longitude, _altitude);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsStateResetHomeChanged(double _latitude, double _longitude, double _altitude) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsStateResetHomeChanged(r10.pointer, r10.capacity, _latitude, _longitude, _altitude);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsStateGPSFixStateChanged(byte _fixed) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsStateGPSFixStateChanged(this.pointer, this.capacity, _fixed);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsStateGPSUpdateStateChanged(C1370xd79df106 _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsStateGPSUpdateStateChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsStateHomeTypeChanged(ARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_HOMETYPECHANGED_TYPE_ENUM _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsStateHomeTypeChanged(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsStateReturnHomeDelayChanged(short _delay) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsStateReturnHomeDelayChanged(this.pointer, this.capacity, _delay);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSSettingsStateGeofenceCenterChanged(double _latitude, double _longitude) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSSettingsStateGeofenceCenterChanged(this.pointer, this.capacity, _latitude, _longitude);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3CameraStateOrientation(byte _tilt, byte _pan) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3CameraStateOrientation(this.pointer, this.capacity, _tilt, _pan);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3CameraStateDefaultCameraOrientation(byte _tilt, byte _pan) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3CameraStateDefaultCameraOrientation(this.pointer, this.capacity, _tilt, _pan);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3CameraStateOrientationV2(float _tilt, float _pan) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3CameraStateOrientationV2(this.pointer, this.capacity, _tilt, _pan);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3CameraStateDefaultCameraOrientationV2(float _tilt, float _pan) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3CameraStateDefaultCameraOrientationV2(this.pointer, this.capacity, _tilt, _pan);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3CameraStateVelocityRange(float _max_tilt, float _max_pan) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3CameraStateVelocityRange(this.pointer, this.capacity, _max_tilt, _max_pan);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3AntiflickeringStateElectricFrequencyChanged(C1368xa14db947 _frequency) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3AntiflickeringStateElectricFrequencyChanged(this.pointer, this.capacity, _frequency.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3AntiflickeringStateModeChanged(ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3AntiflickeringStateModeChanged(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSStateNumberOfSatelliteChanged(byte _numberOfSatellite) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSStateNumberOfSatelliteChanged(this.pointer, this.capacity, _numberOfSatellite);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSStateHomeTypeAvailabilityChanged(C1371xa02f755c _type, byte _available) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSStateHomeTypeAvailabilityChanged(this.pointer, this.capacity, _type.getValue(), _available);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3GPSStateHomeTypeChosenChanged(ARCOMMANDS_ARDRONE3_GPSSTATE_HOMETYPECHOSENCHANGED_TYPE_ENUM _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3GPSStateHomeTypeChosenChanged(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3PROStateFeatures(long _features) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3PROStateFeatures(this.pointer, this.capacity, _features);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setARDrone3AccessoryStateConnectedAccessories(byte _id, C1367x498dbfd4 _accessory_type, String _uid, String _swVersion, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetARDrone3AccessoryStateConnectedAccessories(r9.pointer, r9.capacity, _id, _accessory_type.getValue(), _uid, _swVersion, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonNetworkDisconnect() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonNetworkDisconnect(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsAllSettings() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsAllSettings(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsReset() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsReset(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsProductName(String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsProductName(this.pointer, this.capacity, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsCountry(String _code) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsCountry(this.pointer, this.capacity, _code);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsAutoCountry(byte _automatic) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsAutoCountry(this.pointer, this.capacity, _automatic);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonAllStates() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonAllStates(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonCurrentDate(String _date) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonCurrentDate(this.pointer, this.capacity, _date);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonCurrentTime(String _time) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonCurrentTime(this.pointer, this.capacity, _time);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonReboot() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonReboot(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonOverHeatSwitchOff() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonOverHeatSwitchOff(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonOverHeatVentilate() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonOverHeatVentilate(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonControllerIsPiloting(byte _piloting) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonControllerIsPiloting(this.pointer, this.capacity, _piloting);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonWifiSettingsOutdoorSetting(byte _outdoor) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonWifiSettingsOutdoorSetting(this.pointer, this.capacity, _outdoor);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonMavlinkStart(String _filepath, ARCOMMANDS_COMMON_MAVLINK_START_TYPE_ENUM _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonMavlinkStart(this.pointer, this.capacity, _filepath, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonMavlinkPause() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonMavlinkPause(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonMavlinkStop() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonMavlinkStop(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonFlightPlanSettingsReturnHomeOnDisconnect(byte _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonFlightPlanSettingsReturnHomeOnDisconnect(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCalibrationMagnetoCalibration(byte _calibrate) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCalibrationMagnetoCalibration(this.pointer, this.capacity, _calibrate);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCalibrationPitotCalibration(byte _calibrate) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCalibrationPitotCalibration(this.pointer, this.capacity, _calibrate);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonGPSControllerPositionForRun(double _latitude, double _longitude) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonGPSControllerPositionForRun(this.pointer, this.capacity, _latitude, _longitude);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonAudioControllerReadyForStreaming(byte _ready) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonAudioControllerReadyForStreaming(this.pointer, this.capacity, _ready);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonHeadlightsIntensity(byte _left, byte _right) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonHeadlightsIntensity(this.pointer, this.capacity, _left, _right);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonAnimationsStartAnimation(ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM _anim) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonAnimationsStartAnimation(this.pointer, this.capacity, _anim.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonAnimationsStopAnimation(ARCOMMANDS_COMMON_ANIMATIONS_STOPANIMATION_ANIM_ENUM _anim) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonAnimationsStopAnimation(this.pointer, this.capacity, _anim.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonAnimationsStopAllAnimations() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonAnimationsStopAllAnimations(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonAccessoryConfig(ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM _accessory) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonAccessoryConfig(this.pointer, this.capacity, _accessory.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonChargerSetMaxChargeRate(ARCOMMANDS_COMMON_CHARGER_SETMAXCHARGERATE_RATE_ENUM _rate) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonChargerSetMaxChargeRate(this.pointer, this.capacity, _rate.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonFactoryReset() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonFactoryReset(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonNetworkEventDisconnection(ARCOMMANDS_COMMON_NETWORKEVENT_DISCONNECTION_CAUSE_ENUM _cause) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonNetworkEventDisconnection(this.pointer, this.capacity, _cause.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsStateAllSettingsChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsStateAllSettingsChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsStateResetChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsStateResetChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsStateProductNameChanged(String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsStateProductNameChanged(this.pointer, this.capacity, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsStateProductVersionChanged(String _software, String _hardware) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsStateProductVersionChanged(this.pointer, this.capacity, _software, _hardware);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsStateProductSerialHighChanged(String _high) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsStateProductSerialHighChanged(this.pointer, this.capacity, _high);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsStateProductSerialLowChanged(String _low) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsStateProductSerialLowChanged(this.pointer, this.capacity, _low);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsStateCountryChanged(String _code) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsStateCountryChanged(this.pointer, this.capacity, _code);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonSettingsStateAutoCountryChanged(byte _automatic) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonSettingsStateAutoCountryChanged(this.pointer, this.capacity, _automatic);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateAllStatesChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateAllStatesChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateBatteryStateChanged(byte _percent) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateBatteryStateChanged(this.pointer, this.capacity, _percent);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateMassStorageStateListChanged(byte _mass_storage_id, String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateMassStorageStateListChanged(this.pointer, this.capacity, _mass_storage_id, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateMassStorageInfoStateListChanged(byte _mass_storage_id, int _size, int _used_size, byte _plugged, byte _full, byte _internal) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateMassStorageInfoStateListChanged(r10.pointer, r10.capacity, _mass_storage_id, _size, _used_size, _plugged, _full, _internal);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateCurrentDateChanged(String _date) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateCurrentDateChanged(this.pointer, this.capacity, _date);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateCurrentTimeChanged(String _time) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateCurrentTimeChanged(this.pointer, this.capacity, _time);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateMassStorageInfoRemainingListChanged(int _free_space, short _rec_time, int _photo_remaining) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateMassStorageInfoRemainingListChanged(this.pointer, this.capacity, _free_space, _rec_time, _photo_remaining);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateWifiSignalChanged(short _rssi) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateWifiSignalChanged(this.pointer, this.capacity, _rssi);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateSensorsStatesListChanged(C1415x829404be _sensorName, byte _sensorState) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateSensorsStatesListChanged(this.pointer, this.capacity, _sensorName.getValue(), _sensorState);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateProductModel(ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_ENUM _model) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateProductModel(this.pointer, this.capacity, _model.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateCountryListKnown(byte _listFlags, String _countryCodes) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateCountryListKnown(this.pointer, this.capacity, _listFlags, _countryCodes);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateDeprecatedMassStorageContentChanged(byte _mass_storage_id, short _nbPhotos, short _nbVideos, short _nbPuds, short _nbCrashLogs) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateDeprecatedMassStorageContentChanged(r9.pointer, r9.capacity, _mass_storage_id, _nbPhotos, _nbVideos, _nbPuds, _nbCrashLogs);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateMassStorageContent(byte _mass_storage_id, short _nbPhotos, short _nbVideos, short _nbPuds, short _nbCrashLogs, short _nbRawPhotos) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateMassStorageContent(r10.pointer, r10.capacity, _mass_storage_id, _nbPhotos, _nbVideos, _nbPuds, _nbCrashLogs, _nbRawPhotos);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateMassStorageContentForCurrentRun(byte _mass_storage_id, short _nbPhotos, short _nbVideos, short _nbRawPhotos) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateMassStorageContentForCurrentRun(this.pointer, this.capacity, _mass_storage_id, _nbPhotos, _nbVideos, _nbRawPhotos);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCommonStateVideoRecordingTimestamp(long _startTimestamp, long _stopTimestamp) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCommonStateVideoRecordingTimestamp(this.pointer, this.capacity, _startTimestamp, _stopTimestamp);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonOverHeatStateOverHeatChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonOverHeatStateOverHeatChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonOverHeatStateOverHeatRegulationChanged(byte _regulationType) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonOverHeatStateOverHeatRegulationChanged(this.pointer, this.capacity, _regulationType);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonWifiSettingsStateOutdoorSettingsChanged(byte _outdoor) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonWifiSettingsStateOutdoorSettingsChanged(this.pointer, this.capacity, _outdoor);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonMavlinkStateMavlinkFilePlayingStateChanged(C1417x4c05ad79 _state, String _filepath, C1418xb82bc83c _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonMavlinkStateMavlinkFilePlayingStateChanged(this.pointer, this.capacity, _state.getValue(), _filepath, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonMavlinkStateMavlinkPlayErrorStateChanged(C1419x7fd94000 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonMavlinkStateMavlinkPlayErrorStateChanged(this.pointer, this.capacity, _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonMavlinkStateMissionItemExecuted(int _idx) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonMavlinkStateMissionItemExecuted(this.pointer, this.capacity, _idx);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonFlightPlanSettingsStateReturnHomeOnDisconnectChanged(byte _state, byte _isReadOnly) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m565x24f80c6b(this.pointer, this.capacity, _state, _isReadOnly);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCalibrationStateMagnetoCalibrationStateChanged(byte _xAxisCalibration, byte _yAxisCalibration, byte _zAxisCalibration, byte _calibrationFailed) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCalibrationStateMagnetoCalibrationStateChanged(this.pointer, this.capacity, _xAxisCalibration, _yAxisCalibration, _zAxisCalibration, _calibrationFailed);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCalibrationStateMagnetoCalibrationRequiredState(byte _required) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCalibrationStateMagnetoCalibrationRequiredState(this.pointer, this.capacity, _required);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    /* renamed from: setCommonCalibrationStateMagnetoCalibrationAxisToCalibrateChanged */
    public ARCOMMANDS_GENERATOR_ERROR_ENUM m613x548df4c2(C1411xddcba4d8 _axis) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m564x786bdf6b(this.pointer, this.capacity, _axis.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCalibrationStateMagnetoCalibrationStartedChanged(byte _started) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCalibrationStateMagnetoCalibrationStartedChanged(this.pointer, this.capacity, _started);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCalibrationStatePitotCalibrationStateChanged(C1412xd2bdba57 _state, byte _lastError) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCalibrationStatePitotCalibrationStateChanged(this.pointer, this.capacity, _state.getValue(), _lastError);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonCameraSettingsStateCameraSettingsChanged(float _fov, float _panMax, float _panMin, float _tiltMax, float _tiltMin) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonCameraSettingsStateCameraSettingsChanged(r9.pointer, r9.capacity, _fov, _panMax, _panMin, _tiltMax, _tiltMin);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonFlightPlanStateAvailabilityStateChanged(byte _AvailabilityState) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonFlightPlanStateAvailabilityStateChanged(this.pointer, this.capacity, _AvailabilityState);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonFlightPlanStateComponentStateListChanged(C1416x6f7e0035 _component, byte _State) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonFlightPlanStateComponentStateListChanged(this.pointer, this.capacity, _component.getValue(), _State);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonFlightPlanStateLockStateChanged(byte _LockState) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonFlightPlanStateLockStateChanged(this.pointer, this.capacity, _LockState);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonFlightPlanEventStartingErrorEvent() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonFlightPlanEventStartingErrorEvent(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonFlightPlanEventSpeedBridleEvent() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonFlightPlanEventSpeedBridleEvent(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonARLibsVersionsStateControllerLibARCommandsVersion(String _version) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonARLibsVersionsStateControllerLibARCommandsVersion(this.pointer, this.capacity, _version);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonARLibsVersionsStateSkyControllerLibARCommandsVersion(String _version) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m563x5b611fd6(this.pointer, this.capacity, _version);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonARLibsVersionsStateDeviceLibARCommandsVersion(String _version) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonARLibsVersionsStateDeviceLibARCommandsVersion(this.pointer, this.capacity, _version);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonAudioStateAudioStreamingRunning(byte _running) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonAudioStateAudioStreamingRunning(this.pointer, this.capacity, _running);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonHeadlightsStateIntensityChanged(byte _left, byte _right) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonHeadlightsStateIntensityChanged(this.pointer, this.capacity, _left, _right);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonAnimationsStateList(ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ANIM_ENUM _anim, ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_STATE_ENUM _state, ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_ENUM _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonAnimationsStateList(this.pointer, this.capacity, _anim.getValue(), _state.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonAccessoryStateSupportedAccessoriesListChanged(C1410xcb8ad78b _accessory) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonAccessoryStateSupportedAccessoriesListChanged(this.pointer, this.capacity, _accessory.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonAccessoryStateAccessoryConfigChanged(C1409xc48b501 _newAccessory, C1408x2a262b05 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonAccessoryStateAccessoryConfigChanged(this.pointer, this.capacity, _newAccessory.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonAccessoryStateAccessoryConfigModificationEnabled(byte _enabled) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonAccessoryStateAccessoryConfigModificationEnabled(this.pointer, this.capacity, _enabled);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonChargerStateMaxChargeRateChanged(ARCOMMANDS_COMMON_CHARGERSTATE_MAXCHARGERATECHANGED_RATE_ENUM _rate) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonChargerStateMaxChargeRateChanged(this.pointer, this.capacity, _rate.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonChargerStateCurrentChargeStateChanged(C1414xbac7a5e1 _status, C1413x59fb7652 _phase) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonChargerStateCurrentChargeStateChanged(this.pointer, this.capacity, _status.getValue(), _phase.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonChargerStateLastChargeRateChanged(ARCOMMANDS_COMMON_CHARGERSTATE_LASTCHARGERATECHANGED_RATE_ENUM _rate) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonChargerStateLastChargeRateChanged(this.pointer, this.capacity, _rate.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonChargerStateChargingInfo(ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_ENUM _phase, ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_ENUM _rate, byte _intensity, byte _fullChargingTime) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonChargerStateChargingInfo(this.pointer, this.capacity, _phase.getValue(), _rate.getValue(), _intensity, _fullChargingTime);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setCommonRunStateRunIdChanged(String _runId) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetCommonRunStateRunIdChanged(this.pointer, this.capacity, _runId);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setControllerInfoGps(double _latitude, double _longitude, float _altitude, float _horizontal_accuracy, float _vertical_accuracy, float _north_speed, float _east_speed, float _down_speed, double _timestamp) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetControllerInfoGps(r14.pointer, r14.capacity, _latitude, _longitude, _altitude, _horizontal_accuracy, _vertical_accuracy, _north_speed, _east_speed, _down_speed, _timestamp);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setControllerInfoBarometer(float _pressure, double _timestamp) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetControllerInfoBarometer(this.pointer, this.capacity, _pressure, _timestamp);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDebugGetAllSettings() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDebugGetAllSettings(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDebugSetSetting(short _id, String _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDebugSetSetting(this.pointer, this.capacity, _id, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDebugSettingsInfo(byte _list_flags, short _id, String _label, ARCOMMANDS_DEBUG_SETTING_TYPE_ENUM _type, ARCOMMANDS_DEBUG_SETTING_MODE_ENUM _mode, String _range_min, String _range_max, String _range_step, String _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDebugSettingsInfo(r13.pointer, r13.capacity, _list_flags, _id, _label, _type.getValue(), _mode.getValue(), _range_min, _range_max, _range_step, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDebugSettingsList(short _id, String _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDebugSettingsList(this.pointer, this.capacity, _id, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDroneManagerDiscoverDrones() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDroneManagerDiscoverDrones(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDroneManagerConnect(String _serial, String _key) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDroneManagerConnect(this.pointer, this.capacity, _serial, _key);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDroneManagerForget(String _serial) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDroneManagerForget(this.pointer, this.capacity, _serial);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDroneManagerDroneListItem(String _serial, short _model, String _name, byte _connection_order, byte _active, byte _visible, ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM _security, byte _has_saved_key, byte _rssi, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDroneManagerDroneListItem(r14.pointer, r14.capacity, _serial, _model, _name, _connection_order, _active, _visible, _security.getValue(), _has_saved_key, _rssi, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDroneManagerConnectionState(ARCOMMANDS_DRONE_MANAGER_CONNECTION_STATE_ENUM _state, String _serial, short _model, String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDroneManagerConnectionState(this.pointer, this.capacity, _state.getValue(), _serial, _model, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDroneManagerAuthenticationFailed(String _serial, short _model, String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDroneManagerAuthenticationFailed(this.pointer, this.capacity, _serial, _model, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDroneManagerConnectionRefused(String _serial, short _model, String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDroneManagerConnectionRefused(this.pointer, this.capacity, _serial, _model, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setDroneManagerKnownDroneItem(String _serial, short _model, String _name, ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM _security, byte _has_saved_key, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetDroneManagerKnownDroneItem(r10.pointer, r10.capacity, _serial, _model, _name, _security.getValue(), _has_saved_key, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeStart(ARCOMMANDS_FOLLOW_ME_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeStart(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeStop() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeStop(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeConfigureGeographic(byte _use_default, float _distance, float _elevation, float _azimuth) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeConfigureGeographic(this.pointer, this.capacity, _use_default, _distance, _elevation, _azimuth);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeConfigureRelative(byte _use_default, float _distance, float _elevation, float _azimuth) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeConfigureRelative(this.pointer, this.capacity, _use_default, _distance, _elevation, _azimuth);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeStopAnimation() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeStopAnimation(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeStartHelicoidAnim(byte _use_default, float _speed, float _revolution_number, float _vertical_distance) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeStartHelicoidAnim(this.pointer, this.capacity, _use_default, _speed, _revolution_number, _vertical_distance);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeStartSwingAnim(byte _use_default, float _speed, float _vertical_distance) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeStartSwingAnim(this.pointer, this.capacity, _use_default, _speed, _vertical_distance);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeStartBoomerangAnim(byte _use_default, float _speed, float _distance) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeStartBoomerangAnim(this.pointer, this.capacity, _use_default, _speed, _distance);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeStartCandleAnim(byte _use_default, float _speed, float _vertical_distance) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeStartCandleAnim(this.pointer, this.capacity, _use_default, _speed, _vertical_distance);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeStartDollySlideAnim(byte _use_default, float _speed, float _angle, float _horizontal_distance) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeStartDollySlideAnim(this.pointer, this.capacity, _use_default, _speed, _angle, _horizontal_distance);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeTargetFramingPosition(byte _horizontal, byte _vertical) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeTargetFramingPosition(this.pointer, this.capacity, _horizontal, _vertical);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeTargetImageDetection(float _target_azimuth, float _target_elevation, float _change_of_scale, byte _confidence_index, byte _is_new_selection, long _timestamp) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeTargetImageDetection(r11.pointer, r11.capacity, _target_azimuth, _target_elevation, _change_of_scale, _confidence_index, _is_new_selection, _timestamp);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeState(ARCOMMANDS_FOLLOW_ME_MODE_ENUM _mode, ARCOMMANDS_FOLLOW_ME_BEHAVIOR_ENUM _behavior, ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM _animation, short _animation_available) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeState(this.pointer, this.capacity, _mode.getValue(), _behavior.getValue(), _animation.getValue(), _animation_available);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeModeInfo(ARCOMMANDS_FOLLOW_ME_MODE_ENUM _mode, short _missing_requirements, short _improvements) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeModeInfo(this.pointer, this.capacity, _mode.getValue(), _missing_requirements, _improvements);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeGeographicConfig(byte _use_default, float _distance, float _elevation, float _azimuth) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeGeographicConfig(this.pointer, this.capacity, _use_default, _distance, _elevation, _azimuth);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeRelativeConfig(byte _use_default, float _distance, float _elevation, float _azimuth) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeRelativeConfig(this.pointer, this.capacity, _use_default, _distance, _elevation, _azimuth);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeTargetTrajectory(double _latitude, double _longitude, float _altitude, float _north_speed, float _east_speed, float _down_speed) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeTargetTrajectory(r12.pointer, r12.capacity, _latitude, _longitude, _altitude, _north_speed, _east_speed, _down_speed);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeHelicoidAnimConfig(byte _use_default, float _speed, float _revolution_nb, float _vertical_distance) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeHelicoidAnimConfig(this.pointer, this.capacity, _use_default, _speed, _revolution_nb, _vertical_distance);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeSwingAnimConfig(byte _use_default, float _speed, float _vertical_distance) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeSwingAnimConfig(this.pointer, this.capacity, _use_default, _speed, _vertical_distance);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeBoomerangAnimConfig(byte _use_default, float _speed, float _distance) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeBoomerangAnimConfig(this.pointer, this.capacity, _use_default, _speed, _distance);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeCandleAnimConfig(byte _use_default, float _speed, float _vertical_distance) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeCandleAnimConfig(this.pointer, this.capacity, _use_default, _speed, _vertical_distance);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeDollySlideAnimConfig(byte _use_default, float _speed, float _angle, float _horizontal_distance) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeDollySlideAnimConfig(this.pointer, this.capacity, _use_default, _speed, _angle, _horizontal_distance);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeTargetFramingPositionChanged(byte _horizontal, byte _vertical) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeTargetFramingPositionChanged(this.pointer, this.capacity, _horizontal, _vertical);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setFollowMeTargetImageDetectionState(ARCOMMANDS_FOLLOW_ME_IMAGE_DETECTION_STATUS_ENUM _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetFollowMeTargetImageDetectionState(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoPilotingPCMD(byte _flag, byte _speed, byte _turn) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoPilotingPCMD(this.pointer, this.capacity, _flag, _speed, _turn);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoPilotingPosture(ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoPilotingPosture(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoPilotingAddCapOffset(float _offset) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoPilotingAddCapOffset(this.pointer, this.capacity, _offset);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAnimationsJumpStop() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAnimationsJumpStop(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAnimationsJumpCancel() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAnimationsJumpCancel(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAnimationsJumpLoad() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAnimationsJumpLoad(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAnimationsJump(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_JUMP_TYPE_ENUM _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAnimationsJump(this.pointer, this.capacity, _type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAnimationsSimpleAnimation(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM _id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAnimationsSimpleAnimation(this.pointer, this.capacity, _id.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaRecordPicture(byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaRecordPicture(this.pointer, this.capacity, _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaRecordVideo(ARCOMMANDS_JUMPINGSUMO_MEDIARECORD_VIDEO_RECORD_ENUM _record, byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaRecordVideo(this.pointer, this.capacity, _record.getValue(), _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaRecordPictureV2() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaRecordPictureV2(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaRecordVideoV2(ARCOMMANDS_JUMPINGSUMO_MEDIARECORD_VIDEOV2_RECORD_ENUM _record) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaRecordVideoV2(this.pointer, this.capacity, _record.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoNetworkSettingsWifiSelection(ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGS_WIFISELECTION_TYPE_ENUM _type, ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGS_WIFISELECTION_BAND_ENUM _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoNetworkSettingsWifiSelection(this.pointer, this.capacity, _type.getValue(), _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoNetworkWifiScan(ARCOMMANDS_JUMPINGSUMO_NETWORK_WIFISCAN_BAND_ENUM _band) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoNetworkWifiScan(this.pointer, this.capacity, _band.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoNetworkWifiAuthChannel() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoNetworkWifiAuthChannel(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAudioSettingsMasterVolume(byte _volume) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAudioSettingsMasterVolume(this.pointer, this.capacity, _volume);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAudioSettingsTheme(ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM _theme) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAudioSettingsTheme(this.pointer, this.capacity, _theme.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoRoadPlanAllScriptsMetadata() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoRoadPlanAllScriptsMetadata(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoRoadPlanScriptUploaded(String _uuid, String _md5Hash) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoRoadPlanScriptUploaded(this.pointer, this.capacity, _uuid, _md5Hash);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoRoadPlanScriptDelete(String _uuid) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoRoadPlanScriptDelete(this.pointer, this.capacity, _uuid);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoRoadPlanPlayScript(String _uuid) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoRoadPlanPlayScript(this.pointer, this.capacity, _uuid);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoSpeedSettingsOutdoor(byte _outdoor) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoSpeedSettingsOutdoor(this.pointer, this.capacity, _outdoor);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaStreamingVideoEnable(byte _enable) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaStreamingVideoEnable(this.pointer, this.capacity, _enable);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoVideoSettingsAutorecord(byte _enabled) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoVideoSettingsAutorecord(this.pointer, this.capacity, _enabled);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoPilotingStatePostureChanged(ARCOMMANDS_JUMPINGSUMO_PILOTINGSTATE_POSTURECHANGED_STATE_ENUM _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoPilotingStatePostureChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoPilotingStateAlertStateChanged(C1438x7c2fba54 _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoPilotingStateAlertStateChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoPilotingStateSpeedChanged(byte _speed, short _realSpeed) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoPilotingStateSpeedChanged(this.pointer, this.capacity, _speed, _realSpeed);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAnimationsStateJumpLoadChanged(C1420x356bf738 _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAnimationsStateJumpLoadChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAnimationsStateJumpTypeChanged(C1422x47b9548c _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAnimationsStateJumpTypeChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAnimationsStateJumpMotorProblemChanged(C1421xd8d31ac5 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAnimationsStateJumpMotorProblemChanged(this.pointer, this.capacity, _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoSettingsStateProductGPSVersionChanged(String _software, String _hardware) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoSettingsStateProductGPSVersionChanged(this.pointer, this.capacity, _software, _hardware);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaRecordStatePictureStateChanged(byte _state, byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaRecordStatePictureStateChanged(this.pointer, this.capacity, _state, _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaRecordStateVideoStateChanged(C1432x72607e46 _state, byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaRecordStateVideoStateChanged(this.pointer, this.capacity, _state.getValue(), _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaRecordStatePictureStateChangedV2(C1429x7bb19b87 _state, C1428xeac86070 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaRecordStatePictureStateChangedV2(this.pointer, this.capacity, _state.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaRecordStateVideoStateChangedV2(C1431xaa35342a _state, C1430x194bf913 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaRecordStateVideoStateChangedV2(this.pointer, this.capacity, _state.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaRecordEventPictureEventChanged(C1425x3640371a _event, C1424x5d1a4c6c _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaRecordEventPictureEventChanged(this.pointer, this.capacity, _event.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaRecordEventVideoEventChanged(C1427xdbe3e63d _event, C1426x2bdfb8f _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaRecordEventVideoEventChanged(this.pointer, this.capacity, _event.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoNetworkSettingsStateWifiSelectionChanged(C1435xe6e65c36 _type, C1434x51790a1b _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoNetworkSettingsStateWifiSelectionChanged(this.pointer, this.capacity, _type.getValue(), _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoNetworkStateWifiScanListChanged(String _ssid, short _rssi, C1437xba7e29a1 _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoNetworkStateWifiScanListChanged(this.pointer, this.capacity, _ssid, _rssi, _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoNetworkStateAllWifiScanChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoNetworkStateAllWifiScanChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoNetworkStateWifiAuthChannelListChanged(C1436x615c112b _band, byte _channel, byte _in_or_out) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoNetworkStateWifiAuthChannelListChanged(this.pointer, this.capacity, _band.getValue(), _channel, _in_or_out);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoNetworkStateAllWifiAuthChannelChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoNetworkStateAllWifiAuthChannelChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoNetworkStateLinkQualityChanged(byte _quality) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoNetworkStateLinkQualityChanged(this.pointer, this.capacity, _quality);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAudioSettingsStateMasterVolumeChanged(byte _volume) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAudioSettingsStateMasterVolumeChanged(this.pointer, this.capacity, _volume);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoAudioSettingsStateThemeChanged(C1423xa948c651 _theme) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoAudioSettingsStateThemeChanged(this.pointer, this.capacity, _theme.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoRoadPlanStateScriptMetadataListChanged(String _uuid, byte _version, String _product, String _name, long _lastModified) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoRoadPlanStateScriptMetadataListChanged(r10.pointer, r10.capacity, _uuid, _version, _product, _name, _lastModified);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoRoadPlanStateAllScriptsMetadataChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoRoadPlanStateAllScriptsMetadataChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoRoadPlanStateScriptUploadChanged(C1441x86ef9dbb _resultCode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoRoadPlanStateScriptUploadChanged(this.pointer, this.capacity, _resultCode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoRoadPlanStateScriptDeleteChanged(C1440x28c026d1 _resultCode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoRoadPlanStateScriptDeleteChanged(this.pointer, this.capacity, _resultCode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoRoadPlanStatePlayScriptChanged(C1439x633744e8 _resultCode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoRoadPlanStatePlayScriptChanged(this.pointer, this.capacity, _resultCode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoSpeedSettingsStateOutdoorChanged(byte _outdoor) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoSpeedSettingsStateOutdoorChanged(this.pointer, this.capacity, _outdoor);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoMediaStreamingStateVideoEnableChanged(C1433x444690cf _enabled) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoMediaStreamingStateVideoEnableChanged(this.pointer, this.capacity, _enabled.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setJumpingSumoVideoSettingsStateAutorecordChanged(byte _enabled) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetJumpingSumoVideoSettingsStateAutorecordChanged(this.pointer, this.capacity, _enabled);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperGrab(int _buttons, int _axes) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperGrab(this.pointer, this.capacity, _buttons, _axes);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperMapButtonAction(short _product, ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM _action, int _buttons) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperMapButtonAction(this.pointer, this.capacity, _product, _action.getValue(), _buttons);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperMapAxisAction(short _product, ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM _action, int _axis, int _buttons) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperMapAxisAction(this.pointer, this.capacity, _product, _action.getValue(), _axis, _buttons);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperResetMapping(short _product) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperResetMapping(this.pointer, this.capacity, _product);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperSetExpo(short _product, int _axis, ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM _expo) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperSetExpo(this.pointer, this.capacity, _product, _axis, _expo.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperSetInverted(short _product, int _axis, byte _inverted) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperSetInverted(this.pointer, this.capacity, _product, _axis, _inverted);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperGrabState(int _buttons, int _axes, int _buttons_state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperGrabState(this.pointer, this.capacity, _buttons, _axes, _buttons_state);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperGrabButtonEvent(int _button, ARCOMMANDS_MAPPER_BUTTON_EVENT_ENUM _event) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperGrabButtonEvent(this.pointer, this.capacity, _button, _event.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperGrabAxisEvent(int _axis, byte _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperGrabAxisEvent(this.pointer, this.capacity, _axis, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperButtonMappingItem(int _uid, short _product, ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM _action, int _buttons, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperButtonMappingItem(r9.pointer, r9.capacity, _uid, _product, _action.getValue(), _buttons, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperAxisMappingItem(int _uid, short _product, ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM _action, int _axis, int _buttons, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperAxisMappingItem(r10.pointer, r10.capacity, _uid, _product, _action.getValue(), _axis, _buttons, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperApplicationAxisEvent(ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM _action, byte _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperApplicationAxisEvent(this.pointer, this.capacity, _action.getValue(), _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperApplicationButtonEvent(ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM _action) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperApplicationButtonEvent(this.pointer, this.capacity, _action.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperExpoMapItem(int _uid, short _product, int _axis, ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM _expo, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperExpoMapItem(r9.pointer, r9.capacity, _uid, _product, _axis, _expo.getValue(), _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperInvertedMapItem(int _uid, short _product, int _axis, byte _inverted, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperInvertedMapItem(r9.pointer, r9.capacity, _uid, _product, _axis, _inverted, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperActiveProduct(short _product) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperActiveProduct(this.pointer, this.capacity, _product);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperMiniMapButtonAction(byte _modes, ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM _action, int _buttons) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperMiniMapButtonAction(this.pointer, this.capacity, _modes, _action.getValue(), _buttons);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperMiniMapAxisAction(byte _modes, ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM _action, byte _axis, int _buttons) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperMiniMapAxisAction(this.pointer, this.capacity, _modes, _action.getValue(), _axis, _buttons);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperMiniResetMapping(byte _modes) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperMiniResetMapping(this.pointer, this.capacity, _modes);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperMiniButtonMappingItem(short _uid, byte _modes, ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM _action, int _buttons, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperMiniButtonMappingItem(r9.pointer, r9.capacity, _uid, _modes, _action.getValue(), _buttons, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMapperMiniAxisMappingItem(short _uid, byte _modes, ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM _action, byte _axis, int _buttons, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMapperMiniAxisMappingItem(r10.pointer, r10.capacity, _uid, _modes, _action.getValue(), _axis, _buttons, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingFlatTrim() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingFlatTrim(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingTakeOff() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingTakeOff(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingPCMD(byte _flag, byte _roll, byte _pitch, byte _yaw, byte _gaz, int _timestamp) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingPCMD(r10.pointer, r10.capacity, _flag, _roll, _pitch, _yaw, _gaz, _timestamp);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingLanding() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingLanding(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingEmergency() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingEmergency(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingAutoTakeOffMode(byte _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingAutoTakeOffMode(this.pointer, this.capacity, _state);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingFlyingMode(ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingFlyingMode(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingPlaneGearBox(ARCOMMANDS_MINIDRONE_PILOTING_PLANEGEARBOX_STATE_ENUM _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingPlaneGearBox(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneAnimationsFlip(ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM _direction) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneAnimationsFlip(this.pointer, this.capacity, _direction.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneAnimationsCap(short _offset) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneAnimationsCap(this.pointer, this.capacity, _offset);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneMediaRecordPicture(byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneMediaRecordPicture(this.pointer, this.capacity, _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneMediaRecordPictureV2() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneMediaRecordPictureV2(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingSettingsMaxAltitude(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingSettingsMaxAltitude(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingSettingsMaxTilt(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingSettingsMaxTilt(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingSettingsBankedTurn(byte _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingSettingsBankedTurn(this.pointer, this.capacity, _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSpeedSettingsMaxVerticalSpeed(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSpeedSettingsMaxVerticalSpeed(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSpeedSettingsMaxRotationSpeed(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSpeedSettingsMaxRotationSpeed(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSpeedSettingsWheels(byte _present) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSpeedSettingsWheels(this.pointer, this.capacity, _present);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSpeedSettingsMaxHorizontalSpeed(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSpeedSettingsMaxHorizontalSpeed(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSpeedSettingsMaxPlaneModeRotationSpeed(float _current) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSpeedSettingsMaxPlaneModeRotationSpeed(this.pointer, this.capacity, _current);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSettingsCutOutMode(byte _enable) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSettingsCutOutMode(this.pointer, this.capacity, _enable);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneGPSControllerLatitudeForRun(double _latitude) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneGPSControllerLatitudeForRun(this.pointer, this.capacity, _latitude);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneGPSControllerLongitudeForRun(double _longitude) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneGPSControllerLongitudeForRun(this.pointer, this.capacity, _longitude);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneConfigurationControllerType(String _type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneConfigurationControllerType(this.pointer, this.capacity, _type);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneConfigurationControllerName(String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneConfigurationControllerName(this.pointer, this.capacity, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneUsbAccessoryLightControl(byte _id, ARCOMMANDS_MINIDRONE_USBACCESSORY_LIGHTCONTROL_MODE_ENUM _mode, byte _intensity) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneUsbAccessoryLightControl(this.pointer, this.capacity, _id, _mode.getValue(), _intensity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneUsbAccessoryClawControl(byte _id, ARCOMMANDS_MINIDRONE_USBACCESSORY_CLAWCONTROL_ACTION_ENUM _action) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneUsbAccessoryClawControl(this.pointer, this.capacity, _id, _action.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneUsbAccessoryGunControl(byte _id, ARCOMMANDS_MINIDRONE_USBACCESSORY_GUNCONTROL_ACTION_ENUM _action) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneUsbAccessoryGunControl(this.pointer, this.capacity, _id, _action.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneRemoteControllerSetPairedRemote(short _msb_mac, short _mid_mac, short _lsb_mac) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneRemoteControllerSetPairedRemote(this.pointer, this.capacity, _msb_mac, _mid_mac, _lsb_mac);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingStateFlatTrimChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingStateFlatTrimChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingStateFlyingStateChanged(ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingStateFlyingStateChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingStateAlertStateChanged(ARCOMMANDS_MINIDRONE_PILOTINGSTATE_ALERTSTATECHANGED_STATE_ENUM _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingStateAlertStateChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingStateAutoTakeOffModeChanged(byte _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingStateAutoTakeOffModeChanged(this.pointer, this.capacity, _state);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingStateFlyingModeChanged(ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGMODECHANGED_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingStateFlyingModeChanged(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingStatePlaneGearBoxChanged(C1446x17d2c516 _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingStatePlaneGearBoxChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneMediaRecordStatePictureStateChanged(byte _state, byte _mass_storage_id) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneMediaRecordStatePictureStateChanged(this.pointer, this.capacity, _state, _mass_storage_id);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneMediaRecordStatePictureStateChangedV2(C1445xb7ac1c70 _state, C1444x26c2e159 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneMediaRecordStatePictureStateChangedV2(this.pointer, this.capacity, _state.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneMediaRecordEventPictureEventChanged(C1443x2587de43 _event, C1442x4c61f395 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneMediaRecordEventPictureEventChanged(this.pointer, this.capacity, _event.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingSettingsStateMaxAltitudeChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingSettingsStateMaxAltitudeChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingSettingsStateMaxTiltChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingSettingsStateMaxTiltChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDronePilotingSettingsStateBankedTurnChanged(byte _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDronePilotingSettingsStateBankedTurnChanged(this.pointer, this.capacity, _state);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSpeedSettingsStateMaxVerticalSpeedChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSpeedSettingsStateMaxVerticalSpeedChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSpeedSettingsStateMaxRotationSpeedChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSpeedSettingsStateMaxRotationSpeedChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSpeedSettingsStateWheelsChanged(byte _present) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSpeedSettingsStateWheelsChanged(this.pointer, this.capacity, _present);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSpeedSettingsStateMaxHorizontalSpeedChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSpeedSettingsStateMaxHorizontalSpeedChanged(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSpeedSettingsStateMaxPlaneModeRotationSpeedChanged(float _current, float _min, float _max) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m566xec057123(this.pointer, this.capacity, _current, _min, _max);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSettingsStateProductMotorsVersionChanged(byte _motor, String _type, String _software, String _hardware) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSettingsStateProductMotorsVersionChanged(this.pointer, this.capacity, _motor, _type, _software, _hardware);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSettingsStateProductInertialVersionChanged(String _software, String _hardware) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSettingsStateProductInertialVersionChanged(this.pointer, this.capacity, _software, _hardware);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneSettingsStateCutOutModeChanged(byte _enable) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneSettingsStateCutOutModeChanged(this.pointer, this.capacity, _enable);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneFloodControlStateFloodControlChanged(short _delay) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneFloodControlStateFloodControlChanged(this.pointer, this.capacity, _delay);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneUsbAccessoryStateLightState(byte _id, ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_LIGHTSTATE_STATE_ENUM _state, byte _intensity, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneUsbAccessoryStateLightState(this.pointer, this.capacity, _id, _state.getValue(), _intensity, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneUsbAccessoryStateClawState(byte _id, ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_CLAWSTATE_STATE_ENUM _state, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneUsbAccessoryStateClawState(this.pointer, this.capacity, _id, _state.getValue(), _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneUsbAccessoryStateGunState(byte _id, ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_ENUM _state, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneUsbAccessoryStateGunState(this.pointer, this.capacity, _id, _state.getValue(), _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setMiniDroneNavigationDataStateDronePosition(float _posx, float _posy, short _posz, short _psi, short _ts) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetMiniDroneNavigationDataStateDronePosition(r9.pointer, r9.capacity, _posx, _posy, _posz, _psi, _ts);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupPilotingPCMD(byte _flag, byte _throttle, byte _roll) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupPilotingPCMD(this.pointer, this.capacity, _flag, _throttle, _roll);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupPilotingUserTakeOff(byte _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupPilotingUserTakeOff(this.pointer, this.capacity, _state);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupPilotingMotorMode(ARCOMMANDS_POWERUP_PILOTING_MOTORMODE_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupPilotingMotorMode(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupPilotingSettingsSet(ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM _setting, float _value) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupPilotingSettingsSet(this.pointer, this.capacity, _setting.getValue(), _value);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupMediaRecordPictureV2() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupMediaRecordPictureV2(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupMediaRecordVideoV2(ARCOMMANDS_POWERUP_MEDIARECORD_VIDEOV2_RECORD_ENUM _record) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupMediaRecordVideoV2(this.pointer, this.capacity, _record.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupNetworkSettingsWifiSelection(ARCOMMANDS_POWERUP_NETWORKSETTINGS_WIFISELECTION_TYPE_ENUM _type, ARCOMMANDS_POWERUP_NETWORKSETTINGS_WIFISELECTION_BAND_ENUM _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupNetworkSettingsWifiSelection(this.pointer, this.capacity, _type.getValue(), _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupNetworkWifiScan(ARCOMMANDS_POWERUP_NETWORK_WIFISCAN_BAND_ENUM _band) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupNetworkWifiScan(this.pointer, this.capacity, _band.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupNetworkWifiAuthChannel() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupNetworkWifiAuthChannel(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupMediaStreamingVideoEnable(byte _enable) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupMediaStreamingVideoEnable(this.pointer, this.capacity, _enable);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupVideoSettingsAutorecord(byte _enable) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupVideoSettingsAutorecord(this.pointer, this.capacity, _enable);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupVideoSettingsVideoMode(ARCOMMANDS_POWERUP_VIDEOSETTINGS_VIDEOMODE_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupVideoSettingsVideoMode(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupSoundsBuzz(byte _enable) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupSoundsBuzz(this.pointer, this.capacity, _enable);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupPilotingStateAlertStateChanged(ARCOMMANDS_POWERUP_PILOTINGSTATE_ALERTSTATECHANGED_STATE_ENUM _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupPilotingStateAlertStateChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupPilotingStateFlyingStateChanged(ARCOMMANDS_POWERUP_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupPilotingStateFlyingStateChanged(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupPilotingStateMotorModeChanged(ARCOMMANDS_POWERUP_PILOTINGSTATE_MOTORMODECHANGED_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupPilotingStateMotorModeChanged(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupPilotingStateAttitudeChanged(float _roll, float _pitch, float _yaw) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupPilotingStateAttitudeChanged(this.pointer, this.capacity, _roll, _pitch, _yaw);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupPilotingStateAltitudeChanged(float _altitude) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupPilotingStateAltitudeChanged(this.pointer, this.capacity, _altitude);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupPilotingSettingsStateSettingChanged(C1457x93becaeb _setting, float _current, float _min, float _max, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupPilotingSettingsStateSettingChanged(r9.pointer, r9.capacity, _setting.getValue(), _current, _min, _max, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupMediaRecordStatePictureStateChangedV2(C1450x20b2c26f _state, C1449x8fc98758 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupMediaRecordStatePictureStateChangedV2(this.pointer, this.capacity, _state.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupMediaRecordStateVideoStateChangedV2(C1452xf3a2f512 _state, C1451x62b9b9fb _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupMediaRecordStateVideoStateChangedV2(this.pointer, this.capacity, _state.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupMediaRecordEventPictureEventChanged(C1448x7fadf802 _event, C1447xa6880d54 _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupMediaRecordEventPictureEventChanged(this.pointer, this.capacity, _event.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupMediaRecordEventVideoEventChanged(ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_ENUM _event, ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM _error) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupMediaRecordEventVideoEventChanged(this.pointer, this.capacity, _event.getValue(), _error.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupNetworkSettingsStateWifiSelectionChanged(C1455x5039691e _type, C1454xbacc1703 _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupNetworkSettingsStateWifiSelectionChanged(this.pointer, this.capacity, _type.getValue(), _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupNetworkStateWifiScanListChanged(String _ssid, short _rssi, ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupNetworkStateWifiScanListChanged(this.pointer, this.capacity, _ssid, _rssi, _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupNetworkStateAllWifiScanChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupNetworkStateAllWifiScanChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupNetworkStateWifiAuthChannelListChanged(C1456x65d3813 _band, byte _channel, byte _in_or_out) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupNetworkStateWifiAuthChannelListChanged(this.pointer, this.capacity, _band.getValue(), _channel, _in_or_out);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupNetworkStateAllWifiAuthChannelChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupNetworkStateAllWifiAuthChannelChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupNetworkStateLinkQualityChanged(byte _quality) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupNetworkStateLinkQualityChanged(this.pointer, this.capacity, _quality);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupMediaStreamingStateVideoEnableChanged(C1453xad999db7 _enabled) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupMediaStreamingStateVideoEnableChanged(this.pointer, this.capacity, _enabled.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupVideoSettingsStateAutorecordChanged(byte _enabled) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupVideoSettingsStateAutorecordChanged(this.pointer, this.capacity, _enabled);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupVideoSettingsStateVideoModeChanged(ARCOMMANDS_POWERUP_VIDEOSETTINGSSTATE_VIDEOMODECHANGED_MODE_ENUM _mode) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupVideoSettingsStateVideoModeChanged(this.pointer, this.capacity, _mode.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setPowerupSoundsStateBuzzChanged(byte _enabled) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetPowerupSoundsStateBuzzChanged(this.pointer, this.capacity, _enabled);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcMonitorChannels(byte _enable) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcMonitorChannels(this.pointer, this.capacity, _enable);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcStartCalibration(ARCOMMANDS_RC_CALIBRATION_TYPE_ENUM _calibration_type, ARCOMMANDS_RC_CHANNEL_ACTION_ENUM _channel_action, ARCOMMANDS_RC_CHANNEL_TYPE_ENUM _channel_type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcStartCalibration(this.pointer, this.capacity, _calibration_type.getValue(), _channel_action.getValue(), _channel_type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcInvertChannel(ARCOMMANDS_RC_CHANNEL_ACTION_ENUM _action, byte _flag) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcInvertChannel(this.pointer, this.capacity, _action.getValue(), _flag);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcAbortCalibration() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcAbortCalibration(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcResetCalibration() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcResetCalibration(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcEnableReceiver(byte _enable) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcEnableReceiver(this.pointer, this.capacity, _enable);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcReceiverState(ARCOMMANDS_RC_RECEIVER_STATE_ENUM _state, String _protocol, byte _enabled) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcReceiverState(this.pointer, this.capacity, _state.getValue(), _protocol, _enabled);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcChannelsMonitorState(byte _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcChannelsMonitorState(this.pointer, this.capacity, _state);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcChannelValue(byte _id, ARCOMMANDS_RC_CHANNEL_ACTION_ENUM _action, short _value, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcChannelValue(this.pointer, this.capacity, _id, _action.getValue(), _value, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcCalibrationState(ARCOMMANDS_RC_CALIBRATION_TYPE_ENUM _calibration_type, ARCOMMANDS_RC_CHANNEL_ACTION_ENUM _channel_action, int _required, int _calibrated, byte _neutral_calibrated) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcCalibrationState(r9.pointer, r9.capacity, _calibration_type.getValue(), _channel_action.getValue(), _required, _calibrated, _neutral_calibrated);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setRcChannelActionItem(ARCOMMANDS_RC_CHANNEL_ACTION_ENUM _action, int _supported_type, ARCOMMANDS_RC_CHANNEL_TYPE_ENUM _calibrated_type, byte _inverted) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetRcChannelActionItem(this.pointer, this.capacity, _action.getValue(), _supported_type, _calibrated_type.getValue(), _inverted);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiRequestWifiList() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiRequestWifiList(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiRequestCurrentWifi() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiRequestCurrentWifi(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiConnectToWifi(String _bssid, String _ssid, String _passphrase) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiConnectToWifi(this.pointer, this.capacity, _bssid, _ssid, _passphrase);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiForgetWifi(String _ssid) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiForgetWifi(this.pointer, this.capacity, _ssid);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiWifiAuthChannel() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiWifiAuthChannel(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerDeviceRequestDeviceList() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerDeviceRequestDeviceList(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerDeviceRequestCurrentDevice() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerDeviceRequestCurrentDevice(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerDeviceConnectToDevice(String _deviceName) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerDeviceConnectToDevice(this.pointer, this.capacity, _deviceName);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSettingsAllSettings() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSettingsAllSettings(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSettingsReset() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSettingsReset(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerCommonAllStates() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerCommonAllStates(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAccessPointSettingsAccessPointSSID(String _ssid) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAccessPointSettingsAccessPointSSID(this.pointer, this.capacity, _ssid);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAccessPointSettingsAccessPointChannel(byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAccessPointSettingsAccessPointChannel(this.pointer, this.capacity, _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAccessPointSettingsWifiSelection(C1463xd31e3426 _type, C1462x3db0e20b _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAccessPointSettingsWifiSelection(this.pointer, this.capacity, _type.getValue(), _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAccessPointSettingsWifiSecurity(C1461xa1dbf559 _security_type, String _key) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAccessPointSettingsWifiSecurity(this.pointer, this.capacity, _security_type.getValue(), _key);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerCameraResetOrientation() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerCameraResetOrientation(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerGamepadInfosGetGamepadControls() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerGamepadInfosGetGamepadControls(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerButtonMappingsGetCurrentButtonMappings() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerButtonMappingsGetCurrentButtonMappings(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerButtonMappingsGetAvailableButtonMappings() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerButtonMappingsGetAvailableButtonMappings(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerButtonMappingsSetButtonMapping(int _key_id, String _mapping_uid) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerButtonMappingsSetButtonMapping(this.pointer, this.capacity, _key_id, _mapping_uid);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerButtonMappingsDefaultButtonMapping() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerButtonMappingsDefaultButtonMapping(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisMappingsGetCurrentAxisMappings() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisMappingsGetCurrentAxisMappings(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisMappingsGetAvailableAxisMappings() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisMappingsGetAvailableAxisMappings(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisMappingsSetAxisMapping(int _axis_id, String _mapping_uid) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisMappingsSetAxisMapping(this.pointer, this.capacity, _axis_id, _mapping_uid);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisMappingsDefaultAxisMapping() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisMappingsDefaultAxisMapping(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisFiltersGetCurrentAxisFilters() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisFiltersGetCurrentAxisFilters(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisFiltersGetPresetAxisFilters() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisFiltersGetPresetAxisFilters(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisFiltersSetAxisFilter(int _axis_id, String _filter_uid_or_builder) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisFiltersSetAxisFilter(this.pointer, this.capacity, _axis_id, _filter_uid_or_builder);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisFiltersDefaultAxisFilters() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisFiltersDefaultAxisFilters(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerCoPilotingSetPilotingSource(C1466x668379ec _source) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerCoPilotingSetPilotingSource(this.pointer, this.capacity, _source.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    /* renamed from: setSkyControllerCalibrationEnableMagnetoCalibrationQualityUpdates */
    public ARCOMMANDS_GENERATOR_ERROR_ENUM m616xc8042fa2(byte _enable) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m575xebe21a4b(this.pointer, this.capacity, _enable);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerFactoryReset() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerFactoryReset(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiStateWifiList(String _bssid, String _ssid, byte _secured, byte _saved, int _rssi, int _frequency) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiStateWifiList(r10.pointer, r10.capacity, _bssid, _ssid, _secured, _saved, _rssi, _frequency);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiStateConnexionChanged(String _ssid, ARCOMMANDS_SKYCONTROLLER_WIFISTATE_CONNEXIONCHANGED_STATUS_ENUM _status) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiStateConnexionChanged(this.pointer, this.capacity, _ssid, _status.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiStateWifiAuthChannelListChanged(C1472xd71dd87 _band, byte _channel, byte _in_or_out) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiStateWifiAuthChannelListChanged(this.pointer, this.capacity, _band.getValue(), _channel, _in_or_out);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiStateAllWifiAuthChannelChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiStateAllWifiAuthChannelChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiStateWifiSignalChanged(byte _level) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiStateWifiSignalChanged(this.pointer, this.capacity, _level);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiStateWifiAuthChannelListChangedV2(C1471xd692b263 _band, byte _channel, byte _in_or_out, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiStateWifiAuthChannelListChangedV2(this.pointer, this.capacity, _band.getValue(), _channel, _in_or_out, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiStateWifiCountryChanged(String _code) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiStateWifiCountryChanged(this.pointer, this.capacity, _code);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerWifiStateWifiEnvironmentChanged(C1473x8d24462b _environment) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerWifiStateWifiEnvironmentChanged(this.pointer, this.capacity, _environment.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerDeviceStateDeviceList(String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerDeviceStateDeviceList(this.pointer, this.capacity, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerDeviceStateConnexionChanged(C1467x97a49886 _status, String _deviceName, short _deviceProductID) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerDeviceStateConnexionChanged(this.pointer, this.capacity, _status.getValue(), _deviceName, _deviceProductID);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSettingsStateAllSettingsChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSettingsStateAllSettingsChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSettingsStateResetChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSettingsStateResetChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSettingsStateProductSerialChanged(String _serialNumber) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSettingsStateProductSerialChanged(this.pointer, this.capacity, _serialNumber);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSettingsStateProductVariantChanged(C1469xef2e29b5 _variant) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSettingsStateProductVariantChanged(this.pointer, this.capacity, _variant.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSettingsStateProductVersionChanged(String _software, String _hardware) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSettingsStateProductVersionChanged(this.pointer, this.capacity, _software, _hardware);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerCommonStateAllStatesChanged() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerCommonStateAllStatesChanged(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSkyControllerStateBatteryChanged(byte _percent) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSkyControllerStateBatteryChanged(this.pointer, this.capacity, _percent);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSkyControllerStateGpsFixChanged(byte _fixed) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSkyControllerStateGpsFixChanged(this.pointer, this.capacity, _fixed);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSkyControllerStateGpsPositionChanged(double _latitude, double _longitude, double _altitude, float _heading) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSkyControllerStateGpsPositionChanged(r11.pointer, r11.capacity, _latitude, _longitude, _altitude, _heading);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSkyControllerStateBatteryState(C1470xefdece67 _state) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSkyControllerStateBatteryState(this.pointer, this.capacity, _state.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerSkyControllerStateAttitudeChanged(float _q0, float _q1, float _q2, float _q3) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerSkyControllerStateAttitudeChanged(this.pointer, this.capacity, _q0, _q1, _q2, _q3);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAccessPointSettingsStateAccessPointSSIDChanged(String _ssid) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m568x1269781(this.pointer, this.capacity, _ssid);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    /* renamed from: setSkyControllerAccessPointSettingsStateAccessPointChannelChanged */
    public ARCOMMANDS_GENERATOR_ERROR_ENUM m614x4036c7e0(byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m567x6414b289(this.pointer, this.capacity, _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAccessPointSettingsStateWifiSelectionChanged(C1460xf32549b9 _type, C1459x5db7f79e _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m570x3cd4e891(this.pointer, this.capacity, _type.getValue(), _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAccessPointSettingsStateWifiSecurityChanged(C1458x62c5ec1c _security_type, String _key) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m569x609ba12b(this.pointer, this.capacity, _security_type.getValue(), _key);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerGamepadInfosStateGamepadControl(C1468x849d66fd _type, int _id, String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerGamepadInfosStateGamepadControl(this.pointer, this.capacity, _type.getValue(), _id, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerGamepadInfosStateAllGamepadControlsSent() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerGamepadInfosStateAllGamepadControlsSent(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerButtonMappingsStateCurrentButtonMappings(int _key_id, String _mapping_uid) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerButtonMappingsStateCurrentButtonMappings(this.pointer, this.capacity, _key_id, _mapping_uid);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerButtonMappingsStateAllCurrentButtonMappingsSent() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m574xa83e1bef(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerButtonMappingsStateAvailableButtonMappings(String _mapping_uid, String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerButtonMappingsStateAvailableButtonMappings(this.pointer, this.capacity, _mapping_uid, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    /* renamed from: setSkyControllerButtonMappingsStateAllAvailableButtonsMappingsSent */
    public ARCOMMANDS_GENERATOR_ERROR_ENUM m615xac1f6fb7() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m573x3feda2e(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisMappingsStateCurrentAxisMappings(int _axis_id, String _mapping_uid) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisMappingsStateCurrentAxisMappings(this.pointer, this.capacity, _axis_id, _mapping_uid);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisMappingsStateAllCurrentAxisMappingsSent() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m572xbce56a0f(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisMappingsStateAvailableAxisMappings(String _mapping_uid, String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisMappingsStateAvailableAxisMappings(this.pointer, this.capacity, _mapping_uid, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisMappingsStateAllAvailableAxisMappingsSent() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m571x81a5915f(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisFiltersStateCurrentAxisFilters(int _axis_id, String _filter_uid_or_builder) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisFiltersStateCurrentAxisFilters(this.pointer, this.capacity, _axis_id, _filter_uid_or_builder);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisFiltersStateAllCurrentFiltersSent() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisFiltersStateAllCurrentFiltersSent(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisFiltersStatePresetAxisFilters(String _filter_uid, String _name) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisFiltersStatePresetAxisFilters(this.pointer, this.capacity, _filter_uid, _name);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerAxisFiltersStateAllPresetFiltersSent() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerAxisFiltersStateAllPresetFiltersSent(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerCoPilotingStatePilotingSource(C1465xc0fa5d37 _source) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerCoPilotingStatePilotingSource(this.pointer, this.capacity, _source.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerCalibrationStateMagnetoCalibrationState(C1464xb8d78b0b _status, byte _X_Quality, byte _Y_Quality, byte _Z_Quality) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerCalibrationStateMagnetoCalibrationState(this.pointer, this.capacity, _status.getValue(), _X_Quality, _Y_Quality, _Z_Quality);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    /* renamed from: setSkyControllerCalibrationStateMagnetoCalibrationQualityUpdatesState */
    public ARCOMMANDS_GENERATOR_ERROR_ENUM m617x94f561bf(byte _enabled) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = m576x6d1bcfe8(this.pointer, this.capacity, _enabled);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerButtonEventsSettings() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerButtonEventsSettings(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setSkyControllerCommonEventStateShutdown(ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_ENUM _reason) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetSkyControllerCommonEventStateShutdown(this.pointer, this.capacity, _reason.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiScan(byte _band) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiScan(this.pointer, this.capacity, _band);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiUpdateAuthorizedChannels() {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiUpdateAuthorizedChannels(this.pointer, this.capacity);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiSetApChannel(ARCOMMANDS_WIFI_SELECTION_TYPE_ENUM _type, ARCOMMANDS_WIFI_BAND_ENUM _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiSetApChannel(this.pointer, this.capacity, _type.getValue(), _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiSetSecurity(ARCOMMANDS_WIFI_SECURITY_TYPE_ENUM _type, String _key, ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM _key_type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiSetSecurity(this.pointer, this.capacity, _type.getValue(), _key, _key_type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiSetCountry(ARCOMMANDS_WIFI_COUNTRY_SELECTION_ENUM _selection_mode, String _code) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiSetCountry(this.pointer, this.capacity, _selection_mode.getValue(), _code);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiSetEnvironment(ARCOMMANDS_WIFI_ENVIRONMENT_ENUM _environment) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiSetEnvironment(this.pointer, this.capacity, _environment.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiScannedItem(String _ssid, short _rssi, ARCOMMANDS_WIFI_BAND_ENUM _band, byte _channel, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiScannedItem(r9.pointer, r9.capacity, _ssid, _rssi, _band.getValue(), _channel, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiAuthorizedChannel(ARCOMMANDS_WIFI_BAND_ENUM _band, byte _channel, byte _environment, byte _list_flags) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiAuthorizedChannel(this.pointer, this.capacity, _band.getValue(), _channel, _environment, _list_flags);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiApChannelChanged(ARCOMMANDS_WIFI_SELECTION_TYPE_ENUM _type, ARCOMMANDS_WIFI_BAND_ENUM _band, byte _channel) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiApChannelChanged(this.pointer, this.capacity, _type.getValue(), _band.getValue(), _channel);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiSecurityChanged(String _key, ARCOMMANDS_WIFI_SECURITY_TYPE_ENUM _key_type) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiSecurityChanged(this.pointer, this.capacity, _key, _key_type.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiCountryChanged(ARCOMMANDS_WIFI_COUNTRY_SELECTION_ENUM _selection_mode, String _code) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiCountryChanged(this.pointer, this.capacity, _selection_mode.getValue(), _code);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiEnvironmentChanged(ARCOMMANDS_WIFI_ENVIRONMENT_ENUM _environment) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiEnvironmentChanged(this.pointer, this.capacity, _environment.getValue());
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiRssiChanged(short _rssi) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiRssiChanged(this.pointer, this.capacity, _rssi);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public ARCOMMANDS_GENERATOR_ERROR_ENUM setWifiSupportedCountries(String _countries) {
        ARCOMMANDS_GENERATOR_ERROR_ENUM err = ARCOMMANDS_GENERATOR_ERROR_ENUM.ARCOMMANDS_GENERATOR_ERROR;
        if (!this.valid) {
            return err;
        }
        int errInt = nativeSetWifiSupportedCountries(this.pointer, this.capacity, _countries);
        if (ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt) != null) {
            err = ARCOMMANDS_GENERATOR_ERROR_ENUM.getFromValue(errInt);
        }
        return err;
    }

    public static void setGenericDefaultListener(ARCommandGenericDefaultListener _ARCommandGenericDefaultListener_PARAM) {
        _decoder.setGenericDefaultListener(_ARCommandGenericDefaultListener_PARAM);
    }

    public static void setGenericSetDroneSettingsListener(ARCommandGenericSetDroneSettingsListener _ARCommandGenericSetDroneSettingsListener_PARAM) {
        _decoder.setGenericSetDroneSettingsListener(_ARCommandGenericSetDroneSettingsListener_PARAM);
    }

    public static void setGenericDroneSettingsChangedListener(ARCommandGenericDroneSettingsChangedListener _ARCommandGenericDroneSettingsChangedListener_PARAM) {
        _decoder.setGenericDroneSettingsChangedListener(_ARCommandGenericDroneSettingsChangedListener_PARAM);
    }

    public static void setARDrone3PilotingFlatTrimListener(ARCommandARDrone3PilotingFlatTrimListener _ARCommandARDrone3PilotingFlatTrimListener_PARAM) {
        _decoder.setARDrone3PilotingFlatTrimListener(_ARCommandARDrone3PilotingFlatTrimListener_PARAM);
    }

    public static void setARDrone3PilotingTakeOffListener(ARCommandARDrone3PilotingTakeOffListener _ARCommandARDrone3PilotingTakeOffListener_PARAM) {
        _decoder.setARDrone3PilotingTakeOffListener(_ARCommandARDrone3PilotingTakeOffListener_PARAM);
    }

    public static void setARDrone3PilotingPCMDListener(ARCommandARDrone3PilotingPCMDListener _ARCommandARDrone3PilotingPCMDListener_PARAM) {
        _decoder.setARDrone3PilotingPCMDListener(_ARCommandARDrone3PilotingPCMDListener_PARAM);
    }

    public static void setARDrone3PilotingLandingListener(ARCommandARDrone3PilotingLandingListener _ARCommandARDrone3PilotingLandingListener_PARAM) {
        _decoder.setARDrone3PilotingLandingListener(_ARCommandARDrone3PilotingLandingListener_PARAM);
    }

    public static void setARDrone3PilotingEmergencyListener(ARCommandARDrone3PilotingEmergencyListener _ARCommandARDrone3PilotingEmergencyListener_PARAM) {
        _decoder.setARDrone3PilotingEmergencyListener(_ARCommandARDrone3PilotingEmergencyListener_PARAM);
    }

    public static void setARDrone3PilotingNavigateHomeListener(ARCommandARDrone3PilotingNavigateHomeListener _ARCommandARDrone3PilotingNavigateHomeListener_PARAM) {
        _decoder.setARDrone3PilotingNavigateHomeListener(_ARCommandARDrone3PilotingNavigateHomeListener_PARAM);
    }

    public static void setARDrone3PilotingAutoTakeOffModeListener(ARCommandARDrone3PilotingAutoTakeOffModeListener _ARCommandARDrone3PilotingAutoTakeOffModeListener_PARAM) {
        _decoder.setARDrone3PilotingAutoTakeOffModeListener(_ARCommandARDrone3PilotingAutoTakeOffModeListener_PARAM);
    }

    public static void setARDrone3PilotingMoveByListener(ARCommandARDrone3PilotingMoveByListener _ARCommandARDrone3PilotingMoveByListener_PARAM) {
        _decoder.setARDrone3PilotingMoveByListener(_ARCommandARDrone3PilotingMoveByListener_PARAM);
    }

    public static void setARDrone3PilotingUserTakeOffListener(ARCommandARDrone3PilotingUserTakeOffListener _ARCommandARDrone3PilotingUserTakeOffListener_PARAM) {
        _decoder.setARDrone3PilotingUserTakeOffListener(_ARCommandARDrone3PilotingUserTakeOffListener_PARAM);
    }

    public static void setARDrone3PilotingCircleListener(ARCommandARDrone3PilotingCircleListener _ARCommandARDrone3PilotingCircleListener_PARAM) {
        _decoder.setARDrone3PilotingCircleListener(_ARCommandARDrone3PilotingCircleListener_PARAM);
    }

    public static void setARDrone3PilotingMoveToListener(ARCommandARDrone3PilotingMoveToListener _ARCommandARDrone3PilotingMoveToListener_PARAM) {
        _decoder.setARDrone3PilotingMoveToListener(_ARCommandARDrone3PilotingMoveToListener_PARAM);
    }

    public static void setARDrone3PilotingCancelMoveToListener(ARCommandARDrone3PilotingCancelMoveToListener _ARCommandARDrone3PilotingCancelMoveToListener_PARAM) {
        _decoder.setARDrone3PilotingCancelMoveToListener(_ARCommandARDrone3PilotingCancelMoveToListener_PARAM);
    }

    public static void setARDrone3AnimationsFlipListener(ARCommandARDrone3AnimationsFlipListener _ARCommandARDrone3AnimationsFlipListener_PARAM) {
        _decoder.setARDrone3AnimationsFlipListener(_ARCommandARDrone3AnimationsFlipListener_PARAM);
    }

    public static void setARDrone3CameraOrientationListener(ARCommandARDrone3CameraOrientationListener _ARCommandARDrone3CameraOrientationListener_PARAM) {
        _decoder.setARDrone3CameraOrientationListener(_ARCommandARDrone3CameraOrientationListener_PARAM);
    }

    public static void setARDrone3CameraOrientationV2Listener(ARCommandARDrone3CameraOrientationV2Listener _ARCommandARDrone3CameraOrientationV2Listener_PARAM) {
        _decoder.setARDrone3CameraOrientationV2Listener(_ARCommandARDrone3CameraOrientationV2Listener_PARAM);
    }

    public static void setARDrone3CameraVelocityListener(ARCommandARDrone3CameraVelocityListener _ARCommandARDrone3CameraVelocityListener_PARAM) {
        _decoder.setARDrone3CameraVelocityListener(_ARCommandARDrone3CameraVelocityListener_PARAM);
    }

    public static void setARDrone3MediaRecordPictureListener(ARCommandARDrone3MediaRecordPictureListener _ARCommandARDrone3MediaRecordPictureListener_PARAM) {
        _decoder.setARDrone3MediaRecordPictureListener(_ARCommandARDrone3MediaRecordPictureListener_PARAM);
    }

    public static void setARDrone3MediaRecordVideoListener(ARCommandARDrone3MediaRecordVideoListener _ARCommandARDrone3MediaRecordVideoListener_PARAM) {
        _decoder.setARDrone3MediaRecordVideoListener(_ARCommandARDrone3MediaRecordVideoListener_PARAM);
    }

    public static void setARDrone3MediaRecordPictureV2Listener(ARCommandARDrone3MediaRecordPictureV2Listener _ARCommandARDrone3MediaRecordPictureV2Listener_PARAM) {
        _decoder.setARDrone3MediaRecordPictureV2Listener(_ARCommandARDrone3MediaRecordPictureV2Listener_PARAM);
    }

    public static void setARDrone3MediaRecordVideoV2Listener(ARCommandARDrone3MediaRecordVideoV2Listener _ARCommandARDrone3MediaRecordVideoV2Listener_PARAM) {
        _decoder.setARDrone3MediaRecordVideoV2Listener(_ARCommandARDrone3MediaRecordVideoV2Listener_PARAM);
    }

    public static void setARDrone3NetworkWifiScanListener(ARCommandARDrone3NetworkWifiScanListener _ARCommandARDrone3NetworkWifiScanListener_PARAM) {
        _decoder.setARDrone3NetworkWifiScanListener(_ARCommandARDrone3NetworkWifiScanListener_PARAM);
    }

    public static void setARDrone3NetworkWifiAuthChannelListener(ARCommandARDrone3NetworkWifiAuthChannelListener _ARCommandARDrone3NetworkWifiAuthChannelListener_PARAM) {
        _decoder.setARDrone3NetworkWifiAuthChannelListener(_ARCommandARDrone3NetworkWifiAuthChannelListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsMaxAltitudeListener(ARCommandARDrone3PilotingSettingsMaxAltitudeListener _ARCommandARDrone3PilotingSettingsMaxAltitudeListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsMaxAltitudeListener(_ARCommandARDrone3PilotingSettingsMaxAltitudeListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsMaxTiltListener(ARCommandARDrone3PilotingSettingsMaxTiltListener _ARCommandARDrone3PilotingSettingsMaxTiltListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsMaxTiltListener(_ARCommandARDrone3PilotingSettingsMaxTiltListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsAbsolutControlListener(ARCommandARDrone3PilotingSettingsAbsolutControlListener _ARCommandARDrone3PilotingSettingsAbsolutControlListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsAbsolutControlListener(_ARCommandARDrone3PilotingSettingsAbsolutControlListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsMaxDistanceListener(ARCommandARDrone3PilotingSettingsMaxDistanceListener _ARCommandARDrone3PilotingSettingsMaxDistanceListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsMaxDistanceListener(_ARCommandARDrone3PilotingSettingsMaxDistanceListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsNoFlyOverMaxDistanceListener(ARCommandARDrone3PilotingSettingsNoFlyOverMaxDistanceListener _ARCommandARDrone3PilotingSettingsNoFlyOverMaxDistanceListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsNoFlyOverMaxDistanceListener(_ARCommandARDrone3PilotingSettingsNoFlyOverMaxDistanceListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalSpeedListener */
    public static void m579xf0cb099a(C1486x8848ab22 _ARCommandARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalSpeedListener_PARAM) {
        _decoder.m62xf0cb099a(_ARCommandARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalSpeedListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalSpeedListener */
    public static void m582xaf561848(C1489x275e7bd0 _ARCommandARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalSpeedListener_PARAM) {
        _decoder.m65xaf561848(_ARCommandARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalSpeedListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalAccelerationListener */
    public static void m578x575948f5(C1485x96145e6d _ARCommandARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalAccelerationListener_PARAM) {
        _decoder.m61x575948f5(_ARCommandARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalAccelerationListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalAccelerationListener */
    public static void m581x21eefa87(C1488x54e10dff _ARCommandARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalAccelerationListener_PARAM) {
        _decoder.m64x21eefa87(_ARCommandARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalAccelerationListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxRotationSpeedListener */
    public static void m580xfc38bd40(C1487x744120c8 _ARCommandARDrone3PilotingSettingsSetAutonomousFlightMaxRotationSpeedListener_PARAM) {
        _decoder.m63xfc38bd40(_ARCommandARDrone3PilotingSettingsSetAutonomousFlightMaxRotationSpeedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsBankedTurnListener(ARCommandARDrone3PilotingSettingsBankedTurnListener _ARCommandARDrone3PilotingSettingsBankedTurnListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsBankedTurnListener(_ARCommandARDrone3PilotingSettingsBankedTurnListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsMinAltitudeListener(ARCommandARDrone3PilotingSettingsMinAltitudeListener _ARCommandARDrone3PilotingSettingsMinAltitudeListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsMinAltitudeListener(_ARCommandARDrone3PilotingSettingsMinAltitudeListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsCirclingDirectionListener(ARCommandARDrone3PilotingSettingsCirclingDirectionListener _ARCommandARDrone3PilotingSettingsCirclingDirectionListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsCirclingDirectionListener(_ARCommandARDrone3PilotingSettingsCirclingDirectionListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsCirclingRadiusListener(ARCommandARDrone3PilotingSettingsCirclingRadiusListener _ARCommandARDrone3PilotingSettingsCirclingRadiusListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsCirclingRadiusListener(_ARCommandARDrone3PilotingSettingsCirclingRadiusListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsCirclingAltitudeListener(ARCommandARDrone3PilotingSettingsCirclingAltitudeListener _ARCommandARDrone3PilotingSettingsCirclingAltitudeListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsCirclingAltitudeListener(_ARCommandARDrone3PilotingSettingsCirclingAltitudeListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsPitchModeListener(ARCommandARDrone3PilotingSettingsPitchModeListener _ARCommandARDrone3PilotingSettingsPitchModeListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsPitchModeListener(_ARCommandARDrone3PilotingSettingsPitchModeListener_PARAM);
    }

    public static void setARDrone3SpeedSettingsMaxVerticalSpeedListener(ARCommandARDrone3SpeedSettingsMaxVerticalSpeedListener _ARCommandARDrone3SpeedSettingsMaxVerticalSpeedListener_PARAM) {
        _decoder.setARDrone3SpeedSettingsMaxVerticalSpeedListener(_ARCommandARDrone3SpeedSettingsMaxVerticalSpeedListener_PARAM);
    }

    public static void setARDrone3SpeedSettingsMaxRotationSpeedListener(ARCommandARDrone3SpeedSettingsMaxRotationSpeedListener _ARCommandARDrone3SpeedSettingsMaxRotationSpeedListener_PARAM) {
        _decoder.setARDrone3SpeedSettingsMaxRotationSpeedListener(_ARCommandARDrone3SpeedSettingsMaxRotationSpeedListener_PARAM);
    }

    public static void setARDrone3SpeedSettingsHullProtectionListener(ARCommandARDrone3SpeedSettingsHullProtectionListener _ARCommandARDrone3SpeedSettingsHullProtectionListener_PARAM) {
        _decoder.setARDrone3SpeedSettingsHullProtectionListener(_ARCommandARDrone3SpeedSettingsHullProtectionListener_PARAM);
    }

    public static void setARDrone3SpeedSettingsOutdoorListener(ARCommandARDrone3SpeedSettingsOutdoorListener _ARCommandARDrone3SpeedSettingsOutdoorListener_PARAM) {
        _decoder.setARDrone3SpeedSettingsOutdoorListener(_ARCommandARDrone3SpeedSettingsOutdoorListener_PARAM);
    }

    public static void setARDrone3SpeedSettingsMaxPitchRollRotationSpeedListener(ARCommandARDrone3SpeedSettingsMaxPitchRollRotationSpeedListener _ARCommandARDrone3SpeedSettingsMaxPitchRollRotationSpeedListener_PARAM) {
        _decoder.setARDrone3SpeedSettingsMaxPitchRollRotationSpeedListener(_ARCommandARDrone3SpeedSettingsMaxPitchRollRotationSpeedListener_PARAM);
    }

    public static void setARDrone3NetworkSettingsWifiSelectionListener(ARCommandARDrone3NetworkSettingsWifiSelectionListener _ARCommandARDrone3NetworkSettingsWifiSelectionListener_PARAM) {
        _decoder.setARDrone3NetworkSettingsWifiSelectionListener(_ARCommandARDrone3NetworkSettingsWifiSelectionListener_PARAM);
    }

    public static void setARDrone3NetworkSettingsWifiSecurityListener(ARCommandARDrone3NetworkSettingsWifiSecurityListener _ARCommandARDrone3NetworkSettingsWifiSecurityListener_PARAM) {
        _decoder.setARDrone3NetworkSettingsWifiSecurityListener(_ARCommandARDrone3NetworkSettingsWifiSecurityListener_PARAM);
    }

    public static void setARDrone3PictureSettingsPictureFormatSelectionListener(ARCommandARDrone3PictureSettingsPictureFormatSelectionListener _ARCommandARDrone3PictureSettingsPictureFormatSelectionListener_PARAM) {
        _decoder.setARDrone3PictureSettingsPictureFormatSelectionListener(_ARCommandARDrone3PictureSettingsPictureFormatSelectionListener_PARAM);
    }

    public static void setARDrone3PictureSettingsAutoWhiteBalanceSelectionListener(C1477xe12d7523 _ARCommandARDrone3PictureSettingsAutoWhiteBalanceSelectionListener_PARAM) {
        _decoder.setARDrone3PictureSettingsAutoWhiteBalanceSelectionListener(_ARCommandARDrone3PictureSettingsAutoWhiteBalanceSelectionListener_PARAM);
    }

    public static void setARDrone3PictureSettingsExpositionSelectionListener(ARCommandARDrone3PictureSettingsExpositionSelectionListener _ARCommandARDrone3PictureSettingsExpositionSelectionListener_PARAM) {
        _decoder.setARDrone3PictureSettingsExpositionSelectionListener(_ARCommandARDrone3PictureSettingsExpositionSelectionListener_PARAM);
    }

    public static void setARDrone3PictureSettingsSaturationSelectionListener(ARCommandARDrone3PictureSettingsSaturationSelectionListener _ARCommandARDrone3PictureSettingsSaturationSelectionListener_PARAM) {
        _decoder.setARDrone3PictureSettingsSaturationSelectionListener(_ARCommandARDrone3PictureSettingsSaturationSelectionListener_PARAM);
    }

    public static void setARDrone3PictureSettingsTimelapseSelectionListener(ARCommandARDrone3PictureSettingsTimelapseSelectionListener _ARCommandARDrone3PictureSettingsTimelapseSelectionListener_PARAM) {
        _decoder.setARDrone3PictureSettingsTimelapseSelectionListener(_ARCommandARDrone3PictureSettingsTimelapseSelectionListener_PARAM);
    }

    public static void setARDrone3PictureSettingsVideoAutorecordSelectionListener(ARCommandARDrone3PictureSettingsVideoAutorecordSelectionListener _ARCommandARDrone3PictureSettingsVideoAutorecordSelectionListener_PARAM) {
        _decoder.setARDrone3PictureSettingsVideoAutorecordSelectionListener(_ARCommandARDrone3PictureSettingsVideoAutorecordSelectionListener_PARAM);
    }

    public static void setARDrone3PictureSettingsVideoStabilizationModeListener(ARCommandARDrone3PictureSettingsVideoStabilizationModeListener _ARCommandARDrone3PictureSettingsVideoStabilizationModeListener_PARAM) {
        _decoder.setARDrone3PictureSettingsVideoStabilizationModeListener(_ARCommandARDrone3PictureSettingsVideoStabilizationModeListener_PARAM);
    }

    public static void setARDrone3PictureSettingsVideoRecordingModeListener(ARCommandARDrone3PictureSettingsVideoRecordingModeListener _ARCommandARDrone3PictureSettingsVideoRecordingModeListener_PARAM) {
        _decoder.setARDrone3PictureSettingsVideoRecordingModeListener(_ARCommandARDrone3PictureSettingsVideoRecordingModeListener_PARAM);
    }

    public static void setARDrone3PictureSettingsVideoFramerateListener(ARCommandARDrone3PictureSettingsVideoFramerateListener _ARCommandARDrone3PictureSettingsVideoFramerateListener_PARAM) {
        _decoder.setARDrone3PictureSettingsVideoFramerateListener(_ARCommandARDrone3PictureSettingsVideoFramerateListener_PARAM);
    }

    public static void setARDrone3PictureSettingsVideoResolutionsListener(ARCommandARDrone3PictureSettingsVideoResolutionsListener _ARCommandARDrone3PictureSettingsVideoResolutionsListener_PARAM) {
        _decoder.setARDrone3PictureSettingsVideoResolutionsListener(_ARCommandARDrone3PictureSettingsVideoResolutionsListener_PARAM);
    }

    public static void setARDrone3MediaStreamingVideoEnableListener(ARCommandARDrone3MediaStreamingVideoEnableListener _ARCommandARDrone3MediaStreamingVideoEnableListener_PARAM) {
        _decoder.setARDrone3MediaStreamingVideoEnableListener(_ARCommandARDrone3MediaStreamingVideoEnableListener_PARAM);
    }

    public static void setARDrone3MediaStreamingVideoStreamModeListener(ARCommandARDrone3MediaStreamingVideoStreamModeListener _ARCommandARDrone3MediaStreamingVideoStreamModeListener_PARAM) {
        _decoder.setARDrone3MediaStreamingVideoStreamModeListener(_ARCommandARDrone3MediaStreamingVideoStreamModeListener_PARAM);
    }

    public static void setARDrone3GPSSettingsSetHomeListener(ARCommandARDrone3GPSSettingsSetHomeListener _ARCommandARDrone3GPSSettingsSetHomeListener_PARAM) {
        _decoder.setARDrone3GPSSettingsSetHomeListener(_ARCommandARDrone3GPSSettingsSetHomeListener_PARAM);
    }

    public static void setARDrone3GPSSettingsResetHomeListener(ARCommandARDrone3GPSSettingsResetHomeListener _ARCommandARDrone3GPSSettingsResetHomeListener_PARAM) {
        _decoder.setARDrone3GPSSettingsResetHomeListener(_ARCommandARDrone3GPSSettingsResetHomeListener_PARAM);
    }

    public static void setARDrone3GPSSettingsSendControllerGPSListener(ARCommandARDrone3GPSSettingsSendControllerGPSListener _ARCommandARDrone3GPSSettingsSendControllerGPSListener_PARAM) {
        _decoder.setARDrone3GPSSettingsSendControllerGPSListener(_ARCommandARDrone3GPSSettingsSendControllerGPSListener_PARAM);
    }

    public static void setARDrone3GPSSettingsHomeTypeListener(ARCommandARDrone3GPSSettingsHomeTypeListener _ARCommandARDrone3GPSSettingsHomeTypeListener_PARAM) {
        _decoder.setARDrone3GPSSettingsHomeTypeListener(_ARCommandARDrone3GPSSettingsHomeTypeListener_PARAM);
    }

    public static void setARDrone3GPSSettingsReturnHomeDelayListener(ARCommandARDrone3GPSSettingsReturnHomeDelayListener _ARCommandARDrone3GPSSettingsReturnHomeDelayListener_PARAM) {
        _decoder.setARDrone3GPSSettingsReturnHomeDelayListener(_ARCommandARDrone3GPSSettingsReturnHomeDelayListener_PARAM);
    }

    public static void setARDrone3AntiflickeringElectricFrequencyListener(ARCommandARDrone3AntiflickeringElectricFrequencyListener _ARCommandARDrone3AntiflickeringElectricFrequencyListener_PARAM) {
        _decoder.setARDrone3AntiflickeringElectricFrequencyListener(_ARCommandARDrone3AntiflickeringElectricFrequencyListener_PARAM);
    }

    public static void setARDrone3AntiflickeringSetModeListener(ARCommandARDrone3AntiflickeringSetModeListener _ARCommandARDrone3AntiflickeringSetModeListener_PARAM) {
        _decoder.setARDrone3AntiflickeringSetModeListener(_ARCommandARDrone3AntiflickeringSetModeListener_PARAM);
    }

    public static void setARDrone3MediaRecordStatePictureStateChangedListener(ARCommandARDrone3MediaRecordStatePictureStateChangedListener _ARCommandARDrone3MediaRecordStatePictureStateChangedListener_PARAM) {
        _decoder.setARDrone3MediaRecordStatePictureStateChangedListener(_ARCommandARDrone3MediaRecordStatePictureStateChangedListener_PARAM);
    }

    public static void setARDrone3MediaRecordStateVideoStateChangedListener(ARCommandARDrone3MediaRecordStateVideoStateChangedListener _ARCommandARDrone3MediaRecordStateVideoStateChangedListener_PARAM) {
        _decoder.setARDrone3MediaRecordStateVideoStateChangedListener(_ARCommandARDrone3MediaRecordStateVideoStateChangedListener_PARAM);
    }

    public static void setARDrone3MediaRecordStatePictureStateChangedV2Listener(ARCommandARDrone3MediaRecordStatePictureStateChangedV2Listener _ARCommandARDrone3MediaRecordStatePictureStateChangedV2Listener_PARAM) {
        _decoder.setARDrone3MediaRecordStatePictureStateChangedV2Listener(_ARCommandARDrone3MediaRecordStatePictureStateChangedV2Listener_PARAM);
    }

    public static void setARDrone3MediaRecordStateVideoStateChangedV2Listener(ARCommandARDrone3MediaRecordStateVideoStateChangedV2Listener _ARCommandARDrone3MediaRecordStateVideoStateChangedV2Listener_PARAM) {
        _decoder.setARDrone3MediaRecordStateVideoStateChangedV2Listener(_ARCommandARDrone3MediaRecordStateVideoStateChangedV2Listener_PARAM);
    }

    public static void setARDrone3MediaRecordStateVideoResolutionStateListener(ARCommandARDrone3MediaRecordStateVideoResolutionStateListener _ARCommandARDrone3MediaRecordStateVideoResolutionStateListener_PARAM) {
        _decoder.setARDrone3MediaRecordStateVideoResolutionStateListener(_ARCommandARDrone3MediaRecordStateVideoResolutionStateListener_PARAM);
    }

    public static void setARDrone3MediaRecordEventPictureEventChangedListener(ARCommandARDrone3MediaRecordEventPictureEventChangedListener _ARCommandARDrone3MediaRecordEventPictureEventChangedListener_PARAM) {
        _decoder.setARDrone3MediaRecordEventPictureEventChangedListener(_ARCommandARDrone3MediaRecordEventPictureEventChangedListener_PARAM);
    }

    public static void setARDrone3MediaRecordEventVideoEventChangedListener(ARCommandARDrone3MediaRecordEventVideoEventChangedListener _ARCommandARDrone3MediaRecordEventVideoEventChangedListener_PARAM) {
        _decoder.setARDrone3MediaRecordEventVideoEventChangedListener(_ARCommandARDrone3MediaRecordEventVideoEventChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateFlatTrimChangedListener(ARCommandARDrone3PilotingStateFlatTrimChangedListener _ARCommandARDrone3PilotingStateFlatTrimChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateFlatTrimChangedListener(_ARCommandARDrone3PilotingStateFlatTrimChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateFlyingStateChangedListener(ARCommandARDrone3PilotingStateFlyingStateChangedListener _ARCommandARDrone3PilotingStateFlyingStateChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateFlyingStateChangedListener(_ARCommandARDrone3PilotingStateFlyingStateChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateAlertStateChangedListener(ARCommandARDrone3PilotingStateAlertStateChangedListener _ARCommandARDrone3PilotingStateAlertStateChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateAlertStateChangedListener(_ARCommandARDrone3PilotingStateAlertStateChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateNavigateHomeStateChangedListener(ARCommandARDrone3PilotingStateNavigateHomeStateChangedListener _ARCommandARDrone3PilotingStateNavigateHomeStateChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateNavigateHomeStateChangedListener(_ARCommandARDrone3PilotingStateNavigateHomeStateChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStatePositionChangedListener(ARCommandARDrone3PilotingStatePositionChangedListener _ARCommandARDrone3PilotingStatePositionChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStatePositionChangedListener(_ARCommandARDrone3PilotingStatePositionChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateSpeedChangedListener(ARCommandARDrone3PilotingStateSpeedChangedListener _ARCommandARDrone3PilotingStateSpeedChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateSpeedChangedListener(_ARCommandARDrone3PilotingStateSpeedChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateAttitudeChangedListener(ARCommandARDrone3PilotingStateAttitudeChangedListener _ARCommandARDrone3PilotingStateAttitudeChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateAttitudeChangedListener(_ARCommandARDrone3PilotingStateAttitudeChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateAutoTakeOffModeChangedListener(ARCommandARDrone3PilotingStateAutoTakeOffModeChangedListener _ARCommandARDrone3PilotingStateAutoTakeOffModeChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateAutoTakeOffModeChangedListener(_ARCommandARDrone3PilotingStateAutoTakeOffModeChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateAltitudeChangedListener(ARCommandARDrone3PilotingStateAltitudeChangedListener _ARCommandARDrone3PilotingStateAltitudeChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateAltitudeChangedListener(_ARCommandARDrone3PilotingStateAltitudeChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateGpsLocationChangedListener(ARCommandARDrone3PilotingStateGpsLocationChangedListener _ARCommandARDrone3PilotingStateGpsLocationChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateGpsLocationChangedListener(_ARCommandARDrone3PilotingStateGpsLocationChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateLandingStateChangedListener(ARCommandARDrone3PilotingStateLandingStateChangedListener _ARCommandARDrone3PilotingStateLandingStateChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateLandingStateChangedListener(_ARCommandARDrone3PilotingStateLandingStateChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateAirSpeedChangedListener(ARCommandARDrone3PilotingStateAirSpeedChangedListener _ARCommandARDrone3PilotingStateAirSpeedChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateAirSpeedChangedListener(_ARCommandARDrone3PilotingStateAirSpeedChangedListener_PARAM);
    }

    public static void setARDrone3PilotingStateMoveToChangedListener(ARCommandARDrone3PilotingStateMoveToChangedListener _ARCommandARDrone3PilotingStateMoveToChangedListener_PARAM) {
        _decoder.setARDrone3PilotingStateMoveToChangedListener(_ARCommandARDrone3PilotingStateMoveToChangedListener_PARAM);
    }

    public static void setARDrone3PilotingEventMoveByEndListener(ARCommandARDrone3PilotingEventMoveByEndListener _ARCommandARDrone3PilotingEventMoveByEndListener_PARAM) {
        _decoder.setARDrone3PilotingEventMoveByEndListener(_ARCommandARDrone3PilotingEventMoveByEndListener_PARAM);
    }

    public static void setARDrone3NetworkStateWifiScanListChangedListener(ARCommandARDrone3NetworkStateWifiScanListChangedListener _ARCommandARDrone3NetworkStateWifiScanListChangedListener_PARAM) {
        _decoder.setARDrone3NetworkStateWifiScanListChangedListener(_ARCommandARDrone3NetworkStateWifiScanListChangedListener_PARAM);
    }

    public static void setARDrone3NetworkStateAllWifiScanChangedListener(ARCommandARDrone3NetworkStateAllWifiScanChangedListener _ARCommandARDrone3NetworkStateAllWifiScanChangedListener_PARAM) {
        _decoder.setARDrone3NetworkStateAllWifiScanChangedListener(_ARCommandARDrone3NetworkStateAllWifiScanChangedListener_PARAM);
    }

    public static void setARDrone3NetworkStateWifiAuthChannelListChangedListener(ARCommandARDrone3NetworkStateWifiAuthChannelListChangedListener _ARCommandARDrone3NetworkStateWifiAuthChannelListChangedListener_PARAM) {
        _decoder.setARDrone3NetworkStateWifiAuthChannelListChangedListener(_ARCommandARDrone3NetworkStateWifiAuthChannelListChangedListener_PARAM);
    }

    public static void setARDrone3NetworkStateAllWifiAuthChannelChangedListener(ARCommandARDrone3NetworkStateAllWifiAuthChannelChangedListener _ARCommandARDrone3NetworkStateAllWifiAuthChannelChangedListener_PARAM) {
        _decoder.setARDrone3NetworkStateAllWifiAuthChannelChangedListener(_ARCommandARDrone3NetworkStateAllWifiAuthChannelChangedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsStateMaxAltitudeChangedListener(ARCommandARDrone3PilotingSettingsStateMaxAltitudeChangedListener _ARCommandARDrone3PilotingSettingsStateMaxAltitudeChangedListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsStateMaxAltitudeChangedListener(_ARCommandARDrone3PilotingSettingsStateMaxAltitudeChangedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsStateMaxTiltChangedListener(ARCommandARDrone3PilotingSettingsStateMaxTiltChangedListener _ARCommandARDrone3PilotingSettingsStateMaxTiltChangedListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsStateMaxTiltChangedListener(_ARCommandARDrone3PilotingSettingsStateMaxTiltChangedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsStateAbsolutControlChangedListener(C1490x7118b1b _ARCommandARDrone3PilotingSettingsStateAbsolutControlChangedListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsStateAbsolutControlChangedListener(_ARCommandARDrone3PilotingSettingsStateAbsolutControlChangedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsStateMaxDistanceChangedListener(ARCommandARDrone3PilotingSettingsStateMaxDistanceChangedListener _ARCommandARDrone3PilotingSettingsStateMaxDistanceChangedListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsStateMaxDistanceChangedListener(_ARCommandARDrone3PilotingSettingsStateMaxDistanceChangedListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsStateNoFlyOverMaxDistanceChangedListener */
    public static void m588x6a18bdbf(C1499x946e4737 _ARCommandARDrone3PilotingSettingsStateNoFlyOverMaxDistanceChangedListener_PARAM) {
        _decoder.m71x6a18bdbf(_ARCommandARDrone3PilotingSettingsStateNoFlyOverMaxDistanceChangedListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalSpeedListener */
    public static void m584xde99b1c9(C1492x8d351151 _ARCommandARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalSpeedListener_PARAM) {
        _decoder.m67xde99b1c9(_ARCommandARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalSpeedListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalSpeedListener */
    public static void m587x13bf0037(C1495xab3ca1bf _ARCommandARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalSpeedListener_PARAM) {
        _decoder.m70x13bf0037(_ARCommandARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalSpeedListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalAccelerationListener */
    public static void m583xbd94dde6(C1491x39e0755e _ARCommandARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalAccelerationListener_PARAM) {
        _decoder.m66xbd94dde6(_ARCommandARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalAccelerationListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalAccelerationListener */
    public static void m586xa33d17b8(C1494xe1f82d30 _ARCommandARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalAccelerationListener_PARAM) {
        _decoder.m69xa33d17b8(_ARCommandARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalAccelerationListener_PARAM);
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxRotationSpeedListener */
    public static void m585x60a1a52f(C1493xf81f46b7 _ARCommandARDrone3PilotingSettingsStateAutonomousFlightMaxRotationSpeedListener_PARAM) {
        _decoder.m68x60a1a52f(_ARCommandARDrone3PilotingSettingsStateAutonomousFlightMaxRotationSpeedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsStateBankedTurnChangedListener(ARCommandARDrone3PilotingSettingsStateBankedTurnChangedListener _ARCommandARDrone3PilotingSettingsStateBankedTurnChangedListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsStateBankedTurnChangedListener(_ARCommandARDrone3PilotingSettingsStateBankedTurnChangedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsStateMinAltitudeChangedListener(ARCommandARDrone3PilotingSettingsStateMinAltitudeChangedListener _ARCommandARDrone3PilotingSettingsStateMinAltitudeChangedListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsStateMinAltitudeChangedListener(_ARCommandARDrone3PilotingSettingsStateMinAltitudeChangedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsStateCirclingDirectionChangedListener(C1497x42c9b114 _ARCommandARDrone3PilotingSettingsStateCirclingDirectionChangedListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsStateCirclingDirectionChangedListener(_ARCommandARDrone3PilotingSettingsStateCirclingDirectionChangedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsStateCirclingRadiusChangedListener(C1498x181f096b _ARCommandARDrone3PilotingSettingsStateCirclingRadiusChangedListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsStateCirclingRadiusChangedListener(_ARCommandARDrone3PilotingSettingsStateCirclingRadiusChangedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsStateCirclingAltitudeChangedListener(C1496x3bfc681b _ARCommandARDrone3PilotingSettingsStateCirclingAltitudeChangedListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsStateCirclingAltitudeChangedListener(_ARCommandARDrone3PilotingSettingsStateCirclingAltitudeChangedListener_PARAM);
    }

    public static void setARDrone3PilotingSettingsStatePitchModeChangedListener(ARCommandARDrone3PilotingSettingsStatePitchModeChangedListener _ARCommandARDrone3PilotingSettingsStatePitchModeChangedListener_PARAM) {
        _decoder.setARDrone3PilotingSettingsStatePitchModeChangedListener(_ARCommandARDrone3PilotingSettingsStatePitchModeChangedListener_PARAM);
    }

    public static void setARDrone3SpeedSettingsStateMaxVerticalSpeedChangedListener(C1504x6e88e34e _ARCommandARDrone3SpeedSettingsStateMaxVerticalSpeedChangedListener_PARAM) {
        _decoder.setARDrone3SpeedSettingsStateMaxVerticalSpeedChangedListener(_ARCommandARDrone3SpeedSettingsStateMaxVerticalSpeedChangedListener_PARAM);
    }

    public static void setARDrone3SpeedSettingsStateMaxRotationSpeedChangedListener(C1503x22493756 _ARCommandARDrone3SpeedSettingsStateMaxRotationSpeedChangedListener_PARAM) {
        _decoder.setARDrone3SpeedSettingsStateMaxRotationSpeedChangedListener(_ARCommandARDrone3SpeedSettingsStateMaxRotationSpeedChangedListener_PARAM);
    }

    public static void setARDrone3SpeedSettingsStateHullProtectionChangedListener(ARCommandARDrone3SpeedSettingsStateHullProtectionChangedListener _ARCommandARDrone3SpeedSettingsStateHullProtectionChangedListener_PARAM) {
        _decoder.setARDrone3SpeedSettingsStateHullProtectionChangedListener(_ARCommandARDrone3SpeedSettingsStateHullProtectionChangedListener_PARAM);
    }

    public static void setARDrone3SpeedSettingsStateOutdoorChangedListener(ARCommandARDrone3SpeedSettingsStateOutdoorChangedListener _ARCommandARDrone3SpeedSettingsStateOutdoorChangedListener_PARAM) {
        _decoder.setARDrone3SpeedSettingsStateOutdoorChangedListener(_ARCommandARDrone3SpeedSettingsStateOutdoorChangedListener_PARAM);
    }

    /* renamed from: setARDrone3SpeedSettingsStateMaxPitchRollRotationSpeedChangedListener */
    public static void m589x131ae34d(C1502xfe33eec5 _ARCommandARDrone3SpeedSettingsStateMaxPitchRollRotationSpeedChangedListener_PARAM) {
        _decoder.m72x131ae34d(_ARCommandARDrone3SpeedSettingsStateMaxPitchRollRotationSpeedChangedListener_PARAM);
    }

    public static void setARDrone3NetworkSettingsStateWifiSelectionChangedListener(C1476xab550ef7 _ARCommandARDrone3NetworkSettingsStateWifiSelectionChangedListener_PARAM) {
        _decoder.setARDrone3NetworkSettingsStateWifiSelectionChangedListener(_ARCommandARDrone3NetworkSettingsStateWifiSelectionChangedListener_PARAM);
    }

    public static void setARDrone3NetworkSettingsStateWifiSecurityChangedListener(ARCommandARDrone3NetworkSettingsStateWifiSecurityChangedListener _ARCommandARDrone3NetworkSettingsStateWifiSecurityChangedListener_PARAM) {
        _decoder.setARDrone3NetworkSettingsStateWifiSecurityChangedListener(_ARCommandARDrone3NetworkSettingsStateWifiSecurityChangedListener_PARAM);
    }

    public static void setARDrone3NetworkSettingsStateWifiSecurityListener(ARCommandARDrone3NetworkSettingsStateWifiSecurityListener _ARCommandARDrone3NetworkSettingsStateWifiSecurityListener_PARAM) {
        _decoder.setARDrone3NetworkSettingsStateWifiSecurityListener(_ARCommandARDrone3NetworkSettingsStateWifiSecurityListener_PARAM);
    }

    public static void setARDrone3SettingsStateProductMotorVersionListChangedListener(C1501xad2d1ee0 _ARCommandARDrone3SettingsStateProductMotorVersionListChangedListener_PARAM) {
        _decoder.setARDrone3SettingsStateProductMotorVersionListChangedListener(_ARCommandARDrone3SettingsStateProductMotorVersionListChangedListener_PARAM);
    }

    public static void setARDrone3SettingsStateProductGPSVersionChangedListener(ARCommandARDrone3SettingsStateProductGPSVersionChangedListener _ARCommandARDrone3SettingsStateProductGPSVersionChangedListener_PARAM) {
        _decoder.setARDrone3SettingsStateProductGPSVersionChangedListener(_ARCommandARDrone3SettingsStateProductGPSVersionChangedListener_PARAM);
    }

    public static void setARDrone3SettingsStateMotorErrorStateChangedListener(ARCommandARDrone3SettingsStateMotorErrorStateChangedListener _ARCommandARDrone3SettingsStateMotorErrorStateChangedListener_PARAM) {
        _decoder.setARDrone3SettingsStateMotorErrorStateChangedListener(_ARCommandARDrone3SettingsStateMotorErrorStateChangedListener_PARAM);
    }

    public static void setARDrone3SettingsStateMotorSoftwareVersionChangedListener(C1500x12e11684 _ARCommandARDrone3SettingsStateMotorSoftwareVersionChangedListener_PARAM) {
        _decoder.setARDrone3SettingsStateMotorSoftwareVersionChangedListener(_ARCommandARDrone3SettingsStateMotorSoftwareVersionChangedListener_PARAM);
    }

    public static void setARDrone3SettingsStateMotorFlightsStatusChangedListener(ARCommandARDrone3SettingsStateMotorFlightsStatusChangedListener _ARCommandARDrone3SettingsStateMotorFlightsStatusChangedListener_PARAM) {
        _decoder.setARDrone3SettingsStateMotorFlightsStatusChangedListener(_ARCommandARDrone3SettingsStateMotorFlightsStatusChangedListener_PARAM);
    }

    public static void setARDrone3SettingsStateMotorErrorLastErrorChangedListener(ARCommandARDrone3SettingsStateMotorErrorLastErrorChangedListener _ARCommandARDrone3SettingsStateMotorErrorLastErrorChangedListener_PARAM) {
        _decoder.setARDrone3SettingsStateMotorErrorLastErrorChangedListener(_ARCommandARDrone3SettingsStateMotorErrorLastErrorChangedListener_PARAM);
    }

    public static void setARDrone3SettingsStateP7IDListener(ARCommandARDrone3SettingsStateP7IDListener _ARCommandARDrone3SettingsStateP7IDListener_PARAM) {
        _decoder.setARDrone3SettingsStateP7IDListener(_ARCommandARDrone3SettingsStateP7IDListener_PARAM);
    }

    public static void setARDrone3SettingsStateCPUIDListener(ARCommandARDrone3SettingsStateCPUIDListener _ARCommandARDrone3SettingsStateCPUIDListener_PARAM) {
        _decoder.setARDrone3SettingsStateCPUIDListener(_ARCommandARDrone3SettingsStateCPUIDListener_PARAM);
    }

    public static void setARDrone3PictureSettingsStatePictureFormatChangedListener(C1479x873165c9 _ARCommandARDrone3PictureSettingsStatePictureFormatChangedListener_PARAM) {
        _decoder.setARDrone3PictureSettingsStatePictureFormatChangedListener(_ARCommandARDrone3PictureSettingsStatePictureFormatChangedListener_PARAM);
    }

    public static void setARDrone3PictureSettingsStateAutoWhiteBalanceChangedListener(C1478x18639810 _ARCommandARDrone3PictureSettingsStateAutoWhiteBalanceChangedListener_PARAM) {
        _decoder.setARDrone3PictureSettingsStateAutoWhiteBalanceChangedListener(_ARCommandARDrone3PictureSettingsStateAutoWhiteBalanceChangedListener_PARAM);
    }

    public static void setARDrone3PictureSettingsStateExpositionChangedListener(ARCommandARDrone3PictureSettingsStateExpositionChangedListener _ARCommandARDrone3PictureSettingsStateExpositionChangedListener_PARAM) {
        _decoder.setARDrone3PictureSettingsStateExpositionChangedListener(_ARCommandARDrone3PictureSettingsStateExpositionChangedListener_PARAM);
    }

    public static void setARDrone3PictureSettingsStateSaturationChangedListener(ARCommandARDrone3PictureSettingsStateSaturationChangedListener _ARCommandARDrone3PictureSettingsStateSaturationChangedListener_PARAM) {
        _decoder.setARDrone3PictureSettingsStateSaturationChangedListener(_ARCommandARDrone3PictureSettingsStateSaturationChangedListener_PARAM);
    }

    public static void setARDrone3PictureSettingsStateTimelapseChangedListener(ARCommandARDrone3PictureSettingsStateTimelapseChangedListener _ARCommandARDrone3PictureSettingsStateTimelapseChangedListener_PARAM) {
        _decoder.setARDrone3PictureSettingsStateTimelapseChangedListener(_ARCommandARDrone3PictureSettingsStateTimelapseChangedListener_PARAM);
    }

    public static void setARDrone3PictureSettingsStateVideoAutorecordChangedListener(C1480x355a5883 _ARCommandARDrone3PictureSettingsStateVideoAutorecordChangedListener_PARAM) {
        _decoder.setARDrone3PictureSettingsStateVideoAutorecordChangedListener(_ARCommandARDrone3PictureSettingsStateVideoAutorecordChangedListener_PARAM);
    }

    /* renamed from: setARDrone3PictureSettingsStateVideoStabilizationModeChangedListener */
    public static void m577xcbe2f543(C1484xec3e9acb _ARCommandARDrone3PictureSettingsStateVideoStabilizationModeChangedListener_PARAM) {
        _decoder.m60xcbe2f543(_ARCommandARDrone3PictureSettingsStateVideoStabilizationModeChangedListener_PARAM);
    }

    public static void setARDrone3PictureSettingsStateVideoRecordingModeChangedListener(C1482xa66af7f9 _ARCommandARDrone3PictureSettingsStateVideoRecordingModeChangedListener_PARAM) {
        _decoder.setARDrone3PictureSettingsStateVideoRecordingModeChangedListener(_ARCommandARDrone3PictureSettingsStateVideoRecordingModeChangedListener_PARAM);
    }

    public static void setARDrone3PictureSettingsStateVideoFramerateChangedListener(C1481x949850c0 _ARCommandARDrone3PictureSettingsStateVideoFramerateChangedListener_PARAM) {
        _decoder.setARDrone3PictureSettingsStateVideoFramerateChangedListener(_ARCommandARDrone3PictureSettingsStateVideoFramerateChangedListener_PARAM);
    }

    public static void setARDrone3PictureSettingsStateVideoResolutionsChangedListener(C1483x24732ac6 _ARCommandARDrone3PictureSettingsStateVideoResolutionsChangedListener_PARAM) {
        _decoder.setARDrone3PictureSettingsStateVideoResolutionsChangedListener(_ARCommandARDrone3PictureSettingsStateVideoResolutionsChangedListener_PARAM);
    }

    public static void setARDrone3MediaStreamingStateVideoEnableChangedListener(ARCommandARDrone3MediaStreamingStateVideoEnableChangedListener _ARCommandARDrone3MediaStreamingStateVideoEnableChangedListener_PARAM) {
        _decoder.setARDrone3MediaStreamingStateVideoEnableChangedListener(_ARCommandARDrone3MediaStreamingStateVideoEnableChangedListener_PARAM);
    }

    public static void setARDrone3MediaStreamingStateVideoStreamModeChangedListener(C1475x98fc87d7 _ARCommandARDrone3MediaStreamingStateVideoStreamModeChangedListener_PARAM) {
        _decoder.setARDrone3MediaStreamingStateVideoStreamModeChangedListener(_ARCommandARDrone3MediaStreamingStateVideoStreamModeChangedListener_PARAM);
    }

    public static void setARDrone3GPSSettingsStateHomeChangedListener(ARCommandARDrone3GPSSettingsStateHomeChangedListener _ARCommandARDrone3GPSSettingsStateHomeChangedListener_PARAM) {
        _decoder.setARDrone3GPSSettingsStateHomeChangedListener(_ARCommandARDrone3GPSSettingsStateHomeChangedListener_PARAM);
    }

    public static void setARDrone3GPSSettingsStateResetHomeChangedListener(ARCommandARDrone3GPSSettingsStateResetHomeChangedListener _ARCommandARDrone3GPSSettingsStateResetHomeChangedListener_PARAM) {
        _decoder.setARDrone3GPSSettingsStateResetHomeChangedListener(_ARCommandARDrone3GPSSettingsStateResetHomeChangedListener_PARAM);
    }

    public static void setARDrone3GPSSettingsStateGPSFixStateChangedListener(ARCommandARDrone3GPSSettingsStateGPSFixStateChangedListener _ARCommandARDrone3GPSSettingsStateGPSFixStateChangedListener_PARAM) {
        _decoder.setARDrone3GPSSettingsStateGPSFixStateChangedListener(_ARCommandARDrone3GPSSettingsStateGPSFixStateChangedListener_PARAM);
    }

    public static void setARDrone3GPSSettingsStateGPSUpdateStateChangedListener(ARCommandARDrone3GPSSettingsStateGPSUpdateStateChangedListener _ARCommandARDrone3GPSSettingsStateGPSUpdateStateChangedListener_PARAM) {
        _decoder.setARDrone3GPSSettingsStateGPSUpdateStateChangedListener(_ARCommandARDrone3GPSSettingsStateGPSUpdateStateChangedListener_PARAM);
    }

    public static void setARDrone3GPSSettingsStateHomeTypeChangedListener(ARCommandARDrone3GPSSettingsStateHomeTypeChangedListener _ARCommandARDrone3GPSSettingsStateHomeTypeChangedListener_PARAM) {
        _decoder.setARDrone3GPSSettingsStateHomeTypeChangedListener(_ARCommandARDrone3GPSSettingsStateHomeTypeChangedListener_PARAM);
    }

    public static void setARDrone3GPSSettingsStateReturnHomeDelayChangedListener(ARCommandARDrone3GPSSettingsStateReturnHomeDelayChangedListener _ARCommandARDrone3GPSSettingsStateReturnHomeDelayChangedListener_PARAM) {
        _decoder.setARDrone3GPSSettingsStateReturnHomeDelayChangedListener(_ARCommandARDrone3GPSSettingsStateReturnHomeDelayChangedListener_PARAM);
    }

    public static void setARDrone3GPSSettingsStateGeofenceCenterChangedListener(ARCommandARDrone3GPSSettingsStateGeofenceCenterChangedListener _ARCommandARDrone3GPSSettingsStateGeofenceCenterChangedListener_PARAM) {
        _decoder.setARDrone3GPSSettingsStateGeofenceCenterChangedListener(_ARCommandARDrone3GPSSettingsStateGeofenceCenterChangedListener_PARAM);
    }

    public static void setARDrone3CameraStateOrientationListener(ARCommandARDrone3CameraStateOrientationListener _ARCommandARDrone3CameraStateOrientationListener_PARAM) {
        _decoder.setARDrone3CameraStateOrientationListener(_ARCommandARDrone3CameraStateOrientationListener_PARAM);
    }

    public static void setARDrone3CameraStateDefaultCameraOrientationListener(ARCommandARDrone3CameraStateDefaultCameraOrientationListener _ARCommandARDrone3CameraStateDefaultCameraOrientationListener_PARAM) {
        _decoder.setARDrone3CameraStateDefaultCameraOrientationListener(_ARCommandARDrone3CameraStateDefaultCameraOrientationListener_PARAM);
    }

    public static void setARDrone3CameraStateOrientationV2Listener(ARCommandARDrone3CameraStateOrientationV2Listener _ARCommandARDrone3CameraStateOrientationV2Listener_PARAM) {
        _decoder.setARDrone3CameraStateOrientationV2Listener(_ARCommandARDrone3CameraStateOrientationV2Listener_PARAM);
    }

    public static void setARDrone3CameraStateDefaultCameraOrientationV2Listener(ARCommandARDrone3CameraStateDefaultCameraOrientationV2Listener _ARCommandARDrone3CameraStateDefaultCameraOrientationV2Listener_PARAM) {
        _decoder.setARDrone3CameraStateDefaultCameraOrientationV2Listener(_ARCommandARDrone3CameraStateDefaultCameraOrientationV2Listener_PARAM);
    }

    public static void setARDrone3CameraStateVelocityRangeListener(ARCommandARDrone3CameraStateVelocityRangeListener _ARCommandARDrone3CameraStateVelocityRangeListener_PARAM) {
        _decoder.setARDrone3CameraStateVelocityRangeListener(_ARCommandARDrone3CameraStateVelocityRangeListener_PARAM);
    }

    public static void setARDrone3AntiflickeringStateElectricFrequencyChangedListener(C1474x9d9d43a8 _ARCommandARDrone3AntiflickeringStateElectricFrequencyChangedListener_PARAM) {
        _decoder.setARDrone3AntiflickeringStateElectricFrequencyChangedListener(_ARCommandARDrone3AntiflickeringStateElectricFrequencyChangedListener_PARAM);
    }

    public static void setARDrone3AntiflickeringStateModeChangedListener(ARCommandARDrone3AntiflickeringStateModeChangedListener _ARCommandARDrone3AntiflickeringStateModeChangedListener_PARAM) {
        _decoder.setARDrone3AntiflickeringStateModeChangedListener(_ARCommandARDrone3AntiflickeringStateModeChangedListener_PARAM);
    }

    public static void setARDrone3GPSStateNumberOfSatelliteChangedListener(ARCommandARDrone3GPSStateNumberOfSatelliteChangedListener _ARCommandARDrone3GPSStateNumberOfSatelliteChangedListener_PARAM) {
        _decoder.setARDrone3GPSStateNumberOfSatelliteChangedListener(_ARCommandARDrone3GPSStateNumberOfSatelliteChangedListener_PARAM);
    }

    public static void setARDrone3GPSStateHomeTypeAvailabilityChangedListener(ARCommandARDrone3GPSStateHomeTypeAvailabilityChangedListener _ARCommandARDrone3GPSStateHomeTypeAvailabilityChangedListener_PARAM) {
        _decoder.setARDrone3GPSStateHomeTypeAvailabilityChangedListener(_ARCommandARDrone3GPSStateHomeTypeAvailabilityChangedListener_PARAM);
    }

    public static void setARDrone3GPSStateHomeTypeChosenChangedListener(ARCommandARDrone3GPSStateHomeTypeChosenChangedListener _ARCommandARDrone3GPSStateHomeTypeChosenChangedListener_PARAM) {
        _decoder.setARDrone3GPSStateHomeTypeChosenChangedListener(_ARCommandARDrone3GPSStateHomeTypeChosenChangedListener_PARAM);
    }

    public static void setARDrone3PROStateFeaturesListener(ARCommandARDrone3PROStateFeaturesListener _ARCommandARDrone3PROStateFeaturesListener_PARAM) {
        _decoder.setARDrone3PROStateFeaturesListener(_ARCommandARDrone3PROStateFeaturesListener_PARAM);
    }

    public static void setARDrone3AccessoryStateConnectedAccessoriesListener(ARCommandARDrone3AccessoryStateConnectedAccessoriesListener _ARCommandARDrone3AccessoryStateConnectedAccessoriesListener_PARAM) {
        _decoder.setARDrone3AccessoryStateConnectedAccessoriesListener(_ARCommandARDrone3AccessoryStateConnectedAccessoriesListener_PARAM);
    }

    public static void setCommonNetworkDisconnectListener(ARCommandCommonNetworkDisconnectListener _ARCommandCommonNetworkDisconnectListener_PARAM) {
        _decoder.setCommonNetworkDisconnectListener(_ARCommandCommonNetworkDisconnectListener_PARAM);
    }

    public static void setCommonSettingsAllSettingsListener(ARCommandCommonSettingsAllSettingsListener _ARCommandCommonSettingsAllSettingsListener_PARAM) {
        _decoder.setCommonSettingsAllSettingsListener(_ARCommandCommonSettingsAllSettingsListener_PARAM);
    }

    public static void setCommonSettingsResetListener(ARCommandCommonSettingsResetListener _ARCommandCommonSettingsResetListener_PARAM) {
        _decoder.setCommonSettingsResetListener(_ARCommandCommonSettingsResetListener_PARAM);
    }

    public static void setCommonSettingsProductNameListener(ARCommandCommonSettingsProductNameListener _ARCommandCommonSettingsProductNameListener_PARAM) {
        _decoder.setCommonSettingsProductNameListener(_ARCommandCommonSettingsProductNameListener_PARAM);
    }

    public static void setCommonSettingsCountryListener(ARCommandCommonSettingsCountryListener _ARCommandCommonSettingsCountryListener_PARAM) {
        _decoder.setCommonSettingsCountryListener(_ARCommandCommonSettingsCountryListener_PARAM);
    }

    public static void setCommonSettingsAutoCountryListener(ARCommandCommonSettingsAutoCountryListener _ARCommandCommonSettingsAutoCountryListener_PARAM) {
        _decoder.setCommonSettingsAutoCountryListener(_ARCommandCommonSettingsAutoCountryListener_PARAM);
    }

    public static void setCommonCommonAllStatesListener(ARCommandCommonCommonAllStatesListener _ARCommandCommonCommonAllStatesListener_PARAM) {
        _decoder.setCommonCommonAllStatesListener(_ARCommandCommonCommonAllStatesListener_PARAM);
    }

    public static void setCommonCommonCurrentDateListener(ARCommandCommonCommonCurrentDateListener _ARCommandCommonCommonCurrentDateListener_PARAM) {
        _decoder.setCommonCommonCurrentDateListener(_ARCommandCommonCommonCurrentDateListener_PARAM);
    }

    public static void setCommonCommonCurrentTimeListener(ARCommandCommonCommonCurrentTimeListener _ARCommandCommonCommonCurrentTimeListener_PARAM) {
        _decoder.setCommonCommonCurrentTimeListener(_ARCommandCommonCommonCurrentTimeListener_PARAM);
    }

    public static void setCommonCommonRebootListener(ARCommandCommonCommonRebootListener _ARCommandCommonCommonRebootListener_PARAM) {
        _decoder.setCommonCommonRebootListener(_ARCommandCommonCommonRebootListener_PARAM);
    }

    public static void setCommonOverHeatSwitchOffListener(ARCommandCommonOverHeatSwitchOffListener _ARCommandCommonOverHeatSwitchOffListener_PARAM) {
        _decoder.setCommonOverHeatSwitchOffListener(_ARCommandCommonOverHeatSwitchOffListener_PARAM);
    }

    public static void setCommonOverHeatVentilateListener(ARCommandCommonOverHeatVentilateListener _ARCommandCommonOverHeatVentilateListener_PARAM) {
        _decoder.setCommonOverHeatVentilateListener(_ARCommandCommonOverHeatVentilateListener_PARAM);
    }

    public static void setCommonControllerIsPilotingListener(ARCommandCommonControllerIsPilotingListener _ARCommandCommonControllerIsPilotingListener_PARAM) {
        _decoder.setCommonControllerIsPilotingListener(_ARCommandCommonControllerIsPilotingListener_PARAM);
    }

    public static void setCommonWifiSettingsOutdoorSettingListener(ARCommandCommonWifiSettingsOutdoorSettingListener _ARCommandCommonWifiSettingsOutdoorSettingListener_PARAM) {
        _decoder.setCommonWifiSettingsOutdoorSettingListener(_ARCommandCommonWifiSettingsOutdoorSettingListener_PARAM);
    }

    public static void setCommonMavlinkStartListener(ARCommandCommonMavlinkStartListener _ARCommandCommonMavlinkStartListener_PARAM) {
        _decoder.setCommonMavlinkStartListener(_ARCommandCommonMavlinkStartListener_PARAM);
    }

    public static void setCommonMavlinkPauseListener(ARCommandCommonMavlinkPauseListener _ARCommandCommonMavlinkPauseListener_PARAM) {
        _decoder.setCommonMavlinkPauseListener(_ARCommandCommonMavlinkPauseListener_PARAM);
    }

    public static void setCommonMavlinkStopListener(ARCommandCommonMavlinkStopListener _ARCommandCommonMavlinkStopListener_PARAM) {
        _decoder.setCommonMavlinkStopListener(_ARCommandCommonMavlinkStopListener_PARAM);
    }

    public static void setCommonFlightPlanSettingsReturnHomeOnDisconnectListener(ARCommandCommonFlightPlanSettingsReturnHomeOnDisconnectListener _ARCommandCommonFlightPlanSettingsReturnHomeOnDisconnectListener_PARAM) {
        _decoder.setCommonFlightPlanSettingsReturnHomeOnDisconnectListener(_ARCommandCommonFlightPlanSettingsReturnHomeOnDisconnectListener_PARAM);
    }

    public static void setCommonCalibrationMagnetoCalibrationListener(ARCommandCommonCalibrationMagnetoCalibrationListener _ARCommandCommonCalibrationMagnetoCalibrationListener_PARAM) {
        _decoder.setCommonCalibrationMagnetoCalibrationListener(_ARCommandCommonCalibrationMagnetoCalibrationListener_PARAM);
    }

    public static void setCommonCalibrationPitotCalibrationListener(ARCommandCommonCalibrationPitotCalibrationListener _ARCommandCommonCalibrationPitotCalibrationListener_PARAM) {
        _decoder.setCommonCalibrationPitotCalibrationListener(_ARCommandCommonCalibrationPitotCalibrationListener_PARAM);
    }

    public static void setCommonGPSControllerPositionForRunListener(ARCommandCommonGPSControllerPositionForRunListener _ARCommandCommonGPSControllerPositionForRunListener_PARAM) {
        _decoder.setCommonGPSControllerPositionForRunListener(_ARCommandCommonGPSControllerPositionForRunListener_PARAM);
    }

    public static void setCommonAudioControllerReadyForStreamingListener(ARCommandCommonAudioControllerReadyForStreamingListener _ARCommandCommonAudioControllerReadyForStreamingListener_PARAM) {
        _decoder.setCommonAudioControllerReadyForStreamingListener(_ARCommandCommonAudioControllerReadyForStreamingListener_PARAM);
    }

    public static void setCommonHeadlightsIntensityListener(ARCommandCommonHeadlightsIntensityListener _ARCommandCommonHeadlightsIntensityListener_PARAM) {
        _decoder.setCommonHeadlightsIntensityListener(_ARCommandCommonHeadlightsIntensityListener_PARAM);
    }

    public static void setCommonAnimationsStartAnimationListener(ARCommandCommonAnimationsStartAnimationListener _ARCommandCommonAnimationsStartAnimationListener_PARAM) {
        _decoder.setCommonAnimationsStartAnimationListener(_ARCommandCommonAnimationsStartAnimationListener_PARAM);
    }

    public static void setCommonAnimationsStopAnimationListener(ARCommandCommonAnimationsStopAnimationListener _ARCommandCommonAnimationsStopAnimationListener_PARAM) {
        _decoder.setCommonAnimationsStopAnimationListener(_ARCommandCommonAnimationsStopAnimationListener_PARAM);
    }

    public static void setCommonAnimationsStopAllAnimationsListener(ARCommandCommonAnimationsStopAllAnimationsListener _ARCommandCommonAnimationsStopAllAnimationsListener_PARAM) {
        _decoder.setCommonAnimationsStopAllAnimationsListener(_ARCommandCommonAnimationsStopAllAnimationsListener_PARAM);
    }

    public static void setCommonAccessoryConfigListener(ARCommandCommonAccessoryConfigListener _ARCommandCommonAccessoryConfigListener_PARAM) {
        _decoder.setCommonAccessoryConfigListener(_ARCommandCommonAccessoryConfigListener_PARAM);
    }

    public static void setCommonChargerSetMaxChargeRateListener(ARCommandCommonChargerSetMaxChargeRateListener _ARCommandCommonChargerSetMaxChargeRateListener_PARAM) {
        _decoder.setCommonChargerSetMaxChargeRateListener(_ARCommandCommonChargerSetMaxChargeRateListener_PARAM);
    }

    public static void setCommonFactoryResetListener(ARCommandCommonFactoryResetListener _ARCommandCommonFactoryResetListener_PARAM) {
        _decoder.setCommonFactoryResetListener(_ARCommandCommonFactoryResetListener_PARAM);
    }

    public static void setCommonNetworkEventDisconnectionListener(ARCommandCommonNetworkEventDisconnectionListener _ARCommandCommonNetworkEventDisconnectionListener_PARAM) {
        _decoder.setCommonNetworkEventDisconnectionListener(_ARCommandCommonNetworkEventDisconnectionListener_PARAM);
    }

    public static void setCommonSettingsStateAllSettingsChangedListener(ARCommandCommonSettingsStateAllSettingsChangedListener _ARCommandCommonSettingsStateAllSettingsChangedListener_PARAM) {
        _decoder.setCommonSettingsStateAllSettingsChangedListener(_ARCommandCommonSettingsStateAllSettingsChangedListener_PARAM);
    }

    public static void setCommonSettingsStateResetChangedListener(ARCommandCommonSettingsStateResetChangedListener _ARCommandCommonSettingsStateResetChangedListener_PARAM) {
        _decoder.setCommonSettingsStateResetChangedListener(_ARCommandCommonSettingsStateResetChangedListener_PARAM);
    }

    public static void setCommonSettingsStateProductNameChangedListener(ARCommandCommonSettingsStateProductNameChangedListener _ARCommandCommonSettingsStateProductNameChangedListener_PARAM) {
        _decoder.setCommonSettingsStateProductNameChangedListener(_ARCommandCommonSettingsStateProductNameChangedListener_PARAM);
    }

    public static void setCommonSettingsStateProductVersionChangedListener(ARCommandCommonSettingsStateProductVersionChangedListener _ARCommandCommonSettingsStateProductVersionChangedListener_PARAM) {
        _decoder.setCommonSettingsStateProductVersionChangedListener(_ARCommandCommonSettingsStateProductVersionChangedListener_PARAM);
    }

    public static void setCommonSettingsStateProductSerialHighChangedListener(ARCommandCommonSettingsStateProductSerialHighChangedListener _ARCommandCommonSettingsStateProductSerialHighChangedListener_PARAM) {
        _decoder.setCommonSettingsStateProductSerialHighChangedListener(_ARCommandCommonSettingsStateProductSerialHighChangedListener_PARAM);
    }

    public static void setCommonSettingsStateProductSerialLowChangedListener(ARCommandCommonSettingsStateProductSerialLowChangedListener _ARCommandCommonSettingsStateProductSerialLowChangedListener_PARAM) {
        _decoder.setCommonSettingsStateProductSerialLowChangedListener(_ARCommandCommonSettingsStateProductSerialLowChangedListener_PARAM);
    }

    public static void setCommonSettingsStateCountryChangedListener(ARCommandCommonSettingsStateCountryChangedListener _ARCommandCommonSettingsStateCountryChangedListener_PARAM) {
        _decoder.setCommonSettingsStateCountryChangedListener(_ARCommandCommonSettingsStateCountryChangedListener_PARAM);
    }

    public static void setCommonSettingsStateAutoCountryChangedListener(ARCommandCommonSettingsStateAutoCountryChangedListener _ARCommandCommonSettingsStateAutoCountryChangedListener_PARAM) {
        _decoder.setCommonSettingsStateAutoCountryChangedListener(_ARCommandCommonSettingsStateAutoCountryChangedListener_PARAM);
    }

    public static void setCommonCommonStateAllStatesChangedListener(ARCommandCommonCommonStateAllStatesChangedListener _ARCommandCommonCommonStateAllStatesChangedListener_PARAM) {
        _decoder.setCommonCommonStateAllStatesChangedListener(_ARCommandCommonCommonStateAllStatesChangedListener_PARAM);
    }

    public static void setCommonCommonStateBatteryStateChangedListener(ARCommandCommonCommonStateBatteryStateChangedListener _ARCommandCommonCommonStateBatteryStateChangedListener_PARAM) {
        _decoder.setCommonCommonStateBatteryStateChangedListener(_ARCommandCommonCommonStateBatteryStateChangedListener_PARAM);
    }

    public static void setCommonCommonStateMassStorageStateListChangedListener(ARCommandCommonCommonStateMassStorageStateListChangedListener _ARCommandCommonCommonStateMassStorageStateListChangedListener_PARAM) {
        _decoder.setCommonCommonStateMassStorageStateListChangedListener(_ARCommandCommonCommonStateMassStorageStateListChangedListener_PARAM);
    }

    public static void setCommonCommonStateMassStorageInfoStateListChangedListener(C1518x23468f0d _ARCommandCommonCommonStateMassStorageInfoStateListChangedListener_PARAM) {
        _decoder.setCommonCommonStateMassStorageInfoStateListChangedListener(_ARCommandCommonCommonStateMassStorageInfoStateListChangedListener_PARAM);
    }

    public static void setCommonCommonStateCurrentDateChangedListener(ARCommandCommonCommonStateCurrentDateChangedListener _ARCommandCommonCommonStateCurrentDateChangedListener_PARAM) {
        _decoder.setCommonCommonStateCurrentDateChangedListener(_ARCommandCommonCommonStateCurrentDateChangedListener_PARAM);
    }

    public static void setCommonCommonStateCurrentTimeChangedListener(ARCommandCommonCommonStateCurrentTimeChangedListener _ARCommandCommonCommonStateCurrentTimeChangedListener_PARAM) {
        _decoder.setCommonCommonStateCurrentTimeChangedListener(_ARCommandCommonCommonStateCurrentTimeChangedListener_PARAM);
    }

    public static void setCommonCommonStateMassStorageInfoRemainingListChangedListener(C1517x4e74f88 _ARCommandCommonCommonStateMassStorageInfoRemainingListChangedListener_PARAM) {
        _decoder.setCommonCommonStateMassStorageInfoRemainingListChangedListener(_ARCommandCommonCommonStateMassStorageInfoRemainingListChangedListener_PARAM);
    }

    public static void setCommonCommonStateWifiSignalChangedListener(ARCommandCommonCommonStateWifiSignalChangedListener _ARCommandCommonCommonStateWifiSignalChangedListener_PARAM) {
        _decoder.setCommonCommonStateWifiSignalChangedListener(_ARCommandCommonCommonStateWifiSignalChangedListener_PARAM);
    }

    public static void setCommonCommonStateSensorsStatesListChangedListener(ARCommandCommonCommonStateSensorsStatesListChangedListener _ARCommandCommonCommonStateSensorsStatesListChangedListener_PARAM) {
        _decoder.setCommonCommonStateSensorsStatesListChangedListener(_ARCommandCommonCommonStateSensorsStatesListChangedListener_PARAM);
    }

    public static void setCommonCommonStateProductModelListener(ARCommandCommonCommonStateProductModelListener _ARCommandCommonCommonStateProductModelListener_PARAM) {
        _decoder.setCommonCommonStateProductModelListener(_ARCommandCommonCommonStateProductModelListener_PARAM);
    }

    public static void setCommonCommonStateCountryListKnownListener(ARCommandCommonCommonStateCountryListKnownListener _ARCommandCommonCommonStateCountryListKnownListener_PARAM) {
        _decoder.setCommonCommonStateCountryListKnownListener(_ARCommandCommonCommonStateCountryListKnownListener_PARAM);
    }

    public static void setCommonCommonStateDeprecatedMassStorageContentChangedListener(C1515xf597a1a2 _ARCommandCommonCommonStateDeprecatedMassStorageContentChangedListener_PARAM) {
        _decoder.setCommonCommonStateDeprecatedMassStorageContentChangedListener(_ARCommandCommonCommonStateDeprecatedMassStorageContentChangedListener_PARAM);
    }

    public static void setCommonCommonStateMassStorageContentListener(ARCommandCommonCommonStateMassStorageContentListener _ARCommandCommonCommonStateMassStorageContentListener_PARAM) {
        _decoder.setCommonCommonStateMassStorageContentListener(_ARCommandCommonCommonStateMassStorageContentListener_PARAM);
    }

    public static void setCommonCommonStateMassStorageContentForCurrentRunListener(C1516x4ff7285c _ARCommandCommonCommonStateMassStorageContentForCurrentRunListener_PARAM) {
        _decoder.setCommonCommonStateMassStorageContentForCurrentRunListener(_ARCommandCommonCommonStateMassStorageContentForCurrentRunListener_PARAM);
    }

    public static void setCommonCommonStateVideoRecordingTimestampListener(ARCommandCommonCommonStateVideoRecordingTimestampListener _ARCommandCommonCommonStateVideoRecordingTimestampListener_PARAM) {
        _decoder.setCommonCommonStateVideoRecordingTimestampListener(_ARCommandCommonCommonStateVideoRecordingTimestampListener_PARAM);
    }

    public static void setCommonOverHeatStateOverHeatChangedListener(ARCommandCommonOverHeatStateOverHeatChangedListener _ARCommandCommonOverHeatStateOverHeatChangedListener_PARAM) {
        _decoder.setCommonOverHeatStateOverHeatChangedListener(_ARCommandCommonOverHeatStateOverHeatChangedListener_PARAM);
    }

    public static void setCommonOverHeatStateOverHeatRegulationChangedListener(ARCommandCommonOverHeatStateOverHeatRegulationChangedListener _ARCommandCommonOverHeatStateOverHeatRegulationChangedListener_PARAM) {
        _decoder.setCommonOverHeatStateOverHeatRegulationChangedListener(_ARCommandCommonOverHeatStateOverHeatRegulationChangedListener_PARAM);
    }

    public static void setCommonWifiSettingsStateOutdoorSettingsChangedListener(ARCommandCommonWifiSettingsStateOutdoorSettingsChangedListener _ARCommandCommonWifiSettingsStateOutdoorSettingsChangedListener_PARAM) {
        _decoder.setCommonWifiSettingsStateOutdoorSettingsChangedListener(_ARCommandCommonWifiSettingsStateOutdoorSettingsChangedListener_PARAM);
    }

    public static void setCommonMavlinkStateMavlinkFilePlayingStateChangedListener(C1520xc5448c27 _ARCommandCommonMavlinkStateMavlinkFilePlayingStateChangedListener_PARAM) {
        _decoder.setCommonMavlinkStateMavlinkFilePlayingStateChangedListener(_ARCommandCommonMavlinkStateMavlinkFilePlayingStateChangedListener_PARAM);
    }

    public static void setCommonMavlinkStateMavlinkPlayErrorStateChangedListener(ARCommandCommonMavlinkStateMavlinkPlayErrorStateChangedListener _ARCommandCommonMavlinkStateMavlinkPlayErrorStateChangedListener_PARAM) {
        _decoder.setCommonMavlinkStateMavlinkPlayErrorStateChangedListener(_ARCommandCommonMavlinkStateMavlinkPlayErrorStateChangedListener_PARAM);
    }

    public static void setCommonMavlinkStateMissionItemExecutedListener(ARCommandCommonMavlinkStateMissionItemExecutedListener _ARCommandCommonMavlinkStateMissionItemExecutedListener_PARAM) {
        _decoder.setCommonMavlinkStateMissionItemExecutedListener(_ARCommandCommonMavlinkStateMissionItemExecutedListener_PARAM);
    }

    /* renamed from: setCommonFlightPlanSettingsStateReturnHomeOnDisconnectChangedListener */
    public static void m595x56b72c16(C1519x41d0378e _ARCommandCommonFlightPlanSettingsStateReturnHomeOnDisconnectChangedListener_PARAM) {
        _decoder.m78x56b72c16(_ARCommandCommonFlightPlanSettingsStateReturnHomeOnDisconnectChangedListener_PARAM);
    }

    public static void setCommonCalibrationStateMagnetoCalibrationStateChangedListener(C1513x8aea728 _ARCommandCommonCalibrationStateMagnetoCalibrationStateChangedListener_PARAM) {
        _decoder.setCommonCalibrationStateMagnetoCalibrationStateChangedListener(_ARCommandCommonCalibrationStateMagnetoCalibrationStateChangedListener_PARAM);
    }

    public static void setCommonCalibrationStateMagnetoCalibrationRequiredStateListener(C1511x234108b5 _ARCommandCommonCalibrationStateMagnetoCalibrationRequiredStateListener_PARAM) {
        _decoder.setCommonCalibrationStateMagnetoCalibrationRequiredStateListener(_ARCommandCommonCalibrationStateMagnetoCalibrationRequiredStateListener_PARAM);
    }

    /* renamed from: setCommonCalibrationStateMagnetoCalibrationAxisToCalibrateChangedListener */
    public static void m593x614d3b96(C1510xb983cb0e _ARCommandCommonCalibrationStateMagnetoCalibrationAxisToCalibrateChangedListener_PARAM) {
        _decoder.m76x614d3b96(_ARCommandCommonCalibrationStateMagnetoCalibrationAxisToCalibrateChangedListener_PARAM);
    }

    /* renamed from: setCommonCalibrationStateMagnetoCalibrationStartedChangedListener */
    public static void m594xd554ed80(C1512x3fee74f8 _ARCommandCommonCalibrationStateMagnetoCalibrationStartedChangedListener_PARAM) {
        _decoder.m77xd554ed80(_ARCommandCommonCalibrationStateMagnetoCalibrationStartedChangedListener_PARAM);
    }

    public static void setCommonCalibrationStatePitotCalibrationStateChangedListener(C1514x29acc4d _ARCommandCommonCalibrationStatePitotCalibrationStateChangedListener_PARAM) {
        _decoder.setCommonCalibrationStatePitotCalibrationStateChangedListener(_ARCommandCommonCalibrationStatePitotCalibrationStateChangedListener_PARAM);
    }

    public static void setCommonCameraSettingsStateCameraSettingsChangedListener(ARCommandCommonCameraSettingsStateCameraSettingsChangedListener _ARCommandCommonCameraSettingsStateCameraSettingsChangedListener_PARAM) {
        _decoder.setCommonCameraSettingsStateCameraSettingsChangedListener(_ARCommandCommonCameraSettingsStateCameraSettingsChangedListener_PARAM);
    }

    public static void setCommonFlightPlanStateAvailabilityStateChangedListener(ARCommandCommonFlightPlanStateAvailabilityStateChangedListener _ARCommandCommonFlightPlanStateAvailabilityStateChangedListener_PARAM) {
        _decoder.setCommonFlightPlanStateAvailabilityStateChangedListener(_ARCommandCommonFlightPlanStateAvailabilityStateChangedListener_PARAM);
    }

    public static void setCommonFlightPlanStateComponentStateListChangedListener(ARCommandCommonFlightPlanStateComponentStateListChangedListener _ARCommandCommonFlightPlanStateComponentStateListChangedListener_PARAM) {
        _decoder.setCommonFlightPlanStateComponentStateListChangedListener(_ARCommandCommonFlightPlanStateComponentStateListChangedListener_PARAM);
    }

    public static void setCommonFlightPlanStateLockStateChangedListener(ARCommandCommonFlightPlanStateLockStateChangedListener _ARCommandCommonFlightPlanStateLockStateChangedListener_PARAM) {
        _decoder.setCommonFlightPlanStateLockStateChangedListener(_ARCommandCommonFlightPlanStateLockStateChangedListener_PARAM);
    }

    public static void setCommonFlightPlanEventStartingErrorEventListener(ARCommandCommonFlightPlanEventStartingErrorEventListener _ARCommandCommonFlightPlanEventStartingErrorEventListener_PARAM) {
        _decoder.setCommonFlightPlanEventStartingErrorEventListener(_ARCommandCommonFlightPlanEventStartingErrorEventListener_PARAM);
    }

    public static void setCommonFlightPlanEventSpeedBridleEventListener(ARCommandCommonFlightPlanEventSpeedBridleEventListener _ARCommandCommonFlightPlanEventSpeedBridleEventListener_PARAM) {
        _decoder.setCommonFlightPlanEventSpeedBridleEventListener(_ARCommandCommonFlightPlanEventSpeedBridleEventListener_PARAM);
    }

    /* renamed from: setCommonARLibsVersionsStateControllerLibARCommandsVersionListener */
    public static void m590x5674e694(C1505x3f0c4e1c _ARCommandCommonARLibsVersionsStateControllerLibARCommandsVersionListener_PARAM) {
        _decoder.m73x5674e694(_ARCommandCommonARLibsVersionsStateControllerLibARCommandsVersionListener_PARAM);
    }

    /* renamed from: setCommonARLibsVersionsStateSkyControllerLibARCommandsVersionListener */
    public static void m591x20f7a481(C1507xc10aff9 _ARCommandCommonARLibsVersionsStateSkyControllerLibARCommandsVersionListener_PARAM) {
        _decoder.m74x20f7a481(_ARCommandCommonARLibsVersionsStateSkyControllerLibARCommandsVersionListener_PARAM);
    }

    public static void setCommonARLibsVersionsStateDeviceLibARCommandsVersionListener(C1506xf72e36d6 _ARCommandCommonARLibsVersionsStateDeviceLibARCommandsVersionListener_PARAM) {
        _decoder.setCommonARLibsVersionsStateDeviceLibARCommandsVersionListener(_ARCommandCommonARLibsVersionsStateDeviceLibARCommandsVersionListener_PARAM);
    }

    public static void setCommonAudioStateAudioStreamingRunningListener(ARCommandCommonAudioStateAudioStreamingRunningListener _ARCommandCommonAudioStateAudioStreamingRunningListener_PARAM) {
        _decoder.setCommonAudioStateAudioStreamingRunningListener(_ARCommandCommonAudioStateAudioStreamingRunningListener_PARAM);
    }

    public static void setCommonHeadlightsStateIntensityChangedListener(ARCommandCommonHeadlightsStateIntensityChangedListener _ARCommandCommonHeadlightsStateIntensityChangedListener_PARAM) {
        _decoder.setCommonHeadlightsStateIntensityChangedListener(_ARCommandCommonHeadlightsStateIntensityChangedListener_PARAM);
    }

    public static void setCommonAnimationsStateListListener(ARCommandCommonAnimationsStateListListener _ARCommandCommonAnimationsStateListListener_PARAM) {
        _decoder.setCommonAnimationsStateListListener(_ARCommandCommonAnimationsStateListListener_PARAM);
    }

    public static void setCommonAccessoryStateSupportedAccessoriesListChangedListener(C1509x59840904 _ARCommandCommonAccessoryStateSupportedAccessoriesListChangedListener_PARAM) {
        _decoder.setCommonAccessoryStateSupportedAccessoriesListChangedListener(_ARCommandCommonAccessoryStateSupportedAccessoriesListChangedListener_PARAM);
    }

    public static void setCommonAccessoryStateAccessoryConfigChangedListener(ARCommandCommonAccessoryStateAccessoryConfigChangedListener _ARCommandCommonAccessoryStateAccessoryConfigChangedListener_PARAM) {
        _decoder.setCommonAccessoryStateAccessoryConfigChangedListener(_ARCommandCommonAccessoryStateAccessoryConfigChangedListener_PARAM);
    }

    /* renamed from: setCommonAccessoryStateAccessoryConfigModificationEnabledListener */
    public static void m592x4762e451(C1508xb1fc6bc9 _ARCommandCommonAccessoryStateAccessoryConfigModificationEnabledListener_PARAM) {
        _decoder.m75x4762e451(_ARCommandCommonAccessoryStateAccessoryConfigModificationEnabledListener_PARAM);
    }

    public static void setCommonChargerStateMaxChargeRateChangedListener(ARCommandCommonChargerStateMaxChargeRateChangedListener _ARCommandCommonChargerStateMaxChargeRateChangedListener_PARAM) {
        _decoder.setCommonChargerStateMaxChargeRateChangedListener(_ARCommandCommonChargerStateMaxChargeRateChangedListener_PARAM);
    }

    public static void setCommonChargerStateCurrentChargeStateChangedListener(ARCommandCommonChargerStateCurrentChargeStateChangedListener _ARCommandCommonChargerStateCurrentChargeStateChangedListener_PARAM) {
        _decoder.setCommonChargerStateCurrentChargeStateChangedListener(_ARCommandCommonChargerStateCurrentChargeStateChangedListener_PARAM);
    }

    public static void setCommonChargerStateLastChargeRateChangedListener(ARCommandCommonChargerStateLastChargeRateChangedListener _ARCommandCommonChargerStateLastChargeRateChangedListener_PARAM) {
        _decoder.setCommonChargerStateLastChargeRateChangedListener(_ARCommandCommonChargerStateLastChargeRateChangedListener_PARAM);
    }

    public static void setCommonChargerStateChargingInfoListener(ARCommandCommonChargerStateChargingInfoListener _ARCommandCommonChargerStateChargingInfoListener_PARAM) {
        _decoder.setCommonChargerStateChargingInfoListener(_ARCommandCommonChargerStateChargingInfoListener_PARAM);
    }

    public static void setCommonRunStateRunIdChangedListener(ARCommandCommonRunStateRunIdChangedListener _ARCommandCommonRunStateRunIdChangedListener_PARAM) {
        _decoder.setCommonRunStateRunIdChangedListener(_ARCommandCommonRunStateRunIdChangedListener_PARAM);
    }

    public static void setControllerInfoGpsListener(ARCommandControllerInfoGpsListener _ARCommandControllerInfoGpsListener_PARAM) {
        _decoder.setControllerInfoGpsListener(_ARCommandControllerInfoGpsListener_PARAM);
    }

    public static void setControllerInfoBarometerListener(ARCommandControllerInfoBarometerListener _ARCommandControllerInfoBarometerListener_PARAM) {
        _decoder.setControllerInfoBarometerListener(_ARCommandControllerInfoBarometerListener_PARAM);
    }

    public static void setDebugGetAllSettingsListener(ARCommandDebugGetAllSettingsListener _ARCommandDebugGetAllSettingsListener_PARAM) {
        _decoder.setDebugGetAllSettingsListener(_ARCommandDebugGetAllSettingsListener_PARAM);
    }

    public static void setDebugSetSettingListener(ARCommandDebugSetSettingListener _ARCommandDebugSetSettingListener_PARAM) {
        _decoder.setDebugSetSettingListener(_ARCommandDebugSetSettingListener_PARAM);
    }

    public static void setDebugSettingsInfoListener(ARCommandDebugSettingsInfoListener _ARCommandDebugSettingsInfoListener_PARAM) {
        _decoder.setDebugSettingsInfoListener(_ARCommandDebugSettingsInfoListener_PARAM);
    }

    public static void setDebugSettingsListListener(ARCommandDebugSettingsListListener _ARCommandDebugSettingsListListener_PARAM) {
        _decoder.setDebugSettingsListListener(_ARCommandDebugSettingsListListener_PARAM);
    }

    public static void setDroneManagerDiscoverDronesListener(ARCommandDroneManagerDiscoverDronesListener _ARCommandDroneManagerDiscoverDronesListener_PARAM) {
        _decoder.setDroneManagerDiscoverDronesListener(_ARCommandDroneManagerDiscoverDronesListener_PARAM);
    }

    public static void setDroneManagerConnectListener(ARCommandDroneManagerConnectListener _ARCommandDroneManagerConnectListener_PARAM) {
        _decoder.setDroneManagerConnectListener(_ARCommandDroneManagerConnectListener_PARAM);
    }

    public static void setDroneManagerForgetListener(ARCommandDroneManagerForgetListener _ARCommandDroneManagerForgetListener_PARAM) {
        _decoder.setDroneManagerForgetListener(_ARCommandDroneManagerForgetListener_PARAM);
    }

    public static void setDroneManagerDroneListItemListener(ARCommandDroneManagerDroneListItemListener _ARCommandDroneManagerDroneListItemListener_PARAM) {
        _decoder.setDroneManagerDroneListItemListener(_ARCommandDroneManagerDroneListItemListener_PARAM);
    }

    public static void setDroneManagerConnectionStateListener(ARCommandDroneManagerConnectionStateListener _ARCommandDroneManagerConnectionStateListener_PARAM) {
        _decoder.setDroneManagerConnectionStateListener(_ARCommandDroneManagerConnectionStateListener_PARAM);
    }

    public static void setDroneManagerAuthenticationFailedListener(ARCommandDroneManagerAuthenticationFailedListener _ARCommandDroneManagerAuthenticationFailedListener_PARAM) {
        _decoder.setDroneManagerAuthenticationFailedListener(_ARCommandDroneManagerAuthenticationFailedListener_PARAM);
    }

    public static void setDroneManagerConnectionRefusedListener(ARCommandDroneManagerConnectionRefusedListener _ARCommandDroneManagerConnectionRefusedListener_PARAM) {
        _decoder.setDroneManagerConnectionRefusedListener(_ARCommandDroneManagerConnectionRefusedListener_PARAM);
    }

    public static void setDroneManagerKnownDroneItemListener(ARCommandDroneManagerKnownDroneItemListener _ARCommandDroneManagerKnownDroneItemListener_PARAM) {
        _decoder.setDroneManagerKnownDroneItemListener(_ARCommandDroneManagerKnownDroneItemListener_PARAM);
    }

    public static void setFollowMeStartListener(ARCommandFollowMeStartListener _ARCommandFollowMeStartListener_PARAM) {
        _decoder.setFollowMeStartListener(_ARCommandFollowMeStartListener_PARAM);
    }

    public static void setFollowMeStopListener(ARCommandFollowMeStopListener _ARCommandFollowMeStopListener_PARAM) {
        _decoder.setFollowMeStopListener(_ARCommandFollowMeStopListener_PARAM);
    }

    public static void setFollowMeConfigureGeographicListener(ARCommandFollowMeConfigureGeographicListener _ARCommandFollowMeConfigureGeographicListener_PARAM) {
        _decoder.setFollowMeConfigureGeographicListener(_ARCommandFollowMeConfigureGeographicListener_PARAM);
    }

    public static void setFollowMeConfigureRelativeListener(ARCommandFollowMeConfigureRelativeListener _ARCommandFollowMeConfigureRelativeListener_PARAM) {
        _decoder.setFollowMeConfigureRelativeListener(_ARCommandFollowMeConfigureRelativeListener_PARAM);
    }

    public static void setFollowMeStopAnimationListener(ARCommandFollowMeStopAnimationListener _ARCommandFollowMeStopAnimationListener_PARAM) {
        _decoder.setFollowMeStopAnimationListener(_ARCommandFollowMeStopAnimationListener_PARAM);
    }

    public static void setFollowMeStartHelicoidAnimListener(ARCommandFollowMeStartHelicoidAnimListener _ARCommandFollowMeStartHelicoidAnimListener_PARAM) {
        _decoder.setFollowMeStartHelicoidAnimListener(_ARCommandFollowMeStartHelicoidAnimListener_PARAM);
    }

    public static void setFollowMeStartSwingAnimListener(ARCommandFollowMeStartSwingAnimListener _ARCommandFollowMeStartSwingAnimListener_PARAM) {
        _decoder.setFollowMeStartSwingAnimListener(_ARCommandFollowMeStartSwingAnimListener_PARAM);
    }

    public static void setFollowMeStartBoomerangAnimListener(ARCommandFollowMeStartBoomerangAnimListener _ARCommandFollowMeStartBoomerangAnimListener_PARAM) {
        _decoder.setFollowMeStartBoomerangAnimListener(_ARCommandFollowMeStartBoomerangAnimListener_PARAM);
    }

    public static void setFollowMeStartCandleAnimListener(ARCommandFollowMeStartCandleAnimListener _ARCommandFollowMeStartCandleAnimListener_PARAM) {
        _decoder.setFollowMeStartCandleAnimListener(_ARCommandFollowMeStartCandleAnimListener_PARAM);
    }

    public static void setFollowMeStartDollySlideAnimListener(ARCommandFollowMeStartDollySlideAnimListener _ARCommandFollowMeStartDollySlideAnimListener_PARAM) {
        _decoder.setFollowMeStartDollySlideAnimListener(_ARCommandFollowMeStartDollySlideAnimListener_PARAM);
    }

    public static void setFollowMeTargetFramingPositionListener(ARCommandFollowMeTargetFramingPositionListener _ARCommandFollowMeTargetFramingPositionListener_PARAM) {
        _decoder.setFollowMeTargetFramingPositionListener(_ARCommandFollowMeTargetFramingPositionListener_PARAM);
    }

    public static void setFollowMeTargetImageDetectionListener(ARCommandFollowMeTargetImageDetectionListener _ARCommandFollowMeTargetImageDetectionListener_PARAM) {
        _decoder.setFollowMeTargetImageDetectionListener(_ARCommandFollowMeTargetImageDetectionListener_PARAM);
    }

    public static void setFollowMeStateListener(ARCommandFollowMeStateListener _ARCommandFollowMeStateListener_PARAM) {
        _decoder.setFollowMeStateListener(_ARCommandFollowMeStateListener_PARAM);
    }

    public static void setFollowMeModeInfoListener(ARCommandFollowMeModeInfoListener _ARCommandFollowMeModeInfoListener_PARAM) {
        _decoder.setFollowMeModeInfoListener(_ARCommandFollowMeModeInfoListener_PARAM);
    }

    public static void setFollowMeGeographicConfigListener(ARCommandFollowMeGeographicConfigListener _ARCommandFollowMeGeographicConfigListener_PARAM) {
        _decoder.setFollowMeGeographicConfigListener(_ARCommandFollowMeGeographicConfigListener_PARAM);
    }

    public static void setFollowMeRelativeConfigListener(ARCommandFollowMeRelativeConfigListener _ARCommandFollowMeRelativeConfigListener_PARAM) {
        _decoder.setFollowMeRelativeConfigListener(_ARCommandFollowMeRelativeConfigListener_PARAM);
    }

    public static void setFollowMeTargetTrajectoryListener(ARCommandFollowMeTargetTrajectoryListener _ARCommandFollowMeTargetTrajectoryListener_PARAM) {
        _decoder.setFollowMeTargetTrajectoryListener(_ARCommandFollowMeTargetTrajectoryListener_PARAM);
    }

    public static void setFollowMeHelicoidAnimConfigListener(ARCommandFollowMeHelicoidAnimConfigListener _ARCommandFollowMeHelicoidAnimConfigListener_PARAM) {
        _decoder.setFollowMeHelicoidAnimConfigListener(_ARCommandFollowMeHelicoidAnimConfigListener_PARAM);
    }

    public static void setFollowMeSwingAnimConfigListener(ARCommandFollowMeSwingAnimConfigListener _ARCommandFollowMeSwingAnimConfigListener_PARAM) {
        _decoder.setFollowMeSwingAnimConfigListener(_ARCommandFollowMeSwingAnimConfigListener_PARAM);
    }

    public static void setFollowMeBoomerangAnimConfigListener(ARCommandFollowMeBoomerangAnimConfigListener _ARCommandFollowMeBoomerangAnimConfigListener_PARAM) {
        _decoder.setFollowMeBoomerangAnimConfigListener(_ARCommandFollowMeBoomerangAnimConfigListener_PARAM);
    }

    public static void setFollowMeCandleAnimConfigListener(ARCommandFollowMeCandleAnimConfigListener _ARCommandFollowMeCandleAnimConfigListener_PARAM) {
        _decoder.setFollowMeCandleAnimConfigListener(_ARCommandFollowMeCandleAnimConfigListener_PARAM);
    }

    public static void setFollowMeDollySlideAnimConfigListener(ARCommandFollowMeDollySlideAnimConfigListener _ARCommandFollowMeDollySlideAnimConfigListener_PARAM) {
        _decoder.setFollowMeDollySlideAnimConfigListener(_ARCommandFollowMeDollySlideAnimConfigListener_PARAM);
    }

    public static void setFollowMeTargetFramingPositionChangedListener(ARCommandFollowMeTargetFramingPositionChangedListener _ARCommandFollowMeTargetFramingPositionChangedListener_PARAM) {
        _decoder.setFollowMeTargetFramingPositionChangedListener(_ARCommandFollowMeTargetFramingPositionChangedListener_PARAM);
    }

    public static void setFollowMeTargetImageDetectionStateListener(ARCommandFollowMeTargetImageDetectionStateListener _ARCommandFollowMeTargetImageDetectionStateListener_PARAM) {
        _decoder.setFollowMeTargetImageDetectionStateListener(_ARCommandFollowMeTargetImageDetectionStateListener_PARAM);
    }

    public static void setJumpingSumoPilotingPCMDListener(ARCommandJumpingSumoPilotingPCMDListener _ARCommandJumpingSumoPilotingPCMDListener_PARAM) {
        _decoder.setJumpingSumoPilotingPCMDListener(_ARCommandJumpingSumoPilotingPCMDListener_PARAM);
    }

    public static void setJumpingSumoPilotingPostureListener(ARCommandJumpingSumoPilotingPostureListener _ARCommandJumpingSumoPilotingPostureListener_PARAM) {
        _decoder.setJumpingSumoPilotingPostureListener(_ARCommandJumpingSumoPilotingPostureListener_PARAM);
    }

    public static void setJumpingSumoPilotingAddCapOffsetListener(ARCommandJumpingSumoPilotingAddCapOffsetListener _ARCommandJumpingSumoPilotingAddCapOffsetListener_PARAM) {
        _decoder.setJumpingSumoPilotingAddCapOffsetListener(_ARCommandJumpingSumoPilotingAddCapOffsetListener_PARAM);
    }

    public static void setJumpingSumoAnimationsJumpStopListener(ARCommandJumpingSumoAnimationsJumpStopListener _ARCommandJumpingSumoAnimationsJumpStopListener_PARAM) {
        _decoder.setJumpingSumoAnimationsJumpStopListener(_ARCommandJumpingSumoAnimationsJumpStopListener_PARAM);
    }

    public static void setJumpingSumoAnimationsJumpCancelListener(ARCommandJumpingSumoAnimationsJumpCancelListener _ARCommandJumpingSumoAnimationsJumpCancelListener_PARAM) {
        _decoder.setJumpingSumoAnimationsJumpCancelListener(_ARCommandJumpingSumoAnimationsJumpCancelListener_PARAM);
    }

    public static void setJumpingSumoAnimationsJumpLoadListener(ARCommandJumpingSumoAnimationsJumpLoadListener _ARCommandJumpingSumoAnimationsJumpLoadListener_PARAM) {
        _decoder.setJumpingSumoAnimationsJumpLoadListener(_ARCommandJumpingSumoAnimationsJumpLoadListener_PARAM);
    }

    public static void setJumpingSumoAnimationsJumpListener(ARCommandJumpingSumoAnimationsJumpListener _ARCommandJumpingSumoAnimationsJumpListener_PARAM) {
        _decoder.setJumpingSumoAnimationsJumpListener(_ARCommandJumpingSumoAnimationsJumpListener_PARAM);
    }

    public static void setJumpingSumoAnimationsSimpleAnimationListener(ARCommandJumpingSumoAnimationsSimpleAnimationListener _ARCommandJumpingSumoAnimationsSimpleAnimationListener_PARAM) {
        _decoder.setJumpingSumoAnimationsSimpleAnimationListener(_ARCommandJumpingSumoAnimationsSimpleAnimationListener_PARAM);
    }

    public static void setJumpingSumoMediaRecordPictureListener(ARCommandJumpingSumoMediaRecordPictureListener _ARCommandJumpingSumoMediaRecordPictureListener_PARAM) {
        _decoder.setJumpingSumoMediaRecordPictureListener(_ARCommandJumpingSumoMediaRecordPictureListener_PARAM);
    }

    public static void setJumpingSumoMediaRecordVideoListener(ARCommandJumpingSumoMediaRecordVideoListener _ARCommandJumpingSumoMediaRecordVideoListener_PARAM) {
        _decoder.setJumpingSumoMediaRecordVideoListener(_ARCommandJumpingSumoMediaRecordVideoListener_PARAM);
    }

    public static void setJumpingSumoMediaRecordPictureV2Listener(ARCommandJumpingSumoMediaRecordPictureV2Listener _ARCommandJumpingSumoMediaRecordPictureV2Listener_PARAM) {
        _decoder.setJumpingSumoMediaRecordPictureV2Listener(_ARCommandJumpingSumoMediaRecordPictureV2Listener_PARAM);
    }

    public static void setJumpingSumoMediaRecordVideoV2Listener(ARCommandJumpingSumoMediaRecordVideoV2Listener _ARCommandJumpingSumoMediaRecordVideoV2Listener_PARAM) {
        _decoder.setJumpingSumoMediaRecordVideoV2Listener(_ARCommandJumpingSumoMediaRecordVideoV2Listener_PARAM);
    }

    public static void setJumpingSumoNetworkSettingsWifiSelectionListener(ARCommandJumpingSumoNetworkSettingsWifiSelectionListener _ARCommandJumpingSumoNetworkSettingsWifiSelectionListener_PARAM) {
        _decoder.setJumpingSumoNetworkSettingsWifiSelectionListener(_ARCommandJumpingSumoNetworkSettingsWifiSelectionListener_PARAM);
    }

    public static void setJumpingSumoNetworkWifiScanListener(ARCommandJumpingSumoNetworkWifiScanListener _ARCommandJumpingSumoNetworkWifiScanListener_PARAM) {
        _decoder.setJumpingSumoNetworkWifiScanListener(_ARCommandJumpingSumoNetworkWifiScanListener_PARAM);
    }

    public static void setJumpingSumoNetworkWifiAuthChannelListener(ARCommandJumpingSumoNetworkWifiAuthChannelListener _ARCommandJumpingSumoNetworkWifiAuthChannelListener_PARAM) {
        _decoder.setJumpingSumoNetworkWifiAuthChannelListener(_ARCommandJumpingSumoNetworkWifiAuthChannelListener_PARAM);
    }

    public static void setJumpingSumoAudioSettingsMasterVolumeListener(ARCommandJumpingSumoAudioSettingsMasterVolumeListener _ARCommandJumpingSumoAudioSettingsMasterVolumeListener_PARAM) {
        _decoder.setJumpingSumoAudioSettingsMasterVolumeListener(_ARCommandJumpingSumoAudioSettingsMasterVolumeListener_PARAM);
    }

    public static void setJumpingSumoAudioSettingsThemeListener(ARCommandJumpingSumoAudioSettingsThemeListener _ARCommandJumpingSumoAudioSettingsThemeListener_PARAM) {
        _decoder.setJumpingSumoAudioSettingsThemeListener(_ARCommandJumpingSumoAudioSettingsThemeListener_PARAM);
    }

    public static void setJumpingSumoRoadPlanAllScriptsMetadataListener(ARCommandJumpingSumoRoadPlanAllScriptsMetadataListener _ARCommandJumpingSumoRoadPlanAllScriptsMetadataListener_PARAM) {
        _decoder.setJumpingSumoRoadPlanAllScriptsMetadataListener(_ARCommandJumpingSumoRoadPlanAllScriptsMetadataListener_PARAM);
    }

    public static void setJumpingSumoRoadPlanScriptUploadedListener(ARCommandJumpingSumoRoadPlanScriptUploadedListener _ARCommandJumpingSumoRoadPlanScriptUploadedListener_PARAM) {
        _decoder.setJumpingSumoRoadPlanScriptUploadedListener(_ARCommandJumpingSumoRoadPlanScriptUploadedListener_PARAM);
    }

    public static void setJumpingSumoRoadPlanScriptDeleteListener(ARCommandJumpingSumoRoadPlanScriptDeleteListener _ARCommandJumpingSumoRoadPlanScriptDeleteListener_PARAM) {
        _decoder.setJumpingSumoRoadPlanScriptDeleteListener(_ARCommandJumpingSumoRoadPlanScriptDeleteListener_PARAM);
    }

    public static void setJumpingSumoRoadPlanPlayScriptListener(ARCommandJumpingSumoRoadPlanPlayScriptListener _ARCommandJumpingSumoRoadPlanPlayScriptListener_PARAM) {
        _decoder.setJumpingSumoRoadPlanPlayScriptListener(_ARCommandJumpingSumoRoadPlanPlayScriptListener_PARAM);
    }

    public static void setJumpingSumoSpeedSettingsOutdoorListener(ARCommandJumpingSumoSpeedSettingsOutdoorListener _ARCommandJumpingSumoSpeedSettingsOutdoorListener_PARAM) {
        _decoder.setJumpingSumoSpeedSettingsOutdoorListener(_ARCommandJumpingSumoSpeedSettingsOutdoorListener_PARAM);
    }

    public static void setJumpingSumoMediaStreamingVideoEnableListener(ARCommandJumpingSumoMediaStreamingVideoEnableListener _ARCommandJumpingSumoMediaStreamingVideoEnableListener_PARAM) {
        _decoder.setJumpingSumoMediaStreamingVideoEnableListener(_ARCommandJumpingSumoMediaStreamingVideoEnableListener_PARAM);
    }

    public static void setJumpingSumoVideoSettingsAutorecordListener(ARCommandJumpingSumoVideoSettingsAutorecordListener _ARCommandJumpingSumoVideoSettingsAutorecordListener_PARAM) {
        _decoder.setJumpingSumoVideoSettingsAutorecordListener(_ARCommandJumpingSumoVideoSettingsAutorecordListener_PARAM);
    }

    public static void setJumpingSumoPilotingStatePostureChangedListener(ARCommandJumpingSumoPilotingStatePostureChangedListener _ARCommandJumpingSumoPilotingStatePostureChangedListener_PARAM) {
        _decoder.setJumpingSumoPilotingStatePostureChangedListener(_ARCommandJumpingSumoPilotingStatePostureChangedListener_PARAM);
    }

    public static void setJumpingSumoPilotingStateAlertStateChangedListener(ARCommandJumpingSumoPilotingStateAlertStateChangedListener _ARCommandJumpingSumoPilotingStateAlertStateChangedListener_PARAM) {
        _decoder.setJumpingSumoPilotingStateAlertStateChangedListener(_ARCommandJumpingSumoPilotingStateAlertStateChangedListener_PARAM);
    }

    public static void setJumpingSumoPilotingStateSpeedChangedListener(ARCommandJumpingSumoPilotingStateSpeedChangedListener _ARCommandJumpingSumoPilotingStateSpeedChangedListener_PARAM) {
        _decoder.setJumpingSumoPilotingStateSpeedChangedListener(_ARCommandJumpingSumoPilotingStateSpeedChangedListener_PARAM);
    }

    public static void setJumpingSumoAnimationsStateJumpLoadChangedListener(ARCommandJumpingSumoAnimationsStateJumpLoadChangedListener _ARCommandJumpingSumoAnimationsStateJumpLoadChangedListener_PARAM) {
        _decoder.setJumpingSumoAnimationsStateJumpLoadChangedListener(_ARCommandJumpingSumoAnimationsStateJumpLoadChangedListener_PARAM);
    }

    public static void setJumpingSumoAnimationsStateJumpTypeChangedListener(ARCommandJumpingSumoAnimationsStateJumpTypeChangedListener _ARCommandJumpingSumoAnimationsStateJumpTypeChangedListener_PARAM) {
        _decoder.setJumpingSumoAnimationsStateJumpTypeChangedListener(_ARCommandJumpingSumoAnimationsStateJumpTypeChangedListener_PARAM);
    }

    public static void setJumpingSumoAnimationsStateJumpMotorProblemChangedListener(C1521x7c2fcfec _ARCommandJumpingSumoAnimationsStateJumpMotorProblemChangedListener_PARAM) {
        _decoder.setJumpingSumoAnimationsStateJumpMotorProblemChangedListener(_ARCommandJumpingSumoAnimationsStateJumpMotorProblemChangedListener_PARAM);
    }

    public static void setJumpingSumoSettingsStateProductGPSVersionChangedListener(C1530x8955fd3b _ARCommandJumpingSumoSettingsStateProductGPSVersionChangedListener_PARAM) {
        _decoder.setJumpingSumoSettingsStateProductGPSVersionChangedListener(_ARCommandJumpingSumoSettingsStateProductGPSVersionChangedListener_PARAM);
    }

    public static void setJumpingSumoMediaRecordStatePictureStateChangedListener(ARCommandJumpingSumoMediaRecordStatePictureStateChangedListener _ARCommandJumpingSumoMediaRecordStatePictureStateChangedListener_PARAM) {
        _decoder.setJumpingSumoMediaRecordStatePictureStateChangedListener(_ARCommandJumpingSumoMediaRecordStatePictureStateChangedListener_PARAM);
    }

    public static void setJumpingSumoMediaRecordStateVideoStateChangedListener(ARCommandJumpingSumoMediaRecordStateVideoStateChangedListener _ARCommandJumpingSumoMediaRecordStateVideoStateChangedListener_PARAM) {
        _decoder.setJumpingSumoMediaRecordStateVideoStateChangedListener(_ARCommandJumpingSumoMediaRecordStateVideoStateChangedListener_PARAM);
    }

    public static void setJumpingSumoMediaRecordStatePictureStateChangedV2Listener(C1523x390b8c97 _ARCommandJumpingSumoMediaRecordStatePictureStateChangedV2Listener_PARAM) {
        _decoder.setJumpingSumoMediaRecordStatePictureStateChangedV2Listener(_ARCommandJumpingSumoMediaRecordStatePictureStateChangedV2Listener_PARAM);
    }

    public static void setJumpingSumoMediaRecordStateVideoStateChangedV2Listener(ARCommandJumpingSumoMediaRecordStateVideoStateChangedV2Listener _ARCommandJumpingSumoMediaRecordStateVideoStateChangedV2Listener_PARAM) {
        _decoder.setJumpingSumoMediaRecordStateVideoStateChangedV2Listener(_ARCommandJumpingSumoMediaRecordStateVideoStateChangedV2Listener_PARAM);
    }

    public static void setJumpingSumoMediaRecordEventPictureEventChangedListener(ARCommandJumpingSumoMediaRecordEventPictureEventChangedListener _ARCommandJumpingSumoMediaRecordEventPictureEventChangedListener_PARAM) {
        _decoder.setJumpingSumoMediaRecordEventPictureEventChangedListener(_ARCommandJumpingSumoMediaRecordEventPictureEventChangedListener_PARAM);
    }

    public static void setJumpingSumoMediaRecordEventVideoEventChangedListener(ARCommandJumpingSumoMediaRecordEventVideoEventChangedListener _ARCommandJumpingSumoMediaRecordEventVideoEventChangedListener_PARAM) {
        _decoder.setJumpingSumoMediaRecordEventVideoEventChangedListener(_ARCommandJumpingSumoMediaRecordEventVideoEventChangedListener_PARAM);
    }

    public static void setJumpingSumoNetworkSettingsStateWifiSelectionChangedListener(C1525x56533bcf _ARCommandJumpingSumoNetworkSettingsStateWifiSelectionChangedListener_PARAM) {
        _decoder.setJumpingSumoNetworkSettingsStateWifiSelectionChangedListener(_ARCommandJumpingSumoNetworkSettingsStateWifiSelectionChangedListener_PARAM);
    }

    public static void setJumpingSumoNetworkStateWifiScanListChangedListener(ARCommandJumpingSumoNetworkStateWifiScanListChangedListener _ARCommandJumpingSumoNetworkStateWifiScanListChangedListener_PARAM) {
        _decoder.setJumpingSumoNetworkStateWifiScanListChangedListener(_ARCommandJumpingSumoNetworkStateWifiScanListChangedListener_PARAM);
    }

    public static void setJumpingSumoNetworkStateAllWifiScanChangedListener(ARCommandJumpingSumoNetworkStateAllWifiScanChangedListener _ARCommandJumpingSumoNetworkStateAllWifiScanChangedListener_PARAM) {
        _decoder.setJumpingSumoNetworkStateAllWifiScanChangedListener(_ARCommandJumpingSumoNetworkStateAllWifiScanChangedListener_PARAM);
    }

    public static void setJumpingSumoNetworkStateWifiAuthChannelListChangedListener(C1527x71e11fa5 _ARCommandJumpingSumoNetworkStateWifiAuthChannelListChangedListener_PARAM) {
        _decoder.setJumpingSumoNetworkStateWifiAuthChannelListChangedListener(_ARCommandJumpingSumoNetworkStateWifiAuthChannelListChangedListener_PARAM);
    }

    public static void setJumpingSumoNetworkStateAllWifiAuthChannelChangedListener(C1526x7a81b642 _ARCommandJumpingSumoNetworkStateAllWifiAuthChannelChangedListener_PARAM) {
        _decoder.setJumpingSumoNetworkStateAllWifiAuthChannelChangedListener(_ARCommandJumpingSumoNetworkStateAllWifiAuthChannelChangedListener_PARAM);
    }

    public static void setJumpingSumoNetworkStateLinkQualityChangedListener(ARCommandJumpingSumoNetworkStateLinkQualityChangedListener _ARCommandJumpingSumoNetworkStateLinkQualityChangedListener_PARAM) {
        _decoder.setJumpingSumoNetworkStateLinkQualityChangedListener(_ARCommandJumpingSumoNetworkStateLinkQualityChangedListener_PARAM);
    }

    public static void setJumpingSumoAudioSettingsStateMasterVolumeChangedListener(C1522x83aa06f6 _ARCommandJumpingSumoAudioSettingsStateMasterVolumeChangedListener_PARAM) {
        _decoder.setJumpingSumoAudioSettingsStateMasterVolumeChangedListener(_ARCommandJumpingSumoAudioSettingsStateMasterVolumeChangedListener_PARAM);
    }

    public static void setJumpingSumoAudioSettingsStateThemeChangedListener(ARCommandJumpingSumoAudioSettingsStateThemeChangedListener _ARCommandJumpingSumoAudioSettingsStateThemeChangedListener_PARAM) {
        _decoder.setJumpingSumoAudioSettingsStateThemeChangedListener(_ARCommandJumpingSumoAudioSettingsStateThemeChangedListener_PARAM);
    }

    public static void setJumpingSumoRoadPlanStateScriptMetadataListChangedListener(C1529x3c747cc6 _ARCommandJumpingSumoRoadPlanStateScriptMetadataListChangedListener_PARAM) {
        _decoder.setJumpingSumoRoadPlanStateScriptMetadataListChangedListener(_ARCommandJumpingSumoRoadPlanStateScriptMetadataListChangedListener_PARAM);
    }

    public static void setJumpingSumoRoadPlanStateAllScriptsMetadataChangedListener(C1528xdd328168 _ARCommandJumpingSumoRoadPlanStateAllScriptsMetadataChangedListener_PARAM) {
        _decoder.setJumpingSumoRoadPlanStateAllScriptsMetadataChangedListener(_ARCommandJumpingSumoRoadPlanStateAllScriptsMetadataChangedListener_PARAM);
    }

    public static void setJumpingSumoRoadPlanStateScriptUploadChangedListener(ARCommandJumpingSumoRoadPlanStateScriptUploadChangedListener _ARCommandJumpingSumoRoadPlanStateScriptUploadChangedListener_PARAM) {
        _decoder.setJumpingSumoRoadPlanStateScriptUploadChangedListener(_ARCommandJumpingSumoRoadPlanStateScriptUploadChangedListener_PARAM);
    }

    public static void setJumpingSumoRoadPlanStateScriptDeleteChangedListener(ARCommandJumpingSumoRoadPlanStateScriptDeleteChangedListener _ARCommandJumpingSumoRoadPlanStateScriptDeleteChangedListener_PARAM) {
        _decoder.setJumpingSumoRoadPlanStateScriptDeleteChangedListener(_ARCommandJumpingSumoRoadPlanStateScriptDeleteChangedListener_PARAM);
    }

    public static void setJumpingSumoRoadPlanStatePlayScriptChangedListener(ARCommandJumpingSumoRoadPlanStatePlayScriptChangedListener _ARCommandJumpingSumoRoadPlanStatePlayScriptChangedListener_PARAM) {
        _decoder.setJumpingSumoRoadPlanStatePlayScriptChangedListener(_ARCommandJumpingSumoRoadPlanStatePlayScriptChangedListener_PARAM);
    }

    public static void setJumpingSumoSpeedSettingsStateOutdoorChangedListener(ARCommandJumpingSumoSpeedSettingsStateOutdoorChangedListener _ARCommandJumpingSumoSpeedSettingsStateOutdoorChangedListener_PARAM) {
        _decoder.setJumpingSumoSpeedSettingsStateOutdoorChangedListener(_ARCommandJumpingSumoSpeedSettingsStateOutdoorChangedListener_PARAM);
    }

    public static void setJumpingSumoMediaStreamingStateVideoEnableChangedListener(C1524x1975b2bf _ARCommandJumpingSumoMediaStreamingStateVideoEnableChangedListener_PARAM) {
        _decoder.setJumpingSumoMediaStreamingStateVideoEnableChangedListener(_ARCommandJumpingSumoMediaStreamingStateVideoEnableChangedListener_PARAM);
    }

    public static void setJumpingSumoVideoSettingsStateAutorecordChangedListener(ARCommandJumpingSumoVideoSettingsStateAutorecordChangedListener _ARCommandJumpingSumoVideoSettingsStateAutorecordChangedListener_PARAM) {
        _decoder.setJumpingSumoVideoSettingsStateAutorecordChangedListener(_ARCommandJumpingSumoVideoSettingsStateAutorecordChangedListener_PARAM);
    }

    public static void setMapperGrabListener(ARCommandMapperGrabListener _ARCommandMapperGrabListener_PARAM) {
        _decoder.setMapperGrabListener(_ARCommandMapperGrabListener_PARAM);
    }

    public static void setMapperMapButtonActionListener(ARCommandMapperMapButtonActionListener _ARCommandMapperMapButtonActionListener_PARAM) {
        _decoder.setMapperMapButtonActionListener(_ARCommandMapperMapButtonActionListener_PARAM);
    }

    public static void setMapperMapAxisActionListener(ARCommandMapperMapAxisActionListener _ARCommandMapperMapAxisActionListener_PARAM) {
        _decoder.setMapperMapAxisActionListener(_ARCommandMapperMapAxisActionListener_PARAM);
    }

    public static void setMapperResetMappingListener(ARCommandMapperResetMappingListener _ARCommandMapperResetMappingListener_PARAM) {
        _decoder.setMapperResetMappingListener(_ARCommandMapperResetMappingListener_PARAM);
    }

    public static void setMapperSetExpoListener(ARCommandMapperSetExpoListener _ARCommandMapperSetExpoListener_PARAM) {
        _decoder.setMapperSetExpoListener(_ARCommandMapperSetExpoListener_PARAM);
    }

    public static void setMapperSetInvertedListener(ARCommandMapperSetInvertedListener _ARCommandMapperSetInvertedListener_PARAM) {
        _decoder.setMapperSetInvertedListener(_ARCommandMapperSetInvertedListener_PARAM);
    }

    public static void setMapperGrabStateListener(ARCommandMapperGrabStateListener _ARCommandMapperGrabStateListener_PARAM) {
        _decoder.setMapperGrabStateListener(_ARCommandMapperGrabStateListener_PARAM);
    }

    public static void setMapperGrabButtonEventListener(ARCommandMapperGrabButtonEventListener _ARCommandMapperGrabButtonEventListener_PARAM) {
        _decoder.setMapperGrabButtonEventListener(_ARCommandMapperGrabButtonEventListener_PARAM);
    }

    public static void setMapperGrabAxisEventListener(ARCommandMapperGrabAxisEventListener _ARCommandMapperGrabAxisEventListener_PARAM) {
        _decoder.setMapperGrabAxisEventListener(_ARCommandMapperGrabAxisEventListener_PARAM);
    }

    public static void setMapperButtonMappingItemListener(ARCommandMapperButtonMappingItemListener _ARCommandMapperButtonMappingItemListener_PARAM) {
        _decoder.setMapperButtonMappingItemListener(_ARCommandMapperButtonMappingItemListener_PARAM);
    }

    public static void setMapperAxisMappingItemListener(ARCommandMapperAxisMappingItemListener _ARCommandMapperAxisMappingItemListener_PARAM) {
        _decoder.setMapperAxisMappingItemListener(_ARCommandMapperAxisMappingItemListener_PARAM);
    }

    public static void setMapperApplicationAxisEventListener(ARCommandMapperApplicationAxisEventListener _ARCommandMapperApplicationAxisEventListener_PARAM) {
        _decoder.setMapperApplicationAxisEventListener(_ARCommandMapperApplicationAxisEventListener_PARAM);
    }

    public static void setMapperApplicationButtonEventListener(ARCommandMapperApplicationButtonEventListener _ARCommandMapperApplicationButtonEventListener_PARAM) {
        _decoder.setMapperApplicationButtonEventListener(_ARCommandMapperApplicationButtonEventListener_PARAM);
    }

    public static void setMapperExpoMapItemListener(ARCommandMapperExpoMapItemListener _ARCommandMapperExpoMapItemListener_PARAM) {
        _decoder.setMapperExpoMapItemListener(_ARCommandMapperExpoMapItemListener_PARAM);
    }

    public static void setMapperInvertedMapItemListener(ARCommandMapperInvertedMapItemListener _ARCommandMapperInvertedMapItemListener_PARAM) {
        _decoder.setMapperInvertedMapItemListener(_ARCommandMapperInvertedMapItemListener_PARAM);
    }

    public static void setMapperActiveProductListener(ARCommandMapperActiveProductListener _ARCommandMapperActiveProductListener_PARAM) {
        _decoder.setMapperActiveProductListener(_ARCommandMapperActiveProductListener_PARAM);
    }

    public static void setMapperMiniMapButtonActionListener(ARCommandMapperMiniMapButtonActionListener _ARCommandMapperMiniMapButtonActionListener_PARAM) {
        _decoder.setMapperMiniMapButtonActionListener(_ARCommandMapperMiniMapButtonActionListener_PARAM);
    }

    public static void setMapperMiniMapAxisActionListener(ARCommandMapperMiniMapAxisActionListener _ARCommandMapperMiniMapAxisActionListener_PARAM) {
        _decoder.setMapperMiniMapAxisActionListener(_ARCommandMapperMiniMapAxisActionListener_PARAM);
    }

    public static void setMapperMiniResetMappingListener(ARCommandMapperMiniResetMappingListener _ARCommandMapperMiniResetMappingListener_PARAM) {
        _decoder.setMapperMiniResetMappingListener(_ARCommandMapperMiniResetMappingListener_PARAM);
    }

    public static void setMapperMiniButtonMappingItemListener(ARCommandMapperMiniButtonMappingItemListener _ARCommandMapperMiniButtonMappingItemListener_PARAM) {
        _decoder.setMapperMiniButtonMappingItemListener(_ARCommandMapperMiniButtonMappingItemListener_PARAM);
    }

    public static void setMapperMiniAxisMappingItemListener(ARCommandMapperMiniAxisMappingItemListener _ARCommandMapperMiniAxisMappingItemListener_PARAM) {
        _decoder.setMapperMiniAxisMappingItemListener(_ARCommandMapperMiniAxisMappingItemListener_PARAM);
    }

    public static void setMiniDronePilotingFlatTrimListener(ARCommandMiniDronePilotingFlatTrimListener _ARCommandMiniDronePilotingFlatTrimListener_PARAM) {
        _decoder.setMiniDronePilotingFlatTrimListener(_ARCommandMiniDronePilotingFlatTrimListener_PARAM);
    }

    public static void setMiniDronePilotingTakeOffListener(ARCommandMiniDronePilotingTakeOffListener _ARCommandMiniDronePilotingTakeOffListener_PARAM) {
        _decoder.setMiniDronePilotingTakeOffListener(_ARCommandMiniDronePilotingTakeOffListener_PARAM);
    }

    public static void setMiniDronePilotingPCMDListener(ARCommandMiniDronePilotingPCMDListener _ARCommandMiniDronePilotingPCMDListener_PARAM) {
        _decoder.setMiniDronePilotingPCMDListener(_ARCommandMiniDronePilotingPCMDListener_PARAM);
    }

    public static void setMiniDronePilotingLandingListener(ARCommandMiniDronePilotingLandingListener _ARCommandMiniDronePilotingLandingListener_PARAM) {
        _decoder.setMiniDronePilotingLandingListener(_ARCommandMiniDronePilotingLandingListener_PARAM);
    }

    public static void setMiniDronePilotingEmergencyListener(ARCommandMiniDronePilotingEmergencyListener _ARCommandMiniDronePilotingEmergencyListener_PARAM) {
        _decoder.setMiniDronePilotingEmergencyListener(_ARCommandMiniDronePilotingEmergencyListener_PARAM);
    }

    public static void setMiniDronePilotingAutoTakeOffModeListener(ARCommandMiniDronePilotingAutoTakeOffModeListener _ARCommandMiniDronePilotingAutoTakeOffModeListener_PARAM) {
        _decoder.setMiniDronePilotingAutoTakeOffModeListener(_ARCommandMiniDronePilotingAutoTakeOffModeListener_PARAM);
    }

    public static void setMiniDronePilotingFlyingModeListener(ARCommandMiniDronePilotingFlyingModeListener _ARCommandMiniDronePilotingFlyingModeListener_PARAM) {
        _decoder.setMiniDronePilotingFlyingModeListener(_ARCommandMiniDronePilotingFlyingModeListener_PARAM);
    }

    public static void setMiniDronePilotingPlaneGearBoxListener(ARCommandMiniDronePilotingPlaneGearBoxListener _ARCommandMiniDronePilotingPlaneGearBoxListener_PARAM) {
        _decoder.setMiniDronePilotingPlaneGearBoxListener(_ARCommandMiniDronePilotingPlaneGearBoxListener_PARAM);
    }

    public static void setMiniDroneAnimationsFlipListener(ARCommandMiniDroneAnimationsFlipListener _ARCommandMiniDroneAnimationsFlipListener_PARAM) {
        _decoder.setMiniDroneAnimationsFlipListener(_ARCommandMiniDroneAnimationsFlipListener_PARAM);
    }

    public static void setMiniDroneAnimationsCapListener(ARCommandMiniDroneAnimationsCapListener _ARCommandMiniDroneAnimationsCapListener_PARAM) {
        _decoder.setMiniDroneAnimationsCapListener(_ARCommandMiniDroneAnimationsCapListener_PARAM);
    }

    public static void setMiniDroneMediaRecordPictureListener(ARCommandMiniDroneMediaRecordPictureListener _ARCommandMiniDroneMediaRecordPictureListener_PARAM) {
        _decoder.setMiniDroneMediaRecordPictureListener(_ARCommandMiniDroneMediaRecordPictureListener_PARAM);
    }

    public static void setMiniDroneMediaRecordPictureV2Listener(ARCommandMiniDroneMediaRecordPictureV2Listener _ARCommandMiniDroneMediaRecordPictureV2Listener_PARAM) {
        _decoder.setMiniDroneMediaRecordPictureV2Listener(_ARCommandMiniDroneMediaRecordPictureV2Listener_PARAM);
    }

    public static void setMiniDronePilotingSettingsMaxAltitudeListener(ARCommandMiniDronePilotingSettingsMaxAltitudeListener _ARCommandMiniDronePilotingSettingsMaxAltitudeListener_PARAM) {
        _decoder.setMiniDronePilotingSettingsMaxAltitudeListener(_ARCommandMiniDronePilotingSettingsMaxAltitudeListener_PARAM);
    }

    public static void setMiniDronePilotingSettingsMaxTiltListener(ARCommandMiniDronePilotingSettingsMaxTiltListener _ARCommandMiniDronePilotingSettingsMaxTiltListener_PARAM) {
        _decoder.setMiniDronePilotingSettingsMaxTiltListener(_ARCommandMiniDronePilotingSettingsMaxTiltListener_PARAM);
    }

    public static void setMiniDronePilotingSettingsBankedTurnListener(ARCommandMiniDronePilotingSettingsBankedTurnListener _ARCommandMiniDronePilotingSettingsBankedTurnListener_PARAM) {
        _decoder.setMiniDronePilotingSettingsBankedTurnListener(_ARCommandMiniDronePilotingSettingsBankedTurnListener_PARAM);
    }

    public static void setMiniDroneSpeedSettingsMaxVerticalSpeedListener(ARCommandMiniDroneSpeedSettingsMaxVerticalSpeedListener _ARCommandMiniDroneSpeedSettingsMaxVerticalSpeedListener_PARAM) {
        _decoder.setMiniDroneSpeedSettingsMaxVerticalSpeedListener(_ARCommandMiniDroneSpeedSettingsMaxVerticalSpeedListener_PARAM);
    }

    public static void setMiniDroneSpeedSettingsMaxRotationSpeedListener(ARCommandMiniDroneSpeedSettingsMaxRotationSpeedListener _ARCommandMiniDroneSpeedSettingsMaxRotationSpeedListener_PARAM) {
        _decoder.setMiniDroneSpeedSettingsMaxRotationSpeedListener(_ARCommandMiniDroneSpeedSettingsMaxRotationSpeedListener_PARAM);
    }

    public static void setMiniDroneSpeedSettingsWheelsListener(ARCommandMiniDroneSpeedSettingsWheelsListener _ARCommandMiniDroneSpeedSettingsWheelsListener_PARAM) {
        _decoder.setMiniDroneSpeedSettingsWheelsListener(_ARCommandMiniDroneSpeedSettingsWheelsListener_PARAM);
    }

    public static void setMiniDroneSpeedSettingsMaxHorizontalSpeedListener(ARCommandMiniDroneSpeedSettingsMaxHorizontalSpeedListener _ARCommandMiniDroneSpeedSettingsMaxHorizontalSpeedListener_PARAM) {
        _decoder.setMiniDroneSpeedSettingsMaxHorizontalSpeedListener(_ARCommandMiniDroneSpeedSettingsMaxHorizontalSpeedListener_PARAM);
    }

    public static void setMiniDroneSpeedSettingsMaxPlaneModeRotationSpeedListener(ARCommandMiniDroneSpeedSettingsMaxPlaneModeRotationSpeedListener _ARCommandMiniDroneSpeedSettingsMaxPlaneModeRotationSpeedListener_PARAM) {
        _decoder.setMiniDroneSpeedSettingsMaxPlaneModeRotationSpeedListener(_ARCommandMiniDroneSpeedSettingsMaxPlaneModeRotationSpeedListener_PARAM);
    }

    public static void setMiniDroneSettingsCutOutModeListener(ARCommandMiniDroneSettingsCutOutModeListener _ARCommandMiniDroneSettingsCutOutModeListener_PARAM) {
        _decoder.setMiniDroneSettingsCutOutModeListener(_ARCommandMiniDroneSettingsCutOutModeListener_PARAM);
    }

    public static void setMiniDroneGPSControllerLatitudeForRunListener(ARCommandMiniDroneGPSControllerLatitudeForRunListener _ARCommandMiniDroneGPSControllerLatitudeForRunListener_PARAM) {
        _decoder.setMiniDroneGPSControllerLatitudeForRunListener(_ARCommandMiniDroneGPSControllerLatitudeForRunListener_PARAM);
    }

    public static void setMiniDroneGPSControllerLongitudeForRunListener(ARCommandMiniDroneGPSControllerLongitudeForRunListener _ARCommandMiniDroneGPSControllerLongitudeForRunListener_PARAM) {
        _decoder.setMiniDroneGPSControllerLongitudeForRunListener(_ARCommandMiniDroneGPSControllerLongitudeForRunListener_PARAM);
    }

    public static void setMiniDroneConfigurationControllerTypeListener(ARCommandMiniDroneConfigurationControllerTypeListener _ARCommandMiniDroneConfigurationControllerTypeListener_PARAM) {
        _decoder.setMiniDroneConfigurationControllerTypeListener(_ARCommandMiniDroneConfigurationControllerTypeListener_PARAM);
    }

    public static void setMiniDroneConfigurationControllerNameListener(ARCommandMiniDroneConfigurationControllerNameListener _ARCommandMiniDroneConfigurationControllerNameListener_PARAM) {
        _decoder.setMiniDroneConfigurationControllerNameListener(_ARCommandMiniDroneConfigurationControllerNameListener_PARAM);
    }

    public static void setMiniDroneUsbAccessoryLightControlListener(ARCommandMiniDroneUsbAccessoryLightControlListener _ARCommandMiniDroneUsbAccessoryLightControlListener_PARAM) {
        _decoder.setMiniDroneUsbAccessoryLightControlListener(_ARCommandMiniDroneUsbAccessoryLightControlListener_PARAM);
    }

    public static void setMiniDroneUsbAccessoryClawControlListener(ARCommandMiniDroneUsbAccessoryClawControlListener _ARCommandMiniDroneUsbAccessoryClawControlListener_PARAM) {
        _decoder.setMiniDroneUsbAccessoryClawControlListener(_ARCommandMiniDroneUsbAccessoryClawControlListener_PARAM);
    }

    public static void setMiniDroneUsbAccessoryGunControlListener(ARCommandMiniDroneUsbAccessoryGunControlListener _ARCommandMiniDroneUsbAccessoryGunControlListener_PARAM) {
        _decoder.setMiniDroneUsbAccessoryGunControlListener(_ARCommandMiniDroneUsbAccessoryGunControlListener_PARAM);
    }

    public static void setMiniDroneRemoteControllerSetPairedRemoteListener(ARCommandMiniDroneRemoteControllerSetPairedRemoteListener _ARCommandMiniDroneRemoteControllerSetPairedRemoteListener_PARAM) {
        _decoder.setMiniDroneRemoteControllerSetPairedRemoteListener(_ARCommandMiniDroneRemoteControllerSetPairedRemoteListener_PARAM);
    }

    public static void setMiniDronePilotingStateFlatTrimChangedListener(ARCommandMiniDronePilotingStateFlatTrimChangedListener _ARCommandMiniDronePilotingStateFlatTrimChangedListener_PARAM) {
        _decoder.setMiniDronePilotingStateFlatTrimChangedListener(_ARCommandMiniDronePilotingStateFlatTrimChangedListener_PARAM);
    }

    public static void setMiniDronePilotingStateFlyingStateChangedListener(ARCommandMiniDronePilotingStateFlyingStateChangedListener _ARCommandMiniDronePilotingStateFlyingStateChangedListener_PARAM) {
        _decoder.setMiniDronePilotingStateFlyingStateChangedListener(_ARCommandMiniDronePilotingStateFlyingStateChangedListener_PARAM);
    }

    public static void setMiniDronePilotingStateAlertStateChangedListener(ARCommandMiniDronePilotingStateAlertStateChangedListener _ARCommandMiniDronePilotingStateAlertStateChangedListener_PARAM) {
        _decoder.setMiniDronePilotingStateAlertStateChangedListener(_ARCommandMiniDronePilotingStateAlertStateChangedListener_PARAM);
    }

    public static void setMiniDronePilotingStateAutoTakeOffModeChangedListener(ARCommandMiniDronePilotingStateAutoTakeOffModeChangedListener _ARCommandMiniDronePilotingStateAutoTakeOffModeChangedListener_PARAM) {
        _decoder.setMiniDronePilotingStateAutoTakeOffModeChangedListener(_ARCommandMiniDronePilotingStateAutoTakeOffModeChangedListener_PARAM);
    }

    public static void setMiniDronePilotingStateFlyingModeChangedListener(ARCommandMiniDronePilotingStateFlyingModeChangedListener _ARCommandMiniDronePilotingStateFlyingModeChangedListener_PARAM) {
        _decoder.setMiniDronePilotingStateFlyingModeChangedListener(_ARCommandMiniDronePilotingStateFlyingModeChangedListener_PARAM);
    }

    public static void setMiniDronePilotingStatePlaneGearBoxChangedListener(ARCommandMiniDronePilotingStatePlaneGearBoxChangedListener _ARCommandMiniDronePilotingStatePlaneGearBoxChangedListener_PARAM) {
        _decoder.setMiniDronePilotingStatePlaneGearBoxChangedListener(_ARCommandMiniDronePilotingStatePlaneGearBoxChangedListener_PARAM);
    }

    public static void setMiniDroneMediaRecordStatePictureStateChangedListener(ARCommandMiniDroneMediaRecordStatePictureStateChangedListener _ARCommandMiniDroneMediaRecordStatePictureStateChangedListener_PARAM) {
        _decoder.setMiniDroneMediaRecordStatePictureStateChangedListener(_ARCommandMiniDroneMediaRecordStatePictureStateChangedListener_PARAM);
    }

    public static void setMiniDroneMediaRecordStatePictureStateChangedV2Listener(ARCommandMiniDroneMediaRecordStatePictureStateChangedV2Listener _ARCommandMiniDroneMediaRecordStatePictureStateChangedV2Listener_PARAM) {
        _decoder.setMiniDroneMediaRecordStatePictureStateChangedV2Listener(_ARCommandMiniDroneMediaRecordStatePictureStateChangedV2Listener_PARAM);
    }

    public static void setMiniDroneMediaRecordEventPictureEventChangedListener(ARCommandMiniDroneMediaRecordEventPictureEventChangedListener _ARCommandMiniDroneMediaRecordEventPictureEventChangedListener_PARAM) {
        _decoder.setMiniDroneMediaRecordEventPictureEventChangedListener(_ARCommandMiniDroneMediaRecordEventPictureEventChangedListener_PARAM);
    }

    public static void setMiniDronePilotingSettingsStateMaxAltitudeChangedListener(C1531xc19b3e7f _ARCommandMiniDronePilotingSettingsStateMaxAltitudeChangedListener_PARAM) {
        _decoder.setMiniDronePilotingSettingsStateMaxAltitudeChangedListener(_ARCommandMiniDronePilotingSettingsStateMaxAltitudeChangedListener_PARAM);
    }

    public static void setMiniDronePilotingSettingsStateMaxTiltChangedListener(ARCommandMiniDronePilotingSettingsStateMaxTiltChangedListener _ARCommandMiniDronePilotingSettingsStateMaxTiltChangedListener_PARAM) {
        _decoder.setMiniDronePilotingSettingsStateMaxTiltChangedListener(_ARCommandMiniDronePilotingSettingsStateMaxTiltChangedListener_PARAM);
    }

    public static void setMiniDronePilotingSettingsStateBankedTurnChangedListener(ARCommandMiniDronePilotingSettingsStateBankedTurnChangedListener _ARCommandMiniDronePilotingSettingsStateBankedTurnChangedListener_PARAM) {
        _decoder.setMiniDronePilotingSettingsStateBankedTurnChangedListener(_ARCommandMiniDronePilotingSettingsStateBankedTurnChangedListener_PARAM);
    }

    public static void setMiniDroneSpeedSettingsStateMaxVerticalSpeedChangedListener(C1537x7d4ee4ad _ARCommandMiniDroneSpeedSettingsStateMaxVerticalSpeedChangedListener_PARAM) {
        _decoder.setMiniDroneSpeedSettingsStateMaxVerticalSpeedChangedListener(_ARCommandMiniDroneSpeedSettingsStateMaxVerticalSpeedChangedListener_PARAM);
    }

    public static void setMiniDroneSpeedSettingsStateMaxRotationSpeedChangedListener(C1536x310f38b5 _ARCommandMiniDroneSpeedSettingsStateMaxRotationSpeedChangedListener_PARAM) {
        _decoder.setMiniDroneSpeedSettingsStateMaxRotationSpeedChangedListener(_ARCommandMiniDroneSpeedSettingsStateMaxRotationSpeedChangedListener_PARAM);
    }

    public static void setMiniDroneSpeedSettingsStateWheelsChangedListener(ARCommandMiniDroneSpeedSettingsStateWheelsChangedListener _ARCommandMiniDroneSpeedSettingsStateWheelsChangedListener_PARAM) {
        _decoder.setMiniDroneSpeedSettingsStateWheelsChangedListener(_ARCommandMiniDroneSpeedSettingsStateWheelsChangedListener_PARAM);
    }

    public static void setMiniDroneSpeedSettingsStateMaxHorizontalSpeedChangedListener(C1534x2b3248db _ARCommandMiniDroneSpeedSettingsStateMaxHorizontalSpeedChangedListener_PARAM) {
        _decoder.setMiniDroneSpeedSettingsStateMaxHorizontalSpeedChangedListener(_ARCommandMiniDroneSpeedSettingsStateMaxHorizontalSpeedChangedListener_PARAM);
    }

    /* renamed from: setMiniDroneSpeedSettingsStateMaxPlaneModeRotationSpeedChangedListener */
    public static void m596xb8cf7000(C1535x30d7d388 _ARCommandMiniDroneSpeedSettingsStateMaxPlaneModeRotationSpeedChangedListener_PARAM) {
        _decoder.m79xb8cf7000(_ARCommandMiniDroneSpeedSettingsStateMaxPlaneModeRotationSpeedChangedListener_PARAM);
    }

    public static void setMiniDroneSettingsStateProductMotorsVersionChangedListener(C1533xd43a29b6 _ARCommandMiniDroneSettingsStateProductMotorsVersionChangedListener_PARAM) {
        _decoder.setMiniDroneSettingsStateProductMotorsVersionChangedListener(_ARCommandMiniDroneSettingsStateProductMotorsVersionChangedListener_PARAM);
    }

    public static void setMiniDroneSettingsStateProductInertialVersionChangedListener(C1532x9a315f0a _ARCommandMiniDroneSettingsStateProductInertialVersionChangedListener_PARAM) {
        _decoder.setMiniDroneSettingsStateProductInertialVersionChangedListener(_ARCommandMiniDroneSettingsStateProductInertialVersionChangedListener_PARAM);
    }

    public static void setMiniDroneSettingsStateCutOutModeChangedListener(ARCommandMiniDroneSettingsStateCutOutModeChangedListener _ARCommandMiniDroneSettingsStateCutOutModeChangedListener_PARAM) {
        _decoder.setMiniDroneSettingsStateCutOutModeChangedListener(_ARCommandMiniDroneSettingsStateCutOutModeChangedListener_PARAM);
    }

    public static void setMiniDroneFloodControlStateFloodControlChangedListener(ARCommandMiniDroneFloodControlStateFloodControlChangedListener _ARCommandMiniDroneFloodControlStateFloodControlChangedListener_PARAM) {
        _decoder.setMiniDroneFloodControlStateFloodControlChangedListener(_ARCommandMiniDroneFloodControlStateFloodControlChangedListener_PARAM);
    }

    public static void setMiniDroneUsbAccessoryStateLightStateListener(ARCommandMiniDroneUsbAccessoryStateLightStateListener _ARCommandMiniDroneUsbAccessoryStateLightStateListener_PARAM) {
        _decoder.setMiniDroneUsbAccessoryStateLightStateListener(_ARCommandMiniDroneUsbAccessoryStateLightStateListener_PARAM);
    }

    public static void setMiniDroneUsbAccessoryStateClawStateListener(ARCommandMiniDroneUsbAccessoryStateClawStateListener _ARCommandMiniDroneUsbAccessoryStateClawStateListener_PARAM) {
        _decoder.setMiniDroneUsbAccessoryStateClawStateListener(_ARCommandMiniDroneUsbAccessoryStateClawStateListener_PARAM);
    }

    public static void setMiniDroneUsbAccessoryStateGunStateListener(ARCommandMiniDroneUsbAccessoryStateGunStateListener _ARCommandMiniDroneUsbAccessoryStateGunStateListener_PARAM) {
        _decoder.setMiniDroneUsbAccessoryStateGunStateListener(_ARCommandMiniDroneUsbAccessoryStateGunStateListener_PARAM);
    }

    public static void setMiniDroneNavigationDataStateDronePositionListener(ARCommandMiniDroneNavigationDataStateDronePositionListener _ARCommandMiniDroneNavigationDataStateDronePositionListener_PARAM) {
        _decoder.setMiniDroneNavigationDataStateDronePositionListener(_ARCommandMiniDroneNavigationDataStateDronePositionListener_PARAM);
    }

    public static void setPowerupPilotingPCMDListener(ARCommandPowerupPilotingPCMDListener _ARCommandPowerupPilotingPCMDListener_PARAM) {
        _decoder.setPowerupPilotingPCMDListener(_ARCommandPowerupPilotingPCMDListener_PARAM);
    }

    public static void setPowerupPilotingUserTakeOffListener(ARCommandPowerupPilotingUserTakeOffListener _ARCommandPowerupPilotingUserTakeOffListener_PARAM) {
        _decoder.setPowerupPilotingUserTakeOffListener(_ARCommandPowerupPilotingUserTakeOffListener_PARAM);
    }

    public static void setPowerupPilotingMotorModeListener(ARCommandPowerupPilotingMotorModeListener _ARCommandPowerupPilotingMotorModeListener_PARAM) {
        _decoder.setPowerupPilotingMotorModeListener(_ARCommandPowerupPilotingMotorModeListener_PARAM);
    }

    public static void setPowerupPilotingSettingsSetListener(ARCommandPowerupPilotingSettingsSetListener _ARCommandPowerupPilotingSettingsSetListener_PARAM) {
        _decoder.setPowerupPilotingSettingsSetListener(_ARCommandPowerupPilotingSettingsSetListener_PARAM);
    }

    public static void setPowerupMediaRecordPictureV2Listener(ARCommandPowerupMediaRecordPictureV2Listener _ARCommandPowerupMediaRecordPictureV2Listener_PARAM) {
        _decoder.setPowerupMediaRecordPictureV2Listener(_ARCommandPowerupMediaRecordPictureV2Listener_PARAM);
    }

    public static void setPowerupMediaRecordVideoV2Listener(ARCommandPowerupMediaRecordVideoV2Listener _ARCommandPowerupMediaRecordVideoV2Listener_PARAM) {
        _decoder.setPowerupMediaRecordVideoV2Listener(_ARCommandPowerupMediaRecordVideoV2Listener_PARAM);
    }

    public static void setPowerupNetworkSettingsWifiSelectionListener(ARCommandPowerupNetworkSettingsWifiSelectionListener _ARCommandPowerupNetworkSettingsWifiSelectionListener_PARAM) {
        _decoder.setPowerupNetworkSettingsWifiSelectionListener(_ARCommandPowerupNetworkSettingsWifiSelectionListener_PARAM);
    }

    public static void setPowerupNetworkWifiScanListener(ARCommandPowerupNetworkWifiScanListener _ARCommandPowerupNetworkWifiScanListener_PARAM) {
        _decoder.setPowerupNetworkWifiScanListener(_ARCommandPowerupNetworkWifiScanListener_PARAM);
    }

    public static void setPowerupNetworkWifiAuthChannelListener(ARCommandPowerupNetworkWifiAuthChannelListener _ARCommandPowerupNetworkWifiAuthChannelListener_PARAM) {
        _decoder.setPowerupNetworkWifiAuthChannelListener(_ARCommandPowerupNetworkWifiAuthChannelListener_PARAM);
    }

    public static void setPowerupMediaStreamingVideoEnableListener(ARCommandPowerupMediaStreamingVideoEnableListener _ARCommandPowerupMediaStreamingVideoEnableListener_PARAM) {
        _decoder.setPowerupMediaStreamingVideoEnableListener(_ARCommandPowerupMediaStreamingVideoEnableListener_PARAM);
    }

    public static void setPowerupVideoSettingsAutorecordListener(ARCommandPowerupVideoSettingsAutorecordListener _ARCommandPowerupVideoSettingsAutorecordListener_PARAM) {
        _decoder.setPowerupVideoSettingsAutorecordListener(_ARCommandPowerupVideoSettingsAutorecordListener_PARAM);
    }

    public static void setPowerupVideoSettingsVideoModeListener(ARCommandPowerupVideoSettingsVideoModeListener _ARCommandPowerupVideoSettingsVideoModeListener_PARAM) {
        _decoder.setPowerupVideoSettingsVideoModeListener(_ARCommandPowerupVideoSettingsVideoModeListener_PARAM);
    }

    public static void setPowerupSoundsBuzzListener(ARCommandPowerupSoundsBuzzListener _ARCommandPowerupSoundsBuzzListener_PARAM) {
        _decoder.setPowerupSoundsBuzzListener(_ARCommandPowerupSoundsBuzzListener_PARAM);
    }

    public static void setPowerupPilotingStateAlertStateChangedListener(ARCommandPowerupPilotingStateAlertStateChangedListener _ARCommandPowerupPilotingStateAlertStateChangedListener_PARAM) {
        _decoder.setPowerupPilotingStateAlertStateChangedListener(_ARCommandPowerupPilotingStateAlertStateChangedListener_PARAM);
    }

    public static void setPowerupPilotingStateFlyingStateChangedListener(ARCommandPowerupPilotingStateFlyingStateChangedListener _ARCommandPowerupPilotingStateFlyingStateChangedListener_PARAM) {
        _decoder.setPowerupPilotingStateFlyingStateChangedListener(_ARCommandPowerupPilotingStateFlyingStateChangedListener_PARAM);
    }

    public static void setPowerupPilotingStateMotorModeChangedListener(ARCommandPowerupPilotingStateMotorModeChangedListener _ARCommandPowerupPilotingStateMotorModeChangedListener_PARAM) {
        _decoder.setPowerupPilotingStateMotorModeChangedListener(_ARCommandPowerupPilotingStateMotorModeChangedListener_PARAM);
    }

    public static void setPowerupPilotingStateAttitudeChangedListener(ARCommandPowerupPilotingStateAttitudeChangedListener _ARCommandPowerupPilotingStateAttitudeChangedListener_PARAM) {
        _decoder.setPowerupPilotingStateAttitudeChangedListener(_ARCommandPowerupPilotingStateAttitudeChangedListener_PARAM);
    }

    public static void setPowerupPilotingStateAltitudeChangedListener(ARCommandPowerupPilotingStateAltitudeChangedListener _ARCommandPowerupPilotingStateAltitudeChangedListener_PARAM) {
        _decoder.setPowerupPilotingStateAltitudeChangedListener(_ARCommandPowerupPilotingStateAltitudeChangedListener_PARAM);
    }

    public static void setPowerupPilotingSettingsStateSettingChangedListener(ARCommandPowerupPilotingSettingsStateSettingChangedListener _ARCommandPowerupPilotingSettingsStateSettingChangedListener_PARAM) {
        _decoder.setPowerupPilotingSettingsStateSettingChangedListener(_ARCommandPowerupPilotingSettingsStateSettingChangedListener_PARAM);
    }

    public static void setPowerupMediaRecordStatePictureStateChangedV2Listener(ARCommandPowerupMediaRecordStatePictureStateChangedV2Listener _ARCommandPowerupMediaRecordStatePictureStateChangedV2Listener_PARAM) {
        _decoder.setPowerupMediaRecordStatePictureStateChangedV2Listener(_ARCommandPowerupMediaRecordStatePictureStateChangedV2Listener_PARAM);
    }

    public static void setPowerupMediaRecordStateVideoStateChangedV2Listener(ARCommandPowerupMediaRecordStateVideoStateChangedV2Listener _ARCommandPowerupMediaRecordStateVideoStateChangedV2Listener_PARAM) {
        _decoder.setPowerupMediaRecordStateVideoStateChangedV2Listener(_ARCommandPowerupMediaRecordStateVideoStateChangedV2Listener_PARAM);
    }

    public static void setPowerupMediaRecordEventPictureEventChangedListener(ARCommandPowerupMediaRecordEventPictureEventChangedListener _ARCommandPowerupMediaRecordEventPictureEventChangedListener_PARAM) {
        _decoder.setPowerupMediaRecordEventPictureEventChangedListener(_ARCommandPowerupMediaRecordEventPictureEventChangedListener_PARAM);
    }

    public static void setPowerupMediaRecordEventVideoEventChangedListener(ARCommandPowerupMediaRecordEventVideoEventChangedListener _ARCommandPowerupMediaRecordEventVideoEventChangedListener_PARAM) {
        _decoder.setPowerupMediaRecordEventVideoEventChangedListener(_ARCommandPowerupMediaRecordEventVideoEventChangedListener_PARAM);
    }

    public static void setPowerupNetworkSettingsStateWifiSelectionChangedListener(ARCommandPowerupNetworkSettingsStateWifiSelectionChangedListener _ARCommandPowerupNetworkSettingsStateWifiSelectionChangedListener_PARAM) {
        _decoder.setPowerupNetworkSettingsStateWifiSelectionChangedListener(_ARCommandPowerupNetworkSettingsStateWifiSelectionChangedListener_PARAM);
    }

    public static void setPowerupNetworkStateWifiScanListChangedListener(ARCommandPowerupNetworkStateWifiScanListChangedListener _ARCommandPowerupNetworkStateWifiScanListChangedListener_PARAM) {
        _decoder.setPowerupNetworkStateWifiScanListChangedListener(_ARCommandPowerupNetworkStateWifiScanListChangedListener_PARAM);
    }

    public static void setPowerupNetworkStateAllWifiScanChangedListener(ARCommandPowerupNetworkStateAllWifiScanChangedListener _ARCommandPowerupNetworkStateAllWifiScanChangedListener_PARAM) {
        _decoder.setPowerupNetworkStateAllWifiScanChangedListener(_ARCommandPowerupNetworkStateAllWifiScanChangedListener_PARAM);
    }

    public static void setPowerupNetworkStateWifiAuthChannelListChangedListener(ARCommandPowerupNetworkStateWifiAuthChannelListChangedListener _ARCommandPowerupNetworkStateWifiAuthChannelListChangedListener_PARAM) {
        _decoder.setPowerupNetworkStateWifiAuthChannelListChangedListener(_ARCommandPowerupNetworkStateWifiAuthChannelListChangedListener_PARAM);
    }

    public static void setPowerupNetworkStateAllWifiAuthChannelChangedListener(ARCommandPowerupNetworkStateAllWifiAuthChannelChangedListener _ARCommandPowerupNetworkStateAllWifiAuthChannelChangedListener_PARAM) {
        _decoder.setPowerupNetworkStateAllWifiAuthChannelChangedListener(_ARCommandPowerupNetworkStateAllWifiAuthChannelChangedListener_PARAM);
    }

    public static void setPowerupNetworkStateLinkQualityChangedListener(ARCommandPowerupNetworkStateLinkQualityChangedListener _ARCommandPowerupNetworkStateLinkQualityChangedListener_PARAM) {
        _decoder.setPowerupNetworkStateLinkQualityChangedListener(_ARCommandPowerupNetworkStateLinkQualityChangedListener_PARAM);
    }

    public static void setPowerupMediaStreamingStateVideoEnableChangedListener(ARCommandPowerupMediaStreamingStateVideoEnableChangedListener _ARCommandPowerupMediaStreamingStateVideoEnableChangedListener_PARAM) {
        _decoder.setPowerupMediaStreamingStateVideoEnableChangedListener(_ARCommandPowerupMediaStreamingStateVideoEnableChangedListener_PARAM);
    }

    public static void setPowerupVideoSettingsStateAutorecordChangedListener(ARCommandPowerupVideoSettingsStateAutorecordChangedListener _ARCommandPowerupVideoSettingsStateAutorecordChangedListener_PARAM) {
        _decoder.setPowerupVideoSettingsStateAutorecordChangedListener(_ARCommandPowerupVideoSettingsStateAutorecordChangedListener_PARAM);
    }

    public static void setPowerupVideoSettingsStateVideoModeChangedListener(ARCommandPowerupVideoSettingsStateVideoModeChangedListener _ARCommandPowerupVideoSettingsStateVideoModeChangedListener_PARAM) {
        _decoder.setPowerupVideoSettingsStateVideoModeChangedListener(_ARCommandPowerupVideoSettingsStateVideoModeChangedListener_PARAM);
    }

    public static void setPowerupSoundsStateBuzzChangedListener(ARCommandPowerupSoundsStateBuzzChangedListener _ARCommandPowerupSoundsStateBuzzChangedListener_PARAM) {
        _decoder.setPowerupSoundsStateBuzzChangedListener(_ARCommandPowerupSoundsStateBuzzChangedListener_PARAM);
    }

    public static void setRcMonitorChannelsListener(ARCommandRcMonitorChannelsListener _ARCommandRcMonitorChannelsListener_PARAM) {
        _decoder.setRcMonitorChannelsListener(_ARCommandRcMonitorChannelsListener_PARAM);
    }

    public static void setRcStartCalibrationListener(ARCommandRcStartCalibrationListener _ARCommandRcStartCalibrationListener_PARAM) {
        _decoder.setRcStartCalibrationListener(_ARCommandRcStartCalibrationListener_PARAM);
    }

    public static void setRcInvertChannelListener(ARCommandRcInvertChannelListener _ARCommandRcInvertChannelListener_PARAM) {
        _decoder.setRcInvertChannelListener(_ARCommandRcInvertChannelListener_PARAM);
    }

    public static void setRcAbortCalibrationListener(ARCommandRcAbortCalibrationListener _ARCommandRcAbortCalibrationListener_PARAM) {
        _decoder.setRcAbortCalibrationListener(_ARCommandRcAbortCalibrationListener_PARAM);
    }

    public static void setRcResetCalibrationListener(ARCommandRcResetCalibrationListener _ARCommandRcResetCalibrationListener_PARAM) {
        _decoder.setRcResetCalibrationListener(_ARCommandRcResetCalibrationListener_PARAM);
    }

    public static void setRcEnableReceiverListener(ARCommandRcEnableReceiverListener _ARCommandRcEnableReceiverListener_PARAM) {
        _decoder.setRcEnableReceiverListener(_ARCommandRcEnableReceiverListener_PARAM);
    }

    public static void setRcReceiverStateListener(ARCommandRcReceiverStateListener _ARCommandRcReceiverStateListener_PARAM) {
        _decoder.setRcReceiverStateListener(_ARCommandRcReceiverStateListener_PARAM);
    }

    public static void setRcChannelsMonitorStateListener(ARCommandRcChannelsMonitorStateListener _ARCommandRcChannelsMonitorStateListener_PARAM) {
        _decoder.setRcChannelsMonitorStateListener(_ARCommandRcChannelsMonitorStateListener_PARAM);
    }

    public static void setRcChannelValueListener(ARCommandRcChannelValueListener _ARCommandRcChannelValueListener_PARAM) {
        _decoder.setRcChannelValueListener(_ARCommandRcChannelValueListener_PARAM);
    }

    public static void setRcCalibrationStateListener(ARCommandRcCalibrationStateListener _ARCommandRcCalibrationStateListener_PARAM) {
        _decoder.setRcCalibrationStateListener(_ARCommandRcCalibrationStateListener_PARAM);
    }

    public static void setRcChannelActionItemListener(ARCommandRcChannelActionItemListener _ARCommandRcChannelActionItemListener_PARAM) {
        _decoder.setRcChannelActionItemListener(_ARCommandRcChannelActionItemListener_PARAM);
    }

    public static void setSkyControllerWifiRequestWifiListListener(ARCommandSkyControllerWifiRequestWifiListListener _ARCommandSkyControllerWifiRequestWifiListListener_PARAM) {
        _decoder.setSkyControllerWifiRequestWifiListListener(_ARCommandSkyControllerWifiRequestWifiListListener_PARAM);
    }

    public static void setSkyControllerWifiRequestCurrentWifiListener(ARCommandSkyControllerWifiRequestCurrentWifiListener _ARCommandSkyControllerWifiRequestCurrentWifiListener_PARAM) {
        _decoder.setSkyControllerWifiRequestCurrentWifiListener(_ARCommandSkyControllerWifiRequestCurrentWifiListener_PARAM);
    }

    public static void setSkyControllerWifiConnectToWifiListener(ARCommandSkyControllerWifiConnectToWifiListener _ARCommandSkyControllerWifiConnectToWifiListener_PARAM) {
        _decoder.setSkyControllerWifiConnectToWifiListener(_ARCommandSkyControllerWifiConnectToWifiListener_PARAM);
    }

    public static void setSkyControllerWifiForgetWifiListener(ARCommandSkyControllerWifiForgetWifiListener _ARCommandSkyControllerWifiForgetWifiListener_PARAM) {
        _decoder.setSkyControllerWifiForgetWifiListener(_ARCommandSkyControllerWifiForgetWifiListener_PARAM);
    }

    public static void setSkyControllerWifiWifiAuthChannelListener(ARCommandSkyControllerWifiWifiAuthChannelListener _ARCommandSkyControllerWifiWifiAuthChannelListener_PARAM) {
        _decoder.setSkyControllerWifiWifiAuthChannelListener(_ARCommandSkyControllerWifiWifiAuthChannelListener_PARAM);
    }

    public static void setSkyControllerDeviceRequestDeviceListListener(ARCommandSkyControllerDeviceRequestDeviceListListener _ARCommandSkyControllerDeviceRequestDeviceListListener_PARAM) {
        _decoder.setSkyControllerDeviceRequestDeviceListListener(_ARCommandSkyControllerDeviceRequestDeviceListListener_PARAM);
    }

    public static void setSkyControllerDeviceRequestCurrentDeviceListener(ARCommandSkyControllerDeviceRequestCurrentDeviceListener _ARCommandSkyControllerDeviceRequestCurrentDeviceListener_PARAM) {
        _decoder.setSkyControllerDeviceRequestCurrentDeviceListener(_ARCommandSkyControllerDeviceRequestCurrentDeviceListener_PARAM);
    }

    public static void setSkyControllerDeviceConnectToDeviceListener(ARCommandSkyControllerDeviceConnectToDeviceListener _ARCommandSkyControllerDeviceConnectToDeviceListener_PARAM) {
        _decoder.setSkyControllerDeviceConnectToDeviceListener(_ARCommandSkyControllerDeviceConnectToDeviceListener_PARAM);
    }

    public static void setSkyControllerSettingsAllSettingsListener(ARCommandSkyControllerSettingsAllSettingsListener _ARCommandSkyControllerSettingsAllSettingsListener_PARAM) {
        _decoder.setSkyControllerSettingsAllSettingsListener(_ARCommandSkyControllerSettingsAllSettingsListener_PARAM);
    }

    public static void setSkyControllerSettingsResetListener(ARCommandSkyControllerSettingsResetListener _ARCommandSkyControllerSettingsResetListener_PARAM) {
        _decoder.setSkyControllerSettingsResetListener(_ARCommandSkyControllerSettingsResetListener_PARAM);
    }

    public static void setSkyControllerCommonAllStatesListener(ARCommandSkyControllerCommonAllStatesListener _ARCommandSkyControllerCommonAllStatesListener_PARAM) {
        _decoder.setSkyControllerCommonAllStatesListener(_ARCommandSkyControllerCommonAllStatesListener_PARAM);
    }

    public static void setSkyControllerAccessPointSettingsAccessPointSSIDListener(ARCommandSkyControllerAccessPointSettingsAccessPointSSIDListener _ARCommandSkyControllerAccessPointSettingsAccessPointSSIDListener_PARAM) {
        _decoder.setSkyControllerAccessPointSettingsAccessPointSSIDListener(_ARCommandSkyControllerAccessPointSettingsAccessPointSSIDListener_PARAM);
    }

    public static void setSkyControllerAccessPointSettingsAccessPointChannelListener(C1538x36391db7 _ARCommandSkyControllerAccessPointSettingsAccessPointChannelListener_PARAM) {
        _decoder.setSkyControllerAccessPointSettingsAccessPointChannelListener(_ARCommandSkyControllerAccessPointSettingsAccessPointChannelListener_PARAM);
    }

    public static void setSkyControllerAccessPointSettingsWifiSelectionListener(ARCommandSkyControllerAccessPointSettingsWifiSelectionListener _ARCommandSkyControllerAccessPointSettingsWifiSelectionListener_PARAM) {
        _decoder.setSkyControllerAccessPointSettingsWifiSelectionListener(_ARCommandSkyControllerAccessPointSettingsWifiSelectionListener_PARAM);
    }

    public static void setSkyControllerAccessPointSettingsWifiSecurityListener(ARCommandSkyControllerAccessPointSettingsWifiSecurityListener _ARCommandSkyControllerAccessPointSettingsWifiSecurityListener_PARAM) {
        _decoder.setSkyControllerAccessPointSettingsWifiSecurityListener(_ARCommandSkyControllerAccessPointSettingsWifiSecurityListener_PARAM);
    }

    public static void setSkyControllerCameraResetOrientationListener(ARCommandSkyControllerCameraResetOrientationListener _ARCommandSkyControllerCameraResetOrientationListener_PARAM) {
        _decoder.setSkyControllerCameraResetOrientationListener(_ARCommandSkyControllerCameraResetOrientationListener_PARAM);
    }

    public static void setSkyControllerGamepadInfosGetGamepadControlsListener(ARCommandSkyControllerGamepadInfosGetGamepadControlsListener _ARCommandSkyControllerGamepadInfosGetGamepadControlsListener_PARAM) {
        _decoder.setSkyControllerGamepadInfosGetGamepadControlsListener(_ARCommandSkyControllerGamepadInfosGetGamepadControlsListener_PARAM);
    }

    public static void setSkyControllerButtonMappingsGetCurrentButtonMappingsListener(C1551x39e41b48 _ARCommandSkyControllerButtonMappingsGetCurrentButtonMappingsListener_PARAM) {
        _decoder.setSkyControllerButtonMappingsGetCurrentButtonMappingsListener(_ARCommandSkyControllerButtonMappingsGetCurrentButtonMappingsListener_PARAM);
    }

    public static void setSkyControllerButtonMappingsGetAvailableButtonMappingsListener(C1550xcbb82398 _ARCommandSkyControllerButtonMappingsGetAvailableButtonMappingsListener_PARAM) {
        _decoder.setSkyControllerButtonMappingsGetAvailableButtonMappingsListener(_ARCommandSkyControllerButtonMappingsGetAvailableButtonMappingsListener_PARAM);
    }

    public static void setSkyControllerButtonMappingsSetButtonMappingListener(ARCommandSkyControllerButtonMappingsSetButtonMappingListener _ARCommandSkyControllerButtonMappingsSetButtonMappingListener_PARAM) {
        _decoder.setSkyControllerButtonMappingsSetButtonMappingListener(_ARCommandSkyControllerButtonMappingsSetButtonMappingListener_PARAM);
    }

    public static void setSkyControllerButtonMappingsDefaultButtonMappingListener(ARCommandSkyControllerButtonMappingsDefaultButtonMappingListener _ARCommandSkyControllerButtonMappingsDefaultButtonMappingListener_PARAM) {
        _decoder.setSkyControllerButtonMappingsDefaultButtonMappingListener(_ARCommandSkyControllerButtonMappingsDefaultButtonMappingListener_PARAM);
    }

    public static void setSkyControllerAxisMappingsGetCurrentAxisMappingsListener(ARCommandSkyControllerAxisMappingsGetCurrentAxisMappingsListener _ARCommandSkyControllerAxisMappingsGetCurrentAxisMappingsListener_PARAM) {
        _decoder.setSkyControllerAxisMappingsGetCurrentAxisMappingsListener(_ARCommandSkyControllerAxisMappingsGetCurrentAxisMappingsListener_PARAM);
    }

    public static void setSkyControllerAxisMappingsGetAvailableAxisMappingsListener(C1545x69a7bf76 _ARCommandSkyControllerAxisMappingsGetAvailableAxisMappingsListener_PARAM) {
        _decoder.setSkyControllerAxisMappingsGetAvailableAxisMappingsListener(_ARCommandSkyControllerAxisMappingsGetAvailableAxisMappingsListener_PARAM);
    }

    public static void setSkyControllerAxisMappingsSetAxisMappingListener(ARCommandSkyControllerAxisMappingsSetAxisMappingListener _ARCommandSkyControllerAxisMappingsSetAxisMappingListener_PARAM) {
        _decoder.setSkyControllerAxisMappingsSetAxisMappingListener(_ARCommandSkyControllerAxisMappingsSetAxisMappingListener_PARAM);
    }

    public static void setSkyControllerAxisMappingsDefaultAxisMappingListener(ARCommandSkyControllerAxisMappingsDefaultAxisMappingListener _ARCommandSkyControllerAxisMappingsDefaultAxisMappingListener_PARAM) {
        _decoder.setSkyControllerAxisMappingsDefaultAxisMappingListener(_ARCommandSkyControllerAxisMappingsDefaultAxisMappingListener_PARAM);
    }

    public static void setSkyControllerAxisFiltersGetCurrentAxisFiltersListener(ARCommandSkyControllerAxisFiltersGetCurrentAxisFiltersListener _ARCommandSkyControllerAxisFiltersGetCurrentAxisFiltersListener_PARAM) {
        _decoder.setSkyControllerAxisFiltersGetCurrentAxisFiltersListener(_ARCommandSkyControllerAxisFiltersGetCurrentAxisFiltersListener_PARAM);
    }

    public static void setSkyControllerAxisFiltersGetPresetAxisFiltersListener(ARCommandSkyControllerAxisFiltersGetPresetAxisFiltersListener _ARCommandSkyControllerAxisFiltersGetPresetAxisFiltersListener_PARAM) {
        _decoder.setSkyControllerAxisFiltersGetPresetAxisFiltersListener(_ARCommandSkyControllerAxisFiltersGetPresetAxisFiltersListener_PARAM);
    }

    public static void setSkyControllerAxisFiltersSetAxisFilterListener(ARCommandSkyControllerAxisFiltersSetAxisFilterListener _ARCommandSkyControllerAxisFiltersSetAxisFilterListener_PARAM) {
        _decoder.setSkyControllerAxisFiltersSetAxisFilterListener(_ARCommandSkyControllerAxisFiltersSetAxisFilterListener_PARAM);
    }

    public static void setSkyControllerAxisFiltersDefaultAxisFiltersListener(ARCommandSkyControllerAxisFiltersDefaultAxisFiltersListener _ARCommandSkyControllerAxisFiltersDefaultAxisFiltersListener_PARAM) {
        _decoder.setSkyControllerAxisFiltersDefaultAxisFiltersListener(_ARCommandSkyControllerAxisFiltersDefaultAxisFiltersListener_PARAM);
    }

    public static void setSkyControllerCoPilotingSetPilotingSourceListener(ARCommandSkyControllerCoPilotingSetPilotingSourceListener _ARCommandSkyControllerCoPilotingSetPilotingSourceListener_PARAM) {
        _decoder.setSkyControllerCoPilotingSetPilotingSourceListener(_ARCommandSkyControllerCoPilotingSetPilotingSourceListener_PARAM);
    }

    /* renamed from: setSkyControllerCalibrationEnableMagnetoCalibrationQualityUpdatesListener */
    public static void m606x3bca9676(C1556x940125ee _ARCommandSkyControllerCalibrationEnableMagnetoCalibrationQualityUpdatesListener_PARAM) {
        _decoder.m89x3bca9676(_ARCommandSkyControllerCalibrationEnableMagnetoCalibrationQualityUpdatesListener_PARAM);
    }

    public static void setSkyControllerFactoryResetListener(ARCommandSkyControllerFactoryResetListener _ARCommandSkyControllerFactoryResetListener_PARAM) {
        _decoder.setSkyControllerFactoryResetListener(_ARCommandSkyControllerFactoryResetListener_PARAM);
    }

    public static void setSkyControllerWifiStateWifiListListener(ARCommandSkyControllerWifiStateWifiListListener _ARCommandSkyControllerWifiStateWifiListListener_PARAM) {
        _decoder.setSkyControllerWifiStateWifiListListener(_ARCommandSkyControllerWifiStateWifiListListener_PARAM);
    }

    public static void setSkyControllerWifiStateConnexionChangedListener(ARCommandSkyControllerWifiStateConnexionChangedListener _ARCommandSkyControllerWifiStateConnexionChangedListener_PARAM) {
        _decoder.setSkyControllerWifiStateConnexionChangedListener(_ARCommandSkyControllerWifiStateConnexionChangedListener_PARAM);
    }

    public static void setSkyControllerWifiStateWifiAuthChannelListChangedListener(C1561x9d26f8fd _ARCommandSkyControllerWifiStateWifiAuthChannelListChangedListener_PARAM) {
        _decoder.setSkyControllerWifiStateWifiAuthChannelListChangedListener(_ARCommandSkyControllerWifiStateWifiAuthChannelListChangedListener_PARAM);
    }

    public static void setSkyControllerWifiStateAllWifiAuthChannelChangedListener(ARCommandSkyControllerWifiStateAllWifiAuthChannelChangedListener _ARCommandSkyControllerWifiStateAllWifiAuthChannelChangedListener_PARAM) {
        _decoder.setSkyControllerWifiStateAllWifiAuthChannelChangedListener(_ARCommandSkyControllerWifiStateAllWifiAuthChannelChangedListener_PARAM);
    }

    public static void setSkyControllerWifiStateWifiSignalChangedListener(ARCommandSkyControllerWifiStateWifiSignalChangedListener _ARCommandSkyControllerWifiStateWifiSignalChangedListener_PARAM) {
        _decoder.setSkyControllerWifiStateWifiSignalChangedListener(_ARCommandSkyControllerWifiStateWifiSignalChangedListener_PARAM);
    }

    public static void setSkyControllerWifiStateWifiAuthChannelListChangedV2Listener(C1562x9e7d4159 _ARCommandSkyControllerWifiStateWifiAuthChannelListChangedV2Listener_PARAM) {
        _decoder.setSkyControllerWifiStateWifiAuthChannelListChangedV2Listener(_ARCommandSkyControllerWifiStateWifiAuthChannelListChangedV2Listener_PARAM);
    }

    public static void setSkyControllerWifiStateWifiCountryChangedListener(ARCommandSkyControllerWifiStateWifiCountryChangedListener _ARCommandSkyControllerWifiStateWifiCountryChangedListener_PARAM) {
        _decoder.setSkyControllerWifiStateWifiCountryChangedListener(_ARCommandSkyControllerWifiStateWifiCountryChangedListener_PARAM);
    }

    public static void setSkyControllerWifiStateWifiEnvironmentChangedListener(ARCommandSkyControllerWifiStateWifiEnvironmentChangedListener _ARCommandSkyControllerWifiStateWifiEnvironmentChangedListener_PARAM) {
        _decoder.setSkyControllerWifiStateWifiEnvironmentChangedListener(_ARCommandSkyControllerWifiStateWifiEnvironmentChangedListener_PARAM);
    }

    public static void setSkyControllerDeviceStateDeviceListListener(ARCommandSkyControllerDeviceStateDeviceListListener _ARCommandSkyControllerDeviceStateDeviceListListener_PARAM) {
        _decoder.setSkyControllerDeviceStateDeviceListListener(_ARCommandSkyControllerDeviceStateDeviceListListener_PARAM);
    }

    public static void setSkyControllerDeviceStateConnexionChangedListener(ARCommandSkyControllerDeviceStateConnexionChangedListener _ARCommandSkyControllerDeviceStateConnexionChangedListener_PARAM) {
        _decoder.setSkyControllerDeviceStateConnexionChangedListener(_ARCommandSkyControllerDeviceStateConnexionChangedListener_PARAM);
    }

    public static void setSkyControllerSettingsStateAllSettingsChangedListener(ARCommandSkyControllerSettingsStateAllSettingsChangedListener _ARCommandSkyControllerSettingsStateAllSettingsChangedListener_PARAM) {
        _decoder.setSkyControllerSettingsStateAllSettingsChangedListener(_ARCommandSkyControllerSettingsStateAllSettingsChangedListener_PARAM);
    }

    public static void setSkyControllerSettingsStateResetChangedListener(ARCommandSkyControllerSettingsStateResetChangedListener _ARCommandSkyControllerSettingsStateResetChangedListener_PARAM) {
        _decoder.setSkyControllerSettingsStateResetChangedListener(_ARCommandSkyControllerSettingsStateResetChangedListener_PARAM);
    }

    public static void setSkyControllerSettingsStateProductSerialChangedListener(ARCommandSkyControllerSettingsStateProductSerialChangedListener _ARCommandSkyControllerSettingsStateProductSerialChangedListener_PARAM) {
        _decoder.setSkyControllerSettingsStateProductSerialChangedListener(_ARCommandSkyControllerSettingsStateProductSerialChangedListener_PARAM);
    }

    public static void setSkyControllerSettingsStateProductVariantChangedListener(ARCommandSkyControllerSettingsStateProductVariantChangedListener _ARCommandSkyControllerSettingsStateProductVariantChangedListener_PARAM) {
        _decoder.setSkyControllerSettingsStateProductVariantChangedListener(_ARCommandSkyControllerSettingsStateProductVariantChangedListener_PARAM);
    }

    public static void setSkyControllerSettingsStateProductVersionChangedListener(ARCommandSkyControllerSettingsStateProductVersionChangedListener _ARCommandSkyControllerSettingsStateProductVersionChangedListener_PARAM) {
        _decoder.setSkyControllerSettingsStateProductVersionChangedListener(_ARCommandSkyControllerSettingsStateProductVersionChangedListener_PARAM);
    }

    public static void setSkyControllerCommonStateAllStatesChangedListener(ARCommandSkyControllerCommonStateAllStatesChangedListener _ARCommandSkyControllerCommonStateAllStatesChangedListener_PARAM) {
        _decoder.setSkyControllerCommonStateAllStatesChangedListener(_ARCommandSkyControllerCommonStateAllStatesChangedListener_PARAM);
    }

    public static void setSkyControllerSkyControllerStateBatteryChangedListener(ARCommandSkyControllerSkyControllerStateBatteryChangedListener _ARCommandSkyControllerSkyControllerStateBatteryChangedListener_PARAM) {
        _decoder.setSkyControllerSkyControllerStateBatteryChangedListener(_ARCommandSkyControllerSkyControllerStateBatteryChangedListener_PARAM);
    }

    public static void setSkyControllerSkyControllerStateGpsFixChangedListener(ARCommandSkyControllerSkyControllerStateGpsFixChangedListener _ARCommandSkyControllerSkyControllerStateGpsFixChangedListener_PARAM) {
        _decoder.setSkyControllerSkyControllerStateGpsFixChangedListener(_ARCommandSkyControllerSkyControllerStateGpsFixChangedListener_PARAM);
    }

    public static void setSkyControllerSkyControllerStateGpsPositionChangedListener(C1560x246030ac _ARCommandSkyControllerSkyControllerStateGpsPositionChangedListener_PARAM) {
        _decoder.setSkyControllerSkyControllerStateGpsPositionChangedListener(_ARCommandSkyControllerSkyControllerStateGpsPositionChangedListener_PARAM);
    }

    public static void setSkyControllerSkyControllerStateBatteryStateListener(ARCommandSkyControllerSkyControllerStateBatteryStateListener _ARCommandSkyControllerSkyControllerStateBatteryStateListener_PARAM) {
        _decoder.setSkyControllerSkyControllerStateBatteryStateListener(_ARCommandSkyControllerSkyControllerStateBatteryStateListener_PARAM);
    }

    public static void setSkyControllerSkyControllerStateAttitudeChangedListener(ARCommandSkyControllerSkyControllerStateAttitudeChangedListener _ARCommandSkyControllerSkyControllerStateAttitudeChangedListener_PARAM) {
        _decoder.setSkyControllerSkyControllerStateAttitudeChangedListener(_ARCommandSkyControllerSkyControllerStateAttitudeChangedListener_PARAM);
    }

    /* renamed from: setSkyControllerAccessPointSettingsStateAccessPointSSIDChangedListener */
    public static void m598xb68b585e(C1540x2e93bbe6 _ARCommandSkyControllerAccessPointSettingsStateAccessPointSSIDChangedListener_PARAM) {
        _decoder.m81xb68b585e(_ARCommandSkyControllerAccessPointSettingsStateAccessPointSSIDChangedListener_PARAM);
    }

    /* renamed from: setSkyControllerAccessPointSettingsStateAccessPointChannelChangedListener */
    public static void m597xec7810b4(C1539x44aea02c _ARCommandSkyControllerAccessPointSettingsStateAccessPointChannelChangedListener_PARAM) {
        _decoder.m80xec7810b4(_ARCommandSkyControllerAccessPointSettingsStateAccessPointChannelChangedListener_PARAM);
    }

    /* renamed from: setSkyControllerAccessPointSettingsStateWifiSelectionChangedListener */
    public static void m600x9e9847ae(C1542xbef3ed36 _ARCommandSkyControllerAccessPointSettingsStateWifiSelectionChangedListener_PARAM) {
        _decoder.m83x9e9847ae(_ARCommandSkyControllerAccessPointSettingsStateWifiSelectionChangedListener_PARAM);
    }

    /* renamed from: setSkyControllerAccessPointSettingsStateWifiSecurityChangedListener */
    public static void m599x36f28a96(C1541x6148140e _ARCommandSkyControllerAccessPointSettingsStateWifiSecurityChangedListener_PARAM) {
        _decoder.m82x36f28a96(_ARCommandSkyControllerAccessPointSettingsStateWifiSecurityChangedListener_PARAM);
    }

    public static void setSkyControllerGamepadInfosStateGamepadControlListener(ARCommandSkyControllerGamepadInfosStateGamepadControlListener _ARCommandSkyControllerGamepadInfosStateGamepadControlListener_PARAM) {
        _decoder.setSkyControllerGamepadInfosStateGamepadControlListener(_ARCommandSkyControllerGamepadInfosStateGamepadControlListener_PARAM);
    }

    public static void setSkyControllerGamepadInfosStateAllGamepadControlsSentListener(C1559x329e5e2c _ARCommandSkyControllerGamepadInfosStateAllGamepadControlsSentListener_PARAM) {
        _decoder.setSkyControllerGamepadInfosStateAllGamepadControlsSentListener(_ARCommandSkyControllerGamepadInfosStateAllGamepadControlsSentListener_PARAM);
    }

    public static void setSkyControllerButtonMappingsStateCurrentButtonMappingsListener(C1555xd36d956d _ARCommandSkyControllerButtonMappingsStateCurrentButtonMappingsListener_PARAM) {
        _decoder.setSkyControllerButtonMappingsStateCurrentButtonMappingsListener(_ARCommandSkyControllerButtonMappingsStateCurrentButtonMappingsListener_PARAM);
    }

    /* renamed from: setSkyControllerButtonMappingsStateAllCurrentButtonMappingsSentListener */
    public static void m604x19aa5dda(C1553xa2ae6b52 _ARCommandSkyControllerButtonMappingsStateAllCurrentButtonMappingsSentListener_PARAM) {
        _decoder.m87x19aa5dda(_ARCommandSkyControllerButtonMappingsStateAllCurrentButtonMappingsSentListener_PARAM);
    }

    /* renamed from: setSkyControllerButtonMappingsStateAvailableButtonMappingsListener */
    public static void m605x403440f5(C1554x28cba87d _ARCommandSkyControllerButtonMappingsStateAvailableButtonMappingsListener_PARAM) {
        _decoder.m88x403440f5(_ARCommandSkyControllerButtonMappingsStateAvailableButtonMappingsListener_PARAM);
    }

    /* renamed from: setSkyControllerButtonMappingsStateAllAvailableButtonsMappingsSentListener */
    public static void m603x1a42f18b(C1552xc8de5113 _ARCommandSkyControllerButtonMappingsStateAllAvailableButtonsMappingsSentListener_PARAM) {
        _decoder.m86x1a42f18b(_ARCommandSkyControllerButtonMappingsStateAllAvailableButtonsMappingsSentListener_PARAM);
    }

    public static void setSkyControllerAxisMappingsStateCurrentAxisMappingsListener(C1549x1249a28b _ARCommandSkyControllerAxisMappingsStateCurrentAxisMappingsListener_PARAM) {
        _decoder.setSkyControllerAxisMappingsStateCurrentAxisMappingsListener(_ARCommandSkyControllerAxisMappingsStateCurrentAxisMappingsListener_PARAM);
    }

    /* renamed from: setSkyControllerAxisMappingsStateAllCurrentAxisMappingsSentListener */
    public static void m602xbde72f7a(C1547xe83cb8f2 _ARCommandSkyControllerAxisMappingsStateAllCurrentAxisMappingsSentListener_PARAM) {
        _decoder.m85xbde72f7a(_ARCommandSkyControllerAxisMappingsStateAllCurrentAxisMappingsSentListener_PARAM);
    }

    public static void setSkyControllerAxisMappingsStateAvailableAxisMappingsListener(C1548x4b0b69db _ARCommandSkyControllerAxisMappingsStateAvailableAxisMappingsListener_PARAM) {
        _decoder.setSkyControllerAxisMappingsStateAvailableAxisMappingsListener(_ARCommandSkyControllerAxisMappingsStateAvailableAxisMappingsListener_PARAM);
    }

    /* renamed from: setSkyControllerAxisMappingsStateAllAvailableAxisMappingsSentListener */
    public static void m601x50da7d0a(C1546x3bf38882 _ARCommandSkyControllerAxisMappingsStateAllAvailableAxisMappingsSentListener_PARAM) {
        _decoder.m84x50da7d0a(_ARCommandSkyControllerAxisMappingsStateAllAvailableAxisMappingsSentListener_PARAM);
    }

    public static void setSkyControllerAxisFiltersStateCurrentAxisFiltersListener(ARCommandSkyControllerAxisFiltersStateCurrentAxisFiltersListener _ARCommandSkyControllerAxisFiltersStateCurrentAxisFiltersListener_PARAM) {
        _decoder.setSkyControllerAxisFiltersStateCurrentAxisFiltersListener(_ARCommandSkyControllerAxisFiltersStateCurrentAxisFiltersListener_PARAM);
    }

    public static void setSkyControllerAxisFiltersStateAllCurrentFiltersSentListener(C1543xed4a78d5 _ARCommandSkyControllerAxisFiltersStateAllCurrentFiltersSentListener_PARAM) {
        _decoder.setSkyControllerAxisFiltersStateAllCurrentFiltersSentListener(_ARCommandSkyControllerAxisFiltersStateAllCurrentFiltersSentListener_PARAM);
    }

    public static void setSkyControllerAxisFiltersStatePresetAxisFiltersListener(ARCommandSkyControllerAxisFiltersStatePresetAxisFiltersListener _ARCommandSkyControllerAxisFiltersStatePresetAxisFiltersListener_PARAM) {
        _decoder.setSkyControllerAxisFiltersStatePresetAxisFiltersListener(_ARCommandSkyControllerAxisFiltersStatePresetAxisFiltersListener_PARAM);
    }

    public static void setSkyControllerAxisFiltersStateAllPresetFiltersSentListener(C1544x5c379ac1 _ARCommandSkyControllerAxisFiltersStateAllPresetFiltersSentListener_PARAM) {
        _decoder.setSkyControllerAxisFiltersStateAllPresetFiltersSentListener(_ARCommandSkyControllerAxisFiltersStateAllPresetFiltersSentListener_PARAM);
    }

    public static void setSkyControllerCoPilotingStatePilotingSourceListener(ARCommandSkyControllerCoPilotingStatePilotingSourceListener _ARCommandSkyControllerCoPilotingStatePilotingSourceListener_PARAM) {
        _decoder.setSkyControllerCoPilotingStatePilotingSourceListener(_ARCommandSkyControllerCoPilotingStatePilotingSourceListener_PARAM);
    }

    public static void setSkyControllerCalibrationStateMagnetoCalibrationStateListener(C1558x351361d6 _ARCommandSkyControllerCalibrationStateMagnetoCalibrationStateListener_PARAM) {
        _decoder.setSkyControllerCalibrationStateMagnetoCalibrationStateListener(_ARCommandSkyControllerCalibrationStateMagnetoCalibrationStateListener_PARAM);
    }

    /* renamed from: setSkyControllerCalibrationStateMagnetoCalibrationQualityUpdatesStateListener */
    public static void m607xb12a5b93(C1557xe41c6f0b _ARCommandSkyControllerCalibrationStateMagnetoCalibrationQualityUpdatesStateListener_PARAM) {
        _decoder.m90xb12a5b93(_ARCommandSkyControllerCalibrationStateMagnetoCalibrationQualityUpdatesStateListener_PARAM);
    }

    public static void setSkyControllerButtonEventsSettingsListener(ARCommandSkyControllerButtonEventsSettingsListener _ARCommandSkyControllerButtonEventsSettingsListener_PARAM) {
        _decoder.setSkyControllerButtonEventsSettingsListener(_ARCommandSkyControllerButtonEventsSettingsListener_PARAM);
    }

    public static void setSkyControllerCommonEventStateShutdownListener(ARCommandSkyControllerCommonEventStateShutdownListener _ARCommandSkyControllerCommonEventStateShutdownListener_PARAM) {
        _decoder.setSkyControllerCommonEventStateShutdownListener(_ARCommandSkyControllerCommonEventStateShutdownListener_PARAM);
    }

    public static void setWifiScanListener(ARCommandWifiScanListener _ARCommandWifiScanListener_PARAM) {
        _decoder.setWifiScanListener(_ARCommandWifiScanListener_PARAM);
    }

    public static void setWifiUpdateAuthorizedChannelsListener(ARCommandWifiUpdateAuthorizedChannelsListener _ARCommandWifiUpdateAuthorizedChannelsListener_PARAM) {
        _decoder.setWifiUpdateAuthorizedChannelsListener(_ARCommandWifiUpdateAuthorizedChannelsListener_PARAM);
    }

    public static void setWifiSetApChannelListener(ARCommandWifiSetApChannelListener _ARCommandWifiSetApChannelListener_PARAM) {
        _decoder.setWifiSetApChannelListener(_ARCommandWifiSetApChannelListener_PARAM);
    }

    public static void setWifiSetSecurityListener(ARCommandWifiSetSecurityListener _ARCommandWifiSetSecurityListener_PARAM) {
        _decoder.setWifiSetSecurityListener(_ARCommandWifiSetSecurityListener_PARAM);
    }

    public static void setWifiSetCountryListener(ARCommandWifiSetCountryListener _ARCommandWifiSetCountryListener_PARAM) {
        _decoder.setWifiSetCountryListener(_ARCommandWifiSetCountryListener_PARAM);
    }

    public static void setWifiSetEnvironmentListener(ARCommandWifiSetEnvironmentListener _ARCommandWifiSetEnvironmentListener_PARAM) {
        _decoder.setWifiSetEnvironmentListener(_ARCommandWifiSetEnvironmentListener_PARAM);
    }

    public static void setWifiScannedItemListener(ARCommandWifiScannedItemListener _ARCommandWifiScannedItemListener_PARAM) {
        _decoder.setWifiScannedItemListener(_ARCommandWifiScannedItemListener_PARAM);
    }

    public static void setWifiAuthorizedChannelListener(ARCommandWifiAuthorizedChannelListener _ARCommandWifiAuthorizedChannelListener_PARAM) {
        _decoder.setWifiAuthorizedChannelListener(_ARCommandWifiAuthorizedChannelListener_PARAM);
    }

    public static void setWifiApChannelChangedListener(ARCommandWifiApChannelChangedListener _ARCommandWifiApChannelChangedListener_PARAM) {
        _decoder.setWifiApChannelChangedListener(_ARCommandWifiApChannelChangedListener_PARAM);
    }

    public static void setWifiSecurityChangedListener(ARCommandWifiSecurityChangedListener _ARCommandWifiSecurityChangedListener_PARAM) {
        _decoder.setWifiSecurityChangedListener(_ARCommandWifiSecurityChangedListener_PARAM);
    }

    public static void setWifiCountryChangedListener(ARCommandWifiCountryChangedListener _ARCommandWifiCountryChangedListener_PARAM) {
        _decoder.setWifiCountryChangedListener(_ARCommandWifiCountryChangedListener_PARAM);
    }

    public static void setWifiEnvironmentChangedListener(ARCommandWifiEnvironmentChangedListener _ARCommandWifiEnvironmentChangedListener_PARAM) {
        _decoder.setWifiEnvironmentChangedListener(_ARCommandWifiEnvironmentChangedListener_PARAM);
    }

    public static void setWifiRssiChangedListener(ARCommandWifiRssiChangedListener _ARCommandWifiRssiChangedListener_PARAM) {
        _decoder.setWifiRssiChangedListener(_ARCommandWifiRssiChangedListener_PARAM);
    }

    public static void setWifiSupportedCountriesListener(ARCommandWifiSupportedCountriesListener _ARCommandWifiSupportedCountriesListener_PARAM) {
        _decoder.setWifiSupportedCountriesListener(_ARCommandWifiSupportedCountriesListener_PARAM);
    }
}
