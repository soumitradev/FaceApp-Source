package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR_ENUM */
public enum C1451x62b9b9fb {
    eARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR_OK(0, "No Error"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR_UNKNOWN(1, "Unknown generic error"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR_CAMERA_KO(2, "Video camera is out of order"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR_MEMORYFULL(3, "Memory full ; cannot save one additional video"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR_LOWBATTERY(4, "Battery is too low to start/keep recording."),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR_MAX(5);
    
    static HashMap<Integer, C1451x62b9b9fb> valuesList;
    private final String comment;
    private final int value;

    private C1451x62b9b9fb(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1451x62b9b9fb(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1451x62b9b9fb getFromValue(int value) {
        if (valuesList == null) {
            C1451x62b9b9fb[] valuesArray = C1451x62b9b9fb.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1451x62b9b9fb entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1451x62b9b9fb retVal = (C1451x62b9b9fb) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR_UNKNOWN_ENUM_VALUE;
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
