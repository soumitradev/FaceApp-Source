package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM {
    eARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_CW(0, "Circling ClockWise"),
    ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_CCW(1, "Circling Counter ClockWise"),
    ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_DEFAULT(2, "Use drone default Circling direction set by CirclingDirection cmd"),
    ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_MAX(3);
    
    static HashMap<Integer, ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM retVal = (ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_UNKNOWN_ENUM_VALUE;
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
