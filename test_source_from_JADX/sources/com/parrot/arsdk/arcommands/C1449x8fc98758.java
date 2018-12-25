package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_ENUM */
public enum C1449x8fc98758 {
    eARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_OK(0, "No Error"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_UNKNOWN(1, "Unknown generic error"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_CAMERA_KO(2, "Picture camera is out of order"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_MEMORYFULL(3, "Memory full ; cannot save one additional picture"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_LOWBATTERY(4, "Battery is too low to start/keep recording."),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_MAX(5);
    
    static HashMap<Integer, C1449x8fc98758> valuesList;
    private final String comment;
    private final int value;

    private C1449x8fc98758(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1449x8fc98758(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1449x8fc98758 getFromValue(int value) {
        if (valuesList == null) {
            C1449x8fc98758[] valuesArray = C1449x8fc98758.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1449x8fc98758 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1449x8fc98758 retVal = (C1449x8fc98758) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_UNKNOWN_ENUM_VALUE;
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
