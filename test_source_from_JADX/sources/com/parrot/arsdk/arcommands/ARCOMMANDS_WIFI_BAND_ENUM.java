package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_WIFI_BAND_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    BAND_2_4_GHZ(0, "2.4 GHz band"),
    BAND_5_GHZ(1, "5 GHz band");
    
    static HashMap<Integer, ARCOMMANDS_WIFI_BAND_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_WIFI_BAND_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_WIFI_BAND_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_WIFI_BAND_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_WIFI_BAND_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_WIFI_BAND_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_WIFI_BAND_ENUM retVal = (ARCOMMANDS_WIFI_BAND_ENUM) valuesList.get(Integer.valueOf(value));
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
