package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import android.util.Log;
import android.util.SparseArray;
import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.util.Locale;
import name.antonsmirnov.firmata.message.ReportFirmwareVersionMessage;
import name.antonsmirnov.firmata.message.SamplingIntervalMessage;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3Command;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandByteCode;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandOpCode;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandParamFormat;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandVariableScope;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandType;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3Reply;

public abstract class EV3Sensor implements LegoSensor {
    public static final String TAG = EV3Sensor.class.getSimpleName();
    protected final MindstormsConnection connection;
    protected boolean hasInit;
    protected float lastValidValue = 0.0f;
    protected final int port;
    protected final EV3SensorMode sensorMode;
    protected final EV3SensorType sensorType;
    protected final int updateInterval = 250;

    public enum Sensor {
        NO_SENSOR,
        TOUCH,
        COLOR,
        COLOR_AMBIENT,
        COLOR_REFLECT,
        INFRARED,
        HT_NXT_COLOR,
        NXT_TEMPERATURE_C,
        NXT_TEMPERATURE_F;

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

    public enum SensorConnectionType {
        CONN_UNKNOWN(111),
        CONN_DAISYCHAIN(117),
        CONN_NXT_COLOR(118),
        CONN_NXT_ANALOG(119),
        CONN_NXT_IIC(120),
        CONN_EV3_IN_DUMB(ReportFirmwareVersionMessage.COMMAND),
        CONN_EV3_IN_UART(SamplingIntervalMessage.COMMAND),
        CONN_EV3_OUT_DUMB(123),
        CONN_EV3_OUT_INTELLIGENT(124),
        CONN_EV3_OUT_TACHO(125),
        CONN_NONE(126),
        CONN_ERROR(MetaEvent.SEQUENCER_SPECIFIC);
        
        private static final SparseArray<SensorConnectionType> LOOKUP = null;
        private int sensorConnectionByteCode;

        static {
            LOOKUP = new SparseArray();
            SensorConnectionType[] values = values();
            int length = values.length;
            int i;
            while (i < length) {
                SensorConnectionType c = values[i];
                LOOKUP.put(c.sensorConnectionByteCode, c);
                i++;
            }
        }

        public static SensorConnectionType getSensorConnectionTypeByValue(byte value) {
            return (SensorConnectionType) LOOKUP.get(value & 255);
        }

        public static boolean isMember(byte memberToTest) {
            return LOOKUP.get(memberToTest & 255) != null;
        }

        private SensorConnectionType(int sensorConnectionType) {
            this.sensorConnectionByteCode = sensorConnectionType;
        }

        public byte getByte() {
            return (byte) this.sensorConnectionByteCode;
        }
    }

    public EV3Sensor(int port, EV3SensorType sensorType, EV3SensorMode sensorMode, MindstormsConnection connection) {
        this.port = port;
        this.sensorType = sensorType;
        this.sensorMode = sensorMode;
        this.connection = connection;
    }

