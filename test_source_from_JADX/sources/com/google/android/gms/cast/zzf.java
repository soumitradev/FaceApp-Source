package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast$CastApi.zza;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzbdp;
import com.google.android.gms.internal.zzbea;

final class zzf extends zzbea {
    private /* synthetic */ String zza;
    private /* synthetic */ String zzb;

    zzf(zza zza, GoogleApiClient googleApiClient, String str, String str2) {
        this.zza = str;
        this.zzb = str2;
        super(googleApiClient);
    }

    public final void zza(zzbdp zzbdp) throws RemoteException {
        try {
            zzbdp.zza(this.zza, this.zzb, (zzn) this);
        } catch (IllegalArgumentException e) {
            zza((int) CastStatusCodes.INVALID_REQUEST);
        }
    }
}
