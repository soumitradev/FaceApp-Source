package com.parrot.arsdk.ardatatransfer;

import java.util.HashMap;

public enum ARDATATRANSFER_FTP_RESUME_ENUM {
    FTP_RESUME_FALSE(0),
    FTP_RESUME_TRUE(1);
    
    static HashMap<Integer, ARDATATRANSFER_FTP_RESUME_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARDATATRANSFER_FTP_RESUME_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARDATATRANSFER_FTP_RESUME_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARDATATRANSFER_FTP_RESUME_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARDATATRANSFER_FTP_RESUME_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARDATATRANSFER_FTP_RESUME_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        return (ARDATATRANSFER_FTP_RESUME_ENUM) valuesList.get(Integer.valueOf(value));
    }

    public String toString() {
        if (this.comment != null) {
            return this.comment;
        }
        return super.toString();
    }
}
