package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_GENERATOR_ERROR_ENUM {
    eARCOMMANDS_GENERATOR_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_GENERATOR_OK(0, "No error occured"),
    ARCOMMANDS_GENERATOR_ERROR_BAD_ARGS(1, "At least one of the arguments is invalid"),
    ARCOMMANDS_GENERATOR_ERROR_NOT_ENOUGH_SPACE(2, "The given output buffer was not large enough for the command"),
    ARCOMMANDS_GENERATOR_ERROR(3, "Any other error");
    
    static HashMap<Integer, ARCOMMANDS_GENERATOR_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_GENERATOR_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_GENERATOR_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_GENERATOR_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_GENERATOR_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_GENERATOR_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_GENERATOR_ERROR_ENUM retVal = (ARCOMMANDS_GENERATOR_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_GENERATOR_ERROR_UNKNOWN_ENUM_VALUE;
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
