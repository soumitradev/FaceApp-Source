package com.badlogic.gdx.backends.android;

import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy;

public class AndroidApplicationConfiguration {
    /* renamed from: a */
    public int f55a = 0;
    /* renamed from: b */
    public int f56b = 5;
    public int depth = 16;
    public boolean disableAudio = false;
    /* renamed from: g */
    public int f57g = 6;
    public boolean getTouchEventsForLiveWallpaper = false;
    public boolean hideStatusBar = false;
    public int maxSimultaneousSounds = 16;
    public int numSamples = 0;
    /* renamed from: r */
    public int f58r = 5;
    public ResolutionStrategy resolutionStrategy = new FillResolutionStrategy();
    public int stencil = 0;
    public int touchSleepTime = 0;
    public boolean useAccelerometer = true;
    public boolean useCompass = true;
    public boolean useGLSurfaceView20API18 = false;
    public boolean useImmersiveMode = false;
    public boolean useWakelock = false;
}
