package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick.Motor;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class LegoNxtMotorTurnAngleAction extends TemporalAction {
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private Formula degrees;
    private Motor motorEnum;
    private Sprite sprite;

    protected void update(float percent) {
        int degreesValue;
        try {
            degreesValue = this.degrees.interpretInteger(this.sprite).intValue();
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            degreesValue = 0;
        }
        int tmpAngle = degreesValue;
        int direction = 1;
        if (degreesValue < 0) {
            direction = -1;
            tmpAngle = degreesValue + (degreesValue * -2);
        }
        LegoNXT nxt = (LegoNXT) this.btService.getDevice(BluetoothDevice.LEGO_NXT);
        if (nxt != null) {
            switch (this.motorEnum) {
                case MOTOR_A:
                    nxt.getMotorA().move(direction * 30, tmpAngle);
                    break;
                case MOTOR_B:
                    nxt.getMotorB().move(direction * 30, tmpAngle);
                    break;
                case MOTOR_C:
                    nxt.getMotorC().move(direction * 30, tmpAngle);
                    break;
                case MOTOR_B_C:
                    nxt.getMotorB().move(direction * 30, tmpAngle);
                    nxt.getMotorC().move(direction * 30, tmpAngle);
                    break;
                default:
                    break;
            }
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
