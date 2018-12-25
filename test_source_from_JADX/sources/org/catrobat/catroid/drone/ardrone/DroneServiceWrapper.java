package org.catrobat.catroid.drone.ardrone;

import com.parrot.freeflight.service.DroneControlService;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public final class DroneServiceWrapper {
    private static DroneControlService droneControlService = null;
    private static DroneServiceWrapper instance = null;

    private DroneServiceWrapper() {
    }

    public static DroneServiceWrapper getInstance() {
        if (instance == null) {
            instance = new DroneServiceWrapper();
        }
        return instance;
    }

    public void setDroneService(DroneControlService service) {
        droneControlService = service;
    }

    public DroneControlService getDroneService() {
        return droneControlService;
    }

    public static boolean checkARDroneAvailability() {
        return ProjectManager.getInstance().getCurrentProject().getRequiredResources().contains(Integer.valueOf(5));
    }

    public static boolean isDroneSharedPreferenceEnabled() {
        return SettingsFragment.isDroneSharedPreferenceEnabled(CatroidApplication.getAppContext());
    }
}
