package com.parrot.freeflight.service.commands;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.parrot.freeflight.drone.DroneProxy;
import com.parrot.freeflight.drone.DroneProxy.EVideoRecorderCapability;
import com.parrot.freeflight.drone.DroneProxyConnectedReceiver;
import com.parrot.freeflight.drone.DroneProxyConnectedReceiverDelegate;
import com.parrot.freeflight.drone.DroneProxyConnectionFailedReceiver;
import com.parrot.freeflight.drone.DroneProxyConnectionFailedReceiverDelegate;
import com.parrot.freeflight.drone.DroneProxyDisconnectedReceiver;
import com.parrot.freeflight.drone.DroneProxyDisconnectedReceiverDelegate;
import com.parrot.freeflight.service.DroneControlService;
import com.parrot.freeflight.utils.DeviceCapabilitiesUtils;

public class ConnectCommand extends DroneServiceCommand implements DroneProxyConnectedReceiverDelegate, DroneProxyDisconnectedReceiverDelegate, DroneProxyConnectionFailedReceiverDelegate {
    public static final int CONNECTED = 0;
    public static final int CONNECTION_FAILED = 2;
    public static final int DISCONNECTED = 1;
    private LocalBroadcastManager bm;
    private DroneProxyConnectionFailedReceiver connFailedReceiver = new DroneProxyConnectionFailedReceiver(this);
    private DroneProxyConnectedReceiver connectedReceiver = new DroneProxyConnectedReceiver(this);
    private DroneProxyDisconnectedReceiver disconnectedReceiver = new DroneProxyDisconnectedReceiver(this);
    private DroneProxy droneProxy;
    private int result;

    public ConnectCommand(DroneControlService context) {
        super(context);
        this.droneProxy = DroneProxy.getInstance(context.getApplicationContext());
        this.bm = LocalBroadcastManager.getInstance(context.getApplicationContext());
        this.result = 1;
    }

    public void execute() {
        this.bm.registerReceiver(this.connectedReceiver, new IntentFilter(DroneProxy.DRONE_PROXY_CONNECTED_ACTION));
        this.bm.registerReceiver(this.disconnectedReceiver, new IntentFilter(DroneProxy.DRONE_PROXY_DISCONNECTED_ACTION));
        this.bm.registerReceiver(this.connFailedReceiver, new IntentFilter(DroneProxy.DRONE_PROXY_CONNECTION_FAILED_ACTION));
        EVideoRecorderCapability recorderCapability = DeviceCapabilitiesUtils.getMaxSupportedVideoRes();
        if (recorderCapability == EVideoRecorderCapability.NOT_SUPPORTED) {
            recorderCapability = EVideoRecorderCapability.VIDEO_360P;
        }
        this.droneProxy.doConnect(this.context, recorderCapability);
    }

    public void onToolConnected() {
        onCommandFinished(0);
    }

    public void onToolConnectionFailed(int reason) {
        onCommandFinished(2);
    }

    public void onToolDisconnected() {
        onCommandFinished(1);
    }

    protected void onCommandFinished(int result) {
        this.bm.unregisterReceiver(this.connectedReceiver);
        this.bm.unregisterReceiver(this.disconnectedReceiver);
        this.bm.unregisterReceiver(this.connFailedReceiver);
        this.result = result;
        this.context.onCommandFinished(this);
    }

    public int getResult() {
        return this.result;
    }
}
