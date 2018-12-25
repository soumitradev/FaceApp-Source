package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_ENUM */
public enum C1448x7fadf802 {
    eARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_TAKEN(0, "Picture taken and saved"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_FAILED(1, "Picture failed"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_MAX(2);
    
    static HashMap<Integer, C1448x7fadf802> valuesList;
    private final String comment;
    private final int value;

    private C1448x7fadf802(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1448x7fadf802(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1448x7fadf802 getFromValue(int value) {
        if (valuesList == null) {
            C1448x7fadf802[] valuesArray = C1448x7fadf802.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1448x7fadf802 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1448x7fadf802 retVal = (C1448x7fadf802) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_UNKNOWN_ENUM_VALUE;
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
