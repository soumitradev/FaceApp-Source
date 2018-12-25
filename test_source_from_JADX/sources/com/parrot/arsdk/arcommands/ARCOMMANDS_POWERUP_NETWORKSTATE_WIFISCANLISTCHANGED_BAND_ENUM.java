package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM {
    eARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_2_4GHZ(0, "2.4 GHz band"),
    ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_5GHZ(1, "5 GHz band"),
    ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_MAX(2);
    
    static HashMap<Integer, ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM retVal = (ARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_NETWORKSTATE_WIFISCANLISTCHANGED_BAND_UNKNOWN_ENUM_VALUE;
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
