package com.parrot.arsdk.arnetwork;

/* compiled from: ARNetworkManager */
class ReceivingRunnable implements Runnable {
    long nativeManager;

    private static native int nativeReceivingThreadRun(long j);

    ReceivingRunnable(long managerPtr) {
        this.nativeManager = managerPtr;
    }

    public void run() {
        nativeReceivingThreadRun(this.nativeManager);
    }
}
