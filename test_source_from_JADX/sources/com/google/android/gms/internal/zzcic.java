package com.google.android.gms.internal;

final class zzcic implements Runnable {
    private /* synthetic */ String zza;
    private /* synthetic */ long zzb;
    private /* synthetic */ zzcia zzc;

    zzcic(zzcia zzcia, String str, long j) {
        this.zzc = zzcia;
        this.zza = str;
        this.zzb = j;
    }

    public final void run() {
        this.zzc.zzb(this.zza, this.zzb);
    }
}
