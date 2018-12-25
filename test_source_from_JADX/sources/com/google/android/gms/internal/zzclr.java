package com.google.android.gms.internal;

final class zzclr implements Runnable {
    private /* synthetic */ long zza;
    private /* synthetic */ zzclk zzb;

    zzclr(zzclk zzclk, long j) {
        this.zzb = zzclk;
        this.zza = j;
    }

    public final void run() {
        this.zzb.zzu().zzk.zza(this.zza);
        this.zzb.zzt().zzad().zza("Session timeout duration set", Long.valueOf(this.zza));
    }
}
