package com.google.android.gms.tagmanager;

final class zzab implements zzac {
    private Long zza;
    private /* synthetic */ boolean zzb;
    private /* synthetic */ zzy zzc;

    zzab(zzy zzy, boolean z) {
        this.zzc = zzy;
        this.zzb = z;
    }

    public final boolean zza(Container container) {
        if (!this.zzb) {
            return !container.isDefault();
        } else {
            long lastRefreshTime = container.getLastRefreshTime();
            if (this.zza == null) {
                this.zza = Long.valueOf(this.zzc.zzj.zza());
            }
            return lastRefreshTime + this.zza.longValue() >= this.zzc.zza.zza();
        }
    }
}
