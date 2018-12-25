package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzaxd extends zzaxg<Status> {
    private /* synthetic */ Credential zza;

    zzaxd(zzawz zzawz, GoogleApiClient googleApiClient, Credential credential) {
        this.zza = credential;
        super(googleApiClient);
    }

    protected final /* synthetic */ Result zza(Status status) {
        return status;
    }

    protected final void zza(Context context, zzaxn zzaxn) throws RemoteException {
        zzaxn.zza(new zzaxf(this), new zzaxj(this.zza));
    }
}
