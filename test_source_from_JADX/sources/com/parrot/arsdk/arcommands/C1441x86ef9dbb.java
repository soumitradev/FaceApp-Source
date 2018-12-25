package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTUPLOADCHANGED_RESULTCODE_ENUM */
public enum C1441x86ef9dbb {
    eARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTUPLOADCHANGED_RESULTCODE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTUPLOADCHANGED_RESULTCODE_ERROR_OK(0, "The script was parsed successfully."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTUPLOADCHANGED_RESULTCODE_ERROR_FILE_CORRUPTED(1, "The MD5 hash codes are different or file is unreadable."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTUPLOADCHANGED_RESULTCODE_ERROR_INVALID_FORMAT(2, "The parser is not well formed or can not be parsed."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTUPLOADCHANGED_RESULTCODE_ERROR_FILE_TOO_LARGE(3, "The file is larger than maximum allowed size."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTUPLOADCHANGED_RESULTCODE_ERROR_UNSUPPORTED(4, "Script version is not supported by device."),
    ARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTUPLOADCHANGED_RESULTCODE_MAX(5);
    
    static HashMap<Integer, C1441x86ef9dbb> valuesList;
    private final String comment;
    private final int value;

    private C1441x86ef9dbb(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1441x86ef9dbb(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1441x86ef9dbb getFromValue(int value) {
        if (valuesList == null) {
            C1441x86ef9dbb[] valuesArray = C1441x86ef9dbb.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1441x86ef9dbb entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1441x86ef9dbb retVal = (C1441x86ef9dbb) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_JUMPINGSUMO_ROADPLANSTATE_SCRIPTUPLOADCHANGED_RESULTCODE_UNKNOWN_ENUM_VALUE;
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
