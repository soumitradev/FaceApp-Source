package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITYCHANGED_TYPE_ENUM */
public enum C1385x549a9714 {
    eARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITYCHANGED_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITYCHANGED_TYPE_OPEN(0, "Wifi is not protected by any security (default)"),
    ARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITYCHANGED_TYPE_WPA2(1, "Wifi is protected by wpa2"),
    ARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITYCHANGED_TYPE_MAX(2);
    
    static HashMap<Integer, C1385x549a9714> valuesList;
    private final String comment;
    private final int value;

    private C1385x549a9714(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1385x549a9714(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1385x549a9714 getFromValue(int value) {
        if (valuesList == null) {
            C1385x549a9714[] valuesArray = C1385x549a9714.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1385x549a9714 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1385x549a9714 retVal = (C1385x549a9714) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITYCHANGED_TYPE_UNKNOWN_ENUM_VALUE;
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
