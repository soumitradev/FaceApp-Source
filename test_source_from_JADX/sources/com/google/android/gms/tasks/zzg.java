package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzg<TResult> implements zzm<TResult> {
    private final Executor zza;
    private final Object zzb = new Object();
    private OnFailureListener zzc;

    public zzg(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zza = executor;
        this.zzc = onFailureListener;
    }

    public final void zza() {
        synchronized (this.zzb) {
            this.zzc = null;
        }
    }

    public final void zza(@NonNull Task<TResult> task) {
        if (!task.isSuccessful()) {
            synchronized (this.zzb) {
                if (this.zzc == null) {
                    return;
                }
                this.zza.execute(new zzh(this, task));
            }
        }
    }
}
