package com.parrot.arsdk.arcommands;

import com.parrot.arsdk.arsal.ARSALPrint;

public class ARCommandsFilter {
    private static final String TAG = ARCommandsFilter.class.getSimpleName();
    private long cFilter;
    private boolean valid;

    private native void nativeDeleteFilter(long j);

    private native int nativeFilterCommand(long j, long j2, int i);

    private native long nativeNewFilter(int i);

    private native int nativeSetARDrone3AccessoryStateBehavior(long j, int i);

    private native int nativeSetARDrone3AccessoryStateConnectedAccessoriesBehavior(long j, int i);

    private native int nativeSetARDrone3AnimationsBehavior(long j, int i);

    private native int nativeSetARDrone3AnimationsFlipBehavior(long j, int i);

    private native int nativeSetARDrone3AntiflickeringBehavior(long j, int i);

    private native int nativeSetARDrone3AntiflickeringElectricFrequencyBehavior(long j, int i);

    private native int nativeSetARDrone3AntiflickeringSetModeBehavior(long j, int i);

    private native int nativeSetARDrone3AntiflickeringStateBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3AntiflickeringStateElectricFrequencyChangedBehavior */
    private native int m91x135403b5(long j, int i);

    private native int nativeSetARDrone3AntiflickeringStateModeChangedBehavior(long j, int i);

    private native int nativeSetARDrone3Behavior(long j, int i);

    private native int nativeSetARDrone3CameraBehavior(long j, int i);

    private native int nativeSetARDrone3CameraOrientationBehavior(long j, int i);

    private native int nativeSetARDrone3CameraOrientationV2Behavior(long j, int i);

    private native int nativeSetARDrone3CameraStateBehavior(long j, int i);

    private native int nativeSetARDrone3CameraStateDefaultCameraOrientationBehavior(long j, int i);

    private native int nativeSetARDrone3CameraStateDefaultCameraOrientationV2Behavior(long j, int i);

    private native int nativeSetARDrone3CameraStateOrientationBehavior(long j, int i);

    private native int nativeSetARDrone3CameraStateOrientationV2Behavior(long j, int i);

    private native int nativeSetARDrone3CameraStateVelocityRangeBehavior(long j, int i);

    private native int nativeSetARDrone3CameraVelocityBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsHomeTypeBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsResetHomeBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsReturnHomeDelayBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsSendControllerGPSBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsSetHomeBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsStateBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsStateGPSFixStateChangedBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsStateGPSUpdateStateChangedBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsStateGeofenceCenterChangedBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsStateHomeChangedBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsStateHomeTypeChangedBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsStateResetHomeChangedBehavior(long j, int i);

    private native int nativeSetARDrone3GPSSettingsStateReturnHomeDelayChangedBehavior(long j, int i);

    private native int nativeSetARDrone3GPSStateBehavior(long j, int i);

    private native int nativeSetARDrone3GPSStateHomeTypeAvailabilityChangedBehavior(long j, int i);

    private native int nativeSetARDrone3GPSStateHomeTypeChosenChangedBehavior(long j, int i);

    private native int nativeSetARDrone3GPSStateNumberOfSatelliteChangedBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordEventBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordEventPictureEventChangedBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordEventVideoEventChangedBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordPictureBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordPictureV2Behavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordStateBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordStatePictureStateChangedBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordStatePictureStateChangedV2Behavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordStateVideoResolutionStateBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordStateVideoStateChangedBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordStateVideoStateChangedV2Behavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordVideoBehavior(long j, int i);

    private native int nativeSetARDrone3MediaRecordVideoV2Behavior(long j, int i);

    private native int nativeSetARDrone3MediaStreamingBehavior(long j, int i);

    private native int nativeSetARDrone3MediaStreamingStateBehavior(long j, int i);

    private native int nativeSetARDrone3MediaStreamingStateVideoEnableChangedBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3MediaStreamingStateVideoStreamModeChangedBehavior */
    private native int m92x9d5317a4(long j, int i);

    private native int nativeSetARDrone3MediaStreamingVideoEnableBehavior(long j, int i);

    private native int nativeSetARDrone3MediaStreamingVideoStreamModeBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkSettingsBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkSettingsStateBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkSettingsStateWifiSecurityBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkSettingsStateWifiSecurityChangedBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3NetworkSettingsStateWifiSelectionChangedBehavior */
    private native int m93xc57dd886(long j, int i);

    private native int nativeSetARDrone3NetworkSettingsWifiSecurityBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkSettingsWifiSelectionBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkStateAllWifiAuthChannelChangedBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkStateAllWifiScanChangedBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkStateBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkStateWifiAuthChannelListChangedBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkStateWifiScanListChangedBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkWifiAuthChannelBehavior(long j, int i);

    private native int nativeSetARDrone3NetworkWifiScanBehavior(long j, int i);

    private native int nativeSetARDrone3PROStateBehavior(long j, int i);

    private native int nativeSetARDrone3PROStateFeaturesBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3PictureSettingsAutoWhiteBalanceSelectionBehavior */
    private native int m94xfb563eb2(long j, int i);

    private native int nativeSetARDrone3PictureSettingsBehavior(long j, int i);

    private native int nativeSetARDrone3PictureSettingsExpositionSelectionBehavior(long j, int i);

    private native int nativeSetARDrone3PictureSettingsPictureFormatSelectionBehavior(long j, int i);

    private native int nativeSetARDrone3PictureSettingsSaturationSelectionBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3PictureSettingsStateAutoWhiteBalanceChangedBehavior */
    private native int m95x8e1a581d(long j, int i);

    private native int nativeSetARDrone3PictureSettingsStateBehavior(long j, int i);

    private native int nativeSetARDrone3PictureSettingsStateExpositionChangedBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3PictureSettingsStatePictureFormatChangedBehavior */
    private native int m96xa15a2f58(long j, int i);

    private native int nativeSetARDrone3PictureSettingsStateSaturationChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PictureSettingsStateTimelapseChangedBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3PictureSettingsStateVideoAutorecordChangedBehavior */
    private native int m97x953be9d2(long j, int i);

    /* renamed from: nativeSetARDrone3PictureSettingsStateVideoFramerateChangedBehavior */
    private native int m98x98eee08d(long j, int i);

    /* renamed from: nativeSetARDrone3PictureSettingsStateVideoRecordingModeChangedBehavior */
    private native int m99xb636d846(long j, int i);

    /* renamed from: nativeSetARDrone3PictureSettingsStateVideoResolutionsChangedBehavior */
    private native int m100x9a29ead3(long j, int i);

    /* renamed from: nativeSetARDrone3PictureSettingsStateVideoStabilizationModeChangedBehavior */
    private native int m101xb3638b98(long j, int i);

    private native int nativeSetARDrone3PictureSettingsTimelapseSelectionBehavior(long j, int i);

    private native int nativeSetARDrone3PictureSettingsVideoAutorecordSelectionBehavior(long j, int i);

    private native int nativeSetARDrone3PictureSettingsVideoFramerateBehavior(long j, int i);

    private native int nativeSetARDrone3PictureSettingsVideoRecordingModeBehavior(long j, int i);

    private native int nativeSetARDrone3PictureSettingsVideoResolutionsBehavior(long j, int i);

