package com.parrot.arsdk.arcontroller;

import java.util.HashMap;

public enum ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_ENUM {
    eARCONTROLLER_NETWORK_SENDING_DATA_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_NOT_ACK(0, "data NOT acknowledged"),
    ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_ACK(1, "data acknowledged"),
    ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_HIGH_PRIORITY(2, "high priority data"),
    ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_STREAM(3, "strem data"),
    ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_MAX(4, "Max of the enumeration");
    
    static HashMap<Integer, ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_ENUM retVal = (ARCONTROLLER_NETWORK_SENDING_DATA_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCONTROLLER_NETWORK_SENDING_DATA_TYPE_UNKNOWN_ENUM_VALUE;
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
