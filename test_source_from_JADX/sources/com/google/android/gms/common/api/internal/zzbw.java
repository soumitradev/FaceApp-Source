package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;

public final class zzbw<O extends ApiOptions> extends zzak {
    private final GoogleApi<O> zza;

    public zzbw(GoogleApi<O> googleApi) {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.zza = googleApi;
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zza(@NonNull T t) {
        return this.zza.zza(t);
    }

    public final void zza(zzdh zzdh) {
    }

    public final Context zzb() {
        return this.zza.zzg();
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zzb(@NonNull T t) {
        return this.zza.zzb(t);
    }

    public final void zzb(zzdh zzdh) {
    }

    public final Looper zzc() {
        return this.zza.zzf();
    }
}
