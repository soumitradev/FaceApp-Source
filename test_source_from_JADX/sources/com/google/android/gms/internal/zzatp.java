package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;

final class zzatp {
    private final zze zza;
    private long zzb;

    public zzatp(zze zze) {
        zzbq.zza(zze);
        this.zza = zze;
    }

    public zzatp(zze zze, long j) {
        zzbq.zza(zze);
        this.zza = zze;
        this.zzb = j;
    }

    public final void zza() {
        this.zzb = this.zza.zzb();
    }

    public final boolean zza(long j) {
        return this.zzb == 0 || this.zza.zzb() - this.zzb > j;
    }

    public final void zzb() {
        this.zzb = 0;
    }
}
