package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_JUMP_TYPE_ENUM;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDeviceController;

public class JumpingSumoJumpHighAction extends TemporalAction {
    private static final String TAG = JumpingSumoJumpHighAction.class.getSimpleName();
    private JumpingSumoDeviceController controller;
    private ARDeviceController deviceController;

    protected void begin() {
        super.begin();
        this.controller = JumpingSumoDeviceController.getInstance();
        this.deviceController = this.controller.getDeviceController();
        if (this.deviceController != null) {
            this.deviceController.getFeatureJumpingSumo().sendAnimationsJump(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_JUMP_TYPE_ENUM.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_JUMP_TYPE_HIGH);
            Log.d(TAG, "send jump high command JS down");
        }
    }

    protected void update(float percent) {
    }
}
