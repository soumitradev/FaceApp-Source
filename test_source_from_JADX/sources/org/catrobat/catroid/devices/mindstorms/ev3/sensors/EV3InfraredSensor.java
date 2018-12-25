package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

public class EV3InfraredSensor extends EV3Sensor {
    private static final int DEFAULT_VALUE = 50;
    public static final String TAG = EV3InfraredSensor.class.getSimpleName();

    public EV3InfraredSensor(int port, MindstormsConnection connection) {
        super(port, EV3SensorType.EV3_INFRARED, EV3SensorMode.MODE0, connection);
        this.lastValidValue = 50.0f;
    }

    public float getValue() {
        return (float) getPercentValue();
    }
}
