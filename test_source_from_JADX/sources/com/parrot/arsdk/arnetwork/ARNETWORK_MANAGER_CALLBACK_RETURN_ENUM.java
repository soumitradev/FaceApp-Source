package com.parrot.arsdk.arnetwork;

import java.util.HashMap;

public enum ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM {
    eARNETWORK_MANAGER_CALLBACK_RETURN_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARNETWORK_MANAGER_CALLBACK_RETURN_DEFAULT(0, "default value must be returned when the status callback differ of ARNETWORK_MANAGER_CALLBACK_STATUS_TIMEOUT"),
    ARNETWORK_MANAGER_CALLBACK_RETURN_DATA_POP(0, "pop the data (default behavior)"),
    ARNETWORK_MANAGER_CALLBACK_RETURN_RETRY(1, "reset the number of retry"),
    ARNETWORK_MANAGER_CALLBACK_RETURN_FLUSH(2, "flush all input buffers");
    
    static HashMap<Integer, ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM retVal = (ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARNETWORK_MANAGER_CALLBACK_RETURN_UNKNOWN_ENUM_VALUE;
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
