package com.parrot.arsdk.arcommands;

import java.util.HashMap;

/* renamed from: com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_PICTUREFORMATCHANGED_TYPE_ENUM */
public enum C1391xf931d08 {
    eARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_PICTUREFORMATCHANGED_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_PICTUREFORMATCHANGED_TYPE_RAW(0, "Take raw image"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_PICTUREFORMATCHANGED_TYPE_JPEG(1, "Take a 4:3 jpeg photo"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_PICTUREFORMATCHANGED_TYPE_SNAPSHOT(2, "Take a 16:9 snapshot from camera"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_PICTUREFORMATCHANGED_TYPE_JPEG_FISHEYE(3, "Take jpeg fisheye image only"),
    ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_PICTUREFORMATCHANGED_TYPE_MAX(4);
    
    static HashMap<Integer, C1391xf931d08> valuesList;
    private final String comment;
    private final int value;

    private C1391xf931d08(int value) {
        this.value = value;
        this.comment = null;
    }

    private C1391xf931d08(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static C1391xf931d08 getFromValue(int value) {
        if (valuesList == null) {
            C1391xf931d08[] valuesArray = C1391xf931d08.values();
            valuesList = new HashMap(valuesArray.length);
            for (C1391xf931d08 entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        C1391xf931d08 retVal = (C1391xf931d08) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_PICTUREFORMATCHANGED_TYPE_UNKNOWN_ENUM_VALUE;
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
