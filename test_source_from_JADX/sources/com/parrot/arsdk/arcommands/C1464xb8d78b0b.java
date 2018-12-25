package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_SKYCONTROLLER_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATE_STATUS_ENUM */
public enum C1464xb8d78b0b {
    eARCOMMANDS_SKYCONTROLLER_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATE_STATUS_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_SKYCONTROLLER_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATE_STATUS_UNRELIABLE(0, "A calibration is needed"),
    ARCOMMANDS_SKYCONTROLLER_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATE_STATUS_ASSESSING(1, "A calibration is applied, but still need to be checked"),
    ARCOMMANDS_SKYCONTROLLER_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATE_STATUS_CALIBRATED(2, "The sensor is properly calibrated"),
    ARCOMMANDS_SKYCONTROLLER_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATE_STATUS_MAX(3);
    
    static HashMap<Integer, C1464xb8d78b0b> valuesList;
    private final String comment;
    private final int value;

    private C1464xb8d78b0b(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1464xb8d78b0b(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1464xb8d78b0b getFromValue(int value) {
        if (valuesList == null) {
            C1464xb8d78b0b[] valuesArray = C1464xb8d78b0b.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1464xb8d78b0b entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1464xb8d78b0b retVal = (C1464xb8d78b0b) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_SKYCONTROLLER_CALIBRATIONSTATE_MAGNETOCALIBRATIONSTATE_STATUS_UNKNOWN_ENUM_VALUE;
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
