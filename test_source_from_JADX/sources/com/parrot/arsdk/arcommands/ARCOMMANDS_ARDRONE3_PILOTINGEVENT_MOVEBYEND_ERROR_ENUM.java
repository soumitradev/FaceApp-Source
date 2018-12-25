package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_ENUM {
    eARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_OK(0, "No Error ; The relative displacement"),
    ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_UNKNOWN(1, "Unknown generic error"),
    ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_BUSY(2, "The Device is busy ; command moveBy ignored"),
    ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_NOTAVAILABLE(3, "Command moveBy is not available ; command moveBy ignored"),
    ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_INTERRUPTED(4, "Command moveBy interrupted"),
    ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_MAX(5);
    
    static HashMap<Integer, ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_ENUM retVal = (ARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR_UNKNOWN_ENUM_VALUE;
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
