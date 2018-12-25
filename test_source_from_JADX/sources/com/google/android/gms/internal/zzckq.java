package com.google.android.gms.internal;

final class zzckq implements Runnable {
    private /* synthetic */ zzcii zza;
    private /* synthetic */ zzcif zzb;
    private /* synthetic */ zzcko zzc;

    zzckq(zzcko zzcko, zzcii zzcii, zzcif zzcif) {
        this.zzc = zzcko;
        this.zza = zzcii;
        this.zzb = zzcif;
    }

    public final void run() {
        this.zzc.zza.zzag();
        this.zzc.zza.zzb(this.zza, this.zzb);
    }
}
