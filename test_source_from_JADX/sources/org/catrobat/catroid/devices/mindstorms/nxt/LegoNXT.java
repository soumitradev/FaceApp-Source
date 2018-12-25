package org.catrobat.catroid.devices.mindstorms.nxt;

import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.Mindstorms;
import org.catrobat.catroid.formulaeditor.Sensors;

public interface LegoNXT extends Mindstorms, BluetoothDevice {
    int getBatteryLevel();

    int getKeepAliveTime();

    NXTMotor getMotorA();

    NXTMotor getMotorB();

    NXTMotor getMotorC();

    LegoSensor getSensor1();

    LegoSensor getSensor2();

    LegoSensor getSensor3();

    LegoSensor getSensor4();

    float getSensorValue(Sensors sensors);

    void playTone(int i, int i2);

    void stopAllMovements();
}
