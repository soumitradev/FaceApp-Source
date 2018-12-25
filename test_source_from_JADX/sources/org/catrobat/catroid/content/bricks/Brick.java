package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.ui.adapter.BrickAdapter;

public interface Brick extends Serializable, Cloneable {
    public static final int ARDRONE_SUPPORT = 5;
    public static final int BLUETOOTH_LEGO_EV3 = 20;
    public static final int BLUETOOTH_LEGO_NXT = 2;
    public static final int BLUETOOTH_PHIRO = 10;
    public static final int BLUETOOTH_SENSORS_ARDUINO = 6;
    public static final int CAMERA_BACK = 11;
    public static final int CAMERA_FLASH = 8;
    public static final int CAMERA_FRONT = 12;
    public static final int CAST_REQUIRED = 22;
    public static final int COLLISION = 19;
    public static final int FACE_DETECTION = 4;
    public static final int JUMPING_SUMO = 23;
    public static final int NETWORK_CONNECTION = 21;
    public static final int NFC_ADAPTER = 16;
    public static final int PHYSICS = 3;
    public static final int SENSOR_ACCELERATION = 13;
    public static final int SENSOR_COMPASS = 15;
    public static final int SENSOR_GPS = 18;
    public static final int SENSOR_INCLINATION = 14;
    public static final int SOCKET_RASPI = 7;
    public static final int TEXT_TO_SPEECH = 1;
    public static final int VIBRATOR = 9;
    public static final int VIDEO = 17;

    public enum BrickField {
        COLOR,
        COLOR_CHANGE,
        BRIGHTNESS,
        BRIGHTNESS_CHANGE,
        X_POSITION,
        Y_POSITION,
        X_POSITION_CHANGE,
        Y_POSITION_CHANGE,
        TRANSPARENCY,
        TRANSPARENCY_CHANGE,
        SIZE,
        SIZE_CHANGE,
        VOLUME,
        VOLUME_CHANGE,
        X_DESTINATION,
        Y_DESTINATION,
        STEPS,
        DURATION_IN_SECONDS,
        DEGREES,
        TURN_RIGHT_DEGREES,
        TURN_LEFT_DEGREES,
        TIME_TO_WAIT_IN_SECONDS,
        VARIABLE,
        VARIABLE_CHANGE,
        PEN_SIZE,
        PEN_COLOR_RED,
        PEN_COLOR_GREEN,
        PEN_COLOR_BLUE,
        IF_CONDITION,
        TIMES_TO_REPEAT,
        VIBRATE_DURATION_IN_SECONDS,
        USER_BRICK,
        NOTE,
        SPEAK,
        SHOWTEXT,
        STRING,
        ROTATION_STYLE,
        REPEAT_UNTIL_CONDITION,
        ASK_QUESTION,
        NFC_NDEF_MESSAGE,
        ASK_SPEECH_QUESTION,
        LOOK_INDEX,
        LEGO_NXT_SPEED,
        LEGO_NXT_DEGREES,
        LEGO_NXT_FREQUENCY,
        LEGO_NXT_DURATION_IN_SECONDS,
        LEGO_EV3_FREQUENCY,
        LEGO_EV3_DURATION_IN_SECONDS,
        LEGO_EV3_VOLUME,
        LEGO_EV3_SPEED,
        LEGO_EV3_POWER,
        LEGO_EV3_PERIOD_IN_SECONDS,
        LEGO_EV3_DEGREES,
        DRONE_TIME_TO_FLY_IN_SECONDS,
        LIST_ADD_ITEM,
        LIST_DELETE_ITEM,
        INSERT_ITEM_INTO_USERLIST_VALUE,
        INSERT_ITEM_INTO_USERLIST_INDEX,
        REPLACE_ITEM_IN_USERLIST_VALUE,
        REPLACE_ITEM_IN_USERLIST_INDEX,
        DRONE_POWER_IN_PERCENT,
        DRONE_ALTITUDE_LIMIT,
        DRONE_VERTICAL_SPEED_MAX,
        DRONE_ROTATION_MAX,
        DRONE_TILT_ANGLE,
        JUMPING_SUMO_SPEED,
        JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS,
        JUMPING_SUMO_VOLUME,
        JUMPING_SUMO_ROTATE,
        PHIRO_SPEED,
        PHIRO_DURATION_IN_SECONDS,
        PHIRO_LIGHT_RED,
        PHIRO_LIGHT_GREEN,
        PHIRO_LIGHT_BLUE,
        PHYSICS_BOUNCE_FACTOR,
        PHYSICS_FRICTION,
        PHYSICS_GRAVITY_X,
        PHYSICS_GRAVITY_Y,
        PHYSICS_MASS,
        PHYSICS_VELOCITY_X,
        PHYSICS_VELOCITY_Y,
        PHYSICS_TURN_LEFT_SPEED,
        PHYSICS_TURN_RIGHT_SPEED,
        ARDUINO_ANALOG_PIN_VALUE,
        ARDUINO_ANALOG_PIN_NUMBER,
        ARDUINO_DIGITAL_PIN_VALUE,
        ARDUINO_DIGITAL_PIN_NUMBER,
        RASPI_DIGITAL_PIN_VALUE,
        RASPI_DIGITAL_PIN_NUMBER,
        RASPI_PWM_PERCENTAGE,
        RASPI_PWM_FREQUENCY;
        
        public static final BrickField[] EXPECTS_STRING_VALUE = null;

        static {
            EXPECTS_STRING_VALUE = new BrickField[]{VARIABLE, NOTE, SPEAK, STRING, ASK_QUESTION, NFC_NDEF_MESSAGE, ASK_SPEECH_QUESTION, LIST_ADD_ITEM, INSERT_ITEM_INTO_USERLIST_VALUE, REPLACE_ITEM_IN_USERLIST_VALUE};
        }

        public static boolean isExpectingStringValue(BrickField field) {
            for (BrickField bf : EXPECTS_STRING_VALUE) {
                if (bf.equals(field)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Resources {
    }

    public static class ResourcesSet extends HashSet<Integer> {
        public boolean add(Integer integer) {
            return super.add(integer);
        }
    }

    List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction scriptSequenceAction);

    void addRequiredResources(ResourcesSet resourcesSet);

    Brick clone() throws CloneNotSupportedException;

    CheckBox getCheckBox();

    View getPrototypeView(Context context);

    View getView(Context context);

    boolean isCommentedOut();

    void setAlpha(int i);

    void setBrickAdapter(BrickAdapter brickAdapter);

    void setCommentedOut(boolean z);
}
