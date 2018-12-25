package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;

abstract class zzay implements Runnable {
    private /* synthetic */ zzao zza;

    private zzay(zzao zzao) {
        this.zza = zzao;
    }

    @WorkerThread
    public void run() {
        this.zza.zzb.lock();
        try {
            if (!Thread.interrupted()) {
                zza();
            }
        } catch (RuntimeException e) {
            this.zza.zza.zza(e);
        } catch (Throwable th) {
            this.zza.zzb.unlock();
        }
        this.zza.zzb.unlock();
    }

    @WorkerThread
    protected abstract void zza();
}
