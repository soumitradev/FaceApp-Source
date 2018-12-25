package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzi<TResult> implements zzm<TResult> {
    private final Executor zza;
    private final Object zzb = new Object();
    private OnSuccessListener<? super TResult> zzc;

    public zzi(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zza = executor;
        this.zzc = onSuccessListener;
    }

    public final void zza() {
        synchronized (this.zzb) {
            this.zzc = null;
        }
    }

    public final void zza(@NonNull Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.zzb) {
                if (this.zzc == null) {
                    return;
                }
                this.zza.execute(new zzj(this, task));
            }
        }
    }
}
