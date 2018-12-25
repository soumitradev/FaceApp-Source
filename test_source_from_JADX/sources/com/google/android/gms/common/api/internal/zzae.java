package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public final class zzae {
    private final Map<BasePendingResult<?>, Boolean> zza = Collections.synchronizedMap(new WeakHashMap());
    private final Map<TaskCompletionSource<?>, Boolean> zzb = Collections.synchronizedMap(new WeakHashMap());

    private final void zza(boolean z, Status status) {
        synchronized (this.zza) {
            Map hashMap = new HashMap(this.zza);
        }
        synchronized (this.zzb) {
            Map hashMap2 = new HashMap(this.zzb);
        }
        for (Entry entry : hashMap.entrySet()) {
            if (z || ((Boolean) entry.getValue()).booleanValue()) {
                ((BasePendingResult) entry.getKey()).zzd(status);
            }
        }
        for (Entry entry2 : hashMap2.entrySet()) {
            if (z || ((Boolean) entry2.getValue()).booleanValue()) {
                ((TaskCompletionSource) entry2.getKey()).trySetException(new ApiException(status));
            }
        }
    }

    final void zza(BasePendingResult<? extends Result> basePendingResult, boolean z) {
        this.zza.put(basePendingResult, Boolean.valueOf(z));
        basePendingResult.zza(new zzaf(this, basePendingResult));
    }

    final <TResult> void zza(TaskCompletionSource<TResult> taskCompletionSource, boolean z) {
        this.zzb.put(taskCompletionSource, Boolean.valueOf(z));
        taskCompletionSource.getTask().addOnCompleteListener(new zzag(this, taskCompletionSource));
    }

    final boolean zza() {
        if (this.zza.isEmpty()) {
            if (this.zzb.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public final void zzb() {
        zza(false, zzbm.zza);
    }

    public final void zzc() {
        zza(true, zzdk.zza);
    }
}
