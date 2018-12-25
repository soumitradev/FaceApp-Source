package org.catrobat.catroid.drone.jumpingsumo;

import com.parrot.arsdk.arcontroller.ARDeviceController;

public final class JumpingSumoDeviceController {
    private static JumpingSumoDeviceController controller = new JumpingSumoDeviceController();
    private ARDeviceController deviceController = null;

    public static JumpingSumoDeviceController getInstance() {
        return controller;
    }

    private JumpingSumoDeviceController() {
    }

    public void setDeviceController(ARDeviceController controller) {
        this.deviceController = controller;
    }

    public ARDeviceController getDeviceController() {
        return this.deviceController;
    }

    public boolean isConnected() {
        return this.deviceController != null;
    }
}
