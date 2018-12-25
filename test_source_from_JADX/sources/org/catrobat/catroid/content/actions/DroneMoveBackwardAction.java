package org.catrobat.catroid.content.actions;

public class DroneMoveBackwardAction extends DroneMoveAction {
    protected void move() {
        if (getDroneService() != null) {
            super.setCommandAndYawEnabled(true);
            super.getDroneService().moveBackward(super.getPowerNormalized());
        }
    }

    protected void moveEnd() {
        if (getDroneService() != null) {
            super.setCommandAndYawEnabled(false);
            super.getDroneService().moveBackward(0.0f);
        }
    }
}
