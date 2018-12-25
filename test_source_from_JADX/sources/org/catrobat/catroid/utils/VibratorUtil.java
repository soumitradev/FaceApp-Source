package org.catrobat.catroid.utils;

import android.content.Context;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import java.util.concurrent.Semaphore;
import org.catrobat.catroid.common.BrickValues;

public final class VibratorUtil {
    private static final int MAX_TIME_TO_VIBRATE = 60000;
    private static final String TAG = VibratorUtil.class.getSimpleName();
    private static Context context = null;
    private static boolean keepAlive = false;
    private static boolean paused = false;
    private static long savedTimeToVibrate = 0;
    private static long startTime = 0;
    private static long timeToVibrate = 0;
    private static Vibrator vibrator = null;
    private static Thread vibratorThread = null;
    private static Semaphore vibratorThreadSemaphore = new Semaphore(1);

    /* renamed from: org.catrobat.catroid.utils.VibratorUtil$1 */
    static class C20181 implements Runnable {
        C20181() {
        }

        public void run() {
            while (VibratorUtil.keepAlive) {
                try {
                    VibratorUtil.vibratorThreadSemaphore.acquire();
                    VibratorUtil.startVibrate();
                    while (VibratorUtil.startTime + VibratorUtil.timeToVibrate > SystemClock.uptimeMillis()) {
                        Thread.yield();
                    }
                    VibratorUtil.stopVibrate();
                } catch (InterruptedException e) {
                    String access$600 = VibratorUtil.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("vibratorThreadSemaphore! ");
                    stringBuilder.append(e.getMessage());
                    Log.e(access$600, stringBuilder.toString());
                }
            }
            VibratorUtil.vibratorThreadSemaphore.release();
        }
    }

    private VibratorUtil() {
    }

    public static void setTimeToVibrate(double timeInMillis) {
        Log.d(TAG, "setTimeToVibrate()");
        timeToVibrate = (long) timeInMillis;
        if (vibratorThreadSemaphore.hasQueuedThreads()) {
            vibratorThreadSemaphore.release();
        } else {
            startTime = SystemClock.uptimeMillis();
        }
    }

    public static long getTimeToVibrate() {
        long timePassed = SystemClock.uptimeMillis() - startTime;
        if (timeToVibrate - timePassed < 0) {
            return 0;
        }
        return timeToVibrate - timePassed;
    }

    public static void pauseVibrator() {
        Log.d(TAG, "pauseVibrator()");
        if (!paused) {
            if (startTime + timeToVibrate > SystemClock.uptimeMillis()) {
                savedTimeToVibrate = timeToVibrate - (SystemClock.uptimeMillis() - startTime);
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PAUSED! time left was: ");
                stringBuilder.append(Long.toString(savedTimeToVibrate));
                Log.d(str, stringBuilder.toString());
            } else {
                savedTimeToVibrate = 0;
            }
            timeToVibrate = 0;
            killVibratorThread();
            paused = true;
        }
    }

    public static void resumeVibrator() {
        Log.d(TAG, "resumeVibrator()");
        if (paused) {
            timeToVibrate = savedTimeToVibrate;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("savedTimeToVibrate = ");
            stringBuilder.append(savedTimeToVibrate);
            Log.d(str, stringBuilder.toString());
            savedTimeToVibrate = 0;
            keepAlive = true;
            activateVibratorThread();
            if (timeToVibrate > 0) {
                vibratorThreadSemaphore.release();
            } else {
                Log.d(TAG, "nothing to do");
            }
        }
        paused = false;
    }

    public static void destroy() {
        Log.d(TAG, "reset() - called by StageActivity::onDestroy");
        startTime = 0;
        timeToVibrate = 0;
        savedTimeToVibrate = 0;
        keepAlive = false;
        if (vibratorThreadSemaphore.hasQueuedThreads()) {
            vibratorThreadSemaphore.release();
        }
        paused = false;
        context = null;
        vibrator = null;
        vibratorThread = null;
    }

    public static void reset() {
        setTimeToVibrate(BrickValues.SET_COLOR_TO);
    }

    public static void setContext(Context stageContext) {
        context = stageContext;
    }

    public static boolean isActive() {
        return keepAlive;
    }

    public static void activateVibratorThread() {
        Log.d(TAG, "activateVibratorThread");
        if (context == null) {
            Log.e(TAG, "ERROR: set Context first!");
            return;
        }
        if (vibratorThread == null) {
            vibratorThread = new Thread(new C20181());
        }
        if (vibrator == null) {
            vibrator = (Vibrator) context.getSystemService("vibrator");
        }
        if (!vibratorThread.isAlive()) {
            try {
                vibratorThreadSemaphore.acquire();
            } catch (InterruptedException e) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("vibratorThreadSemaphore! ");
                stringBuilder.append(e.getMessage());
                Log.e(str, stringBuilder.toString());
            }
            keepAlive = true;
            vibratorThread.setName("vibratorThread");
            Log.d(TAG, "starting thread...");
            vibratorThread.start();
        }
    }

    private static void killVibratorThread() {
        Log.d(TAG, "destroy()");
        keepAlive = false;
        if (vibratorThreadSemaphore.hasQueuedThreads()) {
            vibratorThreadSemaphore.release();
        }
        startTime = 0;
        timeToVibrate = 0;
        vibratorThread = null;
    }

    private static synchronized void startVibrate() {
        synchronized (VibratorUtil.class) {
            if (vibrator != null) {
                Log.d(TAG, "startVibrate()");
                startTime = SystemClock.uptimeMillis();
                vibrator.vibrate(60000);
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("start time was: ");
                stringBuilder.append(Long.toString(startTime));
                Log.d(str, stringBuilder.toString());
            }
        }
    }

    private static synchronized void stopVibrate() {
        synchronized (VibratorUtil.class) {
            if (vibrator != null) {
                Log.d(TAG, "stopVibrate()");
                vibrator.cancel();
            }
        }
    }
}
