package com.google.android.gms.internal;

final class zzcjt implements Runnable {
    private /* synthetic */ boolean zza;
    private /* synthetic */ zzcjs zzb;

    zzcjt(zzcjs zzcjs, boolean z) {
        this.zzb = zzcjs;
        this.zza = z;
    }

    public final void run() {
        this.zzb.zzb.zza(this.zza);
    }
}
