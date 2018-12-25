package com.google.android.gms.internal;

final class zzclc implements Runnable {
    private /* synthetic */ zzcnl zza;
    private /* synthetic */ zzcif zzb;
    private /* synthetic */ zzcko zzc;

    zzclc(zzcko zzcko, zzcnl zzcnl, zzcif zzcif) {
        this.zzc = zzcko;
        this.zza = zzcnl;
        this.zzb = zzcif;
    }

    public final void run() {
        this.zzc.zza.zzag();
        this.zzc.zza.zzb(this.zza, this.zzb);
    }
}
