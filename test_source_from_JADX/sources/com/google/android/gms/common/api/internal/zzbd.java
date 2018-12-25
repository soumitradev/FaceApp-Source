package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;

final class zzbd implements OnConnectionFailedListener {
    private /* synthetic */ zzdb zza;

    zzbd(zzba zzba, zzdb zzdb) {
        this.zza = zzdb;
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zza.zza(new Status(8));
    }
}
