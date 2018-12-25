package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDeviceController;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoInitializer;

public class JumpingSumoTakingPictureAction extends TemporalAction {
    private static final String TAG = JumpingSumoTakingPictureAction.class.getSimpleName();
    private JumpingSumoDeviceController controller;
    private ARDeviceController deviceController;
    private JumpingSumoInitializer download;

    protected void begin() {
        super.begin();
        this.controller = JumpingSumoDeviceController.getInstance();
        this.deviceController = this.controller.getDeviceController();
        this.download = JumpingSumoInitializer.getInstance();
        if (this.deviceController != null) {
            this.download.takePicture();
            Log.d(TAG, "Picture has been taken");
        }
    }

    protected void end() {
        super.end();
        this.download.getLastFlightMedias();
    }

    protected void update(float percent) {
    }
}
