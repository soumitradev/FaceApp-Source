package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_FOLLOW_ME_MODE_ENUM;

public class ARFeatureFollowMe {
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_BOOMERANGANIMCONFIG_DISTANCE */
    public static String f1496x66b6eacf;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_BOOMERANGANIMCONFIG_SPEED;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_BOOMERANGANIMCONFIG_USE_DEFAULT */
    public static String f1497x58c4e62f;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_CANDLEANIMCONFIG_SPEED;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_CANDLEANIMCONFIG_USE_DEFAULT */
    public static String f1498xa38d2eba;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_CANDLEANIMCONFIG_VERTICAL_DISTANCE */
    public static String f1499xb201754f;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_DOLLYSLIDEANIMCONFIG_ANGLE;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_DOLLYSLIDEANIMCONFIG_HORIZONTAL_DISTANCE */
    public static String f1500x5420f277;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_DOLLYSLIDEANIMCONFIG_SPEED;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_DOLLYSLIDEANIMCONFIG_USE_DEFAULT */
    public static String f1501x39718710;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_GEOGRAPHICCONFIG_AZIMUTH;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_GEOGRAPHICCONFIG_DISTANCE;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_GEOGRAPHICCONFIG_ELEVATION;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_GEOGRAPHICCONFIG_USE_DEFAULT */
    public static String f1502x275b1a53;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_HELICOIDANIMCONFIG_REVOLUTION_NB */
    public static String f1503x5fccf74f;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_HELICOIDANIMCONFIG_SPEED;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_HELICOIDANIMCONFIG_USE_DEFAULT */
    public static String f1504x8ac3156e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_HELICOIDANIMCONFIG_VERTICAL_DISTANCE */
    public static String f1505xa8948503;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_MODEINFO_IMPROVEMENTS;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_MODEINFO_MISSING_REQUIREMENTS */
    public static String f1506xba3c95c7;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_MODEINFO_MODE;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_AZIMUTH;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_DISTANCE;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_ELEVATION;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_USE_DEFAULT;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_ANIMATION;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_ANIMATION_AVAILABLE;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_BEHAVIOR;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_MODE;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_SWINGANIMCONFIG_SPEED;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_SWINGANIMCONFIG_USE_DEFAULT */
    public static String f1507xc4bb1e2b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_SWINGANIMCONFIG_VERTICAL_DISTANCE */
    public static String f1508xc8604c00;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETFRAMINGPOSITIONCHANGED_HORIZONTAL */
    public static String f1509xe95652a1;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETFRAMINGPOSITIONCHANGED_VERTICAL */
    public static String f1510xca17ad33;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETIMAGEDETECTIONSTATE_STATE */
    public static String f1511x42e48118;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_ALTITUDE;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_DOWN_SPEED */
    public static String f1512xe3f87f41;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_EAST_SPEED */
    public static String f1513x5a648e5c;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_LATITUDE;
    public static String ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_LONGITUDE;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_NORTH_SPEED */
    public static String f1514x49f16236;
    private static String TAG = "ARFeatureFollowMe";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendConfigureGeographic(long j, byte b, float f, float f2, float f3);

    private native int nativeSendConfigureRelative(long j, byte b, float f, float f2, float f3);

    private native int nativeSendStart(long j, int i);

    private native int nativeSendStartBoomerangAnim(long j, byte b, float f, float f2);

    private native int nativeSendStartCandleAnim(long j, byte b, float f, float f2);

    private native int nativeSendStartDollySlideAnim(long j, byte b, float f, float f2, float f3);

    private native int nativeSendStartHelicoidAnim(long j, byte b, float f, float f2, float f3);

    private native int nativeSendStartSwingAnim(long j, byte b, float f, float f2);

    private native int nativeSendStop(long j);

    private native int nativeSendStopAnimation(long j);

    private native int nativeSendTargetFramingPosition(long j, byte b, byte b2);

    private native int nativeSendTargetImageDetection(long j, float f, float f2, float f3, byte b, byte b2, long j2);

    private static native String nativeStaticGetKeyFollowMeBoomerangAnimConfigDistance();

