package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast$CastApi.zza;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzbdp;

final class zzg extends zza {
    private /* synthetic */ String zza;

    zzg(zza zza, GoogleApiClient googleApiClient, String str) {
        this.zza = str;
        super(googleApiClient);
    }

    public final void zza(zzbdp zzbdp) throws RemoteException {
        try {
            String str = this.zza;
            LaunchOptions launchOptions = new LaunchOptions();
            launchOptions.setRelaunchIfRunning(false);
            zzbdp.zza(str, launchOptions, (zzn) this);
        } catch (IllegalStateException e) {
            zza((int) CastStatusCodes.INVALID_REQUEST);
        }
    }
}
