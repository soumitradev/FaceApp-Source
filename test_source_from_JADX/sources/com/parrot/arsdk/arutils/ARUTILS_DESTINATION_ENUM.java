package com.parrot.arsdk.arutils;

import java.util.HashMap;

public enum ARUTILS_DESTINATION_ENUM {
    eARUTILS_DESTINATION_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARUTILS_DESTINATION_DRONE(0),
    ARUTILS_DESTINATION_SKYCONTROLLER(1),
    ARUTILS_DESTINATION_MAX(2);
    
    static HashMap<Integer, ARUTILS_DESTINATION_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARUTILS_DESTINATION_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARUTILS_DESTINATION_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARUTILS_DESTINATION_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARUTILS_DESTINATION_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARUTILS_DESTINATION_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARUTILS_DESTINATION_ENUM retVal = (ARUTILS_DESTINATION_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARUTILS_DESTINATION_UNKNOWN_ENUM_VALUE;
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
