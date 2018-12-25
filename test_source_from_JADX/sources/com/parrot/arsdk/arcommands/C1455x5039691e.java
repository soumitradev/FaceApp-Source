package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_ENUM */
public enum C1455x5039691e {
    eARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_AUTO_ALL(0, "Auto selection"),
    ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_AUTO_2_4GHZ(1, "Auto selection 2.4ghz"),
    ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_AUTO_5GHZ(2, "Auto selection 5 ghz"),
    ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_MANUAL(3, "Manual selection"),
    ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_MAX(4);
    
    static HashMap<Integer, C1455x5039691e> valuesList;
    private final String comment;
    private final int value;

    private C1455x5039691e(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1455x5039691e(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1455x5039691e getFromValue(int value) {
        if (valuesList == null) {
            C1455x5039691e[] valuesArray = C1455x5039691e.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1455x5039691e entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1455x5039691e retVal = (C1455x5039691e) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_UNKNOWN_ENUM_VALUE;
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
