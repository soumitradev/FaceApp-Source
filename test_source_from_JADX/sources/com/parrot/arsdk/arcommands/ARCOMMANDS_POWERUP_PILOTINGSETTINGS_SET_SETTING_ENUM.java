package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM {
    eARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_MAX_ROLL(0, "Max roll value. In degree."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ROLL_KP(1, "How fast the plane reaches the desired roll angle. No unit."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ROLL_RATE_KP(2, "How fast the plane reaches the desired roll rate. No unit."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_MAX_PITCH(3, "Max pitch value. In degree."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_MIN_PITCH(4, "Min pitch value. In degree."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_PITCH_KP(5, "How fast the plane reaches the desired pitch angle. No unit."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_PITCH_RATE_KP(6, "How fast the plane reaches the desired pitch rate. No unit."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_YAW_KP(7, "How fast the plane reaches the desired yaw angle. No unit."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_YAW_RATE_KP(8, "How fast the plane reaches the desired yaw rate. No unit."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ROLL_TO_THROTTLE_RATE(9, "Portion of thrust that is added to motors according to the roll/yaw command to compensate a dive during turn. No unit."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ANGLE_OF_ATTACK(10, "Angle of attack of a plane needed horizontal flight."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ALT_HOLD(11, "Altitude hold during autopilot. 0 for false, other value for true."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ALT_HOLD_THROTTLE(12, "Altitude hold throttle. Expressed in percentage divided by 100 (from 0 to 1)."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_DR_RSSI_EDGE(13, "Rssi value that indicates that the airplane is close to the pilot."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_RECOVERY_DURATION_LIMIT(14, "Limit time for return home duration. In seconds."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_WIND_SPEED(15, "Wind speed in m/s. Should be sent before flight."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_PLANE_SPEED(16, "Target plane speed in m/s. Should be sent before flight."),
    ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_MAX(17);
    
    static HashMap<Integer, ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM retVal = (ARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_PILOTINGSETTINGS_SET_SETTING_UNKNOWN_ENUM_VALUE;
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
