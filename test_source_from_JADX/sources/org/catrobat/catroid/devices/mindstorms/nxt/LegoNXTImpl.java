package org.catrobat.catroid.devices.mindstorms.nxt;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.LegoSensorService;
import org.catrobat.catroid.devices.mindstorms.LegoSensorService.OnSensorChangedListener;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnectionImpl;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;

public class LegoNXTImpl implements LegoNXT, OnSensorChangedListener {
    private static final UUID LEGO_NXT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = LegoNXTImpl.class.getSimpleName();
    protected Context context;
    private boolean isInitialized = false;
    protected MindstormsConnection mindstormsConnection;
    private NXTMotor motorA;
    private NXTMotor motorB;
    private NXTMotor motorC;
    private LegoSensor sensor1;
    private LegoSensor sensor2;
    private LegoSensor sensor3;
    private LegoSensor sensor4;
    private LegoSensorService sensorService;

    public LegoNXTImpl(Context applicationContext) {
        this.context = applicationContext;
    }

    public String getName() {
        return "Lego NXT";
    }

    public Class<? extends BluetoothDevice> getDeviceType() {
        return BluetoothDevice.LEGO_NXT;
    }

    public void setConnection(BluetoothConnection btConnection) {
        this.mindstormsConnection = new MindstormsConnectionImpl(btConnection);
    }

    public UUID getBluetoothDeviceUUID() {
        return LEGO_NXT_UUID;
    }

    public void disconnect() {
        if (this.mindstormsConnection.isConnected()) {
            stopAllMovements();
            if (this.sensorService != null) {
                this.sensorService.deactivateAllSensors(this.mindstormsConnection);
                this.sensorService.destroy();
            }
            this.mindstormsConnection.disconnect();
        }
    }

    public boolean isAlive() {
        try {
            tryGetKeepAliveTime();
            return true;
        } catch (MindstormsException e) {
            return false;
        }
    }

    public void playTone(int frequencyInHz, int durationInMs) {
        if (durationInMs > 0) {
            if (frequencyInHz > 14000) {
                frequencyInHz = 14000;
            } else if (frequencyInHz < 200) {
                frequencyInHz = 200;
            }
            Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.PLAY_TONE, false);
            command.append((byte) (frequencyInHz & 255));
            command.append((byte) ((frequencyInHz & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8));
            command.append((byte) (durationInMs & 255));
            command.append((byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & durationInMs) >> 8));
            try {
                this.mindstormsConnection.send(command);
            } catch (MindstormsException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public int getKeepAliveTime() {
        try {
            return tryGetKeepAliveTime();
        } catch (NXTException e) {
            return -1;
        } catch (MindstormsException e2) {
            return -1;
        }
    }

    private int tryGetKeepAliveTime() {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.KEEP_ALIVE, true);
        byte[] alive = this.mindstormsConnection.sendAndReceive(command);
        NXTError.checkForError(new NXTReply(this.mindstormsConnection.sendAndReceive(command)), 7);
        return ByteBuffer.wrap(new byte[]{alive[3], alive[4], alive[5], alive[6]}).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public int getBatteryLevel() {
        try {
            return tryGetBatteryLevel();
        } catch (NXTException e) {
            return -1;
        } catch (MindstormsException e2) {
            return -1;
        }
    }

    private int tryGetBatteryLevel() {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.GET_BATTERY_LEVEL, true);
        NXTError.checkForError(new NXTReply(this.mindstormsConnection.sendAndReceive(command)), 5);
        byte[] batByte = this.mindstormsConnection.sendAndReceive(command);
        return ByteBuffer.wrap(new byte[]{batByte[3], batByte[4]}).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public NXTMotor getMotorA() {
        return this.motorA;
    }

    public NXTMotor getMotorB() {
        return this.motorB;
    }

    public NXTMotor getMotorC() {
        return this.motorC;
    }

    public void stopAllMovements() {
        this.motorA.stop();
        this.motorB.stop();
        this.motorC.stop();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized float getSensorValue(org.catrobat.catroid.formulaeditor.Sensors r3) {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = org.catrobat.catroid.devices.mindstorms.nxt.LegoNXTImpl.C18151.$SwitchMap$org$catrobat$catroid$formulaeditor$Sensors;	 Catch:{ all -> 0x0045 }
        r1 = r3.ordinal();	 Catch:{ all -> 0x0045 }
        r0 = r0[r1];	 Catch:{ all -> 0x0045 }
        r1 = 0;
        switch(r0) {
            case 1: goto L_0x0038;
            case 2: goto L_0x002b;
            case 3: goto L_0x001e;
            case 4: goto L_0x0011;
            default: goto L_0x000d;
        };
    L_0x000d:
        r0 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        monitor-exit(r2);
        return r0;
    L_0x0011:
        r0 = r2.sensor4;	 Catch:{ all -> 0x0045 }
        if (r0 == 0) goto L_0x001c;
    L_0x0015:
        r0 = r2.sensor4;	 Catch:{ all -> 0x0045 }
        r1 = r0.getLastSensorValue();	 Catch:{ all -> 0x0045 }
    L_0x001c:
        monitor-exit(r2);
        return r1;
    L_0x001e:
        r0 = r2.sensor3;	 Catch:{ all -> 0x0045 }
        if (r0 == 0) goto L_0x0029;
    L_0x0022:
        r0 = r2.sensor3;	 Catch:{ all -> 0x0045 }
        r1 = r0.getLastSensorValue();	 Catch:{ all -> 0x0045 }
    L_0x0029:
        monitor-exit(r2);
        return r1;
    L_0x002b:
        r0 = r2.sensor2;	 Catch:{ all -> 0x0045 }
        if (r0 == 0) goto L_0x0036;
    L_0x002f:
        r0 = r2.sensor2;	 Catch:{ all -> 0x0045 }
        r1 = r0.getLastSensorValue();	 Catch:{ all -> 0x0045 }
    L_0x0036:
        monitor-exit(r2);
        return r1;
    L_0x0038:
        r0 = r2.sensor1;	 Catch:{ all -> 0x0045 }
        if (r0 == 0) goto L_0x0043;
    L_0x003c:
        r0 = r2.sensor1;	 Catch:{ all -> 0x0045 }
        r1 = r0.getLastSensorValue();	 Catch:{ all -> 0x0045 }
    L_0x0043:
        monitor-exit(r2);
        return r1;
    L_0x0045:
        r3 = move-exception;
        monitor-exit(r2);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.devices.mindstorms.nxt.LegoNXTImpl.getSensorValue(org.catrobat.catroid.formulaeditor.Sensors):float");
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

    public void onSensorChanged() {
        assignSensorsToPorts();
    }

    public synchronized void initialise() {
        if (!this.isInitialized) {
            this.mindstormsConnection.init();
            this.motorA = new NXTMotor(0, this.mindstormsConnection);
            this.motorB = new NXTMotor(1, this.mindstormsConnection);
            this.motorC = new NXTMotor(2, this.mindstormsConnection);
            assignSensorsToPorts();
            this.isInitialized = true;
        }
    }

    private synchronized void assignSensorsToPorts() {
        if (this.sensorService == null) {
            this.sensorService = new LegoSensorService(0, this.mindstormsConnection, PreferenceManager.getDefaultSharedPreferences(this.context));
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
}
