package org.catrobat.catroid.facedetection;

import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import org.catrobat.catroid.camera.CameraManager;

public class IcsFaceDetector extends FaceDetector implements FaceDetectionListener {
    private boolean running = false;

    public boolean startFaceDetection() {
        if (this.running) {
            return true;
        }
        if (!CameraManager.getInstance().isReady()) {
            CameraManager.getInstance().startCamera();
        }
        Camera camera = CameraManager.getInstance().getCurrentCamera();
        if (camera == null) {
            return false;
        }
        camera.setFaceDetectionListener(this);
        camera.startFaceDetection();
        this.running = true;
        return true;
    }

    public void stopFaceDetection() {
        if (this.running) {
            this.running = false;
            CameraManager.getInstance().getCurrentCamera().stopFaceDetection();
        }
    }

    public void onFaceDetection(Face[] faces, Camera camera) {
        boolean detected = faces.length > 0;
        onFaceDetected(detected);
        if (detected) {
            int maxConfidence = faces[0].score;
            int bestFaceIndex = 0;
            for (int i = 1; i < faces.length; i++) {
                if (faces[i].score > maxConfidence) {
                    maxConfidence = faces[i].score;
                    bestFaceIndex = i;
                }
            }
            Rect faceBounds = faces[bestFaceIndex].rect;
            Point centerPoint = new Point(faceBounds.centerX(), faceBounds.centerY());
            Point portraitCenterPoint = new Point(centerPoint.y, centerPoint.x);
            Point relationSize = getRelationForFacePosition();
            Point relativePoint = new Point((portraitCenterPoint.x * relationSize.x) / 2000, (portraitCenterPoint.y * relationSize.y) / 2000);
            int faceSize = (faceBounds.right - faceBounds.left) / 10;
            int i2 = 100;
            if (faceSize <= 100) {
                i2 = faceSize;
            }
            onFaceDetected(relativePoint, i2);
        }
    }
}
