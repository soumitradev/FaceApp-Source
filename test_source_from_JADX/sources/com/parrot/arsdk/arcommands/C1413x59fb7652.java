package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_PHASE_ENUM */
public enum C1413x59fb7652 {
    eARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_PHASE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_PHASE_UNKNOWN(0, "The charge phase is unknown or irrelevant."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_PHASE_CONSTANT_CURRENT_1(1, "First phase of the charging process. The battery is charging with constant current."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_PHASE_CONSTANT_CURRENT_2(2, "Second phase of the charging process. The battery is charging with constant current, with a higher voltage than the first phase."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_PHASE_CONSTANT_VOLTAGE(3, "Last part of the charging process. The battery is charging with a constant voltage."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_PHASE_CHARGED(4, "The battery is fully charged."),
    ARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_PHASE_MAX(5);
    
    static HashMap<Integer, C1413x59fb7652> valuesList;
    private final String comment;
    private final int value;

    private C1413x59fb7652(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1413x59fb7652(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1413x59fb7652 getFromValue(int value) {
        if (valuesList == null) {
            C1413x59fb7652[] valuesArray = C1413x59fb7652.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1413x59fb7652 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1413x59fb7652 retVal = (C1413x59fb7652) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_CHARGERSTATE_CURRENTCHARGESTATECHANGED_PHASE_UNKNOWN_ENUM_VALUE;
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
