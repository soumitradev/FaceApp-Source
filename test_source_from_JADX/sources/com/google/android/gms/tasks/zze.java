package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zze<TResult> implements zzm<TResult> {
    private final Executor zza;
    private final Object zzb = new Object();
    private OnCompleteListener<TResult> zzc;

    public zze(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zza = executor;
        this.zzc = onCompleteListener;
    }

    public final void zza() {
        synchronized (this.zzb) {
            this.zzc = null;
        }
    }

    public final void zza(@NonNull Task<TResult> task) {
        synchronized (this.zzb) {
            if (this.zzc == null) {
                return;
            }
            this.zza.execute(new zzf(this, task));
        }
    }
}
