package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_ENUM */
public enum C1429x7bb19b87 {
    eARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_READY(0, "The picture recording is ready"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_BUSY(1, "The picture recording is busy"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_NOTAVAILABLE(2, "The picture recording is not available"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_MAX(3);
    
    static HashMap<Integer, C1429x7bb19b87> valuesList;
    private final String comment;
    private final int value;

    private C1429x7bb19b87(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1429x7bb19b87(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1429x7bb19b87 getFromValue(int value) {
        if (valuesList == null) {
            C1429x7bb19b87[] valuesArray = C1429x7bb19b87.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1429x7bb19b87 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1429x7bb19b87 retVal = (C1429x7bb19b87) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_UNKNOWN_ENUM_VALUE;
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
