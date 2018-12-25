package com.google.android.gms.internal;

import com.google.android.gms.auth.api.proxy.ProxyApi.ProxyResult;
import com.google.android.gms.auth.api.proxy.ProxyResponse;
import com.google.android.gms.common.api.Status;

final class zzayk implements ProxyResult {
    private Status zza;
    private ProxyResponse zzb;

    public zzayk(ProxyResponse proxyResponse) {
        this.zzb = proxyResponse;
        this.zza = Status.zza;
    }

    public zzayk(Status status) {
        this.zza = status;
    }

    public final ProxyResponse getResponse() {
        return this.zzb;
    }

    public final Status getStatus() {
        return this.zza;
    }
}
