package com.parrot.arsdk.arsal;

import java.util.HashMap;

public enum ARSAL_PRINT_LEVEL_ENUM {
    eARSAL_PRINT_LEVEL_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARSAL_PRINT_FATAL(0, "The fatal level. The highest level, always printed"),
    ARSAL_PRINT_ERROR(1, "The error level."),
    ARSAL_PRINT_WARNING(2, "The warning level."),
    ARSAL_PRINT_INFO(3, "The info level."),
    ARSAL_PRINT_DEBUG(4, "The debug level."),
    ARSAL_PRINT_VERBOSE(5, "The verbose level. The lowest usable level"),
    ARSAL_PRINT_MAX(6, "The maximum of enum, do not use !");
    
    static HashMap<Integer, ARSAL_PRINT_LEVEL_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARSAL_PRINT_LEVEL_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARSAL_PRINT_LEVEL_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARSAL_PRINT_LEVEL_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARSAL_PRINT_LEVEL_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARSAL_PRINT_LEVEL_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARSAL_PRINT_LEVEL_ENUM retVal = (ARSAL_PRINT_LEVEL_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARSAL_PRINT_LEVEL_UNKNOWN_ENUM_VALUE;
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
