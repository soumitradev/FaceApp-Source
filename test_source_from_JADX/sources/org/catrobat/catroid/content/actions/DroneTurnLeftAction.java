package org.catrobat.catroid.content.actions;

public class DroneTurnLeftAction extends DroneMoveAction {
    protected void move() {
        if (getDroneService() != null) {
            super.setCommandAndYawEnabled(true);
            super.getDroneService().turnLeft(super.getPowerNormalized());
        }
    }

    protected void moveEnd() {
        if (getDroneService() != null) {
            super.setCommandAndYawEnabled(false);
            super.getDroneService().turnLeft(0.0f);
        }
    }
}
