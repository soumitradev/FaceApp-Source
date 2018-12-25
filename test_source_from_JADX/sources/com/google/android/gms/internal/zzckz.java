package com.google.android.gms.internal;

final class zzckz implements Runnable {
    private /* synthetic */ zzcix zza;
    private /* synthetic */ zzcif zzb;
    private /* synthetic */ zzcko zzc;

    zzckz(zzcko zzcko, zzcix zzcix, zzcif zzcif) {
        this.zzc = zzcko;
        this.zza = zzcix;
        this.zzb = zzcif;
    }

    public final void run() {
        this.zzc.zza.zzag();
        this.zzc.zza.zza(this.zza, this.zzb);
    }
}
