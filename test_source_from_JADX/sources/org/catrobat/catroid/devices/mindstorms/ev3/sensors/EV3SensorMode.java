package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

public enum EV3SensorMode {
    MODE0(0),
    MODE1(1),
    MODE2(2),
    MODE3(3),
    MODE4(4),
    MODE5(5),
    MODE6(6),
    MODE7(7);
    
    private int sensorModeValue;

    private EV3SensorMode(int sensorModeValue) {
        this.sensorModeValue = sensorModeValue;
    }

    public byte getByte() {
        return (byte) this.sensorModeValue;
    }
}
