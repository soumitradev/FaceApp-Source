package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_STATUS_ENUM */
public enum C1414xbac7a5e1 {
    eARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_STATUS_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_STATUS_DISCHARGING(0, "The battery is discharging."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_STATUS_CHARGING_SLOW(1, "The battery is charging at a slow rate about 512 mA."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_STATUS_CHARGING_MODERATE(2, "The battery is charging at a moderate rate (> 512 mA) but slower than the fastest rate."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_STATUS_CHARGING_FAST(3, "The battery is charging at a the fastest rate."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_STATUS_BATTERY_FULL(4, "The charger is plugged and the battery is fully charged."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_STATUS_MAX(5);
    
    static HashMap<Integer, C1414xbac7a5e1> valuesList;
    private final String comment;
    private final int value;

    private C1414xbac7a5e1(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1414xbac7a5e1(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1414xbac7a5e1 getFromValue(int value) {
        if (valuesList == null) {
            C1414xbac7a5e1[] valuesArray = C1414xbac7a5e1.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1414xbac7a5e1 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1414xbac7a5e1 retVal = (C1414xbac7a5e1) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_STATUS_UNKNOWN_ENUM_VALUE;
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
