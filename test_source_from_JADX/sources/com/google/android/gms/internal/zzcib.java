package com.google.android.gms.internal;

final class zzcib implements Runnable {
    private /* synthetic */ String zza;
    private /* synthetic */ long zzb;
    private /* synthetic */ zzcia zzc;

    zzcib(zzcia zzcia, String str, long j) {
        this.zzc = zzcia;
        this.zza = str;
        this.zzb = j;
    }

    public final void run() {
        this.zzc.zza(this.zza, this.zzb);
    }
}
