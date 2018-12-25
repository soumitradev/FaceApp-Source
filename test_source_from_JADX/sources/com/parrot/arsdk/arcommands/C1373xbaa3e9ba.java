package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_ENUM */
public enum C1373xbaa3e9ba {
    eARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_TAKEN(0, "Picture taken and saved"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_FAILED(1, "Picture failed"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_MAX(2);
    
    static HashMap<Integer, C1373xbaa3e9ba> valuesList;
    private final String comment;
    private final int value;

    private C1373xbaa3e9ba(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1373xbaa3e9ba(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1373xbaa3e9ba getFromValue(int value) {
        if (valuesList == null) {
            C1373xbaa3e9ba[] valuesArray = C1373xbaa3e9ba.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1373xbaa3e9ba entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1373xbaa3e9ba retVal = (C1373xbaa3e9ba) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT_UNKNOWN_ENUM_VALUE;
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
