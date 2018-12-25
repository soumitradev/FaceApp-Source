package com.facebook.internal;

import android.content.Intent;
import java.util.UUID;

public class AppCall {
    private static AppCall currentPendingCall;
    private UUID callId;
    private int requestCode;
    private Intent requestIntent;

    public static AppCall getCurrentPendingCall() {
        return currentPendingCall;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.facebook.internal.AppCall finishPendingCall(java.util.UUID r4, int r5) {
        /*
        r0 = com.facebook.internal.AppCall.class;
        monitor-enter(r0);
        r1 = getCurrentPendingCall();	 Catch:{ all -> 0x0022 }
        r2 = 0;
        if (r1 == 0) goto L_0x0020;
    L_0x000a:
        r3 = r1.getCallId();	 Catch:{ all -> 0x0022 }
        r3 = r3.equals(r4);	 Catch:{ all -> 0x0022 }
        if (r3 == 0) goto L_0x0020;
    L_0x0014:
        r3 = r1.getRequestCode();	 Catch:{ all -> 0x0022 }
        if (r3 == r5) goto L_0x001b;
    L_0x001a:
        goto L_0x0020;
    L_0x001b:
        setCurrentPendingCall(r2);	 Catch:{ all -> 0x0022 }
        monitor-exit(r0);
        return r1;
    L_0x0020:
        monitor-exit(r0);
        return r2;
    L_0x0022:
        r4 = move-exception;
        monitor-exit(r0);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.AppCall.finishPendingCall(java.util.UUID, int):com.facebook.internal.AppCall");
    }

    private static synchronized boolean setCurrentPendingCall(AppCall appCall) {
        boolean z;
        synchronized (AppCall.class) {
            AppCall oldAppCall = getCurrentPendingCall();
            currentPendingCall = appCall;
            z = oldAppCall != null;
        }
        return z;
    }

    public AppCall(int requestCode) {
        this(requestCode, UUID.randomUUID());
    }

    public AppCall(int requestCode, UUID callId) {
        this.callId = callId;
        this.requestCode = requestCode;
    }

    public Intent getRequestIntent() {
        return this.requestIntent;
    }

    public UUID getCallId() {
        return this.callId;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public void setRequestIntent(Intent requestIntent) {
        this.requestIntent = requestIntent;
    }

    public boolean setPending() {
        return setCurrentPendingCall(this);
    }
}
