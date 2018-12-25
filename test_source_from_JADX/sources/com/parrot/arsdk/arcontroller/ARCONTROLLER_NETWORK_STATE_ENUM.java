package com.parrot.arsdk.arcontroller;

import java.util.HashMap;

public enum ARCONTROLLER_NETWORK_STATE_ENUM {
    eARCONTROLLER_NETWORK_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCONTROLLER_NETWORK_STATE_STOPPED(0, "network controller stopped"),
    ARCONTROLLER_NETWORK_STATE_RUNNING(1, "network controller running"),
    ARCONTROLLER_NETWORK_STATE_PAUSE(2, "network controller in pause"),
    ARCONTROLLER_NETWORK_STATE_MAX(3, "Max of the enumeration");
    
    static HashMap<Integer, ARCONTROLLER_NETWORK_STATE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCONTROLLER_NETWORK_STATE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCONTROLLER_NETWORK_STATE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCONTROLLER_NETWORK_STATE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCONTROLLER_NETWORK_STATE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCONTROLLER_NETWORK_STATE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCONTROLLER_NETWORK_STATE_ENUM retVal = (ARCONTROLLER_NETWORK_STATE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCONTROLLER_NETWORK_STATE_UNKNOWN_ENUM_VALUE;
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
