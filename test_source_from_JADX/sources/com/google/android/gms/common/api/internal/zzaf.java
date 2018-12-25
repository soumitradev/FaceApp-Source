package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult$zza;
import com.google.android.gms.common.api.Status;

final class zzaf implements PendingResult$zza {
    private /* synthetic */ BasePendingResult zza;
    private /* synthetic */ zzae zzb;

    zzaf(zzae zzae, BasePendingResult basePendingResult) {
        this.zzb = zzae;
        this.zza = basePendingResult;
    }

    public final void zza(Status status) {
        this.zzb.zza.remove(this.zza);
    }
}
