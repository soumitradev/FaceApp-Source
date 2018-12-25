package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.LegoEv3MotorMoveBrick.Motor;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class LegoEv3MotorMoveAction extends TemporalAction {
    private static final int MAX_SPEED = 100;
    private static final int MIN_SPEED = -100;
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private Motor motorEnum;
    private Formula speed;
    private Sprite sprite;

    protected void update(float percent) {
        int speedValue;
        try {
            speedValue = this.speed.interpretInteger(this.sprite).intValue();
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            speedValue = 0;
        }
        if (speedValue < MIN_SPEED) {
            speedValue = MIN_SPEED;
        } else if (speedValue > 100) {
            speedValue = 100;
        }
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
                default:
                    break;
            }
            ev3.moveMotorSpeed(outputField, 0, speedValue);
        }
    }

    public void setMotorEnum(Motor motorEnum) {
        this.motorEnum = motorEnum;
    }

    public void setSpeed(Formula speed) {
        this.speed = speed;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
