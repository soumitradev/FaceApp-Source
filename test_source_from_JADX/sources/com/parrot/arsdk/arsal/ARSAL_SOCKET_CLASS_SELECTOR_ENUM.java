package com.parrot.arsdk.arsal;

import java.util.HashMap;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;

public enum ARSAL_SOCKET_CLASS_SELECTOR_ENUM {
    eARSAL_SOCKET_CLASS_SELECTOR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARSAL_SOCKET_CLASS_SELECTOR_CS0(0),
    ARSAL_SOCKET_CLASS_SELECTOR_UNSPECIFIED(0),
    ARSAL_SOCKET_CLASS_SELECTOR_CS1(32),
    ARSAL_SOCKET_CLASS_SELECTOR_CS2(64),
    ARSAL_SOCKET_CLASS_SELECTOR_CS3(96),
    ARSAL_SOCKET_CLASS_SELECTOR_CS4(128),
    ARSAL_SOCKET_CLASS_SELECTOR_CS5(160),
    ARSAL_SOCKET_CLASS_SELECTOR_CS6(ReportAnalogPinMessageWriter.COMMAND),
    ARSAL_SOCKET_CLASS_SELECTOR_CS7(AnalogMessageWriter.COMMAND);
    
    static HashMap<Integer, ARSAL_SOCKET_CLASS_SELECTOR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARSAL_SOCKET_CLASS_SELECTOR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARSAL_SOCKET_CLASS_SELECTOR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARSAL_SOCKET_CLASS_SELECTOR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARSAL_SOCKET_CLASS_SELECTOR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARSAL_SOCKET_CLASS_SELECTOR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARSAL_SOCKET_CLASS_SELECTOR_ENUM retVal = (ARSAL_SOCKET_CLASS_SELECTOR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARSAL_SOCKET_CLASS_SELECTOR_UNKNOWN_ENUM_VALUE;
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
