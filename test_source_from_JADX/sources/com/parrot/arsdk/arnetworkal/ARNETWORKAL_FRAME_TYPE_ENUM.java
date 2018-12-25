package com.parrot.arsdk.arnetworkal;

import java.util.HashMap;

public enum ARNETWORKAL_FRAME_TYPE_ENUM {
    eARNETWORKAL_FRAME_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARNETWORKAL_FRAME_TYPE_UNINITIALIZED(0, "Unknown type. Don't use"),
    ARNETWORKAL_FRAME_TYPE_ACK(1, "Acknowledgment type. Internal use only"),
    ARNETWORKAL_FRAME_TYPE_DATA(2, "Data type. Main type for data that does not require an acknowledge"),
    ARNETWORKAL_FRAME_TYPE_DATA_LOW_LATENCY(3, "Low latency data type. Should only be used when needed"),
    ARNETWORKAL_FRAME_TYPE_DATA_WITH_ACK(4, "Data that should have an acknowledge type. This type can have a long latency"),
    ARNETWORKAL_FRAME_TYPE_MAX(5, "Unused, iterator maximum value");
    
    static HashMap<Integer, ARNETWORKAL_FRAME_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARNETWORKAL_FRAME_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARNETWORKAL_FRAME_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARNETWORKAL_FRAME_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARNETWORKAL_FRAME_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARNETWORKAL_FRAME_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARNETWORKAL_FRAME_TYPE_ENUM retVal = (ARNETWORKAL_FRAME_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARNETWORKAL_FRAME_TYPE_UNKNOWN_ENUM_VALUE;
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
