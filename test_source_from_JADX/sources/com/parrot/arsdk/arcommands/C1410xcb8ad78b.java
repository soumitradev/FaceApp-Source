package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_ACCESSORYSTATE_SUPPORTEDACCESSORIESLISTCHANGED_ACCESSORY_ENUM */
public enum C1410xcb8ad78b {
    eARCOMMANDS_COMMON_ACCESSORYSTATE_SUPPORTEDACCESSORIESLISTCHANGED_ACCESSORY_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_SUPPORTEDACCESSORIESLISTCHANGED_ACCESSORY_NO_ACCESSORY(0, "No accessory."),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_SUPPORTEDACCESSORIESLISTCHANGED_ACCESSORY_STD_WHEELS(1, "Standard wheels"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_SUPPORTEDACCESSORIESLISTCHANGED_ACCESSORY_TRUCK_WHEELS(2, "Truck wheels"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_SUPPORTEDACCESSORIESLISTCHANGED_ACCESSORY_HULL(3, "Hull"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_SUPPORTEDACCESSORIESLISTCHANGED_ACCESSORY_HYDROFOIL(4, "Hydrofoil"),
    ARCOMMANDS_COMMON_ACCESSORYSTATE_SUPPORTEDACCESSORIESLISTCHANGED_ACCESSORY_MAX(5);
    
    static HashMap<Integer, C1410xcb8ad78b> valuesList;
    private final String comment;
    private final int value;

    private C1410xcb8ad78b(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1410xcb8ad78b(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1410xcb8ad78b getFromValue(int value) {
        if (valuesList == null) {
            C1410xcb8ad78b[] valuesArray = C1410xcb8ad78b.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1410xcb8ad78b entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1410xcb8ad78b retVal = (C1410xcb8ad78b) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_ACCESSORYSTATE_SUPPORTEDACCESSORIESLISTCHANGED_ACCESSORY_UNKNOWN_ENUM_VALUE;
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
