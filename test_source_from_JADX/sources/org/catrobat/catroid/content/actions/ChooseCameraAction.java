package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.camera.CameraManager;

public class ChooseCameraAction extends TemporalAction {
    private static final int BACK = 0;
    private static final int FRONT = 1;
    private int cameraFacing = 1;

    protected void update(float percent) {
        if (this.cameraFacing == 1) {
            CameraManager.getInstance().setToFrontCamera();
        } else {
            CameraManager.getInstance().setToBackCamera();
        }
    }

    public void setFrontCamera() {
        this.cameraFacing = 1;
    }

    public void setBackCamera() {
        this.cameraFacing = 0;
    }
}
