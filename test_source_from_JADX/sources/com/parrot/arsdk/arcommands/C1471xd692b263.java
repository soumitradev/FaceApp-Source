package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIAUTHCHANNELLISTCHANGEDV2_BAND_ENUM */
public enum C1471xd692b263 {
    eARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIAUTHCHANNELLISTCHANGEDV2_BAND_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIAUTHCHANNELLISTCHANGEDV2_BAND_2_4GHZ(0, "2.4 GHz band"),
    ARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIAUTHCHANNELLISTCHANGEDV2_BAND_5GHZ(1, "5 GHz band"),
    ARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIAUTHCHANNELLISTCHANGEDV2_BAND_MAX(2);
    
    static HashMap<Integer, C1471xd692b263> valuesList;
    private final String comment;
    private final int value;

    private C1471xd692b263(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1471xd692b263(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1471xd692b263 getFromValue(int value) {
        if (valuesList == null) {
            C1471xd692b263[] valuesArray = C1471xd692b263.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1471xd692b263 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1471xd692b263 retVal = (C1471xd692b263) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_WIFISTATE_WIFIAUTHCHANNELLISTCHANGEDV2_BAND_UNKNOWN_ENUM_VALUE;
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
