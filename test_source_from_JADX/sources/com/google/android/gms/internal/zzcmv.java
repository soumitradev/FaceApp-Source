package com.google.android.gms.internal;

final class zzcmv implements Runnable {
    private /* synthetic */ zzcjb zza;
    private /* synthetic */ zzcms zzb;

    zzcmv(zzcms zzcms, zzcjb zzcjb) {
        this.zzb = zzcms;
        this.zza = zzcjb;
    }

    public final void run() {
        synchronized (this.zzb) {
            this.zzb.zzb = false;
            if (!this.zzb.zza.zzy()) {
                this.zzb.zza.zzt().zzad().zza("Connected to remote service");
                this.zzb.zza.zza(this.zza);
            }
        }
    }
}
