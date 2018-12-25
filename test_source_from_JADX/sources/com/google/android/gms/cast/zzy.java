package com.google.android.gms.cast;

import com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings;

final class zzy implements Runnable {
    private /* synthetic */ NotificationSettings zza;
    private /* synthetic */ CastRemoteDisplayLocalService zzb;

    zzy(CastRemoteDisplayLocalService castRemoteDisplayLocalService, NotificationSettings notificationSettings) {
        this.zzb = castRemoteDisplayLocalService;
        this.zza = notificationSettings;
    }

    public final void run() {
        this.zzb.zza(this.zza);
    }
}
