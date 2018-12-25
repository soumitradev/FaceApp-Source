package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM */
public enum C1442x4c61f395 {
    eARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_OK(0, "No Error"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_UNKNOWN(1, "Unknown generic error ; only when state is failed"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_BUSY(2, "Picture recording is busy ; only when state is failed"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_NOTAVAILABLE(3, "Picture recording not available ; only when state is failed"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_MEMORYFULL(4, "Memory full ; only when state is failed"),
    ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_LOWBATTERY(5, "Battery is too low to record."),
    ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_MAX(6);
    
    static HashMap<Integer, C1442x4c61f395> valuesList;
    private final String comment;
    private final int value;

    private C1442x4c61f395(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1442x4c61f395(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1442x4c61f395 getFromValue(int value) {
        if (valuesList == null) {
            C1442x4c61f395[] valuesArray = C1442x4c61f395.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1442x4c61f395 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1442x4c61f395 retVal = (C1442x4c61f395) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_UNKNOWN_ENUM_VALUE;
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
