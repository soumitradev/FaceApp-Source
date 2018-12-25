package com.parrot.arsdk.armavlink;

import android.support.v4.app.NotificationManagerCompat;
import java.util.HashMap;

public enum ARMAVLINK_ERROR_ENUM {
    eARMAVLINK_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARMAVLINK_OK(0, "No error"),
    ARMAVLINK_ERROR(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, "Unknown generic error"),
    ARMAVLINK_ERROR_ALLOC(-999, "Memory allocation error"),
    ARMAVLINK_ERROR_BAD_PARAMETER(-998, "Bad parameters"),
    ARMAVLINK_ERROR_MANAGER(-2000, "Unknown ARMAVLINK_Manager error"),
    ARMAVLINK_ERROR_FILE_GENERATOR(-3000, "Unknown ARMAVLINK_FileGenerator error"),
    ARMAVLINK_ERROR_LIST_UTILS(-4000, "Unknown ARMAVLINK_ListUtils error"),
    ARMAVLINK_ERROR_MISSION_ITEM_UTILS(-5000, "Unknown ARMAVLINK_MissionItemUtils error"),
    ARMAVLINK_ERROR_MISSION_ITEM_UTILS_NOT_LINKED_COMMAND(-4999, "Command not linked with Mavlink commands"),
    ARMAVLINK_ERROR_FILE_PARSER(-6000, "Unknown ARMAVLINK_FileParser error"),
    ARMAVLINK_ERROR_FILE_PARSER_FILE_NOT_FOUND(-5999, "File to parse not found"),
    ARMAVLINK_ERROR_FILE_PARSER_WORD_NOT_EXPTECTED(-5998, "A word was not expected during parsing");
    
    static HashMap<Integer, ARMAVLINK_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARMAVLINK_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARMAVLINK_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARMAVLINK_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARMAVLINK_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARMAVLINK_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARMAVLINK_ERROR_ENUM retVal = (ARMAVLINK_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARMAVLINK_ERROR_UNKNOWN_ENUM_VALUE;
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
