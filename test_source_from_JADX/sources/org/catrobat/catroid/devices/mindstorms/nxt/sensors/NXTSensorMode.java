package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

public enum NXTSensorMode {
    RAW(0),
    BOOL(32),
    Percent(128);
    
    private int sensorModeValue;

    private NXTSensorMode(int sensorModeValue) {
        this.sensorModeValue = sensorModeValue;
    }

    public byte getByte() {
        return (byte) this.sensorModeValue;
    }
}
