package com.google.android.gms.common.api.internal;

final class BasePendingResult$zzb {
    private /* synthetic */ BasePendingResult zza;

    private BasePendingResult$zzb(BasePendingResult basePendingResult) {
        this.zza = basePendingResult;
    }

    protected final void finalize() throws Throwable {
        BasePendingResult.zzb(BasePendingResult.zza(this.zza));
        super.finalize();
    }
}
