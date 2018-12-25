package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_ENUM */
public enum C1444x26c2e159 {
    eARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_OK(0, "No Error"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_UNKNOWN(1, "Unknown generic error"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_CAMERA_KO(2, "Picture camera is out of order"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_MEMORYFULL(3, "Memory full ; cannot save one additional picture"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_LOWBATTERY(4, "Battery is too low to start/keep recording."),
    ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_MAX(5);
    
    static HashMap<Integer, C1444x26c2e159> valuesList;
    private final String comment;
    private final int value;

    private C1444x26c2e159(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1444x26c2e159(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1444x26c2e159 getFromValue(int value) {
        if (valuesList == null) {
            C1444x26c2e159[] valuesArray = C1444x26c2e159.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1444x26c2e159 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1444x26c2e159 retVal = (C1444x26c2e159) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_UNKNOWN_ENUM_VALUE;
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
