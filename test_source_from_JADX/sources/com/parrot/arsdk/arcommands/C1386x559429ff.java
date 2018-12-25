package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITY_KEYTYPE_ENUM */
public enum C1386x559429ff {
    eARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITY_KEYTYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITY_KEYTYPE_PLAIN(0, "Key is plain text, not encrypted"),
    ARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITY_KEYTYPE_MAX(1);
    
    static HashMap<Integer, C1386x559429ff> valuesList;
    private final String comment;
    private final int value;

    private C1386x559429ff(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1386x559429ff(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1386x559429ff getFromValue(int value) {
        if (valuesList == null) {
            C1386x559429ff[] valuesArray = C1386x559429ff.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1386x559429ff entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1386x559429ff retVal = (C1386x559429ff) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITY_KEYTYPE_UNKNOWN_ENUM_VALUE;
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
