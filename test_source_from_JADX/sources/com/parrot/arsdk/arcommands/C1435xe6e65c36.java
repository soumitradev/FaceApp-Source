package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_ENUM */
public enum C1435xe6e65c36 {
    eARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_AUTO_ALL(0, "Auto selection"),
    ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_AUTO_2_4GHZ(1, "Auto selection 2.4ghz"),
    ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_AUTO_5GHZ(2, "Auto selection 5 ghz"),
    ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_MANUAL(3, "Manual selection"),
    ARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_MAX(4);
    
    static HashMap<Integer, C1435xe6e65c36> valuesList;
    private final String comment;
    private final int value;

    private C1435xe6e65c36(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1435xe6e65c36(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1435xe6e65c36 getFromValue(int value) {
        if (valuesList == null) {
            C1435xe6e65c36[] valuesArray = C1435xe6e65c36.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1435xe6e65c36 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1435xe6e65c36 retVal = (C1435xe6e65c36) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE_UNKNOWN_ENUM_VALUE;
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
