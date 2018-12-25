package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_BAND_ENUM */
public enum C1454xbacc1703 {
    eARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_BAND_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_BAND_2_4GHZ(0, "2.4 GHz band"),
    ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_BAND_5GHZ(1, "5 GHz band"),
    ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_BAND_ALL(2, "Both 2.4 and 5 GHz bands"),
    ARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_BAND_MAX(3);
    
    static HashMap<Integer, C1454xbacc1703> valuesList;
    private final String comment;
    private final int value;

    private C1454xbacc1703(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1454xbacc1703(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1454xbacc1703 getFromValue(int value) {
        if (valuesList == null) {
            C1454xbacc1703[] valuesArray = C1454xbacc1703.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1454xbacc1703 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1454xbacc1703 retVal = (C1454xbacc1703) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_BAND_UNKNOWN_ENUM_VALUE;
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
