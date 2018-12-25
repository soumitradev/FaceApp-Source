package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM */
public enum C1447xa6880d54 {
    eARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_OK(0, "No Error"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_UNKNOWN(1, "Unknown generic error ; only when state is failed"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_BUSY(2, "Picture recording is busy ; only when state is failed"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_NOTAVAILABLE(3, "Picture recording not available ; only when state is failed"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_MEMORYFULL(4, "Memory full ; only when state is failed"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_LOWBATTERY(5, "Battery is too low to record."),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_MAX(6);
    
    static HashMap<Integer, C1447xa6880d54> valuesList;
    private final String comment;
    private final int value;

    private C1447xa6880d54(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1447xa6880d54(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1447xa6880d54 getFromValue(int value) {
        if (valuesList == null) {
            C1447xa6880d54[] valuesArray = C1447xa6880d54.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1447xa6880d54 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1447xa6880d54 retVal = (C1447xa6880d54) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_UNKNOWN_ENUM_VALUE;
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
