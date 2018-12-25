package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGSSTATE_THEMECHANGED_THEME_ENUM */
public enum C1423xa948c651 {
    eARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGSSTATE_THEMECHANGED_THEME_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGSSTATE_THEMECHANGED_THEME_DEFAULT(0, "Default audio theme (depends on the product color)"),
    ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGSSTATE_THEMECHANGED_THEME_ROBOT(1, "Robot audio theme."),
    ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGSSTATE_THEMECHANGED_THEME_INSECT(2, "Insect audio theme."),
    ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGSSTATE_THEMECHANGED_THEME_MONSTER(3, "Monster audio theme."),
    ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGSSTATE_THEMECHANGED_THEME_MAX(4);
    
    static HashMap<Integer, C1423xa948c651> valuesList;
    private final String comment;
    private final int value;

    private C1423xa948c651(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1423xa948c651(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1423xa948c651 getFromValue(int value) {
        if (valuesList == null) {
            C1423xa948c651[] valuesArray = C1423xa948c651.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1423xa948c651 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1423xa948c651 retVal = (C1423xa948c651) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGSSTATE_THEMECHANGED_THEME_UNKNOWN_ENUM_VALUE;
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
