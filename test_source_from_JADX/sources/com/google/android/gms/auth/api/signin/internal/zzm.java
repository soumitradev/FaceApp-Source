package com.google.android.gms.auth.api.signin.internal;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.Hide;

abstract class zzm<R extends Result> extends com.google.android.gms.common.api.internal.zzm<R, zze> {
    public zzm(GoogleApiClient googleApiClient) {
        super(Auth.GOOGLE_SIGN_IN_API, googleApiClient);
    }

    @Hide
    public final /* bridge */ /* synthetic */ void zza(Object obj) {
        super.zza((Result) obj);
    }
}
