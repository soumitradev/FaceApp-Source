package com.parrot.arsdk.arnetworkal;

import java.util.HashMap;

public enum ARNETWORKAL_MANAGER_RETURN_ENUM {
    eARNETWORKAL_MANAGER_RETURN_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARNETWORKAL_MANAGER_RETURN_DEFAULT(0, "Default return value (no error)"),
    ARNETWORKAL_MANAGER_RETURN_BUFFER_FULL(1, "Impossible to push a frame : network buffer is full"),
    ARNETWORKAL_MANAGER_RETURN_BUFFER_EMPTY(2, "Impossible to pop a frame, no frame in the buffer"),
    ARNETWORKAL_MANAGER_RETURN_BAD_FRAME(3, "Impossible to pop a frame, frame is corrupted"),
    ARNETWORKAL_MANAGER_RETURN_NO_DATA_AVAILABLE(4, "Impossible to read data from the network, no data available"),
    ARNETWORKAL_MANAGER_RETURN_BAD_PARAMETERS(5, "Parameters given to the callback were not good"),
    ARNETWORKAL_MANAGER_RETURN_NETWORK_ERROR(6, "Network error while reading or sending data");
    
    static HashMap<Integer, ARNETWORKAL_MANAGER_RETURN_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARNETWORKAL_MANAGER_RETURN_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARNETWORKAL_MANAGER_RETURN_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARNETWORKAL_MANAGER_RETURN_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARNETWORKAL_MANAGER_RETURN_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARNETWORKAL_MANAGER_RETURN_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARNETWORKAL_MANAGER_RETURN_ENUM retVal = (ARNETWORKAL_MANAGER_RETURN_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARNETWORKAL_MANAGER_RETURN_UNKNOWN_ENUM_VALUE;
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
