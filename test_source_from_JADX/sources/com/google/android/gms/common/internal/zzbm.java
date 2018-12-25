package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;

final class zzbm implements zzbo<R, T> {
    private /* synthetic */ Response zza;

    zzbm(Response response) {
        this.zza = response;
    }

    public final /* synthetic */ Object zza(Result result) {
        this.zza.setResult(result);
        return this.zza;
    }
}
