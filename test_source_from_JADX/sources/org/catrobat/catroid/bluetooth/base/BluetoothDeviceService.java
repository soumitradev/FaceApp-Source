package org.catrobat.catroid.bluetooth.base;

import android.app.Activity;
import android.content.Context;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.stage.StageResourceInterface;

public interface BluetoothDeviceService extends CatroidService, StageResourceInterface {

    public enum ConnectDeviceResult {
        ALREADY_CONNECTED,
        CONNECTION_REQUESTED
    }

    ConnectDeviceResult connectDevice(Class<? extends BluetoothDevice> cls, Activity activity, int i);

    ConnectDeviceResult connectDevice(Class<? extends BluetoothDevice> cls, Context context);

    void deviceConnected(BluetoothDevice bluetoothDevice);

    void disconnectDevices();

    <T extends BluetoothDevice> T getDevice(Class<T> cls);
}
