package org.catrobat.catroid.bluetooth.base;

import android.content.Context;
import java.util.UUID;

public interface BluetoothConnectionFactory {
    <T extends BluetoothDevice> BluetoothConnection createBTConnectionForDevice(Class<T> cls, String str, UUID uuid, Context context);
}
