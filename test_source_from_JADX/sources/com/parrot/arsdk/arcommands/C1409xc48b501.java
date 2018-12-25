package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_ENUM */
public enum C1409xc48b501 {
    eARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_UNCONFIGURED(0, "No accessory configuration set. Controller needs to set one."),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_NO_ACCESSORY(1, "No accessory."),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_STD_WHEELS(2, "Standard wheels"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_TRUCK_WHEELS(3, "Truck wheels"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_HULL(4, "Hull"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_HYDROFOIL(5, "Hydrofoil"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_IN_PROGRESS(6, "Configuration in progress."),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_MAX(7);
    
    static HashMap<Integer, C1409xc48b501> valuesList;
    private final String comment;
    private final int value;

    private C1409xc48b501(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1409xc48b501(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1409xc48b501 getFromValue(int value) {
        if (valuesList == null) {
            C1409xc48b501[] valuesArray = C1409xc48b501.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1409xc48b501 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1409xc48b501 retVal = (C1409xc48b501) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_ACCESSORYSTATE_ACCESSORYCONFIGCHANGED_NEWACCESSORY_UNKNOWN_ENUM_VALUE;
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
