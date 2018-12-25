package com.parrot.arsdk.ardiscovery;

import java.util.HashMap;

public enum ARDISCOVERY_NETWORK_TYPE_ENUM {
    eARDISCOVERY_NETWORK_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARDISCOVERY_NETWORK_TYPE_UNKNOWN(0, "unknown network"),
    ARDISCOVERY_NETWORK_TYPE_NET(1, "IP (e.g. wifi) network"),
    ARDISCOVERY_NETWORK_TYPE_BLE(2, "BLE network"),
    ARDISCOVERY_NETWORK_TYPE_USBMUX(3, "libmux over USB network");
    
    static HashMap<Integer, ARDISCOVERY_NETWORK_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARDISCOVERY_NETWORK_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARDISCOVERY_NETWORK_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARDISCOVERY_NETWORK_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARDISCOVERY_NETWORK_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARDISCOVERY_NETWORK_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARDISCOVERY_NETWORK_TYPE_ENUM retVal = (ARDISCOVERY_NETWORK_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARDISCOVERY_NETWORK_TYPE_UNKNOWN_ENUM_VALUE;
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
