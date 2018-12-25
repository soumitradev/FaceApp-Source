package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_ENUM */
public enum C1374x3add62f {
    eARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_OK(0, "No Error"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_UNKNOWN(1, "Unknown generic error ; only when state is failed"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_BUSY(2, "Video recording is busy ; only when state is failed"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_NOTAVAILABLE(3, "Video recording not available ; only when state is failed"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_MEMORYFULL(4, "Memory full"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_LOWBATTERY(5, "Battery is too low to record."),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_AUTOSTOPPED(6, "Video was auto stopped"),
    ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_MAX(7);
    
    static HashMap<Integer, C1374x3add62f> valuesList;
    private final String comment;
    private final int value;

    private C1374x3add62f(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1374x3add62f(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1374x3add62f getFromValue(int value) {
        if (valuesList == null) {
            C1374x3add62f[] valuesArray = C1374x3add62f.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1374x3add62f entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1374x3add62f retVal = (C1374x3add62f) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR_UNKNOWN_ENUM_VALUE;
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
