package com.google.android.gms.internal;

final class zzcid implements Runnable {
    private /* synthetic */ long zza;
    private /* synthetic */ zzcia zzb;

    zzcid(zzcia zzcia, long j) {
        this.zzb = zzcia;
        this.zza = j;
    }

    public final void run() {
        this.zzb.zzb(this.zza);
    }
}
