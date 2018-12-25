package com.parrot.arsdk.arstream;

import java.util.HashMap;

public enum ARSTREAM_ERROR_ENUM {
    eARSTREAM_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARSTREAM_OK(0, "No error"),
    ARSTREAM_ERROR_BAD_PARAMETERS(1, "Bad parameters"),
    ARSTREAM_ERROR_ALLOC(2, "Unable to allocate data"),
    ARSTREAM_ERROR_FRAME_TOO_LARGE(3, "Bad parameter : frame too large"),
    ARSTREAM_ERROR_BUSY(4, "Object is busy and the operation can not be applied on running objects"),
    ARSTREAM_ERROR_QUEUE_FULL(5, "Frame queue is full");
    
    static HashMap<Integer, ARSTREAM_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARSTREAM_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARSTREAM_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARSTREAM_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARSTREAM_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARSTREAM_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARSTREAM_ERROR_ENUM retVal = (ARSTREAM_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARSTREAM_ERROR_UNKNOWN_ENUM_VALUE;
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
