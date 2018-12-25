package com.parrot.arsdk.arutils;

import java.util.HashMap;

public enum ARUTILS_FTP_RESUME_ENUM {
    eARUTILS_FTP_RESUME_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    FTP_RESUME_FALSE(0),
    FTP_RESUME_TRUE(1);
    
    static HashMap<Integer, ARUTILS_FTP_RESUME_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARUTILS_FTP_RESUME_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARUTILS_FTP_RESUME_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARUTILS_FTP_RESUME_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARUTILS_FTP_RESUME_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARUTILS_FTP_RESUME_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARUTILS_FTP_RESUME_ENUM retVal = (ARUTILS_FTP_RESUME_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARUTILS_FTP_RESUME_UNKNOWN_ENUM_VALUE;
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
