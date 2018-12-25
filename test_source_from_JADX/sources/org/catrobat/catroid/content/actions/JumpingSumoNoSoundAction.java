package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import org.catrobat.catroid.content.bricks.JumpingSumoNoSoundBrick;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDeviceController;

public class JumpingSumoNoSoundAction extends TemporalAction {
    private static final int NO_VOLUME = 0;
    private static final String TAG = JumpingSumoNoSoundBrick.class.getSimpleName();
    private JumpingSumoDeviceController controller;
    private ARDeviceController deviceController;
    private byte normalizedVolume;

    protected void begin() {
        super.begin();
        this.controller = JumpingSumoDeviceController.getInstance();
        this.deviceController = this.controller.getDeviceController();
        this.normalizedVolume = (byte) 0;
        if (this.deviceController != null) {
            this.deviceController.getFeatureJumpingSumo().sendAudioSettingsMasterVolume(this.normalizedVolume);
            Log.d(TAG, "No_Sound");
        }
    }

    protected void update(float percent) {
    }
}
