package com.google.android.gms.internal;

final class zzcld implements Runnable {
    private /* synthetic */ zzcnl zza;
    private /* synthetic */ zzcif zzb;
    private /* synthetic */ zzcko zzc;

    zzcld(zzcko zzcko, zzcnl zzcnl, zzcif zzcif) {
        this.zzc = zzcko;
        this.zza = zzcnl;
        this.zzb = zzcif;
    }

    public final void run() {
        this.zzc.zza.zzag();
        this.zzc.zza.zza(this.zza, this.zzb);
    }
}
