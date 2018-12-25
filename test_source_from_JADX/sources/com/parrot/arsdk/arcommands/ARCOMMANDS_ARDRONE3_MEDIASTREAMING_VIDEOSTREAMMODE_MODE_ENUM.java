package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM {
    eARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_LOW_LATENCY(0, "Minimize latency with average reliability (best for piloting)."),
    ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_HIGH_RELIABILITY(1, "Maximize the reliability with an average latency (best when streaming quality is important but not the latency)."),
    ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_HIGH_RELIABILITY_LOW_FRAMERATE(2, "Maximize the reliability using a framerate decimation with an average latency (best when streaming quality is important but not the latency)."),
    ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_MAX(3);
    
    static HashMap<Integer, ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM retVal = (ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_UNKNOWN_ENUM_VALUE;
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
