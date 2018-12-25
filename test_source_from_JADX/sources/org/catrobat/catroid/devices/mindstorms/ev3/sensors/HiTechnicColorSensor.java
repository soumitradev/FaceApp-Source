package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

public class HiTechnicColorSensor extends EV3Sensor {
    private static final int DEFAULT_VALUE = 0;
    private static final int SENSOR_VALUE_READ_LENGTH = 1;
    public static final String TAG = HiTechnicColorSensor.class.getSimpleName();

    public HiTechnicColorSensor(int port, MindstormsConnection connection, EV3SensorMode mode) {
        super(port, EV3SensorType.IIC, mode, connection);
        this.lastValidValue = 0.0f;
    }

    public float getValue() {
        return (float) (getRawValue(1)[0] & 255);
    }
}
