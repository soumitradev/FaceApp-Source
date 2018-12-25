package com.google.android.gms.common.api.internal;

final class zzdd implements Runnable {
    private /* synthetic */ LifecycleCallback zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ zzdc zzc;

    zzdd(zzdc zzdc, LifecycleCallback lifecycleCallback, String str) {
        this.zzc = zzdc;
        this.zza = lifecycleCallback;
        this.zzb = str;
    }

    public final void run() {
        if (this.zzc.zzc > 0) {
            this.zza.zza(this.zzc.zzd != null ? this.zzc.zzd.getBundle(this.zzb) : null);
        }
        if (this.zzc.zzc >= 2) {
            this.zza.zza();
        }
        if (this.zzc.zzc >= 3) {
            this.zza.zze();
        }
        if (this.zzc.zzc >= 4) {
            this.zza.zzb();
        }
        if (this.zzc.zzc >= 5) {
            this.zza.zzh();
        }
    }
}