    public SensorConnectionType getConnectionType(int chainLayer) {
        int commandCount = this.connection.getCommandCounter();
        byte connectionType = (byte) 0;
        EV3Command command = new EV3Command(this.connection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_REPLY, 1, 0, EV3CommandOpCode.OP_INPUT_DEVICE);
        this.connection.incCommandCounter();
        command.append(EV3CommandByteCode.INPUT_DEVICE_GET_CONNECTION.getByte());
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, chainLayer);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, this.port);
        command.append(EV3CommandVariableScope.PARAM_VARIABLE_SCOPE_GLOBAL, 0);
        try {
            EV3Reply reply = new EV3Reply(this.connection.sendAndReceive(command));
            if (reply.isValid(commandCount)) {
                connectionType = reply.getByte(3);
                return SensorConnectionType.getSensorConnectionTypeByValue(connectionType);
            }
            throw new MindstormsException("Reply not valid!");
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    protected void setMode(EV3SensorMode mode) {
        int commandCount = this.connection.getCommandCounter();
        EV3Command command = new EV3Command(this.connection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_REPLY, 1, 0, EV3CommandOpCode.OP_INPUT_READ_SI);
        this.connection.incCommandCounter();
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 0);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, this.port);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 0);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, mode.getByte());
        command.append(EV3CommandVariableScope.PARAM_VARIABLE_SCOPE_GLOBAL, 0);
        try {
            if (!new EV3Reply(this.connection.sendAndReceive(command)).isValid(commandCount)) {
                throw new MindstormsException("Reply not valid!");
            }
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    protected void initialize() {
        if (this.connection == null || !this.connection.isConnected()) {
            this.hasInit = false;
            return;
        }
        setMode(this.sensorMode);
        int commandCount = this.connection.getCommandCounter();
        EV3Command command = new EV3Command(this.connection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_REPLY, 1, 0, EV3CommandOpCode.OP_INPUT_DEVICE);
        this.connection.incCommandCounter();
        command.append(EV3CommandByteCode.INPUT_DEVICE_READY_RAW.getByte());
        int mode = this.sensorMode.getByte();
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 0);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, this.port);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 0);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, mode);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 1);
        command.append(EV3CommandVariableScope.PARAM_VARIABLE_SCOPE_GLOBAL, 0);
        try {
            if (new EV3Reply(this.connection.sendAndReceive(command)).isValid(commandCount)) {
                this.hasInit = true;
                return;
            }
            throw new MindstormsException("Reply not valid!");
        } catch (MindstormsException e) {
            this.hasInit = false;
            Log.e(TAG, e.getMessage());
        }
    }

    public int getPercentValue() {
        if (this.hasInit) {
            int commandCount = this.connection.getCommandCounter();
            EV3Command command = new EV3Command(this.connection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_REPLY, 1, 0, EV3CommandOpCode.OP_INPUT_READ);
            this.connection.incCommandCounter();
            command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 0);
            command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, this.port);
            command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 0);
            command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, -1);
            command.append(EV3CommandVariableScope.PARAM_VARIABLE_SCOPE_GLOBAL, 0);
            try {
                EV3Reply reply = new EV3Reply(this.connection.sendAndReceive(command));
                if (reply.isValid(commandCount)) {
                    return reply.getByte(3);
                }
                throw new MindstormsException("Reply not valid!");
            } catch (MindstormsException e) {
                Log.e(TAG, e.getMessage());
                return 0;
            }
        }
        initialize();
        return 0;
    }

    public byte[] getRawValue(int numBytes) {
        byte[] valueBytes = new byte[numBytes];
        if (this.hasInit) {
            int commandCount = this.connection.getCommandCounter();
            EV3Command command = new EV3Command(this.connection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_REPLY, numBytes, 0, EV3CommandOpCode.OP_INPUT_DEVICE);
            this.connection.incCommandCounter();
            command.append(EV3CommandByteCode.INPUT_DEVICE_GET_RAW);
            command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 0);
            command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, this.port);
            command.append(EV3CommandVariableScope.PARAM_VARIABLE_SCOPE_GLOBAL, 0);
            try {
                EV3Reply reply = new EV3Reply(this.connection.sendAndReceive(command));
                if (reply.isValid(commandCount)) {
                    return reply.getData(3, reply.getLength() - 3);
                }
                throw new MindstormsException("Reply not valid!");
            } catch (MindstormsException e) {
                Log.e(TAG, e.getMessage());
                return valueBytes;
            }
        }
        initialize();
        return valueBytes;
    }

    public byte[] getSiValue(int numBytes) {
        byte[] siValue = new byte[numBytes];
        if (this.hasInit) {
            int commandCount = this.connection.getCommandCounter();
            EV3Command command = new EV3Command(this.connection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_REPLY, numBytes, 0, EV3CommandOpCode.OP_INPUT_READ_SI);
            this.connection.incCommandCounter();
            command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 0);
            command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, this.port);
            command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 0);
            command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, -1);
            command.append(EV3CommandVariableScope.PARAM_VARIABLE_SCOPE_GLOBAL, 0);
            try {
                EV3Reply reply = new EV3Reply(this.connection.sendAndReceive(command));
                if (reply.isValid(commandCount)) {
                    return reply.getData(3, reply.getLength() - 3);
                }
                throw new MindstormsException("Reply not valid!");
            } catch (MindstormsException e) {
                Log.e(TAG, e.getMessage());
                return siValue;
            }
        }
        initialize();
        return siValue;
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
