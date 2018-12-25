package com.parrot.arsdk.arstream2;

import java.util.HashMap;

public enum ARSTREAM2_ERROR_ENUM {
    eARSTREAM2_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARSTREAM2_OK(0, "No error"),
    ARSTREAM2_ERROR_BAD_PARAMETERS(-1, "Bad parameters"),
    ARSTREAM2_ERROR_ALLOC(-2, "Unable to allocate resource"),
    ARSTREAM2_ERROR_BUSY(-3, "Object is busy and can not be deleted yet"),
    ARSTREAM2_ERROR_QUEUE_FULL(-4, "Queue is full"),
    ARSTREAM2_ERROR_WAITING_FOR_SYNC(-5, "Waiting for synchronization"),
    ARSTREAM2_ERROR_RESYNC_REQUIRED(-6, "Re-synchronization required"),
    ARSTREAM2_ERROR_RESOURCE_UNAVAILABLE(-7, "Resource unavailable"),
    ARSTREAM2_ERROR_NOT_FOUND(-8, "Not found"),
    ARSTREAM2_ERROR_INVALID_STATE(-9, "Invalid state"),
    ARSTREAM2_ERROR_UNSUPPORTED(-10, "Unsupported");
    
    static HashMap<Integer, ARSTREAM2_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARSTREAM2_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARSTREAM2_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARSTREAM2_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARSTREAM2_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARSTREAM2_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARSTREAM2_ERROR_ENUM retVal = (ARSTREAM2_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARSTREAM2_ERROR_UNKNOWN_ENUM_VALUE;
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
