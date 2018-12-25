package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGDIRECTIONCHANGED_VALUE_ENUM */
public enum C1400xe6290a4 {
    eARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGDIRECTIONCHANGED_VALUE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGDIRECTIONCHANGED_VALUE_CW(0, "Circling ClockWise"),
    ARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGDIRECTIONCHANGED_VALUE_CCW(1, "Circling Counter ClockWise"),
    ARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGDIRECTIONCHANGED_VALUE_MAX(2);
    
    static HashMap<Integer, C1400xe6290a4> valuesList;
    private final String comment;
    private final int value;

    private C1400xe6290a4(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1400xe6290a4(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1400xe6290a4 getFromValue(int value) {
        if (valuesList == null) {
            C1400xe6290a4[] valuesArray = C1400xe6290a4.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1400xe6290a4 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1400xe6290a4 retVal = (C1400xe6290a4) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGDIRECTIONCHANGED_VALUE_UNKNOWN_ENUM_VALUE;
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
