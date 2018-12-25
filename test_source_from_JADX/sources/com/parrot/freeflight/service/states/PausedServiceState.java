package com.parrot.freeflight.service.states;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.parrot.freeflight.drone.DroneProxy;
import com.parrot.freeflight.drone.DroneProxyConnectedReceiverDelegate;
import com.parrot.freeflight.drone.DroneProxyConnectionFailedReceiver;
import com.parrot.freeflight.drone.DroneProxyConnectionFailedReceiverDelegate;
import com.parrot.freeflight.drone.DroneProxyDisconnectedReceiver;
import com.parrot.freeflight.drone.DroneProxyDisconnectedReceiverDelegate;
import com.parrot.freeflight.service.DroneControlService;
import com.parrot.freeflight.service.ServiceStateBase;
import com.parrot.freeflight.service.commands.DisconnectCommand;
import com.parrot.freeflight.service.commands.DroneServiceCommand;
import com.parrot.freeflight.service.commands.ResumeCommand;

public class PausedServiceState extends ServiceStateBase implements DroneProxyConnectedReceiverDelegate, DroneProxyConnectionFailedReceiverDelegate, DroneProxyDisconnectedReceiverDelegate {
    private LocalBroadcastManager bm;
    private DroneProxyConnectionFailedReceiver connFailedReceiver;
    boolean disconnected;
    private DroneProxyDisconnectedReceiver disconnectedReceiver;
    private Object lock = new Object();

    public PausedServiceState(DroneControlService context) {
        super(context);
        this.bm = LocalBroadcastManager.getInstance(context.getApplicationContext());
        this.disconnectedReceiver = new DroneProxyDisconnectedReceiver(this);
        this.connFailedReceiver = new DroneProxyConnectionFailedReceiver(this);
    }

    protected void onPrepare() {
        this.bm.registerReceiver(this.disconnectedReceiver, new IntentFilter(DroneProxy.DRONE_PROXY_DISCONNECTED_ACTION));
        this.bm.registerReceiver(this.connFailedReceiver, new IntentFilter(DroneProxy.DRONE_PROXY_CONNECTION_FAILED_ACTION));
    }

    protected void onFinalize() {
        this.bm.unregisterReceiver(this.disconnectedReceiver);
        this.bm.unregisterReceiver(this.connFailedReceiver);
    }

    public void connect() {
        Log.w(getStateName(), "Can't connect. Already connected. Skipped.");
    }

    public void disconnect() {
        Log.d(getStateName(), "Disconnect");
        this.disconnected = false;
        startCommand(new DisconnectCommand(this.context));
    }

    public void resume() {
        startCommand(new ResumeCommand(this.context));
    }

    public void pause() {
        Log.w(getStateName(), "Can't pause. Already paused. Skipped.");
    }

    public void onToolConnected() {
        Log.w(getStateName(), "onToolConnected() Should not happen here");
    }

    public void onToolConnectionFailed(int reason) {
        onToolDisconnected();
    }

    public void onToolDisconnected() {
        this.disconnected = true;
        synchronized (this.lock) {
            this.lock.notify();
        }
        setState(new DisconnectedServiceState(this.context));
        onDisconnected();
    }

    public void onCommandFinished(DroneServiceCommand command) {
        if (command instanceof ResumeCommand) {
            setState(new ConnectedServiceState(this.context));
            onResumed();
        }
    }
}
