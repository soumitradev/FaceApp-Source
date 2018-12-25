package com.parrot.arsdk.armavlink;

import android.util.SparseArray;

public enum MAV_PHOTO_SENSORS_FLAG {
    RGB(1),
    GREEN_BAND(16),
    RED_BAND(256),
    RED_EDGE_BAND(4096),
    NEAR_IR_BAND(65536);
    
    private static SparseArray<MAV_PHOTO_SENSORS_FLAG> valuesList;
    private final int value;

    private MAV_PHOTO_SENSORS_FLAG(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
