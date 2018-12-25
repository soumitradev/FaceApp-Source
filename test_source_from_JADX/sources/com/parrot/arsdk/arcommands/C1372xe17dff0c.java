package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM */
public enum C1372xe17dff0c {
    eARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_OK(0, "No Error"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_UNKNOWN(1, "Unknown generic error ; only when state is failed"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_BUSY(2, "Picture recording is busy ; only when state is failed"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_NOTAVAILABLE(3, "Picture recording not available ; only when state is failed"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_MEMORYFULL(4, "Memory full ; only when state is failed"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_LOWBATTERY(5, "Battery is too low to record."),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_MAX(6);
    
    static HashMap<Integer, C1372xe17dff0c> valuesList;
    private final String comment;
    private final int value;

    private C1372xe17dff0c(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1372xe17dff0c(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1372xe17dff0c getFromValue(int value) {
        if (valuesList == null) {
            C1372xe17dff0c[] valuesArray = C1372xe17dff0c.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1372xe17dff0c entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1372xe17dff0c retVal = (C1372xe17dff0c) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_UNKNOWN_ENUM_VALUE;
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
