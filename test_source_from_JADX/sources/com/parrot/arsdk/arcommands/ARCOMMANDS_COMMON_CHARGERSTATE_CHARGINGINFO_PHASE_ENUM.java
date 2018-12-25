package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_ENUM {
    eARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_UNKNOWN(0, "The charge phase is unknown or irrelevant."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_CONSTANT_CURRENT_1(1, "First phase of the charging process. The battery is charging with constant current."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_CONSTANT_CURRENT_2(2, "Second phase of the charging process. The battery is charging with constant current, with a higher voltage than the first phase."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_CONSTANT_VOLTAGE(3, "Last part of the charging process. The battery is charging with a constant voltage."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_CHARGED(4, "The battery is fully charged."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_DISCHARGING(5, "The battery is discharging; Other arguments refers to the last charge."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_MAX(6);
    
    static HashMap<Integer, ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_ENUM retVal = (ARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_CHARGERSTATE_CHARGINGINFO_PHASE_UNKNOWN_ENUM_VALUE;
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
