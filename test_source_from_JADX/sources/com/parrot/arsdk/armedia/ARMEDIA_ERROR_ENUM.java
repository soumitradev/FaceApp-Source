package com.parrot.arsdk.armedia;

import android.support.v4.app.NotificationManagerCompat;
import java.util.HashMap;

public enum ARMEDIA_ERROR_ENUM {
    eARMEDIA_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARMEDIA_OK(0, "No error"),
    ARMEDIA_ERROR(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, "Unknown generic error"),
    ARMEDIA_ERROR_BAD_PARAMETER(-999, "Bad parameters"),
    ARMEDIA_ERROR_NOT_IMPLEMENTED(-998, "Function not implemented"),
    ARMEDIA_ERROR_MANAGER(-2000, "Unknown manager error"),
    ARMEDIA_ERROR_MANAGER_ALREADY_INITIALIZED(-1999, "Error manager already initialized"),
    ARMEDIA_ERROR_MANAGER_NOT_INITIALIZED(-1998, "Error manager not initialized"),
    ARMEDIA_ERROR_ENCAPSULER(-3000, "Error encapsuler generic error"),
    ARMEDIA_ERROR_ENCAPSULER_WAITING_FOR_IFRAME(-2999, "Error encapsuler waiting for i-frame"),
    ARMEDIA_ERROR_ENCAPSULER_BAD_CODEC(-2998, "Codec non-supported"),
    ARMEDIA_ERROR_ENCAPSULER_BAD_VIDEO_FRAME(-2997, "Error in video frame header"),
    ARMEDIA_ERROR_ENCAPSULER_BAD_VIDEO_SAMPLE(-2996, "Error in audio sample header"),
    ARMEDIA_ERROR_ENCAPSULER_FILE_ERROR(-2995, "File error while encapsulating"),
    ARMEDIA_ERROR_ENCAPSULER_BAD_TIMESTAMP(-2994, "Timestamp is before previous sample");
    
    static HashMap<Integer, ARMEDIA_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARMEDIA_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARMEDIA_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARMEDIA_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARMEDIA_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARMEDIA_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARMEDIA_ERROR_ENUM retVal = (ARMEDIA_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARMEDIA_ERROR_UNKNOWN_ENUM_VALUE;
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
