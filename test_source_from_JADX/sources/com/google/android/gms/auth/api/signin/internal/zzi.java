package com.google.android.gms.auth.api.signin.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzi extends zzm<Status> {
    zzi(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected final /* synthetic */ Result zza(Status status) {
        return status;
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zze zze = (zze) zzb;
        ((zzv) zze.zzaf()).zzb(new zzj(this), zze.m_());
    }
}
