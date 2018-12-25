package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

public class NXTTouchSensor extends NXTSensor {
    private static final int DEFAULT_VALUE = 0;
    public static final String TAG = NXTLightSensor.class.getSimpleName();

    public NXTTouchSensor(int port, MindstormsConnection connection) {
        super(port, NXTSensorType.TOUCH, NXTSensorMode.BOOL, connection);
        this.lastValidValue = 0.0f;
    }

    public float getValue() {
        return (float) getScaledValue();
    }
}
