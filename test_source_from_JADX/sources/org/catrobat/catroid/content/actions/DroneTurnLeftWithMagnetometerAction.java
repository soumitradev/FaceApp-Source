package org.catrobat.catroid.content.actions;

public class DroneTurnLeftWithMagnetometerAction extends DroneMoveAction {
    protected void begin() {
        super.begin();
        super.setCommandAndYawEnabled(true);
    }

    protected void move() {
        super.getDroneService().setDeviceOrientation(0, (int) (super.getPowerNormalized() * 1120403456));
    }

    protected void moveEnd() {
        super.setCommandAndYawEnabled(false);
    }
}
