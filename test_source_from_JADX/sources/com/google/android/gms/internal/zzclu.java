package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzclu implements Runnable {
    private /* synthetic */ AtomicReference zza;
    private /* synthetic */ boolean zzb;
    private /* synthetic */ zzclk zzc;

    zzclu(zzclk zzclk, AtomicReference atomicReference, boolean z) {
        this.zzc = zzclk;
        this.zza = atomicReference;
        this.zzb = z;
    }

    public final void run() {
        this.zzc.zzi().zza(this.zza, this.zzb);
    }
}
