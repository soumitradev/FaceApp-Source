package org.catrobat.catroid.content.actions;

import com.parrot.freeflight.service.DroneControlService;

public class DroneMoveUpAction extends DroneMoveAction {
    protected void move() {
        DroneControlService dcs = super.getDroneService();
        if (dcs != null) {
            dcs.moveUp(super.getPowerNormalized());
        }
    }

    protected void moveEnd() {
        DroneControlService dcs = super.getDroneService();
        if (dcs != null) {
            dcs.moveUp(0.0f);
        }
    }
}
