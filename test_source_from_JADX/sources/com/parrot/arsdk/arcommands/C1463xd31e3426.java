package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_TYPE_ENUM */
public enum C1463xd31e3426 {
    eARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_TYPE_MANUAL(0, "Manual selection"),
    ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_TYPE_MAX(1);
    
    static HashMap<Integer, C1463xd31e3426> valuesList;
    private final String comment;
    private final int value;

    private C1463xd31e3426(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1463xd31e3426(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1463xd31e3426 getFromValue(int value) {
        if (valuesList == null) {
            C1463xd31e3426[] valuesArray = C1463xd31e3426.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1463xd31e3426 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1463xd31e3426 retVal = (C1463xd31e3426) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_TYPE_UNKNOWN_ENUM_VALUE;
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
