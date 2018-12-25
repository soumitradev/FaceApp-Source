package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    TAKEOFF_LAND(0, "Take off or land"),
    TAKE_PICTURE(1, "Take a picture"),
    FLIP_LEFT(2, "Flip left"),
    FLIP_RIGHT(3, "Flip right"),
    FLIP_FRONT(4, "Flip front"),
    FLIP_BACK(5, "Flip back"),
    EMERGENCY(6, "Emergency motors shutdown"),
    ACCESSORY_GUN(7, "Launch USB accessory gun action (shoot)"),
    THROWN_TAKEOFF(8, "Thrown take off"),
    CW_90_SWIPE(9, "90 deg clockwise swipe"),
    CCW_90_SWIPE(10, "90 deg counter clockwise swipe"),
    CW_180_SWIPE(11, "180 deg clockwise swipe"),
    CCW_180_SWIPE(12, "180 deg counter clockwise swipe"),
    GEAR_UP(13, "increase gear"),
    GEAR_DOWN(14, "decrease gear"),
    PLANE_MODE_HALF_BAREL_ROLL_RIGHT(15, "in plane mode make a 180 deg anticlockwise swipe on roll axis"),
    PLANE_MODE_HALF_BAREL_ROLL_LEFT(16, "in plane mode make a 180 deg clockwise swipe on roll axis"),
    PLANE_MODE_BACKSWAP(17, "in plane mode make a 180 deg clockwise swipe on pitch axis"),
    PLANE_MODE_LOOPING(18, "vertical circular loop in plane mode"),
    PLANE_MODE_TOGGLE(19, "switch between plane mode and quad mode"),
    ACCESSORY_CLAW(20, "Launch USB accessory claw action (open/close)"),
    LIGHT_CONTINUOUS(21, "switch continuous light (ON/OFF)"),
    LIGHT_BLINK(22, "switch blink light (ON/OFF)"),
    LIGHT_SINUS(23, "switch sinus light (ON/OFF)"),
    LIGHT_TOGGLE(24, "toggle between light animations (OFF-continuous-blink-sinus-OFF)");
    
    static HashMap<Integer, ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM retVal = (ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM) valuesList.get(Integer.valueOf(value));
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
