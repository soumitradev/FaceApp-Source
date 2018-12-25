package com.google.android.gms.common.api.internal;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;
import java.util.Set;

@Hide
public final class zzj {
    private final ArrayMap<zzh<?>, ConnectionResult> zza = new ArrayMap();
    private final ArrayMap<zzh<?>, String> zzb = new ArrayMap();
    private final TaskCompletionSource<Map<zzh<?>, String>> zzc = new TaskCompletionSource();
    private int zzd;
    private boolean zze = false;

    public zzj(Iterable<? extends GoogleApi<?>> iterable) {
        for (GoogleApi zzc : iterable) {
            this.zza.put(zzc.zzc(), null);
        }
        this.zzd = this.zza.keySet().size();
    }

    public final Set<zzh<?>> zza() {
        return this.zza.keySet();
    }

    public final void zza(zzh<?> zzh, ConnectionResult connectionResult, @Nullable String str) {
        this.zza.put(zzh, connectionResult);
        this.zzb.put(zzh, str);
        this.zzd--;
        if (!connectionResult.isSuccess()) {
            this.zze = true;
        }
        if (this.zzd == 0) {
            if (this.zze) {
                this.zzc.setException(new AvailabilityException(this.zza));
                return;
            }
            this.zzc.setResult(this.zzb);
        }
    }

    public final Task<Map<zzh<?>, String>> zzb() {
        return this.zzc.getTask();
    }
}
