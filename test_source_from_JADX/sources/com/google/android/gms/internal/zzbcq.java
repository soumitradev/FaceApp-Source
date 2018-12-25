package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

final class zzbcq implements ResultCallback<Status> {
    private /* synthetic */ long zza;
    private /* synthetic */ zzbcl zzb;

    zzbcq(zzbcl zzbcl, long j) {
        this.zzb = zzbcl;
        this.zza = j;
    }

    public final /* synthetic */ void onResult(Result result) {
        Status status = (Status) result;
        if (!status.isSuccess()) {
            this.zzb.zza(this.zza, status.getStatusCode());
        }
    }
}
