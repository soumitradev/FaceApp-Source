package com.google.android.gms.internal;

final class zzclq implements Runnable {
    private /* synthetic */ long zza;
    private /* synthetic */ zzclk zzb;

    zzclq(zzclk zzclk, long j) {
        this.zzb = zzclk;
        this.zza = j;
    }

    public final void run() {
        this.zzb.zzu().zzj.zza(this.zza);
        this.zzb.zzt().zzad().zza("Minimum session duration set", Long.valueOf(this.zza));
    }
}
