package com.google.android.gms.internal;

final class zze implements Runnable {
    private /* synthetic */ zzr zza;
    private /* synthetic */ zzd zzb;

    zze(zzd zzd, zzr zzr) {
        this.zzb = zzd;
        this.zza = zzr;
    }

    public final void run() {
        try {
            this.zzb.zzc.put(this.zza);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
