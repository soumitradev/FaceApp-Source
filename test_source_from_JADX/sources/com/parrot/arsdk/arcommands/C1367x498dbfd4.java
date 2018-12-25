package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_ACCESSORYSTATE_CONNECTEDACCESSORIES_ACCESSORY_TYPE_ENUM */
public enum C1367x498dbfd4 {
    eARCOMMANDS_ARDRONE3_ACCESSORYSTATE_CONNECTEDACCESSORIES_ACCESSORY_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_ACCESSORYSTATE_CONNECTEDACCESSORIES_ACCESSORY_TYPE_SEQUOIA(0, "Parrot Sequoia (multispectral camera for agriculture)"),
    ARCOMMANDS_ARDRONE3_ACCESSORYSTATE_CONNECTEDACCESSORIES_ACCESSORY_TYPE_UNKNOWNACCESSORY_1(1, "UNKNOWNACCESSORY_1 camera (thermal+rgb camera)"),
    ARCOMMANDS_ARDRONE3_ACCESSORYSTATE_CONNECTEDACCESSORIES_ACCESSORY_TYPE_MAX(2);
    
    static HashMap<Integer, C1367x498dbfd4> valuesList;
    private final String comment;
    private final int value;

    private C1367x498dbfd4(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1367x498dbfd4(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1367x498dbfd4 getFromValue(int value) {
        if (valuesList == null) {
            C1367x498dbfd4[] valuesArray = C1367x498dbfd4.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1367x498dbfd4 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1367x498dbfd4 retVal = (C1367x498dbfd4) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_ACCESSORYSTATE_CONNECTEDACCESSORIES_ACCESSORY_TYPE_UNKNOWN_ENUM_VALUE;
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
