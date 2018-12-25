package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM {
    eARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_QUADRICOPTER(0, "Fly as a quadrictopter"),
    ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_PLANE_FORWARD(1, "Fly as a plane in forward mode"),
    ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_PLANE_BACKWARD(2, "Fly as a plane in backward mode"),
    ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_MAX(3);
    
    static HashMap<Integer, ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM retVal = (ARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_MINIDRONE_PILOTING_FLYINGMODE_MODE_UNKNOWN_ENUM_VALUE;
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
