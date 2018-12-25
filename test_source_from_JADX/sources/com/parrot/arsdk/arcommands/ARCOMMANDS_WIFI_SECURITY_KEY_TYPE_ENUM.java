package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    PLAIN(0, "Key is plain text, not encrypted");
    
    static HashMap<Integer, ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM retVal = (ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
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
