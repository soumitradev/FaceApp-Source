package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM {
    eARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_NO_ACCESSORY(0, "No accessory."),
    ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_STD_WHEELS(1, "Standard wheels"),
    ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_TRUCK_WHEELS(2, "Truck wheels"),
    ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_HULL(3, "Hull"),
    ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_HYDROFOIL(4, "Hydrofoil"),
    ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_MAX(5);
    
    static HashMap<Integer, ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM retVal = (ARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_ACCESSORY_CONFIG_ACCESSORY_UNKNOWN_ENUM_VALUE;
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
