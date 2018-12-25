package org.catrobat.catroid.drone.ardrone;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.parrot.freeflight.receivers.DroneConnectionChangeReceiverDelegate;
import com.parrot.freeflight.receivers.DroneConnectionChangedReceiver;
import com.parrot.freeflight.receivers.DroneReadyReceiver;
import com.parrot.freeflight.receivers.DroneReadyReceiverDelegate;
import com.parrot.freeflight.service.DroneControlService;
import com.parrot.freeflight.service.DroneControlService.LocalBinder;
import org.catrobat.catroid.stage.StageResourceInterface;

public class DroneConnection implements StageResourceInterface, DroneReadyReceiverDelegate, DroneConnectionChangeReceiverDelegate {
    private static final String TAG = DroneConnection.class.getSimpleName();
    private Context context = null;
    private DroneConnectionChangedReceiver droneConnectionChangeReceiver = null;
    protected DroneControlService droneControlService = null;
    private BroadcastReceiver droneReadyReceiver = null;
    private ServiceConnection droneServiceConnection = new C18211();

    /* renamed from: org.catrobat.catroid.drone.ardrone.DroneConnection$1 */
    class C18211 implements ServiceConnection {
        C18211() {
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(DroneConnection.TAG, "Drone Connected");
            DroneConnection.this.onDroneServiceConnected(service);
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d(DroneConnection.TAG, "Drone Disconnected");
            DroneConnection.this.droneControlService = null;
        }
    }

    public DroneConnection(Context context) {
        this.context = context;
    }

    public void initialise() throws RuntimeException {
        prepareDroneResources();
    }

    private void prepareDroneResources() throws RuntimeException {
        Log.d(TAG, "prepareResources()");
        this.droneReadyReceiver = new DroneReadyReceiver(this);
        this.droneConnectionChangeReceiver = new DroneConnectionChangedReceiver(this);
        helpBindDroneService();
    }

    public void start() {
        if (this.droneControlService != null) {
            Log.d(TAG, "droneControlService .. onResume");
            this.droneControlService.resume();
            DroneServiceWrapper.getInstance().setDroneService(this.droneControlService);
        }
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this.context);
        manager.registerReceiver(this.droneReadyReceiver, new IntentFilter(DroneControlService.DRONE_STATE_READY_ACTION));
        manager.registerReceiver(this.droneConnectionChangeReceiver, new IntentFilter(DroneControlService.DRONE_CONNECTION_CHANGED_ACTION));
    }

    public void pause() {
        if (this.droneControlService != null) {
            this.droneControlService.pause();
            DroneServiceWrapper.getInstance().setDroneService(null);
        }
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this.context);
        manager.unregisterReceiver(this.droneReadyReceiver);
        manager.unregisterReceiver(this.droneConnectionChangeReceiver);
    }

    public void destroy() {
        helpUnbindDroneService();
    }

    private void onDroneServiceConnected(IBinder service) {
        Log.d(TAG, "onDroneServiceConnected");
        this.droneControlService = ((LocalBinder) service).getService();
        DroneServiceWrapper.getInstance().setDroneService(this.droneControlService);
        this.droneControlService.resume();
        this.droneControlService.requestDroneStatus();
        this.droneControlService.requestConfigUpdate();
        Log.d(TAG, "DroneServiceConnection");
    }

    private void helpUnbindDroneService() {
        if (this.droneControlService != null) {
            this.context.unbindService(this.droneServiceConnection);
            this.droneServiceConnection = null;
            this.droneControlService = null;
        }
    }

    private void helpBindDroneService() throws RuntimeException {
        if (this.droneControlService == null && !this.context.bindService(new Intent(this.context, DroneControlService.class), this.droneServiceConnection, 1)) {
            throw new RuntimeException("Connection to the drone not successful");
        }
    }

    public void onDroneReady() {
        Log.d(TAG, "onDroneReady");
    }

    public void onDroneConnected() {
        Log.d(TAG, "onDroneConnected");
        this.droneControlService.requestConfigUpdate();
    }

    public void onDroneDisconnected() {
        Log.d(TAG, "onDroneDisconnected");
    }
}
