package com.parrot.freeflight.service.states;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.parrot.freeflight.drone.DroneProxy;
import com.parrot.freeflight.drone.DroneProxyConfigChangedReceiver;
import com.parrot.freeflight.drone.DroneProxyConfigChangedReceiverDelegate;
import com.parrot.freeflight.drone.DroneProxyConnectionFailedReceiver;
import com.parrot.freeflight.drone.DroneProxyConnectionFailedReceiverDelegate;
import com.parrot.freeflight.drone.DroneProxyDisconnectedReceiver;
import com.parrot.freeflight.drone.DroneProxyDisconnectedReceiverDelegate;
import com.parrot.freeflight.service.DroneControlService;
import com.parrot.freeflight.service.ServiceStateBase;
import com.parrot.freeflight.service.commands.DisconnectCommand;
import com.parrot.freeflight.service.commands.DroneServiceCommand;
import com.parrot.freeflight.service.commands.PauseCommand;
import com.parrot.freeflight.service.commands.ResumeCommand;

public class ConnectedServiceState extends ServiceStateBase implements DroneProxyDisconnectedReceiverDelegate, DroneProxyConnectionFailedReceiverDelegate, DroneProxyConfigChangedReceiverDelegate {
    private LocalBroadcastManager bm;
    private DroneProxyConfigChangedReceiver configChangeReceiver;
    private DroneProxyConnectionFailedReceiver connFailedReceiver;
    boolean disconnected;
    private DroneProxyDisconnectedReceiver disconnectedReceiver;
    private Object lock = new Object();

    public ConnectedServiceState(DroneControlService context) {
        super(context);
        this.bm = LocalBroadcastManager.getInstance(context.getApplicationContext());
        this.disconnectedReceiver = new DroneProxyDisconnectedReceiver(this);
        this.connFailedReceiver = new DroneProxyConnectionFailedReceiver(this);
        this.configChangeReceiver = new DroneProxyConfigChangedReceiver(this);
    }

    protected void onPrepare() {
        this.bm.registerReceiver(this.disconnectedReceiver, new IntentFilter(DroneProxy.DRONE_PROXY_DISCONNECTED_ACTION));
        this.bm.registerReceiver(this.connFailedReceiver, new IntentFilter(DroneProxy.DRONE_PROXY_CONNECTION_FAILED_ACTION));
        this.bm.registerReceiver(this.configChangeReceiver, new IntentFilter(DroneProxy.DRONE_PROXY_CONFIG_CHANGED_ACTION));
    }

    protected void onFinalize() {
        this.bm.unregisterReceiver(this.disconnectedReceiver);
        this.bm.unregisterReceiver(this.connFailedReceiver);
        this.bm.unregisterReceiver(this.configChangeReceiver);
    }

    public void connect() {
        Log.w(getStateName(), "Already connected. Skipped.");
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
        startCommand(new PauseCommand(this.context));
    }

    public void onToolConnectionFailed(int reason) {
        setState(new DisconnectedServiceState(this.context));
        onDisconnected();
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
            onResumed();
        } else if (command instanceof PauseCommand) {
            setState(new PausedServiceState(this.context));
            onPaused();
        }
    }

    public void onConfigChanged() {
        this.context.onConfigStateChanged();
    }
}
