package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

final class zzbr implements Runnable {
    private /* synthetic */ ConnectionResult zza;
    private /* synthetic */ zzbo zzb;

    zzbr(zzbo zzbo, ConnectionResult connectionResult) {
        this.zzb = zzbo;
        this.zza = connectionResult;
    }

    public final void run() {
        this.zzb.onConnectionFailed(this.zza);
    }
}
