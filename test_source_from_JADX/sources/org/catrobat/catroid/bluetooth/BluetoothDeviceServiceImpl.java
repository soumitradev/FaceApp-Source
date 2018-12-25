package org.catrobat.catroid.bluetooth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService.ConnectDeviceResult;

public class BluetoothDeviceServiceImpl implements BluetoothDeviceService {
    private Map<Class<? extends BluetoothDevice>, BluetoothDevice> connectedDevices = new HashMap();

    public ConnectDeviceResult connectDevice(Class<? extends BluetoothDevice> deviceToConnect, Activity activity, int requestCode) {
        if (isDeviceConnectedAndAlive(deviceToConnect)) {
            return ConnectDeviceResult.ALREADY_CONNECTED;
        }
        activity.startActivityForResult(createStartIntent(deviceToConnect, activity), requestCode);
        return ConnectDeviceResult.CONNECTION_REQUESTED;
    }

    public ConnectDeviceResult connectDevice(Class<? extends BluetoothDevice> deviceToConnect, Context context) {
        if (isDeviceConnectedAndAlive(deviceToConnect)) {
            return ConnectDeviceResult.ALREADY_CONNECTED;
        }
        context.startActivity(createStartIntent(deviceToConnect, context));
        return ConnectDeviceResult.CONNECTION_REQUESTED;
    }

    private synchronized boolean isDeviceConnectedAndAlive(Class<? extends BluetoothDevice> deviceToConnect) {
        BluetoothDevice device = (BluetoothDevice) this.connectedDevices.get(deviceToConnect);
        if (device != null) {
            if (device.isAlive()) {
                device.start();
                return true;
            }
            device.disconnect();
            this.connectedDevices.remove(device);
        }
        return false;
    }

    public synchronized void deviceConnected(BluetoothDevice device) {
        this.connectedDevices.put(device.getDeviceType(), device);
        device.start();
    }

    public synchronized void disconnectDevices() {
        for (BluetoothDevice device : this.connectedDevices.values()) {
            device.disconnect();
        }
        this.connectedDevices.clear();
    }

    public synchronized <T extends BluetoothDevice> T getDevice(Class<T> btDevice) {
        BluetoothDevice device = (BluetoothDevice) this.connectedDevices.get(btDevice);
        if (device != null) {
            return device;
        }
        return null;
    }

    protected Intent createStartIntent(Class<? extends BluetoothDevice> deviceToConnect, Context context) {
        Intent intent = new Intent(context, ConnectBluetoothDeviceActivity.class);
        intent.putExtra(ConnectBluetoothDeviceActivity.DEVICE_TO_CONNECT, deviceToConnect);
        return intent;
    }

    public synchronized void initialise() {
        for (BluetoothDevice device : this.connectedDevices.values()) {
            device.initialise();
        }
    }

    public synchronized void start() {
        for (BluetoothDevice device : this.connectedDevices.values()) {
            device.start();
        }
    }

    public synchronized void pause() {
        for (BluetoothDevice device : this.connectedDevices.values()) {
            device.pause();
        }
    }

    public synchronized void destroy() {
        for (BluetoothDevice device : this.connectedDevices.values()) {
            device.destroy();
        }
    }
}
