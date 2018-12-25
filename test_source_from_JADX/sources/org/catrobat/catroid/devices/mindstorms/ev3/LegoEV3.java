package org.catrobat.catroid.devices.mindstorms.ev3;

import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.Mindstorms;
import org.catrobat.catroid.formulaeditor.Sensors;

public interface LegoEV3 extends Mindstorms, BluetoothDevice {
    EV3Motor getMotorA();

    EV3Motor getMotorB();

    EV3Motor getMotorC();

    EV3Motor getMotorD();

    LegoSensor getSensor1();

    LegoSensor getSensor2();

    LegoSensor getSensor3();

    LegoSensor getSensor4();

    float getSensorValue(Sensors sensors);

    boolean isAlive();

    void moveMotorSpeed(byte b, int i, int i2);

    void moveMotorStepsSpeed(byte b, int i, int i2, int i3, int i4, int i5, boolean z);

    void playTone(int i, int i2, int i3);

    void setLed(int i);

    void stopAllMovements();

    void stopMotor(byte b, int i, boolean z);
}
