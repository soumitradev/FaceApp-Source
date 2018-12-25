package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import com.parrot.freeflight.service.DroneControlService;
import java.util.Calendar;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;
import org.catrobat.catroid.facedetection.FaceDetectionHandler;
import org.catrobat.catroid.nfc.NfcHandler;
import org.catrobat.catroid.utils.TouchUtil;

public final class SensorHandler implements SensorEventListener, SensorCustomEventListener, LocationListener, Listener {
    public static final float RADIAN_TO_DEGREE_CONST = 57.295776f;
    private static final String TAG = SensorHandler.class.getSimpleName();
    private static BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private static SensorHandler instance = null;
    private boolean accelerationAvailable = true;
    private float[] accelerationXYZ = new float[3];
    private Sensor accelerometerSensor = null;
    private double altitude = BrickValues.SET_COLOR_TO;
    private boolean compassAvailable = true;
    private float faceDetected = 0.0f;
    private float facePositionX = 0.0f;
    private float facePositionY = 0.0f;
    private float faceSize = 0.0f;
    private float[] gravity = new float[]{0.0f, 0.0f, 0.0f};
    private boolean inclinationAvailable = true;
    private boolean isGpsConnected = false;
    private Location lastLocationGps;
    private long lastLocationGpsMillis;
    private double latitude = BrickValues.SET_COLOR_TO;
    private Sensor linearAccelerationSensor = null;
    private float linearAccelerationX = 0.0f;
    private float linearAccelerationY = 0.0f;
    private float linearAccelerationZ = 0.0f;
    private float locationAccuracy = 0.0f;
    private LocationManager locationManager = null;
    private double longitude = BrickValues.SET_COLOR_TO;
    private float loudness = 0.0f;
    private Sensor magneticFieldSensor = null;
    private float[] rotationMatrix = new float[16];
    private float[] rotationVector = new float[3];
    private Sensor rotationVectorSensor = null;
    private SensorManagerInterface sensorManager = null;
    private float signAccelerationZ = 0.0f;
    private boolean useLinearAccelerationFallback = false;
    private boolean useRotationVectorFallback = false;

    private SensorHandler(Context context) {
        this.sensorManager = new SensorManager((SensorManager) context.getSystemService("sensor"));
        this.linearAccelerationSensor = this.sensorManager.getDefaultSensor(10);
        if (this.linearAccelerationSensor == null) {
            this.useLinearAccelerationFallback = true;
        }
        this.rotationVectorSensor = this.sensorManager.getDefaultSensor(11);
        if (this.rotationVectorSensor == null) {
            this.useRotationVectorFallback = true;
        }
        if (this.useRotationVectorFallback) {
            this.accelerometerSensor = this.sensorManager.getDefaultSensor(1);
            this.magneticFieldSensor = this.sensorManager.getDefaultSensor(2);
            if (this.accelerometerSensor == null) {
                this.accelerationAvailable = false;
                this.inclinationAvailable = false;
            }
            if (this.magneticFieldSensor == null) {
                this.compassAvailable = false;
            }
        } else if (this.useLinearAccelerationFallback) {
            this.accelerometerSensor = this.sensorManager.getDefaultSensor(1);
            if (this.accelerometerSensor == null) {
                this.accelerationAvailable = false;
            }
        }
        this.locationManager = (LocationManager) context.getSystemService(FirebaseAnalytics$Param.LOCATION);
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("*** LINEAR_ACCELERATION SENSOR: ");
        stringBuilder.append(this.linearAccelerationSensor);
        Log.d(str, stringBuilder.toString());
        str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("*** ACCELEROMETER SENSOR: ");
        stringBuilder.append(this.accelerometerSensor);
        Log.d(str, stringBuilder.toString());
        str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("*** ROTATION_VECTOR SENSOR: ");
        stringBuilder.append(this.rotationVectorSensor);
        Log.d(str, stringBuilder.toString());
        str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("*** MAGNETIC_FIELD SENSOR: ");
        stringBuilder.append(this.magneticFieldSensor);
        Log.d(str, stringBuilder.toString());
        str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("*** LOCATION_MANAGER: ");
        stringBuilder.append(this.locationManager);
        Log.d(str, stringBuilder.toString());
    }

