package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_ENUM */
public enum C1453xad999db7 {
    eARCOMMANDS_POWERUP_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_ENABLED(0, "Video streaming is enabled."),
    ARCOMMANDS_POWERUP_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_DISABLED(1, "Video streaming is disabled."),
    ARCOMMANDS_POWERUP_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_ERROR(2, "Video streaming failed to start."),
    ARCOMMANDS_POWERUP_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_MAX(3);
    
    static HashMap<Integer, C1453xad999db7> valuesList;
    private final String comment;
    private final int value;

    private C1453xad999db7(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1453xad999db7(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1453xad999db7 getFromValue(int value) {
        if (valuesList == null) {
            C1453xad999db7[] valuesArray = C1453xad999db7.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1453xad999db7 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1453xad999db7 retVal = (C1453xad999db7) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED_UNKNOWN_ENUM_VALUE;
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
