package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

final class zzad implements zzg {
    private /* synthetic */ OnConnectionFailedListener zza;

    zzad(OnConnectionFailedListener onConnectionFailedListener) {
        this.zza = onConnectionFailedListener;
    }

    public final void zza(@NonNull ConnectionResult connectionResult) {
        this.zza.onConnectionFailed(connectionResult);
    }
}
