package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM {
    eARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_DEFAULT(0, "Default audio theme (depends on the product color)"),
    ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ROBOT(1, "Robot audio theme."),
    ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_INSECT(2, "Insect audio theme."),
    ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_MONSTER(3, "Monster audio theme."),
    ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_MAX(4);
    
    static HashMap<Integer, ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM retVal = (ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_UNKNOWN_ENUM_VALUE;
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
