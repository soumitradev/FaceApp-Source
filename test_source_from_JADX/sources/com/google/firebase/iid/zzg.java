package com.google.firebase.iid;

import android.util.Log;

final class zzg implements Runnable {
    private /* synthetic */ zzd zza;
    private /* synthetic */ zzf zzb;

    zzg(zzf zzf, zzd zzd) {
        this.zzb = zzf;
        this.zza = zzd;
    }

    public final void run() {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "bg processing of the intent starting now");
        }
        this.zzb.zza.zzc(this.zza.zza);
        this.zza.zza();
    }
}
