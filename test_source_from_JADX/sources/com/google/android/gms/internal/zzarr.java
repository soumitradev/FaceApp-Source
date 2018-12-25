package com.google.android.gms.internal;

final class zzarr implements Runnable {
    private /* synthetic */ zzasz zza;
    private /* synthetic */ zzarq zzb;

    zzarr(zzarq zzarq, zzasz zzasz) {
        this.zzb = zzarq;
        this.zza = zzasz;
    }

    public final void run() {
        if (!this.zzb.zza.zzb()) {
            this.zzb.zza.zzc("Connected to service after a timeout");
            this.zzb.zza.zza(this.zza);
        }
    }
}
