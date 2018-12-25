package com.google.android.gms.common.api.internal;

final class zzw implements Runnable {
    private /* synthetic */ zzv zza;

    zzw(zzv zzv) {
        this.zza = zzv;
    }

    public final void run() {
        this.zza.zzm.lock();
        try {
            this.zza.zzh();
        } finally {
            this.zza.zzm.unlock();
        }
    }
}
