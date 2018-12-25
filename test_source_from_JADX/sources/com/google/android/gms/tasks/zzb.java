package com.google.android.gms.tasks;

final class zzb implements Runnable {
    private /* synthetic */ Task zza;
    private /* synthetic */ zza zzb;

    zzb(zza zza, Task task) {
        this.zzb = zza;
        this.zza = task;
    }

    public final void run() {
        try {
            this.zzb.zzc.zza(this.zzb.zzb.then(this.zza));
        } catch (Exception e) {
            if (e.getCause() instanceof Exception) {
                this.zzb.zzc.zza((Exception) e.getCause());
            } else {
                this.zzb.zzc.zza(e);
            }
        } catch (Exception e2) {
            this.zzb.zzc.zza(e2);
        }
    }
}
