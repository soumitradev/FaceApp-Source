package com.badlogic.gdx.backends.android.surfaceview;

import android.content.Context;
import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.badlogic.gdx.graphics.GL20;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLSurfaceViewAPI18 extends SurfaceView implements Callback {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final String TAG = "GLSurfaceViewAPI18";
    private static final GLThreadManager sGLThreadManager = new GLThreadManager();
    private int mDebugFlags;
    private boolean mDetached;
    private EGLConfigChooser mEGLConfigChooser;
    private int mEGLContextClientVersion;
    private EGLContextFactory mEGLContextFactory;
    private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    private GLThread mGLThread;
    private GLWrapper mGLWrapper;
    private boolean mPreserveEGLContextOnPause;
    private Renderer mRenderer;
    private final WeakReference<GLSurfaceViewAPI18> mThisWeakRef = new WeakReference(this);

    private abstract class BaseConfigChooser implements EGLConfigChooser {
        protected int[] mConfigSpec;

        abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public BaseConfigChooser(int[] configSpec) {
            this.mConfigSpec = filterConfigSpec(configSpec);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] num_config = new int[1];
            if (egl.eglChooseConfig(display, this.mConfigSpec, null, 0, num_config)) {
                int numConfigs = num_config[0];
                if (numConfigs <= 0) {
                    throw new IllegalArgumentException("No configs match configSpec");
                }
                EGLConfig[] configs = new EGLConfig[numConfigs];
                if (egl.eglChooseConfig(display, this.mConfigSpec, configs, numConfigs, num_config)) {
                    EGLConfig config = chooseConfig(egl, display, configs);
                    if (config != null) {
                        return config;
                    }
                    throw new IllegalArgumentException("No config chosen");
                }
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            throw new IllegalArgumentException("eglChooseConfig failed");
        }

        private int[] filterConfigSpec(int[] configSpec) {
            if (GLSurfaceViewAPI18.this.mEGLContextClientVersion != 2) {
                return configSpec;
            }
            int len = configSpec.length;
            int[] newConfigSpec = new int[(len + 2)];
            System.arraycopy(configSpec, 0, newConfigSpec, 0, len - 1);
            newConfigSpec[len - 1] = 12352;
            newConfigSpec[len] = 4;
            newConfigSpec[len + 1] = 12344;
            return newConfigSpec;
        }
    }

    public interface EGLContextFactory {
        EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    public interface EGLWindowSurfaceFactory {
        EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj);

        void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
    }

    private static class EglHelper {
        EGL10 mEgl;
        EGLConfig mEglConfig;
        EGLContext mEglContext;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;
        private WeakReference<GLSurfaceViewAPI18> mGLSurfaceViewWeakRef;

        public EglHelper(WeakReference<GLSurfaceViewAPI18> glSurfaceViewWeakRef) {
            this.mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
        }

        public void start() {
            this.mEgl = (EGL10) EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.mEglDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
                GLSurfaceViewAPI18 view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                if (view == null) {
                    this.mEglConfig = null;
                    this.mEglContext = null;
                } else {
                    this.mEglConfig = view.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                    this.mEglContext = view.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
                }
                if (this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
                    this.mEglContext = null;
                    throwEglException("createContext");
                }
                this.mEglSurface = null;
                return;
            }
            throw new RuntimeException("eglInitialize failed");
        }

        public boolean createSurface() {
            if (this.mEgl == null) {
                throw new RuntimeException("egl not initialized");
            } else if (this.mEglDisplay == null) {
                throw new RuntimeException("eglDisplay not initialized");
            } else if (this.mEglConfig == null) {
                throw new RuntimeException("mEglConfig not initialized");
            } else {
                destroySurfaceImp();
                GLSurfaceViewAPI18 view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                if (view != null) {
                    this.mEglSurface = view.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, view.getHolder());
                } else {
                    this.mEglSurface = null;
                }
                if (this.mEglSurface != null) {
                    if (this.mEglSurface != EGL10.EGL_NO_SURFACE) {
                        if (this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                            return true;
                        }
                        logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
                        return false;
                    }
                }
                if (this.mEgl.eglGetError() == 12299) {
                    Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                }
                return false;
            }
        }

        GL createGL() {
            GL gl = this.mEglContext.getGL();
            GLSurfaceViewAPI18 view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
            if (view == null) {
                return gl;
            }
            if (view.mGLWrapper != null) {
                gl = view.mGLWrapper.wrap(gl);
            }
            if ((view.mDebugFlags & 3) == 0) {
                return gl;
            }
            int configFlags = 0;
            Writer log = null;
            if ((view.mDebugFlags & 1) != 0) {
                configFlags = 0 | 1;
            }
            if ((view.mDebugFlags & 2) != 0) {
                log = new LogWriter();
            }
            return GLDebugHelper.wrap(gl, configFlags, log);
        }

        public int swap() {
            if (this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
                return 12288;
            }
            return this.mEgl.eglGetError();
        }

        public void destroySurface() {
            destroySurfaceImp();
        }

        private void destroySurfaceImp() {
            if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                GLSurfaceViewAPI18 view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                if (view != null) {
                    view.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
                }
                this.mEglSurface = null;
            }
        }

        public void finish() {
            if (this.mEglContext != null) {
                GLSurfaceViewAPI18 view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                if (view != null) {
                    view.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            if (this.mEglDisplay != null) {
                this.mEgl.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = null;
            }
        }

        private void throwEglException(String function) {
            throwEglException(function, this.mEgl.eglGetError());
        }

        public static void throwEglException(String function, int error) {
            throw new RuntimeException(formatEglError(function, error));
        }

        public static void logEglErrorAsWarning(String tag, String function, int error) {
            Log.w(tag, formatEglError(function, error));
        }

        private static String getErrorString(int error) {
            switch (error) {
                case 12288:
                    return "EGL_SUCCESS";
                case 12289:
                    return "EGL_NOT_INITIALIZED";
                case 12290:
                    return "EGL_BAD_ACCESS";
                case 12291:
                    return "EGL_BAD_ALLOC";
                case 12292:
                    return "EGL_BAD_ATTRIBUTE";
                case 12293:
                    return "EGL_BAD_CONFIG";
                case 12294:
                    return "EGL_BAD_CONTEXT";
                case 12295:
                    return "EGL_BAD_CURRENT_SURFACE";
                case 12296:
                    return "EGL_BAD_DISPLAY";
                case 12297:
                    return "EGL_BAD_MATCH";
                case 12298:
                    return "EGL_BAD_NATIVE_PIXMAP";
                case 12299:
                    return "EGL_BAD_NATIVE_WINDOW";
                case 12300:
                    return "EGL_BAD_PARAMETER";
                case 12301:
                    return "EGL_BAD_SURFACE";
                case 12302:
                    return "EGL_CONTEXT_LOST";
                default:
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("0x");
                    stringBuilder.append(Integer.toHexString(error));
                    return stringBuilder.toString();
            }
        }

        public static String formatEglError(String function, int error) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(function);
            stringBuilder.append(" failed: ");
            stringBuilder.append(getErrorString(error));
            return stringBuilder.toString();
        }
    }

    static class GLThread extends Thread {
        private EglHelper mEglHelper;
        private ArrayList<Runnable> mEventQueue = new ArrayList();
        private boolean mExited;
        private boolean mFinishedCreatingEglSurface;
        private WeakReference<GLSurfaceViewAPI18> mGLSurfaceViewWeakRef;
        private boolean mHasSurface;
        private boolean mHaveEglContext;
        private boolean mHaveEglSurface;
        private int mHeight = 0;
        private boolean mPaused;
        private boolean mRenderComplete;
        private int mRenderMode = 1;
        private boolean mRequestPaused;
        private boolean mRequestRender = true;
        private boolean mShouldExit;
        private boolean mShouldReleaseEglContext;
        private boolean mSizeChanged = true;
        private boolean mSurfaceIsBad;
        private boolean mWaitingForSurface;
        private int mWidth = 0;

        GLThread(WeakReference<GLSurfaceViewAPI18> glSurfaceViewWeakRef) {
            this.mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
        }

        public void run() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("GLThread ");
            stringBuilder.append(getId());
            setName(stringBuilder.toString());
            try {
                guardedRun();
            } catch (InterruptedException e) {
            } catch (Throwable th) {
                GLSurfaceViewAPI18.sGLThreadManager.threadExiting(this);
            }
            GLSurfaceViewAPI18.sGLThreadManager.threadExiting(this);
        }

        private void stopEglSurfaceLocked() {
            if (this.mHaveEglSurface) {
                this.mHaveEglSurface = false;
                this.mEglHelper.destroySurface();
            }
        }

        private void stopEglContextLocked() {
            if (this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = false;
                GLSurfaceViewAPI18.sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        private void guardedRun() throws java.lang.InterruptedException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
            /*
            r22 = this;
            r1 = r22;
            r2 = new com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18$EglHelper;
            r3 = r1.mGLSurfaceViewWeakRef;
            r2.<init>(r3);
            r1.mEglHelper = r2;
            r2 = 0;
            r1.mHaveEglContext = r2;
            r1.mHaveEglSurface = r2;
            r3 = 0;
            r4 = 0;
            r5 = 0;
            r6 = 0;
            r7 = 0;
            r8 = 0;
            r9 = 0;
            r10 = 0;
            r11 = 0;
            r12 = 0;
            r13 = 0;
            r14 = 0;
        L_0x001c:
            r15 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x026c }
            monitor-enter(r15);	 Catch:{ all -> 0x026c }
        L_0x0021:
            r2 = r1.mShouldExit;	 Catch:{ all -> 0x0263 }
            if (r2 == 0) goto L_0x003d;
        L_0x0025:
            monitor-exit(r15);	 Catch:{ all -> 0x0037 }
            r2 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;
            monitor-enter(r2);
            r22.stopEglSurfaceLocked();	 Catch:{ all -> 0x0033 }
            r22.stopEglContextLocked();	 Catch:{ all -> 0x0033 }
            monitor-exit(r2);	 Catch:{ all -> 0x0033 }
            return;	 Catch:{ all -> 0x0033 }
        L_0x0033:
            r0 = move-exception;	 Catch:{ all -> 0x0033 }
            r15 = r0;	 Catch:{ all -> 0x0033 }
            monitor-exit(r2);	 Catch:{ all -> 0x0033 }
            throw r15;
        L_0x0037:
            r0 = move-exception;
            r2 = r0;
            r16 = r3;
            goto L_0x0267;
        L_0x003d:
            r2 = r1.mEventQueue;	 Catch:{ all -> 0x0263 }
            r2 = r2.isEmpty();	 Catch:{ all -> 0x0263 }
            r16 = r3;
            if (r2 != 0) goto L_0x0055;
        L_0x0047:
            r2 = r1.mEventQueue;	 Catch:{ all -> 0x0269 }
            r3 = 0;	 Catch:{ all -> 0x0269 }
            r2 = r2.remove(r3);	 Catch:{ all -> 0x0269 }
            r2 = (java.lang.Runnable) r2;	 Catch:{ all -> 0x0269 }
            r14 = r2;	 Catch:{ all -> 0x0269 }
            r2 = 0;	 Catch:{ all -> 0x0269 }
            goto L_0x0172;	 Catch:{ all -> 0x0269 }
        L_0x0055:
            r2 = 0;	 Catch:{ all -> 0x0269 }
            r3 = r1.mPaused;	 Catch:{ all -> 0x0269 }
            r17 = r2;	 Catch:{ all -> 0x0269 }
            r2 = r1.mRequestPaused;	 Catch:{ all -> 0x0269 }
            if (r3 == r2) goto L_0x006c;	 Catch:{ all -> 0x0269 }
        L_0x005e:
            r2 = r1.mRequestPaused;	 Catch:{ all -> 0x0269 }
            r3 = r1.mRequestPaused;	 Catch:{ all -> 0x0269 }
            r1.mPaused = r3;	 Catch:{ all -> 0x0269 }
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r3.notifyAll();	 Catch:{ all -> 0x0269 }
            goto L_0x006e;	 Catch:{ all -> 0x0269 }
        L_0x006c:
            r2 = r17;	 Catch:{ all -> 0x0269 }
        L_0x006e:
            r3 = r1.mShouldReleaseEglContext;	 Catch:{ all -> 0x0269 }
            if (r3 == 0) goto L_0x007c;	 Catch:{ all -> 0x0269 }
        L_0x0072:
            r22.stopEglSurfaceLocked();	 Catch:{ all -> 0x0269 }
            r22.stopEglContextLocked();	 Catch:{ all -> 0x0269 }
            r3 = 0;	 Catch:{ all -> 0x0269 }
            r1.mShouldReleaseEglContext = r3;	 Catch:{ all -> 0x0269 }
            r11 = 1;	 Catch:{ all -> 0x0269 }
        L_0x007c:
            if (r7 == 0) goto L_0x0086;	 Catch:{ all -> 0x0269 }
        L_0x007e:
            r22.stopEglSurfaceLocked();	 Catch:{ all -> 0x0269 }
            r22.stopEglContextLocked();	 Catch:{ all -> 0x0269 }
            r3 = 0;	 Catch:{ all -> 0x0269 }
            r7 = r3;	 Catch:{ all -> 0x0269 }
        L_0x0086:
            if (r2 == 0) goto L_0x008f;	 Catch:{ all -> 0x0269 }
        L_0x0088:
            r3 = r1.mHaveEglSurface;	 Catch:{ all -> 0x0269 }
            if (r3 == 0) goto L_0x008f;	 Catch:{ all -> 0x0269 }
        L_0x008c:
            r22.stopEglSurfaceLocked();	 Catch:{ all -> 0x0269 }
        L_0x008f:
            if (r2 == 0) goto L_0x00ba;	 Catch:{ all -> 0x0269 }
        L_0x0091:
            r3 = r1.mHaveEglContext;	 Catch:{ all -> 0x0269 }
            if (r3 == 0) goto L_0x00ba;	 Catch:{ all -> 0x0269 }
        L_0x0095:
            r3 = r1.mGLSurfaceViewWeakRef;	 Catch:{ all -> 0x0269 }
            r3 = r3.get();	 Catch:{ all -> 0x0269 }
            r3 = (com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18) r3;	 Catch:{ all -> 0x0269 }
            if (r3 != 0) goto L_0x00a2;	 Catch:{ all -> 0x0269 }
        L_0x009f:
            r17 = 0;	 Catch:{ all -> 0x0269 }
            goto L_0x00a6;	 Catch:{ all -> 0x0269 }
        L_0x00a2:
            r17 = r3.mPreserveEGLContextOnPause;	 Catch:{ all -> 0x0269 }
        L_0x00a6:
            if (r17 == 0) goto L_0x00b5;	 Catch:{ all -> 0x0269 }
        L_0x00a8:
            r18 = r3;	 Catch:{ all -> 0x0269 }
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r3 = r3.shouldReleaseEGLContextWhenPausing();	 Catch:{ all -> 0x0269 }
            if (r3 == 0) goto L_0x00ba;	 Catch:{ all -> 0x0269 }
        L_0x00b4:
            goto L_0x00b7;	 Catch:{ all -> 0x0269 }
        L_0x00b5:
            r18 = r3;	 Catch:{ all -> 0x0269 }
        L_0x00b7:
            r22.stopEglContextLocked();	 Catch:{ all -> 0x0269 }
        L_0x00ba:
            if (r2 == 0) goto L_0x00cb;	 Catch:{ all -> 0x0269 }
        L_0x00bc:
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r3 = r3.shouldTerminateEGLWhenPausing();	 Catch:{ all -> 0x0269 }
            if (r3 == 0) goto L_0x00cb;	 Catch:{ all -> 0x0269 }
        L_0x00c6:
            r3 = r1.mEglHelper;	 Catch:{ all -> 0x0269 }
            r3.finish();	 Catch:{ all -> 0x0269 }
        L_0x00cb:
            r3 = r1.mHasSurface;	 Catch:{ all -> 0x0269 }
            if (r3 != 0) goto L_0x00e7;	 Catch:{ all -> 0x0269 }
        L_0x00cf:
            r3 = r1.mWaitingForSurface;	 Catch:{ all -> 0x0269 }
            if (r3 != 0) goto L_0x00e7;	 Catch:{ all -> 0x0269 }
        L_0x00d3:
            r3 = r1.mHaveEglSurface;	 Catch:{ all -> 0x0269 }
            if (r3 == 0) goto L_0x00da;	 Catch:{ all -> 0x0269 }
        L_0x00d7:
            r22.stopEglSurfaceLocked();	 Catch:{ all -> 0x0269 }
        L_0x00da:
            r3 = 1;	 Catch:{ all -> 0x0269 }
            r1.mWaitingForSurface = r3;	 Catch:{ all -> 0x0269 }
            r3 = 0;	 Catch:{ all -> 0x0269 }
            r1.mSurfaceIsBad = r3;	 Catch:{ all -> 0x0269 }
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r3.notifyAll();	 Catch:{ all -> 0x0269 }
        L_0x00e7:
            r3 = r1.mHasSurface;	 Catch:{ all -> 0x0269 }
            if (r3 == 0) goto L_0x00f9;	 Catch:{ all -> 0x0269 }
        L_0x00eb:
            r3 = r1.mWaitingForSurface;	 Catch:{ all -> 0x0269 }
            if (r3 == 0) goto L_0x00f9;	 Catch:{ all -> 0x0269 }
        L_0x00ef:
            r3 = 0;	 Catch:{ all -> 0x0269 }
            r1.mWaitingForSurface = r3;	 Catch:{ all -> 0x0269 }
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r3.notifyAll();	 Catch:{ all -> 0x0269 }
        L_0x00f9:
            if (r10 == 0) goto L_0x0107;	 Catch:{ all -> 0x0269 }
        L_0x00fb:
            r9 = 0;	 Catch:{ all -> 0x0269 }
            r10 = 0;	 Catch:{ all -> 0x0269 }
            r3 = 1;	 Catch:{ all -> 0x0269 }
            r1.mRenderComplete = r3;	 Catch:{ all -> 0x0269 }
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r3.notifyAll();	 Catch:{ all -> 0x0269 }
        L_0x0107:
            r3 = r22.readyToDraw();	 Catch:{ all -> 0x0269 }
            if (r3 == 0) goto L_0x0254;	 Catch:{ all -> 0x0269 }
        L_0x010d:
            r3 = r1.mHaveEglContext;	 Catch:{ all -> 0x0269 }
            if (r3 != 0) goto L_0x0142;	 Catch:{ all -> 0x0269 }
        L_0x0111:
            if (r11 == 0) goto L_0x0118;	 Catch:{ all -> 0x0269 }
        L_0x0113:
            r3 = 0;	 Catch:{ all -> 0x0269 }
            r19 = r2;	 Catch:{ all -> 0x0269 }
            r11 = r3;	 Catch:{ all -> 0x0269 }
            goto L_0x0144;	 Catch:{ all -> 0x0269 }
        L_0x0118:
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r3 = r3.tryAcquireEglContextLocked(r1);	 Catch:{ all -> 0x0269 }
            if (r3 == 0) goto L_0x0142;
        L_0x0122:
            r3 = r1.mEglHelper;	 Catch:{ RuntimeException -> 0x0136 }
            r3.start();	 Catch:{ RuntimeException -> 0x0136 }
            r3 = 1;
            r1.mHaveEglContext = r3;	 Catch:{ all -> 0x0269 }
            r4 = 1;	 Catch:{ all -> 0x0269 }
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r3.notifyAll();	 Catch:{ all -> 0x0269 }
            r19 = r2;	 Catch:{ all -> 0x0269 }
            goto L_0x0144;	 Catch:{ all -> 0x0269 }
        L_0x0136:
            r0 = move-exception;	 Catch:{ all -> 0x0269 }
            r3 = r0;	 Catch:{ all -> 0x0269 }
            r19 = r2;	 Catch:{ all -> 0x0269 }
            r2 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r2.releaseEglContextLocked(r1);	 Catch:{ all -> 0x0269 }
            throw r3;	 Catch:{ all -> 0x0269 }
        L_0x0142:
            r19 = r2;	 Catch:{ all -> 0x0269 }
        L_0x0144:
            r2 = r1.mHaveEglContext;	 Catch:{ all -> 0x0269 }
            if (r2 == 0) goto L_0x0152;	 Catch:{ all -> 0x0269 }
        L_0x0148:
            r2 = r1.mHaveEglSurface;	 Catch:{ all -> 0x0269 }
            if (r2 != 0) goto L_0x0152;	 Catch:{ all -> 0x0269 }
        L_0x014c:
            r2 = 1;	 Catch:{ all -> 0x0269 }
            r1.mHaveEglSurface = r2;	 Catch:{ all -> 0x0269 }
            r5 = 1;	 Catch:{ all -> 0x0269 }
            r6 = 1;	 Catch:{ all -> 0x0269 }
            r8 = 1;	 Catch:{ all -> 0x0269 }
        L_0x0152:
            r2 = r1.mHaveEglSurface;	 Catch:{ all -> 0x0269 }
            if (r2 == 0) goto L_0x0256;	 Catch:{ all -> 0x0269 }
        L_0x0156:
            r2 = r1.mSizeChanged;	 Catch:{ all -> 0x0269 }
            if (r2 == 0) goto L_0x0167;	 Catch:{ all -> 0x0269 }
        L_0x015a:
            r8 = 1;	 Catch:{ all -> 0x0269 }
            r2 = r1.mWidth;	 Catch:{ all -> 0x0269 }
            r12 = r2;	 Catch:{ all -> 0x0269 }
            r2 = r1.mHeight;	 Catch:{ all -> 0x0269 }
            r13 = r2;	 Catch:{ all -> 0x0269 }
            r9 = 1;	 Catch:{ all -> 0x0269 }
            r5 = 1;	 Catch:{ all -> 0x0269 }
            r2 = 0;	 Catch:{ all -> 0x0269 }
            r1.mSizeChanged = r2;	 Catch:{ all -> 0x0269 }
            goto L_0x0168;	 Catch:{ all -> 0x0269 }
        L_0x0167:
            r2 = 0;	 Catch:{ all -> 0x0269 }
        L_0x0168:
            r1.mRequestRender = r2;	 Catch:{ all -> 0x0269 }
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r3.notifyAll();	 Catch:{ all -> 0x0269 }
        L_0x0172:
            monitor-exit(r15);	 Catch:{ all -> 0x024f }
            if (r14 == 0) goto L_0x017e;
        L_0x0175:
            r14.run();	 Catch:{ all -> 0x026c }
            r14 = 0;	 Catch:{ all -> 0x026c }
        L_0x017a:
            r3 = r16;	 Catch:{ all -> 0x026c }
            goto L_0x001c;	 Catch:{ all -> 0x026c }
        L_0x017e:
            if (r5 == 0) goto L_0x01b6;	 Catch:{ all -> 0x026c }
        L_0x0180:
            r3 = r1.mEglHelper;	 Catch:{ all -> 0x026c }
            r3 = r3.createSurface();	 Catch:{ all -> 0x026c }
            if (r3 == 0) goto L_0x019f;	 Catch:{ all -> 0x026c }
        L_0x0188:
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x026c }
            monitor-enter(r3);	 Catch:{ all -> 0x026c }
            r15 = 1;
            r1.mFinishedCreatingEglSurface = r15;	 Catch:{ all -> 0x019b }
            r15 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x019b }
            r15.notifyAll();	 Catch:{ all -> 0x019b }
            monitor-exit(r3);	 Catch:{ all -> 0x019b }
            r3 = 0;	 Catch:{ all -> 0x019b }
            r5 = r3;	 Catch:{ all -> 0x019b }
            goto L_0x01b6;	 Catch:{ all -> 0x019b }
        L_0x019b:
            r0 = move-exception;	 Catch:{ all -> 0x019b }
            r2 = r0;	 Catch:{ all -> 0x019b }
            monitor-exit(r3);	 Catch:{ all -> 0x019b }
            throw r2;	 Catch:{ all -> 0x026c }
        L_0x019f:
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x026c }
            monitor-enter(r3);	 Catch:{ all -> 0x026c }
            r15 = 1;
            r1.mFinishedCreatingEglSurface = r15;	 Catch:{ all -> 0x01b2 }
            r1.mSurfaceIsBad = r15;	 Catch:{ all -> 0x01b2 }
            r15 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x01b2 }
            r15.notifyAll();	 Catch:{ all -> 0x01b2 }
            monitor-exit(r3);	 Catch:{ all -> 0x01b2 }
            goto L_0x017a;	 Catch:{ all -> 0x01b2 }
        L_0x01b2:
            r0 = move-exception;	 Catch:{ all -> 0x01b2 }
            r2 = r0;	 Catch:{ all -> 0x01b2 }
            monitor-exit(r3);	 Catch:{ all -> 0x01b2 }
            throw r2;	 Catch:{ all -> 0x026c }
        L_0x01b6:
            if (r6 == 0) goto L_0x01c9;	 Catch:{ all -> 0x026c }
        L_0x01b8:
            r3 = r1.mEglHelper;	 Catch:{ all -> 0x026c }
            r3 = r3.createGL();	 Catch:{ all -> 0x026c }
            r3 = (javax.microedition.khronos.opengles.GL10) r3;	 Catch:{ all -> 0x026c }
            r15 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x026c }
            r15.checkGLDriver(r3);	 Catch:{ all -> 0x026c }
            r6 = 0;	 Catch:{ all -> 0x026c }
            goto L_0x01cb;	 Catch:{ all -> 0x026c }
        L_0x01c9:
            r3 = r16;	 Catch:{ all -> 0x026c }
        L_0x01cb:
            if (r4 == 0) goto L_0x01ea;	 Catch:{ all -> 0x026c }
        L_0x01cd:
            r15 = r1.mGLSurfaceViewWeakRef;	 Catch:{ all -> 0x026c }
            r15 = r15.get();	 Catch:{ all -> 0x026c }
            r15 = (com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18) r15;	 Catch:{ all -> 0x026c }
            if (r15 == 0) goto L_0x01e5;	 Catch:{ all -> 0x026c }
        L_0x01d7:
            r2 = r15.mRenderer;	 Catch:{ all -> 0x026c }
            r20 = r4;	 Catch:{ all -> 0x026c }
            r4 = r1.mEglHelper;	 Catch:{ all -> 0x026c }
            r4 = r4.mEglConfig;	 Catch:{ all -> 0x026c }
            r2.onSurfaceCreated(r3, r4);	 Catch:{ all -> 0x026c }
            goto L_0x01e7;	 Catch:{ all -> 0x026c }
        L_0x01e5:
            r20 = r4;	 Catch:{ all -> 0x026c }
        L_0x01e7:
            r2 = 0;	 Catch:{ all -> 0x026c }
            r4 = r2;	 Catch:{ all -> 0x026c }
            goto L_0x01ec;	 Catch:{ all -> 0x026c }
        L_0x01ea:
            r20 = r4;	 Catch:{ all -> 0x026c }
        L_0x01ec:
            if (r8 == 0) goto L_0x0201;	 Catch:{ all -> 0x026c }
        L_0x01ee:
            r2 = r1.mGLSurfaceViewWeakRef;	 Catch:{ all -> 0x026c }
            r2 = r2.get();	 Catch:{ all -> 0x026c }
            r2 = (com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18) r2;	 Catch:{ all -> 0x026c }
            if (r2 == 0) goto L_0x01ff;	 Catch:{ all -> 0x026c }
        L_0x01f8:
            r15 = r2.mRenderer;	 Catch:{ all -> 0x026c }
            r15.onSurfaceChanged(r3, r12, r13);	 Catch:{ all -> 0x026c }
        L_0x01ff:
            r2 = 0;	 Catch:{ all -> 0x026c }
            r8 = r2;	 Catch:{ all -> 0x026c }
        L_0x0201:
            r2 = r1.mGLSurfaceViewWeakRef;	 Catch:{ all -> 0x026c }
            r2 = r2.get();	 Catch:{ all -> 0x026c }
            r2 = (com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18) r2;	 Catch:{ all -> 0x026c }
            if (r2 == 0) goto L_0x0212;	 Catch:{ all -> 0x026c }
        L_0x020b:
            r15 = r2.mRenderer;	 Catch:{ all -> 0x026c }
            r15.onDrawFrame(r3);	 Catch:{ all -> 0x026c }
        L_0x0212:
            r2 = r1.mEglHelper;	 Catch:{ all -> 0x026c }
            r2 = r2.swap();	 Catch:{ all -> 0x026c }
            r15 = 12288; // 0x3000 float:1.7219E-41 double:6.071E-320;	 Catch:{ all -> 0x026c }
            if (r2 == r15) goto L_0x0244;	 Catch:{ all -> 0x026c }
        L_0x021c:
            r15 = 12302; // 0x300e float:1.7239E-41 double:6.078E-320;	 Catch:{ all -> 0x026c }
            if (r2 == r15) goto L_0x023e;	 Catch:{ all -> 0x026c }
        L_0x0220:
            r15 = "GLThread";	 Catch:{ all -> 0x026c }
            r21 = r3;	 Catch:{ all -> 0x026c }
            r3 = "eglSwapBuffers";	 Catch:{ all -> 0x026c }
            com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.EglHelper.logEglErrorAsWarning(r15, r3, r2);	 Catch:{ all -> 0x026c }
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x026c }
            monitor-enter(r3);	 Catch:{ all -> 0x026c }
            r15 = 1;
            r1.mSurfaceIsBad = r15;	 Catch:{ all -> 0x023a }
            r15 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x023a }
            r15.notifyAll();	 Catch:{ all -> 0x023a }
            monitor-exit(r3);	 Catch:{ all -> 0x023a }
            goto L_0x0246;	 Catch:{ all -> 0x023a }
        L_0x023a:
            r0 = move-exception;	 Catch:{ all -> 0x023a }
            r15 = r0;	 Catch:{ all -> 0x023a }
            monitor-exit(r3);	 Catch:{ all -> 0x023a }
            throw r15;	 Catch:{ all -> 0x026c }
        L_0x023e:
            r21 = r3;
            r3 = 1;
            r7 = r3;
            goto L_0x0246;
        L_0x0244:
            r21 = r3;
        L_0x0246:
            if (r9 == 0) goto L_0x024a;
        L_0x0248:
            r2 = 1;
            r10 = r2;
        L_0x024a:
            r3 = r21;
            r2 = 0;
            goto L_0x001c;
        L_0x024f:
            r0 = move-exception;
            r20 = r4;
            r2 = r0;
            goto L_0x0267;
        L_0x0254:
            r19 = r2;
        L_0x0256:
            r2 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0269 }
            r2.wait();	 Catch:{ all -> 0x0269 }
            r3 = r16;	 Catch:{ all -> 0x0269 }
            r2 = 0;	 Catch:{ all -> 0x0269 }
            goto L_0x0021;	 Catch:{ all -> 0x0269 }
        L_0x0263:
            r0 = move-exception;	 Catch:{ all -> 0x0269 }
            r16 = r3;	 Catch:{ all -> 0x0269 }
            r2 = r0;	 Catch:{ all -> 0x0269 }
        L_0x0267:
            monitor-exit(r15);	 Catch:{ all -> 0x0269 }
            throw r2;	 Catch:{ all -> 0x026c }
        L_0x0269:
            r0 = move-exception;
            r2 = r0;
            goto L_0x0267;
        L_0x026c:
            r0 = move-exception;
            r2 = r0;
            r3 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;
            monitor-enter(r3);
            r22.stopEglSurfaceLocked();	 Catch:{ all -> 0x027b }
            r22.stopEglContextLocked();	 Catch:{ all -> 0x027b }
            monitor-exit(r3);	 Catch:{ all -> 0x027b }
            throw r2;
        L_0x027b:
            r0 = move-exception;
            r2 = r0;
            monitor-exit(r3);	 Catch:{ all -> 0x027b }
            throw r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.GLThread.guardedRun():void");
        }

        public boolean ableToDraw() {
            return this.mHaveEglContext && this.mHaveEglSurface && readyToDraw();
        }

        private boolean readyToDraw() {
            return !this.mPaused && this.mHasSurface && !this.mSurfaceIsBad && this.mWidth > 0 && this.mHeight > 0 && (this.mRequestRender || this.mRenderMode == 1);
        }

        public void setRenderMode(int renderMode) {
            if (renderMode >= 0) {
                if (renderMode <= 1) {
                    synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                        this.mRenderMode = renderMode;
                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                    }
                    return;
                }
            }
            throw new IllegalArgumentException("renderMode");
        }

        public int getRenderMode() {
            int i;
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                i = this.mRenderMode;
            }
            return i;
        }

        public void requestRender() {
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mRequestRender = true;
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
            }
        }

        public void surfaceCreated() {
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mHasSurface = true;
                this.mFinishedCreatingEglSurface = false;
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                while (this.mWaitingForSurface && !this.mFinishedCreatingEglSurface && !this.mExited) {
                    try {
                        GLSurfaceViewAPI18.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void surfaceDestroyed() {
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mHasSurface = false;
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                while (!this.mWaitingForSurface && !this.mExited) {
                    try {
                        GLSurfaceViewAPI18.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onPause() {
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mRequestPaused = true;
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused) {
                    try {
                        GLSurfaceViewAPI18.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onResume() {
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mRequestPaused = false;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                while (!this.mExited && this.mPaused && !this.mRenderComplete) {
                    try {
                        GLSurfaceViewAPI18.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onWindowResize(int w, int h) {
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mWidth = w;
                this.mHeight = h;
                this.mSizeChanged = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused && !this.mRenderComplete && ableToDraw()) {
                    try {
                        GLSurfaceViewAPI18.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestExitAndWait() {
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mShouldExit = true;
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                while (!this.mExited) {
                    try {
                        GLSurfaceViewAPI18.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestReleaseEglContextLocked() {
            this.mShouldReleaseEglContext = true;
            GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
        }

        public void queueEvent(Runnable r) {
            if (r == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mEventQueue.add(r);
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
            }
        }
    }

    private static class GLThreadManager {
        private static String TAG = "GLThreadManager";
        private static final int kGLES_20 = 131072;
        private static final String kMSM7K_RENDERER_PREFIX = "Q3Dimension MSM7500 ";
        private GLThread mEglOwner;
        private boolean mGLESDriverCheckComplete;
        private int mGLESVersion;
        private boolean mGLESVersionCheckComplete;
        private boolean mLimitedGLESContexts;
        private boolean mMultipleGLESContextsAllowed;

        private GLThreadManager() {
        }

        public synchronized void threadExiting(GLThread thread) {
            thread.mExited = true;
            if (this.mEglOwner == thread) {
                this.mEglOwner = null;
            }
            notifyAll();
        }

        public boolean tryAcquireEglContextLocked(GLThread thread) {
            if (this.mEglOwner != thread) {
                if (this.mEglOwner != null) {
                    checkGLESVersion();
                    if (this.mMultipleGLESContextsAllowed) {
                        return true;
                    }
                    if (this.mEglOwner != null) {
                        this.mEglOwner.requestReleaseEglContextLocked();
                    }
                    return false;
                }
            }
            this.mEglOwner = thread;
            notifyAll();
            return true;
        }

        public void releaseEglContextLocked(GLThread thread) {
            if (this.mEglOwner == thread) {
                this.mEglOwner = null;
            }
            notifyAll();
        }

        public synchronized boolean shouldReleaseEGLContextWhenPausing() {
            return this.mLimitedGLESContexts;
        }

        public synchronized boolean shouldTerminateEGLWhenPausing() {
            checkGLESVersion();
            return this.mMultipleGLESContextsAllowed ^ 1;
        }

        public synchronized void checkGLDriver(GL10 gl) {
            if (!this.mGLESDriverCheckComplete) {
                checkGLESVersion();
                String renderer = gl.glGetString(GL20.GL_RENDERER);
                if (this.mGLESVersion < 131072) {
                    this.mMultipleGLESContextsAllowed = renderer.startsWith(kMSM7K_RENDERER_PREFIX) ^ true;
                    notifyAll();
                }
                this.mLimitedGLESContexts = this.mMultipleGLESContextsAllowed ^ true;
                this.mGLESDriverCheckComplete = true;
            }
        }

        private void checkGLESVersion() {
            if (!this.mGLESVersionCheckComplete) {
                this.mGLESVersion = 131072;
                if (this.mGLESVersion >= 131072) {
                    this.mMultipleGLESContextsAllowed = true;
                }
                this.mGLESVersionCheckComplete = true;
            }
        }
    }

    public interface GLWrapper {
        GL wrap(GL gl);
    }

    static class LogWriter extends Writer {
        private StringBuilder mBuilder = new StringBuilder();

        LogWriter() {
        }

        public void close() {
            flushBuilder();
        }

        public void flush() {
            flushBuilder();
        }

        public void write(char[] buf, int offset, int count) {
            for (int i = 0; i < count; i++) {
                char c = buf[offset + i];
                if (c == '\n') {
                    flushBuilder();
                } else {
                    this.mBuilder.append(c);
                }
            }
        }

        private void flushBuilder() {
            if (this.mBuilder.length() > 0) {
                Log.v("GLSurfaceView", this.mBuilder.toString());
                this.mBuilder.delete(0, this.mBuilder.length());
            }
        }
    }

    private class ComponentSizeChooser extends BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue = new int[1];

        public ComponentSizeChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
            super(new int[]{12324, redSize, 12323, greenSize, 12322, blueSize, 12321, alphaSize, 12325, depthSize, 12326, stencilSize, 12344});
            this.mRedSize = redSize;
            this.mGreenSize = greenSize;
            this.mBlueSize = blueSize;
            this.mAlphaSize = alphaSize;
            this.mDepthSize = depthSize;
            this.mStencilSize = stencilSize;
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
            ComponentSizeChooser componentSizeChooser = this;
            EGLConfig[] arr$ = configs;
            int len$ = arr$.length;
            int i$ = 0;
            while (true) {
                int i$2 = i$;
                if (i$2 >= len$) {
                    return null;
                }
                EGLConfig config = arr$[i$2];
                EGL10 egl10 = egl;
                EGLDisplay eGLDisplay = display;
                EGLConfig eGLConfig = config;
                int d = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12325, 0);
                int s = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12326, 0);
                if (d >= componentSizeChooser.mDepthSize && s >= componentSizeChooser.mStencilSize) {
                    egl10 = egl;
                    eGLDisplay = display;
                    eGLConfig = config;
                    int r = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12324, 0);
                    int g = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12323, 0);
                    int b = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12322, 0);
                    i$ = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12321, 0);
                    if (r == componentSizeChooser.mRedSize && g == componentSizeChooser.mGreenSize && b == componentSizeChooser.mBlueSize && i$ == componentSizeChooser.mAlphaSize) {
                        return config;
                    }
                }
                i$ = i$2 + 1;
            }
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
            if (egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
                return this.mValue[0];
            }
            return defaultValue;
        }
    }

    private class DefaultContextFactory implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION;

        private DefaultContextFactory() {
            this.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
            return egl.eglCreateContext(display, config, EGL10.EGL_NO_CONTEXT, GLSurfaceViewAPI18.this.mEGLContextClientVersion != 0 ? new int[]{this.EGL_CONTEXT_CLIENT_VERSION, GLSurfaceViewAPI18.this.mEGLContextClientVersion, 12344} : null);
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            if (!egl.eglDestroyContext(display, context)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("display:");
                stringBuilder.append(display);
                stringBuilder.append(" context: ");
                stringBuilder.append(context);
                Log.e("DefaultContextFactory", stringBuilder.toString());
                EglHelper.throwEglException("eglDestroyContex", egl.eglGetError());
            }
        }
    }

    private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        private DefaultWindowSurfaceFactory() {
        }

        public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {
            EGLSurface result = null;
            try {
                return egl.eglCreateWindowSurface(display, config, nativeWindow, null);
            } catch (IllegalArgumentException e) {
                Log.e(GLSurfaceViewAPI18.TAG, "eglCreateWindowSurface", e);
                return result;
            }
        }

        public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
            egl.eglDestroySurface(display, surface);
        }
    }

    private class SimpleEGLConfigChooser extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(boolean withDepthBuffer) {
            super(8, 8, 8, 0, withDepthBuffer ? 16 : 0, 0);
        }
    }

    public GLSurfaceViewAPI18(Context context) {
        super(context);
        init();
    }

    public GLSurfaceViewAPI18(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mGLThread != null) {
                this.mGLThread.requestExitAndWait();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        if (VERSION.SDK_INT <= 8) {
            holder.setFormat(4);
        }
    }

    public void setGLWrapper(GLWrapper glWrapper) {
        this.mGLWrapper = glWrapper;
    }

    public void setDebugFlags(int debugFlags) {
        this.mDebugFlags = debugFlags;
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public void setPreserveEGLContextOnPause(boolean preserveOnPause) {
        this.mPreserveEGLContextOnPause = preserveOnPause;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public void setRenderer(Renderer renderer) {
        checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
        }
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new DefaultContextFactory();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
        }
        this.mRenderer = renderer;
        this.mGLThread = new GLThread(this.mThisWeakRef);
        this.mGLThread.start();
    }

    public void setEGLContextFactory(EGLContextFactory factory) {
        checkRenderThreadState();
        this.mEGLContextFactory = factory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory factory) {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = factory;
    }

    public void setEGLConfigChooser(EGLConfigChooser configChooser) {
        checkRenderThreadState();
        this.mEGLConfigChooser = configChooser;
    }

    public void setEGLConfigChooser(boolean needDepth) {
        setEGLConfigChooser(new SimpleEGLConfigChooser(needDepth));
    }

    public void setEGLConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
        setEGLConfigChooser(new ComponentSizeChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize));
    }

    public void setEGLContextClientVersion(int version) {
        checkRenderThreadState();
        this.mEGLContextClientVersion = version;
    }

    public void setRenderMode(int renderMode) {
        this.mGLThread.setRenderMode(renderMode);
    }

    public int getRenderMode() {
        return this.mGLThread.getRenderMode();
    }

    public void requestRender() {
        this.mGLThread.requestRender();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.mGLThread.surfaceCreated();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.mGLThread.surfaceDestroyed();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        this.mGLThread.onWindowResize(w, h);
    }

    public void onPause() {
        this.mGLThread.onPause();
    }

    public void onResume() {
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable r) {
        this.mGLThread.queueEvent(r);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDetached && this.mRenderer != null) {
            int renderMode = 1;
            if (this.mGLThread != null) {
                renderMode = this.mGLThread.getRenderMode();
            }
            this.mGLThread = new GLThread(this.mThisWeakRef);
            if (renderMode != 1) {
                this.mGLThread.setRenderMode(renderMode);
            }
            this.mGLThread.start();
        }
        this.mDetached = false;
    }

    protected void onDetachedFromWindow() {
        if (this.mGLThread != null) {
            this.mGLThread.requestExitAndWait();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    private void checkRenderThreadState() {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }
}
