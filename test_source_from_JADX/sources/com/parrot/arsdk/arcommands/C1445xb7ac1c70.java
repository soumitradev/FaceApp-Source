package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_ENUM */
public enum C1445xb7ac1c70 {
    eARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_READY(0, "The picture recording is ready"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_BUSY(1, "The picture recording is busy"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_NOTAVAILABLE(2, "The picture recording is not available"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_MAX(3);
    
    static HashMap<Integer, C1445xb7ac1c70> valuesList;
    private final String comment;
    private final int value;

    private C1445xb7ac1c70(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1445xb7ac1c70(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1445xb7ac1c70 getFromValue(int value) {
        if (valuesList == null) {
            C1445xb7ac1c70[] valuesArray = C1445xb7ac1c70.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1445xb7ac1c70 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1445xb7ac1c70 retVal = (C1445xb7ac1c70) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_MINIDRONE_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_UNKNOWN_ENUM_VALUE;
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
