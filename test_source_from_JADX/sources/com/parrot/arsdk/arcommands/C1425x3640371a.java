package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_ENUM */
public enum C1425x3640371a {
    eARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_TAKEN(0, "Picture taken and saved"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_FAILED(1, "Picture failed"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_MAX(2);
    
    static HashMap<Integer, C1425x3640371a> valuesList;
    private final String comment;
    private final int value;

    private C1425x3640371a(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1425x3640371a(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1425x3640371a getFromValue(int value) {
        if (valuesList == null) {
            C1425x3640371a[] valuesArray = C1425x3640371a.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1425x3640371a entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1425x3640371a retVal = (C1425x3640371a) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_UNKNOWN_ENUM_VALUE;
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
