package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_GPSSETTINGS_HOMETYPE_TYPE_ENUM;
import com.parrot.arsdk.arcommands.C1399x62185770;

public class ARFeatureGeneric {
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_GENERIC_DRONESETTINGSCHANGED_SETTINGS */
    public static String f1515x31ff760a;
    private static String TAG = "ARFeatureGeneric";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendDefault(long j);

    private native int nativeSendSetDroneSettings(long j, int i, float f, int i2, float f2, int i3, float f3, int i4, byte b, int i5, float f4, int i6, float f5, int i7, float f6, int i8, short s, int i9, ARCOMMANDS_ARDRONE3_GPSSETTINGS_HOMETYPE_TYPE_ENUM arcommands_ardrone3_gpssettings_hometype_type_enum, int i10, C1399x62185770 c1399x62185770, int i11, byte b2);

    private static native String nativeStaticGetKeyGenericDroneSettingsChangedSettings();

    static {
        f1515x31ff760a = "";
        f1515x31ff760a = nativeStaticGetKeyGenericDroneSettingsChangedSettings();
    }

    public ARFeatureGeneric(long nativeFeature) {
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

    public ARCONTROLLER_ERROR_ENUM sendDefault() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendDefault(this.jniFeature));
            }
        }
        return error;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM sendSetDroneSettings(com.parrot.arsdk.arcommands.ARCommandsGenericDroneSettings r30) {
        /*
        r29 = this;
        r15 = r29;
        r26 = com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        monitor-enter(r29);
        r1 = r15.initOk;	 Catch:{ all -> 0x008d }
        r2 = 1;
        if (r1 != r2) goto L_0x0089;
    L_0x000a:
        r2 = r15.jniFeature;	 Catch:{ all -> 0x008d }
        r4 = r30.getArdrone3MaxAltitudeIsSet();	 Catch:{ all -> 0x008d }
        r5 = r30.getArdrone3MaxAltitudeCurrent();	 Catch:{ all -> 0x008d }
        r6 = r30.getArdrone3MaxTiltIsSet();	 Catch:{ all -> 0x008d }
        r7 = r30.getArdrone3MaxTiltCurrent();	 Catch:{ all -> 0x008d }
        r8 = r30.getArdrone3MaxDistanceIsSet();	 Catch:{ all -> 0x008d }
        r9 = r30.getArdrone3MaxDistanceValue();	 Catch:{ all -> 0x008d }
        r10 = r30.getArdrone3NoFlyOverMaxDistanceIsSet();	 Catch:{ all -> 0x008d }
        r11 = r30.getArdrone3NoFlyOverMaxDistanceShouldNotFlyOver();	 Catch:{ all -> 0x008d }
        r12 = r30.getArdrone3MaxVerticalSpeedIsSet();	 Catch:{ all -> 0x008d }
        r13 = r30.getArdrone3MaxVerticalSpeedCurrent();	 Catch:{ all -> 0x008d }
        r14 = r30.getArdrone3MaxRotationSpeedIsSet();	 Catch:{ all -> 0x008d }
        r16 = r30.getArdrone3MaxRotationSpeedCurrent();	 Catch:{ all -> 0x008d }
        r17 = r30.getArdrone3MaxPitchRollRotationSpeedIsSet();	 Catch:{ all -> 0x008d }
        r18 = r30.getArdrone3MaxPitchRollRotationSpeedCurrent();	 Catch:{ all -> 0x008d }
        r19 = r30.getArdrone3ReturnHomeDelayIsSet();	 Catch:{ all -> 0x008d }
        r20 = r30.getArdrone3ReturnHomeDelayDelay();	 Catch:{ all -> 0x008d }
        r21 = r30.getArdrone3HomeTypeIsSet();	 Catch:{ all -> 0x008d }
        r22 = r30.getArdrone3HomeTypeType();	 Catch:{ all -> 0x008d }
        r23 = r30.getArdrone3VideoStabilizationModeIsSet();	 Catch:{ all -> 0x008d }
        r24 = r30.getArdrone3VideoStabilizationModeMode();	 Catch:{ all -> 0x008d }
        r25 = r30.getArdrone3BankedTurnIsSet();	 Catch:{ all -> 0x008d }
        r27 = r30.getArdrone3BankedTurnValue();	 Catch:{ all -> 0x008d }
        r1 = r15;
        r28 = r15;
        r15 = r16;
        r16 = r17;
        r17 = r18;
        r18 = r19;
        r19 = r20;
        r20 = r21;
        r21 = r22;
        r22 = r23;
        r23 = r24;
        r24 = r25;
        r25 = r27;
        r1 = r1.nativeSendSetDroneSettings(r2, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25);	 Catch:{ all -> 0x0093 }
        r2 = com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM.getFromValue(r1);	 Catch:{ all -> 0x0093 }
        r1 = r2;
        r26 = r1;
        goto L_0x008b;
    L_0x0089:
        r28 = r15;
    L_0x008b:
        monitor-exit(r29);	 Catch:{ all -> 0x0093 }
        return r26;
    L_0x008d:
        r0 = move-exception;
        r28 = r15;
    L_0x0090:
        r1 = r0;
        monitor-exit(r29);	 Catch:{ all -> 0x0093 }
        throw r1;
    L_0x0093:
        r0 = move-exception;
        goto L_0x0090;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.parrot.arsdk.arcontroller.ARFeatureGeneric.sendSetDroneSettings(com.parrot.arsdk.arcommands.ARCommandsGenericDroneSettings):com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM");
    }
}
