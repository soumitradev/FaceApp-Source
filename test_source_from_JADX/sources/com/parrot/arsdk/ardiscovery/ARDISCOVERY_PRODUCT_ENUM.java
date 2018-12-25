package com.parrot.arsdk.ardiscovery;

import java.util.HashMap;

public enum ARDISCOVERY_PRODUCT_ENUM {
    eARDISCOVERY_PRODUCT_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARDISCOVERY_PRODUCT_ARDRONE(0, "Bebop Drone product"),
    ARDISCOVERY_PRODUCT_JS(1, "JUMPING SUMO product"),
    ARDISCOVERY_PRODUCT_SKYCONTROLLER(2, "Sky controller product"),
    ARDISCOVERY_PRODUCT_JS_EVO_LIGHT(3, "Jumping Sumo EVO Light product"),
    ARDISCOVERY_PRODUCT_JS_EVO_RACE(4, "Jumping Sumo EVO Race product"),
    ARDISCOVERY_PRODUCT_BEBOP_2(5, "Bebop drone 2.0 product"),
    ARDISCOVERY_PRODUCT_POWER_UP(6, "Power up product"),
    ARDISCOVERY_PRODUCT_EVINRUDE(7, "Evinrude product"),
    ARDISCOVERY_PRODUCT_UNKNOWNPRODUCT_4(8, "Unknownproduct_4 product"),
    ARDISCOVERY_PRODUCT_SKYCONTROLLER_NG(9, "Sky controller product (2.0 & newer versions)"),
    ARDISCOVERY_PRODUCT_MINIDRONE(10, "DELOS product"),
    ARDISCOVERY_PRODUCT_MINIDRONE_EVO_LIGHT(11, "Delos EVO Light product"),
    ARDISCOVERY_PRODUCT_MINIDRONE_EVO_BRICK(12, "Delos EVO Brick product"),
    ARDISCOVERY_PRODUCT_MINIDRONE_EVO_HYDROFOIL(13, "Delos EVO Hydrofoil product"),
    ARDISCOVERY_PRODUCT_MINIDRONE_DELOS3(14, "Delos3 product"),
    ARDISCOVERY_PRODUCT_MINIDRONE_WINGX(15, "WingX product"),
    ARDISCOVERY_PRODUCT_SKYCONTROLLER_2(16, "Sky controller 2 product"),
    ARDISCOVERY_PRODUCT_TINOS(17, "Tinos product"),
    ARDISCOVERY_PRODUCT_MAX(18, "Max of products");
    
    static HashMap<Integer, ARDISCOVERY_PRODUCT_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARDISCOVERY_PRODUCT_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARDISCOVERY_PRODUCT_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARDISCOVERY_PRODUCT_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARDISCOVERY_PRODUCT_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARDISCOVERY_PRODUCT_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARDISCOVERY_PRODUCT_ENUM retVal = (ARDISCOVERY_PRODUCT_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARDISCOVERY_PRODUCT_UNKNOWN_ENUM_VALUE;
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
