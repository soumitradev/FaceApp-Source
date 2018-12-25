package org.catrobat.catroid.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public class BluetoothManager {
    public static final int BLUETOOTH_ACTIVATING = 0;
    public static final int BLUETOOTH_ALREADY_ON = 1;
    public static final int BLUETOOTH_NOT_SUPPORTED = -1;
    public static final int REQUEST_ENABLE_BT = 2000;
    private final Activity activity;
    private BluetoothAdapter bluetoothAdapter;

    public BluetoothManager(Activity activity) {
        this.activity = activity;
    }

    public int activateBluetooth() {
        if (this.bluetoothAdapter == null) {
            this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (this.bluetoothAdapter == null) {
            return -1;
        }
        if (this.bluetoothAdapter.isEnabled()) {
            return 1;
        }
        this.activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 2000);
        return 0;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return this.bluetoothAdapter;
    }
}
