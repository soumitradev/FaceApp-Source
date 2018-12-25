package com.google.android.gms.internal;

final class zzcll implements Runnable {
    private /* synthetic */ boolean zza;
    private /* synthetic */ zzclk zzb;

    zzcll(zzclk zzclk, boolean z) {
        this.zzb = zzclk;
        this.zza = z;
    }

    public final void run() {
        this.zzb.zzc(this.zza);
    }
}
