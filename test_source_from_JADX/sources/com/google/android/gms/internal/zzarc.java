package com.google.android.gms.internal;

final class zzarc implements Runnable {
    private /* synthetic */ String zza;
    private /* synthetic */ Runnable zzb;
    private /* synthetic */ zzaqz zzc;

    zzarc(zzaqz zzaqz, String str, Runnable runnable) {
        this.zzc = zzaqz;
        this.zza = str;
        this.zzb = runnable;
    }

    public final void run() {
        this.zzc.zza.zza(this.zza);
        if (this.zzb != null) {
            this.zzb.run();
        }
    }
}
