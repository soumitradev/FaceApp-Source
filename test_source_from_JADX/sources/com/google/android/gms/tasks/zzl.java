package com.google.android.gms.tasks;

final class zzl implements Runnable {
    private /* synthetic */ Task zza;
    private /* synthetic */ zzk zzb;

    zzl(zzk zzk, Task task) {
        this.zzb = zzk;
        this.zza = task;
    }

    public final void run() {
        try {
            Task then = this.zzb.zzb.then(this.zza.getResult());
            if (then == null) {
                this.zzb.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            then.addOnSuccessListener(TaskExecutors.zza, this.zzb);
            then.addOnFailureListener(TaskExecutors.zza, this.zzb);
        } catch (Exception e) {
            if (e.getCause() instanceof Exception) {
                this.zzb.onFailure((Exception) e.getCause());
            } else {
                this.zzb.onFailure(e);
            }
        } catch (Exception e2) {
            this.zzb.onFailure(e2);
        }
    }
}
