package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    NONE(0, "No animation"),
    HELICOID(1, "Turn around the target"),
    SWING(2, "Pass by the zenith and change of side"),
    BOOMERANG(3, "Fly far from the target and fly back"),
    CANDLE(4, "Move to the target and go high when it is near"),
    DOLLY_SLIDE(5, "Fly in line");
    
    static HashMap<Integer, ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM retVal = (ARCOMMANDS_FOLLOW_ME_ANIMATION_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return UNKNOWN;
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
