package com.google.android.gms.internal;

final class zzk implements Runnable {
    private final zzr zza;
    private final zzx zzb;
    private final Runnable zzc;

    public zzk(zzi zzi, zzr zzr, zzx zzx, Runnable runnable) {
        this.zza = zzr;
        this.zzb = zzx;
        this.zzc = runnable;
    }

    public final void run() {
        this.zza.zze();
        if ((this.zzb.zzc == null ? 1 : null) != null) {
            this.zza.zza(this.zzb.zza);
        } else {
            this.zza.zza(this.zzb.zzc);
        }
        if (this.zzb.zzd) {
            this.zza.zza("intermediate-response");
        } else {
            this.zza.zzb("done");
        }
        if (this.zzc != null) {
            this.zzc.run();
        }
    }
}
