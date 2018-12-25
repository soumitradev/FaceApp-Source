package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_CALIBRATIONSTATE_PITOTCALIBRATIONSTATECHANGED_STATE_ENUM */
public enum C1412xd2bdba57 {
    eARCOMMANDS_COMMON_CALIBRATIONSTATE_PITOTCALIBRATIONSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_CALIBRATIONSTATE_PITOTCALIBRATIONSTATECHANGED_STATE_DONE(0, "Calibration is ok"),
    ARCOMMANDS_COMMON_CALIBRATIONSTATE_PITOTCALIBRATIONSTATECHANGED_STATE_READY(1, "Calibration is started, waiting user action"),
    ARCOMMANDS_COMMON_CALIBRATIONSTATE_PITOTCALIBRATIONSTATECHANGED_STATE_IN_PROGRESS(2, "Calibration is in progress"),
    ARCOMMANDS_COMMON_CALIBRATIONSTATE_PITOTCALIBRATIONSTATECHANGED_STATE_REQUIRED(3, "Calibration is required"),
    ARCOMMANDS_COMMON_CALIBRATIONSTATE_PITOTCALIBRATIONSTATECHANGED_STATE_MAX(4);
    
    static HashMap<Integer, C1412xd2bdba57> valuesList;
    private final String comment;
    private final int value;

    private C1412xd2bdba57(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1412xd2bdba57(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1412xd2bdba57 getFromValue(int value) {
        if (valuesList == null) {
            C1412xd2bdba57[] valuesArray = C1412xd2bdba57.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1412xd2bdba57 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1412xd2bdba57 retVal = (C1412xd2bdba57) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_CALIBRATIONSTATE_PITOTCALIBRATIONSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE;
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
