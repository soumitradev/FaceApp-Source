package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_FOLLOW_ME_INPUT_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    DRONE_CALIBRATED(0, "Drone is calibrated"),
    DRONE_GPS_GOOD_ACCURACY(1, "Drone gps has fixed and has a good accuracy"),
    TARGET_GPS_GOOD_ACCURACY(2, "Target gps data is known and has a good accuracy"),
    TARGET_BAROMETER_OK(3, "Target barometer data is available"),
    DRONE_FAR_ENOUGH(4, "Drone is far enough from the target"),
    DRONE_HIGH_ENOUGH(5, "Drone is high enough from the ground"),
    IMAGE_DETECTION(6, "Target detection is done by image detection among other things");
    
    static HashMap<Integer, ARCOMMANDS_FOLLOW_ME_INPUT_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_FOLLOW_ME_INPUT_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_FOLLOW_ME_INPUT_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_FOLLOW_ME_INPUT_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_FOLLOW_ME_INPUT_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_FOLLOW_ME_INPUT_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_FOLLOW_ME_INPUT_ENUM retVal = (ARCOMMANDS_FOLLOW_ME_INPUT_ENUM) valuesList.get(Integer.valueOf(value));
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
