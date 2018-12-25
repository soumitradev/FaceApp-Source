package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_ENUM */
public enum C1456x65d3813 {
    eARCOMMANDS_POWERUP_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_2_4GHZ(0, "2.4 GHz band"),
    ARCOMMANDS_POWERUP_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_5GHZ(1, "5 GHz band"),
    ARCOMMANDS_POWERUP_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_MAX(2);
    
    static HashMap<Integer, C1456x65d3813> valuesList;
    private final String comment;
    private final int value;

    private C1456x65d3813(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1456x65d3813(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1456x65d3813 getFromValue(int value) {
        if (valuesList == null) {
            C1456x65d3813[] valuesArray = C1456x65d3813.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1456x65d3813 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1456x65d3813 retVal = (C1456x65d3813) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_UNKNOWN_ENUM_VALUE;
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
