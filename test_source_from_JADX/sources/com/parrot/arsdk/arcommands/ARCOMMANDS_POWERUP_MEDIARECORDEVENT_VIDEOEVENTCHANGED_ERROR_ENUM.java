package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM {
    eARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_OK(0, "No Error"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_UNKNOWN(1, "Unknown generic error ; only when state is failed"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_BUSY(2, "Video recording is busy ; only when state is failed"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_NOTAVAILABLE(3, "Video recording not available ; only when state is failed"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_MEMORYFULL(4, "Memory full"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_LOWBATTERY(5, "Battery is too low to record."),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_AUTOSTOPPED(6, "Video was auto stopped"),
    ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_MAX(7);
    
    static HashMap<Integer, ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM retVal = (ARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_UNKNOWN_ENUM_VALUE;
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
