package com.parrot.arsdk.ardiscovery;

import android.content.Context;
import android.util.Log;
import com.parrot.arsdk.ardiscovery.ARDiscoveryMux.Listener;

public class ARDiscoveryUsbDiscovery {
    private static final String TAG = "ARDiscoveryUsbDiscovery";
    private ARDiscoveryService broadcaster;
    private Context context;
    private ARDiscoveryDeviceService deviceService;
    private ARDiscoveryMux discoveryMux;
    private final Listener listener = new C20241();
    private boolean started;

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryUsbDiscovery$1 */
    class C20241 implements Listener {
        C20241() {
        }

        public void onDeviceAdded(String name, int type, String deviceId) {
            ARDiscoveryUsbDiscovery.this.deviceService = new ARDiscoveryDeviceService(name, new ARDiscoveryDeviceUsbService(deviceId), type);
            if (ARDiscoveryUsbDiscovery.this.broadcaster != null) {
                ARDiscoveryUsbDiscovery.this.broadcaster.broadcastDeviceServiceArrayUpdated();
            }
        }

        public void onDeviceRemoved() {
            ARDiscoveryUsbDiscovery.this.deviceService = null;
            if (ARDiscoveryUsbDiscovery.this.broadcaster != null) {
                ARDiscoveryUsbDiscovery.this.broadcaster.broadcastDeviceServiceArrayUpdated();
            }
        }
    }

    public void open(ARDiscoveryService broadcaster, Context context) {
        this.context = context;
        this.broadcaster = broadcaster;
    }

    public void close() {
        if (this.started) {
            stop();
        }
    }

    public void start() {
        if (!this.started) {
            this.started = true;
            UsbAccessoryMux.get(this.context.getApplicationContext()).setDiscoveryListener(this.listener);
        }
    }

    public void stop() {
        if (this.started) {
            Log.i(TAG, "Stopping USB Discovery");
            this.started = false;
            UsbAccessoryMux.get(this.context.getApplicationContext()).setDiscoveryListener(null);
        }
    }

    public ARDiscoveryDeviceService getDeviceService() {
        if (this.started) {
            return this.deviceService;
        }
        return null;
    }
}
