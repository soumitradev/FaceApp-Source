package com.google.android.gms.cast;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.Options;
import com.google.android.gms.common.api.Status;

final class zzw implements ServiceConnection {
    private /* synthetic */ String zza;
    private /* synthetic */ CastDevice zzb;
    private /* synthetic */ Options zzc;
    private /* synthetic */ NotificationSettings zzd;
    private /* synthetic */ Context zze;
    private /* synthetic */ Callbacks zzf;

    zzw(String str, CastDevice castDevice, Options options, NotificationSettings notificationSettings, Context context, Callbacks callbacks) {
        this.zza = str;
        this.zzb = castDevice;
        this.zzc = options;
        this.zzd = notificationSettings;
        this.zze = context;
        this.zzf = callbacks;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        CastRemoteDisplayLocalService castRemoteDisplayLocalService = ((zza) iBinder).zza;
        if (castRemoteDisplayLocalService == null || !castRemoteDisplayLocalService.zza(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this, this.zzf)) {
            CastRemoteDisplayLocalService.zza.zzd("Connected but unable to get the service instance", new Object[0]);
            this.zzf.onRemoteDisplaySessionError(new Status(CastStatusCodes.ERROR_SERVICE_CREATION_FAILED));
            CastRemoteDisplayLocalService.zzd.set(false);
            try {
                this.zze.unbindService(this);
            } catch (IllegalArgumentException e) {
                CastRemoteDisplayLocalService.zza.zza("No need to unbind service, already unbound", new Object[0]);
            }
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        CastRemoteDisplayLocalService.zza.zza("onServiceDisconnected", new Object[0]);
        this.zzf.onRemoteDisplaySessionError(new Status(CastStatusCodes.ERROR_SERVICE_DISCONNECTED, "Service Disconnected"));
        CastRemoteDisplayLocalService.zzd.set(false);
        try {
            this.zze.unbindService(this);
        } catch (IllegalArgumentException e) {
            CastRemoteDisplayLocalService.zza.zza("No need to unbind service, already unbound", new Object[0]);
        }
    }
}
