package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKPLAYERRORSTATECHANGED_ERROR_ENUM */
public enum C1419x7fd94000 {
    eARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKPLAYERRORSTATECHANGED_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKPLAYERRORSTATECHANGED_ERROR_NONE(0, "There is no error"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKPLAYERRORSTATECHANGED_ERROR_NOTINOUTDOORMODE(1, "The drone is not in outdoor mode"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKPLAYERRORSTATECHANGED_ERROR_GPSNOTFIXED(2, "The gps is not fixed"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKPLAYERRORSTATECHANGED_ERROR_NOTCALIBRATED(3, "The magnetometer of the drone is not calibrated"),
    ARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKPLAYERRORSTATECHANGED_ERROR_MAX(4);
    
    static HashMap<Integer, C1419x7fd94000> valuesList;
    private final String comment;
    private final int value;

    private C1419x7fd94000(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1419x7fd94000(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1419x7fd94000 getFromValue(int value) {
        if (valuesList == null) {
            C1419x7fd94000[] valuesArray = C1419x7fd94000.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1419x7fd94000 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1419x7fd94000 retVal = (C1419x7fd94000) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_MAVLINKSTATE_MAVLINKPLAYERRORSTATECHANGED_ERROR_UNKNOWN_ENUM_VALUE;
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
