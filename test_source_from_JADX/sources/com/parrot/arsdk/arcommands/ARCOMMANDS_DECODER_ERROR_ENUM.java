package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_DECODER_ERROR_ENUM {
    eARCOMMANDS_DECODER_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_DECODER_OK(0, "No error occured"),
    ARCOMMANDS_DECODER_ERROR_NO_CALLBACK(1, "No error, but no callback was set (so the call had no effect)"),
    ARCOMMANDS_DECODER_ERROR_UNKNOWN_COMMAND(2, "The command buffer contained an unknown command"),
    ARCOMMANDS_DECODER_ERROR_NOT_ENOUGH_DATA(3, "The command buffer did not contain enough data for the specified command"),
    ARCOMMANDS_DECODER_ERROR_NOT_ENOUGH_SPACE(4, "The string buffer was not big enough for the command description"),
    ARCOMMANDS_DECODER_ERROR(5, "Any other error");
    
    static HashMap<Integer, ARCOMMANDS_DECODER_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_DECODER_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_DECODER_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_DECODER_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_DECODER_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_DECODER_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_DECODER_ERROR_ENUM retVal = (ARCOMMANDS_DECODER_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_DECODER_ERROR_UNKNOWN_ENUM_VALUE;
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
