package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM {
    eARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_STANDING(0, "Standing type"),
    ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_JUMPER(1, "Jumper type"),
    ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_KICKER(2, "Kicker type"),
    ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_MAX(3);
    
    static HashMap<Integer, ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM retVal = (ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_UNKNOWN_ENUM_VALUE;
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