    public boolean compassAvailable() {
        return this.compassAvailable;
    }

    public static boolean gpsAvailable() {
        return gpsSensorAvailable() | networkGpsAvailable();
    }

    private static boolean gpsSensorAvailable() {
        return instance.locationManager.isProviderEnabled("gps");
    }

    private static boolean networkGpsAvailable() {
        return instance.locationManager.isProviderEnabled("network");
    }

    private static double startWeekWithMonday() {
        int convertedWeekday;
        int weekdayOfAndroidCalendar = Calendar.getInstance().get(7);
        if (weekdayOfAndroidCalendar == 1) {
            convertedWeekday = 7;
        } else {
            convertedWeekday = weekdayOfAndroidCalendar - 1;
        }
        return (double) convertedWeekday;
    }

    public boolean accelerationAvailable() {
        return this.accelerationAvailable;
    }

    public boolean inclinationAvailable() {
        return this.inclinationAvailable;
    }

    public static SensorHandler getInstance(Context context) {
        if (instance == null) {
            instance = new SensorHandler(context);
        }
        return instance;
    }

    public static void startSensorListener(Context context) {
        if (instance == null) {
            instance = new SensorHandler(context);
        }
        instance.sensorManager.unregisterListener(instance);
        instance.sensorManager.unregisterListener(instance);
        instance.locationManager.removeUpdates(instance);
        instance.locationManager.removeGpsStatusListener(instance);
        registerListener(instance);
        instance.sensorManager.registerListener(instance, Sensors.LOUDNESS);
        FaceDetectionHandler.registerOnFaceDetectedListener(instance);
        FaceDetectionHandler.registerOnFaceDetectionStatusListener(instance);
        instance.locationManager.addGpsStatusListener(instance);
        if (gpsSensorAvailable()) {
            instance.locationManager.requestLocationUpdates("gps", 0, 0.0f, instance);
        }
        if (networkGpsAvailable()) {
            instance.locationManager.requestLocationUpdates("network", 0, 0.0f, instance);
        }
    }

    public static void registerListener(SensorEventListener listener) {
        if (instance != null) {
            if (!instance.useLinearAccelerationFallback) {
                instance.sensorManager.registerListener(listener, instance.linearAccelerationSensor, 1);
            }
            if (!instance.useRotationVectorFallback) {
                instance.sensorManager.registerListener(listener, instance.rotationVectorSensor, 1);
            }
            if (instance.useLinearAccelerationFallback || instance.useRotationVectorFallback) {
                instance.sensorManager.registerListener(listener, instance.accelerometerSensor, 1);
            }
            if (instance.useRotationVectorFallback && instance.magneticFieldSensor != null) {
                instance.sensorManager.registerListener(listener, instance.magneticFieldSensor, 1);
            }
        }
    }

    public static void unregisterListener(SensorEventListener listener) {
        if (instance != null) {
            instance.sensorManager.unregisterListener(listener);
        }
    }

    public static void stopSensorListeners() {
        if (instance != null) {
            instance.sensorManager.unregisterListener(instance);
            instance.sensorManager.unregisterListener(instance);
            instance.locationManager.removeUpdates(instance);
            instance.locationManager.removeGpsStatusListener(instance);
            FaceDetectionHandler.unregisterOnFaceDetectedListener(instance);
            FaceDetectionHandler.unregisterOnFaceDetectionStatusListener(instance);
        }
    }

