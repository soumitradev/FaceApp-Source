package com.google.android.gms.auth.api.signin.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzk extends zzm<Status> {
    zzk(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected final /* synthetic */ Result zza(Status status) {
        return status;
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zze zze = (zze) zzb;
        ((zzv) zze.zzaf()).zzc(new zzl(this), zze.m_());
    }
}
