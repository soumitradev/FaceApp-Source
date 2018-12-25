package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_SKYCONTROLLERSTATE_BATTERYSTATE_STATE_ENUM */
public enum C1470xefdece67 {
    eARCOMMANDS_SKYCONTROLLER_SKYCONTROLLERSTATE_BATTERYSTATE_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_SKYCONTROLLERSTATE_BATTERYSTATE_STATE_CHARGING(0, "Battery is charging"),
    ARCOMMANDS_SKYCONTROLLER_SKYCONTROLLERSTATE_BATTERYSTATE_STATE_CHARGED(1, "Battery is fully charged"),
    ARCOMMANDS_SKYCONTROLLER_SKYCONTROLLERSTATE_BATTERYSTATE_STATE_DISCHARGING(2, "Battery is discharging (normal case when on and unplugged)"),
    ARCOMMANDS_SKYCONTROLLER_SKYCONTROLLERSTATE_BATTERYSTATE_STATE_DISCHARGING_LOW(3, "Battery is low (Can still work for a few minutes)"),
    ARCOMMANDS_SKYCONTROLLER_SKYCONTROLLERSTATE_BATTERYSTATE_STATE_DISCHARGING_CRITICAL(4, "Battery is critically low (the product will automatically shut down if not plugged)"),
    ARCOMMANDS_SKYCONTROLLER_SKYCONTROLLERSTATE_BATTERYSTATE_STATE_MAX(5);
    
    static HashMap<Integer, C1470xefdece67> valuesList;
    private final String comment;
    private final int value;

    private C1470xefdece67(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1470xefdece67(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1470xefdece67 getFromValue(int value) {
        if (valuesList == null) {
            C1470xefdece67[] valuesArray = C1470xefdece67.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1470xefdece67 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1470xefdece67 retVal = (C1470xefdece67) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_SKYCONTROLLERSTATE_BATTERYSTATE_STATE_UNKNOWN_ENUM_VALUE;
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
