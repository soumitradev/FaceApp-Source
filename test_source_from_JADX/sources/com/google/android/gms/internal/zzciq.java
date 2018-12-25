package com.google.android.gms.internal;

final class zzciq implements Runnable {
    private /* synthetic */ zzckj zza;
    private /* synthetic */ zzcip zzb;

    zzciq(zzcip zzcip, zzckj zzckj) {
        this.zzb = zzcip;
        this.zza = zzckj;
    }

    public final void run() {
        this.zza.zzh();
        if (zzcke.zzy()) {
            this.zzb.zza.zzh().zza((Runnable) this);
            return;
        }
        boolean zzb = this.zzb.zzb();
        this.zzb.zzd = 0;
        if (zzb && this.zzb.zze) {
            this.zzb.zza();
        }
    }
}
