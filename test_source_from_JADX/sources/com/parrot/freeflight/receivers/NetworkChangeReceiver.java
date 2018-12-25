package com.parrot.freeflight.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private NetworkChangeReceiverDelegate delegate;

    public NetworkChangeReceiver(NetworkChangeReceiverDelegate delegate) {
        this.delegate = delegate;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.delegate != null) {
            this.delegate.onNetworkChanged((NetworkInfo) intent.getParcelableExtra("networkInfo"));
        }
    }
}
