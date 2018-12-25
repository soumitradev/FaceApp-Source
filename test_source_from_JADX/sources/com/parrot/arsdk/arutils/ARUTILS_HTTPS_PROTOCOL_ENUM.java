package com.parrot.arsdk.arutils;

import java.util.HashMap;

public enum ARUTILS_HTTPS_PROTOCOL_ENUM {
    eARUTILS_HTTPS_PROTOCOL_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    HTTPS_PROTOCOL_FALSE(0),
    HTTPS_PROTOCOL_TRUE(1);
    
    static HashMap<Integer, ARUTILS_HTTPS_PROTOCOL_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARUTILS_HTTPS_PROTOCOL_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARUTILS_HTTPS_PROTOCOL_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARUTILS_HTTPS_PROTOCOL_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARUTILS_HTTPS_PROTOCOL_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARUTILS_HTTPS_PROTOCOL_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARUTILS_HTTPS_PROTOCOL_ENUM retVal = (ARUTILS_HTTPS_PROTOCOL_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARUTILS_HTTPS_PROTOCOL_UNKNOWN_ENUM_VALUE;
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
