package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_ENUM {
    eARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_OK(0, "No Error"),
    ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_UNKNOWN(1, "Unknown generic error"),
    ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_MAX(2);
    
    static HashMap<Integer, ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_ENUM retVal = (ARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_ANIMATIONSSTATE_LIST_ERROR_UNKNOWN_ENUM_VALUE;
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
