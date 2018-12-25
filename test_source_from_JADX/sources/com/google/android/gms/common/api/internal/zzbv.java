package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import java.util.Collections;

final class zzbv implements Runnable {
    private /* synthetic */ ConnectionResult zza;
    private /* synthetic */ zzbu zzb;

    zzbv(zzbu zzbu, ConnectionResult connectionResult) {
        this.zzb = zzbu;
        this.zza = connectionResult;
    }

    public final void run() {
        if (this.zza.isSuccess()) {
            this.zzb.zzf = true;
            if (this.zzb.zzb.l_()) {
                this.zzb.zza();
                return;
            } else {
                this.zzb.zzb.zza(null, Collections.emptySet());
                return;
            }
        }
        ((zzbo) this.zzb.zza.zzm.get(this.zzb.zzc)).onConnectionFailed(this.zza);
    }
}
