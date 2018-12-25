package org.catrobat.catroid.facedetection;

import android.graphics.Point;
import android.support.annotation.VisibleForTesting;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.formulaeditor.SensorCustomEvent;
import org.catrobat.catroid.formulaeditor.SensorCustomEventListener;
import org.catrobat.catroid.formulaeditor.Sensors;

public abstract class FaceDetector {
    private boolean faceDetected = false;
    private List<SensorCustomEventListener> faceDetectedListeners = new LinkedList();
    private List<SensorCustomEventListener> faceDetectionStatusListeners = new LinkedList();

    public abstract boolean startFaceDetection();

    public abstract void stopFaceDetection();

    public void addOnFaceDetectedListener(SensorCustomEventListener listener) {
        if (listener != null) {
            this.faceDetectedListeners.add(listener);
        }
    }

    public void removeOnFaceDetectedListener(SensorCustomEventListener listener) {
        this.faceDetectedListeners.remove(listener);
    }

    public void addOnFaceDetectionStatusListener(SensorCustomEventListener listener) {
        if (listener != null) {
            this.faceDetectionStatusListeners.add(listener);
        }
    }

    public void removeOnFaceDetectionStatusListener(SensorCustomEventListener listener) {
        this.faceDetectionStatusListeners.remove(listener);
    }

    void onFaceDetected(Point position, int size) {
        float[] positionXFloatValue = new float[]{(float) position.x};
        float[] positionYFloatValue = new float[1];
        positionYFloatValue[0] = (float) (CameraManager.getInstance().isCurrentCameraFacingBack() ^ true ? -position.y : position.y);
        float[] sizeFloatValue = new float[]{(float) size};
        SensorCustomEvent xPositionEvent = new SensorCustomEvent(Sensors.FACE_X_POSITION, positionXFloatValue);
        SensorCustomEvent yPositionEvent = new SensorCustomEvent(Sensors.FACE_Y_POSITION, positionYFloatValue);
        SensorCustomEvent sizeEvent = new SensorCustomEvent(Sensors.FACE_SIZE, sizeFloatValue);
        for (SensorCustomEventListener faceDetectedListener : this.faceDetectedListeners) {
            faceDetectedListener.onCustomSensorChanged(xPositionEvent);
            faceDetectedListener.onCustomSensorChanged(yPositionEvent);
            faceDetectedListener.onCustomSensorChanged(sizeEvent);
        }
    }

    protected void onFaceDetected(boolean faceDetected) {
        if (this.faceDetected != faceDetected) {
            this.faceDetected = faceDetected;
            float[] detectedFloatValue = new float[1];
            detectedFloatValue[0] = faceDetected ? 1.0f : 0.0f;
            SensorCustomEvent event = new SensorCustomEvent(Sensors.FACE_DETECTED, detectedFloatValue);
            for (SensorCustomEventListener listener : this.faceDetectionStatusListeners) {
                listener.onCustomSensorChanged(event);
            }
        }
    }

    Point getRelationForFacePosition() {
        return new Point(-ScreenValues.SCREEN_WIDTH, -ScreenValues.SCREEN_HEIGHT);
    }

    @VisibleForTesting
    public void callOnFaceDetected(boolean faceDetected) {
        onFaceDetected(faceDetected);
    }

    @VisibleForTesting
    public void callOnFaceDetected(Point position, int size) {
        onFaceDetected(position, size);
    }
}
