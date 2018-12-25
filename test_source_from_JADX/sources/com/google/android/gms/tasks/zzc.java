package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzc<TResult, TContinuationResult> implements OnFailureListener, OnSuccessListener<TContinuationResult>, zzm<TResult> {
    private final Executor zza;
    private final Continuation<TResult, Task<TContinuationResult>> zzb;
    private final zzp<TContinuationResult> zzc;

    public zzc(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation, @NonNull zzp<TContinuationResult> zzp) {
        this.zza = executor;
        this.zzb = continuation;
        this.zzc = zzp;
    }

    public final void onFailure(@NonNull Exception exception) {
        this.zzc.zza(exception);
    }

    public final void onSuccess(TContinuationResult tContinuationResult) {
        this.zzc.zza((Object) tContinuationResult);
    }

    public final void zza() {
        throw new UnsupportedOperationException();
    }

    public final void zza(@NonNull Task<TResult> task) {
        this.zza.execute(new zzd(this, task));
    }
}
