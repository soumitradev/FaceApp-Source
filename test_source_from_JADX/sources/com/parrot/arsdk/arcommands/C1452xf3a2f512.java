package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_ENUM */
public enum C1452xf3a2f512 {
    eARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_STOPPED(0, "Video is stopped"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_STARTED(1, "Video is started"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_NOTAVAILABLE(2, "The video recording is not available"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_MAX(3);
    
    static HashMap<Integer, C1452xf3a2f512> valuesList;
    private final String comment;
    private final int value;

    private C1452xf3a2f512(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1452xf3a2f512(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1452xf3a2f512 getFromValue(int value) {
        if (valuesList == null) {
            C1452xf3a2f512[] valuesArray = C1452xf3a2f512.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1452xf3a2f512 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1452xf3a2f512 retVal = (C1452xf3a2f512) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_UNKNOWN_ENUM_VALUE;
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
