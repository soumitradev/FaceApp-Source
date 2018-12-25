package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzaxa extends zzaxg<CredentialRequestResult> {
    private /* synthetic */ CredentialRequest zza;

    zzaxa(zzawz zzawz, GoogleApiClient googleApiClient, CredentialRequest credentialRequest) {
        this.zza = credentialRequest;
        super(googleApiClient);
    }

    protected final /* synthetic */ Result zza(Status status) {
        return zzawy.zza(status);
    }

    protected final void zza(Context context, zzaxn zzaxn) throws RemoteException {
        zzaxn.zza(new zzaxb(this), this.zza);
    }
}
