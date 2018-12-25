package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDataContainer;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDeviceController;

public class JumpingSumoTurnAction extends TemporalAction {
    private static final String TAG = JumpingSumoTurnAction.class.getSimpleName();
    private JumpingSumoDeviceController controller;
    private float delay;
    private ARDeviceController deviceController;

    protected void begin() {
        super.begin();
        this.controller = JumpingSumoDeviceController.getInstance();
        this.deviceController = this.controller.getDeviceController();
        JumpingSumoDataContainer position = JumpingSumoDataContainer.getInstance();
        boolean pos = position.getPostion();
        if (this.deviceController == null) {
            return;
        }
        if (pos) {
            this.deviceController.getFeatureJumpingSumo().sendPilotingPosture(ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM.ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_KICKER);
            Log.d(TAG, "send turn command JS down");
            position.setPostion(false);
            this.delay = 1.0f;
            super.setDuration(this.delay);
            return;
        }
        this.deviceController.getFeatureJumpingSumo().sendPilotingPosture(ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_ENUM.ARCOMMANDS_JUMPINGSUMO_PILOTING_POSTURE_TYPE_JUMPER);
        Log.d(TAG, "send turn command JS up");
        position.setPostion(true);
        this.delay = 1.0f;
        super.setDuration(this.delay);
    }

    protected void update(float percent) {
    }
}
