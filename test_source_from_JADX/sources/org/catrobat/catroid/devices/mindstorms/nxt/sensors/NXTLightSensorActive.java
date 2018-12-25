package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

public class NXTLightSensorActive extends NXTSensor {
    private static final int DEFAULT_VALUE = 50;
    public static final String TAG = NXTLightSensorActive.class.getSimpleName();

    public NXTLightSensorActive(int port, MindstormsConnection connection) {
        super(port, NXTSensorType.LIGHT_ACTIVE, NXTSensorMode.Percent, connection);
        this.lastValidValue = 50.0f;
    }

    public float getValue() {
        return (float) getScaledValue();
    }
}
