package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

public enum NXTSensorType {
    NO_SENSOR(0),
    TOUCH(1),
    TEMPERATURE(2),
    LIGHT_ACTIVE(5),
    LIGHT_INACTIVE(6),
    SOUND_DB(7),
    SOUND_DBA(8),
    LOW_SPEED(10),
    LOW_SPEED_9V(11);
    
    private int sensorTypeValue;

    private NXTSensorType(int sensorTypeValue) {
        this.sensorTypeValue = sensorTypeValue;
    }

    public byte getByte() {
        return (byte) this.sensorTypeValue;
    }
}
