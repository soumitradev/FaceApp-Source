package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbes extends zzbev {
    private /* synthetic */ String zzb;
    private /* synthetic */ zzbeq zzd;

    zzbes(zzbeq zzbeq, GoogleApiClient googleApiClient, String str) {
        this.zzd = zzbeq;
        this.zzb = str;
        super(zzbeq, googleApiClient);
    }

    public final void zza(zzbfa zzbfa) throws RemoteException {
        zzbfa.zza(new zzbew(this, zzbfa), this.zzd.zzd, this.zzb);
    }
}
