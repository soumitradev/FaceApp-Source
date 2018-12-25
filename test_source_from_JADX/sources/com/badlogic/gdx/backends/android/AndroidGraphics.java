package com.badlogic.gdx.backends.android;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build.VERSION;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics$BufferFormat;
import com.badlogic.gdx.Graphics$DisplayMode;
import com.badlogic.gdx.Graphics$GraphicsType;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20API18;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18;
import com.badlogic.gdx.backends.android.surfaceview.GdxEglConfigChooser;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.WindowedMean;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Iterator;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

public class AndroidGraphics implements Graphics, Renderer {
    private static final String LOG_TAG = "AndroidGraphics";
    static volatile boolean enforceContinuousRendering = false;
    AndroidApplicationBase app;
    private Graphics$BufferFormat bufferFormat;
    protected final AndroidApplicationConfiguration config;
    volatile boolean created;
    protected float deltaTime;
    private float density;
    volatile boolean destroy;
    EGLContext eglContext;
    String extensions;
    protected int fps;
    protected long frameId;
    protected long frameStart;
    protected int frames;
    GL20 gl20;
    GL30 gl30;
    int height;
    private boolean isContinuous;
    protected long lastFrameTime;
    protected WindowedMean mean;
    volatile boolean pause;
    private float ppcX;
    private float ppcY;
    private float ppiX;
    private float ppiY;
    volatile boolean resume;
    volatile boolean running;
    Object synch;
    int[] value;
    final View view;
    int width;

    private class AndroidDisplayMode extends Graphics$DisplayMode {
        protected AndroidDisplayMode(int width, int height, int refreshRate, int bitsPerPixel) {
            super(width, height, refreshRate, bitsPerPixel);
        }
    }

    public AndroidGraphics(AndroidApplicationBase application, AndroidApplicationConfiguration config, ResolutionStrategy resolutionStrategy) {
        this(application, config, resolutionStrategy, true);
    }

    public AndroidGraphics(AndroidApplicationBase application, AndroidApplicationConfiguration config, ResolutionStrategy resolutionStrategy, boolean focusableView) {
        this.lastFrameTime = System.nanoTime();
        this.deltaTime = 0.0f;
        this.frameStart = System.nanoTime();
        this.frameId = -1;
        this.frames = 0;
        this.mean = new WindowedMean(5);
        this.created = false;
        this.running = false;
        this.pause = false;
        this.resume = false;
        this.destroy = false;
        this.ppiX = 0.0f;
        this.ppiY = 0.0f;
        this.ppcX = 0.0f;
        this.ppcY = 0.0f;
        this.density = 1.0f;
        this.bufferFormat = new Graphics$BufferFormat(5, 6, 5, 0, 16, 0, 0, false);
        this.isContinuous = true;
        this.value = new int[1];
        this.synch = new Object();
        this.config = config;
        this.app = application;
        this.view = createGLSurfaceView(application, resolutionStrategy);
        preserveEGLContextOnPause();
        if (focusableView) {
            this.view.setFocusable(true);
            this.view.setFocusableInTouchMode(true);
        }
    }

    protected void preserveEGLContextOnPause() {
        if ((VERSION.SDK_INT >= 11 && (this.view instanceof GLSurfaceView20)) || (this.view instanceof GLSurfaceView20API18)) {
            try {
                this.view.getClass().getMethod("setPreserveEGLContextOnPause", new Class[]{Boolean.TYPE}).invoke(this.view, new Object[]{Boolean.valueOf(true)});
            } catch (Exception e) {
                Gdx.app.log(LOG_TAG, "Method GLSurfaceView.setPreserveEGLContextOnPause not found");
            }
        }
    }

    protected View createGLSurfaceView(AndroidApplicationBase application, ResolutionStrategy resolutionStrategy) {
        if (checkGL20()) {
            EGLConfigChooser configChooser = getEglConfigChooser();
            if (VERSION.SDK_INT > 10 || !this.config.useGLSurfaceView20API18) {
                GLSurfaceView20 view = new GLSurfaceView20(application.getContext(), resolutionStrategy);
                if (configChooser != null) {
                    view.setEGLConfigChooser(configChooser);
                } else {
                    view.setEGLConfigChooser(this.config.f58r, this.config.f57g, this.config.f56b, this.config.f55a, this.config.depth, this.config.stencil);
                }
                view.setRenderer(this);
                return view;
            }
            GLSurfaceView20API18 view2 = new GLSurfaceView20API18(application.getContext(), resolutionStrategy);
            if (configChooser != null) {
                view2.setEGLConfigChooser(configChooser);
            } else {
                view2.setEGLConfigChooser(this.config.f58r, this.config.f57g, this.config.f56b, this.config.f55a, this.config.depth, this.config.stencil);
            }
            view2.setRenderer(this);
            return view2;
        }
        throw new GdxRuntimeException("Libgdx requires OpenGL ES 2.0");
    }

