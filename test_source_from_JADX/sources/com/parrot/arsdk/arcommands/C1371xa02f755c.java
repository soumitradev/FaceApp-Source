package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_GPSSTATE_HOMETYPEAVAILABILITYCHANGED_TYPE_ENUM */
public enum C1371xa02f755c {
    eARCOMMANDS_ARDRONE3_GPSSTATE_HOMETYPEAVAILABILITYCHANGED_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_GPSSTATE_HOMETYPEAVAILABILITYCHANGED_TYPE_TAKEOFF(0, "The drone has enough information to return to the take off position"),
    ARCOMMANDS_ARDRONE3_GPSSTATE_HOMETYPEAVAILABILITYCHANGED_TYPE_PILOT(1, "The drone has enough information to return to the pilot position"),
    ARCOMMANDS_ARDRONE3_GPSSTATE_HOMETYPEAVAILABILITYCHANGED_TYPE_FIRST_FIX(2, "The drone has not enough information, it will return to the first GPS fix"),
    ARCOMMANDS_ARDRONE3_GPSSTATE_HOMETYPEAVAILABILITYCHANGED_TYPE_FOLLOWEE(3, "The drone has enough information to return to the target of the current (or last) follow me"),
    ARCOMMANDS_ARDRONE3_GPSSTATE_HOMETYPEAVAILABILITYCHANGED_TYPE_MAX(4);
    
    static HashMap<Integer, C1371xa02f755c> valuesList;
    private final String comment;
    private final int value;

    private C1371xa02f755c(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1371xa02f755c(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1371xa02f755c getFromValue(int value) {
        if (valuesList == null) {
            C1371xa02f755c[] valuesArray = C1371xa02f755c.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1371xa02f755c entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1371xa02f755c retVal = (C1371xa02f755c) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_GPSSTATE_HOMETYPEAVAILABILITYCHANGED_TYPE_UNKNOWN_ENUM_VALUE;
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
