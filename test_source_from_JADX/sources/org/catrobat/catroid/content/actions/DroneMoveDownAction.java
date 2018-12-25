package org.catrobat.catroid.content.actions;

public class DroneMoveDownAction extends DroneMoveAction {
    protected void move() {
        if (getDroneService() != null) {
            getDroneService().moveDown(super.getPowerNormalized());
        }
    }

    protected void moveEnd() {
        if (getDroneService() != null) {
            getDroneService().moveDown(0.0f);
        }
    }
}
