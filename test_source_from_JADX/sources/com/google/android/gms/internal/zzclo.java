package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzclo implements Runnable {
    private /* synthetic */ AtomicReference zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ String zzc;
    private /* synthetic */ String zzd;
    private /* synthetic */ zzclk zze;

    zzclo(zzclk zzclk, AtomicReference atomicReference, String str, String str2, String str3) {
        this.zze = zzclk;
        this.zza = atomicReference;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = str3;
    }

    public final void run() {
        this.zze.zzp.zzw().zza(this.zza, this.zzb, this.zzc, this.zzd);
    }
}
