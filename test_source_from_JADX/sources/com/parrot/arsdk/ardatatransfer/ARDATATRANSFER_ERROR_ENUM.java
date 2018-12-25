package com.parrot.arsdk.ardatatransfer;

import android.support.v4.app.NotificationManagerCompat;
import java.util.HashMap;

public enum ARDATATRANSFER_ERROR_ENUM {
    eARDATATRANSFER_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARDATATRANSFER_OK(0, "No error"),
    ARDATATRANSFER_ERROR(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, "Unknown generic error"),
    ARDATATRANSFER_ERROR_ALLOC(-999, "Memory allocation error"),
    ARDATATRANSFER_ERROR_BAD_PARAMETER(-998, "Bad parameters error"),
    ARDATATRANSFER_ERROR_NOT_INITIALIZED(-997, "Not initialized error"),
    ARDATATRANSFER_ERROR_ALREADY_INITIALIZED(-996, "Already initialized error"),
    ARDATATRANSFER_ERROR_THREAD_ALREADY_RUNNING(-995, "Thread already running error"),
    ARDATATRANSFER_ERROR_THREAD_PROCESSING(-994, "Thread processing error"),
    ARDATATRANSFER_ERROR_CANCELED(-993, "Canceled received"),
    ARDATATRANSFER_ERROR_SYSTEM(-992, "System error"),
    ARDATATRANSFER_ERROR_FTP(-991, "Ftp error"),
    ARDATATRANSFER_ERROR_FILE(-990, "File error");
    
    static HashMap<Integer, ARDATATRANSFER_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARDATATRANSFER_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARDATATRANSFER_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARDATATRANSFER_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARDATATRANSFER_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARDATATRANSFER_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARDATATRANSFER_ERROR_ENUM retVal = (ARDATATRANSFER_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARDATATRANSFER_ERROR_UNKNOWN_ENUM_VALUE;
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
