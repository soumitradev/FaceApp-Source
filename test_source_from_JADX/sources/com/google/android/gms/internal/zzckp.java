package com.google.android.gms.internal;

final class zzckp implements Runnable {
    private /* synthetic */ zzcif zza;
    private /* synthetic */ zzcko zzb;

    zzckp(zzcko zzcko, zzcif zzcif) {
        this.zzb = zzcko;
        this.zza = zzcif;
    }

    public final void run() {
        this.zzb.zza.zzag();
        this.zzb.zza.zzb(this.zza);
    }
}
