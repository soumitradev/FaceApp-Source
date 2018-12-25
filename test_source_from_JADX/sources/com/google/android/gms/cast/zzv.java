package com.google.android.gms.cast;

final class zzv implements Runnable {
    private /* synthetic */ CastRemoteDisplayLocalService zza;

    zzv(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zza = castRemoteDisplayLocalService;
    }

    public final void run() {
        CastRemoteDisplayLocalService castRemoteDisplayLocalService = this.zza;
        boolean zzb = this.zza.zzr;
        StringBuilder stringBuilder = new StringBuilder(59);
        stringBuilder.append("onCreate after delay. The local service been started: ");
        stringBuilder.append(zzb);
        castRemoteDisplayLocalService.zza(stringBuilder.toString());
        if (!this.zza.zzr) {
            this.zza.zzb("The local service has not been been started, stopping it");
            this.zza.stopSelf();
        }
    }
}
