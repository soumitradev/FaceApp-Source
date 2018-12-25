package org.catrobat.catroid.stage;

public interface DeviceCameraControl {
    void changeCamera();

    void changeCameraAsync();

    boolean isReady();

    void pausePreview();

    void pausePreviewAsync();

    void prepareCamera();

    void prepareCameraAsync();

    void resumePreview();

    void resumePreviewAsync();

    void stopPreview();

    void stopPreviewAsync();
}
