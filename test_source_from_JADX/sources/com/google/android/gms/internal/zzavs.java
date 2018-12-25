package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.auth.account.WorkAccountApi.AddAccountResult;
import com.google.android.gms.auth.account.zzc;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzm;

final class zzavs extends zzm<AddAccountResult, zzawa> {
    private /* synthetic */ String zza;

    zzavs(zzavq zzavq, Api api, GoogleApiClient googleApiClient, String str) {
        this.zza = str;
        super(api, googleApiClient);
    }

    protected final /* synthetic */ Result zza(Status status) {
        return new zzavx(status, null);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzc) ((zzawa) zzb).zzaf()).zza(new zzavt(this), this.zza);
    }
}
