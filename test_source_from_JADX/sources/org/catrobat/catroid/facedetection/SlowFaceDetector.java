package org.catrobat.catroid.facedetection;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.support.annotation.VisibleForTesting;
import com.google.common.primitives.Ints;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.camera.JpgPreviewCallback;

public class SlowFaceDetector extends FaceDetector implements JpgPreviewCallback {
    private static final int NUMBER_OF_FACES = 1;

    public boolean startFaceDetection() {
        CameraManager.getInstance().addOnJpgPreviewFrameCallback(this);
        return CameraManager.getInstance().startCamera();
    }

    public void stopFaceDetection() {
        CameraManager.getInstance().removeOnJpgPreviewFrameCallback(this);
        CameraManager.getInstance().releaseCamera();
    }

    public void onFrame(byte[] data) {
        Bitmap preview = BitmapFactory.decodeByteArray(data, 0, data.length);
        detectFaces(preview);
        preview.recycle();
    }

    private void detectFaces(Bitmap bitmap) {
        SlowFaceDetector slowFaceDetector = this;
        if (bitmap != null) {
            int height = bitmap.getWidth();
            int width = bitmap.getHeight();
            Matrix rotateAndInvertX = new Matrix();
            boolean invertX = CameraManager.getInstance().isCurrentCameraFacingBack();
            rotateAndInvertX.postRotate((float) 0);
            rotateAndInvertX.postScale(invertX ? -1.0f : 1.0f, 1.0f);
            Bitmap portraitBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotateAndInvertX, true);
            Bitmap rgb565Bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
            Paint paint = new Paint();
            boolean detected = true;
            paint.setDither(true);
            Canvas canvas = new Canvas();
            canvas.setBitmap(rgb565Bitmap);
            canvas.drawBitmap(portraitBitmap, 0.0f, 0.0f, paint);
            Face[] faces = new Face[1];
            if (new FaceDetector(width, height, 1).findFaces(rgb565Bitmap, faces) <= 0) {
                detected = false;
            }
            onFaceDetected(detected);
            if (detected) {
                PointF centerPoint = new PointF();
                faces[0].getMidPoint(centerPoint);
                onFaceFound(centerPoint, faces[0].eyesDistance(), width, height);
            }
        }
    }

    private void onFaceFound(PointF centerPoint, float eyeDistance, int detectionWidth, int detectionHeight) {
        Point intPoint = new Point((int) centerPoint.x, (int) centerPoint.y);
        Point relationSize = getRelationForFacePosition();
        Point relativePoint = new Point(((intPoint.x - (detectionWidth / 2)) * relationSize.x) / detectionWidth, ((intPoint.y - (detectionHeight / 2)) * relationSize.y) / detectionHeight);
        int relativeFaceSize = (((int) (Ints.MAX_POWER_OF_TWO * eyeDistance)) * 200) / detectionWidth;
        int i = 100;
        if (relativeFaceSize <= 100) {
            i = relativeFaceSize;
        }
        onFaceDetected(relativePoint, i);
    }

    @VisibleForTesting
    public void callOnFaceFound(PointF centerPoint, float eyeDistance, int detectionWidth, int detectionHeight) {
        onFaceFound(centerPoint, eyeDistance, detectionWidth, detectionHeight);
    }
}
