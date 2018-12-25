package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTDELETECHANGED_RESULTCODE_ENUM */
public enum C1440x28c026d1 {
    eARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTDELETECHANGED_RESULTCODE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTDELETECHANGED_RESULTCODE_ERROR_OK(0, "The script was deleted successfully."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTDELETECHANGED_RESULTCODE_ERROR_NO_SUCH_SCRIPT(1, "No script with this uuid exists."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTDELETECHANGED_RESULTCODE_ERROR_INTERNAL_FAILURE(2, "An internal error occured while attempting to delete the script."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTDELETECHANGED_RESULTCODE_MAX(3);
    
    static HashMap<Integer, C1440x28c026d1> valuesList;
    private final String comment;
    private final int value;

    private C1440x28c026d1(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1440x28c026d1(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1440x28c026d1 getFromValue(int value) {
        if (valuesList == null) {
            C1440x28c026d1[] valuesArray = C1440x28c026d1.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1440x28c026d1 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1440x28c026d1 retVal = (C1440x28c026d1) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTDELETECHANGED_RESULTCODE_UNKNOWN_ENUM_VALUE;
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
