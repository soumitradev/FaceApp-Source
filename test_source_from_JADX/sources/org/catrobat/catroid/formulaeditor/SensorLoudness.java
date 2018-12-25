package org.catrobat.catroid.formulaeditor;

import android.os.Handler;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.catrobat.catroid.soundrecorder.SoundRecorder;

public final class SensorLoudness {
    private static final double MAX_AMP_VALUE = 32767.0d;
    private static final double SCALE_RANGE = 100.0d;
    private static final String TAG = SensorLoudness.class.getSimpleName();
    private static final int UPDATE_INTERVAL = 50;
    private static SensorLoudness instance = null;
    private Handler handler;
    private float lastValue;
    private ArrayList<SensorCustomEventListener> listenerList;
    private SoundRecorder recorder;
    Runnable statusChecker;

    /* renamed from: org.catrobat.catroid.formulaeditor.SensorLoudness$1 */
    class C18461 implements Runnable {
        C18461() {
        }

        public void run() {
            float[] loudness = new float[]{((float) SensorLoudness.this.recorder.getMaxAmplitude()) * 0.003051851f};
            if (!(SensorLoudness.this.lastValue == loudness[0] || loudness[0] == 0.0f)) {
                SensorLoudness.this.lastValue = loudness[0];
                SensorCustomEvent event = new SensorCustomEvent(Sensors.LOUDNESS, loudness);
                Iterator it = SensorLoudness.this.listenerList.iterator();
                while (it.hasNext()) {
                    ((SensorCustomEventListener) it.next()).onCustomSensorChanged(event);
                }
            }
            SensorLoudness.this.handler.postDelayed(SensorLoudness.this.statusChecker, 50);
        }
    }

    private SensorLoudness() {
        this.listenerList = new ArrayList();
        this.recorder = null;
        this.lastValue = 0.0f;
        this.statusChecker = new C18461();
        this.handler = new Handler();
        this.recorder = new SoundRecorder("/dev/null");
    }

    public static SensorLoudness getSensorLoudness() {
        if (instance == null) {
            instance = new SensorLoudness();
        }
        return instance;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean registerListener(org.catrobat.catroid.formulaeditor.SensorCustomEventListener r5) {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.listenerList;	 Catch:{ all -> 0x0057 }
        r0 = r0.contains(r5);	 Catch:{ all -> 0x0057 }
        r1 = 1;
        if (r0 == 0) goto L_0x000c;
    L_0x000a:
        monitor-exit(r4);
        return r1;
    L_0x000c:
        r0 = r4.listenerList;	 Catch:{ all -> 0x0057 }
        r0.add(r5);	 Catch:{ all -> 0x0057 }
        r0 = r4.recorder;	 Catch:{ all -> 0x0057 }
        r0 = r0.isRecording();	 Catch:{ all -> 0x0057 }
        if (r0 != 0) goto L_0x0055;
    L_0x0019:
        r0 = 0;
        r2 = r4.recorder;	 Catch:{ IOException -> 0x003d, RuntimeException -> 0x0025 }
        r2.start();	 Catch:{ IOException -> 0x003d, RuntimeException -> 0x0025 }
        r2 = r4.statusChecker;	 Catch:{ IOException -> 0x003d, RuntimeException -> 0x0025 }
        r2.run();	 Catch:{ IOException -> 0x003d, RuntimeException -> 0x0025 }
        goto L_0x0055;
    L_0x0025:
        r1 = move-exception;
        r2 = TAG;	 Catch:{ all -> 0x0057 }
        r3 = "Could not start recorder";
        android.util.Log.d(r2, r3, r1);	 Catch:{ all -> 0x0057 }
        r2 = r4.listenerList;	 Catch:{ all -> 0x0057 }
        r2.remove(r5);	 Catch:{ all -> 0x0057 }
        r2 = new org.catrobat.catroid.soundrecorder.SoundRecorder;	 Catch:{ all -> 0x0057 }
        r3 = "/dev/null";
        r2.<init>(r3);	 Catch:{ all -> 0x0057 }
        r4.recorder = r2;	 Catch:{ all -> 0x0057 }
        monitor-exit(r4);
        return r0;
    L_0x003d:
        r1 = move-exception;
        r2 = TAG;	 Catch:{ all -> 0x0057 }
        r3 = "Could not start recorder";
        android.util.Log.d(r2, r3, r1);	 Catch:{ all -> 0x0057 }
        r2 = r4.listenerList;	 Catch:{ all -> 0x0057 }
        r2.remove(r5);	 Catch:{ all -> 0x0057 }
        r2 = new org.catrobat.catroid.soundrecorder.SoundRecorder;	 Catch:{ all -> 0x0057 }
        r3 = "/dev/null";
        r2.<init>(r3);	 Catch:{ all -> 0x0057 }
        r4.recorder = r2;	 Catch:{ all -> 0x0057 }
        monitor-exit(r4);
        return r0;
    L_0x0055:
        monitor-exit(r4);
        return r1;
    L_0x0057:
        r5 = move-exception;
        monitor-exit(r4);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.formulaeditor.SensorLoudness.registerListener(org.catrobat.catroid.formulaeditor.SensorCustomEventListener):boolean");
    }

    public synchronized void unregisterListener(SensorCustomEventListener listener) {
        if (this.listenerList.contains(listener)) {
            this.listenerList.remove(listener);
            if (this.listenerList.size() == 0) {
                this.handler.removeCallbacks(this.statusChecker);
                if (this.recorder.isRecording()) {
                    try {
                        this.recorder.stop();
                    } catch (IOException ioException) {
                        Log.d(TAG, "Could not stop recorder", ioException);
                    }
                    this.recorder = new SoundRecorder("/dev/null");
                }
                this.lastValue = 0.0f;
            }
        }
    }

    @VisibleForTesting
    public void setSoundRecorder(SoundRecorder soundRecorder) {
        this.recorder = soundRecorder;
    }

    @VisibleForTesting
    public SoundRecorder getSoundRecorder() {
        return this.recorder;
    }
}
