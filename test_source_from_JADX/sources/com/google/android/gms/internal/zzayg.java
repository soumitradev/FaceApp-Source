package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyApi.ProxyResult;
import com.google.android.gms.auth.api.zzd;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.Hide;

@Hide
abstract class zzayg extends zzm<ProxyResult, zzaxs> {
    public zzayg(GoogleApiClient googleApiClient) {
        super(zzd.zza, googleApiClient);
    }

    protected final /* synthetic */ Result zza(Status status) {
        return new zzayk(status);
    }

    protected abstract void zza(Context context, zzaxv zzaxv) throws RemoteException;

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzaxs zzaxs = (zzaxs) zzb;
        zza(zzaxs.zzaa(), (zzaxv) zzaxs.zzaf());
    }
}
