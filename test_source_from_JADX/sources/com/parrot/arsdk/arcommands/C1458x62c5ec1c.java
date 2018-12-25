package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISECURITYCHANGED_SECURITY_TYPE_ENUM */
public enum C1458x62c5ec1c {
    eARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISECURITYCHANGED_SECURITY_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISECURITYCHANGED_SECURITY_TYPE_OPEN(0, "Wifi is not protected (default)"),
    ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISECURITYCHANGED_SECURITY_TYPE_WPA2(1, "Wifi is protected by wpa2"),
    ARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISECURITYCHANGED_SECURITY_TYPE_MAX(2);
    
    static HashMap<Integer, C1458x62c5ec1c> valuesList;
    private final String comment;
    private final int value;

    private C1458x62c5ec1c(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1458x62c5ec1c(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1458x62c5ec1c getFromValue(int value) {
        if (valuesList == null) {
            C1458x62c5ec1c[] valuesArray = C1458x62c5ec1c.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1458x62c5ec1c entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1458x62c5ec1c retVal = (C1458x62c5ec1c) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_ACCESSPOINTSETTINGSSTATE_WIFISECURITYCHANGED_SECURITY_TYPE_UNKNOWN_ENUM_VALUE;
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
