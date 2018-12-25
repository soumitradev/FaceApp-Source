package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbq;

final class zzp {
    private final int zza;
    private final ConnectionResult zzb;

    zzp(ConnectionResult connectionResult, int i) {
        zzbq.zza(connectionResult);
        this.zzb = connectionResult;
        this.zza = i;
    }

    final int zza() {
        return this.zza;
    }

    final ConnectionResult zzb() {
        return this.zzb;
    }
}
