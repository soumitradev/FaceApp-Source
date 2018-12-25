package com.google.android.gms.internal;

final class zzclf implements Runnable {
    private /* synthetic */ zzcif zza;
    private /* synthetic */ zzcko zzb;

    zzclf(zzcko zzcko, zzcif zzcif) {
        this.zzb = zzcko;
        this.zza = zzcif;
    }

    public final void run() {
        this.zzb.zza.zzag();
        this.zzb.zza.zzc(this.zza);
    }
}
