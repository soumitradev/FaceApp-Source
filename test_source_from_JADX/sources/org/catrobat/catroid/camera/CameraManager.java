package org.catrobat.catroid.camera;

import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.facedetection.FaceDetectionHandler;
import org.catrobat.catroid.stage.CameraSurface;
import org.catrobat.catroid.stage.DeviceCameraControl;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.FlashUtil;

public final class CameraManager implements DeviceCameraControl, PreviewCallback {
    private static final String TAG = CameraManager.class.getSimpleName();
    public static final int TEXTURE_NAME = 1;
    private static CameraManager instance;
    private CameraInformation backCameraInformation = null;
    private List<JpgPreviewCallback> callbacks = new ArrayList();
    private final Object cameraBaseLock;
    public final Object cameraChangeLock;
    private int cameraCount;
    CameraSurface cameraSurface;
    private Camera currentCamera;
    private CameraInformation currentCameraInformation = null;
    private CameraInformation defaultCameraInformation = null;
    private CameraInformation frontCameraInformation = null;
    private int previewFormat;
    private int previewHeight;
    private int previewWidth;
    StageActivity stageActivity;
    private CameraState state;
    private SurfaceTexture texture;
    private boolean wasRunning;

    /* renamed from: org.catrobat.catroid.camera.CameraManager$1 */
    class C17471 implements Runnable {
        C17471() {
        }

        public void run() {
            CameraManager.this.pausePreview();
        }
    }

    /* renamed from: org.catrobat.catroid.camera.CameraManager$2 */
    class C17482 implements Runnable {
        C17482() {
        }

        public void run() {
            CameraManager.this.resumePreview();
        }
    }

    /* renamed from: org.catrobat.catroid.camera.CameraManager$3 */
    class C17493 implements Runnable {
        C17493() {
        }

        public void run() {
            CameraManager.this.prepareCamera();
        }
    }

    /* renamed from: org.catrobat.catroid.camera.CameraManager$4 */
    class C17504 implements Runnable {
        C17504() {
        }

        public void run() {
            CameraManager.this.stopPreview();
        }
    }

    /* renamed from: org.catrobat.catroid.camera.CameraManager$5 */
    class C17515 implements Runnable {
        C17515() {
        }

        public void run() {
            CameraManager.this.pausePreview();
        }
    }

    /* renamed from: org.catrobat.catroid.camera.CameraManager$6 */
    class C17526 implements Runnable {
        C17526() {
        }

        public void run() {
            CameraManager.this.resumePreview();
        }
    }

    /* renamed from: org.catrobat.catroid.camera.CameraManager$7 */
    class C17537 implements Runnable {
        C17537() {
        }

        public void run() {
            CameraManager.this.changeCamera();
        }
    }

    public class CameraInformation {
        protected int cameraId;
        protected boolean flashAvailable = false;

        protected CameraInformation(int cameraId, boolean flashAvailable) {
            this.cameraId = cameraId;
            this.flashAvailable = flashAvailable;
        }
    }

    public enum CameraState {
        notUsed,
        prepare,
        previewRunning,
        previewPaused,
        stopped
    }

    public static CameraManager getInstance() {
        if (instance == null) {
            instance = new CameraManager();
        }
        return instance;
    }

    private CameraManager() {
        int id = 0;
        this.cameraCount = 0;
        this.stageActivity = null;
        this.cameraSurface = null;
        this.cameraChangeLock = new Object();
        this.cameraBaseLock = new Object();
        this.wasRunning = false;
        this.state = CameraState.notUsed;
        this.cameraCount = Camera.getNumberOfCameras();
        while (true) {
            int id2 = id;
            if (id2 < this.cameraCount) {
                CameraInfo cameraInfo = new CameraInfo();
                Camera.getCameraInfo(id2, cameraInfo);
                if (cameraInfo.facing == 0) {
                    this.backCameraInformation = new CameraInformation(id2, hasCameraFlash(id2));
                    this.currentCameraInformation = this.backCameraInformation;
                }
                if (cameraInfo.facing == 1) {
                    this.frontCameraInformation = new CameraInformation(id2, hasCameraFlash(id2));
                    this.currentCameraInformation = this.frontCameraInformation;
                }
                id = id2 + 1;
            } else {
                this.defaultCameraInformation = this.currentCameraInformation;
                createTexture();
                return;
            }
        }
    }

