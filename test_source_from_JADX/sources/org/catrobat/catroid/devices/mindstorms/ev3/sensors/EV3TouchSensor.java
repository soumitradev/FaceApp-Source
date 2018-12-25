package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

public class EV3TouchSensor extends EV3Sensor {
    private static final int DEFAULT_VALUE = 0;
    public static final String TAG = EV3TouchSensor.class.getSimpleName();

    public EV3TouchSensor(int port, MindstormsConnection connection) {
        super(port, EV3SensorType.EV3_TOUCH, EV3SensorMode.MODE0, connection);
        this.lastValidValue = 0.0f;
    }

    public float getValue() {
        if (getPercentValue() > 50) {
            return 1.0f;
        }
        return 0.0f;
    }
}
