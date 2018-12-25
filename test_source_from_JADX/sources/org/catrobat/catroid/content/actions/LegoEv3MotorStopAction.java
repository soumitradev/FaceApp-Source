package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.bricks.LegoEv3MotorStopBrick.Motor;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;

public class LegoEv3MotorStopAction extends TemporalAction {
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private Motor motorEnum;

    protected void update(float percent) {
        LegoEV3 ev3 = (LegoEV3) this.btService.getDevice(BluetoothDevice.LEGO_EV3);
        if (ev3 != null) {
            byte outputField = (byte) 0;
            switch (this.motorEnum) {
                case MOTOR_A:
                    outputField = (byte) 1;
                    break;
                case MOTOR_B:
                    outputField = (byte) 2;
                    break;
                case MOTOR_C:
                    outputField = (byte) 4;
                    break;
                case MOTOR_D:
                    outputField = (byte) 8;
                    break;
                case MOTOR_B_C:
                    outputField = (byte) 6;
                    break;
                case ALL_MOTORS:
                    outputField = (byte) 15;
                    break;
                default:
                    break;
            }
            ev3.stopMotor(outputField, 0, true);
        }
    }

    public void setMotorEnum(Motor motorEnum) {
        this.motorEnum = motorEnum;
    }
}
