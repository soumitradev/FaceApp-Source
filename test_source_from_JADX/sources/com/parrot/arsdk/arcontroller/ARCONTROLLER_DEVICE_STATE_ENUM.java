package com.parrot.arsdk.arcontroller;

import java.util.HashMap;

public enum ARCONTROLLER_DEVICE_STATE_ENUM {
    eARCONTROLLER_DEVICE_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCONTROLLER_DEVICE_STATE_STOPPED(0, "device controller is stopped"),
    ARCONTROLLER_DEVICE_STATE_STARTING(1, "device controller is starting"),
    ARCONTROLLER_DEVICE_STATE_RUNNING(2, "device controller is running"),
    ARCONTROLLER_DEVICE_STATE_PAUSED(3, "device controller is paused"),
    ARCONTROLLER_DEVICE_STATE_STOPPING(4, "device controller is stopping"),
    ARCONTROLLER_DEVICE_STATE_MAX(5, "Max of the enumeration");
    
    static HashMap<Integer, ARCONTROLLER_DEVICE_STATE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCONTROLLER_DEVICE_STATE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCONTROLLER_DEVICE_STATE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCONTROLLER_DEVICE_STATE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCONTROLLER_DEVICE_STATE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCONTROLLER_DEVICE_STATE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCONTROLLER_DEVICE_STATE_ENUM retVal = (ARCONTROLLER_DEVICE_STATE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCONTROLLER_DEVICE_STATE_UNKNOWN_ENUM_VALUE;
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
