package com.parrot.arsdk.arcontroller;

public class ARFeatureControllerInfo {
    private static String TAG = "ARFeatureControllerInfo";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendBarometer(long j, float f, double d);

    private native int nativeSendGps(long j, double d, double d2, float f, float f2, float f3, float f4, float f5, float f6, double d3);

    private native int nativeSetBarometer(long j, float f, double d);

    private native int nativeSetBarometerPressure(long j, float f);

    private native int nativeSetBarometerTimestamp(long j, double d);

    private native int nativeSetGps(long j, double d, double d2, float f, float f2, float f3, float f4, float f5, float f6, double d3);

    private native int nativeSetGpsAltitude(long j, float f);

    private native int nativeSetGpsDownSpeed(long j, float f);

    private native int nativeSetGpsEastSpeed(long j, float f);

    private native int nativeSetGpsHorizontalAccuracy(long j, float f);

    private native int nativeSetGpsLatitude(long j, double d);

    private native int nativeSetGpsLongitude(long j, double d);

    private native int nativeSetGpsNorthSpeed(long j, float f);

    private native int nativeSetGpsTimestamp(long j, double d);

    private native int nativeSetGpsVerticalAccuracy(long j, float f);

    public ARFeatureControllerInfo(long nativeFeature) {
        if (nativeFeature != 0) {
            this.jniFeature = nativeFeature;
            this.initOk = true;
        }
    }

    public void dispose() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                this.jniFeature = 0;
                this.initOk = false;
            }
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM sendGps(double r19, double r21, float r23, float r24, float r25, float r26, float r27, float r28, double r29) {
        /*
        r18 = this;
        r14 = r18;
        r16 = com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        monitor-enter(r18);
        r1 = r14.initOk;	 Catch:{ all -> 0x0031 }
        r2 = 1;
        if (r1 != r2) goto L_0x002d;
    L_0x000a:
        r2 = r14.jniFeature;	 Catch:{ all -> 0x0031 }
        r1 = r14;
        r4 = r19;
        r6 = r21;
        r8 = r23;
        r9 = r24;
        r10 = r25;
        r11 = r26;
        r12 = r27;
        r13 = r28;
        r17 = r14;
        r14 = r29;
        r1 = r1.nativeSendGps(r2, r4, r6, r8, r9, r10, r11, r12, r13, r14);	 Catch:{ all -> 0x0037 }
        r2 = com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM.getFromValue(r1);	 Catch:{ all -> 0x0037 }
        r1 = r2;
        r16 = r1;
        goto L_0x002f;
    L_0x002d:
        r17 = r14;
    L_0x002f:
        monitor-exit(r18);	 Catch:{ all -> 0x0037 }
        return r16;
    L_0x0031:
        r0 = move-exception;
        r17 = r14;
    L_0x0034:
        r1 = r0;
        monitor-exit(r18);	 Catch:{ all -> 0x0037 }
        throw r1;
    L_0x0037:
        r0 = move-exception;
        goto L_0x0034;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.parrot.arsdk.arcontroller.ARFeatureControllerInfo.sendGps(double, double, float, float, float, float, float, float, double):com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM setGps(double r19, double r21, float r23, float r24, float r25, float r26, float r27, float r28, double r29) {
        /*
        r18 = this;
        r14 = r18;
        r16 = com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        monitor-enter(r18);
        r1 = r14.initOk;	 Catch:{ all -> 0x0031 }
        r2 = 1;
        if (r1 != r2) goto L_0x002d;
    L_0x000a:
        r2 = r14.jniFeature;	 Catch:{ all -> 0x0031 }
        r1 = r14;
        r4 = r19;
        r6 = r21;
        r8 = r23;
        r9 = r24;
        r10 = r25;
        r11 = r26;
        r12 = r27;
        r13 = r28;
        r17 = r14;
        r14 = r29;
        r1 = r1.nativeSetGps(r2, r4, r6, r8, r9, r10, r11, r12, r13, r14);	 Catch:{ all -> 0x0037 }
        r2 = com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM.getFromValue(r1);	 Catch:{ all -> 0x0037 }
        r1 = r2;
        r16 = r1;
        goto L_0x002f;
    L_0x002d:
        r17 = r14;
    L_0x002f:
        monitor-exit(r18);	 Catch:{ all -> 0x0037 }
        return r16;
    L_0x0031:
        r0 = move-exception;
        r17 = r14;
    L_0x0034:
        r1 = r0;
        monitor-exit(r18);	 Catch:{ all -> 0x0037 }
        throw r1;
    L_0x0037:
        r0 = move-exception;
        goto L_0x0034;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.parrot.arsdk.arcontroller.ARFeatureControllerInfo.setGps(double, double, float, float, float, float, float, float, double):com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM");
    }

    public ARCONTROLLER_ERROR_ENUM setGpsLatitude(double _latitude) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetGpsLatitude(this.jniFeature, _latitude));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setGpsLongitude(double _longitude) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetGpsLongitude(this.jniFeature, _longitude));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setGpsAltitude(float _altitude) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetGpsAltitude(this.jniFeature, _altitude));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setGpsHorizontalAccuracy(float _horizontal_accuracy) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetGpsHorizontalAccuracy(this.jniFeature, _horizontal_accuracy));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setGpsVerticalAccuracy(float _vertical_accuracy) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetGpsVerticalAccuracy(this.jniFeature, _vertical_accuracy));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setGpsNorthSpeed(float _north_speed) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetGpsNorthSpeed(this.jniFeature, _north_speed));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setGpsEastSpeed(float _east_speed) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetGpsEastSpeed(this.jniFeature, _east_speed));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setGpsDownSpeed(float _down_speed) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetGpsDownSpeed(this.jniFeature, _down_speed));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setGpsTimestamp(double _timestamp) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetGpsTimestamp(this.jniFeature, _timestamp));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendBarometer(float _pressure, double _timestamp) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendBarometer(this.jniFeature, _pressure, _timestamp));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setBarometer(float _pressure, double _timestamp) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetBarometer(this.jniFeature, _pressure, _timestamp));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setBarometerPressure(float _pressure) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetBarometerPressure(this.jniFeature, _pressure));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setBarometerTimestamp(double _timestamp) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetBarometerTimestamp(this.jniFeature, _timestamp));
            }
        }
        return error;
    }
}
