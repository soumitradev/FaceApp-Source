package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzclp implements Runnable {
    private /* synthetic */ AtomicReference zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ String zzc;
    private /* synthetic */ String zzd;
    private /* synthetic */ boolean zze;
    private /* synthetic */ zzclk zzf;

    zzclp(zzclk zzclk, AtomicReference atomicReference, String str, String str2, String str3, boolean z) {
        this.zzf = zzclk;
        this.zza = atomicReference;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = str3;
        this.zze = z;
    }

    public final void run() {
        this.zzf.zzp.zzw().zza(this.zza, this.zzb, this.zzc, this.zzd, this.zze);
    }
}
