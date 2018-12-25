package com.parrot.arsdk.ardatatransfer;

import java.util.HashMap;

public enum ARDATATRANSFER_UPLOADER_RESUME_ENUM {
    eARDATATRANSFER_UPLOADER_RESUME_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARDATATRANSFER_UPLOADER_RESUME_FALSE(0),
    ARDATATRANSFER_UPLOADER_RESUME_TRUE(1);
    
    static HashMap<Integer, ARDATATRANSFER_UPLOADER_RESUME_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARDATATRANSFER_UPLOADER_RESUME_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARDATATRANSFER_UPLOADER_RESUME_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARDATATRANSFER_UPLOADER_RESUME_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARDATATRANSFER_UPLOADER_RESUME_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARDATATRANSFER_UPLOADER_RESUME_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARDATATRANSFER_UPLOADER_RESUME_ENUM retVal = (ARDATATRANSFER_UPLOADER_RESUME_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARDATATRANSFER_UPLOADER_RESUME_UNKNOWN_ENUM_VALUE;
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
