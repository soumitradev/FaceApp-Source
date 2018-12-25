package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_FILTER_ERROR_ENUM {
    eARCOMMANDS_FILTER_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_FILTER_OK(0, "No error."),
    ARCOMMANDS_FILTER_ERROR_ALLOC(1, "Memory allocation error."),
    ARCOMMANDS_FILTER_ERROR_BAD_STATUS(2, "The given status is not a valid status."),
    ARCOMMANDS_FILTER_ERROR_BAD_FILTER(3, "The given filter is not a valid filter."),
    ARCOMMANDS_FILTER_ERROR_BAD_BUFFER(4, "The given buffer is not a valid buffer."),
    ARCOMMANDS_FILTER_ERROR_OTHER(5, "Any other error.");
    
    static HashMap<Integer, ARCOMMANDS_FILTER_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_FILTER_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_FILTER_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_FILTER_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_FILTER_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_FILTER_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_FILTER_ERROR_ENUM retVal = (ARCOMMANDS_FILTER_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_FILTER_ERROR_UNKNOWN_ENUM_VALUE;
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
