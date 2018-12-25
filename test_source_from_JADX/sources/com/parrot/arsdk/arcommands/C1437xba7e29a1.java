package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM */
public enum C1437xba7e29a1 {
    eARCOMMANDS_JUMPINGSUMO_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_2_4GHZ(0, "2.4 GHz band"),
    ARCOMMANDS_JUMPINGSUMO_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_5GHZ(1, "5 GHz band"),
    ARCOMMANDS_JUMPINGSUMO_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_MAX(2);
    
    static HashMap<Integer, C1437xba7e29a1> valuesList;
    private final String comment;
    private final int value;

    private C1437xba7e29a1(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1437xba7e29a1(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1437xba7e29a1 getFromValue(int value) {
        if (valuesList == null) {
            C1437xba7e29a1[] valuesArray = C1437xba7e29a1.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1437xba7e29a1 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1437xba7e29a1 retVal = (C1437xba7e29a1) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_UNKNOWN_ENUM_VALUE;
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
