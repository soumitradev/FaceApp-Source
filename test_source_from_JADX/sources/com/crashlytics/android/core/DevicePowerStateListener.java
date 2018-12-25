package com.crashlytics.android.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.concurrent.atomic.AtomicBoolean;

class DevicePowerStateListener {
    private static final IntentFilter FILTER_BATTERY_CHANGED = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static final IntentFilter FILTER_POWER_CONNECTED = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
    private static final IntentFilter FILTER_POWER_DISCONNECTED = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
    private final Context context;
    private boolean isPowerConnected;
    private final BroadcastReceiver powerConnectedReceiver;
    private final BroadcastReceiver powerDisconnectedReceiver;
    private final AtomicBoolean receiversRegistered;

    /* renamed from: com.crashlytics.android.core.DevicePowerStateListener$1 */
    class C03891 extends BroadcastReceiver {
        C03891() {
        }

        public void onReceive(Context context, Intent intent) {
            DevicePowerStateListener.this.isPowerConnected = true;
        }
    }

    /* renamed from: com.crashlytics.android.core.DevicePowerStateListener$2 */
    class C03902 extends BroadcastReceiver {
        C03902() {
        }

        public void onReceive(Context context, Intent intent) {
            DevicePowerStateListener.this.isPowerConnected = false;
        }
    }

    public DevicePowerStateListener(Context context) {
        boolean z;
        this.context = context;
        Intent statusIntent = context.registerReceiver(null, FILTER_BATTERY_CHANGED);
        int status = -1;
        if (statusIntent != null) {
            status = statusIntent.getIntExtra("status", -1);
        }
        if (status != 2) {
            if (status != 5) {
                z = false;
                this.isPowerConnected = z;
                this.powerConnectedReceiver = new C03891();
                this.powerDisconnectedReceiver = new C03902();
                context.registerReceiver(this.powerConnectedReceiver, FILTER_POWER_CONNECTED);
                context.registerReceiver(this.powerDisconnectedReceiver, FILTER_POWER_DISCONNECTED);
                this.receiversRegistered = new AtomicBoolean(true);
            }
        }
        z = true;
        this.isPowerConnected = z;
        this.powerConnectedReceiver = new C03891();
        this.powerDisconnectedReceiver = new C03902();
        context.registerReceiver(this.powerConnectedReceiver, FILTER_POWER_CONNECTED);
        context.registerReceiver(this.powerDisconnectedReceiver, FILTER_POWER_DISCONNECTED);
        this.receiversRegistered = new AtomicBoolean(true);
    }

    public boolean isPowerConnected() {
        return this.isPowerConnected;
    }

    public void dispose() {
        if (this.receiversRegistered.getAndSet(false)) {
            this.context.unregisterReceiver(this.powerConnectedReceiver);
            this.context.unregisterReceiver(this.powerDisconnectedReceiver);
        }
    }
}
