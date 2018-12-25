package com.parrot.arsdk.arcontroller;

import java.util.HashMap;

public enum ARCONTROLLER_STREAM_CODEC_TYPE_ENUM {
    eARCONTROLLER_STREAM_CODEC_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCONTROLLER_STREAM_CODEC_TYPE_DEFAULT(0, "default value"),
    ARCONTROLLER_STREAM_CODEC_TYPE_H264(1, "h264 codec"),
    ARCONTROLLER_STREAM_CODEC_TYPE_MJPEG(2, "MJPEG codec"),
    ARCONTROLLER_STREAM_CODEC_TYPE_PCM16LE(3, "PCM16LE codec"),
    ARCONTROLLER_STREAM_CODEC_TYPE_MAX(4, "Max of the enumeration");
    
    static HashMap<Integer, ARCONTROLLER_STREAM_CODEC_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCONTROLLER_STREAM_CODEC_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCONTROLLER_STREAM_CODEC_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCONTROLLER_STREAM_CODEC_TYPE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCONTROLLER_STREAM_CODEC_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCONTROLLER_STREAM_CODEC_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCONTROLLER_STREAM_CODEC_TYPE_ENUM retVal = (ARCONTROLLER_STREAM_CODEC_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCONTROLLER_STREAM_CODEC_TYPE_UNKNOWN_ENUM_VALUE;
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
