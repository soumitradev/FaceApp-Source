package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM {
    eARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_HEADLIGHTS_FLASH(0, "Flash headlights."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_HEADLIGHTS_BLINK(1, "Blink headlights."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_HEADLIGHTS_OSCILLATION(2, "Oscillating headlights."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_SPIN(3, "Spin animation."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_TAP(4, "Tap animation."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_SLOW_SHAKE(5, "Slow shake animation."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_METRONOME(6, "Metronome animation."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ONDULATION(7, "Standing dance animation."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_SPIN_JUMP(8, "Spin jump animation."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_SPIN_TO_POSTURE(9, "Spin that end in standing posture, or in jumper if it was standing animation."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_SPIRAL(10, "Spiral animation."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_SLALOM(11, "Slalom animation."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_BOOST(12, "Boost animation."),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_LOOPING(13, "Make a looping. (Only for WingX)"),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_BARREL_ROLL_180_RIGHT(14, "Make a barrel roll of 180 degree turning on right. (Only for WingX)"),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_BARREL_ROLL_180_LEFT(15, "Make a barrel roll of 180 degree turning on left. (Only for WingX)"),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_BACKSWAP(16, "Put the drone upside down. (Only for WingX)"),
    ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_MAX(17);
    
    static HashMap<Integer, ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM retVal = (ARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_ANIMATIONS_STARTANIMATION_ANIM_UNKNOWN_ENUM_VALUE;
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
