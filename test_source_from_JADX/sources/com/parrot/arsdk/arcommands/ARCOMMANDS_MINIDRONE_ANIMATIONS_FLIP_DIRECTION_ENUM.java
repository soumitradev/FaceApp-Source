package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM {
    eARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_FRONT(0, "Flip direction front"),
    ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_BACK(1, "Flip direction back"),
    ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_RIGHT(2, "Flip direction right"),
    ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_LEFT(3, "Flip direction left"),
    ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_MAX(4);
    
    static HashMap<Integer, ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM retVal = (ARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_MINIDRONE_ANIMATIONS_FLIP_DIRECTION_UNKNOWN_ENUM_VALUE;
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
