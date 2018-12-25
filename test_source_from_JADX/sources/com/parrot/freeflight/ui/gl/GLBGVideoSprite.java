package com.parrot.freeflight.ui.gl;

import android.graphics.Bitmap;

public class GLBGVideoSprite {
    public int imageHeight;
    public int imageWidth;
    public int textureHeight;
    public int textureWidth;

    private native boolean getVideoFrameNative(Bitmap bitmap, float[] fArr);

    private native void onSurfaceChangedNative(int i, int i2);

    private native boolean onUpdateVideoTextureNative();

    public boolean onUpdateVideoTexture() {
        return onUpdateVideoTextureNative();
    }

    public void onSurfaceChanged(int width, int height) {
        onSurfaceChangedNative(width, height);
    }

    public boolean getVideoFrame(Bitmap bitmap, float[] ret) {
        return getVideoFrameNative(bitmap, ret);
    }
}
