package org.catrobat.catroid.devices.mindstorms.ev3;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import java.util.UUID;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.LegoSensorService;
import org.catrobat.catroid.devices.mindstorms.LegoSensorService.OnSensorChangedListener;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnectionImpl;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandByteCode;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandOpCode;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandParamFormat;
import org.catrobat.catroid.formulaeditor.Sensors;

public class LegoEV3Impl implements LegoEV3, OnSensorChangedListener {
    private static final int FREQ_MAX_VAL = 10000;
    private static final int FREQ_MIN_VAL = 250;
    private static final int KEEP_ALIVE_TIME = 5;
    private static final UUID LEGO_EV3_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int NUMBER_VOLUME_LEVELS = 13;
    private static final String TAG = LegoEV3Impl.class.getSimpleName();
    private static final int VOLUME_LEVEL_INCR = 8;
    protected Context context;
    private boolean isInitialized = false;
    protected MindstormsConnection mindstormsConnection;
    private EV3Motor motorA;
    private EV3Motor motorB;
    private EV3Motor motorC;
    private EV3Motor motorD;
    private LegoSensor sensor1;
    private LegoSensor sensor2;
    private LegoSensor sensor3;
    private LegoSensor sensor4;
    private LegoSensorService sensorService;

    public LegoEV3Impl(Context applicationContext) {
        this.context = applicationContext;
    }

    public String getName() {
        return "Lego EV3";
    }

    public Class<? extends BluetoothDevice> getDeviceType() {
        return BluetoothDevice.LEGO_EV3;
    }

