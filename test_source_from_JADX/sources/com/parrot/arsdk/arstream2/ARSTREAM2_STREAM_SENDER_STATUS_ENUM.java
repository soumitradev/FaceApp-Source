package com.parrot.arsdk.arstream2;

import java.util.HashMap;

public enum ARSTREAM2_STREAM_SENDER_STATUS_ENUM {
    eARSTREAM2_STREAM_SENDER_STATUS_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARSTREAM2_STREAM_SENDER_STATUS_SENT(0, "Access unit or NAL unit was sent"),
    ARSTREAM2_STREAM_SENDER_STATUS_CANCELLED(1, "Access unit or NAL unit was cancelled (not sent or partly sent)"),
    ARSTREAM2_STREAM_SENDER_STATUS_MAX(2);
    
    static HashMap<Integer, ARSTREAM2_STREAM_SENDER_STATUS_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARSTREAM2_STREAM_SENDER_STATUS_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARSTREAM2_STREAM_SENDER_STATUS_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARSTREAM2_STREAM_SENDER_STATUS_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARSTREAM2_STREAM_SENDER_STATUS_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARSTREAM2_STREAM_SENDER_STATUS_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARSTREAM2_STREAM_SENDER_STATUS_ENUM retVal = (ARSTREAM2_STREAM_SENDER_STATUS_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARSTREAM2_STREAM_SENDER_STATUS_UNKNOWN_ENUM_VALUE;
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
