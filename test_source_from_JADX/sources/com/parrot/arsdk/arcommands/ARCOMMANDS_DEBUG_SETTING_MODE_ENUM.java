package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_DEBUG_SETTING_MODE_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    READ_ONLY(0, "Controller can only read setting."),
    READ_WRITE(1, "Controller can read and write setting.");
    
    static HashMap<Integer, ARCOMMANDS_DEBUG_SETTING_MODE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_DEBUG_SETTING_MODE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_DEBUG_SETTING_MODE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_DEBUG_SETTING_MODE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_DEBUG_SETTING_MODE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_DEBUG_SETTING_MODE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_DEBUG_SETTING_MODE_ENUM retVal = (ARCOMMANDS_DEBUG_SETTING_MODE_ENUM) valuesList.get(Integer.valueOf(value));
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
