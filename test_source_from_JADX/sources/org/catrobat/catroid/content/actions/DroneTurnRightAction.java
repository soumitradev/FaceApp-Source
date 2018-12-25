package org.catrobat.catroid.content.actions;

public class DroneTurnRightAction extends DroneMoveAction {
    protected void move() {
        if (getDroneService() != null) {
            super.setCommandAndYawEnabled(true);
            super.getDroneService().turnRight(super.getPowerNormalized());
        }
    }

    protected void moveEnd() {
        if (getDroneService() != null) {
            super.setCommandAndYawEnabled(false);
            super.getDroneService().turnRight(0.0f);
        }
    }
}
