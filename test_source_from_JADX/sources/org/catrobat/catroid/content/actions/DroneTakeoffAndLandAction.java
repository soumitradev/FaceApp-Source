package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.freeflight.service.DroneControlService;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;

public class DroneTakeoffAndLandAction extends TemporalAction {
    private float duration = 8.0f;

    protected void begin() {
        super.begin();
        DroneControlService dcs = DroneServiceWrapper.getInstance().getDroneService();
        if (dcs != null) {
            dcs.triggerTakeOff();
            super.setDuration(this.duration);
        }
    }

    protected void update(float percent) {
    }
}
