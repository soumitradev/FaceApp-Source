package com.google.android.gms.internal;

final class zzcng implements Runnable {
    private /* synthetic */ long zza;
    private /* synthetic */ zzcnd zzb;

    zzcng(zzcnd zzcnd, long j) {
        this.zzb = zzcnd;
        this.zza = j;
    }

    public final void run() {
        this.zzb.zza(this.zza);
    }
}