    public void onPauseGLSurfaceView() {
        if (this.view != null) {
            if (this.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.view).onPause();
            }
            if (this.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.view).onPause();
            }
        }
    }

    public void onResumeGLSurfaceView() {
        if (this.view != null) {
            if (this.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.view).onResume();
            }
            if (this.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.view).onResume();
            }
        }
    }

    protected EGLConfigChooser getEglConfigChooser() {
        return new GdxEglConfigChooser(this.config.f58r, this.config.f57g, this.config.f56b, this.config.f55a, this.config.depth, this.config.stencil, this.config.numSamples);
    }

    private void updatePpi() {
        DisplayMetrics metrics = new DisplayMetrics();
        this.app.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.ppiX = metrics.xdpi;
        this.ppiY = metrics.ydpi;
        this.ppcX = metrics.xdpi / 2.54f;
        this.ppcY = metrics.ydpi / 2.54f;
        this.density = metrics.density;
    }

    protected boolean checkGL20() {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl.eglInitialize(display, new int[2]);
        int[] num_config = new int[1];
        egl.eglChooseConfig(display, new int[]{12324, 4, 12323, 4, 12322, 4, 12352, 4, 12344}, new EGLConfig[10], 10, num_config);
        egl.eglTerminate(display);
        if (num_config[0] > 0) {
            return true;
        }
        return false;
    }

    public GL20 getGL20() {
        return this.gl20;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    private void setupGL(GL10 gl) {
        if (this.gl20 == null) {
            this.gl20 = new AndroidGL20();
            Gdx.gl = this.gl20;
            Gdx.gl20 = this.gl20;
            Application application = Gdx.app;
            String str = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("OGL renderer: ");
            stringBuilder.append(gl.glGetString(GL20.GL_RENDERER));
            application.log(str, stringBuilder.toString());
            application = Gdx.app;
            str = LOG_TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("OGL vendor: ");
            stringBuilder.append(gl.glGetString(GL20.GL_VENDOR));
            application.log(str, stringBuilder.toString());
            application = Gdx.app;
            str = LOG_TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("OGL version: ");
            stringBuilder.append(gl.glGetString(GL20.GL_VERSION));
            application.log(str, stringBuilder.toString());
            application = Gdx.app;
            str = LOG_TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("OGL extensions: ");
            stringBuilder.append(gl.glGetString(GL20.GL_EXTENSIONS));
            application.log(str, stringBuilder.toString());
        }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        updatePpi();
        gl.glViewport(0, 0, this.width, this.height);
        if (!this.created) {
            this.app.getApplicationListener().create();
            this.created = true;
            synchronized (this) {
                this.running = true;
            }
        }
        this.app.getApplicationListener().resize(width, height);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.eglContext = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
        setupGL(gl);
        logConfig(config);
        updatePpi();
        Mesh.invalidateAllMeshes(this.app);
        Texture.invalidateAllTextures(this.app);
        Cubemap.invalidateAllCubemaps(this.app);
        ShaderProgram.invalidateAllShaderPrograms(this.app);
        GLFrameBuffer.invalidateAllFrameBuffers(this.app);
        logManagedCachesStatus();
        Display display = this.app.getWindowManager().getDefaultDisplay();
        this.width = display.getWidth();
        this.height = display.getHeight();
        this.mean = new WindowedMean(5);
        this.lastFrameTime = System.nanoTime();
        gl.glViewport(0, 0, this.width, this.height);
    }

    private void logConfig(EGLConfig config) {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGL10 egl10 = egl;
        EGLDisplay eglGetDisplay = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        EGLConfig eGLConfig = config;
        int r = getAttrib(egl10, eglGetDisplay, eGLConfig, 12324, 0);
        int g = getAttrib(egl10, eglGetDisplay, eGLConfig, 12323, 0);
        int b = getAttrib(egl10, eglGetDisplay, eGLConfig, 12322, 0);
        int attrib = getAttrib(egl10, eglGetDisplay, eGLConfig, 12321, 0);
        int d = getAttrib(egl10, eglGetDisplay, eGLConfig, 12325, 0);
        int s = getAttrib(egl10, eglGetDisplay, eGLConfig, 12326, 0);
        int samples = Math.max(getAttrib(egl10, eglGetDisplay, eGLConfig, 12337, 0), getAttrib(egl10, eglGetDisplay, eGLConfig, GdxEglConfigChooser.EGL_COVERAGE_SAMPLES_NV, 0));
        boolean coverageSample = getAttrib(egl10, eglGetDisplay, eGLConfig, GdxEglConfigChooser.EGL_COVERAGE_SAMPLES_NV, 0) != 0;
        Application application = Gdx.app;
        String str = LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("framebuffer: (");
        stringBuilder.append(r);
        stringBuilder.append(", ");
        stringBuilder.append(g);
        stringBuilder.append(", ");
        stringBuilder.append(b);
        stringBuilder.append(", ");
        stringBuilder.append(attrib);
        stringBuilder.append(")");
        application.log(str, stringBuilder.toString());
        application = Gdx.app;
        str = LOG_TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("depthbuffer: (");
        stringBuilder.append(d);
        stringBuilder.append(")");
        application.log(str, stringBuilder.toString());
        application = Gdx.app;
        str = LOG_TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("stencilbuffer: (");
        stringBuilder.append(s);
        stringBuilder.append(")");
        application.log(str, stringBuilder.toString());
        application = Gdx.app;
        str = LOG_TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("samples: (");
        stringBuilder.append(samples);
        stringBuilder.append(")");
        application.log(str, stringBuilder.toString());
        application = Gdx.app;
        str = LOG_TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("coverage sampling: (");
        stringBuilder.append(coverageSample);
        stringBuilder.append(")");
        application.log(str, stringBuilder.toString());
        int d2 = d;
        int a = attrib;
        this.bufferFormat = new Graphics$BufferFormat(r, g, b, attrib, d2, s, samples, coverageSample);
    }

    private int getAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attrib, int defValue) {
        if (egl.eglGetConfigAttrib(display, config, attrib, this.value)) {
            return this.value[0];
        }
        return defValue;
    }

    void resume() {
        synchronized (this.synch) {
            this.running = true;
            this.resume = true;
        }
    }

    void pause() {
        synchronized (this.synch) {
            if (this.running) {
                this.running = false;
                this.pause = true;
                while (this.pause) {
                    try {
                        this.synch.wait(4000);
                        if (this.pause) {
                            Gdx.app.error(LOG_TAG, "waiting for pause synchronization took too long; assuming deadlock and killing");
                            Process.killProcess(Process.myPid());
                        }
                    } catch (InterruptedException e) {
                        Gdx.app.log(LOG_TAG, "waiting for pause synchronization failed!");
                    }
                }
                return;
            }
        }
    }

    void destroy() {
        synchronized (this.synch) {
            this.running = false;
            this.destroy = true;
            while (this.destroy) {
                try {
                    this.synch.wait();
                } catch (InterruptedException e) {
                    Gdx.app.log(LOG_TAG, "waiting for destroy synchronization failed!");
                }
            }
        }
    }

    public void onDrawFrame(GL10 gl) {
        Array<LifecycleListener> listeners;
        Iterator i$;
        long time = System.nanoTime();
        this.deltaTime = ((float) (time - this.lastFrameTime)) / 1.0E9f;
        this.lastFrameTime = time;
        if (this.resume) {
            this.deltaTime = 0.0f;
        } else {
            this.mean.addValue(this.deltaTime);
        }
        synchronized (this.synch) {
            boolean lrunning = this.running;
            boolean lpause = this.pause;
            boolean ldestroy = this.destroy;
            boolean lresume = this.resume;
            if (this.resume) {
                this.resume = false;
            }
            if (this.pause) {
                this.pause = false;
                this.synch.notifyAll();
            }
            if (this.destroy) {
                this.destroy = false;
                this.synch.notifyAll();
            }
        }
        if (lresume) {
            listeners = this.app.getLifecycleListeners();
            synchronized (listeners) {
                i$ = listeners.iterator();
                while (i$.hasNext()) {
                    ((LifecycleListener) i$.next()).resume();
                }
            }
            this.app.getApplicationListener().resume();
            Gdx.app.log(LOG_TAG, "resumed");
        }
        if (lrunning) {
            synchronized (this.app.getRunnables()) {
                this.app.getExecutedRunnables().clear();
                this.app.getExecutedRunnables().addAll(this.app.getRunnables());
                this.app.getRunnables().clear();
            }
            for (int i = 0; i < this.app.getExecutedRunnables().size; i++) {
                try {
                    ((Runnable) this.app.getExecutedRunnables().get(i)).run();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            this.app.getInput().processEvents();
            this.frameId++;
            this.app.getApplicationListener().render();
        }
        if (lpause) {
            listeners = this.app.getLifecycleListeners();
            synchronized (listeners) {
                i$ = listeners.iterator();
                while (i$.hasNext()) {
                    ((LifecycleListener) i$.next()).pause();
                }
            }
            this.app.getApplicationListener().pause();
            Gdx.app.log(LOG_TAG, "paused");
        }
        if (ldestroy) {
            listeners = this.app.getLifecycleListeners();
            synchronized (listeners) {
                i$ = listeners.iterator();
                while (i$.hasNext()) {
                    ((LifecycleListener) i$.next()).dispose();
                }
            }
            this.app.getApplicationListener().dispose();
            Gdx.app.log(LOG_TAG, "destroyed");
        }
        if (time - this.frameStart > 1000000000) {
            this.fps = this.frames;
            this.frames = 0;
            this.frameStart = time;
        }
        this.frames++;
    }

    public long getFrameId() {
        return this.frameId;
    }

    public float getDeltaTime() {
        return this.mean.getMean() == 0.0f ? this.deltaTime : this.mean.getMean();
    }

    public float getRawDeltaTime() {
        return this.deltaTime;
    }

    public Graphics$GraphicsType getType() {
        return Graphics$GraphicsType.AndroidGL;
    }

    public int getFramesPerSecond() {
        return this.fps;
    }

    public void clearManagedCaches() {
        Mesh.clearAllMeshes(this.app);
        Texture.clearAllTextures(this.app);
        Cubemap.clearAllCubemaps(this.app);
        ShaderProgram.clearAllShaderPrograms(this.app);
        GLFrameBuffer.clearAllFrameBuffers(this.app);
        logManagedCachesStatus();
    }

    protected void logManagedCachesStatus() {
        Gdx.app.log(LOG_TAG, Mesh.getManagedStatus());
        Gdx.app.log(LOG_TAG, Texture.getManagedStatus());
        Gdx.app.log(LOG_TAG, Cubemap.getManagedStatus());
        Gdx.app.log(LOG_TAG, ShaderProgram.getManagedStatus());
        Gdx.app.log(LOG_TAG, GLFrameBuffer.getManagedStatus());
    }

    public View getView() {
        return this.view;
    }

    public float getPpiX() {
        return this.ppiX;
    }

    public float getPpiY() {
        return this.ppiY;
    }

    public float getPpcX() {
        return this.ppcX;
    }

    public float getPpcY() {
        return this.ppcY;
    }

    public float getDensity() {
        return this.density;
    }

    public boolean supportsDisplayModeChange() {
        return false;
    }

    public boolean setDisplayMode(Graphics$DisplayMode displayMode) {
        return false;
    }

    public Graphics$DisplayMode[] getDisplayModes() {
        return new Graphics$DisplayMode[]{getDesktopDisplayMode()};
    }

    public boolean setDisplayMode(int width, int height, boolean fullscreen) {
        return false;
    }

    public void setTitle(String title) {
    }

    public Graphics$DisplayMode getDesktopDisplayMode() {
        DisplayMetrics metrics = new DisplayMetrics();
        this.app.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new AndroidDisplayMode(metrics.widthPixels, metrics.heightPixels, 0, 0);
    }

    public Graphics$BufferFormat getBufferFormat() {
        return this.bufferFormat;
    }

    public void setVSync(boolean vsync) {
    }

    public boolean supportsExtension(String extension) {
        if (this.extensions == null) {
            this.extensions = Gdx.gl.glGetString(GL20.GL_EXTENSIONS);
        }
        return this.extensions.contains(extension);
    }

    public void setContinuousRendering(boolean isContinuous) {
        if (this.view != null) {
            boolean z;
            int renderMode;
            if (!enforceContinuousRendering) {
                if (!isContinuous) {
                    z = false;
                    this.isContinuous = z;
                    renderMode = this.isContinuous;
                    if (this.view instanceof GLSurfaceViewAPI18) {
                        ((GLSurfaceViewAPI18) this.view).setRenderMode(renderMode);
                    }
                    if (this.view instanceof GLSurfaceView) {
                        ((GLSurfaceView) this.view).setRenderMode(renderMode);
                    }
                    this.mean.clear();
                }
            }
            z = true;
            this.isContinuous = z;
            renderMode = this.isContinuous;
            if (this.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.view).setRenderMode(renderMode);
            }
            if (this.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.view).setRenderMode(renderMode);
            }
            this.mean.clear();
        }
    }

    public boolean isContinuousRendering() {
        return this.isContinuous;
    }

    public void requestRendering() {
        if (this.view != null) {
            if (this.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.view).requestRender();
            }
            if (this.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.view).requestRender();
            }
        }
    }

    public boolean isFullscreen() {
        return true;
    }

    public boolean isGL30Available() {
        return this.gl30 != null;
    }

    public GL30 getGL30() {
        return this.gl30;
    }
}
