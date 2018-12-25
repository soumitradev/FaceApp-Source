package com.parrot.freeflight.service.commands;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.parrot.freeflight.drone.DroneProxy;
import com.parrot.freeflight.drone.DroneProxyConnectionFailedReceiver;
import com.parrot.freeflight.drone.DroneProxyConnectionFailedReceiverDelegate;
import com.parrot.freeflight.drone.DroneProxyDisconnectedReceiver;
import com.parrot.freeflight.drone.DroneProxyDisconnectedReceiverDelegate;
import com.parrot.freeflight.service.DroneControlService;

public class DisconnectCommand extends DroneServiceCommand implements DroneProxyDisconnectedReceiverDelegate, DroneProxyConnectionFailedReceiverDelegate {
    private LocalBroadcastManager bm;
    private DroneProxyConnectionFailedReceiver connFailedReceiver = new DroneProxyConnectionFailedReceiver(this);
    private DroneProxyDisconnectedReceiver disconnectedReceiver = new DroneProxyDisconnectedReceiver(this);
    private DroneProxy droneProxy;

    public DisconnectCommand(DroneControlService context) {
        super(context);
        this.droneProxy = DroneProxy.getInstance(context.getApplicationContext());
        this.bm = LocalBroadcastManager.getInstance(context.getApplicationContext());
    }

    public void execute() {
        registerListeners();
        this.droneProxy.doResume();
        this.droneProxy.doDisconnect();
    }

    public void onToolConnectionFailed(int reason) {
        unregisterListeners();
    }

    public void onToolDisconnected() {
        unregisterListeners();
        this.context.onCommandFinished(this);
    }

    private void registerListeners() {
        this.bm.registerReceiver(this.disconnectedReceiver, new IntentFilter(DroneProxy.DRONE_PROXY_DISCONNECTED_ACTION));
        this.bm.registerReceiver(this.connFailedReceiver, new IntentFilter(DroneProxy.DRONE_PROXY_CONNECTION_FAILED_ACTION));
    }

    private void unregisterListeners() {
        this.bm.unregisterReceiver(this.disconnectedReceiver);
        this.bm.unregisterReceiver(this.connFailedReceiver);
    }
}
