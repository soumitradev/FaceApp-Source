package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zza<TResult, TContinuationResult> implements zzm<TResult> {
    private final Executor zza;
    private final Continuation<TResult, TContinuationResult> zzb;
    private final zzp<TContinuationResult> zzc;

    public zza(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzp<TContinuationResult> zzp) {
        this.zza = executor;
        this.zzb = continuation;
        this.zzc = zzp;
    }

    public final void zza() {
        throw new UnsupportedOperationException();
    }

    public final void zza(@NonNull Task<TResult> task) {
        this.zza.execute(new zzb(this, task));
    }
}
