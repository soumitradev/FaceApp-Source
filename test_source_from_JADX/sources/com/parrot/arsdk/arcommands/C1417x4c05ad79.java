package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_STATE_ENUM */
public enum C1417x4c05ad79 {
    eARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_STATE_PLAYING(0, "Mavlink file is playing"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_STATE_STOPPED(1, "Mavlink file is stopped (arg filepath and type are useless in this state)"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_STATE_PAUSED(2, "Mavlink file is paused"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_STATE_LOADED(3, "Mavlink file is loaded (it will be played at take-off)"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_STATE_MAX(4);
    
    static HashMap<Integer, C1417x4c05ad79> valuesList;
    private final String comment;
    private final int value;

    private C1417x4c05ad79(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1417x4c05ad79(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1417x4c05ad79 getFromValue(int value) {
        if (valuesList == null) {
            C1417x4c05ad79[] valuesArray = C1417x4c05ad79.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1417x4c05ad79 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1417x4c05ad79 retVal = (C1417x4c05ad79) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKFILEPLAYINGSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE;
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
