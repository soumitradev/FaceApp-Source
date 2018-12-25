package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_ENUM */
public enum C1375xdcd3c0dd {
    eARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_START(0, "Video start"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_STOP(1, "Video stop and saved"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_FAILED(2, "Video failed"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_MAX(3);
    
    static HashMap<Integer, C1375xdcd3c0dd> valuesList;
    private final String comment;
    private final int value;

    private C1375xdcd3c0dd(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1375xdcd3c0dd(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1375xdcd3c0dd getFromValue(int value) {
        if (valuesList == null) {
            C1375xdcd3c0dd[] valuesArray = C1375xdcd3c0dd.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1375xdcd3c0dd entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1375xdcd3c0dd retVal = (C1375xdcd3c0dd) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT_UNKNOWN_ENUM_VALUE;
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
