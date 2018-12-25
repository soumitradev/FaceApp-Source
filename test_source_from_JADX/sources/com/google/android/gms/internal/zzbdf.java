package com.google.android.gms.internal;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzm;

public abstract class zzbdf<R extends Result> extends zzm<R, zzbdp> {
    public zzbdf(GoogleApiClient googleApiClient) {
        super(Cast.API, googleApiClient);
    }

    public final void zza(int i) {
        zza(zza(new Status(CastStatusCodes.INVALID_REQUEST)));
    }
}
