package org.catrobat.catroid.bluetooth.base;

import android.content.Context;

public interface BluetoothDeviceFactory {
    <T extends BluetoothDevice> BluetoothDevice createDevice(Class<T> cls, Context context);
}