    public void playTone(int frequencyInHz, int durationInMs, int volumeInPercent) {
        if (volumeInPercent > 100) {
            volumeInPercent = 100;
        } else if (volumeInPercent < 0) {
            volumeInPercent = 0;
        }
        int i = 1;
        int i2 = durationInMs <= 0 ? 1 : 0;
        if (volumeInPercent != 0) {
            i = 0;
        }
        if ((i | i2) == 0) {
            if (frequencyInHz > 10000) {
                frequencyInHz = 10000;
            } else if (frequencyInHz < 250) {
                frequencyInHz = 250;
            }
            i = 13;
            for (int volLevel = 0; volLevel < 13; volLevel++) {
                if (volumeInPercent > volLevel * 8) {
                    i = volLevel + 1;
                }
            }
            EV3Command eV3Command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandOpCode.OP_SOUND);
            this.mindstormsConnection.incCommandCounter();
            eV3Command.append(EV3CommandByteCode.SOUND_PLAY_TONE);
            eV3Command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, i);
            eV3Command.append(EV3CommandParamFormat.PARAM_FORMAT_LONG, frequencyInHz);
            eV3Command.append(EV3CommandParamFormat.PARAM_FORMAT_LONG, durationInMs);
            try {
                this.mindstormsConnection.send(eV3Command);
            } catch (MindstormsException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public EV3Motor getMotorA() {
        return this.motorA;
    }

    public EV3Motor getMotorB() {
        return this.motorB;
    }

    public EV3Motor getMotorC() {
        return this.motorC;
    }

    public EV3Motor getMotorD() {
        return this.motorD;
    }

    public void onSensorChanged() {
        assignSensorsToPorts();
    }

    public void setConnection(BluetoothConnection btConnection) {
        this.mindstormsConnection = new MindstormsConnectionImpl(btConnection);
    }

    public boolean isAlive() {
        try {
            sendKeepAlive();
            return true;
        } catch (MindstormsException e) {
            return false;
        }
    }

    private void sendKeepAlive() throws MindstormsException {
        EV3Command command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandOpCode.OP_KEEP_ALIVE);
        this.mindstormsConnection.incCommandCounter();
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, 5);
        try {
            this.mindstormsConnection.send(command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void moveMotorStepsSpeed(byte outputField, int chainLayer, int speed, int step1Tacho, int step2Tacho, int step3Tacho, boolean brake) {
        EV3Command command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandOpCode.OP_OUTPUT_STEP_SPEED);
        this.mindstormsConnection.incCommandCounter();
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, chainLayer);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, (int) outputField);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_LONG, speed);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_LONG, step1Tacho);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_LONG, step2Tacho);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_LONG, step3Tacho);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, (int) brake);
        try {
            this.mindstormsConnection.send(command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void moveMotorSpeed(byte outputField, int chainLayer, int speed) {
        EV3Command setSpeedCommand = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandOpCode.OP_OUTPUT_SPEED);
        this.mindstormsConnection.incCommandCounter();
        setSpeedCommand.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, chainLayer);
        setSpeedCommand.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, (int) outputField);
        setSpeedCommand.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, speed);
        EV3Command eV3Command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandOpCode.OP_OUTPUT_START);
        this.mindstormsConnection.incCommandCounter();
        eV3Command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, chainLayer);
        eV3Command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, (int) outputField);
        try {
            this.mindstormsConnection.send(setSpeedCommand);
            this.mindstormsConnection.send(eV3Command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void stopMotor(byte outputField, int chainLayer, boolean brake) {
        EV3Command command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandOpCode.OP_OUTPUT_STOP);
        this.mindstormsConnection.incCommandCounter();
        command.append((byte) chainLayer);
        command.append(outputField);
        command.append((byte) brake);
        try {
            this.mindstormsConnection.send(command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void setLed(int ledStatus) {
        EV3Command command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandOpCode.OP_UI_WRITE);
        this.mindstormsConnection.incCommandCounter();
        command.append(EV3CommandByteCode.UI_WRITE_LED);
        command.append(EV3CommandParamFormat.PARAM_FORMAT_LONG, ledStatus);
        try {
            this.mindstormsConnection.send(command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public float getSensorValue(Sensors sensor) {
        float f = 0.0f;
        switch (sensor) {
            case EV3_SENSOR_1:
                if (this.sensor1 != null) {
                    f = this.sensor1.getLastSensorValue();
                }
                return f;
            case EV3_SENSOR_2:
                if (this.sensor2 != null) {
                    f = this.sensor2.getLastSensorValue();
                }
                return f;
            case EV3_SENSOR_3:
                if (this.sensor3 != null) {
                    f = this.sensor3.getLastSensorValue();
                }
                return f;
            case EV3_SENSOR_4:
                if (this.sensor4 != null) {
                    f = this.sensor4.getLastSensorValue();
                }
                return f;
            default:
                return -1.0f;
        }
    }

    public LegoSensor getSensor1() {
        return this.sensor1;
    }

    public LegoSensor getSensor2() {
        return this.sensor2;
    }

    public LegoSensor getSensor3() {
        return this.sensor3;
    }

    public LegoSensor getSensor4() {
        return this.sensor4;
    }

    public synchronized void initialise() {
        if (!this.isInitialized) {
            this.mindstormsConnection.init();
            this.motorA = new EV3Motor(0);
            this.motorB = new EV3Motor(1);
            this.motorC = new EV3Motor(2);
            this.motorD = new EV3Motor(3);
            assignSensorsToPorts();
            this.isInitialized = true;
        }
    }

    private synchronized void assignSensorsToPorts() {
        if (this.sensorService == null) {
            this.sensorService = new LegoSensorService(1, this.mindstormsConnection, PreferenceManager.getDefaultSharedPreferences(this.context));
            this.sensorService.registerOnSensorChangedListener(this);
        }
        this.sensor1 = this.sensorService.createSensor(0);
        this.sensor2 = this.sensorService.createSensor(1);
        this.sensor3 = this.sensorService.createSensor(2);
        this.sensor4 = this.sensorService.createSensor(3);
    }

    public void start() {
        initialise();
        assignSensorsToPorts();
        this.sensorService.resumeSensorUpdate();
    }

    public void pause() {
        stopAllMovements();
        this.sensorService.pauseSensorUpdate();
    }

    public void destroy() {
        this.sensorService.deactivateAllSensors(this.mindstormsConnection);
    }

    public void stopAllMovements() {
        stopMotor((byte) 15, 0, true);
    }

    public void disconnect() {
        if (this.mindstormsConnection.isConnected()) {
            stopAllMovements();
            this.mindstormsConnection.disconnect();
        }
    }

    public UUID getBluetoothDeviceUUID() {
        return LEGO_EV3_UUID;
    }
}
