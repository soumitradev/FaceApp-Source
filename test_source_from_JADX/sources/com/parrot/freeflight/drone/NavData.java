package com.parrot.freeflight.drone;

public class NavData {
    public static final int ERROR_STATE_ALERT_CAMERA = 13;
    public static final int ERROR_STATE_ALERT_ULTRASOUND = 15;
    public static final int ERROR_STATE_ALERT_VBAT_LOW = 14;
    public static final int ERROR_STATE_ALERT_VISION = 16;
    public static final int ERROR_STATE_EMERGENCY_ANGLE_OUT_OF_RANGE = 8;
    public static final int ERROR_STATE_EMERGENCY_CAMERA = 5;
    public static final int ERROR_STATE_EMERGENCY_CUTOUT = 3;
    public static final int ERROR_STATE_EMERGENCY_MOTORS = 4;
    public static final int ERROR_STATE_EMERGENCY_PIC_VERSION = 7;
    public static final int ERROR_STATE_EMERGENCY_PIC_WATCHDOG = 6;
    public static final int ERROR_STATE_EMERGENCY_ULTRASOUND = 11;
    public static final int ERROR_STATE_EMERGENCY_UNKNOWN = 12;
    public static final int ERROR_STATE_EMERGENCY_USER_EL = 10;
    public static final int ERROR_STATE_EMERGENCY_VBAT_LOW = 9;
    public static final int ERROR_STATE_NAVDATA_CONNECTION = 1;
    public static final int ERROR_STATE_NONE = 0;
    public static final int ERROR_STATE_START_NOT_RECEIVED = 2;
    public int batteryStatus = 0;
    public boolean cameraReady = false;
    public int emergencyState = 0;
    public boolean flying = false;
    public boolean initialized = false;
    public int numFrames;
    public boolean recordReady = false;
    public boolean recording;
    public boolean usbActive = false;
    public int usbRemainingTime = -1;

    public void copyFrom(NavData navData) {
        this.batteryStatus = navData.batteryStatus;
        this.emergencyState = navData.emergencyState;
        this.flying = navData.flying;
        this.initialized = navData.initialized;
        this.recording = navData.recording;
        this.numFrames = navData.numFrames;
        this.usbActive = navData.usbActive;
        this.usbRemainingTime = navData.usbRemainingTime;
        this.cameraReady = navData.cameraReady;
        this.recordReady = navData.recordReady;
    }

    public static final boolean isEmergency(int code) {
        if (code <= 0 || code >= 13) {
            return false;
        }
        return true;
    }
}
