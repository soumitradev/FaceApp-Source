package com.parrot.arsdk.arstream;

import java.util.HashMap;

public enum ARSTREAM_SENDER_STATUS_ENUM {
    eARSTREAM_SENDER_STATUS_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARSTREAM_SENDER_STATUS_FRAME_SENT(0, "Frame was sent and acknowledged by peer"),
    ARSTREAM_SENDER_STATUS_FRAME_CANCEL(1, "Frame was not sent, and was cancelled by a new frame"),
    ARSTREAM_SENDER_STATUS_FRAME_LATE_ACK(2, "We received a full ack for an old frame. The callback will be called with null pointer and zero size."),
    ARSTREAM_SENDER_STATUS_MAX(3);
    
    static HashMap<Integer, ARSTREAM_SENDER_STATUS_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARSTREAM_SENDER_STATUS_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARSTREAM_SENDER_STATUS_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARSTREAM_SENDER_STATUS_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARSTREAM_SENDER_STATUS_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARSTREAM_SENDER_STATUS_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARSTREAM_SENDER_STATUS_ENUM retVal = (ARSTREAM_SENDER_STATUS_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARSTREAM_SENDER_STATUS_UNKNOWN_ENUM_VALUE;
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
