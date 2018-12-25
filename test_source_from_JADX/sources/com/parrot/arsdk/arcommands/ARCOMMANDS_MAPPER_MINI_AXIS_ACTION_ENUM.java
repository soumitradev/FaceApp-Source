package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ROLL(0, "roll"),
    PITCH(1, "pitch"),
    YAW(2, "yaw"),
    GAZ(3, "gaz");
    
    static HashMap<Integer, ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM retVal = (ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM) valuesList.get(Integer.valueOf(value));
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
