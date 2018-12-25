package org.catrobat.catroid.facedetection;

import android.hardware.Camera;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.formulaeditor.SensorCustomEventListener;

public final class FaceDetectionHandler {
    private static final String TAG = FaceDetectionHandler.class.getSimpleName();
    private static FaceDetector faceDetector;
    private static boolean paused = false;
    private static boolean running = false;

    private FaceDetectionHandler() {
        throw new AssertionError();
    }

    private static void createFaceDetector() {
        if (isIcsFaceDetectionSupported()) {
            faceDetector = new IcsFaceDetector();
        } else {
            faceDetector = new SlowFaceDetector();
        }
    }

    public static boolean isFaceDetectionRunning() {
        return running;
    }

    public static boolean startFaceDetection() {
        if (running) {
            return true;
        }
        if (!CameraManager.getInstance().hasBackCamera() && !CameraManager.getInstance().hasFrontCamera()) {
            return false;
        }
        if (faceDetector == null) {
            createFaceDetector();
            if (faceDetector == null) {
                return false;
            }
        }
        running = faceDetector.startFaceDetection();
        return running;
    }

    public static void resetFaceDedection() {
        if (running) {
            stopFaceDetection();
        }
        paused = false;
    }

    public static void stopFaceDetection() {
        if (running && faceDetector != null) {
            faceDetector.stopFaceDetection();
            running = false;
        }
    }

    public static void pauseFaceDetection() {
        if (running && faceDetector != null) {
            paused = true;
            stopFaceDetection();
            running = false;
        }
    }

    public static void resumeFaceDetection() {
        if (paused) {
            startFaceDetection();
            paused = false;
        }
    }

    public static void registerOnFaceDetectedListener(SensorCustomEventListener listener) {
        if (faceDetector == null) {
            createFaceDetector();
        }
        faceDetector.addOnFaceDetectedListener(listener);
    }

    public static void unregisterOnFaceDetectedListener(SensorCustomEventListener listener) {
        if (faceDetector != null) {
            faceDetector.removeOnFaceDetectedListener(listener);
        }
    }

    public static void registerOnFaceDetectionStatusListener(SensorCustomEventListener listener) {
        if (faceDetector == null) {
            createFaceDetector();
        }
        faceDetector.addOnFaceDetectionStatusListener(listener);
    }

    public static void unregisterOnFaceDetectionStatusListener(SensorCustomEventListener listener) {
        if (faceDetector != null) {
            faceDetector.removeOnFaceDetectionStatusListener(listener);
        }
    }

    public static boolean isIcsFaceDetectionSupported() {
        int possibleFaces = 0;
        try {
            boolean cameraReady = CameraManager.getInstance().isReady();
            if (!cameraReady) {
                CameraManager.getInstance().startCamera();
            }
            possibleFaces = getMaxNumberOfFaces(CameraManager.getInstance().getCurrentCamera());
            if (!cameraReady) {
                CameraManager.getInstance().releaseCamera();
            }
        } catch (Exception exception) {
            Log.e(TAG, "Camera unaccessable!", exception);
        }
        if (possibleFaces > 0) {
            return true;
        }
        return false;
    }

    private static int getMaxNumberOfFaces(Camera camera) {
        if (camera == null || camera.getParameters() == null) {
            return 0;
        }
        return camera.getParameters().getMaxNumDetectedFaces();
    }

    @VisibleForTesting
    public static void setFaceDetector(FaceDetector detector) {
        faceDetector = detector;
    }

    @VisibleForTesting
    public static FaceDetector getFaceDetector() {
        return faceDetector;
    }
}
