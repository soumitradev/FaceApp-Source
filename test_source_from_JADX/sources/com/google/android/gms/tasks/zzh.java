package com.google.android.gms.tasks;

final class zzh implements Runnable {
    private /* synthetic */ Task zza;
    private /* synthetic */ zzg zzb;

    zzh(zzg zzg, Task task) {
        this.zzb = zzg;
        this.zza = task;
    }

    public final void run() {
        synchronized (this.zzb.zzb) {
            if (this.zzb.zzc != null) {
                this.zzb.zzc.onFailure(this.zza.getException());
            }
        }
    }
}
