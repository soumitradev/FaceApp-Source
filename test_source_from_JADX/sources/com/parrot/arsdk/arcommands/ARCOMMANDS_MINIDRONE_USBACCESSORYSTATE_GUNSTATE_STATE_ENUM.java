package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_ENUM {
    eARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_READY(0, "Gun is ready to fire."),
    ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_BUSY(1, "Gun is busy (ie not ready to fire)."),
    ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_MAX(2);
    
    static HashMap<Integer, ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_ENUM retVal = (ARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_MINIDRONE_USBACCESSORYSTATE_GUNSTATE_STATE_UNKNOWN_ENUM_VALUE;
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
