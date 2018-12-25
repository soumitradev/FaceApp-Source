package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyRequest;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzayi extends zzayg {
    private /* synthetic */ ProxyRequest zza;

    zzayi(zzayh zzayh, GoogleApiClient googleApiClient, ProxyRequest proxyRequest) {
        this.zza = proxyRequest;
        super(googleApiClient);
    }

    protected final void zza(Context context, zzaxv zzaxv) throws RemoteException {
        zzaxv.zza(new zzayj(this), this.zza);
    }
}
