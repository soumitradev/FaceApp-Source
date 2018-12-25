package com.google.android.gms.internal;

final class zzckt implements Runnable {
    private /* synthetic */ zzcii zza;
    private /* synthetic */ zzcko zzb;

    zzckt(zzcko zzcko, zzcii zzcii) {
        this.zzb = zzcko;
        this.zza = zzcii;
    }

    public final void run() {
        this.zzb.zza.zzag();
        this.zzb.zza.zza(this.zza);
    }
}
