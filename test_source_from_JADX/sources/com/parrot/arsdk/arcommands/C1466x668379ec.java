package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_COPILOTING_SETPILOTINGSOURCE_SOURCE_ENUM */
public enum C1466x668379ec {
    eARCOMMANDS_SKYCONTROLLER_COPILOTING_SETPILOTINGSOURCE_SOURCE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_COPILOTING_SETPILOTINGSOURCE_SOURCE_SKYCONTROLLER(0, "Use the SkyController joysticks"),
    ARCOMMANDS_SKYCONTROLLER_COPILOTING_SETPILOTINGSOURCE_SOURCE_CONTROLLER(1, "Use the Tablet (or smartphone, or whatever) controls Disables the SkyController joysticks"),
    ARCOMMANDS_SKYCONTROLLER_COPILOTING_SETPILOTINGSOURCE_SOURCE_MAX(2);
    
    static HashMap<Integer, C1466x668379ec> valuesList;
    private final String comment;
    private final int value;

    private C1466x668379ec(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1466x668379ec(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1466x668379ec getFromValue(int value) {
        if (valuesList == null) {
            C1466x668379ec[] valuesArray = C1466x668379ec.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1466x668379ec entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1466x668379ec retVal = (C1466x668379ec) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_COPILOTING_SETPILOTINGSOURCE_SOURCE_UNKNOWN_ENUM_VALUE;
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
