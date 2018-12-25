package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_AUTOWHITEBALANCECHANGED_TYPE_ENUM */
public enum C1390xca383277 {
    eARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_AUTOWHITEBALANCECHANGED_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_AUTOWHITEBALANCECHANGED_TYPE_AUTO(0, "Auto guess of best white balance params"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_AUTOWHITEBALANCECHANGED_TYPE_TUNGSTEN(1, "Tungsten white balance"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_AUTOWHITEBALANCECHANGED_TYPE_DAYLIGHT(2, "Daylight white balance"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_AUTOWHITEBALANCECHANGED_TYPE_CLOUDY(3, "Cloudy white balance"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_AUTOWHITEBALANCECHANGED_TYPE_COOL_WHITE(4, "White balance for a flash"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_AUTOWHITEBALANCECHANGED_TYPE_MAX(5);
    
    static HashMap<Integer, C1390xca383277> valuesList;
    private final String comment;
    private final int value;

    private C1390xca383277(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1390xca383277(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1390xca383277 getFromValue(int value) {
        if (valuesList == null) {
            C1390xca383277[] valuesArray = C1390xca383277.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1390xca383277 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1390xca383277 retVal = (C1390xca383277) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_AUTOWHITEBALANCECHANGED_TYPE_UNKNOWN_ENUM_VALUE;
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
