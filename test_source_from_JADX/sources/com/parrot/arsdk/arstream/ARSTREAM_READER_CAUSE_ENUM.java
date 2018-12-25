package com.parrot.arsdk.arstream;

import java.util.HashMap;

public enum ARSTREAM_READER_CAUSE_ENUM {
    eARSTREAM_READER_CAUSE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARSTREAM_READER_CAUSE_FRAME_COMPLETE(0, "Frame is complete (no error)"),
    ARSTREAM_READER_CAUSE_FRAME_TOO_SMALL(1, "Frame buffer is too small for the frame on the network"),
    ARSTREAM_READER_CAUSE_COPY_COMPLETE(2, "Copy of previous frame buffer is complete (called only after ARSTREAM_READER_CAUSE_FRAME_TOO_SMALL)"),
    ARSTREAM_READER_CAUSE_CANCEL(3, "Reader is closing, so buffer is no longer used"),
    ARSTREAM_READER_CAUSE_MAX(4);
    
    static HashMap<Integer, ARSTREAM_READER_CAUSE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARSTREAM_READER_CAUSE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARSTREAM_READER_CAUSE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARSTREAM_READER_CAUSE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARSTREAM_READER_CAUSE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARSTREAM_READER_CAUSE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARSTREAM_READER_CAUSE_ENUM retVal = (ARSTREAM_READER_CAUSE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARSTREAM_READER_CAUSE_UNKNOWN_ENUM_VALUE;
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
