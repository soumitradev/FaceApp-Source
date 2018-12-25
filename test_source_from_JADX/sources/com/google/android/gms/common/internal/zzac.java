package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

final class zzac implements zzf {
    private /* synthetic */ ConnectionCallbacks zza;

    zzac(ConnectionCallbacks connectionCallbacks) {
        this.zza = connectionCallbacks;
    }

    public final void zza(int i) {
        this.zza.onConnectionSuspended(i);
    }

    public final void zza(@Nullable Bundle bundle) {
        this.zza.onConnected(bundle);
    }
}
