package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_ENUM */
public enum C1450x20b2c26f {
    eARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_READY(0, "The picture recording is ready"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_BUSY(1, "The picture recording is busy"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_NOTAVAILABLE(2, "The picture recording is not available"),
    ARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_MAX(3);
    
    static HashMap<Integer, C1450x20b2c26f> valuesList;
    private final String comment;
    private final int value;

    private C1450x20b2c26f(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1450x20b2c26f(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1450x20b2c26f getFromValue(int value) {
        if (valuesList == null) {
            C1450x20b2c26f[] valuesArray = C1450x20b2c26f.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1450x20b2c26f entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1450x20b2c26f retVal = (C1450x20b2c26f) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_POWERUP_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE_UNKNOWN_ENUM_VALUE;
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
