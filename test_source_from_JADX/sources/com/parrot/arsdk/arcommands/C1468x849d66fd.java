package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_GAMEPADINFOSSTATE_GAMEPADCONTROL_TYPE_ENUM */
public enum C1468x849d66fd {
    eARCOMMANDS_SKYCONTROLLER_GAMEPADINFOSSTATE_GAMEPADCONTROL_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_GAMEPADINFOSSTATE_GAMEPADCONTROL_TYPE_AXIS(0, "An analog axis (one of the 4 joysticks)"),
    ARCOMMANDS_SKYCONTROLLER_GAMEPADINFOSSTATE_GAMEPADCONTROL_TYPE_BUTTON(1, "A button (including small joystick clicks)"),
    ARCOMMANDS_SKYCONTROLLER_GAMEPADINFOSSTATE_GAMEPADCONTROL_TYPE_MAX(2);
    
    static HashMap<Integer, C1468x849d66fd> valuesList;
    private final String comment;
    private final int value;

    private C1468x849d66fd(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1468x849d66fd(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1468x849d66fd getFromValue(int value) {
        if (valuesList == null) {
            C1468x849d66fd[] valuesArray = C1468x849d66fd.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1468x849d66fd entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1468x849d66fd retVal = (C1468x849d66fd) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_GAMEPADINFOSSTATE_GAMEPADCONTROL_TYPE_UNKNOWN_ENUM_VALUE;
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
