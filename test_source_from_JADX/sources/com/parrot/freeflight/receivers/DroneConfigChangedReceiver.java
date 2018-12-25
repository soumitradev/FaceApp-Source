package com.parrot.freeflight.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.parrot.freeflight.service.DroneControlService;

public class DroneConfigChangedReceiver extends BroadcastReceiver {
    private DroneConfigChangedReceiverDelegate delegate;

    public DroneConfigChangedReceiver(DroneConfigChangedReceiverDelegate delegate) {
        this.delegate = delegate;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.delegate != null && intent.getAction().equals(DroneControlService.DRONE_CONFIG_STATE_CHANGED_ACTION)) {
            this.delegate.onDroneConfigChanged();
        }
    }
}
