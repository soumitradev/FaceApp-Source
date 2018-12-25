package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIENVIRONMENTCHANGED_ENVIRONMENT_ENUM */
public enum C1473x8d24462b {
    eARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIENVIRONMENTCHANGED_ENVIRONMENT_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIENVIRONMENTCHANGED_ENVIRONMENT_INDOOR(0, "indoor environment"),
    ARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIENVIRONMENTCHANGED_ENVIRONMENT_OUTDOOR(1, "outdoor environment"),
    ARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIENVIRONMENTCHANGED_ENVIRONMENT_MAX(2);
    
    static HashMap<Integer, C1473x8d24462b> valuesList;
    private final String comment;
    private final int value;

    private C1473x8d24462b(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1473x8d24462b(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1473x8d24462b getFromValue(int value) {
        if (valuesList == null) {
            C1473x8d24462b[] valuesArray = C1473x8d24462b.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1473x8d24462b entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1473x8d24462b retVal = (C1473x8d24462b) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIENVIRONMENTCHANGED_ENVIRONMENT_UNKNOWN_ENUM_VALUE;
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
