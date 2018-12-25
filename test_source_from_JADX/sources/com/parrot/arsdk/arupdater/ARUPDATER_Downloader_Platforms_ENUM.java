package com.parrot.arsdk.arupdater;

import java.util.HashMap;

public enum ARUPDATER_Downloader_Platforms_ENUM {
    eARUPDATER_Downloader_Platforms_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARUPDATER_DOWNLOADER_ANDROID_PLATFORM(0),
    ARUPDATER_DOWNLOADER_IOS_PLATFORM(1);
    
    static HashMap<Integer, ARUPDATER_Downloader_Platforms_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARUPDATER_Downloader_Platforms_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARUPDATER_Downloader_Platforms_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARUPDATER_Downloader_Platforms_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARUPDATER_Downloader_Platforms_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARUPDATER_Downloader_Platforms_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARUPDATER_Downloader_Platforms_ENUM retVal = (ARUPDATER_Downloader_Platforms_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARUPDATER_Downloader_Platforms_UNKNOWN_ENUM_VALUE;
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
