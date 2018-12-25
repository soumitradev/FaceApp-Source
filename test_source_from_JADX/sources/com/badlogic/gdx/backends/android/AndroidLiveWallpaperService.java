package com.badlogic.gdx.backends.android;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService.Engine;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.WindowManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxNativesLoader;
import name.antonsmirnov.firmata.FormatHelper;

public abstract class AndroidLiveWallpaperService extends WallpaperService {
    static boolean DEBUG = false;
    static final String TAG = "WallpaperService";
    protected volatile AndroidLiveWallpaper app = null;
    protected int engines = 0;
    protected volatile boolean isPreviewNotified = false;
    protected volatile AndroidWallpaperEngine linkedEngine = null;
    protected volatile boolean notifiedPreviewState = false;
    volatile int[] sync = new int[0];
    protected Callback view = null;
    protected int viewFormat;
    protected int viewHeight;
    protected int viewWidth;
    protected int visibleEngines = 0;

    public class AndroidWallpaperEngine extends Engine {
        protected int engineFormat;
        protected int engineHeight;
        protected boolean engineIsVisible = false;
        protected int engineWidth;
        boolean offsetsConsumed = true;
        float xOffset = 0.0f;
        float xOffsetStep = 0.0f;
        int xPixelOffset = 0;
        float yOffset = 0.0f;
        float yOffsetStep = 0.0f;
        int yPixelOffset = 0;

        /* renamed from: com.badlogic.gdx.backends.android.AndroidLiveWallpaperService$AndroidWallpaperEngine$1 */
        class C03331 implements Runnable {
            C03331() {
            }

            public void run() {
                synchronized (AndroidLiveWallpaperService.this.sync) {
                    boolean isCurrent = AndroidLiveWallpaperService.this.linkedEngine == AndroidWallpaperEngine.this;
                }
                if (isCurrent) {
                    ((AndroidWallpaperListener) AndroidLiveWallpaperService.this.app.listener).offsetChange(AndroidWallpaperEngine.this.xOffset, AndroidWallpaperEngine.this.yOffset, AndroidWallpaperEngine.this.xOffsetStep, AndroidWallpaperEngine.this.yOffsetStep, AndroidWallpaperEngine.this.xPixelOffset, AndroidWallpaperEngine.this.yPixelOffset);
                }
            }
        }

        public AndroidWallpaperEngine() {
            super(AndroidLiveWallpaperService.this);
            if (AndroidLiveWallpaperService.DEBUG != null) {
                AndroidLiveWallpaperService androidLiveWallpaperService = AndroidLiveWallpaperService.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" > AndroidWallpaperEngine() ");
                stringBuilder.append(hashCode());
                Log.d(androidLiveWallpaperService, stringBuilder.toString());
            }
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            if (AndroidLiveWallpaperService.DEBUG) {
                String str = AndroidLiveWallpaperService.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" > AndroidWallpaperEngine - onCreate() ");
                stringBuilder.append(hashCode());
                stringBuilder.append(" running: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.engines);
                stringBuilder.append(", linked: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.linkedEngine == this);
                stringBuilder.append(", thread: ");
                stringBuilder.append(Thread.currentThread().toString());
                Log.d(str, stringBuilder.toString());
            }
            super.onCreate(surfaceHolder);
        }

