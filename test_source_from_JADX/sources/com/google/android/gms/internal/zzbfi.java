package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.clearcut.zzb;
import com.google.android.gms.clearcut.zze;
import com.google.android.gms.common.api.Api$ApiOptions$NoOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzg;

public final class zzbfi extends GoogleApi<Api$ApiOptions$NoOptions> implements zzb {
    private zzbfi(@NonNull Context context) {
        super(context, ClearcutLogger.zza, null, new zzg());
    }

    public static zzb zza(@NonNull Context context) {
        return new zzbfi(context);
    }

    public final PendingResult<Status> zza(zze zze) {
        return zzc(new zzbfl(zze, zze()));
    }
}
