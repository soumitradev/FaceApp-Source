package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzaxe extends zzaxg<Status> {
    zzaxe(zzawz zzawz, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected final /* synthetic */ Result zza(Status status) {
        return status;
    }

    protected final void zza(Context context, zzaxn zzaxn) throws RemoteException {
        zzaxn.zza(new zzaxf(this));
    }
}
