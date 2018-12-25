package org.catrobat.catroid.content.actions;

import android.util.Log;

public class DroneTurnRightWithMagnetometerAction extends DroneMoveAction {
    private static final String TAG = DroneTurnRightWithMagnetometerAction.class.getSimpleName();
    private boolean isCalled = false;

    protected void begin() {
        super.begin();
        if (!this.isCalled) {
            super.getDroneService().setMagnetoEnabled(true);
            super.getDroneService().calibrateMagneto();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            Log.d(getClass().getSimpleName(), "isCalled");
        }
    }

    protected void move() {
        int value = (int) (super.getPowerNormalized() * 1120403456);
        super.setCommandAndYawEnabled(true);
        super.getDroneService().setDeviceOrientation(0, -value);
    }

    protected void moveEnd() {
        super.setCommandAndYawEnabled(false);
        this.isCalled = true;
    }
}