    public static Object getSensorValue(Sensors sensor) {
        Sensors sensors = sensor;
        SensorManagerInterface sensorManagerInterface = instance.sensorManager;
        double d = BrickValues.SET_COLOR_TO;
        if (sensorManagerInterface == null) {
            return Double.valueOf(BrickValues.SET_COLOR_TO);
        }
        DroneControlService dcs = DroneServiceWrapper.getInstance().getDroneService();
        float[] rotationMatrixOut = new float[16];
        int rotateOrientation;
        int rotate;
        float[] orientations;
        int rotate2;
        float rawInclinationX;
        float correctedInclinationX;
        switch (sensor) {
            case X_ACCELERATION:
                rotateOrientation = rotateOrientation();
                rotate = rotateOrientation;
                if (rotateOrientation != 0) {
                    return Double.valueOf((double) ((-instance.linearAccelerationY) * ((float) rotate)));
                }
                return Double.valueOf((double) instance.linearAccelerationX);
            case Y_ACCELERATION:
                rotateOrientation = rotateOrientation();
                rotate = rotateOrientation;
                if (rotateOrientation != 0) {
                    return Double.valueOf((double) (instance.linearAccelerationX * ((float) rotate)));
                }
                return Double.valueOf((double) instance.linearAccelerationY);
            case Z_ACCELERATION:
                return Double.valueOf((double) instance.linearAccelerationZ);
            case COMPASS_DIRECTION:
                orientations = new float[3];
                if (!instance.useRotationVectorFallback) {
                    SensorManager.getRotationMatrixFromVector(instance.rotationMatrix, instance.rotationVector);
                }
                rotate = rotateOrientation();
                rotate2 = rotate;
                if (rotate == 1) {
                    SensorManager.remapCoordinateSystem(instance.rotationMatrix, 2, 129, rotationMatrixOut);
                    SensorManager.getOrientation(rotationMatrixOut, orientations);
                } else if (rotate2 == -1) {
                    SensorManager.remapCoordinateSystem(instance.rotationMatrix, 130, 1, rotationMatrixOut);
                    SensorManager.getOrientation(rotationMatrixOut, orientations);
                } else {
                    SensorManager.getOrientation(instance.rotationMatrix, orientations);
                }
                return Double.valueOf((Double.valueOf((double) orientations[0]).doubleValue() * 57.2957763671875d) * -1.0d);
            case LATITUDE:
                return Double.valueOf(instance.latitude);
            case LONGITUDE:
                return Double.valueOf(instance.longitude);
            case LOCATION_ACCURACY:
                return Double.valueOf((double) instance.locationAccuracy);
            case ALTITUDE:
                return Double.valueOf(instance.altitude);
            case X_INCLINATION:
                if (instance.useRotationVectorFallback) {
                    rotateOrientation = rotateOrientation();
                    rotate = rotateOrientation;
                    if (rotateOrientation != 0) {
                        rawInclinationX = ((float) Math.acos((double) (instance.accelerationXYZ[1] * ((float) rotate)))) * 57.295776f;
                    } else {
                        rawInclinationX = ((float) Math.acos((double) instance.accelerationXYZ[0])) * 57.295776f;
                    }
                    correctedInclinationX = 0.0f;
                    if (rawInclinationX >= 90.0f && rawInclinationX <= 180.0f) {
                        correctedInclinationX = instance.signAccelerationZ > 0.0f ? -(rawInclinationX - 90.0f) : -((90.0f - rawInclinationX) + 180.0f);
                    } else if (rawInclinationX >= 0.0f && rawInclinationX < 90.0f) {
                        correctedInclinationX = instance.signAccelerationZ > 0.0f ? 90.0f - rawInclinationX : rawInclinationX + 90.0f;
                    }
                    if (rotateOrientation() != 0) {
                        correctedInclinationX = -correctedInclinationX;
                    }
                    return Double.valueOf((double) correctedInclinationX);
                }
                orientations = new float[3];
                SensorManager.getRotationMatrixFromVector(instance.rotationMatrix, instance.rotationVector);
                rotate = rotateOrientation();
                rotate2 = rotate;
                if (rotate == 1) {
                    SensorManager.remapCoordinateSystem(instance.rotationMatrix, 2, 129, rotationMatrixOut);
                    SensorManager.getOrientation(rotationMatrixOut, orientations);
                } else if (rotate2 == -1) {
                    SensorManager.remapCoordinateSystem(instance.rotationMatrix, 130, 1, rotationMatrixOut);
                    SensorManager.getOrientation(rotationMatrixOut, orientations);
                } else {
                    SensorManager.getOrientation(instance.rotationMatrix, orientations);
                }
                return Double.valueOf((Double.valueOf((double) orientations[2]).doubleValue() * 57.2957763671875d) * -1.0d);
            case Y_INCLINATION:
                if (instance.useRotationVectorFallback) {
                    rotateOrientation = rotateOrientation();
                    rotate = rotateOrientation;
                    if (rotateOrientation != 0) {
                        rawInclinationX = ((float) Math.acos((double) (instance.accelerationXYZ[0] * ((float) rotate)))) * 57.295776f;
                    } else {
                        rawInclinationX = ((float) Math.acos((double) instance.accelerationXYZ[1])) * 57.295776f;
                    }
                    correctedInclinationX = 0.0f;
                    if (rawInclinationX >= 90.0f && rawInclinationX <= 180.0f) {
                        correctedInclinationX = instance.signAccelerationZ > 0.0f ? -(rawInclinationX - 90.0f) : -((90.0f - rawInclinationX) + 180.0f);
                    } else if (rawInclinationX >= 0.0f && rawInclinationX < 90.0f) {
                        correctedInclinationX = instance.signAccelerationZ > 0.0f ? 90.0f - rawInclinationX : rawInclinationX + 90.0f;
                    }
                    return Double.valueOf((double) correctedInclinationX);
                }
                orientations = new float[3];
                SensorManager.getRotationMatrixFromVector(instance.rotationMatrix, instance.rotationVector);
                if (rotateOrientation() == 1) {
                    SensorManager.remapCoordinateSystem(instance.rotationMatrix, 2, 129, rotationMatrixOut);
                    SensorManager.getOrientation(rotationMatrixOut, orientations);
                } else if (rotateOrientation() == -1) {
                    SensorManager.remapCoordinateSystem(instance.rotationMatrix, 130, 1, rotationMatrixOut);
                    SensorManager.getOrientation(rotationMatrixOut, orientations);
                } else {
                    SensorManager.getOrientation(instance.rotationMatrix, orientations);
                }
                float xInclinationUsedToExtendRangeOfRoll = (orientations[2] * 57.295776f) * -1.0f;
                Double sensorValue = Double.valueOf((double) orientations[1]);
                if (Math.abs(xInclinationUsedToExtendRangeOfRoll) <= 90.0f) {
                    return Double.valueOf((sensorValue.doubleValue() * 57.2957763671875d) * -1.0d);
                }
                float uncorrectedYInclination = (sensorValue.floatValue() * 57.295776f) * -1.0f;
                if (uncorrectedYInclination > 0.0f) {
                    return Double.valueOf(180.0d - ((double) uncorrectedYInclination));
                }
                return Double.valueOf(-180.0d - ((double) uncorrectedYInclination));
            case FACE_DETECTED:
                return Double.valueOf((double) instance.faceDetected);
            case FACE_SIZE:
                return Double.valueOf((double) instance.faceSize);
            case FACE_X_POSITION:
                rotateOrientation = rotateOrientation();
                rotate = rotateOrientation;
                if (rotateOrientation != 0) {
                    return Double.valueOf((double) ((-instance.facePositionY) * ((float) rotate)));
                }
                return Double.valueOf((double) instance.facePositionX);
            case FACE_Y_POSITION:
                rotateOrientation = rotateOrientation();
                rotate = rotateOrientation;
                if (rotateOrientation != 0) {
                    return Double.valueOf(((double) instance.facePositionX) * ((double) rotate));
                }
                return Double.valueOf((double) instance.facePositionY);
            case LOUDNESS:
                return Double.valueOf((double) instance.loudness);
            case DATE_YEAR:
                return Double.valueOf((double) Calendar.getInstance().get(1));
            case DATE_MONTH:
                return Double.valueOf((double) (Calendar.getInstance().get(2) + 1));
            case DATE_DAY:
                return Double.valueOf((double) Calendar.getInstance().get(5));
            case DATE_WEEKDAY:
                return Double.valueOf(startWeekWithMonday());
            case TIME_HOUR:
                return Double.valueOf((double) Calendar.getInstance().get(11));
            case TIME_MINUTE:
                return Double.valueOf((double) Calendar.getInstance().get(12));
            case TIME_SECOND:
                return Double.valueOf((double) Calendar.getInstance().get(13));
            case NXT_SENSOR_1:
            case NXT_SENSOR_2:
            case NXT_SENSOR_3:
            case NXT_SENSOR_4:
                LegoNXT nxt = (LegoNXT) btService.getDevice(BluetoothDevice.LEGO_NXT);
                if (nxt != null) {
                    return Double.valueOf((double) nxt.getSensorValue(sensors));
                }
                break;
            case EV3_SENSOR_1:
            case EV3_SENSOR_2:
            case EV3_SENSOR_3:
            case EV3_SENSOR_4:
                LegoEV3 ev3 = (LegoEV3) btService.getDevice(BluetoothDevice.LEGO_EV3);
                if (ev3 != null) {
                    return Double.valueOf((double) ev3.getSensorValue(sensors));
                }
                break;
            case PHIRO_BOTTOM_LEFT:
            case PHIRO_BOTTOM_RIGHT:
            case PHIRO_FRONT_LEFT:
            case PHIRO_FRONT_RIGHT:
            case PHIRO_SIDE_LEFT:
            case PHIRO_SIDE_RIGHT:
                Phiro phiro = (Phiro) btService.getDevice(BluetoothDevice.PHIRO);
                if (phiro != null) {
                    return Double.valueOf((double) phiro.getSensorValue(sensors));
                }
                break;
            case GAMEPAD_A_PRESSED:
            case GAMEPAD_B_PRESSED:
            case GAMEPAD_DOWN_PRESSED:
            case GAMEPAD_LEFT_PRESSED:
            case GAMEPAD_RIGHT_PRESSED:
            case GAMEPAD_UP_PRESSED:
                if (CastManager.getInstance().isButtonPressed(sensors)) {
                    d = 1.0d;
                }
                return Double.valueOf(d);
            case LAST_FINGER_INDEX:
                return Double.valueOf((double) TouchUtil.getLastTouchIndex());
            case FINGER_TOUCHED:
                if (TouchUtil.isFingerTouching(TouchUtil.getLastTouchIndex())) {
                    d = 1.0d;
                }
                return Double.valueOf(d);
            case FINGER_X:
                return Double.valueOf((double) TouchUtil.getX(TouchUtil.getLastTouchIndex()));
            case FINGER_Y:
                return Double.valueOf((double) TouchUtil.getY(TouchUtil.getLastTouchIndex()));
            case DRONE_BATTERY_STATUS:
                return Double.valueOf((double) dcs.getDroneNavData().batteryStatus);
            case DRONE_EMERGENCY_STATE:
                return Double.valueOf((double) dcs.getDroneNavData().emergencyState);
            case DRONE_USB_REMAINING_TIME:
                return Double.valueOf((double) dcs.getDroneNavData().usbRemainingTime);
            case DRONE_NUM_FRAMES:
                return Double.valueOf((double) dcs.getDroneNavData().numFrames);
            case DRONE_RECORDING:
                if (dcs.getDroneNavData().recording) {
                    return Double.valueOf(1.0d);
                }
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            case DRONE_FLYING:
                if (dcs.getDroneNavData().flying) {
                    return Double.valueOf(1.0d);
                }
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            case DRONE_INITIALIZED:
                if (dcs.getDroneNavData().initialized) {
                    return Double.valueOf(1.0d);
                }
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            case DRONE_USB_ACTIVE:
                if (dcs.getDroneNavData().usbActive) {
                    return Double.valueOf(1.0d);
                }
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            case DRONE_CAMERA_READY:
                if (dcs.getDroneNavData().cameraReady) {
                    return Double.valueOf(1.0d);
                }
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            case DRONE_RECORD_READY:
                if (dcs.getDroneNavData().recordReady) {
                    return Double.valueOf(1.0d);
                }
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            case NFC_TAG_MESSAGE:
                return String.valueOf(NfcHandler.getLastNfcTagMessage());
            case NFC_TAG_ID:
                return String.valueOf(NfcHandler.getLastNfcTagId());
            default:
                break;
        }
        return Double.valueOf(BrickValues.SET_COLOR_TO);
    }

