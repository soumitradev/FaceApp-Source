package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_RC_CHANNEL_TYPE_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    INVALID(0, "Invalid channel physical type."),
    SIGNED_AXIS(1, "Signed axis type."),
    UNSIGNED_AXIS(2, "Unsigned axis type."),
    MONOSTABLE_BUTTON(3, "Monostable button type."),
    BISTABLE_BUTTON(4, "Bistable button type."),
    TRISTATE_BUTTON(5, "Tristate button type."),
    ROTARY_BUTTON(6, "Rotary button type.");
    
    static HashMap<Integer, ARCOMMANDS_RC_CHANNEL_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_RC_CHANNEL_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_RC_CHANNEL_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_RC_CHANNEL_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_RC_CHANNEL_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_RC_CHANNEL_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_RC_CHANNEL_TYPE_ENUM retVal = (ARCOMMANDS_RC_CHANNEL_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
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
