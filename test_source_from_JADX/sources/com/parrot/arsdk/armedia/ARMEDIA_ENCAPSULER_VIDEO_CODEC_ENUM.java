package com.parrot.arsdk.armedia;

import java.util.HashMap;

public enum ARMEDIA_ENCAPSULER_VIDEO_CODEC_ENUM {
    eARMEDIA_ENCAPSULER_VIDEO_CODEC_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    CODEC_UNKNNOWN(0),
    CODEC_MPEG4_VISUAL(1),
    CODEC_MPEG4_AVC(2),
    CODEC_MOTION_JPEG(3);
    
    static HashMap<Integer, ARMEDIA_ENCAPSULER_VIDEO_CODEC_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARMEDIA_ENCAPSULER_VIDEO_CODEC_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARMEDIA_ENCAPSULER_VIDEO_CODEC_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARMEDIA_ENCAPSULER_VIDEO_CODEC_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARMEDIA_ENCAPSULER_VIDEO_CODEC_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARMEDIA_ENCAPSULER_VIDEO_CODEC_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARMEDIA_ENCAPSULER_VIDEO_CODEC_ENUM retVal = (ARMEDIA_ENCAPSULER_VIDEO_CODEC_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARMEDIA_ENCAPSULER_VIDEO_CODEC_UNKNOWN_ENUM_VALUE;
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
