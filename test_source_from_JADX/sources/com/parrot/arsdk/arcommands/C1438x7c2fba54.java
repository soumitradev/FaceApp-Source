package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_PILOTINGSTATE_ALERTSTATECHANGED_STATE_ENUM */
public enum C1438x7c2fba54 {
    eARCOMMANDS_JUMPINGSUMO_PILOTINGSTATE_ALERTSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_PILOTINGSTATE_ALERTSTATECHANGED_STATE_NONE(0, "No alert"),
    ARCOMMANDS_JUMPINGSUMO_PILOTINGSTATE_ALERTSTATECHANGED_STATE_CRITICAL_BATTERY(1, "Critical battery alert"),
    ARCOMMANDS_JUMPINGSUMO_PILOTINGSTATE_ALERTSTATECHANGED_STATE_LOW_BATTERY(2, "Low battery alert"),
    ARCOMMANDS_JUMPINGSUMO_PILOTINGSTATE_ALERTSTATECHANGED_STATE_MAX(3);
    
    static HashMap<Integer, C1438x7c2fba54> valuesList;
    private final String comment;
    private final int value;

    private C1438x7c2fba54(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1438x7c2fba54(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1438x7c2fba54 getFromValue(int value) {
        if (valuesList == null) {
            C1438x7c2fba54[] valuesArray = C1438x7c2fba54.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1438x7c2fba54 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1438x7c2fba54 retVal = (C1438x7c2fba54) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_PILOTINGSTATE_ALERTSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE;
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
