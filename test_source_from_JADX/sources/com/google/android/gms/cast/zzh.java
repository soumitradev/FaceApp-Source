package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast$CastApi.zza;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzbdp;

final class zzh extends zza {
    private /* synthetic */ String zza;
    private /* synthetic */ LaunchOptions zzb;

    zzh(zza zza, GoogleApiClient googleApiClient, String str, LaunchOptions launchOptions) {
        this.zza = str;
        this.zzb = launchOptions;
        super(googleApiClient);
    }

    public final void zza(zzbdp zzbdp) throws RemoteException {
        try {
            zzbdp.zza(this.zza, this.zzb, (zzn) this);
        } catch (IllegalStateException e) {
            zza((int) CastStatusCodes.INVALID_REQUEST);
        }
    }
}
