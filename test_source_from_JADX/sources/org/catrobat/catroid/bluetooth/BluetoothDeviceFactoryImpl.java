package org.catrobat.catroid.bluetooth;

import android.content.Context;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceFactory;
import org.catrobat.catroid.devices.arduino.ArduinoImpl;
import org.catrobat.catroid.devices.arduino.phiro.PhiroImpl;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3Impl;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXTImpl;

public class BluetoothDeviceFactoryImpl implements BluetoothDeviceFactory {
    public <T extends BluetoothDevice> BluetoothDevice createDevice(Class<T> service, Context applicationContext) {
        if (service == BluetoothDevice.LEGO_NXT) {
            return new LegoNXTImpl(applicationContext);
        }
        if (service == BluetoothDevice.LEGO_EV3) {
            return new LegoEV3Impl(applicationContext);
        }
        if (service == BluetoothDevice.PHIRO) {
            return new PhiroImpl();
        }
        if (service == BluetoothDevice.ARDUINO) {
            return new ArduinoImpl();
        }
        return null;
    }
}
