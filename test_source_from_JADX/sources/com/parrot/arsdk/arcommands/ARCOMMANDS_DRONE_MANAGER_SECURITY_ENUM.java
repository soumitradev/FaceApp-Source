package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    NONE(0, "The drone is not protected."),
    WPA2(1, "The drone is protected with Wpa2 (passphrase).");
    
    static HashMap<Integer, ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM retVal = (ARCOMMANDS_DRONE_MANAGER_SECURITY_ENUM) valuesList.get(Integer.valueOf(value));
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
