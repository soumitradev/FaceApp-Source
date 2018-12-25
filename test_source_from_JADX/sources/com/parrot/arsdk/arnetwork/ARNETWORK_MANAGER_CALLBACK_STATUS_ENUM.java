package com.parrot.arsdk.arnetwork;

import java.util.HashMap;

public enum ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM {
    eARNETWORK_MANAGER_CALLBACK_STATUS_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARNETWORK_MANAGER_CALLBACK_STATUS_SENT(0, "data sent"),
    ARNETWORK_MANAGER_CALLBACK_STATUS_ACK_RECEIVED(1, "acknowledged received"),
    ARNETWORK_MANAGER_CALLBACK_STATUS_TIMEOUT(2, "timeout occurred, data not received; the callback must return what the network manager must do with the data."),
    ARNETWORK_MANAGER_CALLBACK_STATUS_CANCEL(3, "data will not sent"),
    ARNETWORK_MANAGER_CALLBACK_STATUS_FREE(4, "free the data sent without Data Copy."),
    ARNETWORK_MANAGER_CALLBACK_STATUS_DONE(5, "the use of the data is done, the date will not more used");
    
    static HashMap<Integer, ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM retVal = (ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARNETWORK_MANAGER_CALLBACK_STATUS_UNKNOWN_ENUM_VALUE;
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
