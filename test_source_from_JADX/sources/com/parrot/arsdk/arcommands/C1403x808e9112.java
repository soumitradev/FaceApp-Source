package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_ORIENTATION_MODE_ENUM */
public enum C1403x808e9112 {
    eARCOMMANDS_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_ORIENTATION_MODE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_ORIENTATION_MODE_NONE(0, "The drone won't change its orientation"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_ORIENTATION_MODE_TO_TARGET(1, "The drone will make a rotation to look in direction of the given location"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_ORIENTATION_MODE_HEADING_START(2, "The drone will orientate itself to the given heading before moving to the location"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_ORIENTATION_MODE_HEADING_DURING(3, "The drone will orientate itself to the given heading while moving to the location"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_ORIENTATION_MODE_MAX(4);
    
    static HashMap<Integer, C1403x808e9112> valuesList;
    private final String comment;
    private final int value;

    private C1403x808e9112(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1403x808e9112(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1403x808e9112 getFromValue(int value) {
        if (valuesList == null) {
            C1403x808e9112[] valuesArray = C1403x808e9112.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1403x808e9112 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1403x808e9112 retVal = (C1403x808e9112) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_ORIENTATION_MODE_UNKNOWN_ENUM_VALUE;
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
