package com.parrot.arsdk.arstream2;

import java.util.HashMap;

public enum ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM {
    eARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_NONE(0, "The Access Unit is not a synchronization point"),
    ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_IDR(1, "The Access Unit is an IDR picture"),
    ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_IFRAME(2, "The Access Unit is an I-frame"),
    ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_PIR_START(3, "The Access Unit is a Periodic Intra Refresh start"),
    ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_MAX(4);
    
    static HashMap<Integer, ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM retVal = (ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_UNKNOWN_ENUM_VALUE;
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
