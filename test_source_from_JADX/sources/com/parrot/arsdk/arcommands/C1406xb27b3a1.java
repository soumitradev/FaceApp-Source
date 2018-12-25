package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ENUM */
public enum C1406xb27b3a1 {
    eARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_NOERROR(0, "No error detected"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERROREEPROM(1, "EEPROM access failure"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORMOTORSTALLED(2, "Motor stalled"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORPROPELLERSECURITY(3, "Propeller cutout security triggered"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORCOMMLOST(4, "Communication with motor failed by timeout"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORRCEMERGENCYSTOP(5, "RC emergency stop"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORREALTIME(6, "Motor controler scheduler real-time out of bounds"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORMOTORSETTING(7, "One or several incorrect values in motor settings"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORBATTERYVOLTAGE(8, "Battery voltage out of bounds"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORLIPOCELLS(9, "Incorrect number of LIPO cells"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORMOSFET(10, "Defectuous MOSFET or broken motor phases"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORTEMPERATURE(11, "Too hot or too cold Cypress temperature"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORBOOTLOADER(12, "Not use for BLDC but useful for HAL"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_ERRORASSERT(13, "Error Made by BLDC_ASSERT()"),
    ARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_MAX(14);
    
    static HashMap<Integer, C1406xb27b3a1> valuesList;
    private final String comment;
    private final int value;

    private C1406xb27b3a1(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1406xb27b3a1(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1406xb27b3a1 getFromValue(int value) {
        if (valuesList == null) {
            C1406xb27b3a1[] valuesArray = C1406xb27b3a1.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1406xb27b3a1 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1406xb27b3a1 retVal = (C1406xb27b3a1) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR_UNKNOWN_ENUM_VALUE;
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
