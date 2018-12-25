package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast$CastApi.zza;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzbdp;

final class zzi extends zza {
    private /* synthetic */ String zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ zzab zzd = null;

    zzi(zza zza, GoogleApiClient googleApiClient, String str, String str2, zzab zzab) {
        this.zza = str;
        this.zzb = str2;
        super(googleApiClient);
    }

    public final void zza(zzbdp zzbdp) throws RemoteException {
        try {
            zzbdp.zza(this.zza, this.zzb, this.zzd, (zzn) this);
        } catch (IllegalStateException e) {
            zza((int) CastStatusCodes.INVALID_REQUEST);
        }
    }
}
