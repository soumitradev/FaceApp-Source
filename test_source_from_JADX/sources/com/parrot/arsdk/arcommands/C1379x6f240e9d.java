package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_STREAMING_ENUM */
public enum C1379x6f240e9d {
    eARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_STREAMING_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_STREAMING_RES360P(0, "360p resolution."),
    ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_STREAMING_RES480P(1, "480p resolution."),
    ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_STREAMING_RES720P(2, "720p resolution."),
    ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_STREAMING_RES1080P(3, "1080p resolution."),
    ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_STREAMING_MAX(4);
    
    static HashMap<Integer, C1379x6f240e9d> valuesList;
    private final String comment;
    private final int value;

    private C1379x6f240e9d(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1379x6f240e9d(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1379x6f240e9d getFromValue(int value) {
        if (valuesList == null) {
            C1379x6f240e9d[] valuesArray = C1379x6f240e9d.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1379x6f240e9d entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1379x6f240e9d retVal = (C1379x6f240e9d) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_STREAMING_UNKNOWN_ENUM_VALUE;
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
