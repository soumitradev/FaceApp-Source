package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_FOLLOW_ME_MODE_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    NONE(0, "No follow me"),
    LOOK_AT(1, "Look at the target without moving automatically"),
    GEOGRAPHIC(2, "Follow the target keeping the same vector"),
    RELATIVE(3, "Follow the target keeping the same orientation to its direction");
    
    static HashMap<Integer, ARCOMMANDS_FOLLOW_ME_MODE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_FOLLOW_ME_MODE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_FOLLOW_ME_MODE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_FOLLOW_ME_MODE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_FOLLOW_ME_MODE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_FOLLOW_ME_MODE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_FOLLOW_ME_MODE_ENUM retVal = (ARCOMMANDS_FOLLOW_ME_MODE_ENUM) valuesList.get(Integer.valueOf(value));
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
