package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_ANTIFLICKERING_ELECTRICFREQUENCY_FREQUENCY_ENUM */
public enum C1369x263150ea {
    eARCOMMANDS_ARDRONE3_ANTIFLICKERING_ELECTRICFREQUENCY_FREQUENCY_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_ANTIFLICKERING_ELECTRICFREQUENCY_FREQUENCY_FIFTYHERTZ(0, "Electric frequency of the country is 50hz"),
    ARCOMMANDS_ARDRONE3_ANTIFLICKERING_ELECTRICFREQUENCY_FREQUENCY_SIXTYHERTZ(1, "Electric frequency of the country is 60hz"),
    ARCOMMANDS_ARDRONE3_ANTIFLICKERING_ELECTRICFREQUENCY_FREQUENCY_MAX(2);
    
    static HashMap<Integer, C1369x263150ea> valuesList;
    private final String comment;
    private final int value;

    private C1369x263150ea(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1369x263150ea(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1369x263150ea getFromValue(int value) {
        if (valuesList == null) {
            C1369x263150ea[] valuesArray = C1369x263150ea.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1369x263150ea entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1369x263150ea retVal = (C1369x263150ea) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_ANTIFLICKERING_ELECTRICFREQUENCY_FREQUENCY_UNKNOWN_ENUM_VALUE;
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
