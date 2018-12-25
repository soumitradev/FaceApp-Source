package com.google.android.gms.internal;

import android.os.Build.VERSION;

final class zzatn implements Runnable {
    private /* synthetic */ zzatm zza;

    zzatn(zzatm zzatm) {
        this.zza = zzatm;
    }

    public final void run() {
        if (this.zza.zza != null) {
            if (((zzato) this.zza.zzd.zzb).callServiceStopSelfResult(this.zza.zza.intValue())) {
                this.zza.zzb.zzb("Local AnalyticsService processed last dispatch request");
            }
            return;
        }
        if (VERSION.SDK_INT >= 24) {
            this.zza.zzb.zzb("AnalyticsJobService processed last dispatch request");
            ((zzato) this.zza.zzd.zzb).zza(this.zza.zzc, false);
        }
    }
}
