package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

final class zzad extends BroadcastReceiver {
    private zzac zza;

    public zzad(zzac zzac) {
        this.zza = zzac;
    }

    public final void onReceive(Context context, Intent intent) {
        if (this.zza != null && this.zza.zzb()) {
            if (FirebaseInstanceId.zze()) {
                Log.d("FirebaseInstanceId", "Connectivity changed. Starting background sync.");
            }
            FirebaseInstanceId.zza(this.zza, 0);
            this.zza.zza().unregisterReceiver(this);
            this.zza = null;
        }
    }

    public final void zza() {
        if (FirebaseInstanceId.zze()) {
            Log.d("FirebaseInstanceId", "Connectivity change received registered");
        }
        this.zza.zza().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
}
