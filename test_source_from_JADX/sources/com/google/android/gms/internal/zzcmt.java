package com.google.android.gms.internal;

final class zzcmt implements Runnable {
    private /* synthetic */ zzcjb zza;
    private /* synthetic */ zzcms zzb;

    zzcmt(zzcms zzcms, zzcjb zzcjb) {
        this.zzb = zzcms;
        this.zza = zzcjb;
    }

    public final void run() {
        synchronized (this.zzb) {
            this.zzb.zzb = false;
            if (!this.zzb.zza.zzy()) {
                this.zzb.zza.zzt().zzae().zza("Connected to service");
                this.zzb.zza.zza(this.zza);
            }
        }
    }
}
