package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

public class TemperatureSensor extends EV3Sensor {
    private static final int DEFAULT_VALUE = 0;
    private static final int SENSOR_VALUE_READ_LENGTH = 4;
    public static final String TAG = TemperatureSensor.class.getSimpleName();

    public TemperatureSensor(int port, MindstormsConnection connection, EV3SensorMode mode) {
        super(port, EV3SensorType.NXT_TEMPERATURE, mode, connection);
        this.lastValidValue = 0.0f;
    }

    public float getValue() {
        return ByteBuffer.wrap(getSiValue(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }
}
