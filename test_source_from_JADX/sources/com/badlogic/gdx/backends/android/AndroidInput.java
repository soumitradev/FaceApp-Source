package com.badlogic.gdx.backends.android;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics$DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Pool;
import java.util.ArrayList;
import java.util.Arrays;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.BrickValues;

public class AndroidInput implements Input, OnKeyListener, OnTouchListener {
    public static final int NUM_TOUCHES = 20;
    public static final int SUPPORTED_KEYS = 260;
    /* renamed from: R */
    final float[] f102R;
    public boolean accelerometerAvailable;
    private SensorEventListener accelerometerListener;
    private final float[] accelerometerValues;
    final Application app;
    private float azimuth;
    int[] button = new int[20];
    private boolean catchBack;
    private boolean catchMenu;
    private boolean compassAvailable;
    private SensorEventListener compassListener;
    private final AndroidApplicationConfiguration config;
    final Context context;
    private long currentEventTimeStamp;
    int[] deltaX = new int[20];
    int[] deltaY = new int[20];
    private Handler handle;
    final boolean hasMultitouch;
    private float inclination;
    private boolean[] justPressedKeys;
    private boolean justTouched;
    private int keyCount;
    ArrayList<KeyEvent> keyEvents = new ArrayList();
    private boolean keyJustPressed;
    ArrayList<OnKeyListener> keyListeners = new ArrayList();
    boolean keyboardAvailable;
    private boolean[] keys;
    private final float[] magneticFieldValues;
    private SensorManager manager;
    private final Orientation nativeOrientation;
    private final AndroidOnscreenKeyboard onscreenKeyboard;
    final float[] orientation;
    private float pitch;
    private InputProcessor processor;
    int[] realId = new int[20];
    boolean requestFocus;
    private float roll;
    private int sleepTime;
    private String text;
    private TextInputListener textListener;
    ArrayList<TouchEvent> touchEvents = new ArrayList();
    private final AndroidTouchHandler touchHandler;
    int[] touchX = new int[20];
    int[] touchY = new int[20];
    boolean[] touched = new boolean[20];
    Pool<KeyEvent> usedKeyEvents = new Pool<KeyEvent>(16, 1000) {
        protected KeyEvent newObject() {
            return new KeyEvent();
        }
    };
    Pool<TouchEvent> usedTouchEvents = new Pool<TouchEvent>(16, 1000) {
        protected TouchEvent newObject() {
            return new TouchEvent();
        }
    };
    protected final Vibrator vibrator;

    static class KeyEvent {
        static final int KEY_DOWN = 0;
        static final int KEY_TYPED = 2;
        static final int KEY_UP = 1;
        char keyChar;
        int keyCode;
        long timeStamp;
        int type;

        KeyEvent() {
        }
    }

    private class SensorListener implements SensorEventListener {
        final float[] accelerometerValues;
        final float[] magneticFieldValues;
        final Orientation nativeOrientation;

