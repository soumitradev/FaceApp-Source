package com.parrot.arsdk.arsal;

import java.util.HashMap;

public enum ARSAL_FTW_TYPE_ENUM {
    eARSAL_FTW_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARSAL_FTW_F(0),
    ARSAL_FTW_D(1);
    
    static HashMap<Integer, ARSAL_FTW_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARSAL_FTW_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARSAL_FTW_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARSAL_FTW_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARSAL_FTW_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARSAL_FTW_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARSAL_FTW_TYPE_ENUM retVal = (ARSAL_FTW_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARSAL_FTW_TYPE_UNKNOWN_ENUM_VALUE;
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
