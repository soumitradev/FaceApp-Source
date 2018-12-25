package org.catrobat.catroid.devices.arduino;

import org.catrobat.catroid.bluetooth.base.BluetoothDevice;

public interface Arduino extends BluetoothDevice {
    double getAnalogArduinoPin(int i);

    double getDigitalArduinoPin(int i);

    void setAnalogArduinoPin(int i, int i2);

    void setDigitalArduinoPin(int i, int i2);
}
