package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;

public interface zzbh {
    <A extends zzb, R extends Result, T extends zzm<R, A>> T zza(T t);

    void zza();

    void zza(int i);

    void zza(Bundle bundle);

    void zza(ConnectionResult connectionResult, Api<?> api, boolean z);

    <A extends zzb, T extends zzm<? extends Result, A>> T zzb(T t);

    boolean zzb();

    void zzc();
}