        public void onSurfaceCreated(SurfaceHolder holder) {
            AndroidLiveWallpaperService androidLiveWallpaperService = AndroidLiveWallpaperService.this;
            androidLiveWallpaperService.engines++;
            AndroidLiveWallpaperService.this.setLinkedEngine(this);
            if (AndroidLiveWallpaperService.DEBUG) {
                String str = AndroidLiveWallpaperService.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" > AndroidWallpaperEngine - onSurfaceCreated() ");
                stringBuilder.append(hashCode());
                stringBuilder.append(", running: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.engines);
                stringBuilder.append(", linked: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.linkedEngine == this);
                Log.d(str, stringBuilder.toString());
            }
            Log.i(AndroidLiveWallpaperService.TAG, "engine surface created");
            super.onSurfaceCreated(holder);
            if (AndroidLiveWallpaperService.this.engines == 1) {
                AndroidLiveWallpaperService.this.visibleEngines = 0;
            }
            if (AndroidLiveWallpaperService.this.engines == 1 && AndroidLiveWallpaperService.this.app == null) {
                AndroidLiveWallpaperService.this.viewFormat = 0;
                AndroidLiveWallpaperService.this.viewWidth = 0;
                AndroidLiveWallpaperService.this.viewHeight = 0;
                AndroidLiveWallpaperService.this.app = new AndroidLiveWallpaper(AndroidLiveWallpaperService.this);
                AndroidLiveWallpaperService.this.onCreateApplication();
                if (AndroidLiveWallpaperService.this.app.graphics == null) {
                    throw new Error("You must override 'AndroidLiveWallpaperService.onCreateApplication' method and call 'initialize' from its body.");
                }
            }
            AndroidLiveWallpaperService.this.view = (Callback) AndroidLiveWallpaperService.this.app.graphics.view;
            getSurfaceHolder().removeCallback(AndroidLiveWallpaperService.this.view);
            this.engineFormat = AndroidLiveWallpaperService.this.viewFormat;
            this.engineWidth = AndroidLiveWallpaperService.this.viewWidth;
            this.engineHeight = AndroidLiveWallpaperService.this.viewHeight;
            if (AndroidLiveWallpaperService.this.engines == 1) {
                AndroidLiveWallpaperService.this.view.surfaceCreated(holder);
            } else {
                AndroidLiveWallpaperService.this.view.surfaceDestroyed(holder);
                notifySurfaceChanged(this.engineFormat, this.engineWidth, this.engineHeight, false);
                AndroidLiveWallpaperService.this.view.surfaceCreated(holder);
            }
            notifyPreviewState();
            notifyOffsetsChanged();
            if (!Gdx.graphics.isContinuousRendering()) {
                Gdx.graphics.requestRendering();
            }
        }

        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (AndroidLiveWallpaperService.DEBUG) {
                String str = AndroidLiveWallpaperService.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" > AndroidWallpaperEngine - onSurfaceChanged() isPreview: ");
                stringBuilder.append(isPreview());
                stringBuilder.append(", ");
                stringBuilder.append(hashCode());
                stringBuilder.append(", running: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.engines);
                stringBuilder.append(", linked: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.linkedEngine == this);
                stringBuilder.append(", sufcace valid: ");
                stringBuilder.append(getSurfaceHolder().getSurface().isValid());
                Log.d(str, stringBuilder.toString());
            }
            Log.i(AndroidLiveWallpaperService.TAG, "engine surface changed");
            super.onSurfaceChanged(holder, format, width, height);
            notifySurfaceChanged(format, width, height, true);
        }

        private void notifySurfaceChanged(int format, int width, int height, boolean forceUpdate) {
            if (forceUpdate || format != AndroidLiveWallpaperService.this.viewFormat || width != AndroidLiveWallpaperService.this.viewWidth || height != AndroidLiveWallpaperService.this.viewHeight) {
                this.engineFormat = format;
                this.engineWidth = width;
                this.engineHeight = height;
                if (AndroidLiveWallpaperService.this.linkedEngine == this) {
                    AndroidLiveWallpaperService.this.viewFormat = this.engineFormat;
                    AndroidLiveWallpaperService.this.viewWidth = this.engineWidth;
                    AndroidLiveWallpaperService.this.viewHeight = this.engineHeight;
                    AndroidLiveWallpaperService.this.view.surfaceChanged(getSurfaceHolder(), AndroidLiveWallpaperService.this.viewFormat, AndroidLiveWallpaperService.this.viewWidth, AndroidLiveWallpaperService.this.viewHeight);
                } else if (AndroidLiveWallpaperService.DEBUG) {
                    Log.d(AndroidLiveWallpaperService.TAG, " > engine is not active, skipping surfaceChanged event");
                }
            } else if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > surface is current, skipping surfaceChanged event");
            }
        }

