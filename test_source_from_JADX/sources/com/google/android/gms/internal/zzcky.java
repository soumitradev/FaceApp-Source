package com.google.android.gms.internal;

final class zzcky implements Runnable {
    private /* synthetic */ zzcif zza;
    private /* synthetic */ zzcko zzb;

    zzcky(zzcko zzcko, zzcif zzcif) {
        this.zzb = zzcko;
        this.zza = zzcif;
    }

    public final void run() {
        this.zzb.zza.zzag();
        this.zzb.zza.zza(this.zza);
    }
}
