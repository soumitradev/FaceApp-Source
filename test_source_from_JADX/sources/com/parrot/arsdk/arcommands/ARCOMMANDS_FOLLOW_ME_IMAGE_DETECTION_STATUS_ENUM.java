package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_FOLLOW_ME_IMAGE_DETECTION_STATUS_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    NONE(0, "No image detection"),
    OK(1, "Image detection is considered ok by the drone"),
    LOST(2, "Image detection is considered lost or in contradiction with gps value. This state will remain until a new selection of the target is done");
    
    static HashMap<Integer, ARCOMMANDS_FOLLOW_ME_IMAGE_DETECTION_STATUS_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_FOLLOW_ME_IMAGE_DETECTION_STATUS_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_FOLLOW_ME_IMAGE_DETECTION_STATUS_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_FOLLOW_ME_IMAGE_DETECTION_STATUS_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_FOLLOW_ME_IMAGE_DETECTION_STATUS_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_FOLLOW_ME_IMAGE_DETECTION_STATUS_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_FOLLOW_ME_IMAGE_DETECTION_STATUS_ENUM retVal = (ARCOMMANDS_FOLLOW_ME_IMAGE_DETECTION_STATUS_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return UNKNOWN;
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
