package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_ENUM */
public enum C1431xaa35342a {
    eARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_STOPPED(0, "Video is stopped"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_STARTED(1, "Video is started"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_NOTAVAILABLE(2, "The video recording is not available"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_MAX(3);
    
    static HashMap<Integer, C1431xaa35342a> valuesList;
    private final String comment;
    private final int value;

    private C1431xaa35342a(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1431xaa35342a(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1431xaa35342a getFromValue(int value) {
        if (valuesList == null) {
            C1431xaa35342a[] valuesArray = C1431xaa35342a.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1431xaa35342a entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1431xaa35342a retVal = (C1431xaa35342a) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE_UNKNOWN_ENUM_VALUE;
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
