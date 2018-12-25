package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.RemoteException;
import com.google.android.gms.auth.account.zzc;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzm;

final class zzavu extends zzm<Result, zzawa> {
    private /* synthetic */ Account zza;

    zzavu(zzavq zzavq, Api api, GoogleApiClient googleApiClient, Account account) {
        this.zza = account;
        super(api, googleApiClient);
    }

    protected final Result zza(Status status) {
        return new zzavz(status);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzc) ((zzawa) zzb).zzaf()).zza(new zzavv(this), this.zza);
    }
}
