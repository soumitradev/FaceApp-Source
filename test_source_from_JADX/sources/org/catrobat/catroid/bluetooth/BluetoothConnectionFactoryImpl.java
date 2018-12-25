package org.catrobat.catroid.bluetooth;

import android.content.Context;
import java.util.UUID;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothConnectionFactory;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;

public class BluetoothConnectionFactoryImpl implements BluetoothConnectionFactory {
    public <T extends BluetoothDevice> BluetoothConnection createBTConnectionForDevice(Class<T> cls, String address, UUID deviceUUID, Context applicationContext) {
        return new BluetoothConnectionImpl(address, deviceUUID);
    }
}
