package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIASTREAMINGSTATE_VIDEOSTREAMMODECHANGED_MODE_ENUM */
public enum C1384xe5650d6d {
    eARCOMMANDS_ARDRONE3_MEDIASTREAMINGSTATE_VIDEOSTREAMMODECHANGED_MODE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_MEDIASTREAMINGSTATE_VIDEOSTREAMMODECHANGED_MODE_LOW_LATENCY(0, "Minimize latency with average reliability (best for piloting)."),
    ARCOMMANDS_ARDRONE3_MEDIASTREAMINGSTATE_VIDEOSTREAMMODECHANGED_MODE_HIGH_RELIABILITY(1, "Maximize the reliability with an average latency (best when streaming quality is important but not the latency)."),
    ARCOMMANDS_ARDRONE3_MEDIASTREAMINGSTATE_VIDEOSTREAMMODECHANGED_MODE_HIGH_RELIABILITY_LOW_FRAMERATE(2, "Maximize the reliability using a framerate decimation with an average latency (best when streaming quality is important but not the latency)."),
    ARCOMMANDS_ARDRONE3_MEDIASTREAMINGSTATE_VIDEOSTREAMMODECHANGED_MODE_MAX(3);
    
    static HashMap<Integer, C1384xe5650d6d> valuesList;
    private final String comment;
    private final int value;

    private C1384xe5650d6d(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1384xe5650d6d(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1384xe5650d6d getFromValue(int value) {
        if (valuesList == null) {
            C1384xe5650d6d[] valuesArray = C1384xe5650d6d.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1384xe5650d6d entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1384xe5650d6d retVal = (C1384xe5650d6d) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_MEDIASTREAMINGSTATE_VIDEOSTREAMMODECHANGED_MODE_UNKNOWN_ENUM_VALUE;
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
