package org.catrobat.catroid.bluetooth.base;

import java.util.UUID;
import org.catrobat.catroid.devices.arduino.Arduino;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT;
import org.catrobat.catroid.stage.StageResourceInterface;

public interface BluetoothDevice extends StageResourceInterface {
    public static final Class<Arduino> ARDUINO = Arduino.class;
    public static final Class<LegoEV3> LEGO_EV3 = LegoEV3.class;
    public static final Class<LegoNXT> LEGO_NXT = LegoNXT.class;
    public static final Class<Phiro> PHIRO = Phiro.class;

    void disconnect();

    UUID getBluetoothDeviceUUID();

    Class<? extends BluetoothDevice> getDeviceType();

    String getName();

    boolean isAlive();

    void setConnection(BluetoothConnection bluetoothConnection);
}
