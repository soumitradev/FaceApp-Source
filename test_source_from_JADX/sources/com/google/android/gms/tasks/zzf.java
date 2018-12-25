package com.google.android.gms.tasks;

final class zzf implements Runnable {
    private /* synthetic */ Task zza;
    private /* synthetic */ zze zzb;

    zzf(zze zze, Task task) {
        this.zzb = zze;
        this.zza = task;
    }

    public final void run() {
        synchronized (this.zzb.zzb) {
            if (this.zzb.zzc != null) {
                this.zzb.zzc.onComplete(this.zza);
            }
        }
    }
}
