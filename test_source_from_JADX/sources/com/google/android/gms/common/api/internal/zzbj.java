package com.google.android.gms.common.api.internal;

abstract class zzbj {
    private final zzbh zza;

    protected zzbj(zzbh zzbh) {
        this.zza = zzbh;
    }

    protected abstract void zza();

    public final void zza(zzbi zzbi) {
        zzbi.zzf.lock();
        try {
            if (zzbi.zzn == this.zza) {
                zza();
            }
            zzbi.zzf.unlock();
        } catch (Throwable th) {
            zzbi.zzf.unlock();
        }
    }
}
