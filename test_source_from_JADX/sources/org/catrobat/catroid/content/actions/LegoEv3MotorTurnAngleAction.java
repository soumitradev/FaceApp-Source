package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.LegoEv3MotorTurnAngleBrick.Motor;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class LegoEv3MotorTurnAngleAction extends TemporalAction {
    private static final int MAX_SPEED = 100;
    private static final int POWER_DOWN_RAMP_DEGREES = 20;
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private Formula degrees;
    private Motor motorEnum;
    private Sprite sprite;

    protected void update(float percent) {
        int degreesValue;
        int step2Angle;
        try {
            degreesValue = this.degrees.interpretInteger(r1.sprite).intValue();
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            degreesValue = 0;
        }
        int tmpAngle = degreesValue;
        int direction = 1;
        if (degreesValue < 0) {
            direction = -1;
            tmpAngle = degreesValue + (degreesValue * -2);
        }
        int step3Angle = 0;
        if (tmpAngle > 20) {
            step2Angle = tmpAngle - 20;
            step3Angle = 20;
        } else {
            step2Angle = tmpAngle;
        }
        LegoEV3 ev3 = (LegoEV3) r1.btService.getDevice(BluetoothDevice.LEGO_EV3);
        if (ev3 != null) {
            byte outputField = (byte) 0;
            switch (r1.motorEnum) {
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
            }
            byte outputField2 = outputField;
            ev3.moveMotorStepsSpeed(outputField2, 0, direction * 100, 0, step2Angle, step3Angle, true);
        }
    }

    public void setMotorEnum(Motor motorEnum) {
        this.motorEnum = motorEnum;
    }

    public void setDegrees(Formula degrees) {
        this.degrees = degrees;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
