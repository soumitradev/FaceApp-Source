package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_ENUM {
    eARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_UNKNOWN(0, "The charge rate is not known."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_SLOW(1, "Slow charge rate."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_MODERATE(2, "Moderate charge rate."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_FAST(3, "Fast charge rate."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_MAX(4);
    
    static HashMap<Integer, ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_ENUM retVal = (ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_RATE_UNKNOWN_ENUM_VALUE;
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
