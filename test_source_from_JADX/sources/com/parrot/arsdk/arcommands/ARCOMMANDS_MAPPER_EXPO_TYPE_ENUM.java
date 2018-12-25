package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    LINEAR(0, "No expo applied, axis is linear"),
    EXPO_0(1, "Light exponential curve"),
    EXPO_1(2, "Medium exponential curve"),
    EXPO_2(3, "Heavy exponential curve"),
    EXPO_4(4, "Maximum exponential curve");
    
    static HashMap<Integer, ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM retVal = (ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
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
