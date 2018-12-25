package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORECORDINGMODECHANGED_MODE_ENUM */
public enum C1393xabbd0257 {
    eARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORECORDINGMODECHANGED_MODE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORECORDINGMODECHANGED_MODE_QUALITY(0, "Maximize recording quality."),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORECORDINGMODECHANGED_MODE_TIME(1, "Maximize recording time."),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORECORDINGMODECHANGED_MODE_MAX(2);
    
    static HashMap<Integer, C1393xabbd0257> valuesList;
    private final String comment;
    private final int value;

    private C1393xabbd0257(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1393xabbd0257(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1393xabbd0257 getFromValue(int value) {
        if (valuesList == null) {
            C1393xabbd0257[] valuesArray = C1393xabbd0257.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1393xabbd0257 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1393xabbd0257 retVal = (C1393xabbd0257) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORECORDINGMODECHANGED_MODE_UNKNOWN_ENUM_VALUE;
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
