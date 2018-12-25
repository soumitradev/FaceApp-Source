package com.google.android.gms.cast;

import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.cast.Cast$CastApi.zza;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzbdp;
import com.google.android.gms.internal.zzbea;

final class zzl extends zzbea {
    private /* synthetic */ String zza;

    zzl(zza zza, GoogleApiClient googleApiClient, String str) {
        this.zza = str;
        super(googleApiClient);
    }

    public final void zza(zzbdp zzbdp) throws RemoteException {
        if (TextUtils.isEmpty(this.zza)) {
            zza(zza(new Status(CastStatusCodes.INVALID_REQUEST, "IllegalArgument: sessionId cannot be null or empty", null)));
            return;
        }
        try {
            zzbdp.zza(this.zza, (zzn) this);
        } catch (IllegalStateException e) {
            zza((int) CastStatusCodes.INVALID_REQUEST);
        }
    }
}
