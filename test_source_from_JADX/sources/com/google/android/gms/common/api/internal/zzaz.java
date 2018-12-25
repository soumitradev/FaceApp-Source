package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import java.util.Collections;

public final class zzaz implements zzbh {
    private final zzbi zza;

    public zzaz(zzbi zzbi) {
        this.zza = zzbi;
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zza(T t) {
        this.zza.zzd.zza.add(t);
        return t;
    }

    public final void zza() {
        for (zze zzg : this.zza.zza.values()) {
            zzg.zzg();
        }
        this.zza.zzd.zzc = Collections.emptySet();
    }

    public final void zza(int i) {
    }

    public final void zza(Bundle bundle) {
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zzb(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    public final boolean zzb() {
        return true;
    }

    public final void zzc() {
        this.zza.zzh();
    }
}
