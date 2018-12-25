package com.parrot.arsdk.arnetwork;

import android.support.v4.app.NotificationManagerCompat;
import java.util.HashMap;

public enum ARNETWORK_ERROR_ENUM {
    eARNETWORK_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARNETWORK_OK(0, "No error"),
    ARNETWORK_ERROR(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, "Unknown generic error"),
    ARNETWORK_ERROR_ALLOC(-999, "Memory allocation error"),
    ARNETWORK_ERROR_BAD_PARAMETER(-998, "Bad parameters"),
    ARNETWORK_ERROR_ID_UNKNOWN(-997, "Given IOBuffer identifier is unknown"),
    ARNETWORK_ERROR_BUFFER_SIZE(-996, "Insufficient free space in the buffer"),
    ARNETWORK_ERROR_BUFFER_EMPTY(-995, "Buffer is empty, nothing was read"),
    ARNETWORK_ERROR_SEMAPHORE(-994, "Error when using a semaphore"),
    ARNETWORK_ERROR_MUTEX(-993, "Error when using a mutex"),
    ARNETWORK_ERROR_MUTEX_DOUBLE_LOCK(-992, "A mutex is already locked by the same thread"),
    ARNETWORK_ERROR_MANAGER(-2000, "Unknown ARNETWORK_Manager error"),
    ARNETWORK_ERROR_MANAGER_NEW_IOBUFFER(-1999, "IOBuffer creation error"),
    ARNETWORK_ERROR_MANAGER_NEW_SENDER(-1998, "Sender creation error"),
    ARNETWORK_ERROR_MANAGER_NEW_RECEIVER(-1997, "Receiver creation error"),
    ARNETWORK_ERROR_NEW_BUFFER(-1996, "Buffer creation error"),
    ARNETWORK_ERROR_NEW_RINGBUFFER(-1995, "RingBuffer creation error"),
    ARNETWORK_ERROR_IOBUFFER(-3000, "Unknown IOBuffer error"),
    ARNETWORK_ERROR_IOBUFFER_BAD_ACK(-2999, "Bad sequence number for the acknowledge"),
    ARNETWORK_ERROR_RECEIVER(-5000, "Unknown Receiver error"),
    ARNETWORK_ERROR_RECEIVER_BUFFER_END(-4999, "Receiver buffer too small"),
    ARNETWORK_ERROR_RECEIVER_BAD_FRAME(-4998, "Bad frame content on network");
    
    static HashMap<Integer, ARNETWORK_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARNETWORK_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARNETWORK_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARNETWORK_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARNETWORK_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARNETWORK_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARNETWORK_ERROR_ENUM retVal = (ARNETWORK_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARNETWORK_ERROR_UNKNOWN_ENUM_VALUE;
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
