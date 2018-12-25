package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_SETTINGSSTATE_PRODUCTVARIANTCHANGED_VARIANT_ENUM */
public enum C1469xef2e29b5 {
    eARCOMMANDS_SKYCONTROLLER_SETTINGSSTATE_PRODUCTVARIANTCHANGED_VARIANT_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_SETTINGSSTATE_PRODUCTVARIANTCHANGED_VARIANT_BEBOP(0, "SkyController of the bebop generation. (Bebop battery, original key layout, red/blue/yellow)"),
    ARCOMMANDS_SKYCONTROLLER_SETTINGSSTATE_PRODUCTVARIANTCHANGED_VARIANT_BEBOP2(1, "SkyController of the bebop2 generation. (Bebop2 battery, updated key layout, black)"),
    ARCOMMANDS_SKYCONTROLLER_SETTINGSSTATE_PRODUCTVARIANTCHANGED_VARIANT_MAX(2);
    
    static HashMap<Integer, C1469xef2e29b5> valuesList;
    private final String comment;
    private final int value;

    private C1469xef2e29b5(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1469xef2e29b5(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1469xef2e29b5 getFromValue(int value) {
        if (valuesList == null) {
            C1469xef2e29b5[] valuesArray = C1469xef2e29b5.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1469xef2e29b5 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1469xef2e29b5 retVal = (C1469xef2e29b5) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_SETTINGSSTATE_PRODUCTVARIANTCHANGED_VARIANT_UNKNOWN_ENUM_VALUE;
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
