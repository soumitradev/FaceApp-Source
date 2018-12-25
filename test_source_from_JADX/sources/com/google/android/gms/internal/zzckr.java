package com.google.android.gms.internal;

final class zzckr implements Runnable {
    private /* synthetic */ zzcii zza;
    private /* synthetic */ zzcif zzb;
    private /* synthetic */ zzcko zzc;

    zzckr(zzcko zzcko, zzcii zzcii, zzcif zzcif) {
        this.zzc = zzcko;
        this.zza = zzcii;
        this.zzb = zzcif;
    }

    public final void run() {
        this.zzc.zza.zzag();
        this.zzc.zza.zza(this.zza, this.zzb);
    }
}
