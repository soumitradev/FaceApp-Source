package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_ENUM */
public enum C1389x5b9d9bcb {
    eARCOMMANDS_ARDRONE3_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_2_4GHZ(0, "2.4 GHz band"),
    ARCOMMANDS_ARDRONE3_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_5GHZ(1, "5 GHz band"),
    ARCOMMANDS_ARDRONE3_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_MAX(2);
    
    static HashMap<Integer, C1389x5b9d9bcb> valuesList;
    private final String comment;
    private final int value;

    private C1389x5b9d9bcb(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1389x5b9d9bcb(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1389x5b9d9bcb getFromValue(int value) {
        if (valuesList == null) {
            C1389x5b9d9bcb[] valuesArray = C1389x5b9d9bcb.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1389x5b9d9bcb entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1389x5b9d9bcb retVal = (C1389x5b9d9bcb) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND_UNKNOWN_ENUM_VALUE;
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
