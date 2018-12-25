package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzclw implements Runnable {
    private /* synthetic */ AtomicReference zza;
    private /* synthetic */ zzclk zzb;

    zzclw(zzclk zzclk, AtomicReference atomicReference) {
        this.zzb = zzclk;
        this.zza = atomicReference;
    }

    public final void run() {
        this.zzb.zzi().zza(this.zza);
    }
}
