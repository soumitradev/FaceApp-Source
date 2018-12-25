package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;

final class zze implements Runnable {
    private /* synthetic */ Intent zza;
    private /* synthetic */ zzd zzb;

    zze(zzd zzd, Intent intent) {
        this.zzb = zzd;
        this.zza = intent;
    }

    public final void run() {
        String action = this.zza.getAction();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(action).length() + 61);
        stringBuilder.append("Service took too long to process intent: ");
        stringBuilder.append(action);
        stringBuilder.append(" App may get closed.");
        Log.w("EnhancedIntentService", stringBuilder.toString());
        this.zzb.zza();
    }
}
