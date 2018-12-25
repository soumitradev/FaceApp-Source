package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.freeflight.service.DroneControlService;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;

public class DroneSwitchCameraAction extends TemporalAction {
    private static final String TAG = DroneSwitchCameraAction.class.getSimpleName();

    protected void begin() {
        super.begin();
        Log.d(TAG, "begin!");
        DroneControlService dcs = DroneServiceWrapper.getInstance().getDroneService();
        if (dcs != null) {
            dcs.switchCamera();
        }
    }

    protected void update(float percent) {
    }
}
