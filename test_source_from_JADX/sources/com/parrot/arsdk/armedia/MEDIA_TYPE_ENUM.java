package com.parrot.arsdk.armedia;

import java.util.HashMap;

public enum MEDIA_TYPE_ENUM {
    eMEDIA_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    MEDIA_TYPE_VIDEO(0),
    MEDIA_TYPE_PHOTO(1),
    MEDIA_TYPE_MAX(2);
    
    static HashMap<Integer, MEDIA_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private MEDIA_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private MEDIA_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static MEDIA_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            MEDIA_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (MEDIA_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        MEDIA_TYPE_ENUM retVal = (MEDIA_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eMEDIA_TYPE_UNKNOWN_ENUM_VALUE;
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
