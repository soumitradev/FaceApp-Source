package com.google.android.gms.tasks;

final class zzj implements Runnable {
    private /* synthetic */ Task zza;
    private /* synthetic */ zzi zzb;

    zzj(zzi zzi, Task task) {
        this.zzb = zzi;
        this.zza = task;
    }

    public final void run() {
        synchronized (this.zzb.zzb) {
            if (this.zzb.zzc != null) {
                this.zzb.zzc.onSuccess(this.zza.getResult());
            }
        }
    }
}
