package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.bricks.LegoNxtMotorStopBrick.Motor;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT;

public class LegoNxtMotorStopAction extends TemporalAction {
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private Motor motorEnum;

    protected void update(float percent) {
        LegoNXT nxt = (LegoNXT) this.btService.getDevice(BluetoothDevice.LEGO_NXT);
        if (nxt != null) {
            switch (this.motorEnum) {
                case MOTOR_A:
                    nxt.getMotorA().stop();
                    break;
                case MOTOR_B:
                    nxt.getMotorB().stop();
                    break;
                case MOTOR_C:
                    nxt.getMotorC().stop();
                    break;
                case MOTOR_B_C:
                    nxt.getMotorB().stop();
                    nxt.getMotorC().stop();
                    break;
                case ALL_MOTORS:
                    nxt.stopAllMovements();
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
