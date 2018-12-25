package com.parrot.arsdk.arnetwork;

/* compiled from: ARNetworkManager */
class SendingRunnable implements Runnable {
    long nativeManager;

    private static native int nativeSendingThreadRun(long j);

    SendingRunnable(long managerPtr) {
        this.nativeManager = managerPtr;
    }

    public void run() {
        nativeSendingThreadRun(this.nativeManager);
    }
}
