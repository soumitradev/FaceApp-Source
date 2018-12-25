package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast$CastApi.zza;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzbdp;
import com.google.android.gms.internal.zzbea;

final class zzj extends zzbea {
    zzj(zza zza, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    public final void zza(zzbdp zzbdp) throws RemoteException {
        try {
            zzbdp.zza((zzn) this);
        } catch (IllegalStateException e) {
            zza((int) CastStatusCodes.INVALID_REQUEST);
        }
    }
}
