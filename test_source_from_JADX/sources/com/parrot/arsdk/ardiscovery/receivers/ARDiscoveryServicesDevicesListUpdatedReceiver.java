package com.parrot.arsdk.ardiscovery.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.parrot.arsdk.arsal.ARSALPrint;

public class ARDiscoveryServicesDevicesListUpdatedReceiver extends BroadcastReceiver {
    private static String TAG = "ARDiscoveryServicesDevicesListUpdatedReceiver";
    private ARDiscoveryServicesDevicesListUpdatedReceiverDelegate delegate;

    public ARDiscoveryServicesDevicesListUpdatedReceiver(ARDiscoveryServicesDevicesListUpdatedReceiverDelegate delegate) {
        ARSALPrint.m530d(TAG, "ARDiscoveryServicesDevicesListUpdatedReceiver constructor");
        this.delegate = delegate;
    }

    public void onReceive(Context context, Intent intent) {
        ARSALPrint.m530d(TAG, "onReceive");
        if (this.delegate != null) {
            this.delegate.onServicesDevicesListUpdated();
        }
    }
}
