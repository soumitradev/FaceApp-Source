package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_ENUM */
public enum C1404x955e1555 {
    eARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_USERREQUEST(0, "User requested a navigate home (available->inProgress)"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_CONNECTIONLOST(1, "Connection between controller and product lost (available->inProgress)"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_LOWBATTERY(2, "Low battery occurred (available->inProgress)"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_FINISHED(3, "Navigate home is finished (inProgress->available)"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_STOPPED(4, "Navigate home has been stopped (inProgress->available)"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_DISABLED(5, "Navigate home disabled by product (inProgress->unavailable or available->unavailable)"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_ENABLED(6, "Navigate home enabled by product (unavailable->available)"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_MAX(7);
    
    static HashMap<Integer, C1404x955e1555> valuesList;
    private final String comment;
    private final int value;

    private C1404x955e1555(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1404x955e1555(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1404x955e1555 getFromValue(int value) {
        if (valuesList == null) {
            C1404x955e1555[] valuesArray = C1404x955e1555.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1404x955e1555 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1404x955e1555 retVal = (C1404x955e1555) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON_UNKNOWN_ENUM_VALUE;
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
