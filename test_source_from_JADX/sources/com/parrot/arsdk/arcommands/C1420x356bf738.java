package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE_ENUM */
public enum C1420x356bf738 {
    eARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE_UNKNOWN(0, "Unknown state (obsolete)."),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE_UNLOADED(1, "Unloaded state."),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE_LOADED(2, "Loaded state."),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE_BUSY(3, "Unknown state (obsolete)."),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE_LOW_BATTERY_UNLOADED(4, "Unloaded state and low battery."),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE_LOW_BATTERY_LOADED(5, "Loaded state and low battery."),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE_MAX(6);
    
    static HashMap<Integer, C1420x356bf738> valuesList;
    private final String comment;
    private final int value;

    private C1420x356bf738(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1420x356bf738(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1420x356bf738 getFromValue(int value) {
        if (valuesList == null) {
            C1420x356bf738[] valuesArray = C1420x356bf738.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1420x356bf738 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1420x356bf738 retVal = (C1420x356bf738) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPLOADCHANGED_STATE_UNKNOWN_ENUM_VALUE;
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
