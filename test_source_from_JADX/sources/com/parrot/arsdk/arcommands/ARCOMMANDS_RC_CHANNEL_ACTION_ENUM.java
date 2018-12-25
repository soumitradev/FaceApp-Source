package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_RC_CHANNEL_ACTION_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    INVALID(0, "Invalid/Unused channel."),
    ROLL(1, "Roll axis channel."),
    PITCH(2, "Pitch axis channel."),
    YAW(3, "Yaw axis channel."),
    GAZ(4, "Gaz / Throttle / Altitude axis channel."),
    TAKEOFF_LAND(5, "Takeoff / Land channel."),
    EMERGENCY(6, "Emergency channel."),
    RETURN_HOME(7, "Return Home channel."),
    PILOTING_MODE(8, "RC Piloting mode. Auto mode: used for doing flightplans and for assisted flying with a non-RC controller. Easy manual mode: used for assisted flying with a RC controller. Manual mode: used for non-assisted flying with a RC controller and for directly controlling the servomotors."),
    TAKE_CONTROL(9, "RC take control. When take control is activated the RC controller, if available, becomes the main controller.");
    
    static HashMap<Integer, ARCOMMANDS_RC_CHANNEL_ACTION_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_RC_CHANNEL_ACTION_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_RC_CHANNEL_ACTION_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_RC_CHANNEL_ACTION_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_RC_CHANNEL_ACTION_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_RC_CHANNEL_ACTION_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_RC_CHANNEL_ACTION_ENUM retVal = (ARCOMMANDS_RC_CHANNEL_ACTION_ENUM) valuesList.get(Integer.valueOf(value));
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
