package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_ENUM */
public enum C1460xf32549b9 {
    eARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_MANUAL(0, "Manual selection"),
    ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_MAX(1);
    
    static HashMap<Integer, C1460xf32549b9> valuesList;
    private final String comment;
    private final int value;

    private C1460xf32549b9(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1460xf32549b9(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1460xf32549b9 getFromValue(int value) {
        if (valuesList == null) {
            C1460xf32549b9[] valuesArray = C1460xf32549b9.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1460xf32549b9 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1460xf32549b9 retVal = (C1460xf32549b9) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_UNKNOWN_ENUM_VALUE;
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
