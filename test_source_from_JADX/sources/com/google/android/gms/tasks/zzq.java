package com.google.android.gms.tasks;

import java.util.concurrent.Callable;

final class zzq implements Runnable {
    private /* synthetic */ zzp zza;
    private /* synthetic */ Callable zzb;

    zzq(zzp zzp, Callable callable) {
        this.zza = zzp;
        this.zzb = callable;
    }

    public final void run() {
        try {
            this.zza.zza(this.zzb.call());
        } catch (Exception e) {
            this.zza.zza(e);
        }
    }
}
