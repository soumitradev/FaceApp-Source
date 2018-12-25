package com.parrot.freeflight.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.parrot.freeflight.service.DroneControlService;

public class DroneFlyingStateReceiver extends BroadcastReceiver {
    private DroneFlyingStateReceiverDelegate delegate;

    public DroneFlyingStateReceiver(DroneFlyingStateReceiverDelegate delegate) {
        this.delegate = delegate;
    }

    public void onReceive(Context context, Intent intent) {
        boolean flying = intent.getBooleanExtra(DroneControlService.EXTRA_DRONE_FLYING, false);
        if (this.delegate != null) {
            this.delegate.onDroneFlyingStateChanged(flying);
        }
    }
}
