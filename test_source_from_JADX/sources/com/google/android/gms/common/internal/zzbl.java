package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult$zza;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

final class zzbl implements PendingResult$zza {
    private /* synthetic */ PendingResult zza;
    private /* synthetic */ TaskCompletionSource zzb;
    private /* synthetic */ zzbo zzc;
    private /* synthetic */ zzbp zzd;

    zzbl(PendingResult pendingResult, TaskCompletionSource taskCompletionSource, zzbo zzbo, zzbp zzbp) {
        this.zza = pendingResult;
        this.zzb = taskCompletionSource;
        this.zzc = zzbo;
        this.zzd = zzbp;
    }

    public final void zza(Status status) {
        if (status.isSuccess()) {
            this.zzb.setResult(this.zzc.zza(this.zza.await(0, TimeUnit.MILLISECONDS)));
            return;
        }
        this.zzb.setException(this.zzd.zza(status));
    }
}
