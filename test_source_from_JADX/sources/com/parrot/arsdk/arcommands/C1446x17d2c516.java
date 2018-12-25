package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_PILOTINGSTATE_PLANEGEARBOXCHANGED_STATE_ENUM */
public enum C1446x17d2c516 {
    eARCOMMANDS_MINIDRONE_PILOTINGSTATE_PLANEGEARBOXCHANGED_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_MINIDRONE_PILOTINGSTATE_PLANEGEARBOXCHANGED_STATE_GEAR_1(0, "Gear 1. Low speed"),
    ARCOMMANDS_MINIDRONE_PILOTINGSTATE_PLANEGEARBOXCHANGED_STATE_GEAR_2(1, "Gear 2. Middle speed"),
    ARCOMMANDS_MINIDRONE_PILOTINGSTATE_PLANEGEARBOXCHANGED_STATE_GEAR_3(2, "Gear 3. High speed"),
    ARCOMMANDS_MINIDRONE_PILOTINGSTATE_PLANEGEARBOXCHANGED_STATE_MAX(3);
    
    static HashMap<Integer, C1446x17d2c516> valuesList;
    private final String comment;
    private final int value;

    private C1446x17d2c516(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1446x17d2c516(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1446x17d2c516 getFromValue(int value) {
        if (valuesList == null) {
            C1446x17d2c516[] valuesArray = C1446x17d2c516.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1446x17d2c516 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1446x17d2c516 retVal = (C1446x17d2c516) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_MINIDRONE_PILOTINGSTATE_PLANEGEARBOXCHANGED_STATE_UNKNOWN_ENUM_VALUE;
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