    private static native String nativeStaticGetKeyFollowMeBoomerangAnimConfigSpeed();

    private static native String nativeStaticGetKeyFollowMeBoomerangAnimConfigUsedefault();

    private static native String nativeStaticGetKeyFollowMeCandleAnimConfigSpeed();

    private static native String nativeStaticGetKeyFollowMeCandleAnimConfigUsedefault();

    private static native String nativeStaticGetKeyFollowMeCandleAnimConfigVerticaldistance();

    private static native String nativeStaticGetKeyFollowMeDollySlideAnimConfigAngle();

    private static native String nativeStaticGetKeyFollowMeDollySlideAnimConfigHorizontaldistance();

    private static native String nativeStaticGetKeyFollowMeDollySlideAnimConfigSpeed();

    private static native String nativeStaticGetKeyFollowMeDollySlideAnimConfigUsedefault();

    private static native String nativeStaticGetKeyFollowMeGeographicConfigAzimuth();

    private static native String nativeStaticGetKeyFollowMeGeographicConfigDistance();

    private static native String nativeStaticGetKeyFollowMeGeographicConfigElevation();

    private static native String nativeStaticGetKeyFollowMeGeographicConfigUsedefault();

    private static native String nativeStaticGetKeyFollowMeHelicoidAnimConfigRevolutionnb();

    private static native String nativeStaticGetKeyFollowMeHelicoidAnimConfigSpeed();

    private static native String nativeStaticGetKeyFollowMeHelicoidAnimConfigUsedefault();

    private static native String nativeStaticGetKeyFollowMeHelicoidAnimConfigVerticaldistance();

    private static native String nativeStaticGetKeyFollowMeModeInfoImprovements();

    private static native String nativeStaticGetKeyFollowMeModeInfoMissingrequirements();

    private static native String nativeStaticGetKeyFollowMeModeInfoMode();

    private static native String nativeStaticGetKeyFollowMeRelativeConfigAzimuth();

    private static native String nativeStaticGetKeyFollowMeRelativeConfigDistance();

    private static native String nativeStaticGetKeyFollowMeRelativeConfigElevation();

    private static native String nativeStaticGetKeyFollowMeRelativeConfigUsedefault();

    private static native String nativeStaticGetKeyFollowMeStateAnimation();

    private static native String nativeStaticGetKeyFollowMeStateAnimationavailable();

    private static native String nativeStaticGetKeyFollowMeStateBehavior();

    private static native String nativeStaticGetKeyFollowMeStateMode();

    private static native String nativeStaticGetKeyFollowMeSwingAnimConfigSpeed();

    private static native String nativeStaticGetKeyFollowMeSwingAnimConfigUsedefault();

    private static native String nativeStaticGetKeyFollowMeSwingAnimConfigVerticaldistance();

    private static native String nativeStaticGetKeyFollowMeTargetFramingPositionChangedHorizontal();

    private static native String nativeStaticGetKeyFollowMeTargetFramingPositionChangedVertical();

    private static native String nativeStaticGetKeyFollowMeTargetImageDetectionStateState();

    private static native String nativeStaticGetKeyFollowMeTargetTrajectoryAltitude();

    private static native String nativeStaticGetKeyFollowMeTargetTrajectoryDownspeed();

    private static native String nativeStaticGetKeyFollowMeTargetTrajectoryEastspeed();

    private static native String nativeStaticGetKeyFollowMeTargetTrajectoryLatitude();

    private static native String nativeStaticGetKeyFollowMeTargetTrajectoryLongitude();

    private static native String nativeStaticGetKeyFollowMeTargetTrajectoryNorthspeed();

