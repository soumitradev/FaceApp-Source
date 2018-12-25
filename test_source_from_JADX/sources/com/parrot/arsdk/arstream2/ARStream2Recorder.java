package com.parrot.arsdk.arstream2;

import android.util.Log;

public class ARStream2Recorder {
    private static final String TAG = ARStream2Recorder.class.getSimpleName();
    private final long nativeRef;
    private final String recordFileName;

    private native boolean nativeStart(long j, String str);

    private native boolean nativeStop(long j);

    public ARStream2Recorder(ARStream2Manager manager, String recordFileName) {
        this.nativeRef = manager.getNativeRef();
        this.recordFileName = recordFileName;
    }

    private boolean isValid() {
        return this.nativeRef != 0;
    }

    public void start() {
        if (isValid()) {
            nativeStart(this.nativeRef, this.recordFileName);
        } else {
            Log.e(TAG, "unable to start, recorder is not valid!");
        }
    }

    public void stop() {
        if (isValid()) {
            nativeStop(this.nativeRef);
        } else {
            Log.e(TAG, "unable to stop, recorder is not valid!");
        }
    }
}
