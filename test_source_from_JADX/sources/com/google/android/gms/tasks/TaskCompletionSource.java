package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

public class TaskCompletionSource<TResult> {
    private final zzp<TResult> zza = new zzp();

    @NonNull
    public Task<TResult> getTask() {
        return this.zza;
    }

    public void setException(@NonNull Exception exception) {
        this.zza.zza(exception);
    }

    public void setResult(TResult tResult) {
        this.zza.zza((Object) tResult);
    }

    public boolean trySetException(@NonNull Exception exception) {
        return this.zza.zzb(exception);
    }

    public boolean trySetResult(TResult tResult) {
        return this.zza.zzb((Object) tResult);
    }
}
