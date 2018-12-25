package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.auth.account.zzc;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzm;

final class zzavr extends zzm<Result, zzawa> {
    private /* synthetic */ boolean zza;

    zzavr(zzavq zzavq, Api api, GoogleApiClient googleApiClient, boolean z) {
        this.zza = z;
        super(api, googleApiClient);
    }

    protected final Result zza(Status status) {
        return new zzavy(status);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzc) ((zzawa) zzb).zzaf()).zza(this.zza);
        zza(new zzavy(Status.zza));
    }
}
