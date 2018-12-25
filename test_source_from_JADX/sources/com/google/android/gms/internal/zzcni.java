package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;

final class zzcni {
    private final zze zza;
    private long zzb;

    public zzcni(zze zze) {
        zzbq.zza(zze);
        this.zza = zze;
    }

    public final void zza() {
        this.zzb = this.zza.zzb();
    }

    public final boolean zza(long j) {
        return this.zzb == 0 || this.zza.zzb() - this.zzb >= 3600000;
    }

    public final void zzb() {
        this.zzb = 0;
    }
}
