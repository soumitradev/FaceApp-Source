package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

public class EV3ColorSensor extends EV3Sensor {
    private static final int DEFAULT_VALUE = 50;
    private static final int SENSOR_VALUE_READ_LENGTH = 1;
    public static final String TAG = EV3ColorSensor.class.getSimpleName();

    public EV3ColorSensor(int port, MindstormsConnection connection, EV3SensorMode mode) {
        super(port, EV3SensorType.EV3_COLOR, mode, connection);
        this.lastValidValue = 50.0f;
    }

    public float getValue() {
        if (this.sensorMode == EV3SensorMode.MODE2) {
            return (float) (getRawValue(1)[0] & 255);
        }
        return (float) getPercentValue();
    }
}
