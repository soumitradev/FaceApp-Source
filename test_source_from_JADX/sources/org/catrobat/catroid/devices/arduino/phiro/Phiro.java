package org.catrobat.catroid.devices.arduino.phiro;

import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.formulaeditor.Sensors;

public interface Phiro extends BluetoothDevice {
    int getSensorValue(Sensors sensors);

    void moveLeftMotorBackward(int i);

    void moveLeftMotorForward(int i);

    void moveRightMotorBackward(int i);

    void moveRightMotorForward(int i);

    void playTone(int i, int i2);

    void reportFirmwareVersion();

    void setLeftRGBLightColor(int i, int i2, int i3);

    void setRightRGBLightColor(int i, int i2, int i3);

    void stopAllMovements();

    void stopLeftMotor();

    void stopRightMotor();
}
