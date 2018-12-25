package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_RC_RECEIVER_STATE_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    CONNECTED(0, "RC drone receiver connected to a RC."),
    DISCONNECTED(1, "RC drone receiver not connected to a RC.");
    
    static HashMap<Integer, ARCOMMANDS_RC_RECEIVER_STATE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_RC_RECEIVER_STATE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_RC_RECEIVER_STATE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_RC_RECEIVER_STATE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_RC_RECEIVER_STATE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_RC_RECEIVER_STATE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_RC_RECEIVER_STATE_ENUM retVal = (ARCOMMANDS_RC_RECEIVER_STATE_ENUM) valuesList.get(Integer.valueOf(value));
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
