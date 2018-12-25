package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_PITCHMODECHANGED_VALUE_ENUM */
public enum C1401xc59e32b5 {
    eARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_PITCHMODECHANGED_VALUE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_PITCHMODECHANGED_VALUE_NORMAL(0, "Positive pitch values will make the drone lower its nose. Negative pitch values will make the drone raise its nose."),
    ARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_PITCHMODECHANGED_VALUE_INVERTED(1, "Pitch commands are inverted. Positive pitch values will make the drone raise its nose. Negative pitch values will make the drone lower its nose."),
    ARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_PITCHMODECHANGED_VALUE_MAX(2);
    
    static HashMap<Integer, C1401xc59e32b5> valuesList;
    private final String comment;
    private final int value;

    private C1401xc59e32b5(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1401xc59e32b5(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1401xc59e32b5 getFromValue(int value) {
        if (valuesList == null) {
            C1401xc59e32b5[] valuesArray = C1401xc59e32b5.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1401xc59e32b5 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1401xc59e32b5 retVal = (C1401xc59e32b5) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PILOTINGSETTINGSSTATE_PITCHMODECHANGED_VALUE_UNKNOWN_ENUM_VALUE;
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
