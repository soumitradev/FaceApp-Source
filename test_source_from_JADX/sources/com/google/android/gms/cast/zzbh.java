package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;

final class zzbh extends RemoteMediaPlayer$zzb {
    private /* synthetic */ GoogleApiClient zzb;
    private /* synthetic */ RemoteMediaPlayer zzd;

    zzbh(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2) {
        this.zzd = remoteMediaPlayer;
        this.zzb = googleApiClient2;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zzd)) {
            RemoteMediaPlayer$zza zzf;
            RemoteMediaPlayer.zzf(this.zzd).zza(this.zzb);
            try {
                RemoteMediaPlayer.zzg(this.zzd).zza(this.zza);
                zzf = RemoteMediaPlayer.zzf(this.zzd);
            } catch (IllegalStateException e) {
                try {
                    zza((MediaChannelResult) zza(new Status(2100)));
                    zzf = RemoteMediaPlayer.zzf(this.zzd);
                } catch (Throwable th) {
                    RemoteMediaPlayer.zzf(this.zzd).zza(null);
                }
            }
            zzf.zza(null);
        }
    }
}
