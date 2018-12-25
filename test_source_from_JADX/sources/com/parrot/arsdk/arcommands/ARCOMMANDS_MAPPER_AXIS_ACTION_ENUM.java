package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM {
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
    ROLL(16, "roll"),
    PITCH(17, "pitch"),
    YAW(18, "yaw"),
    GAZ(19, "gaz"),
    CAMERA_PAN(20, "camera pan"),
    CAMERA_TILT(21, "camera tilt");
    
    static HashMap<Integer, ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM retVal = (ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM) valuesList.get(Integer.valueOf(value));
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
