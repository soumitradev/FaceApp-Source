package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_BAND_ENUM */
public enum C1462x3db0e20b {
    eARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_BAND_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_BAND_2_4GHZ(0, "2.4 GHz band"),
    ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_BAND_5GHZ(1, "5 GHz band"),
    ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_BAND_MAX(2);
    
    static HashMap<Integer, C1462x3db0e20b> valuesList;
    private final String comment;
    private final int value;

    private C1462x3db0e20b(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1462x3db0e20b(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1462x3db0e20b getFromValue(int value) {
        if (valuesList == null) {
            C1462x3db0e20b[] valuesArray = C1462x3db0e20b.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1462x3db0e20b entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1462x3db0e20b retVal = (C1462x3db0e20b) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGS_WIFISELECTION_BAND_UNKNOWN_ENUM_VALUE;
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
