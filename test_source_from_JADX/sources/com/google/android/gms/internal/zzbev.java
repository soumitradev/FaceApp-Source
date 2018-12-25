package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplaySessionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzm;

class zzbev extends zzm<CastRemoteDisplaySessionResult, zzbfa> {
    final /* synthetic */ zzbeq zza;

    public zzbev(zzbeq zzbeq, GoogleApiClient googleApiClient) {
        this.zza = zzbeq;
        super(zzbeq.zzb, googleApiClient);
    }

    protected final /* synthetic */ Result zza(Status status) {
        return new zzbey(status);
    }

    public void zza(zzbfa zzbfa) throws RemoteException {
    }
}