        public void onVisibilityChanged(boolean visible) {
            boolean reportedVisible = isVisible();
            if (AndroidLiveWallpaperService.DEBUG) {
                String str = AndroidLiveWallpaperService.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" > AndroidWallpaperEngine - onVisibilityChanged(paramVisible: ");
                stringBuilder.append(visible);
                stringBuilder.append(" reportedVisible: ");
                stringBuilder.append(reportedVisible);
                stringBuilder.append(") ");
                stringBuilder.append(hashCode());
                stringBuilder.append(", sufcace valid: ");
                stringBuilder.append(getSurfaceHolder().getSurface().isValid());
                Log.d(str, stringBuilder.toString());
            }
            super.onVisibilityChanged(visible);
            if (reportedVisible || !visible) {
                notifyVisibilityChanged(visible);
                return;
            }
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > fake visibilityChanged event! Android WallpaperService likes do that!");
            }
        }

        private void notifyVisibilityChanged(boolean visible) {
            if (this.engineIsVisible != visible) {
                this.engineIsVisible = visible;
                if (this.engineIsVisible) {
                    onResume();
                } else {
                    onPause();
                }
            } else if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > visible state is current, skipping visibilityChanged event!");
            }
        }

        public void onResume() {
            AndroidLiveWallpaperService androidLiveWallpaperService = AndroidLiveWallpaperService.this;
            androidLiveWallpaperService.visibleEngines++;
            if (AndroidLiveWallpaperService.DEBUG) {
                String str = AndroidLiveWallpaperService.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" > AndroidWallpaperEngine - onResume() ");
                stringBuilder.append(hashCode());
                stringBuilder.append(", running: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.engines);
                stringBuilder.append(", linked: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.linkedEngine == this);
                stringBuilder.append(", visible: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.visibleEngines);
                Log.d(str, stringBuilder.toString());
            }
            Log.i(AndroidLiveWallpaperService.TAG, "engine resumed");
            if (AndroidLiveWallpaperService.this.linkedEngine != null) {
                if (AndroidLiveWallpaperService.this.linkedEngine != this) {
                    AndroidLiveWallpaperService.this.setLinkedEngine(this);
                    AndroidLiveWallpaperService.this.view.surfaceDestroyed(getSurfaceHolder());
                    notifySurfaceChanged(this.engineFormat, this.engineWidth, this.engineHeight, false);
                    AndroidLiveWallpaperService.this.view.surfaceCreated(getSurfaceHolder());
                } else {
                    notifySurfaceChanged(this.engineFormat, this.engineWidth, this.engineHeight, false);
                }
                if (AndroidLiveWallpaperService.this.visibleEngines == 1) {
                    AndroidLiveWallpaperService.this.app.onResume();
                }
                notifyPreviewState();
                notifyOffsetsChanged();
                if (!Gdx.graphics.isContinuousRendering()) {
                    Gdx.graphics.requestRendering();
                }
            }
        }

        public void onPause() {
            AndroidLiveWallpaperService androidLiveWallpaperService = AndroidLiveWallpaperService.this;
            androidLiveWallpaperService.visibleEngines--;
            if (AndroidLiveWallpaperService.DEBUG) {
                String str = AndroidLiveWallpaperService.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" > AndroidWallpaperEngine - onPause() ");
                stringBuilder.append(hashCode());
                stringBuilder.append(", running: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.engines);
                stringBuilder.append(", linked: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.linkedEngine == this);
                stringBuilder.append(", visible: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.visibleEngines);
                Log.d(str, stringBuilder.toString());
            }
            Log.i(AndroidLiveWallpaperService.TAG, "engine paused");
            if (AndroidLiveWallpaperService.this.visibleEngines >= AndroidLiveWallpaperService.this.engines) {
                Log.e(AndroidLiveWallpaperService.TAG, "wallpaper lifecycle error, counted too many visible engines! repairing..");
                AndroidLiveWallpaperService.this.visibleEngines = Math.max(AndroidLiveWallpaperService.this.engines - 1, 0);
            }
            if (AndroidLiveWallpaperService.this.linkedEngine != null && AndroidLiveWallpaperService.this.visibleEngines == 0) {
                AndroidLiveWallpaperService.this.app.onPause();
            }
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine - onPause() done!");
            }
        }

        public void onSurfaceDestroyed(SurfaceHolder holder) {
            AndroidLiveWallpaperService androidLiveWallpaperService = AndroidLiveWallpaperService.this;
            boolean z = true;
            androidLiveWallpaperService.engines--;
            if (AndroidLiveWallpaperService.DEBUG) {
                String str = AndroidLiveWallpaperService.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" > AndroidWallpaperEngine - onSurfaceDestroyed() ");
                stringBuilder.append(hashCode());
                stringBuilder.append(", running: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.engines);
                stringBuilder.append(" ,linked: ");
                if (AndroidLiveWallpaperService.this.linkedEngine != this) {
                    z = false;
                }
                stringBuilder.append(z);
                stringBuilder.append(", isVisible: ");
                stringBuilder.append(this.engineIsVisible);
                Log.d(str, stringBuilder.toString());
            }
            Log.i(AndroidLiveWallpaperService.TAG, "engine surface destroyed");
            if (AndroidLiveWallpaperService.this.engines == 0) {
                AndroidLiveWallpaperService.this.onDeepPauseApplication();
            }
            if (AndroidLiveWallpaperService.this.linkedEngine == this && AndroidLiveWallpaperService.this.view != null) {
                AndroidLiveWallpaperService.this.view.surfaceDestroyed(holder);
            }
            this.engineFormat = 0;
            this.engineWidth = 0;
            this.engineHeight = 0;
            if (AndroidLiveWallpaperService.this.engines == 0) {
                AndroidLiveWallpaperService.this.linkedEngine = null;
            }
            super.onSurfaceDestroyed(holder);
        }

        public void onDestroy() {
            super.onDestroy();
        }

        public Bundle onCommand(String pAction, int pX, int pY, int pZ, Bundle pExtras, boolean pResultRequested) {
            if (AndroidLiveWallpaperService.DEBUG) {
                String str = AndroidLiveWallpaperService.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" > AndroidWallpaperEngine - onCommand(");
                stringBuilder.append(pAction);
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(pX);
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(pY);
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(pZ);
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(pExtras);
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(pResultRequested);
                stringBuilder.append(")");
                stringBuilder.append(", linked: ");
                stringBuilder.append(AndroidLiveWallpaperService.this.linkedEngine == this);
                Log.d(str, stringBuilder.toString());
            }
            return super.onCommand(pAction, pX, pY, pZ, pExtras, pResultRequested);
        }

        public void onTouchEvent(MotionEvent event) {
            if (AndroidLiveWallpaperService.this.linkedEngine == this) {
                AndroidLiveWallpaperService.this.app.input.onTouch(null, event);
            }
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            this.offsetsConsumed = false;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.xOffsetStep = xOffsetStep;
            this.yOffsetStep = yOffsetStep;
            this.xPixelOffset = xPixelOffset;
            this.yPixelOffset = yPixelOffset;
            notifyOffsetsChanged();
            if (!Gdx.graphics.isContinuousRendering()) {
                Gdx.graphics.requestRendering();
            }
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
        }

        protected void notifyOffsetsChanged() {
            if (AndroidLiveWallpaperService.this.linkedEngine == this && (AndroidLiveWallpaperService.this.app.listener instanceof AndroidWallpaperListener) && !this.offsetsConsumed) {
                this.offsetsConsumed = true;
                AndroidLiveWallpaperService.this.app.postRunnable(new C03331());
            }
        }

        protected void notifyPreviewState() {
            if (AndroidLiveWallpaperService.this.linkedEngine == this && (AndroidLiveWallpaperService.this.app.listener instanceof AndroidWallpaperListener)) {
                final boolean currentPreviewState = AndroidLiveWallpaperService.this.linkedEngine.isPreview();
                AndroidLiveWallpaperService.this.app.postRunnable(new Runnable() {
                    public void run() {
                        boolean shouldNotify = false;
                        synchronized (AndroidLiveWallpaperService.this.sync) {
                            if (!(AndroidLiveWallpaperService.this.isPreviewNotified && AndroidLiveWallpaperService.this.notifiedPreviewState == currentPreviewState)) {
                                AndroidLiveWallpaperService.this.notifiedPreviewState = currentPreviewState;
                                AndroidLiveWallpaperService.this.isPreviewNotified = true;
                                shouldNotify = true;
                            }
                        }
                        if (shouldNotify) {
                            AndroidLiveWallpaper currentApp = AndroidLiveWallpaperService.this.app;
                            if (currentApp != null) {
                                ((AndroidWallpaperListener) currentApp.listener).previewStateChange(currentPreviewState);
                            }
                        }
                    }
                });
            }
        }
    }

    static {
        GdxNativesLoader.load();
    }

    protected void setLinkedEngine(AndroidWallpaperEngine linkedEngine) {
        synchronized (this.sync) {
            this.linkedEngine = linkedEngine;
        }
    }

    public void onCreate() {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" > AndroidLiveWallpaperService - onCreate() ");
            stringBuilder.append(hashCode());
            Log.d(str, stringBuilder.toString());
        }
        Log.i(TAG, "service created");
        super.onCreate();
    }

    public Engine onCreateEngine() {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - onCreateEngine()");
        }
        Log.i(TAG, "engine created");
        return new AndroidWallpaperEngine();
    }

    public void onCreateApplication() {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - onCreateApplication()");
        }
    }

    public void initialize(ApplicationListener listener) {
        initialize(listener, new AndroidApplicationConfiguration());
    }

    public void initialize(ApplicationListener listener, AndroidApplicationConfiguration config) {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - initialize()");
        }
        this.app.initialize(listener, config);
        if (config.getTouchEventsForLiveWallpaper && Integer.parseInt(VERSION.SDK) >= 7) {
            this.linkedEngine.setTouchEventsEnabled(true);
        }
    }

    public SurfaceHolder getSurfaceHolder() {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - getSurfaceHolder()");
        }
        synchronized (this.sync) {
            if (this.linkedEngine == null) {
                return null;
            }
            SurfaceHolder surfaceHolder = this.linkedEngine.getSurfaceHolder();
            return surfaceHolder;
        }
    }

    public void onDeepPauseApplication() {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - onDeepPauseApplication()");
        }
        if (this.app != null) {
            this.app.graphics.clearManagedCaches();
        }
    }

    public void onDestroy() {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" > AndroidLiveWallpaperService - onDestroy() ");
            stringBuilder.append(hashCode());
            Log.d(str, stringBuilder.toString());
        }
        Log.i(TAG, "service destroyed");
        super.onDestroy();
        if (this.app != null) {
            this.app.onDestroy();
            this.app = null;
            this.view = null;
        }
    }

    protected void finalize() throws Throwable {
        Log.i(TAG, "service finalized");
        super.finalize();
    }

    public AndroidLiveWallpaper getLiveWallpaper() {
        return this.app;
    }

    public WindowManager getWindowManager() {
        return (WindowManager) getSystemService("window");
    }
}
