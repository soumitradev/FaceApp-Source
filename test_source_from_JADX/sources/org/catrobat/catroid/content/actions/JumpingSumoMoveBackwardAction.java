package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDeviceController;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class JumpingSumoMoveBackwardAction extends TemporalAction {
    protected static final float JUMPING_SUMO_MOVE_DURATION_STOP = 0.0f;
    protected static final byte JUMPING_SUMO_MOVE_SPEED_STOP = (byte) 0;
    private static final String TAG = JumpingSumoMoveBackwardAction.class.getSimpleName();
    private JumpingSumoDeviceController controller;
    private ARDeviceController deviceController;
    private Formula duration;
    private byte normalizedPower;
    private Formula powerInPercent;
    private Sprite sprite;

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setDelay(Formula delay) {
        this.duration = delay;
    }

    public void setPower(Formula powerInPercent) {
        this.powerInPercent = powerInPercent;
    }

    protected void begin() {
        Float valueOf;
        super.begin();
        this.controller = JumpingSumoDeviceController.getInstance();
        this.deviceController = this.controller.getDeviceController();
        try {
            if (this.duration == null) {
                valueOf = Float.valueOf(0.0f);
            } else {
                valueOf = this.duration.interpretFloat(this.sprite);
            }
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            valueOf = Float.valueOf(0.0f);
        }
        super.setDuration(valueOf.floatValue());
        try {
            if (this.duration == null) {
                this.normalizedPower = Byte.valueOf((byte) 0).byteValue();
            } else {
                this.normalizedPower = (byte) ((int) (-this.powerInPercent.interpretFloat(this.sprite).floatValue()));
            }
        } catch (InterpretationException interpretationException2) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException2);
            this.normalizedPower = Byte.valueOf((byte) 0).byteValue();
        }
        move();
    }

    protected void move() {
        if (this.deviceController != null) {
            this.deviceController.getFeatureJumpingSumo().setPilotingPCMDSpeed(this.normalizedPower);
            this.deviceController.getFeatureJumpingSumo().setPilotingPCMDFlag((byte) 1);
            Log.d(TAG, "send -move command JS");
        }
    }

    protected void moveEnd() {
        if (this.deviceController != null) {
            this.deviceController.getFeatureJumpingSumo().setPilotingPCMDSpeed(Byte.valueOf((byte) 0).byteValue());
            this.deviceController.getFeatureJumpingSumo().setPilotingPCMDFlag((byte) 0);
            Log.d(TAG, "send -stop command JS");
        }
    }

    protected void update(float percent) {
    }

    protected void end() {
        super.end();
        moveEnd();
    }
}
