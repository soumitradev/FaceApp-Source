package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_FOLLOW_ME_BEHAVIOR_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    IDLE(0, "Drone is not moving according to the target This means that at least one required input is missing"),
    FOLLOW(1, "Follow the target"),
    LOOK_AT(2, "Look at the target without moving");
    
    static HashMap<Integer, ARCOMMANDS_FOLLOW_ME_BEHAVIOR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_FOLLOW_ME_BEHAVIOR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_FOLLOW_ME_BEHAVIOR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_FOLLOW_ME_BEHAVIOR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_FOLLOW_ME_BEHAVIOR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_FOLLOW_ME_BEHAVIOR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_FOLLOW_ME_BEHAVIOR_ENUM retVal = (ARCOMMANDS_FOLLOW_ME_BEHAVIOR_ENUM) valuesList.get(Integer.valueOf(value));
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
