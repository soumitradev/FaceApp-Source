package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_ELECTRICFREQUENCYCHANGED_FREQUENCY_ENUM */
public enum C1368xa14db947 {
    eARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_ELECTRICFREQUENCYCHANGED_FREQUENCY_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_ELECTRICFREQUENCYCHANGED_FREQUENCY_FIFTYHERTZ(0, "Electric frequency of the country is 50hz"),
    ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_ELECTRICFREQUENCYCHANGED_FREQUENCY_SIXTYHERTZ(1, "Electric frequency of the country is 60hz"),
    ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_ELECTRICFREQUENCYCHANGED_FREQUENCY_MAX(2);
    
    static HashMap<Integer, C1368xa14db947> valuesList;
    private final String comment;
    private final int value;

    private C1368xa14db947(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1368xa14db947(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1368xa14db947 getFromValue(int value) {
        if (valuesList == null) {
            C1368xa14db947[] valuesArray = C1368xa14db947.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1368xa14db947 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1368xa14db947 retVal = (C1368xa14db947) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_ELECTRICFREQUENCYCHANGED_FREQUENCY_UNKNOWN_ENUM_VALUE;
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