    public boolean hasBackCamera() {
        if (this.backCameraInformation == null) {
            return false;
        }
        return true;
    }

    public boolean hasFrontCamera() {
        if (this.frontCameraInformation == null) {
            return false;
        }
        return true;
    }

    private boolean hasCameraFlash(int cameraId) {
        try {
            Camera camera = Camera.open(cameraId);
            if (camera == null) {
                return false;
            }
            Parameters parameters = camera.getParameters();
            if (parameters.getFlashMode() == null) {
                camera.release();
                return false;
            }
            List<String> supportedFlashModes = parameters.getSupportedFlashModes();
            if (!(supportedFlashModes == null || supportedFlashModes.isEmpty())) {
                if (supportedFlashModes.size() != 1 || !((String) supportedFlashModes.get(0)).equals("off")) {
                    camera.release();
                    return true;
                }
            }
            camera.release();
            return false;
        } catch (Exception exception) {
            Log.e(TAG, "failed checking for flash", exception);
            return false;
        }
    }

    public boolean isCurrentCameraFacingBack() {
        return this.currentCameraInformation == this.backCameraInformation;
    }

    public boolean isCurrentCameraFacingFront() {
        return this.currentCameraInformation == this.frontCameraInformation;
    }

    public boolean isCameraActive() {
        return this.state == CameraState.previewRunning;
    }

    public void setToDefaultCamera() {
        updateCamera(this.defaultCameraInformation);
    }

    public boolean setToBackCamera() {
        if (!hasBackCamera()) {
            return false;
        }
        updateCamera(this.backCameraInformation);
        return true;
    }