        SensorListener(Orientation nativeOrientation, float[] accelerometerValues, float[] magneticFieldValues) {
            this.accelerometerValues = accelerometerValues;
            this.magneticFieldValues = magneticFieldValues;
            this.nativeOrientation = nativeOrientation;
        }

        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == 1) {
                if (this.nativeOrientation == Orientation.Portrait) {
                    System.arraycopy(event.values, 0, this.accelerometerValues, 0, this.accelerometerValues.length);
                } else {
                    this.accelerometerValues[0] = event.values[1];
                    this.accelerometerValues[1] = -event.values[0];
                    this.accelerometerValues[2] = event.values[2];
                }
            }
            if (event.sensor.getType() == 2) {
                System.arraycopy(event.values, 0, this.magneticFieldValues, 0, this.magneticFieldValues.length);
            }
        }
    }

    static class TouchEvent {
        static final int TOUCH_DOWN = 0;
        static final int TOUCH_DRAGGED = 2;
        static final int TOUCH_MOVED = 4;
        static final int TOUCH_SCROLLED = 3;
        static final int TOUCH_UP = 1;
        int button;
        int pointer;
        int scrollAmount;
        long timeStamp;
        int type;
        /* renamed from: x */
        int f59x;
        /* renamed from: y */
        int f60y;

        TouchEvent() {
        }
    }

    public AndroidInput(Application activity, Context context, Object view, AndroidApplicationConfiguration config) {
        int i = 0;
        this.keyCount = 0;
        this.keys = new boolean[260];
        this.keyJustPressed = false;
        this.justPressedKeys = new boolean[260];
        this.accelerometerAvailable = false;
        this.accelerometerValues = new float[3];
        this.text = null;
        this.textListener = null;
        this.sleepTime = 0;
        this.catchBack = false;
        this.catchMenu = false;
        this.compassAvailable = false;
        this.magneticFieldValues = new float[3];
        this.azimuth = 0.0f;
        this.pitch = 0.0f;
        this.roll = 0.0f;
        this.inclination = 0.0f;
        this.justTouched = false;
        this.currentEventTimeStamp = System.nanoTime();
        this.requestFocus = true;
        this.f102R = new float[9];
        this.orientation = new float[3];
        if (view instanceof View) {
            View v = (View) view;
            v.setOnKeyListener(this);
            v.setOnTouchListener(this);
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            v.requestFocus();
        }
        this.config = config;
        this.onscreenKeyboard = new AndroidOnscreenKeyboard(context, new Handler(), this);
        while (i < this.realId.length) {
            this.realId[i] = -1;
            i++;
        }
        this.handle = new Handler();
        this.app = activity;
        this.context = context;
        this.sleepTime = config.touchSleepTime;
        this.touchHandler = new AndroidMultiTouchHandler();
        this.hasMultitouch = this.touchHandler.supportsMultitouch(context);
        this.vibrator = (Vibrator) context.getSystemService("vibrator");
        i = getRotation();
        Graphics$DisplayMode mode = this.app.getGraphics().getDesktopDisplayMode();
        if (!(i == 0 || i == BrickValues.LEGO_ANGLE) || mode.width < mode.height) {
            if ((i != 90 && i != 270) || mode.width > mode.height) {
                this.nativeOrientation = Orientation.Portrait;
                return;
            }
        }
        this.nativeOrientation = Orientation.Landscape;
    }

    public float getAccelerometerX() {
        return this.accelerometerValues[0];
    }

    public float getAccelerometerY() {
        return this.accelerometerValues[1];
    }

    public float getAccelerometerZ() {
        return this.accelerometerValues[2];
    }

    public void getTextInput(TextInputListener listener, String title, String text, String hint) {
        final String str = title;
        final String str2 = hint;
        final String str3 = text;
        final TextInputListener textInputListener = listener;
        this.handle.post(new Runnable() {

            /* renamed from: com.badlogic.gdx.backends.android.AndroidInput$3$2 */
            class C03282 implements OnClickListener {

                /* renamed from: com.badlogic.gdx.backends.android.AndroidInput$3$2$1 */
                class C03271 implements Runnable {
                    C03271() {
                    }

                    public void run() {
                        textInputListener.canceled();
                    }
                }

                C03282() {
                }

                public void onClick(DialogInterface dialog, int whichButton) {
                    Gdx.app.postRunnable(new C03271());
                }
            }

            /* renamed from: com.badlogic.gdx.backends.android.AndroidInput$3$3 */
            class C03303 implements OnCancelListener {

                /* renamed from: com.badlogic.gdx.backends.android.AndroidInput$3$3$1 */
                class C03291 implements Runnable {
                    C03291() {
                    }

                    public void run() {
                        textInputListener.canceled();
                    }
                }

                C03303() {
                }

                public void onCancel(DialogInterface arg0) {
                    Gdx.app.postRunnable(new C03291());
                }
            }

            public void run() {
                Builder alert = new Builder(AndroidInput.this.context);
                alert.setTitle(str);
                final EditText input = new EditText(AndroidInput.this.context);
                input.setHint(str2);
                input.setText(str3);
                input.setSingleLine();
                alert.setView(input);
                alert.setPositiveButton(AndroidInput.this.context.getString(17039370), new OnClickListener() {

                    /* renamed from: com.badlogic.gdx.backends.android.AndroidInput$3$1$1 */
                    class C03251 implements Runnable {
                        C03251() {
                        }

                        public void run() {
                            textInputListener.input(input.getText().toString());
                        }
                    }

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Gdx.app.postRunnable(new C03251());
                    }
                });
                alert.setNegativeButton(AndroidInput.this.context.getString(17039360), new C03282());
                alert.setOnCancelListener(new C03303());
                alert.show();
            }
        });
    }

    public int getX() {
        int i;
        synchronized (this) {
            i = this.touchX[0];
        }
        return i;
    }

    public int getY() {
        int i;
        synchronized (this) {
            i = this.touchY[0];
        }
        return i;
    }

    public int getX(int pointer) {
        int i;
        synchronized (this) {
            i = this.touchX[pointer];
        }
        return i;
    }

    public int getY(int pointer) {
        int i;
        synchronized (this) {
            i = this.touchY[pointer];
        }
        return i;
    }

    public boolean isTouched(int pointer) {
        boolean z;
        synchronized (this) {
            z = this.touched[pointer];
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean isKeyPressed(int r3) {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = -1;
        r1 = 0;
        if (r3 != r0) goto L_0x000f;
    L_0x0005:
        r0 = r2.keyCount;	 Catch:{ all -> 0x000d }
        if (r0 <= 0) goto L_0x000b;
    L_0x0009:
        r1 = 1;
    L_0x000b:
        monitor-exit(r2);
        return r1;
    L_0x000d:
        r3 = move-exception;
        goto L_0x001c;
    L_0x000f:
        if (r3 < 0) goto L_0x001e;
    L_0x0011:
        r0 = 260; // 0x104 float:3.64E-43 double:1.285E-321;
        if (r3 < r0) goto L_0x0016;
    L_0x0015:
        goto L_0x001e;
    L_0x0016:
        r0 = r2.keys;	 Catch:{ all -> 0x000d }
        r0 = r0[r3];	 Catch:{ all -> 0x000d }
        monitor-exit(r2);
        return r0;
    L_0x001c:
        monitor-exit(r2);
        throw r3;
    L_0x001e:
        monitor-exit(r2);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidInput.isKeyPressed(int):boolean");
    }

    public synchronized boolean isKeyJustPressed(int key) {
        if (key == -1) {
            return this.keyJustPressed;
        }
        if (key >= 0) {
            if (key < 260) {
                return this.justPressedKeys[key];
            }
        }
        return false;
    }

    public boolean isTouched() {
        synchronized (this) {
            if (this.hasMultitouch) {
                for (int pointer = 0; pointer < 20; pointer++) {
                    if (this.touched[pointer]) {
                        return true;
                    }
                }
            }
            boolean z = this.touched[0];
            return z;
        }
    }

    public void setInputProcessor(InputProcessor processor) {
        synchronized (this) {
            this.processor = processor;
        }
    }

    void processEvents() {
        synchronized (this) {
            int i;
            this.justTouched = false;
            if (this.keyJustPressed) {
                this.keyJustPressed = false;
                for (i = 0; i < this.justPressedKeys.length; i++) {
                    this.justPressedKeys[i] = false;
                }
            }
            int len;
            if (this.processor != null) {
                int i2;
                InputProcessor processor = this.processor;
                len = this.keyEvents.size();
                for (i2 = 0; i2 < len; i2++) {
                    KeyEvent e = (KeyEvent) this.keyEvents.get(i2);
                    this.currentEventTimeStamp = e.timeStamp;
                    switch (e.type) {
                        case 0:
                            processor.keyDown(e.keyCode);
                            this.keyJustPressed = true;
                            this.justPressedKeys[e.keyCode] = true;
                            break;
                        case 1:
                            processor.keyUp(e.keyCode);
                            break;
                        case 2:
                            processor.keyTyped(e.keyChar);
                            break;
                        default:
                            break;
                    }
                    this.usedKeyEvents.free(e);
                }
                len = this.touchEvents.size();
                for (i2 = 0; i2 < len; i2++) {
                    TouchEvent e2 = (TouchEvent) this.touchEvents.get(i2);
                    this.currentEventTimeStamp = e2.timeStamp;
                    switch (e2.type) {
                        case 0:
                            processor.touchDown(e2.f59x, e2.f60y, e2.pointer, e2.button);
                            this.justTouched = true;
                            break;
                        case 1:
                            processor.touchUp(e2.f59x, e2.f60y, e2.pointer, e2.button);
                            break;
                        case 2:
                            processor.touchDragged(e2.f59x, e2.f60y, e2.pointer);
                            break;
                        case 3:
                            processor.scrolled(e2.scrollAmount);
                            break;
                        case 4:
                            processor.mouseMoved(e2.f59x, e2.f60y);
                            break;
                        default:
                            break;
                    }
                    this.usedTouchEvents.free(e2);
                }
            } else {
                i = this.touchEvents.size();
                for (len = 0; len < i; len++) {
                    TouchEvent e3 = (TouchEvent) this.touchEvents.get(len);
                    if (e3.type == 0) {
                        this.justTouched = true;
                    }
                    this.usedTouchEvents.free(e3);
                }
                i = this.keyEvents.size();
                for (int i3 = 0; i3 < i; i3++) {
                    this.usedKeyEvents.free(this.keyEvents.get(i3));
                }
            }
            if (this.touchEvents.size() == 0) {
                for (i = 0; i < this.deltaX.length; i++) {
                    this.deltaX[0] = 0;
                    this.deltaY[0] = 0;
                }
            }
            this.keyEvents.clear();
            this.touchEvents.clear();
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        if (this.requestFocus && view != null) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            this.requestFocus = false;
        }
        this.touchHandler.onTouch(event, this);
        if (this.sleepTime != 0) {
            try {
                Thread.sleep((long) this.sleepTime);
            } catch (InterruptedException e) {
            }
        }
        return true;
    }

    public void onTap(int x, int y) {
        postTap(x, y);
    }

    public void onDrop(int x, int y) {
        postTap(x, y);
    }

    protected void postTap(int x, int y) {
        synchronized (this) {
            TouchEvent event = (TouchEvent) this.usedTouchEvents.obtain();
            event.timeStamp = System.nanoTime();
            event.pointer = 0;
            event.f59x = x;
            event.f60y = y;
            event.type = 0;
            this.touchEvents.add(event);
            event = (TouchEvent) this.usedTouchEvents.obtain();
            event.timeStamp = System.nanoTime();
            event.pointer = 0;
            event.f59x = x;
            event.f60y = y;
            event.type = 1;
            this.touchEvents.add(event);
        }
        Gdx.app.getGraphics().requestRendering();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKey(android.view.View r11, int r12, android.view.KeyEvent r13) {
        /*
        r10 = this;
        r0 = 0;
        r1 = r10.keyListeners;
        r1 = r1.size();
    L_0x0007:
        r2 = 1;
        if (r0 >= r1) goto L_0x001c;
    L_0x000a:
        r3 = r10.keyListeners;
        r3 = r3.get(r0);
        r3 = (android.view.View.OnKeyListener) r3;
        r3 = r3.onKey(r11, r12, r13);
        if (r3 == 0) goto L_0x0019;
    L_0x0018:
        return r2;
    L_0x0019:
        r0 = r0 + 1;
        goto L_0x0007;
    L_0x001c:
        monitor-enter(r10);
        r0 = 0;
        r1 = r13.getKeyCode();	 Catch:{ all -> 0x0141 }
        r3 = 2;
        r4 = 0;
        if (r1 != 0) goto L_0x005b;
    L_0x0026:
        r1 = r13.getAction();	 Catch:{ all -> 0x0141 }
        if (r1 != r3) goto L_0x005b;
    L_0x002c:
        r1 = r13.getCharacters();	 Catch:{ all -> 0x0141 }
        r2 = r0;
        r0 = 0;
    L_0x0032:
        r5 = r1.length();	 Catch:{ all -> 0x0141 }
        if (r0 >= r5) goto L_0x0059;
    L_0x0038:
        r5 = r10.usedKeyEvents;	 Catch:{ all -> 0x0141 }
        r5 = r5.obtain();	 Catch:{ all -> 0x0141 }
        r5 = (com.badlogic.gdx.backends.android.AndroidInput.KeyEvent) r5;	 Catch:{ all -> 0x0141 }
        r2 = r5;
        r5 = java.lang.System.nanoTime();	 Catch:{ all -> 0x0141 }
        r2.timeStamp = r5;	 Catch:{ all -> 0x0141 }
        r2.keyCode = r4;	 Catch:{ all -> 0x0141 }
        r5 = r1.charAt(r0);	 Catch:{ all -> 0x0141 }
        r2.keyChar = r5;	 Catch:{ all -> 0x0141 }
        r2.type = r3;	 Catch:{ all -> 0x0141 }
        r5 = r10.keyEvents;	 Catch:{ all -> 0x0141 }
        r5.add(r2);	 Catch:{ all -> 0x0141 }
        r0 = r0 + 1;
        goto L_0x0032;
    L_0x0059:
        monitor-exit(r10);	 Catch:{ all -> 0x0141 }
        return r4;
    L_0x005b:
        r1 = r13.getUnicodeChar();	 Catch:{ all -> 0x0141 }
        r1 = (char) r1;	 Catch:{ all -> 0x0141 }
        r5 = 67;
        if (r12 != r5) goto L_0x0066;
    L_0x0064:
        r1 = 8;
    L_0x0066:
        r5 = r13.getKeyCode();	 Catch:{ all -> 0x0141 }
        r6 = 260; // 0x104 float:3.64E-43 double:1.285E-321;
        if (r5 < r6) goto L_0x0070;
    L_0x006e:
        monitor-exit(r10);	 Catch:{ all -> 0x0141 }
        return r4;
    L_0x0070:
        r5 = r13.getAction();	 Catch:{ all -> 0x0141 }
        r6 = 4;
        r7 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        switch(r5) {
            case 0: goto L_0x00e6;
            case 1: goto L_0x007c;
            default: goto L_0x007a;
        };	 Catch:{ all -> 0x0141 }
    L_0x007a:
        goto L_0x0123;
    L_0x007c:
        r8 = java.lang.System.nanoTime();	 Catch:{ all -> 0x0141 }
        r5 = r10.usedKeyEvents;	 Catch:{ all -> 0x0141 }
        r5 = r5.obtain();	 Catch:{ all -> 0x0141 }
        r5 = (com.badlogic.gdx.backends.android.AndroidInput.KeyEvent) r5;	 Catch:{ all -> 0x0141 }
        r0 = r5;
        r0.timeStamp = r8;	 Catch:{ all -> 0x0141 }
        r0.keyChar = r4;	 Catch:{ all -> 0x0141 }
        r5 = r13.getKeyCode();	 Catch:{ all -> 0x0141 }
        r0.keyCode = r5;	 Catch:{ all -> 0x0141 }
        r0.type = r2;	 Catch:{ all -> 0x0141 }
        if (r12 != r6) goto L_0x00a1;
    L_0x0097:
        r5 = r13.isAltPressed();	 Catch:{ all -> 0x0141 }
        if (r5 == 0) goto L_0x00a1;
    L_0x009d:
        r12 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r0.keyCode = r12;	 Catch:{ all -> 0x0141 }
    L_0x00a1:
        r5 = r10.keyEvents;	 Catch:{ all -> 0x0141 }
        r5.add(r0);	 Catch:{ all -> 0x0141 }
        r5 = r10.usedKeyEvents;	 Catch:{ all -> 0x0141 }
        r5 = r5.obtain();	 Catch:{ all -> 0x0141 }
        r5 = (com.badlogic.gdx.backends.android.AndroidInput.KeyEvent) r5;	 Catch:{ all -> 0x0141 }
        r0 = r5;
        r0.timeStamp = r8;	 Catch:{ all -> 0x0141 }
        r0.keyChar = r1;	 Catch:{ all -> 0x0141 }
        r0.keyCode = r4;	 Catch:{ all -> 0x0141 }
        r0.type = r3;	 Catch:{ all -> 0x0141 }
        r3 = r10.keyEvents;	 Catch:{ all -> 0x0141 }
        r3.add(r0);	 Catch:{ all -> 0x0141 }
        if (r12 != r7) goto L_0x00ce;
    L_0x00be:
        r3 = r10.keys;	 Catch:{ all -> 0x0141 }
        r3 = r3[r7];	 Catch:{ all -> 0x0141 }
        if (r3 == 0) goto L_0x0123;
    L_0x00c4:
        r3 = r10.keyCount;	 Catch:{ all -> 0x0141 }
        r3 = r3 - r2;
        r10.keyCount = r3;	 Catch:{ all -> 0x0141 }
        r3 = r10.keys;	 Catch:{ all -> 0x0141 }
        r3[r7] = r4;	 Catch:{ all -> 0x0141 }
        goto L_0x0123;
    L_0x00ce:
        r3 = r10.keys;	 Catch:{ all -> 0x0141 }
        r5 = r13.getKeyCode();	 Catch:{ all -> 0x0141 }
        r3 = r3[r5];	 Catch:{ all -> 0x0141 }
        if (r3 == 0) goto L_0x0123;
    L_0x00d8:
        r3 = r10.keyCount;	 Catch:{ all -> 0x0141 }
        r3 = r3 - r2;
        r10.keyCount = r3;	 Catch:{ all -> 0x0141 }
        r3 = r10.keys;	 Catch:{ all -> 0x0141 }
        r5 = r13.getKeyCode();	 Catch:{ all -> 0x0141 }
        r3[r5] = r4;	 Catch:{ all -> 0x0141 }
        goto L_0x0123;
    L_0x00e6:
        r3 = r10.usedKeyEvents;	 Catch:{ all -> 0x0141 }
        r3 = r3.obtain();	 Catch:{ all -> 0x0141 }
        r3 = (com.badlogic.gdx.backends.android.AndroidInput.KeyEvent) r3;	 Catch:{ all -> 0x0141 }
        r0 = r3;
        r8 = java.lang.System.nanoTime();	 Catch:{ all -> 0x0141 }
        r0.timeStamp = r8;	 Catch:{ all -> 0x0141 }
        r0.keyChar = r4;	 Catch:{ all -> 0x0141 }
        r3 = r13.getKeyCode();	 Catch:{ all -> 0x0141 }
        r0.keyCode = r3;	 Catch:{ all -> 0x0141 }
        r0.type = r4;	 Catch:{ all -> 0x0141 }
        if (r12 != r6) goto L_0x010b;
    L_0x0101:
        r3 = r13.isAltPressed();	 Catch:{ all -> 0x0141 }
        if (r3 == 0) goto L_0x010b;
    L_0x0107:
        r12 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r0.keyCode = r12;	 Catch:{ all -> 0x0141 }
    L_0x010b:
        r3 = r10.keyEvents;	 Catch:{ all -> 0x0141 }
        r3.add(r0);	 Catch:{ all -> 0x0141 }
        r3 = r10.keys;	 Catch:{ all -> 0x0141 }
        r5 = r0.keyCode;	 Catch:{ all -> 0x0141 }
        r3 = r3[r5];	 Catch:{ all -> 0x0141 }
        if (r3 != 0) goto L_0x0123;
    L_0x0118:
        r3 = r10.keyCount;	 Catch:{ all -> 0x0141 }
        r3 = r3 + r2;
        r10.keyCount = r3;	 Catch:{ all -> 0x0141 }
        r3 = r10.keys;	 Catch:{ all -> 0x0141 }
        r5 = r0.keyCode;	 Catch:{ all -> 0x0141 }
        r3[r5] = r2;	 Catch:{ all -> 0x0141 }
    L_0x0123:
        r3 = r10.app;	 Catch:{ all -> 0x0141 }
        r3 = r3.getGraphics();	 Catch:{ all -> 0x0141 }
        r3.requestRendering();	 Catch:{ all -> 0x0141 }
        monitor-exit(r10);	 Catch:{ all -> 0x0141 }
        if (r12 != r7) goto L_0x0130;
    L_0x012f:
        return r2;
    L_0x0130:
        r0 = r10.catchBack;
        if (r0 == 0) goto L_0x0137;
    L_0x0134:
        if (r12 != r6) goto L_0x0137;
    L_0x0136:
        return r2;
    L_0x0137:
        r0 = r10.catchMenu;
        if (r0 == 0) goto L_0x0140;
    L_0x013b:
        r0 = 82;
        if (r12 != r0) goto L_0x0140;
    L_0x013f:
        return r2;
    L_0x0140:
        return r4;
    L_0x0141:
        r0 = move-exception;
        monitor-exit(r10);	 Catch:{ all -> 0x0141 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidInput.onKey(android.view.View, int, android.view.KeyEvent):boolean");
    }

    public void setOnscreenKeyboardVisible(final boolean visible) {
        this.handle.post(new Runnable() {
            public void run() {
                InputMethodManager manager = (InputMethodManager) AndroidInput.this.context.getSystemService("input_method");
                if (visible) {
                    View view = ((AndroidGraphics) AndroidInput.this.app.getGraphics()).getView();
                    view.setFocusable(true);
                    view.setFocusableInTouchMode(true);
                    manager.showSoftInput(((AndroidGraphics) AndroidInput.this.app.getGraphics()).getView(), 0);
                    return;
                }
                manager.hideSoftInputFromWindow(((AndroidGraphics) AndroidInput.this.app.getGraphics()).getView().getWindowToken(), 0);
            }
        });
    }

    public void setCatchBackKey(boolean catchBack) {
        this.catchBack = catchBack;
    }

    public boolean isCatchBackKey() {
        return this.catchBack;
    }

    public void setCatchMenuKey(boolean catchMenu) {
        this.catchMenu = catchMenu;
    }

    public void vibrate(int milliseconds) {
        this.vibrator.vibrate((long) milliseconds);
    }

    public void vibrate(long[] pattern, int repeat) {
        this.vibrator.vibrate(pattern, repeat);
    }

    public void cancelVibrate() {
        this.vibrator.cancel();
    }

    public boolean justTouched() {
        return this.justTouched;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isButtonPressed(int r5) {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.hasMultitouch;	 Catch:{ all -> 0x002d }
        r1 = 1;
        r2 = 0;
        if (r0 == 0) goto L_0x001d;
    L_0x0007:
        r0 = 0;
    L_0x0008:
        r3 = 20;
        if (r0 >= r3) goto L_0x001d;
    L_0x000c:
        r3 = r4.touched;	 Catch:{ all -> 0x002d }
        r3 = r3[r0];	 Catch:{ all -> 0x002d }
        if (r3 == 0) goto L_0x001a;
    L_0x0012:
        r3 = r4.button;	 Catch:{ all -> 0x002d }
        r3 = r3[r0];	 Catch:{ all -> 0x002d }
        if (r3 != r5) goto L_0x001a;
    L_0x0018:
        monitor-exit(r4);	 Catch:{ all -> 0x002d }
        return r1;
    L_0x001a:
        r0 = r0 + 1;
        goto L_0x0008;
    L_0x001d:
        r0 = r4.touched;	 Catch:{ all -> 0x002d }
        r0 = r0[r2];	 Catch:{ all -> 0x002d }
        if (r0 == 0) goto L_0x002a;
    L_0x0023:
        r0 = r4.button;	 Catch:{ all -> 0x002d }
        r0 = r0[r2];	 Catch:{ all -> 0x002d }
        if (r0 != r5) goto L_0x002a;
    L_0x0029:
        goto L_0x002b;
    L_0x002a:
        r1 = 0;
    L_0x002b:
        monitor-exit(r4);	 Catch:{ all -> 0x002d }
        return r1;
    L_0x002d:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x002d }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidInput.isButtonPressed(int):boolean");
    }

    private void updateOrientation() {
        if (SensorManager.getRotationMatrix(this.f102R, null, this.accelerometerValues, this.magneticFieldValues)) {
            SensorManager.getOrientation(this.f102R, this.orientation);
            this.azimuth = (float) Math.toDegrees((double) this.orientation[0]);
            this.pitch = (float) Math.toDegrees((double) this.orientation[1]);
            this.roll = (float) Math.toDegrees((double) this.orientation[2]);
        }
    }

    public void getRotationMatrix(float[] matrix) {
        SensorManager.getRotationMatrix(matrix, null, this.accelerometerValues, this.magneticFieldValues);
    }

    public float getAzimuth() {
        if (!this.compassAvailable) {
            return 0.0f;
        }
        updateOrientation();
        return this.azimuth;
    }

    public float getPitch() {
        if (!this.compassAvailable) {
            return 0.0f;
        }
        updateOrientation();
        return this.pitch;
    }

    public float getRoll() {
        if (!this.compassAvailable) {
            return 0.0f;
        }
        updateOrientation();
        return this.roll;
    }

    void registerSensorListeners() {
        if (this.config.useAccelerometer) {
            this.manager = (SensorManager) this.context.getSystemService("sensor");
            if (this.manager.getSensorList(1).size() == 0) {
                this.accelerometerAvailable = false;
            } else {
                Sensor accelerometer = (Sensor) this.manager.getSensorList(1).get(0);
                this.accelerometerListener = new SensorListener(this.nativeOrientation, this.accelerometerValues, this.magneticFieldValues);
                this.accelerometerAvailable = this.manager.registerListener(this.accelerometerListener, accelerometer, 1);
            }
        } else {
            this.accelerometerAvailable = false;
        }
        if (this.config.useCompass) {
            if (this.manager == null) {
                this.manager = (SensorManager) this.context.getSystemService("sensor");
            }
            accelerometer = this.manager.getDefaultSensor(2);
            if (accelerometer != null) {
                this.compassAvailable = this.accelerometerAvailable;
                if (this.compassAvailable) {
                    this.compassListener = new SensorListener(this.nativeOrientation, this.accelerometerValues, this.magneticFieldValues);
                    this.compassAvailable = this.manager.registerListener(this.compassListener, accelerometer, 1);
                }
            } else {
                this.compassAvailable = false;
            }
        } else {
            this.compassAvailable = false;
        }
        Gdx.app.log("AndroidInput", "sensor listener setup");
    }

    void unregisterSensorListeners() {
        if (this.manager != null) {
            if (this.accelerometerListener != null) {
                this.manager.unregisterListener(this.accelerometerListener);
                this.accelerometerListener = null;
            }
            if (this.compassListener != null) {
                this.manager.unregisterListener(this.compassListener);
                this.compassListener = null;
            }
            this.manager = null;
        }
        Gdx.app.log("AndroidInput", "sensor listener tear down");
    }

    public InputProcessor getInputProcessor() {
        return this.processor;
    }

    public boolean isPeripheralAvailable(Peripheral peripheral) {
        if (peripheral == Peripheral.Accelerometer) {
            return this.accelerometerAvailable;
        }
        if (peripheral == Peripheral.Compass) {
            return this.compassAvailable;
        }
        if (peripheral == Peripheral.HardwareKeyboard) {
            return this.keyboardAvailable;
        }
        boolean z = true;
        if (peripheral == Peripheral.OnscreenKeyboard) {
            return true;
        }
        if (peripheral == Peripheral.Vibrator) {
            if (this.vibrator == null) {
                z = false;
            }
            return z;
        } else if (peripheral == Peripheral.MultitouchScreen) {
            return this.hasMultitouch;
        } else {
            return false;
        }
    }

    public int getFreePointerIndex() {
        int len = this.realId.length;
        for (int i = 0; i < len; i++) {
            if (this.realId[i] == -1) {
                return i;
            }
        }
        this.realId = resize(this.realId);
        this.touchX = resize(this.touchX);
        this.touchY = resize(this.touchY);
        this.deltaX = resize(this.deltaX);
        this.deltaY = resize(this.deltaY);
        this.touched = resize(this.touched);
        this.button = resize(this.button);
        return len;
    }

    private int[] resize(int[] orig) {
        int[] tmp = new int[(orig.length + 2)];
        System.arraycopy(orig, 0, tmp, 0, orig.length);
        return tmp;
    }

    private boolean[] resize(boolean[] orig) {
        boolean[] tmp = new boolean[(orig.length + 2)];
        System.arraycopy(orig, 0, tmp, 0, orig.length);
        return tmp;
    }

    public int lookUpPointerIndex(int pointerId) {
        int len = this.realId.length;
        for (int i = 0; i < len; i++) {
            if (this.realId[i] == pointerId) {
                return i;
            }
        }
        StringBuffer buf = new StringBuffer();
        for (int i2 = 0; i2 < len; i2++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i2);
            stringBuilder.append(":");
            stringBuilder.append(this.realId[i2]);
            stringBuilder.append(FormatHelper.SPACE);
            buf.append(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Pointer ID lookup failed: ");
        stringBuilder2.append(pointerId);
        stringBuilder2.append(", ");
        stringBuilder2.append(buf.toString());
        Gdx.app.log("AndroidInput", stringBuilder2.toString());
        return -1;
    }

    public int getRotation() {
        int orientation;
        if (this.context instanceof Activity) {
            orientation = ((Activity) this.context).getWindowManager().getDefaultDisplay().getRotation();
        } else {
            orientation = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay().getRotation();
        }
        switch (orientation) {
            case 0:
                return 0;
            case 1:
                return 90;
            case 2:
                return BrickValues.LEGO_ANGLE;
            case 3:
                return 270;
            default:
                return 0;
        }
    }

    public Orientation getNativeOrientation() {
        return this.nativeOrientation;
    }

    public void setCursorCatched(boolean catched) {
    }

    public boolean isCursorCatched() {
        return false;
    }

    public int getDeltaX() {
        return this.deltaX[0];
    }

    public int getDeltaX(int pointer) {
        return this.deltaX[pointer];
    }

    public int getDeltaY() {
        return this.deltaY[0];
    }

    public int getDeltaY(int pointer) {
        return this.deltaY[pointer];
    }

    public void setCursorPosition(int x, int y) {
    }

    public void setCursorImage(Pixmap pixmap, int xHotspot, int yHotspot) {
    }

    public long getCurrentEventTime() {
        return this.currentEventTimeStamp;
    }

    public void addKeyListener(OnKeyListener listener) {
        this.keyListeners.add(listener);
    }

    public void onPause() {
        unregisterSensorListeners();
        Arrays.fill(this.realId, -1);
        Arrays.fill(this.touched, false);
    }

    public void onResume() {
        registerSensorListeners();
    }
}
