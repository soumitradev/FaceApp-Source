package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

public class NXTLightSensor extends NXTSensor {
    private static final int DEFAULT_VALUE = 50;
    public static final String TAG = NXTLightSensor.class.getSimpleName();

    public NXTLightSensor(int port, MindstormsConnection connection) {
        super(port, NXTSensorType.LIGHT_INACTIVE, NXTSensorMode.Percent, connection);
        this.lastValidValue = 50.0f;
    }

    public float getValue() {
        return (float) getScaledValue();
    }
}