    private native int nativeSetARDrone3PictureSettingsVideoStabilizationModeBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingAutoTakeOffModeBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingCancelMoveToBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingCircleBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingEmergencyBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingEventBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingEventMoveByEndBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingFlatTrimBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingLandingBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingMoveByBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingMoveToBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingNavigateHomeBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingPCMDBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsAbsolutControlBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsBankedTurnBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsCirclingAltitudeBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsCirclingDirectionBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsCirclingRadiusBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsMaxAltitudeBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsMaxDistanceBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsMaxTiltBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsMinAltitudeBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsNoFlyOverMaxDistanceBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsPitchModeBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalAccelerationBehavior */
    private native int m102xdf40c57c(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalSpeedBehavior */
    private native int m103x9aca6c6f(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsSetAutonomousFlightMaxRotationSpeedBehavior */
    private native int m104x32b201d5(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalAccelerationBehavior */
    private native int m105xa48f1d4e(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalSpeedBehavior */
    private native int m106xe5cf5cdd(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAbsolutControlChangedBehavior */
    private native int m107x66f31c6a(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalAccelerationBehavior */
    private native int m108x1654442d(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalSpeedBehavior */
    private native int m109x3310a2de(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAutonomousFlightMaxRotationSpeedBehavior */
    private native int m110xaa10804(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalAccelerationBehavior */
    private native int m111x2b24943f(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalSpeedBehavior */
    private native int m112xbdbe630c(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsStateBankedTurnChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsStateBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateCirclingAltitudeChangedBehavior */
    private native int m113x5683d12a(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateCirclingDirectionChangedBehavior */
    private native int m114x52959161(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateCirclingRadiusChangedBehavior */
    private native int m115x78009aba(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsStateMaxAltitudeChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsStateMaxDistanceChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsStateMaxTiltChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsStateMinAltitudeChangedBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3PilotingSettingsStateNoFlyOverMaxDistanceChangedBehavior */
    private native int m116x17f88fc6(long j, int i);

    private native int nativeSetARDrone3PilotingSettingsStatePitchModeChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateAirSpeedChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateAlertStateChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateAltitudeChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateAttitudeChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateAutoTakeOffModeChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateFlatTrimChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateFlyingStateChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateGpsLocationChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateLandingStateChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateMoveToChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateNavigateHomeStateChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStatePositionChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingStateSpeedChangedBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingTakeOffBehavior(long j, int i);

    private native int nativeSetARDrone3PilotingUserTakeOffBehavior(long j, int i);

    private native int nativeSetARDrone3SettingsStateBehavior(long j, int i);

    private native int nativeSetARDrone3SettingsStateCPUIDBehavior(long j, int i);

    private native int nativeSetARDrone3SettingsStateMotorErrorLastErrorChangedBehavior(long j, int i);

    private native int nativeSetARDrone3SettingsStateMotorErrorStateChangedBehavior(long j, int i);

    private native int nativeSetARDrone3SettingsStateMotorFlightsStatusChangedBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3SettingsStateMotorSoftwareVersionChangedBehavior */
    private native int m117x2d09e013(long j, int i);

    private native int nativeSetARDrone3SettingsStateP7IDBehavior(long j, int i);

    private native int nativeSetARDrone3SettingsStateProductGPSVersionChangedBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3SettingsStateProductMotorVersionListChangedBehavior */
    private native int m118x22e3deed(long j, int i);

    private native int nativeSetARDrone3SpeedSettingsBehavior(long j, int i);

    private native int nativeSetARDrone3SpeedSettingsHullProtectionBehavior(long j, int i);

    private native int nativeSetARDrone3SpeedSettingsMaxPitchRollRotationSpeedBehavior(long j, int i);

    private native int nativeSetARDrone3SpeedSettingsMaxRotationSpeedBehavior(long j, int i);

    private native int nativeSetARDrone3SpeedSettingsMaxVerticalSpeedBehavior(long j, int i);

    private native int nativeSetARDrone3SpeedSettingsOutdoorBehavior(long j, int i);

    private native int nativeSetARDrone3SpeedSettingsStateBehavior(long j, int i);

    private native int nativeSetARDrone3SpeedSettingsStateHullProtectionChangedBehavior(long j, int i);

    /* renamed from: nativeSetARDrone3SpeedSettingsStateMaxPitchRollRotationSpeedChangedBehavior */
    private native int m119xf5133f14(long j, int i);

    /* renamed from: nativeSetARDrone3SpeedSettingsStateMaxRotationSpeedChangedBehavior */
    private native int m120x269fc723(long j, int i);

    /* renamed from: nativeSetARDrone3SpeedSettingsStateMaxVerticalSpeedChangedBehavior */
    private native int m121x72df731b(long j, int i);

    private native int nativeSetARDrone3SpeedSettingsStateOutdoorChangedBehavior(long j, int i);

    private native int nativeSetCommonARLibsVersionsStateBehavior(long j, int i);

    /* renamed from: nativeSetCommonARLibsVersionsStateControllerLibARCommandsVersionBehavior */
    private native int m122xb8263ea9(long j, int i);

    /* renamed from: nativeSetCommonARLibsVersionsStateDeviceLibARCommandsVersionBehavior */
    private native int m123x6ce4f6e3(long j, int i);

    /* renamed from: nativeSetCommonARLibsVersionsStateSkyControllerLibARCommandsVersionBehavior */
    private native int m124x2f00048(long j, int i);

    private native int nativeSetCommonAccessoryBehavior(long j, int i);

    private native int nativeSetCommonAccessoryConfigBehavior(long j, int i);

    private native int nativeSetCommonAccessoryStateAccessoryConfigChangedBehavior(long j, int i);

    /* renamed from: nativeSetCommonAccessoryStateAccessoryConfigModificationEnabledBehavior */
    private native int m125x7512bc98(long j, int i);

    private native int nativeSetCommonAccessoryStateBehavior(long j, int i);

    /* renamed from: nativeSetCommonAccessoryStateSupportedAccessoriesListChangedBehavior */
    private native int m126xcf3ac911(long j, int i);

    private native int nativeSetCommonAnimationsBehavior(long j, int i);

    private native int nativeSetCommonAnimationsStartAnimationBehavior(long j, int i);

    private native int nativeSetCommonAnimationsStateBehavior(long j, int i);

    private native int nativeSetCommonAnimationsStateListBehavior(long j, int i);

    private native int nativeSetCommonAnimationsStopAllAnimationsBehavior(long j, int i);

    private native int nativeSetCommonAnimationsStopAnimationBehavior(long j, int i);

    private native int nativeSetCommonAudioBehavior(long j, int i);

    private native int nativeSetCommonAudioControllerReadyForStreamingBehavior(long j, int i);

    private native int nativeSetCommonAudioStateAudioStreamingRunningBehavior(long j, int i);

    private native int nativeSetCommonAudioStateBehavior(long j, int i);

    private native int nativeSetCommonBehavior(long j, int i);

    private native int nativeSetCommonCalibrationBehavior(long j, int i);

    private native int nativeSetCommonCalibrationMagnetoCalibrationBehavior(long j, int i);

    private native int nativeSetCommonCalibrationPitotCalibrationBehavior(long j, int i);

    private native int nativeSetCommonCalibrationStateBehavior(long j, int i);

    /* renamed from: nativeSetCommonCalibrationStateMagnetoCalibrationAxisToCalibrateChangedBehavior */
    private native int m127xd0a05add(long j, int i);

    /* renamed from: nativeSetCommonCalibrationStateMagnetoCalibrationRequiredStateBehavior */
    private native int m128x330ce902(long j, int i);

    /* renamed from: nativeSetCommonCalibrationStateMagnetoCalibrationStartedChangedBehavior */
    private native int m129x304c5c7(long j, int i);

    /* renamed from: nativeSetCommonCalibrationStateMagnetoCalibrationStateChangedBehavior */
    private native int m130x23361037(long j, int i);

    /* renamed from: nativeSetCommonCalibrationStatePitotCalibrationStateChangedBehavior */
    private native int m131x627c5d9c(long j, int i);

    private native int nativeSetCommonCameraSettingsStateBehavior(long j, int i);

    private native int nativeSetCommonCameraSettingsStateCameraSettingsChangedBehavior(long j, int i);

    private native int nativeSetCommonChargerBehavior(long j, int i);

    private native int nativeSetCommonChargerSetMaxChargeRateBehavior(long j, int i);

    private native int nativeSetCommonChargerStateBehavior(long j, int i);

    private native int nativeSetCommonChargerStateChargingInfoBehavior(long j, int i);

    private native int nativeSetCommonChargerStateCurrentChargeStateChangedBehavior(long j, int i);

    private native int nativeSetCommonChargerStateLastChargeRateChangedBehavior(long j, int i);

    private native int nativeSetCommonChargerStateMaxChargeRateChangedBehavior(long j, int i);

    private native int nativeSetCommonCommonAllStatesBehavior(long j, int i);

    private native int nativeSetCommonCommonBehavior(long j, int i);

    private native int nativeSetCommonCommonCurrentDateBehavior(long j, int i);

    private native int nativeSetCommonCommonCurrentTimeBehavior(long j, int i);

    private native int nativeSetCommonCommonRebootBehavior(long j, int i);

    private native int nativeSetCommonCommonStateAllStatesChangedBehavior(long j, int i);

    private native int nativeSetCommonCommonStateBatteryStateChangedBehavior(long j, int i);

    private native int nativeSetCommonCommonStateBehavior(long j, int i);

    private native int nativeSetCommonCommonStateCountryListKnownBehavior(long j, int i);

    private native int nativeSetCommonCommonStateCurrentDateChangedBehavior(long j, int i);

    private native int nativeSetCommonCommonStateCurrentTimeChangedBehavior(long j, int i);

    /* renamed from: nativeSetCommonCommonStateDeprecatedMassStorageContentChangedBehavior */
    private native int m132x101f0ab1(long j, int i);

    private native int nativeSetCommonCommonStateMassStorageContentBehavior(long j, int i);

    /* renamed from: nativeSetCommonCommonStateMassStorageContentForCurrentRunBehavior */
    private native int m133x6a1ff1eb(long j, int i);

    /* renamed from: nativeSetCommonCommonStateMassStorageInfoRemainingListChangedBehavior */
    private native int m134x1f6eb897(long j, int i);

    /* renamed from: nativeSetCommonCommonStateMassStorageInfoStateListChangedBehavior */
    private native int m135x3d6f589c(long j, int i);

    private native int nativeSetCommonCommonStateMassStorageStateListChangedBehavior(long j, int i);

    private native int nativeSetCommonCommonStateProductModelBehavior(long j, int i);

    private native int nativeSetCommonCommonStateSensorsStatesListChangedBehavior(long j, int i);

    private native int nativeSetCommonCommonStateVideoRecordingTimestampBehavior(long j, int i);

    private native int nativeSetCommonCommonStateWifiSignalChangedBehavior(long j, int i);

    private native int nativeSetCommonControllerBehavior(long j, int i);

    private native int nativeSetCommonControllerIsPilotingBehavior(long j, int i);

    private native int nativeSetCommonFactoryBehavior(long j, int i);

    private native int nativeSetCommonFactoryResetBehavior(long j, int i);

    private native int nativeSetCommonFlightPlanEventBehavior(long j, int i);

    private native int nativeSetCommonFlightPlanEventSpeedBridleEventBehavior(long j, int i);

    private native int nativeSetCommonFlightPlanEventStartingErrorEventBehavior(long j, int i);

    private native int nativeSetCommonFlightPlanSettingsBehavior(long j, int i);

    private native int nativeSetCommonFlightPlanSettingsReturnHomeOnDisconnectBehavior(long j, int i);

    private native int nativeSetCommonFlightPlanSettingsStateBehavior(long j, int i);

    /* renamed from: nativeSetCommonFlightPlanSettingsStateReturnHomeOnDisconnectChangedBehavior */
    private native int m136x38af87dd(long j, int i);

    private native int nativeSetCommonFlightPlanStateAvailabilityStateChangedBehavior(long j, int i);

    private native int nativeSetCommonFlightPlanStateBehavior(long j, int i);

    private native int nativeSetCommonFlightPlanStateComponentStateListChangedBehavior(long j, int i);

    private native int nativeSetCommonFlightPlanStateLockStateChangedBehavior(long j, int i);

    private native int nativeSetCommonGPSBehavior(long j, int i);

    private native int nativeSetCommonGPSControllerPositionForRunBehavior(long j, int i);

    private native int nativeSetCommonHeadlightsBehavior(long j, int i);

    private native int nativeSetCommonHeadlightsIntensityBehavior(long j, int i);

    private native int nativeSetCommonHeadlightsStateBehavior(long j, int i);

    private native int nativeSetCommonHeadlightsStateIntensityChangedBehavior(long j, int i);

    private native int nativeSetCommonMavlinkBehavior(long j, int i);

    private native int nativeSetCommonMavlinkPauseBehavior(long j, int i);

    private native int nativeSetCommonMavlinkStartBehavior(long j, int i);

    private native int nativeSetCommonMavlinkStateBehavior(long j, int i);

    /* renamed from: nativeSetCommonMavlinkStateMavlinkFilePlayingStateChangedBehavior */
    private native int m137xdf6d55b6(long j, int i);

    private native int nativeSetCommonMavlinkStateMavlinkPlayErrorStateChangedBehavior(long j, int i);

    private native int nativeSetCommonMavlinkStateMissionItemExecutedBehavior(long j, int i);

    private native int nativeSetCommonMavlinkStopBehavior(long j, int i);

    private native int nativeSetCommonNetworkBehavior(long j, int i);

    private native int nativeSetCommonNetworkDisconnectBehavior(long j, int i);

    private native int nativeSetCommonNetworkEventBehavior(long j, int i);

    private native int nativeSetCommonNetworkEventDisconnectionBehavior(long j, int i);

    private native int nativeSetCommonOverHeatBehavior(long j, int i);

    private native int nativeSetCommonOverHeatStateBehavior(long j, int i);

    private native int nativeSetCommonOverHeatStateOverHeatChangedBehavior(long j, int i);

    private native int nativeSetCommonOverHeatStateOverHeatRegulationChangedBehavior(long j, int i);

    private native int nativeSetCommonOverHeatSwitchOffBehavior(long j, int i);

    private native int nativeSetCommonOverHeatVentilateBehavior(long j, int i);

    private native int nativeSetCommonRunStateBehavior(long j, int i);

    private native int nativeSetCommonRunStateRunIdChangedBehavior(long j, int i);

    private native int nativeSetCommonSettingsAllSettingsBehavior(long j, int i);

    private native int nativeSetCommonSettingsAutoCountryBehavior(long j, int i);

    private native int nativeSetCommonSettingsBehavior(long j, int i);

    private native int nativeSetCommonSettingsCountryBehavior(long j, int i);

    private native int nativeSetCommonSettingsProductNameBehavior(long j, int i);

    private native int nativeSetCommonSettingsResetBehavior(long j, int i);

    private native int nativeSetCommonSettingsStateAllSettingsChangedBehavior(long j, int i);

    private native int nativeSetCommonSettingsStateAutoCountryChangedBehavior(long j, int i);

    private native int nativeSetCommonSettingsStateBehavior(long j, int i);

    private native int nativeSetCommonSettingsStateCountryChangedBehavior(long j, int i);

    private native int nativeSetCommonSettingsStateProductNameChangedBehavior(long j, int i);

    private native int nativeSetCommonSettingsStateProductSerialHighChangedBehavior(long j, int i);

    private native int nativeSetCommonSettingsStateProductSerialLowChangedBehavior(long j, int i);

    private native int nativeSetCommonSettingsStateProductVersionChangedBehavior(long j, int i);

    private native int nativeSetCommonSettingsStateResetChangedBehavior(long j, int i);

    private native int nativeSetCommonWifiSettingsBehavior(long j, int i);

    private native int nativeSetCommonWifiSettingsOutdoorSettingBehavior(long j, int i);

    private native int nativeSetCommonWifiSettingsStateBehavior(long j, int i);

    private native int nativeSetCommonWifiSettingsStateOutdoorSettingsChangedBehavior(long j, int i);

    private native int nativeSetControllerInfoBarometerBehavior(long j, int i);

    private native int nativeSetControllerInfoBehavior(long j, int i);

    private native int nativeSetControllerInfoGpsBehavior(long j, int i);

    private native int nativeSetDebugBehavior(long j, int i);

    private native int nativeSetDebugGetAllSettingsBehavior(long j, int i);

    private native int nativeSetDebugSetSettingBehavior(long j, int i);

    private native int nativeSetDebugSettingsInfoBehavior(long j, int i);

    private native int nativeSetDebugSettingsListBehavior(long j, int i);

    private native int nativeSetDroneManagerAuthenticationFailedBehavior(long j, int i);

    private native int nativeSetDroneManagerBehavior(long j, int i);

    private native int nativeSetDroneManagerConnectBehavior(long j, int i);

    private native int nativeSetDroneManagerConnectionRefusedBehavior(long j, int i);

    private native int nativeSetDroneManagerConnectionStateBehavior(long j, int i);

    private native int nativeSetDroneManagerDiscoverDronesBehavior(long j, int i);

    private native int nativeSetDroneManagerDroneListItemBehavior(long j, int i);

    private native int nativeSetDroneManagerForgetBehavior(long j, int i);

    private native int nativeSetDroneManagerKnownDroneItemBehavior(long j, int i);

    private native int nativeSetFollowMeBehavior(long j, int i);

    private native int nativeSetFollowMeBoomerangAnimConfigBehavior(long j, int i);

    private native int nativeSetFollowMeCandleAnimConfigBehavior(long j, int i);

    private native int nativeSetFollowMeConfigureGeographicBehavior(long j, int i);

    private native int nativeSetFollowMeConfigureRelativeBehavior(long j, int i);

    private native int nativeSetFollowMeDollySlideAnimConfigBehavior(long j, int i);

    private native int nativeSetFollowMeGeographicConfigBehavior(long j, int i);

    private native int nativeSetFollowMeHelicoidAnimConfigBehavior(long j, int i);

    private native int nativeSetFollowMeModeInfoBehavior(long j, int i);

    private native int nativeSetFollowMeRelativeConfigBehavior(long j, int i);

    private native int nativeSetFollowMeStartBehavior(long j, int i);

    private native int nativeSetFollowMeStartBoomerangAnimBehavior(long j, int i);

    private native int nativeSetFollowMeStartCandleAnimBehavior(long j, int i);

    private native int nativeSetFollowMeStartDollySlideAnimBehavior(long j, int i);

    private native int nativeSetFollowMeStartHelicoidAnimBehavior(long j, int i);

    private native int nativeSetFollowMeStartSwingAnimBehavior(long j, int i);

    private native int nativeSetFollowMeStateBehavior(long j, int i);

    private native int nativeSetFollowMeStopAnimationBehavior(long j, int i);

    private native int nativeSetFollowMeStopBehavior(long j, int i);

    private native int nativeSetFollowMeSwingAnimConfigBehavior(long j, int i);

    private native int nativeSetFollowMeTargetFramingPositionBehavior(long j, int i);

    private native int nativeSetFollowMeTargetFramingPositionChangedBehavior(long j, int i);

    private native int nativeSetFollowMeTargetImageDetectionBehavior(long j, int i);

    private native int nativeSetFollowMeTargetImageDetectionStateBehavior(long j, int i);

    private native int nativeSetFollowMeTargetTrajectoryBehavior(long j, int i);

    private native int nativeSetGenericBehavior(long j, int i);

    private native int nativeSetGenericDefaultBehavior(long j, int i);

    private native int nativeSetGenericDroneSettingsChangedBehavior(long j, int i);

    private native int nativeSetGenericSetDroneSettingsBehavior(long j, int i);

    private native int nativeSetJumpingSumoAnimationsBehavior(long j, int i);

    private native int nativeSetJumpingSumoAnimationsJumpBehavior(long j, int i);

    private native int nativeSetJumpingSumoAnimationsJumpCancelBehavior(long j, int i);

    private native int nativeSetJumpingSumoAnimationsJumpLoadBehavior(long j, int i);

    private native int nativeSetJumpingSumoAnimationsJumpStopBehavior(long j, int i);

    private native int nativeSetJumpingSumoAnimationsSimpleAnimationBehavior(long j, int i);

    private native int nativeSetJumpingSumoAnimationsStateBehavior(long j, int i);

    private native int nativeSetJumpingSumoAnimationsStateJumpLoadChangedBehavior(long j, int i);

    /* renamed from: nativeSetJumpingSumoAnimationsStateJumpMotorProblemChangedBehavior */
    private native int m138x80865fb9(long j, int i);

    private native int nativeSetJumpingSumoAnimationsStateJumpTypeChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoAudioSettingsBehavior(long j, int i);

    private native int nativeSetJumpingSumoAudioSettingsMasterVolumeBehavior(long j, int i);

    private native int nativeSetJumpingSumoAudioSettingsStateBehavior(long j, int i);

    /* renamed from: nativeSetJumpingSumoAudioSettingsStateMasterVolumeChangedBehavior */
    private native int m139x9dd2d085(long j, int i);

    private native int nativeSetJumpingSumoAudioSettingsStateThemeChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoAudioSettingsThemeBehavior(long j, int i);

    private native int nativeSetJumpingSumoBehavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordBehavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordEventBehavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordEventPictureEventChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordEventVideoEventChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordPictureBehavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordPictureV2Behavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordStateBehavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordStatePictureStateChangedBehavior(long j, int i);

    /* renamed from: nativeSetJumpingSumoMediaRecordStatePictureStateChangedV2Behavior */
    private native int m140x53345626(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordStateVideoStateChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordStateVideoStateChangedV2Behavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordVideoBehavior(long j, int i);

    private native int nativeSetJumpingSumoMediaRecordVideoV2Behavior(long j, int i);

    private native int nativeSetJumpingSumoMediaStreamingBehavior(long j, int i);

    private native int nativeSetJumpingSumoMediaStreamingStateBehavior(long j, int i);

    /* renamed from: nativeSetJumpingSumoMediaStreamingStateVideoEnableChangedBehavior */
    private native int m141x339e7c4e(long j, int i);

    private native int nativeSetJumpingSumoMediaStreamingVideoEnableBehavior(long j, int i);

    private native int nativeSetJumpingSumoNetworkBehavior(long j, int i);

    private native int nativeSetJumpingSumoNetworkSettingsBehavior(long j, int i);

    private native int nativeSetJumpingSumoNetworkSettingsStateBehavior(long j, int i);

    /* renamed from: nativeSetJumpingSumoNetworkSettingsStateWifiSelectionChangedBehavior */
    private native int m142xcc09fbdc(long j, int i);

    private native int nativeSetJumpingSumoNetworkSettingsWifiSelectionBehavior(long j, int i);

    /* renamed from: nativeSetJumpingSumoNetworkStateAllWifiAuthChannelChangedBehavior */
    private native int m143x94aa7fd1(long j, int i);

    private native int nativeSetJumpingSumoNetworkStateAllWifiScanChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoNetworkStateBehavior(long j, int i);

    private native int nativeSetJumpingSumoNetworkStateLinkQualityChangedBehavior(long j, int i);

    /* renamed from: nativeSetJumpingSumoNetworkStateWifiAuthChannelListChangedBehavior */
    private native int m144x7637af72(long j, int i);

    private native int nativeSetJumpingSumoNetworkStateWifiScanListChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoNetworkWifiAuthChannelBehavior(long j, int i);

    private native int nativeSetJumpingSumoNetworkWifiScanBehavior(long j, int i);

    private native int nativeSetJumpingSumoPilotingAddCapOffsetBehavior(long j, int i);

    private native int nativeSetJumpingSumoPilotingBehavior(long j, int i);

    private native int nativeSetJumpingSumoPilotingPCMDBehavior(long j, int i);

    private native int nativeSetJumpingSumoPilotingPostureBehavior(long j, int i);

    private native int nativeSetJumpingSumoPilotingStateAlertStateChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoPilotingStateBehavior(long j, int i);

    private native int nativeSetJumpingSumoPilotingStatePostureChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoPilotingStateSpeedChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanAllScriptsMetadataBehavior(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanBehavior(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanPlayScriptBehavior(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanScriptDeleteBehavior(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanScriptUploadedBehavior(long j, int i);

    /* renamed from: nativeSetJumpingSumoRoadPlanStateAllScriptsMetadataChangedBehavior */
    private native int m145xe1891135(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanStateBehavior(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanStatePlayScriptChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanStateScriptDeleteChangedBehavior(long j, int i);

    /* renamed from: nativeSetJumpingSumoRoadPlanStateScriptMetadataListChangedBehavior */
    private native int m146x40cb0c93(long j, int i);

    private native int nativeSetJumpingSumoRoadPlanStateScriptUploadChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoSettingsStateBehavior(long j, int i);

    /* renamed from: nativeSetJumpingSumoSettingsStateProductGPSVersionChangedBehavior */
    private native int m147xa37ec6ca(long j, int i);

    private native int nativeSetJumpingSumoSpeedSettingsBehavior(long j, int i);

    private native int nativeSetJumpingSumoSpeedSettingsOutdoorBehavior(long j, int i);

    private native int nativeSetJumpingSumoSpeedSettingsStateBehavior(long j, int i);

    private native int nativeSetJumpingSumoSpeedSettingsStateOutdoorChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoVideoSettingsAutorecordBehavior(long j, int i);

    private native int nativeSetJumpingSumoVideoSettingsBehavior(long j, int i);

    private native int nativeSetJumpingSumoVideoSettingsStateAutorecordChangedBehavior(long j, int i);

    private native int nativeSetJumpingSumoVideoSettingsStateBehavior(long j, int i);

    private native int nativeSetMapperActiveProductBehavior(long j, int i);

    private native int nativeSetMapperApplicationAxisEventBehavior(long j, int i);

    private native int nativeSetMapperApplicationButtonEventBehavior(long j, int i);

    private native int nativeSetMapperAxisMappingItemBehavior(long j, int i);

    private native int nativeSetMapperBehavior(long j, int i);

    private native int nativeSetMapperButtonMappingItemBehavior(long j, int i);

    private native int nativeSetMapperExpoMapItemBehavior(long j, int i);

    private native int nativeSetMapperGrabAxisEventBehavior(long j, int i);

    private native int nativeSetMapperGrabBehavior(long j, int i);

    private native int nativeSetMapperGrabButtonEventBehavior(long j, int i);

    private native int nativeSetMapperGrabStateBehavior(long j, int i);

    private native int nativeSetMapperInvertedMapItemBehavior(long j, int i);

    private native int nativeSetMapperMapAxisActionBehavior(long j, int i);

    private native int nativeSetMapperMapButtonActionBehavior(long j, int i);

    private native int nativeSetMapperMiniAxisMappingItemBehavior(long j, int i);

    private native int nativeSetMapperMiniBehavior(long j, int i);

    private native int nativeSetMapperMiniButtonMappingItemBehavior(long j, int i);

    private native int nativeSetMapperMiniMapAxisActionBehavior(long j, int i);

    private native int nativeSetMapperMiniMapButtonActionBehavior(long j, int i);

    private native int nativeSetMapperMiniResetMappingBehavior(long j, int i);

    private native int nativeSetMapperResetMappingBehavior(long j, int i);

    private native int nativeSetMapperSetExpoBehavior(long j, int i);

    private native int nativeSetMapperSetInvertedBehavior(long j, int i);

    private native int nativeSetMiniDroneAnimationsBehavior(long j, int i);

    private native int nativeSetMiniDroneAnimationsCapBehavior(long j, int i);

    private native int nativeSetMiniDroneAnimationsFlipBehavior(long j, int i);

    private native int nativeSetMiniDroneBehavior(long j, int i);

    private native int nativeSetMiniDroneConfigurationBehavior(long j, int i);

    private native int nativeSetMiniDroneConfigurationControllerNameBehavior(long j, int i);

    private native int nativeSetMiniDroneConfigurationControllerTypeBehavior(long j, int i);

    private native int nativeSetMiniDroneFloodControlStateBehavior(long j, int i);

    private native int nativeSetMiniDroneFloodControlStateFloodControlChangedBehavior(long j, int i);

    private native int nativeSetMiniDroneGPSBehavior(long j, int i);

    private native int nativeSetMiniDroneGPSControllerLatitudeForRunBehavior(long j, int i);

    private native int nativeSetMiniDroneGPSControllerLongitudeForRunBehavior(long j, int i);

    private native int nativeSetMiniDroneMediaRecordBehavior(long j, int i);

    private native int nativeSetMiniDroneMediaRecordEventBehavior(long j, int i);

    private native int nativeSetMiniDroneMediaRecordEventPictureEventChangedBehavior(long j, int i);

    private native int nativeSetMiniDroneMediaRecordPictureBehavior(long j, int i);

    private native int nativeSetMiniDroneMediaRecordPictureV2Behavior(long j, int i);

    private native int nativeSetMiniDroneMediaRecordStateBehavior(long j, int i);

    private native int nativeSetMiniDroneMediaRecordStatePictureStateChangedBehavior(long j, int i);

    private native int nativeSetMiniDroneMediaRecordStatePictureStateChangedV2Behavior(long j, int i);

    private native int nativeSetMiniDroneNavigationDataStateBehavior(long j, int i);

    private native int nativeSetMiniDroneNavigationDataStateDronePositionBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingAutoTakeOffModeBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingEmergencyBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingFlatTrimBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingFlyingModeBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingLandingBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingPCMDBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingPlaneGearBoxBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingSettingsBankedTurnBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingSettingsBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingSettingsMaxAltitudeBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingSettingsMaxTiltBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingSettingsStateBankedTurnChangedBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingSettingsStateBehavior(long j, int i);

    /* renamed from: nativeSetMiniDronePilotingSettingsStateMaxAltitudeChangedBehavior */
    private native int m148xdbc4080e(long j, int i);

    private native int nativeSetMiniDronePilotingSettingsStateMaxTiltChangedBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingStateAlertStateChangedBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingStateAutoTakeOffModeChangedBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingStateBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingStateFlatTrimChangedBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingStateFlyingModeChangedBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingStateFlyingStateChangedBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingStatePlaneGearBoxChangedBehavior(long j, int i);

    private native int nativeSetMiniDronePilotingTakeOffBehavior(long j, int i);

    private native int nativeSetMiniDroneRemoteControllerBehavior(long j, int i);

    private native int nativeSetMiniDroneRemoteControllerSetPairedRemoteBehavior(long j, int i);

    private native int nativeSetMiniDroneSettingsBehavior(long j, int i);

    private native int nativeSetMiniDroneSettingsCutOutModeBehavior(long j, int i);

    private native int nativeSetMiniDroneSettingsStateBehavior(long j, int i);

    private native int nativeSetMiniDroneSettingsStateCutOutModeChangedBehavior(long j, int i);

    /* renamed from: nativeSetMiniDroneSettingsStateProductInertialVersionChangedBehavior */
    private native int m149xfe81f17(long j, int i);

    /* renamed from: nativeSetMiniDroneSettingsStateProductMotorsVersionChangedBehavior */
    private native int m150xd890b983(long j, int i);

    private native int nativeSetMiniDroneSpeedSettingsBehavior(long j, int i);

    private native int nativeSetMiniDroneSpeedSettingsMaxHorizontalSpeedBehavior(long j, int i);

    private native int nativeSetMiniDroneSpeedSettingsMaxPlaneModeRotationSpeedBehavior(long j, int i);

    private native int nativeSetMiniDroneSpeedSettingsMaxRotationSpeedBehavior(long j, int i);

    private native int nativeSetMiniDroneSpeedSettingsMaxVerticalSpeedBehavior(long j, int i);

    private native int nativeSetMiniDroneSpeedSettingsStateBehavior(long j, int i);

    /* renamed from: nativeSetMiniDroneSpeedSettingsStateMaxHorizontalSpeedChangedBehavior */
    private native int m151x45b9b1ea(long j, int i);

    /* renamed from: nativeSetMiniDroneSpeedSettingsStateMaxPlaneModeRotationSpeedChangedBehavior */
    private native int m152xef48b495(long j, int i);

    /* renamed from: nativeSetMiniDroneSpeedSettingsStateMaxRotationSpeedChangedBehavior */
    private native int m153x90f0ca04(long j, int i);

    /* renamed from: nativeSetMiniDroneSpeedSettingsStateMaxVerticalSpeedChangedBehavior */
    private native int m154xdd3075fc(long j, int i);

    private native int nativeSetMiniDroneSpeedSettingsStateWheelsChangedBehavior(long j, int i);

    private native int nativeSetMiniDroneSpeedSettingsWheelsBehavior(long j, int i);

    private native int nativeSetMiniDroneUsbAccessoryBehavior(long j, int i);

    private native int nativeSetMiniDroneUsbAccessoryClawControlBehavior(long j, int i);

    private native int nativeSetMiniDroneUsbAccessoryGunControlBehavior(long j, int i);

    private native int nativeSetMiniDroneUsbAccessoryLightControlBehavior(long j, int i);

    private native int nativeSetMiniDroneUsbAccessoryStateBehavior(long j, int i);

    private native int nativeSetMiniDroneUsbAccessoryStateClawStateBehavior(long j, int i);

    private native int nativeSetMiniDroneUsbAccessoryStateGunStateBehavior(long j, int i);

    private native int nativeSetMiniDroneUsbAccessoryStateLightStateBehavior(long j, int i);

    private native int nativeSetPowerupBehavior(long j, int i);

    private native int nativeSetPowerupMediaRecordBehavior(long j, int i);

    private native int nativeSetPowerupMediaRecordEventBehavior(long j, int i);

    private native int nativeSetPowerupMediaRecordEventPictureEventChangedBehavior(long j, int i);

    private native int nativeSetPowerupMediaRecordEventVideoEventChangedBehavior(long j, int i);

    private native int nativeSetPowerupMediaRecordPictureV2Behavior(long j, int i);

    private native int nativeSetPowerupMediaRecordStateBehavior(long j, int i);

    private native int nativeSetPowerupMediaRecordStatePictureStateChangedV2Behavior(long j, int i);

    private native int nativeSetPowerupMediaRecordStateVideoStateChangedV2Behavior(long j, int i);

    private native int nativeSetPowerupMediaRecordVideoV2Behavior(long j, int i);

    private native int nativeSetPowerupMediaStreamingBehavior(long j, int i);

    private native int nativeSetPowerupMediaStreamingStateBehavior(long j, int i);

    private native int nativeSetPowerupMediaStreamingStateVideoEnableChangedBehavior(long j, int i);

    private native int nativeSetPowerupMediaStreamingVideoEnableBehavior(long j, int i);

    private native int nativeSetPowerupNetworkBehavior(long j, int i);

    private native int nativeSetPowerupNetworkSettingsBehavior(long j, int i);

    private native int nativeSetPowerupNetworkSettingsStateBehavior(long j, int i);

    private native int nativeSetPowerupNetworkSettingsStateWifiSelectionChangedBehavior(long j, int i);

    private native int nativeSetPowerupNetworkSettingsWifiSelectionBehavior(long j, int i);

    private native int nativeSetPowerupNetworkStateAllWifiAuthChannelChangedBehavior(long j, int i);

    private native int nativeSetPowerupNetworkStateAllWifiScanChangedBehavior(long j, int i);

    private native int nativeSetPowerupNetworkStateBehavior(long j, int i);

    private native int nativeSetPowerupNetworkStateLinkQualityChangedBehavior(long j, int i);

    private native int nativeSetPowerupNetworkStateWifiAuthChannelListChangedBehavior(long j, int i);

    private native int nativeSetPowerupNetworkStateWifiScanListChangedBehavior(long j, int i);

    private native int nativeSetPowerupNetworkWifiAuthChannelBehavior(long j, int i);

    private native int nativeSetPowerupNetworkWifiScanBehavior(long j, int i);

    private native int nativeSetPowerupPilotingBehavior(long j, int i);

    private native int nativeSetPowerupPilotingMotorModeBehavior(long j, int i);

    private native int nativeSetPowerupPilotingPCMDBehavior(long j, int i);

    private native int nativeSetPowerupPilotingSettingsBehavior(long j, int i);

    private native int nativeSetPowerupPilotingSettingsSetBehavior(long j, int i);

    private native int nativeSetPowerupPilotingSettingsStateBehavior(long j, int i);

    private native int nativeSetPowerupPilotingSettingsStateSettingChangedBehavior(long j, int i);

    private native int nativeSetPowerupPilotingStateAlertStateChangedBehavior(long j, int i);

    private native int nativeSetPowerupPilotingStateAltitudeChangedBehavior(long j, int i);

    private native int nativeSetPowerupPilotingStateAttitudeChangedBehavior(long j, int i);

    private native int nativeSetPowerupPilotingStateBehavior(long j, int i);

    private native int nativeSetPowerupPilotingStateFlyingStateChangedBehavior(long j, int i);

    private native int nativeSetPowerupPilotingStateMotorModeChangedBehavior(long j, int i);

    private native int nativeSetPowerupPilotingUserTakeOffBehavior(long j, int i);

    private native int nativeSetPowerupSoundsBehavior(long j, int i);

    private native int nativeSetPowerupSoundsBuzzBehavior(long j, int i);

    private native int nativeSetPowerupSoundsStateBehavior(long j, int i);

    private native int nativeSetPowerupSoundsStateBuzzChangedBehavior(long j, int i);

    private native int nativeSetPowerupVideoSettingsAutorecordBehavior(long j, int i);

    private native int nativeSetPowerupVideoSettingsBehavior(long j, int i);

    private native int nativeSetPowerupVideoSettingsStateAutorecordChangedBehavior(long j, int i);

    private native int nativeSetPowerupVideoSettingsStateBehavior(long j, int i);

    private native int nativeSetPowerupVideoSettingsStateVideoModeChangedBehavior(long j, int i);

    private native int nativeSetPowerupVideoSettingsVideoModeBehavior(long j, int i);

    private native int nativeSetRcAbortCalibrationBehavior(long j, int i);

    private native int nativeSetRcBehavior(long j, int i);

    private native int nativeSetRcCalibrationStateBehavior(long j, int i);

    private native int nativeSetRcChannelActionItemBehavior(long j, int i);

    private native int nativeSetRcChannelValueBehavior(long j, int i);

    private native int nativeSetRcChannelsMonitorStateBehavior(long j, int i);

    private native int nativeSetRcEnableReceiverBehavior(long j, int i);

    private native int nativeSetRcInvertChannelBehavior(long j, int i);

    private native int nativeSetRcMonitorChannelsBehavior(long j, int i);

    private native int nativeSetRcReceiverStateBehavior(long j, int i);

    private native int nativeSetRcResetCalibrationBehavior(long j, int i);

    private native int nativeSetRcStartCalibrationBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerAccessPointSettingsAccessPointChannelBehavior */
    private native int m155x961aaf06(long j, int i);

    private native int nativeSetSkyControllerAccessPointSettingsAccessPointSSIDBehavior(long j, int i);

    private native int nativeSetSkyControllerAccessPointSettingsBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerAccessPointSettingsStateAccessPointChannelChangedBehavior */
    private native int m156x5bcb2ffb(long j, int i);

    /* renamed from: nativeSetSkyControllerAccessPointSettingsStateAccessPointSSIDChangedBehavior */
    private native int m157xed049cf3(long j, int i);

    private native int nativeSetSkyControllerAccessPointSettingsStateBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerAccessPointSettingsStateWifiSecurityChangedBehavior */
    private native int m158xe4d25c9d(long j, int i);

    /* renamed from: nativeSetSkyControllerAccessPointSettingsStateWifiSelectionChangedBehavior */
    private native int m159x8618de03(long j, int i);

    private native int nativeSetSkyControllerAccessPointSettingsWifiSecurityBehavior(long j, int i);

    private native int nativeSetSkyControllerAccessPointSettingsWifiSelectionBehavior(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersBehavior(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersDefaultAxisFiltersBehavior(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersGetCurrentAxisFiltersBehavior(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersGetPresetAxisFiltersBehavior(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersSetAxisFilterBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerAxisFiltersStateAllCurrentFiltersSentBehavior */
    private native int m160x4d2c0a24(long j, int i);

    /* renamed from: nativeSetSkyControllerAxisFiltersStateAllPresetFiltersSentBehavior */
    private native int m161x608e2a8e(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersStateBehavior(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersStateCurrentAxisFiltersBehavior(long j, int i);

    private native int nativeSetSkyControllerAxisFiltersStatePresetAxisFiltersBehavior(long j, int i);

    private native int nativeSetSkyControllerAxisMappingsBehavior(long j, int i);

    private native int nativeSetSkyControllerAxisMappingsDefaultAxisMappingBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerAxisMappingsGetAvailableAxisMappingsBehavior */
    private native int m162x6dfe4f43(long j, int i);

    private native int nativeSetSkyControllerAxisMappingsGetCurrentAxisMappingsBehavior(long j, int i);

    private native int nativeSetSkyControllerAxisMappingsSetAxisMappingBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerAxisMappingsStateAllAvailableAxisMappingsSentBehavior */
    private native int m163x32d2d8d1(long j, int i);

    /* renamed from: nativeSetSkyControllerAxisMappingsStateAllCurrentAxisMappingsSentBehavior */
    private native int m164x6bc70181(long j, int i);

    /* renamed from: nativeSetSkyControllerAxisMappingsStateAvailableAxisMappingsBehavior */
    private native int m165xc0c229e8(long j, int i);

    private native int nativeSetSkyControllerAxisMappingsStateBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerAxisMappingsStateCurrentAxisMappingsBehavior */
    private native int m166x16a03258(long j, int i);

    private native int nativeSetSkyControllerBehavior(long j, int i);

    private native int nativeSetSkyControllerButtonEventsBehavior(long j, int i);

    private native int nativeSetSkyControllerButtonEventsSettingsBehavior(long j, int i);

    private native int nativeSetSkyControllerButtonMappingsBehavior(long j, int i);

    private native int nativeSetSkyControllerButtonMappingsDefaultButtonMappingBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerButtonMappingsGetAvailableButtonMappingsBehavior */
    private native int m167xdb8403e5(long j, int i);

    /* renamed from: nativeSetSkyControllerButtonMappingsGetCurrentButtonMappingsBehavior */
    private native int m168xaf9adb55(long j, int i);

    private native int nativeSetSkyControllerButtonMappingsSetButtonMappingBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerButtonMappingsStateAllAvailableButtonsMappingsSentBehavior */
    private native int m169x6eb9e2a0(long j, int i);

    /* renamed from: nativeSetSkyControllerButtonMappingsStateAllCurrentButtonMappingsSentBehavior */
    private native int m170x8bbfd361(long j, int i);

    /* renamed from: nativeSetSkyControllerButtonMappingsStateAvailableButtonMappingsBehavior */
    private native int m171xa1e5990a(long j, int i);

    private native int nativeSetSkyControllerButtonMappingsStateBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerButtonMappingsStateCurrentButtonMappingsBehavior */
    private native int m172xe33975ba(long j, int i);

    private native int nativeSetSkyControllerCalibrationBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerCalibrationEnableMagnetoCalibrationQualityUpdatesBehavior */
    private native int m173xab1db5bd(long j, int i);

    private native int nativeSetSkyControllerCalibrationStateBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerCalibrationStateMagnetoCalibrationQualityUpdatesStateBehavior */
    private native int m174x33ca7e5a(long j, int i);

    /* renamed from: nativeSetSkyControllerCalibrationStateMagnetoCalibrationStateBehavior */
    private native int m175x4f9acae5(long j, int i);

    private native int nativeSetSkyControllerCameraBehavior(long j, int i);

    private native int nativeSetSkyControllerCameraResetOrientationBehavior(long j, int i);

    private native int nativeSetSkyControllerCoPilotingBehavior(long j, int i);

    private native int nativeSetSkyControllerCoPilotingSetPilotingSourceBehavior(long j, int i);

    private native int nativeSetSkyControllerCoPilotingStateBehavior(long j, int i);

    private native int nativeSetSkyControllerCoPilotingStatePilotingSourceBehavior(long j, int i);

    private native int nativeSetSkyControllerCommonAllStatesBehavior(long j, int i);

    private native int nativeSetSkyControllerCommonBehavior(long j, int i);

    private native int nativeSetSkyControllerCommonEventStateBehavior(long j, int i);

    private native int nativeSetSkyControllerCommonEventStateShutdownBehavior(long j, int i);

    private native int nativeSetSkyControllerCommonStateAllStatesChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerCommonStateBehavior(long j, int i);

    private native int nativeSetSkyControllerDeviceBehavior(long j, int i);

    private native int nativeSetSkyControllerDeviceConnectToDeviceBehavior(long j, int i);

    private native int nativeSetSkyControllerDeviceRequestCurrentDeviceBehavior(long j, int i);

    private native int nativeSetSkyControllerDeviceRequestDeviceListBehavior(long j, int i);

    private native int nativeSetSkyControllerDeviceStateBehavior(long j, int i);

    private native int nativeSetSkyControllerDeviceStateConnexionChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerDeviceStateDeviceListBehavior(long j, int i);

    private native int nativeSetSkyControllerFactoryBehavior(long j, int i);

    private native int nativeSetSkyControllerFactoryResetBehavior(long j, int i);

    private native int nativeSetSkyControllerGamepadInfosBehavior(long j, int i);

    private native int nativeSetSkyControllerGamepadInfosGetGamepadControlsBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerGamepadInfosStateAllGamepadControlsSentBehavior */
    private native int m176x4d25c73b(long j, int i);

    private native int nativeSetSkyControllerGamepadInfosStateBehavior(long j, int i);

    private native int nativeSetSkyControllerGamepadInfosStateGamepadControlBehavior(long j, int i);

    private native int nativeSetSkyControllerSettingsAllSettingsBehavior(long j, int i);

    private native int nativeSetSkyControllerSettingsBehavior(long j, int i);

    private native int nativeSetSkyControllerSettingsResetBehavior(long j, int i);

    private native int nativeSetSkyControllerSettingsStateAllSettingsChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerSettingsStateBehavior(long j, int i);

    private native int nativeSetSkyControllerSettingsStateProductSerialChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerSettingsStateProductVariantChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerSettingsStateProductVersionChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerSettingsStateResetChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerSkyControllerStateAttitudeChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerSkyControllerStateBatteryChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerSkyControllerStateBatteryStateBehavior(long j, int i);

    private native int nativeSetSkyControllerSkyControllerStateBehavior(long j, int i);

    private native int nativeSetSkyControllerSkyControllerStateGpsFixChangedBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerSkyControllerStateGpsPositionChangedBehavior */
    private native int m177x28b6c079(long j, int i);

    private native int nativeSetSkyControllerWifiBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiConnectToWifiBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiForgetWifiBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiRequestCurrentWifiBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiRequestWifiListBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiStateAllWifiAuthChannelChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiStateBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiStateConnexionChangedBehavior(long j, int i);

    /* renamed from: nativeSetSkyControllerWifiStateWifiAuthChannelListChangedBehavior */
    private native int m178xb74fc28c(long j, int i);

    /* renamed from: nativeSetSkyControllerWifiStateWifiAuthChannelListChangedV2Behavior */
    private native int m179xfe5ed2a8(long j, int i);

    private native int nativeSetSkyControllerWifiStateWifiCountryChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiStateWifiEnvironmentChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiStateWifiListBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiStateWifiSignalChangedBehavior(long j, int i);

    private native int nativeSetSkyControllerWifiWifiAuthChannelBehavior(long j, int i);

    private native int nativeSetWifiApChannelChangedBehavior(long j, int i);

    private native int nativeSetWifiAuthorizedChannelBehavior(long j, int i);

    private native int nativeSetWifiBehavior(long j, int i);

    private native int nativeSetWifiCountryChangedBehavior(long j, int i);

    private native int nativeSetWifiEnvironmentChangedBehavior(long j, int i);

    private native int nativeSetWifiRssiChangedBehavior(long j, int i);

    private native int nativeSetWifiScanBehavior(long j, int i);

    private native int nativeSetWifiScannedItemBehavior(long j, int i);

    private native int nativeSetWifiSecurityChangedBehavior(long j, int i);

    private native int nativeSetWifiSetApChannelBehavior(long j, int i);

    private native int nativeSetWifiSetCountryBehavior(long j, int i);

    private native int nativeSetWifiSetEnvironmentBehavior(long j, int i);

    private native int nativeSetWifiSetSecurityBehavior(long j, int i);

    private native int nativeSetWifiSupportedCountriesBehavior(long j, int i);

    private native int nativeSetWifiUpdateAuthorizedChannelsBehavior(long j, int i);

    public ARCommandsFilter() {
        this(ARCOMMANDS_FILTER_STATUS_ENUM.ARCOMMANDS_FILTER_STATUS_ALLOWED);
    }

    public ARCommandsFilter(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        this.cFilter = nativeNewFilter(behavior.getValue());
        this.valid = this.cFilter != 0;
        if (!this.valid) {
            dispose();
        }
    }

    public boolean isValid() {
        return this.valid;
    }

    public void dispose() {
        if (this.valid) {
            nativeDeleteFilter(this.cFilter);
        }
        this.valid = false;
    }

    public long getFilter() {
        return this.cFilter;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.valid) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this);
                stringBuilder.append(": Finalize error -> dispose () was not called !");
                ARSALPrint.m532e(str, stringBuilder.toString());
                dispose();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public ARCOMMANDS_FILTER_STATUS_ENUM filterCommand(ARCommand command) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_STATUS_ENUM.getFromValue(nativeFilterCommand(this.cFilter, command.getData(), command.getDataSize()));
        }
        return ARCOMMANDS_FILTER_STATUS_ENUM.ARCOMMANDS_FILTER_STATUS_ERROR;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setGenericBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetGenericBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setGenericDefaultBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetGenericDefaultBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setGenericSetDroneSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetGenericSetDroneSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setGenericDroneSettingsChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetGenericDroneSettingsChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingFlatTrimBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingFlatTrimBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingTakeOffBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingTakeOffBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingPCMDBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingPCMDBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingLandingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingLandingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingEmergencyBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingEmergencyBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingNavigateHomeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingNavigateHomeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingAutoTakeOffModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingAutoTakeOffModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingMoveByBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingMoveByBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingUserTakeOffBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingUserTakeOffBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingCircleBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingCircleBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingMoveToBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingMoveToBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingCancelMoveToBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingCancelMoveToBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3AnimationsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3AnimationsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3AnimationsFlipBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3AnimationsFlipBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3CameraBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3CameraBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3CameraOrientationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3CameraOrientationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3CameraOrientationV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3CameraOrientationV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3CameraVelocityBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3CameraVelocityBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordPictureBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordPictureBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordVideoBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordVideoBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordPictureV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordPictureV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordVideoV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordVideoV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordStatePictureStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordStatePictureStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordStateVideoStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordStateVideoStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordStatePictureStateChangedV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordStatePictureStateChangedV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordStateVideoStateChangedV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordStateVideoStateChangedV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordStateVideoResolutionStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordStateVideoResolutionStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordEventPictureEventChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordEventPictureEventChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaRecordEventVideoEventChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaRecordEventVideoEventChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateFlatTrimChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateFlatTrimChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateFlyingStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateFlyingStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateAlertStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateAlertStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateNavigateHomeStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateNavigateHomeStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStatePositionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStatePositionChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateSpeedChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateSpeedChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateAttitudeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateAttitudeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateAutoTakeOffModeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateAutoTakeOffModeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateAltitudeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateAltitudeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateGpsLocationChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateGpsLocationChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateLandingStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateLandingStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateAirSpeedChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateAirSpeedChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingStateMoveToChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingStateMoveToChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingEventMoveByEndBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingEventMoveByEndBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkWifiScanBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkWifiScanBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkWifiAuthChannelBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkWifiAuthChannelBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkStateWifiScanListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkStateWifiScanListChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkStateAllWifiScanChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkStateAllWifiScanChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkStateWifiAuthChannelListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkStateWifiAuthChannelListChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkStateAllWifiAuthChannelChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkStateAllWifiAuthChannelChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsMaxAltitudeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsMaxAltitudeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsMaxTiltBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsMaxTiltBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsAbsolutControlBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsAbsolutControlBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsMaxDistanceBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsMaxDistanceBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsNoFlyOverMaxDistanceBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsNoFlyOverMaxDistanceBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalSpeedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m182xfa9cf738(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m103x9aca6c6f(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalSpeedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m185xb92805e6(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m106xe5cf5cdd(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxHorizontalAccelerationBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m181x612b3693(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m102xdf40c57c(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxVerticalAccelerationBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m184x2bc0e825(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m105xa48f1d4e(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsSetAutonomousFlightMaxRotationSpeedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m183x60aaade(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m104x32b201d5(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsBankedTurnBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsBankedTurnBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsMinAltitudeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsMinAltitudeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsCirclingDirectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsCirclingDirectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsCirclingRadiusBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsCirclingRadiusBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsCirclingAltitudeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsCirclingAltitudeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsPitchModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsPitchModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStateMaxAltitudeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsStateMaxAltitudeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStateMaxTiltChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsStateMaxTiltChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStateAbsolutControlChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m107x66f31c6a(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStateMaxDistanceChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsStateMaxDistanceChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsStateNoFlyOverMaxDistanceChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m191x73eaab5d(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m116x17f88fc6(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalSpeedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m187xe86b9f67(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m109x3310a2de(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalSpeedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m190x1d90edd5(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m112xbdbe630c(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalAccelerationBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m186xc766cb84(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m108x1654442d(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalAccelerationBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m189xad0f0556(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m111x2b24943f(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PilotingSettingsStateAutonomousFlightMaxRotationSpeedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m188x6a7392cd(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m110xaa10804(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStateBankedTurnChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsStateBankedTurnChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStateMinAltitudeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsStateMinAltitudeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStateCirclingDirectionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m114x52959161(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStateCirclingRadiusChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m115x78009aba(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStateCirclingAltitudeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m113x5683d12a(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PilotingSettingsStatePitchModeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PilotingSettingsStatePitchModeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SpeedSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsMaxVerticalSpeedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SpeedSettingsMaxVerticalSpeedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsMaxRotationSpeedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SpeedSettingsMaxRotationSpeedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsHullProtectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SpeedSettingsHullProtectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsOutdoorBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SpeedSettingsOutdoorBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsMaxPitchRollRotationSpeedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SpeedSettingsMaxPitchRollRotationSpeedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SpeedSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsStateMaxVerticalSpeedChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m121x72df731b(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsStateMaxRotationSpeedChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m120x269fc723(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsStateHullProtectionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SpeedSettingsStateHullProtectionChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SpeedSettingsStateOutdoorChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SpeedSettingsStateOutdoorChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3SpeedSettingsStateMaxPitchRollRotationSpeedChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m192x1cecd0eb(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m119xf5133f14(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkSettingsWifiSelectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkSettingsWifiSelectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkSettingsWifiSecurityBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkSettingsWifiSecurityBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkSettingsStateWifiSelectionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m93xc57dd886(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkSettingsStateWifiSecurityChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkSettingsStateWifiSecurityChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3NetworkSettingsStateWifiSecurityBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3NetworkSettingsStateWifiSecurityBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SettingsStateProductMotorVersionListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m118x22e3deed(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SettingsStateProductGPSVersionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SettingsStateProductGPSVersionChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SettingsStateMotorErrorStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SettingsStateMotorErrorStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SettingsStateMotorSoftwareVersionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m117x2d09e013(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SettingsStateMotorFlightsStatusChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SettingsStateMotorFlightsStatusChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SettingsStateMotorErrorLastErrorChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SettingsStateMotorErrorLastErrorChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SettingsStateP7IDBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SettingsStateP7IDBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3SettingsStateCPUIDBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3SettingsStateCPUIDBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsPictureFormatSelectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsPictureFormatSelectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsAutoWhiteBalanceSelectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m94xfb563eb2(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsExpositionSelectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsExpositionSelectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsSaturationSelectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsSaturationSelectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsTimelapseSelectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsTimelapseSelectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsVideoAutorecordSelectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsVideoAutorecordSelectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsVideoStabilizationModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsVideoStabilizationModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsVideoRecordingModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsVideoRecordingModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsVideoFramerateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsVideoFramerateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsVideoResolutionsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsVideoResolutionsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsStatePictureFormatChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m96xa15a2f58(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsStateAutoWhiteBalanceChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m95x8e1a581d(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsStateExpositionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsStateExpositionChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsStateSaturationChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsStateSaturationChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsStateTimelapseChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PictureSettingsStateTimelapseChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsStateVideoAutorecordChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m97x953be9d2(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setARDrone3PictureSettingsStateVideoStabilizationModeChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m180xd5b4e2e1(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m101xb3638b98(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsStateVideoRecordingModeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m99xb636d846(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsStateVideoFramerateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m98x98eee08d(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PictureSettingsStateVideoResolutionsChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m100x9a29ead3(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaStreamingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaStreamingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaStreamingVideoEnableBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaStreamingVideoEnableBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaStreamingVideoStreamModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaStreamingVideoStreamModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaStreamingStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaStreamingStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaStreamingStateVideoEnableChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3MediaStreamingStateVideoEnableChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3MediaStreamingStateVideoStreamModeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m92x9d5317a4(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsSetHomeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsSetHomeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsResetHomeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsResetHomeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsSendControllerGPSBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsSendControllerGPSBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsHomeTypeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsHomeTypeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsReturnHomeDelayBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsReturnHomeDelayBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsStateHomeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsStateHomeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsStateResetHomeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsStateResetHomeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsStateGPSFixStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsStateGPSFixStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsStateGPSUpdateStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsStateGPSUpdateStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsStateHomeTypeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsStateHomeTypeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsStateReturnHomeDelayChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsStateReturnHomeDelayChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSSettingsStateGeofenceCenterChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSSettingsStateGeofenceCenterChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3CameraStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3CameraStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3CameraStateOrientationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3CameraStateOrientationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3CameraStateDefaultCameraOrientationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3CameraStateDefaultCameraOrientationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3CameraStateOrientationV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3CameraStateOrientationV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3CameraStateDefaultCameraOrientationV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3CameraStateDefaultCameraOrientationV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3CameraStateVelocityRangeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3CameraStateVelocityRangeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3AntiflickeringBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3AntiflickeringBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3AntiflickeringElectricFrequencyBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3AntiflickeringElectricFrequencyBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3AntiflickeringSetModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3AntiflickeringSetModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3AntiflickeringStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3AntiflickeringStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3AntiflickeringStateElectricFrequencyChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m91x135403b5(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3AntiflickeringStateModeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3AntiflickeringStateModeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSStateNumberOfSatelliteChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSStateNumberOfSatelliteChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSStateHomeTypeAvailabilityChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSStateHomeTypeAvailabilityChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3GPSStateHomeTypeChosenChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3GPSStateHomeTypeChosenChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PROStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PROStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3PROStateFeaturesBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3PROStateFeaturesBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3AccessoryStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3AccessoryStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setARDrone3AccessoryStateConnectedAccessoriesBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetARDrone3AccessoryStateConnectedAccessoriesBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonNetworkBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonNetworkBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonNetworkDisconnectBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonNetworkDisconnectBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonNetworkEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonNetworkEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonNetworkEventDisconnectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonNetworkEventDisconnectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsAllSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsAllSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsResetBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsResetBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsProductNameBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsProductNameBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsCountryBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsCountryBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsAutoCountryBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsAutoCountryBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsStateAllSettingsChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsStateAllSettingsChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsStateResetChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsStateResetChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsStateProductNameChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsStateProductNameChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsStateProductVersionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsStateProductVersionChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsStateProductSerialHighChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsStateProductSerialHighChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsStateProductSerialLowChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsStateProductSerialLowChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsStateCountryChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsStateCountryChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonSettingsStateAutoCountryChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonSettingsStateAutoCountryChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonAllStatesBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonAllStatesBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonCurrentDateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonCurrentDateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonCurrentTimeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonCurrentTimeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonRebootBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonRebootBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateAllStatesChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateAllStatesChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateBatteryStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateBatteryStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateMassStorageStateListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateMassStorageStateListChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateMassStorageInfoStateListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m135x3d6f589c(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateCurrentDateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateCurrentDateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateCurrentTimeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateCurrentTimeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateMassStorageInfoRemainingListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m134x1f6eb897(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateWifiSignalChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateWifiSignalChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateSensorsStatesListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateSensorsStatesListChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateProductModelBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateProductModelBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateCountryListKnownBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateCountryListKnownBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateDeprecatedMassStorageContentChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m132x101f0ab1(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateMassStorageContentBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateMassStorageContentBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateMassStorageContentForCurrentRunBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m133x6a1ff1eb(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCommonStateVideoRecordingTimestampBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCommonStateVideoRecordingTimestampBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonOverHeatBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonOverHeatBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonOverHeatSwitchOffBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonOverHeatSwitchOffBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonOverHeatVentilateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonOverHeatVentilateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonOverHeatStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonOverHeatStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonOverHeatStateOverHeatChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonOverHeatStateOverHeatChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonOverHeatStateOverHeatRegulationChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonOverHeatStateOverHeatRegulationChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonControllerBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonControllerBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonControllerIsPilotingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonControllerIsPilotingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonWifiSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonWifiSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonWifiSettingsOutdoorSettingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonWifiSettingsOutdoorSettingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonWifiSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonWifiSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonWifiSettingsStateOutdoorSettingsChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonWifiSettingsStateOutdoorSettingsChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonMavlinkBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonMavlinkBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonMavlinkStartBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonMavlinkStartBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonMavlinkPauseBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonMavlinkPauseBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonMavlinkStopBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonMavlinkStopBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonMavlinkStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonMavlinkStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonMavlinkStateMavlinkFilePlayingStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m137xdf6d55b6(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonMavlinkStateMavlinkPlayErrorStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonMavlinkStateMavlinkPlayErrorStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonMavlinkStateMissionItemExecutedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonMavlinkStateMissionItemExecutedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFlightPlanSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFlightPlanSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFlightPlanSettingsReturnHomeOnDisconnectBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFlightPlanSettingsReturnHomeOnDisconnectBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFlightPlanSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFlightPlanSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setCommonFlightPlanSettingsStateReturnHomeOnDisconnectChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m198x608919b4(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m136x38af87dd(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCalibrationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCalibrationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCalibrationMagnetoCalibrationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCalibrationMagnetoCalibrationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCalibrationPitotCalibrationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCalibrationPitotCalibrationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCalibrationStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCalibrationStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCalibrationStateMagnetoCalibrationStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m130x23361037(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCalibrationStateMagnetoCalibrationRequiredStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m128x330ce902(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setCommonCalibrationStateMagnetoCalibrationAxisToCalibrateChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m196x6b1f2934(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m127xd0a05add(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setCommonCalibrationStateMagnetoCalibrationStartedChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m197xdf26db1e(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m129x304c5c7(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCalibrationStatePitotCalibrationStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m131x627c5d9c(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCameraSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCameraSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonCameraSettingsStateCameraSettingsChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonCameraSettingsStateCameraSettingsChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonGPSBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonGPSBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonGPSControllerPositionForRunBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonGPSControllerPositionForRunBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFlightPlanStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFlightPlanStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFlightPlanStateAvailabilityStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFlightPlanStateAvailabilityStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFlightPlanStateComponentStateListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFlightPlanStateComponentStateListChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFlightPlanStateLockStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFlightPlanStateLockStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFlightPlanEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFlightPlanEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFlightPlanEventStartingErrorEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFlightPlanEventStartingErrorEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFlightPlanEventSpeedBridleEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFlightPlanEventSpeedBridleEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonARLibsVersionsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonARLibsVersionsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setCommonARLibsVersionsStateControllerLibARCommandsVersionBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m193x6046d432(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m122xb8263ea9(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setCommonARLibsVersionsStateSkyControllerLibARCommandsVersionBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m194x2ac9921f(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m124x2f00048(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonARLibsVersionsStateDeviceLibARCommandsVersionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m123x6ce4f6e3(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAudioBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAudioBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAudioControllerReadyForStreamingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAudioControllerReadyForStreamingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAudioStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAudioStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAudioStateAudioStreamingRunningBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAudioStateAudioStreamingRunningBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonHeadlightsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonHeadlightsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonHeadlightsIntensityBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonHeadlightsIntensityBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonHeadlightsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonHeadlightsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonHeadlightsStateIntensityChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonHeadlightsStateIntensityChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAnimationsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAnimationsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAnimationsStartAnimationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAnimationsStartAnimationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAnimationsStopAnimationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAnimationsStopAnimationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAnimationsStopAllAnimationsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAnimationsStopAllAnimationsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAnimationsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAnimationsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAnimationsStateListBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAnimationsStateListBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAccessoryBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAccessoryBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAccessoryConfigBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAccessoryConfigBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAccessoryStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAccessoryStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAccessoryStateSupportedAccessoriesListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m126xcf3ac911(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonAccessoryStateAccessoryConfigChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonAccessoryStateAccessoryConfigChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setCommonAccessoryStateAccessoryConfigModificationEnabledBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m195x5134d1ef(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m125x7512bc98(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonChargerBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonChargerBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonChargerSetMaxChargeRateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonChargerSetMaxChargeRateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonChargerStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonChargerStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonChargerStateMaxChargeRateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonChargerStateMaxChargeRateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonChargerStateCurrentChargeStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonChargerStateCurrentChargeStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonChargerStateLastChargeRateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonChargerStateLastChargeRateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonChargerStateChargingInfoBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonChargerStateChargingInfoBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonRunStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonRunStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonRunStateRunIdChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonRunStateRunIdChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFactoryBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFactoryBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setCommonFactoryResetBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetCommonFactoryResetBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setControllerInfoBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetControllerInfoBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setControllerInfoGpsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetControllerInfoGpsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setControllerInfoBarometerBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetControllerInfoBarometerBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDebugBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDebugBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDebugGetAllSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDebugGetAllSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDebugSetSettingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDebugSetSettingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDebugSettingsInfoBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDebugSettingsInfoBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDebugSettingsListBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDebugSettingsListBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDroneManagerBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDroneManagerBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDroneManagerDiscoverDronesBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDroneManagerDiscoverDronesBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDroneManagerConnectBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDroneManagerConnectBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDroneManagerForgetBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDroneManagerForgetBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDroneManagerDroneListItemBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDroneManagerDroneListItemBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDroneManagerConnectionStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDroneManagerConnectionStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDroneManagerAuthenticationFailedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDroneManagerAuthenticationFailedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDroneManagerConnectionRefusedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDroneManagerConnectionRefusedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setDroneManagerKnownDroneItemBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetDroneManagerKnownDroneItemBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeStartBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeStartBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeStopBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeStopBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeConfigureGeographicBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeConfigureGeographicBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeConfigureRelativeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeConfigureRelativeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeStopAnimationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeStopAnimationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeStartHelicoidAnimBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeStartHelicoidAnimBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeStartSwingAnimBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeStartSwingAnimBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeStartBoomerangAnimBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeStartBoomerangAnimBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeStartCandleAnimBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeStartCandleAnimBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeStartDollySlideAnimBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeStartDollySlideAnimBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeTargetFramingPositionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeTargetFramingPositionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeTargetImageDetectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeTargetImageDetectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeModeInfoBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeModeInfoBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeGeographicConfigBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeGeographicConfigBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeRelativeConfigBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeRelativeConfigBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeTargetTrajectoryBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeTargetTrajectoryBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeHelicoidAnimConfigBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeHelicoidAnimConfigBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeSwingAnimConfigBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeSwingAnimConfigBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeBoomerangAnimConfigBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeBoomerangAnimConfigBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeCandleAnimConfigBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeCandleAnimConfigBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeDollySlideAnimConfigBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeDollySlideAnimConfigBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeTargetFramingPositionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeTargetFramingPositionChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setFollowMeTargetImageDetectionStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetFollowMeTargetImageDetectionStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoPilotingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoPilotingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoPilotingPCMDBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoPilotingPCMDBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoPilotingPostureBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoPilotingPostureBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoPilotingAddCapOffsetBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoPilotingAddCapOffsetBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoPilotingStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoPilotingStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoPilotingStatePostureChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoPilotingStatePostureChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoPilotingStateAlertStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoPilotingStateAlertStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoPilotingStateSpeedChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoPilotingStateSpeedChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAnimationsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAnimationsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAnimationsJumpStopBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAnimationsJumpStopBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAnimationsJumpCancelBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAnimationsJumpCancelBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAnimationsJumpLoadBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAnimationsJumpLoadBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAnimationsJumpBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAnimationsJumpBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAnimationsSimpleAnimationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAnimationsSimpleAnimationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAnimationsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAnimationsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAnimationsStateJumpLoadChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAnimationsStateJumpLoadChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAnimationsStateJumpTypeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAnimationsStateJumpTypeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAnimationsStateJumpMotorProblemChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m138x80865fb9(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoSettingsStateProductGPSVersionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m147xa37ec6ca(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordPictureBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordPictureBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordVideoBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordVideoBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordPictureV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordPictureV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordVideoV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordVideoV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordStatePictureStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordStatePictureStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordStateVideoStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordStateVideoStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordStatePictureStateChangedV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m140x53345626(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordStateVideoStateChangedV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordStateVideoStateChangedV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordEventPictureEventChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordEventPictureEventChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaRecordEventVideoEventChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaRecordEventVideoEventChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoNetworkSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkSettingsWifiSelectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoNetworkSettingsWifiSelectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoNetworkSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkSettingsStateWifiSelectionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m142xcc09fbdc(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoNetworkBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkWifiScanBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoNetworkWifiScanBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkWifiAuthChannelBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoNetworkWifiAuthChannelBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoNetworkStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkStateWifiScanListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoNetworkStateWifiScanListChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkStateAllWifiScanChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoNetworkStateAllWifiScanChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkStateWifiAuthChannelListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m144x7637af72(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkStateAllWifiAuthChannelChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m143x94aa7fd1(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoNetworkStateLinkQualityChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoNetworkStateLinkQualityChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAudioSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAudioSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAudioSettingsMasterVolumeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAudioSettingsMasterVolumeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAudioSettingsThemeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAudioSettingsThemeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAudioSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAudioSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAudioSettingsStateMasterVolumeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m139x9dd2d085(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoAudioSettingsStateThemeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoAudioSettingsStateThemeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoRoadPlanBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanAllScriptsMetadataBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoRoadPlanAllScriptsMetadataBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanScriptUploadedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoRoadPlanScriptUploadedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanScriptDeleteBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoRoadPlanScriptDeleteBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanPlayScriptBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoRoadPlanPlayScriptBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoRoadPlanStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanStateScriptMetadataListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m146x40cb0c93(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanStateAllScriptsMetadataChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m145xe1891135(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanStateScriptUploadChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoRoadPlanStateScriptUploadChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanStateScriptDeleteChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoRoadPlanStateScriptDeleteChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoRoadPlanStatePlayScriptChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoRoadPlanStatePlayScriptChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoSpeedSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoSpeedSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoSpeedSettingsOutdoorBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoSpeedSettingsOutdoorBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoSpeedSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoSpeedSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoSpeedSettingsStateOutdoorChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoSpeedSettingsStateOutdoorChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaStreamingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaStreamingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaStreamingVideoEnableBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaStreamingVideoEnableBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaStreamingStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoMediaStreamingStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoMediaStreamingStateVideoEnableChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m141x339e7c4e(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoVideoSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoVideoSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoVideoSettingsAutorecordBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoVideoSettingsAutorecordBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoVideoSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoVideoSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setJumpingSumoVideoSettingsStateAutorecordChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetJumpingSumoVideoSettingsStateAutorecordChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperGrabBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperGrabBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperMapButtonActionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperMapButtonActionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperMapAxisActionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperMapAxisActionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperResetMappingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperResetMappingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperSetExpoBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperSetExpoBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperSetInvertedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperSetInvertedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperGrabStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperGrabStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperGrabButtonEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperGrabButtonEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperGrabAxisEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperGrabAxisEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperButtonMappingItemBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperButtonMappingItemBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperAxisMappingItemBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperAxisMappingItemBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperApplicationAxisEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperApplicationAxisEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperApplicationButtonEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperApplicationButtonEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperExpoMapItemBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperExpoMapItemBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperInvertedMapItemBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperInvertedMapItemBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperActiveProductBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperActiveProductBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperMiniBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperMiniBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperMiniMapButtonActionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperMiniMapButtonActionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperMiniMapAxisActionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperMiniMapAxisActionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperMiniResetMappingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperMiniResetMappingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperMiniButtonMappingItemBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperMiniButtonMappingItemBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMapperMiniAxisMappingItemBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMapperMiniAxisMappingItemBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingFlatTrimBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingFlatTrimBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingTakeOffBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingTakeOffBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingPCMDBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingPCMDBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingLandingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingLandingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingEmergencyBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingEmergencyBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingAutoTakeOffModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingAutoTakeOffModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingFlyingModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingFlyingModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingPlaneGearBoxBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingPlaneGearBoxBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingStateFlatTrimChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingStateFlatTrimChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingStateFlyingStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingStateFlyingStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingStateAlertStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingStateAlertStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingStateAutoTakeOffModeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingStateAutoTakeOffModeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingStateFlyingModeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingStateFlyingModeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingStatePlaneGearBoxChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingStatePlaneGearBoxChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneAnimationsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneAnimationsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneAnimationsFlipBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneAnimationsFlipBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneAnimationsCapBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneAnimationsCapBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneMediaRecordBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneMediaRecordBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneMediaRecordPictureBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneMediaRecordPictureBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneMediaRecordPictureV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneMediaRecordPictureV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneMediaRecordStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneMediaRecordStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneMediaRecordStatePictureStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneMediaRecordStatePictureStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneMediaRecordStatePictureStateChangedV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneMediaRecordStatePictureStateChangedV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneMediaRecordEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneMediaRecordEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneMediaRecordEventPictureEventChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneMediaRecordEventPictureEventChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingSettingsMaxAltitudeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingSettingsMaxAltitudeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingSettingsMaxTiltBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingSettingsMaxTiltBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingSettingsBankedTurnBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingSettingsBankedTurnBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingSettingsStateMaxAltitudeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m148xdbc4080e(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingSettingsStateMaxTiltChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingSettingsStateMaxTiltChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDronePilotingSettingsStateBankedTurnChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDronePilotingSettingsStateBankedTurnChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSpeedSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsMaxVerticalSpeedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSpeedSettingsMaxVerticalSpeedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsMaxRotationSpeedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSpeedSettingsMaxRotationSpeedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsWheelsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSpeedSettingsWheelsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsMaxHorizontalSpeedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSpeedSettingsMaxHorizontalSpeedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsMaxPlaneModeRotationSpeedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSpeedSettingsMaxPlaneModeRotationSpeedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSpeedSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsStateMaxVerticalSpeedChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m154xdd3075fc(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsStateMaxRotationSpeedChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m153x90f0ca04(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsStateWheelsChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSpeedSettingsStateWheelsChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSpeedSettingsStateMaxHorizontalSpeedChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m151x45b9b1ea(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setMiniDroneSpeedSettingsStateMaxPlaneModeRotationSpeedChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m199xc2a15d9e(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m152xef48b495(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSettingsCutOutModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSettingsCutOutModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSettingsStateProductMotorsVersionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m150xd890b983(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSettingsStateProductInertialVersionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m149xfe81f17(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneSettingsStateCutOutModeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneSettingsStateCutOutModeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneFloodControlStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneFloodControlStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneFloodControlStateFloodControlChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneFloodControlStateFloodControlChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneGPSBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneGPSBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneGPSControllerLatitudeForRunBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneGPSControllerLatitudeForRunBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneGPSControllerLongitudeForRunBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneGPSControllerLongitudeForRunBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneConfigurationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneConfigurationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneConfigurationControllerTypeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneConfigurationControllerTypeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneConfigurationControllerNameBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneConfigurationControllerNameBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneUsbAccessoryStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneUsbAccessoryStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneUsbAccessoryStateLightStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneUsbAccessoryStateLightStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneUsbAccessoryStateClawStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneUsbAccessoryStateClawStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneUsbAccessoryStateGunStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneUsbAccessoryStateGunStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneUsbAccessoryBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneUsbAccessoryBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneUsbAccessoryLightControlBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneUsbAccessoryLightControlBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneUsbAccessoryClawControlBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneUsbAccessoryClawControlBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneUsbAccessoryGunControlBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneUsbAccessoryGunControlBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneRemoteControllerBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneRemoteControllerBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneRemoteControllerSetPairedRemoteBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneRemoteControllerSetPairedRemoteBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneNavigationDataStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneNavigationDataStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setMiniDroneNavigationDataStateDronePositionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetMiniDroneNavigationDataStateDronePositionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingPCMDBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingPCMDBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingUserTakeOffBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingUserTakeOffBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingMotorModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingMotorModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingStateAlertStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingStateAlertStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingStateFlyingStateChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingStateFlyingStateChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingStateMotorModeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingStateMotorModeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingStateAttitudeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingStateAttitudeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingStateAltitudeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingStateAltitudeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingSettingsSetBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingSettingsSetBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupPilotingSettingsStateSettingChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupPilotingSettingsStateSettingChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaRecordBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaRecordBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaRecordPictureV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaRecordPictureV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaRecordVideoV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaRecordVideoV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaRecordStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaRecordStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaRecordStatePictureStateChangedV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaRecordStatePictureStateChangedV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaRecordStateVideoStateChangedV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaRecordStateVideoStateChangedV2Behavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaRecordEventBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaRecordEventBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaRecordEventPictureEventChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaRecordEventPictureEventChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaRecordEventVideoEventChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaRecordEventVideoEventChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkSettingsWifiSelectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkSettingsWifiSelectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkSettingsStateWifiSelectionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkSettingsStateWifiSelectionChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkWifiScanBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkWifiScanBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkWifiAuthChannelBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkWifiAuthChannelBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkStateWifiScanListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkStateWifiScanListChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkStateAllWifiScanChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkStateAllWifiScanChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkStateWifiAuthChannelListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkStateWifiAuthChannelListChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkStateAllWifiAuthChannelChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkStateAllWifiAuthChannelChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupNetworkStateLinkQualityChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupNetworkStateLinkQualityChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaStreamingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaStreamingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaStreamingVideoEnableBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaStreamingVideoEnableBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaStreamingStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaStreamingStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupMediaStreamingStateVideoEnableChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupMediaStreamingStateVideoEnableChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupVideoSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupVideoSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupVideoSettingsAutorecordBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupVideoSettingsAutorecordBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupVideoSettingsVideoModeBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupVideoSettingsVideoModeBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupVideoSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupVideoSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupVideoSettingsStateAutorecordChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupVideoSettingsStateAutorecordChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupVideoSettingsStateVideoModeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupVideoSettingsStateVideoModeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupSoundsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupSoundsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupSoundsBuzzBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupSoundsBuzzBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupSoundsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupSoundsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setPowerupSoundsStateBuzzChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetPowerupSoundsStateBuzzChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcMonitorChannelsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcMonitorChannelsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcStartCalibrationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcStartCalibrationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcInvertChannelBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcInvertChannelBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcAbortCalibrationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcAbortCalibrationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcResetCalibrationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcResetCalibrationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcEnableReceiverBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcEnableReceiverBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcReceiverStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcReceiverStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcChannelsMonitorStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcChannelsMonitorStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcChannelValueBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcChannelValueBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcCalibrationStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcCalibrationStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setRcChannelActionItemBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetRcChannelActionItemBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiStateWifiListBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiStateWifiListBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiStateConnexionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiStateConnexionChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiStateWifiAuthChannelListChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m178xb74fc28c(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiStateAllWifiAuthChannelChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiStateAllWifiAuthChannelChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiStateWifiSignalChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiStateWifiSignalChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiStateWifiAuthChannelListChangedV2Behavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m179xfe5ed2a8(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiStateWifiCountryChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiStateWifiCountryChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiStateWifiEnvironmentChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiStateWifiEnvironmentChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiRequestWifiListBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiRequestWifiListBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiRequestCurrentWifiBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiRequestCurrentWifiBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiConnectToWifiBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiConnectToWifiBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiForgetWifiBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiForgetWifiBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerWifiWifiAuthChannelBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerWifiWifiAuthChannelBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerDeviceBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerDeviceBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerDeviceRequestDeviceListBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerDeviceRequestDeviceListBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerDeviceRequestCurrentDeviceBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerDeviceRequestCurrentDeviceBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerDeviceConnectToDeviceBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerDeviceConnectToDeviceBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerDeviceStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerDeviceStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerDeviceStateDeviceListBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerDeviceStateDeviceListBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerDeviceStateConnexionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerDeviceStateConnexionChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSettingsAllSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSettingsAllSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSettingsResetBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSettingsResetBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSettingsStateAllSettingsChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSettingsStateAllSettingsChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSettingsStateResetChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSettingsStateResetChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSettingsStateProductSerialChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSettingsStateProductSerialChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSettingsStateProductVariantChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSettingsStateProductVariantChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSettingsStateProductVersionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSettingsStateProductVersionChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCommonBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCommonBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCommonAllStatesBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCommonAllStatesBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCommonStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCommonStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCommonStateAllStatesChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCommonStateAllStatesChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSkyControllerStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSkyControllerStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSkyControllerStateBatteryChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSkyControllerStateBatteryChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSkyControllerStateGpsFixChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSkyControllerStateGpsFixChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSkyControllerStateGpsPositionChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m177x28b6c079(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSkyControllerStateBatteryStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSkyControllerStateBatteryStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerSkyControllerStateAttitudeChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerSkyControllerStateAttitudeChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAccessPointSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAccessPointSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAccessPointSettingsAccessPointSSIDBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAccessPointSettingsAccessPointSSIDBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAccessPointSettingsAccessPointChannelBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m155x961aaf06(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAccessPointSettingsWifiSelectionBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAccessPointSettingsWifiSelectionBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAccessPointSettingsWifiSecurityBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAccessPointSettingsWifiSecurityBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAccessPointSettingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAccessPointSettingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerAccessPointSettingsStateAccessPointSSIDChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m201xc05d45fc(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m157xed049cf3(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerAccessPointSettingsStateAccessPointChannelChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m200xf649fe52(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m156x5bcb2ffb(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerAccessPointSettingsStateWifiSelectionChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m203xa86a354c(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m159x8618de03(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerAccessPointSettingsStateWifiSecurityChangedBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m202x40c47834(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m158xe4d25c9d(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCameraBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCameraBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCameraResetOrientationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCameraResetOrientationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerGamepadInfosBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerGamepadInfosBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerGamepadInfosGetGamepadControlsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerGamepadInfosGetGamepadControlsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerGamepadInfosStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerGamepadInfosStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerGamepadInfosStateGamepadControlBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerGamepadInfosStateGamepadControlBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerGamepadInfosStateAllGamepadControlsSentBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m176x4d25c73b(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerButtonMappingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerButtonMappingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerButtonMappingsGetCurrentButtonMappingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m168xaf9adb55(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerButtonMappingsGetAvailableButtonMappingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m167xdb8403e5(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerButtonMappingsSetButtonMappingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerButtonMappingsSetButtonMappingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerButtonMappingsDefaultButtonMappingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerButtonMappingsDefaultButtonMappingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerButtonMappingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerButtonMappingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerButtonMappingsStateCurrentButtonMappingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m172xe33975ba(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerButtonMappingsStateAllCurrentButtonMappingsSentBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m207x237c4b78(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m170x8bbfd361(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerButtonMappingsStateAvailableButtonMappingsBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m208x4a062e93(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m171xa1e5990a(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerButtonMappingsStateAllAvailableButtonsMappingsSentBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m206x2414df29(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m169x6eb9e2a0(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisMappingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisMappingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisMappingsGetCurrentAxisMappingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisMappingsGetCurrentAxisMappingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisMappingsGetAvailableAxisMappingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m162x6dfe4f43(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisMappingsSetAxisMappingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisMappingsSetAxisMappingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisMappingsDefaultAxisMappingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisMappingsDefaultAxisMappingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisMappingsStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisMappingsStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisMappingsStateCurrentAxisMappingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m166x16a03258(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerAxisMappingsStateAllCurrentAxisMappingsSentBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m205xc7b91d18(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m164x6bc70181(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisMappingsStateAvailableAxisMappingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m165xc0c229e8(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerAxisMappingsStateAllAvailableAxisMappingsSentBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m204x5aac6aa8(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m163x32d2d8d1(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisFiltersBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisFiltersBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisFiltersGetCurrentAxisFiltersBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisFiltersGetCurrentAxisFiltersBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisFiltersGetPresetAxisFiltersBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisFiltersGetPresetAxisFiltersBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisFiltersSetAxisFilterBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisFiltersSetAxisFilterBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisFiltersDefaultAxisFiltersBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisFiltersDefaultAxisFiltersBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisFiltersStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisFiltersStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisFiltersStateCurrentAxisFiltersBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisFiltersStateCurrentAxisFiltersBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisFiltersStateAllCurrentFiltersSentBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m160x4d2c0a24(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisFiltersStatePresetAxisFiltersBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerAxisFiltersStatePresetAxisFiltersBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerAxisFiltersStateAllPresetFiltersSentBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m161x608e2a8e(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCoPilotingBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCoPilotingBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCoPilotingSetPilotingSourceBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCoPilotingSetPilotingSourceBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCoPilotingStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCoPilotingStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCoPilotingStatePilotingSourceBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCoPilotingStatePilotingSourceBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCalibrationBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCalibrationBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerCalibrationEnableMagnetoCalibrationQualityUpdatesBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m209x459c8414(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m173xab1db5bd(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCalibrationStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCalibrationStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCalibrationStateMagnetoCalibrationStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m175x4f9acae5(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    /* renamed from: setSkyControllerCalibrationStateMagnetoCalibrationQualityUpdatesStateBehavior */
    public ARCOMMANDS_FILTER_ERROR_ENUM m210xbafc4931(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(m174x33ca7e5a(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerButtonEventsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerButtonEventsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerButtonEventsSettingsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerButtonEventsSettingsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerFactoryBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerFactoryBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerFactoryResetBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerFactoryResetBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCommonEventStateBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCommonEventStateBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setSkyControllerCommonEventStateShutdownBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetSkyControllerCommonEventStateShutdownBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiScanBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiScanBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiUpdateAuthorizedChannelsBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiUpdateAuthorizedChannelsBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiSetApChannelBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiSetApChannelBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiSetSecurityBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiSetSecurityBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiSetCountryBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiSetCountryBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiSetEnvironmentBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiSetEnvironmentBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiScannedItemBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiScannedItemBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiAuthorizedChannelBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiAuthorizedChannelBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiApChannelChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiApChannelChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiSecurityChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiSecurityChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiCountryChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiCountryChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiEnvironmentChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiEnvironmentChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiRssiChangedBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiRssiChangedBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }

    public ARCOMMANDS_FILTER_ERROR_ENUM setWifiSupportedCountriesBehavior(ARCOMMANDS_FILTER_STATUS_ENUM behavior) {
        if (this.valid) {
            return ARCOMMANDS_FILTER_ERROR_ENUM.getFromValue(nativeSetWifiSupportedCountriesBehavior(this.cFilter, behavior.getValue()));
        }
        return ARCOMMANDS_FILTER_ERROR_ENUM.ARCOMMANDS_FILTER_ERROR_BAD_FILTER;
    }
}
