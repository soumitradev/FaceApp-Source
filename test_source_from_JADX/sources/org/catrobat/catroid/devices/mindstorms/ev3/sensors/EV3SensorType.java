package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

public enum EV3SensorType {
    NO_SENSOR(0),
    NXT_TOUCH(1),
    NXT_LIGHT(2),
    NXT_SOUND(3),
    NXT_COLOR(4),
    NXT_ULTRASONIC(5),
    NXT_TEMPERATURE(6),
    EV3_LARGE_MOTOR(7),
    EV3_MEDIUM_MOTOR(8),
    EV3_TOUCH(16),
    EV3_COLOR(29),
    EV3_ULTRASONIC(30),
    EV3_GYRO(32),
    EV3_INFRARED(33),
    ENERGY_METER(99),
    IIC(100);
    
    private int sensorTypeValue;

    private EV3SensorType(int sensorTypeValue) {
        this.sensorTypeValue = sensorTypeValue;
    }

    public byte getByte() {
        return (byte) this.sensorTypeValue;
    }
}
