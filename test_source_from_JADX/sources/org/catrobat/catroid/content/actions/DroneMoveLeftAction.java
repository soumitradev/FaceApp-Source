package org.catrobat.catroid.content.actions;

public class DroneMoveLeftAction extends DroneMoveAction {
    protected void move() {
        if (getDroneService() != null) {
            super.setCommandAndYawEnabled(true);
            super.getDroneService().moveLeft(super.getPowerNormalized());
        }
    }

    protected void moveEnd() {
        if (getDroneService() != null) {
            super.setCommandAndYawEnabled(false);
            super.getDroneService().moveLeft(0.0f);
        }
    }
}
