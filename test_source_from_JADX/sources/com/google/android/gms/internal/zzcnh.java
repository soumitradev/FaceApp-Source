package com.google.android.gms.internal;

final class zzcnh implements Runnable {
    private /* synthetic */ long zza;
    private /* synthetic */ zzcnd zzb;

    zzcnh(zzcnd zzcnd, long j) {
        this.zzb = zzcnd;
        this.zza = j;
    }

    public final void run() {
        this.zzb.zzb(this.zza);
    }
}
