package com.parrot.freeflight.drone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DroneProxyDisconnectedReceiver extends BroadcastReceiver {
    private DroneProxyDisconnectedReceiverDelegate delegate;

    public DroneProxyDisconnectedReceiver(DroneProxyDisconnectedReceiverDelegate delegate) {
        this.delegate = delegate;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.delegate != null) {
            this.delegate.onToolDisconnected();
        }
    }
}
