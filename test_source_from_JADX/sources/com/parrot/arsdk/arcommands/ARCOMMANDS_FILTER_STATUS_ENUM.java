package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_FILTER_STATUS_ENUM {
    eARCOMMANDS_FILTER_STATUS_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_FILTER_STATUS_ALLOWED(0, "The command should pass the filter"),
    ARCOMMANDS_FILTER_STATUS_BLOCKED(1, "The command should not pass the filter"),
    ARCOMMANDS_FILTER_STATUS_UNKNOWN(2, "Unknown command. The command was possibly added in a newer version of libARCommands, or is an invalid command."),
    ARCOMMANDS_FILTER_STATUS_ERROR(3, "The filtering of the command failed.");
    
    static HashMap<Integer, ARCOMMANDS_FILTER_STATUS_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_FILTER_STATUS_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_FILTER_STATUS_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_FILTER_STATUS_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_FILTER_STATUS_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_FILTER_STATUS_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_FILTER_STATUS_ENUM retVal = (ARCOMMANDS_FILTER_STATUS_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_FILTER_STATUS_UNKNOWN_ENUM_VALUE;
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
