package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_ENUM */
public enum C1428xeac86070 {
    eARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_OK(0, "No Error"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_UNKNOWN(1, "Unknown generic error"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_CAMERA_KO(2, "Picture camera is out of order"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_MEMORYFULL(3, "Memory full ; cannot save one additional picture"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_LOWBATTERY(4, "Battery is too low to start/keep recording."),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_MAX(5);
    
    static HashMap<Integer, C1428xeac86070> valuesList;
    private final String comment;
    private final int value;

    private C1428xeac86070(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1428xeac86070(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1428xeac86070 getFromValue(int value) {
        if (valuesList == null) {
            C1428xeac86070[] valuesArray = C1428xeac86070.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1428xeac86070 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1428xeac86070 retVal = (C1428xeac86070) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR_UNKNOWN_ENUM_VALUE;
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