    public static void clearFaceDetectionValues() {
        if (instance != null) {
            instance.faceDetected = 0.0f;
            instance.faceSize = 0.0f;
            instance.facePositionX = 0.0f;
            instance.facePositionY = 0.0f;
        }
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    private static boolean isDeviceDefaultRotationLandscape() {
        int rotation = ((WindowManager) CatroidApplication.getAppContext().getSystemService("window")).getDefaultDisplay().getRotation();
        Configuration config = CatroidApplication.getAppContext().getResources().getConfiguration();
        return (config.orientation == 2 && (rotation == 0 || rotation == 2)) || (config.orientation == 1 && (rotation == 1 || rotation == 3));
    }

    private static int rotateOrientation() {
        if ((ProjectManager.getInstance().isCurrentProjectLandscapeMode() ^ isDeviceDefaultRotationLandscape()) == 0) {
            return 0;
        }
        return ProjectManager.getInstance().isCurrentProjectLandscapeMode() ? 1 : -1;
    }

    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case 1:
                this.accelerationXYZ = (float[]) event.values.clone();
                if (this.useLinearAccelerationFallback) {
                    determinePseudoLinearAcceleration((float[]) this.accelerationXYZ.clone());
                }
                double normOfG = Math.sqrt((double) (((this.accelerationXYZ[0] * this.accelerationXYZ[0]) + (this.accelerationXYZ[1] * this.accelerationXYZ[1])) + (this.accelerationXYZ[2] * this.accelerationXYZ[2])));
                this.accelerationXYZ[0] = (float) (((double) this.accelerationXYZ[0]) / normOfG);
                this.accelerationXYZ[1] = (float) (((double) this.accelerationXYZ[1]) / normOfG);
                this.accelerationXYZ[2] = (float) (((double) this.accelerationXYZ[2]) / normOfG);
                this.signAccelerationZ = Math.signum(event.values[2]);
                return;
            case 2:
                float[] tempInclinationMatrix = new float[9];
                SensorManager.getRotationMatrix(this.rotationMatrix, tempInclinationMatrix, this.accelerationXYZ, (float[]) event.values.clone());
                return;
            case 10:
                this.linearAccelerationX = event.values[0];
                this.linearAccelerationY = event.values[1];
                this.linearAccelerationZ = event.values[2];
                return;
            case 11:
                this.rotationVector[0] = event.values[0];
                this.rotationVector[1] = event.values[1];
                this.rotationVector[2] = event.values[2];
                return;
            default:
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unhandled sensor type: ");
                stringBuilder.append(event.sensor.getType());
                Log.v(str, stringBuilder.toString());
                return;
        }
    }

    private void determinePseudoLinearAcceleration(float[] input) {
        this.gravity[0] = (this.gravity[0] * 0.8f) + ((1.0f - 0.8f) * input[0]);
        this.gravity[1] = (this.gravity[1] * 0.8f) + ((1.0f - 0.8f) * input[1]);
        this.gravity[2] = (this.gravity[2] * 0.8f) + ((1.0f - 0.8f) * input[2]);
        this.linearAccelerationX = (input[0] - this.gravity[0]) * -1.0f;
        this.linearAccelerationY = (input[1] - this.gravity[1]) * -1.0f;
        this.linearAccelerationZ = (input[2] - this.gravity[2]) * -1.0f;
    }

    public void onCustomSensorChanged(SensorCustomEvent event) {
        switch (event.sensor) {
            case FACE_DETECTED:
                instance.faceDetected = event.values[0];
                return;
            case FACE_SIZE:
                instance.faceSize = event.values[0];
                return;
            case FACE_X_POSITION:
                instance.facePositionX = event.values[0];
                return;
            case FACE_Y_POSITION:
                instance.facePositionY = event.values[0];
                return;
            case LOUDNESS:
                instance.loudness = event.values[0];
                return;
            default:
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unhandled sensor: ");
                stringBuilder.append(event.sensor);
                Log.v(str, stringBuilder.toString());
                return;
        }
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            if (location.getProvider().equals("gps") || !this.isGpsConnected) {
                this.latitude = location.getLatitude();
                this.longitude = location.getLongitude();
                this.locationAccuracy = location.getAccuracy();
                this.altitude = location.hasAltitude() ? location.getAltitude() : BrickValues.SET_COLOR_TO;
            }
            if (location.getProvider().equals("gps")) {
                this.lastLocationGpsMillis = SystemClock.elapsedRealtime();
                this.lastLocationGps = location;
            }
        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }

    public void onGpsStatusChanged(int event) {
        boolean z = true;
        switch (event) {
            case 3:
                this.isGpsConnected = true;
                return;
            case 4:
                if (this.lastLocationGps != null) {
                    if (SystemClock.elapsedRealtime() - this.lastLocationGpsMillis >= 3000) {
                        z = false;
                    }
                    this.isGpsConnected = z;
                    return;
                }
                return;
            default:
                return;
        }
    }
}
