package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    APP_0(0, "Action handled by the application"),
    APP_1(1, "Action handled by the application"),
    APP_2(2, "Action handled by the application"),
    APP_3(3, "Action handled by the application"),
    APP_4(4, "Action handled by the application"),
    APP_5(5, "Action handled by the application"),
    APP_6(6, "Action handled by the application"),
    APP_7(7, "Action handled by the application"),
    APP_8(8, "Action handled by the application"),
    APP_9(9, "Action handled by the application"),
    APP_10(10, "Action handled by the application"),
    APP_11(11, "Action handled by the application"),
    APP_12(12, "Action handled by the application"),
    APP_13(13, "Action handled by the application"),
    APP_14(14, "Action handled by the application"),
    APP_15(15, "Action handled by the application"),
    RETURN_HOME(16, "Return to home"),
    TAKEOFF_LAND(17, "Take off or land"),
    VIDEO_RECORD(18, "Start/stop video record"),
    TAKE_PICTURE(19, "Take a picture"),
    CAMERA_EXPOSITION_INC(20, "Increment camera exposition"),
    CAMERA_EXPOSITION_DEC(21, "Decrement camera exposition"),
    FLIP_LEFT(22, "Flip left"),
    FLIP_RIGHT(23, "Flip right"),
    FLIP_FRONT(24, "Flip front"),
    FLIP_BACK(25, "Flip back"),
    EMERGENCY(26, "Emergency motors shutdown"),
    CENTER_CAMERA(27, "Reset camera to its default position"),
    CYCLE_HUD(28, "Cycle between different hud configurations on HDMI (Skycontroller 1 only)");
    
    static HashMap<Integer, ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM retVal = (ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return UNKNOWN;
        }
        return retVal;
    }

    public String toString() {
        if (this.comment != null) {
            return this.comment;
        }
        return super.toString();
    }
}
