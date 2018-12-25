package com.parrot.arsdk.ardiscovery;

import java.util.HashMap;

public enum ARDISCOVERY_PRODUCT_FAMILY_ENUM {
    eARDISCOVERY_PRODUCT_FAMILY_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARDISCOVERY_PRODUCT_FAMILY_ARDRONE(0, "AR DRONE product family"),
    ARDISCOVERY_PRODUCT_FAMILY_JS(1, "JUMPING SUMO product family"),
    ARDISCOVERY_PRODUCT_FAMILY_SKYCONTROLLER(2, "SKY CONTROLLER product family"),
    ARDISCOVERY_PRODUCT_FAMILY_MINIDRONE(3, "DELOS product"),
    ARDISCOVERY_PRODUCT_FAMILY_POWER_UP(4, "Power Up product family"),
    ARDISCOVERY_PRODUCT_FAMILY_FIXED_WING(5, "Fixed wing product family"),
    ARDISCOVERY_PRODUCT_FAMILY_GAMEPAD(6, "Gamepad product family"),
    ARDISCOVERY_PRODUCT_FAMILY_MAX(7, "Max of product familys");
    
    static HashMap<Integer, ARDISCOVERY_PRODUCT_FAMILY_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARDISCOVERY_PRODUCT_FAMILY_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARDISCOVERY_PRODUCT_FAMILY_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARDISCOVERY_PRODUCT_FAMILY_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARDISCOVERY_PRODUCT_FAMILY_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARDISCOVERY_PRODUCT_FAMILY_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARDISCOVERY_PRODUCT_FAMILY_ENUM retVal = (ARDISCOVERY_PRODUCT_FAMILY_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARDISCOVERY_PRODUCT_FAMILY_UNKNOWN_ENUM_VALUE;
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
