package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_PLAYSCRIPTCHANGED_RESULTCODE_ENUM */
public enum C1439x633744e8 {
    eARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_PLAYSCRIPTCHANGED_RESULTCODE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_PLAYSCRIPTCHANGED_RESULTCODE_SCRIPT_STARTED(0, "The script started playing successfully."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_PLAYSCRIPTCHANGED_RESULTCODE_SCRIPT_FINISHED(1, "The script finished successfully."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_PLAYSCRIPTCHANGED_RESULTCODE_SCRIPT_NO_SUCH_SCRIPT(2, "No script with this uuid exists."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_PLAYSCRIPTCHANGED_RESULTCODE_SCRIPT_ERROR(3, "An error occured while playing the script."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_PLAYSCRIPTCHANGED_RESULTCODE_MAX(4);
    
    static HashMap<Integer, C1439x633744e8> valuesList;
    private final String comment;
    private final int value;

    private C1439x633744e8(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1439x633744e8(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1439x633744e8 getFromValue(int value) {
        if (valuesList == null) {
            C1439x633744e8[] valuesArray = C1439x633744e8.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1439x633744e8 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1439x633744e8 retVal = (C1439x633744e8) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_PLAYSCRIPTCHANGED_RESULTCODE_UNKNOWN_ENUM_VALUE;
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
