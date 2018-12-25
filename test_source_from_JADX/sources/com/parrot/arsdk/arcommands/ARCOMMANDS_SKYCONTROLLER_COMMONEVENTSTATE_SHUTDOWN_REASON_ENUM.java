package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_ENUM {
    eARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_POWEROFF_BUTTON(0, "The power off button has been pressed"),
    ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_UPDATE(1, "The product is going to be updated"),
    ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_LOW_BATTERY(2, "The product battery is too low."),
    ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_FACTORY_RESET(3, "The product is going to be factory reset"),
    ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_MAX(4);
    
    static HashMap<Integer, ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_ENUM retVal = (ARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_COMMONEVENTSTATE_SHUTDOWN_REASON_UNKNOWN_ENUM_VALUE;
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
