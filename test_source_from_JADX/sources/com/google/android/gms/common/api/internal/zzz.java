package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;

@Hide
public final class zzz<O extends ApiOptions> extends GoogleApi<O> {
    private final zze zzb;
    private final zzt zzc;
    private final zzr zzd;
    private final zza<? extends zzcyj, zzcyk> zze;

    public zzz(@NonNull Context context, Api<O> api, Looper looper, @NonNull zze zze, @NonNull zzt zzt, zzr zzr, zza<? extends zzcyj, zzcyk> zza) {
        super(context, api, looper);
        this.zzb = zze;
        this.zzc = zzt;
        this.zzd = zzr;
        this.zze = zza;
        this.zza.zza((GoogleApi) this);
    }

    public final zze zza(Looper looper, zzbo<O> zzbo) {
        this.zzc.zza(zzbo);
        return this.zzb;
    }

    public final zzcv zza(Context context, Handler handler) {
        return new zzcv(context, handler, this.zzd, this.zze);
    }

    public final zze zzh() {
        return this.zzb;
    }
}
