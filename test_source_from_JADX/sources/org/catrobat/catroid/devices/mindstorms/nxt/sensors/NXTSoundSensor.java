package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

public class NXTSoundSensor extends NXTSensor {
    private static final int DEFAULT_VALUE = 0;
    public static final String TAG = NXTLightSensor.class.getSimpleName();

    public NXTSoundSensor(int port, MindstormsConnection connection) {
        super(port, NXTSensorType.SOUND_DBA, NXTSensorMode.Percent, connection);
        this.lastValidValue = 0.0f;
    }

    public float getValue() {
        return (float) getScaledValue();
    }
}
