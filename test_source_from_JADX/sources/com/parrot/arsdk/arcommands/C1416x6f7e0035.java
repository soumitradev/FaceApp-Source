package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_COMPONENT_ENUM */
public enum C1416x6f7e0035 {
    eARCOMMANDS_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_COMPONENT_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_COMPONENT_GPS(0, "Drone GPS component. State is 0 when the drone needs a GPS fix."),
    ARCOMMANDS_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_COMPONENT_CALIBRATION(1, "Drone Calibration component. State is 0 when the sensors of the drone needs to be calibrated."),
    ARCOMMANDS_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_COMPONENT_MAVLINK_FILE(2, "Mavlink file component. State is 0 when the mavlink file is missing or contains error."),
    ARCOMMANDS_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_COMPONENT_TAKEOFF(3, "Drone Take off component. State is 0 when the drone cannot take-off."),
    ARCOMMANDS_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_COMPONENT_WAYPOINTSBEYONDGEOFENCE(4, "Component for waypoints beyond the geofence. State is 0 when one or more waypoints are beyond the geofence."),
    ARCOMMANDS_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_COMPONENT_MAX(5);
    
    static HashMap<Integer, C1416x6f7e0035> valuesList;
    private final String comment;
    private final int value;

    private C1416x6f7e0035(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1416x6f7e0035(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1416x6f7e0035 getFromValue(int value) {
        if (valuesList == null) {
            C1416x6f7e0035[] valuesArray = C1416x6f7e0035.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1416x6f7e0035 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1416x6f7e0035 retVal = (C1416x6f7e0035) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_FLIGHTPLANSTATE_COMPONENTSTATELISTCHANGED_COMPONENT_UNKNOWN_ENUM_VALUE;
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
