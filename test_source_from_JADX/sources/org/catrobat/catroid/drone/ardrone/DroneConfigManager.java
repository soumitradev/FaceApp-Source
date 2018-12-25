package org.catrobat.catroid.drone.ardrone;

import android.util.Log;
import com.parrot.freeflight.service.DroneControlService;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.DroneConfigPreference.Preferences;

public final class DroneConfigManager {
    private static final String TAG = DroneConfigManager.class.getSimpleName();
    private static DroneControlService droneControlService;
    private static DroneConfigManager instance;
    private final String first = "FIRST";
    private final String fourth = "FOURTH";
    private final String second = "SECOND";
    private final String third = "THIRD";

    private DroneConfigManager() {
    }

    public static DroneConfigManager getInstance() {
        if (instance == null) {
            instance = new DroneConfigManager();
        }
        return instance;
    }

    public void setDroneConfig(Preferences[] preferences) {
        for (Preferences preference : preferences) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Drone = ");
            stringBuilder.append(preference);
            Log.d(str, stringBuilder.toString());
        }
        setBasicConfig(preferences[0].getPreferenceCode());
        setAltitude(preferences[1].getPreferenceCode());
        setVerticalSpeed(preferences[2].getPreferenceCode());
        setRotationSpeed(preferences[3].getPreferenceCode());
        setTiltAngle(preferences[4].getPreferenceCode());
    }

    private void setBasicConfig(String preference) {
        Object obj;
        int hashCode = preference.hashCode();
        if (hashCode != -1852950412) {
            if (hashCode != 66902672) {
                if (hashCode != 79793479) {
                    if (hashCode == 2079612442) {
                        if (preference.equals("FOURTH")) {
                            obj = 3;
                            switch (obj) {
                                case null:
                                    setIndoorConfigWithHull();
                                    return;
                                case 1:
                                    setIndoorConfigWithoutHull();
                                    return;
                                case 2:
                                    setOutdoorConfigWithHull();
                                    return;
                                case 3:
                                    setOutdoorConfigWithoutHull();
                                    return;
                                default:
                                    return;
                            }
                        }
                    }
                } else if (preference.equals("THIRD")) {
                    obj = 2;
                    switch (obj) {
                        case null:
                            setIndoorConfigWithHull();
                            return;
                        case 1:
                            setIndoorConfigWithoutHull();
                            return;
                        case 2:
                            setOutdoorConfigWithHull();
                            return;
                        case 3:
                            setOutdoorConfigWithoutHull();
                            return;
                        default:
                            return;
                    }
                }
            } else if (preference.equals("FIRST")) {
                obj = null;
                switch (obj) {
                    case null:
                        setIndoorConfigWithHull();
                        return;
                    case 1:
                        setIndoorConfigWithoutHull();
                        return;
                    case 2:
                        setOutdoorConfigWithHull();
                        return;
                    case 3:
                        setOutdoorConfigWithoutHull();
                        return;
                    default:
                        return;
                }
            }
        } else if (preference.equals("SECOND")) {
            obj = 1;
            switch (obj) {
                case null:
                    setIndoorConfigWithHull();
                    return;
                case 1:
                    setIndoorConfigWithoutHull();
                    return;
                case 2:
                    setOutdoorConfigWithHull();
                    return;
                case 3:
                    setOutdoorConfigWithoutHull();
                    return;
                default:
                    return;
            }
        }
        obj = -1;
        switch (obj) {
            case null:
                setIndoorConfigWithHull();
                return;
            case 1:
                setIndoorConfigWithoutHull();
                return;
            case 2:
                setOutdoorConfigWithHull();
                return;
            case 3:
                setOutdoorConfigWithoutHull();
                return;
            default:
                return;
        }
    }

    public void setIndoorConfigWithHull() {
        droneControlService = DroneServiceWrapper.getInstance().getDroneService();
        if (droneControlService != null) {
            droneControlService.getDroneConfig().setOutdoorFlight(false);
            droneControlService.getDroneConfig().setOutdoorHull(true);
            Log.d(TAG, "Set Config = indoor with hull");
        }
    }

    public void setIndoorConfigWithoutHull() {
        droneControlService = DroneServiceWrapper.getInstance().getDroneService();
        if (droneControlService != null) {
            droneControlService.getDroneConfig().setOutdoorFlight(false);
            droneControlService.getDroneConfig().setOutdoorHull(false);
            Log.d(TAG, "Set Config = indoor without hull");
        }
    }

    public void setOutdoorConfigWithHull() {
        droneControlService = DroneServiceWrapper.getInstance().getDroneService();
        if (droneControlService != null) {
            droneControlService.getDroneConfig().setOutdoorFlight(true);
            droneControlService.getDroneConfig().setOutdoorHull(true);
            Log.d(TAG, "Set Config = outdoor with hull");
        }
    }

    public void setOutdoorConfigWithoutHull() {
        droneControlService = DroneServiceWrapper.getInstance().getDroneService();
        if (droneControlService != null) {
            droneControlService.getDroneConfig().setOutdoorFlight(true);
            droneControlService.getDroneConfig().setOutdoorHull(false);
            Log.d(TAG, "Set Config = outdoor without hull");
        }
    }

    private void setAltitude(String preference) {
        Object obj;
        int altitudeValue = 3;
        int hashCode = preference.hashCode();
        if (hashCode != -1852950412) {
            if (hashCode != 66902672) {
                if (hashCode != 79793479) {
                    if (hashCode == 2079612442) {
                        if (preference.equals("FOURTH")) {
                            obj = 3;
                            switch (obj) {
                                case null:
                                    altitudeValue = 3;
                                    break;
                                case 1:
                                    altitudeValue = 5;
                                    break;
                                case 2:
                                    altitudeValue = 10;
                                    break;
                                case 3:
                                    altitudeValue = 100;
                                    break;
                                default:
                                    break;
                            }
                            setAltitude(altitudeValue);
                        }
                    }
                } else if (preference.equals("THIRD")) {
                    obj = 2;
                    switch (obj) {
                        case null:
                            altitudeValue = 3;
                            break;
                        case 1:
                            altitudeValue = 5;
                            break;
                        case 2:
                            altitudeValue = 10;
                            break;
                        case 3:
                            altitudeValue = 100;
                            break;
                        default:
                            break;
                    }
                    setAltitude(altitudeValue);
                }
            } else if (preference.equals("FIRST")) {
                obj = null;
                switch (obj) {
                    case null:
                        altitudeValue = 3;
                        break;
                    case 1:
                        altitudeValue = 5;
                        break;
                    case 2:
                        altitudeValue = 10;
                        break;
                    case 3:
                        altitudeValue = 100;
                        break;
                    default:
                        break;
                }
                setAltitude(altitudeValue);
            }
        } else if (preference.equals("SECOND")) {
            obj = 1;
            switch (obj) {
                case null:
                    altitudeValue = 3;
                    break;
                case 1:
                    altitudeValue = 5;
                    break;
                case 2:
                    altitudeValue = 10;
                    break;
                case 3:
                    altitudeValue = 100;
                    break;
                default:
                    break;
            }
            setAltitude(altitudeValue);
        }
        obj = -1;
        switch (obj) {
            case null:
                altitudeValue = 3;
                break;
            case 1:
                altitudeValue = 5;
                break;
            case 2:
                altitudeValue = 10;
                break;
            case 3:
                altitudeValue = 100;
                break;
            default:
                break;
        }
        setAltitude(altitudeValue);
    }

    public void setAltitude(int value) {
        droneControlService = DroneServiceWrapper.getInstance().getDroneService();
        if (droneControlService != null) {
            Log.d(TAG, String.format("old altitude = %d", new Object[]{Integer.valueOf(droneControlService.getDroneConfig().getAltitudeLimit())}));
            if (3 > value || value > 100) {
                droneControlService.getDroneConfig().setAltitudeLimit(3);
            } else {
                droneControlService.getDroneConfig().setAltitudeLimit(value);
            }
            Log.d(TAG, String.format("new altitude = %d", new Object[]{Integer.valueOf(droneControlService.getDroneConfig().getAltitudeLimit())}));
        }
    }

    private void setVerticalSpeed(String preference) {
        Object obj;
        int verticalValue = BrickValues.DRONE_VERTICAL_INDOOR;
        int hashCode = preference.hashCode();
        if (hashCode != -1852950412) {
            if (hashCode != 66902672) {
                if (hashCode != 79793479) {
                    if (hashCode == 2079612442) {
                        if (preference.equals("FOURTH")) {
                            obj = 3;
                            switch (obj) {
                                case null:
                                    verticalValue = 200;
                                    break;
                                case 1:
                                    verticalValue = BrickValues.DRONE_VERTICAL_INDOOR;
                                    break;
                                case 2:
                                    verticalValue = 1000;
                                    break;
                                case 3:
                                    verticalValue = 2000;
                                    break;
                                default:
                                    break;
                            }
                            setVerticalSpeed(verticalValue);
                        }
                    }
                } else if (preference.equals("THIRD")) {
                    obj = 2;
                    switch (obj) {
                        case null:
                            verticalValue = 200;
                            break;
                        case 1:
                            verticalValue = BrickValues.DRONE_VERTICAL_INDOOR;
                            break;
                        case 2:
                            verticalValue = 1000;
                            break;
                        case 3:
                            verticalValue = 2000;
                            break;
                        default:
                            break;
                    }
                    setVerticalSpeed(verticalValue);
                }
            } else if (preference.equals("FIRST")) {
                obj = null;
                switch (obj) {
                    case null:
                        verticalValue = 200;
                        break;
                    case 1:
                        verticalValue = BrickValues.DRONE_VERTICAL_INDOOR;
                        break;
                    case 2:
                        verticalValue = 1000;
                        break;
                    case 3:
                        verticalValue = 2000;
                        break;
                    default:
                        break;
                }
                setVerticalSpeed(verticalValue);
            }
        } else if (preference.equals("SECOND")) {
            obj = 1;
            switch (obj) {
                case null:
                    verticalValue = 200;
                    break;
                case 1:
                    verticalValue = BrickValues.DRONE_VERTICAL_INDOOR;
                    break;
                case 2:
                    verticalValue = 1000;
                    break;
                case 3:
                    verticalValue = 2000;
                    break;
                default:
                    break;
            }
            setVerticalSpeed(verticalValue);
        }
        obj = -1;
        switch (obj) {
            case null:
                verticalValue = 200;
                break;
            case 1:
                verticalValue = BrickValues.DRONE_VERTICAL_INDOOR;
                break;
            case 2:
                verticalValue = 1000;
                break;
            case 3:
                verticalValue = 2000;
                break;
            default:
                break;
        }
        setVerticalSpeed(verticalValue);
    }

    public void setVerticalSpeed(int value) {
        droneControlService = DroneServiceWrapper.getInstance().getDroneService();
        if (droneControlService != null) {
            Log.d(TAG, String.format("old vertical = %d", new Object[]{Integer.valueOf(droneControlService.getDroneConfig().getVertSpeedMax())}));
            if (200 > value || value > 2000) {
                droneControlService.getDroneConfig().setVertSpeedMax(BrickValues.DRONE_VERTICAL_INDOOR);
            } else {
                droneControlService.getDroneConfig().setVertSpeedMax(value);
            }
            Log.d(TAG, String.format("new vertical = %d", new Object[]{Integer.valueOf(droneControlService.getDroneConfig().getVertSpeedMax())}));
        }
    }

    private void setRotationSpeed(String preference) {
        Object obj;
        int rotationValue = 100;
        int hashCode = preference.hashCode();
        if (hashCode != -1852950412) {
            if (hashCode != 66902672) {
                if (hashCode != 79793479) {
                    if (hashCode == 2079612442) {
                        if (preference.equals("FOURTH")) {
                            obj = 3;
                            switch (obj) {
                                case null:
                                    rotationValue = 40;
                                    break;
                                case 1:
                                    rotationValue = 100;
                                    break;
                                case 2:
                                    rotationValue = 200;
                                    break;
                                case 3:
                                    rotationValue = 350;
                                    break;
                                default:
                                    break;
                            }
                            setRotationSpeed(rotationValue);
                        }
                    }
                } else if (preference.equals("THIRD")) {
                    obj = 2;
                    switch (obj) {
                        case null:
                            rotationValue = 40;
                            break;
                        case 1:
                            rotationValue = 100;
                            break;
                        case 2:
                            rotationValue = 200;
                            break;
                        case 3:
                            rotationValue = 350;
                            break;
                        default:
                            break;
                    }
                    setRotationSpeed(rotationValue);
                }
            } else if (preference.equals("FIRST")) {
                obj = null;
                switch (obj) {
                    case null:
                        rotationValue = 40;
                        break;
                    case 1:
                        rotationValue = 100;
                        break;
                    case 2:
                        rotationValue = 200;
                        break;
                    case 3:
                        rotationValue = 350;
                        break;
                    default:
                        break;
                }
                setRotationSpeed(rotationValue);
            }
        } else if (preference.equals("SECOND")) {
            obj = 1;
            switch (obj) {
                case null:
                    rotationValue = 40;
                    break;
                case 1:
                    rotationValue = 100;
                    break;
                case 2:
                    rotationValue = 200;
                    break;
                case 3:
                    rotationValue = 350;
                    break;
                default:
                    break;
            }
            setRotationSpeed(rotationValue);
        }
        obj = -1;
        switch (obj) {
            case null:
                rotationValue = 40;
                break;
            case 1:
                rotationValue = 100;
                break;
            case 2:
                rotationValue = 200;
                break;
            case 3:
                rotationValue = 350;
                break;
            default:
                break;
        }
        setRotationSpeed(rotationValue);
    }

    public void setRotationSpeed(int value) {
        droneControlService = DroneServiceWrapper.getInstance().getDroneService();
        if (droneControlService != null) {
            Log.d(TAG, String.format("old rotation = %d", new Object[]{Integer.valueOf(droneControlService.getDroneConfig().getYawSpeedMax())}));
            if (40 > value || value > 350) {
                droneControlService.getDroneConfig().setYawSpeedMax(100);
            } else {
                droneControlService.getDroneConfig().setYawSpeedMax(value);
            }
            Log.d(TAG, String.format("new rotation = %d", new Object[]{Integer.valueOf(droneControlService.getDroneConfig().getYawSpeedMax())}));
        }
    }

    private void setTiltAngle(String preference) {
        Object obj;
        int tiltValue = 12;
        int hashCode = preference.hashCode();
        if (hashCode != -1852950412) {
            if (hashCode != 66902672) {
                if (hashCode != 79793479) {
                    if (hashCode == 2079612442) {
                        if (preference.equals("FOURTH")) {
                            obj = 3;
                            switch (obj) {
                                case null:
                                    tiltValue = 5;
                                    break;
                                case 1:
                                    tiltValue = 12;
                                    break;
                                case 2:
                                    tiltValue = 20;
                                    break;
                                case 3:
                                    tiltValue = 30;
                                    break;
                                default:
                                    break;
                            }
                            setTiltAngle(tiltValue);
                        }
                    }
                } else if (preference.equals("THIRD")) {
                    obj = 2;
                    switch (obj) {
                        case null:
                            tiltValue = 5;
                            break;
                        case 1:
                            tiltValue = 12;
                            break;
                        case 2:
                            tiltValue = 20;
                            break;
                        case 3:
                            tiltValue = 30;
                            break;
                        default:
                            break;
                    }
                    setTiltAngle(tiltValue);
                }
            } else if (preference.equals("FIRST")) {
                obj = null;
                switch (obj) {
                    case null:
                        tiltValue = 5;
                        break;
                    case 1:
                        tiltValue = 12;
                        break;
                    case 2:
                        tiltValue = 20;
                        break;
                    case 3:
                        tiltValue = 30;
                        break;
                    default:
                        break;
                }
                setTiltAngle(tiltValue);
            }
        } else if (preference.equals("SECOND")) {
            obj = 1;
            switch (obj) {
                case null:
                    tiltValue = 5;
                    break;
                case 1:
                    tiltValue = 12;
                    break;
                case 2:
                    tiltValue = 20;
                    break;
                case 3:
                    tiltValue = 30;
                    break;
                default:
                    break;
            }
            setTiltAngle(tiltValue);
        }
        obj = -1;
        switch (obj) {
            case null:
                tiltValue = 5;
                break;
            case 1:
                tiltValue = 12;
                break;
            case 2:
                tiltValue = 20;
                break;
            case 3:
                tiltValue = 30;
                break;
            default:
                break;
        }
        setTiltAngle(tiltValue);
    }

    public void setTiltAngle(int value) {
        droneControlService = DroneServiceWrapper.getInstance().getDroneService();
        if (droneControlService != null) {
            Log.d(TAG, String.format("old tilt = %d", new Object[]{Integer.valueOf(droneControlService.getDroneConfig().getDeviceTiltMax())}));
            if (5 > value || value > 30) {
                droneControlService.getDroneConfig().setDeviceTiltMax(12);
            } else {
                droneControlService.getDroneConfig().setDeviceTiltMax(value);
            }
            Log.d(TAG, String.format("new tilt = %d", new Object[]{Integer.valueOf(droneControlService.getDroneConfig().getDeviceTiltMax())}));
        }
    }
}
