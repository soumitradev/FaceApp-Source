package com.parrot.arsdk.arsal;

import android.support.v4.app.NotificationManagerCompat;
import java.util.HashMap;

public enum ARSAL_ERROR_ENUM {
    eARSAL_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARSAL_OK(0, "No error"),
    ARSAL_ERROR(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, "ARSAL Generic error"),
    ARSAL_ERROR_ALLOC(-999, "ARSAL alloc error"),
    ARSAL_ERROR_SYSTEM(-998, "ARSAL system error"),
    ARSAL_ERROR_BAD_PARAMETER(-997, "ARSAL bad parameter error"),
    ARSAL_ERROR_FILE(-996, "ARSAL file error"),
    ARSAL_ERROR_MD5(-2000, "ARSAL md5 error"),
    ARSAL_ERROR_BLE_CONNECTION(-5000, "BLE connection generic error"),
    ARSAL_ERROR_BLE_NOT_CONNECTED(-4999, "BLE is not connected"),
    ARSAL_ERROR_BLE_DISCONNECTION(-4998, "BLE disconnection error"),
    ARSAL_ERROR_BLE_SERVICES_DISCOVERING(-4997, "BLE network services discovering error"),
    ARSAL_ERROR_BLE_CHARACTERISTICS_DISCOVERING(-4996, "BLE network characteristics discovering error"),
    ARSAL_ERROR_BLE_CHARACTERISTIC_CONFIGURING(-4995, "BLE network characteristic configuring error"),
    ARSAL_ERROR_BLE_STACK(-4994, "BLE stack generic error"),
    ARSAL_ERROR_BLE_TIMEOUT(-4993, "BLE timeout"),
    ARSAL_ERROR_BLE_NO_DATA(-4992, "BLE no data");
    
    static HashMap<Integer, ARSAL_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARSAL_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARSAL_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARSAL_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARSAL_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARSAL_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARSAL_ERROR_ENUM retVal = (ARSAL_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARSAL_ERROR_UNKNOWN_ENUM_VALUE;
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
