package org.catrobat.catroid.formulaeditor;

import android.util.Log;

public enum Sensors {
    X_ACCELERATION,
    Y_ACCELERATION,
    Z_ACCELERATION,
    COMPASS_DIRECTION,
    X_INCLINATION,
    Y_INCLINATION,
    LOUDNESS,
    LATITUDE,
    LONGITUDE,
    LOCATION_ACCURACY,
    ALTITUDE,
    DATE_YEAR,
    DATE_MONTH,
    DATE_DAY,
    DATE_WEEKDAY,
    TIME_HOUR,
    TIME_MINUTE,
    TIME_SECOND,
    FACE_DETECTED,
    FACE_SIZE,
    FACE_X_POSITION,
    FACE_Y_POSITION,
    OBJECT_X(true),
    OBJECT_Y(true),
    OBJECT_TRANSPARENCY(true),
    OBJECT_BRIGHTNESS(true),
    OBJECT_COLOR(true),
    OBJECT_SIZE(true),
    OBJECT_ROTATION(true),
    OBJECT_LAYER(true),
    OBJECT_DISTANCE_TO(true),
    NXT_SENSOR_1,
    NXT_SENSOR_2,
    NXT_SENSOR_3,
    NXT_SENSOR_4,
    EV3_SENSOR_1,
    EV3_SENSOR_2,
    EV3_SENSOR_3,
    EV3_SENSOR_4,
    PHIRO_FRONT_LEFT,
    PHIRO_FRONT_RIGHT,
    PHIRO_SIDE_LEFT,
    PHIRO_SIDE_RIGHT,
    PHIRO_BOTTOM_LEFT,
    PHIRO_BOTTOM_RIGHT,
    DRONE_BATTERY_STATUS,
    DRONE_EMERGENCY_STATE,
    DRONE_FLYING,
    DRONE_INITIALIZED,
    DRONE_USB_ACTIVE,
    DRONE_USB_REMAINING_TIME,
    DRONE_CAMERA_READY,
    DRONE_RECORD_READY,
    DRONE_RECORDING,
    DRONE_NUM_FRAMES,
    COLLIDES_WITH_EDGE(true),
    COLLIDES_WITH_FINGER(true),
    OBJECT_X_VELOCITY(true),
    OBJECT_Y_VELOCITY(true),
    OBJECT_ANGULAR_VELOCITY(true),
    LAST_FINGER_INDEX,
    FINGER_X,
    FINGER_Y,
    FINGER_TOUCHED,
    OBJECT_LOOK_NUMBER(true),
    OBJECT_LOOK_NAME(true),
    OBJECT_BACKGROUND_NUMBER(true),
    OBJECT_BACKGROUND_NAME(true),
    NFC_TAG_ID(true),
    NFC_TAG_MESSAGE(true),
    GAMEPAD_A_PRESSED,
    GAMEPAD_B_PRESSED,
    GAMEPAD_UP_PRESSED,
    GAMEPAD_DOWN_PRESSED,
    GAMEPAD_LEFT_PRESSED,
    GAMEPAD_RIGHT_PRESSED;
    
    public static final String TAG = null;
    public final boolean isObjectSensor;

    static {
        TAG = Sensors.class.getSimpleName();
    }

    private Sensors(boolean isObjectSensor) {
        this.isObjectSensor = isObjectSensor;
    }

    public static boolean isSensor(String value) {
        return getSensorByValue(value) != null;
    }

    public static Sensors getSensorByValue(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, Log.getStackTraceString(illegalArgumentException));
            return null;
        }
    }
}
