package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.bricks.PhiroMotorStopBrick.Motor;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;

public class PhiroMotorStopAction extends TemporalAction {
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private Motor motorEnum;

    protected void update(float percent) {
        Phiro phiro = (Phiro) this.btService.getDevice(BluetoothDevice.PHIRO);
        if (phiro != null) {
            switch (this.motorEnum) {
                case MOTOR_LEFT:
                    phiro.stopLeftMotor();
                    break;
                case MOTOR_RIGHT:
                    phiro.stopRightMotor();
                    break;
                case MOTOR_BOTH:
                    phiro.stopAllMovements();
                    break;
                default:
                    break;
            }
        }
    }

    public void setMotorEnum(Motor motorEnum) {
        this.motorEnum = motorEnum;
    }
}
