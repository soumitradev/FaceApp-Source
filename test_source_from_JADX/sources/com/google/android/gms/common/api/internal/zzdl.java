package com.google.android.gms.common.api.internal;

final class zzdl implements zzdn {
    private /* synthetic */ zzdk zza;

    zzdl(zzdk zzdk) {
        this.zza = zzdk;
    }

    public final void zza(BasePendingResult<?> basePendingResult) {
        this.zza.zzb.remove(basePendingResult);
    }
}
