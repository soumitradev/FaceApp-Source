package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARControllerException;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDeviceController;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class JumpingSumoRotateLeftAction extends TemporalAction {
    protected static final float JUMPING_SUMO_ROTATE_ZERO = 0.0f;
    private static final String TAG = JumpingSumoRotateLeftAction.class.getSimpleName();
    private JumpingSumoDeviceController controller;
    private Formula degree;
    private ARDeviceController deviceController;
    private float duration;
    private float newDegree;
    private Sprite sprite;

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setDegree(Formula degree) {
        this.degree = degree;
    }

    protected void begin() {
        super.begin();
        this.controller = JumpingSumoDeviceController.getInstance();
        this.deviceController = this.controller.getDeviceController();
        try {
            if (this.degree == null) {
                this.newDegree = Float.valueOf(0.0f).floatValue();
            } else {
                this.newDegree = this.degree.interpretFloat(this.sprite).floatValue();
            }
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            this.newDegree = Float.valueOf(0.0f).floatValue();
        }
        move();
    }

    protected void move() {
        ARCONTROLLER_DEVICE_STATE_ENUM state = ARCONTROLLER_DEVICE_STATE_ENUM.eARCONTROLLER_DEVICE_STATE_UNKNOWN_ENUM_VALUE;
        try {
            state = this.deviceController.getState();
        } catch (ARControllerException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
        if (state != ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Device not running. State: ");
            stringBuilder.append(state);
            Log.e(str, stringBuilder.toString());
            return;
        }
        if (this.deviceController != null) {
            this.newDegree = (float) ((((double) this.newDegree) * 3.141592653589793d) / 180.0d);
            this.deviceController.getFeatureJumpingSumo().setPilotingPCMDFlag((byte) 1);
            this.deviceController.getFeatureJumpingSumo().sendPilotingAddCapOffset(-this.newDegree);
            this.duration = 1.0f;
            super.setDuration(this.duration);
            Log.d(TAG, "send move command JS");
        }
    }

    protected void moveEnd() {
        if (this.deviceController != null) {
            this.deviceController.getFeatureJumpingSumo().setPilotingPCMDFlag((byte) 0);
            Log.d(TAG, "send stop command JS");
        }
    }

    protected void update(float percent) {
    }

    protected void end() {
        super.end();
        moveEnd();
    }
}
