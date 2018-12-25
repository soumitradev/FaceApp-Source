package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGED_STATE_ENUM */
public enum C1432x72607e46 {
    eARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGED_STATE_STOPPED(0, "Video was stopped"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGED_STATE_STARTED(1, "Video was started"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGED_STATE_FAILED(2, "Video was failed"),
    ARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGED_STATE_MAX(3);
    
    static HashMap<Integer, C1432x72607e46> valuesList;
    private final String comment;
    private final int value;

    private C1432x72607e46(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1432x72607e46(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1432x72607e46 getFromValue(int value) {
        if (valuesList == null) {
            C1432x72607e46[] valuesArray = C1432x72607e46.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1432x72607e46 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1432x72607e46 retVal = (C1432x72607e46) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_MEDIARECORDSTATE_VIDEOSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE;
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
