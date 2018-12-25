package com.parrot.arsdk.ardiscovery;

import java.util.HashMap;

public enum ARDISCOVERY_CONNECTION_STATE_ENUM {
    eARDISCOVERY_CONNECTION_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARDISCOVERY_CONNECTION_STATE_UNKNOWN(0, "Connection state unknown"),
    ARDISCOVERY_CONNECTION_STATE_AVAILABLE(1, "Connection is available, product is theoretically connectable"),
    ARDISCOVERY_CONNECTION_STATE_CONNECTED_TO_OTHER(2, "Product is connected to a controller. This controller is not one of the listed in the following enum values"),
    ARDISCOVERY_CONNECTION_STATE_CONNECTED_TO_TINOS(3, "Product is connected to a Tinos controller"),
    ARDISCOVERY_CONNECTION_STATE_MAX(4, "Max value, no meaning, should be considered like Unknown");
    
    static HashMap<Integer, ARDISCOVERY_CONNECTION_STATE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARDISCOVERY_CONNECTION_STATE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARDISCOVERY_CONNECTION_STATE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARDISCOVERY_CONNECTION_STATE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARDISCOVERY_CONNECTION_STATE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARDISCOVERY_CONNECTION_STATE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARDISCOVERY_CONNECTION_STATE_ENUM retVal = (ARDISCOVERY_CONNECTION_STATE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARDISCOVERY_CONNECTION_STATE_UNKNOWN_ENUM_VALUE;
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
