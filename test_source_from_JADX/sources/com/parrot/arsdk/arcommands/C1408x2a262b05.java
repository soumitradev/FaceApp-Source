package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_ERROR_ENUM */
public enum C1408x2a262b05 {
    eARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_ERROR_OK(0, "No error. Accessory config change successful."),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_ERROR_UNKNOWN(1, "Cannot change accessory configuration for some reason."),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_ERROR_FLYING(2, "Cannot change accessory configuration while flying."),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_ERROR_MAX(3);
    
    static HashMap<Integer, C1408x2a262b05> valuesList;
    private final String comment;
    private final int value;

    private C1408x2a262b05(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1408x2a262b05(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1408x2a262b05 getFromValue(int value) {
        if (valuesList == null) {
            C1408x2a262b05[] valuesArray = C1408x2a262b05.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1408x2a262b05 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1408x2a262b05 retVal = (C1408x2a262b05) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_ERROR_UNKNOWN_ENUM_VALUE;
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
