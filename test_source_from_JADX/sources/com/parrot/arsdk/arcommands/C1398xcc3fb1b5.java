package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_ENUM */
public enum C1398xcc3fb1b5 {
    eARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_24_FPS(0, "23.976 frames per second."),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_25_FPS(1, "25 frames per second."),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_30_FPS(2, "29.97 frames per second."),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_MAX(3);
    
    static HashMap<Integer, C1398xcc3fb1b5> valuesList;
    private final String comment;
    private final int value;

    private C1398xcc3fb1b5(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1398xcc3fb1b5(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1398xcc3fb1b5 getFromValue(int value) {
        if (valuesList == null) {
            C1398xcc3fb1b5[] valuesArray = C1398xcc3fb1b5.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1398xcc3fb1b5 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1398xcc3fb1b5 retVal = (C1398xcc3fb1b5) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_UNKNOWN_ENUM_VALUE;
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
