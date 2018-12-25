package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveBackwardBrick.Motor;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class PhiroMotorMoveBackwardAction extends TemporalAction {
    private static final int MAX_SPEED = 100;
    private static final int MIN_SPEED = 0;
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
        if (speedValue < 0) {
            speedValue = 0;
        } else if (speedValue > 100) {
            speedValue = 100;
        }
        Phiro phiro = (Phiro) this.btService.getDevice(BluetoothDevice.PHIRO);
        if (phiro != null) {
            switch (this.motorEnum) {
                case MOTOR_LEFT:
                    phiro.moveLeftMotorBackward(speedValue);
                    break;
                case MOTOR_RIGHT:
                    phiro.moveRightMotorBackward(speedValue);
                    break;
                case MOTOR_BOTH:
                    phiro.moveRightMotorBackward(speedValue);
                    phiro.moveLeftMotorBackward(speedValue);
                    break;
                default:
                    break;
            }
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
