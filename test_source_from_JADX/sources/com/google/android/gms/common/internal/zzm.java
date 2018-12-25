package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;

public final class zzm implements zzj {
    private /* synthetic */ zzd zza;

    public zzm(zzd zzd) {
        this.zza = zzd;
    }

    public final void zza(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.isSuccess()) {
            this.zza.zza(null, this.zza.zzah());
            return;
        }
        if (zzd.zzg(this.zza) != null) {
            zzd.zzg(this.zza).zza(connectionResult);
        }
    }
}
