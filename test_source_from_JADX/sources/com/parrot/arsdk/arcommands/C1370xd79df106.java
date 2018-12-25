package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_GPSUPDATESTATECHANGED_STATE_ENUM */
public enum C1370xd79df106 {
    eARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_GPSUPDATESTATECHANGED_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_GPSUPDATESTATECHANGED_STATE_UPDATED(0, "Drone GPS update succeed"),
    ARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_GPSUPDATESTATECHANGED_STATE_INPROGRESS(1, "Drone GPS update In progress"),
    ARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_GPSUPDATESTATECHANGED_STATE_FAILED(2, "Drone GPS update failed"),
    ARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_GPSUPDATESTATECHANGED_STATE_MAX(3);
    
    static HashMap<Integer, C1370xd79df106> valuesList;
    private final String comment;
    private final int value;

    private C1370xd79df106(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1370xd79df106(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1370xd79df106 getFromValue(int value) {
        if (valuesList == null) {
            C1370xd79df106[] valuesArray = C1370xd79df106.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1370xd79df106 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1370xd79df106 retVal = (C1370xd79df106) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_GPSUPDATESTATECHANGED_STATE_UNKNOWN_ENUM_VALUE;
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
