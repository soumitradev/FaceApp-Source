package com.parrot.arsdk.arnetworkal;

import android.support.v4.app.NotificationManagerCompat;
import java.util.HashMap;

public enum ARNETWORKAL_ERROR_ENUM {
    eARNETWORKAL_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARNETWORKAL_OK(0, "No error"),
    ARNETWORKAL_ERROR(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, "ARNetworkAL Generic error"),
    ARNETWORKAL_ERROR_ALLOC(-999, "Memory allocation error"),
    ARNETWORKAL_ERROR_BAD_PARAMETER(-998, "Bad parameters"),
    ARNETWORKAL_ERROR_FIFO_INIT(-997, "Fifo creation error (details set in errno)"),
    ARNETWORKAL_ERROR_MAIN_THREAD(-996, "The function cannot be run in main thread"),
    ARNETWORKAL_ERROR_MANAGER(-2000, "Manager generic error"),
    ARNETWORKAL_ERROR_MANAGER_OPERATION_NOT_SUPPORTED(-1999, "The current manager does not support this operation"),
    ARNETWORKAL_ERROR_NETWORK(-3000, "Network generic error"),
    ARNETWORKAL_ERROR_NETWORK_TYPE(-2999, "Network type, not available for the platform error"),
    ARNETWORKAL_ERROR_WIFI(-4000, "Wifi generic error"),
    ARNETWORKAL_ERROR_WIFI_SOCKET_CREATION(-3999, "Wifi socket error during creation"),
    ARNETWORKAL_ERROR_WIFI_SOCKET_PERMISSION_DENIED(-3998, "Wifi socket permission denied"),
    ARNETWORKAL_ERROR_WIFI_SOCKET_GETOPT(-3997, "wifi socket error on getopt"),
    ARNETWORKAL_ERROR_WIFI_SOCKET_SETOPT(-3996, "wifi socket error on setopt"),
    ARNETWORKAL_ERROR_BLE_CONNECTION(-5000, "BLE connection generic error"),
    ARNETWORKAL_ERROR_BLE_NOT_CONNECTED(-4999, "BLE is not connected"),
    ARNETWORKAL_ERROR_BLE_DISCONNECTION(-4998, "BLE disconnection error"),
    ARNETWORKAL_ERROR_BLE_SERVICES_DISCOVERING(-4997, "BLE network services discovering error"),
    ARNETWORKAL_ERROR_BLE_CHARACTERISTICS_DISCOVERING(-4996, "BLE network characteristics discovering error"),
    ARNETWORKAL_ERROR_BLE_CHARACTERISTIC_CONFIGURING(-4995, "BLE network characteristic configuring error"),
    ARNETWORKAL_ERROR_BLE_STACK(-4994, "BLE stack generic error");
    
    static HashMap<Integer, ARNETWORKAL_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARNETWORKAL_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARNETWORKAL_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARNETWORKAL_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARNETWORKAL_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARNETWORKAL_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARNETWORKAL_ERROR_ENUM retVal = (ARNETWORKAL_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARNETWORKAL_ERROR_UNKNOWN_ENUM_VALUE;
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
