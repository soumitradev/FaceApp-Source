package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_RECORDING_ENUM */
public enum C1378xf397ca6e {
    eARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_RECORDING_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_RECORDING_RES360P(0, "360p resolution."),
    ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_RECORDING_RES480P(1, "480p resolution."),
    ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_RECORDING_RES720P(2, "720p resolution."),
    ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_RECORDING_RES1080P(3, "1080p resolution."),
    ARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_RECORDING_MAX(4);
    
    static HashMap<Integer, C1378xf397ca6e> valuesList;
    private final String comment;
    private final int value;

    private C1378xf397ca6e(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1378xf397ca6e(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1378xf397ca6e getFromValue(int value) {
        if (valuesList == null) {
            C1378xf397ca6e[] valuesArray = C1378xf397ca6e.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1378xf397ca6e entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1378xf397ca6e retVal = (C1378xf397ca6e) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_RECORDING_UNKNOWN_ENUM_VALUE;
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
