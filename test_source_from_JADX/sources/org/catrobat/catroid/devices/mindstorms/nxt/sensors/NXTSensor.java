package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import android.util.Log;
import java.util.Locale;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.nxt.Command;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandByte;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandType;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTError;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTReply;

public abstract class NXTSensor implements LegoSensor {
    public static final String TAG = NXTSensor.class.getSimpleName();
    protected final MindstormsConnection connection;
    protected boolean hasInit;
    protected float lastValidValue = 0.0f;
    protected final int port;
    protected final NXTSensorMode sensorMode;
    protected final NXTSensorType sensorType;
    protected final int updateInterval = 250;

    public enum Sensor {
        NO_SENSOR,
        TOUCH,
        SOUND,
        LIGHT_INACTIVE,
        LIGHT_ACTIVE,
        ULTRASONIC;

        public static String[] getSensorCodes() {
            String[] valueStrings = new String[values().length];
            for (int i = 0; i < values().length; i++) {
                valueStrings[i] = values()[i].name();
            }
            return valueStrings;
        }

        public String getSensorCode() {
            return name();
        }

        public static Sensor getSensorFromSensorCode(String sensorCode) {
            if (sensorCode == null) {
                return NO_SENSOR;
            }
            try {
                return valueOf(sensorCode);
            } catch (IllegalArgumentException e) {
                return NO_SENSOR;
            }
        }
    }

    private static class SensorReadings {
        public int normalized;
        public int raw;
        public int scaled;

        private SensorReadings() {
        }
    }

    public NXTSensor(int port, NXTSensorType sensorType, NXTSensorMode sensorMode, MindstormsConnection connection) {
        this.port = port;
        this.sensorType = sensorType;
        this.sensorMode = sensorMode;
        this.connection = connection;
    }

    protected void updateTypeAndMode() {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.SET_INPUT_MODE, true);
        command.append((byte) this.port);
        command.append(this.sensorType.getByte());
        command.append(this.sensorMode.getByte());
        NXTError.checkForError(new NXTReply(this.connection.sendAndReceive(command)), 3);
    }

    protected int getScaledValue() {
        return getSensorReadings().scaled;
    }

    protected int getRawValue() {
        return getSensorReadings().raw;
    }

    protected int getNormalizedValue() {
        return getSensorReadings().normalized;
    }

    public SensorReadings getSensorReadings() {
        if (!this.hasInit) {
            initialize();
        }
        SensorReadings sensorReadings = new SensorReadings();
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.GET_INPUT_VALUES, true);
        command.append((byte) this.port);
        NXTReply reply = new NXTReply(this.connection.sendAndReceive(command));
        NXTError.checkForError(reply, 16);
        sensorReadings.raw = reply.getShort(8);
        sensorReadings.normalized = reply.getShort(10);
        sensorReadings.scaled = reply.getShort(12);
        return sensorReadings;
    }

    protected void initialize() {
        if (this.connection == null || !this.connection.isConnected()) {
            this.hasInit = false;
            return;
        }
        updateTypeAndMode();
        try {
            Thread.sleep(100);
            resetScaledValue();
            Thread.sleep(100);
            updateTypeAndMode();
            this.hasInit = true;
        } catch (InterruptedException e) {
            this.hasInit = false;
        }
    }

    protected void resetScaledValue() {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.RESET_INPUT_SCALED_VALUE, false);
        command.append((byte) this.port);
        this.connection.send(command);
    }

    public int getUpdateInterval() {
        return 250;
    }

    public void updateLastSensorValue() {
        try {
            this.lastValidValue = getValue();
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public float getLastSensorValue() {
        return this.lastValidValue;
    }

    public String getName() {
        return String.format(Locale.getDefault(), "%s_%s_%d", new Object[]{TAG, this.sensorType.name(), Integer.valueOf(this.port)});
    }

    public int getConnectedPort() {
        return this.port;
    }
}
