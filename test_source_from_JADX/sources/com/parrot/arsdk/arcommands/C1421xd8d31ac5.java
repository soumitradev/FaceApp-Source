package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPMOTORPROBLEMCHANGED_ERROR_ENUM */
public enum C1421xd8d31ac5 {
    eARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPMOTORPROBLEMCHANGED_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPMOTORPROBLEMCHANGED_ERROR_NONE(0, "None."),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPMOTORPROBLEMCHANGED_ERROR_BLOCKED(1, "Motor blocked"),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPMOTORPROBLEMCHANGED_ERROR_OVER_HEATED(2, "Motor over heated"),
    ARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPMOTORPROBLEMCHANGED_ERROR_MAX(3);
    
    static HashMap<Integer, C1421xd8d31ac5> valuesList;
    private final String comment;
    private final int value;

    private C1421xd8d31ac5(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1421xd8d31ac5(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1421xd8d31ac5 getFromValue(int value) {
        if (valuesList == null) {
            C1421xd8d31ac5[] valuesArray = C1421xd8d31ac5.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1421xd8d31ac5 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1421xd8d31ac5 retVal = (C1421xd8d31ac5) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_ANIMATIONSSTATE_JUMPMOTORPROBLEMCHANGED_ERROR_UNKNOWN_ENUM_VALUE;
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
