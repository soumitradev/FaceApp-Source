package com.google.android.gms.internal;

final class zzcnb implements Runnable {
    private /* synthetic */ zzckj zza;
    private /* synthetic */ Runnable zzb;

    zzcnb(zzcmy zzcmy, zzckj zzckj, Runnable runnable) {
        this.zza = zzckj;
        this.zzb = runnable;
    }

    public final void run() {
        this.zza.zzag();
        this.zza.zza(this.zzb);
        this.zza.zzae();
    }
}