    static {
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_MODE = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_BEHAVIOR = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_ANIMATION = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_ANIMATION_AVAILABLE = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_MODEINFO_MODE = "";
        f1506xba3c95c7 = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_MODEINFO_IMPROVEMENTS = "";
        f1502x275b1a53 = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_GEOGRAPHICCONFIG_DISTANCE = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_GEOGRAPHICCONFIG_ELEVATION = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_GEOGRAPHICCONFIG_AZIMUTH = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_USE_DEFAULT = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_DISTANCE = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_ELEVATION = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_AZIMUTH = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_LATITUDE = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_LONGITUDE = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_ALTITUDE = "";
        f1514x49f16236 = "";
        f1513x5a648e5c = "";
        f1512xe3f87f41 = "";
        f1504x8ac3156e = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_HELICOIDANIMCONFIG_SPEED = "";
        f1503x5fccf74f = "";
        f1505xa8948503 = "";
        f1507xc4bb1e2b = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_SWINGANIMCONFIG_SPEED = "";
        f1508xc8604c00 = "";
        f1497x58c4e62f = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_BOOMERANGANIMCONFIG_SPEED = "";
        f1496x66b6eacf = "";
        f1498xa38d2eba = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_CANDLEANIMCONFIG_SPEED = "";
        f1499xb201754f = "";
        f1501x39718710 = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_DOLLYSLIDEANIMCONFIG_SPEED = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_DOLLYSLIDEANIMCONFIG_ANGLE = "";
        f1500x5420f277 = "";
        f1509xe95652a1 = "";
        f1510xca17ad33 = "";
        f1511x42e48118 = "";
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_MODE = nativeStaticGetKeyFollowMeStateMode();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_BEHAVIOR = nativeStaticGetKeyFollowMeStateBehavior();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_ANIMATION = nativeStaticGetKeyFollowMeStateAnimation();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_STATE_ANIMATION_AVAILABLE = nativeStaticGetKeyFollowMeStateAnimationavailable();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_MODEINFO_MODE = nativeStaticGetKeyFollowMeModeInfoMode();
        f1506xba3c95c7 = nativeStaticGetKeyFollowMeModeInfoMissingrequirements();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_MODEINFO_IMPROVEMENTS = nativeStaticGetKeyFollowMeModeInfoImprovements();
        f1502x275b1a53 = nativeStaticGetKeyFollowMeGeographicConfigUsedefault();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_GEOGRAPHICCONFIG_DISTANCE = nativeStaticGetKeyFollowMeGeographicConfigDistance();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_GEOGRAPHICCONFIG_ELEVATION = nativeStaticGetKeyFollowMeGeographicConfigElevation();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_GEOGRAPHICCONFIG_AZIMUTH = nativeStaticGetKeyFollowMeGeographicConfigAzimuth();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_USE_DEFAULT = nativeStaticGetKeyFollowMeRelativeConfigUsedefault();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_DISTANCE = nativeStaticGetKeyFollowMeRelativeConfigDistance();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_ELEVATION = nativeStaticGetKeyFollowMeRelativeConfigElevation();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_RELATIVECONFIG_AZIMUTH = nativeStaticGetKeyFollowMeRelativeConfigAzimuth();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_LATITUDE = nativeStaticGetKeyFollowMeTargetTrajectoryLatitude();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_LONGITUDE = nativeStaticGetKeyFollowMeTargetTrajectoryLongitude();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_TARGETTRAJECTORY_ALTITUDE = nativeStaticGetKeyFollowMeTargetTrajectoryAltitude();
        f1514x49f16236 = nativeStaticGetKeyFollowMeTargetTrajectoryNorthspeed();
        f1513x5a648e5c = nativeStaticGetKeyFollowMeTargetTrajectoryEastspeed();
        f1512xe3f87f41 = nativeStaticGetKeyFollowMeTargetTrajectoryDownspeed();
        f1504x8ac3156e = nativeStaticGetKeyFollowMeHelicoidAnimConfigUsedefault();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_HELICOIDANIMCONFIG_SPEED = nativeStaticGetKeyFollowMeHelicoidAnimConfigSpeed();
        f1503x5fccf74f = nativeStaticGetKeyFollowMeHelicoidAnimConfigRevolutionnb();
        f1505xa8948503 = nativeStaticGetKeyFollowMeHelicoidAnimConfigVerticaldistance();
        f1507xc4bb1e2b = nativeStaticGetKeyFollowMeSwingAnimConfigUsedefault();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_SWINGANIMCONFIG_SPEED = nativeStaticGetKeyFollowMeSwingAnimConfigSpeed();
        f1508xc8604c00 = nativeStaticGetKeyFollowMeSwingAnimConfigVerticaldistance();
        f1497x58c4e62f = nativeStaticGetKeyFollowMeBoomerangAnimConfigUsedefault();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_BOOMERANGANIMCONFIG_SPEED = nativeStaticGetKeyFollowMeBoomerangAnimConfigSpeed();
        f1496x66b6eacf = nativeStaticGetKeyFollowMeBoomerangAnimConfigDistance();
        f1498xa38d2eba = nativeStaticGetKeyFollowMeCandleAnimConfigUsedefault();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_CANDLEANIMCONFIG_SPEED = nativeStaticGetKeyFollowMeCandleAnimConfigSpeed();
        f1499xb201754f = nativeStaticGetKeyFollowMeCandleAnimConfigVerticaldistance();
        f1501x39718710 = nativeStaticGetKeyFollowMeDollySlideAnimConfigUsedefault();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_DOLLYSLIDEANIMCONFIG_SPEED = nativeStaticGetKeyFollowMeDollySlideAnimConfigSpeed();
        ARCONTROLLER_DICTIONARY_KEY_FOLLOW_ME_DOLLYSLIDEANIMCONFIG_ANGLE = nativeStaticGetKeyFollowMeDollySlideAnimConfigAngle();
        f1500x5420f277 = nativeStaticGetKeyFollowMeDollySlideAnimConfigHorizontaldistance();
        f1509xe95652a1 = nativeStaticGetKeyFollowMeTargetFramingPositionChangedHorizontal();
        f1510xca17ad33 = nativeStaticGetKeyFollowMeTargetFramingPositionChangedVertical();
        f1511x42e48118 = nativeStaticGetKeyFollowMeTargetImageDetectionStateState();
    }

