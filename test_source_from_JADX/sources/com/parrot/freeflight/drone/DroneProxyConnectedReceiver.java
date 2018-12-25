package com.parrot.freeflight.drone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DroneProxyConnectedReceiver extends BroadcastReceiver {
    private DroneProxyConnectedReceiverDelegate delegate;

    public DroneProxyConnectedReceiver(DroneProxyConnectedReceiverDelegate delegate) {
        this.delegate = delegate;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.delegate != null) {
            this.delegate.onToolConnected();
        }
    }
}
