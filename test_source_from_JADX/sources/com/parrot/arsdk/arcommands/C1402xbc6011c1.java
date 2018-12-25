package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSETTINGS_CIRCLINGDIRECTION_VALUE_ENUM */
public enum C1402xbc6011c1 {
    eARCOMMANDS_ARDRONE3_PILOTINGSETTINGS_CIRCLINGDIRECTION_VALUE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PILOTINGSETTINGS_CIRCLINGDIRECTION_VALUE_CW(0, "Circling ClockWise"),
    ARCOMMANDS_ARDRONE3_PILOTINGSETTINGS_CIRCLINGDIRECTION_VALUE_CCW(1, "Circling Counter ClockWise"),
    ARCOMMANDS_ARDRONE3_PILOTINGSETTINGS_CIRCLINGDIRECTION_VALUE_MAX(2);
    
    static HashMap<Integer, C1402xbc6011c1> valuesList;
    private final String comment;
    private final int value;

    private C1402xbc6011c1(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1402xbc6011c1(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1402xbc6011c1 getFromValue(int value) {
        if (valuesList == null) {
            C1402xbc6011c1[] valuesArray = C1402xbc6011c1.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1402xbc6011c1 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1402xbc6011c1 retVal = (C1402xbc6011c1) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PILOTINGSETTINGS_CIRCLINGDIRECTION_VALUE_UNKNOWN_ENUM_VALUE;
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
