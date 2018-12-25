package org.catrobat.catroid.stage;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import org.catrobat.catroid.camera.CameraManager;

public class CameraSurface extends SurfaceView implements Callback {
    private static final String TAG = CameraSurface.class.getSimpleName();
    private Camera camera = null;

    public CameraSurface(Context context) {
        super(context);
        getHolder().addCallback(this);
        getHolder().setType(3);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.camera = CameraManager.getInstance().getCurrentCamera();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        synchronized (CameraManager.getInstance().cameraChangeLock) {
            try {
                if (this.camera != null) {
                    this.camera.stopPreview();
                    this.camera.setPreviewDisplay(holder);
                    this.camera.startPreview();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error at surfaceChanged");
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        getHolder().removeCallback(this);
    }
}
