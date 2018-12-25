package com.parrot.arsdk.armedia;

import java.util.HashMap;

public enum ARMEDIA_MANAGER_ERROR_ENUM {
    eARMEDIA_MANAGER_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARMEDIA_MANAGER_OK(0),
    ARMEDIA_MANAGER_ALREADY_INITIALIZED(1),
    ARMEDIA_MANAGER_NOT_INITIALIZED(2);
    
    static HashMap<Integer, ARMEDIA_MANAGER_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARMEDIA_MANAGER_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARMEDIA_MANAGER_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARMEDIA_MANAGER_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARMEDIA_MANAGER_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARMEDIA_MANAGER_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARMEDIA_MANAGER_ERROR_ENUM retVal = (ARMEDIA_MANAGER_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARMEDIA_MANAGER_ERROR_UNKNOWN_ENUM_VALUE;
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
