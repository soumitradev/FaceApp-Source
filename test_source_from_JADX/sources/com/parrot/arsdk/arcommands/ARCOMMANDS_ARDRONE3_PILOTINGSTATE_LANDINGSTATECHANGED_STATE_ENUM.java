package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_ENUM {
    eARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_LINEAR(0, "Linear landing"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_SPIRAL(1, "Spiral landing"),
    ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_MAX(2);
    
    static HashMap<Integer, ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_ENUM retVal = (ARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE;
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
