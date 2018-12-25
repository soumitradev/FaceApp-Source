package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_TYPE_ENUM */
public enum C1418xb82bc83c {
    eARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_TYPE_FLIGHTPLAN(0, "Mavlink file for FlightPlan"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_TYPE_MAPMYHOUSE(1, "Mavlink file for MapMyHouse"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_TYPE_MAX(2);
    
    static HashMap<Integer, C1418xb82bc83c> valuesList;
    private final String comment;
    private final int value;

    private C1418xb82bc83c(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1418xb82bc83c(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1418xb82bc83c getFromValue(int value) {
        if (valuesList == null) {
            C1418xb82bc83c[] valuesArray = C1418xb82bc83c.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1418xb82bc83c entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1418xb82bc83c retVal = (C1418xb82bc83c) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_TYPE_UNKNOWN_ENUM_VALUE;
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