    public boolean setToFrontCamera() {
        if (!hasFrontCamera()) {
            return false;
        }
        updateCamera(this.frontCameraInformation);
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateCamera(org.catrobat.catroid.camera.CameraManager.CameraInformation r5) {
        /*
        r4 = this;
        r0 = r4.cameraChangeLock;
        monitor-enter(r0);
        r1 = r4.state;	 Catch:{ all -> 0x0048 }
        r2 = r4.currentCameraInformation;	 Catch:{ all -> 0x0048 }
        if (r5 != r2) goto L_0x000b;
    L_0x0009:
        monitor-exit(r0);	 Catch:{ all -> 0x0048 }
        return;
    L_0x000b:
        r4.currentCameraInformation = r5;	 Catch:{ all -> 0x0048 }
        r2 = org.catrobat.catroid.utils.FlashUtil.isOn();	 Catch:{ all -> 0x0048 }
        if (r2 == 0) goto L_0x0029;
    L_0x0013:
        r2 = r4.currentCameraInformation;	 Catch:{ all -> 0x0048 }
        r2 = r2.flashAvailable;	 Catch:{ all -> 0x0048 }
        if (r2 != 0) goto L_0x0029;
    L_0x0019:
        r2 = TAG;	 Catch:{ all -> 0x0048 }
        r3 = "destroy Stage because flash isOn while changing camera";
        android.util.Log.w(r2, r3);	 Catch:{ all -> 0x0048 }
        r2 = getInstance();	 Catch:{ all -> 0x0048 }
        r2.destroyStage();	 Catch:{ all -> 0x0048 }
        monitor-exit(r0);	 Catch:{ all -> 0x0048 }
        return;
    L_0x0029:
        org.catrobat.catroid.utils.FlashUtil.pauseFlash();	 Catch:{ all -> 0x0048 }
        org.catrobat.catroid.facedetection.FaceDetectionHandler.pauseFaceDetection();	 Catch:{ all -> 0x0048 }
        r4.releaseCamera();	 Catch:{ all -> 0x0048 }
        r4.startCamera();	 Catch:{ all -> 0x0048 }
        org.catrobat.catroid.facedetection.FaceDetectionHandler.resumeFaceDetection();	 Catch:{ all -> 0x0048 }
        org.catrobat.catroid.utils.FlashUtil.resumeFlash();	 Catch:{ all -> 0x0048 }
        r2 = org.catrobat.catroid.camera.CameraManager.CameraState.prepare;	 Catch:{ all -> 0x0048 }
        if (r1 == r2) goto L_0x0043;
    L_0x003f:
        r2 = org.catrobat.catroid.camera.CameraManager.CameraState.previewRunning;	 Catch:{ all -> 0x0048 }
        if (r1 != r2) goto L_0x0046;
    L_0x0043:
        r4.changeCameraAsync();	 Catch:{ all -> 0x0048 }
    L_0x0046:
        monitor-exit(r0);	 Catch:{ all -> 0x0048 }
        return;
    L_0x0048:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0048 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.camera.CameraManager.updateCamera(org.catrobat.catroid.camera.CameraManager$CameraInformation):void");
    }

    public boolean startCamera() {
        synchronized (this.cameraBaseLock) {
            if (this.currentCamera != null || createCamera()) {
                Parameters cameraParameters = this.currentCamera.getParameters();
                this.previewFormat = cameraParameters.getPreviewFormat();
                this.previewWidth = cameraParameters.getPreviewSize().width;
                this.previewHeight = cameraParameters.getPreviewSize().height;
                try {
                    this.currentCamera.startPreview();
                    return true;
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    return false;
                }
            }
            return false;
        }
    }

    public void releaseCamera() {
        synchronized (this.cameraBaseLock) {
            if (this.currentCamera == null) {
                return;
            }
            this.currentCamera.setPreviewCallback(null);
            this.currentCamera.stopPreview();
            this.currentCamera.release();
            this.currentCamera = null;
        }
    }

    private boolean createCamera() {
        if (this.currentCamera != null) {
            return false;
        }
        try {
            this.currentCamera = Camera.open(this.currentCameraInformation.cameraId);
            if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
                this.currentCamera.setDisplayOrientation(0);
            } else {
                this.currentCamera.setDisplayOrientation(90);
            }
            Parameters cameraParameters = this.currentCamera.getParameters();
            List<Size> previewSizes = cameraParameters.getSupportedPreviewSizes();
            int previewWidth = 0;
            int previewHeight = 0;
            int i = 0;
            while (i < previewSizes.size() && ((Size) previewSizes.get(i)).height <= ScreenValues.SCREEN_HEIGHT) {
                if (((Size) previewSizes.get(i)).height > previewHeight) {
                    previewHeight = ((Size) previewSizes.get(i)).height;
                    previewWidth = ((Size) previewSizes.get(i)).width;
                }
                i++;
            }
            cameraParameters.setPreviewSize(previewWidth, previewHeight);
            this.currentCamera.setParameters(cameraParameters);
            this.currentCamera.setPreviewCallbackWithBuffer(this);
            if (this.texture != null) {
                try {
                    setTexture();
                } catch (IOException ioException) {
                    Log.e(TAG, "Setting preview texture failed!", ioException);
                    return false;
                }
            }
            return true;
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, "Creating camera caused an exception", runtimeException);
            return false;
        }
    }

    public boolean hasCurrentCameraFlash() {
        return this.currentCameraInformation.flashAvailable;
    }

    public CameraState getState() {
        return this.state;
    }

    public Camera getCurrentCamera() {
        return this.currentCamera;
    }

    public void addOnJpgPreviewFrameCallback(JpgPreviewCallback callback) {
        if (!this.callbacks.contains(callback)) {
            this.callbacks.add(callback);
        }
    }

    public void removeOnJpgPreviewFrameCallback(JpgPreviewCallback callback) {
        this.callbacks.remove(callback);
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        if (this.callbacks.size() != 0) {
            byte[] jpgData = getDecodeableBytesFromCameraFrame(data);
            for (JpgPreviewCallback callback : this.callbacks) {
                callback.onFrame(jpgData);
            }
        }
    }

    private byte[] getDecodeableBytesFromCameraFrame(byte[] cameraData) {
        YuvImage image = new YuvImage(cameraData, this.previewFormat, this.previewWidth, this.previewHeight, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compressToJpeg(new Rect(0, 0, this.previewWidth, this.previewHeight), 50, out);
        return out.toByteArray();
    }

    private void createTexture() {
        this.texture = new SurfaceTexture(1);
    }

    private void setTexture() throws IOException {
        this.currentCamera.setPreviewTexture(this.texture);
    }

    public void setFlashParams(Parameters flash) {
        Log.d(TAG, flash.toString());
        if (this.currentCamera != null && flash != null) {
            Parameters current = this.currentCamera.getParameters();
            current.setFlashMode(flash.getFlashMode());
            this.currentCamera.setParameters(current);
        }
    }

    public void prepareCamera() {
        this.state = CameraState.previewRunning;
        if (this.cameraSurface == null) {
            this.cameraSurface = new CameraSurface(this.stageActivity);
        }
        LayoutParams params = new LayoutParams(-2, -2);
        ViewGroup parent = (ViewGroup) this.cameraSurface.getParent();
        if (parent != null) {
            parent.removeView(this.cameraSurface);
        }
        this.stageActivity.addContentView(this.cameraSurface, params);
        startCamera();
    }

    public void stopPreview() {
        this.state = CameraState.notUsed;
        if (this.cameraSurface != null) {
            ViewParent parentView = this.cameraSurface.getParent();
            if (parentView instanceof ViewGroup) {
                ((ViewGroup) parentView).removeView(this.cameraSurface);
            }
            this.cameraSurface = null;
            try {
                if (this.currentCamera != null) {
                    this.currentCamera.stopPreview();
                    setTexture();
                }
                if (FaceDetectionHandler.isFaceDetectionRunning() || FlashUtil.isAvailable()) {
                    this.currentCamera.startPreview();
                }
            } catch (IOException e) {
                Log.e(TAG, "reset Texture failed at stopPreview");
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public void pausePreview() {
        if (this.state == CameraState.previewRunning) {
            this.wasRunning = true;
        }
        stopPreview();
        this.state = CameraState.previewPaused;
    }

    public void resumePreview() {
        prepareCamera();
        this.wasRunning = false;
    }

    public void pauseForScene() {
        this.stageActivity.post(new C17471());
        this.wasRunning = false;
        this.state = CameraState.notUsed;
    }

    public void resumeForScene() {
        this.stageActivity.post(new C17482());
    }

    public void prepareCameraAsync() {
        this.stageActivity.post(new C17493());
    }

    public void stopPreviewAsync() {
        this.stageActivity.post(new C17504());
    }

    public void pausePreviewAsync() {
        if (!(this.state == CameraState.previewPaused || this.state == CameraState.stopped)) {
            if (this.state != CameraState.notUsed) {
                this.stageActivity.post(new C17515());
            }
        }
    }

    public void resumePreviewAsync() {
        if (this.state == CameraState.previewPaused) {
            if (this.wasRunning) {
                this.stageActivity.post(new C17526());
            }
        }
    }

    public boolean isReady() {
        if (this.currentCamera != null) {
            return true;
        }
        return false;
    }

    public void updatePreview(CameraState newState) {
        synchronized (this.cameraChangeLock) {
            if (this.state == CameraState.previewRunning && newState != CameraState.prepare) {
                stopPreviewAsync();
            } else if (this.state == CameraState.notUsed && newState != CameraState.stopped) {
                prepareCameraAsync();
            }
        }
    }

    public void changeCameraAsync() {
        this.stageActivity.post(new C17537());
    }

    public void changeCamera() {
        stopPreview();
        prepareCamera();
    }

    public void setStageActivity(StageActivity stageActivity) {
        this.stageActivity = stageActivity;
    }

    public void destroyStage() {
        if (this.stageActivity != null) {
            this.stageActivity.destroy();
        }
    }

    public boolean switchToCameraWithFlash() {
        if (hasCurrentCameraFlash()) {
            return true;
        }
        if (this.frontCameraInformation.flashAvailable) {
            updateCamera(this.frontCameraInformation);
            return true;
        } else if (!this.backCameraInformation.flashAvailable) {
            return false;
        } else {
            updateCamera(this.backCameraInformation);
            return true;
        }
    }
}
