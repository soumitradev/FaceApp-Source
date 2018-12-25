package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_ENUM */
public enum C1433x444690cf {
    eARCOMMANDS_JUMPINGSUMO_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_ENABLED(0, "Video streaming is enabled."),
    ARCOMMANDS_JUMPINGSUMO_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_DISABLED(1, "Video streaming is disabled."),
    ARCOMMANDS_JUMPINGSUMO_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_ERROR(2, "Video streaming failed to start."),
    ARCOMMANDS_JUMPINGSUMO_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_MAX(3);
    
    static HashMap<Integer, C1433x444690cf> valuesList;
    private final String comment;
    private final int value;

    private C1433x444690cf(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1433x444690cf(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1433x444690cf getFromValue(int value) {
        if (valuesList == null) {
            C1433x444690cf[] valuesArray = C1433x444690cf.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1433x444690cf entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1433x444690cf retVal = (C1433x444690cf) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_UNKNOWN_ENUM_VALUE;
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
