package com.parrot.freeflight.ui.gl;

import android.graphics.Bitmap;

public class GLBGVideoSprite {

    public int imageWidth;
    public int imageHeight;

    public int textureWidth;
    public int textureHeight;
	
	public boolean onUpdateVideoTexture()
	{
		return onUpdateVideoTextureNative();
	}
	
	public void onSurfaceChanged(int width, int height )
	{
		onSurfaceChangedNative(width, height);
	}
	
	public boolean getVideoFrame(Bitmap bitmap, float[] ret)
	{
		return getVideoFrameNative(bitmap, ret);
	}
	
	private native boolean onUpdateVideoTextureNative();

	private native void onSurfaceChangedNative(int width, int height);
	
	private native boolean getVideoFrameNative(Bitmap bitmap, float[] ret);

}
