package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.camera.CameraManager.CameraState;

public class CameraBrickAction extends TemporalAction {
    CameraState state = CameraState.notUsed;

    protected void update(float percent) {
        CameraManager.getInstance().updatePreview(this.state);
    }

    public void reset() {
    }

    public void setCameraAction(CameraState newState) {
        this.state = newState;
    }
}
