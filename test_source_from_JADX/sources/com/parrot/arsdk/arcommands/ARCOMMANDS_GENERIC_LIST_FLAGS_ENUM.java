package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    FIRST(0, "indicate it's the first element of the list."),
    LAST(1, "indicate it's the last element of the list."),
    EMPTY(2, "indicate the list is empty (implies First/Last). All other arguments should be ignored."),
    REMOVE(3, "This value should be removed from the existing list.");
    
    static HashMap<Integer, ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM retVal = (ARCOMMANDS_GENERIC_LIST_FLAGS_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return UNKNOWN;
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
