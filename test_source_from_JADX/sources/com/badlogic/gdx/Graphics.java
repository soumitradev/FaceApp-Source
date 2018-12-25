package com.badlogic.gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;

public interface Graphics {
    Graphics$BufferFormat getBufferFormat();

    float getDeltaTime();

    float getDensity();

    Graphics$DisplayMode getDesktopDisplayMode();

    Graphics$DisplayMode[] getDisplayModes();

    long getFrameId();

    int getFramesPerSecond();

    GL20 getGL20();

    GL30 getGL30();

    int getHeight();

    float getPpcX();

    float getPpcY();

    float getPpiX();

    float getPpiY();

    float getRawDeltaTime();

    Graphics$GraphicsType getType();

    int getWidth();

    boolean isContinuousRendering();

    boolean isFullscreen();

    boolean isGL30Available();

    void requestRendering();

    void setContinuousRendering(boolean z);

    boolean setDisplayMode(int i, int i2, boolean z);

    boolean setDisplayMode(Graphics$DisplayMode graphics$DisplayMode);

    void setTitle(String str);

    void setVSync(boolean z);

    boolean supportsDisplayModeChange();

    boolean supportsExtension(String str);
}
