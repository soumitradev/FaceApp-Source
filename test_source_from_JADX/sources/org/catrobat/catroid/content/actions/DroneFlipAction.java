package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;

public class DroneFlipAction extends TemporalAction {
    private static final String TAG = DroneFlipAction.class.getSimpleName();
    private DroneServiceWrapper service = DroneServiceWrapper.getInstance();

    protected void begin() {
        super.begin();
        if (this.service.getDroneService() != null) {
            this.service.getDroneService().doLeftFlip();
        }
    }

    protected void update(float percent) {
        Log.d(TAG, "update!");
    }

    public boolean act(float delta) {
        return Boolean.valueOf(super.act(delta)).booleanValue();
    }
}
