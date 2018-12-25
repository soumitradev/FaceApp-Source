package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzag implements OnCompleteListener<TResult> {
    private /* synthetic */ TaskCompletionSource zza;
    private /* synthetic */ zzae zzb;

    zzag(zzae zzae, TaskCompletionSource taskCompletionSource) {
        this.zzb = zzae;
        this.zza = taskCompletionSource;
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzb.zzb.remove(this.zza);
    }
}
