package org.catrobat.catroid.content.actions;

public class DroneMoveForwardAction extends DroneMoveAction {
    protected void move() {
        if (getDroneService() != null) {
            super.setCommandAndYawEnabled(true);
            super.getDroneService().moveForward(super.getPowerNormalized());
        }
    }

    protected void moveEnd() {
        if (getDroneService() != null) {
            super.setCommandAndYawEnabled(false);
            super.getDroneService().moveForward(0.0f);
        }
    }
}
