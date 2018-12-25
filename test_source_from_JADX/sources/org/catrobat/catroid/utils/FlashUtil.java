package org.catrobat.catroid.utils;

import android.hardware.Camera.Parameters;
import android.util.Log;
import org.catrobat.catroid.camera.CameraManager;

public final class FlashUtil {
    private static final String TAG = FlashUtil.class.getSimpleName();
    private static boolean available = false;
    private static boolean currentFlashValue = false;
    private static Parameters paramsOff = null;
    private static Parameters paramsOn = null;
    private static boolean paused = false;
    private static boolean startAgain = false;

    private FlashUtil() {
    }

    public static boolean isAvailable() {
        return available;
    }

    public static boolean isOn() {
        return currentFlashValue;
    }

    public static void pauseFlash() {
        Log.d(TAG, "pauseFlash");
        if (!paused && isAvailable()) {
            paused = true;
            if (isOn()) {
                startAgain = true;
                flashOff();
            }
        }
    }

    public static void resumeFlash() {
        Log.d(TAG, "resumeFlash()");
        if (paused) {
            initializeFlash();
            if (startAgain) {
                flashOn();
                startAgain = false;
            }
        }
        paused = false;
    }

    public static void destroy() {
        Log.d(TAG, "reset all variables - called by StageActivity::onDestroy");
        currentFlashValue = false;
        paused = false;
        available = false;
        startAgain = false;
        if (CameraManager.getInstance().isReady()) {
            paramsOff = null;
            paramsOn = null;
        }
    }

    public static void reset() {
        currentFlashValue = false;
    }

    public static void initializeFlash() {
        Log.d(TAG, "initializeFlash()");
        available = true;
        if (!CameraManager.getInstance().isReady()) {
            CameraManager.getInstance().startCamera();
        }
        paramsOn = CameraManager.getInstance().getCurrentCamera().getParameters();
        if (paramsOn != null) {
            paramsOn.setFlashMode("torch");
        }
        paramsOff = CameraManager.getInstance().getCurrentCamera().getParameters();
        if (paramsOff != null) {
            paramsOff.setFlashMode("off");
        }
    }

    public static void flashOn() {
        Log.d(TAG, "flashOn()");
        if (CameraManager.getInstance().hasCurrentCameraFlash()) {
            CameraManager.getInstance().setFlashParams(paramsOn);
            currentFlashValue = true;
            return;
        }
        currentFlashValue = false;
        CameraManager.getInstance().destroyStage();
    }

    public static void flashOff() {
        Log.d(TAG, "flashOff()");
        CameraManager.getInstance().setFlashParams(paramsOff);
        currentFlashValue = false;
    }
}
