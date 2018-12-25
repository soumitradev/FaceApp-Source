package com.google.android.gms.cast;

final class zzx implements Runnable {
    private /* synthetic */ boolean zza;
    private /* synthetic */ CastRemoteDisplayLocalService zzb;

    zzx(CastRemoteDisplayLocalService castRemoteDisplayLocalService, boolean z) {
        this.zzb = castRemoteDisplayLocalService;
        this.zza = z;
    }

    public final void run() {
        this.zzb.zzb(this.zza);
    }
}
