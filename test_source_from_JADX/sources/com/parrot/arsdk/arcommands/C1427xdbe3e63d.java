package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_ENUM */
public enum C1427xdbe3e63d {
    eARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_START(0, "Video start"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_STOP(1, "Video stop and saved"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_FAILED(2, "Video failed"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_MAX(3);
    
    static HashMap<Integer, C1427xdbe3e63d> valuesList;
    private final String comment;
    private final int value;

    private C1427xdbe3e63d(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1427xdbe3e63d(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1427xdbe3e63d getFromValue(int value) {
        if (valuesList == null) {
            C1427xdbe3e63d[] valuesArray = C1427xdbe3e63d.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1427xdbe3e63d entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1427xdbe3e63d retVal = (C1427xdbe3e63d) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_UNKNOWN_ENUM_VALUE;
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
