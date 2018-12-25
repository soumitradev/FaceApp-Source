package com.parrot.arsdk.arutils;

import java.util.HashMap;

public enum ARUTILS_FTP_TYPE_ENUM {
    eARUTILS_FTP_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARUTILS_FTP_TYPE_GENERIC(0),
    ARUTILS_FTP_TYPE_UPDATE(1),
    ARUTILS_FTP_TYPE_FLIGHTPLAN(2),
    ARUTILS_FTP_TYPE_MAX(3);
    
    static HashMap<Integer, ARUTILS_FTP_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARUTILS_FTP_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARUTILS_FTP_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARUTILS_FTP_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARUTILS_FTP_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARUTILS_FTP_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARUTILS_FTP_TYPE_ENUM retVal = (ARUTILS_FTP_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARUTILS_FTP_TYPE_UNKNOWN_ENUM_VALUE;
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