    public ARFeatureFollowMe(long nativeFeature) {
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

    public ARCONTROLLER_ERROR_ENUM sendStart(ARCOMMANDS_FOLLOW_ME_MODE_ENUM _mode) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendStart(this.jniFeature, _mode.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendStop() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendStop(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendConfigureGeographic(byte _use_default, float _distance, float _elevation, float _azimuth) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendConfigureGeographic(this.jniFeature, _use_default, _distance, _elevation, _azimuth));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendConfigureRelative(byte _use_default, float _distance, float _elevation, float _azimuth) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendConfigureRelative(this.jniFeature, _use_default, _distance, _elevation, _azimuth));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendStopAnimation() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendStopAnimation(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendStartHelicoidAnim(byte _use_default, float _speed, float _revolution_number, float _vertical_distance) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendStartHelicoidAnim(this.jniFeature, _use_default, _speed, _revolution_number, _vertical_distance));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendStartSwingAnim(byte _use_default, float _speed, float _vertical_distance) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendStartSwingAnim(this.jniFeature, _use_default, _speed, _vertical_distance));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendStartBoomerangAnim(byte _use_default, float _speed, float _distance) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendStartBoomerangAnim(this.jniFeature, _use_default, _speed, _distance));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendStartCandleAnim(byte _use_default, float _speed, float _vertical_distance) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendStartCandleAnim(this.jniFeature, _use_default, _speed, _vertical_distance));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendStartDollySlideAnim(byte _use_default, float _speed, float _angle, float _horizontal_distance) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendStartDollySlideAnim(this.jniFeature, _use_default, _speed, _angle, _horizontal_distance));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendTargetFramingPosition(byte _horizontal, byte _vertical) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendTargetFramingPosition(this.jniFeature, _horizontal, _vertical));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendTargetImageDetection(float _target_azimuth, float _target_elevation, float _change_of_scale, byte _confidence_index, byte _is_new_selection, long _timestamp) {
        ARFeatureFollowMe aRFeatureFollowMe = this;
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            try {
                if (aRFeatureFollowMe.initOk) {
                    int nativeError = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendTargetImageDetection(aRFeatureFollowMe.jniFeature, _target_azimuth, _target_elevation, _change_of_scale, _confidence_index, _is_new_selection, _timestamp));
                }
            } finally {
                Object obj = r0;
            }
        }
        return error;
    }
}
