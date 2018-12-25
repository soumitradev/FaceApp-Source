package com.parrot.arsdk.armedia;

import java.util.HashMap;

public enum ARMEDIA_VIDEOATOM_MEDIATYPE_ENUM {
    eARMEDIA_VIDEOATOM_MEDIATYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARMEDIA_VIDEOATOM_MEDIATYPE_VIDEO(0),
    ARMEDIA_VIDEOATOM_MEDIATYPE_SOUND(1),
    ARMEDIA_VIDEOATOM_MEDIATYPE_METADATA(2);
    
    static HashMap<Integer, ARMEDIA_VIDEOATOM_MEDIATYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARMEDIA_VIDEOATOM_MEDIATYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARMEDIA_VIDEOATOM_MEDIATYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARMEDIA_VIDEOATOM_MEDIATYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARMEDIA_VIDEOATOM_MEDIATYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARMEDIA_VIDEOATOM_MEDIATYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARMEDIA_VIDEOATOM_MEDIATYPE_ENUM retVal = (ARMEDIA_VIDEOATOM_MEDIATYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARMEDIA_VIDEOATOM_MEDIATYPE_UNKNOWN_ENUM_VALUE;
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
